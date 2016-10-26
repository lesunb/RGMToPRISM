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

import java.io.IOException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.producer.RTGoreProducer;

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
public class PRISMCodeGenerationAction extends AbstractCodeGeneractionAction {
	private IWorkbenchWindow window;
	
	/**
	 * Creates a new KLCodeGenerationAction instance
	 */
	public PRISMCodeGenerationAction() {
		super();
	}
	
	/**
	 * Execute the action body
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {

		if(selectedActors.isEmpty())
			return;
		
		//update input/output folders values
		updateUsedFolders();
		
        //create a long-running op for the code generation process
        IWorkbench wb = PlatformUI.getWorkbench();
        IProgressService ps = wb.getProgressService();

        try {
        	/*	ps.busyCursorWhile( new IRunnableWithProgress() {
        		public void run( IProgressMonitor monitor )
        			throws InvocationTargetException, InterruptedException {
        			
        			//show user that work is progressing
        			monitor.beginTask("Generating PRISM model' source code", IProgressMonitor.UNKNOWN );

					try {*/
						//generate the source code for the KL side
						RTGoreProducer producer = new RTGoreProducer(selectedActors, selectedGoals, sourceFolder, targetFolder, false );
						producer.run();
					/*
						//refresh workspace folder
						refreshWorkspaceFolder( targetFolder, 
							new SubProgressMonitor( monitor, IProgressMonitor.UNKNOWN ) );
					} catch( CodeGenerationException cge ) { 
						throw new InvocationTargetException( cge, cge.getMessage() );
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//close the monitor
					monitor.done();
        		}
        	} ); 
       	} catch (InvocationTargetException ite) {
       		showErrorMessage("Invocation error: " , ite.getMessage(), shell );
   		} catch (InterruptedException ie) {
   			showErrorMessage("Interrupted error: " , ie.getCause().getMessage(), shell );
       	*/} catch (CodeGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
}