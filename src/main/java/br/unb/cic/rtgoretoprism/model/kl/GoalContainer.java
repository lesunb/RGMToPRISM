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

package br.unb.cic.rtgoretoprism.model.kl;

import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;

import java.util.ArrayList;

/**
 * A container for Goal object
 * 
 * @author Mirko Morandini 
 */
public class GoalContainer extends RTContainer{
	public final Const achieve;

	public Const request;


	private ArrayList<String[]> dependencies;	
	private ArrayList<GoalContainer> parentlist;

	/**
	 * Creates a standard achieve goal with request plan.
	 */
	//public GoalContainer(Goal goal) {
	//	this(goal, Const.REQUEST, Const.ACHIEVE);
	//}

	/**
	 * Creates a new GoalContainer instance
	 *  
	 * @param goal
	 * @param achieve
	 */
	public GoalContainer(FHardGoal goal, Const achieve) {
		super(goal);
		
		this.achieve = achieve;
		this.request = Const.NONE;
	
		parentlist = new ArrayList<GoalContainer>();
		dependencies = new ArrayList<String[]>();
		
		this.setCreationCondition(goal.getCreationProperty());
	}

	/**
	 * 
	 * @param request
	 */
	public void setRequest(Const request) {
		this.request=request;
	}
	
	/**
	 * @param p
	 * @return
	 */
	public PlanContainer addMERealPlan(PlanContainer child) {
		plans.add(child);
		child.setRoot(this);
		if (decomposition == Const.OR || decomposition == Const.ME) {
			assert decomposition == Const.ME;// otherwise there is an error elsewhere!
			// needed to add more triggering goals to one real plan
			child.addMEGoal(this);
		}
		return child;
	}
	
	/**
	 * @param dec
	 * @return
	 */
	public GoalContainer addDecomp(GoalContainer child) {
		goals.add(child);
		child.setRoot(this);
		if (decomposition == Const.OR || decomposition == Const.ME) {
			//mm: 'assert' commented to make ME goals possible
			// assert decomposition == Const.OR;// otherwise there is an error elsewhere!
			// for this goals dispatch-plans are created (not needed for AND-goals)
			// (needed to add more triggering goals to one dispatch plan)
			child.addParent(this);
		}
		return child;
	}

	/**
	 * @return Returns all parent goals (This goal is part of the decomposition of a parent goal).
	 */
	public ArrayList<GoalContainer> getParentGoals() {
		return parentlist;
	}

	/**
	 * @param gc
	 */
	private void addParent(GoalContainer gc) {
		parentlist.add(gc);
	}

	/**
	 * 
	 * @param dependum
	 * @param dependee
	 */
	public void addDependency(String dependum, String dependee) {
		// dependencies.add(dep);
		dependencies.add(new String[] { dependum, dependee });
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String[]> getDependencies() {
		return dependencies;
	}

	/**
	 * @param dep
	 * @return The name of the dependee Actor.
	 */
	public String getActorFromDependency(String[] dep) {
		return dep[1];
	}

	/**
	 * Returns the name of the dependum goal from a goal dependency.
	 * @param dep
	 * @return The name of the dependum goal
	 */
	public String getDependumGoalFromDependency(String[] dep) {
		return dep[0];
	}
	
	public void setRoot(RTContainer root){
		super.setRoot(root);
		setUid(super.getElId());
	}
}