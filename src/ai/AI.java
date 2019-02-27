package ai;


/** TODO: Figure out how to get feedback to the different ais about how
 * their desicions were made. 
 * BFS: will need numerical and boolean inputs. 
 * Brain: will need whether its previous decision was correct or not. */
public interface AI {
	// Each type of AI should get a list of Objects as their input defined in AIInput
	// With the list of inputs, they their outputs based on the inputs given. 
	void parseInput(AIInput _input); 
	
	// A method called by SnakeAI to update the output of the snake. 
	AIOutput getOutput();
}
