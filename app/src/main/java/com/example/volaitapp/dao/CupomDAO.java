package com.example.volaitapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.volaitapp.Conexao;
import com.example.volaitapp.Cupom;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class CupomDAO {

    private final Conexao conexao;
    private final SQLiteDatabase banco;


    public CupomDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long insertCupom(Cupom cupom){
        ContentValues values = new ContentValues();
        values.put("CupomId", cupom.getCupomId());
        values.put("CupomCode", cupom.getcupomCode());
        values.put("ValorDesconto", String.valueOf(cupom.getValorDesconto()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = cupom.getCupomValidade();
        String dateToStr = dateFormat.format(date);

        values.put("CupomValidade", dateToStr);

        return banco.insert("tb_cupom", null, values);
    }
    

    public List<Cupom> selectCupom()
    {
        List<Cupom> listaCupoms = new ArrayList<>();

        Cursor cursor = banco.query("tb_cupom",
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
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                cupom.setCupomValidade(inFormat.parse(cursor.getString(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            listaCupoms.add(cupom);
        }

        return listaCupoms;
    }

    public Cupom selectCupomById(int id){
        Cupom cupom = new Cupom();
        Cursor cursor = banco.query("tb_cupom",
                new String[]{"CupomId",
                        "CupomCode",
                        "ValorDesconto",
                        "CupomValidade"},
                "CupomId = ?",
                new String[]{String.valueOf(id)},
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

            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                cupom.setCupomValidade(inFormat.parse(cursor.getString(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return cupom;
    }

    
    
}
