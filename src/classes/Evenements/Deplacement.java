package classes.Evenements;

import classes.*;

/* On cree un evenement Déplacement pour deplacer le robot d'une case à une autre */

public class Deplacement extends Evenement{
    DonneesSimulation donnees;
    private final int indice;
    private final int i ;
    private final int j ;
    public Deplacement(DonneesSimulation donnees,long date ,int indice, int i , int j) {
        super(date);
        this.donnees = donnees;
        this.indice = indice;
        this.i = i;
        this.j = j;
    }

    @Override
    public void execute() {
        Robot robot = donnees.getRobots(indice);
        Case position = robot.getPosition();
        position.setLigne(i);
        position.setColonne(j);
        robot.setPosition(position);
    }
}