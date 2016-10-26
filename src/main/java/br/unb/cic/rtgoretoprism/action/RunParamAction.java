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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.progress.IProgressService;
import org.troposproject.util.ProcessCallback;
import org.troposproject.util.Spawn;

import br.unb.cic.rtgoretoprism.console.ConsoleUtil;
import br.unb.cic.rtgoretoprism.generator.goda.producer.RTGoreProducer;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;

/**
 * This class allow the user to launch the selected agent (KL) into an (already 
 * started) Jadex agent platform.
 * 
 * @author bertolini
 *
 */
//TODO: this should be rewritten in order to provide more flexibiity/robustness
public class RunParamAction extends AbstractCodeGeneractionAction{
	/** workbench part owing action selection element*/
	IWorkbenchPart targetPart;
	/** the source file for the transformation */
	IFolder selectedFolder;
	private IWorkbenchWindow window;	
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
	
	public void selectionChanged(IAction action, ISelection selection) {
		StructuredSelection ss = (StructuredSelection) selection;
		//rememeber the selected file, it will be our source for the transformation
		selectedFolder = (IFolder) ss.getFirstElement();
	} */


	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
    public void run(IAction action) {
    	//get current shell
    	//Shell shell = targetPart.getSite().getShell();
    	
    	//update input/output folders values
    	updateUsedFolders();
    	
        //create a long-running op for the code generation process
        IWorkbench wb = PlatformUI.getWorkbench();
        IProgressService ps = wb.getProgressService();

    	try {
			ps.busyCursorWhile(new RunnableWithProgressCallback());
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;		
	}
	
	class RunnableWithProgressCallback implements IRunnableWithProgress, ProcessCallback{
		
		IProgressMonitor monitor;
		long startTime;		
		String agentName;
		String goals;		
		
		public void run( IProgressMonitor monitor )
				throws InvocationTargetException, InterruptedException {
			
			this.monitor = monitor;
			
			
			//show user that work is progressing
			monitor.beginTask("Generating PARAM formulas", IProgressMonitor.UNKNOWN );

	    	for(Actor actor : selectedActors){
		    	try {
		    		//Note: this should be generalized
		    		//String folderName = selectedFolder.getName();
		    		
		    		//we expect something like BASIC_AGENT_PACKAGE_PREFIX_XXX
		    		//get the name of the agent to be started
		    		//String agentName = folderName.substring( PathLocation.BASIC_AGENT_PACKAGE_PREFIX.length() );
		    		
		    		RTGoreProducer producer = new RTGoreProducer(selectedActors, selectedGoals, sourceFolder, targetFolder, true);
					producer.run();
		    		
		    		agentName = actor.getName().replaceAll("\n", "_");			    						
		    		
		    		StringBuilder pctl = new StringBuilder("P=? [ true U (");
		    		StringBuilder goals = new StringBuilder();
		    		int i = 0;
		    		for(FHardGoal goal : selectedGoals){
		    			pctl.append(AgentDefinition.parseElId(goal.getName()) + (i < selectedGoals.size() - 1 ? "&" : ""));
		    			goals.append(AgentDefinition.parseElId(goal.getName()));
		    			i++;
		    		}
		    		pctl.append(") ]");
		    		FileUtility.deleteFile(targetFolder + "/AgentRole_" + agentName + "/reachability.pctl", false);
		    		FileUtility.writeFile(pctl.toString(), targetFolder + "/AgentRole_" + agentName + "/reachability.pctl");
		    		this.goals = goals.toString();
		    		
					String cmd = "./param";
					String arg1 = targetFolder + "/AgentRole_" + agentName + "/" + agentName + ".pm";
					String arg2 = targetFolder + "/AgentRole_" + agentName + "/reachability.pctl";		
					String arg3 = "--result-file";
					String arg4 = goals.toString();
					
					//get the output console for this agent
					MessageConsole myConsole = ConsoleUtil.findConsole( agentName );
					MessageConsoleStream out = myConsole.newMessageStream();
		
					//run the process
					startTime = new Date().getTime();
					Spawn spawn = new Spawn( new File(toolsFolder), out, out, this, new String[]{cmd, arg1, arg2, arg3, arg4});
					spawn.start();
					
					//remove the temporary pctl formula
					//FileUtility.deleteFile(targetFolder + "AgentRole_" + agentName + "/reachability.pctl");
		    	}
		    	catch (Exception e ) {
		    		MessageDialog.openError( shell, "Error starting PARAM for the selected Actor", e.toString() );
		    	}
	    	}
		}
		
		@Override
		public void runAfterExit(int exitStatus, ArrayList list) {
			
			if(exitStatus == 0){
				//close the monitor
				monitor.done();
				System.out.println("PARAM Formula created, exit status: " + exitStatus); 
				System.out.println("Goal(s): " + this.goals + "\t\t\tTime: " + (new Date().getTime() - this.startTime) + "\t\tSize: " + FileUtility.fileSize(targetFolder + "AgentRole_" + agentName + '/' + this.goals + ".out"));
			}else{
				System.out.println("Error generating PARAM Formula " + this.goals);
			}
		}
	}
}