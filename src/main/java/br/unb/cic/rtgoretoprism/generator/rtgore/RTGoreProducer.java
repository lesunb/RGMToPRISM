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
import it.itc.sra.taom4e.model.core.informalcore.Plan;
import it.itc.sra.taom4e.model.core.informalcore.TroposIntentional;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FContribution;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FPlan;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FSoftGoal;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
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
	Map<String, Boolean> rtOptGoals;
	
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
		this.rtOptGoals = new TreeMap<String, Boolean>();
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
			for( FHardGoal rootgoal : tn.getRootGoals(a) ) {
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
	private void addGoal(FHardGoal g, GoalContainer gc, final AgentDefinition ad) throws IOException {
		addContributions(g, gc, ad);
		
		//TODO: uncomment if needed!
		//if (tn.isDelegated(g))
		//for testing purposes we allow requests for all goals
		gc.setRequest(Const.REQUEST);
			
		String rtRegex = gc.getRtRegex();
		storeRegexResults(rtRegex);
		Integer prevPath = gc.getPrevTimePath();
		Integer rootPath = gc.getTimePath();
		Integer rootTime = gc.getTimeSlot();
		List<FHardGoal> declist = (List<FHardGoal>) tn.getBooleanDec(g, FHardGoal.class);
		sortIntentionalElements(declist, ad);
		if (tn.isBooleanDecAND(g))
			// sets decomposition flag and creates the AND-Plan (call only one time!)
			gc.createDecomposition(Const.AND);			
		else if (tn.isBooleanDecOR(g))
			// sets decomposition flag and creates the Metagoal+plan (call only one time!)
			gc.createDecomposition(Const.OR);			
		
		iterateGoals(ad, gc, declist);
		//Set goals alternatives and tries
		iterateRts(gc, gc.getDecompGoals());
							
		
		if (tn.isMeansEndDec(g)){
			List<FPlan> melist = tn.getMeansEndMeanPlans(g);
			sortIntentionalElements(melist, ad);
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			gc.createDecomposition(Const.ME);
			for (FPlan p : melist) {
				boolean newplan = !ad.containsPlan(p);
				boolean parPlan = false;
				
				PlanContainer pc = ad.createPlan(p);
				
				if(rtSortedGoals.containsKey(pc.getElId())){
					Integer [] decDeltaPathTime = rtSortedGoals.get(pc.getElId());					
					if(decDeltaPathTime[1] != 0){
						if(gc.getFutTimePath() > 0)
							pc.setPrevTimePath(gc.getFutTimePath());
						else
							pc.setPrevTimePath(gc.getTimePath());
						pc.setTimePath(rootPath);
						pc.setTimeSlot(gc.getTimeSlot() + decDeltaPathTime[1]);
					}else if(decDeltaPathTime[0] != 0 ){
						pc.setPrevTimePath(prevPath);
						if(gc.getFutTimePath() > 0)
							pc.setTimePath(gc.getFutTimePath() + decDeltaPathTime[0]);
						else
							pc.setTimePath(gc.getTimePath() + decDeltaPathTime[0]);						
						pc.setTimeSlot(rootTime + decDeltaPathTime[1]);
						parPlan = true;
					}else{						
						pc.setTimePath(rootPath);
						pc.setTimeSlot(rootTime);
					}
				}else{
					pc.setPrevTimePath(gc.getPrevTimePath());
					pc.setTimePath(gc.getTimePath());
					pc.setTimeSlot(gc.getTimeSlot());
				}
				
				if(gc.getCreationCondition() != null)
					if(pc.getCreationCondition() != null)
						pc.setCreationCondition(gc.getCreationCondition() + " & " + pc.getCreationCondition());
					else
						pc.setCreationCondition(gc.getCreationCondition());

				
				gc.addMERealPlan(pc);
				
				if (newplan){
					addPlan(p, pc, ad);					
					gc.setFutTimePath(gc.getFutTimePath() + pc.getTimePath());
					if(!parPlan && rtAltGoals.get(pc.getElId()) == null)
						gc.setTimeSlot(pc.getTimeSlot());
					else
						gc.setTimePath(pc.getTimePath());
											
				}
				
			}			
			
			//The unusual "means-end" with a goal as means:
			//The "means" goal is afterwards handled like in an OR-decomposition!
			List<FHardGoal> megoallist = tn.getMeansEndMeanGoals(g);
			
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			// gc.createDecomposition(Const.ME);
			for (FHardGoal go : megoallist) {
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
	
	private void iterateGoals(AgentDefinition ad, GoalContainer gc, List<FHardGoal> decList) throws IOException{
		
		Integer prevPath = gc.getPrevTimePath();
		Integer rootPath = gc.getTimePath();
		Integer rootTime = gc.getTimeSlot();
		gc.setRootTimeSlot(rootTime);
		
		for (FHardGoal dec : decList) {
			boolean newgoal = !ad.containsGoal(dec);
			boolean parDec = false;
			// addDecomp adds the new goal to container and goalbase and, if needed (OR, M-E)
			// organizes dispatch goals
			GoalContainer deccont = ad.createGoal(dec, Const.ACHIEVE);
			
			if(rtSortedGoals != null && rtSortedGoals.containsKey(deccont.getElId())){
				Integer [] decDeltaPathTime = rtSortedGoals.get(deccont.getElId());				
				if(decDeltaPathTime[1] != 0 ){ //|| tn.getBooleanDec(dec, TroposIntentional.class).isEmpty()
					if(gc.getFutTimePath() > 0)
						deccont.setPrevTimePath(gc.getFutTimePath());						
					else
						deccont.setPrevTimePath(gc.getTimePath());
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(gc.getTimeSlot() + decDeltaPathTime[1]);
				}else if(decDeltaPathTime[0] != 0 ){
					if(gc.getFutTimePath() > 0)
						deccont.setTimePath(gc.getFutTimePath() + decDeltaPathTime[0]);
					else
						deccont.setTimePath(gc.getTimePath() + decDeltaPathTime[0]);					
					deccont.setTimeSlot(rootTime);
					parDec = true;
				}else{					
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(rootTime);
				}
			}
			
			if(gc.getCreationCondition() != null)
				if(deccont.getCreationCondition() != null)
					deccont.setCreationCondition(gc.getCreationCondition() + " & " + deccont.getCreationCondition());
				else
					deccont.setCreationCondition(gc.getCreationCondition());

			gc.addDecomp(deccont);
			
			if (newgoal){
				addGoal(dec, deccont, ad);				
				gc.setFutTimePath(gc.getFutTimePath() + deccont.getTimePath());
				if(!parDec && rtAltGoals.get(deccont.getElId()) == null){
					gc.setTimeSlot(deccont.getTimeSlot());
				}else
					gc.setTimePath(deccont.getTimePath());				
			}				
		}		
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

	@SuppressWarnings("unchecked")
	private void addPlan(Plan p, PlanContainer pc, final AgentDefinition ad) throws IOException {
		addContributions(p, pc, ad);
		
		Integer prevPath = pc.getPrevTimePath();
		Integer rootPath = pc.getTimePath();
		Integer rootTime = pc.getTimeSlot();
		storeRegexResults(pc.getRtRegex());

		if (tn.isMeansEndDec(p)){
			List<FPlan> melist = tn.getMeansEndMeanPlans(p);
			sortIntentionalElements(melist, ad);
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			pc.createDecomposition(Const.ME);
			iteratePlans(ad, pc, melist);			
			//Set goals alternatives and tries
			iterateRts(pc, pc.getDecompPlans());
		}
		else if (tn.isBooleanDecAND(p)){			
			List<FPlan> decList = (List<FPlan>) tn.getBooleanDec(p, FPlan.class);
			sortIntentionalElements(decList, ad);
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			if (tn.isBooleanDecAND(p)) 
				pc.createDecomposition(Const.AND);
			else
				pc.createDecomposition(Const.OR);
			iteratePlans(ad, pc, decList);			
			//Set goals alternatives and tries
			iterateRts(pc, pc.getDecompPlans());
		}
	}
	
	private void iteratePlans(AgentDefinition ad, PlanContainer pc, List<FPlan> decList) throws IOException{
		
		Integer prevPath = pc.getPrevTimePath();
		Integer rootPath = pc.getTimePath();
		Integer rootTime = pc.getTimeSlot();
		for (FPlan dec : decList) {
			boolean newplan = !ad.containsPlan(dec);
			boolean parPlan = false;
					
			PlanContainer deccont = ad.createPlan(dec);
			
			if(rtSortedGoals.containsKey(deccont.getElId())){
				Integer [] decDeltaPathTime = rtSortedGoals.get(deccont.getElId());				
				if(decDeltaPathTime[1] != 0){
					if(pc.getFutTimePath() > 0)
						deccont.setPrevTimePath(pc.getFutTimePath());
					else
						deccont.setPrevTimePath(pc.getPrevTimePath());
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(pc.getTimeSlot() + decDeltaPathTime[1]);
				}else if(decDeltaPathTime[0] != 0){					
					if(pc.getFutTimePath() > 0)
						deccont.setTimePath(pc.getFutTimePath() + decDeltaPathTime[0]);
					else
						deccont.setTimePath(pc.getTimePath() + decDeltaPathTime[0]);					
					
					deccont.setTimeSlot(rootTime);
					parPlan = true;					
				}else{
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(rootTime);
				}
			}else{
				deccont.setTimePath(pc.getTimePath());
				deccont.setTimeSlot(pc.getTimeSlot());
			}
			
			if(pc.getCreationCondition() != null)
				if(deccont.getCreationCondition() != null)
					deccont.setCreationCondition(pc.getCreationCondition() + " & " + deccont.getCreationCondition());
				else
					deccont.setCreationCondition(pc.getCreationCondition());
			
			pc.addDecomp(deccont);
			if (newplan){
				addPlan(dec, deccont, ad);									
				if(!parPlan && rtAltGoals.get(deccont.getElId()) == null){
					pc.setTimeSlot(deccont.getTimeSlot());
					pc.setFutTimePath(deccont.getTimePath());
				}else{ 
					pc.setTimePath(deccont.getTimePath());
					pc.setFutTimePath(pc.getFutTimePath() + deccont.getTimePath());
				}
			}
		}
	}
	
	private void iterateRts(RTContainer gc, List<? extends RTContainer> rts){
		for(RTContainer dec : rts){
			String elId = dec.getElId();
			LinkedList <RTContainer> decPlans = fowardMeansEnd(dec, new LinkedList<RTContainer>());
			//Alternatives			
			if(rtAltGoals.get(elId) != null){		
				if(!dec.getFirstAlternatives().contains(rts.get(0))){
					for(String altGoalId : rtAltGoals.get(elId)){
						RTContainer altDec = gc.getDecompElement(altGoalId);						
						if(altDec != null){
							LinkedList <RTContainer> decAltPlans = fowardMeansEnd(altDec, new LinkedList<RTContainer>());
							if(!decPlans.contains(dec)){
								if(dec.getAlternatives().get(dec) == null)
									dec.getAlternatives().put(dec, new LinkedList<RTContainer>());
								dec.getAlternatives().get(dec).add(altDec);
							}
							if(!decAltPlans.contains(altDec))
								altDec.getFirstAlternatives().add(dec);							
							for(RTContainer decPlan : decPlans){
								if(decPlan.getAlternatives().get(dec) == null)
									decPlan.getAlternatives().put(dec, new LinkedList<RTContainer>());
								decPlan.getAlternatives().get(dec).add(altDec);
								break;
							}
							for(RTContainer decAltPlan : decAltPlans)
								decAltPlan.getFirstAlternatives().add(dec);
						}			
					}
				}
			}				
			//Try
			if(rtTryGoals.get(elId) != null){	
				String [] tryGoals = rtTryGoals.get(elId);
				RTContainer sucessPlan = null;
				if(tryGoals[0] != null){
					sucessPlan = fowardMeansEnd(gc.getDecompElement(tryGoals[0]), new LinkedList<RTContainer>()).get(0);
					decPlans.get(0).setTrySuccess(sucessPlan);
					sucessPlan.setTryOriginal(decPlans.get(0));
					sucessPlan.setSuccessTry(true);
				}
				if(tryGoals[1] != null){
					RTContainer failurePlan = fowardMeansEnd(gc.getDecompElement(tryGoals[1]), new LinkedList<RTContainer>()).get(0);
					decPlans.get(0).setTryFailure(failurePlan);
					failurePlan.setTryOriginal(decPlans.get(0));
					failurePlan.setSuccessTry(false);
				}
			}
			//Optional
			if(rtOptGoals.containsKey(elId))
				decPlans.get(0).setOptional(rtOptGoals.get(elId));
			//Cardinality
			if(rtCardGoals.containsKey(elId)){
				Integer cardNumber = rtCardGoals.get(elId);
				decPlans.get(0).setCardNumber(cardNumber);
			}
		}		
	}
	
	private LinkedList<RTContainer> fowardMeansEnd(RTContainer dec, LinkedList<RTContainer> decs){
		if(dec.getDecompGoals() != null && !dec.getDecompGoals().isEmpty())
			for(RTContainer subDec : dec.getDecompGoals())				
				fowardMeansEnd(subDec, decs);
		else if(dec.getDecompPlans() != null && !dec.getDecompPlans().isEmpty())
			for(RTContainer subDec : dec.getDecompPlans())	
				fowardMeansEnd(subDec, decs);
		else
			decs.add(dec);
		
		return decs;
	}
	
	@SuppressWarnings("unchecked")
	private void storeRegexResults(String rtRegex) throws IOException {
		if(rtRegex != null){
			Object [] res = RTGoreSorter.parseRegex(rtRegex + '\n');
			rtSortedGoals.putAll((Map<String, Integer[]>) res [0]);
			rtCardGoals.putAll((Map<String, Integer>) res [1]);
			rtAltGoals.putAll((Map<String, Set<String>>) res [2]);
			rtTryGoals.putAll((Map<String, String[]>) res [3]);
			rtOptGoals.putAll((Map<String, Boolean>) res[4]);
		}
	}
	
	private void sortIntentionalElements(List<? extends TroposIntentional> elements, final AgentDefinition ad){
		Collections.sort(elements, new Comparator<TroposIntentional>() {
			@Override
			public int compare(TroposIntentional gA, TroposIntentional gB) {
				float idA = Float.parseFloat(ad.parseElId(gA.getName()).replaceAll("[TG]", ""));
				float idB = Float.parseFloat(ad.parseElId(gB.getName()).replaceAll("[TG]", ""));
				return (int) (idA * 1000 - idB * 1000);
			}
		});
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
