package com.feldim2425.OTPGen.ui.event;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.feldim2425.OTPGen.Main;
import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.codegen.CodeEntry;
import com.feldim2425.OTPGen.codegen.CodeFactory;
import com.feldim2425.OTPGen.ui.CodeEditUI;
import com.feldim2425.OTPGen.ui.MainUI;

public class CodeEditHandler implements ActionListener, WindowListener {
	private CodeEditUI ui;
	
	public CodeEditHandler(CodeEditUI ui){
		this.ui=ui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ui.cancelButton))
			ui.dispose();
		else if(e.getSource().equals(ui.okButton)){
			if(ui.getEntry()==null){
				CodeFactory.addEntry(new CodeEntry(
						new String(ui.pwSecret.getPassword()),
						ui.txtCompany.getText(),
						ui.txtUser.getText(),
						ui.listTags.getSelectedValuesList()));
				if(Main.doneInit) CodeFactory.updateData();
			}
			else{
				ui.getEntry().setCompany( ui.txtCompany.getText());
				ui.getEntry().setUser(ui.txtUser.getText());
				ui.getEntry().setSecret(new String(ui.pwSecret.getPassword()));
				ui.getEntry().getTaglist().clear();
				ui.getEntry().getTaglist().addAll(ui.listTags.getSelectedValuesList());
				ui.getEntry().update((int) ((30D-CodeFactory.nextCodeCoutdown())*10D) , true);
			}
			
			SaveFile.saveAll(SaveFile.save);
			ui.dispose();
		}
		else if(e.getSource().equals(ui.ckbxShow)){
			ui.pwSecret.setEchoChar((ui.ckbxShow.isSelected()) ? '\0' : '•');
			ui.btnCp.setEnabled(ui.ckbxShow.isSelected());
		}
		else if(e.getSource().equals(ui.btnCp)){
			StringSelection selection = new StringSelection(new String(ui.pwSecret.getPassword()));
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection,selection);
		}
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		MainUI.setEditing(false);
	}
	
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {}
}
