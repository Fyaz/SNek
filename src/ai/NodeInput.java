package ai;

public class NodeInput {

	// input neighbors
	private final boolean ahead;
	private final boolean left;
	private final boolean right;
	// current output
	private final int mimic;
	// feel 
	private int previousemotion;
	
	public NodeInput(boolean _ahead, boolean _left, boolean _right, int _mimic, int _previousemotion) {
		ahead = _ahead;
		left = _left;
		right = _right;
		mimic = _mimic;
		previousemotion = _previousemotion;
		
	}
	
}
