package classes;
import classes.OptimalPath.*;

import java.util.LinkedList;

public abstract class Robot {
    protected Case position;
    protected String imagePath;
    protected int CapaciteResevoire;
    public int date;
    public int tempsRemplissage;
    public int tempsVersement;
    public Robot(Case position, String imagePath, int CapaciteResevoire) {
        this.position = position;
        this.imagePath = imagePath;
        this.CapaciteResevoire = CapaciteResevoire;
        this.date = 0;
    }
    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date ;
    }
    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) {
        this.position.setLigne(position.getLigne()) ;
        this.position.setColonne(position.getColonne());
    }

    public int getCapaciteResevoire() {

        return CapaciteResevoire;
    }

    public abstract double getVitesse(NatureTerrain nature);
    public abstract void deverserEau(int vol);
    public abstract void remplirReservoir();
    public abstract int getVolumeEau();
    public abstract int getTempsRemplissage();
    public abstract int getTempsVersement();
    public abstract boolean estTraversable(Case c);

    public LinkedList<Case> getChemin(Case destination, Graphe graphe) {
        Dijkstra dijkstra = new Dijkstra(graphe);
        ResultatChemin resultat = dijkstra.calculerPlusCourtChemin(this.position, destination);
        return new LinkedList<>(resultat.getChemin());
    }
    public double getTempsTotal(Case destination, Graphe graphe) {
        Dijkstra dijkstra = new Dijkstra(graphe);
        ResultatChemin resultat = dijkstra.calculerPlusCourtChemin(this.position, destination);
        return resultat.getTempsTotal();
    }

    // Méthode pour obtenir le chemin de l'image
    public String getImagePath() {

        return imagePath;
    }
    public  boolean IncendieAccessible(Incendie c,Graphe graphe){
        Case ca = c.getPosition();
        LinkedList<Case> liste = getChemin(ca, graphe);
        Case finale = liste.peekLast();
        return finale != null && finale.equals(ca);
    }
    public LinkedList<Double> getTempsParEtape(Case destination, Graphe graphe) {
        Dijkstra dijkstra = new Dijkstra(graphe);
        ResultatChemin resultat = dijkstra.calculerPlusCourtChemin(this.position, destination);
        return resultat.getTempsParEtape(); // Obtenir les temps par étape
    }

}