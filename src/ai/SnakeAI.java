package ai;

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
	
	// The brain for the snake 
	private final AI brain;	// TODO: A custom AI I'm working on. Not working. 
	private final AI BFS;	// Using BFS to calculate the best path to the food. 
	
	// Current outputs of the snake
	// co stands for "current output"
	private AIOutput co;
	
	public SnakeAI() {
		co = new AIOutput(false, true, false, false);
		brain = new NeuralAI(0, co.up, co.down, co.left, co.right);
		BFS = new NeuralAI(0, co.up, co.down, co.left, co.right);
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
	
	// Public Methods =======================================================================
	
	/** A method to call whatever AI is currently generate a new output. 
	 * The new output will be stored in co. call up(), down(), left(), right() to 
	 * get the results of the output. */
	public void update() {
		co = brain.getOutput();
	}
	
}
