package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConexionHTTPActivity extends AppCompatActivity implements View.OnClickListener{

    EditText direccion;
    RadioButton radioJava;
    RadioButton radioApache;
    Button conectar;
    WebView web;
    TextView tiempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_http);
        iniciar();
    }

    private void iniciar() {
        direccion = (EditText) findViewById(R.id.edtURL);
        radioJava = (RadioButton) findViewById(R.id.rdbJava);
        radioApache = (RadioButton) findViewById(R.id.rdbApache);
        conectar = (Button) findViewById(R.id.btnConectar);
        conectar.setOnClickListener(this);
        web = (WebView) findViewById(R.id.wbvMostrar);
        tiempo = (TextView) findViewById(R.id.txvTiempo);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }
    @Override
    public void onClick(View v) {
        String texto = direccion.getText().toString();
        long inicio, fin;
        Resultado resultado;
        if (v == conectar) {
            inicio = System.currentTimeMillis();
            if (radioJava.isChecked())
                resultado = Conexion.conectarJava(texto);
            else
                resultado = Conexion.conectarApache(texto);
            fin = System.currentTimeMillis();
            if (resultado.getCodigo())
                web.loadDataWithBaseURL(null, resultado.getContenido(),"text/html", "UTF-8", null);
            else
                web.loadDataWithBaseURL(null, resultado.getMensaje(),"text/html", "UTF-8", null);
            tiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
        }
    }
}
