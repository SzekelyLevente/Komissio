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
    private Button torles,tart,tartzona,ujkor;
    private EditText csokbiz,kezdocimke,db;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    //private ImageView qr;
    private LinearLayout tartalycimkek;
    private ArrayList<Tartalycimke> tartcimkek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        torles=binding.torles;
        tart=binding.tart;
        tartzona=binding.tartzona;
        csokbiz=binding.csokbiz;
        tartalycimkek=binding.tartalycimkek;
        kezdocimke=binding.kezdocimke;
        ujkor=binding.ujkor;
        db=binding.db;

        tartcimkek=new ArrayList<>();

        sharedPref = this.getSharedPreferences("kom",Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        csokbiz.setText(sharedPref.getString("csokibiz",""));
        db.setText(sharedPref.getString("db",""));

        /*
        editor.putString("kezdocimke","56675401");
        editor.apply();

         */

        if(sharedPref.contains("kezdocimke") && sharedPref.contains("db"))
        {
            tartalyGyartas(sharedPref.getString("kezdocimke",""),Integer.parseInt(db.getText().toString()));
        }

        torles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                csokbiz.setText("");
            }
        });

        tart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Tartalekos.class);
                startActivity(i);
                finish();
            }
        });

        tartzona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TartalekosZona.class);
                startActivity(i);
                finish();
            }
        });

        csokbiz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("csokibiz", charSequence.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ujkor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!kezdocimke.getText().toString().equals("") || !db.getText().toString().equals(""))
                {
                    editor.putString("kezdocimke", kezdocimke.getText().toString());
                    editor.putString("db",db.getText().toString());
                    editor.apply();
                    csokbiz.setText("");
                    tartalyGyartas(kezdocimke.getText().toString(), Integer.parseInt(db.getText().toString()));
                    kezdocimke.setText("");
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Add meg az adatokat!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void tartalyGyartas(String kc, int db)
    {
        //String kc=kezdocimke.getText().toString();
        int pontutani=Integer.parseInt(kc.charAt(kc.length()-1)+"");
        int pontelotti=Integer.parseInt(kc.substring(0,kc.length()-1));
        tartalycimkek.removeAllViews();
        tartcimkek.clear();
        for (int i=0; i<db; i++)
        {
            Tartalycimke tc=new Tartalycimke("9400"+pontelotti+""+pontutani);
            tartcimkek.add(tc);
            ImageView iv=new ImageView(MainActivity.this);
            TextView t=new TextView(MainActivity.this);
            t.setText("94.00"+pontelotti+"."+pontutani);
            t.setGravity(Gravity.CENTER);
            iv.setImageBitmap(tc.getQr());
            int i2=i;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    csokbiz.setText(csokbiz.getText().toString()+""+i2);
                    Toast.makeText(MainActivity.this,"Hozzáadva: "+i2,Toast.LENGTH_SHORT).show();
                }
            });
            tartalycimkek.addView(iv);
            tartalycimkek.addView(t);
            pontelotti++;
            pontutani-=3;
            if (pontutani<0)
            {
                pontutani+=10;
            }
        }
    }
}