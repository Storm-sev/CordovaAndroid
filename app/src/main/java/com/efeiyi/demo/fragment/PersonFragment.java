package com.efeiyi.demo.fragment;

import com.efeiyi.demo.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class PersonFragment extends BaseFragment {
    @Override
    protected void setUpListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String loadWebViewUrl() {
        return "file:///android_asset/www/index.html";
    }

    @Override
    protected int attachWebviewIdRes() {
        return R.id.wb_person;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_person;
    }

    public static BaseFragment NewInstance() {
        PersonFragment fragment = new PersonFragment();
        return fragment;
    }
}
