package com.community.jboss.visitingcard.about;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.community.jboss.visitingcard.R;


public class LicenseFragment extends Fragment {
    public static final String PREF_DARK_THEME = "dark_theme";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("License");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33691E")));
        View tmpview = inflater.inflate(R.layout.fragment_license, container, false);
        if (useDarkTheme) {
            (tmpview.findViewById(R.id.license_holder)).setBackgroundColor(Color.parseColor("#212121"));
            ((AppCompatTextView) tmpview.findViewById(R.id.license_text_view)).setTextColor(Color.WHITE);
        }

        return tmpview;
    }

    @Override
    public void onDestroy() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2962FF")));
        super.onDestroy();
    }
}
