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

import it.itc.sra.taom4e.model.core.informalcore.Actor;
import it.itc.sra.taom4e.model.core.informalcore.formalcore.FHardGoal;
import it.itc.sra.taom4e.model.diagram.mixeddiagram.MD_ActorUI;
import it.itc.sra.taom4e.model.diagram.mixeddiagram.MD_IntentionalUI;
import it.itc.sra.taom4e.platform.edit.parts.mixeddiagram.MD_ActorUIEditPart;
import it.itc.sra.taom4e.platform.edit.parts.mixeddiagram.MD_IntentionalUIEditPart;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.unb.cic.rtgoretoprism.RTGoreToPrismPlugin;

/**
 * The basic abstract class for action that generate code
 * 
 * @see it.itc.sra.taom4e.t2xJadexGenerator.action.KLCodeGenerationAction
 * @see it.itc.sra.taom4e.t2xJadexGenerator.action.CLCodeGenerationAction
 * 
 * @author bertolini
 */
public abstract class AbstractCodeGeneractionAction extends Action implements IWorkbenchWindowActionDelegate, IEditorActionDelegate {
	/** the set of user-selected systemActors that will be the source of the 
	 * code generation process */
	protected Set<Actor> selectedActors;
	
	protected Set<FHardGoal> selectedGoals;
	
	/** the current Shell */
	protected Shell shell;
	
	/** template source folder */
	protected String sourceFolder;
	
	/** generated file target folder */
	protected String targetFolder;


	/**
	 * Creates a new AbstractCodeGeneractionAction instance
	 */
	public AbstractCodeGeneractionAction() {
		//super();
		
		this.selectedActors = new HashSet<Actor>();
		this.selectedGoals = new HashSet<FHardGoal>();
	}
	
	/**
	 * Update the values of the template input and generated code folder
	 * based on user preferences 
	 */
	protected void updateUsedFolders() {
		//should we use internal or user-defined template?
		boolean useInternalTemplate = RTGoreToPrismPlugin
			.getDefault().getPluginPreferences().getBoolean(
				RTGoreToPrismPlugin.ATC_USE_INTERNAL_SOURCE_PATH );
		
		//get the proper path to the code generation template to be used
		sourceFolder = 
			RTGoreToPrismPlugin.getDefault().getPluginPreferences().getString(
			useInternalTemplate ? 		
				RTGoreToPrismPlugin.ATC_INTERNAL_SOURCE_PATH :
				RTGoreToPrismPlugin.ATC_SOURCE_PATH
		);	

		//get generated agents' target folder
		targetFolder = RTGoreToPrismPlugin.getDefault().
			getPluginPreferences().getString( RTGoreToPrismPlugin.ATC_TARGET_PATH );
	}
	
	/**
	 * Allow the selection only if a set of (sub)Actor has been chosen
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		//at first disable the action
		action.setEnabled( false );
		
		//if( shell == null )
		//	return;
		
		//assure no actor is selected
		selectedActors.clear();
		selectedGoals.clear();

		//then verify if it is necessary to enable it
		if( !(selection instanceof StructuredSelection) )
			return;
		
		//current selection
		StructuredSelection ss = (StructuredSelection) selection;
		
		Iterator it = ss.iterator();
		
		while( it.hasNext() ) {
			//current selected element
			Object selected = it.next();
			
			//we expect only an MD_ActorUIEditPart type
			if( (selected instanceof MD_IntentionalUIEditPart) ){
				MD_IntentionalUIEditPart currEP = (MD_IntentionalUIEditPart) selected;			
				MD_IntentionalUI currActorUI = (MD_IntentionalUI) currEP.getModel();
				//current selected Actor model objectMD_ActorUIEditPart
				if(currActorUI.getSingleCore() instanceof FHardGoal){
					FHardGoal currGoal = (FHardGoal) currActorUI.getSingleCore();
					//add it to the set
					selectedGoals.add( currGoal );
					selectedActors.add(currGoal.getActor());
					action.setEnabled( true );
				}
			}else{
				//we expect only an MD_ActorUIEditPart type
				if( !(selected instanceof MD_ActorUIEditPart) )
					return;
				
				MD_ActorUIEditPart currEP = (MD_ActorUIEditPart) selected;			
				MD_ActorUI currActorUI = (MD_ActorUI) currEP.getModel();
				//current selected Actor model objectMD_ActorUIEditPart
				Actor currActor = (Actor) currActorUI.getSingleCore();
	
				//mm: Commented to allow code generation for all agents
				////we accept only system one
				//if( !currActor.isIsSystem() )
				//	return;
				
				//add it to the set
				selectedActors.add( currActor );
				
				//get all owned subsystems, if any
				List subSystems = currActor.getSubsystems();
				//add the to the set too
				for( Object a : subSystems )
					selectedActors.add( (Actor) a );
			}
			
			//if here, we are ok so enable the action back
			action.setEnabled( true );
		}
	}
	
	/**
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if( targetEditor != null ) {
			shell = targetEditor.getEditorSite().getShell();
		}
		else {
			shell = null;
		}
	}

    /**
	 * Prints a message in a new error message box.
	 * @param message
	 * @param shell
	 */
	protected void showErrorMessage(String title, String message, Shell shell) {
		MessageBox mb = new MessageBox( shell, SWT.ICON_ERROR | SWT.OK);
		mb.setText( title );
		mb.setMessage(message);
		mb.open();
	}
	
	/**
	 * Refresh the workspace resource related to the folder into which agent's
	 * code has been generated
	 * 
	 * @param target the target folder for the agent generation process
	 * @param monitor the (sub)progress monitor to be used
	 * 
	 * @throws InvocationTargetException
	 */
	protected void refreshWorkspaceFolder( String target, IProgressMonitor subMonitor ) throws InvocationTargetException {
		//get workspace root
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		
		//get the folder resource for the specified target dir
		IContainer folder = workspaceRoot.getContainerForLocation( new Path( target ) );
		
		//if found into workspace, refresh its content
		if( folder != null ) {
			try {
				folder.refreshLocal( IResource.DEPTH_INFINITE, subMonitor );
			} catch( CoreException ce ) {
				throw new InvocationTargetException( ce, ce.getMessage() );
			}
		}
	}
}