package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;


public class SameIdentifierTaskTest {

	/**
	 * Invalid test case.
	 * 
	 * Two or more tasks with the same identifier
	 * 
	 * */
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase17() {
		String goalName = "G1:_um_filho";
		String[] elementsName = {"T1:_um_filho", "1", "1",
				"T1.1:_dois_filhos_[T1.11;T1.11]", "1", "2",
				"T1.11:_folha_um", "1", "3",
				"T1.11:_folha_dois", "2", "3"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
	
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(4, 0, 2, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, null);

			//Run Goda
			producer.run(agentDefinition);
			
			fail("No exception found.");			

		}catch(Exception e){
			assertNotNull(e);
		}
	}
	
	@Test
	public void TestCase18() {
		String goalName = "G1:_um_filho";
		String[] elementsName = {"T1:_três_filhos[T1.1;T1.1;T1.1]", "1", "1",
				"T1.1:_folha_um", "1", "2",
				"T1.1:_folha_um", "2", "2",
				"T1.1:_folha_um", "3", "2"};
	
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
		
		try{
			
			//Create CRGM java model
			CRGMTestProducer producer = new CRGMTestProducer(3, 0, 3, "EvaluationActor");
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
