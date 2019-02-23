package ai;

public class NodeOutput {
	
	public final boolean up;
	public final boolean down;
	public final boolean left;
	public final boolean right;
	
	public NodeOutput(boolean _up, boolean _down, boolean _left, boolean _right) {
		up = _up;
		down = _down;
		left = _left;
		right = _right;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof NodeOutput))
			return false;
		NodeOutput other = (NodeOutput)o;
		return (up == other.up 
			 && down == other.down 
			 && left == other.left 
			 && right == other.right);
	}

}
