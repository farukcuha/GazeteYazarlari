package com.example.gazeteyazarlari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import de.cketti.mailto.EmailIntentBuilder;

public class MyIntro extends AppCompatActivity implements View.OnClickListener {
    private ImageButton github, twitter, google;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_intro);

        github = findViewById(R.id.githublink);
        twitter = findViewById(R.id.twitterlink);
        google = findViewById(R.id.googlelink);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.githublink:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/farukcuha"));
                startActivity(intent);
                break;
            case R.id.twitterlink:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com/faruk__cuha"));
                startActivity(intent);
                break;

            case R.id.googlelink:
                EmailIntentBuilder.from(MyIntro.this)
                        .to("ahmetfarukcuha@gmail.com")
                        .start();
                break;
        }
    }
}