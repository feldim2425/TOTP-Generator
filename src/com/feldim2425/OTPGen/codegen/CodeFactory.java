package com.feldim2425.OTPGen.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.feldim2425.OTPGen.Main;
import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.ui.MainUI;

public class CodeFactory{
	
	private static Thread timer;
	private static ArrayList<CodeEntry> clist = new ArrayList<CodeEntry>();
	private static ArrayList<Integer> visible = new ArrayList<Integer>();
	private static HashMap<Integer,Integer> sort = new HashMap<Integer,Integer>();

	public static boolean resort = false;
	
	public static void addEntry(String secret, String company, String user){
		CodeEntry c = new CodeEntry(secret, company, user);
		addEntry(c);
	}
	
	public static void addEntry(CodeEntry c){
		c.setSize(200,90);
		clist.add(c);
		if(Main.doneInit){
			readIndex();
			updateUI();
		}
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
	
	synchronized public static void updateUI(){
		String filter = MainUI.window.selectedView();
		ArrayList<Integer> oldv = new ArrayList<Integer>();
		oldv.addAll(visible);
		
		int len = clist.size();
		MainUI.window.scrollPane.removeAll();
		visible.clear();
		for(int i=0;i<len;i++)
		{
			if(matchTagFilter(clist.get(sort.get(i)),filter))
				visible.add(sort.get(i));
			if(visible.contains(sort.get(i))){
				MainUI.window.scrollPane.addToList(clist.get(sort.get(i)));
				if(MainUI.window!=null && !MainUI.window.tgbtnRun.isSelected())
					clist.get(sort.get(i)).update((int) ((30D-CodeFactory.nextCodeCoutdown())*10D) , true);
			}
		}
		
		updateCodes(oldv,visible,null);
	}
	
	public static void updateCodes(List<Integer> old, List<Integer> newlist, List<Integer> force){
		force = (force==null) ? new ArrayList<Integer>() : force;
		int s = newlist.size();
		for(int i=0;i<s;i++){
			if(!old.contains(newlist.get(i)) && clist.contains(newlist.get(i))){
				clist.get(newlist.get(i)).update((int) ((30D-CodeFactory.nextCodeCoutdown())*10D) , true);
				force.remove(newlist.get(i));
			}
		}
		
		if(!force.isEmpty()) updateCodes(force);
	}
	
	public static void updateCodes(List<Integer> update){
		int s=update.size();
		for(int i=0;i<s;i++){
			if(clist.contains(update.get(i))){
				clist.get(update.get(i)).update((int) ((30D-CodeFactory.nextCodeCoutdown())*10D) , true);
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
	
	synchronized public static void readIndex(){
		if(resort){
			doResort();
			return;
		}
		int s = clist.size();
		sort.clear();
		for(int i=0;i<s;i++){
			if(sort.containsKey(clist.get(i).getIndex())){
				doResort();
				return;
			}
			else{
				sort.put(clist.get(i).getIndex(),i);
			}
		}
	}
	
	private static void doResort(){
		int s = clist.size();
		sort.clear();
		for(int i=0;i<s;i++){
			sort.put(i, i);
			clist.get(i).setIndex(i);
		}
		SaveFile.saveAll(SaveFile.save);
	}
	
	//If there are CodeEntrys without or duplicated index reset the order
	public static void doLoadIndexResort(){
		resort = true;
	}
	
	public static boolean haveToResort(){
		return resort;
	}
	

	public static ArrayList<CodeEntry> getClist() {
		return clist;
	}

	public static ArrayList<Integer> getVisible() {
		return visible;
	}

	public static HashMap<Integer, Integer> getSort() {
		return sort;
	}
}
