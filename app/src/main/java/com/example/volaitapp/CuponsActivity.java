package com.example.volaitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CuponsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cupom);
    }
}