package com.example.volaitapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.volaitapp.adapters.CupomListViewAdapter;
import com.example.volaitapp.dao.CupomDAO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyCupons extends AppCompatActivity {
    List<Cupom> listaCupons;
    ListView cuponsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_cupons);

        CupomDAO cupomDAO = new CupomDAO(getApplicationContext());

        listaCupons = cupomDAO.selectCupom();
        cuponsListView = findViewById(R.id.CuponsListView);

        CupomListViewAdapter adapter = new CupomListViewAdapter(getApplicationContext(), R.layout.cupom100item, listaCupons);
        cuponsListView.setAdapter(adapter);
    }



    public void TelaMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void TelaLancamento(View view){
        Intent intent = new Intent(getApplicationContext(), LancamentoActivity.class);
        startActivity(intent);
    }
    public void TelaCupom(View view){
        Intent intent = new Intent(getApplicationContext(), MyCupons.class);
        startActivity(intent);
    }
    public void Telahome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}