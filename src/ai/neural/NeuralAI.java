package ai.neural;

import ai.AI;
import ai.AIOutput;
import ai.neural.Node;

public class NeuralAI implements AI {

	// numerical values that are being tracked. Arbitrary
	private final int LOSS = -100;
	private final int EAT = 10000;
	private final int WIN = 50000;
	
	// There are 4 possible outputs: move up, down, left, right
	// If the decreases netFeel, try random things (4 possible outputs)
	// If netFeel == 0, try something different (turn left or right) 
	// If netFeel > 0, continue doing things (maintain current output; go straight)
	private long netFeel;
	private boolean makeNewNode; // a flag to dictate whether to add a new node or not. 
	
	// the start of the root. Reset to this point every game instance. 
	private final Node root;
	
	// keeps track of which node we're in while traversing it (like a linkedlist)
	private Node current;
	
	// The current output of the AI. 
	private AIOutput output;
	
	public NeuralAI() {
		netFeel = 0;
		output = new AIOutput(false, true, false, false);
		root = new Node(0, output);
		current = root;
	}
	
	public NeuralAI(int startFeel) {
		netFeel = startFeel;
		output = new AIOutput(false, true, false, false);
		root = new Node(0, output);
		current = root;
	}
	
	public NeuralAI(int startFeel, boolean up, boolean down, boolean left, boolean right) {
		netFeel = startFeel;
		output = new AIOutput(up, down, left, right);
		root = new Node(0, output);
		current = root;
	}
	
	// Editting specific values of the AI
	public void reset() { current = root; }
	public long getFeel() { return netFeel; }
	public void setFeel(long l) { netFeel = l; } 
	public int getLossFeel() { return LOSS; }
	public int getWinFeel() { return WIN; }
	public int getEatFeel() { return EAT; }
	
	/* TODO: Confirm that the ai algo termintates. 
	 * Psuedocode:
	 * 	for(every game state)
	 * 	  decrement netFeel (approach 0)
	 * 	  if a connected Node matches current output:
	 * 		if the connected Node has negative feel: 
	 * 		  try something different. 
	 * 		else if the connectedNode is neutral || good: 
	 * 		  continue current output. 
	 * 	  else if no connectedNode exists with the current output:
	 * 		if netFeel is bad:
	 * 			try something random
	 * 	  	else if netFeel is neutral: 
	 * 			try something different:
	 * 		else if netFeel is good:
	 *			do the same thing: 
	 * 		Mark down the results in a new node. */
	public void updateAI() {
		makeNewNode = false;
		netFeel -= (double)netFeel/100; // decrement netFeel (he goes back to normal after 5 iterations of nothing)
		if(current.contains(current)) { // if a connected Node matches current output: 
			current = current.nextNode(current);
			netFeel += current.getFeel();
			if(current.getFeel() < 0) { //TODO: This here is bad. Find a way to remove it. If a connectedNode has negative feel: 
				doSomethingDifferent();
			}
			current.setFeel(current.getFeel() - current.getFeel()/100);
		}
		else {
			if(netFeel < 0)
				doSomethingRandom();
			else if(netFeel == 0)
				doSomethingDifferent();
			addNewNode(netFeel);
		}
	}
	
	/** Add a new node to the current Node with the _feel.  */
	public void addNewNode(long l) {
		if(makeNewNode) {
			Node newNode = new Node(l, current.getOutput().up, 
									   current.getOutput().down, 
									   current.getOutput().left, 
									   current.getOutput().right);
			current.addNode(newNode);
			current = current.nextNode(newNode);
		}
		reset();
	}
	
	/** Randomly calculate a new decision that isn't the same as the current decision. */
	private void doSomethingDifferent() {
		int currentoutput;
		if(output.up)
			currentoutput = 0;
		else if(output.down)
			currentoutput = 1;
		else if(output.left)
			currentoutput = 2;
		else 
			currentoutput = 3;
		int newoutput = (int)(Math.random()*4);
		while(newoutput == currentoutput)
			newoutput = (int)(Math.random()*4);
		output.up = false;
		output.down = false;
		output.left = false;
		output.right = false;
		switch(newoutput) {
			case 0: output.up = true; break;
			case 1: output.down = true; break;
			case 2: output.left = true; break;
			case 3: output.right = true; break;	// Set the output to something random. 
		}
	}
	
	/** Calculate a random decision. This includes the current decision, so the AI may
	 * decide to do the same thing it has been doing. */
	private void doSomethingRandom() {
		int newoutput = (int)(Math.random()*4);
		output.up = false;
		output.down = false;
		output.left = false;
		output.right = false;
		switch(newoutput) {
			case 0: output.up = true; break;
			case 1: output.down = true; break;
			case 2: output.left = true; break;
			case 3: output.right = true; break;	// Set the output to something random. 
		}
	}

	@Override
	public AIOutput getOutput() {
		updateAI();
		return output;
	}
	
}
