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
		String goalName = "G0: no brackets G1;G2";
		String[] elementsName = {"G1: leaf one", "1", "1",
				"G2: leaf two", "2", "1"};
		
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
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestCase22() {
		String goalName = "G0: [G1;G2] wrong place";
		String[] elementsName = {"G1: leaf one", "1", "1",
				"G2: leaf two", "2", "1"};
		
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
			e.printStackTrace();
		}
	}
	
	private void createRegister(String[] elementsName, InformationRegister[] infoArray) {	
		int index = 0;
		int index_infoArray = 0;
		for (InformationRegister info : infoArray) {
			info = new InformationRegister();
			info.createRegister(elementsName, index);
			index = index + 3;
			
			infoArray[index_infoArray] = info;
			index_infoArray++;
		}
	}
}
