package com.example.gazeteyazarlari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Yazarlar extends AppCompatActivity {
    private String gazeteUrl;
    private ArrayList<Model> list = new ArrayList<>();
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazarlar);

        gazeteUrl = getIntent().getExtras().getString("URL");
        recyclerView = findViewById(R.id.yazarlarlist);
        new YazarlarTask(Yazarlar.this, gazeteUrl, list, recyclerView).execute();

    }
}