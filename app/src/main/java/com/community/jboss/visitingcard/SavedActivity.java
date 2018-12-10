package com.community.jboss.visitingcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.community.jboss.visitingcard.adapter.SavedCardsAdapter;
import com.community.jboss.visitingcard.model.CardListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.community.jboss.visitingcard.utils.Constants.currentDatabase;
import static com.community.jboss.visitingcard.utils.Constants.currentUser;

public class SavedActivity extends AppCompatActivity {

    ArrayList<CardListItem> savedCards;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        lv = findViewById(R.id.savedcard_list);
        savedCards = new ArrayList<>();
        intiData();
    }

    private void intiData(){
        currentDatabase.getReference().child(currentUser.getUid()).child("savedCards").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    CardListItem cm = new CardListItem(
                            d.getKey(),
                            d.child("avatarUrl").getValue().toString(),
                            d.child("name").getValue().toString(),
                            d.child("email").getValue().toString()
                    );
                    savedCards.add(cm);
                }
                SavedCardsAdapter itemArrayAdapter = new SavedCardsAdapter(SavedActivity.this, R.layout.nearby_card_item, savedCards);
                lv.setAdapter(itemArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
