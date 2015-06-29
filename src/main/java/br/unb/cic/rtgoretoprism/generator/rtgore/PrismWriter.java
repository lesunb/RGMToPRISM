/**
 * <copyright>
 *
 * TAOM4E - Tool for Agent Oriented Modeling for the Eclipse Platform
 * Copyright (C) ITC-IRST, Trento, Italy
 * Author: Mirko Morandini
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * The electronic copy of the license can be found here:
 * http://sra.itc.it/tools/taom/freesoftware/gpl.txt
 *
 * The contact information:
 * e-mail: taom4e@itc.it
 * site: http://sra.itc.it/tools/taom4e/
 *
 * </copyright>
 */

package br.unb.cic.rtgoretoprism.generator.rtgore;

import it.itc.sra.taom4e.model.core.informalcore.Plan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import br.unb.cic.rtgoretoprism.util.PathLocation;

/**
 * Writes the agent from the internal representation
 * 
 * @author Mirko Morandini
 * @author bertolini (comments, reorganizetion)
 */
public class PrismWriter {
	/** the set of placeholder founded into template files that are 
	 * substituted with the proper values during the code generation
	 * process. */
	private static final String UTIL_PACKAGE_TAG		= "$UTIL_PACKAGE";
	private static final String CAPABILITY_AGENT_TAG	= "$CAPABILITY_AGENT";
	
	
	private static final String MODULE_NAME_TAG			= "$MODULE_NAME$";
	private static final String NO_ERROR_TAG			= "$NO_ERROR$";
	private static final String TIME_SLOT_TAG			= "$TIME_SLOT";
	private static final String PREV_TIME_SLOT_TAG		= "$PREV_TIME_SLOT";
	private static final String GID_TAG					= "$GID$";
	private static final String GUID_TAG					= "$GUID$";
	private static final String PREV_GID_TAG			= "$PREV_GID$";
	private static final String GOAL_MODULES_TAG 		= "$GOAL_MODULES$";
	private static final String SKIPPED_TAG				= "$SKIPPED$";
	private static final String NOT_SKIPPED_TAG			= "$NOT_SKIPPED$";
	private static final String XOR_GIDS_TAG 			= "$XOR_GIDS$";
	private static final String XOR_VALUE_TAG	 		= "$XOR_VALUE$";
	private static final String DEC_HEADER_TAG	 		= "$DEC_HEADER$";
	private static final String DEC_TYPE_TAG	 		= "$DEC_TYPE$";
	private static final String CARD_TYPE_TAG	 		= "$CARD_TYPE$";
	private static final String MAX_RETRIES_TAG	 		= "$MAX_RETRIES$";
	private static final String CARD_N_TAG		 		= "$CARD_N$";
	
	private static final String CTX_CONDITION_TAG		= "$CTX_CONDITION$";
	private static final String CTX_EFFECT_TAG			= "$CTX_EFFECT$";
	
	private static final String CONST_PARAM_TAG			= "$CONST_PARAM$";
	private static final String CONST_PARAM_VAL			= "param";
	
	private static final String PARAMS_BASH_TAG	 		= "$PARAMS_BASH$";
	private static final String REPLACE_BASH_TAG	 	= "$REPLACE_BASH$";
	
	/** where to find BDI related template plan section, inside the templated folder */
	private final String TEMPLATE_PLAN_PATH = "planskeletons/";
	/** where to find BDI related template util section, inside the templated folder */
	private final String TEMPLATE_UTIL_PATH = "util/";
	/** where to find BDI related template base section, inside the template folder */
	private final String TEMPLATE_KL_BASE_PATH = "KL/";
	/** where to find PRISM related template base section, inside the template folder */
	private final String TEMPLATE_PRISM_BASE_PATH = "PRISM/";
	/** where to find CL related template base section, inside the template folder */
	private final String TEMPLATE_CL_BASE_PATH = "CL/";
	
	/** template input base folder */
	private String templateInputBaseFolder;
	/** template input KL folder */
	private String inputKLFolder;
	/** template input PRISM folder */
	private String inputPRISMFolder;
	/** generated agent target folder */
	private String agentOutputFolder;
	/** the folder that will contain all the generated agent */
	private String basicOutputFolder;
	/** the base package for the current Agent */
	private String basicAgentPackage;
	
	// Strings that contain the parts of the ADF skeleton, read from file
	private String header, body, footer, evalBash;

	// Strings filled with content, to replace the placeholders in the adf skeleton.
	//Note: they are used whith concat() function

	
	private String noErrorFormula = "";
	private StringBuilder planModules = new StringBuilder();
	private String evalFormulaParams = "";
	private String evalFormulaReplace = "";
	
