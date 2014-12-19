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

import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * This class define a Console page that will contain some output for the
 * Agent generation process 
 *  
 * @author bertolini
 */
public class ATCConsole {
	private static final String ATC_CONSOLE_NAME = "ATC_Console";
	
	//the shared console
	private static MessageConsole myConsole;
	
	static {
		myConsole = ConsoleUtil.findConsole( ATC_CONSOLE_NAME );
	}
	

	/**
	 * Creates a new ATCConsole instance
	 *
	 */
	public ATCConsole() {
		//assure the console window is showed
		//IConsole myConsole = ...;//your console instance
		//IWorkbenchPage page = ...;//obtain the active page
		//String id = IConsoleConstants.ID_CONSOLE_VIEW;
		//IConsoleView view = (IConsoleView) page.showView(id);
		//view.display(myConsole);
	}
	
	/**
	 * Write the specified text to the console
	 * 
	 * @param text the text to be written on the console
	 */
	public static void println( String text ) {
		MessageConsoleStream out = myConsole.newMessageStream();
		out.println( text );
	}
	
	public static void print( String text ) {
		MessageConsoleStream out = myConsole.newMessageStream();
		out.print( text );
	}
}