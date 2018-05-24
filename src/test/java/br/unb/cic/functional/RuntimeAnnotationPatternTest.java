package br.unb.cic.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;


public class RuntimeAnnotationPatternTest {

	/**
	 * Invalid test case.
	 * 
	 * Runtime Annotation written with wrong pattern
	 * 
	 * */

	private AgentDefinition agentDefinition;

	@Test
	public void TestCase25() {
		String goalName = "G0: two children [G1;]";
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
	public void TestCase26() {
		String goalName = "G0: two chlidren [G1#]";
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
	public void TestCase27() {
		String goalName = "G1: one child [T1+]";
		String[] elementsName = {"T1: leaf", "1", "1"};

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
	public void TestCase28() {
		String goalName = "G1: one child [T1@]";
		String[] elementsName = {"T1: leaf", "1", "1"};

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
	public void TestCase29() {
		String goalName = "G0: two children [G1|]";
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
	public void TestCase30() {
		String goalName = "G1: one child [opt()]";
		String[] elementsName = {"T1: leaf", "1", "1"};

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
	public void TestCase31() {
		String goalName = "G1: one child";
		String[] elementsName = {"T1: three children [try(T1.1)?T1.2]", "1", "1",
				"T1.1: leaf one", "1", "2",
				"T1.2: leaf two", "2", "2",
				"T1.3: leaf three", "3", "2"};

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
