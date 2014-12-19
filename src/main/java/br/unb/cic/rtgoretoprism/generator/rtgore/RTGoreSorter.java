package br.unb.cic.rtgoretoprism.generator.rtgore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import br.unb.cic.RTRegexBaseVisitor;
import br.unb.cic.RTRegexLexer;
import br.unb.cic.RTRegexParser;
import br.unb.cic.RTRegexParser.GAltContext;
import br.unb.cic.RTRegexParser.GCardContext;
import br.unb.cic.RTRegexParser.GIdContext;
import br.unb.cic.RTRegexParser.GSkipContext;
import br.unb.cic.RTRegexParser.GTimeContext;
import br.unb.cic.RTRegexParser.GTryContext;
import br.unb.cic.RTRegexParser.ParensContext;
import br.unb.cic.RTRegexParser.PrintExprContext;



public class RTGoreSorter{
	
	public static Object[] parseRegex(String regex) throws IOException{
		//Reading the DSL script
	    InputStream is = new ByteArrayInputStream(regex.getBytes("UTF-8"));
	    
	    //Loading the DSL script into the ANTLR stream.
	    CharStream cs = new ANTLRInputStream(is);
	    
	    //Passing the input to the lexer to create tokens
	    RTRegexLexer lexer = new RTRegexLexer(cs);
	    
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    
	    //Passing the tokens to the parser to create the parse trea. 
	    RTRegexParser parser = new RTRegexParser(tokens);
	    
	    //Semantic model to be populated
	    //Graph g = new Graph();
	    
	    //Adding the listener to facilitate walking through parse tree. 
	    //parser.addParseListener(new MyRTRegexBaseListener());
	    
	    //invoking the parser. 
	    //parser.prog();
	    
	    //Graph.printGraph(g);
	    
	    //ParseTreeWalker walker = new ParseTreeWalker();
	    //walker.walk(new MyRTRegexBaseListener(), parser.prog());
	    
	    ParseTree tree = parser.rt();
	    CustomRTRegexVisitor rtRegexVisitor = new CustomRTRegexVisitor();
	    rtRegexVisitor.visit(tree);
	    
	    return new Object [] 	{rtRegexVisitor.timeMemory, 
	    						rtRegexVisitor.cardMemory, 
	    						rtRegexVisitor.altMemory,
	    						rtRegexVisitor.tryMemory};
	}
}

class CustomRTRegexVisitor extends  RTRegexBaseVisitor<String> {

	Map<String, Integer[]> timeMemory = new HashMap<String, Integer[]>();		
	Map<String, Integer> cardMemory = new HashMap<String, Integer>();
	Map<String, Set<String>> altMemory = new HashMap<String, Set<String>>();
	Map<String, String[]> tryMemory = new HashMap<String, String[]>();
	
	@Override
	public String visitPrintExpr(PrintExprContext ctx) {
		visit(ctx.expr());		
		return "Goals sorted";
	}
	
	@Override
	public String visitGId(GIdContext ctx) {
		String gid = ctx.GID().getText();
		if ( !timeMemory.containsKey(gid) ){
			timeMemory.put(gid, new Integer[]{0,0});			
			cardMemory.put(gid, 0);
		}
		return gid;
	}
	
	
	@Override
	public String visitGTime(GTimeContext ctx) {
		String gidAo = visit(ctx.expr(0));
		String gidBo = visit(ctx.expr(1));
		//String [] gidAs = gidAo.split("-");
		String [] gidBs = gidBo.split("-");
		for(String gidB : gidBs){
			Integer [] pathTimeB = timeMemory.get(gidB);			
			if(ctx.op.getType() == RTRegexParser.PAR){
				pathTimeB[0]++;
			}else if(ctx.op.getType() == RTRegexParser.SEQ)
				pathTimeB[1]++;
		}
		return gidAo + '-' + gidBo;
	}
	
	@Override
	public String visitGAlt(GAltContext ctx) {
		String gidAo = visit(ctx.expr(0));
		String gidBo = visit(ctx.expr(1));
		String [] gidAs = gidAo.split("-");
		String [] gidBs = gidBo.split("-");
		for(String gidA : gidAs){
			for(String gidB : gidBs){				
				if(ctx.op.getType() == RTRegexParser.ALT){
					addToAltSet(gidA, gidB);
					addToAltSet(gidB, gidA);
				}
			}
		}
		return gidAo + '-' + gidBo;
	}
	
	private void addToAltSet(String gid1, String gid2){
		if(altMemory.get(gid1) == null)
			altMemory.put(gid1, new HashSet<String>());
		altMemory.get(gid1).add(gid2);
	}
	
	@Override
	public String visitGCard(GCardContext ctx) {		
		String gid = visit(ctx.expr(0));
		Integer card = 0;
		if(cardMemory.containsKey(gid))
			card = cardMemory.get(gid);
		cardMemory.put(gid, card + Integer.parseInt(ctx.expr(1).getText()));
		return gid;
	}
	
	@Override
	public String visitGTry(GTryContext ctx) {
		String gidT = visit(ctx.expr(0));
		String gidS = visit(ctx.expr(1));
		String gidF = visit(ctx.expr(2));
		Integer [] pathTimeT, pathTimeS, pathTimeF; 
		pathTimeT = timeMemory.get(gidS);
		if(gidS != null){
			pathTimeS = timeMemory.get(gidS);
			pathTimeS[1] = pathTimeS[1] + 1;
		}
		if(gidF != null){
			pathTimeF = timeMemory.get(gidF);
			pathTimeF[1] = pathTimeF[1] + 1;
		}
		tryMemory.put(gidT, new String[]{gidS, gidF});		
		return gidT;
	}
	
	@Override
	public String visitGSkip(GSkipContext ctx) {		
		return null;
	}
	
	@Override
	public String visitParens(ParensContext ctx) {
		return visit(ctx.expr());
	}
	
}
