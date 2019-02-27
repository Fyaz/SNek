package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class MenuItem implements GraphicsElement {

	private String name;
	private boolean close_when_executed;
	public abstract void actionPerformed();
	
	/** Basic Constructor. Sets the name to "<Blank Menu Item>. */
	public MenuItem() {
		this("<Blank Menu Item>", false);
	}
	
	/** Instantiate a Menu Item with the name, _name. */
	public MenuItem(String _name) {
		this(_name, false);
	}
	
	/** Instantiate a Menu item with the name, _name,
	 * and specify whether the menu item wants to close the parent
	 * Menu or not. */
	public MenuItem(String _name, boolean _close_when_executed) {
		name = _name;
		close_when_executed = _close_when_executed;
	}
	
	// Getter Setters for name
	public String getName() { return name; }
	public void setName(String _name) { name = _name; }
	
	// Getter Setter for close_when_executed
	public boolean closeAfterExecution() { return close_when_executed; }
	public void setcloseAfter(boolean _close_when_executed) { close_when_executed = _close_when_executed; }
	
	public String toString() { 
		return name;
	}
	
	@Override
	public void draw(int x, int y, int length, int height, Graphics g) {
		Color previous_color = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString(name, x, y);
		g.setColor(previous_color);
	}
	
}
