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

import it.itc.sra.taom4e.model.core.gencore.TroposModelElement;

import java.util.ArrayList;
import java.util.Hashtable;

import br.unb.cic.rtgoretoprism.util.NameUtility;

/**
 * A generic tropos element container based on a wrapped hashtable. It will 
 * maintain a list of contribution related to a Tropos element
 * 
 * @author Mirko Morandini
 */
public class ElementContainer {
	//the name of the contained element
	private String name="";
	
	// child goals at decompositions
	protected ArrayList<GoalContainer> goals;

	// means end plans
	protected ArrayList<PlanContainer> plans;
	
	protected Const decomposition = Const.NONE;
	
	//the container hashtable
	private Hashtable<SoftgoalContainer, String> contributions;// the softgoal is the key here!

	/**
	 * Creates a new ElementContainer instance
	 * 
	 * @param the tropos element to be contained
	 */
	public ElementContainer( TroposModelElement m ) {
		this.name = NameUtility.adjustName( m.getName() );
		
		contributions = new Hashtable<SoftgoalContainer, String>();
	}

	/**
	 * Get the name of the contained element
	 *  
	 * @return the name of the contained element
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Add a contribution to this softgoal
	 * 
	 * @param sgcont the softgoal container
	 * @param metric the contribution metric
	 */
	public void addContribution(SoftgoalContainer sgcont, String metric) {
		contributions.put(sgcont, metric);// the softgoal is the key here!
	}
	
	/**
	 * Get the contirbution for this tropos element
	 * 
	 * @return the list of contributions
	 */
	public Hashtable<SoftgoalContainer, String> getContributions() {
		return contributions;
	}
	

	/**
	 * @param and
	 */
	public void createDecomposition(Const decomp) {
		decomposition = decomp;
		if (decomp == Const.AND) {

		} else if (decomp == Const.OR || decomp == Const.ME) {

		}
	}

	/**
	 * 
	 * @return
	 */
	public Const getDecomposition() {
		return decomposition;
	}
}