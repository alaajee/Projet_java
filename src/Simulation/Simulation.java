package Simulation;

import classes.Robot;
import classes.*;
import classes.Evenements.*;
import tests.*;

import java.util.*;
import gui.GUISimulator;
import gui.Simulable;

public class Simulation implements Simulable {
    private GUISimulator gui;
    private DonneesSimulation donnees;
    private SimulationRenderer renderer;
    private Simulateur simulateur;
    private final String fichier;
    private LinkedHashMap<Incendie, Robot> incendiesRob ;
    private ChefPompier chefPompier;

    public Simulation(GUISimulator gui, DonneesSimulation donnees,Simulateur simulateur,String fichier, LinkedHashMap<Incendie, Robot> incendiesRob,ChefPompier chefPompier ) {
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
                    LinkedList<Evenement> evenements = chefPompier.RobotChemin(incendiesRob,  incendiesRob.size()-1,simulateur.getDateSimulation());
                    Incendie incendie = (Incendie) incendiesRob.keySet().toArray()[incendiesRob.size()-1];
                    Robot robot = incendiesRob.get(incendie);

                    LinkedList<Evenement> evenements1 = chefPompier.RobotRemplissage(incendiesRob, incendiesRob.size()-1,simulateur.getDateSimulation());

                    LinkedList<Evenement> evenements2 = chefPompier.RobotRemplir(incendiesRob,incendiesRob.size()-1,simulateur.getDateSimulation());

                    simulateur.ajouterEvenements(evenements);
                    simulateur.ajouterEvenements(evenements2);
                    simulateur.ajouterEvenements(evenements1);
                };

            }


            if (!simulateur.simulationTerminee()) {
                simulateur.executeEvenements();
                simulateur.incrementDateSimulation();
                renderer.drawScene(); // Met à jour l'affichage
            }
        }
        else {
            System.out.println("\n====================================================================");
            System.out.println("                       FIN DE LA SIMULATION                        ");
            System.out.println("====================================================================\n");

        }
    }

    @Override
    public void restart() {
        simulateur.restart();
        TestSimulation.tester(fichier,gui);
    }
}