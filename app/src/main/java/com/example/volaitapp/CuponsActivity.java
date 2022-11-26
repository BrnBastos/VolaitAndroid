package com.example.volaitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class CuponsActivity extends AppCompatActivity {

    TextView textViewCupomCode, textViewDatavalidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cupom);

        Intent intent = getIntent();
        Cupom cupom = ((Cupom) intent.getSerializableExtra("Cupom"));

        textViewCupomCode = findViewById(R.id.textView24);
        textViewDatavalidade = findViewById(R.id.datavalidade);

        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        textViewCupomCode.setText(cupom.getcupomCode());
        textViewDatavalidade.setText(dt.format(cupom.getCupomValidade()));
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