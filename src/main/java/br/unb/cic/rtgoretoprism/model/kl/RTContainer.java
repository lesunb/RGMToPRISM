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

import it.itc.sra.taom4e.model.core.informalcore.TroposIntentional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A container for Goal object
 * 
 * @author Mirko Morandini 
 */
public abstract class RTContainer extends ElementContainer implements Comparable<RTContainer>{

	//Analysis
	private boolean included;
	//root element
	private RTContainer root;
	//RTGore
	private String uid = "";
	private String elId;
	private String rtRegex;
	private Integer timeSlot = 0;
	private Integer rootTimeSlot = 0;
	private Integer timePath = 0;
	private Integer prevTimePath = 0;
	private Integer futTimePath = 0;
	private Integer cardNumber = 0;
	private Const cardType = Const.SEQ;
	private Map <RTContainer, LinkedList<RTContainer>> alternatives;
	private LinkedList<RTContainer> firstAlternatives;
	private RTContainer trySuccess;
	private RTContainer tryFailure;
	private RTContainer tryOriginal;
	private boolean		successTry;
	private boolean optional;
	//Contexts
	private List<String> fulfillmentConditions;
	private List<String> adoptionConditions;
	/**
	 * Creates a standard achieve goal with request plan.
	 */
	//public GoalContainer(Goal goal) {
	//	this(goal, Const.REQUEST, Const.ACHIEVE);
	//}
	
	public boolean isAlternative(){
		return !firstAlternatives.isEmpty() || !alternatives.isEmpty();
	}
	
	public boolean isTry(){
		return trySuccess != null;
	}
	
	public boolean isTrySuccess(){
		return tryOriginal != null && successTry;
	}
	
	public boolean isTryFailure(){
		return tryOriginal != null && !successTry;
	}

	/**
	 * Creates a new GoalContainer instance
	 *  
	 * @param goal
	 * @param achieve
	 */
	public RTContainer(TroposIntentional intentional) {
		super(intentional);

		goals = new LinkedList<GoalContainer>();
		plans = new LinkedList<PlanContainer>();
		alternatives = new TreeMap<RTContainer,LinkedList<RTContainer>>();
		firstAlternatives = new LinkedList<RTContainer>();	
		fulfillmentConditions = new ArrayList<String>();
		adoptionConditions = new ArrayList<String>();
	}
	
	/**
	 * @return Returns the goals.
	 */
	public LinkedList<GoalContainer> getDecompGoals() {
		return goals;
	}
	
	/**
	 * @return Returns the plans.
	 */
	public LinkedList<PlanContainer> getDecompPlans() {
		return plans;
	}
	
	/**
	 * @return Returns a decomposed goal by elId.
	 */
	public GoalContainer getDecompGoal(String elId) {
		for(GoalContainer dec : goals)
			if(dec.getElId().equals(elId))
				return dec;
		return null;
	}
	
	/**
	 * @return Returns a decomposed plan by elId.
	 */
	public PlanContainer getDecompPlan(String elId) {
		for(PlanContainer dec : plans)
			if(dec.getElId().equals(elId))
				return dec;
		return null;
	}

	/**
	 * @return Returns a decomposed plan by elId.
	 */
	public RTContainer getDecompElement(String elId) {
		for(GoalContainer dec : goals)
			if(dec.getElId().equals(elId))
				return dec;
		for(PlanContainer dec : plans)
			if(dec.getElId().equals(elId))
				return dec;
		return null;
	}
	
	/**
	 * Returns the name of the goal without the RTRegex
	 * @return The name of the goal
	 */
	public String getClearElName(){
		String rtRegex = getRtRegex() != null ? getRtRegex() : "";
		StringBuilder sb = new StringBuilder();
		for(String word : getName().split("_")){
			if(word.isEmpty())
				continue;
			StringBuilder sbb = new StringBuilder(word);
			sbb.setCharAt(0, Character.toUpperCase(word.charAt(0)));
			sb.append(sbb);
		}
		return sb.toString().replaceAll("[:\\.-]", "_").replace("[" + rtRegex + "]", "");
	}
	
