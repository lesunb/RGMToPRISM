package br.unb.cic.rtgoretoprism.generator.goda.producer;

import it.itc.sra.taom4e.model.core.informalcore.formalcore.FActor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FPlan;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FormalcoreFactory;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.impl.FormalcoreFactoryImpl;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;

public class CRGMEvaluationCtProducer {
	
	private int MAX_DEPTH;
	private int MAX_GOALS_DEPTH;
	private int MAX_BRANCH;
	
	private String ACTOR_NAME;
	
	//private int RETRY_MOD = 3;
	
	private FormalcoreFactory formalFactory;
	private FActor actor;
	public AgentDefinition agentDefinition;
	private int timeSlot = 0;
	private int timePath = 0;
	private PlanContainer original=null;
	private PlanContainer success=null;
	private PlanContainer failure=null;
	
	{
		formalFactory = FormalcoreFactoryImpl.init();
	}
	
	public static void main(String [] args){
		CRGMEvaluationCtProducer producer = new CRGMEvaluationCtProducer(8, 6, 2, "EvaluationActor");
		producer.generateCRGM();
		System.out.println(producer.agentDefinition.getGoalBase().size());
		System.out.println(producer.agentDefinition.planbase.size());		
	}
	
	public CRGMEvaluationCtProducer(int maxDepth, int maxGoalsDepth, int maxBranch, String actorName){
		this.MAX_DEPTH = maxDepth;
		this.MAX_GOALS_DEPTH = maxGoalsDepth;
		this.MAX_BRANCH = maxBranch;
		this.ACTOR_NAME = actorName;
	}
	
	public AgentDefinition generateCRGM(){
		createActor();
		agentDefinition = new AgentDefinition(actor);
		RTContainer root = createGoal(null, 0, 0, "");
		agentDefinition.addRootGoal((GoalContainer)root);
		addElement(root, 0, "");
		return agentDefinition;
	}
	
	public void addElement(RTContainer parent, int depth, String id){
		
		if(++depth == MAX_DEPTH)
			return;
		
		for(int branch = 0; branch < MAX_BRANCH; branch++)
			//if(depth == MAX_GOALS_DEPTH || branch < 2)
				addElement(createElement(parent, depth, branch, id + branch), depth, id + branch);			
	}
	
	private RTContainer createElement(RTContainer parent, int depth, int branch, String id) {
		if(depth < MAX_GOALS_DEPTH){
			return createGoal(parent, depth, branch, id);
		}else{
			return createPlan(parent, depth, branch, id);
		}
	}

	private GoalContainer createGoal(RTContainer parent, int depth, int branch, String id) {
		FHardGoal goal = formalFactory.createFHardGoal();
		goal.setActor(actor);
		goal.setName("G" + depth + (id.isEmpty() ? "" : "." + id) + ": Goal");
		goal.setIdentificator("G_"+depth+"_"+id);
		GoalContainer goalCt = agentDefinition.createGoal(goal, Const.ACHIEVE);
		goalCt.setIncluded(true);
		goalCt.setCardNumber(0);
		goalCt.setCardType(Const.SEQ);
		if(branch > 0)
			;//timeSlot++;		
		goalCt.setTimeSlot(timeSlot);
		goalCt.setTimePath(timePath);
		if(parent != null){
			((GoalContainer) parent).createDecomposition(Const.AND);
			((GoalContainer) parent).addDecomp(goalCt);
		}
		return goalCt;
	}
	
	private PlanContainer createPlan(RTContainer parent, int depth, int branch, String id) {
		FPlan plan = formalFactory.createFPlan();
		plan.setActor(actor);
		plan.setName("T" + depth + "." + id + ": Plan");
		plan.setIdentificator("T_"+depth+"_"+id);
		PlanContainer planCt = agentDefinition.createPlan(plan);
		
		planCt.setIncluded(true);
		planCt.setCardNumber(0);
		planCt.setCardType(Const.SEQ);

		if(branch == 0)
			;//timeSlot = timePath++;
		timeSlot++;
		
		planCt.setPrevTimePath(timePath);
		planCt.setTimePath(timePath);
		planCt.setTimeSlot(timeSlot);		
		
		//Custom
		//planCt.setOptional(true);
		if(false && depth == MAX_DEPTH - 1){
			if(branch == 0)
				original = planCt;
			else if(branch == 1){
				planCt.setTryOriginal(original);
				planCt.setSuccessTry(true);
				original.setTrySuccess(planCt);
			}else if(branch == 2){
				planCt.setTryOriginal(original);
				original.setTryFailure(planCt);
			}
			//planCt.setCardNumber(2);
			//planCt.setCardType(Const.RTRY);												
		}
		if(parent instanceof GoalContainer){
			((GoalContainer) parent).addMERealPlan(planCt);
			//if(depth == MAX_GOALS_DEPTH + 1)
			((GoalContainer) parent).createDecomposition(Const.AND);
			//else
				//((GoalContainer) parent).createDecomposition(Const.AND);
		}else{
			((PlanContainer) parent).createDecomposition(Const.AND);
			((PlanContainer) parent).addDecomp(planCt);
		}
		return planCt;
	}
	
	private void createActor() {
		FActor actor = formalFactory.createFActor();
		actor.setName(ACTOR_NAME);
		actor.setIdentificator("EvaluationActor");
		actor.setIsSystem(true);
		this.actor = actor;
	}
	
	
}
