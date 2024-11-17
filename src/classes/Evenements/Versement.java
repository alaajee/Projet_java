package classes.Evenements;

import classes.DonneesSimulation;
import classes.Robot;
import classes.Incendie;

import java.util.LinkedHashMap;

/* Cette classe représente l'évenement du versement de l'eau sur l'incendie*/

public class Versement extends Evenement{
    DonneesSimulation donnees;
    private final int indice;
    private final int eau_versee;
    private final int indice_incendie;
    private final LinkedHashMap<Incendie, Robot> incendiesRob ;
    public Versement(DonneesSimulation donnees,long date,int indice,int eau_versee,int indice_incendie,LinkedHashMap<Incendie, Robot> incendiesRob) {
        super(date);
        this.donnees = donnees;
        this.indice = indice;
        this.eau_versee = eau_versee;
        this.indice_incendie = indice_incendie;
        this.incendiesRob = incendiesRob;
    }

    @Override
    public void execute() {
        Incendie incendie = donnees.getIncendie(indice_incendie);
        Robot robot = donnees.getRobots(indice);
        if (incendie != null){
            int eau_versee = incendie.getLitre_eau();
            incendie.setLitre_eau(robot.getVolumeEau());
            robot.deverserEau(eau_versee);
            if (incendie.getLitre_eau() <= 0){
                donnees.RemoveIncendie(indice_incendie);
                incendiesRob.put(incendie, null);
            }
        }
    }

    public int getEau_versee() {
        return eau_versee;
    }
}