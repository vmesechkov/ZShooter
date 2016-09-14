package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Arrays;

public class PathFinder {

	private static PathFinder pathFinder = null;

	TileMap map;
	byte[][] mapContent;

	public PathFinder(TileMap map) {
		this.map = map;
		mapContent = map.getContent();

//		openList = new TreeSet<Integer>(new Comparator<Integer>() {
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				if (fScores.get(o1) == null) {
//					if (fScores.get(o2) != null) {
//						return 0 - fScores.get(o2);
//					}
//					else {
//						return 0;
//					}
//				}
//				if (fScores.get(o2) == null) {
//					return fScores.get(o1);
//				}
//				return fScores.get(o1) - fScores.get(o2);
//			}
//		});
	}

	public int getMapLengthX() { return mapContent.length; }

	public int getHScore(int currentNode, int targetNodeX, int targetNodeY) {
		return 10 * ( Math.abs( (currentNode % mapContent.length) - targetNodeX ) + Math.abs( (currentNode / mapContent.length) - targetNodeY) );
	}

	int hScore;
	int gScore;
	int fScore;

	int currentX;
	int currentY;
	int targetX;
	int targetY;

	int currentNode;
	int adjacent;

	int[] adj = new int[8];
	int movementCost = 0;

//	final HashMap<Integer, Integer> fScores = new HashMap<Integer, Integer>();
//	final HashMap<Integer, Integer> gScores = new HashMap<Integer, Integer>();
//	final HashMap<Integer, Integer> hScores = new HashMap<Integer, Integer>();
//
//	final HashMap<Integer, Integer> parents = new HashMap<Integer, Integer>();
//
//	TreeSet<Integer> openList;
//	TreeSet<Integer> closedList = new TreeSet<Integer>();
//
//	boolean pathFound;
//
//	public boolean findPath(int startNode, int targetNode, ArrayList<Integer> pathContainer) {
//		pathFound = false;
//
//		// Clear the container of the path nodes
//		pathContainer.clear();
//
//		// Clear the open and closed lists
//		openList.clear();
//		closedList.clear();
//
//		// Set current to start node
//		currentNode = startNode;
//
//		// Add the start node to the open list and set its gScore to 0 (0 distance traveled)
//		openList.add(currentNode);
//		gScores.put(currentNode, 0);
//
//		// Calculate target XY coords by its index
//		targetX = targetNode % mapContent.length;
//		targetY = targetNode / mapContent.length;
//
//		// Calculate the heuristic distance to the goal node and calculate the final score fScore
//		hScore = getHScore(currentNode, targetX, targetY);
//		gScore = 0;
//		fScore = hScore + gScore;
//
//		// Store the scores of the node
//		hScores.put(currentNode, hScore);
//		gScores.put(currentNode, gScore);
//		fScores.put(currentNode, fScore);
//
//		// Run forever (break when a goal is found or when its not reachable)
//		while (true) {
//			Gdx.app.log("Searching", "Current node: " + currentNode);
//			// Get the lowest F score on the openList
//			try {
//				currentNode = openList.first();
//			}
//			// If there are no nodes on the openList...
//			catch (NoSuchElementException e) {
//				// Path has not been found, break and return false
//				pathFound = false;
//				break;
//			}
//
//			// Switch it to the closed list
//			openList.remove(currentNode);
//			closedList.add(currentNode);
//
//			// If we've found the target node, break, make a path and return true
//			if (currentNode == targetNode) {
//				pathFound = true;
//				break;
//			}
//
//			// Calculate current node's XY coordinates
//			currentX = currentNode % mapContent.length;
//			currentY = currentNode / mapContent.length;
//
//			// Set all 8 adjacent nodes
//			adj[0] = currentNode + 1;
//			adj[1] = currentNode + mapContent.length + 1;
//			adj[2] = currentNode + mapContent.length;
//			adj[3] = currentNode + mapContent.length - 1;
//			adj[4] = currentNode - 1;
//			adj[5] = currentNode - mapContent.length - 1;
//			adj[6] = currentNode - mapContent.length;
//			adj[7] = currentNode - mapContent.length + 1;
//
//			// Reset invalid nodes
//			if (currentX == mapContent.length - 1) {
//				adj[0] = currentNode;
//				adj[1] = currentNode;
//				adj[7] = currentNode;
//			}
//			if (currentX == 0) {
//				adj[3] = currentNode;
//				adj[4] = currentNode;
//				adj[5] = currentNode;
//			}
//			if (currentY == mapContent.length - 1) {
//				adj[1] = currentNode;
//				adj[2] = currentNode;
//				adj[3] = currentNode;
//			}
//			if (currentY == 0) {
//				adj[5] = currentNode;
//				adj[6] = currentNode;
//				adj[7] = currentNode;
//			}
//
////			if (currentX < map.getContent().length - 1) {
////				adj[0] = currentNode + 1;
////				if (currentY < map.getContent()[currentX].length) {
////					adj[1] = currentNode + map.getContent().length + 1;
////				}
////				else {
////					adj[1] = currentNode;
////				}
////			}
////			else {
////				adj[0] = currentNode;
////				adj[1] = currentNode;
////			}
////			if (currentY < map.getContent()[currentX].length) {
////				adj[2] = currentNode + map.getContent().length;
////				if (currentX > 0) {
////					adj[3] = currentNode + map.getContent().length - 1;
////				}
////				else {
////					adj[3] = currentNode;
////				}
////			}
////			else {
////				adj[2] = currentNode;
////				adj[3] = currentNode;
////			}
////			if (currentX > 0) {
////				adj[4] = currentNode - 1;
////				if (currentY > 0) {
////					adj[5] = currentNode - map.getContent().length - 1;
////				}
////				else {
////					adj[5] = currentNode;
////				}
////			}
////			else {
////				adj[4] = currentNode;
////				adj[5] = currentNode;
////			}
////			if (currentY > 0) {
////				adj[6] = currentNode - map.getContent().length;
////				if (currentX < map.getContent().length) {
////					adj[7] = currentNode - map.getContent().length + 1;
////				}
////				else {
////					adj[7] = currentNode;
////				}
////			}
////			else {
////				adj[6] = currentNode;
////				adj[7] = currentNode;
////			}
//
//			// Iterate over all adjacent nodes
//			for (int i = 0; i < 8; i++) {
//				// Get next adjacent node
//				adjacent = adj[i];
//				// If the node is invalid, continue to next iteration
//				if (adjacent == currentNode) { continue; }
//				// If adjacent node is diagonal - movement cost is 14, else 10
//				if (i == 1 || i == 3 || i == 5 || i == 7) { movementCost = 14; }
//				else { movementCost = 10; }
//
//				if ( map.nodeIsWalkable(adjacent) && !(closedList.contains(adjacent)) ) {
//					// If the adjacent node is on the openList already
//					if (openList.contains(adjacent)) {
//						// Calculate current gScore of the adjacent node
//						gScore = gScores.get(currentNode) + movementCost;
//						// If this gScore is less than the already recorded gScore, this current path is shorter
//						// from the start node to this adjacent node
//						if (gScore < gScores.get(adjacent)) {
//							// Record the gScore
//							gScores.put(adjacent, gScore);
//							// hScore is the same, so dont recalculate it, just use it and store the new fScore
//							fScore = gScore + hScores.get(adjacent);
//							fScores.put(adjacent, fScore);
//							// Remove and add the node (Re-Sorting the tree)
//							openList.remove(adjacent);
//							openList.add(adjacent);
//						}
//					}
//					// If the adjacent node hasnt been seen yet, calculate its g,h and f Scores and add it to the openList
//					else {
//						// Record current as its parent node
//						parents.put(adjacent, currentNode);
//						// Calc G score (distance from start)
//						gScore = gScores.get(currentNode) + movementCost;
//						gScores.put(adjacent, gScore);
//						// Calc H score (distance to target)
//						hScore = getHScore(adjacent, targetX, targetY);
//						hScores.put(adjacent, hScore);
//						// Calc F score
//						fScore = gScore + hScore;
//						fScores.put(adjacent, fScore);
//						// Add it to openList and it'll sort it in its fScore place
//						openList.add(adjacent);
//					}
//				}
//			}
//		}
//
//		if (!pathFound) {
//			return false;
//		}
//
//		currentNode = targetNode;
//		while (currentNode != startNode) {
//			currentNode = parents.get(currentNode);
//			pathContainer.add(currentNode);
//		}
//		return true;
//	}

	public void clearOpenList() {
		openListNumElements = 0;
		for (int i = 0; i < openList.length; i++) {
			openList[i] = 0;
		}
	}

	public void clearClosedList() {
		closedListNumElements = 0;
		for (int i = 0; i < closedList.length; i++) {
			closedList[i] = 0;
		}
	}

	public void clearParents() {
		parentListNumElements = 0;
		for (int i = 0; i < parents.length; i++) {
			parents[i][0] = 0;
			parents[i][1] = 0;
		}
	}

	public void clearGScores() {
		gScoresNumElements = 0;
		for (int i = 0; i < gScores.length; i++) {
			gScores[i][0] = 0;
			gScores[i][1] = 0;
		}
	}

	public void clearHScores() {
		hScoresNumElements = 0;
		for (int i = 0; i < hScores.length; i++) {
			hScores[i][0] = 0;
			hScores[i][1] = 0;
		}
	}

	public void clearFScores() {
		fScoresNumElements = 0;
		for (int i = 0; i < fScores.length; i++) {
			fScores[i][0] = 0;
			fScores[i][1] = 0;
		}
	}

	int startNode;
	int targetNode;

	int[] openList = new int[10000];
	int openListNumElements = 0;

	int[] closedList = new int[10000];
	int closedListNumElements = 0;

	int[][] gScores = new int[10000][2];
	int gScoresNumElements = 0;
	int[][] hScores = new int[10000][2];
	int hScoresNumElements = 0;
	int[][] fScores = new int[10000][2];
	int fScoresNumElements = 0;
	int[][] parents = new int[10000][2];
	int parentListNumElements = 0;

	// TODO add validation for IndexOutOfBounds error if some array gets filled up...
	public void addOpenList(int currentNode, int parentNode, int targetX, int targetY, int movementCost) {
		// Save parent node for current node at index openListNumElements (which points to the first free index in the array)
		parents[parentListNumElements][0] = currentNode;
		parents[parentListNumElements++][1] = parentNode;
		// Save movement cost for current node
		gScores[gScoresNumElements][0] = currentNode;
		gScores[gScoresNumElements][1] = movementCost;
		// Save heuristic distance to goal for current node
		hScores[hScoresNumElements][0] = currentNode;
		hScores[hScoresNumElements][1] = getHScore(currentNode, targetX, targetY );
		// Save total final distance score for the current node
		fScores[fScoresNumElements][0] = currentNode;
		fScores[fScoresNumElements][1] = gScores[gScoresNumElements][1] + hScores[hScoresNumElements][1];
		// Add it to the open list at the first free position and increment the free position to point to the next free index
		openList[openListNumElements] = currentNode;
		// Save last positions in these arrays
		gScoresNumElements++;
		fScoresNumElements++;
		hScoresNumElements++;
		openListNumElements++;
	}

	public boolean openListContains(int currentNode) {
		for (int i = 0; i < openListNumElements; i++) {
			if (openList[i] == currentNode) {
				return true;
			}
		}
		return false;
	}

	public void moveToClosedList(int currentNode) {
		for (int i = 0; i < openListNumElements; i++) {
			if (openList[i] == currentNode) {
				for (int j = i; j < openListNumElements; j++) {
					openList[j] = openList[j + 1];
				}
				//Gdx.app.log("PathFinding: ", "Node " + currentNode + " went into the closedList");
				openListNumElements--;
				closedList[closedListNumElements++] = currentNode;
				return;
			}
		}
	}

	public boolean closedListContains(int currentNode) {
		for (int i = 0; i < closedListNumElements; i++) {
			if (closedList[i] == currentNode) {
				return true;
			}
		}
		return false;
	}

	int lowestFIndex = 0;
	int lowestFValue = 0;

	public int findFIndexOfNode(int node) {
		for (int i = 0; i < fScoresNumElements; i++) {
			if (fScores[i][0] == node) {
				return i;
			}
		}
		return -1;
	}

	public int lowestFIndex() {
		lowestFIndex = 0;
		lowestFValue = fScores[findFIndexOfNode(openList[0])][1];
		//Gdx.app.log("PathFinding", "LowestFValue: " + lowestFValue + ", LowestFIndex: " + lowestFIndex);
		//Gdx.app.log("PathFinding ", "fScores: " + Arrays.toString(fScores[0]) + Arrays.toString(fScores[1]) + Arrays.toString(fScores[2]) + Arrays.toString(fScores[3]) + Arrays.toString(fScores[4]) + Arrays.toString(fScores[5]) + Arrays.toString(fScores[6]) + Arrays.toString(fScores[7]) + Arrays.toString(fScores[8]) + Arrays.toString(fScores[9]));
		//Gdx.app.log("LowestFIndex: ", "Index " + findFIndexOfNode(openList[0]));
		int index;
		for (int i = 0; i < openListNumElements; i++) {
			index = findFIndexOfNode(openList[i]);
			if (fScores[index][1] < lowestFValue) {
				lowestFValue = fScores[index][1];
				lowestFIndex = i;
			}
		}
		return lowestFIndex;
	}

	public int getGScore(int node) {
		for (int i = 0; i < gScoresNumElements; i++) {
			// If the current node in gScores is the target node
			if (gScores[i][0] == node) {
				// Return the gScore (movement cost) of that node
				return gScores[i][1];
			}
		}
		return -1;
	}

	public void changeGScore(int node, int movementCost) {
		for (int i = 0; i < gScoresNumElements; i++) {
			// If the current node in gScores is the target node
			if (gScores[i][0] == node) {
				// Return the gScore (movement cost) of that node
				gScores[i][1] = movementCost;
				return;
			}
		}
	}

	public void recalculateFScore(int node) {
		for (int i = 0; i < openListNumElements; i++) {
			// If the current node in gScores is the target node
			if (fScores[i][0] == node) {
				// Return the gScore (movement cost) of that node
				fScores[i][1] = gScores[i][1] + hScores[i][1];
				return;
			}
		}
	}

	public void changeParent(int targetNode, int parentNode) {
		for (int i = 0; i < parentListNumElements; i++) {
			if (parents[i][0] == targetNode) {
				parents[i][1] = parentNode;
				return;
			}
		}
	}

	public int getParent(int targetNode) {
		for (int i = 0; i < parentListNumElements; i++) {
			if (parents[i][0] == targetNode) {
				return parents[i][1];
			}
		}
		return -1;
	}

	int tmpIndex;	// Temp holder for adjacent index when checking diagonal tile for neighbouring walls, etc...
	public boolean findPath(int startX, int startY, int targetX, int targetY, int[] pathContainer) {
		//Gdx.app.log("PathFinding Starting ", "Starting... Start XY: " + startX + "," + startY + " - Target XY: " + targetX + "," + targetY);
		// Clean up the arrays
		clearOpenList();
		clearClosedList();

		clearParents();

		clearGScores();
		clearHScores();
		clearFScores();

		startNode = startY * mapContent.length + startX;
		currentNode = startNode;
		addOpenList(currentNode, currentNode, targetX, targetY, 0);

		targetNode = targetY * mapContent.length + targetX;
		if (!map.nodeIsWalkable(targetNode)) {
			//Gdx.app.log("PathFinding ", "Unreachable node chosen...");
			return false;
		}

		while (true) {
			currentNode = openList[lowestFIndex()];
			// If there are no nodes on the openList, lowestFIndex will return -1
			if (currentNode == -1) {
				//Gdx.app.log("Path: ", "Error, no path exists");
				return false;
			}
			//Gdx.app.log("PathFinding ", "Stepping on : " + currentNode);
			moveToClosedList(currentNode);

			//Gdx.app.log("PathFinding ", "ClosedList: " + Arrays.toString(closedList));

			// Calculate current node's XY coordinates
			currentX = currentNode % mapContent.length;
			currentY = currentNode / mapContent.length;

			if (currentX == targetX && currentY == targetY) {
				//Gdx.app.log("PathFinding ", "!!!Target found!!!");
				//StringBuilder sb = new StringBuilder();
				tmpIndex = 0;
				while (currentNode != startNode) {
					pathContainer[tmpIndex++] = currentNode;
					//sb.append(currentNode + ",");
					currentNode = getParent(currentNode);
				}
				pathContainer[tmpIndex] = startNode;
				//Gdx.app.log("PathFinding path: ", sb.toString());
				return true;
			}

			// Set all 8 adjacent nodes
			adj[0] = currentNode + 1;
			adj[1] = currentNode + mapContent.length + 1;
			adj[2] = currentNode + mapContent.length;
			adj[3] = currentNode + mapContent.length - 1;
			adj[4] = currentNode - 1;
			adj[5] = currentNode - mapContent.length - 1;
			adj[6] = currentNode - mapContent.length;
			adj[7] = currentNode - mapContent.length + 1;

			// Reset invalid nodes
			if (currentX == mapContent.length - 1) {
				adj[0] = currentNode;
				adj[1] = currentNode;
				adj[7] = currentNode;
			}
			if (currentX == 0) {
				adj[3] = currentNode;
				adj[4] = currentNode;
				adj[5] = currentNode;
			}
			if (currentY == mapContent.length - 1) {
				adj[1] = currentNode;
				adj[2] = currentNode;
				adj[3] = currentNode;
			}
			if (currentY == 0) {
				adj[5] = currentNode;
				adj[6] = currentNode;
				adj[7] = currentNode;
			}

			for (int i = 0; i < 8; i++) {
				// Get next adjacent node
				adjacent = adj[i];
				// If the node is invalid, continue to next iteration
				if (adjacent == currentNode) { continue; }

				// Before checking anything else, make sure we can step on that square...
				if (!map.nodeIsWalkable(adjacent) || closedListContains(adjacent)) {
					continue;
				}

				// If adjacent node is diagonal - movement cost is 14, else 10
				if (i == 1 || i == 3 || i == 5 || i == 7) {
					tmpIndex = i + 1;
					if (tmpIndex == 8) { tmpIndex = 0; }	// Check if we're IndexOutOfBounds and reset...
					// Check if there is a gap we can actually pass through...
					if (!map.nodeIsWalkable(adj[tmpIndex]) && !map.nodeIsWalkable(adj[i - 1])) {
						continue;
					}
					movementCost = 14;
				}
				else {
					// Node is orthogonal, movement cost is 10
					movementCost = 10;
				}

				// If the node is already on the open list
				if (openListContains(adjacent)) {
					gScore = movementCost + getGScore(currentNode);
					// Check if current gScore is better than the recorded gScore (dist from starting point)
					if (gScore < getGScore(adjacent)) {
						changeParent(adjacent, currentNode);
						changeGScore(adjacent, gScore);
						recalculateFScore(adjacent);
					}
				}
				else {
					// If its a new node, add it for checking...
					gScore = movementCost + getGScore(currentNode);
					addOpenList(adjacent, currentNode, targetX, targetY, gScore);
				}
			}
//			try {
//				Thread.sleep(100);
//			}
//			catch (InterruptedException e) {
//				Gdx.app.log("PathFinding ", "Interrupted Exception: " + e.getMessage());
//			}
			//Gdx.app.log("PathFinding ", "OpenList: " + Arrays.toString(openList));
		}
	}

	public static PathFinder getPathFinder(TileMap map) {
		if (pathFinder == null) {
			pathFinder = new PathFinder(map);
		}
		return pathFinder;
	}
}
