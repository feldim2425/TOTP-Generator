package com.feldim2425.OTPGen.codegen;

import java.util.ArrayList;

import com.feldim2425.OTPGen.Main;
import com.feldim2425.OTPGen.ui.MainUI;

public class CodeFactory{
	
	private static Thread timer;
	public static ArrayList<CodeEntry> clist = new ArrayList<CodeEntry>();
	
	public static void addEntry(String secret, String company, String user){
		CodeEntry c = new CodeEntry(secret, company, user);
		addEntry(c);
	}
	
	public static void addEntry(CodeEntry c){
		c.setSize(200,90);
		clist.add(c);
		if(Main.doneInit) updateUI();
	}
	
	public static void startCodeTimer(){	//Start the timer thread
		timer = new Thread(new CodeTimer());
		timer.start();
	}
	
	public static void stopCodeTimer(){		//Stop the timer thread
		timer.interrupt();
	}
	
	public static double nextCodeCoutdown(){
		return (System.currentTimeMillis()/1000D)%30D;
	}
	
	public static int getTime(){
		long sec = System.currentTimeMillis() / 1000L;
		return (int) (sec / 30);
	}
	
	public static void updateUI(){
		int len = clist.size();
		MainUI.window.scrollPane.removeAll();
		for(int i=0;i<len;i++)
		{
			MainUI.window.scrollPane.addToList(clist.get(i));
		}
	}
}
