package br.unb.cic.functional;

import static org.junit.Assert.fail;

import org.junit.Test;

import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;

/**
 * Falta:
 * 		criar todos os modelos
 * 		chamar a execução de todos os testCase
 * 
 * */

class InformationRegister {
	String id;
	int branch;
	int depth;
}

public class CorrectModelTest {


	/**
	 * Valid test suit.
	 * 
	 * Correct models for GODA. 
	 * 
	 * */
	
	private AgentDefinition agentDefinition;
	
	@Test
	public void TestCase1() {
		String goalName = "G0:_dois_filhos_[G1#G2]";
		String contextAnnotation = "assertion condition OPERANDO = true\n";
		
		//id, branch, depth
		String[] elementsName = {"G1:_um_filho", "1", "1",
				"T1:_três_filhos_[try(T1.1)?T1.2:T1.3]", "1", "2",
				"T1.1:_um_filho_[opt(T1.11)]", "1", "3",
				"T1.11:_folha", "1", "4",
				"T1.2:_dois_filhos_[T1.21#T1.22]", "2", "3",
				"T1.21:_folha_um", "1", "4",
				"T1.22:_folha_dois", "2", "4",
				"T1.3:_folha", "3", "3",
				"G2:_um_filho_[T1@2]", "2", "1",
				"T1:_retry_duas_vezes", "1", "2"};
		
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
			fail("Exception found: " + e);
		}	
	}
	
	@Test
	public void TestCase2() {
		String goalName = "G0:_dois_filhos_[G1;G2]";
		String contextAnnotation = "assertion condition OPERANDO > 10\n";

		//id, branch, depth
		String[] elementsName = {"G1:_um_filho", "1", "1",
				"T1:_três_filhos_[T1.1#T1.2#T1.3]", "1", "2", 
				"T1.1:_folha_um", "1", "3",
				"T1.2:_folha_dois", "2", "3",
				"T1.3:_folha_três", "3", "3",
				"G2:_um_filho_[T1#3]", "2", "1",
				"T1:_folha", "1", "2"};
		
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
			fail("Exception found:" + e);
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
