package com.example.volaitapp.usuario;

import java.io.Serializable;

public class Pessoa implements Serializable {

    private int idPessoa;
    private String nomePessoa, telefone, cpf;

    public Pessoa(){}

    public Pessoa(int idPessoa, String nomePessoa, String telefone, String cpf){
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
        this.cpf = cpf;
    }
    public Pessoa(int idPessoa, String nomePessoa, String telefone){
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
    }

    public Pessoa(String nomePessoa, String telefone){
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
    }

    public Pessoa(String nomePessoa, String telefone, String cpf){
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public int getId() {
        return idPessoa;
    }

    public void setId(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nomePessoa;
    }

    public void setNome(String nome) {
        this.nomePessoa = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
