package com.example.pr_idi.mydatabaseexample;

/**
 * FilmData
 * Created by pr_idi on 10/11/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FilmData {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    // Here we only select Title and Director, must select the appropriate columns
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                                    MySQLiteHelper.COLUMN_TITLE,
                                    MySQLiteHelper.COLUMN_COUNTRY,
                                    MySQLiteHelper.COLUMN_YEAR_RELEASE,
                                    MySQLiteHelper.COLUMN_DIRECTOR,
                                    MySQLiteHelper.COLUMN_PROTAGONIST,
                                    MySQLiteHelper.COLUMN_CRITICS_RATE};

    public FilmData(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        //database.delete(MySQLiteHelper.TABLE_FILMS, null, null);
        //createFilm("Blade Runner", "Ridley Scott", "United States", 1982, "Harrison Ford", 8);
        //createFilm("Bee Movie", "Jim Sharman", "United States", 2007, "Jerry Seinfeld", 6);
        //createFilm("The Godfather", "Francis Ford Coppola", "United States", 1972, "Al Pacino", 8);
        //createFilm("Toy Story", "John Lasseter", "United States", 1995, "Tom Hanks", 8);
    }

    public void close() {
        dbHelper.close();
    }

    public Film createFilm(String title, String director, String country, int year,
                           String protagonist, int rate) {
        ContentValues values = new ContentValues();
        Log.d("Creating", "Creating " + title + " " + director);

        // Add data: Note that this method only provides title and director
        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, director);


        values.put(MySQLiteHelper.COLUMN_COUNTRY, country);
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, year);
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, protagonist);
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, rate);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_FILMS, null,
                values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void updateRate(long idfilm, int newrate) {
        ContentValues newvalues = new ContentValues();
        newvalues.put(MySQLiteHelper.COLUMN_CRITICS_RATE, newrate);
        database.update(MySQLiteHelper.TABLE_FILMS, newvalues, MySQLiteHelper.COLUMN_ID + " = " + idfilm, null);

    }
    public void deleteFilmID(long id) {
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Film> getAllFilmsActorLimited(String protagonist) {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_PROTAGONIST + " = " + "'"+protagonist+"'", null, null, null, MySQLiteHelper.COLUMN_TITLE+" ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Film> getAllFilmsTitleLimited(String title) {
        List<Film> comments = new ArrayList<>();
        String sql = "SELECT * FROM films WHERE title LIKE '%" + title + "%' ORDER BY year_release DESC";
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }
    public List<Film> getAllFilms() {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_TITLE+" ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }
    public List<Film> getAllFilmsYear() {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_YEAR_RELEASE+" DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film();


        film.setId(cursor.getLong(0));
        film.setTitle(cursor.getString(1));
        film.setCountry(cursor.getString(2));
        film.setYear(cursor.getInt(3));
        film.setDirector(cursor.getString(4));
        film.setProtagonist(cursor.getString(5));
        film.setCritics_rate(cursor.getInt(6));

        return film;
    }
}