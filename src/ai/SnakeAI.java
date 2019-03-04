package ai;

import ai.BreadthAI.BFS;
import ai.custom.NeuralAI;

/** A General class for handling the inputs and outputs of a snake.
 * Designed AI's will have specify what their inputs are using AI's parseInput,
 * and output specific outputs (defined in AIOutput). */
public class SnakeAI {
	
	// Decide whether the AI should be on or not.
	private boolean AIOn;
	
	// Different Modes for the AI
	private int mode = 0;
	public static final byte NEURAL_AI = 0;
	public static final byte BFS = 1;
	
	// Different types of AI for the snake 
	private AI brain;
	
	// Use this to collect the different inputs used for the AI 
	private AIInput inputs;
	
	// Current outputs of the snake
	// co stands for "current output"
	private AIOutput co;
	
	/** Basic Constructor. */
	public SnakeAI() {
		inputs = new AIInput();
		co = new AIOutput(false, true, false, false);
		brain = new NeuralAI(0, co.up, co.down, co.left, co.right);
	}
	
	// Getters and Setters ==================================================================
	
	// Get output AI decisions 
	public boolean up() { return (co.generateMode() == AIOutput.UP); }
	public boolean down() { return (co.generateMode() == AIOutput.DOWN); }
	public boolean left() { return (co.generateMode() == AIOutput.LEFT); }
	public boolean right() { return (co.generateMode() == AIOutput.RIGHT); }
	
	// Control the behavior of the AI 
	public void turnOn() { AIOn = true; }
	public void turnOff() { AIOn = false; }
	public boolean isOn() { return AIOn; }
	
	/** There are different static variables provided for 
	switching the modes for the AI. */
	public void setAI(int _mode) { 
		mode = _mode; 
		if(mode == NEURAL_AI)
			brain = new NeuralAI(0, co.up, co.down, co.left, co.right);
		else if(mode == BFS)
			brain = new BFS();
	}
	
	/** Collect which AI is currently being run. */
	public int mode() { return mode; }
	
	/** Reset the inputs */
	public void resetInputs() { inputs.clear(); }
	
	/** Add an input to later be used by the AI when calling update(). */
	public void addInput(Object o) { inputs.add(o); }
	
	// Public Methods =======================================================================
	
	/** A method to call whatever AI is currently generate a new output. 
	 * The new output will be stored in co. call up(), down(), left(), right() to 
	 * get the results of the output. */
	public void update() {
		brain.parseInput(inputs);
		co = brain.getOutput();
	}
	
}
