package br.unb.cic.rtgoretoprism.generator.goda.producer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.parser.RTParser;
import br.unb.cic.rtgoretoprism.generator.goda.writer.ManageWriter;
import br.unb.cic.rtgoretoprism.generator.goda.writer.ParamWriter;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.paramwrapper.ParamWrapper;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import br.unb.cic.rtgoretoprism.util.PathLocation;
import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;

public class PARAMProducer {
	
	private String sourceFolder;
	private String targetFolder;
	private String toolsFolder;
	private Set<Actor> allActors;
	private Set<FHardGoal> allGoals;
	
	private String agentName;
	private List<String> leaves = new ArrayList<String>();

	public PARAMProducer(Set<Actor> allActors, Set<FHardGoal> allGoals, String in, String out, String tools) {
		
		this.sourceFolder = in;
		this.targetFolder = out;
		this.toolsFolder = tools;
		this.allActors = allActors;
		this.allGoals = allGoals;
	}
	
	public void run() throws CodeGenerationException, IOException {
		
		long startTime = new Date().getTime();

		for(Actor actor : allActors){

			RTGoreProducer producer = new RTGoreProducer(allActors, allGoals, sourceFolder, targetFolder, false);
			AgentDefinition ad = producer.run();
			
			agentName = ad.getAgentName();
			
			ATCConsole.println("Generating PARAM formulas for: " + agentName);
			
			//Generate pctl formula
			generatePctlFormula();
			
			// Run article algorithm
			String nodeForm = composeNodeForm(ad.rootlist.getFirst(), null);
			
			//Print formula
			printFormula(nodeForm);
		}
		ATCConsole.println( "PARAM formulas created in " + (new Date().getTime() - startTime) + "ms.");
	}

	private void generatePctlFormula() throws IOException {

		//agentName = actor.getName().replaceAll("\n", "_");			    						
		StringBuilder pctl = new StringBuilder("P=? [ true U (");
		StringBuilder goals = new StringBuilder();
		int i = 0;
		
		for(FHardGoal goal : allGoals){
			pctl.append(AgentDefinition.parseElId(goal.getName()) + (i < allGoals.size() - 1 ? "&" : ""));
			goals.append(AgentDefinition.parseElId(goal.getName()));
			i++;
		}
		
		pctl.append(") ]");
		
		FileUtility.deleteFile(targetFolder + "/AgentRole_" + agentName + "/reachability.pctl", false);
		FileUtility.writeFile(pctl.toString(), targetFolder + "/AgentRole_" + agentName + "/reachability.pctl");
	}

	private void printFormula(String nodeForm) throws CodeGenerationException {
		
		nodeForm = composeFormula(nodeForm);
		
		String output = targetFolder + "/" + PathLocation.BASIC_AGENT_PACKAGE_PREFIX + agentName + "/";
		
		PrintWriter generalFormula = ManageWriter.createFile("result.out", output);
		
		ManageWriter.printModel(generalFormula, nodeForm);
	}

	private String composeFormula(String nodeForm) throws CodeGenerationException {
		
		String paramInputFolder = sourceFolder + "/PARAM/";

		String body = ManageWriter.readFileAsString(paramInputFolder + "formulabody.param");
		
		for (String leaf : this.leaves) {
			body = body + leaf + (leaf.equals(leaves.get(leaves.size()-1))? "]\n[" : ", ");
		}
		
		for (String leaf : this.leaves) {
			body = body + "[0,1]" + (leaf.equals(leaves.get(leaves.size()-1))? "]\n" : " ");
		}
		
		body = body + "  1*" + nodeForm;
		
		return body;
	}

	private String composeNodeForm(GoalContainer rootGoal, PlanContainer rootPlan) throws IOException, CodeGenerationException {
		
		Const decType;
		String rtAnnot;
		String nodeForm;
		String nodeId;
		LinkedList<GoalContainer> decompGoal = new LinkedList<GoalContainer>();
		LinkedList<PlanContainer> decompPlans = new LinkedList<PlanContainer>();
		
		if (rootGoal != null) {
			nodeId = rootGoal.getClearUId();
			decompGoal = rootGoal.getDecompGoals();
			decompPlans = rootGoal.getDecompPlans();
			decType = rootGoal.getDecomposition();
			rtAnnot = rootGoal.getRtRegex();
		} 
		else {
			nodeId = rootPlan.getClearElId();
			decompGoal = rootPlan.getDecompGoals();
			decompPlans = rootPlan.getDecompPlans();
			decType = rootPlan.getDecomposition();
			rtAnnot = rootPlan.getRtRegex();	
		}
		
		nodeForm = getNodeForm(decType, rtAnnot, nodeId);
		
		/*Run for sub goals*/
		for (GoalContainer subNode : decompGoal) {
			String subNodeId = subNode.getClearUId();
			String subNodeForm = composeNodeForm(subNode, null);
			nodeForm = replaceSubForm(nodeForm, subNodeForm, nodeId, subNodeId);
		}
		
		/*Run for sub tasks*/
		for (PlanContainer subNode : decompPlans) {
			String subNodeId = subNode.getClearElId();
			String subNodeForm = composeNodeForm(null, subNode);
			nodeForm = replaceSubForm(nodeForm, subNodeForm, nodeId, subNodeId);
		}
		
		/*If leaf task*/
		if ((decompGoal.size() == 0) && (decompPlans.size() == 0)) {
			
			String planName = rootPlan.getClearElId();
			this.leaves.add(planName);
			
			//Create DTMC model (param)
			ParamWriter writer = new ParamWriter(sourceFolder, targetFolder, agentName, planName);
			writer.writeModel();
			
			//Call to param
			ParamWrapper paramWrapper = new ParamWrapper(targetFolder, toolsFolder, agentName,  planName);
			paramWrapper.getReliability(writer.getModel());
			
			//Delete DTMC model
			writer.deleteModel();
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
