package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class SubMenu extends MenuItem implements GraphicsElement {
	
	// The items in the menu. 
	private ArrayList<MenuItem> menuItems;
	
	private boolean visible; // If the menu is visible or not. 
	private int selection; // A cursor for the items being selected 
	private boolean close; // A flag. Generated after a MenuItem has been executed.
						   // If a menu item wants the menu to close after selection, then
						   // this flag will allow it to close it. 
	
	public SubMenu() {
		super();
		visible = false;
	}
	
	/** Add a menu item that requires implementing an action. */
	public void addMenuItem(MenuItem item) {
		menuItems.add(item);
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
		if(visible) {
			Color previous_color = g.getColor();
			g.setColor(new Color(0,0,0,96));
			g.fillRect(x, y, length, height);
			g.setColor(Color.WHITE);
			g.drawRect(x, y, length, height);
			
			int textX = x + length/2 - 30;
			int spaceBetweenText = Math.round((float)height/menuItems.size());
			int textY = y + spaceBetweenText / 2;
			for(int i = 0; i < menuItems.size(); i++) {
				menuItems.get(i).draw(textX, textY+(i*spaceBetweenText)+5, 0, spaceBetweenText, g);
			}
			
			g.drawRect(x+2, y+(spaceBetweenText*selection)+2, length-4, spaceBetweenText-4);
			g.setColor(previous_color);
		}
	}


}
