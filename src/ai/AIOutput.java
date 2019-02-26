package ai;

public class AIOutput {
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	public AIOutput(boolean _up, boolean _down, boolean _left, boolean _right) {
		up = _up;
		down = _down;
		left = _left;
		right = _right;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof AIOutput))
			return false;
		AIOutput other = (AIOutput)o;
		return (up == other.up 
			 && down == other.down 
			 && left == other.left 
			 && right == other.right);
	}

}
