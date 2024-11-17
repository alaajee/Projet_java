package classes.Evenements;

/* Classe abstraite d'evenement */

public abstract class Evenement{
    private final long date;
    public Evenement(long date){
        this.date = date;
    }
    public String getDate_String(){
        return "Date " + date;
    }
    public abstract void execute();
    public long getDate(){
        return date;
    }
}