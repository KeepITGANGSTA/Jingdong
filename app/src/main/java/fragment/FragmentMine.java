package fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bwie.jingdong.AccountSetting;
import com.bwie.jingdong.BillDetails;
import com.bwie.jingdong.LoginActivity;
import com.bwie.jingdong.R;
import com.bwie.jingdong.SearchActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Api;
import okhttp3.Call;
import presenter.LoginPresent;
import presenter.PresentLoginView;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/9/30.
 */

public class FragmentMine extends Fragment implements PresentLoginView{

    private View mContentView;
    @BindView(R.id.lin_login)
    LinearLayout lin_login;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    @BindView(R.id.lin_myList)
    LinearLayout lin_myList;
    @BindView(R.id.iv_usericon)
    ImageView iv_usericon;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private LoginPresent loginPresent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#cccccc"));

//底部导航栏
//window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_mine, container, false);
        }
        ViewGroup viewGroup = (ViewGroup) mContentView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mContentView);
        }
        ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences = SharedPreferencesUtil.getPreferences();
        edit = preferences.edit();
        loginPresent = new LoginPresent(this,getContext());
        System.out.println("onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        int userID = preferences.getInt("UserID", 0);
        if (userID!=0){
            loginPresent.getUserInfo(Api.USER_INFO,userID);
        }else {
            iv_usericon.setImageResource(R.mipmap.b5i);
            tv_nickname.setText("登录/注册");
        }

    }


    @OnClick({R.id.lin_login,R.id.iv_setting,R.id.lin_myList})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.lin_login:
                int userID = preferences.getInt("UserID", 0);
                if (userID==0){
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getContext(), AccountSetting.class);
                    startActivity(intent);
                }

                break;
            case R.id.iv_setting:
                Intent setting=new Intent(getContext(), AccountSetting.class);
                startActivity(setting);
                break;
            case R.id.lin_myList:
                Intent intent=new Intent(getContext(), BillDetails.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void RequestFail(Call call, IOException e) {
    }

    @Override
    public void LoginSuccess(String result) {

    }

    @Override
    public void LoginFail(String result) {

    }

    @Override
    public void ResSuccess(String result) {

    }

    @Override
    public void ResFail(String result) {

    }

    @Override
    public void RequestRes(Call call, IOException e) {

    }

    @Override
    public void getInfoSuccess(String result) {
        System.out.println("获取用户信息成功");
        try {
            JSONObject object=new JSONObject(result);
            JSONObject o=object.getJSONObject("data");
            String iconUrl=o.getString("icon");
            int uid=o.getInt("uid");
            String nickName=o.getString("nickname");
            String username=o.getString("username");
            System.out.println("iconUrl--"+iconUrl);
            System.out.println("nickName--"+nickName);
            System.out.println("username--"+username);
            if (!iconUrl.equals("null")){
                System.out.println("FragmentMineOnResume---"+iconUrl);
                Glide.with(getContext()).load(iconUrl).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_usericon);
            }
            if (!nickName.equals("null")){
                tv_nickname.setText(nickName);
            }else {
                tv_nickname.setText(username);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getInfoFail(String result) {
        System.out.println("获取用户信息失败");
    }

    @Override
    public void RequestGetInfoFail(Call call, IOException e) {
        System.out.println("请求失败");
    }
}