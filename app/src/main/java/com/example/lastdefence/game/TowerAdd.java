package com.example.lastdefence.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.lastdefence.allActivity.MainGameActivity;
import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.constant.Map;
import com.example.lastdefence.impleClass.Tower;
import com.example.lastdefence.threads.BulletRunThread;
import com.example.lastdefence.towers.TowerCannon;
import com.example.lastdefence.towers.TowerLaser;
import com.example.lastdefence.towers.TowerList;
import com.example.lastdefence.towers.TowerArrow;
import com.example.lastdefence.view.GameView;

public class TowerAdd {
    //serialization提供了一种持久化对象实例的机制,
    // 为了在一个特定对象的一个域上关闭serialization，可以在这个域前加上关键字transient
    transient BulletRunThread tft;
    transient GameView mv;
    int tower_draw = 0; //绘制塔的按钮，0为不画，1234代表不同塔
    float[] position_botton = new float[2]; //根据屏幕坐标得到缩放前的坐标
    float []move_Button = new float[2];   //根据格子坐标得到的屏幕坐标
    int []move_Button_Pre = new int[2];   //格子坐标
    TowerList tower_list;
    transient Bitmap bitmap1;
    transient Bitmap bitmap2;
    transient Bitmap bitmap3 ;
    transient Bitmap bitmap4 ;
    transient Bitmap tower1;
    transient Bitmap tower2;
    transient Bitmap tower3;
    transient Bitmap tower4;
    transient Bitmap red_put;

    boolean firstAddTower=true;
    boolean flagMove = true; //是否移动标识
    boolean isDown = false;  //按下
    boolean putRed = false; //判断是否可放塔
    Paint paint;
    Game game;
    //用于记录我们点击的炮的行和列
    int[] realRowCol = new int[2];
    Tower t;//我们点击的炮
    transient static final int MOVE_THRESHOLD=10; //用于判断手指是否滑动的阈值

    public TowerAdd(GameView mySurfaceView){
        this.mv = mySurfaceView;
        tower_list = mySurfaceView.tower_list;
        bitmap1 = mySurfaceView.getButton()[0];
        bitmap2 =  mySurfaceView.getButton()[1];
        bitmap3 =  mySurfaceView.getButton()[2];
        bitmap4 =  mySurfaceView.getButton()[3];
        tower1 =  mySurfaceView.getButton()[4];
        tower2 =  mySurfaceView.getButton()[5];
        tower3 =  mySurfaceView.getButton()[6];
        tower4 =  mySurfaceView.getButton()[7];
        game =mySurfaceView.game;
        paint = new Paint();
        paint.setAntiAlias(true); //抗锯齿方法
        tft = mySurfaceView.tft;

    }

