package tests;

import classes.OptimalPath.*;
import classes.Robots.*;
import classes.*;
import java.util.LinkedList;

public class TestDjikstra {

    public static void main(String[] args) {
        int nbLignes = 5;
        int nbColonnes = 5;
        int tailleCases = 10; // Taille des cases
        Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);

        // Initialiser les cases avec des natures de terrain différentes
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                NatureTerrain nature;
                if (i == 1 && j == 1) {
                    nature = NatureTerrain.ROCHE; // Une case d'eau
                } else if (i == 2 && j == 2) {
                    nature = NatureTerrain.EAU; // Une case de forêt
                } else {
                    nature = NatureTerrain.TERRAIN_LIBRE; // Terrain libre pour le reste
                }
                carte.setCase(i, j, new Case(i, j, nature));
            }
        }

        // Position de départ et destination pour le test
        Case depart = carte.getCase(0, 0);
        Case destination = carte.getCase(4, 4);

        // Test avec un Drone
        Drone drone = new Drone(depart, 10.0, 100,100); // Exemple de vitesse et capacité pour le Drone
        Graphe grapheDrone = new Graphe(carte, drone); // Graphe spécifique pour le Drone
        System.out.println("Test pour le Drone:");
        testRobot(drone, destination, grapheDrone);

        // Test avec un Robot à Chenilles
        RobotChenilles robotChenilles = new RobotChenilles(depart, 8.0, 200,200);
        Graphe grapheChenilles = new Graphe(carte, robotChenilles); // Graphe spécifique pour le Robot à Chenilles
        System.out.println("\nTest pour le Robot à Chenilles:");
        testRobot(robotChenilles, destination, grapheChenilles);

        // Test avec un Robot à Pattes
        // RobotPattes robotPattes = new RobotPattes(depart, 6.0);
        // Graphe graphePattes = new Graphe(carte, robotPattes); // Graphe spécifique pour le Robot à Pattes
        // System.out.println("\nTest pour le Robot à Pattes:");
        // testRobot(robotPattes, destination, graphePattes);

        // Test avec un Robot à Roues
        RobotRoues robotRoues = new RobotRoues(depart, 12.0, 150,150);
        Graphe grapheRoues = new Graphe(carte, robotRoues); // Graphe spécifique pour le Robot à Roues
        System.out.println("\nTest pour le Robot à Roues:");
        testRobot(robotRoues, destination, grapheRoues);
    }

    private static void testRobot(Robot robot, Case destination, Graphe graphe) {
        // Calculer et afficher le temps total pour atteindre la destination
        // double tempsTotal = robot.getTempsTotal(destination, graphe);
        // //.out.println("Temps total pour atteindre la destination : " + tempsTotal);

        // Calculer et afficher le chemin emprunté par le robot
        LinkedList<Case> chemin = robot.getChemin(destination, graphe);
        System.out.print("Chemin emprunté par le robot : ");

        // Afficher chaque case comme un couple (i, j)
        chemin.forEach(c -> System.out.print("(" + c.getLigne() + ", " + c.getColonne() + ") "));
        System.out.println();
    }
}