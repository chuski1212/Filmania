package com.example.pr_idi.mydatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFilm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);
        setTitle("Add new film");
    }
    public void addFilm(View view) {
        EditText title = (EditText)findViewById(R.id.title);
        EditText director = (EditText)findViewById(R.id.director);
        EditText country = (EditText)findViewById(R.id.country);
        EditText protagonist = (EditText)findViewById(R.id.protagonist);
        EditText year = (EditText)findViewById(R.id.year);
        EditText rate = (EditText)findViewById(R.id.rate);
        if((title.getText().toString().isEmpty()) || (director.getText().toString().isEmpty()) || (country.getText().toString().isEmpty()) ||
                (protagonist.getText().toString().isEmpty()) || (year.getText().toString().isEmpty()) || (rate.getText().toString().isEmpty())){
            Toast.makeText(this,
                    "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
        else{
            ((GlobalDBControler)this.getApplication()).getFilmData().createFilm(title.getText().toString(), director.getText().toString(), country.getText().toString(),
                    Integer.parseInt(year.getText().toString()),protagonist.getText().toString(), Integer.parseInt(rate.getText().toString()));
            Toast.makeText(this, "New film added!", Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
