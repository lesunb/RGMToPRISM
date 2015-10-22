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

import br.unb.cic.rtgoretoprism.util.kl.TroposNavigator;

public class CRGMToPrismPlugin extends AbstractUIPlugin{
	
	//preferences data references
	//atc part
	public static final String ATC_INTERNAL_SOURCE_PATH = "atc.internal.source.path";
	public static final String ATC_USE_INTERNAL_SOURCE_PATH = "atc.use.internal.source.path";
    public static final String ATC_SOURCE_PATH = "atc.source.path";
    public static final String ATC_TARGET_PATH = "atc.target.path";
    //jadex part
    public static final String PRISM_PARAM_PATH = "atc.tools.path";
    
    public static final String PLUGIN_ID = "br.unb.cic.rTGoreToPrism";
	
	private static TroposNavigator tn;
	
	//The shared instance.
	private static CRGMToPrismPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public CRGMToPrismPlugin() {
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
	public static CRGMToPrismPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
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
			internalSourcePath = findFileInPlugin( 
					PLUGIN_ID, "src/main/resources/TemplateInput" ).toString();
		} catch( Exception e ) { //should never happen
			internalSourcePath = "undefined";
		}
		
		store.setDefault( ATC_INTERNAL_SOURCE_PATH, internalSourcePath );
		store.setDefault( ATC_USE_INTERNAL_SOURCE_PATH, true );
		store.setDefault( ATC_SOURCE_PATH, internalSourcePath );
		store.setDefault( ATC_TARGET_PATH, currentWorkspace+"/CRGMToPrismModels/dtmc/" );
		
		//System.out.println***(ResourcesPlugin.getWorkspace().getPathVariableManager().getPathVariableNames());
		
		//Path jadexPath=new Path("%ECLIPSE_HOME%/plugins/it.itc.sra.taom4e.t2xJadexGenerator/lib");	 
		//IPath jadexFullPath = ResourcesPlugin.getWorkspace().getPathVariableManager().resolvePath(jadexPath);
		
		
		try {
			//get the location of the internal Template used into code generation process
			jadexLibSourcePath = AgentTemplateCreatorPlugin.findFileInPlugin( 
					PLUGIN_ID, "lib" ).toString();
		} catch( Exception e ) { //should never happen
			jadexLibSourcePath = "undefined";
		}
		store.setDefault( PRISM_PARAM_PATH, jadexLibSourcePath);

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
	
	/**
	 * The main output, can be changed to everything
	 * @param s
	 */
	public static void print(final String s) {
		System.out.println(s);
	}
	
}
