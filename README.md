# Goal-Oriented Dependability Analysis (GODA)
A Contextual-Runtime Goal Model (CRGM) to PRISM model/PARAMetric formula generator to be used with the TAOM4E tool for TROPOS goal model.

## Environment:

* Donwnload Eclipse 4.5 (http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/mars2)
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

## Using CRGMToPRISM

### Create a CRGMToPRISM Project

 * File > New Project


### Create a Tropos Diagram

Right-click in the project folder (or subfolder) and:
 * File > New > Other > Tropos Model


### Genearting The Prism Model

* NOTE: Before generate the PRISM code,  verify if thereisn't any TROPOS goal with missing labels.
* NOTE2: You could have goals in your model that you can't see in the graphical representation. Refer to the the TROPOS outline view an remove any not labeled goal.

### Syntax used to lable Goals and Tasks 

Goals and tasks must have a lable according to the rules bellow. The general form of a lable is:

* *IDENTIFIER*: *DESCRIPTION* *[runtime annotation]*

#### *IDENTIFIER*: *DESCRIPTION*

* Goals must use a **prefix G** followed by an **unique numeric ID** within the CRGM Goals set followed by a **colon** followed by a **textual description** . Ex: G1: Goal description, G2: Goal description, G3: Goal description

* Same rule used for Goals, except that:
	* Tasks must use a **prefix T** 
	* Task level refers to the depth level after the goal they succeed
	* Level 1 tasks' prefix should be followed by an **unique numeric ID** within their context. Ex:
		* T1: Task description, T2: Task description, T3: Task description (for goal **G2**)
		* T1: Task description, T2: Task description, T3: Task description (for goal **G3**)
	* Second and subsequent level tasks prefix must be followed by the same **unique numeric ID** of its first level task, a **dot** and an **unique numeric ID**. Ex:
		* T1.1: Tsk Dsc, T1.2: Tsk dsc (descendants of T1)
		* T1.11: Tsk dsc, T1.12: Tsk dsc (descendantas of T1 -> T1.1) 
		* T2.11: Tsk dsc, T2.12: Tsk dsc, T2.13: Tsk dsc... (descendants of T2 -> T2.1) and so forward.

#### *[runtime annotation]*

* Any non-leaf node that is refined/decomposed into two or more sub-nodes must have a *runtime annotation* as part of its lable
* Leaf-nodes and nodes refined by a single sub-node are trivial and require no *runtime annotation*; they may still receive a *runtime annotation* of type **opt(E)** in case their sub-node should be specified as **optional**
* *runtime annotations* must be inside brakets and be placed after *DESCRIPTION*. Ex:
* G1:Goal description **[G2;G3#G4]**
* *runtime annotations* may be separated from *DESCRIPTION* with space(s) and/or break line(s). As long as it is inside the brakets and after *DESCRIPTION*, it will be parsed

### A CRGM example

![CRGM example]
(https://github.com/lesunb/CRGMToPRISM/blob/master/docs/CRGM.png?raw=true)


## Plugin Structure

* plugin.xml: The main file that describes the plugin. It contains information to help with the code generation, libraries, plugin dependencies, and extension points.
* build.properties: The file used for describing the build process. Mainly, this is used to specify the needed libraries.
* src: Plugin classes.

##Development

### Updating an ANTLR grammar

* After updating any .g4 grammar file (for context or runtime annotations), use maven version 3 to recompile the java classes for your language (regex, parser, etc):
	* At the project root folder, use (linux): mvn package -e
	* Refresh the project folder in eclipse
	* Restart the workspace used for testing the framework

##Bugs? Doubts?

* Look for existing issues or create a new one describing your problem or doubt
* Contact the author by email
	* danilo.filgueira[at]polimi[dot]it
	* TODO: add other team members contacts here
