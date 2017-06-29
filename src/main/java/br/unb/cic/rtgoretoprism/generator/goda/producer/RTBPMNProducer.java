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

package br.unb.cic.rtgoretoprism.generator.goda.producer;

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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.parser.BPMNRTParser;
import br.unb.cic.rtgoretoprism.generator.goda.writer.PrismWriter;
import br.unb.cic.rtgoretoprism.generator.goda.writer.BPMNWriter;
import br.unb.cic.rtgoretoprism.generator.goda.writer.dtmc.DTMCWriter;
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
public class RTBPMNProducer {
	/** the navigator for the Tropos model */
	private TroposNavigator tn;
	
	/** input folder for the templates */ 
	private String inputFolder;
	/** output folder for the generated code */
	private String outputFolder;
	/** the set of Actor for which code should be generated */
	private Set<Actor> allActors;
	/** the set of Actor for which code should be generated */
	private Set<FHardGoal> allGoals;
	/** defines if the generated code will be parametric (PARAM) or not (PRISM) **/
	private boolean parametric;
	
	/** memory for the parsed RT regexes */
	Map<String, Boolean[]> rtSortedGoals;
	Map<String, Object[]> rtCardGoals;
	Map<String, Set<String>> rtAltGoals;
	Map<String, String[]> rtTryGoals;
	Map<String, Boolean> rtOptGoals;
	
	//private int globalTime;
	//private int globalPath;
	
	/**
	 * Creates a new Producer instance
	 * 
	 * @param allActors the actor to generate code for
	 * @param in the template input folder
	 * @param out the generated code output folder
	 */
	public RTBPMNProducer(Set<Actor> allActors, Set<FHardGoal> allGoals, String in, String out, boolean parametric ) {
		
		tn = new TroposNavigator( allActors.iterator().next().eResource() );
		//Set<Actor> allActors = getSystemActors();
		
		this.inputFolder = in;
		this.outputFolder = out;
		this.allActors = allActors;
		this.allGoals = allGoals;
		
		this.rtSortedGoals = new TreeMap<String, Boolean[]>();
		this.rtCardGoals = new TreeMap<String, Object[]>();
		this.rtAltGoals = new TreeMap<String, Set<String>>();
		this.rtTryGoals = new TreeMap<String, String[]>();
		this.rtOptGoals = new TreeMap<String, Boolean>();
		this.parametric = parametric;
	}
	
	/**
	 * Run the process by which the Jadex source code is generated for the
	 * specified Tropos (system) actors
	 * 
	 * @throws CodeGenerationException 
	 * @throws IOException 
	 */

