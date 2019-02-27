package ai;

import java.util.ArrayList;

import ai.BreadthAI.BFS;
import ai.neural.NeuralAI;

/** An AI decides outputs based on specified inputs. 
 * Inputs: 
 * 	- Location of the food. <Point>
 * 	- Location of the head of the snake. <Point> 
 * 	- The direction the snake is currently facing. <byte dX, dY>
 *  - Whether there is a section of the body in front of the snake. <boolean>
 *  */
public class SnakeAI {
	
	// Decide whether the ai should be on or not.
	private boolean AIOn;
	
	// Different Modes for the AI
	private int mode = 0;
	public static final int NEURAL_AI = 0;
	public static final int BFS = 1;
	
	// Different types of AI for the snake 
	private ArrayList<AI> brain;
	
	// Current outputs of the snake
	// co stands for "current output"
	private AIOutput co;
	
	public SnakeAI() {
		co = new AIOutput(false, true, false, false);
		brain = new ArrayList<AI>();
		brain.add(new NeuralAI(0, co.up, co.down, co.left, co.right));
		brain.add(new BFS());
	}
	
	// Getters and Setters ==================================================================
	
	// Get output AI decisions 
	public boolean up() { return co.up; }
	public boolean down() { return co.down; }
	public boolean left() { return co.left; }
	public boolean right() { return co.right; }
	
	// Control the behavior of the AI 
	public void turnOn() { AIOn = true; }
	public void turnOff() { AIOn = false; }
	public boolean isOn() { return AIOn; }
	
	/** There are different static variables provided for 
	switching the modes for the AI. */
	public void setAI(int _mode) {
		mode = _mode;
	}
	
	// Public Methods =======================================================================
	
	/** A method to call whatever AI is currently generate a new output. 
	 * The new output will be stored in co. call up(), down(), left(), right() to 
	 * get the results of the output. */
	public void update() {
		co = brain.get(mode).getOutput();
	}
	
}
