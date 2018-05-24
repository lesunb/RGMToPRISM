package br.unb.cic.functional;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import br.unb.cic.rtgoretoprism.generator.goda.parser.CtxParser;
//import br.unb.cic.rtgoretoprism.generator.goda.parser.GoalParser;
import br.unb.cic.rtgoretoprism.generator.goda.parser.RTParser;
//import br.unb.cic.rtgoretoprism.generator.goda.parser.TaskParser;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.ctx.ContextCondition;
import br.unb.cic.rtgoretoprism.model.ctx.CtxSymbols;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FActor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FPlan;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FormalcoreFactory;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.impl.FormalcoreFactoryImpl;

public class CRGMTestProducer{

	private FormalcoreFactory formalFactory;
	private FActor actor;
	public AgentDefinition agentDefinition;
	private PlanContainer original = null;

	private String ACTOR_NAME;
	private int MAX_DEPTH;
	private int MAX_GOALS_DEPTH;
	private int MAX_BRANCH;

	private int timePath = 0;
	private int timeSlot = 0;
	private int index = 0;

	/** memory for the parsed RT regexes */
	Map<String, Boolean[]> rtSortedGoals;
	Map<String, Object[]> rtCardGoals;
	Map<String, Set<String>> rtAltGoals;
	Map<String, String[]> rtTryGoals;
	Map<String, Boolean> rtOptGoals;

	private Map<String, String> ctxVars;

	/** Prepare CRGM model */

	{
		formalFactory = FormalcoreFactoryImpl.init();
	}

	public CRGMTestProducer(int maxDepth, int maxGoalsDepth, int maxBranch, String actorName){

		this.ACTOR_NAME = actorName;

		this.MAX_DEPTH = maxDepth;
		this.MAX_GOALS_DEPTH = maxGoalsDepth;
		this.MAX_BRANCH = maxBranch;

		this.rtSortedGoals = new TreeMap<String, Boolean[]>();
		this.rtCardGoals = new TreeMap<String, Object[]>();
		this.rtAltGoals = new TreeMap<String, Set<String>>();
		this.rtTryGoals = new TreeMap<String, String[]>();
		this.rtOptGoals = new TreeMap<String, Boolean>();

		this.ctxVars = new TreeMap<String, String>();
	}


	public AgentDefinition generateCRGM(String goalId, InformationRegister[] info, String context) throws IOException {

		createActor();
		agentDefinition = new AgentDefinition(actor);
		RTContainer root = createGoal(null,0,0,goalId, context);
		agentDefinition.addRootGoal((GoalContainer)root);

		if(info != null){
			addElement(root, info, 0);
		}
		return agentDefinition;
	}

	private void addElement(RTContainer parent, InformationRegister[] info, int currentDepth) throws IOException {

		if(index == info.length)
			return;

		if(info[index].depth == MAX_DEPTH)
			return;

		currentDepth++;
		for(int branch = 0; branch < MAX_BRANCH; branch++){

			if(index != info.length && currentDepth == info[index].depth){
				RTContainer nextParent = createElement(parent, info[index].depth, info[index].branch, info[index].id);
				index++;
				addElement(nextParent, info, currentDepth);
			}else{
				return;
			}
		}
	}

	private RTContainer createElement(RTContainer parent, int depth, int branch, String id) throws IOException {
		if(depth < MAX_GOALS_DEPTH){
			return createGoal(parent, depth, branch, id, null);
		}else{
			return createPlan(parent, id, depth, branch, null);
		}
	}

