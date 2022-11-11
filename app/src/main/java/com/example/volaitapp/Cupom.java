package com.example.volaitapp;

import java.io.Serializable;
import java.math.BigDecimal;

public class Cupom implements Serializable {
    private int CupomId;
    public int getCupomId()
    {
        return this.CupomId;
    }
    public void setCupomId(int value)
    {
        this.CupomId = value;
    }

    private String CupomCode;
    public String getcupomCode()
    {
        return this.CupomCode;
    }
    public void setcupomCode(String value)
    {
        this.CupomCode = value;
    }

    private BigDecimal ValorDesconto;
    public BigDecimal getValorDesconto()
    {
        return this.ValorDesconto;
    }
    public void setValorDesconto(BigDecimal value)
    {
        this.ValorDesconto = value;
    }

    private java.sql.Date CupomValidade;
    public java.sql.Date getCupomValidade()
    {
        return this.CupomValidade;
    }
    public void setCupomValidade(java.sql.Date value)
    {
        this.CupomValidade = value;
    }

    public Cupom()
    {

    }
    public Cupom(int CupomId_,String cupomCode_,BigDecimal ValorDesconto_,java.sql.Date CupomValidade_)
    {
        this.CupomId = CupomId_;
        this.CupomCode = cupomCode_;
        this.ValorDesconto = ValorDesconto_;
        this.CupomValidade = CupomValidade_;
    }
}
