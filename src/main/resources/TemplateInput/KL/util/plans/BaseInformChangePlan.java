package $UTIL_PACKAGE.plans;

import jadex.runtime.IMessageEvent;

import java.util.StringTokenizer;

import $UTIL_PACKAGE.components.Components;
import $UTIL_PACKAGE.components.TSoftgoal;

/**
 * This plan handles INFORM-queries for changing values of softgoals and 
 * belief-base resources. 
 * Expected format: "change [softgoal|resource] value {;[softgoal|resource] value}*
 * On error the plan responses with a failure message.
 * 
 * @author Mirko Morandini
 */
public abstract class BaseInformChangePlan extends APlan {
	String request = "";
	
	public BaseInformChangePlan() {
		super();
		
		getLogger().info("Created: " + this + ".");
	}

	public void body() {
		request = (String) ((IMessageEvent) getInitialEvent()).getContent();
		TSoftgoal sg;

		StringTokenizer stok = new StringTokenizer(request, " ");

		if (stok.countTokens() >= 3) {
			@SuppressWarnings("unused")
			String action = stok.nextToken(" ");// contains the word "change"
			do {
				String component = stok.nextToken(" ;");// the softgoal/resource name
				String content = stok.nextToken(" ;");// the new value

				if ((sg = Components.getSoftgoal(component)) != null) {
					try {
						double imp = Double.valueOf(content);
						sg.setImportance(imp);
						getLogger().info("Importance of Softgoal " + component + " set to " + imp + ".");
					} catch (NumberFormatException e) {
						String failure=	"Content for Softgoal " + component + " not a double number.";
						requestFailure(failure);
						return;
					}
				} else if (getBeliefbase().containsBelief(RESOURCEPREFIX + component)) {
					try {
						//Class c = getBeliefbase().getBelief(RESOURCEPREFIX + component).getClazz();
						//Object o = c.cast(content);
						//getBeliefbase().getBelief(RESOURCEPREFIX + component).setFact(o);
						getBeliefbase().getBelief(RESOURCEPREFIX + component).setFact(content);
						getLogger().info("New content set for resource " + component + ".");
					} catch (RuntimeException e) {
						String failure=	"Errors storing the content for resource " + component
								+ ", maybe due to a casting problem.";
						requestFailure(failure);
						return;
					}
				} else {
					String failure=	"Component " + component
									+ " not found either in the belief base nor in the softgoals base.";
					requestFailure(failure);
					return;
				}
			} while (stok.hasMoreElements() && stok.countTokens() >= 2);

		} else {
			String failure=	"Inform change format not correct: " + request
							+ ". Expected: \"change {[softgoal|resource] value;}+\"";
			requestFailure(failure);
		}
	}

	/**
	 * @param request
	 */
	protected void requestFailure(String cont) {
		getLogger().warning(cont);
		sendMessage(((IMessageEvent) getInitialEvent()).createReply("failure", cont));
	}
}