package br.unb.cic.functional;

import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;

class InformationRegister {
	String id;
	int branch;
	int depth;
}

/**
 * Valid test suit.
 * 
 * Correct models for GODA. 
 * 
 * */
public class CorrectModelTest {
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase1() {
		String goalName = "G0: two children [G1#G2]";
		String contextAnnotation = "assertion condition OPERAND = true\n";
		
		//id, branch, depth
		String[] elementsName = {"G1: one child", "1", "1",
				"T1: three children [try(T1.1)?T1.2:T1.3]", "1", "2",
				"T1.1: one child [opt(T1.11)]", "1", "3",
				"T1.11: leaf", "1", "4",
				"T1.2: two children [T1.21#T1.22]", "2", "3",
				"T1.21: leaf one", "1", "4",
				"T1.22: leaf two", "2", "4",
				"T1.3: two", "3", "3",
				"G2: one child [T1@2]", "2", "1",
				"T1: retry twice", "1", "2"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);
		
		try{
			
			//Create CRGM java model		
			CRGMTestProducer producer = new CRGMTestProducer(5, 2, 3, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, contextAnnotation);
			
			//Run Goda
			producer.run(agentDefinition);
		}catch(Exception e){
			fail("Exception found: " + e.getMessage());
		}	
	}
	
	@Test
	public void TestCase2() {
		String goalName = "G0: two children [G1;G2]";
		String contextAnnotation = "assertion condition OPERAND > 10\n";

		//id, branch, depth
		String[] elementsName = {"G1: one child", "1", "1",
				"T1: three children [T1.1#T1.2#T1.3]", "1", "2", 
				"T1.1: leaf one", "1", "3",
				"T1.2: leaf two", "2", "3",
				"T1.3: leaf three", "3", "3",
				"G2: one child [T1#3]", "2", "1",
				"T1: leaf", "1", "2"};
		
		//Add elements to new register
		InformationRegister[] info = new InformationRegister[elementsName.length/3];
		createRegister(elementsName, info);

		try{		
			//Create CRGM java model		
			CRGMTestProducer producer = new CRGMTestProducer(4, 2, 3, "EvaluationActor");
			agentDefinition = producer.generateCRGM(goalName, info, contextAnnotation);
			
			//Run Goda
			producer.run(agentDefinition);
		}catch(Exception e){
			fail("Exception found:" + e.getMessage());
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
