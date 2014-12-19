/**
 * <copyright>
 *
 * TAOM4E - Tool for Agent Oriented Modeling for the Eclipse Platform
 * Copyright (C) ITC-IRST, Trento, Italy
 * Authors: Davide Bertolini, Mirko Morandini
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

package br.unb.cic.rtgoretoprism.generator.kl;

import java.io.File;
import java.io.IOException;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import br.unb.cic.rtgoretoprism.util.PathLocation;

/**
 * The class that generate the skeleton code for the read agents
 * 
 * @author Loris Delpero (initial work)
 * @author Mirko Morandini
 * @author bertolini
 * 
 */

public class CodeGenerator {
	/** constant defining the supported protocols */
	private static final String REQUEST_INITITATOR = "RequestInitiator";
	private static final String REQUEST_RESPONDER = "RequestResponder";

	private static final String CFP_INITIATOR = "CFPInitiator";
	private static final String CFP_RESPONDER = "CFPResponder";

	//private static final String SUBSCRIBE_INITIATOR = "SubscribeInitiator";
	private static final String SUBSCRIBE_RESPONDER = "SubscribeResponder";

	private static final String SUBSCRIPTION_MANAGER = "SManager";

	/** where to find related template base section, inside the templated folder */
	private static final String TEMPLATE_BASE_PATH = "CL/";
	/** the folder to which output protocol should be written */
	private static final String PROTOCOL_OUTPUT_FOLDER = "protocols";
	/** the folder from which input util should be read */
	public static final String UTIL_INPUT_FOLDER = "util";
	/** the folder to which output util should be written */
	public static final String UTIL_OUTPUT_FOLDER = "util/cl";

	private static final String TESTER_AGENT_TEMPLATE_INPUT_NAME = "CapabilityTesterAgent.java";

	/** the set of placeholder founded into template files that are 
	 * substituted with the proper values during the code generation process. */
	//name of the current fsm
	public static String FSM_NAME_TAG 						= "<fsm-name>";
	//pkg of the current agent
	public static String AGENT_PACKAGE_NAME_TAG 			= "<pkg-name>";
	//utility pkg of the current agent
	public static String UTIL_PACKAGE_NAME_TAG 				= "<util-pkg-name>";
	//pkg of the capabilities of the current agent
	public static String CAPABILITIES_PACKAGE_NAME_TAG 		= "<caps-pkg-name>";
	//name of the testing agent (CapabilityTesterAgent)
	private static String CAPABILITY_TESTER_AGENT_TAG 		= "<cap-tester-name>";
	//number of the current protocol
	private static String PROTOCOL_NUMBER_TAG 				= "<prot-number>";

	
	private static String SOURCE_NAME_TAG					= "<sourcename-tag>";
	private static String TARGET_NAME_TAG					= "<targetname-tag>";
	private static String STOP_NAME_TAG						= "<stopname-tag>";
	private static String OR_NAME_TAG						= "<orname-tag>";
	private static String ACTIVITIES_LIST_TAG				= "<activitieslist-tag>";
	private static String NEXT_STATE_TAG					= "<nextstate-tag>";
	private static String AND_NAME_TAG						= "<andname-tag>";
	
	//the folder to which output should be directed
	private String outputFolder;
	//the folder from which templated should be read
	private String inputFolder;
	//the folder from which templated protocol should be read
	private String protocolInputFolder;
	//the folder from wich templated util shoul be read
	private String utilInputFolder;

	
	
	
	
	/**
	 * Create a new CodeGenerator instance
	 * 
	 * @param umlRes the resource for the uml2 object
	 * @param xmiReader the current xmiReader
	 * @param tData the current TransformData
	 * @param in template input folder
	 * @param out selected output folder for generate code
	 */
	public CodeGenerator(String in, String out) {
	
		
		this.inputFolder = in + "/" + TEMPLATE_BASE_PATH;
		this.utilInputFolder = inputFolder + UTIL_INPUT_FOLDER + "/";
		this.protocolInputFolder = inputFolder + "IProtocols" + "/";
		
		this.outputFolder = out + "/"; 
	}

	/**
	 * Execute the processing step in order to obtain generate code for the
	 * requested actors/agent
	 * 
	 * @param selectedActors the actor for which the code should be actualy generated
	 * 
	 * @throws CodeGenerationException
	 */

