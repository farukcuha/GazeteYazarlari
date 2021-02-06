package com.example.gazeteyazarlari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import de.cketti.mailto.EmailIntentBuilder;

public class MainActivity extends AppCompatActivity {
    private ImageView sabah, haberturk, hurriyet, milliyet, sozcu, takvim, turkiye, yenisafak, akit, karar;
    private Intent intent;
    private static final String URL = "URL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null){
            Toast.makeText(MainActivity.this, "İnternet Bağlantınısı Yok", Toast.LENGTH_SHORT).show();
        }


        sabah = findViewById(R.id.sabah);
        haberturk = findViewById(R.id.haberturk);
        hurriyet = findViewById(R.id.hurriyet);
        milliyet = findViewById(R.id.milliyet);
        sozcu = findViewById(R.id.sozcu);
        takvim = findViewById(R.id.takvim);
        turkiye = findViewById(R.id.turkiye);
        yenisafak = findViewById(R.id.yenisafak);
        akit = findViewById(R.id.akit);
        karar = findViewById(R.id.karar);

        intent = new Intent(MainActivity.this, Yazarlar.class);



    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sabah:
                intent.putExtra(URL, "https://www.sabah.com.tr/yazarlar");
                startActivity(intent);
                break;
            case R.id.haberturk:
                intent.putExtra(URL, "https://www.haberturk.com/htyazarlar");
                startActivity(intent);
                break;
            case R.id.hurriyet:
                intent.putExtra(URL, "https://www.hurriyet.com.tr/yazarlar/");
                startActivity(intent);
                break;
            case R.id.karar:
                intent.putExtra(URL, "https://www.karar.com/yazarlar");
                startActivity(intent);
                break;
            case R.id.sozcu:
                intent.putExtra(URL, "https://www.sozcu.com.tr/kategori/yazarlar/");
                startActivity(intent);
                break;
            case R.id.milliyet:
                intent.putExtra(URL, "https://www.milliyet.com.tr/yazarlar/");
                startActivity(intent);
                break;
            case R.id.turkiye:
                intent.putExtra(URL, "https://www.turkiyegazetesi.com.tr/yazarlar");
                startActivity(intent);
                break;
            case R.id.yenisafak:
                intent.putExtra(URL, "https://www.yenisafak.com/yazarlar/bugun-yazanlar");
                startActivity(intent);
                break;
            case R.id.akit:
                intent.putExtra(URL, "https://www.yeniakit.com.tr/yazarlar/");
                startActivity(intent);
                break;
            case R.id.takvim:
                intent.putExtra(URL, "https://www.takvim.com.tr/yazarlar");
                startActivity(intent);
                break;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.gelistirici:
                Intent intent  = new Intent(MainActivity.this, MyIntro.class);
                startActivity(intent);
                break;
            case R.id.geribildirim:

                break;

            case R.id.oneri:
                EmailIntentBuilder.from(getApplicationContext())
                        .to("ahmetfarukcuha@gmail.com")
                        .start();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}