	public static LinkedList<RTContainer> fowardMeansEnd(RTContainer dec, LinkedList<RTContainer> decs){
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
	
	/**
	 * Returns the name of the goal without the RTRegex
	 * @return The name of the goal
	 */
	public String getClearUId(){
		if(getUid() != null)
			return getUid().replace(".", "_");
		else
			return null;
	}
	
	public String getUid() {
		if(root != null)
			return uid;
		else
			return elId;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	/**
	 * Returns the name of the goal without the RTRegex
	 * @return The name of the goal
	 */
	public String getClearElId(){
		if(elId != null)
			return elId.replace(".", "_");
		else
			return null;
	}

	public String getElId() {
		return elId;
	}

	public void setElId(String elId) {
		this.elId = elId;
	}

	public String getRtRegex() {
		return rtRegex;
	}

	public void setRtRegex(String rtRegex) {
		this.rtRegex = rtRegex;
	}

	public Integer getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(Integer timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public Integer getRootTimeSlot() {
		return rootTimeSlot;
	}

	public void setRootTimeSlot(Integer rootTimeSlot) {
		this.rootTimeSlot = rootTimeSlot;
	}

	public Integer getPrevTimePath() {
		return prevTimePath;
	}

	public void setPrevTimePath(Integer prevTimePath) {
		this.prevTimePath = prevTimePath;
	}

	public Integer getFutTimePath() {
		return futTimePath;
	}

	public void setFutTimePath(Integer futTimePath) {
		this.futTimePath = futTimePath;
	}

	public LinkedList<RTContainer> getFirstAlternatives() {
		return firstAlternatives;
	}

	public void setFirstAlternatives(LinkedList<RTContainer> firstAlternatives) {
		this.firstAlternatives = firstAlternatives;
	}
	
	public Map<RTContainer, LinkedList<RTContainer>> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(Map<RTContainer, LinkedList<RTContainer>> alternatives) {
		this.alternatives = alternatives;
	}

	public String getAltElsId(RTContainer altFirst){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < getAlternatives().get(altFirst).size(); i++)
			sb.append(getAlternatives().get(altFirst).get(i).getClearElId() + (i < getAlternatives().get(altFirst).size() - 1 ? "_" : ""));
		return sb.toString();
	}

	public RTContainer getTrySuccess() {
		return trySuccess;
	}

	public void setTrySuccess(RTContainer trySuccess) {
		this.trySuccess = trySuccess;
	}

	public RTContainer getTryFailure() {
		return tryFailure;
	}

	public void setTryFailure(RTContainer tryFailure) {
		this.tryFailure = tryFailure;
	}	

	public RTContainer getTryOriginal() {
		return tryOriginal;
	}

	public void setTryOriginal(RTContainer tryOriginal) {
		this.tryOriginal = tryOriginal;
	}

	public boolean isSuccessTry() {
		return successTry;
	}

	public void setSuccessTry(boolean successTry) {
		this.successTry = successTry;
	}

	public Integer getTimePath() {
		return timePath;
	}

	public void setTimePath(Integer timePath) {
		this.timePath = timePath;
	}
	
	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	public Integer getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Const getCardType() {
		return cardType;
	}

	public void setCardType(Const cardType) {
		this.cardType = cardType;
	}
		
	public List<String> getFulfillmentConditions() {
		return fulfillmentConditions;
	}
	
	public void addFulfillmentConditions(String fulfillmentConditions) {
		if(fulfillmentConditions != null && !fulfillmentConditions.isEmpty())
			this.fulfillmentConditions.addAll((List<String>)java.util.Arrays.asList(fulfillmentConditions.split("%")));
	}
	
	public void addFulfillmentConditions(List<String> fulfillmentConditions) {
		this.fulfillmentConditions.addAll(fulfillmentConditions);
	}
	
	public List<String> getAdoptionConditions() {
		return adoptionConditions;
	}
	
	public void addAdoptionConditions(String adoptionConditions) {
		if(adoptionConditions != null && !adoptionConditions.isEmpty())
			this.adoptionConditions.addAll((List<String>)java.util.Arrays.asList(adoptionConditions.split("%")));
	}
	
	public void addAdoptionConditions(List<String> adoptionConditions) {
		this.adoptionConditions.addAll(adoptionConditions);
	}
	
	public RTContainer getRoot() {
		return root;
	}

	public void setRoot(RTContainer root) {
		this.root = root;		
	}

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}

	@Override
	public int compareTo(RTContainer gc) {

		int pathC = getPrevTimePath().compareTo(gc.getPrevTimePath());
		int timeC = getTimeSlot().compareTo(gc.getTimeSlot());
		String elId = getElId();
		
		if(pathC != 0){
			return pathC;
		}else if (timeC != 0 ){
			return timeC;
		}else if(elId != null){
			return getElId().compareTo(gc.getElId());
		}else{
			return 0; //TODO is it right?
		}
	}		
}