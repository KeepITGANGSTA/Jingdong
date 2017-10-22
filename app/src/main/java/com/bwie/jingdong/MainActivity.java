package com.bwie.jingdong;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private int num=4;
    private Handler handler=new Handler();
    Runnable task=new Runnable() {
        @Override
        public void run() {
            num--;
            if (num==0){
                handler.removeCallbacks(task);
                Intent intent=new Intent(MainActivity.this,GoodsActivity.class);
                startActivity(intent);
                finish();
            }else {
                handler.postDelayed(this,1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(MainActivity.this,GoodsActivity.class);
        startActivity(intent);
        finish();
        //handler.postDelayed(task,1000);

    }

}
