package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import classes.*;
import classes.Robots.*;
import classes.NatureTerrain;
/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {

    public static DonneesSimulation creeDonnees(String fichierDonnees) {
        DonneesSimulation donnees = new DonneesSimulation();
        try {
            lire(fichierDonnees, donnees);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + fichierDonnees + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + fichierDonnees + " invalide: " + e.getMessage());
        }
        return donnees;
    }

    public static void lire(String fichierDonnees, DonneesSimulation donnees)
        throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier " + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        lecteur.lireCarte(donnees);
        lecteur.lireIncendies(donnees);
        lecteur.lireRobots(donnees);
        scanner.close();
        System.out.println("\n == Lecture terminee");
    }

    private static Scanner scanner;

    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }
    private void lireCarte(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
          int tailleCases = scanner.nextInt();

            System.out.println("Carte " + nbLignes + "x" + nbColonnes + "; taille des cases = \n" + tailleCases);
            donnees.creerCarte(nbColonnes, nbLignes, tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    Case cell = lireCase(lig, col);
                    donnees.getCarte().setCase(lig, col, cell);
                }
            }
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. Attendu: nbLignes nbColonnes tailleCases");
        }
    }

    /**
     * Lit et affiche les donnees d'une case.
     */
    private Case lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);
            return new Case(lig, col, nature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }


    }
    /**
     * Lit et initialise les données des incendies dans DonneesSimulation.
     */
    private void lireIncendies(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            donnees.creerIncendies(nbIncendies); // Initialise l'espace pour les incendies dans DonneesSimulation

            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i, donnees);
            }
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. Attendu: nbIncendies");
        }
    }

    /**
     * Lit et initialise le i-ème incendie dans DonneesSimulation.
     * @param i Index de l'incendie
     */
    /**
     * Lit et initialise le i-ème incendie dans DonneesSimulation.
     * @param i Index de l'incendie
     */
    private void lireIncendie(int i, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int litre_eau = scanner.nextInt();

            if (litre_eau <= 0) {
                throw new DataFormatException("L'intensité de l'incendie (litres d'eau) " + i + " doit être > 0");
            }
            verifieLigneTerminee();

            // Obtient la case correspondante sur la carte
            Case position = donnees.getCarte().getCase(lig, col);

            // Crée un nouvel incendie en utilisant `litre_eau` et `position`
            Incendie incendie = new Incendie(litre_eau, position,"assets/fire.png");

            // Ajoute l'incendie dans DonneesSimulation
            donnees.setIncendie(i, incendie);

            System.out.println("position = (" + lig + "," + col + "); intensité = " + litre_eau);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide pour l'incendie. Attendu: ligne colonne intensité");
        }
    }


    /**
     * Lit et initialise les données des robots dans DonneesSimulation.
     */
    private void lireRobots(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            donnees.creerRobots(nbRobots); // Initialise l'espace pour les robots dans DonneesSimulation

            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i, donnees);
            }
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. Attendu: nbRobots");
        }
    }

    /**
     * Lit et initialise le i-ème robot dans DonneesSimulation.
     * @param i Index du robot
     */
    private void lireRobot(int i, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        //.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + "); ");
            String type = scanner.next();
            System.out.print("type = " + type + "; ");

            // Lecture optionnelle de la vitesse
            String s = scanner.findInLine("(\\d+(\\.\\d+)?)"); // Lecture de la vitesse (entier ou flottant)
            double vitesse = (s != null) ? Double.parseDouble(s) : getDefaultVitesse(type);
            System.out.print("vitesse = " + vitesse);

            verifieLigneTerminee();

            // Obtient la position de départ sur la carte
            Case position = donnees.getCarte().getCase(lig, col);

            // Crée le robot en fonction du type
            Robot robot;
            if (type.equalsIgnoreCase("drone")) {
                robot = new Drone(position, vitesse, 10000,10000); // Exemple de capacité de réservoir
            } else if (type.equalsIgnoreCase("roues")) {
                robot = new RobotRoues(position, vitesse, 5000,5000); // Exemple de robot à roues
            } else if (type.equalsIgnoreCase("pattes")) {
                robot = new RobotPattes(position, vitesse,100000,100000); // RobotPattes sans réservoir
            } else if (type.equalsIgnoreCase("chenilles")){
                robot = new RobotChenilles(position,vitesse,2000,2000);
            }
            else {
                throw new DataFormatException("Type de robot inconnu : " + type);
            }

            // Ajoute le robot dans DonneesSimulation
            donnees.setRobot(i, robot);

            //.out.println();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide pour le robot. Attendu: ligne colonne type [vitesse]");
        }
    }


    /**
     * Retourne une vitesse par défaut pour un type de robot donné.
     */
    private double getDefaultVitesse(String type) {
        switch (type.toLowerCase()) {
            case "drone":
                return 100.0; // Vitesse par défaut pour un drone
            case "robotroues":
                return 80.0; // Vitesse par défaut pour un robot à roues
            case "robotpattes":
                return 30.0; // Vitesse par défaut pour un robot à pattes
            case "robotchenilles":
                return 60.0;
            default:
                return 50.0; // Vitesse par défaut pour d'autres types
        }
    }

    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
