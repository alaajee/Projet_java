package Simulation;

import classes.Robot;
import classes.Evenements.*;
import classes.*;
import tests.TestSimulation;

import java.util.*;

import gui.GUISimulator;
import gui.Simulable;

// La classe simulation principale qui permet à chaque next de chercher des robots libres et les affecter à des incendies

public class SimulationNaive implements Simulable {
    private final GUISimulator gui;
    private final DonneesSimulation donnees;
    private final SimulationRenderer renderer;
    private final Simulateur simulateur;
    private final String fichier;
    private final LinkedHashMap<Incendie, Robot> incendiesRob ;
    private final ChefPompierNaif chefPompier;

    public SimulationNaive(GUISimulator gui, DonneesSimulation donnees,Simulateur simulateur,String fichier, LinkedHashMap<Incendie, Robot> incendiesRob,ChefPompierNaif chefPompier ) {
        this.gui = gui;
        this.donnees = donnees;
        this.renderer = new SimulationRenderer(gui, donnees);
        this.simulateur = simulateur;
        this.fichier = fichier;
        this.incendiesRob = incendiesRob;
        this.chefPompier = chefPompier;
        gui.setSimulable(this);
        renderer.drawScene();
    }


    @Override
    public void next() {
        if ( donnees.getNbrIncendies() > 0) {
            for (int i=0;i<donnees.getNbrRobots();i++){
                if (chefPompier.Affecter_Robot()) {
                    LinkedList<Evenement> evenements = chefPompier.RobotChemin(incendiesRob,  incendiesRob.size()-1);
                    LinkedList<Evenement> evenements1 = chefPompier.RobotRemplissage(incendiesRob, incendiesRob.size()-1);
                    LinkedList<Evenement> evenements2 = chefPompier.RobotRemplir(incendiesRob,incendiesRob.size()-1);
                    simulateur.ajouterEvenements(evenements);
                    simulateur.ajouterEvenements(evenements2);
                    simulateur.ajouterEvenements(evenements1);
                }
            }
            if (!simulateur.simulationTerminee()) {
                simulateur.executeEvenements();
                simulateur.incrementDateSimulation();
                renderer.drawScene(); // Met à jour l'affichage
            }
        }
        else {
            System.out.println();
        }
    }

    @Override
    public void restart() {
        simulateur.restart();
        TestSimulation.tester(fichier,gui);
    }
}