package br.unb.cic.rtgoretoprism.paramformula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolicParamAltGenerator {

	public String getAlternativeFormula (String[] nodes) {
		String xor[] = new String[nodes.length];

		Map<String,String> list = generateList(nodes, xor);

		String param = generateCombinations(list, xor);

		return param;
	}

	public static String generateCombinations (Map<String, String> list, String[] xors) {

		String formula = new String();

		int n = xors.length; 
		for(int num = 0;num < (1 << n);num++) { 
			List<String> combination = new ArrayList<>(); 
			for(int ndx = 0;ndx < n;ndx++) { 
				// (is the bit "on" in this number?) 
				if((num & (1 << ndx)) != 0) { 
					// then it's included in the list 
					combination.add(xors[ndx]); 
				} 
			} 

			if (combination.size() > 0) {
				if (formula.length() == 0) {
					formula = printFormula(list, combination);
				}
				else {
					formula = formula + printFormula(list, combination);	
				}
			}
		}
		return formula + " ";
	}

	private static String printFormula(Map<String, String> list, List<String> combination) {

		String[] nodes = new String[combination.size()];
		String all_xors = new String();

		int index = 0;
		for(String elem : combination) {
			if (all_xors.length() == 0) {
				all_xors = elem;
			}
			else {
				all_xors = all_xors.concat(" * " + elem);
			}
			nodes[index] = list.get(elem);
			index++;
		}

		int total = nodes.length;
		String formula = null;
		for (int i = 0; i < total; i++) {
			if (total%2 != 0) {
				//Odd: +
				if (formula == null) {
					formula = " + " + all_xors + " * " + nodes[i];
				}
				else {
					formula = formula + " + " + all_xors + " * " + nodes[i];
				}
			}
			else {
				//Even: -
				if (formula == null) {
					formula = " - " + all_xors + " * " + nodes[i];
				}
				else {
					formula = formula + " - " + all_xors + " * " + nodes[i];
				}
			}
		}
		return formula;
	}

	private static Map<String, String> generateList(String[] nodes, String[] xor_list) {

		Map<String,String> list = new HashMap<String,String>();
		for (int i = 0; i < nodes.length; i++) {
			String xor = "XOR_" + nodes[i];
			xor_list[i] = xor;
			list.put(xor, nodes[i]);
		}
		return list;
	}
}
