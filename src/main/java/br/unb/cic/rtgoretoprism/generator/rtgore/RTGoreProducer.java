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

package br.unb.cic.rtgoretoprism.generator.rtgore;

import it.itc.sra.taom4e.model.core.gencore.TroposModelElement;
import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.Dependency;
import it.itc.sra.taom4e.model.core.informalcore.Goal;
import it.itc.sra.taom4e.model.core.informalcore.HardGoal;
import it.itc.sra.taom4e.model.core.informalcore.Plan;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FContribution;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FSoftGoal;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.model.kl.ElementContainer;
import br.unb.cic.rtgoretoprism.model.kl.GoalContainer;
import br.unb.cic.rtgoretoprism.model.kl.PlanContainer;
import br.unb.cic.rtgoretoprism.model.kl.RTContainer;
import br.unb.cic.rtgoretoprism.model.kl.SoftgoalContainer;
import br.unb.cic.rtgoretoprism.util.NameUtility;
import br.unb.cic.rtgoretoprism.util.kl.TroposNavigator;

/**
 * 
 * 
 * @author Mirko Morandini
 *
 */
public class RTGoreProducer {
	/** the navigator for the Tropos model */
	private TroposNavigator tn;
	
	/** input folder for the templates */ 
	private String inputFolder;
	/** output folder for the generated code */
	private String outputFolder;
	/** the set of Actor for which code should be generated */
	private Set<Actor> allActors;
	
	Map<String, Integer[]> rtSortedGoals;
	Map<String, Integer> rtCardGoals;
	Map<String, Set<String>> rtAltGoals;
	Map<String, String[]> rtTryGoals;
	
	/**
	 * Creates a new Producer instance
	 * 
	 * @param allActors the actor to generate code for
	 * @param in the template input folder
	 * @param out the generated code output folder
	 */
	public RTGoreProducer(String troposSource, String in, String out ) {
		
		tn = new TroposNavigator( troposSource );
		Set<Actor> allActors = getSystemActors();
		
		this.inputFolder = in;
		this.outputFolder = out;
		this.allActors = allActors;
		
		this.rtSortedGoals = new TreeMap<String, Integer[]>();
		this.rtCardGoals = new TreeMap<String, Integer>();
		this.rtAltGoals = new TreeMap<String, Set<String>>();
		this.rtTryGoals = new TreeMap<String, String[]>();
	}
	
	/**
	 * Run the process by which the Jadex source code is generated for the
	 * specified Tropos (system) actors
	 * 
	 * @throws CodeGenerationException 
	 * @throws IOException 
	 */
	public void run() throws CodeGenerationException, IOException {
		ATCConsole.println("Starting PRISM Model Generation Process (Knowledge Level)" );
		ATCConsole.println("\tTemplate Input Folder: " + inputFolder );
		ATCConsole.println("\tOutput Folder: " + outputFolder );
		
		for( Actor a : allActors ) {
			if(!a.isIsSystem())
				return;
			
			ATCConsole.println( "Generating Jadex agent for: " + a.getName() );
			
			//generate the AgentDefinition object for the current actor
			AgentDefinition ad = new AgentDefinition( a );
			
			// analyse all root goals
			for( HardGoal rootgoal : tn.getRootGoals(a) ) {
				Const type, request;
				
				if( tn.isDelegated(rootgoal) ) {
					type = Const.ACHIEVE;
					request=Const.REQUEST;
				} else {
					// type=Const.MAINTAIN;
					
//TODO:  For now only achieve is implemented, this has to be a maintain-goal!
					type = Const.ACHIEVE; 
					request = Const.NONE;
				}
				
				//create the goalcontainer for this one
				GoalContainer gc = ad.createGoal(rootgoal, type);
				gc.setRequest(request);
				
				//add to the root goal list
				ad.addRootGoal(gc);
				addGoal(rootgoal, gc, ad);
								
			}		

			//the list of root plans for current agent' capabilities
			List<Plan> planList = tn.getAllCapabilityPlan( a );
			
			// write the agent (xml+java plan bodies) to the output directory
			PrismWriter writer = new PrismWriter( ad, planList, inputFolder, outputFolder);
			writer.writeModel();
		}
		
		ATCConsole.println( "DONE!" );
	}
	
