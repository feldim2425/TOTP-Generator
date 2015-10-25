package com.feldim2425.OTPGen.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.ui.TagUI;
import com.feldim2425.OTPGen.utils.EntryTag;

public class TagHandler implements ActionListener, ListSelectionListener {
	
	private TagUI ui;
	
	public TagHandler(TagUI ui){
		this.ui=ui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ui.btnCancel)){
			ui.dispose();
		}
		else if(e.getSource().equals(ui.btnOk)){
			SaveFile.saveAll(SaveFile.save);
			ui.dispose();
		}
		else if(e.getSource().equals(ui.btnNewTag)){
			ui.list.clearSelection();
			ui.btnRename.setEnabled(true);
			ui.btnRename.setText("Create");
			ui.txtRename.setText("");
			ui.txtRename.setEnabled(true);
			ui.chckbxShowInStandart.setSelected(true);
			ui.chckbxShowInStandart.setEnabled(true);
			ui.btnRemTag.setEnabled(false);
		}
		else if(e.getSource().equals(ui.btnRename)){
			String name = ui.txtRename.getText();
			if(name.matches("[A-Za-z0-9_]*") && name.length()<=16 && name.length()>0 && !name.startsWith("_")){
				if(ui.list.getSelectedValue()==null){
					SaveFile.getTagList().add(new EntryTag(name, ui.chckbxShowInStandart.isSelected()));
				}
				else{
					tagByName(ui.list.getSelectedValue()).setName(name);
				}
				ui.initList();
			}
		}
		else if(e.getSource().equals(ui.chckbxShowInStandart)){
			if(ui.list.getSelectedValue()!=null && ui.chckbxShowInStandart.isEnabled()){
				tagByName(ui.list.getSelectedValue()).setStdview(ui.chckbxShowInStandart.isSelected());
			}
		}
		else if(e.getSource().equals(ui.btnRemTag)){
			if(ui.list.getSelectedValue()!=null){
				SaveFile.getTagList().remove(tagByName(ui.list.getSelectedValue()));
				ui.initList();
				ui.list.clearSelection();
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource().equals(ui.list)){
			if(ui.list.getSelectedIndex()<0){
				disableSet();
			}
			else{
				EntryTag tag = tagByName(ui.list.getSelectedValue());
				ui.btnRename.setEnabled(true);
				ui.btnRename.setText("Rename");
				ui.txtRename.setText(tag.getName());
				ui.txtRename.setEnabled(true);
				ui.chckbxShowInStandart.setSelected(tag.isStdview());
				ui.chckbxShowInStandart.setEnabled(true);
				ui.btnRemTag.setEnabled(true);
			}
		}
	}
	
	private EntryTag tagByName(String name){
		int size = SaveFile.getTagList().size();
		for(int i=0;i<size;i++){
			if(SaveFile.getTagList().get(i).getName().equals(name))
				return SaveFile.getTagList().get(i);
		}
		return null;
	}
	
	private void disableSet(){
		ui.btnRename.setEnabled(false);
		ui.btnRename.setText("Rename");
		ui.txtRename.setText("");
		ui.txtRename.setEnabled(false);
		ui.chckbxShowInStandart.setEnabled(false);
		ui.chckbxShowInStandart.setSelected(false);
		ui.btnRemTag.setEnabled(false);
	}

}
