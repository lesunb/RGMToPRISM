# RGMToPRISM by Danilo Mendonça
A Runtime Goal Model to PRISM model generator to be used with TAOM4E tool for TROPOS.

# Environment:

* Donwnload Eclipse 4.4 (http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2)
* Taom4e (http://selab.fbk.eu/taom/eu.fbk.se.taom4e.updateSite/)

# Install Plugin Taom4e

Help > Install new Software > Add 

* Name: Taom4e
* Location: http://selab.fbk.eu/taom/eu.fbk.se.taom4e.updateSite/

PDE ( Plug-in Development Environment (PDE) )

* Help > Eclipse Market Place > "PDE"

# Building and Running RGMToPRISM

 * clone the repo: 
  $ git clone https://github.com/lesunb/RGMToPRISM/ 
 
* Import, in Eclipse:  
  File > Import Project > Existing Projects Into Workspace

* Patch Taom4e Plugin
In order to succesfull run the RGMToPRISM you need to patch Taom4e plugin. Do it by as follow:
 Replace the taom4eplatform.jar present in your <eclipse folder>/plugins/it.itc.sra.taom4e.platform_0.6.3.1 by the one in RGMToPRISM/lib .


# Import RGMToPRISM project to eclipse

 * Open Eclipse
 * File > Import > Existing Project
 * Find the folder where you cloned RGMToPRISM project
 * Accept the defaults
 
# Running the Plugin

* Right click in the project
* Run As > Eclipse Application, it should open another Eclipse with the active plugin.


# Create Project

File > New Project


# Create a Tropos Diagram


# Genearting The Prism Model

* NOTE: Before generate the PRISM code,  verify if thereisn't any TROPOS goal with missing labels.
* NOTE2: You could have goals in your model that you can't see in the graphical representation. Refer to the the TROPOS outline view an remove any not labeled goal.


# Plugin Structure

* plugin.xml: The main file that describes the plugin. It contains information to help with the code generation, libraries, plugin dependencies, and extension points.
* build.properties: The file used for describing the build process. Mainly, this is used to specify the needed libraries.
* src: Plugin classes.