	/** PRISM patterns */
	private String leafGoalPattern;
	private String andDecPattern;
	private String xorDecPattern;
	private String xorDecHeaderPattern;
	private String xorSkippedPattern;
	private String xorNotSkippedPattern;
	private String seqRenamePattern;
	private String trySDecPattern;
	private String tryFDecPattern;
	private String optDecPattern;
	private String optHeaderPattern;
	private String seqCardPattern;
	private String intlCardPattern;
	private String ctxGoalPattern;
	private String ctxTaskPattern;
	
	/** Has all the informations about the agent. */ 
	private AgentDefinition ad;
	/** the list of plan that are root for a capability of the selected agent */
	private List<Plan> capabilityPlanList;
	
	private Map<String, String> ctxVars;

	/**
	 * Creates a new AgentWriter instance
	 * 
	 * @param ad the source agentDefinition object from which data should be extracted
	 * @param inputFolder template input folder 
	 * @param outputFolder generated code target folder
	 */
	public PrismWriter(AgentDefinition ad, List<Plan> capPlan, String input, String output ) {
		this.ad = ad;
		this.capabilityPlanList = capPlan;
		this.templateInputBaseFolder = input + "/"; 
		this.inputKLFolder = templateInputBaseFolder + TEMPLATE_KL_BASE_PATH;
		this.inputPRISMFolder = templateInputBaseFolder + TEMPLATE_PRISM_BASE_PATH;
		this.basicOutputFolder = output + "/";
		this.agentOutputFolder = 
			basicOutputFolder + PathLocation.BASIC_AGENT_PACKAGE_PREFIX + ad.getAgentName() + "/";
		
		//the package that generated bdi .java files will be put in
		this.basicAgentPackage = PathLocation.BASIC_AGENT_PACKAGE_PREFIX + ad.getAgentName();
		
		this.ctxVars = new TreeMap<String, String>();
	}

	/**
	 * Writes the whole Agent (ADF + Java plan bodies).
	 * 
	 * @throws CodeGenerationException 
	 * @throws IOException 
	 */
	public void writeModel() throws CodeGenerationException, IOException {
		String utilPkgName = basicAgentPackage + PathLocation.UTIL_KL_PKG;
		
		//String planInputFolder = inputKLFolder + TEMPLATE_PLAN_PATH;
		//String utilInputFolder = inputKLFolder + TEMPLATE_UTIL_PATH;
		String prismInputFolder = inputPRISMFolder;
		
		String planOutputFolder = agentOutputFolder + "plans" + "/";
		String planPkgName = basicAgentPackage + ".plans";
		
		//read some of the used template
		header = readFileAsString( prismInputFolder + "modelheader.pm" );
		body = readFileAsString( prismInputFolder + "modelbody.pm" );
		evalBash = readFileAsString( prismInputFolder + "eval_formula.sh" );
		//footer = readFileAsString( planInputFolder + "agentfooter.xml" );

		//update some template placeholder
		//header = header.replace( NAME_TAG, ad.getAgentName() );
		//header = header.replace( PACKAGE_TAG, basicAgentPackage );
		//header = header.replace( UTIL_PACKAGE_TAG, utilPkgName );
		//header = header.replace( BDI_PLAN_PACKAGE_TAG, planPkgName );
		
		
		//create the agent output dir
		writeAnOutputDir( agentOutputFolder );
		//create the agent plan output dir
		//writeAnOutputDir( planOutputFolder );
		//create the output ADF XML file */
		PrintWriter modelFile = createPrismFile( ad.getAgentName(), agentOutputFolder );
		PrintWriter evalBashFile = createBashFile( "eval_formula", agentOutputFolder );
		//Reads all softgoals from the softgoals list and writes them into the belief base.
		//writeBBSoftGoals( ad.softgoalbase );
		//Writes all goals to the ADF file
		writePrismModel( prismInputFolder, ad.rootlist, planOutputFolder, basicAgentPackage, utilPkgName, planPkgName );		
		//Writes the plan contributions and the bodies of the real plans
		//writePlans( planInputFolder, planOutputFolder, ad.planbase, basicAgentPackage, utilPkgName, planPkgName, ad.getAgentName() );
		//Copies to the output directory all the files where only the package-name changes.
		//writeDefaultJavaFiles( planInputFolder, planOutputFolder, basicAgentPackage, utilPkgName, planPkgName );
		//replaces all placeholders in the ADF skeleton and writes the ADF file.
		printModel( modelFile );
		printEvalBash( evalBashFile );
		//Writes the batch files (Windows) to start the agent.
		//writeAgentStartingFile( basicOutputFolder, ad.getAgentName(), basicAgentPackage );
		//Writes the batch files (Windows) to compile the agent.
		//writeAgentCompileFile( agentOutputFolder );
		//Write the platform starting file
		//writePlatformStartingFile( basicOutputFolder );
		//copy some of the input template dir that are used by the running BDI agent 
		//writeUtilDir( utilInputFolder, agentOutputFolder, utilPkgName );
		//create the skeleton part of the CL elements (the capability agent and related utils)
		//writeCapabilityCLSkeleton( ad.getAgentName(), capabilityPlanList, agentOutputFolder, templateInputBaseFolder );
	}

