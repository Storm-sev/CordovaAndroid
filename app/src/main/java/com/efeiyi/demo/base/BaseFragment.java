package com.efeiyi.demo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efeiyi.demo.fragment.CordovaContext;
import com.efeiyi.demo.utils.LogUtils;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.engine.SystemWebView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/4.
 * fragment 基类
 */

public abstract class BaseFragment extends Fragment implements CordovaInterface{


    private static final String TAG = BaseFragment.class.getSimpleName();

    protected Context mContext;
    protected View mRootView;
    protected SystemWebView webView = null;

    /**
     * ButterKnife
     */
    protected Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {


            LayoutInflater localInflate = inflater.cloneInContext(new CordovaContext(getContext(),this));
            mRootView = localInflate.inflate(attachLayoutRes(), null);
            Unbinder bind = ButterKnife.bind(this, mRootView);
            Config.init(getActivity());
            webView = getWebView();
            ArrayList<String> list = new ArrayList<>();
        }

        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    protected abstract  SystemWebView getWebView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        setUpListener();
    }


    /**
     * 设置监听
     */
    protected abstract void setUpListener();


    /**
     * 加载数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initViews();


    /**
     * 加载布局
     */
    protected abstract int attachLayoutRes();


    @Override
    public void onDestroy() {
        super.onDestroy();
        
        if(webView != null) {

            webView.destroy();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    // Cordova 实现的方法
    protected boolean keepRunning = true;


    protected CordovaPlugin activityResultCallBack = null;
    protected boolean activityResultKeepRunning;

    protected final ExecutorService threadPool = Executors.newCachedThreadPool();


    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }



    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        LogUtils.d(TAG,"startActivityForResult");

        this.activityResultCallBack = command;
        this.activityResultKeepRunning = this.keepRunning;

        if(command != null) {
            this.keepRunning = false;
        }

        super.startActivityForResult(intent,requestCode);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        LogUtils.d(TAG,"onActivityResult");
        CordovaPlugin callback = this.activityResultCallBack;
        
        if(callback == null) {

            callback.onActivityResult(requestCode,resultCode,data);

        }
        // 新添加的内容
        this.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {
        LogUtils.d(TAG, "setActivityResultCallback");
        this.activityResultCallBack = plugin;
    }


    /**
     * 单个权限申请
     * @param plugin
     * @param requestCode
     * @param permission
     */
    @Override
    public void requestPermission(CordovaPlugin plugin, int requestCode, String permission) {

    }


    /**
     * 权限组申请
     */
    @Override
    public void requestPermissions(CordovaPlugin plugin, int requestCode, String[] permissions) {

    }


    /**
     * 判断是否有当前权限
     */
    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public Object onMessage(String id, Object data) {
        LogUtils.d(TAG,"onMessage");
        return this.onMessage(id,data);
    }


}
