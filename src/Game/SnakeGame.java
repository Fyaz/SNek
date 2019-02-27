package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ai.SnakeAI;
import graphics.GameMenu;
import graphics.MenuItem;

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
	private static final String CONFIRM = "confirm selection";
	
	// Physics update time
	private final long UPDATE_TIME = 50000000;	// The amount of time between each game update: 50000000
	private long previous_update_time = 0;	// After a certain amount of time, update the snake. (Separates the game physics from the frame rate)
	private int framerate = 0;	// Number of frames generated in between physics updates
	private int display_framerate = 0;
	
	// Game Variables
	private Snek snake = new Snek(GRID_WIDTH*GRID_HEIGHT);	// Create a snake (or rather, a list of body parts)
	private Point food_location;
	private GameMenu menu;
	private boolean isPaused = false;	// A variables to keep track if the game is paused or not.
	private boolean isLoser = false;	// The player loses the game when they collide with the edge or itself
	private boolean isWinner = false;	// The player wins when it the snake is the size of the grid x grid. 
	
	// Snake AI
	SnakeAI ai;
	private int generation = 0;
	
	// Constructor ==========================================================================
	
	public SnakeGame() {
		// Configure Window
		setPreferredSize(WINDOW);
		setSize(getPreferredSize());
		
		//Configure Game variables
		ai = new SnakeAI();
		ai.turnOff();
		resetGame();
		configureKeyBindings();
		configureMenu();
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
		if(ai.isOn())
			g2D.drawString("Gen: " + generation, 5, 30);
		
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
		
		// Update game variables after a certain amount of time.
		g2D.setColor(TEXT_COLOR);
		if(!isPaused && !isLoser && !isWinner) {
			if(System.nanoTime() - previous_update_time > UPDATE_TIME) {
				gameUpdate();
			}
		}
		else {
			if(isWinner) {
				g2D.drawString("You Win!", getWidth()/2-40, getHeight()/2-30);
				g2D.drawString("Press \"Space\" to play again", getWidth()/2-80, getHeight()/2-15);
			}
			else if(isLoser) {
				g2D.drawString("You Lose...", getWidth()/2-40, getHeight()/2-30);
				g2D.drawString("Press \"Space\" to play again", getWidth()/2-80, getHeight()/2-15);
			}
			else if(isPaused) {
				g2D.drawString("Game Paused", getWidth()/3, getHeight()/3-10);
				menu.draw(getWidth()/3, getHeight()/3, getWidth()/3, getHeight()/3, g2D);
			}
		}
		
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
			ai.update();	
		}
		
		// Update the snake (move it forward)
		Point newHead = new Point(snake.head().getX()+snake.getdX(), snake.head().getY()+snake.getdY());
		if(!inGrid(newHead)) {
			isLoser = true;
		}
		
		// Check collision with self
		for(int i = 1; i < snake.length(); i++)
			if(snake.getPointAt(i).equals(snake.head())) {
				isLoser = true; 
			}
		
		// Check for collision with the food
		if(snake.head().equals(food_location)) {
			snake.addToBody(newHead);
			food_location = getRandomPoint();
		}
		else {
			snake.UpdateSnek(newHead);
		}
		
		// Check if winner
		if(snake.length() == (GRID_WIDTH * GRID_HEIGHT)) {
			isWinner = true;
		}
		
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
			i.setX(i.getX() + snake.getdX());
			i.setY(i.getY() + snake.getdY());
		}
		return false;
	}
	
	/** Check whether the Point p is within the grid. 
	 * @param p - Check whether this Point is within in grid 
	 * @return true if p is within the bounds of the grid, else false. */
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
		snake.clear();
		snake.addToBody(new Point(GRID_WIDTH/2, GRID_HEIGHT/2));
		snake.addToBody(new Point(GRID_WIDTH/2-1, GRID_HEIGHT/2));
		snake.addToBody(new Point(GRID_WIDTH/2-2, GRID_HEIGHT/2));
		snake.addToBody(new Point(GRID_WIDTH/2-3, GRID_HEIGHT/2));
		food_location = getRandomPoint();
		snake.setdX(0);
		snake.setdY(snake.getSpeed());
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
				getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), CONFIRM);
				getActionMap().put(KEY_UP, new AbstractAction() {
					private static final long serialVersionUID = 2L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(isPaused) 
							menu.moveUp();
						else
							moveUp();
					}
				});
				getActionMap().put(KEY_DOWN, new AbstractAction() {
					private static final long serialVersionUID = 3L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(isPaused)
							menu.moveDown();
						else
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
				getActionMap().put(CONFIRM, new AbstractAction() {
					private static final long serialVersionUID = 8L;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(isPaused) {
							menu.execute();
							if(menu.closeFlagSet())
								isPaused = false;
						}
					}
				});
	}
	
	/** Configure the pause menu to switch between different AIs. */
	private void configureMenu() {
		menu = new GameMenu();
		menu.addMenuItem(new MenuItem("Human", false) {
			@Override
			public void actionPerformed() {
				ai.turnOff();
			}
		});
		menu.addMenuItem(new MenuItem("Neural", false) {
			@Override
			public void actionPerformed() {
				ai.turnOn();
				ai.setAI(SnakeAI.NEURAL_AI);
				generation = 1;
			}
		});
		menu.addMenuItem(new MenuItem("BFS", false) {
			@Override
			public void actionPerformed() {
				ai.turnOn();
				ai.setAI(SnakeAI.BFS);
			}
		});
		menu.addMenuItem(new MenuItem("Exit", true) {
			@Override
			public void actionPerformed() {
			}
		});
	}
	
	// Action Methods =======================================================================
	
	/** Turn the snake to move up if he's not looking down. */
	private void moveUp() {
		if(snake.getdY() != snake.getSpeed()) {
			snake.setdX(0);
			snake.setdY(-snake.getSpeed());
		}
	}
	
	private void moveDown() {
		if(snake.getdY() != -snake.getSpeed()) {
			snake.setdX(0);
			snake.setdY(snake.getSpeed());
		}
	}
	
	private void moveLeft() {
		if(snake.getdX() != snake.getSpeed()) {
			snake.setdX(-snake.getSpeed());
			snake.setdY(0);
		}
	}
	
	private void moveRight() {
		if(snake.getdX() != -snake.getSpeed()) {
			snake.setdX(snake.getSpeed());
			snake.setdY(0);
		}
	}
}