    public boolean touchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
       //Log.i("点击事件","x="+x+" y="+y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //第一座塔
                if (!mv.cm.pause
                       && x<((Constants.BUTTON_TOWER_POSITION_X+Constants.BUTTON_TOWER_LENGTH)*Constants.RADIO+Constants.LOX)
                       && x>((Constants.BUTTON_TOWER_POSITION_X)*Constants.RADIO+Constants.LOX)
                       && y>((Constants.PMY-Constants.BUTTON_TOWER_LENGTH)*Constants.RADIO+Constants.LOY)
                       && y<((Constants.PMY)*Constants.RADIO+Constants.LOY)
                       &&mv.pao1Click
                       && !mv.isPressOnTower
                       && !mv.isDrawMenu)
               {
                   flagMove=false;
                   isDown =true;
                   position_botton[0]=event.getX()/Constants.RADIO-Constants.LOX;
                   position_botton[1]=event.getY()/Constants.RADIO-Constants.LOY;
                   move_Button[0]=event.getX();
                   move_Button[1]=event.getY();
                   tower_draw = 1;
               } //第二座塔
                if (!mv.cm.pause
                        && x<((Constants.BUTTON_TOWER_POSITION_X+Constants.BUTTON_TOWER_LENGTH)*2*Constants.RADIO+Constants.LOX)
                        && x>((Constants.BUTTON_TOWER_POSITION_X*2+Constants.BUTTON_TOWER_LENGTH)*Constants.RADIO+Constants.LOX)
                        && y>((Constants.PMY-Constants.BUTTON_TOWER_LENGTH)*Constants.RADIO+Constants.LOY)
                        && y<((Constants.PMY)*Constants.RADIO+Constants.LOY)
                        &&mv.pao2Click
                        && !mv.isPressOnTower
                        && !mv.isDrawMenu)
                {
                    flagMove=false;
                    isDown =true;
                    position_botton[0]=event.getX()/Constants.RADIO-Constants.LOX;
                    position_botton[1]=event.getY()/Constants.RADIO-Constants.LOY;
                    move_Button[0]=event.getX();
                    move_Button[1]=event.getY();
                    tower_draw = 2;
                } //第三座塔
                if (!mv.cm.pause
                        && x<((Constants.BUTTON_TOWER_POSITION_X+Constants.BUTTON_TOWER_LENGTH)*3*Constants.RADIO+Constants.LOX)
                        && x>((Constants.BUTTON_TOWER_POSITION_X*3+Constants.BUTTON_TOWER_LENGTH*2)*Constants.RADIO+Constants.LOX)
                        && y>((Constants.PMY-Constants.BUTTON_TOWER_LENGTH)*Constants.RADIO+Constants.LOY)
                        && y<((Constants.PMY)*Constants.RADIO+Constants.LOY)
                        &&mv.pao3Click
                        && !mv.isPressOnTower
                        && !mv.isDrawMenu)
                {
                    flagMove=false;
                    isDown =true;
                    position_botton[0]=event.getX()/Constants.RADIO-Constants.LOX;
                    position_botton[1]=event.getY()/Constants.RADIO-Constants.LOY;
                    move_Button[0]=event.getX();
                    move_Button[1]=event.getY();
                    tower_draw = 3;
                }
                /*
                 *塔售卖
                 */
                else if(!mv.cm.pause
                        && x<(2*Constants.PMX/4+mv.towerSell.getWidth()+Constants.LOX)*Constants.RADIO
                       && x>(2*Constants.PMX/4+Constants.LOX) *Constants.RADIO
                       && y<(Constants.PMY+Constants.LOY)*Constants.RADIO
                       && y >(Constants.PMY-mv.towerSell.getHeight()+Constants.LOY)*Constants.RADIO
                       &&mv.isPressOnTower
                       && !mv.isDrawMenu)
               {
                   mv.update.sell(t);
                   game.map_tower[t.getRowCol()[0]][t.getRowCol()[1]] = 0;
                   mv.tower_list.remove(t);
               }
                /**
                 * 塔升级
                 */
                else if(!mv.cm.pause && x<(Constants.PMX/4+mv.towerUpgrade.getWidth()+Constants.LOX)*Constants.RADIO
                        && x>(Constants.PMX/4+Constants.LOX) *Constants.RADIO
                        && y<(Constants.PMY+Constants.LOY)*Constants.RADIO
                        && y >(Constants.PMY-mv.towerUpgrade.getHeight()+Constants.LOY)*Constants.RADIO
                        &&mv.isPressOnTower
                        && !mv.isDrawMenu)
                {
                    mv.update.upDateTower(t);
                }
                mv.isPressOnTower = false;
                /**
                 * 暂停
                 */
                if(x<(Constants.PMX+Constants.LOX)*Constants.RADIO
                        && x>(Constants.PMX - mv.pause.getWidth()+Constants.LOX) *Constants.RADIO
                        && y<(mv.pause.getHeight()+Constants.LOY)*Constants.RADIO
                        && y >(0+Constants.LOY)*Constants.RADIO
                )
                {
                    //暂停按钮
                    mv.pauseOrContinueThreads();
                }
                int[] rrll = LBX.getRowcol(event.getX()/Constants.RADIO-Constants.LOX, event.getY()/Constants.RADIO-Constants.LOY);
                if(!mv.cm.pause && rrll[0]>=0 && rrll[0]<= Map.MAP_DATA[0].length && rrll[1]>=0 && rrll[1]<=Map.MAP_DATA[0][0].length)
                {
                    for(int i=0;i<mv.tower_list.size();i++)
                    {
                        t = mv.tower_list.get(i);
                        int[] rowCol = t.getRowCol();
                        if(rowCol[0]==rrll[0] && rowCol[1]==rrll[1])
                        {
                            realRowCol[0] = rowCol[0];
                            realRowCol[1] = rowCol[1];
                            mv.isPressOnTower = true;
                            if(t.getCurrentState()<=3 && t.getCurrentUpdatePrice()<=mv.coin){
                                mv.canUpgrade = true;
                            }else {
                                mv.canUpgrade = false;
                            }
                            break;
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                //首次按下滑动
                if(flagMove!=true && isDown ==true){
                    //若当前还没有判定为移动动作，则需要执行判断逻辑
                    //计算X\Y方向的移动差
                    float dx=Math.abs(event.getX()/Constants.RADIO-Constants.LOX-position_botton[0]);
                    float dy=Math.abs(event.getY()/Constants.RADIO-Constants.LOY-position_botton[1]);
                    if(dx>MOVE_THRESHOLD||dy>MOVE_THRESHOLD)
                    {
                        flagMove=true;
                    }
                }else{
                    //按下已经有滑动
                    if(isDown){
                        /**
                         * 用于不同分辨率数据还原
                         */
                        position_botton[0]=(x-Constants.LOX)/Constants.RADIO; //还原后屏幕x坐标
                        position_botton[1]=(y-Constants.LOY)/Constants.RADIO; //还原后屏幕y坐标
                        int[] temp1 = LBX.getRowcol((x-Constants.LOX)/Constants.RADIO, (y-Constants.LOY)/Constants.RADIO); //临时数组存放格子的坐标
                        if(temp1[0]!=move_Button_Pre[0]||temp1[1]!=move_Button_Pre[1]){
                            move_Button_Pre[0]=temp1[0];//格子行坐标
                            move_Button_Pre[1]=temp1[1];//格子列座标
                            move_Button = LBX.getPosition(temp1[0], temp1[1]); //根据格子坐标得到的屏幕坐标位置

                            if(isEmpty(temp1[0], temp1[1])){
                                putRed = false;

                            }else{
                                putRed = true;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                if (flagMove){
                    int[] temp = getPoint(move_Button[0],move_Button[1]); //获得地图坐标
                    if(isEmpty(temp[0],temp[1])){
                        float[] temp1 = LBX.getPosition(temp[0],temp[1]); //屏幕坐标
                        switch (tower_draw){
                            case 1:
                                if(mv.coin >= Constants.PUTTOWER1CONSUMECOIN){
                                    tower_list.add(new TowerArrow(temp1[0], temp1[1],  tower1, mv));
                                    mv.coin -= Constants.PUTTOWER1CONSUMECOIN;
                                    setCurrentMap(temp[0], temp[1]);
                                }
                                break;
                            case 2:
                                if(mv.coin >= Constants.PUTTOWER2CONSUMECOIN){
                                    tower_list.add(new TowerLaser(temp1[0], temp1[1],  tower2, mv));
                                    mv.coin -= Constants.PUTTOWER2CONSUMECOIN;
                                    setCurrentMap(temp[0], temp[1]);
                                }

                                break;
                            case 3:
                                if(mv.coin >= Constants.PUTTOWER3CONSUMECOIN){
                                    tower_list.add(new TowerCannon(temp1[0], temp1[1],  tower3, mv));
                                    mv.coin -= Constants.PUTTOWER3CONSUMECOIN;
                                    setCurrentMap(temp[0], temp[1]);
                                }

                                break;

                        }
                        if(firstAddTower ){
                            tft.start();
                            firstAddTower=false;
                        }
                    }
                    flagMove = true;
                    putRed = false;
                }
                tower_draw = 0;
                break;
        }

    return true;
    }


    private void setCurrentMap(int x, int y) {
        game.setMap_Tower(x, y);

    }

    public void draw(Canvas canvas){

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setARGB(128,200,200,200);
        switch (tower_draw){
            case 0: break;
            case 1:
                canvas.drawBitmap(Bitmap.createBitmap(tower1, 0, 0, 48, 48),
                        move_Button[0]-Bitmap.createBitmap(tower1, 0, 0, 48, 48).getWidth()/2,
                        move_Button[1]-Bitmap.createBitmap(tower1, 0, 0, 48, 48).getHeight()/2, paint);
                break;
            case 2:
                canvas.drawBitmap(Bitmap.createBitmap(tower2, 0, 0, 48, 48),
                        move_Button[0]-Bitmap.createBitmap(tower2, 0, 0, 48, 48).getWidth()/2,
                        move_Button[1]-Bitmap.createBitmap(tower2, 0, 0, 48, 48).getHeight()/2, paint);
                break;
        }

        //绘制我们点击的炮周围的圆形
        if(mv.isPressOnTower)
        {
            canvas.drawCircle(t.getXY()[0], t.getXY()[1],
                    t.getR(), paint);
        }
    }

    //根据屏幕坐标得到地图坐标
    public int[] getPoint(float x, float y){
        int []temp = LBX.getRowcol(x, y);
        return temp;
    }
    //判断该坐标是否位于现在地图上
    public boolean isEmpty(int x, int y){


        if( game.map_tower[x][y]==0){
            return true;
        }
        return false;
    }

}
