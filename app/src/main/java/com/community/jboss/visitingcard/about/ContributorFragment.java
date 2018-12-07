package com.community.jboss.visitingcard.about;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.about.adapter.ContributorListAdapter;
import com.community.jboss.visitingcard.about.listeners.RecyclerTouchListener;
import com.community.jboss.visitingcard.about.models.ContributionModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class ContributorFragment extends Fragment {

    public static final String PREF_DARK_THEME = "dark_theme";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Contributors");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6F00")));

        View tmp = inflater.inflate(R.layout.fragment_contributor, container, false);

        ArrayList<ContributionModel> contibutorslist = new ArrayList<>();
        try {
            String data = new ContributionCounter().execute("https://api.github.com/repos/JBossOutreach/visiting-card-android/contributors").get();
            JSONArray jsonArr = new JSONArray(data);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                ContributionModel rm = new ContributionModel();
                rm.setName(jsonObj.get("login").toString());
                rm.setAvatar(jsonObj.get("avatar_url").toString());
                rm.setContributorurl(jsonObj.get("html_url").toString());
                contibutorslist.add(rm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = tmp.findViewById(R.id.contributorlist);
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mAdapter = new ContributorListAdapter(contibutorslist, getContext(), useDarkTheme);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contibutorslist.get(position).getContributorurl()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                // Necessary
            }
        }));
        return tmp;
    }

    @Override
    public void onDestroy() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2962FF")));
        super.onDestroy();
    }

}

class ContributionCounter extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urlString) {
        String data = new ContributionCounter().readUrl(urlString[0]);
        return data;
    }

    private String readUrl(String urlString) {
        String tmp = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                }
        }
        return tmp;
    }


}
