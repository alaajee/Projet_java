package Simulation;
import classes.*;
import gui.GUISimulator;
import gui.ImageElement;

public class SimulationRenderer {
    private final GUISimulator gui;
    private final DonneesSimulation donnees;
    private final int  largeur;
    private final int  hauteur;// Taille d'affichage de chaque case

    public SimulationRenderer(GUISimulator gui, DonneesSimulation donnees) {
        this.gui = gui;
        this.donnees = donnees;
        this.largeur = gui.getPanelWidth() / donnees.getCarte().getNbLignes();
        this.hauteur = (gui.getPanelHeight() - 15 ) / donnees.getCarte().getNbColonnes();
    }

    public void drawScene() {
        drawMap();
        drawRobots();
        drawFires();
    }

    private void drawMap() {
        Carte carte = donnees.getCarte();

        for (int lig = 0; lig < carte.getNbLignes(); lig++) {
            for (int col = 0; col < carte.getNbColonnes(); col++) {
                Case cell = carte.getCase(lig, col);
                NatureTerrain nature = cell.getNature();

                // Si la case est une forêt, ajoutez d'abord un fond de terrain libre
                if (nature == NatureTerrain.FORET) {
                    gui.addGraphicalElement(new ImageElement(
                            col * largeur, lig * hauteur, NatureTerrain.TERRAIN_LIBRE.getImagePath(), largeur,hauteur, gui));
                }

                // Dessin effectif de chaque case avec son image correspondante
                gui.addGraphicalElement(new ImageElement(
                        col * largeur, lig * hauteur, nature.getImagePath(),  largeur,hauteur, gui));

                // Effet de densité de la forêt : dessine des éléments supplémentaires si des cases adjacentes sont également des forêts
                if (nature == NatureTerrain.FORET) {
                    // Vérifie la case adjacente à droite
                    if (col + 1 < carte.getNbColonnes() && carte.getCase(lig, col + 1).getNature() == NatureTerrain.FORET) {
                        gui.addGraphicalElement(new ImageElement(
                                col * largeur, lig * hauteur, nature.getImagePath(),  largeur,hauteur, gui));
                    }
                    // Vérifie la case adjacente en dessous
                    if (lig + 1 < carte.getNbLignes() && carte.getCase(lig + 1, col).getNature() == NatureTerrain.FORET) {
                        gui.addGraphicalElement(new ImageElement(
                                col * largeur, lig * hauteur, nature.getImagePath(),  largeur,hauteur, gui));
                    }
                }
            }
        }
    }

    private void drawRobots() {
        for (int i = 0; i < donnees.getNbrRobots(); i++) {
            Robot robot = donnees.getRobots(i);
            Case position = robot.getPosition();
            int row = position.getLigne();
            int col = position.getColonne();
            gui.addGraphicalElement(new ImageElement(
                    col * largeur, row * hauteur , robot.getImagePath(),  largeur,hauteur, gui));
        }
    }

    private void drawFires() {
        for (Incendie fire : donnees.getIncendie()) {
            if (fire != null) {
                Case position = fire.getPosition();
                int row = position.getLigne();
                int col = position.getColonne();

                gui.addGraphicalElement(new ImageElement(
                        col * largeur, row * hauteur , fire.getImagepath(),  largeur,hauteur, gui));
            }
        }
    }
}
