package com.example.lastdefence.allActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lastdefence.R;
import com.example.lastdefence.utils.Utils;

public class MainGameActivity extends AppCompatActivity {

    //Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为全屏模式
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //设置为横屏模式
        setContentView(R.layout.activity_main_game);
        Utils.hideBottomUIMenu(this);
        Button start = (Button)findViewById(R.id.buttonStart);
        start.setOnClickListener(new Button.OnClickListener(){			//开始按钮添加监听
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGameActivity.this,ListViewActivity.class);
                MainGameActivity.this.startActivity(intent);		//发送Intent启动ListViewActivity
            }});

        Button aboutBtn = (Button)findViewById(R.id.buttonAbout)  ;			//关于游戏
        aboutBtn.setOnClickListener(new Button.OnClickListener(){			//按钮添加监听
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGameActivity.this,AboutGameActivity.class);
                MainGameActivity.this.startActivity(intent);
            }})  ;
        Button exitBtn = (Button) this.findViewById(R.id.buttonExit)  ;			//退出游戏按钮
        exitBtn.setOnClickListener(new Button.OnClickListener(){					//按钮添加监听
            @Override
            public void onClick(View arg0){
                System.exit(0)  ;											//退出游戏
            }}) ;
    }



}
