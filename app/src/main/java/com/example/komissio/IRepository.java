package com.example.komissio;

public interface IRepository {
    String Read(String kulcs);
    void Update(String kulcs, String ertek);
}
