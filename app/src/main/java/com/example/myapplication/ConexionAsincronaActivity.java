package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConexionAsincronaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText direccion;
    RadioButton radioJava, radioApache;
    Button conectar;
    WebView web;
    TextView tiempo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_http);
        iniciar();

    }
    private void iniciar(){
        direccion = (EditText) findViewById(R.id.edtURL);
        radioJava = (RadioButton) findViewById(R.id.rdbJava);
        radioApache = (RadioButton) findViewById(R.id.rdbApache);
        conectar = (Button) findViewById(R.id.btnConectar);
        conectar.setOnClickListener(this);
        web = (WebView) findViewById(R.id.wbvMostrar);
        tiempo = (TextView) findViewById(R.id.txvTiempo);
    }

    @Override
    public void onClick(View v) {
        TareaAsincrona tareaAsincrona = new TareaAsincrona();
        String texto = direccion.getText().toString();
        if (v == conectar) {
            tiempo.setText("Descargando Pagina");
            tareaAsincrona.execute(texto);

        }
    }

    public class TareaAsincrona extends AsyncTask<String, Void, Resultado> {
        private ProgressDialog progreso;

        long inicio, fin;

        @Override
        protected Resultado doInBackground(String... strings) {
            Resultado resultado;

            if (radioJava.isChecked())
                resultado = Conexion.conectarJava(strings[0]);
            else
                resultado = Conexion.conectarApache(strings[0]);

            fin = System.currentTimeMillis();

            return resultado;
        }

        protected void onPreExecute() {
            inicio = System.currentTimeMillis();
            // Se puede pasar contexto usando constructor y pasando el contexto
            progreso = new ProgressDialog(ConexionAsincronaActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            if(radioJava.isChecked()) {
                progreso.setMessage("Conectando con JAVA. . .");
            }else if(radioApache.isChecked()){
                progreso.setMessage("Conectando con APACHE. . .");
            }
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected void onPostExecute(Resultado resultado) {
            super.onPostExecute(resultado);
            if (resultado.getCodigo())
                web.loadDataWithBaseURL(null, resultado.getContenido(),"text/html", "UTF-8", null);
            else
                web.loadDataWithBaseURL(null, resultado.getMensaje(),"text/html", "UTF-8", null);
            tiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
            progreso.dismiss();
        }
    }
}