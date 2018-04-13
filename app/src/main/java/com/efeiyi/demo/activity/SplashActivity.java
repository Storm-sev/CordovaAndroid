package com.efeiyi.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.efeiyi.demo.R;

public class SplashActivity extends AppCompatActivity {

    private Button btn_to_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();

        setupListener();
    }

    private void setupListener() {


        btn_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startToMain();
            }
        });
    }

    private void startToMain() {
        startActivity(new Intent(this, Main2Activity.class));
    }

    private void initView() {

        btn_to_main = findViewById(R.id.btn_to_main);

    }


}
