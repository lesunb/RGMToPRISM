package $UTIL_PACKAGE.plans;

import java.util.List;

import $UTIL_PACKAGE.components.Components;
import $UTIL_PACKAGE.components.TDependency;
import $UTIL_PACKAGE.components.TGoal;

import jadex.runtime.GoalFailureException;
import jadex.runtime.IGoal;

/**
 * @author Mirko Morandini
 */
public abstract class BaseANDDispatchGoalPlan extends APlan {
	
	/**
	 * Creates a new BaseANDDispatchGoalPlan
	 *
	 */
	public BaseANDDispatchGoalPlan() {
		super();
		
		getLogger().info("Created: " + this + ".");
	}

	/**
	 * 
	 */
	public void body() {
		String param = (String) getParameter("param").getValue();
		// the new prototype uses resources and produce/use dependencies to propagate results, if
		// wanted by the designer.
		goalDependencies(param);
		goalsANDdispatch(param);
	}

	@SuppressWarnings("deprecation")
	/**
	 * 
	 */
	protected void goalDependencies(String param) {
		// get the triggering goal name
		String goalname = getRootGoal().getModelElement().getName();
		// get the goal object created in the beliefbase
		TGoal g = Components.getGoal(goalname);
		
		if (g == null) {
			getLogger().warning("Goal " + goalname + " not found in Belief Base!");
			fail();// propagates the failure directly to the parent goal.
		}
		
		// get all decomposition goals for this goal from the beliefbase
		List<TDependency> dependencies = g.getAllDependencies(this);
		
		// dispatch all dependencies in AND (one fails->all fails)
		for (TDependency dep : dependencies) {
			request(dep.getDependumGoal(), dep.getDependeeActor(), param, "resolve dependency", "dependency_request");
		}
	}
	
	@Override
	/**
	 * 
	 */
	protected void requestFAILURE(String goal, String actor, String param, String monitorTask) {	 
		String cont = "Actor " + actor + " informs that dependent goal " + goal + 
			" with parameters " + param	+ " has FAILED!";
		getLogger().warning(cont);
		fail();
	}
	
	@Override
	/**
	 * 
	 */
	protected void requestINFORM(String goal, String actor, String param, String monitorTask) {
		String cont = "Actor " + actor + " informs that dependent goal " + goal + 
			" with parameters " + param + " was reached.";
		getLogger().info(cont);
	}

	/**
	 * Overwriteable! Attention: used deprecated method "Plan.getRootGoal"
	 * 
	 * @param param
	 */
	@SuppressWarnings("deprecation")
	protected void goalsANDdispatch(String param) {
		Object result = param;
		// get the triggering goal name
		String goalname = getRootGoal().getModelElement().getName();

		// get the goal object created in the beliefbase
		TGoal g = Components.getGoal(goalname);
		
		if (g == null) {
			getLogger().warning(
				"AND goal failure: No goal decomposition for " + goalname + " found in Belief Base!");
			fail();// propagates the failure directly to the parent goal.
		}
		// get all decomposition goals for this goal from the beliefbase
		List<TGoal> goals = g.getAllDecompositionGoals(this);
		
		if (goals.size() < 1)
			fail();
		
		// dispatch all goals in AND (one fails->all fails)
		for (TGoal tGoal : goals) {
			monitor=new Object[] {"dispatch subgoal", g.getName(), tGoal.getName()};
			IGoal goal = createGoal(tGoal.getName());
			goal.getParameter("param").setValue(param);
		
			try {
				dispatchSubgoalAndWait(goal, TIMEOUT);
				result = goal.getParameter("result").getValue();
			} catch (GoalFailureException e) {
				getLogger().warning("Local goal failure (goal " + tGoal.name + ", content: " + param + ").");
				fail();// propagates the first failure directly to the parent goal (AND!).
			}
			monitor=null;
		}
		
		// return the results from the subgoal to the triggering goal
		// only the last result is returned, all others are discared by default
		getParameter("result").setValue(result);// sets result in the triggering goal
	}
}