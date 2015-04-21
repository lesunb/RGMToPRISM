/**
 * <copyright>
 *
 * TAOM4E - Tool for Agent Oriented Modeling for the Eclipse Platform
 * Copyright (C) ITC-IRST, Trento, Italy
 * Authors: Mirko Morandini
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * The electronic copy of the license can be found here:
 * http://sra.itc.it/tools/taom/freesoftware/gpl.txt
 *
 * The contact information:
 * e-mail: taom4e@itc.it
 * site: http://sra.itc.it/tools/taom4e/
 *
 * </copyright>
 */

package br.unb.cic.rtgoretoprism.util.kl;

import it.itc.sra.taom4e.model.core.gencore.BusinessModel;
import it.itc.sra.taom4e.model.core.gencore.TroposModelElement;
import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.BooleanDecLink;
import it.itc.sra.taom4e.model.core.informalcore.BooleanDecomposition;
import it.itc.sra.taom4e.model.core.informalcore.Capability;
import it.itc.sra.taom4e.model.core.informalcore.Contribution;
import it.itc.sra.taom4e.model.core.informalcore.Dependency;
import it.itc.sra.taom4e.model.core.informalcore.Goal;
import it.itc.sra.taom4e.model.core.informalcore.HardGoal;
import it.itc.sra.taom4e.model.core.informalcore.MeansEnd;
import it.itc.sra.taom4e.model.core.informalcore.Plan;
import it.itc.sra.taom4e.model.core.informalcore.SoftGoal;
import it.itc.sra.taom4e.model.core.informalcore.TroposIntentional;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FContribution;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FPlan;
import it.itc.sra.taom4e.model.project.Project;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import br.unb.cic.rtgoretoprism.util.TroposEMFHelper;

/**
 * A class to get easier navigation inside a Tropos model.
 * 
 * Note: in the future (apart from the 1.5 syntax) this class will be merged 
 * with the one inside the Taom4e component 
 * 
 * @author Mirko Morandini
 * @author bertolini
 */
public class TroposNavigator {
	/** current tropos Project */
	private Project prj;
	/** current business model */
	private BusinessModel bm;

	/**
	 * Creates a new TroposNavigator instance
	 * 
	 *  @param tropos source file
	 */
	public TroposNavigator(String filename) {
		//used for I/O functionality
		TroposEMFHelper helper = new TroposEMFHelper();
		
		try {
			//load the source model
			URI loadURI = URI.createFileURI(filename);
			Resource loadRes = helper.loadResource(loadURI);
			
			//get basic objects
			prj = (Project) loadRes.getContents().get(0);
			bm = prj.getBusinessModel();
		} catch (Exception e) {
			System.err.println("Loading exception " + e);
		}
	}
	
	/**
	 * Creates a new TroposNavigator instance 
	 * 
	 * @param res the already created Tropos resource
	 */
	public TroposNavigator( Resource res ) {
		//get basic object
		prj = (Project) res.getContents().get(0);
		bm = prj.getBusinessModel();
	}

	/**
	 * Get the list of actor present into current bm
	 * 
	 * @return the list of actor
	 */
	public List<Actor> getActors() {
		return bm.getChildrenInstancesOfType( Actor.class );
	}

	/**
	 * Get the actor with the specified name
	 * 
	 * @param name the name of the actor to look for
	 * 
	 * @return the actor with the specified name
	 */
	public Actor getActor(String name) {
		List list = getActors();
		
		Iterator it = list.iterator();

		while (it.hasNext()) {
			Actor at = (Actor) it.next();
			if( at.getName().equals(name) ) {
				return at;
			}
		}
		
		return null;
	}

	/**
	 * Get all the SoftGoal of the current actor
	 * 
	 * @param a the actor to query for
	 * 
	 * @return the list of softgoals
	 */
	public List<SoftGoal> getSoftGoals(Actor a) {
		LinkedList<SoftGoal> sglist = new LinkedList<SoftGoal>();
		sglist.addAll( a.getOwnedElementsInstancesOfType( SoftGoal.class ) );
		
		return sglist;
	}

