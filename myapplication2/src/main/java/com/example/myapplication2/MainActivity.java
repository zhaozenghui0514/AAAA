package com.example.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private LuckyPan luck;
    private ImageView start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果转盘不在旋转
                if(!luck.isStart()){
                    luck.LuckyStart();//让他旋转
                    start.setImageResource(R.mipmap.stop);
                } else {//还在旋转
                    //如果停止按钮没有按
                    if(!luck.isShouldEnd()){
                        //点击一下就让他停止
                        luck.LuckyEnd();
                        start.setImageResource(R.mipmap.start);

                    } else {
                        //如果还在旋转，但是停止按钮已经按了,按钮不起作用
                        start.setImageResource(R.mipmap.start);
                    }
                }
            }
        });
    }

    private void initView() {
        luck = (LuckyPan) findViewById(R.id.luck);
        start = (ImageView) findViewById(R.id.start);
    }
}
