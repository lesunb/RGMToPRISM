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

package br.unb.cic.rtgoretoprism;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 * morandini: added default variable values 
 * @author bertolini, morandini 
 */
public class AgentTemplateCreatorPlugin extends AbstractUIPlugin {
	//preferences data references
	//atc part
	public static final String ATC_INTERNAL_SOURCE_PATH = "atc.internal.source.path";
	public static final String ATC_USE_INTERNAL_SOURCE_PATH = "atc.use.internal.source.path";
    public static final String ATC_SOURCE_PATH = "atc.source.path";
    public static final String ATC_TARGET_PATH = "atc.target.path";
    //jadex part
    public static final String JADEX_BASE_PATH = "jadex.base.path";
    


	//The shared instance.
	private static AgentTemplateCreatorPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public AgentTemplateCreatorPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static AgentTemplateCreatorPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("it.itc.sra.taom4e.t2xJadexGenerator", path);
	}
	
	/** 
	 * Initializes a preference store with default preference values 
	 * for this plug-in.
	 * @param store the preference store to fill
	 */
	protected void initializeDefaultPreferences(IPreferenceStore store) {
		String internalSourcePath;
		String jadexLibSourcePath;
		String currentWorkspace=ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute().toString();
		
		try {
			//get the location of the internal Template used into code generation process
			internalSourcePath = AgentTemplateCreatorPlugin.findFileInPlugin( 
					"it.itc.sra.taom4e.t2xJadexGenerator", "TemplateInput" ).toString();
		} catch( Exception e ) { //should never happen
			internalSourcePath = "undefined";
		}
		
		store.setDefault( ATC_INTERNAL_SOURCE_PATH, internalSourcePath );
		store.setDefault( ATC_USE_INTERNAL_SOURCE_PATH, true );
		store.setDefault( ATC_SOURCE_PATH, internalSourcePath );
		store.setDefault( ATC_TARGET_PATH, currentWorkspace+"/KLAgents/src/" );
		
		//System.out.println***(ResourcesPlugin.getWorkspace().getPathVariableManager().getPathVariableNames());
		
		//Path jadexPath=new Path("%ECLIPSE_HOME%/plugins/it.itc.sra.taom4e.t2xJadexGenerator/lib");	 
		//IPath jadexFullPath = ResourcesPlugin.getWorkspace().getPathVariableManager().resolvePath(jadexPath);
		
		
		try {
			//get the location of the internal Template used into code generation process
			jadexLibSourcePath = AgentTemplateCreatorPlugin.findFileInPlugin( 
					"it.itc.sra.taom4e.t2xJadexGenerator", "lib" ).toString();
		} catch( Exception e ) { //should never happen
			jadexLibSourcePath = "undefined";
		}
		store.setDefault( JADEX_BASE_PATH, jadexLibSourcePath);

	}
	
	/**
	 * Get the location of the specified File inside the selected plugin
	 * 
	 * @param plugin id of the plugin to query on
	 * @param file the file path to search for
	 * 
	 * @return the complete path of the selected file inside specified plugin
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static IPath findFileInPlugin(String plugin, String file) throws 
		MalformedURLException, IOException {
		Bundle bundle = Platform.getBundle(plugin);
		URL fileURL = bundle.getEntry(file);
		URL localJarURL = Platform.asLocalURL(fileURL);
		IPath filePath = new Path(localJarURL.getPath());
		
		return filePath;
	}
}