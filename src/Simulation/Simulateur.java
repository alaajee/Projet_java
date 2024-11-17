package Simulation;

import classes.Evenements.*;
import java.util.Iterator;
import java.util.LinkedList;

import classes.*;
public class Simulateur {
    private int dateSimulation;
    private final LinkedList<Evenement> listeEven;

    public Simulateur(DonneesSimulation donnees) {
        this.dateSimulation = 0;
        this.listeEven = new LinkedList<>(); // Initialisation de la liste chaînée
    }

    public int getDateSimulation() {
        return dateSimulation;
    }

    public void ajouteEvenement(Evenement e){
        this.listeEven.addLast(e);
    }

    public Evenement getEvenement(){
        return this.listeEven.getFirst();
    }

    public Evenement getEvenement(int index ){
        return this.listeEven.get(index);
    }

    public void incrementDateSimulation(){
        this.dateSimulation++;
    }

    public boolean simulationTerminee(){
        return this.listeEven.isEmpty();
    }
    @Override
    public String toString() {
        return "Simulateur [dateSimulation=" + this.dateSimulation + ", evenementsActuels=" + this.listeEven
                + "]";
    }

    public void executeEvenements() {
        Iterator<Evenement> iterator = listeEven.iterator();

        while (iterator.hasNext()) {

            Evenement event = iterator.next();

            // Vérifie si la date de l'événement est inférieure ou égale à dateSimulation
            if (event.getDate() <= dateSimulation) {
                //tem.out.println("c'est moi qui s'éxecute maintenant " + event.getDate());
                event.execute(); // Exécute l'événement
                iterator.remove(); // Supprime l'événement de la liste après exécution
            }

        }
    }

    public void ajouterEvenements(LinkedList<Evenement> evenements) {
        this.listeEven.addAll(evenements); // Ajoute tous les événements de la liste fournie
    }
    public void restart(){
        this.dateSimulation = 0;
        this.listeEven.clear();
    }

    public LinkedList<Evenement> getlisteEvenement () {
        return listeEven;
    }
}