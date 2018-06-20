package br.unb.cic.rtgoretoprism.generator.goda.writer;

import java.util.*;
import it.itc.sra.taom4e.model.core.informalcore.Plan;
import it.itc.sra.taom4e.model.core.informalcore.Actor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.unb.cic.RTRegexBaseVisitor;
import br.unb.cic.RTRegexLexer;
import br.unb.cic.RTRegexParser;
import br.unb.cic.RTRegexParser.GAltContext;
import br.unb.cic.RTRegexParser.GCardContext;
import br.unb.cic.RTRegexParser.GIdContext;
import br.unb.cic.RTRegexParser.GOptContext;
import br.unb.cic.RTRegexParser.GSkipContext;
import br.unb.cic.RTRegexParser.GTimeContext;
import br.unb.cic.RTRegexParser.GTryContext;
import br.unb.cic.RTRegexParser.ParensContext;
import br.unb.cic.RTRegexParser.PrintExprContext;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.parser.CtxParser;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.ctx.ContextCondition;
import br.unb.cic.rtgoretoprism.model.ctx.CtxSymbols;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import br.unb.cic.rtgoretoprism.util.PathLocation;

public class BPMNWriter{
	private AgentDefinition ad;
	private Actor a;
	public LinkedList listn;
	
	public BPMNWriter(AgentDefinition ad, Actor a){
		this.ad = ad;
		this.a = a;
	}

	public void start(){
		begin();
		running(ad.rootlist.getFirst(), 0);
		end(ad.rootlist.getFirst());
	}

	public void end(RTContainer n){
		System.out.println(getName_noRT(n.getName()));
		System.out.println("[[Terminal_Event]]");	
		System.out.println("======== "+ a.getName() + " ========");	
		System.out.println( "\n=> BPMN created!");
	}

	public void begin(){
		System.out.println("\n=> Starting BPMN Generation Process..." );
		System.out.println("\n======== "+ a.getName() + " ========");	
		System.out.println("[[Initial_Event]]");
	}

	public String getName_forRT(String a){
		String b = a.substring(0, a.indexOf(":"));
		return b;
	}
	public String getName_noRT(String a){
		String b = a.substring(0, a.indexOf(":"));
		if(a.indexOf('[') != -1 && a.indexOf(']') != -1)
			b = a.substring(0, a.indexOf("[")-1);
		else
			b = a.substring(0, a.length());
		return b;
	}

	public boolean ctx_on(RTContainer n){ //return true if n contains context condition
		if(n.getFulfillmentConditions().size()>0)
			return true;
		return false;
	}

	public void get_ctx(RTContainer n, int ctx){ //print context condition
		int index = n.getFulfillmentConditions().size()-1;
		System.out.print("<< Exclusive_Gateway_ctx : ");
		for(int i=0;i<n.getFulfillmentConditions().size()-1-ctx;i++)
			System.out.print("(" + n.getFulfillmentConditions().get(i).substring(n.getFulfillmentConditions().get(i).indexOf("assertion")+10, n.getFulfillmentConditions().get(i).length()) + "?);");
		System.out.print("(" + n.getFulfillmentConditions().get(index-ctx).substring(n.getFulfillmentConditions().get(index-ctx).indexOf("assertion")+10, n.getFulfillmentConditions().get(index-ctx).length()) + "?)");
		System.out.println(">>\n-----------------------No");
		System.out.println("[Final Event]");
		System.out.println("-----------------------Yes");
	}

	public int container_size(RTContainer a){ //returns size of container
		if(a.getDecompGoals().size()>0 && a.getDecompPlans().size()==0)
			return a.getDecompGoals().size();
		else
			return a.getDecompPlans().size();
	}
	
	public RTContainer container_element(RTContainer a, boolean first){ //if first == true > first element, else last
		if(a.getDecompGoals().size()>0 && a.getDecompPlans().size()==0){
			if(first)
				return a.getDecompGoals().getFirst();
			else
				return a.getDecompGoals().getLast(); 
		}else{
			if(first)
				return a.getDecompPlans().getFirst();
			else
				return a.getDecompPlans().getLast();
		}
	}

	public RTContainer container_element(RTContainer a, int element){  //if second element is a integer, take the element 
		if(a.getDecompGoals().size()>0 && a.getDecompPlans().size()==0)
				return a.getDecompGoals().get(element); 
		else
				return a.getDecompPlans().get(element); 
	}
	
