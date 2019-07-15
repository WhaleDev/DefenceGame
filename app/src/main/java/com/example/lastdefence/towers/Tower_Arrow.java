package com.example.lastdefence.towers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.game.LBX;
import com.example.lastdefence.impleClass.Tower;
import com.example.lastdefence.view.GameView;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Tower_Arrow implements Tower{ //实现接口序列化箭塔对象，用于传递对象
    private boolean flag = true;
    private int currentPrice = Constants.TOWER1CURRENTPRICE[0];
    private float x;
    private float y;//塔的中心点坐标
    private float shotR=Constants.TOWER1_R[0];
    private   Bitmap bitmap;
    GameView mv;
    Paint paint;
//    public ArrayList<Monster> master_list;
    private float yAngle;
//    public BulletList bulletList = new BulletList();//子弹列表
    private int limitCount = 20;//防止子弹发射过快

    private int[] rowCol  = new int[2];//塔的行和列
    public Bitmap all;
    private int currentState = 1;//当前是第几级的炮
    //升级塔是播放动画的标志位
    private boolean playDongHua = false;
    private float shanXingAngle = 0;
    public Tower_Arrow(float x, float y, Bitmap bitmap, GameView mv) {
        super();
        this.x = x;
        this.y = y;
        rowCol = LBX.getRowcol(x, y);
        this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, 48, 48);
        this.all = bitmap;
        this.mv = mv;
        paint = mv.paint;
    }
    @Override
    public void draw(Canvas canvas){
        Matrix m1=new Matrix();
        m1.setTranslate(x-bitmap.getWidth()/2,y-bitmap.getHeight()/2);
        m1.preRotate(yAngle, bitmap.getWidth()/2, bitmap.getHeight()/2);
        canvas.drawBitmap(bitmap, m1,null);

   }
    //返回塔的中心点坐标
    @Override
    public float[] getXY() {
        return new float[]{x,y};
    }
    @Override
    public float getR() {
        // TODO Auto-generated method stub
        return shotR;
    }

}
