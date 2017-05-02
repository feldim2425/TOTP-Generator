package com.feldim2425.optgen.ui.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelList extends JScrollPane{
	
	private JPanel view ;
	
	public PanelList() {
		super(VERTICAL_SCROLLBAR_ALWAYS,HORIZONTAL_SCROLLBAR_NEVER);
		
		view = new JPanel();
		view.setBackground(Color.WHITE);
		view.setLayout(null);
		view.validate();
		this.setViewportView(view);
	}
	
	public Component addToList(Component panel){
		panel.setSize(view.getWidth(),panel.getHeight());
		panel.setLocation(1, calcNextY()+1);
		view.setSize(new Dimension(view.getWidth(), calcNextY()+panel.getHeight()));
		view.setPreferredSize(view.getSize());
		view.add(panel);
		revalidate();
		return panel;
	}
	
	
	public void removeAll(){
		Component[] comp = view.getComponents();
		for(int i=0;i<comp.length;i++){
			if(comp[i]!=null){
				view.remove(comp[i]);
			}
		}
		view.repaint();
	}
	
	private int calcNextY(){
		Component[] comp = view.getComponents();
		int y = 0;
		for(int i=0;i<comp.length;i++){
			if(comp[i]!=null){
				y+=comp[i].getHeight();
			}
		}
		return y;
	}
	

	private static final long serialVersionUID = 1L;

}
