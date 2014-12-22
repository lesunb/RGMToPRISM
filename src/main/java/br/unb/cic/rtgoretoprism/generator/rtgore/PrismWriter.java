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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.generator.kl.CodeGenerator;
import br.unb.cic.rtgoretoprism.model.kl.ElementContainer;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import br.unb.cic.rtgoretoprism.model.kl.SoftgoalContainer;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import br.unb.cic.rtgoretoprism.util.NameUtility;
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
	private static final String PLANNAME_TAG 			= "$PLANNAME";
	private static final String DEPENDUM_TAG 			= "$DEPENDUM";
	private static final String VAL_TAG 				= "$VAL";
	private static final String PRIO_TAG 				= "$PRIO";
	private static final String DEST_TAG 				= "$DEST";
	private static final String SRC_TAG 				= "$SRC";
	private static final String CHILD_TAG 				= "$CHILD";
	private static final String TRIGGERS_TAG 			= "$TRIGGERS";
	private static final String RESULTS_TAG 			= "$RESULTS";
	private static final String PARAMS_TAG 				= "$PARAMS";
	private static final String DECTYPE_TAG 			= "$DECTYPE";
	private static final String VALUE_TAG 				= "$VALUE";
	private static final String EVENTS_TAG 				= "$EVENTS";
	private static final String REALPLANS_TAG 			= "$REALPLANS";
	private static final String METAPLANS_TAG 			= "$METAPLANS";
	private static final String DISPATCHANDPLANS_TAG 	= "$DISPATCHANDPLANS";
	private static final String DISPATCHPLANS_TAG 		= "$DISPATCHPLANS";
	private static final String REQUESTPLANS_TAG 		= "$REQUESTPLANS";
	private static final String METAGOALS_TAG 			= "$METAGOALS";
	private static final String GOALS_TAG 				= "$GOALS";
	private static final String DEPENDENCIES_TAG 		= "$DEPENDENCIES";
	private static final String CONTRIB_TAG 			= "$CONTRIB";
	private static final String MEANSEND_TAG 			= "$MEANSEND";
	private static final String DECOMP_TAG 				= "$DECOMP";
	private static final String BBSOFTGOALS_TAG 		= "$BBSOFTGOALS";
	private static final String BBGOALS_TAG 			= "$BBGOALS";
	private static final String PACKAGE_TAG 			= "$PACKAGE";
	private static final String UTIL_PACKAGE_TAG		= "$UTIL_PACKAGE";
	private static final String PLAN_PACKAGE_TAG		= "$PLAN_PACKAGE";
	private static final String BDI_PLAN_PACKAGE_TAG	= "$BDI_PLAN_PACKAGE";
	private static final String NAME_TAG 				= "$NAME";
	private static final String CAPABILITY_AGENT_TAG	= "$CAPABILITY_AGENT";
	private static final String AGENT_NAME_TAG			= "$AGENT_NAME";
	private static final String TMP_TAG 				= "TMP";
	
	private static final String MODULE_NAME_TAG			= "$MODULE_NAME$";
	private static final String NO_ERROR_TAG			= "$NO_ERROR$";
	private static final String TIME_SLOT_TAG			= "$TIME_SLOT$";
	private static final String PREV_TIME_SLOT_TAG		= "$PREV_TIME_SLOT$";
	private static final String GID_TAG					= "$GID$";
	private static final String PREV_GID_TAG					= "$PREV_GID$";
	private static final String GOAL_MODULES_TAG 		= "$GOAL_MODULES$";
	private static final String XOR_GIDS_TAG 			= "$XOR_GIDS$";
	private static final String XOR_VALUE_TAG	 		= "$XOR_VALUE$";
	private static final String DEC_HEADER_TAG	 		= "$DEC_HEADER$";
	private static final String DEC_TYPE_TAG	 		= "$DEC_TYPE$";
	
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
	private String header, body, footer;

	// Strings filled with content, to replace the placeholders in the adf skeleton.
	//Note: they are used whith concat() function
	private String bbdecomp = "", bbmeansend = "", bbcontrib = "", bbdepend = "";
	private String bbsoftgoals = "";
	private String bbgoals = "";

	private String adfgoals = "", adfmetagoals = "";
	private String adfrequestplans = "", adfdispatchplans = "", adfandplans = "";
	private String adfmetaplans = "", adfrealplans = "", adfevents = "";
	
	private String noErrorFormula = "";
	private String planModules = "";

	/** Has all the informations about the agent. */ 
	private AgentDefinition ad;
	/** the list of plan that are root for a capability of the selected agent */
	private List<Plan> capabilityPlanList;

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
		this.templateInputBaseFolder = input;// + "/"; 
		this.inputKLFolder = templateInputBaseFolder + TEMPLATE_KL_BASE_PATH;
		this.inputPRISMFolder = templateInputBaseFolder + TEMPLATE_PRISM_BASE_PATH;
		this.basicOutputFolder = output + "/";
		this.agentOutputFolder = 
			basicOutputFolder + PathLocation.BASIC_AGENT_PACKAGE_PREFIX + ad.getAgentName() + "/";
		
		//the package that generated bdi .java files will be put in
		this.basicAgentPackage = PathLocation.BASIC_AGENT_PACKAGE_PREFIX + ad.getAgentName();
	}

	/**
	 * Writes the whole Agent (ADF + Java plan bodies).
	 * 
	 * @throws CodeGenerationException 
	 */
	public void writeModel() throws CodeGenerationException {
		String utilPkgName = basicAgentPackage + PathLocation.UTIL_KL_PKG;
		
		//String planInputFolder = inputKLFolder + TEMPLATE_PLAN_PATH;
		//String utilInputFolder = inputKLFolder + TEMPLATE_UTIL_PATH;
		String prismInputFolder = inputPRISMFolder;
		
		String planOutputFolder = agentOutputFolder + "plans" + "/";
		String planPkgName = basicAgentPackage + ".plans";
		
		//read some of the used template
		header = readFileAsString( prismInputFolder + "modelheader.pm" );
		body = readFileAsString( prismInputFolder + "modelbody.pm" );
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
		PrintWriter modelFile = writeModel( ad.getAgentName(), agentOutputFolder );
		//Reads all softgoals from the softgoals list and writes them into the belief base.
		//writeBBSoftGoals( ad.softgoalbase );
		//Writes all goals to the ADF file
		writeTasks( prismInputFolder, ad.planbase, planOutputFolder, basicAgentPackage, utilPkgName, planPkgName );
		//Writes the plan contributions and the bodies of the real plans
		//writePlans( planInputFolder, planOutputFolder, ad.planbase, basicAgentPackage, utilPkgName, planPkgName, ad.getAgentName() );
		//Copies to the output directory all the files where only the package-name changes.
		//writeDefaultJavaFiles( planInputFolder, planOutputFolder, basicAgentPackage, utilPkgName, planPkgName );
		//replaces all placeholders in the ADF skeleton and writes the ADF file.
		printModel( modelFile );
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
	 * Create the skeleton component for the CL part that will be used in order to run the KL part
	 * This skeletons are created only in the case that more specialized one (i.e. the ones coming
	 * from the UML diagrams) haven't been explicitly created and just in order to allow the KL 
	 * part to run
	 *  
	 * @param agent name of the current agent
	 * @param plans the list of root plans for this agent' capabilities
	 * @param outputFolder target folder
	 * @param templatedInputFolder template input folder
	 * 
	 * @throws CodeGenerationException
	 */	
	private void writeCapabilityCLSkeleton( String agent, List<Plan> plans, String outputFolder, String templatedInputFolder ) throws CodeGenerationException {
		//the skeleton for the FSM capabilities agent
		String capSkeletonName = "EmptyCapabilityTemplate.java";
		//the utility elements that should be copied
		String fileNames[] = new String[] { "DSManager.java", "CapabilityFSMBehaviour.java" };
		//the skeleton for the CapabilitiesAgent
		String capabilitiesAgentName = PathLocation.CAPABILITY_AGENT_NAME + ".java";

		
		//true if we have created some new files. We avoid overwriting existing files
		//since probably they are more specialized than the skeleton we create here.
		boolean created = false;
		
		//current Agent pacakage
		String currAgentPkg = PathLocation.BASIC_AGENT_PACKAGE_PREFIX + agent;
		//current capability package
		String currCapabilityPkg =  currAgentPkg + "." + PathLocation.CAPABILITIES_FOLDER;
		//current cl utility package
		String currUtilPkg = currAgentPkg + PathLocation.UTIL_CL_PKG;
	
		//create the capability folder if necessary
		writeAnOutputDir( outputFolder +  PathLocation.CAPABILITIES_FOLDER );
		
		//iterate over the capabilities root plans	
		for( Plan plan : plans ) {
			//capability agent to be created for this root plan
			String FSM_Name = PathLocation.BASIC_FSM_PREFIX + NameUtility.adjustName( plan.getName() );
			//path for the capability agent
			String FSM_Path = outputFolder +  PathLocation.CAPABILITIES_FOLDER + "/" + FSM_Name + ".java";
			
			File currFSM = new File( FSM_Path );
			
			//do not overwrite existing one
			if( !currFSM.exists() ) {
				//we have created some new files
				created = true;

				//the template input skeleton part
				String fsmSkeleton = readFileAsString( templatedInputFolder + TEMPLATE_CL_BASE_PATH +
						capSkeletonName );
		
				//update tags
				fsmSkeleton = fsmSkeleton.replace( CodeGenerator.CAPABILITIES_PACKAGE_NAME_TAG, currCapabilityPkg );
				fsmSkeleton = fsmSkeleton.replace( CodeGenerator.UTIL_PACKAGE_NAME_TAG, currUtilPkg );
				fsmSkeleton = fsmSkeleton.replace( CodeGenerator.FSM_NAME_TAG, FSM_Name );
				
				//write it
				writeFile( fsmSkeleton, FSM_Path  );
			}
		}
		
		if( created ) {
			//if something has been created we need to update the utility part too in order
			//to cope with code dependency
			
			//template input folder
			String templateInputFolder = templatedInputFolder + TEMPLATE_CL_BASE_PATH;
			//template input util folder
			String utilInputFolder = templateInputFolder + CodeGenerator.UTIL_INPUT_FOLDER + "/";
			//target util folder
			String outputUtilFolder = outputFolder +  CodeGenerator.UTIL_OUTPUT_FOLDER; 

			//create the target util dir if necessary
			writeAnOutputDir( outputUtilFolder );
			
			//create the selected util's file if necessary
			for( int i = 0; i < fileNames.length; i++ ) {
				String currName = fileNames[ i ];
				//target path
				String out = outputUtilFolder + "/" + currName;

				File curr = new File( out );

				//avoid overwriting
				if( !curr.exists() ) {
					//template input skeleton
					String utilSkeleton = readFileAsString( utilInputFolder + "/" + currName );
					//update tags
					utilSkeleton = utilSkeleton.replace( CodeGenerator.UTIL_PACKAGE_NAME_TAG, currUtilPkg );
					//write it
					writeFile( utilSkeleton, out );
				}
			}
			
		
			//create the CapabilitiesAgent
			//template input skeleton
			String capAgSkeleton = readFileAsString( templateInputFolder + capabilitiesAgentName );
			//update tags
			capAgSkeleton = capAgSkeleton.replace( CodeGenerator.AGENT_PACKAGE_NAME_TAG, currAgentPkg );
			capAgSkeleton = capAgSkeleton.replace( CodeGenerator.CAPABILITIES_PACKAGE_NAME_TAG, currCapabilityPkg );
			capAgSkeleton = capAgSkeleton.replace( CodeGenerator.UTIL_PACKAGE_NAME_TAG, currUtilPkg );
			//write it
			writeFile( capAgSkeleton, outputFolder + capabilitiesAgentName);	
		}
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
	 * Create agent ADF file
	 * 
	 * @param agentName name of the current agent
	 * @param output the output dir
	 * 
	 * @return the created (empyt) ADF file
	 * @throws CodeGenerationException
	 */
	private PrintWriter writeADF( String agentName, String output ) throws CodeGenerationException {
		try {
			String adf = agentName + ".agent.xml";
			PrintWriter adfFile = new PrintWriter( 
					new BufferedWriter(	new FileWriter( output + adf ) ) );
			
			return adfFile;
		} catch (IOException e) {
			String msg = "Error: Can't create output adf file.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}
	
	/**
	 * Create agent ADF file
	 * 
	 * @param agentName name of the current agent
	 * @param output the output dir
	 * 
	 * @return the created (empyt) ADF file
	 * @throws CodeGenerationException
	 */
	private PrintWriter writeModel( String agentName, String output ) throws CodeGenerationException {
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
	 * Writes the batch files (Windows) to start the agent.
	 * 
	 *  @param output the output dir
	 *  @param agentName current Agent name
	 *  @param agentPkg current base agent package
	 * 
	 * @throws CodeGenerationException 
	 */
	private void writeAgentStartingFile( String output, String agentName, String agentPkg ) throws CodeGenerationException {
		String bat = "java jade.Boot -container My" + agentName
			+ ":jadex.adapter.jade.JadeAgentAdapter" + "(" + agentPkg + "." + 
			agentName + " default)";
		
		writeFile( bat, output + agentName + ".bat");
	}

	/**
	 * Create the file to compile all the agent stuff
	 * 
	 * @param output target base agent output dir
	 * 
	 * @throws CodeGenerationException 
	 */
	private void writeAgentCompileFile( String output ) throws CodeGenerationException {
		//target dir for the util.kl package
		String utilKLDir = "util\\kl";
		String plansDir = "plans";

		String bat = 
			"md build" + "\n" + 
			"javac -sourcepath ..\\ " + utilKLDir + "\\components" + "\\*.java -d .\\build" + "\n" +
			"javac -sourcepath ..\\ " + utilKLDir + "\\plans" + "\\*.java -d .\\build" + "\n" +
			"javac -sourcepath ..\\ " + plansDir + "\\*.java -d .\\build" + "\n";

		writeFile( bat, output + "/compile_kl.bat");
	}

	/**
	 * Writes the batch file (Windows) to start the platform
	 * 
	 * @param output the output dir
	 */
	private void writePlatformStartingFile( String output ) {
		try {
			FileUtility.copyFile( inputKLFolder + "platform.ba_", output + "/" + "run_platform_kl.bat" );
		} catch (IOException e) {
			String msg = "Warning: platform start batch file not copied correctly.";
			ATCConsole.println( msg );
		}
	}
		
	/**
	 * Copies to the output directory all the files where only the package-name changes.
	 * 
	 * @param input template input folder
	 * @param output target dir
	 * @param pkgName current package name
	 * @param utilPkgName current util package name
	 * @param planPkgName current plan package name
	 * 
	 * @throws CodeGenerationException 
	 */
	private void writeDefaultJavaFiles( String input, String output, String pkgName,
			String utilPkgName, String planPkgName ) throws CodeGenerationException {
		// here only the internal package name changes
		String dispatchGoalPlan = readFileAsString(input + "DispatchGoalPlan.java");
		dispatchGoalPlan = dispatchGoalPlan.replace(PACKAGE_TAG, pkgName);
		dispatchGoalPlan = dispatchGoalPlan.replace(UTIL_PACKAGE_TAG, utilPkgName);
		dispatchGoalPlan = dispatchGoalPlan.replace(PLAN_PACKAGE_TAG, planPkgName );
		
		writeFile(dispatchGoalPlan, output + "DispatchGoalPlan.java");

		String goalRequestPlan = readFileAsString(input + "GoalRequestPlan.java");
		goalRequestPlan = goalRequestPlan.replace(PACKAGE_TAG, pkgName);
		goalRequestPlan = goalRequestPlan.replace(UTIL_PACKAGE_TAG, utilPkgName);
		goalRequestPlan = goalRequestPlan.replace(PLAN_PACKAGE_TAG, planPkgName);
		
		writeFile(goalRequestPlan, output + "GoalRequestPlan.java");

		String informChangePlan = readFileAsString(input + "InformChangePlan.java");
		informChangePlan = informChangePlan.replace(PACKAGE_TAG, pkgName);
		informChangePlan = informChangePlan.replace(UTIL_PACKAGE_TAG, utilPkgName );
		informChangePlan = informChangePlan.replace(PLAN_PACKAGE_TAG, planPkgName );
		
		writeFile(informChangePlan, output + "InformChangePlan.java");

		// the other files are written directly in writePlans/writeGoals
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
		body = body.replace(GOAL_MODULES_TAG, planModules);

		//footer = footer.replace(EVENTS_TAG, adfevents);

		adf.println(header);
		adf.println(body);
		//adf.println(footer);
		adf.close();
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
	 */
	private void writeTasks( String input, Hashtable<String,PlanContainer> gb, 
			String planOutputFolder, String pkgName, String utilPkgName, String planPkgName ) throws CodeGenerationException {

		String leafGoalPattern 			= readFileAsString(input + "pattern_leafgoal.pm");
		String andDecPattern 			= readFileAsString(input + "pattern_and.pm");
		String xorDecPattern 			= readFileAsString(input + "pattern_xor.pm");
		String xorDecHeaderPattern 		= readFileAsString(input + "pattern_xor_header.pm");
		String trySDecPattern	 		= readFileAsString(input + "pattern_try_success.pm");
		String tryFDecPattern	 		= readFileAsString(input + "pattern_try_fail.pm");
		String optDecPattern 			= readFileAsString(input + "pattern_opt.pm");
		String optHeaderPattern	 		= readFileAsString(input + "pattern_opt_header.pm");
		
		List<PlanContainer> runTimeTasks = new ArrayList<PlanContainer>(gb.values());
		Collections.sort(runTimeTasks);

		for( PlanContainer goal : runTimeTasks ) {
			writePrismModule(goal, 
							leafGoalPattern, 
							andDecPattern, 
							xorDecPattern, 
							xorDecHeaderPattern, 
							trySDecPattern, 
							tryFDecPattern,
							optDecPattern,
							optHeaderPattern);
		}
		
		System.out.println(planModules);
	}

	/**
	 * 
	 * 
	 * @param g current goalContainer
	 * @param gpattern
	 * @param ppattern
	 * @param file_MetaPlan the current MetaPlan file name
	 * @param planOutputFolder the outputFolder for the created plans
	 * @param pkgName current package name
	 * @param utilPkg current util package name
	 * @param planPkg current plan package
	 * 
	 * @throws CodeGenerationException 
	 */
	private void writeMetaGoalPlan(GoalContainer g, String gpattern, String ppattern,
		String file_MetaPlan, String planOutputFolder, String pkgName, String utilPkg, 
		String planPkg ) throws CodeGenerationException {
		
		String adfgoal = gpattern.replace(NAME_TAG, g.getName());
		adfmetagoals = adfmetagoals.concat(adfgoal);
		String adfplan = ppattern.replace(NAME_TAG, g.getName());
		adfmetaplans = adfmetaplans.concat(adfplan);
		
		// copy and rename the plan body
		String file = file_MetaPlan.replace(PACKAGE_TAG, pkgName);
		file = file.replace(UTIL_PACKAGE_TAG, utilPkg );
		file = file.replace(PLAN_PACKAGE_TAG, planPkg );
		file = file.replace( TMP_TAG, g.getName());
		
		writeFile(file, planOutputFolder + "MetaPlan_" + g.getName() + ".java");
	}

	/**
	 * 
	 * @param g the current goalContainer
	 * @param pattern
	 */
	private void writeRequestplan(GoalContainer g, String pattern) {
		String adfplan = pattern.replace(NAME_TAG, g.getName());
		adfrequestplans = adfrequestplans.concat(adfplan);
	}

	/**
	 * 
	 * @param g the current goalContainer
	 * @param pattern
	 */
	private void writeEvent(GoalContainer g, String pattern) {
		String adfevent = pattern.replace(NAME_TAG, g.getName());
		adfevents = adfevents.concat(adfevent);
	}
	
	/**
	 * Writes the dispatch plans (with bodies) for every child goal
	 * 
	 * @param goal
	 * @param pattern
	 */
	private void writePrismModule(PlanContainer plan, 
							 String pattern, 
							 String andPattern, 
							 String xorPattern, 
							 String xorHeader, 
							 String trySPattern, 
							 String tryFPattern,
							 String optPattern,
							 String optHeader) {
		
		String params="", results="", triggers="";		
		String planModule = pattern.replace(MODULE_NAME_TAG, plan.getClearElId());
		
		if(plan.getTryOriginal() != null || plan.getTrySuccess() != null || plan.getTryFailure() != null){
			if(plan.getTrySuccess() != null || plan.getTryFailure() != null){
				//Try				
				planModule = planModule.replace(DEC_HEADER_TAG, "");
				planModule = planModule.replace(DEC_TYPE_TAG, andPattern);
				appendTryToNoErrorFormula(plan);
			}else if(plan.isSuccessTry()){
				//Try success
				PlanContainer tryPlan = (PlanContainer) plan.getTryOriginal();
				trySPattern = trySPattern.replace(PREV_GID_TAG, tryPlan.getElId());
				planModule = planModule.replace(DEC_TYPE_TAG, trySPattern);
			}else{
				//Try fail
				PlanContainer tryPlan = (PlanContainer) plan.getTryOriginal();
				tryFPattern = tryFPattern.replace(PREV_GID_TAG, tryPlan.getElId());
				planModule = planModule.replace(DEC_TYPE_TAG, tryFPattern);
			}	
			planModule = planModule.replace(DEC_HEADER_TAG, "");
		}else if(!plan.getAlternatives().isEmpty() || plan.getFirstAlternative() != null){
			//Alternatives
			if(!plan.getAlternatives().isEmpty()){
				xorPattern = xorPattern.replace(XOR_GIDS_TAG, plan.getElId() + "_" + plan.getAltElsId());
				xorHeader = xorHeader.replace(XOR_GIDS_TAG, plan.getElId() + "_" + plan.getAltElsId());
				xorPattern = xorPattern.replace(XOR_VALUE_TAG, 0 + "");
				appendAlternativesToNoErrorFormula(plan);
			}else{
				xorPattern = xorPattern.replace(XOR_GIDS_TAG, plan.getFirstAlternative().getElId() + "_" + plan.getFirstAlternative().getAltElsId());
				xorHeader = "";
				xorPattern = xorPattern.replace(XOR_VALUE_TAG, plan.getFirstAlternative().getAlternatives().indexOf(plan) + 1 + "");
			}
			planModule = planModule.replace(DEC_HEADER_TAG, xorHeader);
			planModule = planModule.replace(DEC_TYPE_TAG, xorPattern);
		}else if(plan.isOptional()){
			//Opt
			planModule = planModule.replace(DEC_HEADER_TAG, optHeader);
			planModule = planModule.replace(DEC_TYPE_TAG, optPattern);
			noErrorFormula += " & s" + plan.getElId() + " < 3";
		}else{
			//And/OR			
			planModule = planModule.replace(DEC_HEADER_TAG, "");
			planModule = planModule.replace(DEC_TYPE_TAG, andPattern);
			noErrorFormula += " & s" + plan.getElId() + " < 3";
		}			
		
		//Time
		Integer timePath = plan.getTimePath();
		Integer timeSlot = plan.getTimeSlot() + 1;
		planModule = planModule.replace(PREV_TIME_SLOT_TAG, timePath + "_" + (timeSlot - 1) + "");
		planModule = planModule.replace(TIME_SLOT_TAG, timePath + "_" + timeSlot + "");
		//GID
		planModule = planModule.replace(GID_TAG, plan.getElId());		
		planModules = planModules.concat(planModule);
	}

	private void appendAlternativesToNoErrorFormula(PlanContainer plan) {
		noErrorFormula += " & (s" + plan.getElId() + " < 3";
		for(RTContainer altPlan : plan.getAlternatives())
			noErrorFormula += " & s" + altPlan.getElId() + " < 3";
		noErrorFormula += ")";
	}

	private void appendTryToNoErrorFormula(PlanContainer plan) {
		noErrorFormula += " & (s" + plan.getElId() + " < 3 | (true ";
		if(plan.getTrySuccess() != null){
			RTContainer trySucessPlan = plan.getTrySuccess();			
			noErrorFormula += " & s" + trySucessPlan.getElId() + " < 3";			
		}
		if(plan.getTryFailure() != null){
			RTContainer tryFailurePlan = plan.getTryFailure();			
			noErrorFormula += " & s" + tryFailurePlan.getElId() + " < 3";			
		}
		noErrorFormula += "))";
	}

	/**
	 * Writes the dispatch plans (with bodies) for every child goal
	 * 
	 * @param goal
	 * @param pattern
	 */
	private void writeDecompositionLinks(GoalContainer goal, String pattern) {
		String params="", results="", triggers="";
		
		for (GoalContainer parent : goal.getParentGoals()) {
			params=params.concat("<goalmapping ref=\""+parent.getName()+".param\"/>");
			results=results.concat("<goalmapping ref=\""+parent.getName()+".result\"/>");
			triggers=triggers.concat("<goal ref=\""+parent.getName()+"\"/>");
			
			addBBDecomp(parent, goal);
		}
		
		String adfplan = pattern.replace(PARAMS_TAG, params);
		adfplan = adfplan.replace(RESULTS_TAG, results);
		adfplan = adfplan.replace(TRIGGERS_TAG, triggers);
		
		adfplan = adfplan.replace(CHILD_TAG, goal.getName());
		
		adfdispatchplans = adfdispatchplans.concat(adfplan);
	}

	/**
	 * 
	 * 
	 * @param g the current goalContainer
	 * @param pattern
	 * @param file_ANDGoalPlan
	 * @param planOutputFolder the output folder for generated plans
	 * @param pkgName the current package name
	 * @param utilPkgName the current util package name
	 * @param planPkgName the current plan package name
	 * 
	 * @throws CodeGenerationException 
	 */
	private void writeDispatchANDPlan(GoalContainer g, String pattern, String file_ANDGoalPlan, 
			String planOutputFolder, String pkgName, String utilPkgName, String planPkgName ) throws CodeGenerationException {
		String adfplan = pattern.replace(NAME_TAG, g.getName());
		
		adfandplans = adfandplans.concat(adfplan);
		
		for (GoalContainer child : g.getDecompGoals()) {
			addBBDecomp(g, child, 1/* g.getPriority() */);
		}
		
		// copy and rename the plan body
		String file = file_ANDGoalPlan.replace(PACKAGE_TAG, pkgName);
		file = file.replace(UTIL_PACKAGE_TAG, utilPkgName);
		file = file.replace(PLAN_PACKAGE_TAG, planPkgName );
		file = file.replace( TMP_TAG, g.getName());
		
		writeFile(file, planOutputFolder + "ANDGoalPlan_" + g.getName() + ".java");
	}

	/**
	 * 
	 * @param src
	 * @param dest
	 */
	private void addBBDecomp(ElementContainer src, ElementContainer dest) {
		String pattern = "\t\t\t<fact>new TLink(\"" + SRC_TAG + "\",\"" + DEST_TAG + "\")</fact>\n";
		
		String fact = pattern.replace(SRC_TAG, src.getName());
		fact = fact.replace(DEST_TAG, dest.getName());
		bbdecomp = bbdecomp.concat(fact);
	}

	/**
	 * 
	 * @param src
	 * @param dest
	 * @param priority
	 */
	private void addBBDecomp(ElementContainer src, ElementContainer dest, int priority) {
		String pattern = "\t\t\t<fact>new TLink(\"" + SRC_TAG + "\",\"" + DEST_TAG + "\"," + PRIO_TAG + ")</fact>\n";
		
		String fact = pattern.replace(SRC_TAG, src.getName());
		fact = fact.replace(DEST_TAG, dest.getName());
		fact = fact.replace(PRIO_TAG, "" + priority);
		bbdecomp = bbdecomp.concat(fact);
	}

	/**
	 * 
	 * @param src
	 * @param dest
	 */
	private void addBBMeansEnd(ElementContainer src, ElementContainer dest) {
		String pattern = "\t\t\t<fact>new TLink(\"" + SRC_TAG + "\",\"" + DEST_TAG + "\")</fact>\n";
		
		String fact = pattern.replace(SRC_TAG, src.getName());
		fact = fact.replace(DEST_TAG, dest.getName());
		bbmeansend = bbmeansend.concat(fact);
	}

	/**
	 * 
	 * @param src
	 * @param dest
	 * @param value
	 */
	private void addBBContrib(ElementContainer src, ElementContainer dest, String value) {
		String pattern = "\t\t\t<fact>new TContrib(\"" + SRC_TAG + "\",\"" + DEST_TAG + "\",\"" + VAL_TAG + "\")</fact>\n";
		
		String fact = pattern.replace(SRC_TAG, src.getName());
		fact = fact.replace(DEST_TAG, dest.getName());
		fact = fact.replace(VAL_TAG, value);
		bbcontrib = bbcontrib.concat(fact);
	}
	
	/**
	 * 
	 * @param goal The 'why' for the dependency, our starting point.
	 * @param dependum The dependum Goal.
	 * @param dependee The dependee Actor.
	 */
	private void addBBDependency(GoalContainer goal, String dependum, String dependee) {
		//the dependencies actually work only if the source goal is AND-decomposed. 
		String pattern = "\t\t\t<fact>new TDependency(\"" + SRC_TAG + "\",\"" + DEPENDUM_TAG + "\",\"" + DEST_TAG + "\")</fact>\n";
		
		String fact = pattern.replace(SRC_TAG, goal.getName());
		fact = fact.replace(DEPENDUM_TAG, dependum);
		fact = fact.replace(DEST_TAG, dependee);
		bbdepend = bbdepend.concat(fact);	
	}

	/**
	 * Writes the plan contributions and the bodies of the real plans.
	 * 
	 * @param input the template input folder
	 * @param output the target dir
	 * @param pb the beliefe base plan
	 * @param pkgName the current package name
	 * @param utilPkg the current util package name
	 * @param planPkg the current plan package name
	 * @param agentName the current agent name 
	 * 
	 * @throws CodeGenerationException 
	 */
	private void writePlans( String input, String output, Hashtable<String,PlanContainer> pb, String pkgName,
			String utilPkg, String planPkg, String agentName ) throws CodeGenerationException {
		String file_RealPlan = readFileAsString(input + "RealPlan_" + TMP_TAG + ".java");
		
		String realplanpattern = readFileAsString(input + "pattern_realplan.xml");

		for (PlanContainer plan : pb.values()) {
			// retrieve softgoal contributions
			for (SoftgoalContainer sg : plan.getContributions().keySet()) {
				addBBContrib(plan, sg, plan.getContributions().get(sg));
			}
			
			writeMeansEndLink(plan, realplanpattern);
			
			// copy and rename the real plan body
			String newfile = file_RealPlan.replace(PACKAGE_TAG, pkgName);
			newfile = newfile.replace(UTIL_PACKAGE_TAG, utilPkg );
			newfile = newfile.replace( PLAN_PACKAGE_TAG, planPkg );
			newfile = newfile.replace( TMP_TAG, plan.getName());
			newfile = newfile.replace( AGENT_NAME_TAG, agentName );
			
			writeFile(newfile, output + "RealPlan_" + plan.getName() + ".java");
//TODO: add access to resources in plan body!
		}
	}
	
	/**
	 * Writes the dispatch plans (with bodies) for every child goal
	 * 
	 * @param goal
	 * @param pattern
	 */
	private void writeMeansEndLink(PlanContainer plan, String pattern) {
		String params="", results="", triggers="";
		
		for (GoalContainer meansEnd:plan.getMEGoals()) {
			params=params.concat("<goalmapping ref=\""+meansEnd.getName()+".param\"/>");
			results=results.concat("<goalmapping ref=\""+meansEnd.getName()+".result\"/>");
			triggers=triggers.concat("<goal ref=\""+meansEnd.getName()+"\"/>");
			
			addBBMeansEnd(meansEnd, plan);
		}
		
		String adfplan = pattern.replace(PARAMS_TAG, params);
		adfplan = adfplan.replace(RESULTS_TAG, results);
		adfplan = adfplan.replace(TRIGGERS_TAG, triggers);
		
		adfplan = adfplan.replace(PLANNAME_TAG, plan.getName());
		
		adfrealplans = adfrealplans.concat(adfplan);
	}

	/**
	 * Write out a 'copy' of the templateinput util directory for the bdi agents
	 * 
	 * @param input the templateinput 'util' dir
	 * @param output target base agent output dir
	 * @param utilKLPkg name of the current utility package
	 * 
	 * @throws CodeGenerationException
	 */
	private void writeUtilDir( String input, String output, String utilKLPkg ) throws CodeGenerationException {
		//target dir for the util.kl package
		String utilKLDir = "util/kl";
		
		//export 'components' side
		copyDir( input + "/" + "components" + "/", output + utilKLDir + "/components/", utilKLPkg );
		//export 'plans' side
		copyDir( input + "/" + "plans" + "/", output + utilKLDir + "/plans/", utilKLPkg );
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