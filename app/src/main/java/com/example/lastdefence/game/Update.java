package com.example.lastdefence.game;

import com.example.lastdefence.impleClass.Tower;
import com.example.lastdefence.towers.TowerArrow;

/**
 * Created by whale on 2019-08-03 14:56
 */
public class Update {
    //塔升级方法
     public void upDateTower(Tower t){
         if(t instanceof TowerArrow){
             t.upDateSelf();
         }

    }

    public void sell(Tower t){
         if(t instanceof TowerArrow){
             t.sell();
         }
    }

}

