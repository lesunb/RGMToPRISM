package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;


public class OneChildTest {

	/**
	 * Invalid test case.
	 * 
	 * Goals and/or tasks with only one child and wrong runtime annotation
	 * 
	 * */
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase9() {
		String goalName = "G1: one child [try(T1)?skip:T2]";
		String[] elementsName = {"T1: one child", "1", "1"};
		
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
			e.printStackTrace();
		}
	}

	@Test
	public void TestCase10() {

		String goalName = "G1: task error";
		String[] elementsName = {"T1: one child [T1.1#T1.2]", "1", "1", 
				"T1.1: leaf", "1", "2"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
		
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(3, 0, 1, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, null);
			
			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");
		}catch(Exception e){
			assertNotNull(e);
			e.printStackTrace();
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
