package com.example.lastdefence.towers;

import android.graphics.Canvas;

import com.example.lastdefence.impleClass.Tower;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class TowerList extends ArrayList<Tower>  {

    public TowerList(){}
    public void draw(Canvas canvas){
        for(int i=0; i<this.size(); i++){
            this.get(i).draw(canvas);
        }
    }

    }

