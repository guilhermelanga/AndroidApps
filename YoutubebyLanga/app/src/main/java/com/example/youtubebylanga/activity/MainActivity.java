package com.example.youtubebylanga.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.youtubebylanga.R;
import com.example.youtubebylanga.adapter.AdapterVideo;
import com.example.youtubebylanga.api.YoutubeService;
import com.example.youtubebylanga.helper.RetrofitConfig;
import com.example.youtubebylanga.helper.YoutubeConfig;
import com.example.youtubebylanga.listener.RecyclerItemClickListener;
import com.example.youtubebylanga.model.Items;
import com.example.youtubebylanga.model.Resultado;
import com.example.youtubebylanga.model.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity{


    //RECYCLERVIEW
    private RecyclerView recyclerVideos;

    //SERACHVIEW BOTÃO
    private MaterialSearchView searchView;

    //CRIA LISTA PARA RECEBER VIDEOS
    private List<Items> videos = new ArrayList<>();
    private Resultado resultado;

    //CRIAR O ADAPATER PARA O RECYCLER
    private AdapterVideo adapterVideo;

    //RETROFIT
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CONFIGURAR TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("YouTube By Langa");
        setSupportActionBar(toolbar);

        //INICILIZAR O RECYLCERVIEW
        recyclerVideos = findViewById(R.id.recyclerVideos);

        //INICIALIZAR O SEARCH VIEW
        searchView = findViewById(R.id.searchView);

        //CONFIGURAR RETROFIT
        retrofit = RetrofitConfig.getRetrofit();

        //RECUPERAR VÍDEOS
        recuperaVideos("");


        //CONFIGURAR MÉTODOS PARA O SEARCHVIEW
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperaVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                recuperaVideos("");
            }
        });



    }

    //BUSCA OS VIDEOS
    private void recuperaVideos(String pesquisa){
        String q = pesquisa.replaceAll(" ", "+");
        YoutubeService youtubeService = retrofit.create(YoutubeService.class);
        youtubeService.recuperarVideos(
              "snippet",
              "date",
              "20",
              YoutubeConfig.CHAVE_YOUTUBE_API,
              YoutubeConfig.CANAL_ID,
                q
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resulado", "resultado: " + response.toString());
                if(response.isSuccessful()){
                    resultado = response.body();
                    videos = resultado.items;

                    //INFLA O RECYCLER VIEW COM OS VIDEOS CARREGADOS
                    configurarRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });

    }

    //CONFIGURAR O RECYCLER VIEW
    public void configurarRecyclerView(){
        adapterVideo = new AdapterVideo(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

        //CONFIGURAR EVENTO DE CLIQUE
        recyclerVideos.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerVideos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Items video = videos.get(position);
                String idVideo = video.id.videoId;

                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("idVideo", idVideo);
                startActivity(intent);


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));
    }

    //CONFIGURA O BOTÃO DE SEARCH NA TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }
}