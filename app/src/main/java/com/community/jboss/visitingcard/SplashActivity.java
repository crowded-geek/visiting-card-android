package com.community.jboss.visitingcard;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.widget.ImageView;

import com.community.jboss.visitingcard.IntroScreens.SliderActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView animatedView = findViewById(R.id.anim_v);
        Drawable animation = animatedView.getDrawable();
        if(animation instanceof AnimatedVectorDrawableCompat) {
            ((AnimatedVectorDrawableCompat) animation).start();
        } else if(animation instanceof  AnimatedVectorDrawable){
            ((AnimatedVectorDrawable) animation).start();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                startActivity(i);
            }
        },4000);
    }
}
