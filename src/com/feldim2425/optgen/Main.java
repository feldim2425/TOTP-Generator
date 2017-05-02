package com.feldim2425.optgen;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.feldim2425.optgen.codegen.CodeFactory;
import com.feldim2425.optgen.ui.MainUI;

public class Main {
	
	public static final String VERSION = "0.4.0-beta";
	public static final String R_DATE = "30. December 2015";
	public static boolean doneInit = false;
	
	/*
	 * TODO: Add the encryption 
	 */
	public static void main(String[] args) {
		int c = askFile();
		if(c==2) return;
		if(c==0 && !SaveFile.selectSave()){
			if(!SaveFile.curruptedFile()) return;
		}
		MainUI.start();
		CodeFactory.startCodeTimer();
		
		doneInit=true;
	}
	
	public static void exit(boolean ask){		//stop this programm; if ask=true ask user if this progam should stop
		int n=0;
		if (ask){
			JFrame askframe=new JFrame();
			Object[] options = {"Yes", "No"};
			 n = JOptionPane.showOptionDialog(askframe,
					"Do you really want to close this program?",
				    MainUI.STD_NAME+" : Close ?",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,     //do not use a custom Icon
				    options,  //the titles of buttons
				    options[0]); //default button title
			 askframe.dispose();
		}
		if (n==0){
			//EventListener.sendCmd(Main.class, EventListener.NOARGS, "EXIT_ALL");
			if(doneInit){
				MainUI.window.frame.dispose();
				CodeFactory.stopCodeTimer();
			}
		}
	}
	
	
	private static int askFile(){
		JFrame askframe=new JFrame();
		Object[] options = {"Create/Select File", "Open Blank Window", "Close"};
		int n = JOptionPane.showOptionDialog(askframe,
				"Select or create a save file?",
				MainUI.STD_NAME+" : Open a File",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		askframe.dispose();
		return n;
	}
}
