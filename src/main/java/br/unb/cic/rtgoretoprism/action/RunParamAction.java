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

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.producer.PARAMProducer;

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
    	
    	//update input/output folders values
    	updateUsedFolders();
		
        /* Generating PCTL and PARAM formulas */
		try {
			PARAMProducer producer = new PARAMProducer(selectedActors, selectedGoals, sourceFolder, targetFolder, toolsFolder);
			producer.run();
		} catch (CodeGenerationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}