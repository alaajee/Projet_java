
package classes.OptimalPath;

import java.util.*;
import classes.Case;

public class Dijkstra {
    private Graphe graphe;

    public Dijkstra(Graphe graphe) {
        this.graphe = graphe;
    }

    public ResultatChemin calculerPlusCourtChemin(Case depart, Case destination) {
        // Initialisation des structures de données pour Dijkstra
        Map<Case, Double> distances = new HashMap<>();
        Map<Case, Case> predecesseurs = new HashMap<>();
        PriorityQueue<Case> file = new PriorityQueue<>(Comparator.comparing(distances::get));

        // Initialiser les distances de chaque case à l'infini, sauf pour la case de départ
        for (Case c : graphe.getCases()) {
            distances.put(c, Double.POSITIVE_INFINITY);
        }
        distances.put(depart, 0.0);
        file.add(depart);

        // Algorithme de Dijkstra
        while (!file.isEmpty()) {
            Case current = file.poll();
            if (current.equals(destination)) break;

            for (Arete arete : graphe.getAretes(current)) {
                Case voisin = arete.getTo();

                // Inclure la destination même si elle n'est pas traversable
                if (!distances.containsKey(voisin) && !voisin.equals(destination)) {
                    continue;
                }

                double nouvelleDistance = distances.get(current) + arete.getPoids();
                if (nouvelleDistance < distances.get(voisin)) {
                    distances.put(voisin, nouvelleDistance);
                    predecesseurs.put(voisin, current);
                    file.add(voisin);
                }
            }
        }

        // Reconstruire le chemin depuis la destination vers le départ
        LinkedList<Case> chemin = new LinkedList<>();
        LinkedList<Double> tempsParEtape = new LinkedList<>();
        Case step = destination;

        if (predecesseurs.containsKey(step) || step.equals(depart)) {
            while (step != null) {
                chemin.addFirst(step);
                Case precedent = predecesseurs.get(step);
                if (precedent != null) {
                    // Récupérer le poids de l'arête entre `precedent` et `step`
                    double temps = 0.0;
                    for (Arete arete : graphe.getAretes(precedent)) {
                        if (arete.getTo().equals(step)) {
                            temps = arete.getPoids();
                            break;
                        }
                    }
                    tempsParEtape.addFirst(temps); // Ajouter le poids pour chaque étape
                }
                step = precedent;
            }
        }

        double tempsTotal = distances.get(destination);
        return new ResultatChemin(chemin, tempsTotal, tempsParEtape);
    }
}
