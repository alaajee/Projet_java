package tests;
import classes.*;
import classes.Evenements.*;
import classes.Robot;
import gui.GUISimulator;
import io.LecteurDonnees;
import Simulation.*;

import java.awt.*;
import java.util.LinkedHashMap;


public class TestScenario {
    public static void tester(GUISimulator gui,String fichier) {
        // Scenario 0
        DonneesSimulation donnees = LecteurDonnees.creeDonnees(fichier);
        Simulateur simulateur = new Simulateur(donnees);
        LinkedHashMap<Incendie, Robot> incendiesRob = new LinkedHashMap<>();
        try {
            Robot robot = donnees.getRobots(0);
            Case position = robot.getPosition();
            int ligne = position.getLigne();
            int colonne = position.getColonne();
            for (int k=0;k<4;k++){

                if (ligne < 0){
                    simulateur.ajouteEvenement(new Deplacement(donnees,0,0,ligne - 1 , colonne));
                    ligne =  ligne - 1;
                    System.out.println(donnees.getRobots(0).getPosition().getLigne());
                    System.out.println(ligne);
                }
                else {
                    throw new Exception("This is a forced exception.");
                }
            }
            SimulationVersion1 simulateur1 = new SimulationVersion1(gui,donnees,simulateur,fichier,TestType.Scenario);
        }catch (Exception e){
            Robot robot = donnees.getRobots(1);
            Case position = robot.getPosition();
            int ligne = position.getLigne();
            int colonne = position.getColonne();
            simulateur.ajouteEvenement(new Deplacement(donnees,0,1,ligne - 1 , colonne));
            ligne =  ligne - 1;
            simulateur.ajouteEvenement(new Versement(donnees,1,1,5000 ,4,incendiesRob));
            simulateur.ajouteEvenement(new Deplacement(donnees,1,1,ligne  , colonne - 1 ));
            colonne = colonne - 1;
            simulateur.ajouteEvenement(new Deplacement(donnees,2,1,ligne , colonne - 1));
            simulateur.ajouteEvenement(new Remplissage(donnees,2,1));
            simulateur.ajouteEvenement(new Deplacement(donnees,2,1,ligne  , colonne + 1 ));
            colonne = colonne + 1;
            simulateur.ajouteEvenement(new Deplacement(donnees,3,1,ligne , colonne + 1));
            simulateur.ajouteEvenement(new Versement(donnees,3,1,3000  ,4,incendiesRob));
            simulateur.ajouteEvenement(new Deplacement(donnees,3,1,ligne + 1 , colonne + 1));

            SimulationVersion1 simulateur1 = new SimulationVersion1(gui,donnees,simulateur,fichier,TestType.Scenario);
        }
    }
    public static void main(String[] args){
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        GUISimulator gui = new GUISimulator(800, 600, Color.black);
        tester(gui,args[0]);
    }
}