package $UTIL_PACKAGE.components;


import jadex.util.Tuple;

/**
 * A Tropos contribution link to a Softgoal.
 * Can define fixed values for the different contribution modes (++ to --)
 * 
 *  @author Mirko Morandini
 */
public class TDependency extends Tuple {
	public TDependency(String why, String dependum, String dependee){
		super(new Object[]{why, dependum, dependee});
	}

	public String getWhyGoal(){
		return (String)get(0);
	}
	public String getDependumGoal(){
		return (String)get(1);
	}
	public String getDependeeActor(){
		return (String)get(2);
	}
}