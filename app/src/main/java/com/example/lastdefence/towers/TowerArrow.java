package com.example.lastdefence.towers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.lastdefence.allActivity.MainGameActivity;
import com.example.lastdefence.bullet.BulletArrow;
import com.example.lastdefence.bullet.BulletList;
import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.game.LBX;
import com.example.lastdefence.impleClass.Monster;
import com.example.lastdefence.impleClass.Tower;
import com.example.lastdefence.view.GameView;

import java.util.ArrayList;

public class TowerArrow implements Tower{ //实现接口序列化箭塔对象，用于传递对象
    private boolean flag = true;
    private int currentPrice = Constants.TOWER1CURRENTPRICE[0];
    private float x;
    private float y;//塔的中心点坐标
    private float shotR=Constants.TOWER1_R[0];
    private   Bitmap bitmap;
    GameView mv;
    Paint paint;
    public ArrayList<Monster> monster_list;
    private float yAngle;
    public BulletList bulletList = new BulletList();//子弹列表
    private int limitCount = 20;//防止子弹发射过快

    private int[] rowCol  = new int[2];//塔的行和列
    public Bitmap all;
    private int currentState = 1;//当前是第几级的炮
    //升级塔是播放动画的标志位
    private boolean playDongHua = false;
    private float shanXingAngle = 0;
    public TowerArrow(float x, float y, Bitmap bitmap, GameView mv) {
        super();
        this.x = x;
        this.y = y;
        rowCol = LBX.getRowcol(x, y);
        this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, 48, 48);
        this.all = bitmap;
        this.mv = mv;
        paint = mv.paint;
        monster_list = mv.master_list;
    }
    @Override
    public void draw(Canvas canvas){
            Matrix m1=new Matrix();
            m1.setTranslate(x-bitmap.getWidth()/2,y-bitmap.getHeight()/2);
            m1.preRotate(yAngle, bitmap.getWidth()/2, bitmap.getHeight()/2);
            canvas.drawBitmap(bitmap, m1,null);
            bulletList.draw(canvas);

   }
    //返回塔的中心点坐标
    @Override
    public float[] getXY() {
        return new float[]{x,y};
    }
    @Override
    public float getR() {
        return shotR;
    }


    @Override
    public void fire(Monster a) {
        if(limitCount%Constants.TOWER1_SHOOTTIME==0 && a.isMonstersLive() ) //减小发射速度
        {

            bulletList.add(new BulletArrow((float)(x+24*Math.cos(Math.PI*yAngle/180)),
                    (float)(y+24*Math.sin(Math.PI*yAngle/180)),a,currentState,mv));
        }

        limitCount ++;
    }

    @Override
    public void findMaster() {
        ArrayList<Monster>al = new ArrayList<Monster>();
        Monster m = null;
        for(int i=0; i<monster_list.size(); i++){
            Monster a = monster_list.get(i);
            float[] point = a.getCurrentPoint();
            if((point[0]-x)*(point[0]-x)+(point[1]-y)*(point[1]-y)<shotR*shotR&&a.isMonstersLive()){
                al.add(a);
            }
        }
        m = getNearestMaster(al);
        if(m == null)
        {
            return;
        }
        findJD(m.getCurrentPoint()[0],m.getCurrentPoint()[1]);
        if(bulletList.size()==0)///&&bulletList.size()==0
        {
            //如果在升级过程中不能发射炮弹
                 fire(m);
        }

    }

    //找到最近的怪
    public Monster getNearestMaster(ArrayList<Monster> al)
    {
        float distance = 1000000;
        Monster m = null;
        for(int i = 0;i<al.size();i++)
        {
            Monster a = al.get(i);
            float[] point = a.getCurrentPoint();
            float temp = (point[0]-x)*(point[0]-x)+(point[1]-y)*(point[1]-y);
            if(temp<distance)
            {
                distance = temp;
                m = a;
            }
        }
        return m;
    }

    @Override
    public void findJD(float cx, float cy) {

        float xspan=cx-x;
        float yspan=cy-y;
        yAngle=(float)Math.toDegrees(Math.atan(xspan/yspan));
        if(yspan<=0)
        {
            yAngle=(float)-Math.toDegrees(Math.atan(xspan/yspan));
        }
        else
        {
            yAngle=-(-180+(float)Math.toDegrees(Math.atan(xspan/yspan)));
        }
        yAngle-=90;

    }

    @Override
    public BulletList getBullet() {
        return bulletList;
    }

    @Override
    public int[] getRowCol() {
        return rowCol;
    }

    @Override
    public int getCurrentState() {
        return currentState;
    }

    //画扇型的动画
    public void DrawShanXing()
    {
        while(true)
        {
            playDongHua = true;
            shanXingAngle = shanXingAngle + 5;
            Constants.sleep(10);
            if(shanXingAngle>=360)
            {
                shanXingAngle = 0;
                break;
            }
        }
    }

    @Override
    public void upDateSelf() {
        switch(currentState)
        {
            case 1:
                //判断升级所需的钱数是否够用
                if(mv.coin>=Constants.UPDATETOWER1[1])//此处的数字要写在常量列里面
                {
                    //休息几秒钟有一个升级的过程,换图
                    DrawShanXing();
                    playDongHua = false;
                    bitmap = bitmap.createBitmap(all, 48, 0, 48, 48);
                    currentState = currentState + 1;
                    currentPrice = Constants.TOWER1CURRENTPRICE[currentState-1];
                    shotR = Constants.TOWER1_R[currentState-1];//将射击的范围变大，伤害变大
                    mv.coin -= Constants.UPDATETOWER1[1];
                    //原先的类里面fire方法要重新改写一下，根据当前的状态子弹的数量增加
                }
                else
                {
                    mv.canUpgrade = false;
                }
                break;
            case 2:
                //判断升级所需的钱数是否够用
                if(mv.coin>=Constants.UPDATETOWER1[2])//此处的数字要写在常量列里面
                {
                    //休息几秒钟有一个升级的过程,换图
                    DrawShanXing();
                    playDongHua = false;
                    bitmap = bitmap.createBitmap(all, 96, 0, 48, 48);
                    currentState = currentState + 1;
                    mv.coin -= Constants.UPDATETOWER1[2];
                    currentPrice = Constants.TOWER1CURRENTPRICE[currentState-1];
                    shotR = Constants.TOWER1_R[currentState-1];//将射击的范围变大，伤害变大
                    //原先的类里面fire方法要重新改写一下，根据当前的状态子弹的数量增加
                }
                else
                {
                    mv.canUpgrade = false;
                }
                break;
            case 3:
                //判断升级所需的钱数是否够用
                if(mv.coin>=Constants.UPDATETOWER1[3])//此处的数字要写在常量列里面
                {
                    //休息几秒钟有一个升级的过程,换图
                    DrawShanXing();
                    playDongHua = false;
                    bitmap = bitmap.createBitmap(all, 144, 0, 48, 48);
                    currentState = currentState + 1;
                    mv.coin -= Constants.UPDATETOWER1[3];
                    currentPrice = Constants.TOWER1CURRENTPRICE[currentState-1];
                    shotR = Constants.TOWER1_R[currentState-1];//将射击的范围变大，伤害变大
                    //原先的类里面fire方法要重新改写一下，根据当前的状态子弹的数量增加
                }
                else
                {
                    mv.canUpgrade = false;
                }
                break;
            case 4 :
                //写一句话，不可以升级了，已经是最高级别的了

                break;
        }

    }

    @Override
    public void sell() {
        mv.coin += Constants.TOWER1CURRENTPRICE[currentState-1];
    }

    @Override
    public int getCurrentUpdatePrice() {
        return Constants.UPDATETOWER1[currentState];
    }
}
