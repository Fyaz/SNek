package ai;

public class AIOutput {
	
	// Standard modes for output. 
	// Used to describe the vertical and horizontal movements
	// Haven't supported diagonal movement. 
	public static final byte UP = 0;
	public static final byte DOWN = 1;
	public static final byte LEFT = 2;
	public static final byte RIGHT = 3;
	
	// The outputs themselves 
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	/** Instantiate a new AIOutput based around one
	 * of the already defined modes: UP, DOWN, LEFT, RIGHT. */
	public AIOutput(int type) {
		setOutput(type);
	}
	
	/** Set the conditions for the output yourself. */
	public AIOutput(boolean _up, boolean _down, boolean _left, boolean _right) {
		up = _up;
		down = _down;
		left = _left;
		right = _right;
	}
	
	/** /** There are basic default types of outputs used by the snake game. 
	 * They're defined as UP, DOWN, LEFT, RIGHT. If the current output matches
	 * any of these nodes then it'll return that mode int. 
	 * @param mode - 0 will configure the output to the up direction. 
	 * 1 will configure the output to the down direction. 
	 * 2 will configure the output to the left direction. 
	 * 3 will configure the output to the right direction. 
	 * Anything else won't affect the output at all. */
	public void setOutput(int mode) {
		switch(mode) {
		case UP:
			up = true;
			down = false;
			left = false;
			right = false;
			break;
		case DOWN:
			up = false;
			down = true;
			left = false;
			right = false;
			break;
		case LEFT:
			up = false;
			down = false;
			left = true;
			right = false;
			break;
		case RIGHT:
			up = false;
			down = false;
			left = false;
			right = true;
			break;
		}
	}
	
	/** There are basic default types of outputs used by the snake game. 
	 * They're defined as UP, DOWN, LEFT, RIGHT. If the current output matches
	 * any of these nodes then it'll return that mode int. 
	 * @return 0 if UP, 1 if DOWN, 2, if LEFT, 3 if RIGHT, else -1 for no match. */
	public int generateMode() {
		if(up && !down && !left && !right)
			return UP;
		else if(!up && down && !left && !right)
			return DOWN;
		else if(!up && !down && left && !right)
			return LEFT;
		else if(!up && !down && !left && right)
			return RIGHT;
		else return -1;
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
	
	@Override
	public String toString() {
		return "[up = " + up + ", down = " + down + ", left = " + left + ", right = " + right + "]";
	}

}
