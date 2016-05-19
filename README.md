# RGMToPRISM by Danilo Mendonça
A Runtime Goal Model to PRISM model generator to be used with TAOM4E tool for TROPOS.

## Environment:

* Donwnload Eclipse 4.4 (http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2)
* Taom4e plugin (http://selab.fbk.eu/taom/) (see plugin instalation bellow)

## Install Plugin Taom4e

Help > Install new Software > Add 

 * Name: Taom4e
 * Location: http://selab.fbk.eu/taom/eu.fbk.se.taom4e.updateSite/

PDE ( Plug-in Development Environment (PDE) )

 * Help > Eclipse Market Place > "PDE"

## Building and Running RGMToPRISM

 * clone the repo: 
  $ git clone https://github.com/lesunb/RGMToPRISM/ 

 * Patch Taom4e Plugin:
  In order to succesfull run the RGMToPRISM you need to patch Taom4e plugin. Do it by as follow:
   Replace the taom4eplatform.jar present in your <eclipse folder>/plugins/it.itc.sra.taom4e.platform_0.6.3.1 by the one in RGMToPRISM/lib .


## Import RGMToPRISM project to eclipse

 * Open Eclipse
 * File > Import > Existing Project
 * Find the folder where you cloned RGMToPRISM project
 * Accept the defaults
 
## Running the Plugin

 * Right click in the project
 * Run As > Eclipse Application, it should open another Eclipse with the active plugin.

## Plugin first time setup

 In the new Eclipse window, configure the plugin:
  * Window -> Preferences -> TAOM4E -> PRISM GENERATOR

###Templates directory

 * Uncheck 'Use standard'
 * Click in 'Select' buton to select a Template directory path
 * Select the folder inside the project: src/main/resources/TemplateInput

###Output directory

 * Click in 'Select' buton to select a Output directory path
 * Select any system folder. Ex: ~/workspace/CRGMToPRISM/dtmc

###PRISM/PARAM directory

 * Click in 'Select' buton to select a Tools directory path
 * Select any system folder. Ex: ~/workspace/CRGMToPRISM/tools
 * Copy the binaries of both [PRISM](http://www.prismmodelchecker.org/download.php) and [PARAM](http://depend.cs.uni-sb.de/tools/param/) to a specific folder without version suffixes:
	* PATH/prism
	* PATH/param

## Create a CRGMToPRISM Project

* File > New Project


## Create a Tropos Diagram

* File > New > Other > Tropos Model


## Genearting The Prism Model

* NOTE: Before generate the PRISM code,  verify if thereisn't any TROPOS goal with missing labels.
* NOTE2: You could have goals in your model that you can't see in the graphical representation. Refer to the the TROPOS outline view an remove any not labeled goal.


## Plugin Structure

* plugin.xml: The main file that describes the plugin. It contains information to help with the code generation, libraries, plugin dependencies, and extension points.
* build.properties: The file used for describing the build process. Mainly, this is used to specify the needed libraries.
* src: Plugin classes.

#Development

## Updating an ANTLR grammar

* After updating any .g4 grammar file (for context or runtime annotations), use maven version 3 to recompile the java classes for your language (regex, parser, etc):
	* At the project root folder, use (linux): mvn package -e
	* Refresh the project folder in eclipse
	* Restart the workspace used for testing the framework

#Bugs? Doubts?

* Look for existing issues or create a new one describing your problem or doubt
* Contact the author by email
	* danilo.filgueira[at]polimi[dot]it
	* TODO: add other team members contacts here

