package com.example.lastdefence.bullet;

import android.graphics.Canvas;
import com.example.lastdefence.impleClass.Bullet;

import java.util.ArrayList;

public class BulletList extends ArrayList<Bullet> {

	public void draw(Canvas canvas) {
		for(int i=0; i<this.size(); i++){
			this.get(i).drawSelf(canvas);
		}
	}
	
	public void run(){
		for(int i=0; i<this.size(); i++){
			
			if(get(i).isLive()){
				this.get(i).run();
			}else{
				this.remove(i);
			}
		}
	}
}
