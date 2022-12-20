package com.example.komissio;

public class Logic implements ILogic{

    private IRepository repo;

    public Logic(IRepository repo) {
        this.repo = repo;
    }

    @Override
    public String Read(String kulcs) {
        return this.repo.Read(kulcs);
    }

    @Override
    public void Update(String kulcs, String ertek){
        if (kulcs=="kezdocimke" && ertek.length()!=8)
        {
            throw new IllegalArgumentException("8 karakter kell!!");
        }
        this.repo.Update(kulcs,ertek);
    }

    @Override
    public boolean Contains(String kulcs) {
        try
        {
            this.repo.Read(kulcs);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
