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

package br.unb.cic.rtgoretoprism.preferences;

import java.io.File;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import br.unb.cic.rtgoretoprism.CRGMToPrismPlugin;


/**
 * The main preference page for the 'Agent Template Creator' related setting.
 * It allows customization of setting related to templates, Jade and Jadex 
 * elements.
 * 
 * @author bertolini, morandini
 */
public class ATCPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	//gui elements related to the 'template' section 
	private Label templateDirSourceLabel;
	private Label templateSourcePathLabel; //the source location of user define template
	private Button templateSourcePathButton;
	private Label targetPathLabel; //the source code Jadex's agent target directory
	private Button useDefaultButton; //the use-default template button
	
	//gui elements related to the 'jadex' section
	private Label jadexPathLabel; //the jadex-lib path label
	private Button jadexPathButton; //the browse dir button


	
	/**
	 * @see IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		//Initialize the preference store we wish to use
		setPreferenceStore( CRGMToPrismPlugin.getDefault().getPreferenceStore() );
	}
	
	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite( parent, SWT.NONE );

		// Create a data that takes up the extra space in the dialog.
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		GridLayout layout = new GridLayout();
		composite.setLayout(layout);

		//create the different gui main sections
		//template one
		createContentsATCPart( composite );
		new Label( composite, SWT.NONE );

		//and jadex one
		createContentsJadexPart( composite );
		
		return composite;
	}

	/**
	 * Create the gui element for the 'template' section of this page
	 * 
	 * @param composite the composite to write on
	 */
	private void createContentsATCPart( Composite composite ) {
		//get current preference value
		final String pv_sourcePath = CRGMToPrismPlugin.getDefault().getPluginPreferences().
			getString( CRGMToPrismPlugin.ATC_SOURCE_PATH );
		final String pv_targetPath = CRGMToPrismPlugin.getDefault().getPluginPreferences().
			getString( CRGMToPrismPlugin.ATC_TARGET_PATH );
		boolean pv_useInternal = CRGMToPrismPlugin.getDefault().getPluginPreferences().
			getBoolean( CRGMToPrismPlugin.ATC_USE_INTERNAL_SOURCE_PATH );		
		
		Group templateGroup = new Group( composite, SWT.SHADOW_ETCHED_IN );
		templateGroup.setText("Source Template");
		templateGroup.setLayout( new GridLayout( 3, false) );
		
		GridData data = new GridData( GridData.FILL_HORIZONTAL );
		templateGroup.setLayoutData( data );

		Composite inner = new Composite( templateGroup, SWT.NONE );
		inner.setLayout( new RowLayout() );
		data = new GridData();
		data.horizontalSpan = 3;
		inner.setLayoutData( data );
		
		//the button that manage the use of default/internal template vs user provided one
		useDefaultButton = new Button( inner, SWT.CHECK );
		useDefaultButton.setSelection( pv_useInternal );

		Label useDefaultLabel = new Label( inner, SWT.NONE );
		useDefaultLabel.setText( "Use standard" );
		
		
		templateDirSourceLabel = new Label( templateGroup, SWT.NONE );
		templateDirSourceLabel.setText( "Template directory " );
		templateDirSourceLabel.setEnabled( !pv_useInternal );
		
		templateSourcePathLabel = new Label( templateGroup, SWT.NONE ); 
		templateSourcePathLabel.setText( pv_sourcePath );
		templateSourcePathLabel.setEnabled( !pv_useInternal );

		GridData data2 = new GridData( GridData.FILL_HORIZONTAL );
		templateSourcePathLabel.setLayoutData( data2 );

		templateSourcePathButton = new Button( templateGroup, SWT.PUSH );
		templateSourcePathButton.setText( "Select" );
		templateSourcePathButton.setEnabled( !pv_useInternal );
		
		//the listener for showing the dir selection dialog
		templateSourcePathButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog( e.widget.getDisplay().getActiveShell() );
				
				dirDialog.setText("User defined template directory selection");
				dirDialog.setMessage("Select a directory");
				
				File f = new File(pv_sourcePath);
		    	dirDialog.setFilterPath(f.getAbsolutePath());
		    	
				String dirPath = dirDialog.open();
			
				// if the path was not defined - return
				if (dirPath != null) 
					templateSourcePathLabel.setText( dirPath );
			}
		});
		
		
		Group codeGenGroup = new Group( composite, SWT.SHADOW_ETCHED_IN );
		codeGenGroup.setText("Code generation");
		codeGenGroup.setLayout( new GridLayout( 3, false) );
		
		data = new GridData( GridData.FILL_HORIZONTAL );
		codeGenGroup.setLayoutData( data );

		
		Label targetLabel = new Label( codeGenGroup, SWT.NONE );
		targetLabel.setText( "Output directory " );

		targetPathLabel = new Label( codeGenGroup, SWT.NONE );
		targetPathLabel.setText( pv_targetPath );
		
		GridData data3 = new GridData( GridData.FILL_HORIZONTAL );
		targetPathLabel.setLayoutData( data3 );

		Button targetPathButton = new Button( codeGenGroup, SWT.PUSH );
		targetPathButton.setText( "Select" );

		//the listener for showing the dir selection dialog
		targetPathButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog( e.widget.getDisplay().getActiveShell() );
				
				dirDialog.setText("Target agent skeleton directory selection");
				dirDialog.setMessage("Select a directory");

				File f = new File(pv_targetPath);
		    	dirDialog.setFilterPath(f.getAbsolutePath());
		    	
		    	String dirPath = dirDialog.open();
				// if the path was not defined - return
				if (dirPath != null) 
					targetPathLabel.setText( dirPath );
			}
		});
			
		//update gui state based on gui event
		useDefaultButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean selected = ( (Button) e.widget).getSelection();
				
				templateSourcePathButton.setEnabled( !selected );
				templateDirSourceLabel.setEnabled( !selected );
				templateSourcePathLabel.setEnabled( !selected );
			}
			});
	}
	

	
	/**
	 * Create the gui element for the 'jadex' section of this page
	 * 
	 * @param composite the composite to write on
	 */
	private void createContentsJadexPart( Composite composite ) {
		//get current preference value
		final String value = CRGMToPrismPlugin.getDefault().getPluginPreferences().
			getString( CRGMToPrismPlugin.JADEX_BASE_PATH );
		
		Group group = new Group( composite, SWT.SHADOW_ETCHED_IN );
		group.setText("PRISM/PARAM tools");
		group.setLayout( new GridLayout( 3, false) ); 
		
		GridData data1 = new GridData( GridData.FILL_HORIZONTAL );
		group.setLayoutData( data1 );
		
		Label label = new Label( group, SWT.NONE );
		label.setText( "Tools binaries directory" );
		
		jadexPathLabel = new Label( group, SWT.NONE );
		jadexPathLabel.setText( value );

		GridData data2 = new GridData( GridData.FILL_HORIZONTAL );
		jadexPathLabel.setLayoutData( data2 );

		jadexPathButton = new Button( group, SWT.PUSH );
		jadexPathButton.setText( "Select" );
		
		//the listener for showing the dir selection dialog
		jadexPathButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog( jadexPathButton.getShell() );
				
				dirDialog.setText("PRISM and PARAM binaries directory selection");
				dirDialog.setMessage("Select a directory");
				
				//get the path to the Jadex library directory
		    	//String bx = RTGoreToPrismPlugin.getDefault().getPluginPreferences().getString( RTGoreToPrismPlugin.JADEX_BASE_PATH ) + "/";
		    	
		    	File f = new File(value);
		    	dirDialog.setFilterPath(f.getAbsolutePath());
				String dirPath = dirDialog.open();
				// if the path was not defined - return
				if (dirPath != null) 
					jadexPathLabel.setText( dirPath );
			}
		});
	}
	
	/**
	 * Performs special processing when this page's Restore Defaults button 
	 * has been pressed.
	 */
	protected void performDefaults() {
		//get preferences default values
		String templateSourcePath = CRGMToPrismPlugin.getDefault().getPluginPreferences().
			getDefaultString( CRGMToPrismPlugin.ATC_SOURCE_PATH );
		String agentTargetDir = CRGMToPrismPlugin.getDefault().getPluginPreferences().
			getDefaultString( CRGMToPrismPlugin.ATC_TARGET_PATH );
		boolean useDefaultTemplate = CRGMToPrismPlugin.getDefault().getPluginPreferences().
			getDefaultBoolean( CRGMToPrismPlugin.ATC_USE_INTERNAL_SOURCE_PATH );

		String jadexLib = CRGMToPrismPlugin.getDefault().getPluginPreferences().
		getDefaultString( CRGMToPrismPlugin.JADEX_BASE_PATH );

		
		//update gui elements
		templateSourcePathLabel.setText( templateSourcePath );
		targetPathLabel.setText( agentTargetDir );
		useDefaultButton.setSelection( useDefaultTemplate );
		templateSourcePathButton.setEnabled( !useDefaultTemplate );
		templateDirSourceLabel.setEnabled( !useDefaultTemplate );
		templateSourcePathLabel.setEnabled( !useDefaultTemplate );
			
		jadexPathLabel.setText( jadexLib );
	}

	/** 
	 * Save the data to the preference store.
	 */
	public boolean performOk() {
		//set preferences new value
		CRGMToPrismPlugin.getDefault().getPluginPreferences().
			setValue( CRGMToPrismPlugin.ATC_SOURCE_PATH, templateSourcePathLabel.getText() );
		CRGMToPrismPlugin.getDefault().getPluginPreferences().
			setValue( CRGMToPrismPlugin.ATC_TARGET_PATH, targetPathLabel.getText() );
		CRGMToPrismPlugin.getDefault().getPluginPreferences().
			setValue( CRGMToPrismPlugin.ATC_USE_INTERNAL_SOURCE_PATH, useDefaultButton.getSelection() );
		 
		CRGMToPrismPlugin.getDefault().getPluginPreferences().
			setValue( CRGMToPrismPlugin.JADEX_BASE_PATH, jadexPathLabel.getText() );
		
		return super.performOk();
	}
}