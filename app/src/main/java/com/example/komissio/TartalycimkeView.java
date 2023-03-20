package com.example.komissio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komissio.databinding.ActivityTartalycimkeViewBinding;

import java.util.ArrayList;

public class TartalycimkeView extends Activity {

    private ActivityTartalycimkeViewBinding binding;
    private Button hozzaad,vissza;
    private TextView seged;
    private LinearLayout lay;
    private ImageView qr;
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
        seged=binding.seged;
        lay=binding.lay;
        qr=binding.qr;

        repository=new Repository(this);
        logic=new Logic(repository);

        ertek=getIntent().getStringExtra("ertek");
        szam=getIntent().getIntExtra("szam",-1);
        csokibiz=logic.Read("csokibiz");

        tartalycimke=new Tartalycimke(ertek);
        qr.setImageBitmap(tartalycimke.createBitmap());

        ArrayList<Integer> helyek=new ArrayList<>();
        for (int i = 0; i < csokibiz.length(); i++) {
            if(Integer.parseInt(csokibiz.charAt(i)+"")==szam)
            {
                helyek.add(i+1);
            }
        }

        if(helyek.size()!=0)
        {
            String szoveg="Van már itt: ";
            for (int i = 0; i < helyek.size(); i++) {
                szoveg+=helyek.get(i)+"";
                if(i!=helyek.size()-1)
                {
                    szoveg+=",";
                }
            }
            seged.setText(szoveg);
        }

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TartalycimkeView.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        hozzaad.setText("hozzáad: "+szam);

        hozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                csokibiz=csokibiz+""+szam;
                logic.Update("csokibiz",csokibiz);
                Toast.makeText(TartalycimkeView.this,"Hozzáadva: "+szam,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(TartalycimkeView.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}