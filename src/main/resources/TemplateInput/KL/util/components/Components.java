package $UTIL_PACKAGE.components;

import java.util.Hashtable;

/**
 * Class with only static methods, holds hashtables to store Tropos components 
 * (Goals and Softgoals).
 * 
 * @author Mirko Morandini
 */
public class Components {
	private static Hashtable<String, TSoftgoal> softgoals = new Hashtable<String, TSoftgoal>();

	private static Hashtable<String, TGoal> goals = new Hashtable<String, TGoal>();

	public static TSoftgoal createSoftgoal(String name, double importance) {
		if (softgoals.get(name) == null) {
			softgoals.put(name, new TSoftgoal(name, importance));
		}
		return softgoals.get(name);
	}

	public static TGoal createGoal(String name, String decomp) {
		if (goals.get(name) == null) {
			goals.put(name, new TGoal(name, decomp));
		}
		return goals.get(name);
	}

	public static TSoftgoal getSoftgoal(String name) {
		return softgoals.get(name);
	}

	public static TGoal getGoal(String name) {
		return goals.get(name);
	}
}