package com.feldim2425.OTPGen.ui.event;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JRadioButtonMenuItem;

import com.feldim2425.OTPGen.Main;
import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.codegen.CodeFactory;
import com.feldim2425.OTPGen.ui.CodeEditUI;
import com.feldim2425.OTPGen.ui.MainUI;
import com.feldim2425.OTPGen.ui.TagUI;

public class MainHandler implements WindowListener, ComponentListener, ActionListener, ContainerListener, ItemListener {
	
	private HashMap<Component,Integer> hScale = new HashMap<Component,Integer>();
	private HashMap<Component,Integer> wScale = new HashMap<Component,Integer>();
	
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		Main.exit(true);
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		if(e.getComponent().equals(MainUI.window.frame)){
			rescale(MainUI.window.frame.getWidth(),MainUI.window.frame.getHeight());
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof Component)) return;
		
		if(e.getSource().equals(MainUI.window.mntmEditTag)){
			TagUI.start();
		}
		else if(e.getSource().equals(MainUI.window.mntmOpen)){
			if(!SaveFile.selectSave()){
				SaveFile.curruptedFile();
			}
		}
		else if(e.getSource().equals(MainUI.window.tgbtnRun)){
			if(MainUI.window.tgbtnRun.isSelected())
				MainUI.window.tgbtnRun.setText("Pause");
			else
				MainUI.window.tgbtnRun.setText("Run");
		}else if(e.getSource().equals(MainUI.window.btnAddCode)){
			CodeEditUI.start(null);
		}
	}

	@Override
	public void componentAdded(ContainerEvent e) {
		if(e.getContainer().equals(MainUI.window.mnShow) && (e.getChild() instanceof  JRadioButtonMenuItem)){
			((JRadioButtonMenuItem) e.getChild()).addActionListener(this);
		}
		
	}

	@Override
	public void componentRemoved(ContainerEvent e) {}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(MainUI.window==null) return;
		if((e.getSource() instanceof JRadioButtonMenuItem) && e.getStateChange()==ItemEvent.SELECTED &&
				hasComponent(MainUI.window.mnShow.getMenuComponents(), (Component) e.getSource())){
			Component[] comp = MainUI.window.mnShow.getMenuComponents();
			((Component) e.getSource()).setEnabled(false);
			for(int i=0;i<comp.length;i++){
				if((comp[i] instanceof JRadioButtonMenuItem) && !comp[i].equals(e.getSource())){
					((JRadioButtonMenuItem)comp[i]).setSelected(false);
					comp[i].setEnabled(true);
				}
			}
			CodeFactory.updateUI();
		}
	}
	/*---Other Functions----*/
	
	private boolean hasComponent(Component[] comp,Component c){
		for(int i=0;i<comp.length;i++){
			if(comp[i].equals(c)) return true;
		}
		return false;
	}
	
	public void addHeigthScale(Component c){
		if(!hScale.containsKey(c))
			hScale.put(c, c.getHeight());
	}
	
	public void addWidthScale(Component c){
		if(!wScale.containsKey(c))
			wScale.put(c,c.getWidth());
	}
	
	private void rescale(int nw, int nh){
		Iterator<Component> wi = wScale.keySet().iterator();
		while(wi.hasNext()){
			Component c = wi.next();
			int h = c.getHeight();
			int dw =  MainUI.STD_WIN_WIDTH-wScale.get(c);
			c.setSize(nw-dw, h);
		}
		
		Iterator<Component> hi = hScale.keySet().iterator();
		while(hi.hasNext()){
			Component c = hi.next();
			int w = c.getWidth();
			int dh =  MainUI.STD_WIN_HEIGTH-hScale.get(c);
			c.setSize(w,nh-dh);
		}
	}

}