	private GoalContainer createGoal(RTContainer parent, int depth, int branch, String id, String context) {
		
		/** Check branch with_functional_test to insert the GoalParser class */
		
		/*try {
			GoalParser.parseRegex(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		FHardGoal goal = formalFactory.createFHardGoal();
		goal.setActor(actor);
		goal.setName(id);
		goal.setIdentificator(AgentDefinition.parseElId(id));
		GoalContainer goalCt = agentDefinition.createGoal(goal, Const.ACHIEVE);
		goalCt.setIncluded(true);
		goalCt.setCardNumber(0);
		goalCt.setCardType(Const.SEQ);

		goalCt.setTimeSlot(timeSlot);
		goalCt.setTimePath(timePath);
		goalCt.addFulfillmentConditions(context);
		if(parent != null){
			((GoalContainer) parent).createDecomposition(Const.AND);
			((GoalContainer) parent).addDecomp(goalCt);
		}
		return goalCt;
	}

	@SuppressWarnings("unused")
	public PlanContainer createPlan(RTContainer parent, String id, int depth, int branch, String context) {
		
		/** Check branch with_functional_test to insert the TaskParser class */
		
		/*try {
			TaskParser.parseRegex(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		FPlan plan = formalFactory.createFPlan();
		plan.setActor(actor);
		plan.setName(id);
		plan.setIdentificator(AgentDefinition.parseElId(id));
		PlanContainer planCt = agentDefinition.createPlan(plan);

		planCt.setIncluded(true);
		planCt.setCardNumber(0);
		planCt.setCardType(Const.SEQ);
		planCt.addFulfillmentConditions(context);

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
		}
		if(parent instanceof GoalContainer){
			((GoalContainer) parent).addMERealPlan(planCt);
			((GoalContainer) parent).createDecomposition(Const.AND);
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


	/** Run GODA's elements' labels analysis
	 * @throws Exception */
	public void run(AgentDefinition ad) throws Exception{

		System.out.println("Starting GODA execution.");

		long startTime = new Date().getTime();

		Actor a = this.actor;
		if(a != null){
			if(!a.isIsSystem()){
				throw new Exception();
			}

			//analyze all root goals
			for(GoalContainer rootgoal : ad.getRootGoalList()){
				for(PlanContainer plan : rootgoal.getDecompPlans())
					addPlan(plan, ad);
				addGoal(rootgoal, ad);
			}
		}

		System.out.println("Finished GODA execution in " + (new Date().getTime() - startTime) + "ms. ");
	}

	@SuppressWarnings("unchecked")
	private void addGoal(GoalContainer gc, final AgentDefinition ad) throws Exception {

		//Analyze labels
		String rtRegex = gc.getRtRegex();
		storeRegexResults(gc.getUid(), rtRegex, gc.getDecomposition());

		//Analyze context annotation
		if(!gc.getFulfillmentConditions().isEmpty()){
			for(String ctxCondition : gc.getFulfillmentConditions()){

				Object [] parsedCtxs = CtxParser.parseRegex(ctxCondition);
				List<ContextCondition> ctxConditions = (List<ContextCondition>)parsedCtxs[0];
				addCtxVar(ctxConditions);
			}
		}

		if(gc.getDecomposition() == Const.NONE || gc.getDecomposition() == Const.ME){
			return;
		}else{
			for(GoalContainer g : gc.getDecompGoals())
				addGoal(g,ad);
		}
	}

	@SuppressWarnings("unchecked")
	private void addPlan(PlanContainer pc, final AgentDefinition ad) throws Exception {

		//Analyze labels
		storeRegexResults(pc.getUid(), pc.getRtRegex(), pc.getDecomposition());

		//Analyze context annotation
		if(!pc.getFulfillmentConditions().isEmpty()){
			for(String ctxCondition : pc.getFulfillmentConditions()){
				Object [] parsedCtxs = CtxParser.parseRegex(ctxCondition);
				List<ContextCondition> ctxConditions = (List<ContextCondition>)parsedCtxs[0];
				addCtxVar(ctxConditions);
			}
		}

		if(pc.getDecomposition() == Const.NONE){
			return;
		}else{
			for(PlanContainer plan : pc.getDecompPlans())
				addPlan(plan,ad);
		}
	}

	@SuppressWarnings("unchecked")
	private void storeRegexResults(String uid, String rtRegex, Const decType) throws IOException {
		if(rtRegex != null){
			Object [] res = RTParser.parseRegex(uid, rtRegex + '\n', decType);
			rtSortedGoals.putAll((Map<String, Boolean[]>) res [0]);
			rtCardGoals.putAll((Map<String, Object[]>) res [1]);
			rtAltGoals.putAll((Map<String, Set<String>>) res [2]);
			rtTryGoals.putAll((Map<String, String[]>) res [3]);
			rtOptGoals.putAll((Map<String, Boolean>) res[4]);
		}
	}

	private void addCtxVar(List<ContextCondition> ctxs) throws Exception {
		for(ContextCondition ctxCondition : ctxs)
			ctxVars.put(ctxCondition.getVar(), ctxCondition.getOp() == CtxSymbols.BOOL ? "bool" : "double");
	}
}
