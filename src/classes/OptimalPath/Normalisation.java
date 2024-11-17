
package classes.OptimalPath;

import java.util.LinkedList;

public class Normalisation {

    private LinkedList<Double> data;
    private LinkedList<Double> normalizedData;

    public Normalisation(LinkedList<Double> data) {
        this.data = data;
        this.normalizedData = new LinkedList<>();
        normalize();
    }

    // Méthode pour effectuer la normalisation
    private void normalize() {
        if (data.isEmpty()) {
            //.out.println("La liste est vide. Rien à normaliser.");
            return;
        }
    
        // Trouver les valeurs minimale et maximale
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
    
        for (double value : data) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
    
        // Si toutes les valeurs sont identiques, normaliser toutes les valeurs à 10
        if (max == min) {
            for (double value : data) {
                normalizedData.add(1.0); // Toutes les valeurs reçoivent le même score
            }
            return;
        }
    
        // Normaliser les données
        for (double value : data) {
            double normalizedValue = ((value - min) / (max - min)) * 10;
            normalizedData.add(normalizedValue);
        }
    }
    

    // Méthode pour récupérer les données normalisées
    public LinkedList<Double> getNormalizedData() {
        return normalizedData;
    }

    // Méthode pour afficher les données normalisées
    public void printNormalizedData() {
        //.out.println("Données normalisées : " + normalizedData);
    }
    public Double getElementAtIndex(int index) {
        if (index < 0 || index >= normalizedData.size()) {
            throw new IndexOutOfBoundsException("Indice hors limites : " + index);
        }
        return normalizedData.get(index);
    }
}
