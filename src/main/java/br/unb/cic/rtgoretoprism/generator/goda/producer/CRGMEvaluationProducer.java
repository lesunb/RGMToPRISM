package br.unb.cic.rtgoretoprism.generator.goda.producer;

import java.util.LinkedList;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FActor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FPlan;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FormalcoreFactory;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.impl.FormalcoreFactoryImpl;

public class CRGMEvaluationProducer {
	
	private int MAX_TASKS;
	private Const dec;
	private Const timeRule;
	private Const mandRule;
	
	private String ACTOR_NAME;
	
	//private int RETRY_MOD = 3;
	
	private FormalcoreFactory formalFactory;
	private FActor actor;
	public AgentDefinition agentDefinition;
	private int timePath = 0;
	private int timeSlot = 0;
	
	{
		formalFactory = FormalcoreFactoryImpl.init();
	}
	
	public static void main(String [] args){
		CRGMEvaluationProducer producer = new CRGMEvaluationProducer(8, Const.AND, Const.SEQ, Const.NONE, "EvaluationActor");
		producer.generateCRGM();
		System.out.println(producer.agentDefinition.getGoalBase().size());
		System.out.println(producer.agentDefinition.planbase.size());		
	}
	
	public CRGMEvaluationProducer(int maxTasks, Const dec, Const timeRule, Const mandRule, String actorName){
		this.MAX_TASKS = maxTasks;
		this.ACTOR_NAME = actorName;
		this.dec = dec;
		this.timeRule = timeRule;
		this.mandRule = mandRule;
	}
	
	public AgentDefinition generateCRGM(){
		createActor();
		agentDefinition = new AgentDefinition(actor);
		RTContainer root = createGoal(null, 0, 0, "");
		agentDefinition.addRootGoal((GoalContainer)root);
		for(int i = 0; i < MAX_TASKS; i++)
			addRuleElements(root, i + "");
		return agentDefinition;
	}
	
	public void addRuleElements(RTContainer parent, String id){
		
		PlanContainer p1, p2, p3;
		switch(mandRule){				
			case OPT:
				p1 = createPlan(parent, dec, timeRule, Const.OPT, id + "1");
				p1.setOptional(true);
				break;
			
			case XOR: 
				p1 = createPlan(parent, dec, timeRule, Const.XOR, id + "1");
				p2 = createPlan(parent, dec, timeRule, Const.XOR, id + "2");
				p3 = createPlan(parent, dec, timeRule, Const.XOR, id + "3");
				p1.getAlternatives().put(p1, new LinkedList<RTContainer>());				
				p1.getAlternatives().get(p1).add(p2);
				p1.getAlternatives().get(p1).add(p3);
				p2.getFirstAlternatives().add(p1);
				p3.getFirstAlternatives().add(p1);
				break;

			case TRY: 
				p1 = createPlan(parent, dec, timeRule, Const.TRY, id + "1");			
				p2 = createPlan(parent, dec, timeRule, Const.TRY_S, id + "2");
				p3 = createPlan(parent, dec, timeRule, Const.TRY_F, id + "3");
				p1.setTrySuccess(p2);
				p1.setTryFailure(p3);
				p2.setSuccessTry(true);
				p2.setTryOriginal(p1);
				p3.setSuccessTry(false);
				p3.setTryOriginal(p1);				
				break;
		
			case RTRY: 
				p1 = createPlan(parent, dec, timeRule, Const.RTRY, id + "1");			
				break;
			
			default: 
				p1 = createPlan(parent, dec, timeRule, mandRule, id + "1");
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
	
	private PlanContainer createPlan(RTContainer parent, Const dec, Const timeType, Const mandType, String id) {
		FPlan plan = formalFactory.createFPlan();
		plan.setActor(actor);
		plan.setName("T" + id + ": Plan");
		plan.setIdentificator("T_"+id);
		PlanContainer planCt = agentDefinition.createPlan(plan);
		
		planCt.setIncluded(true);
		planCt.setCardNumber(mandType != Const.RTRY ? 0 : 3);
		planCt.setCardType(mandType != Const.RTRY ? timeType : mandType);

		if(timeType == Const.SEQ || timeSlot == 0)
			timeSlot++;
		
		planCt.setPrevTimePath(timePath);
		planCt.setTimePath(timePath);
		planCt.setTimeSlot(timeSlot);		
		
		//Custom
		planCt.setOptional(mandType == Const.OPT);

		if(parent instanceof GoalContainer){
			((GoalContainer) parent).addMERealPlan(planCt);
			//if(depth == MAX_GOALS_DEPTH + 1)
			((GoalContainer) parent).createDecomposition(dec);
			//else
				//((GoalContainer) parent).createDecomposition(Const.AND);
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
