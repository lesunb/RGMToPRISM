/**
 * <copyright>
 *
 * TAOM4E - Tool for Agent Oriented Modeling for the Eclipse Platform
 * Copyright (C) ITC-IRST, Trento, Italy
 * Author: Mirko Morandini
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

package br.unb.cic.rtgoretoprism.generator.kl;

import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.Goal;
import it.itc.sra.taom4e.model.core.informalcore.Plan;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FPlan;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FSoftGoal;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.ElementContainer;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import br.unb.cic.rtgoretoprism.model.kl.SoftgoalContainer;
import br.unb.cic.rtgoretoprism.util.NameUtility;

/**
 * A class that maintain the soft/hard/plan base and rootlist for a 
 * translating Agent
 * 
 * @author Mirko Morandini
 */
public class AgentDefinition {
	/** name of the agent we are representing */
	private String agentname;
	
	public LinkedList<GoalContainer> rootlist = new LinkedList<GoalContainer>();
	
	public Hashtable<String, SoftgoalContainer> softgoalbase;
	public Hashtable<String, GoalContainer> goalbase;
	public Hashtable<String, PlanContainer> planbase;
	
	/**
	 * Creates a new AgentDefinition instance
	 * 
	 *  @param a the Actor we want to generate code for
	 */
	public AgentDefinition( Actor a ) {
		softgoalbase = new Hashtable<String, SoftgoalContainer>();
		goalbase = new Hashtable<String, GoalContainer>();
		planbase = new Hashtable<String, PlanContainer>();
		
		agentname = NameUtility.adjustName( a.getName() );
	}

	/**
	 * Add a root goal container to the list
	 * 
	 * @param rootgoal the goalcontainer to add
	 */
	public void addRootGoal(GoalContainer rootgoal) {
		rootlist.add(rootgoal);
	}
	
	/**
	 * Get the list of rootGoalContainer
	 * 
	 * @return the list of rootGoalContainer
	 */
	public List<GoalContainer> getRootGoalList() {
		return rootlist;
	}

	/**
	 * Get/Create the softGoalcontainer for the specified softGoal
	 * 
	 * @param sg the softgoal to add to the softgoalbase
	 * 
	 * @return the (possibly) created softgoalcontainer
	 */
	public SoftgoalContainer createSoftgoal(FSoftGoal sg) {
		//needed here to get shurely the right name!
		SoftgoalContainer sgc = new SoftgoalContainer(sg);
		
		//if already exist, return the base' one
		if (softgoalbase.containsKey(sgc.getName() ))
				return softgoalbase.get(sgc.getName());//just created sgc disposed!
		
		//update softgoalbase
		softgoalbase.put(sgc.getName(), sgc);
		
		return sgc;
	}
	
	/**
	 * Get/Create the hardGoalContainer for the specified hardGoal
	 * 
	 * @param goal the hardgoal to add to the hardGoalbase
	 * @param type hardgoal type
	 * 
	 * @return the (possibly) created goalcontainer
	 */
	public GoalContainer createGoal(FHardGoal goal, Const type) {
		GoalContainer gc = new GoalContainer(goal, type);
		setRTAttributes(gc);
		
		//if already exist, return the base' one
		if (goalbase.containsKey(gc.getName()))
			return goalbase.get(gc.getName());
		
		//updated hardgoalbase
		goalbase.put(gc.getName(), gc);
		
		return gc;		
	}
	
	/**
	 * Get/Create the plangoalContainer for the specified plan
	 * 
	 * @param p the plan to add to the planbase
	 * 
	 * @return the (possibliy) created plancontainer
	 */
	public PlanContainer createPlan(FPlan p) {
		PlanContainer pc = new PlanContainer(p);
		
		setRTAttributes(pc);
		
		//if already exist, return the base' one
		if (planbase.containsKey(pc.getName() ))
			return planbase.get(pc.getName());
		
		//updated planbase
		planbase.put(pc.getName(), pc);
		
		return pc;
	}

	/**
	 * 
	 * @param goal
	 * @return
	 */
	public boolean containsGoal( Goal goal ) {
		ElementContainer gc = new ElementContainer( goal );
		
		if( goalbase.containsKey( gc.getName() ) )
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param plan
	 * @return
	 */
	public boolean containsPlan(Plan plan) {
		ElementContainer pc = new ElementContainer( plan );
		
		if (planbase.containsKey( pc.getName() ) )
			return true;
		
		return false;
	}

	/**
	 * Get the name of the represented Agent
	 * 
	 * @return the name of the represented Agent
	 */
	public String getAgentName() {
		return agentname;
	}
	
	public static String parseElId(String name){
		
		String patternString = "(^[GT]\\d+\\.?\\d*):";
		Pattern pattern = Pattern.compile(patternString);
		java.util.regex.Matcher matcher = pattern.matcher(name);
		if(matcher.find())
			return matcher.group(1);
		else
			return null;
	}
	
	public static String parseRTRegex(String name){
		
		String patternString = "\\[(.*)\\]";
		Pattern pattern = Pattern.compile(patternString);
		java.util.regex.Matcher matcher = pattern.matcher(name);
		if(matcher.find())
			return matcher.group(1);
		else
			return null;
	}
	
	private void setRTAttributes(RTContainer gc){
		
		gc.setElId(parseElId(gc.getName()));
		gc.setRtRegex(parseRTRegex(gc.getName()));
	}
	
	public Hashtable<String, GoalContainer>  getGoalBase(){
		return goalbase;
	}
}