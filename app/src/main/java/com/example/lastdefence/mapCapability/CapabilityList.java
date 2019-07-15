package com.example.lastdefence.mapCapability;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lastdefence.impleClass.Capability;

import java.util.ArrayList;

public class CapabilityList extends ArrayList<Capability> {

    Paint paint;

    public void draw(Canvas canvas){
        for(int i=0; i<this.size(); i++){
            this.get(i).draw(canvas,paint);
        }
    }


}
