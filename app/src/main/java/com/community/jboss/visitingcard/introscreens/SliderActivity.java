package com.community.jboss.visitingcard.introscreens;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.community.jboss.visitingcard.LoginActivity;
import com.community.jboss.visitingcard.R;

public class SliderActivity extends AppCompatActivity {

    private String TAG = "SliderActivity";
    private TextView[] mDots;
    private LinearLayout mDotLayout;
    private android.widget.Button button;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //  Nothing required for now
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            // Hiding or Showing the button depending upon the position of pager screen

            if (position == mDots.length - 1) {      // If on last page, show the Login Button
                button.setEnabled(true);
                button.animate().alpha(1f).setDuration(500);   // Showing Button
                button.setText(R.string.slider_button_text);
            } else {                                  // Otherwise, keep it hidden
                button.setEnabled(false);
                button.animate().alpha(0f).setDuration(300);   // Hiding Button
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //  Nothing required for now
        }
    };

    public void addDotsIndicator(int position) {
        mDots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setGravity(Gravity.CENTER);
            mDots[i].setTextColor(Color.parseColor("#AA000000"));
            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(Color.parseColor("#FFCC00"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        //TODO: The Slider should appear only on when the app is launched for the first time. - Add appropriate conditions for that.



        // TODO: Create Introduction slides explaining all the functionalities of the app here.

        // TODO: if you're creating an Adapter for the ViewPager create it in the same Package and name it as SlideAdapter

        Log.d(TAG, "Opening Slider");

        // TODO: Create Introduction slides explaining all the functionality of the app here.
        // TODO: If you're creating an Adapter for the ViewPager, create it in the same Package and name it as SliderAdapter

        ViewPager sliderViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);
        button = findViewById(R.id.button);

        button.setAlpha(0f);

        int numberOfItemsInSlider = 4;
        SliderAdapter sliderAdapter = new SliderAdapter(this, numberOfItemsInSlider);
        sliderViewPager.setAdapter(sliderAdapter);
        sliderViewPager.addOnPageChangeListener(pageChangeListener); // Page-change-listener defined at bottom of this class
        addDotsIndicator(0); // Passing in the initial position of slider

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SliderActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}