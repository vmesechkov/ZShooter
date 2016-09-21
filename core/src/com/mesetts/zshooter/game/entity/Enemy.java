package com.mesetts.zshooter.game.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mesetts.zshooter.InGameScreen;
import com.mesetts.zshooter.ZShooter;
import com.mesetts.zshooter.game.PathFinder;
import com.mesetts.zshooter.game.TileMap;
import com.mesetts.zshooter.game.weaponsys.Handgun;
import com.mesetts.zshooter.game.weaponsys.Weapon;

import java.util.Random;


public class Enemy extends Entity implements Pool.Poolable {

	private Game game;

	public Enemy(World world, int tileSize, Game game) {
		super(world, tileSize, CollisionShape.CIRCLE);
		this.game = game;

		//PathFinder.getPathFinder(map).findPath((int)player.body.getPosition().x, (int)player.body.getPosition().y, (int)body.getPosition().x, (int)body.getPosition().y, path);
//		long startTime = System.currentTimeMillis();
//		PathFinder.getPathFinder(map).findPath((int)player.body.getPosition().x, (int)player.body.getPosition().y, 7, 1, path);
//		long endTime = System.currentTimeMillis();
//		Gdx.app.log("PathFinding ", "Time taken: " + (endTime - startTime));

//		setPosition(posx, posy);

		weaponsSheet = new Texture(Gdx.files.internal("data/weapons_sheet_128.png"));
		weaponsFrames = TextureRegion.split(weaponsSheet, weaponsSheet.getWidth() / 14, weaponsSheet.getHeight() / 3);
	}

	Texture weaponsSheet;
	TextureRegion[][] weaponsFrames;

	public void init(int health, float posX, float posY) {
		body.setActive(true);
		body.setAwake(true);
		setHealth(health);
		body.setTransform(posX, posY, 0);
		state = State.IDLE;
	}

	@Override
	public void reset() {
		body.setAwake(false);
		body.setActive(false);
	}

	public enum State {
		IDLE,
		CHASE,
		ATTACK,
		ATTACK_IDLE,
		WANDER,
		DYING,
		DEAD
	}

	State state = State.IDLE;

	int[] path = new int[10000];

	Entity enemy;

