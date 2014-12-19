package $UTIL_PACKAGE.plans;

import jadex.adapter.fipa.AgentIdentifier;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;

/**
 * The base class for the different plan types. Defines the Planname prefixes, too.
 * 
 * @author Mirko Morandini
 */
public abstract class APlan extends Plan {
	//the timeout for all requests to other agents (dependencies, capability execution,...)
	public static final int TIMEOUT = 15000;
	
	public static final String PLANPREFIX = "realPlan_";
	public static final String GOALPREFIX = "dispatchGoalPlan_";
	public static final String RESOURCEPREFIX = "resource_";
	
	/**
	 * Contains informations to monitor the actual state of the plan.
	 * Needed for failure handling e.g. in <code>failed()</code>, if the plan exits due to a timeout.
	 */ 
	public Object[] monitor;
	
	/**
	 * Sends a request to another agent and deals with the received messages.
	 * @param task the taks to be performed
	 * @param actor
	 * @param param
	 * @param monitorTask the name of the task performed - for monitoring and logging purposes.
	 * @param encoding the content of the "encoding" field in the message.
	 */
	protected void request(String task, String actor, String param, String monitorTask, String encoding) {
		monitor=new Object[] {monitorTask, task, actor, param};
		jadex.adapter.fipa.AgentIdentifier receiver;
		receiver = new AgentIdentifier(actor, true);

		IMessageEvent me = createMessageEvent("request");
		me.getParameterSet(jadex.adapter.fipa.SFipa.RECEIVERS).addValue(receiver);
		me.getParameter(jadex.adapter.fipa.SFipa.ENCODING).setValue(encoding);
		me.setContent(task + " " + param);
		IMessageEvent reply = sendMessageAndWait(me, TIMEOUT);
		
		if (reply.getParameter("performative").getValue().equals(SFipa.AGREE)) {
			requestAGREE(task, actor, param, monitorTask);
			//aspetta un INFROM
			reply=waitForReply(me, TIMEOUT);
		}
		
		if (reply.getParameter("performative").getValue().equals(SFipa.FAILURE)) {
			requestFAILURE(task, actor, param, monitorTask);
		} else if (reply.getParameter("performative").getValue().equals(SFipa.INFORM)) {
			requestINFORM(task, actor, param, monitorTask);
		} else if (reply.getParameter("performative").getValue().equals(SFipa.REFUSE)) {
			requestREFUSE(task, actor, param, monitorTask);
		} else if (reply.getParameter("performative").getValue().equals(SFipa.NOT_UNDERSTOOD)) {
			requestN_U(task, actor, param, monitorTask);
		}
		
		monitor=null;
	}

	protected void requestAGREE(String task, String actor, String param, String monitorTask) {
		String cont = "Actor " + actor + " gave AGREE on \""+monitorTask+"\" with " + task + ".";
		getLogger().info(cont);
	}

	protected void requestINFORM(String task, String actor, String param, String monitorTask) {
		String cont = "Actor " + actor + " INFORMS that \""+monitorTask+"\" with " + task 
			+ " was successfull performed.";
		getLogger().info(cont);
	}
	
	protected void requestFAILURE(String task, String actor, String param, String monitorTask) {			
		String cont = "Actor " + actor + " informs that \""+monitorTask+"\" with " + task 
			+ " and parameters " + param + " has FAILED!";
		getLogger().warning(cont);
		fail();
	}
	
	protected void requestN_U(String task, String actor, String param, String monitorTask) {
		String cont = "Actor " + actor + " informs that \""+monitorTask+"\" with " + task 
			+ " and parameters " + param + " was not understood!";
		getLogger().warning(cont);
		fail();
	}

	protected void requestREFUSE(String task, String actor, String param, String monitorTask) {
		String cont = "Actor " + actor + " informs that \""+monitorTask+"\" with " + task 
			+ " and parameters " + param + " was refused!";
		getLogger().warning(cont);
		fail();
	}
	
	public void failed() {
		Exception e = getException();
		//if (e.toString().equals("jadex.runtime.TimeoutException")) {
		//	getLogger().warning("");
		//}
		//very easy failure logging:
		String reason="";
		if (monitor!=null) {
			for(Object s:monitor) {
				reason=reason+" "+s;
			}
		}
		getLogger().warning(e+". Problem: "+reason+".");
	}
}