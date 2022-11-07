package com.example.komissio;

import java.util.ArrayList;

public class Zona {

    private String rendelesek;
    private String nev;

    public Zona(String nev)
    {
        this.rendelesek="";
        this.nev=nev;
    }

    public void torles()
    {
        rendelesek="";
    }

    public void modosit(String s)
    {
        rendelesek=s;
    }
}
