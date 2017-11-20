package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class DescargaActivity extends AppCompatActivity implements View.OnClickListener {

        EditText texto;
        Button botonImagen;
        ImageView imagen;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_descarga);
            texto = (EditText) findViewById(R.id.edtURL);
            botonImagen = (Button) findViewById(R.id.btnDescargarImagen);
            botonImagen.setOnClickListener(this);
            imagen = (ImageView) findViewById(R.id.imgImagen);
        }
        @Override
        public void onClick(View v) {
            String url = texto.getText().toString();
            if (v == botonImagen)
                descargaImagen(url);
        }
        private void descargaImagen(String url) {

            /*Picasso.with(this).load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(imagen);*/

            //utilizar OkHttp3
            OkHttpClient client = new OkHttpClient();

            Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(client))
                    .build();

            picasso.with(this).load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(imagen);




        }
    }