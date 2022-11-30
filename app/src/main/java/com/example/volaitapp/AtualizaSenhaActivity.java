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
import java.util.Objects;

public class AtualizaSenhaActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText editTextSenha, editTextNovaSenha, editTextConfirmarSenha;
    Button btnSalvar;
    String senhaatual, novasenha, confirmarsenha;
    String URL = "https://losttanpencil1.conveyor.cloud/api/cliente/updatecliente";
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_atualiza_senha);
        editTextSenha = findViewById(R.id.editTextTextPersonName);
        editTextNovaSenha = findViewById(R.id.editTextTextPersonName2);
        editTextConfirmarSenha = findViewById(R.id.editTextTextPersonName4);

        btnSalvar = findViewById(R.id.button3);

        sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);



        String senhaShared = sharedpreferences.getString("SenhaCliente", "");

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senhaatual = String.valueOf(editTextSenha.getText());
                novasenha = String.valueOf(editTextNovaSenha.getText());
                confirmarsenha = String.valueOf(editTextConfirmarSenha.getText());
                if(Objects.equals(senhaatual, senhaShared)) {
                    if (Objects.equals(novasenha, confirmarsenha)) {
                        if(!Objects.equals(senhaatual, novasenha)) {
                            long cpf = sharedpreferences.getLong("CPFCliente", 0);

                            Cliente cliente = new Cliente(
                                    cpf,
                                    sharedpreferences.getString("NomeCliente", ""),
                                    sharedpreferences.getString("NomeSocialCliente", ""),
                                    sharedpreferences.getString("LoginCliente", ""),
                                    sharedpreferences.getString("TelefoneCliente", ""),
                                    novasenha
                            );

                            validarCampos();

                            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                            try {
                                NetworkInfo ni = cm.getActiveNetworkInfo();
                                if (ni != null) {
                                    putDataUser(cliente);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("SenhaCliente", novasenha);
                                    editor.apply();

                                    Toast.makeText(getApplicationContext(), "Senha atualizada com sucesso", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            } catch (Exception e) {

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Sua nova senha não pode ser igual a antiga.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Senha e Confirmar não correspondem.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "As senhas não correspondem.", Toast.LENGTH_SHORT).show();
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

        if (verificacao != campoNulo(senhaatual)) {
            editTextSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(novasenha)) {
            editTextNovaSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo nova senha", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(confirmarsenha)) {
            editTextConfirmarSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo confirmar senha", Toast.LENGTH_SHORT).show();
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
        senhaatual = String.valueOf(editTextSenha.getText());
        novasenha = String.valueOf(editTextNovaSenha.getText());
        confirmarsenha = String.valueOf(editTextConfirmarSenha.getText());

        outState.putString("nome", senhaatual);
        outState.putString("nomesocial", novasenha);
        outState.putString("telefone", confirmarsenha);
    }

    // MENU
    public void TelaCupom(View view){
        Intent intent = new Intent(getApplicationContext(), MyCupons.class);
        startActivity(intent);
    }
    public void TelaMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void TelaHome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public void TelaEdit(View view){
        Intent intent = new Intent(getApplicationContext(), EditPerfilActivity.class);
        startActivity(intent);
    }
    public void TelaLancamento(View view){
        Intent intent = new Intent(getApplicationContext(), LancamentoActivity.class);
        startActivity(intent);
    }
}