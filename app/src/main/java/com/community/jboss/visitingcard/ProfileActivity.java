package com.community.jboss.visitingcard;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.model.CardModel;
import com.community.jboss.visitingcard.visitingcard.VisitingCardActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import static com.community.jboss.visitingcard.utils.Constants.currentDatabase;
import static com.community.jboss.visitingcard.utils.Constants.currentUser;
import static com.community.jboss.visitingcard.utils.Constants.userPhotoUrl;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);
        TextView profileName = findViewById(R.id.tv_profile_name);
        TextView profilePhone = findViewById(R.id.tv_profile_phone);
        TextView profileMail = findViewById(R.id.tv_profile_mail);
        TextView profileLinkedin = findViewById(R.id.tv_profile_linkedin);
        TextView profileTwitter = findViewById(R.id.tv_profile_twitter);
        TextView profileGithub = findViewById(R.id.tv_profile_github);
        CircularImageView profilePicture =findViewById(R.id.profile_picture);
        userPhotoUrl = currentUser.getPhotoUrl();
        Glide.with(getApplicationContext()).load(userPhotoUrl).into(profilePicture);
        currentDatabase.getReference().child(currentUser.getUid()).getDatabase().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileName.setText(dataSnapshot.child(currentUser.getUid()).child("name").getValue().toString());
                profilePhone.setText(dataSnapshot.child(currentUser.getUid()).child("phone").getValue().toString());
                profileMail.setText(dataSnapshot.child(currentUser.getUid()).child("email").getValue().toString());
                profileLinkedin.setText(dataSnapshot.child(currentUser.getUid()).child("linkedin").getValue().toString());
                profileTwitter.setText(dataSnapshot.child(currentUser.getUid()).child("twitter").getValue().toString());
                profileGithub.setText(dataSnapshot.child(currentUser.getUid()).child("github").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton edit = findViewById(R.id.fab_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, VisitingCardActivity.class));
            }
        });
    }
}
