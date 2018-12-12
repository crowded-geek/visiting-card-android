package com.community.jboss.visitingcard.visitingcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.community.jboss.visitingcard.about.AboutActivity;
import com.community.jboss.visitingcard.maps.MapsActivity;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;



import android.content.DialogInterface;
import android.graphics.Bitmap;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;
import com.community.jboss.visitingcard.maps.MapsActivity;
import java.io.IOException;
import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;
import android.widget.ImageButton;

public class VisitingCardActivity extends AppCompatActivity {

    private ImageButton profile_img;

    public static final String PREF_DARK_THEME = "dark_theme";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);

	profile_img=findViewById(R.id.profile_img);

        // TODO: Add a ImageView and a number of EditText to get his/her Visiting Card details (Currently authenticated User)

        // TODO: Add profileImage, Name, Email, PhoneNumber, Github, LinkedIn & Twitter Fields.

        // TODO: Clicking the ImageView should invoke an implicit intent to take an image using camera / pick an image from the Gallery.

        // TODO: Align FAB to Bottom Right and replace it's icon with a SAVE icon
        // TODO: On Click on FAB should make a network call to store the entered information in the cloud using POST method(Do this in NetworkUtils class)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Proceed to Maps Activity", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent toVisitingCard = new Intent(VisitingCardActivity.this, MapsActivity.class);
                                startActivity(toVisitingCard);
                            }
                        }).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE || requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE) {
            Bitmap pickedPhoto = null;
            try {
                pickedPhoto = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();
            } catch (IOException e) {
                e.printStackTrace();
            }
            profile_img.setImageBitmap(pickedPhoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(VisitingCardActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent aboutIntent = new Intent(VisitingCardActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.darktheme:
                SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean useDarkTheme = preferences.getBoolean(AboutActivity.PREF_DARK_THEME, false);
                if(!useDarkTheme)
                {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putBoolean(PREF_DARK_THEME, true);
                    editor.apply();
                }
                else {SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putBoolean(PREF_DARK_THEME, false);
                    editor.apply();}
                Intent restarter = getIntent();
                finish();
                startActivity(restarter);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void select_img(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select image source")
                .setCancelable(false)
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EZPhotoPickConfig config = new EZPhotoPickConfig();
                        config.photoSource = PhotoSource.GALLERY; // or PhotoSource.CAMERA
                        config.isAllowMultipleSelect = false; // only for GALLERY pick and API >18
                        EZPhotoPick.startPhotoPickActivity(VisitingCardActivity.this, config);
                    }
                })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EZPhotoPickConfig config = new EZPhotoPickConfig();
                        config.photoSource = PhotoSource.CAMERA; // or PhotoSource.CAMERA
                        config.isAllowMultipleSelect = false; // only for GALLERY pick and API >18
                        EZPhotoPick.startPhotoPickActivity(VisitingCardActivity.this, config);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
