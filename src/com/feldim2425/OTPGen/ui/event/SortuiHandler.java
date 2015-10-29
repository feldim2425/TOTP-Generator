package com.feldim2425.OTPGen.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.codegen.CodeFactory;
import com.feldim2425.OTPGen.ui.MainUI;
import com.feldim2425.OTPGen.ui.SortUI;

public class SortuiHandler implements WindowListener, ActionListener{
	
	private SortUI ui;
	
	public SortuiHandler(SortUI ui){
		this.ui=ui;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ui.cancelButton)){
			ui.dispose();
		}
		else if(e.getSource().equals(ui.okButton)){
			saveChanges();
			ui.dispose();
		}
		else if(e.getSource().equals(ui.btnDown)){
			if(ui.list.getSelectedIndex()+1 >= ui.list.getModel().getSize()) return;
			int sel = ui.list.getSelectedIndex();
			moveItem(sel, sel+1);
			ui.list.setSelectedIndex(sel+1);
		}
		else if(e.getSource().equals(ui.btnUp)){
			if(ui.list.getSelectedIndex()-1 < 0) return;
			int sel = ui.list.getSelectedIndex();
			moveItem(sel, sel-1);
			ui.list.setSelectedIndex(sel-1);
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
	
	private void moveItem(int curpos, int pos){
		if(!ui.tempsort.containsKey(curpos) || pos==curpos) return;
		int c = (pos<curpos) ? -1 : 1; 
		int steps = Math.max(pos, curpos)-Math.min(pos, curpos);
		for(int i=0; i<steps; i++){
			int t = ui.tempsort.get(pos+i*c);
			ui.tempsort.put(pos+i*c, ui.tempsort.get(pos+(i-1)*c));
			ui.tempsort.put(pos+(i-1)*c, t);
		}
		ui.updateList();
	}
	
	private void saveChanges(){
		CodeFactory.getSort().clear();
		int s = CodeFactory.getClist().size();
		for(int i=0;i<s;i++){
			CodeFactory.getSort().put(i, ui.tempsort.get(i));
			CodeFactory.getClist().get(ui.tempsort.get(i)).setIndex(i);
		}
		
		SaveFile.saveAll(SaveFile.save);
		CodeFactory.updateUI();
	}
}
