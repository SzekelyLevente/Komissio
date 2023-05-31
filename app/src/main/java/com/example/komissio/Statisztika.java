package com.example.komissio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.komissio.databinding.ActivityStatisztikaBinding;

public class Statisztika extends Activity {

    private ActivityStatisztikaBinding binding;
    private TextView text;
    private Button torles,vissza;

    private IRepository repository;
    private ILogic logic;

    private int rendelesdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStatisztikaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        text = binding.text;
        torles=binding.torles;
        vissza=binding.vissza;

        repository=new Repository(this);
        logic=new Logic(repository);

        init();

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Statisztika.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        torles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.Update("rendelesdb","0");
                init();
            }
        });
    }

    public void init()
    {
        rendelesdb=Integer.parseInt(logic.Read("rendelesdb"));

        String s="";
        s+="kiszedett rendelések száma: "+rendelesdb+"db";
        text.setText(s);
    }
}