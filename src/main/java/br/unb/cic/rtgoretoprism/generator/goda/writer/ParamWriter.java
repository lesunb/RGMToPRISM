package br.unb.cic.rtgoretoprism.generator.goda.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import br.unb.cic.rtgoretoprism.util.PathLocation;

public class ParamWriter {
	
	private final String TEMPLATE_PARAM_BASE_PATH = "PARAM/";
	private final String CONST_INITIAL_MODULE = "const double rTask";
	private final String PARAM_INITIAL_MODULE = "param double rTask";
	private final String END_MODULE = "endmodule";
	
	private String agentOutputFolder;
	private String inputPARAMFolder;
	private String planName;
	private String agentName;

	private String header, body, model;
	
	public ParamWriter(String input, String output, String agentName, String planName) {

		//Recuperar a pasta criada pelo PRISM
		this.inputPARAMFolder = input + "/" + TEMPLATE_PARAM_BASE_PATH;
		this.agentOutputFolder = 
				output + "/" + PathLocation.BASIC_AGENT_PACKAGE_PREFIX + agentName + "/";
		this.planName = planName;
		this.agentName = agentName;
	}
	
	public void writeModel() throws CodeGenerationException {
		
		String paramInputFolder = inputPARAMFolder;
		String prismFile = agentOutputFolder + agentName + ".pm";
		
		header = readFileAsString( paramInputFolder + "modelheader.param" );
		body = readFileAsString( prismFile );
		
		String planModule = getPlanModule(body, planName);
		
		PrintWriter modelFile = createParamFile();
		printModel(modelFile, planModule);
		
	}
	
	public String getModel() {
		return model;
	}
	
	private String getPlanModule(String body, String planName) {

		String initString = CONST_INITIAL_MODULE + planName;
		String endString  = END_MODULE;
		
		int initIndex = body.indexOf(initString);
		int endIndex = body.indexOf(endString, initIndex);
		
		String newBody = body.substring(initIndex, endIndex) + "\n" + END_MODULE;
		
		String replace = initString + "=0.99;";
		String replacement = PARAM_INITIAL_MODULE + planName + ";";		
		newBody = newBody.replaceAll(replace, replacement);

		return newBody;
	}

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
	
	private PrintWriter createParamFile() throws CodeGenerationException {
		try {
			String adf = planName + ".param";
			PrintWriter adfFile = new PrintWriter( 
					new BufferedWriter(	new FileWriter( agentOutputFolder + adf ) ) );
			
			return adfFile;
		} catch (IOException e) {
			String msg = "Error: Can't create output model file.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}
	
	private void printModel(PrintWriter adf, String planModule) {

		model = header + planModule;
		
		adf.println(header);
		adf.print(planModule);

		adf.close();
	}
}
