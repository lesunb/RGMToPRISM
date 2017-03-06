package br.unb.cic.rtgoretoprism.model.ctx;

public class ContextCondition {
	String var;
	CtxSymbols op;
	CtxSymbols type;
	String value;
	
	public ContextCondition(String var, CtxSymbols op, CtxSymbols type, String value) {
		this.var = var;
		this.op = op;
		this.value = value;
		this.type = type;
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
	
	public CtxSymbols getType() {
		return type;
	}

	public void setType(CtxSymbols type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
