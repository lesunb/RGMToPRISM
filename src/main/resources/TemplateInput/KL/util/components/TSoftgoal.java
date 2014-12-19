package $UTIL_PACKAGE.components;

/**
 * @author Mirko Morandini
 */
public class TSoftgoal extends TComp {
	private double imp;

	public TSoftgoal(String name, double importance){
		super(name);
		imp=importance;
	}

	public boolean isSoftgoal() {return true;}
	public boolean isGoal() {return false;}
	public boolean isPlan() {return false;}

	/**
	 * @return
	 */
	public double getImportance() {
		return imp;
	}
	public void setImportance(double imp) {
		this.imp=imp;
	}
}