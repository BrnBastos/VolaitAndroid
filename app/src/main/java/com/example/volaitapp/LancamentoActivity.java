package com.example.volaitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LancamentoActivity extends AppCompatActivity {

    private ImageView thumb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_lancamento);

        thumb1 = (ImageView) findViewById(R.id.img_thumbnailvolait);

        thumb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=0K91Jp4o4Rc")));
            }
        });

    }


    public void TelaCupom(View view){
        Intent intent = new Intent(getApplicationContext(), CuponsActivity.class);
        startActivity(intent);
    }
    public void TelaMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void TelaMain(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public void TelaLancamento(View view){
        Intent intent = new Intent(getApplicationContext(), LancamentoActivity.class);
        startActivity(intent);
    }
}
