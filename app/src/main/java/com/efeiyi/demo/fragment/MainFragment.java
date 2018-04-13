package com.efeiyi.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efeiyi.demo.R;
import com.efeiyi.demo.base.BaseFragment;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.engine.SystemWebView;

/**
 * Created by Administrator on 2018/4/10.
 *  基类
 */

public class MainFragment extends BaseFragment {


    public static final String TAG = MainFragment.class.getSimpleName();

    private CordovaPreferences preferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected SystemWebView getWebView() {
        // 返回设置的webView
        return mRootView.findViewById(R.id.main_webView);
    }

    @Override
    protected void setUpListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

        if(webView != null) {

            webView.loadUrl( "file:///android_asset/www/index.html");
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragmetn_main;
    }


    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }



}
