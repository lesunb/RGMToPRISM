package br.unb.cic.rtgoretoprism.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EvaluationSettingsDialog extends Dialog {
  private Text txtMaxDepth;
  private Text txtBranches;
  private String maxDepth = "";
  private String branches = "";

  public EvaluationSettingsDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);
    GridLayout layout = new GridLayout(2, false);
    layout.marginRight = 5;
    layout.marginLeft = 10;
    container.setLayout(layout);

    Label lblmaxDepth = new Label(container, SWT.NONE);
    lblmaxDepth.setText("Max Tree Depth:");

    txtMaxDepth = new Text(container, SWT.BORDER);
    txtMaxDepth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
        1, 1));
    txtMaxDepth.setText(maxDepth);
    txtMaxDepth.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        Text textWidget = (Text) e.getSource();
        String maxDepthText = textWidget.getText();
        setMaxDepth(maxDepthText);
      }
    });
    txtMaxDepth.addVerifyListener(new VerifyNumbers());
    
    Label lblBranches = new Label(container, SWT.NONE);
    GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false,
        false, 1, 1);
    gd_lblNewLabel.horizontalIndent = 1;
    lblBranches.setLayoutData(gd_lblNewLabel);
    lblBranches.setText("Branches:");

    txtBranches = new Text(container, SWT.BORDER| SWT.PASSWORD);
    txtBranches.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
        false, 1, 1));
    txtBranches.setText(branches);
    txtBranches.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        Text textWidget = (Text) e.getSource();
        String branchesText = textWidget.getText();
        setBranches(branchesText);
      }
    });
    txtBranches.addVerifyListener(new VerifyNumbers());
    
    return container;
  }

  // override method to use "Login" as label for the OK button
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, "Login", true);
    getButton(IDialogConstants.OK_ID).setEnabled(false);
    createButton(parent, IDialogConstants.CANCEL_ID,
        IDialogConstants.CANCEL_LABEL, false);
  }

  @Override
  protected Point getInitialSize() {
    return new Point(450, 300);
  }

  @Override
  protected void okPressed() {
    maxDepth = txtMaxDepth.getText();
    branches = txtBranches.getText();
    super.okPressed();
  }

  public Integer getMaxDepth() {
    return Integer.valueOf(maxDepth);
  }

  public void setMaxDepth(String maxDepth) {
	  this.maxDepth = maxDepth;
	  validate();
  }

  public Integer getBranches() {
    return Integer.valueOf(branches);
  }

  public void setBranches(String branches) {
	  this.branches = branches;
	  validate();
  }
  
  private void validate(){
	  if(!this.maxDepth.isEmpty() && !this.branches.isEmpty())
		  getButton(IDialogConstants.OK_ID).setEnabled(true);
	  else
		  getButton(IDialogConstants.OK_ID).setEnabled(false);
  }
  
  class VerifyNumbers implements VerifyListener{

	@Override
	public void verifyText(VerifyEvent e) {
		String txt = e.character + "";
		e.doit = txt.matches("[0-9]|\\\b");		
	}
	  
  }

} 