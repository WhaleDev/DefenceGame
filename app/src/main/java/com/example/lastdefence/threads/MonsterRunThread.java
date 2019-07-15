package com.example.lastdefence.threads;

import com.example.lastdefence.master.MonsterList;
import com.example.lastdefence.view.GameView;

public class MonsterRunThread extends Thread {
	public boolean pause = false;//线程暂停的标志位
	String startString="";
	public boolean runFlag = true;
	GameView mv;
	MonsterList list ;
	public MonsterRunThread(GameView mv){
		this.mv = mv;
		list = mv.master_list;
		this.setName("master run thread");
	}

	public void run(){

		while(runFlag){


			try{
				if(!pause)
				{
					list.run();

				}

				Thread.sleep(40);

			}catch(Exception e){
				e.printStackTrace();
			}
		}


	}

	public boolean isFlag() {
		return runFlag;
	}

	public void setFlag(boolean flag) {
		this.runFlag = flag;
	}

	public String getString() {
		return startString;
	}

}