	public String take_last(RTContainer n, String name, int ctx){ //takes the last node inside the sub-tree, for [RETURN]
		String rule = n.getRtRegex();
		String back = name;
		if(container_size(n)>0){
			back = getName_noRT(container_element(n,true).getName());
			back = take_last(container_element(n,true), back, ctx);
		}

		if(rule!=null){
			if((rule.indexOf('#') != -1 && n.getDecomposition()==Const.AND) || rule.indexOf('%') != -1)
				back = "PARALLEL_GATEWAY (From RT: " + getName_noRT(n.getName()) + ") ";
			if(rule.indexOf('#') != -1 && n.getDecomposition()==Const.OR)
				back = "INCLUSIVE_GATEWAY (From RT: " + getName_noRT(n.getName()) + ") ";
			if(rule.contains("opt") || rule.indexOf('|') != -1)
				back = "EXCLUSIVE_GATEWAY (From RT: " + getName_noRT(n.getName()) + ") ";
			if(rule.contains("try")){
				for(int i=0;i<container_size(n);i++){
					String args1 = n.getRtRegex().substring(n.getRtRegex().indexOf("(") + 1, n.getRtRegex().indexOf(")"));
					if(args1.equals(getName_forRT(container_element(n,i).getName())))
						back = getName_noRT(container_element(n,i).getName());
				}
			}
			if(container_size(n)>0)
					back = take_last(container_element(n,true), back, ctx);
		}

		if(ctx_on(n)&&(n.getFulfillmentConditions().size()>ctx)){
			ctx+=1000000; // It's to avoid future changes
			back = "EXCLUSIVE_GATEWAY_CTX (From: " + getName_noRT(n.getName()) + ") ";
		}
		return back;
	}

	public void running(RTContainer n, int ctx_aux){
		String rule = n.getRtRegex();
		int ctx=ctx_aux;
		if(ctx_on(n)&&(n.getFulfillmentConditions().size()>ctx)){
			get_ctx(n,ctx);
			ctx+=n.getFulfillmentConditions().size()-ctx;
		}

		if(rule==null)
			rule="none";

		if(rule.indexOf(';') != -1 && n.getDecomposition()==Const.AND){
			sAND(n,ctx);
		}
		if(rule.indexOf('#') != -1 && n.getDecomposition()==Const.AND){
			pAND(n,ctx);
		}

		if(rule.indexOf(';') != -1 && n.getDecomposition()==Const.OR){
			sOR(n,ctx);
		}
 
		if(rule.indexOf('#') != -1 && n.getDecomposition()==Const.OR){
			pOR(n,ctx);
		}

		if(rule.indexOf('+') != -1){
			run_k(n,ctx);
		}

		if(rule.indexOf('%') != -1){
			paralell_k(n,ctx);
		}

		if(rule.indexOf('@') != -1){
			try_k(n,ctx);
		}

		if(rule.contains("opt")){
			optional(n,ctx);
		}

		if(rule.contains("try")){		
			tryn(n,ctx);
		}

		if(rule.indexOf('|') != -1){
			xor(n,ctx);
		}

		if(rule.contains("none") && container_size(n)>0){
			means_end(n,ctx);
		}
	}

	public void sAND(RTContainer n, int ctx){
		for(int i=0;i<container_size(n);i++){
			running(container_element(n,i), ctx);
			System.out.println(getName_noRT(container_element(n,i).getName()));	
		}
	}

	public void pAND(RTContainer n, int ctx){
		System.out.println("<< Parallel_Gateway >>");
		for(int i=0;i<container_size(n);i++){
			System.out.println("-----------------------Process " + (i+1));
			running(container_element(n,i), ctx);
			System.out.println(getName_noRT(container_element(n,i).getName()));
		}
		System.out.println("<< End Paralell_Gateway >>");
	}

	public void sOR(RTContainer n, int ctx){
		for(int i=0;i<container_size(n);i++){
			if(i>0){
				System.out.println("<< Exclusive_Gateway : Do you want to perform " + getName_noRT(container_element(n,i).getName())+ "? >>");
				System.out.println("-----------------------Yes");
			}
			running(container_element(n,i), ctx);
			System.out.println(getName_noRT(container_element(n,i).getName()));
			if(i>0){
				System.out.println("-----------------------No");
				System.out.println("<< End Exclusive_Gateway >>");
			}
		}
	}

	public void pOR(RTContainer n, int ctx){
		System.out.println("<< Inclusive_Gateway >>");
		for(int i=0;i<container_size(n);i++){
			System.out.println("-----------------------Process " + (i+1));
			running(container_element(n,i), ctx);
			System.out.println(getName_noRT(container_element(n,i).getName()));
		}
		System.out.println("<< End Inclusive_Gateway >>");
	}

