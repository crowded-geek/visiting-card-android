package com.community.jboss.visitingcard.about;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.community.jboss.visitingcard.R;

import static com.community.jboss.visitingcard.about.AboutActivity.PREF_DARK_THEME;


public class OrganisationFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About Jboss");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0D47A1")));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        View tmpview = inflater.inflate(R.layout.fragment_organisation, container, false);
        if (!useDarkTheme) {
            tmpview.findViewById(R.id.organisation_holder).setBackground(new ColorDrawable(Color.parseColor("#E3F2FD")));

        }

        MaterialCardView githubbutton = tmpview.findViewById(R.id.github_button);
        MaterialCardView gitterbutton = tmpview.findViewById(R.id.gitterbutton);
        MaterialCardView twitterbutton = tmpview.findViewById(R.id.twitter_button);
        MaterialCardView facebookbutton = tmpview.findViewById(R.id.facebook_button);
        MaterialCardView instagrambutton = tmpview.findViewById(R.id.wikipedia_button);
        MaterialCardView linkedinbutton = tmpview.findViewById(R.id.linkedin_button);
        MaterialCardView versioncard = tmpview.findViewById(R.id.version_card_view);
        MaterialCardView jbosscard = tmpview.findViewById(R.id.jboss_card_view);
        githubbutton.setOnClickListener(this);
        gitterbutton.setOnClickListener(this);
        twitterbutton.setOnClickListener(this);
        facebookbutton.setOnClickListener(this);
        instagrambutton.setOnClickListener(this);
        linkedinbutton.setOnClickListener(this);
        if (useDarkTheme) {
            githubbutton.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            gitterbutton.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            twitterbutton.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            facebookbutton.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            instagrambutton.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            linkedinbutton.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            versioncard.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            jbosscard.setCardBackgroundColor(getResources().getColor(R.color.darkcardcolour));
            ((AppCompatTextView) tmpview.findViewById(R.id.jbossdescription)).setTextColor(Color.WHITE);
        }
        return tmpview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook_button:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.facebook_url)));
                startActivity(intent);
                break;
            case R.id.wikipedia_button:
                Intent insta_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.wikipedia_url)));
                startActivity(insta_intent);
                break;
            case R.id.twitter_button:
                Intent twitt_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.twitter_url)));
                startActivity(twitt_intent);
                break;
            case R.id.github_button:
                Intent github_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.github_url)));
                startActivity(github_intent);
                break;
            case R.id.gitterbutton:
                Intent gitter_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.gitter_url)));
                startActivity(gitter_intent);
                break;
            case R.id.linkedin_button:
                Intent linkedin_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.linkedin_url)));
                startActivity(linkedin_intent);
                break;
            default:
                Log.e("LeadManagement", "Fatal Error");
                break;
        }
    }

    @Override
    public void onDestroy() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2962FF")));
        super.onDestroy();
    }
}
