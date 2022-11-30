package com.example.volaitapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.volaitapp.Conexao;
import com.example.volaitapp.Cupom;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class CupomDAO {

    private final Conexao conexao;
    private final SQLiteDatabase banco;
    SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CupomDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long insertCupom(Cupom cupom){
        ContentValues values = new ContentValues();
        values.put("CupomId", cupom.getCupomId());
        values.put("CupomCode", cupom.getcupomCode());
        values.put("CupomCode", String.valueOf(cupom.getValorDesconto()));
        values.put("CupomValidade", String.valueOf(cupom.getCupomValidade()));
            

        return banco.insert("tb_cupom", null, values);
    }
    

    public List<Cupom> selectCupom()
    {
        List<Cupom> listaCupoms = new ArrayList<>();

        Cursor cursor = banco.query("tbCupom",
                new String[] {
                        "CupomId",
                        "CupomCode",
                        "ValorDesconto",
                        "CupomValidade"
                        
                },
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()){
            Cupom cupom = new Cupom();

            cupom.setCupomId(cursor.getInt(0));
            cupom.setcupomCode(cursor.getString(1));
            cupom.setValorDesconto(BigDecimal.valueOf(cursor.getDouble(2)));
            //cupom.setCupomValidade(Date.parse(cursor.getString(3)));

            listaCupoms.add(cupom);
        }

        return listaCupoms;
    }

    public Cupom selectCupomPorCNPJ(String cnpj){
        Cupom cupom = new Cupom();
        Cursor cursor = banco.query("tbCupom",
                new String[]{"CupomId",
                        "CupomCode",
                        "ValorDesconto",
                        "CupomValidade"},
                "CupomId = ?",
                new String[]{cnpj},
                null,
                null,
                null,
                String.valueOf(1)
        );
        while(cursor.moveToNext())
        {
            cupom.setCupomId(cursor.getInt(0));
            cupom.setcupomCode(cursor.getString(1));
            cupom.setValorDesconto(BigDecimal.valueOf(cursor.getDouble(2)));

            try {
                cupom.setCupomValidade(inFormat.parse(cursor.getString(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return cupom;
    }

    
    
}
