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

package br.unb.cic.rtgoretoprism.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;


/**
 * This class offers some basic functionality in order to simplify the work with 
 * EMF resource from Eclipse based or standalone (see emf-sdo-xsd-Standalone-2.1.0.zip 
 * for related jars) application.
 *  
 * @author bertolini
 */
public class TroposEMFHelper {
	//the managed resourceSet
	private ResourceSet resourceSet;
	
	/**
	 * Create an EMF resource for the supplied URI
	 * 
	 * @param uri the uri to create the resource for
	 * @return the created Resource
	 */
	public Resource createResource( URI uri ) {
		Resource res = resourceSet.createResource( uri );
		res.setURI( uri );
		
		return res;
	}

	/**
	 * Get an EMF resource for the supplied URI
	 * 
	 * @param uri the uri to get the resource for
	 * 
	 * @return a resource for the supplied uri
	 */
	public Resource getResource( URI uri ) {
		Resource res = resourceSet.getResource( uri, true );
		//res.setURI( uri );
		
		return res;
	}

	
	/**
	 * Initialize the EMF resources for the supported extensions
	 * NOTE: required emf package should be initialized by the clients
	 *
	 */
	private void initializeEMFFactories() {
		// Initialize the packages
		//ProjectPackageImpl.init();
		//In addition, you'll also need to register your package, XyzPackage.eINSTANCE.
		
		// Register the various resource factories for the required extensions
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		
		Map m = reg.getExtensionToFactoryMap();
		m.put( Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl() );
		m.put( "xml", new XMLResourceFactoryImpl() );
		m.put( "tropos", new XMIResourceFactoryImpl() );

		//In order for EMF to locate a dynamic package that has been built up 
		//programmatically, it must be registered in a package registry. For 
		//example, the following code globally registers companyPackage: 
		//EPackage.Registry.INSTANCE.put(companyPackage.getNsURI(), companyPackage); 
	}
	
	/**
	 * Get the resultSet from which all the resources are obtained.
	 * 
	 * @return the created ResourceSet
	 */
	public ResourceSet getResourceSet() {
		if( resourceSet == null ) {
			// Obtain a new resource set
			resourceSet = new ResourceSetImpl();
		}
		
		return resourceSet;
	}
	
	/**
	 * Save the contents of the specified resource
	 * 
	 * @param res the resource to be saved
	 * 
	 * @throws IOException
	 */
	public void saveResource( Resource res ) throws IOException {
		Map options = new HashMap();
		options.put(XMIResource.OPTION_DECLARE_XML, Boolean.TRUE);
		res.save(options);
	}

	/**
	 * Save the contents of the specified resource
	 * 
	 * @param res the resource to be saved
	 * @param options the saving options
	 * 
	 * @throws IOException
	 */
	public void saveResource( Resource res, Map options ) throws IOException {
		res.save(options);
	}

	/**
	 * Load the contents of the specified URI into an EMF Resource
	 * 
	 * @param uri the uri to load data from
	 * 
	 * @return the EMF resource for the loaded data
	 * @throws IOException
	 */
	public Resource loadResource( URI uri ) throws IOException {
		Resource res = getResource( uri );
		Map options = new HashMap();
		options.put(XMIResource.OPTION_DECLARE_XML, Boolean.TRUE);
		res.load(options);
		return res;
	}

	/**
	 * Load the contents of the specified URI into an EMF Resource
	 * 
	 * @param uri the uri to load data from
	 * @param options the loading option
	 * 
	 * @return the EMF resource for the loaded data
	 * @throws IOException
	 */
	public Resource loadResource( URI uri, Map options ) throws IOException {
		Resource res = getResource( uri );
		res.load(options);
		return res;
	}

	/**
	 * Creates a new TroposEMFHelper instance working on a new ResourceSet
	 */
	public TroposEMFHelper() {
		//initialize EMF related stuff
		initializeEMFFactories();
		//get the working ResourceSet 
		resourceSet = getResourceSet();
	}
	
	/**
	 * Creates a new TroposEMFHelper instance for the specified ResourceSet
	 * 
	 * @param rs the ResourceSet to be used
	 */
	public TroposEMFHelper( ResourceSet rs ) {
		//initialize EMF related stuff
		initializeEMFFactories();
		//get the working ResourceSet 
		this.resourceSet = rs;
	}

	/**
	 * Return the ID of the specified eobject in the selected resource
	 * 
	 * @param resource the resource to query
	 * @param eobject the object to get ID for
	 * 
	 * @return the ID of the eobject into the selected resource
	 */
	public static String getEObjectID( XMLResource resource, EObject eobject ) {
		return resource.getID( eobject );
	}
	
	
    /**
     * Create a new resource for the given URI with UUID support
     * 
     * @param uri the URI for the new resource
     * @param rset the (optional) resourceSet to be setted for the new created resource
     * 
     * @return the new created resource
     */
	public static Resource createResourceUUID( URI uri, ResourceSet rset ) {
		//create a new resource with uuid support
		Resource res = new XMIResourceImpl() {
			  protected boolean useUUIDs() {
			    return true;
			  }
		};
	
		res.setURI( uri );
		
		//if a resourceSet has been provided, connect the new resource with it
		if( rset != null ) {
			rset.getResources().add( res );
		}
		
		return res;
	}
}