package com.animelabs.gopaisasampleproject.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.animelabs.gopaisasampleproject.R;
import com.animelabs.gopaisasampleproject.Utility.SharedPrefUtility;

import static java.lang.Thread.sleep;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class SplashActivity extends AppCompatActivity {
    public static final String IS_FIRST_TIME_CALLED = "IS_FIRST_TIME_CALLED";
    private TextView splash_text;
    private Animation slide_up;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefUtility.getBoolean(getApplicationContext())){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(IS_FIRST_TIME_CALLED,true);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_splash);
        slide_up = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.scale_up_animation);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/lbstor_regu.ttf");
        splash_text = (TextView)findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.startAnimation(slide_up);
        splash_text.setTypeface(custom_font);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                }catch (Exception e){

                }finally {
                    Intent intent;
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();
    }
}
