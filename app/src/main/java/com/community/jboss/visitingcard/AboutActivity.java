package com.community.jboss.visitingcard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        LinearLayout contributors = findViewById(R.id.btm_contrib);
        LinearLayout license = findViewById(R.id.btm_license);
        LinearLayout github = findViewById(R.id.social_github);
        LinearLayout twitter = findViewById(R.id.social_twitter);
        LinearLayout gitter = findViewById(R.id.social_gitter);
        contributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContributors();
            }
        });
        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLink("https://raw.githubusercontent.com/JBossOutreach/visiting-card-android/master/LICENSE.md");
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLink("https://github.com/JBossOutreach/visiting-card-android");
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLink("https://twitter.com/jboss");
            }
        });
        gitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLink("https://gitter.im/JBossOutreach");
            }
        });
    }

    private void showContributors() {
        Intent intent = new Intent(AboutActivity.this, ContributorsActivity.class);
        startActivity(intent);
    }

    private void openLink(String url){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }
}
