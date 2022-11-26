package com.example.volaitapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.volaitapp.R;
import com.example.volaitapp.usuario.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    EditText editTextNome, editTextNomeSocial, editTextTelefone, editTextCpf, editTextEmail, editTextSenha, editTextConfSenha;
    Button btnCadastrar;
    String nome, nomesocial, telefone, cpf, email, senha, confirmar;
    String URL = "https://lostgreenmouse38.conveyor.cloud/api/cliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cadastro);

        editTextCpf        = (EditText) findViewById(R.id.editTextCpf);
        editTextNome       = (EditText) findViewById(R.id.editTextNome);
        editTextNomeSocial = (EditText) findViewById(R.id.editTextTextPersonName3);
        editTextTelefone   = (EditText) findViewById(R.id.editTextTelefone);
        editTextEmail      = (EditText) findViewById(R.id.editTextEmail);
        editTextSenha      = (EditText) findViewById(R.id.editTextSenha);
        btnCadastrar       = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(v -> {


            cpf        = String.valueOf(editTextCpf.getText());
                    nome       = String.valueOf(editTextNome.getText());
                    nomesocial = String.valueOf(editTextNomeSocial.getText());
                    telefone   = String.valueOf(editTextTelefone.getText());
                    email      = String.valueOf(editTextEmail.getText());
                    senha      = String.valueOf(editTextSenha.getText());

            Cliente cliente = new Cliente(
                    Long.valueOf(cpf),
                    nome,
                    nomesocial,
                    email,
                    telefone,
                    senha
            );

            validarCampos();

                postDataUser(cliente);
                Intent intent = new Intent(this, LoginActivity.class);
                Toast.makeText(this, "Cadastro efetuado", Toast.LENGTH_SHORT).show();
                startActivity(intent);
        });
    }

    public void postDataUser(Cliente cliente){
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

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
        } else if (verificacao != campoNulo(cpf)) {
            editTextCpf.requestFocus();
            Toast.makeText(this, "Preencha o campo CPF.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(email)) {
            editTextEmail.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(senha)) {
            editTextSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean campoNulo(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    // SAVED INSTANCE
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        cpf = String.valueOf(editTextCpf.getText());
        nome = String.valueOf(editTextNome.getText());
        nomesocial = String.valueOf(editTextNomeSocial.getText());
        telefone = String.valueOf(editTextTelefone.getText());
        email = String.valueOf(editTextEmail.getText());

        outState.putString("cpf", cpf);
        outState.putString("nome", nome);
        outState.putString("nomesocial", nomesocial);
        outState.putString("telefone", telefone);
        outState.putString("email", email);
    }
}

