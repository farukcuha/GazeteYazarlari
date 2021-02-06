package com.example.yazarlar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class YazarlarAdapter extends RecyclerView.Adapter<YazarlarAdapter.Holder> {
    private List<Model> list;
    private Context context;

    public YazarlarAdapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public YazarlarAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yazarlarlisteitem, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YazarlarAdapter.Holder holder, int position) {
        Model model = list.get(position);
        holder.yazarAd.setText(model.getYazarAd());
        holder.yaziBaslik.setText(model.getYazarBaslik());
        Glide.with(holder.itemView.getContext()).load(model.getYazarResim())
                .transform(new CenterCrop(), new RoundedCorners(30)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);

                return false;
            }
        }).into(holder.yazarResim);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), YaziActivity.class);
                intent.putExtra("yaziurl", model.getYaziUrl());

                intent.putExtra("yazaradi", model.getYazarAd());
                intent.putExtra("resim", model.getYazarResim());
                intent.putExtra("yazi", model.getYazi());
                intent.putExtra("tarih", model.getTarih());
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView yazarResim;
        TextView yazarAd, yaziBaslik;
        ProgressBar progressBar;

        public Holder(@NonNull View itemView) {
            super(itemView);
            yazarResim = itemView.findViewById(R.id.yazarresim);
            yazarAd = itemView.findViewById(R.id.yazarad);
            yaziBaslik = itemView.findViewById(R.id.yazaryazibaslik);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }


}
