package ai;

/** An AI decides outputs based on specified inputs. 
 * Inputs: 
 * 	- Location of the food. <Point>
 * 	- Location of the head of the snake. <Point> 
 * 	- The direction the snake is currently facing. <byte dX, dY>
 *  - Whether there is a section of the body in front of the snake. <boolean>
 *  */
public class SnakeAI {
	
	// There are 4 possible outputs: move up, down, left, right
	// If the decreases netFeel, try random things (4 possible outputs)
	// If netFeel == 0, try something different (turn left or right) 
	// If netFeel > 0, continue doing things (maintain current output; go straight)
	private long netFeel;
	private boolean makeNewNode; // a flag to dictate whether to add a new node or not. 
	private boolean AIOn; // Decide whether the ai should be on or not. 
	
	// Current outputs of the snake
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	// the start of the root. Reset to this point every game instance. 
	private final Node root;
	
	// keeps track of which node we're in while traversing it (like a linkedlist)
	private Node current;
	
	public SnakeAI() {
		AIOn = false;
		netFeel = 0;
		up = false;
		down = true;
		left = false;
		right = false;
		root = new Node(0, up, down, left, right);
		current = root;
	}
	
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
	 * 		Mark down the results in a new node.
	 * 		  */
	public void updateAI() {
		if(AIOn) {
			makeNewNode = false;
			netFeel -= (double)netFeel/100; // decrement netFeel (he goes back to normal after 5 iterations of nothing)
			if(current.contains(current)) { // if a connected Node matches current output: 
				current = current.nextNode(current);
				netFeel += current.getFeel();
				if(current.getFeel() < 0) { // if a connectedNode has negative feel: 
					doSomethingDifferent();
				}
				current.setFeel(current.getFeel() - current.getFeel()/100);
			}
			else {
				if(netFeel < 0)
					doSomethingRandom();
				else if(netFeel == 0)
					doSomethingDifferent();
				makeNewNode = true;
			}
		}
	}
	
	// Add a new node to the current Node with the _feel. 
	public void addNewNode(long l) {
		if(AIOn) {
			if(makeNewNode) {
				Node newNode = new Node(l, up, down, left, right);
				current.addNode(newNode);
				current = current.nextNode(newNode);
			}
		}
	}
	
	public void reset() { current = root; }
	public long getFeel() { return netFeel; }
	public void setFeel(long l) { netFeel = l; } 
	public boolean up() { return up; }
	public boolean down() { return down; }
	public boolean left() { return left; }
	public boolean right() { return right; }
	
	public void turnOn() { AIOn = true; }
	public void turnOff() { AIOn = false; }
	public boolean isOn() { return AIOn; }
	
	private void doSomethingDifferent() {
		int currentoutput;
		NodeOutput output = current.getOutput();
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
		up = false;
		down = false;
		left = false;
		right = false;
		switch(newoutput) {
			case 0: up = true; break;
			case 1: down = true; break;
			case 2: left = true; break;
			case 3: right = true; break;	// Set the output to something random. 
		}
	}
	
	private void doSomethingRandom() {
		int newoutput = (int)(Math.random()*4);
		up = false;
		down = false;
		left = false;
		right = false;
		switch(newoutput) {
			case 0: up = true; break;
			case 1: down = true; break;
			case 2: left = true; break;
			case 3: right = true; break;	// Set the output to something random. 
		}
	}
	
}
