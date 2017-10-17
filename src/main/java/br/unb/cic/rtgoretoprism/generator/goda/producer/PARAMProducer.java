package br.unb.cic.rtgoretoprism.generator.goda.producer;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.parser.RTParser;
import br.unb.cic.rtgoretoprism.generator.goda.writer.ParamWriter;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.paramwrapper.ParamWrapper;
import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;

public class PARAMProducer {
	
	private String sourceFolder;
	private String targetFolder;
	private String toolsFolder;
	private Set<Actor> allActors;
	private Set<FHardGoal> allGoals;
	
	private String agentName;

	public PARAMProducer(Set<Actor> allActors, Set<FHardGoal> allGoals, String in, String out, String tools) {
		
		this.sourceFolder = in;
		this.targetFolder = out;
		this.toolsFolder = tools;
		this.allActors = allActors;
		this.allGoals = allGoals;
	}
	
	public void run() throws CodeGenerationException, IOException {
		
		ATCConsole.println("Generating PARAM formulas" );
		
		long startTime = new Date().getTime();

		for(Actor actor : allActors){

			ATCConsole.println("Generating PARAM formulas for: " + actor.getName());
			
			RTGoreProducer producer = new RTGoreProducer(allActors, allGoals, sourceFolder, targetFolder, false);
			AgentDefinition ad = producer.run();
			
			agentName = ad.getAgentName();
			
			/* Run article algorithm */
			composeNodeForm(ad.rootlist.getFirst(), null);
		}
		ATCConsole.println( "PARAM formula created in " + (new Date().getTime() - startTime) + "ms.");
	}

	private String composeNodeForm(GoalContainer rootGoal, PlanContainer rootPlan) throws IOException, CodeGenerationException {
		
		Const decType;
		String rtAnnot;
		String nodeForm;
		String nodeId;
		
		LinkedList<GoalContainer> decompGoal = new LinkedList<GoalContainer>();
		LinkedList<PlanContainer> decompPlans = new LinkedList<PlanContainer>();
		
		
		if (rootGoal != null) {
			nodeId = rootGoal.getUid();
			decompGoal = rootGoal.getDecompGoals();
			decompPlans = rootGoal.getDecompPlans();
			decType = rootGoal.getDecomposition();
			rtAnnot = rootGoal.getRtRegex();
		} 
		else {
			nodeId = rootPlan.getElId();
			decompGoal = rootPlan.getDecompGoals();
			decompPlans = rootPlan.getDecompPlans();
			decType = rootPlan.getDecomposition();
			rtAnnot = rootPlan.getRtRegex();
		}
		
		nodeForm = getNodeForm(decType, rtAnnot, nodeId);
		
		/*Run for sub goals*/
		for (GoalContainer subNode : decompGoal) {
			String subNodeId = subNode.getUid();
			String subNodeForm = composeNodeForm(subNode, null);
			nodeForm = replaceSubForm(nodeForm, subNodeForm, nodeId, subNodeId);

		}
		
		/*Run for sub tasks*/
		for (PlanContainer subNode : decompPlans) {
			String subNodeId = subNode.getElId();
			String subNodeForm = composeNodeForm(null, subNode);
			nodeForm = replaceSubForm(nodeForm, subNodeForm, nodeId, subNodeId);
		}
		
		/*If leaf task*/
		if ((decompGoal.size() == 0) && (decompPlans.size() == 0)) {
			String planName = rootPlan.getClearElId();
			
			ParamWriter writer = new ParamWriter(sourceFolder, targetFolder, agentName, planName);
			writer.writeModel();
			
			//Chama o PARAM
			ParamWrapper paramWrapper = new ParamWrapper(targetFolder, toolsFolder, agentName,  planName);
			paramWrapper.getReliability(writer.getModel());

		}

		return nodeForm;
	}

	private String replaceSubForm(String nodeForm, String subNodeForm, String nodeId, String subNodeId) {
		
		if (nodeForm.equals(nodeId)) {
			nodeForm = subNodeForm;
		}
		else {
			nodeForm = nodeForm.replaceAll(subNodeId, subNodeForm);
		}
		
		return nodeForm;
	}

	private String getNodeForm(Const decType, String rtAnnot, String uid) throws IOException {

		if (rtAnnot == null) {
			return uid;
		}
		
		Object [] res = RTParser.parseRegex(uid, rtAnnot + '\n', decType);
		return (String) res[5];
	}
}
