package classes;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class DonneesSimulation {
    private Carte carte;
    private Robot[] robots;
    private Incendie[] incendie;
    private int NbrIncendies;
    private int NbrRobots;
    //private Linkedlist<Graphe> ListGraph;
    public DonneesSimulation() {
        this.NbrIncendies = 0;
        this.NbrRobots = 0;


    }
    public int getNbrIncendies() {
        return this.NbrIncendies;
    }
    public int getNbrRobots() {
        return this.NbrRobots;
    }
    public Carte getCarte() {
        return carte;
    }
    public Robot[] getRobots() {
        return robots;
    }

    public Incendie[] getIncendie() {
        return incendie;
    }

    public Robot getRobots(int index) {
        return robots[index];
    }

    public void setRobot(int index, Robot robot) {
        if (index >= 0 && index < robots.length) {
            this.robots[index] = robot;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for robots array.");
        }
    }

    public Incendie getIncendie(int index) {
        return incendie[index];
    }
    public void RemoveIncendie(int index) {
        // Mettre le dernier élément à null
        incendie[index] = null;
        // Réduire le nombre d'incendies
        NbrIncendies--;
    }

    public void setIncendie(int index, Incendie incendiee) {
        if (index >= 0 && index < incendie.length) {
            incendie[index] = incendiee;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " hors des limites pour les incendies.");
        }
    }

    public void creerRobots(int nbRobots) {
        robots = new Robot[nbRobots]; // Crée un tableau pour les robots
        this.NbrRobots = nbRobots; // Stocke le nombre de robots
    }

    public void creerIncendies(int NbrIncendies) {
        incendie = new Incendie[NbrIncendies];
        this.NbrIncendies = NbrIncendies;
    }

    public void creerCarte(int NbColonne , int NbLigne ,int tailleCases) {
        carte = new Carte(NbColonne , NbLigne , tailleCases);
    }
    public int getIndice(Robot robot) {
        for (int i = 0; i < robots.length; i++) {
            if (robots[i] == robot) { // Compare les références des robots
                return i; // Retourne l'index du robot trouvé
            }
        }
        throw new NoSuchElementException("Robot non trouvé dans la liste.");
    }
    public int getIndice(Incendie incendiee) {
        for (int i = 0; i < incendie.length; i++) {
            if (incendie[i] == incendiee) { // Compare les références des incendies
                return i; // Retourne l'index de l'incendie trouvé
            }
        }
        throw new NoSuchElementException("Incendie non trouvé dans la liste.");
    }

    public Case getCaseEau(int robotX, int robotY) {
        boolean[][] visited = new boolean[carte.getNbLignes()][carte.getNbColonnes()];
        Queue<Case> queue = new LinkedList<>();
        queue.add(carte.getCase(robotX, robotY));
        visited[robotX][robotY] = true;

        while (!queue.isEmpty()) {
            Case currentCase = queue.poll();
            int x = currentCase.getLigne();
            int y = currentCase.getColonne();

            // Vérifie si la case actuelle est de type EAU
            if (currentCase.getNature() == NatureTerrain.EAU) {
                return currentCase; // Retourne la case d'eau la plus proche
            }

            // Ajoute les cases voisines à la queue
            for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) { // droite, bas, gauche, haut
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < carte.getNbLignes() && newY >= 0 && newY < carte.getNbColonnes()
                        && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.add(carte.getCase(newX, newY));
                }
            }
        }

        throw new NoSuchElementException("Aucune case avec le terrain d'eau trouvée.");
    }


}



