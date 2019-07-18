package com.example.lastdefence.impleClass;

import android.graphics.Canvas;

import com.example.lastdefence.bullet.BulletList;

public interface Tower{
    public void draw(Canvas canvas);

    public float[] getXY();

    public float getR();

    public void fire(Monster a);

    public void findMaster();

    public void findJD(float x,float y) ;

    public BulletList getBullet();

    public int[] getRowCol();

    public int getCurrentState();

    //跑自己升级的方法
    public void upDateSelf();
    //炮卖掉自己
    public void sell();

    public int getCurrentUpdatePrice();
}
