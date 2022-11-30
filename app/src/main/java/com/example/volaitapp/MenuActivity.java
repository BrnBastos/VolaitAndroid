package com.example.volaitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);
    }

    public void TelaGeoLoc(View view){
        Intent intent = new Intent(getApplicationContext(), GeolocActivity.class);
        startActivity(intent);
    }

    public void Telahome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public void TelaeditPerfil(View view){
        Intent intent = new Intent(getApplicationContext(), EditPerfilActivity.class);
        startActivity(intent);
    }

    public void Telaprivacidade(View view){
        Intent intent = new Intent(getApplicationContext(), PrivacidadeActivity.class);
        startActivity(intent);
    }

    public void TelaSobre(View view){
        Intent intent = new Intent(getApplicationContext(), SobreActivity.class);
        startActivity(intent);
    }

    public void TelaCupom(View view){
        Intent intent = new Intent(getApplicationContext(), MyCupons.class);
        startActivity(intent);
    }

    public void TelaMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void TelaLancamento(View view){
        Intent intent = new Intent(getApplicationContext(), LancamentoActivity.class);
        startActivity(intent);
    }

}
