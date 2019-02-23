package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import ai.SnakeAI;

/** Opens up a JPanel that plays a Snake game using the arrow keys on the keyboard. */
public class SnakeGame extends JPanel {
	
	// Variables ============================================================================
	
	/** ?? */
	private static final long serialVersionUID = 1L;
	
	// The maximum dimensions of the grid
	private final Dimension WINDOW = new Dimension(500,500);
	private final int GRID_WIDTH = 25;
	private final int GRID_HEIGHT = 25;
	
	// Default Colors
	private final Color TEXT_COLOR = Color.WHITE;
	private final Color BACKGROUND = Color.BLACK;
	private final Color HEAD_COLOR = Color.CYAN;
	private final Color SNEK_COLOR = Color.GREEN;
	private final Color FOOD_COLOR = Color.ORANGE;
	
	// Input Variables (needed for Key Bindings)
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String KEY_UP = "move up";
	private static final String KEY_DOWN = "move down";
	private static final String KEY_LEFT = "move left";
	private static final String KEY_RIGHT = "move right";
	private static final String PAUSE = "pause game";
	private static final String SAVESTATE = "save";
	
	// Snake and direction variables
	private Snek snake = new Snek(GRID_WIDTH*GRID_HEIGHT);	// Create a snake (or rather, a list of body parts)
	private final byte SPEED = 1;
	private int dX = 0;
	private int dY = 0;
	
	// Food Variables 
	private Point food_location;
	
	// Physics update time
	private final long UPDATE_TIME = 50000000;	// The amount of time between each game update: 50000000
	private long previous_update_time = 0;	// After a certain amount of time, update the snake. (Separates the game physics from the frame rate)
	private int framerate = 0;	// Number of frames generated in between physics updates
	private int display_framerate = 0;
	private boolean isPaused = false;	// A variables to keep track if the game is paused or not.
	private boolean isLoser = false;	// The player loses the game when they collide with the edge or itself
	private boolean isWinner = false;	// The player wins when it the snake is the size of the grid x grid. 
	
	// Snake AI
	SnakeAI ai;
	private int generation = 0;
	private final int LOSEFEEL = -100;
	private final int EATFEEL = 10000;
	private final int WINFEEL = 50000;
	
	// Constructor ==========================================================================
	
	public SnakeGame() {
		// Configure Window
		setPreferredSize(WINDOW);
		setSize(getPreferredSize());
		
		//Configure Game variables
		ai = new SnakeAI();
		ai.turnOn();
		resetGame();
		configureKeyBindings();
	}
	
	// Public Methods =======================================================================
	
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		int block_width = getWidth()/GRID_WIDTH;
		int block_height = getHeight()/GRID_HEIGHT;
		
		// Background
		g2D.setColor(BACKGROUND);
		g2D.fillRect(0, 0, getWidth(), getHeight());
		
		// Draw text info
		g2D.setColor(TEXT_COLOR);
		g2D.drawString("FPS: " + display_framerate, 5, 15);
		g2D.drawString("Gen: " + generation, 5, 30);
		g2D.drawString("Feel: " + ai.getFeel(), 5, 45);
		
		// Update game variables after a certain amount of time.
		if(!isPaused && !isLoser && !isWinner) {
			if(System.nanoTime() - previous_update_time > UPDATE_TIME) {
				gameUpdate();
			}
		}
		else {
			if(isWinner) {
				g2D.drawString("You Win!", getWidth()/2-40, getHeight()/2-30);
				g2D.drawString("Do you want to play again? ", getWidth()/2-80, getHeight()/2-15);
			}
			else if(isLoser) {
				g2D.drawString("You Lose...", getWidth()/2-40, getHeight()/2-30);
				g2D.drawString("Do you want to play again? ", getWidth()/2-80, getHeight()/2-15);
			}
			else if(isPaused)
				g2D.drawString("Paused", getWidth()/2-20, getHeight()/2-10);
		}
		
		// Draw all the blocks of the snake
		g2D.setColor(HEAD_COLOR);
		g2D.fillRect(snake.head().getX()*(block_width)+2, 
				     snake.head().getY()*(block_height)+2,
				     block_width-2, 
				     block_height-2);
		g2D.setColor(SNEK_COLOR);
		for(int i = 1; i < snake.length(); i++) {
			g2D.fillRect(snake.getPointAt(i).getX()*(block_width)+2, 
					     snake.getPointAt(i).getY()*(block_height)+2,
					     block_width-2, 
					     block_height-2);
		}
		
		//Draw the food
		g2D.setColor(FOOD_COLOR);
		g2D.fillRect(food_location.getX()*(block_width)+2, 
				     food_location.getY()*(block_height)+2,
				     block_width-2, 
				     block_height-2);
		
