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
    String url = "https://funredstone42.conveyor.cloud/api/cupom";

    Boolean isSearchByName = false;
    String query = null;

    DisplayImageOptions options = null;

    private BottomNavigationView nav;

    int artId = 1;
    SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_cupons);

        listaCupons = new ArrayList<>();

        cuponsListView = findViewById(R.id.CuponsListView);
        getCupom();
    }
    private void getCupom(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject objt = response.getJSONObject(i);
                                Cupom cupom = new Cupom();
                                cupom.setCupomId(objt.getInt("CupomID"));
                                cupom.setcupomCode(objt.getString("CupomCode"));
                                cupom.setValorDesconto(BigDecimal.valueOf(objt.getDouble("ValorDesconto")));
                                String dta =objt.getString("CupomValidade");
                                try {
                                    cupom.setCupomValidade( inFormat.parse(dta));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                listaCupons.add(cupom);
                            }
                            CupomListViewAdapter adapter = new CupomListViewAdapter(getApplicationContext(), R.layout.cupom100item, listaCupons);
                            cuponsListView.setAdapter(adapter);
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
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