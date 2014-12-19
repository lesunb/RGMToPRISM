package $PLAN_PACKAGE;

import $UTIL_PACKAGE.plans.BaseGoalRequestPlan;
import jadex.runtime.IMessageEvent;

/**
 * A sample implementation for goal requests that deals with
 * 
 */
public class GoalRequestPlan extends BaseGoalRequestPlan {
	public GoalRequestPlan(String requestedGoal) {
		super(requestedGoal);
	}

	@Override
	protected void goalSuccess(String name, String param, Object result) {
		String cont = "Goal success with " + name + " \"" + param + "\": " + result;
		getLogger().info(cont);
		sendMessage(((IMessageEvent) getInitialEvent()).createReply("inform", cont));
	}

	@Override
	protected void goalFailure(String name, String param) {
		String cont = "Failure with goal " + name + ": " + param;
		getLogger().info(cont);
		sendMessage(((IMessageEvent) getInitialEvent()).createReply("failure", cont));
	}

	@Override
	protected void requestFailure(String request) {
		String cont = "Request format not correct, needs: #" + "action param";
		getLogger().info(cont);
		sendMessage(((IMessageEvent) getInitialEvent()).createReply("failure", cont));
	}

	@Override
	protected void goalAgree(String name, String param) {
		//sendMessage(((IMessageEvent) getInitialEvent()).createReply("agree", param));
	}
}
