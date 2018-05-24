package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;

public class SameIdentifierGoalTest {

	/**
	 * Invalid test case.
	 * 
	 * Two or more goals with the same identifier
	 * 
	 * */
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase15() {
		String goalName = "G1: first";
		String[] elementsName = {"G1: second", "1", "1"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model		
			CRGMTestProducer producer = new CRGMTestProducer(2, 2, 1, "EvaluationActor");
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
	public void TestCase16() {
		String goalName = "G1: first";
		String[] elementsName = {"G1: second", "1", "1",
				"G1: third", "1", "2",
				"G1: fourth", "1", "3"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model		
			CRGMTestProducer producer = new CRGMTestProducer(4, 4, 1, "EvaluationActor");
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
