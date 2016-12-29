package com.example.pr_idi.mydatabaseexample;

import android.app.Application;
import android.content.Context;

/**
 * Created by JORGEPC on 29/12/2016.
 */

public class GlobalDBControler extends Application {
    private FilmData filmData;

    public void init(Context context) {
        filmData = new FilmData(context);
        open();
    }

    public void open() {
        filmData.open();
    }

    public void close() {
        filmData.close();
    }

    public FilmData getFilmData() {
        return filmData;
    }
}
