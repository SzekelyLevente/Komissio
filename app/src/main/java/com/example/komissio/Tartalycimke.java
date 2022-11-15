package com.example.komissio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Tartalycimke {

    private Bitmap qr;
    private String ertek;

    public Tartalycimke(String ertek)
    {
        this.ertek=ertek;
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(ertek, BarcodeFormat.QR_CODE, 256, 256);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(bitMatrix);//creating bitmap of code
            this.qr=mBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getQr() {
        return qr;
    }

    public String getErtek() {
        return ertek;
    }


}
