package com.example.lastdefence.threads;

import android.view.SurfaceView;

import com.example.lastdefence.view.GameView;

public class DrawThread extends Thread{
    SurfaceView mv;
    boolean flag = true;

    public DrawThread(SurfaceView mv){
        this.mv = mv;
        this.setName("drawthread");
    }

    public void run(){

        try{
            while(flag){
                if(mv instanceof GameView)
                    ((GameView)mv).repaint();
                Thread.sleep(20);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void setFlag(boolean flag){
        this.flag = flag;
    }
}

