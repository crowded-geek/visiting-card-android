package com.community.jboss.visitingcard.maps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.community.jboss.visitingcard.FavoriteActivity;
import com.community.jboss.visitingcard.ProfileActivity;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SavedActivity;
import com.community.jboss.visitingcard.about.AboutActivity;
import com.community.jboss.visitingcard.adapter.ItemArrayAdapter;
import com.community.jboss.visitingcard.model.CardListItem;
import com.community.jboss.visitingcard.model.CardModel;
import com.community.jboss.visitingcard.visitingcard.ViewVisitingCard;
import com.community.jboss.visitingcard.visitingcard.VisitingCardActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.community.jboss.visitingcard.utils.Constants.currentUser;
import static com.community.jboss.visitingcard.utils.Constants.userPhotoUrl;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ListView list;
    private ArrayList<CardListItem> nearByCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        View bottomSheet = findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        // TODO: Replace the TextView with a ListView containing list of Visiting cards in that locality using geo-fencing

        // TODO: List item click should result in launching of ViewVisitingCard Acitivity with the info of the tapped Visiting card.

        list = findViewById(R.id.list_near_cards);
        nearByCards = new ArrayList<>();
        addDataToList();
        Button profile = findViewById(R.id.btn_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(MapsActivity.this, ProfileActivity.class);
                profileIntent.putExtra("cardData", "null0");
                startActivity(profileIntent);
            }
        });
        Button favCards = findViewById(R.id.btn_fav);
        favCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MapsActivity.this, FavoriteActivity.class);
                startActivity(in);
            }
        });
        Button savedCards = findViewById(R.id.btn_saved);
        savedCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, SavedActivity.class);
                startActivity(i);
            }
        });
        //TODO: Create Custom pins for the selected location
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //TODO: Implement geo-fencing(NOT AS A WHOLE) just visual representation .i.e., a circle of an arbitrary radius with the PIN being the centre of it.
        //TODO: Make the circle color as @color/colorAccent
    }

    private void addDataToList() {
        CardListItem cM = new CardListItem(currentUser.getUid(), userPhotoUrl.toString(), "crowded.geek", "whatever@whatevr.com");
        for(int i=0;i<10;i++){
            nearByCards.add(cM);
        }
        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(MapsActivity.this, R.layout.nearby_card_item, nearByCards);
        list.setAdapter(itemArrayAdapter);
    }


    // TODO: Replace the stating location with user's current location.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addCircle(new CircleOptions().center(sydney).radius(3280).fillColor(getResources().getColor(R.color.colorAccent)));

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"))
                .setIcon(
                        BitmapDescriptorFactory.fromResource(R.drawable.custom_pin)
                );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
