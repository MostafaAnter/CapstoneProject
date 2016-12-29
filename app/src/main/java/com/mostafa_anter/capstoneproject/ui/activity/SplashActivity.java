package com.mostafa_anter.capstoneproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mostafa_anter.capstoneproject.R;
import com.mostafa_anter.capstoneproject.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Util.AnimateView {

    @BindView(R.id.text_container)
    LinearLayout linearLayout;
    @BindView(R.id.app_name)
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // change font of app_name text view
        Util.changeViewTypeFace(this, "Righteous-Regular.ttf", appName);

        // animate container of text views :)
        Util.animateView(this, R.anim.fade_in_enter, linearLayout, this);
    }

    @Override
    public void onAnimationEnd() {
        // do some thing
        startActivity(new Intent(SplashActivity.this, MainActivity.class)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
