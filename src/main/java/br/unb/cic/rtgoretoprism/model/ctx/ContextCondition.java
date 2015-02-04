package br.unb.cic.rtgoretoprism.model.ctx;

public class ContextCondition {
	String var;
	CtxSymbols op;
	String value;
	
	public ContextCondition(String var, CtxSymbols op, String value) {
		this.var = var;
		this.op = op;
		this.value = value;
	}
}
