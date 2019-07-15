package com.example.lastdefence.allActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lastdefence.R;
import com.example.lastdefence.utils.Utils;

public class ListViewActivity extends Activity {
    int[] msgIds={R.string.number1,R.string.number2,R.string.number3,
            R.string.number4,R.string.number5,R.string.number6,
            R.string.number7,R.string.number8,R.string.number9,
            R.string.number10,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为全屏模式
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //设置为横屏模式
        setContentView(R.layout.listviewactivitymain);
        Utils.hideBottomUIMenu(this);

        ListView lv = (ListView)this.findViewById(R.id.ListView01); //初始化ListView
        //为ListView准备内容适配器
        BaseAdapter ba=new BaseAdapter(){
            LayoutInflater inflater=LayoutInflater.from(
                    ListViewActivity.this);
            @Override
            public int getCount() {
                return msgIds.length;								//总共20个选项
            }
            @Override
            public Object getItem(int arg0) { return null; }
            @Override
            public long getItemId(int arg0) { return 0; }
            @Override
            public View getView(int arg0, View arg1, ViewGroup arg2) {
                LinearLayout ll = (									//拿到LinearLayout
                        LinearLayout)(inflater.inflate(
                        R.layout.row, null).findViewById(R.id.LinearLayout_row));
                TextView tv=(TextView)ll.getChildAt(0);
                tv.setText(getResources().getText(msgIds[arg0]));	//设置关卡内容
                return ll;
            }};
        lv.setAdapter(ba);

        lv.setOnItemClickListener(									//设置选项被单击的监听器
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(						//创建Intent（开始游戏）
                                ListViewActivity.this,StartGameActivity.class);

                        bundle.putInt("mapNum", arg2);		//传递关卡号与最高得分给StartGameActivity

                        intent.putExtra("bundle", bundle);
                        ListViewActivity.this.startActivity(intent);	//启动StartGameActivity
                    } });
        lv.setPadding(50,0,50,0);
        lv.setBackgroundColor(Color.GRAY);


    }

}
