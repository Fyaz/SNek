package ai.neural;

import java.util.ArrayList;

import ai.AIOutput;

/** A node used in the Snake AI. */
public class Node {

	// TODO: replace feel with input. 
	private NodeInput input;
	private long feel;	// Whenever a decision was made, a node is created keeping track of the 
	private final AIOutput output;
	private ArrayList<Node> connectedNodes;
	
	public Node(long l, AIOutput _output) {
		feel = l;
		output = _output;
		connectedNodes = new ArrayList<Node>();
	}
	
	public Node(long l, boolean _up, boolean _down, boolean _left, boolean _right) {
		feel = l;
		output = new AIOutput(_up, _down, _left, _right);
		connectedNodes = new ArrayList<Node>();
	}
	
	public boolean contains(Node other) {
		return connectedNodes.contains(other);
	}
	
	public Node nextNode(Node other) {
		for(int i = 0; i < connectedNodes.size(); i++)
			if(connectedNodes.get(i).output.equals(other.output))
				return connectedNodes.get(i);
		return new Node(0, false, false, false, false);
	}
	
	public void addNode(Node other) {
		connectedNodes.add(other);
	}
	
	public long getFeel() {
		return feel;
	}
	
	public void setFeel(long l) {
		feel = l;
	}
	
	public AIOutput getOutput() {
		return output;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Node))
			return false;
		Node other = (Node)o;
		return output.equals(other.output);	// If they have the matching outputs
	}
	
}
