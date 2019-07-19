package com.example.lastdefence.master;

import android.graphics.Canvas;

import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.impleClass.Monster;
import com.example.lastdefence.threads.CreateMonster;
import com.example.lastdefence.view.GameView;

import java.util.ArrayList;

public class MonsterList extends ArrayList<Monster> {
    public GameView mv;
    public MonsterList(GameView mv)
    {
        this.mv = mv;
    }
    public void draw(Canvas canvas) {
        for(int i=0; i<this.size(); i++){
            this.get(i).draw(canvas);
        }

    }
    public void run(){
        if(mv.threadIsDie == true)
        {
            mv.boshu++;
            mv.shanXingjiao=0;
            mv.threadIsDie = false;
            switch(mv.boshu%Constants.CYCLE)
            {
                case 1:
                    mv.cm = new CreateMonster(mv.master_list,mv,mv.creep_1);
                    mv.cm.start();
                    break;
                case 2:
                    //如果没有怪了，重新添加
                    mv.cm = new CreateMonster(mv.master_list,mv,mv.creep_1);
                    mv.cm.start();
                    break;
                case 0:
                    mv.cm = new CreateMonster(mv.master_list,mv,mv.creep_1);
                    mv.cm.start();
                    break;
            }

        }
        for(int i=0; i<this.size(); i++){
            if(mv.cm.pause)
            {
                break;
            }
            Monster master = this.get(i);
            master.run();
            if(!master.isMonstersLive()){
                this.remove(master);
            }
        }

    }

}
