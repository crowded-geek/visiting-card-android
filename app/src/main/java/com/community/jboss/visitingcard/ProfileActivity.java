package com.community.jboss.visitingcard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.model.CardModel;
import com.community.jboss.visitingcard.visitingcard.VisitingCardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import static com.community.jboss.visitingcard.utils.Constants.currentDatabase;
import static com.community.jboss.visitingcard.utils.Constants.currentUser;
import static com.community.jboss.visitingcard.utils.Constants.userPhotoUrl;

public class ProfileActivity extends AppCompatActivity {

    Intent i;
    SparkButton addFav;
    TextView profileName, profilePhone, profileMail, profileLinkedin, profileTwitter, profileGithub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);
        i = getIntent();
        profileName = findViewById(R.id.tv_profile_name);
        profilePhone = findViewById(R.id.tv_profile_phone);
        profileMail = findViewById(R.id.tv_profile_mail);
        profileLinkedin = findViewById(R.id.tv_profile_linkedin);
        profileTwitter = findViewById(R.id.tv_profile_twitter);
        profileGithub = findViewById(R.id.tv_profile_github);
        CircularImageView profilePicture =findViewById(R.id.profile_picture);
        addFav = findViewById(R.id.add_fav);
        addFav.setVisibility(View.GONE);
        userPhotoUrl = currentUser.getPhotoUrl();
        Glide.with(getApplicationContext()).load(userPhotoUrl).into(profilePicture);
        FloatingActionButton edit = findViewById(R.id.fab_edit);
        if(i.getStringExtra("cardData").equals("null0")) {
            setCardData(currentUser.getUid());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProfileActivity.this, VisitingCardActivity.class));
                }
            });
        } else {
            setCardData(i.getStringExtra("cardData"));
            addFav.setVisibility(View.VISIBLE);
            addFav.setEventListener(new SparkEventListener() {
                @Override
                public void onEvent(ImageView button, boolean buttonState) {
                    if(buttonState){
                        addToFavs(i.getStringExtra("cardData"), currentUser.getUid());
                    } else {
                        currentDatabase.getReference().child(currentUser.getUid()).child("favCards").child(i.getStringExtra("cardData")).removeValue();
                    }
                }

                @Override
                public void onEventAnimationEnd(ImageView button, boolean buttonState) {

                }

                @Override
                public void onEventAnimationStart(ImageView button, boolean buttonState) {

                }
            });
            edit.setImageDrawable(getResources().getDrawable(R.drawable.baseline_done_white_36));
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToSaved(i.getStringExtra("cardData"));
                }
            });
        }
    }

    private void addToFavs(String cardData, String uid) {
        currentDatabase.getReference().child(cardData).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CardModel model = new CardModel();
                model.setName(dataSnapshot.child("name").getValue().toString());
                model.setPhone(dataSnapshot.child("phone").getValue().toString());
                model.setEmail(dataSnapshot.child("email").getValue().toString());
                model.setLinkedin(dataSnapshot.child("linkedin").getValue().toString());
                model.setTwitter(dataSnapshot.child("twitter").getValue().toString());
                model.setGithub(dataSnapshot.child("github").getValue().toString());
                model.setAvatarUrl(dataSnapshot.child("avatarUrl").getValue().toString());
                currentDatabase.getReference().child(uid).child("favCards").child(cardData).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this, "Added to favs", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addToSaved(String cardData) {
        currentDatabase.getReference().child(cardData).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CardModel model = new CardModel();
                model.setName(dataSnapshot.child("name").getValue().toString());
                model.setPhone(dataSnapshot.child("phone").getValue().toString());
                model.setEmail(dataSnapshot.child("email").getValue().toString());
                model.setLinkedin(dataSnapshot.child("linkedin").getValue().toString());
                model.setTwitter(dataSnapshot.child("twitter").getValue().toString());
                model.setGithub(dataSnapshot.child("github").getValue().toString());
                model.setAvatarUrl(dataSnapshot.child("avatarUrl").getValue().toString());
                currentDatabase.getReference().child(currentUser.getUid()).child("savedCards").child(cardData).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void setCardData(String uid) {
        currentDatabase.getReference().child(uid).getDatabase().getReference().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileName.setText(dataSnapshot.child(uid).child("name").getValue().toString());
                profilePhone.setText(dataSnapshot.child(uid).child("phone").getValue().toString());
                profileMail.setText(dataSnapshot.child(uid).child("email").getValue().toString());
                profileLinkedin.setText(dataSnapshot.child(uid).child("linkedin").getValue().toString());
                profileTwitter.setText(dataSnapshot.child(uid).child("twitter").getValue().toString());
                profileGithub.setText(dataSnapshot.child(uid).child("github").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