	Object hitObj;
	public Entity findVisibleEnemy() {
		//Gdx.app.log("Enemy", " Finding nearest enemy");
		Array<Body> bodies = new Array<Body>();
		body.getWorld().getBodies(bodies);
		for (Body b: bodies) {
			if (b.getUserData() instanceof Player) {
				hitObj = null;
				body.getWorld().rayCast(new RayCastCallback() {
					@Override
					public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
						hitObj = fixture.getBody().getUserData();
						if (hitObj instanceof Enemy || hitObj instanceof com.mesetts.zshooter.game.weaponsys.Projectile) {
							hitObj = null;
							//Gdx.app.log("Enemy Chase", " Hitting a zombie, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
							return -1f;	//-1 to filter, 0 to stop, 1 to continue
						}
						if (hitObj instanceof TileMap) {
							//Gdx.app.log("Enemy Chase", " Hitting the map, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
							hitObj = null;
							return 0f;
						}
						if (hitObj instanceof Player) {
							//Gdx.app.log("Enemy Chase", " Hitting a player, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
							return -1f;
						}
						// Hit object is a wall
						return 0f;
					}
				}, body.getPosition(), b.getPosition());
				// If we've hit a player, chase...
				if (hitObj != null && hitObj instanceof Player) {
					return (Entity)hitObj;
				}
			}
		}
		// Nothing hit...return null
		return null;
	}

	Vector2 moveVec = new Vector2();

	Random r = new Random();
	int pointX;
	int pointY;
	int radius;
	float angle;
	boolean haveAPoint;
	Vector2 point = new Vector2();

	float idleTime;
	float idleTargetTime;
	float attackTime;
	boolean enemyHit;

	boolean iSeePlayer;

	public void update(float deltaTime) {

		if (health <= 0) {
			// If we're not dying yet, start dying...
			if (state != State.DYING && state != State.DEAD) {
				state = State.DYING;
				stateTime = 0;

				Weapon weapon = new Handgun(weaponsFrames[2], 25, 15, 12, 6, 1, 10, 0.4f, 13f, world);
				((InGameScreen)(InGameScreen.getInGameScreen(game))).getWeapons().add(weapon);
				weapon.drop(Gdx.app.getGraphics().getDeltaTime());
			}
			else {
				// If we're dying and finished our animation, lets get DEAD
				if (animation.getAnimation("Die").isAnimationFinished(stateTime) && state != State.DEAD) {
					position.set(body.getPosition());
					state = State.DEAD;
//					world.destroyBody(body);
//					body = null;
				} else {
					// If we're still dying or we're dead
					if (body != null) {
						body.setLinearVelocity(0, 0);
						body.setAngularVelocity(0);
					}
					animate("Die", deltaTime);
				}
			}
			return;
		}

		if (state == State.IDLE) {
			//Gdx.app.log("Enemy ", "State IDLE");
			// Check for a visible enemy and switch to CHASE
			enemy = findVisibleEnemy();
			if (enemy != null) {
				state = State.CHASE;
				update(deltaTime);
				return;
			}

			if (idleTime == 0f) {
				idleTargetTime = (r.nextFloat() * 1) + 1;
			}

			idleTime += deltaTime;
			if (idleTime > idleTargetTime) {
				idleTime = 0f;
				state = State.WANDER;
			}
			else {
				animate("Idle3", deltaTime);
				body.setLinearVelocity(0f, 0f);
				body.setAngularVelocity(0);
			}
		}

		if (state == State.WANDER) {
			//Gdx.app.log("Enemy ", "State WANDER");
			if (!haveAPoint) {
				while (true) {
					radius = r.nextInt(5);
					angle = r.nextFloat() * 360f;
					pointX = (int) (Math.sqrt(radius) * Math.cos(Math.toRadians(angle)) + body.getPosition().x);
					pointY = (int) (Math.sqrt(radius) * Math.sin(Math.toRadians(angle)) + body.getPosition().y);

					hitObj = null;
					body.getWorld().rayCast(new RayCastCallback() {
						@Override
						public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
							// Get owner of fixture
							hitObj = fixture.getBody().getUserData();
							if (hitObj instanceof Enemy || hitObj instanceof com.mesetts.zshooter.game.weaponsys.Projectile) {
								hitObj = null;
								//Gdx.app.log("Enemy Chase", " Hitting a zombie, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
								return -1f;
							}
							if (hitObj instanceof TileMap) {
								//Gdx.app.log("Enemy Chase", " Hitting the map, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
								hitObj = null;
								return 0f;
							}
							if (hitObj instanceof Player) {
								//Gdx.app.log("Enemy Chase", " Hitting a player, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
								return -1f;
							}
							// Hit object is a wall or a player
							return 0;
						}
					}, body.getPosition().x, body.getPosition().y, pointX, pointY);
					if (hitObj == null) {
						break;
					}
				}
				point.set(pointX, pointY);
				haveAPoint = true;
			}
			else {
				if (point.dst(body.getPosition()) > 0.6) {
					enemy = findVisibleEnemy();
					if (enemy != null) {
						state = State.CHASE;
						update(deltaTime);
						return;
					}

					animate("Run", deltaTime);
					//Gdx.app.log("Enemy", " Going to point " + point.x + "," + point.y);
					moveVec.set(point);
					moveVec.sub(body.getPosition());
					moveVec.nor();
					setPan(90.0f + (float) (Math.toDegrees(Math.atan2(moveVec.y , moveVec.x))));
					this.body.setLinearVelocity(moveVec);
				}
				else {
					//Gdx.app.log("Enemy", " Point reached. Going idle...");
					haveAPoint = false;
					state = State.IDLE;
				}
			}
		}

		if (state == State.CHASE) {
			//Gdx.app.log("Enemy ", "State CHASE");
			// Make sure we're seeing the enemy
			iSeePlayer = true;
			body.getWorld().rayCast(new RayCastCallback() {
				@Override
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
					hitObj = fixture.getBody().getUserData();

					if (hitObj instanceof TileMap) {
						iSeePlayer = false;
						return 0;
					}
					return -1f;
				}
			}, body.getPosition().x, body.getPosition().y, enemy.body.getPosition().x, enemy.body.getPosition().y );

			// If we see the player, chase
			if (iSeePlayer) {
				if (body.getPosition().dst(enemy.body.getPosition()) > 0.6) {
					animate("Run", deltaTime);
					moveVec.set(enemy.body.getPosition().x - body.getPosition().x, enemy.body.getPosition().y - body.getPosition().y);
					moveVec.nor();
					body.setLinearVelocity(moveVec);
					setPan(90.0f + (float) (Math.toDegrees(Math.atan2(moveVec.y, moveVec.x))));
				}
				else {
					state = State.ATTACK;
					update(deltaTime);
					return;
				}
			}
			else {
				// Else, stay put
				enemy = null;
				state = State.IDLE;
			}
		}


		if (state == State.ATTACK) {
			if (enemy != null) {
				moveVec.set(enemy.body.getPosition().x - body.getPosition().x, enemy.body.getPosition().y - body.getPosition().y);
				setPan(90.0f + (float) (Math.toDegrees(Math.atan2(moveVec.y, moveVec.x))));

				body.setLinearVelocity(0f,0f);
				body.setAngularVelocity(0);

				animate("Attack", deltaTime);
				attackTime += deltaTime;
				if (attackTime > 0.4 && !enemyHit) {
					if (body.getPosition().dst(enemy.body.getPosition()) <= 0.8) {
						enemyHit = true;
						enemy.health -= 10;
						Gdx.app.log("Enemy ", "Player Health: " + enemy.health);
					}
				}
				// Idle after hit
				if (attackTime >= 0.6) {
					attackTime = 0;
					if (body.getPosition().dst(enemy.body.getPosition()) <= 0.8) {
						enemyHit = false;
						state = State.ATTACK_IDLE;
					}
					else {
						state = State.CHASE;
						update(deltaTime);
						return;
					}
				}
			}
			else {
				attackTime = 0;
				state = State.IDLE;
				update(deltaTime);
				return;
			}
			return;
		}
		if (state == State.ATTACK_IDLE) {
			body.setLinearVelocity(0f,0f);
			body.setAngularVelocity(0);

			attackTime += deltaTime;
			animate("Idle1", deltaTime);

			if (attackTime > 2f) {
				attackTime = 0;
				state = State.ATTACK;
			}
		}
	}

	public void update2(float deltaTime) {

		if (health <= 0) {
			// If we're not dying yet, start dying...
			if (state != State.DYING && state != State.DEAD) {
				state = State.DYING;
				stateTime = 0;
			}
			else {
				// If we're dying and finished our animation, lets get DEAD
				if (animation.getAnimation("Die").isAnimationFinished(stateTime) && state != State.DEAD) {
					position.set(body.getPosition());
					state = State.DEAD;
					world.destroyBody(body);
					body = null;
				} else {
					// If we're still dying or we're dead
					if (body != null) {
						body.setLinearVelocity(0, 0);
						body.setAngularVelocity(0);
					}
					animate("Die", deltaTime);
				}
			}
			return;
		}

		// We dont have anything to do...lets find something...
		if (state == State.IDLE) {
			Gdx.app.log("Enemy", " State Idle, Pan: " + getPan());

			if (idleTime == 0f) {
				idleTargetTime = (r.nextFloat() * 1) + 1;
			}

			animate("Idle3", deltaTime);
			body.setLinearVelocity(0f, 0f);
			body.setAngularVelocity(0);

			idleTime += deltaTime;
			if (idleTime > idleTargetTime) {
				idleTime = 0f;
			}
			else {
				enemy = findVisibleEnemy();
				if (enemy != null) {
					state = State.CHASE;
				}
				return;
			}

			// If we have an enemy, chase him
			if (enemy != null) {
				state = State.CHASE;
			}
			// If we dont have an enemy
			else {
				// Find one...
				enemy = findVisibleEnemy();
				// If we didnt find a visible enemy
				if (enemy == null) {
					// Wander
					state = State.WANDER;
				}
				// If we did
				else {
					// Chase
					state = State.CHASE;
				}
			}
			return;
		}
		// If we're chasing an enemy
		if (state == State.CHASE) {
			Gdx.app.log("Enemy", " State Chase");
			// Make sure we're seeing the enemy
			hitObj = null;
			body.getWorld().rayCast(new RayCastCallback() {
				@Override
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
					hitObj = fixture.getBody().getUserData();
					if (hitObj instanceof Enemy || hitObj instanceof com.mesetts.zshooter.game.weaponsys.Projectile) {
						hitObj = null;
						//Gdx.app.log("Enemy Chase", " Hitting a zombie, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
						return -1f;
					}
					if (hitObj instanceof TileMap) {
						//Gdx.app.log("Enemy Chase", " Hitting the map, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
						hitObj = null;
						return 0f;
					}
					if (hitObj instanceof Player) {
						//Gdx.app.log("Enemy Chase", " Hitting a player, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
						return 1f;
					}
					// Hit object is a wall
					return 0f;
				}
			}, body.getPosition().x, body.getPosition().y, enemy.body.getPosition().x, enemy.body.getPosition().y );
			// If we've hit a player, save his instance
			if (hitObj != null && hitObj instanceof Player) {
				enemy = (Entity)hitObj;
			}
			else {
				// If we havent hit a player, we dont see our enemy anymore...
				enemy = null;
			}

			// If we still have an enemy, we're seeing him...Chase!
			if (enemy != null) {
				if (body.getPosition().dst(enemy.body.getPosition()) > 0.6) {
					animate("Run", deltaTime);
					moveVec.set(enemy.body.getPosition().x - body.getPosition().x, enemy.body.getPosition().y - body.getPosition().y);
					moveVec.nor();
					body.setLinearVelocity(moveVec);
					setPan(90.0f + (float) (Math.toDegrees(Math.atan2(moveVec.y, moveVec.x))));
				}
				else {
					state = State.ATTACK;
				}
			}
			else {
				state = State.IDLE;
			}
			return;
		}
		if (state == State.WANDER) {
			Gdx.app.log("Enemy", " State Wander");
			if (!haveAPoint) {
				while (true) {
					radius = r.nextInt(5);
					angle = r.nextFloat() * 360f;
					pointX = (int) (Math.sqrt(radius) * Math.cos(Math.toRadians(angle)) + body.getPosition().x);
					pointY = (int) (Math.sqrt(radius) * Math.sin(Math.toRadians(angle)) + body.getPosition().y);

					hitObj = null;
					body.getWorld().rayCast(new RayCastCallback() {
						@Override
						public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
							// Get owner of fixture
							hitObj = fixture.getBody().getUserData();
							if (hitObj instanceof Enemy || hitObj instanceof com.mesetts.zshooter.game.weaponsys.Projectile) {
								hitObj = null;
								//Gdx.app.log("Enemy Chase", " Hitting a zombie, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
								return -1f;
							}
							if (hitObj instanceof TileMap) {
								//Gdx.app.log("Enemy Chase", " Hitting the map, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
								hitObj = null;
								return 0f;
							}
							if (hitObj instanceof Player) {
								//Gdx.app.log("Enemy Chase", " Hitting a player, Fraction: " + fraction + ", PointXY: " + point.x + ", " + point.y);
								return 1f;
							}
							// Hit object is a wall or a player
							return 0;
						}
					}, body.getPosition().x, body.getPosition().y, pointX, pointY);
					if (hitObj == null) {
						break;
					}
				}
				point.set(pointX, pointY);
				haveAPoint = true;
			}
			else {
				if (point.dst(body.getPosition()) > 0.6) {
					enemy = findVisibleEnemy();
					if (enemy != null) {
						state = State.CHASE;
						return;
					}

					animate("Run", deltaTime);
					//Gdx.app.log("Enemy", " Going to point " + point.x + "," + point.y);
					moveVec.set(point);
					moveVec.sub(body.getPosition());
					moveVec.nor();
					setPan(90.0f + (float) (Math.toDegrees(Math.atan2(moveVec.y , moveVec.x))));
					this.body.setLinearVelocity(moveVec);
				}
				else {
					//Gdx.app.log("Enemy", " Point reached. Going idle...");
					haveAPoint = false;
					state = State.IDLE;
				}
			}
			return;
		}




//		moveVec.set(player.body.getPosition().x - body.getPosition().x, player.body.getPosition().y - body.getPosition().y);
//		moveVec.nor();
//		this.body.setLinearVelocity(moveVec);
//		setPan(90.0f + (float) (Math.toDegrees(Math.atan2(moveVec.y , moveVec.x))));

//		if (state == State.IDLE) {
//			// Search for visible enemy
//			// No enemy - WANDER
//			return;
//		}
//		if (state == State.WANDER) {
//			// Get a random visible tile and walk towards it
//			// if we dont have a tile already.
//			// Else, just walk towards that tile
//			return;
//		}
//		if (state == State.CHASE) {
//			// If we dont have a path...
//			// either we've ran out of nodes, and we've reached the enemy
//			state = State.ATTACK;
//			return;
//			// or we havent had an enemy so far.
//			// construct a path
//
//			// If we do have a path, chase the enemy
//
//		}
//		if (state == State.ATTACK) {
//			// attack
//		}
	}

	public State getState() {
		return state;
	}

}
