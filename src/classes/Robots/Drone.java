package classes.Robots;
import classes.*;

// Drone ne depasse pas 150 km/h
public class Drone extends Robot {
    private double vitesse;
    private int capaciteReservoir;
    private int volumeEau;
    public int tempsRemplissage;
    public int tempsVersement;
    // Constructeur avec chemin d'image
    public Drone(Case position, double vitesse, int capaciteReservoir,  int volumeEau) {
        super(position, "assets/drone.png",volumeEau ); // Passe position et le chemin de l'image au constructeur de Robot
        this.vitesse = vitesse;
        this.capaciteReservoir = capaciteReservoir;
        this.volumeEau = 10000; // Réservoir plein au départ
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
    public Case getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Case position) {
        this.position = position;
    }

    @Override
    public double getVitesse(NatureTerrain nature) {
        // Logique de vitesse en fonction du type de terrain
        return this.vitesse;
    }

    @Override
    public void deverserEau(int vol) {
        if (this.volumeEau >= vol) {
            this.volumeEau -= vol;
        }
    }

    @Override
    public void remplirReservoir() {
        this.volumeEau = this.capaciteReservoir;
        //.out.println("Réservoir rempli à " + this.capaciteReservoir + " litres.");
    }


    @Override
    public boolean estTraversable(Case c) {
        return true;
    }
    @Override
    public  int getVolumeEau(){

        return this.volumeEau;
    }
}
