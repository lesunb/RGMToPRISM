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

package br.unb.cic.rtgoretoprism.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

/**
 * A set (actually just one) of utility for managing Eclipse Consoles
 * 
 * @author bertolini
 */
public class ConsoleUtil {
	/**
	 * Find the console with the specified name or create a new one if none
	 * found
	 * 
	 * @param name the name of the console to look for
	 * 
	 * @return the (possibly new) console with the searched name
	 */
    public static MessageConsole findConsole(String name) {
    	ConsolePlugin plugin = ConsolePlugin.getDefault();
    	IConsoleManager conMan = plugin.getConsoleManager();
    	//the list of existing consoles
    	IConsole[] existing = conMan.getConsoles();
    	
    	//look for already existing ones
    	for (int i = 0; i < existing.length; i++) {
    		if( name.equals(existing[i].getName()) )
    			return (MessageConsole) existing[i];
    	}
    	
    	//no console found, so create a new one
    	MessageConsole myConsole = new MessageConsole(name, null);
    	//register it
    	conMan.addConsoles(new IConsole[]{myConsole});
    	
    	return myConsole;
    }
}