	/**
	 * Get all the HardGoal of the current actor
	 * 
	 * @param a the actor to query for
	 * 
	 * @return the list of hardgoal
	 */
	public List<FHardGoal> getHardGoals(Actor a) {
		LinkedList<FHardGoal> hglist = new LinkedList<FHardGoal>();
		hglist.addAll( a.getOwnedElementsInstancesOfType( FHardGoal.class ) );
		
		return hglist;
	}

	/**
	 * Gets root goals, only by checking if they are not part of a decomposition.
	 * 
	 * @param a the actor to query for
	 * 
	 * @return All root hardgoals.
	 */
	public List<FHardGoal> getRootGoals(Actor a) {
		LinkedList<FHardGoal> rootgoals = new LinkedList<FHardGoal>();
		List<FHardGoal> list = getHardGoals(a);
		
		for( FHardGoal hg : list ) { // jumped if list.size=0
			List allInc = hg.getIncomingRelationsInstancesOfType(BooleanDecLink.class);

			if( allInc.size() == 0 )
				rootgoals.add(hg);
			
//			if (hg.getBoolDecLink().size() == 0)
//				rootgoals.add(hg);
		}
		return rootgoals;
	}

	/**
	 * Gets all plans of an actor
	 * 
	 * @param a The Actor to query for
	 * 
	 * @return A list of Plans
	 */
	public List<Plan> getPlans(Actor a) {
		List<Plan> list = a.getOwnedElementsInstancesOfType( FPlan.class );
		
		return list;
	}

	/**
	 * Get all the root plans for the (not derived anymore) capabilities of 
	 * the specified Actor
	 *  
	 * @param a the Actor to query for
	 * 
	 * @return the list of all the root plan for the (not derived anymore) 
	 * capabilities of the specified Actor
	 */
	public List<Plan> getAllCapabilityPlan( Actor a ) {
		List<Plan> planList = new LinkedList<Plan>();
		
		//all the capabilities of the model
		List<Capability> caps = bm.getCapabilityTable().getCapability();
		
		for( Capability cap : caps ) {
			//get only the 'last' capability of the derived chain
			if( cap.getDerived() == null ) {
				//get the root plan for the capability
				Plan sourcePlan = (Plan) cap.getAbility().getSource();
			
				//verify if the capability is owned by the current Actor
				if( sourcePlan.getActor() == a )
					planList.add( sourcePlan );
			}
		}
		
		return planList;
	}
	
	
	/**
	 * Gets all plans of actor a that are in a direct means-end relatin with 
	 * some goal. This are all the plans that are interesting, because all 
	 * subplans belong to the capability modeling.
	 * 
	 * @param a The Actor to query for
	 * 
	 * @return A list of Plans
	 */
	public List<Plan> getAllMeansEndPlans(Actor a) {
		List<Plan> plist = new LinkedList<Plan>();
		
		List<Plan> list = getPlans(a);

		for (Plan plan : list) {
			List<MeansEnd> mlist = plan.getOutcomingRelationsInstancesOfType( MeansEnd.class );
			
			for( MeansEnd me : mlist ) {
				TroposModelElement target = me.getTarget();
				
				if( target instanceof Goal ) { 
					plist.add(plan);
					break;
				}
			}
			
//			if (plan.getMeansEndMeans().size() > 0) {
//				plist.add(plan);
//			}
		}
		
		return plist;
	}

	/**
	 * Verify if this goal is booleandecomposed
	 * 
	 * @param g the goal to query for
	 * 
	 * @return true if the goal is decomposed, false otherwise
	 */
	public boolean isBooleanDec(TroposIntentional g) {
		return getBooleanDec(g, TroposIntentional.class).size() == 0 ? false : true;
	}

