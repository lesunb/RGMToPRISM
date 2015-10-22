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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.progress.IProgressService;
import org.troposproject.util.ProcessCallback;
import org.troposproject.util.Spawn;

import br.unb.cic.rtgoretoprism.console.ConsoleUtil;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.generator.goda.producer.CRGMEvaluationProducer;
import br.unb.cic.rtgoretoprism.generator.goda.writer.PrismWriter;
import br.unb.cic.rtgoretoprism.generator.goda.writer.dtmc.DTMCWriter;
import br.unb.cic.rtgoretoprism.generator.kl.AgentDefinition;
import br.unb.cic.rtgoretoprism.gui.EvaluationSettingsDialog;
import br.unb.cic.rtgoretoprism.model.kl.Const;
import br.unb.cic.rtgoretoprism.util.FileUtility;
import it.itc.sra.taom4e.model.core.informalcore.Plan;

/**
 * This action let the user to start the process of generating agent code for
 * the selected Tropos's system actors from a Taom4e' diagram.
 * This will create the Jadex BDI agents for the knowledge level side.
 *
 * @see it.itc.sra.taom4e.t2xJadexGenerator.action.CLCodeGenerationAction for 
 *      the capability level side
 *      
 * @author bertolini
 */
public class EvaluatePARAMAction extends AbstractCodeGeneractionAction {
	private IWorkbenchWindow window;
	
	/**
	 * Creates a new KLCodeGenerationAction instance
	 */
	public EvaluatePARAMAction() {
		super();
	}
	
