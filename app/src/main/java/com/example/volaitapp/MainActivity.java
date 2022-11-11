package com.example.volaitapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volaitapp.adapters.CupomListViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bundle> {
    List<Cupom> listaCupons;
    ListView cuponsListView;

    Boolean isSearchByName = false;
    String query = null;
    
    DisplayImageOptions options = null;

    private BottomNavigationView nav;

    int artId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        cuponsListView = findViewById(R.id.CuponsListView);

        CupomListViewAdapter adapter = new CupomListViewAdapter(this, R.layout.cupom100item, listaCupons);
        cuponsListView.setAdapter(adapter);
    }
    public void TelaCupom(View view){
        Intent intent = new Intent(getApplicationContext(), CuponsActivity.class);
        startActivity(intent);
    }
    public void TelaMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<Bundle> onCreateLoader(int id, @Nullable Bundle args) {
        Toast toast = Toast.makeText(this, "Carregando...", Toast.LENGTH_LONG);
        toast.show();
        if (isSearchByName == false) {
            return new fetchCupom(this,"id",null);
        }
        else if (isSearchByName == true){
            return new fetchCupom(this,"name",query);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bundle> loader, Bundle data) {
        Cupom cupom = new Cupom();
        try {
            Bundle allData = data;
            int i = 0;
            JSONObject objCupom = null;
            boolean isArray = allData.getBoolean("isArray");
            if (isArray == false) {
                objCupom = new JSONObject(data.getString("artData"));
                cupom.setCupomId(objCupom.getInt("CupomId"));
                cupom.setcupomCode(objCupom.getString("CupomCode"));
                cupom.setValorDesconto(BigDecimal.valueOf(objCupom.getDouble("ValorDesconto")));
                cupom.setCupomValidade(Date.valueOf(objCupom.getString("ValidadeDesconto")));
            }
            if (isArray == true) {
                JSONArray arrayArts = new JSONArray(data.getString("artData"));
                while (arrayArts.length() > i) {
                    objCupom = new JSONObject(data.getString("artData"));
                    cupom.setCupomId(objCupom.getInt("CupomId"));
                    cupom.setcupomCode(objCupom.getString("CupomCode"));
                    cupom.setValorDesconto(BigDecimal.valueOf(objCupom.getDouble("ValorDesconto")));
                    cupom.setCupomValidade(Date.valueOf(objCupom.getString("ValidadeDesconto")));
                    listaCupons.add(cupom);
                    i = i + 1;
                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<Bundle> loader) {

    }


}