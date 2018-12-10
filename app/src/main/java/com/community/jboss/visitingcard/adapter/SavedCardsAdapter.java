package com.community.jboss.visitingcard.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.ProfileActivity;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.model.CardListItem;
import com.community.jboss.visitingcard.model.CardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;

import static com.community.jboss.visitingcard.utils.Constants.currentDatabase;
import static com.community.jboss.visitingcard.utils.Constants.currentUser;

public class SavedCardsAdapter extends ArrayAdapter<CardListItem> {

    private int listItemLayout;
    public SavedCardsAdapter(Context context, int layoutId, ArrayList<CardListItem> itemList) {
        super(context, layoutId, itemList);
        listItemLayout = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CardListItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(listItemLayout, parent, false);

            viewHolder.avatar = convertView.findViewById(R.id.li_image);
            viewHolder.name = convertView.findViewById(R.id.li_name);
            viewHolder.email = convertView.findViewById(R.id.li_email);
            viewHolder.view = convertView.findViewById(R.id.li_btn);
            convertView.setTag(viewHolder); // view lookup cache stored in tag
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        Glide.with(getContext()).load(item.getAvatarUrl()).into(viewHolder.avatar);
        viewHolder.name.setText(item.getName());
        viewHolder.email.setText(item.getEmail());
        viewHolder.view.setText("VIEW");
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                View v = View.inflate(getContext(), R.layout.visiting_card, null);
                TextView profileName = v.findViewById(R.id.tv_profile_name);
                TextView profilePhone = v.findViewById(R.id.tv_profile_phone);
                TextView profileMail = v.findViewById(R.id.tv_profile_mail);
                TextView profileLinkedin = v.findViewById(R.id.tv_profile_linkedin);
                TextView profileTwitter = v.findViewById(R.id.tv_profile_twitter);
                TextView profileGithub = v.findViewById(R.id.tv_profile_github);
                CircularImageView profilePicture = v.findViewById(R.id.profile_picture);
                SparkButton addFav = v.findViewById(R.id.add_fav);
                FloatingActionButton edit = v.findViewById(R.id.fab_edit);
                edit.hide();
                currentDatabase.getReference().child(item.getUid()).getDatabase().getReference().addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        profileName.setText(dataSnapshot.child(item.getUid()).child("name").getValue().toString());
                        profilePhone.setText(dataSnapshot.child(item.getUid()).child("phone").getValue().toString());
                        profileMail.setText(dataSnapshot.child(item.getUid()).child("email").getValue().toString());
                        profileLinkedin.setText(dataSnapshot.child(item.getUid()).child("linkedin").getValue().toString());
                        profileTwitter.setText(dataSnapshot.child(item.getUid()).child("twitter").getValue().toString());
                        profileGithub.setText(dataSnapshot.child(item.getUid()).child("github").getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
                addFav.setEventListener(new SparkEventListener() {
                    @Override
                    public void onEvent(ImageView button, boolean buttonState) {
                        if(buttonState){
                            addToFavs(item.getUid(), currentUser.getUid());
                        } else {
                            currentDatabase.getReference().child(currentUser.getUid()).child("favCards").child(item.getUid()).removeValue();
                        }
                    }

                    @Override
                    public void onEventAnimationEnd(ImageView button, boolean buttonState) {

                    }

                    @Override
                    public void onEventAnimationStart(ImageView button, boolean buttonState) {

                    }
                });
                b.setView(v);
                b.create().show();
            }

        });
        // Return the completed view to render on screen
        return convertView;
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
                        Toast.makeText(getContext(), "Added to favs", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    private static class ViewHolder {
        CircularImageView avatar;
        TextView name, email;
        Button view;
    }
}