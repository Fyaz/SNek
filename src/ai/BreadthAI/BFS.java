package ai.BreadthAI;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import ai.AI;
import ai.AIInput;
import ai.AIOutput;
import game.Point;
import game.Snek;

public class BFS implements AI {
	
	// Different inputs used by BFS
	private Point food_loc;	// The location of the Food (destination)
	private int gL;	// The grid length [0, gL-1]
	private int gH;	// The grid height [0, gH-1]
	private Snek snake;	// The points that make up the snake's body. 
	
	// The resulting output of the BFS
	private AIOutput currentOutput;
	private Deque<AIOutput> nextOutputs;
	
	public BFS() {
		food_loc = new Point(0,0);
		gL = 0;
		gH = 0;
		currentOutput = new AIOutput(false, false, false, false);
		nextOutputs = new LinkedList<AIOutput>();
	}
	
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
	 * currentOutput = nextOutputs.dequeue(); */
	private void BreadthFirstSearch() {
		Queue<Point> stack = new LinkedList<Point>();
		Map<Point, Point> parentMap = new HashMap<Point, Point>();
		
		// Find a path from head to destination 
		stack.add(snake.head());
		while(!stack.isEmpty()) {
			Point s = stack.remove();
			Point temp = new Point(s.getX(), s.getY());
			// Check all the neighbors of curr
			temp.setX(s.getX()-1);
			if(temp.getX() > -1 && temp.getX() < gL && !snake.hasPoint(temp)) { // Check left
				stack.add(temp);
				parentMap.put(temp, s);
			}
			temp.setX(s.getX()+1);
			if(temp.getX() > -1 && temp.getX() < gL && !snake.hasPoint(temp)) { // Check right
				stack.add(temp);
				parentMap.put(temp, s);
			}
			temp.setX(s.getX());
			temp.setY(s.getY()-1);
			if(temp.getY() > -1 && temp.getY() < gH && !snake.hasPoint(temp)) { // Check up
				stack.add(temp);
				parentMap.put(temp, s);
			}
			temp.setY(s.getY()+1);
			if(temp.getY() > -1 && temp.getY() < gH && !snake.hasPoint(temp)) { // Check down
				stack.add(temp);
				parentMap.put(temp, s);
			}
			if(stack.contains(food_loc))
				break;
		}
		
		//Backtrack the parent Map to generate the path 
		Point p = food_loc;
		while(!p.equals(snake.head())) {
			nextOutputs.addLast(new AIOutput(generateOutputMode(p, parentMap.get(p))));
			p = parentMap.get(p);
		}
		currentOutput = nextOutputs.remove();
	}

	/** This code generates a list of outputs based on a 
	 * path from now to the location of the food.
	 * If the list of outputs is still not complete, then output the next output
	 * else generate a new list. */
	@Override
	public AIOutput getOutput() {
		if(!nextOutputs.isEmpty()) {
			currentOutput = nextOutputs.poll();
			return currentOutput;
		}
		BreadthFirstSearch();
		return currentOutput;
	}

	@Override
	public void parseInput(AIInput _input) {
		try {
			if((_input.get(0) instanceof Point))
				food_loc = (Point)_input.get(0);
			else
				throw new IllegalArgumentException();
			if((_input.get(1) instanceof Integer))
				gL = (int)_input.get(1);
			else
				throw new IllegalArgumentException();
			if((_input.get(2) instanceof Integer))
				gH = (int)_input.get(2);
			else
				throw new IllegalArgumentException();
			if((_input.get(3) instanceof Snek))
				snake = (Snek)_input.get(3);
			else
				throw new IllegalArgumentException();
			
		}
		catch(IllegalArgumentException e) {
			System.err.println("BFS recieved an invalid input.");
		}
	}
	
	/** Assuming Point one and Point two are adjacent points. 
	 * Generate a mode for AIOutput for for one -> two. 
	 * Example: if one = (0,0) && two = (0,1) 
	 * 			because you have to go UP to get from one to two, 
	 * 			the method returns 0 (the mode for go UP). */
	private int generateOutputMode(Point one, Point two) {
		int[] diff = {one.getX() - two.getX(), one.getY() - two.getY()};
		if(diff[1] == -1)
			return AIOutput.UP;
		else if(diff[1] == 1)
			return AIOutput.DOWN;
		else if(diff[0] == 1)
			return AIOutput.LEFT;
		else if(diff[0] == -1)
			return AIOutput.RIGHT;
		else return -1;
	}

}
