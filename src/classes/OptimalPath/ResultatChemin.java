package classes.OptimalPath;

import classes.Case;


import java.util.LinkedList;

public class ResultatChemin {
    private LinkedList<Case> chemin;
    private double tempsTotal;
    private LinkedList<Double> tempsParEtape; // Nouvelle liste pour stocker les temps par étape

    public ResultatChemin(LinkedList<Case> chemin, double tempsTotal, LinkedList<Double> tempsParEtape) {
        this.chemin = chemin;
        this.tempsTotal = tempsTotal;
        this.tempsParEtape = tempsParEtape; // Initialisation de la liste
    }

    public LinkedList<Case> getChemin() {
        return chemin;
    }

    public double getTempsTotal() {
        return tempsTotal;
    }

    public LinkedList<Double> getTempsParEtape() {
        return tempsParEtape; // Nouvelle méthode pour obtenir les temps par étape
    }
}