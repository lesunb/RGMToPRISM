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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.progress.IProgressService;
import org.troposproject.util.ProcessCallback;
import org.troposproject.util.Spawn;

import br.unb.cic.rtgoretoprism.console.ConsoleUtil;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.producer.CRGMEvaluationCtProducer;
import br.unb.cic.rtgoretoprism.generator.goda.writer.PrismWriter;
import br.unb.cic.rtgoretoprism.generator.goda.writer.dtmc.DTMCWriter;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.gui.EvaluationSettingsDialog;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import it.itc.sra.taom4e.model.core.informalcore.Plan;

/**
 * This action let the user to start the process of generating agent code for
 * the selected Tropos's system actors from a Taom4e' diagram.
 * This will create the Jadex BDI agents for the knowledge level side.
 *
 * @see it.itc.sra.taom4e.t2xJadexGenerator.action.CLCodeGenerationAction for 
 *      the capability level side
 *      
 * @author bertolini
 */
public class EvaluatePRISMAction extends AbstractCodeGeneractionAction {
	private IWorkbenchWindow window;
	
	/**
	 * Creates a new KLCodeGenerationAction instance
	 */
	public EvaluatePRISMAction() {
		super();
	}
	
	/**
	 * Execute the action body
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		//update input/output folders values
		updateUsedFolders();
		
		 //create a long-running op for the code generation process
        IWorkbench wb = PlatformUI.getWorkbench();
        IProgressService ps = wb.getProgressService();

    	try {
    		EvaluationSettingsDialog settingsDialog = new EvaluationSettingsDialog(shell);
			if (settingsDialog.open() == Window.OK) {				
				ps.busyCursorWhile(new RunnableWithProgressCallback(settingsDialog.getMaxDepth(), settingsDialog.getBranches()));
			}			
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;		
	}

	class RunnableWithProgressCallback implements IRunnableWithProgress, ProcessCallback{
		
		IProgressMonitor monitor;
		long startTime;
		int currentDepth = 2;
		int MAX_DEPTH = 7;
		int BRANCHES = 2;
		String agentName;
		
		public RunnableWithProgressCallback(int maxDepth, int branches) {
			this.MAX_DEPTH = maxDepth;
			this.BRANCHES = branches;
		}
		
		public void run( IProgressMonitor monitor )
				throws InvocationTargetException, InterruptedException {
			
			this.monitor = monitor;
			
			//show user that work is progressing
			monitor.beginTask("Evaluating PRISM model",  MAX_DEPTH + 1);
			evaluatePrism();
	       
		}
		
		public void evaluatePrism(){

			if(currentDepth > MAX_DEPTH)
				return;
			
	        try {
	        	agentName = "EvaluationActor";
	        	String cmd = "./prism-4.2";
				String arg1 = agentName + ".pm";
				String arg2 = "reachability.pctl";
				
				MessageConsole myConsole = ConsoleUtil.findConsole( agentName );
				MessageConsoleStream out = myConsole.newMessageStream();
				
				CRGMEvaluationCtProducer evaluationProducer = new CRGMEvaluationCtProducer(currentDepth, currentDepth - 1, BRANCHES, agentName);        								
				//CRGMEvaluationProducer evaluationProducer = new CRGMEvaluationProducer(BRANCHES, Const.AND, Const.SEQ, Const.NONE, agentName);
				AgentDefinition ad = evaluationProducer.generateCRGM();
				PrismWriter writer = new DTMCWriter( ad, new ArrayList<Plan>(), sourceFolder, targetFolder, false);
				writer.writeModel();					
				Spawn spawn = new Spawn( new File(targetFolder + "AgentRole_" + agentName), out, out, this, new String[]{cmd, arg1, arg2});
				startTime = new Date().getTime();
				spawn.start();
				System.out.println("Initing evaluation of " + ad.planbase.size() + " leaf-tasks with current tree depth of " + currentDepth);
				System.out.println("Waiting for PRISM model to be built");
			} catch (CodeGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void runAfterExit(int exitStatus, ArrayList list) {
			
			if(exitStatus == 0){				
				
				System.out.println("PRISM model built, exit status: " + exitStatus); 
				System.out.println("Time: " + (new Date().getTime() - this.startTime) + "\t\tSize: " + FileUtility.fileSize(targetFolder + "/AgentRole_" + agentName + "/" + this.agentName + ".pm"));
				
				//close the monitor
				if(currentDepth == MAX_DEPTH)
					monitor.done();
				else
					monitor.worked(currentDepth);

				currentDepth++;
				evaluatePrism();
			}else{
				System.out.println("Error building PRISM model");
			}					
		}
	}	
}