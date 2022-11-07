package com.example.komissio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.komissio.databinding.ActivityNevjegyBinding;

public class Nevjegy extends Activity {

    private ActivityNevjegyBinding binding;
    private Button vissza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNevjegyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vissza=binding.vissza;

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Nevjegy.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}