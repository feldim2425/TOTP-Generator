package com.feldim2425.OTPGen.codegen;

import java.util.ArrayList;

import com.feldim2425.OTPGen.Main;
import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.ui.MainUI;

public class CodeFactory{
	
	private static Thread timer;
	public static ArrayList<CodeEntry> clist = new ArrayList<CodeEntry>();
	public static ArrayList<Integer> visible = new ArrayList<Integer>();
	
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
		String filter = MainUI.window.selectedView();
		
		int len = clist.size();
		MainUI.window.scrollPane.removeAll();
		visible.clear();
		for(int i=0;i<len;i++)
		{
			if(matchTagFilter(clist.get(i),filter))
				visible.add(i);
			if(visible.contains(i)){
				MainUI.window.scrollPane.addToList(clist.get(i));
				if(MainUI.window!=null && !MainUI.window.tgbtnRun.isSelected())
					clist.get(i).update((int) ((30D-CodeFactory.nextCodeCoutdown())*10D) , true);
			}
		}
	}
	
	private static boolean matchTagFilter(CodeEntry e,String filter){
		if(filter.equals("*")) return true;
		else if(filter.equals("#"))
			if(e.getTaglist().isEmpty()) return true;
			else{
				int s = SaveFile.getTagList().size();
				for(int i=0;i<s;i++){
					if(SaveFile.getTagList().get(i).isStdview() && e.getTaglist().contains(SaveFile.getTagList().get(i).getName()))
						return true;
				}
				return false;
			}
		else if(filter.equals("~"))
			return e.getTaglist().isEmpty();
		else
			return e.getTaglist().contains(filter);
	}
}
