package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ModificarCritica extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Modify Critics Rate");
        setContentView(R.layout.activity_modificar_critica);

        Intent informacion = getIntent();

        int ratefilm = informacion.getIntExtra(FirstMenu.EXTRA_RATEFILM, 0);
        String titlefilm = informacion.getStringExtra(FirstMenu.EXTRA_TITLEFILM);

        TextView tituloFilm = (TextView) findViewById(R.id.titulo);
        tituloFilm.setText(titlefilm);

        TextView notaFilm = (TextView) findViewById(R.id.newCritica);
        System.out.println(ratefilm);
        notaFilm.setText(String.valueOf(ratefilm));

    }

    public void changeRate(View view) {
        TextView notaFilm = (TextView) findViewById(R.id.newCritica);
        int criticaNueva = Integer.parseInt(notaFilm.getText().toString());
        Intent informacion = getIntent();
        long idfilm = informacion.getLongExtra(FirstMenu.EXTRA_IDFILM, 0);
        ((GlobalDBControler) this.getApplication()).getFilmData().updateRate(idfilm,criticaNueva);
        Toast.makeText(getApplicationContext(), "Critics rate modified!",
                Toast.LENGTH_LONG).show();
        finish();


    }
}
