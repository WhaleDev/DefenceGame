package com.example.lastdefence.game;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.lastdefence.constant.Map;
import com.example.lastdefence.view.GameView;

import static com.example.lastdefence.constant.Map.MAP_DATA;
import static com.example.lastdefence.constant.Map.a;

/**
 * 地图绘制类
 */
public class LBX {
    final static float yGlobalOffset = 80;  //地图全局y的总偏移量
    final static float xGlobalOffset = 100;  //地图全局x的总偏移量
    private static float [][]tempC;         //正方形x屏幕坐标
    private static float [][]tempR;         //y

    GameView mv;
    Path mPatha = new Path();               //Path类可在View上将N个点连成一条“路径”，然后调路径绘制图形
    Bitmap road;                           //路径图片
    Bitmap addPlace;

    Bitmap start_bit;
    Bitmap end_bit;                         //目标点图片
    public int mapNum;                      //地图编号
    int start[];                            //开始点的数组坐标
    int end[];                              //结束点的数组坐标
    public float[] temp3;
    public float[] temp2;



    public LBX(Bitmap road,Bitmap addPlace,GameView mv){
        Init(road,addPlace,mv);
        PositionInit();
        InitLBX();
    }

    /**
     * 初始化正方形路径
     */
    public void InitLBX(){
        mPatha.moveTo(a,a);
        mPatha.lineTo(a,-a);
        mPatha.lineTo(-a,-a);
        mPatha.lineTo(-a,a);
        mPatha.lineTo(a,a);

    }

    /**
     * 成员变量的初始化
     */
    public void Init(Bitmap road,Bitmap addPlace,GameView mv){
        this.mv = mv;
        mapNum = mv.mapNum;
        start = mv.game.source;
        end = mv.game.target;
        start_bit = mv.getLBXBit()[1];
        end_bit = mv.getLBXBit()[2];
        this.road = road;
        this.addPlace = addPlace;

    }

    /**
     * 绘制单个格子
     */
    public void drawSelf(Canvas canvas, Paint paint,float xOffset,float yOffset,int[] color){
        canvas.save();
        canvas.translate(xOffset,yOffset);   //设置原点偏移量
        paint.setARGB(color[0],color[1],color[2],color[3]); //设置画笔颜色
        canvas.drawPath(mPatha,paint);
        paint.setARGB(120,120,120,120);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawPath(mPatha,paint);
        canvas.restore();
    }

    /**
     *绘制整个地图
     */
    public void drawMap(Canvas canvas,Paint paint){
        int[] colorBlack = new int[]{255,0,0,0};
        for (int row=0;row<MAP_DATA[0].length;row++){
            for(int col=0;col<MAP_DATA[0][0].length;col++){
                float[]temp = getPosition(row,col);  //获取屏幕坐标
                int[] color = colorBlack;
               // drawSelf(canvas,paint,temp[0],temp[1],color); //绘制每一个格子

                if(MAP_DATA[mapNum][row][col]==1||MAP_DATA[mapNum][row][col]==2
                        ||MAP_DATA[mapNum][row][col]==3||MAP_DATA[mapNum][row][col]==4
                        ||MAP_DATA[mapNum][row][col]==5)
                    canvas.drawBitmap(road, temp[0]-road.getWidth()/2, temp[1]-road.getHeight()/2, paint);
                if(MAP_DATA[mapNum][row][col]==0) canvas.drawBitmap(addPlace,temp[0]-addPlace.getWidth()/2, temp[1]-addPlace.getHeight()/2, paint);

            }
        }
        temp2 = getPosition(start[1],start[0]);
        temp3 = getPosition(end[1],end[0]);
    }

    /**
     *
     * @param row 格子X坐标
     * @param col 格子Y坐标
     * @return 地图上屏幕坐标
     */
    public static float[] getPosition(int row,int col)
    {
        return new float[]{tempC[row][col],tempR[row][col]};
    }

    /**
     * 地图功能格子初始化
     */
    public void PositionInit() {
        tempR = new float[Map.MAP_DATA[0].length][Map.MAP_DATA[0][0].length];
        tempC = new float[Map.MAP_DATA[0].length][Map.MAP_DATA[0][0].length];

        for (int i = 0; i < Map.MAP_DATA[0].length; i++) {
            for (int j = 0; j < Map.MAP_DATA[0][0].length; j++) {
                float xOffset = 2 * a * j + xGlobalOffset;
                float yOffset = 2 * a * i + yGlobalOffset;
                tempC[i][j] = xOffset;
                tempR[i][j] = yOffset;


            }
        }
    }

    /**
     *
     *根据屏幕坐标确定格子
     */
        public static int[] getRowcol(float x, float y){
            int col =Math.round( (x-xGlobalOffset)/(2*a));
            int row = Math.round((y-yGlobalOffset)/(2*a));
            if(row>4 || col >9 || row<0|| col <0){
                //选中炮塔之后，如果触控的区域超过地图的范围，则炮塔在左上角进行绘制
                return new int[]{0,0};
            }
            return new int[]{row,col};
        }



    }