	/**
	 * Writes all goals to the ADF file (to beliefbase, goals and plans section) and organizes
	 * (copies) the plan bodies. Works not recursive on the goal structure, but processes all goals
	 * in the list in sequence.
	 * 
	 * @param input the template input folder
	 * @param gb beliefe base goal
	 * @param planOutputFolder
	 * @param pkgName
	 * @param utilPkgName
	 * @param planPkgName 
	 * 
	 * @throws CodeGenerationException 
	 * @throws IOException 
	 */
	private void writePrismModel( String input, LinkedList<GoalContainer> rootGoals, 
			String planOutputFolder, String pkgName, String utilPkgName, String planPkgName ) throws CodeGenerationException, IOException {

		leafGoalPattern 				= readFileAsString(input + "pattern_leafgoal.pm");
		andDecPattern 					= readFileAsString(input + "pattern_and.pm");
		xorDecPattern 					= readFileAsString(input + "pattern_xor.pm");
		xorDecHeaderPattern 			= readFileAsString(input + "pattern_xor_header.pm");
		xorSkippedPattern	 			= readFileAsString(input + "pattern_skip_xor.pm");
		xorNotSkippedPattern	 		= readFileAsString(input + "pattern_skip_not_xor.pm");
		seqRenamePattern				= readFileAsString(input + "pattern_seq_rename.pm");
		trySDecPattern	 				= readFileAsString(input + "pattern_try_success.pm");
		tryFDecPattern	 				= readFileAsString(input + "pattern_try_fail.pm");
		optDecPattern 					= readFileAsString(input + "pattern_opt.pm");
		optHeaderPattern	 			= readFileAsString(input + "pattern_opt_header.pm");
		seqCardPattern	 				= readFileAsString(input + "pattern_card_seq.pm");
		intlCardPattern	 				= readFileAsString(input + "pattern_card_retry.pm");//TODO: create retry in a separate pattern
		ctxGoalPattern	 				= readFileAsString(input + "pattern_ctx_goal.pm");
		ctxTaskPattern	 				= readFileAsString(input + "pattern_ctx_task.pm");
		Collections.sort(rootGoals);
		
		for( GoalContainer root : rootGoals ) {
			writeElement(
							root, 
							leafGoalPattern,							
							seqCardPattern,
							intlCardPattern,
							andDecPattern, 
							xorDecPattern, 
							xorDecHeaderPattern, 
							trySDecPattern, 
							tryFDecPattern,
							optDecPattern,
							optHeaderPattern,
							ctxGoalPattern,
							ctxTaskPattern,
							null);
			
			StringBuilder sbCtxVars = new StringBuilder();
			for(String ctx : ctxVars.keySet())
				sbCtxVars.append(CONST_PARAM_VAL + " " + ctxVars.get(ctx) + " " + ctx + ";\n");
			planModules = planModules.append(sbCtxVars.toString());
		}
		
		//System.out.println(planModules);
	}


	
	/**
	 * Writes the dispatch plans (with bodies) for every child goal
	 * 
	 * @param goal
	 * @param pattern
	 * @throws IOException 
	 */
	private String[] writeElement(
							 RTContainer root, 
							 String pattern, 
							 String seqCardPattern,
							 String intlCardPattern,
							 String andPattern, 
							 String xorPattern, 
							 String xorHeader, 
							 String trySPattern, 
							 String tryFPattern,
							 String optPattern,
							 String optHeader,
							 String ctxGoalPattern,
							 String ctxTaskPattern,
							 String prevFormula) throws IOException {
		
		String operator = root.getDecomposition() == Const.AND ? " & " : " | ";
		if(!root.getDecompGoals().isEmpty()){
			StringBuilder goalFormula = new StringBuilder();
			String prevGoalFormula = prevFormula;
			int prevTimeSlot = root.getDecompGoals().get(0).getRootTimeSlot();
			for(GoalContainer gc : root.getDecompGoals()){
				String currentFormula;
				if(prevTimeSlot < gc.getRootTimeSlot())
					currentFormula = prevGoalFormula;
				else
					currentFormula = prevFormula;
				writeElement(gc, pattern, seqCardPattern, intlCardPattern, andPattern, xorPattern, xorHeader, trySPattern, tryFPattern, optPattern, optHeader, ctxGoalPattern, ctxTaskPattern, currentFormula);												
				if(gc.isIncluded())
					prevGoalFormula = gc.getClearUId();
				if(prevGoalFormula != null)
					goalFormula.append(prevGoalFormula + operator);							
			}
			if(prevGoalFormula != null)
				goalFormula.replace(goalFormula.lastIndexOf(operator), goalFormula.length(), "");
			if(root.isIncluded())
				planModules = planModules.append("\nformula " + root.getClearUId() + " = " + goalFormula + ";\n");
			return new String [] {root.getClearUId(), goalFormula.toString()};
		}else if(!root.getDecompPlans().isEmpty()){
			StringBuilder taskFormula = new StringBuilder();
			String prevTaskFormula = prevFormula;
			for(PlanContainer pc : root.getDecompPlans()){
				String childFormula = writeElement(pc, pattern, seqCardPattern, intlCardPattern, andPattern, xorPattern, xorHeader, trySPattern, tryFPattern, optPattern, optHeader, ctxGoalPattern, ctxTaskPattern, prevTaskFormula)[1];
				//prevTaskFormula = pc.getClearUId();
				taskFormula.append("(" + childFormula + ")" + operator);
			}			
			taskFormula.replace(taskFormula.lastIndexOf(operator), taskFormula.length(), "");
			if(root instanceof GoalContainer)
				planModules = planModules.append("\nformula " + root.getClearUId() + " = " + taskFormula + ";\n");
			return new String [] {root.getClearUId(), taskFormula.toString()};
		}else if(root instanceof PlanContainer){
			return writePrismModule(root, pattern, seqCardPattern, intlCardPattern, andPattern, xorPattern, xorHeader, trySPattern, tryFPattern, optPattern, optHeader, ctxGoalPattern, ctxTaskPattern, prevFormula);
		}
		
		return new String[]{"",""};
	}
	
