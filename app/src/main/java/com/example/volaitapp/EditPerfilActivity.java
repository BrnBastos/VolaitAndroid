package com.example.volaitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.volaitapp.usuario.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class EditPerfilActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText editTextNome, editTextNomeSocial, editTextLogin, edtxtSenha, editTextTelefone;
    Button btnSalvar;
    String nome, nomesocial, telefone, email;
    String URL = "https://losttanpencil1.conveyor.cloud/api/cliente/updatecliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_perfil);

        editTextNome = findViewById(R.id.editTextTextPersonName7);
        editTextNomeSocial = findViewById(R.id.editTextTextPersonName9);
        editTextLogin = findViewById(R.id.editTextTextPersonName11);
        editTextTelefone = findViewById(R.id.editTextTextPersonName10);

        btnSalvar = findViewById(R.id.button2);

        sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

        editTextNome.setText(sharedpreferences.getString("NomeCliente", ""));
        editTextNomeSocial.setText(sharedpreferences.getString("NomeSocialCliente", ""));
        editTextLogin.setText(sharedpreferences.getString("LoginCliente", ""));
        editTextTelefone.setText(sharedpreferences.getString("TelefoneCliente", ""));

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long cpf = sharedpreferences.getLong("CPFCliente", 0);
                String senha = sharedpreferences.getString("SenhaCliente", "");

                nome       = String.valueOf(editTextNome.getText());
                nomesocial = String.valueOf(editTextNomeSocial.getText());
                telefone   = String.valueOf(editTextTelefone.getText());
                email      = String.valueOf(editTextLogin.getText());

                Cliente cliente = new Cliente(
                        cpf,
                        nome,
                        nomesocial,
                        email,
                        telefone,
                        senha
                );

                validarCampos();

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                try{
                    NetworkInfo ni = cm.getActiveNetworkInfo();
                    if(ni != null) {
                        putDataUser(cliente);
                        Intent intent = new Intent(getApplicationContext(), EditPerfilActivity.class);
                        Toast.makeText(getApplicationContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }catch (Exception e){

                }
            }
        });
    }

    public void putDataUser(Cliente cliente){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject clienteJsonObject = new JSONObject();
            clienteJsonObject.put("CPFCliente", cliente.getCPFCliente());
            clienteJsonObject.put("NomeCliente", cliente.getNomeCliente());
            clienteJsonObject.put("NomeSocialCliente", cliente.getNomeSocialCliente());
            clienteJsonObject.put("LoginCliente", cliente.getLoginCliente());
            clienteJsonObject.put("TelefoneCliente", cliente.getTelefoneCliente());
            clienteJsonObject.put("SenhaCliente", cliente.getSenhaCliente());

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Cliente", clienteJsonObject);

            final String requestBody = clienteJsonObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // VALIDAÇÃO DE CAMPOS
    private void validarCampos() {
        boolean verificacao = false;

        if (verificacao != campoNulo(nome)) {
            editTextNome.requestFocus();
            Toast.makeText(this, "Preencha o campo nome.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(nomesocial)) {
            editTextTelefone.requestFocus();
            Toast.makeText(this, "Preencha o campo nome social.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(telefone)) {
            editTextTelefone.requestFocus();
            Toast.makeText(this, "Preencha o campo telefone.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(email)) {
            editTextLogin.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean campoNulo(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    // SAVED INSTANCE
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //cpf = String.valueOf(editTextCpf.getText());
        nome = String.valueOf(editTextNome.getText());
        nomesocial = String.valueOf(editTextNomeSocial.getText());
        telefone = String.valueOf(editTextTelefone.getText());
        email = String.valueOf(editTextLogin.getText());

        outState.putString("nome", nome);
        outState.putString("nomesocial", nomesocial);
        outState.putString("telefone", telefone);
        outState.putString("email", email);
    }

    // MENU
    public void TelaMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void TelaHome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public void TelaAttSenha(View view){
        Intent intent = new Intent(getApplicationContext(), AtualizaSenhaActivity.class);
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
}