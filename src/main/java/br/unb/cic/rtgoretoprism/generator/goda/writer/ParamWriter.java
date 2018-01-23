package br.unb.cic.rtgoretoprism.generator.goda.writer;

import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;

public class ParamWriter {
	
	private final String TEMPLATE_PARAM_BASE_PATH = "PARAM/";
	private static final String PLAN_ID = "$PLAN_ID$";
	
	private String inputPARAMFolder;
	private String planName;
	
	public ParamWriter(String input, String planName) {

		this.inputPARAMFolder = input + "/" + TEMPLATE_PARAM_BASE_PATH;
		this.planName = planName;
	}
	
	public String writeModel() throws CodeGenerationException {

		String model = ManageWriter.readFileAsString(inputPARAMFolder + "modelbody.param");
		model = model.replace(PLAN_ID, planName);

		return model;
	}
}
