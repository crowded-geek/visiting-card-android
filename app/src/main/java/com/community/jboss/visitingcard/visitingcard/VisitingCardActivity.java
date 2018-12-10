package com.community.jboss.visitingcard.visitingcard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;
import com.community.jboss.visitingcard.about.AboutActivity;
import com.community.jboss.visitingcard.maps.MapsActivity;
import com.community.jboss.visitingcard.model.CardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.UUID;


import android.widget.EditText;
import android.widget.Toast;

import retrofit2.http.Url;

import static com.community.jboss.visitingcard.utils.Constants.currentDatabase;
import static com.community.jboss.visitingcard.utils.Constants.currentUser;
import static com.community.jboss.visitingcard.utils.Constants.storageReference;
import static com.community.jboss.visitingcard.utils.Constants.userPhotoUrl;

public class VisitingCardActivity extends AppCompatActivity {

    private CircularImageView profile_img;

    public static final String PREF_DARK_THEME = "dark_theme";

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    private EditText profileName, profilePhone, profileMail, profileLinkedin, profileTwitter, profileGithub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_visiting_card);

	    profile_img=findViewById(R.id.profile_img);
        userPhotoUrl = currentUser.getPhotoUrl();
        Glide.with(getApplicationContext()).load(userPhotoUrl).into(profile_img);

        profileName = findViewById(R.id.profile_name);
        profilePhone = findViewById(R.id.profile_phone);
        profileMail = findViewById(R.id.profile_mail);
        profileLinkedin = findViewById(R.id.profile_linkedin);
        profileTwitter = findViewById(R.id.profile_twitter);
        profileGithub = findViewById(R.id.profile_github);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(currentUser.getUid())) {
                    profileName.setText(snapshot.child(currentUser.getUid()).child("name").getValue().toString());
                    profilePhone.setText(snapshot.child(currentUser.getUid()).child("phone").getValue().toString());
                    profileMail.setText(snapshot.child(currentUser.getUid()).child("email").getValue().toString());
                    profileLinkedin.setText(snapshot.child(currentUser.getUid()).child("linkedin").getValue().toString());
                    profileTwitter.setText(snapshot.child(currentUser.getUid()).child("twitter").getValue().toString());
                    profileGithub.setText(snapshot.child(currentUser.getUid()).child("github").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // TODO: Add a ImageView and a number of EditText to get his/her Visiting Card details (Currently authenticated User)

        // TODO: Add profileImage, Name, Email, PhoneNumber, Github, LinkedIn & Twitter Fields.

        // TODO: Clicking the ImageView should invoke an implicit intent to take an image using camera / pick an image from the Gallery.

        // TODO: Align FAB to Bottom Right and replace it's icon with a SAVE icon
        // TODO: On Click on FAB should make a network call to store the entered information in the cloud using POST method(Do this in NetworkUtils class)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Save the details?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                saveInfo();
                            }
                        }).show();
            }
        });

    }

    private void saveInfo() {

        currentDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(currentUser.getUid())){
                    currentDatabase.getReference().child(currentUser.getUid()).child("name").setValue(profileName.getText().toString());
                    currentDatabase.getReference().child(currentUser.getUid()).child("phone").setValue(profilePhone.getText().toString());
                    currentDatabase.getReference().child(currentUser.getUid()).child("email").setValue(profileMail.getText().toString());
                    currentDatabase.getReference().child(currentUser.getUid()).child("linkedin").setValue(profileLinkedin.getText().toString());
                    currentDatabase.getReference().child(currentUser.getUid()).child("twitter").setValue(profileTwitter.getText().toString());
                    currentDatabase.getReference().child(currentUser.getUid()).child("github").setValue(profileGithub.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(VisitingCardActivity.this, MapsActivity.class));
                        }
                    });
                } else {
                    CardModel cardModel = new CardModel(profileName.getText().toString(),
                            profilePhone.getText().toString(),
                            profileMail.getText().toString(),
                            profileLinkedin.getText().toString(),
                            profileTwitter.getText().toString(),
                            profileGithub.getText().toString(),
                            currentUser.getPhotoUrl().toString());
                    currentDatabase.getReference().child(currentUser.getUid()).setValue(cardModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(VisitingCardActivity.this, MapsActivity.class));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            filePath = data.getData();
            uploadImage(filePath);
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
                boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
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
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage(Uri filePath){
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            changeProfileUrl(taskSnapshot.getDownloadUrl());
                            Glide.with(getApplicationContext()).load(taskSnapshot.getDownloadUrl()).into(profile_img);
                            Toast.makeText(VisitingCardActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(VisitingCardActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void changeProfileUrl(Uri downloadUrl) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(downloadUrl)
                .build();
        currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("VisitingCardActivity", "User profile updated.");
                        }
                    }
                });
    }
}
