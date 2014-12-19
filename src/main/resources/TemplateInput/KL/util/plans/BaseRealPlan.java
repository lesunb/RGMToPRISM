package $UTIL_PACKAGE.plans;

/**
 * @author Mirko Morandini
 */
public abstract class BaseRealPlan extends APlan {
	//the name of the capabilities agent
	protected String capabilitiesAgent = "$CAPABILITY_AGENT"; 
	
	/**
	 * Creates a new BaseRealPlan instance
	 */
	public BaseRealPlan() {
		super();
		
		getLogger().info(
				"RealPlan " + getName() + " started. Param: " + getParameter("param").getValue());
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	protected Object useResource( String name ) {
		Object res = null;
		
		try {
			res = getBeliefbase().getBelief(RESOURCEPREFIX + name).getFact();
		} catch (RuntimeException e) {
			getLogger().warning("UseResource: Resource " + name + " not found!");
		}
		return res;
	}

	/**
	 * 
	 * @param name
	 * @param o
	 */
	protected void produceResource(String name, Object o) {
		try {
			getBeliefbase().getBelief(RESOURCEPREFIX + name).setFact(o);
		} catch (RuntimeException e) {
			getLogger().warning("ProduceResource: Resource " + name + " not found!");
		}
	}
	
	/**
	 * 
	 * @param capability
	 * @param param
	 * @param agent
	 */
	protected void requestCapability( String capability, String param, String agent ) {
		request(capability, capabilitiesAgent + "_" + agent, param, "request Capability", "capability_request");
	}

	@Override
	/**
	 * 
	 */
	protected void requestINFORM(String capability, String actor, String param, String monitorTask) {
		String cont = "Actor " + capabilitiesAgent + " informs that capability " + capability + 
			" with parameters " + param + " was correctly executed.";
		getLogger().info(cont);
	}
	
	@Override 
	/**
	 * 
	 */
	protected void requestFAILURE(String capability, String actor, String param, String monitorTask) {	 
		String cont = "Actor " + capabilitiesAgent + " informs that capability " + capability + 
			" with parameters " + param + " has FAILED!";
		getLogger().warning(cont);
		fail();
	}
	
	@Override 
	/**
	 * 
	 */
	protected void requestAGREE(String capability, String actor, String param, String monitorTask) {	 
		getLogger().info("AGREE form "+ capabilitiesAgent + " for capability "+ capability + ".");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPlanName() {
		return getRPlan().getModelElement().getName().substring("RealPlan_".length());
	}
}