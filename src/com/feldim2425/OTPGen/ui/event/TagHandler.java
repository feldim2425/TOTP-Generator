package com.feldim2425.OTPGen.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.codegen.CodeFactory;
import com.feldim2425.OTPGen.ui.MainUI;
import com.feldim2425.OTPGen.ui.TagUI;
import com.feldim2425.OTPGen.utils.EntryTag;

public class TagHandler implements ActionListener, ListSelectionListener, WindowListener {
	
	private TagUI ui;
	public ArrayList<EntryTag> tags = new ArrayList<EntryTag>();
	HashMap<String,String> nameTable = new HashMap<String, String>();
	
	public TagHandler(TagUI ui){
		this.ui=ui;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ui.btnCancel)){
			ui.dispose();
		}
		else if(e.getSource().equals(ui.btnOk)){
			doChanges();
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
				if(ui.list.getSelectedValue()==null && tagByName(name)==null){
					tags.add(new EntryTag(name, ui.chckbxShowInStandart.isSelected()));
				}
				else if(ui.list.getSelectedValue()!=null && tagByName(name)==null){
					EntryTag tag = tagByName(ui.list.getSelectedValue());
					String orig = getOriginalName(tag.getName());
					nameTable.put((orig==null) ? tag.getName() : orig, name);
					tag.setName(name);
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
				EntryTag tag = tagByName(ui.list.getSelectedValue());
				String orig = getOriginalName(tag.getName());
				nameTable.put((orig==null) ? tag.getName() : orig, null);
				tags.remove(tag);
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
		int size = tags.size();
		for(int i=0;i<size;i++){
			if(tags.get(i).getName().equals(name))
				return tags.get(i);
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
	
	public void initLists(){
		int size = SaveFile.getTagList().size();
		for(int i=0;i<size;i++){
			tags.add(SaveFile.getTagList().get(i).copy());
		}
	}
	
	public void saveLists(){
		SaveFile.getTagList().clear();
		SaveFile.getTagList().addAll(tags);
	}
	
	private String getOriginalName(String rename){
		Iterator<String> keys = nameTable.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			if(nameTable.get(key).equals(rename)) return key;
		}
		return null;
	}
	
	private void doChanges(){
		Iterator<String> keys = nameTable.keySet().iterator();			
		int size = CodeFactory.clist.size();
		while(keys.hasNext()){
			String key = keys.next();
			for(int i=0;i<size;i++) 		//Rename Tags in the codes lists
				if(CodeFactory.clist.get(i).getTaglist().remove(key) && nameTable.get(key)!=null)
					CodeFactory.clist.get(i).getTaglist().add(nameTable.get(key));
		}
		
		String newview = null;
		String view = MainUI.window.selectedView();
		if(nameTable.containsKey(view)){
			newview = nameTable.get(view);
			newview = (newview==null) ? "#" : newview;
		}
		else newview = view;
		
		saveLists();
		SaveFile.saveAll(SaveFile.save);
		MainUI.window.reinitTags((newview==null)?"#":newview);
		if(newview==view)CodeFactory.updateUI();
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
