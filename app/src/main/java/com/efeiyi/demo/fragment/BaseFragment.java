package com.efeiyi.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efeiyi.demo.utils.LogUtils;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/16.
 * Cordova fragment 基类
 */
public abstract class BaseFragment extends Fragment implements CordovaInterface {

    public static final String TAG = BaseFragment.class.getCanonicalName();

    protected Context mContext;
    protected View mRootView;

    protected ConfigXmlParser mParser;
    protected CordovaPreferences preferences;
    protected String launchUrl;
    protected MyCordovaInterfaceImpl mCordovaInterfaceImpl;

    protected ArrayList<PluginEntry> mPluginEntries;
    protected SystemWebView systemWebView;
    protected CordovaWebView mWebView;


    private Unbinder unbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadConfig();

    }

    private void loadConfig() {

        mParser = new ConfigXmlParser();
        mParser.parse(this.getActivity());
        preferences = mParser.getPreferences();
        preferences.setPreferencesBundle(getActivity().getIntent().getExtras());
        launchUrl = mParser.getLaunchUrl();

        mPluginEntries = mParser.getPluginEntries();

        mParser.parse(mContext);

        mCordovaInterfaceImpl = new MyCordovaInterfaceImpl(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            LayoutInflater localInflater = inflater.cloneInContext(new CordovaContext(mContext, this));
            mRootView = localInflater.inflate(attachLayoutRes(), null);
            unbinder = ButterKnife.bind(this, mRootView);
            systemWebView = mRootView.findViewById(attachWebviewIdRes());
            // 构建systemWebView
            mWebView = new CordovaWebViewImpl(new SystemWebViewEngine(systemWebView));
            // webView初始化
            mWebView.init(mCordovaInterfaceImpl, mParser.getPluginEntries(), preferences);

        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWebView.loadUrl(loadWebViewUrl());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setUpListener();
    }

    /**
     * 监听
     */
    protected abstract void setUpListener();


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 返回webView的网址
     */
    protected abstract String loadWebViewUrl();

    /**
     * 获取webView页面
     */
    @IdRes
    protected abstract int attachWebviewIdRes();


    /**
     * 获取布局文件
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        if (unbinder != null) {
            unbinder.unbind();
        }

        if (null != mWebView) {
            mWebView.handleDestroy();
        }

    }


    //  -------------------- webView 接口实现的方法  -----------------------

    protected static CordovaPlugin mCordovaPlugin;
    protected boolean keepRunning = true;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    protected boolean activityResultKeepRunning;

    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        LogUtils.d(TAG, "BaseFragment webView 版本   startActivityForResult : ");
        mCordovaPlugin = command;
        this.activityResultKeepRunning = this.keepRunning;
        if (command != null) {
            this.keepRunning = false;
        }
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get plugin
        CordovaPlugin plugin = this.mCordovaPlugin;

        if (plugin != null) {
            plugin.onActivityResult(requestCode, resultCode, data);
        }

        mCordovaInterfaceImpl.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {

        this.mCordovaPlugin = plugin;

    }

    @Override
    public Object onMessage(String id, Object data) {
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    @Override
    public void requestPermission(CordovaPlugin plugin, int requestCode, String permission) {

    }

    @Override
    public void requestPermissions(CordovaPlugin plugin, int requestCode, String[] permissions) {

    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }
}