	/**
	 * Verify if this goal is AND decomposed
	 * 
	 * @param g
	 * 
	 * @return
	 */
	public boolean isBooleanDecAND(TroposIntentional g) {
		if( isBooleanDec(g) ) {
			BooleanDecomposition bd = 
				(BooleanDecomposition) g.getSingleOutcomingRelationInstanceOfType(BooleanDecomposition.class);
			if( bd.getType().equals(BooleanDecomposition.TYPE_AND))
				return true;
			
//			if (g.getBooleanDecomposition().getType().equals(BooleanDecomposition.TYPE_AND))
//				return true;
		}
		
		return false;
	}

	/**
	 * Verify if this goal is OR decomposed
	 * 
	 * @param g
	 * 
	 * @return
	 */
	public boolean isBooleanDecOR(TroposIntentional g) {
		if (isBooleanDec(g)) {
			BooleanDecomposition bd = 
				(BooleanDecomposition) g.getSingleOutcomingRelationInstanceOfType(BooleanDecomposition.class);
			if( bd.getType().equals(BooleanDecomposition.TYPE_OR))
				return true;
			
//			if (g.getBooleanDecomposition().getType().equals(BooleanDecomposition.TYPE_OR))
//				return true;
		}
		return false;
	}

	/**
	 * Checks if this goal was delegated from another agent in the earlier 
	 * phases of goal analysis.
	 * 
	 * @param g The goal.
	 * 
	 * @return true if it is the dependum of a dependency link.
	 */
	public boolean isDelegated(Goal g) {
		// if this dependency exists, the goal was delegated from another agent 
		// in the earlier phases of goal analysis
		List deps = g.getIncomingRelationsInstancesOfType( Dependency.class );
		
//		if (g.getDependency().size() > 0)
		
		if( deps.size() > 0 )
			return true;
		return false;
	}

	/**
	 * Verify if this goal is the why for some dependency
	 * 
	 * @param g the goal to test
	 * 
	 * @return
	 */
	public boolean isGoalWhyDependency(Goal g) {
		List whyDeps = g.getOutcomingRelationsInstancesOfType( Dependency.class );
		
		if( whyDeps.size() > 0 )
			return true;
		return false;
		
//		if (g.getDependencyWhy() == null)
//			return false;
//		if (g.getDependencyWhy().size() < 1)
//			return false;
//		return true;
	}

	/**
	 * Returns all dependencies to goals (only goals!) upon which the agent that 
	 * owns g depends and where g is the 'why'. From this dependencies you can 
	 * retrieve the dependent goal (getGoalDependum(), it is assured that this 
	 * returns not null) and the dependee actor (getDependee()).
	 * 
	 * @param g the 'why'-goal.
	 * 
	 * @return the list of dependency
	 */
	public List<Dependency> getGoalDependencies(Goal g) {
		List<Dependency> deplist = new LinkedList<Dependency>();
		
		List whyDeps = g.getOutcomingRelationsInstancesOfType( Dependency.class );
		
//		for (Object o : g.getDependencyWhy()) {
		for (Object o : whyDeps ) {
			if (o instanceof Dependency) {
				Dependency dep = (Dependency) o;
				TroposModelElement dependum = dep.getDependum();

				if( dependum instanceof Goal ) {
//				if (dep.getGoalDependum() != null) {
					deplist.add(dep);
				}
			}
		}
		
		return deplist;
	}
	
	/**
	 * Get the dependee actor for this dependency
	 * 
	 * @param dep the dependency to query from 
	 * 
	 * @return The dependee Actor.
	 */
	public Actor getActorFromDependency(Dependency dep) {
		return (Actor) dep.getDependee();
//		return dep.getDependee();
	}
	
	/**
	 * Returns the dependum goal from a goal dependency previously retrieved with 
	 * 'getGoalDependencies()'.
	 *  
	 * @param dep
	 * 
	 * @return The dependum goal
	 */
	public Goal getDependumGoalFromDependency(Dependency dep) {
		TroposModelElement dependum = dep.getDependum();
		
		if( dependum instanceof Goal )
			return (Goal) dependum;
		
//		if (dep.getGoalDependum()!=null) {
//		return dep.getGoalDependum();
//		}
		
		System.err.println("Program Error: the dependency is not a goal dipendency, " +
				"use only 'getGoalDependencies()' to retrieve the dependency used here!");
		
		return null;
	}
	

