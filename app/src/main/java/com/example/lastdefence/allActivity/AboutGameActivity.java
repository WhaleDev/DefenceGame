package com.example.lastdefence.allActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lastdefence.R;
import com.example.lastdefence.utils.Utils;

public class AboutGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为全屏模式
        setContentView(R.layout.activity_about_game);
        Utils.hideBottomUIMenu(this);
        Button buttonBack = (Button)findViewById(R.id.go_back);
        buttonBack.setOnClickListener(new Button.OnClickListener(){
            @Override
                public void onClick(View v){
                    finish();
        }}
        );
    }
}
