package com.community.jboss.visitingcard.VisitingCard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.community.jboss.visitingcard.Maps.MapsActivity;
import com.community.jboss.visitingcard.Networking.CardMaker;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitingCardActivity extends AppCompatActivity {

    private EditText etName, etEmail, etNumber;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);
        card = new Card();
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etNumber = findViewById(R.id.et_number);

        // TODO: Add a ImageView and a number of EditText to get his/her Visiting Card details (Currently authenticated User)

        // TODO: Add profileImage, Name, Email, PhoneNumber, Github, LinkedIn & Twitter Fields.

        // TODO: Clicking the ImageView should invoke an implicit intent to take an image using camera / pick an image from the Gallery.

        // TODO: Align FAB to Bottom Right and replace it's icon with a SAVE icon
        // TODO: On Click on FAB should make a network call to store the entered information in the cloud using POST method(Do this in NetworkUtils class)

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setName(etName.getText().toString());
                card.setEmail(etEmail.getText().toString());
                card.setNumber(etNumber.getText().toString());
                saveCard(card);
            }
        });
    }
    private void saveCard(Card card) {
        Retrofit.Builder b = new Retrofit.Builder()
                .baseUrl("SERVER_URL")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = b.build();
        CardMaker cardMaker = retrofit.create(CardMaker.class);
        Call<Card> call = cardMaker.postCard(card);
        call.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                Log.d("VisitingCardActivity","Post Request Succeeded");
                Toast.makeText(VisitingCardActivity.this, "Succeeded", Toast.LENGTH_SHORT).show();
                Snackbar.make(null, "Proceed to MapsActivity?", Snackbar.LENGTH_SHORT).setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent toVisitingCard = new Intent(VisitingCardActivity.this, MapsActivity.class);
                        startActivity(toVisitingCard);
                    }
                });
            }
            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.d("VisitingCardActivity","Post Request Succeeded");
                Toast.makeText(VisitingCardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                Snackbar.make(null, "Proceed to MapsActivity?", Snackbar.LENGTH_SHORT).setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent toVisitingCard = new Intent(VisitingCardActivity.this, MapsActivity.class);
                        startActivity(toVisitingCard);
                    }
                });
            }
        });
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}