package br.unb.cic.rtgoretoprism.generator.rtgore.writer;

import it.itc.sra.taom4e.model.core.informalcore.Plan;

import java.util.List;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.generator.rtgore.PrismWriter;

public class DTMCWriter extends PrismWriter{
	
	public DTMCWriter(AgentDefinition ad, List<Plan> capPlan, String input, String output, boolean parametric) {
		super(ad, capPlan, input, output, parametric);
	}
	
	

}
