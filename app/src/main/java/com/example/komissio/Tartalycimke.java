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

public class Tartalycimke{

    private String ertek;

    public Tartalycimke(String ertek)
    {
        this.ertek=ertek;
    }

    public ImageView createQR(Context ctx)
    {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(ertek, BarcodeFormat.QR_CODE, 512, 512);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(bitMatrix);
            ImageView iv=new ImageView(ctx);
            iv.setImageBitmap(mBitmap);
            return iv;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap createBitmap()
    {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(ertek, BarcodeFormat.QR_CODE, 512, 512);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(bitMatrix);
            return mBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getSzam()
    {
        int szam=Integer.parseInt(""+ertek.charAt(ertek.length()-2));
        return szam;
    }

    public String getErtek() {
        return ertek;
    }
}
