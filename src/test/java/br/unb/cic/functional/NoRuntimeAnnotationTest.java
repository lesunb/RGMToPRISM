package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;

public class NoRuntimeAnnotationTest {

	/**
	 * Invalid test case.
	 * 
	 * Runtime Annotation not recognizable in label
	 * 
	 * */
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase21() {
		String goalName = "G0:_sem_colchetes_G1;G2";
		String[] elementsName = {"G1:_folha_um", "1", "1",
				"G2:_folha_dois", "2", "1"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(2, 2, 2, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, null);

			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");	
						

		}catch(Exception e){
			assertNotNull(e);
		}
	}
	
	@Test
	public void TestCase22() {
		String goalName = "G0:_[G1;G2]_lugar_errado";
		String[] elementsName = {"G1:_folha_um", "1", "1",
				"G2:_folha_dois", "2", "1"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(2, 2, 2, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, null);

			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");	
						
		}catch(Exception e){
			assertNotNull(e);
		}
	}
	
	private void createRegister(String[] elementsName, InformationRegister[] info) {
		
		int aux = 0;
		for(int i = 0; i < elementsName.length/3; i++){
			info[i] = new InformationRegister();
			info[i].id = elementsName[aux];
			info[i].branch = Integer.parseInt(elementsName[aux+1]);
			info[i].depth = Integer.parseInt(elementsName[aux+2]);
			aux = aux + 3;
		}
	}
}
