import io.LecteurDonnees;
import classes.*;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

public class Simulation implements Simulable {
    private GUISimulator gui;
    private DonneesSimulation donnees;
    private SimulationRenderer renderer;

    public Simulation(GUISimulator gui, DonneesSimulation donnees) {
        this.gui = gui;
        this.donnees = donnees;
        this.renderer = new SimulationRenderer(gui, donnees);
        gui.setSimulable(this);
        renderer.drawScene(); // Initial draw
    }

    @Override
    public void next() {

    }

    @Override
    public void restart() {

    }
}