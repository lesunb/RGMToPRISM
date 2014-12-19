package $UTIL_PACKAGE.plans;

import jadex.runtime.GoalFailureException;
import jadex.runtime.IGoal;

/**
 * Dispatch goals at OR-decomposition or Means-end.
 * 
 * @author Mirko Morandini
 */
@SuppressWarnings("serial")
public abstract class BaseDispatchGoalPlan extends APlan {
	public String requestedgoal;

	public BaseDispatchGoalPlan(final String requestedGoal) {
		super();
		this.requestedgoal=requestedGoal;
		getLogger().info("Created: "+this+" with goal "+this.requestedgoal+".");
	}

	public void body() {
		String param="";	
		
		param = (String)getParameter("param").getValue();	
		IGoal goal = createGoal(requestedgoal);
		goal.getParameter("param").setValue(param);
		
		try{	
			dispatchSubgoalAndWait(goal, TIMEOUT);
			Object result=goal.getParameter("result").getValue();
			//return the results from the subgoal to the triggering goal 
			getParameter("result").setValue(result);
			
		} catch(GoalFailureException e){
			getLogger().warning("Local goal failure (goal "+requestedgoal+", content: "+param+").");
			fail();//propagates the failure to the parent goal.
		}
	}	
}