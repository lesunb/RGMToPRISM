package $UTIL_PACKAGE.components;

/**
 * A general Tropos component.
 * @author Mirko Morandini
 */
public abstract class TComp {
	public String name;
	
	public TComp(String name){
		this.name=name;
	}
	
	public abstract boolean isGoal();
	public abstract boolean isPlan();
	public abstract boolean isSoftgoal();
	
	public String getName() {
		return name;
	}
}