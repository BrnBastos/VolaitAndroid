package com.example.volaitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volaitapp.dao.CupomDAO;

import java.text.SimpleDateFormat;

public class CuponsActivity extends AppCompatActivity {

    TextView textViewCupomCode, textViewDatavalidade;
    Button btnResgatarCupom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cupom);

        Intent intent = getIntent();
        Cupom cupom = ((Cupom) intent.getSerializableExtra("Cupom"));

        textViewCupomCode = findViewById(R.id.textView24);
        textViewDatavalidade = findViewById(R.id.datavalidade);
        btnResgatarCupom = findViewById(R.id.btnResgatar);

        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        textViewCupomCode.setText(cupom.getcupomCode());
        textViewDatavalidade.setText(dt.format(cupom.getCupomValidade()));

        btnResgatarCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CupomDAO cupomDAO = new CupomDAO(getApplicationContext());
                cupomDAO.insertCupom(cupom);
                Toast.makeText(getApplicationContext(), "Cupom resgatado com sucesso.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), MyCupons.class));
            }
        });
    }



    public void TelaCupom(View view){
        Intent intent = new Intent(getApplicationContext(), CuponsActivity.class);
        startActivity(intent);
    }
    public void TelaMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void TelaMain(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}