/** 
 * <copyright>
 *
 * TAOM4E - Tool for Agent Oriented Modeling for the Eclipse Platform
 * Copyright (C) ITC-IRST, Trento, Italy
 * Authors: Davide Bertolini, Aliaksei Novikau
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

package br.unb.cic.rtgoretoprism.util;

/**
 * This interface define some constant used during the agent generation process
 * in order to share common info between the capability and the knowledge level
 * activities.
 *  
 * @author bertolini
 */
public interface PathLocation {
	public static final String BASIC_AGENT_PACKAGE_PREFIX 			= "AgentRole_";
	public static final String BASIC_FSM_PREFIX 					= "Fsm_";
	public static final String CAPABILITY_TESTER_AGENT_NAME_PREFIX 	= "CapabilityTester_";
	public static final String CAPABILITY_AGENT_NAME 				= "CapabilitiesAgent";
	
	/** the folder to which agent capabilities should be written */
	public static final String CAPABILITIES_FOLDER 					= "capabilities";

	/** the 'util' pkg for the CL part */
	public static final String UTIL_CL_PKG							= ".util.cl";
	/** the 'util' pkg for the KL part */
	public static final String UTIL_KL_PKG							= ".util.kl";
}