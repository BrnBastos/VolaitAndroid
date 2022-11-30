package com.example.volaitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.volaitapp.Cupom;
import com.example.volaitapp.CuponsActivity;
import com.example.volaitapp.R;

import java.io.Serializable;
import java.util.List;


public class CupomListViewAdapter extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Cupom> listaCupons;

    public CupomListViewAdapter(Context context, int layout, List<Cupom> listaCupons) {
        this.layout = layout;
        this.context = context;
        this.listaCupons = listaCupons;
    }

    @Override
    public int getCount() {
        return listaCupons.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCupons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ConstraintLayout btnCupomDetalhes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.btnCupomDetalhes = row.findViewById(R.id.card__cupom);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Cupom cupom = listaCupons.get(position);

       holder.btnCupomDetalhes.setOnClickListener(v -> {
            Intent intentAbrirEditar = new Intent(context, CuponsActivity.class);
            intentAbrirEditar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentAbrirEditar.putExtra("Cupom", cupom);
            context.startActivity(intentAbrirEditar);
        });

        return row;
    }
}
