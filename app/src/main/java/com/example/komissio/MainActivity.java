package com.example.komissio;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.komissio.databinding.ActivityMainBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;
    private Button torles,ujkor,nevjegy;
    private EditText csokbiz,kezdocimke,db;
    private LinearLayout tartalycimkek;

    private String csokbizs;
    private String tarts;
    private ArrayList<Tartalycimke> tcimkek;
    
    private IRepository repository;
    private ILogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        torles=binding.torles;
        csokbiz=binding.csokbiz;
        tartalycimkek=binding.tartalycimkek;
        kezdocimke=binding.kezdocimke;
        ujkor=binding.ujkor;
        db=binding.db;
        nevjegy=binding.nevjegy;

        repository=new Repository(this);
        logic=new Logic(repository);

        tcimkek=new ArrayList<>();

        csokbizs= logic.Read("csokibiz");
        csokbiz.setText(csokbizs);
        db.setText(logic.Read("db"));

        /*
        editor.putString("kezdocimke","56675401");
        editor.apply();

         */

        if(logic.Contains("kezdocimke") && logic.Contains("db"))
        {
            tartalyGyartas(logic.Read("kezdocimke"),Integer.parseInt(logic.Read("db")));
        }

        torles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                csokbiz.setText("");
            }
        });

        csokbiz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                csokbizs=charSequence.toString();
                logic.Update("csokibiz",csokbizs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ujkor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!kezdocimke.getText().toString().equals("") && !db.getText().toString().equals(""))
                {
                    try
                    {
                        logic.Update("kezdocimke",kezdocimke.getText().toString());
                        logic.Update("db",db.getText().toString());
                        csokbiz.setText("");
                        tartalyGyartas(kezdocimke.getText().toString(), Integer.parseInt(db.getText().toString()));
                        kezdocimke.setText("");
                    }
                    catch(Exception ex)
                    {
                        Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Add meg az adatokat!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        nevjegy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Nevjegy.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void tartalyGyartas(String kc, int db)
    {
        //String kc=kezdocimke.getText().toString();
        int pontutani=Integer.parseInt(kc.charAt(kc.length()-1)+"");
        int pontelotti=Integer.parseInt(kc.substring(0,kc.length()-1));
        tartalycimkek.removeAllViews();
        tcimkek.clear();
        for (int i=0; i<db; i++)
        {
            Tartalycimke tc=new Tartalycimke("9400"+pontelotti+""+pontutani);
            char rszam=(pontelotti+"").charAt((pontelotti+"").length()-1);
            int i2=Integer.parseInt(rszam+"");
            tcimkek.add(tc);
            tartalycimkek.addView(gombGyartas(i2,i));
            pontelotti++;
            pontutani-=3;
            if (pontutani<0)
            {
                pontutani+=10;
            }
        }
    }

    public Button gombGyartas(int szam, int index)
    {
        Button btn=new Button(MainActivity.this);
        btn.setText(szam+"");
        btn.setGravity(Gravity.CENTER);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TartalycimkeView.class);
                i.putExtra("ertek",tcimkek.get(index).getErtek());
                i.putExtra("szam",szam);
                startActivity(i);
                finish();
            }
        });
        return btn;
    }
}