package com.efeiyi.demo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.efeiyi.demo.R;
import com.efeiyi.demo.fragment.BaseFragment;
import com.efeiyi.demo.fragment.PersonFragment;
import com.efeiyi.demo.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends FragmentActivity {


    public static final String TAG = Main2Activity.class.getSimpleName();

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    private BaseFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        initView();
    }


    // init
    private void initView() {


        //MainFragment fragment = MainFragment.newInstance();
        //  HomeFragment fragment = HomeFragment.newInstance();

       // fragment = CordovaFragment.NewInstance();
        fragment = PersonFragment.NewInstance();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.fl_main, fragment);

        ft.commit();

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        LogUtils.d(TAG, "Main2Activity  === : request :" + requestCode);
      //  fragment.startActivityForResult(intent,requestCode);
    }
//
    /**
     * 返回的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fragment.onActivityResult(requestCode,resultCode,data);
        LogUtils.d(TAG, "CordovaActivity --- onActivityResult : requestCode = " + requestCode +
                "resultCode = " + resultCode + "data : " + data == null ? "true" : "false");

    }
}

