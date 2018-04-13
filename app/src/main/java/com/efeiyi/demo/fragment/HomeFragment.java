package com.efeiyi.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efeiyi.demo.R;
import com.efeiyi.demo.utils.LogUtils;

import org.apache.cordova.Config;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;


/**
 * Created by Administrator on 2018/4/10.
 */

public class HomeFragment extends Fragment implements CordovaInterface {


    public static final String TAG = HomeFragment.class.getSimpleName();

    private Context mContext;

    private SystemWebView webView;

    private View mRootView;

    private CordovaWebView mWebView;
    private CordovaPreferences preferences;
    protected String launchUrl;
    protected ArrayList<PluginEntry> pluginEntries;
    private ConfigXmlParser parser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logConfig();
        LogUtils.d(TAG,"homefragment");
    }

    private void logConfig() {

        parser = new ConfigXmlParser();
        parser.parse(this.getActivity());
        preferences = parser.getPreferences();
        preferences.setPreferencesBundle(getActivity().getIntent().getExtras());
        launchUrl = parser.getLaunchUrl();
        pluginEntries= parser.getPluginEntries();
        LogUtils.d(TAG,"homeFragment" +
                "plugin +" + pluginEntries.size());
        parser.parse(mContext);

        for (PluginEntry pluginEntry : pluginEntries) {

            LogUtils.d(TAG,"获取的值 : " + pluginEntry.service);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {

            LayoutInflater localInflate = inflater.cloneInContext(new CordovaContext(mContext, this));
            mRootView = localInflate.inflate(attachLayoutRes(), null);
            webView = (SystemWebView) mRootView.findViewById(R.id.home_webView);
            mWebView = new CordovaWebViewImpl(new SystemWebViewEngine(webView));
            mWebView.init(new CordovaInterfaceImpl(this.getActivity()),parser.getPluginEntries(),parser.getPreferences());
         //   mWebView.init(new CordovaInterfaceImpl(getActivity()),);
            Config.init(getActivity());


        }
        return mRootView;
    }

    private int attachLayoutRes() {
        return R.layout.fragment_home;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mWebView.loadUrl("file:///android_asset/www/index.html");
        webView.loadUrl("file:///android_asset/www/index.html");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != webView) {
            webView.getCordovaWebView().handleDestroy();
        }
    }

    //---------------------------- 配置cordova----------------------------------


    protected static CordovaPlugin mCordovaPlugin;

    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        LogUtils.d(
         TAG, "HomeFragment  startActivityForResult.........."
        );



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.d(TAG,"homeFragment onActivityResult.......... ");

        CordovaPlugin cordovaPlugin = this.mCordovaPlugin;

        if(cordovaPlugin !=  null) {
            cordovaPlugin.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {
        LogUtils.d(TAG,"HomeFragment  setActivityResultCallback .....");
    }

    @Override
    public Object onMessage(String id, Object data) {
        LogUtils.d(TAG,"homeFragment onMessage " + id);
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        LogUtils.d(TAG,"homeFragment getThreadPool.....");
        return null;
    }

    @Override
    public void requestPermission(CordovaPlugin plugin, int requestCode, String permission) {

        LogUtils.d(TAG,"homeFragment requestPermission");
    }

    @Override
    public void requestPermissions(CordovaPlugin plugin, int requestCode, String[] permissions) {

        LogUtils.d(TAG,"homeFragment  requestPermissions...");
    }

    @Override
    public boolean hasPermission(String permission) {

        LogUtils.d(TAG,"homeFragment  hasPermission");

        return false;
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;

    }
}