	public void run() throws CodeGenerationException, IOException {
				
		long startTime = new Date().getTime();
		
		for( Actor a : allActors ) {
			if(!a.isIsSystem())
				return;
			
			//generate the AgentDefinition object for the current actor
			AgentDefinition ad = new AgentDefinition( a );
			
			//------------------------------------------------------------------------------
			//------------------------------------------------------------------------------
			//------------------------------------------------------------------------------

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
				//-----HERE 
				addGoal(rootgoal, gc, ad, false);
				//-----
			}		
			//------------------------------------------------------------------------------
			//---------	---------------------------------------------------------------------
			
			//TODO: check this planlist creation, maybe it can be added on the fly
			//the list of root plans for current agent' capabilities
			List<Plan> planList = tn.getAllCapabilityPlan( a );
			
			BPMNWriter writer = new BPMNWriter(ad, a);
			writer.start();
		}
	}
	
	/**
	 * 
	 * @param g
	 * @param gc
	 * @param ad
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	private void addGoal(FHardGoal g, GoalContainer gc, final AgentDefinition ad, boolean included) throws IOException {
		included = included || allGoals.isEmpty() || allGoals.contains(g);
		gc.setIncluded(included);
		addContributions(g, gc, ad);
		
		//TODO: uncomment if needed!
		//if (tn.isDelegated(g))
		//for testing purposes we allow requests for all goals
		//gc.setRequest(Const.REQUEST);
		String rtRegex = gc.getRtRegex();

		//---------------------------------------------------------------====Call for BPMNRTParser
		storeRegexResults(gc.getUid(), rtRegex); 
		
		List<FHardGoal> declist = (List<FHardGoal>) tn.getBooleanDec(g, FHardGoal.class);
		sortIntentionalElements(declist);
		if (tn.isBooleanDecAND(g))
			// sets decomposition flag and creates the AND-Plan (call only one time!)
			gc.createDecomposition(Const.AND);			
		else if (tn.isBooleanDecOR(g))
			// sets decomposition flag and creates the Metagoal+plan (call only one time!)
			gc.createDecomposition(Const.OR);	
		
		
		iterateGoals(ad, gc, declist, included);
		iterateRts(gc, gc.getDecompGoals());
		iterateMeansEnds(g, gc, ad, included);
		iterateRts(gc, gc.getDecompPlans());
		//Set goals alternatives, tries, optional and cardinalities
			
							
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
	
	@SuppressWarnings("unchecked")
	private void storeRegexResults(String uid, String rtRegex) throws IOException { //Call for BPMNRTParser
		if(rtRegex != null){
			Object [] res = BPMNRTParser.parseRegex(uid, rtRegex + '\n');
			rtSortedGoals.putAll((Map<String, Boolean[]>) res [0]);
			rtCardGoals.putAll((Map<String, Object[]>) res [1]);
			rtAltGoals.putAll((Map<String, Set<String>>) res [2]);
			rtTryGoals.putAll((Map<String, String[]>) res [3]);
			rtOptGoals.putAll((Map<String, Boolean>) res[4]);
		}
	}

	private void iterateGoals(AgentDefinition ad, GoalContainer gc, List<FHardGoal> decList, boolean include) throws IOException{
		
		Integer prevPath = gc.getPrevTimePath();
		Integer rootFutPath = gc.getFutTimePath();
		Integer rootPath = gc.getTimePath();
		Integer rootTime = gc.getTimeSlot();
		gc.setRootTimeSlot(rootTime);
		
		for (FHardGoal dec : decList) {
			boolean newgoal = !ad.containsGoal(dec);
			boolean parDec = false;
			boolean trivial = false;
			// addDecomp adds the new goal to container and goalbase and, if needed (OR, M-E)
			// organizes dispatch goals
			GoalContainer deccont = ad.createGoal(dec, Const.ACHIEVE);
			gc.addDecomp(deccont);
			
			if(rtSortedGoals.containsKey(deccont.getElId())){
				Boolean [] decDeltaPathTime = rtSortedGoals.get(deccont.getElId());				
				if(decDeltaPathTime[1]){ //|| tn.getBooleanDec(dec, TroposIntentional.class).isEmpty()
					/*if(gc.getFutTimePath() > 0){
						deccont.setPrevTimePath(gc.getFutTimePath());
					}else*/
						deccont.setPrevTimePath(gc.getTimePath());
					deccont.setFutTimePath(gc.getFutTimePath());//TODO: a sequential element should receive 'future time' from its predecessor?  
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(gc.getTimeSlot() + 1);
				}else if(decDeltaPathTime[0]){
					if(gc.getFutTimePath() > 0)
						deccont.setTimePath(gc.getFutTimePath() + 1);
					else
						deccont.setTimePath(gc.getTimePath() + 1);					
					deccont.setTimeSlot(rootTime);
					parDec = true;
				}else{
					trivial = true;
					deccont.setPrevTimePath(prevPath);
					deccont.setFutTimePath(rootFutPath);
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(rootTime);
				}
			}else{
				trivial = true;
				deccont.setPrevTimePath(prevPath);
				deccont.setFutTimePath(gc.getFutTimePath());
				deccont.setTimePath(gc.getTimePath());
				deccont.setTimeSlot(gc.getTimeSlot());
			}
			
			if(rtCardGoals.containsKey(deccont.getElId())){
				Object[] card = rtCardGoals.get(deccont.getElId());
				Const cardType = (Const) card[0];
				Integer cardNumber = (Integer) card[1];
				deccont.setCardType(cardType);
				if(cardType.equals(Const.SEQ))
					deccont.setTimeSlot(deccont.getTimeSlot() + cardNumber - 1);
			}
			
			deccont.addFulfillmentConditions(gc.getFulfillmentConditions());

			if (newgoal){
				addGoal(dec, deccont, ad, include);	
				gc.setFutTimePath(Math.max(deccont.getTimePath(), deccont.getFutTimePath())); //TODO: gc.getFutTimePath() + ?
				if(trivial || (!parDec && rtAltGoals.get(deccont.getElId()) == null))
						gc.setTimeSlot(deccont.getTimeSlot());
				/*if(trivial || (parDec || rtAltGoals.get(deccont.getElId()) != null))
						gc.setTimePath(deccont.getTimePath());*/		
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
		
		//Integer prevPath = pc.getPrevTimePath();
		//Integer rootPath = pc.getTimePath();
		//Integer rootTime = pc.getTimeSlot();
		storeRegexResults(pc.getUid(), pc.getRtRegex());

		if (tn.isMeansEndDec(p)){
			List<FPlan> melist = tn.getMeansEndMeanPlans(p);
			sortIntentionalElements(melist);
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			pc.createDecomposition(Const.ME);
			iteratePlans(ad, pc, melist);			
			//Set goals alternatives and tries
			iterateRts(pc, pc.getDecompPlans());
		}
		else if (tn.isBooleanDecAND(p)){			
			List<FPlan> decList = (List<FPlan>) tn.getBooleanDec(p, FPlan.class);
			sortIntentionalElements(decList);
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
	
	private void iteratePlans(AgentDefinition ad, PlanContainer pc, List<FPlan> decList) throws IOException{;
		Integer prevPath = pc.getPrevTimePath();
		Integer rootFutPath = pc.getFutTimePath();
		Integer rootPath = pc.getTimePath();
		Integer rootTime = pc.getTimeSlot();
		for (FPlan dec : decList) {
			boolean newplan = !ad.containsPlan(dec);
			boolean parPlan = false;
					
			PlanContainer deccont = ad.createPlan(dec);
			pc.addDecomp(deccont);
			
			if(rtSortedGoals.containsKey(deccont.getElId())){
				Boolean [] decDeltaPathTime = rtSortedGoals.get(deccont.getElId());				
				if(decDeltaPathTime[1]){
					/*if(pc.getFutTimePath() > 0)
						deccont.setPrevTimePath(pc.getFutTimePath());
					else*/
						deccont.setPrevTimePath(pc.getTimePath());
					deccont.setFutTimePath(pc.getFutTimePath());
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(pc.getTimeSlot() + 1);
				}else if(decDeltaPathTime[0]){
					if(pc.getFutTimePath() > 0)
						deccont.setTimePath(pc.getFutTimePath() + 1);
					else
						deccont.setTimePath(pc.getTimePath() + 1);
					
					deccont.setTimeSlot(rootTime);
					parPlan = true;					
				}else{
					deccont.setPrevTimePath(prevPath);
					deccont.setFutTimePath(rootFutPath);
					deccont.setTimePath(rootPath);
					deccont.setTimeSlot(rootTime);
				}
			}else{
				deccont.setPrevTimePath(prevPath);
				deccont.setFutTimePath(pc.getFutTimePath());
				deccont.setTimePath(pc.getTimePath());
				deccont.setTimeSlot(pc.getTimeSlot());
			}
			
			if(rtCardGoals.containsKey(deccont.getElId())){
				Object[] card = rtCardGoals.get(deccont.getElId());
				Const cardType = (Const) card[0];
				Integer cardNumber = (Integer) card[1];
				if(cardType.equals(Const.SEQ))
					deccont.setTimeSlot(deccont.getTimeSlot() + cardNumber - 1);
			}
			
			deccont.addFulfillmentConditions(pc.getFulfillmentConditions());
			//deccont.addAdoptionConditions(pc.getAdoptionConditions());
			
			if (newplan){
				addPlan(dec, deccont, ad);									
				pc.setFutTimePath(Math.max(deccont.getTimePath(), deccont.getFutTimePath())); //TODO: why to add pc.getFutTimePath() ?
				if(!parPlan && rtAltGoals.get(deccont.getElId()) == null){
					pc.setTimeSlot(deccont.getTimeSlot());
				}/*else{ 
					pc.setTimePath(deccont.getTimePath());
				}*/
			}
		}
	}
	
	private void iterateMeansEnds(FHardGoal g, GoalContainer gc, final AgentDefinition ad, boolean included) throws IOException{
		
		Integer prevPath = gc.getPrevTimePath();
		Integer rootFutPath = gc.getFutTimePath();
		Integer rootPath = gc.getTimePath();
		Integer rootTime = gc.getTimeSlot();

		if (included && tn.isMeansEndDec(g)){
			List<FPlan> melist = tn.getMeansEndMeanPlans(g);
			sortIntentionalElements(melist);
			// sets decomposition flag and creates the Metagoal+plan,
			// shall be the same than with OR! They could also be mixed in this implementation!
			gc.createDecomposition(Const.ME);
			for (FPlan p : melist) {
				boolean newplan = !ad.containsPlan(p);
				boolean parPlan = false;
				boolean trivial = false;
				
				PlanContainer pc = ad.createPlan(p);
				gc.addMERealPlan(pc);
				
				if(rtSortedGoals.containsKey(pc.getElId())){
					Boolean [] decDeltaPathTime = rtSortedGoals.get(pc.getElId());										
					if(decDeltaPathTime[1]){
						/*if(gc.getFutTimePath() > 0)
							pc.setPrevTimePath(gc.getFutTimePath());
						else*/
						pc.setPrevTimePath(gc.getTimePath());
						pc.setFutTimePath(gc.getFutTimePath());
						pc.setTimePath(rootPath);
						pc.setTimeSlot(gc.getTimeSlot() + 1);
					}else if(decDeltaPathTime[0]){
						parPlan = true;
						pc.setPrevTimePath(prevPath);
						if(gc.getFutTimePath() > 0)
							pc.setTimePath(gc.getFutTimePath() + 1);
						else
							pc.setTimePath(gc.getTimePath() + 1);
						pc.setTimeSlot(rootTime + 0);//TODO: check if there is no case in which both path and time are incremented
					}else{
						trivial = true;
						pc.setPrevTimePath(prevPath);
						pc.setFutTimePath(rootFutPath);
						pc.setTimePath(rootPath);
						pc.setTimeSlot(rootTime);
					}
				}else{
					trivial = true;
					//parPlan = true;
					pc.setPrevTimePath(gc.getPrevTimePath());
					pc.setFutTimePath(gc.getFutTimePath());
					pc.setTimePath(gc.getTimePath());
					pc.setTimeSlot(gc.getTimeSlot());
				}
				
				if(rtCardGoals.containsKey(pc.getElId())){
					Object[] card = rtCardGoals.get(pc.getElId());
					Const cardType = (Const) card[0];
					Integer cardNumber = (Integer) card[1];
					if(cardType.equals(Const.SEQ))
						pc.setTimeSlot(pc.getTimeSlot() + cardNumber - 1);
				}
								
				pc.addFulfillmentConditions(gc.getFulfillmentConditions());
				
				if (newplan){
					addPlan(p, pc, ad);					
					gc.setFutTimePath(Math.max(pc.getTimePath(), pc.getFutTimePath())); //TODO: gc.getFutTimePath() + ?
					if(trivial || (!parPlan && rtAltGoals.get(pc.getElId()) == null))
						gc.setTimeSlot(pc.getTimeSlot());
					/*if(trivial || (parPlan || rtAltGoals.get(pc.getElId()) != null))
						gc.setTimePath(pc.getTimePath());*/
											
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
					addGoal(go, pc, ad, true);
			}
		}
	}
	
	private void iterateRts(RTContainer gc, List<? extends RTContainer> rts){
		for(RTContainer dec : rts){
			String elId = dec.getElId();
			LinkedList <RTContainer> decPlans = RTContainer.fowardMeansEnd(dec, new LinkedList<RTContainer>());			
			//Alternatives			
			if(rtAltGoals.get(elId) != null){		
				if(!dec.getFirstAlternatives().contains(rts.get(0))){
					for(String altGoalId : rtAltGoals.get(elId)){
						RTContainer altDec = gc.getDecompElement(altGoalId);						
						if(altDec != null){
							LinkedList <RTContainer> decAltPlans = RTContainer.fowardMeansEnd(altDec, new LinkedList<RTContainer>());
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
								//break;
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
				if(tryGoals[0] != null){
					RTContainer successPlan = gc.getDecompElement(tryGoals[0]);
					LinkedList<RTContainer> decSucessPlans = RTContainer.fowardMeansEnd(successPlan, new LinkedList<RTContainer>());
					for(RTContainer decPlan : decPlans){
						decPlan.setTrySuccess(successPlan);
					}
					for(RTContainer decSucessPlan : decSucessPlans){						
						decSucessPlan.setTryOriginal(dec);
						decSucessPlan.setSuccessTry(true);
					}
				}
				if(tryGoals[1] != null){
					RTContainer failurePlan = gc.getDecompElement(tryGoals[1]);
					LinkedList<RTContainer> decFailurePlans = RTContainer.fowardMeansEnd(failurePlan, new LinkedList<RTContainer>());
					for(RTContainer decPlan : decPlans){
						decPlan.setTryFailure(failurePlan);
					}
					for(RTContainer decFailurePlan : decFailurePlans){						
						decFailurePlan.setTryOriginal(dec);
						decFailurePlan.setSuccessTry(false);
					}
				}
			}
			//Optional
			if(rtOptGoals.containsKey(elId))
				for(RTContainer decPlan : decPlans)
					decPlan.setOptional(rtOptGoals.get(elId));
			//Cardinality
			if(rtCardGoals.containsKey(elId)){
				Object[] card = rtCardGoals.get(elId);
				Const cardType = (Const) card[0];
				Integer cardNumber = (Integer) card[1];
				for(RTContainer decPlan : decPlans){
					decPlan.setCardType(cardType);
					decPlan.setCardNumber(cardNumber);
				}
			}
			
			
		}		
	}

	
	private void sortIntentionalElements(List<? extends TroposIntentional> elements){
		Collections.sort(elements, new Comparator<TroposIntentional>() {
			@Override
			public int compare(TroposIntentional gA, TroposIntentional gB) {
				float idA = Float.parseFloat(AgentDefinition.parseElId(gA.getName()).replaceAll("[TG]", ""));
				float idB = Float.parseFloat(AgentDefinition.parseElId(gB.getName()).replaceAll("[TG]", ""));
				return (int) (idA * 1000 - idB * 1000);
			}
		});
	}
	
	/*private Set<Actor> getSystemActors(){
		List<Actor> actors = tn.getActors();
		Set<Actor> systemActors = new HashSet<Actor>();
		for(Actor a : actors){
			if(a.isIsSystem())
				systemActors.add(a);
		}
		return systemActors;
	}*/
}
