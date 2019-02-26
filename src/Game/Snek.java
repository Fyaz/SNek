package game;

public class Snek {
	
	// Values defining the body itself
	private Point[] body;	// A list of points for the body of the snek
	private int length; 	// the current length of the snake's body
	
	// Movement Values
	private final byte SPEED = 1;
	private int dX = 0;
	private int dY = 0;
	
	public Snek(int max_length) {
		body = new Point[max_length];
		length = 0;
	}
	
	// Access methods =========================================================
	
	/** Return the point at the body part, body_part. 
	 * Think about it like accessing an array. 
	 * Input: an integer for the location between 0 and length of the snake. 
	 * Output: the Point at body_part.  */
	public Point getPointAt(int body_part) {
		if(body_part > body.length || body_part < 0) {
			System.err.println("Failed to return the location of body part at " + body_part + ". Returned (-1, -1).");
			return new Point();
		}
		else
			return body[body_part];
	}
	
	/** Return the Point that is currently the head of the snake.
	 * Precondition: The snake has been initialized and has a length > 0. */
	public Point head() { return body[0]; }
	
	/** Returns the current length of the snake. */
	public int length() { return length; }
	
	/** Returns the maximum length of the snake. */
	public int getMaxLength() { return body.length; }
	
	/** Returns true if Point _point is within the body of the snake. 
	 * Else returns false. */
	public boolean hasPoint(Point _point) {
		for(int i = 0; i < length; i++)
			if(body[i].equals(_point))
				return true;
		return false;
	}
	
	public int getSpeed() { return SPEED; }
	public int getdX() { return dX; }
	public int getdY() { return dY; }
	public void setdX(int _dx) { dX = _dx; }
	public void setdY(int _dy) { dY = _dy; }
	
	// Modifier Methods =========================================================
	
	/** Make the snake longer. Update the head of the snake 
	 * with newHead, and make the rest of the body longer. */
	public void addToBody(Point newHead) {
		length++;
		if(length != body.length)
			UpdateSnek(newHead);
	}
	
	/** Move the snake by giving it a newHead. */
	public void UpdateSnek(Point newHead) {
		for(int i = length-1; i > 0; i--)
			body[i] = body[i-1];
		body[0] = newHead;
	}
	
	/** Resets the length of the snake.
	 * You can still access previous points on the snake, but 
	 * None of the Snek methods will work for any points outside of the length(). */
	public void clear() {
		length = 0;
	}
}