	@SuppressWarnings("unchecked")
	private String[] writePrismModule(
							 RTContainer root, 
							 String singlePattern,
							 String seqCardPattern,
							 String intlCardPattern,
							 String andPattern, 
							 String xorPattern, 
							 String xorHeader, 
							 String trySPattern, 
							 String tryFPattern,
							 String optPattern,
							 String optHeader,
							 String ctxGoalPattern,
							 String ctxTaskPattern,
							 String prevFormula) throws IOException{
		
		singlePattern = new String(singlePattern);
		seqCardPattern = new String(seqCardPattern);
		intlCardPattern = new String(intlCardPattern);
		andPattern = new String(andPattern);
		xorPattern = new String(xorPattern);
		xorHeader = new String(xorHeader);
		trySPattern = new String(trySPattern);
		tryFPattern = new String(tryFPattern);
		optPattern = new String(optPattern);
		
		PlanContainer plan = (PlanContainer) root;
		String planModule;
		StringBuilder planFormula = new StringBuilder();
					
		if(plan.getCardNumber() > 1){
			StringBuilder seqRenames = new StringBuilder();
			if(plan.getCardType() == Const.SEQ){
				for(int i = 2; i <= plan.getCardNumber(); i++){
					String seqRename = new String(seqRenamePattern);
					seqRename = seqRename.replace(CARD_N_TAG, i + "");
					seqRenames.append(seqRename);
				}
				seqCardPattern = seqCardPattern.replace("$SEQ_RENAMES$", seqRenames);
				planModule = seqCardPattern.replace(MODULE_NAME_TAG, plan.getClearElName());
			}else{
				for(int i = 2; i <= plan.getCardNumber(); i++){
					String seqRename = new String(seqRenamePattern);
					seqRename = seqRename.replace(CARD_N_TAG, i + "");
					seqRenames.append(seqRename);
				}
				intlCardPattern = intlCardPattern.replace("$SEQ_RENAMES$", seqRenames);
				planModule = intlCardPattern.replace(MODULE_NAME_TAG, plan.getClearElName());
			}
		}else
			planModule = singlePattern.replace(MODULE_NAME_TAG, plan.getClearElName());
		
		StringBuilder sbHeader = new StringBuilder();
		StringBuilder sbType = new StringBuilder();
		
		if((plan.getTryOriginal() != null || plan.getTrySuccess() != null || plan.getTryFailure() != null) ||
		   (!plan.getAlternatives().isEmpty() || !plan.getFirstAlternatives().isEmpty()) ||
		   (plan.isOptional())){			
			if(plan.getTryOriginal() != null || plan.getTrySuccess() != null || plan.getTryFailure() != null){
				if(plan.getTrySuccess() != null || plan.getTryFailure() != null){
					//Try					
					if(plan.getAlternatives().isEmpty() && plan.getFirstAlternatives().isEmpty())
						sbType.append(andPattern);
					appendTryToNoErrorFormula(plan);
					processPlanFormula(plan, planFormula, Const.TRY);
				}else if(plan.isSuccessTry()){
					//Try success
					PlanContainer tryPlan = (PlanContainer) plan.getTryOriginal();
					trySPattern = trySPattern.replace(PREV_GID_TAG, tryPlan.getClearUId());
					sbType.append(trySPattern);
					processPlanFormula(plan, planFormula, Const.TRY_S);
				}else{
					//Try fail
					PlanContainer tryPlan = (PlanContainer) plan.getTryOriginal();
					tryFPattern = tryFPattern.replace(PREV_GID_TAG, tryPlan.getClearUId());
					sbType.append(tryFPattern);
					processPlanFormula(plan, planFormula, Const.TRY_F);
				}	
			}
			if(!plan.getAlternatives().isEmpty() || !plan.getFirstAlternatives().isEmpty()){
				//Alternatives
				String xorNotSkippeds = new String();
				StringBuilder xorHeaders = new StringBuilder();
				String xorSkipped = new String(xorSkippedPattern);
				String xorNotSkipped = new String(xorNotSkippedPattern);
				if(false && CONST_PARAM_VAL.equals("param")){
					String xorVar = new String(xorHeader);
					evalFormulaParams += "XOR_" + plan.getClearUId() + "=\"0\";\n";
					evalFormulaReplace += " -e \"s/XOR_" + plan.getClearUId() + "/$XOR_" + plan.getClearUId() + "/g\"";
					xorHeaders.append(xorVar.replace(GID_TAG, plan.getClearUId()));
					sbHeader.append(xorHeaders);
					xorNotSkippeds = xorNotSkippeds.concat(xorNotSkipped.replace(GID_TAG, plan.getClearUId()) + "*");
				}else{				
					if(!plan.getAlternatives().isEmpty()){
						for(RTContainer altFirst : plan.getAlternatives().keySet()){
							String xorVar = new String(xorHeader);
							evalFormulaParams += "XOR_" + altFirst.getClearUId() + "=\"0\";\n";
							evalFormulaReplace += " -e \"s/XOR_" + altFirst.getClearUId() + "/$XOR_" + altFirst.getClearUId() + "/g\"";
							xorHeaders.append(xorVar.replace(GID_TAG, altFirst.getClearUId()));
							xorSkipped = xorSkipped.replace(GID_TAG, altFirst.getClearUId());
							xorNotSkipped = xorNotSkipped.replace(GID_TAG, altFirst.getClearUId());
							xorNotSkippeds = xorNotSkippeds.concat(xorNotSkipped + "*");
							LinkedList<RTContainer> alts = plan.getAlternatives().get(altFirst);
							for(RTContainer alt : alts){
								xorSkipped = new String(xorSkippedPattern);
								xorNotSkipped = new String(xorNotSkippedPattern);
								xorVar = new String(xorHeader);
								evalFormulaParams += "XOR_" + alt.getClearUId() + "=\"0\";\n";
								evalFormulaReplace += " -e \"s/XOR_" + alt.getClearUId() + "/$XOR_" + alt.getClearUId() + "/g\"";
								xorHeaders.append(xorVar.replace(GID_TAG, alt.getClearUId()));																		
								xorSkipped = xorSkipped.replace(GID_TAG, alt.getClearUId());											
								xorNotSkipped = xorNotSkipped.replace(GID_TAG, alt.getClearUId());
								//xorNotSkippeds = xorNotSkippeds.concat(xorSkipped + "*");
							}
							//appendAlternativesToNoErrorFormula(plan);
						}	
						sbHeader.append(xorHeaders);
					}				
					if(!plan.getFirstAlternatives().isEmpty()){
						for(int i = 0; i < plan.getFirstAlternatives().size(); i++){
							RTContainer firstAlt = plan.getFirstAlternatives().get(i);
							xorSkipped = new String(xorSkippedPattern);
							xorNotSkipped = new String(xorNotSkippedPattern);
							xorSkipped = xorSkipped.replace(GID_TAG, firstAlt.getClearUId());
							xorNotSkipped = xorNotSkipped.replace(GID_TAG, firstAlt.getClearUId());
							//xorNotSkippeds = xorNotSkippeds.concat(xorSkipped + "*");
							for(RTContainer alt : firstAlt.getAlternatives().get(firstAlt)){
								if(alt.equals(plan) || calcAltIndex(firstAlt.getAlternatives().get(firstAlt), plan) == 0){
									xorNotSkipped = new String(xorNotSkippedPattern);
									xorNotSkipped = xorNotSkipped.replace(GID_TAG, alt.getClearUId());
									xorNotSkippeds = xorNotSkippeds.concat(xorNotSkipped + "*");
								}else{								
									xorSkipped = new String(xorSkippedPattern);
									xorSkipped = xorSkipped.replace(GID_TAG, alt.getClearUId());
									//xorNotSkippeds = xorNotSkippeds.concat(xorSkipped + "*");
								}							
							}
						}
					}
				}
				processPlanFormula(plan, planFormula, Const.XOR);
				xorNotSkippeds = xorNotSkippeds.substring(0, xorNotSkippeds.lastIndexOf("*")).replaceAll("[\n]", "");
				xorPattern = xorPattern.replace(NOT_SKIPPED_TAG, xorNotSkippeds);
				sbType.append(xorPattern.replace(SKIPPED_TAG, "(1 - " + xorNotSkippeds + ")"));
			}
			if(plan.isOptional()){
				//Opt
				sbHeader.append(optHeader);
				sbType.append(optPattern);
				noErrorFormula += " & s" + plan.getClearUId() + " < 4";
				evalFormulaParams += "OPT_" + plan.getClearUId() + "=\"1\";\n";
				evalFormulaReplace += " -e \"s/OPT_" + plan.getClearUId() + "/$OPT_" + plan.getClearUId() + "/g\"";
				processPlanFormula(plan, planFormula,Const.OPT);
			}
		}else{
			//And/OR			
			sbType.append(andPattern + "\n\n");
			noErrorFormula += " & s" + plan.getClearUId() + " < 4";
			processPlanFormula(plan, planFormula, plan.getRoot().getDecomposition());
		}
		
		evalFormulaParams += "rTask" + plan.getClearUId() + "=\"0.999\";\n";
		evalFormulaReplace += " -e \"s/rTask" + plan.getClearUId() + "/$rTask" + plan.getClearUId() + "/g\"";		
		//Header
		planModule = planModule.replace(DEC_HEADER_TAG, sbHeader.toString() + "\n");
		//Type
		planModule = planModule.replace(DEC_TYPE_TAG, sbType.toString());
		//CONTEXT CONDITION
		if(plan.getCreationCondition() != null && CONST_PARAM_VAL.equals("const")){
			Object [] parsedCtxs = CtxParser.parseRegex(plan.getCreationCondition());
			addCtxVar((Set<String[]>)parsedCtxs[0]);
			planModule = planModule.replace(CTX_EFFECT_TAG, ctxTaskPattern);
			planModule = planModule.replace(CTX_CONDITION_TAG, "(" + parsedCtxs[1] + ")" + " &");
		}else{
			planModule = planModule.replace(CTX_EFFECT_TAG, "");
			planModule = planModule.replace(CTX_CONDITION_TAG, "");
		}
		//Prev Success Guard Condition
		planModule = planModule.replace("$PREV_SUCCESS$", buildPrevSuccessFormula(prevFormula, plan));
		//Time
		Integer prevTimePath = plan.getPrevTimePath();
		Integer timePath = plan.getTimePath();
		Integer timeSlot = plan.getTimeSlot();
		if(plan.getCardType().equals(Const.SEQ))
			timeSlot -= plan.getCardNumber() - 1; 
		for(int i = plan.getCardNumber(); i > 0; i--){
			planModule = planModule.replace(PREV_TIME_SLOT_TAG + (i > 1 ? "_N" + i : "") + "$", prevTimePath + "_" + (timeSlot - 1 + i) + "");
			planModule = planModule.replace(TIME_SLOT_TAG + (i > 1 ? "_N" + i : "") + "$", timePath + "_" + (timeSlot + i) + "");
		}
		//GID
		//planModule = planModule.replace(GID_TAG, plan.getClearUId());
		planModule = planModule.replace(GID_TAG, plan.getClearUId());
		//CONST OR PARAM
		planModule = planModule.replace(CONST_PARAM_TAG, CONST_PARAM_VAL);
		//MAX RETRIES
		planModule = planModule.replace(MAX_RETRIES_TAG, plan.getCardNumber() + "");				
		planModules = planModules.append(planModule);				
		return new String[]{plan.getClearUId(), planFormula.toString()};
	}
	
