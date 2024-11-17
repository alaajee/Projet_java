package tests;
import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import classes.DonneesSimulation;

public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            //.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            //.exit(1);
        }
        DonneesSimulation donnees = new DonneesSimulation();
        try {
            LecteurDonnees.lire(args[0],donnees);
        } catch (FileNotFoundException e) {
            //.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            //.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}

