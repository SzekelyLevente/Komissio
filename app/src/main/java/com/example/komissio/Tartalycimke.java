package com.example.komissio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.lang.UCharacter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Tartalycimke extends LinearLayout{

    private ImageView iv;
    private TextView tv;
    private String ertek;

    public Tartalycimke(String ertek,String szoveg,Context context)
    {
        super(context);
        this.ertek=ertek;
        this.setOrientation(LinearLayout.VERTICAL);
        iv=new ImageView(context);
        tv=new TextView(context);
        tv.setText(szoveg);
        tv.setGravity(Gravity.CENTER);
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(ertek, BarcodeFormat.QR_CODE, 512, 512);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(mBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        this.addView(iv);
        this.addView(tv);

    }

    public ImageView getIv() {
        return this.iv;
    }

    public TextView getTv() {
        return this.tv;
    }

    public String getErtek() {
        return ertek;
    }
}
