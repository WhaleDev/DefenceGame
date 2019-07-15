package com.example.lastdefence.master;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.constant.Map;
import com.example.lastdefence.game.LBX;
import com.example.lastdefence.impleClass.Monster;
import com.example.lastdefence.view.GameView;

public class Monster_1 implements Monster {

    float totalBlood = Constants.MASTER1_BLOOD;
    float nowBlood = Constants.MASTER1_BLOOD;
    public GameView mv;
    int x;  //起点
    int y;
    int nextX;
    int nextY;
    public Paint paint;
    public float currentPointX;  //记录当前怪的像素点
    public float currentPointY;
    boolean gameStart = true;
    int bloodTime = 0;//血条存在的时间
    public transient Bitmap creep;
    public int mapNum;                      //地图编号
    static int UNIT_SPEED = Constants.UNIT_SPEED;
    int BScount = 20;
    boolean currentRunning = true;
    float pianyiX;
    float pianyiY;//这里的偏移是怪从地图的一个点想另一个点行走时后的过度。“视觉上的流畅”


    public Monster_1(GameView mv, float totalBlood, Bitmap creep){
        this.mv = mv;
        mapNum = mv.mapNum;
        this.totalBlood = totalBlood;
        this.nowBlood = totalBlood;
        this.creep = creep;
        x = Map.source[mv.mapNum][0]; //地图上的x，y坐标
        y = Map.source[mv.mapNum][1];
        nextX = Map.source[mv.mapNum][0];
        nextY = Map.source[mv.mapNum][1];
        paint = new Paint();
        currentPointX = LBX.getPosition(y, x)[0]; //当前X,y像素位置（方法参数为此）
        currentPointY = LBX.getPosition(y, x)[1];


    }

    boolean live = true;
    /*
     绘制单个怪物以及血条
     */
    @Override
    public void draw(Canvas canvas) {
        if (gameStart){
            canvas.drawBitmap(creep, currentPointX-creep.getWidth()/2,
                    currentPointY-creep.getHeight()/2, paint);

        }


    }

    @Override
    public void run() {
        if(gameStart){

            if(BScount>=UNIT_SPEED){
                BScount = 0;
                pianyiX=0;pianyiY=0;
                x = nextX;
                y = nextY;

                switch (Map.MAP_DATA[mv.mapNum][y][x]){

                    case 1: nextX++;break;
                    case 2: nextY++;break;
                    case 3: nextX--;break;
                    case 4: nextY--;break;
                }

            }

                float[] p1=LBX.getPosition(y, x);
                float[] p2=LBX.getPosition(nextY, nextX);

                pianyiX = (p2[0]-p1[0])/UNIT_SPEED*BScount;
                pianyiY = (p2[1]-p1[1])/UNIT_SPEED*BScount;
                currentPointX = p1[0]+pianyiX;
                currentPointY = p1[1]+pianyiY;

                BScount++;
            }

        }




    @Override
    public boolean isLive() {

        return live;
    }
}