	private Integer calcAltIndex(LinkedList <? extends RTContainer> alts, RTContainer plan){
		for(RTContainer alt : alts){
			if(!alt.getDecompGoals().isEmpty() && calcAltIndex(alt.getDecompGoals(), plan) >= 0)
				return alts.indexOf(alt);
			if(!alt.getDecompPlans().isEmpty() && calcAltIndex(alt.getDecompPlans(), plan) >= 0)
				return alts.indexOf(alt);			
			return alts.indexOf(plan) + 1;
		}
		return alts.indexOf(plan);		
	}
	
	private void addCtxVar(Set<String[]> vars){
		for(String[] var : vars)
		ctxVars.put(var[0], var[1]);
	}
	
	private void processPlanFormula(PlanContainer plan, StringBuilder planFormula, Const decType){
		
		String op = planFormula.length() == 0 ? "" : " & ";
		switch(decType){
			case OR: planFormula.append(op + "(s" + plan.getClearUId() + "=2)");break;
			case AND: planFormula.append(op + "(s" + plan.getClearUId() + "=2)");break;
			case XOR: planFormula.append(op + buildXorSuccessFormula(plan));break;
			case TRY: planFormula.append(op + "(s" + plan.getClearUId() + "=2)");break;
			case TRY_S: planFormula.append(op + "(s" + plan.getClearUId() + "=2)");break;
			case TRY_F: planFormula.append(op + "(s" + plan.getClearUId() + "=2)");break;
			case OPT: planFormula.append(op + "(s" + plan.getClearUId() + "=2 | s" + plan.getClearUId() + "=3)");break;
			default: planFormula.append(op + "(s" + plan.getClearUId() + "=2)");
		}
	}
	
