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

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.troposproject.util.Spawn;

import br.unb.cic.rtgoretoprism.AgentTemplateCreatorPlugin;
import br.unb.cic.rtgoretoprism.console.ConsoleUtil;
import br.unb.cic.rtgoretoprism.util.PathLocation;

/**
 * This class allow the user to launch the selected agent (KL) into an (already 
 * started) Jadex agent platform.
 * 
 * @author bertolini
 *
 */
//TODO: this should be rewritten in order to provide more flexibiity/robustness
public class RunJadexAgentAction extends ActionDelegate implements IObjectActionDelegate {
	/** workbench part owing action selection element*/
	IWorkbenchPart targetPart;
	/** the source file for the transformation */
	IFolder selectedFolder;

    /*
     *  (non-Javadoc)
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
     */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		StructuredSelection ss = (StructuredSelection) selection;
		//rememeber the selected file, it will be our source for the transformation
		selectedFolder = (IFolder) ss.getFirstElement();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionDelegate#init(org.eclipse.jface.action.IAction)
	 */
	public void init(IAction action) {
		super.init(action);
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
    public void run(IAction action) {
    	//get current shell
    	Shell shell = targetPart.getSite().getShell();

    	//get the path to the Jadex library directory
    	String bx = AgentTemplateCreatorPlugin.getDefault().getPluginPreferences().
    		getString( AgentTemplateCreatorPlugin.JADEX_BASE_PATH ) + "/"; 

    	try {
    		//Note: this should be generalized
    		String folderName = selectedFolder.getName();
    		
    		//we expect something like BASIC_AGENT_PACKAGE_PREFIX_XXX
    		//get the name of the agent to be started
    		String agentName = folderName.substring( PathLocation.BASIC_AGENT_PACKAGE_PREFIX.length() );

    		//get the corresponding bin directory (with classes file) of this source one (src). This
    		//assume that the project use the bin/src directory (as good practices suggest...)
			String bin = "\""+selectedFolder.getProject().getFolder( "bin" ).getLocation().toString()+"\""; // .toOSString();

			
	    	//construct the required CLASSPATH path (at least the one for 
			//Jade 3.4 and Jadex 0.96). 
	    	//Note: this should be really generalized
			
	    	String libs =  ".;\"" +
				bx + "jadeTools.jar" + "\";\"" + 
				bx + "jade.jar" + "\";\"" +
				bx + "iiop.jar" + "\";\"" +
				bx + "http.jar" + "\";\"" +
				bx + "commons-codec-1.3.jar" + "\";\"" +
				bx + "jadex_rt.jar" + "\";\"" +
				bx + "jadex_jadeadapter.jar" + "\";\"" +
	    		bx + "jibx-run.jar" + "\";\"" + 
	    		bx + "jadex_rt.jar" + "\";\"" + 
	    		bx + "xpp3.jar" + "\";\"" + 
	    		bx + "GraphLayout.jar" + "\";\"" + 
	    		bx + "jhall.jar" + "\";\"" + 
	    		bx + "jadex_tools.jar\"";
		
			//launch the agent in a new container
			String cmd = "java -cp " + libs + ";" + bin + " jade.Boot -container My" + 
			agentName + ":jadex.adapter.jade.JadeAgentAdapter(" + folderName + "." + agentName + " default)";
			
			//get the output console for this agent
			MessageConsole myConsole = ConsoleUtil.findConsole( agentName );
			MessageConsoleStream out = myConsole.newMessageStream();

			//run the process
			Spawn spawn = new Spawn( cmd, out, out );
			spawn.start();
    	}
    	catch (Exception e ) {
    		MessageDialog.openError( shell, "Error starting the selected Agent", e.toString() );
    	}
    }
}