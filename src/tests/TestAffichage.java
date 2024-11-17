package tests;

import io.LecteurDonnees;
import classes.DonneesSimulation;
import java.awt.Color;
import Simulation.*;

import gui.GUISimulator;

public class TestAffichage {
    public static void tester(GUISimulator gui, String fichier) {
        DonneesSimulation donnees = LecteurDonnees.creeDonnees(fichier);
        Simulateur evenements = new Simulateur(donnees);
        SimulationVersion1 simulateur = new SimulationVersion1(gui,donnees,evenements,fichier,TestType.Affichage);
    }
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        GUISimulator gui = new GUISimulator(800, 600, Color.black);
        tester(gui,args[0]);
    }
}

