package com.feldim2425.OTPGen.codegen;

import com.feldim2425.OTPGen.ui.MainUI;


public class CodeTimer implements Runnable{

	@Override
	public void run() {
		boolean run = true;
		while(run && !Thread.interrupted()){
			try{
				if(MainUI.window!=null && MainUI.window.tgbtnRun!=null && !MainUI.window.tgbtnRun.isSelected()){
					int len = CodeFactory.clist.size();
					int statebar = (int) ((30D-CodeFactory.nextCodeCoutdown())*10D);
					for(int i=0;i<len;i++){
						CodeFactory.clist.get(i).update(statebar);
					}
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				run=false;
			}
		}
	}

}
