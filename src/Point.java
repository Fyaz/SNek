
public class Point {

	private int X;
	private int Y;
	
	public Point() {
		X = -1;
		Y = -1;
	}
	
	public Point(int x, int y) {
		X = x;
		Y = y;
	}
	
	public int getX() { return X; }
	public int getY() { return Y; }
	
	public void setX(int x) { X = x; }
	public void setY(int y) { Y = y; }
	
	public boolean equals(Point other) {
		return (X == other.getX() && Y == other.getY());
	}
	
	@Override
	public String toString() {
		return "(" + X + "," + Y + ")";
	}
	
}
