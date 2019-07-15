package com.example.lastdefence.threads;

import android.graphics.Bitmap;

import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.master.MonsterList;
import com.example.lastdefence.master.Monster_1;
import com.example.lastdefence.view.GameView;

public class CreateMonster extends Thread {
	public boolean pause = false;			//线程暂停的标志位
	public boolean flag = true;				//线程循环标记位
	MonsterList list;						//怪物列表引用
	GameView mv;						//游戏主界面引用
	Bitmap creep;							//怪物图片
	public int count ;						//怪物数量
	public CreateMonster(MonsterList list, GameView mv, Bitmap creep){
		this.list = list;
		this.mv = mv;
		this.creep = creep;
		count = mv.boShuCount;
	}

	public void run(){

		while(mv.playDongHua){

			Constants.sleep(20);
			if(!pause){
				mv.shanXingjiao+=1.2f;
				if(mv.shanXingjiao>=360f){
					mv.playDongHua=false;
					break;
				}
			}
		}
		//}
		while(flag){
			if(count<1){							//判断怪物数量
				break;
			}
			try{
				if(!pause)
				{
					if(mv.boshu%Constants.CYCLE==1)	//第二波怪物的执行方法
					{

						mv.drawStartDemo = true;//绘制怪物出现时的动画
						list.add(new Monster_1(mv, Constants.MASTER1_BLOOD+(mv.boshu-1)*Constants.INCREASE_BLOOD1, creep));
						count--;

					}

					else if(mv.boshu%Constants.CYCLE== 2)//第三波怪物的执行方法
					{

						mv.drawStartDemo = true;//绘制怪物出现时的动画
						list.add(new Monster_1(mv, Constants.MASTER1_BLOOD+(mv.boshu-1)*Constants.INCREASE_BLOOD1, creep));
						count--;


					}

					else if(mv.boshu%Constants.CYCLE== 0)//第一波怪物的执行方法
					{


						mv.drawStartDemo = true;//绘制怪物出现时的动画
						list.add(new Monster_1(mv, Constants.MASTER1_BLOOD+(mv.boshu-1)*Constants.INCREASE_BLOOD1, creep));
						count--;


					}

				}
				Thread.sleep(Constants.CREATE_TIME_SPAN);

			}catch(Exception e){
				e.printStackTrace();				//打印错误信息
			}
		}
		while(list.size()!=0){
			Constants.sleep(1000);
		}
		mv.threadIsDie = true;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
		mv.playDongHua = flag;
		mv.threadIsDie = flag;
	}
}
