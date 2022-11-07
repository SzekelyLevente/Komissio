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
import android.widget.TextView;

import com.example.komissio.databinding.ActivityTartalekosBinding;

public class Tartalekos extends Activity {

    private ActivityTartalekosBinding binding;
    private Button torles,csokbiz;
    private EditText tartalekos;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTartalekosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        torles=binding.torles;
        csokbiz=binding.csokbiz;
        tartalekos=binding.tartalekos;

        sharedPref = this.getSharedPreferences("kom", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        tartalekos.setText(sharedPref.getString("tartalekos",""));

        torles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tartalekos.setText("");
            }
        });

        csokbiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Tartalekos.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        tartalekos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("tartalekos",charSequence.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}