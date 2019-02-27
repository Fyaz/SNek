package ai;

import java.util.ArrayList;

/** A collection holding a list of Objects. 
 * It is a list of Objects because different Ai may 
 * require different amounts and types of inputs. 
 * 
 * The AI interface has a method called parseInputs 
 * that each AI should call to compile this list of Objects to things they need. */
public class AIInput {

	/** A list containing different Objects.
	 * The AI have to compile the inputs individually. */
	private ArrayList<Object> inputs;
	
	/** Basic Constructor */
	public AIInput() {
		inputs = new ArrayList<Object>();
	}
	
	/** Add an object to the list of Objects. */
	public void add(Object o) {
		inputs.add(o);
	}
	
	/** Remove an object from the list of Objects at index, i.
	 * @param i - the index of the input you want to remove. */
	public void remove(int i) {
		if(!inputs.isEmpty())
			if(i > -1 && i < inputs.size())
				inputs.remove(i);
	}
	
	/** Returns an Object from the list. 
	 * @param i - the index of the input you want to recover. 
	 * @return the Object stored at index, i. */
	public Object get(int i) {
		if(i > -1 && i < inputs.size())
			return inputs.get(i);
		else 
			return null;
	}
	
	/** Clear the list. */
	public void clear() {
		inputs.clear();
	}
	
}
