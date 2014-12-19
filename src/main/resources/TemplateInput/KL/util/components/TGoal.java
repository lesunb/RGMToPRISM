package $UTIL_PACKAGE.components;


import jadex.runtime.Plan;

import java.util.ArrayList;
import java.util.List;

import $UTIL_PACKAGE.plans.AMetaPlan;


/**
 * A Tropos goal component with methods to get recursively the contributions 
 * rating of a goal-decomposition tree.
 * 
 */
public class TGoal extends TComp {
	private String decomp;//"OR", "AND", "Means-end", "Delegation"
	
	public boolean isSoftgoal() {return false;}
	public boolean isGoal() {return true;}
	public boolean isPlan() {return false;}
	
	public TGoal(String name, String decomp){
		super(name);
		this.decomp=decomp;
	}

	/**
	 * Calculates recursively the contributions rating of a goal-decomposition tree.
	 * @param caller The calling plan itself (usually "this").
	 * @return a floating value (usually in [-1,1]) for the rating.
	 */
	public double getRating(AMetaPlan caller) {
		//calc contribution of the direct softgoal contributions
		double rating=caller.calcSoftgoalContributions(this.name);
		System.out.println("I am in goal "+name+", decomp "+decomp+", contrib. here: "+rating);
		
		if (decomp.equals("ME")){
			List<String> plans=getAllDecompositionPlans(caller);
			double r=-1;
			for(String planname:plans){
				r=Math.max(r,caller.calcSoftgoalContributions(planname));
			}
			rating+=r;
		}else {
			List<TGoal> goals=getAllDecompositionGoals(caller);
			if (decomp.equals("OR")){
				double r=-1;
				for(TGoal goal:goals){
					//recursively calc. rating
					r=Math.max(r,goal.getRating(caller));
				}
				rating+=r;
			}else if (decomp.equals("AND")){
				double r=0;
				for(TGoal goal:goals){
					r+=goal.getRating(caller);
				}
				rating+=r;
			}
			//add delegation
		}
		return rating;
	}

	/**
	 * Gets all child plans from the belief base on Means-end.
	 * @param caller The calling plan itself (usually "this").
	 * @return The planname.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllDecompositionPlans(Plan caller){		
		List execute = (List)caller.getExpression("query_ME_link").execute("$component", name);
		List <String>list = execute;
		return list;
	}
	/**
	 * Gets all child goals from the belief base on AND/OR decomposition.
	 * @param caller The calling plan itself (usually "this").
	 * @return The planname.
	 */
	@SuppressWarnings("unchecked")
	public List<TGoal> getAllDecompositionGoals(Plan caller){
		List execute = (List)caller.getExpression("query_link").execute("$component", name);
		List <TGoal>list = execute;
		return list;
	}
	
	/**
	 * Gets all child goals from the belief base on AND/OR decomposition.
	 * @param caller The calling plan itself (usually "this").
	 * @return The planname.
	 */
	@SuppressWarnings("unchecked")
	public List<TDependency> getAllDependencies(Plan caller){
		//for compatibility with older adf versions
		if (caller.getExpression("query_dependencies")==null)
			return new ArrayList<TDependency>() ;
		//executes the query (new adf version)
		List execute = (List)caller.getExpression("query_dependencies").execute("$component", name);
		List <TDependency>list = execute;
		return list;
	}

	/**
	 * @return Returns the decomposition type ("OR", "AND", "Means-end" or "Delegation").
	 */
	public String getDecomp() {
		return decomp;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
}