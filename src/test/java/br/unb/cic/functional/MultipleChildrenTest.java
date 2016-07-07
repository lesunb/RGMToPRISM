package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;


public class MultipleChildrenTest {

	/**
	 * Invalid test case.
	 * 
	 * Goals and/or tasks multiple children and no runtime annotation
	 * 
	 * */
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase11() {
		String goalName = "G1:_dois_filhos";
		String[] elementsName = {"G2:_folha_um", "1", "1", 
				"G3:_folha_dois", "2", "1"};
		
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
	public void TestCase12() {
		String goalName = "G0:_quatro_filhos";
		String[] elementsName = {"G1:_folha_um", "1", "1",
				"G2:_folha_dois", "2", "1",
				"G3:_folha_três", "3", "1",
				"G4:_folha_quatro", "4", "1"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(2, 2, 4, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, null);

			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");						

		}catch(Exception e){
			assertNotNull(e);
		}
	}
	
	@Test
	public void TestCase13() {
		String goalName = "G1:_um_filho";
		String[] elementsName = {"T1:_dois_filhos", "1", "1",
				"T1.1:_folha_um", "1", "2",
				"T1.2:_folha_dois", "2", "2"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(3, 0, 2, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, null);

			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");						

		}catch(Exception e){
			assertNotNull(e);
		}					
	}
	
	@Test
	public void TestCase14() {
		String goalName = "G1:_um_filho";
		String[] elementsName = {"T1:_quatro_filhos", "1", "1",
				"T1.1:_folha_um", "1", "2", 
				"T1.2:_folha_dois", "2", "2",
				"T1.3:_folha_três", "3", "2",
				"T1.4:_folha_quatro", "4", "2"};
	
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
		
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(3, 0, 4, "EvaluationActor");
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
