package com.example.volaitapp.usuario;

public class Cliente
{
    private long getCPFCliente;
    public long getCPFCliente()
    {
        return this.getCPFCliente;
    }
    public void setCPFCliente(long value)
    {
        this.getCPFCliente = value;
    }

    private String getNomeCliente;
    public String getNomeCliente()
    {
        return this.getNomeCliente;
    }
    public void setNomeCliente(String value)
    {
        this.getNomeCliente = value;
    }

    private String getNomeSocialCliente;
    public String getNomeSocialCliente()
    {
        return this.getNomeSocialCliente;
    }
    public void setNomeSocialCliente(String value)
    {
        this.getNomeSocialCliente = value;
    }

    private String getLoginCliente;
    public String getLoginCliente()
    {
        return this.getLoginCliente;
    }
    public void setLoginCliente(String value)
    {
        this.getLoginCliente = value;
    }

    private String getTelefoneCliente;
    public String getTelefoneCliente()
    {
        return this.getTelefoneCliente;
    }
    public void setTelefoneCliente(String value)
    {
        this.getTelefoneCliente = value;
    }

    private String getSenhaCliente;
    public String getSenhaCliente()
    {
        return this.getSenhaCliente;
    }
    public void setSenhaCliente(String value)
    {
        this.getSenhaCliente = value;
    }

    public Cliente(){}

    public Cliente(long CPFCliente_, String NomeCliente_, String NomeSocialCliente_, String LoginCliente_, String TelefoneCliente_, String SenhaCliente_)
    {
        this.getCPFCliente = CPFCliente_;
        this.getNomeCliente = NomeCliente_;
        this.getNomeSocialCliente = NomeSocialCliente_;
        this.getLoginCliente = LoginCliente_;
        this.getTelefoneCliente = TelefoneCliente_;
        this.getSenhaCliente = SenhaCliente_;
    }
}
