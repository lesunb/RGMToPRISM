package br.unb.cic.rtgoretoprism.generator.goda.writer.dtmc;

import it.itc.sra.taom4e.model.core.informalcore.Plan;

import java.util.List;

import br.unb.cic.rtgoretoprism.generator.goda.writer.PrismWriter;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;

public class DTMCWriter extends PrismWriter{
	
	public DTMCWriter(AgentDefinition ad, List<Plan> capPlan, String input, String output, boolean parametric) {
		super(ad, capPlan, input, output, parametric);
	}
	
	

}
