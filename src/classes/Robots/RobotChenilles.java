package classes.Robots;
import classes.*;

public class RobotChenilles extends Robot {
    private double vitesse;
    private int capaciteReservoir;
    private int volumeEau;
    public int tempsRemplissage;
    public int tempsVersement;
    public RobotChenilles(Case position, double vitesse, int capaciteReservoir,int volumeEau) {
        super(position, "assets/chenilles.png",volumeEau); // Chemin d'image spécifique pour RobotRoues
        this.vitesse = vitesse;
        this.capaciteReservoir = capaciteReservoir;
        this.volumeEau = 2000;
        this.tempsRemplissage = 0;
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
        if (nature == NatureTerrain.FORET) {
            return vitesse/2; // Exemple : ne peut pas se déplacer en forêt
        } else if (nature == NatureTerrain.EAU || nature == NatureTerrain.ROCHE) {
            return 0;
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
        return nature != NatureTerrain.EAU && nature != NatureTerrain.ROCHE;
    }

    public int getVolumeEau(){
        return this.volumeEau;
    }
}
