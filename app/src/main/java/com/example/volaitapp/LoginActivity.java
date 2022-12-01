package com.example.volaitapp;

import static android.app.PendingIntent.getActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.volaitapp.usuario.Cliente;
import com.example.volaitapp.usuario.Pessoa;
import com.example.volaitapp.usuario.Usuario;
import com.example.volaitapp.usuario.UsuarioDAO;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText edtxtLogin, edtxtSenha;
    Button btnLogin;
    TextView txtCadastro;
    String login, senha;
    String PARAMETER = "login";
    String URL = "https://righttancat31.conveyor.cloud/api/cliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

        edtxtLogin = findViewById(R.id.txtEmailLogin);
        edtxtSenha = findViewById(R.id.txtSenhaLogin);
        btnLogin = findViewById(R.id.btnentrar);
        txtCadastro = findViewById(R.id.btncadastro);

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                try{
                    NetworkInfo ni = cm.getActiveNetworkInfo();
                    if(ni != null) {
                        getUserData();
                    }
                }catch (Exception e){

                }
            }
        });
    }

    // VERIFICAR LOGIN PELA API
    private void getUserData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        Uri builtUri = Uri.parse(URL).buildUpon().appendQueryParameter(PARAMETER, edtxtLogin.getText().toString()).build();
        String builtUrl = builtUri.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, builtUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            login = jsonObject.getString("LoginCliente");
                            senha = jsonObject.getString("SenhaCliente");

                            if ((edtxtLogin.getText().toString()).equals(login) && (edtxtSenha.getText().toString()).equals(senha)) {

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("key", 1);
                                editor.putLong("CPFCliente", Long.parseLong(jsonObject.getString("CPFCliente")));
                                editor.putString("NomeCliente", jsonObject.getString("NomeCliente"));
                                editor.putString("NomeSocialCliente", jsonObject.getString("NomeSocialCliente"));
                                editor.putString("LoginCliente", jsonObject.getString("LoginCliente"));
                                editor.putString("TelefoneCliente", jsonObject.getString("TelefoneCliente"));
                                editor.putString("SenhaCliente", jsonObject.getString("SenhaCliente"));
                                editor.apply();

                                startActivity(intent);
                            } else {
                                message();
                            }

                            Log.e("url", "SucessMesage:");
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                            nullMessage();
                            Log.e("url", "onCatch Response: NÃO FOI");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nullMessage();
                Log.e("url", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }

    // VERIFICAÇÃO DE CAMPOS
    private boolean campoNulo (String campo){
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    private void validarCampos(){
        boolean verificacao = false;

        String login = edtxtLogin.getText().toString();
        String senha = edtxtSenha.getText().toString();

        if (verificacao != campoNulo(login)) {
            edtxtLogin.requestFocus();
            Toast.makeText(this, "Preencha o campo login.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(senha)) {
            edtxtSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        }
    }

    // MENSAGENS
    private void message(){
        Toast.makeText(this, "Login ou senha não correspondem.", Toast.LENGTH_SHORT).show();
    }

    private void nullMessage(){
        Toast.makeText(this, "Login não cadastrado.", Toast.LENGTH_SHORT).show();
    }
}


