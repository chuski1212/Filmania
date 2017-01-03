package com.example.pr_idi.mydatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

public class RecyclerView extends AppCompatActivity {


    private FilmsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        android.support.v7.widget.RecyclerView recyclerFilms = (android.support.v7.widget.RecyclerView) findViewById(R.id.filmview);
        adapter = new FilmsAdapter(this, ((GlobalDBControler) this.getApplication()).getFilmData().getAllFilms());
        recyclerFilms.setAdapter(adapter);
        recyclerFilms.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        ((GlobalDBControler) this.getApplication()).getFilmData().deleteFilmID(item.getItemId());
        adapter.updateFilms(((GlobalDBControler) this.getApplication()).getFilmData().getAllFilms());
        return super.onContextItemSelected(item);
    }
}
