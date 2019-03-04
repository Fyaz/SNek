package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class GameMenu implements GraphicsElement {
	
	// The items in the menu. 
	private ArrayList<MenuItem> menuItems;
	
	// A cursor for the items being selected 
	private int selection;
	private boolean close; // A flag. Generated after a MenuItem has been executed.
						   // If a menu item wants the menu to close after selection, then
						   // this flag will allow it to close it. 
	
	/** Basic Constructor. */
	public GameMenu() {
		menuItems = new ArrayList<MenuItem>();
		selection = 0;
		close = false;
	}
	
	/** Add a menu item that requires implementing an action. */
	public void addMenuItem(MenuItem item) {
		menuItems.add(item);
	}
	
	/** Returns the item at index i. Returns null if nothing is there. */
	public MenuItem getMenuItem(int i) {
		return menuItems.get(i);
	}
	
	/** Returns the index of the currently selected menu item. */
	public int getSelection() {
		return selection;
	}
	
	/** A method for navigating the Menu. 
	 * Makes the cursor go up in the menu. */
	public void moveUp() { 
		if(selection > 0) {
			selection--;
		}
	}
	
	/** A method for navigating the Menu.
	 * Makes the cursor go down in the menu. */
	public void moveDown() { 
		if(selection < menuItems.size()-1) {
			selection++;
		}
	}
	
	/** A method to confirm selection in the Menu. 
	 * When called, the actionPerformed method for the MenuItem is called. */
	public void execute() {
		if(menuItems.size() != 0)
			menuItems.get(selection).actionPerformed();
		close = menuItems.get(selection).closeAfterExecution();
	}
	
	/** A flag is set to close the menu if the menu item wants it closed. */
	public boolean closeFlagSet() {
		return close;
	}

	@Override
	public void draw(int x, int y, int length, int height, Graphics g) {
		Color previous_color = g.getColor();
		g.setColor(new Color(0,0,0,96));
		g.fillRect(x, y, length, height);
		
		g.setColor(Color.WHITE);
		int spaceBetweenText = Math.round((float)height/menuItems.size());
		int textY = y + spaceBetweenText / 2;
		for(int i = 0; i < menuItems.size(); i++) {
			menuItems.get(i).draw(x+10, textY+(i*spaceBetweenText)+5, 0, spaceBetweenText, g);
			g.drawRect(x, y+(i*spaceBetweenText), length, height/menuItems.size());
		}
		
		g.drawRect(x+2, y+(spaceBetweenText*selection)+2, length-4, spaceBetweenText-4);
		g.setColor(previous_color);
	}

}
