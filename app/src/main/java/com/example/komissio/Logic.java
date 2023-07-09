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
        if(kulcs=="db" && (Integer.parseInt(ertek) < 1 || Integer.parseInt(ertek) > 10))
        {
            throw new IllegalArgumentException("Csak 1 és 10 közé eső számot adjon meg!");
        }
        this.repo.Update(kulcs,ertek);
    }

    @Override
    public boolean Contains(String kulcs) {
        String ertek=this.repo.Read(kulcs);
        return (ertek==""?false:true);
    }
}
