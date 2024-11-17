package classes.OptimalPath;

import classes.*;
import classes.Robots.*;
import java.util.*;

public class Graphe {
    private Carte carte;
    private Robot robot; // Le robot spécifique pour lequel le graphe est construit
    private Map<Case, List<Arete>> adjacences;

    public Graphe(Carte carte, Robot robot) {
        this.carte = carte;
        this.robot = robot;
        this.adjacences = new HashMap<>();
        construireGraphe();
    }

    private void construireGraphe() {
        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                Case currentCase = carte.getCase(i, j);
                adjacences.putIfAbsent(currentCase, new ArrayList<>());
                ajouterAretes(currentCase);
            }
        }
    }

    private void ajouterAretes(Case src) {
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(src, dir)) {
                Case voisin = carte.getVoisin(src, dir);
    
                // Vérifier si la case source ou la case voisine est infranchissable pour le robot
                if (!robot.estTraversable(src) || !robot.estTraversable(voisin)) {
                    continue; // Ignorer cette arête si l'une des cases est infranchissable
                }
    
                // Vérifier que les cases sont adjacentes (différence d'une ligne ou d'une colonne, mais pas les deux)
                int diffLignes = Math.abs(src.getLigne() - voisin.getLigne());
                int diffColonnes = Math.abs(src.getColonne() - voisin.getColonne());
                if ((diffLignes > 1 || diffColonnes > 1) || (diffLignes == 1 && diffColonnes == 1)) {
                    continue; // Ignorer cette arête si les cases ne sont pas adjacentes
                }
    
                // Si les deux cases sont traversables et adjacentes, calculer le poids et ajouter l'arête
                double poids = calculerPoids(src, voisin);
                adjacences.get(src).add(new Arete(src, voisin, poids));
            }
        }
    }
    
    private double calculerPoids(Case src, Case voisin) {
        // Utiliser la vitesse du robot sur chaque case
        double vitesseSrc = robot.getVitesse(src.getNature());
        double vitesseVoisin = robot.getVitesse(voisin.getNature());


        // Calculer le poids comme le temps de déplacement entre deux cases
        double vitesseMoyenne = (vitesseSrc + vitesseVoisin) / 2;
        return carte.getTailleCases() * vitesseMoyenne; // poids = taille * vitesse
    }

    public List<Arete> getAretes(Case cellule) {
        return adjacences.get(cellule);
    }
    

    public Set<Case> getCases() {
        return adjacences.keySet();
    }
    public void printAllCases() {
        System.out.println("Liste des cases dans le graphe :");
        for (Case c : getCases()) {
            System.out.println("Case (" + c.getLigne() + ", " + c.getColonne() + ") - Nature : " + c.getNature());
        }
    }
    
    public void printAllAretes() {
        System.out.println("Liste des arêtes dans le graphe :");
        for (Map.Entry<Case, List<Arete>> entry : adjacences.entrySet()) {
            Case src = entry.getKey();
            List<Arete> aretes = entry.getValue();
    
            for (Arete arete : aretes) {
                System.out.println(
                    "Arête : (" + arete.getFrom().getLigne() + ", " + arete.getFrom().getColonne() + ") -> ("
                    + arete.getTo().getLigne() + ", " + arete.getTo().getColonne() + ") - Poids : " + arete.getPoids()
                );
            }
        }
    }
    
}
