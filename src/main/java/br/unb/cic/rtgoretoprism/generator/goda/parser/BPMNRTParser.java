package br.unb.cic.rtgoretoprism.generator.goda.parser;

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
import br.unb.cic.RTRegexParser.GOptContext;
import br.unb.cic.RTRegexParser.GSkipContext;
import br.unb.cic.RTRegexParser.GTimeContext;
import br.unb.cic.RTRegexParser.GTryContext;
import br.unb.cic.RTRegexParser.ParensContext;
import br.unb.cic.RTRegexParser.PrintExprContext;
import br.unb.cic.rtgoretoprism.model.kl.Const;



public class BPMNRTParser{
	
	public static Object[] parseRegex(String uid, String regex) throws IOException{
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
	    BPMNCustomRTRegexVisitor rtRegexVisitor = new BPMNCustomRTRegexVisitor(uid);
	    rtRegexVisitor.visit(tree);
	    return new Object [] 	{rtRegexVisitor.timeMemory, 
	    						rtRegexVisitor.cardMemory, 
	    						rtRegexVisitor.altMemory,
	    						rtRegexVisitor.tryMemory,
	    						rtRegexVisitor.optMemory};
	}
}

class BPMNCustomRTRegexVisitor extends  RTRegexBaseVisitor<String> {

	final String uid;
	Map<String, Boolean[]> timeMemory = new HashMap<String, Boolean[]>();		
	Map<String, Object[]> cardMemory = new HashMap<String, Object[]>();
	Map<String, Set<String>> altMemory = new HashMap<String, Set<String>>();
	Map<String, String[]> tryMemory = new HashMap<String, String[]>();
	Map<String, Boolean> optMemory = new HashMap<String, Boolean>();
	
	public BPMNCustomRTRegexVisitor(String uid) {
		this.uid = uid;
	}
	
	@Override
	public String visitPrintExpr(PrintExprContext ctx) {
		visit(ctx.expr());		
		return "Goals sorted";
	}
	
	@Override
	public String visitGId(GIdContext ctx) {
		String gid = ctx.t.getText() + ctx.FLOAT().toString();
		if(ctx.t.getType() == RTRegexParser.TASK)
			gid = uid + '_' + gid;
		if ( !timeMemory.containsKey(gid) ){
			timeMemory.put(gid, new Boolean[]{false,false});			
			//cardMemory.put(gid, 1);
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
			Boolean [] pathTimeB = timeMemory.get(gidB);			
			if(ctx.op.getType() == RTRegexParser.INT){
				pathTimeB[0] = true;
			}else if(ctx.op.getType() == RTRegexParser.SEQ){
				pathTimeB[1] = true;
			}
		}
		return gidAo + '-' + gidBo;
	}
	
	@Override
	public String visitGAlt(GAltContext ctx) {
		String gidAo = visit(ctx.expr(0));
		String gidBo = visit(ctx.expr(1));
		String [] gidAs = gidAo.split("-");
		String [] gidBs = gidBo.split("-");		
		
		for(String gidB : gidBs){				
			for(String gidA : gidAs){
				if(ctx.op.getType() == RTRegexParser.ALT){
					addToAltSet(gidA, gidB);
					//addToAltSet(gidB, gidA);
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
	public String visitGOpt(GOptContext ctx) {
		String gId = super.visit(ctx.expr());
		optMemory.put(gId, true);
		return gId;
	}
	
	@Override
	public String visitGCard(GCardContext ctx) {		
		String gid = visit(ctx.expr());
		if(ctx.op.getType() == RTRegexParser.C_INT)
			cardMemory.put(gid, new Object[]{Const.INT,Integer.parseInt(ctx.FLOAT().getText())});
		else if(ctx.op.getType() == RTRegexParser.C_SEQ)
			cardMemory.put(gid, new Object[]{Const.SEQ,Integer.parseInt(ctx.FLOAT().getText())});
		else
			cardMemory.put(gid, new Object[]{Const.RTRY,Integer.parseInt(ctx.FLOAT().getText())});
		return gid;
	}
	
	@Override
	public String visitGTry(GTryContext ctx) {
		String gidT = visit(ctx.expr(0));
		String gidS = visit(ctx.expr(1));
		String gidF = visit(ctx.expr(2));
		Boolean [] pathTimeS, pathTimeF; 
		if(gidS != null){
			pathTimeS = timeMemory.get(gidS);
			pathTimeS[1] = pathTimeS[1] = true;
		}
		if(gidF != null){
			pathTimeF = timeMemory.get(gidF);
			pathTimeF[1] = pathTimeF[1] = true;
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
