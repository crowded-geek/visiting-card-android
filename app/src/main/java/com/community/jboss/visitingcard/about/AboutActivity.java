package com.community.jboss.visitingcard.about;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.community.jboss.visitingcard.R;

public class AboutActivity extends AppCompatActivity {


    public Toolbar toolbar;
    public static boolean state = false;

    public static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if (useDarkTheme) {
            setTheme(R.style.AppTheme_BG);
        }
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbar_about);

        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_about, new AboutFragment()).commit();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(v -> {
            if (state) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_in_left).replace(R.id.frame_about, new AboutFragment()).commit();
                state = false;
            } else finish();
        });
        getSupportActionBar().setTitle("About");
        toolbar.setBackgroundColor(Color.parseColor("#2962FF"));

    }

    @Override
    public void onBackPressed() {
        if (state) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_about, new AboutFragment()).commit();
            toolbar.setTitle("About");
            toolbar.setBackgroundColor(Color.parseColor("#2962FF"));
            state = false;
        } else super.onBackPressed();
    }
}

