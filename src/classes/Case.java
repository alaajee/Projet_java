package classes;

public class Case {
    private int ligne ;
    private int colonne ;
    private final NatureTerrain nature;

    public Case(int line , int colon , NatureTerrain nature){
        this.ligne = line;
        this.colonne = colon;
        this.nature = nature;
    }

    public int getLigne(){
        return this.ligne;
    }

    public int getColonne(){
        return this.colonne;
    }

    public NatureTerrain getNature(){
        return this.nature;
    }

    public void setLigne(int ligne){
        this.ligne = ligne;
    }

    public void setColonne(int colonne){
        this.colonne = colonne;
    }

}