		// Call to paint and loop the game engine?
		framerate++;
		EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            repaint();
	        }
	    });
	}
	
	// Private Methods ======================================================================
	
	/** Update the game and physics variables. */
	private void gameUpdate() {
		
		// Update the snake (move it forward)
		Point newHead = new Point(snake.head().getX()+dX, snake.head().getY()+dY);
		if(!inGrid(newHead)) {
			ai.setFeel(ai.getFeel() + LOSEFEEL);
			isLoser = true;
		}
		
		//Update AI
		if(ai.isOn()) {
			if(ai.up())
				moveUp();
			else if(ai.down())
				moveDown();
			else if(ai.left())
				moveLeft();
			else if(ai.right())
				moveRight();
			ai.updateAI();	
		}
		
		// Check collision with self
		for(int i = 1; i < snake.length(); i++)
			if(snake.getPointAt(i).equals(snake.head())) {
				ai.setFeel(ai.getFeel() + LOSEFEEL);
				isLoser = true; 
			}
		
		// Check for collision with the food
		if(snake.head().equals(food_location)) {
			ai.setFeel(ai.getFeel() + EATFEEL);
			snake.addToBody(newHead);
			food_location = getRandomPoint();
		}
		else {
			snake.UpdateSnek(newHead);
		}
		
		// Check if winner
		if(snake.length() == (GRID_WIDTH * GRID_HEIGHT)) {
			ai.setFeel(ai.getFeel() + WINFEEL);
			isWinner = true;
		}
		ai.addNewNode(ai.getFeel());
		
		if((isWinner || isLoser) && ai.isOn()) {
			resetGame();
		}
		
		// Update window variables
		display_framerate = framerate;
		framerate = 0;
		previous_update_time = System.nanoTime();
	}
	
	/** If the snake were to just go straight, then would he hit food? */
	private boolean food_stright_ahead() {
		Point i = new Point(snake.head().getX(), snake.head().getY());
		while(inGrid(i)) {
			if(i.equals(food_location))
				return true;
			i.setX(i.getX() + dX);
			i.setY(i.getY() + dY);
		}
		return false;
	}
	
	/** Check whether the Point p is within the grid. */
	private boolean inGrid(Point p) {
		return (p.getX() > -1 && p.getX() < GRID_WIDTH && p.getY() > -1 && p.getY() < GRID_HEIGHT);
	}
	
	/** Returns a random point within the grid that does not conflict with the snake's body. */
	private Point getRandomPoint() {
		Point output = new Point((int)(Math.random()*GRID_WIDTH), (int)(Math.random()*GRID_HEIGHT));
		while(snake.hasPoint(output))
			output = new Point((int)(Math.random()*GRID_WIDTH), (int)(Math.random()*GRID_HEIGHT));
		return output;
	}
	
	/** Reset the snake and regenerate the food. */
	private void resetGame() {
		generation++;
		ai.reset();
		snake.clear();
		snake.addToBody(new Point(GRID_WIDTH/2, GRID_HEIGHT/2));
		snake.addToBody(new Point(GRID_WIDTH/2-1, GRID_HEIGHT/2));
		snake.addToBody(new Point(GRID_WIDTH/2-2, GRID_HEIGHT/2));
		snake.addToBody(new Point(GRID_WIDTH/2-3, GRID_HEIGHT/2));
		food_location = getRandomPoint();
		dX = 0;
		dY = SPEED;
		previous_update_time = System.nanoTime();
		isLoser = false;
		isWinner = false;
		isPaused = false;
	}
	
	/** Configure the key bindings for the player to control. */
	private void configureKeyBindings() {
		// Configure Key Bindings
				getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), KEY_UP);
				getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), KEY_DOWN);
				getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), KEY_LEFT);
				getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), KEY_RIGHT);
				getInputMap(IFW).put(KeyStroke.getKeyStroke(' '), PAUSE);
				getInputMap(IFW).put(KeyStroke.getKeyStroke('s'), SAVESTATE);
				getActionMap().put(KEY_UP, new AbstractAction() {
					private static final long serialVersionUID = 2L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						moveUp();
					}
				});
				getActionMap().put(KEY_DOWN, new AbstractAction() {
					private static final long serialVersionUID = 3L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						moveDown();
					}
				});
				getActionMap().put(KEY_LEFT, new AbstractAction() {
					private static final long serialVersionUID = 4L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						moveLeft();
					}
				});
				getActionMap().put(KEY_RIGHT, new AbstractAction() {
					private static final long serialVersionUID = 5L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						moveRight();
					}
				});
				getActionMap().put(PAUSE, new AbstractAction() {
					private static final long serialVersionUID = 6L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(isPaused)
							isPaused = false;
						else
							isPaused = true;
						if(isLoser || isWinner)
							resetGame();
					}
				});
				getActionMap().put(SAVESTATE, new AbstractAction() {
					private static final long serialVersionUID = 7L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(isPaused) {
						}
					}
				});
				
	}
	
	// Action Methods =======================================================================
	
	/** Turn the snake to move up if he's not looking down. */
	private void moveUp() {
		if(dY != SPEED) {
			dX = 0;
			dY = -SPEED;
		}
	}
	
	private void moveDown() {
		if(dY != -SPEED) {
			dX = 0;
			dY = SPEED;
		}
	}
	
	private void moveLeft() {
		if(dX != SPEED) {
			dX = -SPEED;
			dY = 0;
		}
	}
	
	private void moveRight() {
		if(dX != -SPEED) {
			dX = SPEED;
			dY = 0;
		}
	}
}
