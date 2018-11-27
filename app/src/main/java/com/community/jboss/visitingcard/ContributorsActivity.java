package com.community.jboss.visitingcard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.community.jboss.visitingcard.networking.HttpDude;
import com.community.jboss.visitingcard.model.Contributor;
import com.community.jboss.visitingcard.util.ItemArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContributorsActivity extends AppCompatActivity {

    private String TAG = ContributorsActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contributors JSON
    private static String url;

    private static ArrayList<Contributor> contributors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contributors);
        contributors = new ArrayList<>();
        url = "https://api.github.com/repos/JBossOutreach/visiting-card-android/contributors";
        lv = findViewById(R.id.contributors);
        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(ContributorsActivity.this, R.layout.contrib_list_item, contributors);
        lv.setAdapter(itemArrayAdapter);
        new GetContributors().execute();

    }

    private class GetContributors extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ContributorsActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpDude sh = new HttpDude();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            System.out.println(jsonStr);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray reps = new JSONArray(jsonStr);
                    // looping through All Repositories
                    for (int i = 0; i < reps.length(); i++) {
                        JSONObject c = reps.getJSONObject(i);

                        String avatarUrl = c.getString("avatar_url");
                        String name = c.getString("login");
                        int commits = c.getInt("contributions");
                        Contributor contibutor = new Contributor();
                        contibutor.setAvatarUrl(avatarUrl);
                        contibutor.setName(name);
                        contibutor.setContibutions(commits);

                        // adding repo to repos list
                        contributors.add(contibutor);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(ContributorsActivity.this, R.layout.contrib_list_item, contributors);
            lv.setAdapter(itemArrayAdapter);
        }

    }
}
