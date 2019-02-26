package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class MenuItem implements GraphicsElement {

	private String name;
	abstract void actionPerformed();
	
	public MenuItem() {
		name = "<Blank Menu Item>";
	}
	
	public MenuItem(String _name) {
		name = _name;
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
	
	@Override
	public void draw(int x, int y, int length, int height, Graphics g) {
		Font MenuText = new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, height);
		Color previous_color = g.getColor();
		Font previous_font = g.getFont();
		
		g.setColor(Color.WHITE);
		g.setFont(MenuText);
		g.drawString(name, x, y);
		g.setColor(previous_color);
		g.setFont(previous_font);
	}
	
	
	
	
}
