package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;

public class OperandOperatorTest {

	/**
	 * Invalid test case.
	 * 
	 * Context annotation with invalid operator and/or operand
	 * 
	 * */

	private AgentDefinition agentDefinition;

	@Test
	public void TestCase32() {
		String goalName = "G1:_anotação_de_contexto_incorreta";	
		String contextAnnotation = "assertion condition 2OPERANDO = 7\n";
		String[] elementsName = {"T1:_folha", "1", "1"};

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
		}
	}

	@Test
	public void TestCase33() {
		String goalName = "G1:_anotação_de_contexto_incorreta";
		String[] elementsName = {"T1:_folha", "1", "1"};
		String contextAnnotation = "assertion condition OPERANDO + 7\n";

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
