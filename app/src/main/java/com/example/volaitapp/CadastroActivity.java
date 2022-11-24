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
import com.example.volaitapp.usuario.Cliente;
import com.example.volaitapp.usuario.Pessoa;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    EditText edtxtNome, edtxtTelefone, edtxtCpf, edtxtEmail, edtxtSenha;
    Button btnCadastrar;
    String nome, telefone, cpf, email, senha, confirmar;
    String URL = "https://newgreypage69.conveyor.cloud/api/UsuarioApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cadastro);

        edtxtNome = findViewById(R.id.editTextNome);
        edtxtTelefone = findViewById(R.id.editTextTelefone);
        edtxtCpf = findViewById(R.id.editTextCpf);
        edtxtEmail = findViewById(R.id.editTextEmail);
        edtxtSenha = findViewById(R.id.editTextSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(v -> {
            nome = String.valueOf(edtxtNome.getText());
            telefone = String.valueOf(edtxtTelefone.getText());
            cpf = String.valueOf(edtxtCpf.getText());
            email = String.valueOf(edtxtEmail.getText());
            senha = String.valueOf(edtxtSenha.getText());

            Pessoa pessoa = new Pessoa(nome, telefone, cpf);
            Cliente cliente = new Cliente(email, senha);

            validarCampos();

            if(Objects.equals(senha, confirmar)){
                postDataUser(pessoa, cliente);
                Intent intent = new Intent(this, LoginActivity.class);
                Toast.makeText(this, "Cadastro efetuado", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            else {
                Toast.makeText(CadastroActivity.this, "As senhas não correspondem.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postDataUser(Pessoa pessoa, Cliente cliente){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject person = new JSONObject();
            pessoa.put("nomePessoa", pessoa.getNome());
            pessoa.put("telefone", pessoa.getTelefone());
            pessoa.put("cpf", pessoa.getCpf());

            JSONObject usuario = new JSONObject();
            usuario.put("login", cliente.getLogin());
            usuario.put("senha", cliente.getSenha());


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Pessoa", pessoa);
            jsonBody.put("Usuario", usuario);

            final String requestBody = jsonBody.toString();

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
            edtxtNome.requestFocus();
            Toast.makeText(this, "Preencha o campo nome.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(telefone)) {
            edtxtTelefone.requestFocus();
            Toast.makeText(this, "Preencha o campo telefone.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(cpf)) {
            edtxtCpf.requestFocus();
            Toast.makeText(this, "Preencha o campo CPF.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(email)) {
            edtxtEmail.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(senha)) {
            edtxtSenha.requestFocus();
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

        nome = String.valueOf(edtxtNome.getText());
        telefone = String.valueOf(edtxtTelefone.getText());
        cpf = String.valueOf(edtxtCpf.getText());
        email = String.valueOf(edtxtEmail.getText());
        senha = String.valueOf(edtxtSenha.getText());

        outState.putString("nome", nome);
        outState.putString("telefone", telefone);
        outState.putString("cpf", cpf);
        outState.putString("email", email);
        outState.putString("senha", senha);
    }
}