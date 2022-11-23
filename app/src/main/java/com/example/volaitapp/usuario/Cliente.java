package com.example.volaitapp.usuario;

public class Cliente
{
    private long _CPFCliente;
    public long getCPFCliente()
    {
        return this._CPFCliente;
    }
    public void setCPFCliente(long value)
    {
        this._CPFCliente = value;
    }

    private String _NomeCliente;
    public String getNomeCliente()
    {
        return this._NomeCliente;
    }
    public void setNomeCliente(String value)
    {
        this._NomeCliente = value;
    }

    private String _NomeSocialCliente;
    public String getNomeSocialCliente()
    {
        return this._NomeSocialCliente;
    }
    public void setNomeSocialCliente(String value)
    {
        this._NomeSocialCliente = value;
    }

    private String _LoginCliente;
    public String getLoginCliente()
    {
        return this._LoginCliente;
    }
    public void setLoginCliente(String value)
    {
        this._LoginCliente = value;
    }

    private String _TelefoneCliente;
    public String getTelefoneCliente()
    {
        return this._TelefoneCliente;
    }
    public void setTelefoneCliente(String value)
    {
        this._TelefoneCliente = value;
    }

    private String _SenhaCliente;
    public String getSenhaCliente()
    {
        return this._SenhaCliente;
    }
    public void setSenhaCliente(String value)
    {
        this._SenhaCliente = value;
    }

    public Cliente(){}

    public Cliente(long CPFCliente_, String NomeCliente_, String NomeSocialCliente_, String LoginCliente_, String TelefoneCliente_, String SenhaCliente_)
    {
        this.CPFCliente = CPFCliente_;
        this.NomeCliente = NomeCliente_;
        this.NomeSocialCliente = NomeSocialCliente_;
        this.LoginCliente = LoginCliente_;
        this.TelefoneCliente = TelefoneCliente_;
        this.SenhaCliente = SenhaCliente_;
    }
}
