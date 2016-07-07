package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;


public class NoChildTest {

	/**
	 * Invalid test case.
	 * 
	 * Goals and/or tasks no child and runtime annotation
	 * 
	 * */
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase7() {
		String goalName = "G1:_nenhum_filho_[T1@2]";
		
		//Add elements to new register
		InformationRegister[] info = null;
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(1, 0, 1, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, null);

			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");
						

		}catch(Exception e){
			assertNotNull(e);
		}
	}
	
	@Test
	public void TestCase8() {
		String goalName = "G1:_erro_na_tarefa";
		String[] elementsName = {"T1:_nenhum_filho_[T1.1@2]", "1", "1"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(2, 0, 1, "EvaluationActor");
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
