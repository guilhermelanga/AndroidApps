package com.example.youtubebylanga.adapter;

import android.content.Context;
import android.icu.text.MessagePattern;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubebylanga.R;
import com.example.youtubebylanga.model.Items;
import com.example.youtubebylanga.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {

    private List<Items> videos = new ArrayList<>();
    private Context context;

    public AdapterVideo(List<Items> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_video, viewGroup, false);
        return new AdapterVideo.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Items video = videos.get(i);
        String urlImage = video.snippet.thumbnails.high.url;
        Picasso.get().load(urlImage).into(myViewHolder.capa);
        myViewHolder.titulo.setText(video.snippet.title);
        myViewHolder.descricao.setText(video.snippet.description);
        myViewHolder.data.setText(video.snippet.publishedAt);

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, descricao, data;
        ImageView capa;

        public MyViewHolder(View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.txtTitulo);
            capa = itemView.findViewById(R.id.imageCapa);
            descricao = itemView.findViewById(R.id.textDescricao);
            data = itemView.findViewById(R.id.textData);


        }

    }
}
