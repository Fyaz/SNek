package ai;

import java.util.HashSet;
import java.util.Set;

/** a node used in the Snake AI. */
public class Node {

	private int input;
	private int output; // Generate an output based on the input.
	private Set<Node> connectedNodes = new HashSet<Node>();
	
	public Node(int _input) {
	}
	
	public boolean addNewNode(int _input) {
		Node newNode = new Node(_input);
		if(connectedNodes.contains(newNode))
			return false;
		connectedNodes.add(newNode);
		return true;
	}
	
}