	private String buildXorSuccessFormula(PlanContainer plan){
		StringBuilder sb = new StringBuilder();
		/*for(RTContainer altFirst: plan.getAlternatives().keySet()){
			for(RTContainer alt : plan.getAlternatives().get(altFirst)){
				for(RTContainer decAlt : RTContainer.fowardMeansEnd(alt, new LinkedList<RTContainer>()))
					sb.append("s" + decAlt.getClearUId() + "=2 | ");
			}
		}
		for(RTContainer firstAlt: plan.getFirstAlternatives()){
			//for(RTContainer alt : firstAlt.getAlternatives().get(firstAlt))
				for(RTContainer decAlt : RTContainer.fowardMeansEnd(firstAlt, new LinkedList<RTContainer>())){
					//if(!decAlt.equals(plan))
						sb.append("s" + decAlt.getClearUId() + "=3 | ");
						break;
				}
		}*/
		//sb.replace(sb.length() - 3, sb.length(), "");
		return sb.append("(s" + plan.getClearUId() + "=2)").toString();
	}
	
	private String buildPrevSuccessFormula(String prevFormula, PlanContainer plan){
		if(prevFormula == null)
			return "";
		StringBuilder sb = new StringBuilder("(" + prevFormula);
		for(RTContainer altFirst: plan.getAlternatives().keySet()){
			//for(RTContainer alt : plan.getAlternatives().get(altFirst)){
				for(RTContainer decAlt : RTContainer.fowardMeansEnd(altFirst, new LinkedList<RTContainer>()))
					if(!decAlt.equals(plan))
						sb.append(" | s" + decAlt.getClearUId() + "=3");
					else
						break;
			//}
		}
		for(RTContainer firstAlt: plan.getFirstAlternatives()){
			for(RTContainer alt : firstAlt.getAlternatives().get(firstAlt))
				for(RTContainer decAlt : RTContainer.fowardMeansEnd(alt, new LinkedList<RTContainer>()))
					if(!decAlt.equals(plan)){
						sb.append(" | s" + decAlt.getClearUId() + "=3");
						//break;
					}else
						break;
		}
		//sb.replace(sb.length() - 3, sb.length(), "");
		return sb.append(") & ").toString();//.append("s" + plan.getClearUId() + "=2").toString();
	}
	
