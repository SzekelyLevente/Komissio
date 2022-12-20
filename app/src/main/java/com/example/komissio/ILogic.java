package com.example.komissio;

public interface ILogic {
    String Read(String kulcs);
    void Update(String kulcs, String ertek);
    boolean Contains(String kulcs);
}
