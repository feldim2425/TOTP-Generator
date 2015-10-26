package com.feldim2425.OTPGen.codegen;

import com.feldim2425.OTPGen.ui.MainUI;


public class CodeTimer implements Runnable{

	@Override
	public void run() {
		boolean run = true;
		long lastCodeTime=CodeFactory.getTime();
		while(run && !Thread.interrupted()){
			try{
				if(MainUI.window!=null && MainUI.window.tgbtnRun!=null && !MainUI.window.tgbtnRun.isSelected()){
					//Calculate some values to save a bit of memory and CPU power. For very bad/old PC's
					int statebar = (int) ((30D-CodeFactory.nextCodeCoutdown())*10D);	//Calculate StateBar
					boolean regen = CodeFactory.getTime()!=lastCodeTime;	//Test if there is a new Code
					
					int len = CodeFactory.clist.size();
					for(int i=0;i<len;i++){
						if(CodeFactory.visible.contains(i))
							CodeFactory.clist.get(i).update(statebar,regen);
					}
					
					if(regen){
						lastCodeTime = CodeFactory.getTime();
						regen = false;
					}
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				run=false;
			}
		}
	}

}