	private void appendAlternativesToNoErrorFormula(PlanContainer plan) {
		for(RTContainer altFirst : plan.getAlternatives().keySet()){
			noErrorFormula += " & (s" + plan.getClearUId() + " < 4";
			for(RTContainer altPlan : plan.getAlternatives().get(altFirst)){
				noErrorFormula += " & s" + altPlan.getClearUId() + " < 4";			
			}
			noErrorFormula += ")";		
		}
	}

	private void appendTryToNoErrorFormula(PlanContainer plan) {
		noErrorFormula += " & (s" + plan.getClearUId() + " < 4 | (true ";
		if(plan.getTrySuccess() != null){
			RTContainer trySucessPlan = plan.getTrySuccess();			
			noErrorFormula += " & s" + trySucessPlan.getClearUId() + " < 4";			
		}
		if(plan.getTryFailure() != null){
			RTContainer tryFailurePlan = plan.getTryFailure();			
			noErrorFormula += " & s" + tryFailurePlan.getClearUId() + " < 4";			
		}
		noErrorFormula += "))";
	}
	
	/**
	 * Create an agent output dir
	 * 
	 * @param output the dir to be created
	 * 
	 * @throws CodeGenerationException
	 */
	private void writeAnOutputDir( String output ) throws CodeGenerationException {
		File dir = new File( output );
		
		if( !dir.exists() && !dir.mkdirs() ) {
			String msg = "Error: Can't create directory \"" + dir + "\"!";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}

	/**
	 * Create agent PRISM file
	 * 
	 * @param agentName name of the current agent
	 * @param output the output dir
	 * 
	 * @return the created (empyt) ADF file
	 * @throws CodeGenerationException
	 */
	private PrintWriter createPrismFile( String agentName, String output ) throws CodeGenerationException {
		try {
			String adf = agentName + ".pm";
			PrintWriter adfFile = new PrintWriter( 
					new BufferedWriter(	new FileWriter( output + adf ) ) );
			
			return adfFile;
		} catch (IOException e) {
			String msg = "Error: Can't create output model file.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}
	
	/**
	 * Create agent BASH file
	 * 
	 * @param agentName name of the current agent
	 * @param output the output dir
	 * 
	 * @return the created (empyt) ADF file
	 * @throws CodeGenerationException
	 */
	private PrintWriter createBashFile( String bashName, String output ) throws CodeGenerationException {
		try {
			String adf = bashName + ".sh";
			PrintWriter adfFile = new PrintWriter( 
					new BufferedWriter(	new FileWriter( output + adf ) ) );
			
			return adfFile;
		} catch (IOException e) {
			String msg = "Error: Can't create output model file.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}

	/**
	 * replaces all placeholders in the ADF skeleton and writes the ADF file.
	 * 
	 * @param adf the file to be written to
	 */
	private void printModel( PrintWriter adf ) {
		//header = header.replace(BBGOALS_TAG, bbgoals);
		//header = header.replace(BBSOFTGOALS_TAG, bbsoftgoals);
		//header = header.replace(DECOMP_TAG, bbdecomp);
		//header = header.replace(MEANSEND_TAG, bbmeansend);
		//header = header.replace(CONTRIB_TAG, bbcontrib);
		//header = header.replace(DEPENDENCIES_TAG, bbdepend);		

		//body = body.replace(GOALS_TAG, adfgoals);
		//body = body.replace(METAGOALS_TAG, adfmetagoals);
		//body = body.replace(REQUESTPLANS_TAG, adfrequestplans);
		//body = body.replace(DISPATCHPLANS_TAG, adfdispatchplans);
		//body = body.replace(DISPATCHANDPLANS_TAG, adfandplans);
		//body = body.replace(METAPLANS_TAG, adfmetaplans);
		//body = body.replace(REALPLANS_TAG, adfrealplans);
		header = header.replace(NO_ERROR_TAG, noErrorFormula);
		//header = header.replace(G_INIT_TAG, "");		
		body = body.replace(GOAL_MODULES_TAG, planModules);

		//footer = footer.replace(EVENTS_TAG, adfevents);

		adf.println(header);
		adf.println(body);
		//adf.println(footer);
		adf.close();
	}

	private void printEvalBash( PrintWriter pw ){
		
		evalBash = evalBash.replace(PARAMS_BASH_TAG, evalFormulaParams);
		evalBash = evalBash.replace(REPLACE_BASH_TAG, evalFormulaReplace);
		
		pw.print(evalBash + '\n');
		pw.close();
	}
	
	/**
	 * Copy source dir files into target one adapting the package tags
	 * 
	 * @param input source input dir
	 * @param output target ouput dir
	 * @param utilPkgName current util package name
	 * 
	 * @throws CodeGenerationException
	 */
	private void copyDir( String input, String output, String utilPkgName ) throws CodeGenerationException {
		//verify input dir presence
		File inputDir = new File( input );

		if( !inputDir.exists()) {
			String msg = "Error: the input folder '" + input + "' doesn't exist. Exit";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}

		//verify output dir creation
		File outputDir = new File( output );
		
		if( !outputDir.exists() && !outputDir.mkdirs() ) {
			String msg = "Error: Can't create output directory \"" + outputDir + "\"!";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
		
		//copy all the util file found into the input template dir
		File[] files = inputDir.listFiles();
		
		for( File curr : files ) { 
			if( curr.isDirectory() ) {
				//do not attemp to copy directory (avoid 'CVS' problem)
				continue;
			}
			
			String file = readFileAsString( curr.getAbsolutePath() );

			//update tags
			file = file.replace( UTIL_PACKAGE_TAG, utilPkgName );
			file = file.replace( CAPABILITY_AGENT_TAG, PathLocation.CAPABILITY_AGENT_NAME );
			
			//save it
			writeFile( file, output + curr.getName() );
		}
	}

	
	/**
	 * Read the specified file into a String
	 * 
	 * @param filePath the path of the file to read
	 * 
	 * @return the file content as a String
	 * 
	 * @throws CodeGenerationException 
	 */
	private String readFileAsString( String filePath ) throws CodeGenerationException {
		String res = null;
		
		try {
			res = FileUtility.readFileAsString( filePath );			
		} catch (IOException e) {
			String msg = "Error: file " + filePath + " not found.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
		
		return res;
	}

	/**
	 * Write the specified content into a file
	 * 
	 * @param content the content of the file
	 * @param filename the target file
	 * 
	 * @throws CodeGenerationException 
	 */
	private void writeFile(String content, String filename) throws CodeGenerationException {
		try {
			FileUtility.writeFile( content, filename );
		} catch (IOException e) {
			String msg = "Error: Can't create file \"" + filename + "\"!";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}
}