	/**
	 * Get the list of 'use' means-end this target plan/goal is involved in
	 * 
	 * @param m the target plan/goal
	 * 
	 * @return the list of 'use' means-end
	 */
	public List<Resource> getUseResources( TroposModelElement m ) {
		 List<Resource> clist = new LinkedList<Resource>();
		 
		 List<MeansEnd> list = m.getIncomingRelationsInstancesOfType( MeansEnd.class );
		 
		 for( MeansEnd c : list) {
			 if( c.getSource() instanceof Resource )
				 clist.add( (Resource) c.getSource() );
		 }
		 
		 return clist;
	 }

//	/**
//	 * Get the list of 'produce' means-end this source plan/goal is involved in
//	 *  
//	 * @param m the source plan/goal
//	 * 
//	 * @return the list of 'produce' means-end
//	 */
//	//Note: this is still not allowed by the current mm implementation!	
//	public List<Resource> getProduceResources( TroposModelElement m ) {
//		 List<Resource> clist = new LinkedList<Resource>();
//		 
//		 List<MeansEnd> list = m.getOutcomingRelationsInstancesOfType( MeansEnd.class );
//		 
//		 for( MeansEnd c : list ) {
//			 if( c.getTarget() instanceof Resource )
//				 clist.add( (Resource) c.getTarget() );
//		 }
//		
//		 return clist;
//	}
	
	
	/**
	 * Gets boolean decomposition goals for a goal (no plans allowed in 
	 * my reduced Tropos!)
	 * 
	 * @param g the goal to query for
	 * 
	 * @return A list of Goals.
	 */
	public List<? extends TroposIntentional> getBooleanDec(TroposIntentional g, Class intentionalClass) {
		LinkedList<TroposIntentional> elements = new LinkedList<TroposIntentional>();

		BooleanDecomposition bd = 
			(BooleanDecomposition) g.getSingleOutcomingRelationInstanceOfType(BooleanDecomposition.class);

//		BooleanDecomposition dec = g.getBooleanDecomposition();
//		if( dec == null)		
		if( bd == null )
			return elements;// goals is empty!
		
		List <TroposIntentional> children = bd.getTargets();

//		List<BooleanDecLink> list = dec.getBooleanDecLink();

//		for (BooleanDecLink link : list) {// jumped if list.size=0
		for( Object child : children ) {// jumped if list.size=0
			if( child instanceof Goal && intentionalClass.equals(FHardGoal.class)) 
				elements.add( (Goal) child );
			else if( child instanceof Plan && intentionalClass.equals(FPlan.class)) 
				elements.add( (Plan) child );
			else if( intentionalClass.equals(TroposIntentional.class)) 
				elements.add( (TroposIntentional) child );
			
//			if (link.getTargetGoal() != null)// I don't want to have Target Plans!
//				goals.add(link.getTargetGoal());
		}
		
		return elements;
	}

	/**
	 * Verify if this goal is involved in a means-end as a target/end
	 * 
	 * @param g the goal to query for
	 * 
	 * @return 
	 */
	public boolean isMeansEndDec(TroposIntentional g) {
		return g.getIncomingRelationsInstancesOfType( MeansEnd.class ).size() != 0;
		
//		return g.getMeansEndEnd().size() < 1 ? false : true;
	}

	/**
	 * Get the list of plan that are involved in a means end (as a means/source)
	 * with the specified goal.
	 * 
	 * @param g the goal to query for
	 * 
	 * @return A list of only Plans.
	 */
	public List<FPlan> getMeansEndMeanPlans(Goal g) {
		LinkedList<FPlan> plans = new LinkedList<FPlan>();
		List<MeansEnd> list = g.getIncomingRelationsInstancesOfType( MeansEnd.class );
		
//		List<MeansEnd> list = g.getMeansEndEnd();

		for (MeansEnd me : list) {// jumped if list.size=0
			if(me.getSource() instanceof FPlan )
				plans.add( (FPlan) me.getSource() );

//			if (me.getMeansPlan() != null)
//				plans.add(me.getMeansPlan());
		}
		return plans;
	}
	
