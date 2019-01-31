import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {
	
	private static BufferedImage icon;
	private static String icon_path = "icon.png";

	/** Method to start the game. */
	public static void main(String[] args) {
		JFrame window = new JFrame();
		SnakeGame app = new SnakeGame();
		window.add(app);
		window.pack();
		
		// Configure the window
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	// Set the look and feel to the system look and feel. 
			icon = ImageIO.read(new File(icon_path));
			window.setIconImage(icon);	// Set the icon of the window (top left)
        } 
        catch (Exception e) {
            System.err.println("System look and feel did not load properly.");
        }
		window.setTitle("Snake Game");
		window.setLocationRelativeTo(null);	// Center the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// When the X button is pressed, terminate the program.
		window.setVisible(true);
	}
}
