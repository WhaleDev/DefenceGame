package com.example.lastdefence.towers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

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

    @Override
    public void upDateSelf() {

    }

    @Override
    public void sell() {

    }

    @Override
    public int getCurrentUpdatePrice() {
        return 0;
    }
}
