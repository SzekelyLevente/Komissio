package com.example.komissio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komissio.databinding.ActivityTartalycimkeViewBinding;

public class TartalycimkeView extends Activity {

    private ActivityTartalycimkeViewBinding binding;
    private Button hozzaad,vissza;
    private LinearLayout lay;
    private Tartalycimke tartalycimke;

    private IRepository repository;
    private ILogic logic;

    private int szam;
    private String ertek;
    private String csokibiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTartalycimkeViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hozzaad=binding.hozzaad;
        vissza=binding.vissza;
        lay=binding.lay;

        repository=new Repository(this);
        logic=new Logic(repository);

        ertek=getIntent().getStringExtra("ertek");
        szam=getIntent().getIntExtra("szam",-1);
        csokibiz=logic.Read("csokibiz");

        tartalycimke=new Tartalycimke(ertek);
        lay.addView(tartalycimke.createQR(TartalycimkeView.this));

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TartalycimkeView.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        hozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(csokibiz.contains(szam+""))
                {
                    Toast.makeText(TartalycimkeView.this,"Már létezik itt: "+(csokibiz.indexOf(szam+"")+1),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //csokbiz.setText(csokbiz.getText().toString()+""+i2);
                    csokibiz=csokibiz+""+szam;
                    logic.Update("csokibiz",csokibiz);
                    Toast.makeText(TartalycimkeView.this,"Hozzáadva: "+szam,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}