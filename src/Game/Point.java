package game;

public class Point implements Comparable<Point> {

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
	
	public Point(Point other) {
		X = other.X;
		Y = other.Y;
	}
	
	public int getX() { return X; }
	public int getY() { return Y; }
	
	public void setX(int x) { X = x; }
	public void setY(int y) { Y = y; }
	
	@Override
	public int hashCode() {
		int hash = X;
		hash = 31*hash + Y;
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		Point other = (Point)o;
		return (X == other.getX() && Y == other.getY());
	}
	
	@Override
	public String toString() {
		return "(" + X + "," + Y + ")";
	}
	
	@Override
	public int compareTo(Point o) {
		if(areaUnderVector() > o.areaUnderVector()) {
			return 1;
		}
		else if(areaUnderVector() < o.areaUnderVector()) {
			return -1;
		}
		else if(areaUnderVector() == o.areaUnderVector()) {
			if(Y > o.getY())
				return 1;
			else if(Y < o.getY())
				return -1;
		}
		return 0;
	}
	
	private double areaUnderVector() {
		return X*Y/2;
	}
}
