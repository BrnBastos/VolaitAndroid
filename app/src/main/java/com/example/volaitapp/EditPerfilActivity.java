package com.example.volaitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditPerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_perfil);
    }
}