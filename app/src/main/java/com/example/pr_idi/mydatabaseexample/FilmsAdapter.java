package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by daniel on 30/12/2016.
 */

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder>{


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{


        public TextView pName;
        public TextView pDirector;
        public TextView pProtagonist;
        public TextView pCountry;
        public TextView pYear;
        public TextView pRate;
        public long id;

        public ViewHolder(View itemView){
            super(itemView);
            pName = (TextView) itemView.findViewById(R.id.name);
            pDirector = (TextView) itemView.findViewById(R.id.director);
            pProtagonist = (TextView) itemView.findViewById(R.id.protagonist);
            pCountry = (TextView) itemView.findViewById(R.id.country);
            pYear = (TextView) itemView.findViewById(R.id.year);
            pRate = (TextView) itemView.findViewById(R.id.rate);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(pName.getText());
            menu.add(0,(int)id, 0, "Delete");
        }
    }

    private List<Film> pFilms;
    private Context pContext;

    public FilmsAdapter(Context context, List<Film> films){
        pFilms = films;
        pContext = context;
    }

    private Context getContext(){
        return pContext;
    }

    @Override
    public FilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View filmView = inflater.inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(filmView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilmsAdapter.ViewHolder viewHolder, int pos){
        Film film = pFilms.get(pos);
        TextView name = viewHolder.pName;
        name.setText(film.getTitle());
        TextView director = viewHolder.pDirector;
        director.setText(film.getDirector());
        TextView protagonist = viewHolder.pProtagonist;
        protagonist.setText(film.getProtagonist());
        TextView country = viewHolder.pCountry;
        country.setText(film.getCountry());
        TextView year = viewHolder.pYear;
        year.setText(Integer.toString(film.getYear()));
        TextView rate = viewHolder.pRate;
        rate.setText(Integer.toString(film.getCritics_rate()) + "/10");
        viewHolder.id = film.getId();
    }
    @Override
    public int getItemCount(){
        return pFilms.size();
    }


}
