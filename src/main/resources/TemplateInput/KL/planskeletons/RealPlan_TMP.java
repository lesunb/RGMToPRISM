package $PLAN_PACKAGE;

import $UTIL_PACKAGE.plans.BaseRealPlan;

/**
 * 
 */
public class RealPlan_TMP extends BaseRealPlan {
	@Override
	public void body() {
		String param = (String) getParameter("param").getValue();
		//String resource = (String) useResource("RESOURCE");
		
		//****Business logic****
		//the name of the capability is the one of the main plan
		//String capabilityName = getPlanName();
		//requestCapability( capabilityName, param , "$AGENT_NAME" );
		//**********************
		
		getParameter("result").setValue(param);
		//produceResource("RESOURCE", result);
	}
}
