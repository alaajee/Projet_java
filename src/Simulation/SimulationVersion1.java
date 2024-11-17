package Simulation;

import classes.*;


import gui.GUISimulator;
import gui.Simulable;
import tests.TestAffichage;
import tests.TestScenario;
import tests.TestType;

// Première version de simulation pour tester l'affichage et les deux scénarios

public class SimulationVersion1 implements Simulable {
    private final GUISimulator gui;
    protected final DonneesSimulation donnees;
    private final SimulationRenderer renderer;
    private final Simulateur simulateur;
    private final String fichier;
    private final TestType TestType;

    public SimulationVersion1(GUISimulator gui, DonneesSimulation donnees,Simulateur simulateur,String fichier,TestType TestType) {
        this.gui = gui;
        this.donnees = donnees;
        this.renderer = new SimulationRenderer(gui, donnees);
        this.simulateur = simulateur;
        this.fichier = fichier;
        this.TestType = TestType;
        gui.setSimulable(this);
        renderer.drawScene();
    }


    @Override
    public void next() {
        if (!simulateur.simulationTerminee()) {
            simulateur.executeEvenements();
            simulateur.incrementDateSimulation();
            renderer.drawScene(); // Met à jour l'affichage
        }
        else {
            System.out.println("Simulation terminée : aucun événement restant.");
        }
    }

    @Override
    public void restart() {
        simulateur.restart();
        switch (TestType) {
            case Affichage:
                TestAffichage.tester(gui, fichier);
                break;
            case Scenario:
                TestScenario.tester(gui, fichier);
                break;
            default:
                throw new IllegalArgumentException("Type de test inconnu ");
        }
    }
}