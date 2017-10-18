package br.unb.cic.rtgoretoprism.generator.goda.writer;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.util.PathLocation;

public class ParamWriter {
	
	private final String TEMPLATE_PARAM_BASE_PATH = "PARAM/";
	private static final String PLAN_ID = "$PLAN_ID$";
	
	private String agentOutputFolder;
	private String inputPARAMFolder;
	private String planName;
	private String model;
	
	public ParamWriter(String input, String output, String agentName, String planName) {

		this.inputPARAMFolder = input + "/" + TEMPLATE_PARAM_BASE_PATH;
		this.agentOutputFolder = 
				output + "/" + PathLocation.BASIC_AGENT_PACKAGE_PREFIX + agentName + "/";
		this.planName = planName;
	}
	
	public PrintWriter writeModel() throws CodeGenerationException {

		model = ManageWriter.readFileAsString(inputPARAMFolder + "modelbody.param");
		model = model.replace(PLAN_ID, planName);
		
		PrintWriter modelFile = ManageWriter.createFile(planName + ".param", agentOutputFolder);
		ManageWriter.printModel(modelFile, model);
		
		return modelFile;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public void deleteModel() throws CodeGenerationException {
		
		String paramPath = agentOutputFolder + planName + ".param";
		Path path = Paths.get(paramPath);
		
		ManageWriter.deleteFile(path);
	}
}
