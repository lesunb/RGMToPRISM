package $UTIL_PACKAGE.components;


import jadex.util.Tuple;

/**
 * Used to store decomposition links in the belief base, extended from Tuple. 
 * Not used in the code, but only in the ADF belief base and the query expressions.
 * 
 * @author Mirko Morandini
 */
public class TLink extends Tuple {
	/**
	 * Creates a new TLink instance
	 * 
	 * @param begin source element name
	 * @param end target element name
	 */
	public TLink( String begin, String end ) {
		super(begin, end);
	}

	/**
	 * Creates a new TLink instance
	 * 
	 * @param begin source element name
	 * @param end target element name
	 * @param priority link priority
	 */
	public TLink(String begin, String end, int priority) {
		super(new Object[] { begin, end, priority });// used java 1.5 Integer boxing!
	}

	/**
	 * Get the goal related to this link
	 * 
	 * @param idx index of the element to search for
	 * 
	 * @return the goal for this link
	 */
	public TGoal getGoal(int idx) {
		return Components.getGoal((String) get(idx));
	}

	/**
	 * Gets the priority, if it was set in the constructor.
	 * 
	 * @return the priority if set, 0 otherwise.
	 */
	public Integer getPriority() {
		return size() > 2 ? (Integer) get(2) : 0;
	}
}