package com.efeiyi.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efeiyi.demo.R;
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

/**
 * Created by Administrator on 2018/4/11.
 */

public class CordovaFragment extends Fragment implements CordovaInterface {

    public static final String TAG = CordovaFragment.class.getSimpleName();


    private Context mContext;

    private View mRootView;
    private SystemWebView webView;
    private CordovaWebView mWebView;
    private ConfigXmlParser parser;
    private CordovaPreferences preferences;
    private ArrayList<PluginEntry> pluginEntries;
    private String launchUrl;
    private MyCordovaInterfaceImpl mCordovaInterface;


    /**
     * 创建本类实例
     */
    public static CordovaFragment NewInstance() {
        return new CordovaFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localConfig();
    }

    private void localConfig() {
        // 设置数据解析器
        parser = new ConfigXmlParser();
        parser.parse(this.getActivity());
        preferences = parser.getPreferences();
        preferences.setPreferencesBundle(getActivity().getIntent().getExtras());
        launchUrl = parser.getLaunchUrl();
        pluginEntries = parser.getPluginEntries();

        parser.parse(mContext);

        mCordovaInterface = new MyCordovaInterfaceImpl(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            LayoutInflater localInflater = inflater.cloneInContext(new CordovaContext(getActivity(), this));
            mRootView = localInflater.inflate(attachLayoutRes(), null);
            webView = mRootView.findViewById(R.id.wb_webView);

            //Config.init(getActivity());
            //构建 CordovaWebView
            mWebView = new CordovaWebViewImpl(new SystemWebViewEngine(webView));
//            mWebView.init(new CordovaInterfaceImpl(this.getActivity()), parser.getPluginEntries(), preferences);
            mWebView.init(mCordovaInterface,parser.getPluginEntries(),preferences);
        }
        return mRootView;
    }

    @LayoutRes
    private int attachLayoutRes() {
        return R.layout.fragment_cordova;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 加载视图
//        webView.loadUrl("file:///android_asset/www/index.html");
        mWebView.loadUrl("file:///android_asset/www/index.html");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (null != webView) {
            webView.getCordovaWebView().handleDestroy();
        }
    }

    //------------------------------------------------

    protected static CordovaPlugin mCordovaPlugin;

    protected boolean keepRunning = true;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    protected boolean activityResultKeepRunning;


//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//        super.startActivityForResult(intent, requestCode);
//        LogUtils.d(TAG,"CordovaFragment   startActivityForResult : " + requestCode);
//    }


    /**
     *
     */
    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        LogUtils.d(TAG, "CordovaFragment  --- startActivityForResult() " + command.getServiceName() +
                "requestCode : " + requestCode);
            this.mCordovaPlugin = command;
            this.activityResultKeepRunning = this.keepRunning;
            if(command != null) {
                this.keepRunning = false;
            }
        super.startActivityForResult(intent, requestCode);
    }




    /**
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.d(TAG,"CordovaFragment -dddddddddddd-- onActivityResult : resultCode = " +
                resultCode + " resultCode : " + resultCode + " data: " + data == null? 1:0);
      //  super.onActivityResult(requestCode, resultCode, data);
        // 获取插件
        CordovaPlugin plugin = this.mCordovaPlugin;
        if (plugin != null) {
            plugin.onActivityResult(requestCode, resultCode, data);
        }
        mCordovaInterface.onActivityResult(requestCode, resultCode,data);
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
