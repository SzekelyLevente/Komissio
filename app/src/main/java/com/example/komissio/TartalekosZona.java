package com.example.komissio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.komissio.databinding.ActivityTartalekosZonaBinding;

import java.util.ArrayList;

public class TartalekosZona extends Activity {

    private ActivityTartalekosZonaBinding binding;
    private Button vissza,t1,t2,t3,t4;
    private EditText z1,z2,z3,z4;
    private LinearLayout ll;
    private ArrayList<Zona> zonak;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTartalekosZonaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vissza=binding.vissza;
        z1=binding.z1;
        z2=binding.z2;
        z3=binding.z3;
        z4=binding.z4;

        t1=binding.t1;
        t2=binding.t2;
        t3=binding.t3;
        t4=binding.t4;

        zonak=new ArrayList<Zona>();

        sharedPref = this.getSharedPreferences("kom", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        for (int i=1;i<=4;i++)
        {
            zonak.add(new Zona("z"+i));
            betoltes("z"+i);
        }

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TartalekosZona.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        z1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("z1",charSequence.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                z1.setText("");
            }
        });

        z2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("z2",charSequence.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                z2.setText("");
            }
        });

        z3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("z3",charSequence.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                z3.setText("");
            }
        });

        z4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("z4",charSequence.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                z4.setText("");
            }
        });
    }

    public void betoltes(String nev)
    {

        String adatok=sharedPref.getString(nev,"");
        int ind=Integer.parseInt(nev.charAt(1)+"");
        switch (nev)
        {
            case "z1":
                z1.setText(adatok);
                break;
            case "z2":
                z2.setText(adatok);
                break;
            case "z3":
                z3.setText(adatok);
                break;
            case "z4":
                z4.setText(adatok);
                break;
        }
        zonak.get(ind-1).modosit(adatok);
    }
}