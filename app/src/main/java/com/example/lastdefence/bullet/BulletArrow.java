package com.example.lastdefence.bullet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.impleClass.Bullet;
import com.example.lastdefence.impleClass.Monster;
import com.example.lastdefence.master.MonsterList;
import com.example.lastdefence.view.GameView;

/**
 * Created by JiangYu on 2019-07-17 10:52
 */
public class BulletArrow implements Bullet {

    private Monster monster;

    private Paint paint;

    private float yAngle; //子弹与怪之间的角度

    private boolean live = true;

    private float currentX;  //当前坐标
    private float currentY;

    private MonsterList list;

    private int damage;

    private int currentState;

    private GameView mv;

    private float speed =8;



    public BulletArrow(float x, float y, Monster a, int currentState, GameView mv){
        this.mv = mv;
        this.currentX=x;
        this.currentY=y;
        monster = a;
        paint =  new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        this.currentState = currentState;
        damage = Constants.BULLETNUMBER1DAMAGE[currentState-1];
    }

    @Override
    public void drawSelf(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(currentX-mv.bulletShell.getWidth()/2,currentY-mv.bulletShell.getHeight()/2);
        matrix.preRotate((float)Math.toDegrees(yAngle),mv.bulletShell.getWidth()/2,mv.bulletShell.getHeight()/2);
        canvas.drawBitmap(mv.bulletShell,matrix,null);
    }

    @Override
    public void run() {
        if(monster.isMonstersLive()){
            float xspan = monster.getCurrentPoint()[0]-currentX;
            float yspan = monster.getCurrentPoint()[1]-currentY;
            if(xspan*xspan+yspan*yspan<20){
                live = false;
                if(!monster.decreaseBlood(damage)){
                   monster.setMonstersLive(false);
                }
                return;
            }

            if(xspan == 0){
                if (monster.getCurrentPoint()[1]>=this.currentY)   //子弹需要下移
                    xspan = 0.0000001f;
                else
                    xspan = -0.0000001f;
            }
            if (yspan == 0){
                if(monster.getCurrentPoint()[0]>=this.currentX)
                    yspan = 0.0000001f;
                else
                    yspan = -0.0000001f;
            }
            if(xspan>0 &&yspan>0)
                yAngle = (float)Math.atan(Math.abs(yspan/xspan));   //第一象限
            else if(xspan<0&&yspan>0)
                yAngle = (float)Math.PI- (float)Math.atan(Math.abs(yspan/xspan));
            else if (xspan<0&&yspan<0)
                yAngle = (float)Math.PI+ (float)Math.atan(Math.abs(yspan/xspan));
            else
                yAngle = (float)(2*Math.PI)-(float)Math.atan(Math.abs(yspan/xspan));
        }
        if (this.currentX<0||this.currentY<0||currentX>Constants.PMX||currentY>Constants.PMY)
            this.live = false;
        currentX+= speed*Math.cos(yAngle);
        currentY+= speed*Math.sin(yAngle);


    }

    @Override
    public boolean isLive() {
        return live;
    }
}
