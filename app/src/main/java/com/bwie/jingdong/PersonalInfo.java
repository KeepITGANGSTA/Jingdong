package com.bwie.jingdong;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.usher.greendao_demo.greendao.gen.UserDao;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import bean.User;
import butterknife.BindView;
import butterknife.OnClick;
import common.Api;
import manager.GreenDaoManager;
import presenter.AlertPresent;
import presenter.PresentAlertView;
import utils.OKHttp;
import utils.OkCallBack;
import utils.OkHttpMethed;

import static android.R.string.cancel;

/**
 * Created by 李英杰 on 2017/10/11.
 */

public class PersonalInfo extends BaseActivity implements PresentAlertView{

    @BindView(R.id.iv_personalBack)
    ImageView iv_personalBack;
    @BindView(R.id.iv_personalIcon)
    ImageView iv_personalIcon;
    @BindView(R.id.tv_personUsername)
    TextView tv_personUsername;
    @BindView(R.id.tv_personNickname)
    TextView tv_personNickname;
    @BindView(R.id.tv_personGendername)
    TextView tv_personGendername;
    @BindView(R.id.tv_personBirname)
    TextView tv_personBirname;
    @BindView(R.id.rel_personIcon)
    RelativeLayout rel_personIcon;
    @BindView(R.id.rel_personNickname)
    RelativeLayout rel_personNickname;
    @BindView(R.id.rel_personUsername)
    RelativeLayout rel_personUsername;

    private View inflate;
    private Button choosePhoto;
    private Button takePhoto;
    private Button btn_can;
    private Dialog dialog;
    private UserDao userDao;

    private final int CAMER=1;
    private final int PICTURE=2;
    private final int TAILOR=3;
    private Uri imgUri;
    private User load;
    private AlertPresent alertPresent;


    @Override
    public int bindLayout() {
        return R.layout.personal_view;
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.rel_personIcon,R.id.iv_personalBack,R.id.rel_personUsername,R.id.rel_personNickname})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.rel_personIcon:
                lastDialog();
                break;
            case R.id.rel_personUsername:
                showToast("暂不支持修改用户名");
                break;
            case R.id.rel_personNickname:
                startActivity(AlertNickName.class);
                break;
            case R.id.iv_personalBack:
                finish();
                break;
        }
    }

    private void lastDialog() {
        dialog = new Dialog(this,R.style.DialogSelect);
        inflate = LayoutInflater.from(this).inflate(R.layout.cancle, null);
        choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
        btn_can = (Button) inflate.findViewById(R.id.btn_qu);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        btn_can.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 10;
        lp.width=App.screen_width-20;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }



    @Override
    public void Click(View view) {
        switch (view.getId()){
            case R.id.takePhoto:
                Intent in=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //imgUri= Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"image.jpg"));
                imgUri= Uri.fromFile(new File(getCacheDir(),"image.jpg"));
                in.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
                startActivityForResult(in,CAMER);
                break;
            case R.id.choosePhoto:
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,PICTURE);
                break;
            case R.id.btn_qu:
                dialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMER:
                if (data!=null){
                    Uri uri=data.getData();
                    tailor(uri);
                }
                break;
            case PICTURE:
                if (data!=null){
                    Uri ur=data.getData();
                    tailor(ur);
                }
                break;
            case UCrop.REQUEST_CROP:
                showToast("裁剪成功");
                Uri output = UCrop.getOutput(data);
                Bitmap bitmap = BitmapFactory.decodeFile(output.getPath());
                iv_personalIcon.setImageBitmap(bitmap);
                saveImg(bitmap);
                updateIcon();
                break;
        }
    }

    /**
     *  上传头像
     */
    private void updateIcon() {
        User loadd = userDao.load((long) 1);
        int id=loadd.getUid();
        System.out.println("uid用户id-----"+id);
        File file=new File(getFilesDir()+"/shang.jpg");
        if (file.exists() && file!=null){
            OKHttp.getIntence(PersonalInfo.this).AlertIcon(Api.AlertIcon, id, file, new OkCallBack() {
                @Override
                public void onFailure(String e, String msg) {
                    showToast(msg);
                    System.out.println("失败---="+msg);
                }

                @Override
                public void onResponse(String result) {
                    showToast(result);
                    System.out.println("成功---="+result);
                    updateDb();
                }


            });
        }
    }

    private void updateDb() {
        Map<String,String> parms=new HashMap<>();
        parms.put("uid",load.getUid()+"");
        System.out.println("updateDb-----------------");
        alertPresent.AlertIco(OkHttpMethed.POST,Api.USER_INFO,parms);
    }

    /**
     * 保存图片至本地
     * @param bitmap
     */
    private void saveImg(Bitmap bitmap) {
        File file=new File(getFilesDir()+"/shang.jpg");
        try {
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void tailor(Uri uri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));
        UCrop.of(uri,destinationUri).withAspectRatio(16,16).withMaxResultSize(200,200).start(this);
    }

    @Override
    public void initView() {
        userDao = GreenDaoManager.getmDaoSession().getUserDao();
    }


    @Override
    public void initData() {
        alertPresent = new AlertPresent(this,this);
        showToast("个人中心");
        load = userDao.load((long) 1);
        String iconUrl= load.getIconUrl();
        String nickName= load.getNickname();
        String userName= load.getUsername();
        if (!iconUrl.equals("null")){
            Glide.with(this).load(iconUrl).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_personalIcon);
        }
        tv_personBirname.setText("null");
        tv_personGendername.setText("null");
        tv_personNickname.setText(nickName+"");
        tv_personUsername.setText(userName+"");

    }

    @Override
    public void AlertSuccess(String result) {
        System.out.println("Personallnfo成功---------"+result);
        try {
            JSONObject object=new JSONObject(result);
            JSONObject o=object.getJSONObject("data");
            load.setIconUrl(o.getString("icon"));
            userDao.update(load);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AlertFailure(String result) {
        System.out.println("Personallnfo失败---------"+result);
    }

    @Override
    public void Fail(String e, String msg) {
        System.out.println("Personallnfo请求失败---------"+e+"---"+msg);
    }

    @Override
    public void GoodsSuccess(String result) {

    }

    @Override
    public void GoodsFailure(String result) {

    }
}
