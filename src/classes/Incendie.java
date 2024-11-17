package classes;

import classes.OptimalPath.Dijkstra;
import classes.OptimalPath.Graphe;
import classes.OptimalPath.ResultatChemin;

import java.util.LinkedList;

public class Incendie {
    private final Case position;
    private int litre_eau;
    protected String imagePath;
    public Incendie(int litre_eau ,Case position,String imagePath ) {
        if (position.getColonne() <0 || position.getLigne() < 0){
            throw new IllegalArgumentException("Il n'y a plus de colonne");
        }
        this.litre_eau = litre_eau;
        this.position = position;
        this.imagePath = "assets/fire.png";
    }
    public Case getPosition() {
        return position;
    }
    public int getLitre_eau(){
        return litre_eau;
    }
    public void setLitre_eau(int litre_verse){
        this.litre_eau -= litre_verse;
    }
    public String getImagepath(){
        return imagePath;
    }
    public LinkedList<Case> getChemin(Case destination, Graphe graphe) {
        Dijkstra dijkstra = new Dijkstra(graphe);
        ResultatChemin resultat = dijkstra.calculerPlusCourtChemin(this.position, destination);
        return new LinkedList<>(resultat.getChemin());
    }
    public LinkedList<Double> getTempsParEtape(Case destination, Graphe graphe) {
        Dijkstra dijkstra = new Dijkstra(graphe);
        ResultatChemin resultat = dijkstra.calculerPlusCourtChemin(this.position, destination);
        return resultat.getTempsParEtape(); // Obtenir les temps par étape
    }
}