	/**
	 * Get the list of plan that are involved in a means end (as a means/source)
	 * with the specified root plan.
	 * 
	 * @param g the goal to query for
	 * 
	 * @return A list of only Plans.
	 */
	public List<FPlan> getMeansEndMeanPlans(Plan p) {
		LinkedList<FPlan> plans = new LinkedList<FPlan>();
		List<MeansEnd> list = p.getIncomingRelationsInstancesOfType( MeansEnd.class );
		
//		List<MeansEnd> list = g.getMeansEndEnd();

		for (MeansEnd me : list) {// jumped if list.size=0
			if(me.getSource() instanceof FPlan )
				plans.add( (FPlan) me.getSource() );

//			if (me.getMeansPlan() != null)
//				plans.add(me.getMeansPlan());
		}
		return plans;
	}

	/**
	 * Get the list of goals that are involved in a means end (as a means/source)
	 * with the specified goal (usually, OR-decomposition should be used for goals!).
	 * 
	 * @param g the goal to query for
	 * 
	 * @return A list of only Goals.
	 */
	public List<FHardGoal> getMeansEndMeanGoals(FHardGoal g) {
		LinkedList<FHardGoal> goals = new LinkedList<FHardGoal>();
		List<MeansEnd> list = g.getIncomingRelationsInstancesOfType( MeansEnd.class );
		
//		List<MeansEnd> list = g.getMeansEndEnd();

		for (MeansEnd me : list) {// jumped if list.size=0
			if(me.getSource() instanceof Goal )
				goals.add( (FHardGoal) me.getSource() );

//			if (me.getMeansPlan() != null)
//				plans.add(me.getMeansPlan());
		}
		return goals;
	}

//	/**
//	 * Returns all dependencies, without all dependencies with 'why'-argument.
//	 * @param a
//	 * @return
//	 * @deprecated
//	 */
//	public List<Dependency> getGoalDelegations(Actor a) {
//		List<Dependency> delegations = new LinkedList<Dependency>();
//		List<Dependency> deplist = a.getOutcomingRelationsInstancesOfType( Dependency.class );
////		List<Dependency> deplist = a.getDependerDependency();
//		
//		for (Dependency dep : deplist) {
//			TroposModelElement why = dep.getWhy();
//			if( why == null )
//				delegations.add(dep);
//			
////			if (dep.getGoalWhy() == null && dep.getPlanWhy() == null && dep.getResourceWhy() == null)
////				delegations.add(dep);
//		}
//		
//		return delegations;
//	}

	/**
	 * Verify if this modelelement is the source for (at least) a contribution
	 * 
	 * @return
	 */
	public boolean hasContributions(TroposModelElement m) {
		return getContributions(m).size() == 0 ? false : true;
	}

	/**
	 * Get the list of contribution starting from this modelElement
	 * 
	 * @param m the source modeleleemnt
	 * 
	 * @return the list of Contribution starting form this modelElement
	 */
	public List<FContribution> getContributions(TroposModelElement m) {
		List<FContribution> clist = new LinkedList<FContribution>();
		List list = m.getOutcomingRelationsInstancesOfType( Contribution.class );

//		List list = m.getOutcomingBinaryRelations();
	
		for (Object c : list) {
			if (c instanceof FContribution)
				clist.add((FContribution) c);
		}
		
		return clist;
	}

	/**
	 * Get the list of capability for this bm
	 * 
	 * @return the list of capability for this model
	 */
	public List<Capability> getCapabilityTable() {
		return bm.getCapabilityTable().getCapability();
	}	
	

//	public void printContents(String start, List alist) {
//		List<TroposModelElement> list = alist;
//		
//		System.out.print(start + ": ");
//		
//		for (TroposModelElement el : list) {
//			System.out.print(el.getName() + "  ");
//		}
//		System.out.println();
//	}
}