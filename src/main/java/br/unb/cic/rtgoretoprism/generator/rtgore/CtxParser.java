package br.unb.cic.rtgoretoprism.generator.rtgore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import br.unb.cic.CtxRegexBaseVisitor;
import br.unb.cic.CtxRegexLexer;
import br.unb.cic.CtxRegexParser;
import br.unb.cic.CtxRegexParser.CAndContext;
import br.unb.cic.CtxRegexParser.CBoolContext;
import br.unb.cic.CtxRegexParser.CDIFFContext;
import br.unb.cic.CtxRegexParser.CEQContext;
import br.unb.cic.CtxRegexParser.CFloatContext;
import br.unb.cic.CtxRegexParser.CGEContext;
import br.unb.cic.CtxRegexParser.CGTContext;
import br.unb.cic.CtxRegexParser.CLEContext;
import br.unb.cic.CtxRegexParser.CLTContext;
import br.unb.cic.CtxRegexParser.COrContext;
import br.unb.cic.CtxRegexParser.CVarContext;
import br.unb.cic.CtxRegexParser.PrintExprContext;
import br.unb.cic.rtgoretoprism.model.ctx.ContextCondition;
import br.unb.cic.rtgoretoprism.model.ctx.CtxSymbols;

public class CtxParser{
	
	public static void main (String [] args){
		try {
			System.out.println(CtxParser.parseRegex("assertion condition MEMORY<30&PROCESSOR<=80\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object[] parseRegex(String regex) throws IOException{
		//Reading the DSL script
	    InputStream is = new ByteArrayInputStream(regex.getBytes("UTF-8"));
	    
	    //Loading the DSL script into the ANTLR stream.
	    CharStream cs = new ANTLRInputStream(is);
	    
	    //Passing the input to the lexer to create tokens
	    CtxRegexLexer lexer = new CtxRegexLexer(cs);
	    
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    
	    //Passing the tokens to the parser to create the parse trea. 
	    CtxRegexParser parser = new CtxRegexParser(tokens);
	    
	    //Semantic model to be populated
	    //Graph g = new Graph();
	    
	    //Adding the listener to facilitate walking through parse tree. 
	    //parser.addParseListener(new MyCtxRegexBaseListener());
	    
	    //invoking the parser. 
	    //parser.prog();
	    
	    //Graph.printGraph(g);
	    
	    //ParseTreeWalker walker = new ParseTreeWalker();
	    //walker.walk(new MyCtxRegexBaseListener(), parser.prog());
	    
	    ParseTree tree = parser.ctx();
	    CtxFormulaParserVisitor CtxRegexVisitor = new CtxFormulaParserVisitor();
	    return new Object[]{CtxRegexVisitor.ctxVars, CtxRegexVisitor.visit(tree)};
	    
	    //return new Object [] 	{CtxRegexVisitor.memory};
	}
}

class CtxFormulaParserVisitor extends  CtxRegexBaseVisitor<String> {

	Set<String[]> ctxVars = new HashSet<String[]>();
	List<ContextCondition> memory = new ArrayList<ContextCondition>();
	
	@Override
	public String visitPrintExpr(PrintExprContext ctx) {
		return visit(ctx.expr());		
	}
	
	@Override
	public String visitCVar(CVarContext ctx) {
		String var = ctx.VAR().getText();
		if(ctx.getParent() instanceof CAndContext ||
		   ctx.getParent() instanceof COrContext){
			ctxVars.add(new String[]{var, "bool"});
			memory.add(new ContextCondition(var, CtxSymbols.BOOL, "true"));
		}else
			ctxVars.add(new String[]{var, "double"});
		return var;
	}	
	
	@Override
	public String visitCEQ(CEQContext ctx) {
		String var = visit(ctx.expr(0));
		String value = visit(ctx.expr(1));
		memory.add(new ContextCondition(var, CtxSymbols.EQ, value));
		return var + " = " + value;
	}
	
	@Override
	public String visitCDIFF(CDIFFContext ctx) {
		String var = visit(ctx.expr(0));
		String value = visit(ctx.expr(1));
		memory.add(new ContextCondition(var, CtxSymbols.DIFF, value));
		return var + " != " + value;
	}
	
	@Override
	public String visitCLE(CLEContext ctx) {
		String var = visit(ctx.expr(0));
		String value = visit(ctx.expr(1));
		memory.add(new ContextCondition(var, CtxSymbols.LE, value));
		return var + " <= " + value;
	}
	
	@Override
	public String visitCLT(CLTContext ctx) {
		String var = visit(ctx.expr(0));
		String value = visit(ctx.expr(1));
		memory.add(new ContextCondition(var, CtxSymbols.LT, value));
		return var + " < " + value;
	}
	
	@Override
	public String visitCGE(CGEContext ctx) {
		String var = visit(ctx.expr(0));
		String value = visit(ctx.expr(1));
		memory.add(new ContextCondition(var, CtxSymbols.GE, value));
		return var + " >= " + value;
	}
	
	@Override
	public String visitCGT(CGTContext ctx) {
		String var = visit(ctx.expr(0));
		String value = visit(ctx.expr(1));
		memory.add(new ContextCondition(var, CtxSymbols.GT, value));
		return var + " > " + value;
	}
	
	@Override
	public String visitCAnd(CAndContext ctx) {
		String varA = visit(ctx.expr(0));
		String varB = visit(ctx.expr(1));
		return varA + " & " + varB;
	}
	
	@Override
	public String visitCOr(COrContext ctx) {
		String varA = visit(ctx.expr(0));
		String varB = visit(ctx.expr(1));
		return varA + " | " + varB;
	}
	
	@Override
	public String visitCBool(CBoolContext ctx) {		
		return ctx.BOOL().getText();
	}
	
	@Override
	public String visitCFloat(CFloatContext ctx) {		
		return ctx.FLOAT().getText();
	}
	
	private CtxSymbols parseSymbol(String op){
		for(CtxSymbols symbol : CtxSymbols.values())
			if(symbol.toString().equals(op))
				return symbol;
		return null;
	}
}