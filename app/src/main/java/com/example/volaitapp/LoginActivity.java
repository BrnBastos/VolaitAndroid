package com.example.volaitapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

    EditText edtxtLogin, edtxtSenha;
    Button btnLogin;
    TextView txtCadastro;
    String login, senha;
    String PARAMETER = "login";
    String URL = "https://rightorangecar53.conveyor.cloud/api/cliente";
    private static final String FILE_NAME = "usuarioLogado.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

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
                    if(ni != null)
                        getUserData();

                }catch (Exception e){

                }
            }
        });}

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
                              /*  Gson gson = new Gson();
                                Cliente user1 = gson.fromJson(user.toString(), Cliente.class);
                                Pessoa person1 = gson.fromJson(person.toString(), Pessoa.class);
                                insertUser(user1, person1);

                                Client client = new Client(user1, person1);
                                String json = gson.toJson(client);
                                gravarDados(json);*/

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    /*// VERIFICAR LOGIN PELO BANCO
    public void verificarLogin(String email, String senha){
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
            Client client = userDAO.selectClient(email, senha);

            Gson gson = new Gson();
            String json = gson.toJson(client);
            gravarDados(json);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // INSERIR USUÁRIO NO BANCO
    private long insertUser(Usuario usuario, Pessoa pessoa){
        dataBase = new DataBase(getApplicationContext());
        conection = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idUser", user.getIdUsuario());
        values.put("idPerson", person.getId());
        values.put("name", person.getNome());
        values.put("email", user.getLogin());
        values.put("password", user.getSenha());

        return conection.insert("tbClient", null, values);
    }*/


    // ARMAZENAR DADOS NO ARQUIVO JSON
    private void gravarDados(String json) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(json.getBytes());
            Toast.makeText(this, "Usuário logado.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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


