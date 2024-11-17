package classes.Evenements;

import classes.DonneesSimulation;
import classes.Robot;

/* Cette classe permet de remplir le resevoir du robot */

public class Remplissage extends Evenement{
    DonneesSimulation donnees;
    private final int indice;

    public Remplissage(DonneesSimulation donnees,long date,int indice) {
        super(date);
        this.donnees = donnees;
        this.indice = indice;

    }

    @Override
    public void execute() {
        Robot robot = donnees.getRobots(indice);
        robot.remplirReservoir();
    }
}