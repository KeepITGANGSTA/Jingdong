package com.bwie.jingdong;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CusActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener{

    private CaptureManager captureManager;
    private boolean isLight=false;
    private DecoratedBarcodeView mdb;
    private Button btn_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus);
        btn_light= (Button) findViewById(R.id.btn_light);
        mdb= (DecoratedBarcodeView) findViewById(R.id.mdb);
        mdb.setTorchListener(this);
        if (!hasLight()){
            btn_light.setVisibility(View.GONE);
        }

        captureManager=new CaptureManager(this,mdb);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();

        btn_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLight){
                    mdb.setTorchOff();
                }else {
                    mdb.setTorchOn();
                }
            }
        });
    }


    private boolean hasLight(){
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mdb.onKeyDown(keyCode,event) || super.onKeyDown(keyCode,event);
    }


    @Override
    public void onTorchOn() {
        Toast.makeText(this, "torch on", Toast.LENGTH_SHORT).show();
        isLight=true;
    }

    @Override
    public void onTorchOff() {
        Toast.makeText(this, "torch off", Toast.LENGTH_SHORT).show();
        isLight=false;
    }


}
