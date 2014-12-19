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

package br.unb.cic.rtgoretoprism.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.troposproject.util.Spawn;

import br.unb.cic.rtgoretoprism.AgentTemplateCreatorPlugin;
import br.unb.cic.rtgoretoprism.console.ConsoleUtil;

/**
 * This class allow the user to launch the Jadex Platform (GUI) as an external
 * process from the Eclipse workspace
 *  
 * @author bertolini
 *
 */
//TODO: this should be rewritten in order to provide more flexibiity/robustness
public class JadexGUIAction implements IWorkbenchWindowActionDelegate {
    /** current window */
    IWorkbenchWindow window;
    
    /**
     * We will cache window object in order to be able to provide parent shell
     * for the message dialog.
     * 
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init(IWorkbenchWindow window) {
        this.window = window;
    }
    
    /**
     * Selection in the workbench has been changed.
     * 
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    public void selectionChanged(IAction action, ISelection selection) {
    	action.setEnabled( true );
    }

    /**
     * Show action dialog
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
    */
    public void run(IAction action) {
    	//get the path to the Jadex library directory
    	String bx = AgentTemplateCreatorPlugin.getDefault().getPluginPreferences().
    		getString( AgentTemplateCreatorPlugin.JADEX_BASE_PATH ) + "/"; 

    	//construct the required CLASSPATH path (at least the one for 
		//Jade 3.4 and Jadex 0.96). 
    	//Note: this should be really generalized
//		String libs =  ".;" +
//			bx + "jadeTools.jar" + ";" + 
//			bx + "jade.jar" + ";" +
//			bx + "iiop.jar" + ";" +
//			bx + "http.jar" + ";" +
//			bx + "commons-codec-1.3.jar" + ";" +
//			bx + "jadex_rt.jar" + ";" +
//			bx + "jadex_jadeadapter.jar";

    	String libs =  ".;\"" +
			bx + "jadeTools.jar" + "\";\"" + 
			bx + "jade.jar" + "\";\"" +
			bx + "iiop.jar" + "\";\"" +
			bx + "http.jar" + "\";\"" +
			bx + "commons-codec-1.3.jar" + "\";\"" +
			bx + "jadex_rt.jar" + "\";\"" +
			bx + "jadex_jadeadapter.jar\"";
		
		//the command to execute the platform gui
		String cmd = "java -cp " + libs + " jade.Boot rma:jadex.adapter.jade.tools.rma.rma";
		
		try {
			//get the console for the platform
			MessageConsole myConsole = ConsoleUtil.findConsole( "Jadex platform" );
			MessageConsoleStream out = myConsole.newMessageStream();
			//activate on write activity
			out.setActivateOnWrite( true );
			
			//run the process
			Spawn spawn = new Spawn( cmd, out, out );
			spawn.start();
		}
		catch (Exception e ) {
			MessageDialog.openError( window.getShell(), "Error starting the Jadex platform GUI", e.toString() );
		}
    }

    /**
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose() { }
}