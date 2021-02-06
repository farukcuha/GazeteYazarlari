package com.example.yazarlar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class YaziActivity extends AppCompatActivity {
    private ImageView yazarResim;
    private TextView yazarAd, yaziTarih, yazi;
    private String yaziurl, siteurl;
    private String str_yazaradi, str_yazarresim, str_yazi, str_tarih;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazi);

        yazarResim = findViewById(R.id.yazarresim);
        yazarAd = findViewById(R.id.yazaradi);
        yaziTarih = findViewById(R.id.yazitarih);
        yazi = findViewById(R.id.yazi);
        progressBar = findViewById(R.id.progresbar);


        yaziurl = getIntent().getExtras().getString("yaziurl");
        siteurl = getIntent().getExtras().getString("siteurl");
        str_yazaradi = getIntent().getExtras().getString("yazaradi");
        str_yazarresim = getIntent().getExtras().getString("resim");
        str_yazi = getIntent().getExtras().getString("yazi");
        str_tarih = getIntent().getExtras().getString("tarih");


        Glide.with(getApplicationContext()).load(str_yazarresim).centerCrop().listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(yazarResim);

        yazarAd.setText(str_yazaradi);
        yaziTarih.setText(str_tarih);
        yazi.setText(str_yazi);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yazimenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.paylas:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, yaziurl);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, null));
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
