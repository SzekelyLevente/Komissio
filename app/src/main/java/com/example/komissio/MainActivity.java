package com.example.komissio;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
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
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

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
    private Button torles,ujkor,nevjegy,nyomtatas;
    private EditText kezdocimke,db;
    private TextView t1,t2,t3,t4,t5,t6;
    private LinearLayout tartalycimkek;

    private String csokbizs;
    private ArrayList<Tartalycimke> tcimkek;
    
    private IRepository repository;
    private ILogic logic;

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    private String[] doboz;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        torles=binding.torles;
        tartalycimkek=binding.tartalycimkek;
        kezdocimke=binding.kezdocimke;
        ujkor=binding.ujkor;
        db=binding.db;
        nevjegy=binding.nevjegy;
        nyomtatas=binding.nyomtatas;
        doboz=new String[6];

        t1=binding.t1;
        t2=binding.t2;
        t3=binding.t3;
        t4=binding.t4;
        t5=binding.t5;
        t6=binding.t6;

        repository=new Repository(this);
        logic=new Logic(repository);

        tcimkek=new ArrayList<>();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        width = size.x;

        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Biztosan törölni szeretnél?");
        builder.setCancelable(false);
        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                csokbizTorles();
            }
        });
        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog=builder.create();

        csokbizs= logic.Read("csokibiz");
        //csokbiz.setText(csokbizs);
        csokbizBetoltes(csokbizs);
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
                alertDialog.show();
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
                        csokbizTorles();
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

        nyomtatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] tartalycimkek=new String[tcimkek.size()];
                for (int i=0;i<tcimkek.size();i++)
                {
                    tartalycimkek[i]=tcimkek.get(i).getErtek();
                }
                Intent i=new Intent(MainActivity.this,Nyomtatas.class);
                i.putExtra("tartalycimkek",tartalycimkek);
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
            LinearLayout ll=null;
            if(i%2==0)
            {
                ll=new LinearLayout(MainActivity.this);
                ll.setId(i/2);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                tartalycimkek.addView(ll);
            }
            else
            {
                ll=findViewById(i/2);
            }
            ll.addView(gombGyartas(i2,i));
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
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(width/2),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(lp);
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

    public void csokbizBetoltes(String csokbiz)
    {
        for (int i=0;i<6;i++)
        {
            if(i<csokbiz.length())
            {
                doboz[i]=csokbiz.charAt(i)+"";
                switch (i)
                {
                    case 0:
                        t1.setText(csokbiz.charAt(i)+"");
                        break;
                    case 1:
                        t2.setText(csokbiz.charAt(i)+"");
                        break;
                    case 2:
                        t3.setText(csokbiz.charAt(i)+"");
                        break;
                    case 3:
                        t4.setText(csokbiz.charAt(i)+"");
                        break;
                    case 4:
                        t5.setText(csokbiz.charAt(i)+"");
                        break;
                    case 5:
                        t6.setText(csokbiz.charAt(i)+"");
                        break;
                }
            }
        }
    }

    public void csokbizTorles()
    {
        for (int i=0;i<doboz.length;i++)
        {
            doboz[i]=null;
        }
        logic.Update("csokibiz","");
        t1.setText("n");
        t2.setText("n");
        t3.setText("n");
        t4.setText("n");
        t5.setText("n");
        t6.setText("n");
    }
}