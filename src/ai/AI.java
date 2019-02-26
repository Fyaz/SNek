package ai;


/** TODO: Figure out how to get feedback to the different ais about how
 * their desicions were made. 
 * BFS: will need numerical and boolean inputs. 
 * Brain: will need whether its previous decision was correct or not. */
public interface AI {
	AIOutput getOutput(); // A method called by SnakeAI to update the output of the snake. 
}
