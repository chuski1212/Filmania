package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FirstMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //private FilmData filmData;
    private List<Film> listaFilms;
    public final static String EXTRA_IDFILM = "com.example.filmania.idfilm";
    public final static String EXTRA_TITLEFILM = "com.example.filmania.titlefilm";
    public final static String EXTRA_RATEFILM = "com.example.filmania.ratefilm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        ((GlobalDBControler) this.getApplication()).init(this);


        listaFilms = ((GlobalDBControler) this.getApplication()).getFilmData().getAllFilms();

        loadList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadList() {
        listaFilms = ((GlobalDBControler) this.getApplication()).getFilmData().getAllFilms();
        ListView mylist = (ListView) findViewById(R.id.mylist);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getTitles());
        mylist.setAdapter(adapter);
        registerForContextMenu(mylist);
    }

    private List<String> getTitles() {
        List<String> titulos = new ArrayList<>();
        int n = listaFilms.size();
        for (int i = 0; i < n; ++i) {
            titulos.add(listaFilms.get(i).getTitle());
        }
        return titulos;
    }

    public void filter(View view) {
        TextView authorText = (TextView) findViewById(R.id.authortext);
        String protagonist = authorText.getText().toString();
        if (!protagonist.equals("")) {
            System.out.println(protagonist);
            listaFilms = ((GlobalDBControler) this.getApplication()).getFilmData().getAllFilmsActorLimited(protagonist);
            ListView mylist = (ListView) findViewById(R.id.mylist);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, getTitles());
            mylist.setAdapter(adapter);
            registerForContextMenu(mylist);
        }
    }

    public void reset(View view) {
        TextView authorText = (TextView) findViewById(R.id.authortext);
        authorText.setText("");
        loadList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;
        String selectedWord = ((TextView) info.targetView).getText().toString();
        menu.setHeaderTitle(selectedWord);
        if (v.getId()==R.id.mylist) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.modify:

                Intent actModificar = new Intent(this, ModificarCritica.class);
                actModificar.putExtra(EXTRA_IDFILM,listaFilms.get(info.position).getId());
                actModificar.putExtra(EXTRA_RATEFILM,listaFilms.get(info.position).getCritics_rate());
                actModificar.putExtra(EXTRA_TITLEFILM,listaFilms.get(info.position).getTitle());
                startActivity(actModificar);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_complete) {
            Intent nextAct = new Intent(this,RecyclerView.class);
            startActivity(nextAct);
        } else if (id == R.id.nav_new) {

        } else if (id == R.id.nav_help) {
            Intent nextAct = new Intent(this, Help.class);
            startActivity(nextAct);

        } else if (id == R.id.nav_about) {
            Intent nextAct = new Intent(this, About.class);
            startActivity(nextAct);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        ((GlobalDBControler) this.getApplication()).open();
        loadList();
        TextView authorText = (TextView) findViewById(R.id.authortext);
        authorText.setText("");
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}
