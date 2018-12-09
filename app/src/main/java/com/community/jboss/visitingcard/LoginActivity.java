package com.community.jboss.visitingcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.community.jboss.visitingcard.introscreens.SliderActivity;
import com.community.jboss.visitingcard.visitingcard.VisitingCardActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import static com.community.jboss.visitingcard.utils.Constants.currentUser;
import static com.community.jboss.visitingcard.utils.Constants.storage;
import static com.community.jboss.visitingcard.utils.Constants.currentDatabase;
import static com.community.jboss.visitingcard.utils.Constants.storageReference;
import static com.community.jboss.visitingcard.utils.Constants.userPhotoUrl;
import static com.community.jboss.visitingcard.utils.Constants.userName;
import static com.community.jboss.visitingcard.utils.Constants.userEmail;

public class LoginActivity extends AppCompatActivity {

    private final String PREFERENCES_NAME = "SharedPreferences";
    private final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 23602;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: The Slider should appear only on when the app is launched for the first time. - Add appropriate conditions for that.
        // Here we check if this is a first time launch
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        // Checking if the app was opened earlier, true will be default value showing this is the first time
        boolean firstTimeUserLogin = sharedPreferences.getBoolean("firstTime", true);
        if (firstTimeUserLogin) {
            Log.d(TAG, "Opening App For First Time");

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, SliderActivity.class);
            startActivity(intent);

            finish();
        }
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // Choose authentication providers
        if(firebaseAuth.getCurrentUser()==null) {
            // Create and launch sign-in intent
            startActivityForResult(AuthUI.getInstance().
                    createSignInIntentBuilder().
                    setIsSmartLockEnabled(true).
                    build(), RC_SIGN_IN);
        } else {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            currentDatabase = FirebaseDatabase.getInstance();
            userName = currentUser.getDisplayName();
            userEmail = currentUser.getEmail();
            userPhotoUrl = currentUser.getPhotoUrl();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            Intent toVisitingCard = new Intent(LoginActivity.this, VisitingCardActivity.class);
            startActivity(toVisitingCard);
        }

        // TODO: Perform Firebase Authentication using Email Auth or Google Sign-in.

        // TODO: Have a Sign-in with google Button.

        //TODO: Move the FAB to bottom Right and replace it's icon with a check icon
    }
}
