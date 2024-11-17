package tests;

import classes.*;
import classes.Robot;
import gui.GUISimulator;
import io.LecteurDonnees;
import Simulation.*;

import java.awt.*;
import java.util.LinkedHashMap;

public class TestSimulationNaif {
    public static void tester(String fichier ,GUISimulator gui){
        LinkedHashMap<Incendie, Robot> incendiesRob = new LinkedHashMap<>();
        DonneesSimulation donnees = LecteurDonnees.creeDonnees(fichier);
        Simulateur simulateur = new Simulateur(donnees);
        ChefPompierNaif chef = new ChefPompierNaif(donnees, incendiesRob);
        SimulationNaive simulation = new SimulationNaive(gui,donnees,simulateur,fichier,incendiesRob,chef);
    }
    public static void main(String[] args){
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        GUISimulator gui = new GUISimulator(800, 600, Color.black);
        tester(args[0],gui);
    }
}