	public void run_k(RTContainer n, int ctx){ //n+k
		String args = n.getRtRegex().substring(n.getRtRegex().indexOf("+") + 1, n.getRtRegex().length());
		int k = Integer.parseInt(args);
		running(container_element(n,0), ctx);
		System.out.println(getName_noRT(container_element(n,0).getName()));
		System.out.println("<< Exclusive_Gateway : " + getName_noRT(container_element(n,0).getName()) + " was satisfied " + k + " times? >>");
		System.out.println("-----------------------No");
		System.out.println("[ RETURN TO " + take_last(n, getName_noRT(n.getName()), ctx) +"]");
		System.out.println("-----------------------Yes");
	}

	public void paralell_k(RTContainer n, int ctx){ //n#k
		String args = n.getRtRegex().substring(n.getRtRegex().indexOf("%") + 1, n.getRtRegex().length());
		int k = Integer.parseInt(args);
		System.out.println("<< Parallel_Gateway >>");
		for(int i=0;i<k;i++){
			System.out.println("-----------------------Process " + (i+1));
			running(container_element(n,0), ctx);
			System.out.println(getName_noRT(container_element(n,0).getName()));
		}
		System.out.println("<< End Parallel_Gateway >>");
	}

	public void try_k(RTContainer n, int ctx){ //n@k
		String args = n.getRtRegex().substring(n.getRtRegex().indexOf("@") + 1, n.getRtRegex().length());
		int k = Integer.parseInt(args);
		running(container_element(n,0), ctx);
		System.out.println(getName_noRT(container_element(n,0).getName()));		
		System.out.println("<< Exclusive_Gateway : " + getName_noRT(container_element(n,0).getName()) + " was satisfied? >>");
		System.out.println("-----------------------No"); 
		System.out.println("<< Exclusive_Gateway : Attemp < " + k + "? >>");
		System.out.println("-----------------------Yes");
		System.out.println("[ RETURN TO " + take_last(n, getName_noRT(n.getName()),ctx) +"]");
		System.out.println("-----------------------No");
		System.out.println("[Final Event]");
		System.out.println("<< End Exclusive_Gateway >>");
		System.out.println("-----------------------Yes");
		System.out.println("<< End Exclusive_Gateway >>");
	}

	public void optional(RTContainer n, int ctx){
			System.out.println("<< Exclusive_Gateway : Do you want to perform " + getName_noRT(container_element(n,0).getName()) + "? >>");		
			System.out.println("-----------------------Yes");
			running(container_element(n,0), ctx);
			System.out.println(getName_noRT(container_element(n,0).getName()));
			System.out.println("-----------------------No");
			System.out.println("<< End Exclusive_Gateway >>");
	}

	public void tryn(RTContainer n, int ctx){ //try():
		String args1 = n.getRtRegex().substring(n.getRtRegex().indexOf("(") + 1, n.getRtRegex().indexOf(")"));
		String args2 = n.getRtRegex().substring(n.getRtRegex().indexOf("?") + 1, n.getRtRegex().indexOf(":"));
		String args3 = n.getRtRegex().substring(n.getRtRegex().indexOf(":") + 1, n.getRtRegex().length());
		for(int i=0;i<container_size(n);i++){
			if(args1.equals(getName_forRT(container_element(n,i).getName()))){
				running(container_element(n,i), ctx);
				System.out.println(getName_noRT(container_element(n,i).getName()));
				System.out.println("<< Exclusive_Gateway : " + getName_noRT(container_element(n,i).getName()) + " was satisfied? >>");
			}
		}
		
		System.out.println("-----------------------Yes");
		if(args2.equals("skip")){
			skip();
		}
		else{
			for(int i=0;i<container_size(n);i++){
				if(args2.equals(getName_forRT(container_element(n,i).getName()))){
					running(container_element(n,i), ctx);
					System.out.println(getName_noRT(container_element(n,i).getName()));
				}
			
			}
		}

		System.out.println("-----------------------No");
		if(args3.equals("skip")){
			skip();
		}else{
			for(int i=0;i<container_size(n);i++){
				if(args3.equals(getName_forRT(container_element(n,i).getName()))){
					running(container_element(n,i), ctx);
					System.out.println(getName_noRT(container_element(n,i).getName()));
				}
			}
		}
		System.out.println("<< End Exclusive_Gateway >>");
	}

	public void xor(RTContainer n, int ctx){
		System.out.println("<< Exclusive_Gateway (X) >>");
		for(int i=0;i<container_size(n);i++){
			System.out.println("-----------------------Process " + (i+1));
			running(container_element(n,i), ctx);
			System.out.println(getName_noRT(container_element(n,i).getName()));
		}
		System.out.println("<< End Exclusive_Gateway (X) >>");
	}

	public void skip(){
		System.out.println("[Skip]");
	}

	public void means_end(RTContainer n, int ctx){
		for(int i=0;i<container_size(n);i++){
			running(container_element(n,i), ctx);
			System.out.println(getName_noRT(container_element(n,i).getName()));
		}
	}
}