	/**
	 * Create the folder for this agent
	 * 
	 * @param agentName the name of the agent
	 * @param agentDir the agent folder
	 * @param capsDir the agent capability folder
	 * 
	 * @throws CodeGenerationException
	 */
	public void createAgentFolder( String agentName, File agentDir, File capsDir ) throws CodeGenerationException {
		// Creates agent folder
		ATCConsole.println("Generating Jade agent for: " + agentName );

		if( (!agentDir.exists() && !agentDir.mkdirs() ) ||
			(!capsDir.exists() && !capsDir.mkdirs()) ) {
			String msg = "Error while generating Agent Folders. Exit";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}

	/**
	 * Create the folder (and related files) for the Util package
	 * 
	 * @param utilInput template util input dir
	 * @param agentDir agent target dir
	 * @param agentName agent name
	 * @param utilPkg agent util package
	 * 
	 * @throws CodeGenerationException
	 */
	public void createUtilDir( String utilInput, File agentDir, String agentName, String utilPkg ) throws CodeGenerationException {
//		ATCConsole.println("Generating util package for agent "	+ agentName);
		
		File utilDirectory = new File(utilInput);
		
		if( !utilDirectory.exists() ) {
			String msg = "Error: the input util folder '" + utilInput + "' doesn't exist.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}

		File destDir = new File(agentDir + "/" + UTIL_OUTPUT_FOLDER );
		
		if( !destDir.exists() && !destDir.mkdirs() ) {
			String msg = "Error: Can't create output directory \"" + destDir + "\"!";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}

		//copy all the util files found into the input template dir
		File[] files = utilDirectory.listFiles();
		
		for( File curr : files ) {
			if( curr.isDirectory() ) {
				//do not attemp to copy directory (avoid 'CVS' problem)
				continue;
			}

			String file = readFileAsString( curr.getAbsolutePath() );
			
			//update tags
			file = file.replace( UTIL_PACKAGE_NAME_TAG, utilPkg );
			
			writeFile( file, agentDir + "/" + UTIL_OUTPUT_FOLDER + "/" + curr.getName() );
		}
	}

	/**
	 * Create the CapabilityTester Agent class file
	 * 
	 * @param capName current capability name
	 * @param capsDir target capability dir
	 * @param agentName agent name
	 * @param agentPkg agent package
	 * @param capsPkg capability package
	 * @param utilPkg util package
	 * @param fsmName current FSM name
	 * 
	 * @throws CodeGenerationException 
	 */
	public void createCapabilityTesterAgentClass( String capName, File capsDir, String agentName, 
			String agentPkg, String capsPkg, String utilPkg, String fsmName ) throws CodeGenerationException {
//		ATCConsole.println( "Generating class " + agentClassNameInputTemplate + 
//				"for agent " + agentName + " and capapility " + capName );

		String file = readFileAsString( inputFolder + TESTER_AGENT_TEMPLATE_INPUT_NAME );
		
		//update tags
		file = file.replace( FSM_NAME_TAG, fsmName );
		file = file.replace( AGENT_PACKAGE_NAME_TAG, agentPkg );
		file = file.replace( UTIL_PACKAGE_NAME_TAG,  utilPkg );
		file = file.replace( CAPABILITIES_PACKAGE_NAME_TAG, capsPkg );
		file = file.replace( CAPABILITY_TESTER_AGENT_TAG, (PathLocation.CAPABILITY_TESTER_AGENT_NAME_PREFIX + capName) );
		
		writeFile( file, capsDir + "/" + PathLocation.CAPABILITY_TESTER_AGENT_NAME_PREFIX + capName + ".java" );
	}

	/**
	 * Creates the CapabilitiesAgent. This Agent listens to requests for 
	 * performing a capability, loads it using Class.forName and starts the 
	 * capability behaviour.
	 * Sends AGREE/REFUSE/NOT-UNTERSTOOD messages, but does not monitor the 
	 * capability lifecycle, so the capability has to give the final 
	 * INFORM/FAILURE response to the requesting agent.
	 * 
	 * @throws CodeGenerationException 
	 */
	public void createCapabilitiesAgentClass( File agentDir, String agentName, String agentPkg, String capsPkg, 
			String utilPkg ) throws CodeGenerationException {
		String classname="/" + PathLocation.CAPABILITY_AGENT_NAME + ".java";
		
//		ATCConsole.println("Generating CapabilitiesAgent class "+ classname +" for agent " + agentName );
		
		String file = readFileAsString( inputFolder + classname );
		
		//update tags
		file = file.replace( AGENT_PACKAGE_NAME_TAG, agentPkg );
		file = file.replace( CAPABILITIES_PACKAGE_NAME_TAG, capsPkg );
		file = file.replace( UTIL_PACKAGE_NAME_TAG, utilPkg );
		
		writeFile( file, agentDir + classname );
	}


	/**
	 * Create the capability/FSM file
	 * 
	 * @param cap current capability
	 * @param capsDir capability target dir
	 * @param agentName agent name
	 * @param agentPkg agent package
	 * @param capsPkg capability package
	 * @param utilPkg utility package
	 * @param fsmName current FSM name
	 * 
	 * @throws CodeGenerationException 
	 */
	

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