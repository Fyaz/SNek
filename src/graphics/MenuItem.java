package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class MenuItem implements GraphicsElement {

	private String name;
	private boolean close_when_executed;
	public abstract void actionPerformed();
	
	public MenuItem() {
		this("<Blank Menu Item>", false);
	}
	
	public MenuItem(String _name) {
		this(_name, false);
	}
	
	public MenuItem(String _name, boolean _close_when_executed) {
		name = _name;
		close_when_executed = _close_when_executed;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String _name) {
		name = _name;
	}
	
	public String toString() { 
		return name;
	}
	
	public boolean closeAfterExecution() {
		return close_when_executed;
	}
	
	public void setcloseAfter(boolean _close_when_executed) {
		close_when_executed = _close_when_executed;
	}
	
	@Override
	public void draw(int x, int y, int length, int height, Graphics g) {
		Color previous_color = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString(name, x, y);
		g.setColor(previous_color);
	}
	
}