	/**
	 * 
	 * @param g
	 * @param gc
	 * @param ad
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	private void addGoal(Goal g, GoalContainer gc, final AgentDefinition ad) throws IOException {
		addContributions(g, gc, ad);
		
		//TODO: uncomment if needed!
		//if (tn.isDelegated(g))
		//for testing purposes we allow requests for all goals
		gc.setRequest(Const.REQUEST);
			
		String rtRegex = gc.getRtRegex();
		
		if(rtRegex != null){
			Object [] res = RTGoreSorter.parseRegex(rtRegex + '\n');
			rtSortedGoals.putAll((Map<String, Integer[]>) res [0]);
			rtCardGoals.putAll((Map<String, Integer>) res [1]);
			rtAltGoals.putAll((Map<String, Set<String>>) res [2]);
			rtTryGoals.putAll((Map<String, String[]>) res [3]);
		}
		Integer rootTime = gc.getTimeSlot();
		List<Goal> declist = tn.getBooleanDec(g);
		Collections.sort(declist, new Comparator<Goal>() {
			@Override
			public int compare(Goal gA, Goal gB) {
				int idA = Integer.parseInt(ad.parseElId(gA.getName()).replace("G", ""));
				int idB = Integer.parseInt(ad.parseElId(gB.getName()).replace("G", ""));
				return idA - idB;
			}
		});
		if (tn.isBooleanDecAND(g)) {
			// sets decomposition flag and creates the AND-Plan (call only one time!)
			gc.createDecomposition(Const.AND);
			for (Goal dec : declist) {
				boolean newgoal = !ad.containsGoal(dec);
				boolean parDec = false;
				// addDecomp adds the new goal to container and goalbase and, if needed (OR, M-E)
				// organizes dispatch goals
				GoalContainer deccont = ad.createGoal(dec, Const.ACHIEVE);
				
				if(rtSortedGoals != null && rtSortedGoals.containsKey(deccont.getElId())){
					Integer [] decDeltaPathTime = rtSortedGoals.get(deccont.getElId());
					if(decDeltaPathTime[0] == 0 || tn.getBooleanDec(dec).isEmpty()){
						deccont.setTimePath(gc.getTimePath());
						deccont.setTimeSlot(gc.getTimeSlot() + decDeltaPathTime[1]);
					}else{
						deccont.setTimePath(gc.getTimePath() + decDeltaPathTime[0]);
						deccont.setTimeSlot(rootTime + decDeltaPathTime[1]);
						parDec = true;
					}
				}

				gc.addDecomp(deccont);
				
				if (newgoal){
					addGoal(dec, deccont, ad);
					if(parDec)
						deccont.setTimePath(deccont.getTimePath() - 1);
					gc.setTimePath(deccont.getTimePath());
					gc.setTimeSlot(deccont.getTimeSlot());	
				}				
			}
		} else if (tn.isBooleanDecOR(g)) {
			// sets decomposition flag and creates the Metagoal+plan (call only one time!)
			gc.createDecomposition(Const.OR);
			for (Goal dec : declist) {
				boolean newgoal = !ad.containsGoal(dec);
				boolean parDec = false;
				// addDecomp adds the new goal and, if needed (OR, M-E) organizes dispatch goals
				GoalContainer deccont = ad.createGoal(dec, Const.ACHIEVE);
				
				if(rtSortedGoals != null && rtSortedGoals.containsKey(deccont.getElId())){
					Integer [] decDeltaPathTime = rtSortedGoals.get(deccont.getElId());
					if(decDeltaPathTime[0] == 0 || tn.getBooleanDec(dec).isEmpty()){
						deccont.setTimePath(gc.getTimePath());
						deccont.setTimeSlot(gc.getTimeSlot() + decDeltaPathTime[1]);
					}else{
						deccont.setTimePath(gc.getTimePath() + decDeltaPathTime[0]);
						deccont.setTimeSlot(rootTime + decDeltaPathTime[1]);
						parDec = true;
					}
				}

				gc.addDecomp(deccont);
				
				if (newgoal){
					addGoal(dec, deccont, ad);
					if(parDec)
						deccont.setTimePath(deccont.getTimePath() - 1);
					gc.setTimePath(deccont.getTimePath());
					gc.setTimeSlot(deccont.getTimeSlot());	
				}
			}
		}
		//Set goals alternatives and tries
		rtIteration(gc, gc.getDecompGoals());
							
		if (tn.isMeansEndDec(g)){
			List<Plan> melist = tn.getMeansEndMeanPlans(g);
			
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			gc.createDecomposition(Const.ME);
			for (Plan p : melist) {
				boolean newplan = !ad.containsPlan(p);
				boolean parPlan = false;
				
				PlanContainer pc = ad.createPlan(p);
				
				if(rtSortedGoals.containsKey(pc.getElId())){
					Integer [] decDeltaPathTime = rtSortedGoals.get(pc.getElId());
					if(decDeltaPathTime[0] == 0){
						pc.setTimePath(gc.getTimePath());
						pc.setTimeSlot(gc.getTimeSlot() + decDeltaPathTime[1]);
					}else{
						pc.setTimePath(gc.getTimePath() + decDeltaPathTime[0]);
						pc.setTimeSlot(rootTime + decDeltaPathTime[1]);
						parPlan = true;
					}
				}else{
					pc.setTimePath(gc.getTimePath());
					pc.setTimeSlot(gc.getTimeSlot());
				}
				
				gc.addMERealPlan(pc);
				
				if (newplan){
					addPlan(p, pc, ad);
					if(parPlan)
						pc.setTimePath(pc.getTimePath() - 1);
					gc.setTimePath(pc.getTimePath());
					gc.setTimeSlot(pc.getTimeSlot());
				}
				
			}			
			
			//The unusual "means-end" with a goal as means:
			//The "means" goal is afterwards handled like in an OR-decomposition!
			List<Goal> megoallist = tn.getMeansEndMeanGoals(g);
			
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			// gc.createDecomposition(Const.ME);
			for (Goal go : megoallist) {
				boolean newgoal = !ad.containsGoal(go);
				
				GoalContainer pc = ad.createGoal(go, Const.ACHIEVE);
				//gc.addMERealPlan(pc);
				gc.addDecomp(pc);
				if (newgoal)
					addGoal(go, pc, ad);
			}
		}
		
		if (tn.isGoalWhyDependency(g)) {
			for (Dependency dep : tn.getGoalDependencies(g)) {
				//String goal = AgentDefinition.fill(tn.getDependumGoalFromDependency(dep).getName());
				String goal = NameUtility.adjustName( tn.getDependumGoalFromDependency(dep).getName() );
				//String actor = AgentDefinition.fill(tn.getActorFromDependency(dep).getName());
				String actor = NameUtility.adjustName( tn.getActorFromDependency(dep).getName() );
				gc.addDependency(goal, actor);
			}
		}
		
	}
	
	private void rtIteration(GoalContainer gc, List<? extends RTContainer> rts){
		for(RTContainer dec : rts){
			String elId = dec.getElId();
			dec = fowardMeansEnd(dec);
			//Alternatives			
			if(rtAltGoals.get(elId) != null){				
				if(dec.getFirstAlternative() == null)
					for(String altGoalId : rtAltGoals.get(elId)){
						RTContainer altPlan = fowardMeansEnd(gc.getDecompGoal(altGoalId));
						if(altPlan != null){
							dec.getAlternatives().add(altPlan);
							altPlan.setFirstAlternative(dec);
						}
					}
			}
			//Try
			if(rtTryGoals.get(elId) != null){	
				String [] tryGoals = rtTryGoals.get(elId);
				if(tryGoals[0] != null)
					fowardMeansEnd(gc.getDecompGoal(tryGoals[0])).setTrySuccess(dec);
				if(tryGoals[1] != null)
					fowardMeansEnd(gc.getDecompGoal(tryGoals[1])).setTryFailure(dec);
			}
		}
		
	}
	
	private RTContainer fowardMeansEnd(RTContainer dec){
		if(dec.getDecompPlans() != null && !dec.getDecompPlans().isEmpty())
			dec = dec.getDecompPlans().get(0);
		return dec;
	}

	/**
	 * @param g
	 * @param gc
	 * @param ad
	 */
	private void addContributions(TroposModelElement m, ElementContainer ec, AgentDefinition ad) {
		if (tn.hasContributions(m)) {
			for (FContribution c : tn.getContributions(m)) {
				
				if (c.getTarget() instanceof FSoftGoal) {
					FSoftGoal sg = (FSoftGoal) c.getTarget();
					SoftgoalContainer sgcont = ad.createSoftgoal(sg);
					ec.addContribution(sgcont, c.getMetric());
				}
			}
		}
	}

	private void addPlan(Plan p, PlanContainer pc, AgentDefinition ad) {
		addContributions(p, pc, ad);
		// RESOURCES (USE/PRODUCE) TO BE IMPLEMENTED, HERE AND IN THE NAVIGATOR!!
	}
	
	private Set<Actor> getSystemActors(){
		List<Actor> actors = tn.getActors();
		Set<Actor> systemActors = new HashSet<Actor>();
		for(Actor a : actors){
			if(a.isIsSystem())
				systemActors.add(a);
		}
		return systemActors;
	}
}
