package com.example.volaitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }
    public void TelaGeoLoc(View view){
        Intent intent = new Intent(getApplicationContext(), GeolocActivity.class);
        startActivity(intent);
    }

}