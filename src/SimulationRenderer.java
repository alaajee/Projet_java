import classes.*;
import gui.GUISimulator;
import gui.Rectangle;
import java.awt.Color;
import gui.ImageElement;

public class SimulationRenderer {
    private GUISimulator gui;
    private DonneesSimulation donnees;
    private int cellSize; // Taille d'affichage de chaque case

    public SimulationRenderer(GUISimulator gui, DonneesSimulation donnees) {
        this.gui = gui;
        this.donnees = donnees;
        this.cellSize = gui.getHeight() / (donnees.getCarte().getNbLignes() + 5); // Calcul de cellSize une fois
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
                            col * cellSize, lig * cellSize, NatureTerrain.TERRAIN_LIBRE.getImagePath(), cellSize, cellSize, gui));
                }

                // Dessin effectif de chaque case avec son image correspondante
                gui.addGraphicalElement(new ImageElement(
                        col * cellSize, lig * cellSize, nature.getImagePath(), cellSize, cellSize, gui));

                // Effet de densité de la forêt : dessine des éléments supplémentaires si des cases adjacentes sont également des forêts
                if (nature == NatureTerrain.FORET) {
                    // Vérifie la case adjacente à droite
                    if (col + 1 < carte.getNbColonnes() && carte.getCase(lig, col + 1).getNature() == NatureTerrain.FORET) {
                        gui.addGraphicalElement(new ImageElement(
                                col * cellSize + cellSize / 2, lig * cellSize, nature.getImagePath(), cellSize, cellSize, gui));
                    }
                    // Vérifie la case adjacente en dessous
                    if (lig + 1 < carte.getNbLignes() && carte.getCase(lig + 1, col).getNature() == NatureTerrain.FORET) {
                        gui.addGraphicalElement(new ImageElement(
                                col * cellSize, lig * cellSize + cellSize / 2, nature.getImagePath(), cellSize, cellSize, gui));
                    }
                }
            }
        }
    }

    private void drawRobots() {
        System.out.println("Nombre de robots : " + donnees.getNbrRobots());
        for (int i = 0; i < donnees.getNbrRobots(); i++) {
            Robot robot = donnees.getRobots(i);
            Case position = robot.getPosition();
            int row = position.getLigne();
            int col = position.getColonne();
            gui.addGraphicalElement(new ImageElement(
                    col * cellSize, row * cellSize + cellSize / 2, robot.getImagePath(), cellSize, cellSize, gui));
        }
    }

    private void drawFires() {
        System.out.println("Nombre d'incendies : " + donnees.getNbrIncendies());
        for (int i = 0; i < donnees.getNbrIncendies(); i++) {
            Incendie fire = donnees.getIncendie(i);
            Case position = fire.getPosition();
            int row = position.getLigne();
            int col = position.getColonne();

            gui.addGraphicalElement(new ImageElement(
                    col * cellSize, row * cellSize + cellSize / 4, fire.getImagepath(), cellSize, cellSize, gui));
        }
    }

    private Color getColorForTerrain(NatureTerrain terrain) {
        switch (terrain) {
            case FORET:
                return new Color(34, 139, 34); // Dark green for forests
            case EAU:
                return Color.BLUE;
            case ROCHE:
                return Color.GRAY;
            case TERRAIN_LIBRE:
                return Color.YELLOW;
            default:
                return Color.BLUE; // Default color for unknown terrain
        }
    }
}
