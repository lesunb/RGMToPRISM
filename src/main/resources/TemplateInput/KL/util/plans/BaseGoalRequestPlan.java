package $UTIL_PACKAGE.plans;

import jadex.runtime.GoalFailureException;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;

import java.util.StringTokenizer;

/**
 * @author Mirko Morandini
 */
public abstract class BaseGoalRequestPlan extends APlan {
	public String requestedgoal;

	public BaseGoalRequestPlan(final String requestedGoal) {
		super();
		this.requestedgoal=requestedGoal;
		getLogger().info("Created: "+ this + " with goal " + this.requestedgoal + ".");
	}

	public void body() {	
		String request=(String)((IMessageEvent)getInitialEvent()).getContent();
		
		//if(event.getEventclass().equals("eventclass_message")){
		StringTokenizer stok = new StringTokenizer(request," ");
		int cnttokens = stok.countTokens();
		if(cnttokens>=2){
			@SuppressWarnings("unused") 
			String action = stok.nextToken();//the goal name
			String param = stok.nextToken();
			
			IGoal goal = createGoal(requestedgoal);
			goal.getParameter("param").setValue(param);
			goalAgree(goal.getName(),param);
			
			try{
				dispatchSubgoalAndWait(goal);
				Object result=goal.getParameter("result").getValue();
				goalSuccess(goal.getName(),param, result);
			}catch(GoalFailureException e){
				getLogger().warning("Goal "+goal.getName()+" failed (content: "+param+").");
				goalFailure(goal.getName(),param);
			}
		}else{
			getLogger().warning("Request format not correct: "+request);
			requestFailure(request);
		}	
	}
	
	/**
	 * @param name
	 * @param param
	 * @param result
	 */
	protected abstract void goalSuccess(String name, String param, Object result);
	
	/**
	 * @param name
	 * @param param
	 */
	protected abstract void goalFailure(String name, String param);

	/**
	 * @param request
	 */
	protected abstract void requestFailure(String request);

	/**
	 * @param name
	 * @param param
	 */
	protected abstract void goalAgree(String name, String param);
}