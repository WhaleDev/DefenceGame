package com.example.lastdefence.towers;

import android.graphics.Canvas;

import com.example.lastdefence.impleClass.Tower;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class TowerList extends ArrayList<Tower> implements Externalizable {

    public TowerList(){}
    public void draw(Canvas canvas){
        for(int i=0; i<this.size(); i++){
            this.get(i).draw(canvas);
        }
    }

    public void findMaster(){
        for(int i=0; i<this.size(); i++){
            this.get(i).findMaster();
        }
    }

    @Override
    public void readExternal(ObjectInput input) throws IOException,
            ClassNotFoundException
    {
        int size = input.readInt();
        this.ensureCapacity(size);
        for(int i=0; i<size; i++)
        {

            Tower t = (Tower) input.readObject();
            this.add(t);
        }

    }
    @Override
    public void writeExternal(ObjectOutput output) throws IOException
    {
        output.writeInt(this.size());
        for(int i=0; i<this.size(); i++)
        {
            Tower tower = this.get(i);
            output.writeObject(tower);
        }

    }

    }

