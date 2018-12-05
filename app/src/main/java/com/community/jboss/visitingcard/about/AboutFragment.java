package com.community.jboss.visitingcard.about;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.community.jboss.visitingcard.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;


public class AboutFragment extends Fragment implements View.OnClickListener {

    public static final String PREF_DARK_THEME = "dark_theme";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        View tmpview = inflater.inflate(R.layout.fragment_about, container, false);
        tmpview.findViewById(R.id.licensebutton).setOnClickListener(this);
        tmpview.findViewById(R.id.contributorbutton).setOnClickListener(this);
        tmpview.findViewById(R.id.contact_us_view).setOnClickListener(this);
        tmpview.findViewById(R.id.jbossbutton).setOnClickListener(this);
        tmpview.findViewById(R.id.librariesbutton).setOnClickListener(this);
        if (useDarkTheme) {
            ((AppCompatTextView) tmpview.findViewById(R.id.app_name_textview)).setTextColor(Color.WHITE);
            ((AppCompatTextView) tmpview.findViewById(R.id.license_text_view)).setTextColor(Color.WHITE);
            ((AppCompatTextView) tmpview.findViewById(R.id.contributor_text_view)).setTextColor(Color.WHITE);
            ((AppCompatTextView) tmpview.findViewById(R.id.library_text_view)).setTextColor(Color.WHITE);
            ((AppCompatTextView) tmpview.findViewById(R.id.contactus_text_view)).setTextColor(Color.WHITE);
            ((CardView) tmpview.findViewById(R.id.version_card_view)).setCardBackgroundColor(Color.parseColor("#212121"));
            ((CardView) tmpview.findViewById(R.id.licensebutton)).setCardBackgroundColor(Color.parseColor("#212121"));
            ((CardView) tmpview.findViewById(R.id.contributorbutton)).setCardBackgroundColor(Color.parseColor("#212121"));
            ((CardView) tmpview.findViewById(R.id.jbossbutton)).setCardBackgroundColor(Color.parseColor("#212121"));
            ((CardView) tmpview.findViewById(R.id.contact_us_view)).setCardBackgroundColor(Color.parseColor("#212121"));
            ((CardView) tmpview.findViewById(R.id.librariesbutton)).setCardBackgroundColor(Color.parseColor("#212121"));
            ((AppCompatImageView) tmpview.findViewById(R.id.license_image_view)).setImageResource(R.drawable.round_copyright_white_48);
            ((AppCompatImageView) tmpview.findViewById(R.id.contributor_image_view)).setImageResource(R.drawable.round_group_white_48);
            ((AppCompatImageView) tmpview.findViewById(R.id.libraries_image_view)).setImageResource(R.drawable.round_book_white_48);
            ((AppCompatImageView) tmpview.findViewById(R.id.contactus_image_view)).setImageResource(R.drawable.round_forum_white_48);
            ((LinearLayout) tmpview.findViewById(R.id.about_header_bg)).setBackgroundColor(Color.parseColor("#424242"));
        }
        return tmpview;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.licensebutton:
                fragment = new LicenseFragment();
                break;
            case R.id.jbossbutton:
                fragment = new OrganisationFragment();
                break;
            case R.id.contributorbutton:
                fragment = new ContributorFragment();
                break;
            case R.id.contact_us_view:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.gitter_url)));
                startActivity(intent);
                break;
            case R.id.librariesbutton:
                startActivity(new Intent(getContext(), OssLicensesMenuActivity.class));
                break;
            default:
                Log.e("LeadManagement", "Fatal Error");
                break;
        }
        if (fragment != null) {
            getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_in_left).replace(R.id.frame_about, fragment).commit();
            AboutActivity.state = true;
        }
    }
}
