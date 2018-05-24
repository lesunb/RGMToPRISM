package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;

public class ContextAnnotationValueTest {
	
	/**
	 * Invalid test case.
	 * 
	 * Context annotation with invalid value
	 * 
	 * */
	
	private AgentDefinition agentDefinition;

	@Test
	public void TestCase34() {
		String goalName = "G1: invalid context annotation";
		String[] elementsName = {"T1: leaf", "1", "1"};
		String contextAnnotation = "assertion condition OPERAND != value\n";
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(2, 0, 1, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, contextAnnotation);

			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");					

		}catch(Exception e){
			assertNotNull(e);
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestCase35() {
		String goalName = "G1: invalid context annotation";
		String[] elementsName = {"T1: task", "1", "1"};
		String contextAnnotation = "assertion condition OPERAND >= A\n";
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(2, 0, 1, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, contextAnnotation);

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
