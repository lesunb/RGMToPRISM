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

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public CtxSymbols getOp() {
		return op;
	}

	public void setOp(CtxSymbols op) {
		this.op = op;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
