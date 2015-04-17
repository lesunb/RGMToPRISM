# RGMToPRISM by Danilo Mendonça
A Runtime Goal Model to PRISM model generator to be used with TAOM4E tool for TROPOS.

# Environment:

* Donwnload Eclipse 4.4 (http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2)
* Taom4e (http://selab.fbk.eu/taom/eu.fbk.se.taom4e.updateSite/)

# Install Plugins

Taom4e

Help > Install new Software > Add 

* Name: Taom4e
* Location: http://selab.fbk.eu/taom/eu.fbk.se.taom4e.updateSite/

PDE ( Plug-in Development Environment (PDE) )

* Help > Eclipse Market Place > "PDE"

# Building and Running RGMToPRISM

 * clone the repo: $ git clone https://github.com/lesunb/RGMToPRISM/
 * Import, in Eclipse:  File > Import Project > Plug-ins and Fragments
 * 
# Plugin Structure
* plugin.xml: The main file that describes the plugin. It contains information to help with the code generation, libraries, plugin dependencies, and extension points.
* build.properties: The file used for describing the build process. Mainly, this is used to specify the needed libraries.
* invokatron/*.java: Plugin classes.
* sample.gif: The icon that shows up next to the menu item.


# Create Project

File > New Project
