package ai.BreadthAI;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import ai.AI;
import ai.AIInput;
import ai.AIOutput;
import game.Point;
import game.Snek;

public class BFS implements AI {
	
	// Variables ============================================================================
	
	// Different inputs used by BFS
	private Point food_loc;	// The location of the Food (destination)
	private int gL;	// The grid length [0, gL-1]
	private int gH;	// The grid height [0, gH-1]
	private Snek snake;	// The points that make up the snake's body. 
	
	// The resulting output of the BFS
	private AIOutput currentOutput;
	private Stack<AIOutput> outputStack;
	
	// Constructor ==========================================================================
	
	public BFS() {
		food_loc = new Point(0,0);
		gL = 0;
		gH = 0;
		currentOutput = new AIOutput(false, false, false, false);
		outputStack = new Stack<AIOutput>();
	}
	
	// Public Methods =======================================================================
	
	/** Implemented from the AI interface.
	 * This code generates a list of outputs based on a 
	 * path from now to the location of the food.
	 * If the list of outputs is still not complete, then output the next output
	 * else generate a new list. */
	@Override
	public AIOutput getOutput() {
		BreadthFirstSearch();
		return currentOutput;
	}

	/** Implemented from the AI interface. */
	@Override
	public void parseInput(AIInput _input) {
		try {
			if((_input.get(0) instanceof Point))
				food_loc = (Point)_input.get(0);
			else
				throw new IllegalArgumentException();
			if((_input.get(1) instanceof Integer))
				gL = (Integer)_input.get(1);
			else
				throw new IllegalArgumentException();
			if((_input.get(2) instanceof Integer))
				gH = (Integer)_input.get(2);
			else
				throw new IllegalArgumentException();
			if((_input.get(3) instanceof Snek))
				snake = (Snek)_input.get(3);
			else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException e) {
			System.err.println("BFS recieved an invalid input.");
			System.exit(-1);
		}
	}
	
	// Private Methods ======================================================================
	
	/** BFS Pseudo-code
 	 * Let S be a stack
	 * Let P be a map of parents to children 
	 * Push the head to the stack.
	 * while the stack is not empty:	// Find a path
	 * 	 s = stack.pop();
	 *   for every neighbor of s that does not collide with the snake nor edges of the grid:
	 *   	add the neighbor to the stack. 
	 *   	add the pair (neighbor -> s) to P. 
	 *   	if neighbor is the destination:
	 *   		break;
	 *   if the destination is in the stack:
	 *   	break;
	 * end loop 
	 * Push the destination to nextOutputs 	// Back track the parent's list to generate the path. 
	 * p = the parent of the destination. 
	 * while p is not the head
	 * 	 push the direction to nextOutputs
	 * 	 p = the parent of p
	 * end loop
	 * currentOutput = nextOutputs.pop(); */
	private void BreadthFirstSearch() {
		Queue<Point> queue = new LinkedList<Point>();
		Map<Point, Point> parentMap = new HashMap<Point, Point>();
		
		// Find a path from head to destination 
		queue.add(snake.head());
		parentMap.put(snake.head(), null);
		while(!queue.isEmpty()) {
			Point s = queue.poll();
			// Check all the neighbors of s
			Point neighbor = new Point(s.getX()-1, s.getY()); // Check left
			if(inGrid(neighbor) && !snake.hasPoint(neighbor) && !parentMap.containsKey(neighbor)) { 
				queue.add(new Point(neighbor));
				parentMap.put(new Point(neighbor), s);
			}
			neighbor.setX(s.getX()+1); // Check right
			if(inGrid(neighbor) && !snake.hasPoint(neighbor) && !parentMap.containsKey(neighbor)) { 
				queue.add(new Point(neighbor));
				parentMap.put(new Point(neighbor), s);
			}
			neighbor.setX(s.getX());
			neighbor.setY(s.getY()-1); // Check up
			if(inGrid(neighbor) && !snake.hasPoint(neighbor) && !parentMap.containsKey(neighbor)) { 
				queue.add(new Point(neighbor));
				parentMap.put(new Point(neighbor), s);
			}
			neighbor.setY(s.getY()+1); // Check down
			if(inGrid(neighbor) && !snake.hasPoint(neighbor) && !parentMap.containsKey(neighbor)) { 
				queue.add(new Point(neighbor));
				parentMap.put(new Point(neighbor), s);
			}
			if(s.equals(food_loc))
				break;
		}
		
		// Using the implicit BFS tree, to find the next next towards getting to the food. 
		int mode;
		Point p = food_loc;
		while(parentMap.get(p) != null && !snake.head().equals(parentMap.get(p))) {
			mode = generateOutputMode(parentMap.get(p), p);
			outputStack.push(new AIOutput(mode));
			p = parentMap.get(p);
		}
		mode = generateOutputMode(parentMap.get(p), p);
		outputStack.push(new AIOutput(mode));
		currentOutput = outputStack.pop();
		
		// TODO: If a path to the food is not found, have the next output move towards safe positions.
	}
	
	/** Check if Point p is in the grid that's been seen by BFS. */
	private boolean inGrid(Point p) {
		return (p.getX() > -1 && p.getY() > -1 && p.getX() < gL && p.getY() < gH);
	}
	
	/** Assuming Point one and Point two are adjacent points. 
	 * Generate a mode for AIOutput for for one -> two. 
	 * Example: if one = (0,0) && two = (0,1) 
	 * 			because you have to go UP to get from one to two, 
	 * 			the method returns 0 (the mode for go UP). */
	private int generateOutputMode(Point one, Point two) {
		if(one != null && two != null) {
			int[] diff = {one.getX() - two.getX(), one.getY() - two.getY()};
			if(diff[0] == 0 && diff[1] == 1)
				return AIOutput.UP;
			else if(diff[0] == 0 && diff[1] == -1)
				return AIOutput.DOWN;
			else if(diff[0] == 1 && diff[1] == 0)
				return AIOutput.LEFT;
			else if(diff[0] == -1 && diff[1] == 0)
				return AIOutput.RIGHT;
			else {
				System.out.println("generateOutputMode:" + one + " and  + " + two + " not adjacent");
			}
		}
		return -1;
	}
}
