package com.example.pr_idi.mydatabaseexample;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.support.v7.widget.SearchView;


import java.util.List;

public class RecyclerView extends AppCompatActivity {


    private FilmsAdapter adapter;
    private android.support.v7.widget.RecyclerView recyclerFilms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerFilms = (android.support.v7.widget.RecyclerView) findViewById(R.id.filmview);
        adapter = new FilmsAdapter(this, ((GlobalDBControler) this.getApplication()).getFilmData().getAllFilmsYear());
        recyclerFilms.setAdapter(adapter);
        recyclerFilms.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        ((GlobalDBControler) this.getApplication()).getFilmData().deleteFilmID(item.getItemId());
        adapter.updateFilms(((GlobalDBControler) this.getApplication()).getFilmData().getAllFilmsYear());
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        adapter.updateFilms(((GlobalDBControler) this.getApplication()).getFilmData().getAllFilmsYear());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final GlobalDBControler db = (GlobalDBControler)this.getApplication();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchbar_recyclerview, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.updateFilms(db.getFilmData().getAllFilmsTitleLimited(query));
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.updateFilms(db.getFilmData().getAllFilmsTitleLimited(newText));
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                adapter.updateFilms(db.getFilmData().getAllFilmsYear());
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchView.setQuery("", false);
                adapter.updateFilms(db.getFilmData().getAllFilmsYear());
                searchView.clearFocus();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    }
