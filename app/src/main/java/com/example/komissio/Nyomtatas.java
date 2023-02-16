package com.example.komissio;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komissio.databinding.ActivityNyomtatasBinding;

import java.util.Timer;
import java.util.TimerTask;

public class Nyomtatas extends Activity {

    private ActivityNyomtatasBinding binding;
    private ImageView tartalycimke;
    private Button vissza;
    private TextView szam;

    private String[] tartalycimkek;
    private String akt;
    private int index;
    private Handler timerHandler;
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNyomtatasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tartalycimke=binding.tartalycimke;
        vissza=binding.vissza;
        szam=binding.szam;

        tartalycimkek=getIntent().getStringArrayExtra("tartalycimkek");
        index=tartalycimkek.length;
        //init(index);

        timerHandler = new Handler();
        timerRunnable = new Runnable() {

            @Override
            public void run() {
                if(index==0)
                {
                    timerHandler.removeCallbacks(timerRunnable);
                    Toast.makeText(Nyomtatas.this,"Nyomtatás vége",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    index--;
                    init(index);
                    timerHandler.postDelayed(this, 2000);
                }
            }
        };
        timerHandler.postDelayed(timerRunnable, 0);

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerHandler.removeCallbacks(timerRunnable);
                Intent i=new Intent(Nyomtatas.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void init(int index)
    {
        akt=tartalycimkek[index];
        Tartalycimke tc=new Tartalycimke(akt);
        tartalycimke.setImageBitmap(tc.createBitmap());
        szam.setText(tc.getSzam()+"");
    }
}