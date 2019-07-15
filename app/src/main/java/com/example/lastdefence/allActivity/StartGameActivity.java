package com.example.lastdefence.allActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lastdefence.constant.Constants;
import com.example.lastdefence.utils.Utils;
import com.example.lastdefence.view.GameView;

public class StartGameActivity extends AppCompatActivity {

    GameView mv;
    public int mapNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);   //设置为全屏
        Utils.hideBottomUIMenu(this);
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Constants.PMX1 = display.getWidth();    //测试机小米8se PMX1=2004 PMY1=1080
        Constants.PMY1 = display.getHeight();
        Log.i("查看屏幕大小",Constants.PMX1+"     "+Constants.PMY1);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        mapNum = bundle.getInt("mapNum");
        mv = new GameView(this);
        this.setContentView(mv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //继续游戏
    public void replay()
    {
        mv = new GameView(this);
        this.setContentView(mv);
    }


    /**
     * 双击退出游戏
     */
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    Toast.makeText(StartGameActivity.this, "再按一次返回主菜单", Toast.LENGTH_SHORT).show();
                    firstTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        return super.onKeyDown(keyCode, event);
    }

}
