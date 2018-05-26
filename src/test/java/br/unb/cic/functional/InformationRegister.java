package br.unb.cic.functional;

public class InformationRegister {
	
	String id;
	int branch;
	int depth;
	
	public InformationRegister() {
		super();
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public int getBranch() {
		return branch;
	}

	private void setBranch(int branch) {
		this.branch = branch;
	}

	public int getDepth() {
		return depth;
	}

	private void setDepth(int depth) {
		this.depth = depth;
	}
	
	public void createRegister(String[] elementsName, int index) {
		
		this.setId(elementsName[index]);
		this.setBranch(Integer.parseInt(elementsName[index+1]));
		this.setDepth(Integer.parseInt(elementsName[index+2]));

	}
}