	/**
	 * Execute the action body
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		//update input/output folders values
		updateUsedFolders();
		
		 //create a long-running op for the code generation process
        IWorkbench wb = PlatformUI.getWorkbench();
        IProgressService ps = wb.getProgressService();

    	try {
    		EvaluationSettingsDialog settingsDialog = new EvaluationSettingsDialog(shell);
			if (settingsDialog.open() == Window.OK) {				
				ps.busyCursorWhile(new RunnableWithProgressCallback(settingsDialog.getMaxDepth(), settingsDialog.getBranches()));
			}			
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;		
	}

	class RunnableWithProgressCallback implements IRunnableWithProgress, ProcessCallback{
		
		final static String TERM_TIME = "TERM_TIME";
		final static String MULT_TIME = "MULT_TIME";
		final static String EVAL_TIME = "EVAL_TIME";
		String agentName;
		IProgressMonitor monitor;
		int currentDepth = 2;
		int currentExp = 0;
		int MAX_DEPTH = 2;
		int BRANCHES = 2;	
		int MAX_EXPS = 10;
		double avgGenerationTime = 0;
		double avgEvaluationTime = 0;
		boolean evaluate = false;
		
		public RunnableWithProgressCallback(int maxDepth, int branches) {
			this.MAX_DEPTH = maxDepth;
			this.BRANCHES = branches;
			agentName = "EvaluationActor";
		}
		
		public void run( IProgressMonitor monitor )
				throws InvocationTargetException, InterruptedException {
			
			this.monitor = monitor;
			
			//show user that work is progressing
			monitor.beginTask("Evaluating PRISM model",  MAX_DEPTH);
			try {
				FileUtility.deleteFile(targetFolder + "AgentRole_" + agentName + "/experiment.out", false);
				FileUtility.writeFile("Experiment with " + BRANCHES + " leaf-tasks\n", targetFolder + "AgentRole_" + agentName + "/experiment.out");
				FileUtility.deleteFile(targetFolder + "AgentRole_" + agentName + "/reachability.pctl", false);
				StringBuilder pctl = new StringBuilder("P=? [ true U (G0)]");
	        	FileUtility.writeFile(pctl.toString(), targetFolder + "AgentRole_" + agentName + "/reachability.pctl");
			} catch (IOException e) {
				e.printStackTrace();
			}
			evaluatePrism();
	       
		}
		
		public void evaluatePrism(){

	        try {	        	
	        	String cmd = "./generate.sh";
				String arg1 = agentName;
				String arg2 = "reachability.pctl";
				String arg3 = BRANCHES - 1 + "";
				//String arg4 = agentName;
				
				MessageConsole myConsole = ConsoleUtil.findConsole( agentName );
				MessageConsoleStream out = myConsole.newMessageStream();
				
				//CRGMEvaluationCtProducer evaluationProducer = new CRGMEvaluationCtProducer(currentDepth, currentDepth - 1, BRANCHES, agentName);        								
				CRGMEvaluationProducer evaluationProducer = new CRGMEvaluationProducer(MAX_DEPTH, Const.AND, Const.SEQ, Const.RTRY, agentName);
				AgentDefinition ad = evaluationProducer.generateCRGM();
				PrismWriter writer = new DTMCWriter( ad, new ArrayList<Plan>(), sourceFolder, targetFolder, true);
				writer.writeModel();					
				Spawn spawn = new Spawn( new File(targetFolder + "AgentRole_" + agentName), out, out, this, new String[]{cmd, arg1, arg2, arg3});
				spawn.start();				
				System.out.println("Initing evaluation of " + ad.planbase.size() + " leaf-tasks with current tree depth of " + currentDepth);
				System.out.println("Waiting for PRISM model to be built");
			} catch (CodeGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void evaluateFormula(){
			String cmd = "./evaluate.sh";
			String arg1 = agentName + ".out";
			MessageConsole myConsole = ConsoleUtil.findConsole( agentName );
			MessageConsoleStream out = myConsole.newMessageStream();
			Spawn spawn = new Spawn( new File(targetFolder + "AgentRole_" + agentName), out, out, this, new String[]{cmd, arg1});
			spawn.start();
		}
		
		@Override
		public void runAfterExit(int exitStatus, ArrayList<String> list) {
			
			list = new ArrayList<String>(list);
			if(exitStatus == 0){
								
				if(evaluate){					
					addTimeResult(list, EVAL_TIME);
					System.out.println("Parametric formula evaluated, exit status: " + exitStatus); 
					if(currentExp >= MAX_EXPS - 1){
						increaseCount();
						writeResults();
						return;
					}else{
						currentExp++;
						evaluateFormula();
					}
				}else{
					addTimeResult(list, TERM_TIME);
					System.out.println("Parametric formula generated, exit status: " + exitStatus); 
				
					if(currentExp >= MAX_EXPS - 1){
						evaluate = true;						
						currentExp = 0;
						evaluateFormula();
					}else{
						currentExp++;
						evaluatePrism();
					}
				}
			}else{
				if(evaluate){
					increaseCount();
					System.out.println("Error generating parametric formula");
				}
			}					
		}
		
		private void writeResults(){
			try {
				FileUtility.writeFile( 
						BRANCHES + "\t\t" + avgGenerationTime/MAX_EXPS + "\n\n" +
						BRANCHES + "\t\t" + avgEvaluationTime/MAX_EXPS,
						targetFolder + "AgentRole_" + agentName + "/experiment.out");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void addTimeResult(List<String> lines, String type){
			String line;
			switch(type){
				case TERM_TIME: 
						line = findLine(lines, "^T:(\\d*\\.?\\d+)$");
						avgGenerationTime+= matchDouble(line, "^T:(\\d*\\.?\\d+)$"); 
					break;
				case MULT_TIME:
						line = findLine(lines, "^T:(\\d*\\.?\\d+)$");
						avgGenerationTime+= matchDouble(line, "^T:(\\d*\\.?\\d+)$");
					break;
				case EVAL_TIME: 
						line = findLine(lines, "^T:(\\d*\\.?\\d+)$");
						avgEvaluationTime+= matchDouble(line, "^T:(\\d*\\.?\\d+)$");
					break;
				default: break;
			}
		}
		
		private String findLine(List<String> lines, String stringPattern){
			for(String line : lines){
				Pattern pattern = Pattern.compile(stringPattern);
				java.util.regex.Matcher matcher = pattern.matcher(line);
				if(matcher.find())
					return line;
			}
			return null;
		}
	
		private Double matchDouble(String line, String stringPattern){
			Pattern pattern = Pattern.compile(stringPattern);
			java.util.regex.Matcher matcher = pattern.matcher(line);
			if(matcher.find())
				return Double.parseDouble(matcher.group(1));
			else
				return 0D;
		}

		private void increaseCount() {
			//close the monitor
			if(currentDepth == MAX_DEPTH)
				monitor.done();
			else
				monitor.worked(currentDepth);

			currentDepth++;
		}
	}	
}