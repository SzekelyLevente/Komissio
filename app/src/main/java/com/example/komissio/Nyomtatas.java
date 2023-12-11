package com.example.komissio;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komissio.databinding.ActivityNyomtatasBinding;

import java.util.Timer;
import java.util.TimerTask;

public class Nyomtatas extends Activity {

    private ActivityNyomtatasBinding binding;
    private ImageView nyomtato;
    private Button vissza, beallit;
    private TextView nyomtatoSzam;
    private EditText nyomtatoInput;

    private IRepository repo;
    private ILogic logic;

    private Tartalycimke tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNyomtatasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nyomtato=findViewById(R.id.nyomtato);
        vissza=findViewById(R.id.vissza);
        beallit=findViewById(R.id.beallit);
        nyomtatoSzam=findViewById(R.id.nyomtatoSzam);
        nyomtatoInput=findViewById(R.id.nyomtatoInput);

        this.repo=new Repository(Nyomtatas.this);
        this.logic=new Logic(repo);

        init();

        beallit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nyomtatoInput.getText().toString().equals(""))
                {
                    logic.Update("nyomtato",nyomtatoInput.getText().toString());
                    init();
                    Toast.makeText(Nyomtatas.this,"Nyomtató száma átállítva!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Nyomtatas.this,"Írjon be egy értéket!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Nyomtatas.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void init()
    {
        if(!logic.Contains("nyomtato"))
        {
            logic.Update("nyomtato","nyomtato");
        }

        String nyomtato=logic.Read("nyomtato");
        tc=new Tartalycimke(nyomtato);
        this.nyomtato.setImageBitmap(tc.createBitmap());
        nyomtatoSzam.setText(nyomtato);
    }
}