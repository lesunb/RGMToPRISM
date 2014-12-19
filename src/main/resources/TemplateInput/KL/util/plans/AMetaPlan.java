package $UTIL_PACKAGE.plans;

import java.util.List;

import $UTIL_PACKAGE.components.Components;
import $UTIL_PACKAGE.components.TContrib;
import $UTIL_PACKAGE.components.TGoal;

import jadex.runtime.ICandidateInfo;
import jadex.runtime.Plan;

/**
 * An abstract metaplan to use for the Jadex metaplans.
 * Contains the selection intelligence, that can also be overridden.
 * 
 * @author Mirko Morandini
 */
public abstract class AMetaPlan extends Plan {

	/**
	 * 
	 */
	public AMetaPlan() {
		super();
	}

	@Override
	public void body()	{
		ICandidateInfo[] apps = (ICandidateInfo[])getParameterSet("applicables").getValues();
		ICandidateInfo sel = null;

		sel = selection(apps);
		//getPlan(this) changed to getPlan for compatibility to Jadex 0.96
		getLogger().info("MetaPlan "+this+" started, plan "+sel.getPlan().getName()+" dispatched.");
		getParameterSet("result").addValue(sel);
	}
	
//	example selection method	
//	protected abstract ICandidateInfo selection(ICandidateInfo[] applicables);
//		//this loop should be used to retrieve the applicable plans and all parameters:
//		for(int i=0; i<apps.length; i++){
//			String param = (String)apps[i].getPlan().getParameter("param").getValue();
//			...
//		}
	
	/**
	 * search goals in AND/OR, plans in Means-end in beliefbase
	 * for every goal and every plan found, control contributions
	 * for every goal found, search recursively goals in AND/OR, plans in Means-end in beliefbase
	 */	
	protected ICandidateInfo selection(ICandidateInfo[] applicables) {
		double r, max=-100;
		ICandidateInfo best=null;
		
		for(ICandidateInfo app:applicables){
			r=rate(app);//takes the plan name
			if (r>max){
				max=r;
				best=app;
			}
			//getPlan(this) changed to getPlan for compatibility to Jadex 0.96
			System.out.println("Plan "+app.getPlan().getModelElement().getName()+" rating: "+r);
		}
		
		System.out.println("best plan: "+best);
		System.out.println();
		return best;
	}


	/**
	 * Calculates the softgoal contribution recursively for the whole hierarchy, 
	 * starting from this plan. Relies on the beliefbase definitions to retrieve the goal decompositions.
	 * @return A value in [0..1] that expresses the softgoal rating at this level of the hierarchy.
	 */
	public double rate(ICandidateInfo app) {
		double rating=0;
		//getPlan(this) changed to getPlan for compatibility to Jadex 0.96
		String planname=app.getPlan().getModelElement().getName();//takes the plan name
		System.out.println("Applicable plan: "+planname);
		
		if (planname.startsWith(APlan.PLANPREFIX)) {//MEANS-END
			String plan=planname.substring(APlan.PLANPREFIX.length());
			rating=calcSoftgoalContributions(plan);
			
		} else if (planname.startsWith(APlan.GOALPREFIX)) {//OR-DECOMPOSITION
			//there can be no AND-plan here! (AND-plans can be found only after AND-goals!)
			String goalname=planname.substring(APlan.GOALPREFIX.length());
			//i can retrieve the goal directly, without an expression on beliefbase!
			TGoal goal=Components.getGoal(goalname);
			assert goal!=null;			
			//returns the goal's rating: recursive rating calculation down to the leaf plans.
			rating=goal.getRating(this);
		} else
			System.out.println("WARNING: Jadex Plan Type unknown: "+planname+"!");
		return rating;
	}
	
	/**
	 * Calculates the softgoal contribution for a plan in means-end or for a goal (uses the dispatch-plans).
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public double calcSoftgoalContributions(String name) {
		double contrib=0;
		
		//Write expression directly here!
		List<TContrib> clist = (List<TContrib>)getExpression("query_contributions").execute("$component", name);
		System.out.println("Contribution list for plan "+name+": "+clist);
		
		for (TContrib c:clist){
			contrib+=c.getContribRating();
		}
		
		return contrib;
	}
}