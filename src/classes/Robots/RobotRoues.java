package classes.Robots;
import classes.*;

public class RobotRoues extends Robot {
    private double vitesse;
    private int capaciteReservoir;
    private int volumeEau;
    public int tempsVersement;
    public int tempsRemplissage;
    public RobotRoues(Case position, double vitesse, int capaciteReservoir,int volumeEau) {
        super(position, "assets/roues.png",volumeEau); // Chemin d'image spécifique pour RobotRoues
        this.vitesse = vitesse;
        this.capaciteReservoir = capaciteReservoir;
        this.volumeEau = 5000; // Réservoir plein au départ
        this.tempsRemplissage = 0 ;
        this.tempsVersement = 0;
    }
    public int getTempsRemplissage() {
        return tempsRemplissage;
    }
    public int getTempsVersement(){
        return tempsVersement;
    }
    @Override
    public double getVitesse(NatureTerrain nature) {
        // RobotRoues peut avoir des limitations sur certains terrains, comme la forêt
        if (nature == NatureTerrain.TERRAIN_LIBRE) {
            return 0; // Exemple : ne peut pas se déplacer en forêt
        }

        return vitesse;
    }

    @Override
    public void deverserEau(int vol) {
        if (volumeEau >= vol) {
            volumeEau -= vol;
        }
    }

    @Override
    public void remplirReservoir() {
        this.volumeEau = capaciteReservoir;
    }
    @Override
    public boolean estTraversable(Case c) {
        NatureTerrain nature = c.getNature();
        // Retourner false pour les terrains non traversables par ce robot
        return nature != NatureTerrain.EAU && nature != NatureTerrain.ROCHE && nature!= NatureTerrain.FORET;
    }

    public int getVolumeEau(){
        return this.volumeEau;
    }
}
