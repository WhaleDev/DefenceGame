package com.example.lastdefence.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.lastdefence.R;
import com.example.lastdefence.allActivity.StartGameActivity;
import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.game.Game;
import com.example.lastdefence.game.LBX;
import com.example.lastdefence.game.TowerAdd;
import com.example.lastdefence.game.Update;
import com.example.lastdefence.game.UpdateBitmap;
import com.example.lastdefence.master.MonsterList;
import com.example.lastdefence.threads.BulletRunThread;
import com.example.lastdefence.threads.CreateMonster;
import com.example.lastdefence.threads.DrawThread;
import com.example.lastdefence.threads.MonsterRunThread;
import com.example.lastdefence.towers.TowerList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    protected boolean threadFlag;//线程标志
    DrawThread dmt;//绘制怪物线程
    MonsterRunThread mrt;//怪物运动线程
    public BulletRunThread tft;//子弹运动线程
    StartGameActivity activity;

    public Paint paint;       //画笔
    public LBX lbx;           //绘制地图
    public TowerList tower_list;    //塔列表封装了对塔的各种操作

    public Game game;           //怪物在地图上行走相关
    public Score score;        //绘制分数
    public Update update;
    public ScreenScaleResult screenScaleResult;
    public boolean threadIsDie = false;//判断线程是否在运行
    public int mapNum;         //地图编号
    public CreateMonster cm;//生成怪物线程
    public int coin = 150;        //总金钱
    public int totalScore = 0;          //总积分
    public int boshu = 1;             //怪物波数
    public float shanXingjiao = 0;     //扇形角
    public boolean playDongHua;         //开始动画
    public MonsterList master_list; //怪物列表封装了对怪物的各种操作
    public int boShuCount = Constants.MASTER_COUNT;
    public int life = 10; //生命值

    public boolean isPlay = true; // 标记游戏运行状态
    public boolean gameisover = false;  //判断游戏是否结束
    public boolean isPressOnTower = false; //炮的点击状态
    public boolean isDrawMenu = false;//菜单状态
    public boolean canUpgrade = true; //判断是否可以升级
    public TowerAdd ta;       //造塔

    public Bitmap creep_1;
    public Bitmap creep_2;
    public Bitmap creep_3;
    Bitmap background;
    Bitmap road;
    Bitmap start;
    Bitmap end;
    Bitmap red_put;
    Bitmap turn_num1;
    Bitmap turn_num2;
    Bitmap turn_num3;
    Bitmap turn_num4;
    Bitmap button_0;
    Bitmap button_1;
    Bitmap button_2;
    Bitmap button_3;
    Bitmap tower1;
    Bitmap tower2;
    Bitmap tower3;
    Bitmap tower4;

    Bitmap master1;
    Bitmap huiHe;
    Bitmap deFen;

    public Bitmap backBlack;
    public Bitmap gameOver;
    public Bitmap levelScore;
    public Bitmap bulletShell;

    public Bitmap towerSell;
    public Bitmap towerUpgrade;
    public Bitmap pause;         //点击游戏暂停
    public Bitmap continuePlay;  //点击继续游戏



    public boolean pao1Click = true;  //炮1按钮处在可点击状态
    public boolean pao2Click = true;
    public boolean pao3Click = true;
    public boolean pao4Click = true;


    /**
     * 构造函数
     *
     * @param
     */
    public GameView(StartGameActivity activity) {
        super(activity);
        //传入当前屏幕大小值,进行缩放计算
        screenScaleResult = ScreenScaleUtil.calScale(Constants.PMX1, Constants.PMY1);
        //获取缩放后的左上角xy坐标及缩放比例
        Constants.LOX = screenScaleResult.lucX;
        Constants.LOY = screenScaleResult.lucY;
        Constants.RADIO = screenScaleResult.ratio;
        this.activity = activity;           //获取activity对象
        this.getHolder().addCallback(this); //获取holder对象
        paint = new Paint();
        paint.setAntiAlias(true);            //设置抗锯齿效果
        mapNum = activity.mapNum;            //获取地图编号
        this.setFocusable(true);             //设置控制键盘获取按钮焦点
        this.setFocusableInTouchMode(true);  //设置焦点的联系方式
    }


    //点击事件由TowerAdd类完成
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameisover) return true;
        ta.touchEvent(event);
        return true;
    }


    //绘图方法
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.GRAY);       //绘制背景色
        paint.setStyle(Style.FILL);
        canvas.drawRect(-10, -10, Constants.PMX1 + 10, Constants.PMY1 + 10, paint);

        canvas.save();
        canvas.translate(Constants.LOX, Constants.LOY);     //绘制平移
        canvas.scale(Constants.RADIO, Constants.RADIO);     //绘制缩放
        paint.setColor(Color.GRAY);
        paint.setStyle(Style.FILL);
        canvas.drawRect(-10, -10, Constants.PMX + 10, Constants.PMY + 10, paint);

        lbx.drawMap(canvas, paint);

        paint.setAlpha(255);
        master_list.draw(canvas);

        ta.draw(canvas);   //移动过程中绘制塔
        tower_list.draw(canvas);
        //绘制塔按钮
        if (!gameisover) {
            if (coin >= Constants.PUTTOWER1CONSUMECOIN && !isPressOnTower) {
                canvas.drawBitmap(button_0, Constants.BUTTON_TOWER_POSITION_X, Constants.PMY - Constants.BUTTON_TOWER_LENGTH, paint);
                pao1Click = true;
            } else {
                if (!isPressOnTower) {
                    canvas.drawBitmap(UpdateBitmap.liangDu(button_0, 0.4f), Constants.BUTTON_TOWER_POSITION_X,
                            Constants.PMY - Constants.BUTTON_TOWER_LENGTH, paint);
                    pao1Click = false;
                }
            }
            if (coin >= Constants.PUTTOWER2CONSUMECOIN && !isPressOnTower) {
                canvas.drawBitmap(button_1, button_0.getWidth()+Constants.BUTTON_TOWER_POSITION_X*2, Constants.PMY - Constants.BUTTON_TOWER_LENGTH, paint);
                pao1Click = true;
            } else {
                if (!isPressOnTower) {
                    canvas.drawBitmap(UpdateBitmap.liangDu(button_1, 0.4f), button_0.getWidth()+Constants.BUTTON_TOWER_POSITION_X*2,
                            Constants.PMY - Constants.BUTTON_TOWER_LENGTH, paint);
                    pao1Click = false;
                }
            }

            if (coin >= Constants.PUTTOWER3CONSUMECOIN && !isPressOnTower) {
                canvas.drawBitmap(button_2, button_0.getWidth()*2+Constants.BUTTON_TOWER_POSITION_X*3, Constants.PMY - Constants.BUTTON_TOWER_LENGTH, paint);
                pao1Click = true;
            } else {
                if (!isPressOnTower) {
                    canvas.drawBitmap(UpdateBitmap.liangDu(button_1, 0.4f), button_0.getWidth()*2+Constants.BUTTON_TOWER_POSITION_X*3,
                            Constants.PMY - Constants.BUTTON_TOWER_LENGTH, paint);
                    pao1Click = false;
                }
            }

        }
        if(life<=0&&dmt!=null&&mrt!=null&&tft!=null){
            dmt.setFlag(false);
            mrt.setFlag(false);
            tft.setFlag(false);

        }
        paint.setStyle(Style.STROKE);
        canvas.drawBitmap(huiHe, Constants.PMX / 2 - 70, 0, paint);
        canvas.drawBitmap(deFen, 0, 0, paint);
        score.drawSelf(canvas,paint,2);
        score.drawSelf(canvas,paint,1);
        score.drawSelf(canvas,paint,3);

        if(isPressOnTower){  //点击塔操作
            if(canUpgrade){
                canvas.drawBitmap(towerUpgrade,3*Constants.PMX/5,
                        Constants.PMY - towerUpgrade.getHeight(),paint);
            }else {         //不满足升级条件，控件低亮度显示
                canvas.drawBitmap(UpdateBitmap.liangDu(towerUpgrade,0.4f),3*Constants.PMX/5,
                        Constants.PMY-towerUpgrade.getHeight(),paint);
            }
            canvas.drawBitmap(towerSell,2*Constants.PMX/5,
                    Constants.PMY-towerUpgrade.getHeight(),paint);

        }

        if(gameisover){
            canvas.drawBitmap(backBlack, 0,0, paint);
            canvas.drawBitmap(gameOver, 268,76, paint);
            canvas.drawBitmap(levelScore, 268,76+gameOver.getHeight()+20, paint);
            score.drawSelf(canvas,paint,4);
        }

        canvas.restore();
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    public void pauseOrContinueThreads()
    {
        cm.pause = !cm.pause;
        mrt.pause = !mrt.pause;
        tft.pause = !tft.pause;
    }

    public void replay()
    {
        activity.replay();
    }

    //初始化格子
    public Bitmap[] getLBXBit() {
        return new Bitmap[]{
                road,
                start,
                end,
                turn_num1,
                turn_num2,
                turn_num3,
                turn_num4
        };
    }

    //初始化按钮
    public Bitmap[] getButton() {
        return new Bitmap[]{button_0, button_1, button_2, button_3,
                tower1, tower2, tower3, tower4, red_put};
    }

    public void surfaceCreated(SurfaceHolder holder) {
        background = BitmapFactory.decodeResource(this.getResources(), R.mipmap.backgroung);
        road = BitmapFactory.decodeResource(this.getResources(), R.mipmap.road);
        button_0 = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.tower_button_1);
        button_1 = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.tower_button_2);
        button_2 = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.tower_button_3);

        tower1 = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.test_tower);
        tower2 = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.test_tower);
        master1 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.monster1);
        creep_1 = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.monster1);
        huiHe = BitmapFactory.decodeResource(this.getResources(), R.mipmap.huihe);
        deFen = BitmapFactory.decodeResource(this.getResources(), R.mipmap.defen);
        backBlack = BitmapFactory.decodeResource(this.getResources(), R.mipmap.backblack);
        gameOver=BitmapFactory.decodeResource(this.getResources(), R.mipmap.gameover);
        levelScore = BitmapFactory.decodeResource(this.getResources(), R.mipmap.guanqiadefen);
        bulletShell=BitmapFactory.decodeResource(this.getResources(), R.mipmap.bullet_shell);
        towerSell=BitmapFactory.decodeResource(this.getResources(), R.mipmap.sell);
        towerUpgrade = BitmapFactory.decodeResource(this.getResources(),R.mipmap.update);
        pause = BitmapFactory.decodeResource(this.getResources(),R.mipmap.replay);
        continuePlay = BitmapFactory.decodeResource(this.getResources(),R.mipmap.pause);

        game = new Game(this);
        master_list = new MonsterList(this);
        tower_list = new TowerList();
        lbx = new LBX(road, this);
        tft = new BulletRunThread(tower_list);
        ta = new TowerAdd(this);

        score = new Score(this)	;

        if (this.boshu % 3 == 1)
            cm = new CreateMonster(master_list, this, creep_1);
        if (this.boshu % 3 == 2)
            cm = new CreateMonster(master_list, this, creep_1);
        if (this.boshu % 3 == 0)
            cm = new CreateMonster(master_list, this, creep_1);

        cm.start();
        boShuCount = Constants.MASTER_COUNT;



        mrt = new MonsterRunThread(this);
        mrt.start();
        dmt = new DrawThread(this);
        dmt.start();
    }

    @SuppressLint("WrongCall")
    public void repaint() {
        SurfaceHolder holder = this.getHolder();

        Canvas canvas = holder.lockCanvas();
        try {
            synchronized (holder) {
                onDraw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }

    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
        master_list.clear();
        tower_list.clear();

        cm.setFlag(false);
        mrt.setFlag(false);
        dmt.setFlag(false);
        tft.setFlag(false);

        try {
            dmt.join();
            mrt.join();
            cm.join();
            tft.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}



