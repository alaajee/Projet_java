package classes;

import tests.*;
import classes.*;
import classes.Robots.*;
import classes.OptimalPath.*;
import classes.Evenements.*;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class ChefPompier {

    private LinkedHashMap<Incendie, Robot> incendiesRob; // Utilise LinkedHashMap pour préserver l'ordre d'insertion
    private DonneesSimulation donnees;

    public ChefPompier(DonneesSimulation donnees, LinkedHashMap<Incendie, Robot> incendiesRob) {
        this.donnees = donnees;
        this.incendiesRob = incendiesRob;
    }

    public boolean Affecter_Robot() {
        // Filtrer les incendies non affectés
        List<Incendie> incendiesNonAffectes = Arrays.stream(donnees.getIncendie())
                .filter(incendie -> incendie != null && !incendiesRob.containsKey(incendie))
                .collect(Collectors.toList());
    
        // Filtrer les robots non affectés
        List<Robot> robotsNonAffectes = Arrays.stream(donnees.getRobots())
                .filter(robot -> !incendiesRob.containsValue(robot))
                .collect(Collectors.toList());
    
        // Vérifier s'il reste des incendies ou des robots disponibles
        if (incendiesNonAffectes.isEmpty() || robotsNonAffectes.isEmpty()) {
            //tem.out.println("Aucun incendie ou robot disponible.");
            return false;
        }
    
        Incendie incendieSelectionne = null;
        Robot robotSelectionne = null;
        double tempsMinimal = Double.POSITIVE_INFINITY;
    
        // Parcourir tous les incendies et robots pour trouver le couple avec le temps minimal
        for (Incendie incendie : incendiesNonAffectes) {
            for (Robot robot : robotsNonAffectes) {
                Graphe graphe = new Graphe(donnees.getCarte(), robot);
                double tempsTotal = robot.getTempsTotal(incendie.getPosition(), graphe);
    
                if (tempsTotal < tempsMinimal) {
                    tempsMinimal = tempsTotal;
                    incendieSelectionne = incendie;
                    robotSelectionne = robot;
                }
            }
        }
    
        // Vérifier si un couple valide a été trouvé
        if (incendieSelectionne != null && robotSelectionne != null) {
            incendiesRob.put(incendieSelectionne, robotSelectionne); // Affecter le robot à l'incendie
            //tem.out.println("Incendie " + incendieSelectionne + " a été affecté au robot " + robotSelectionne);
            return true;
        }
    
        //tem.out.println("Aucun robot ne peut être affecté à un incendie.");
        return false;
    }
    
    
    

    public LinkedList<Evenement> RobotChemin(LinkedHashMap<Incendie, Robot> incendiesRob, int index, int dateCumulative) {
        LinkedList<Evenement> listeEvenements = new LinkedList<>();

        if (index < 0 || index >= incendiesRob.size()) {
            //tem.out.println("Index invalide pour incendiesRob");
            return listeEvenements;
        }

        Incendie incendie = (Incendie) incendiesRob.keySet().toArray()[index];
        Robot robot = incendiesRob.get(incendie);
        Graphe graphe = new Graphe(donnees.getCarte(), robot);

        //tem.out.println("On est dans le robot de capacité " + robot.getCapaciteResevoire());
        if (incendie != null && robot != null) {
          
            LinkedList<Case> list = robot.getChemin(incendie.getPosition(), graphe);
            //.out.println(list.size());
            LinkedList<Double> temps = robot.getTempsParEtape(incendie.getPosition(), graphe);
            //.out.println(temps.size());

            Normalisation temps_normalisé= new Normalisation(temps);
            temps_normalisé.getNormalizedData();
            //temps_normalisé.printNormalizedData();

            int date = robot.getDate();
            //tem.out.println("date_d=" + date);
            for (int i = 1; i < list.size(); i++) {
                listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), list.get(i).getLigne(), list.get(i).getColonne()));
                date += temps_normalisé.getElementAtIndex(i-1)+1;
                
               /*  if(robot.getVolumeEau()==1987000){
                    //.out.println("date_deplacement"+date);
                }*/
            }
            date = date + robot.getTempsVersement();
            listeEvenements.add(new Versement(donnees, date, donnees.getIndice(robot), robot.getVolumeEau(), donnees.getIndice(incendie), incendiesRob));
            /*if(robot.getVolumeEau()==1987000){
                //.out.println("date_versement"+date);
            }*/
            robot.setDate(date + 1);
            /*if(robot.getVolumeEau()==1978000){
                //.out.println("date_de_robot=" + robot.getDate());
            }*/
        }
        return listeEvenements;
    }
    public LinkedList<Evenement> RobotRemplir(LinkedHashMap<Incendie, Robot> incendiesRob,int index, int dateCumulative){
        Incendie incendie = (Incendie) incendiesRob.keySet().toArray()[index];
        Robot robot = incendiesRob.get(incendie);
        LinkedList<Evenement> listeEvenements = new LinkedList<>();

        //tem.out.println("On est dans le robot de capacité " + robot.getCapaciteResevoire());
        Graphe graphe = new Graphe(donnees.getCarte(), robot);
        int date = robot.getDate();
        if ( robot.getVolumeEau() <=0 ){
            Case case_eau = donnees.getCaseEau(incendie.getPosition().getLigne(), incendie.getPosition().getColonne());
            LinkedList<Case> liste_eau = incendie.getChemin(case_eau, graphe);
            for (int i = 1; i < liste_eau.size(); i++) {
                listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), liste_eau.get(i).getLigne(), liste_eau.get(i).getColonne()));
                date += 1;
                if (robot.getCapaciteResevoire() == 10000){
                    //tem.out.println("les nouveaux coordonnées sont " + liste_eau.get(i).getLigne() +  liste_eau.get(i).getColonne() );
                }
                //tem.out.println("remplir_hh, se deplacer pour remplir dans l'extreme cas" + date);
            }
            listeEvenements.add(new Remplissage(donnees, date, donnees.getIndice(robot)));
            date =  date + robot.getTempsVersement();
            robot.setDate(date + 1);
            //tem.out.println("date_re" + robot.getDate());
        }
        return listeEvenements;
    }
    public LinkedList<Evenement> RobotRemplissage(LinkedHashMap<Incendie, Robot> incendiesRob,int index,int dateCumulative){
        Incendie incendie = (Incendie) incendiesRob.keySet().toArray()[index];
        Robot robot = incendiesRob.get(incendie);
        Graphe graphe = new Graphe(donnees.getCarte(), robot);
        LinkedList<Evenement> listeEvenements = new LinkedList<>();
        int date = robot.getDate();
        //tem.out.println("On est dans le robot de capacité " + robot.getCapaciteResevoire());
        if (incendie.getLitre_eau() > robot.getVolumeEau()){
            int eau_versee = incendie.getLitre_eau() - robot.getVolumeEau();
            Case case_eau = donnees.getCaseEau(incendie.getPosition().getLigne(), incendie.getPosition().getColonne());
            LinkedList<Case> liste_eau = incendie.getChemin(case_eau, graphe);
            //tem.out.println("coordonnees de robot" + robot.getPosition().getLigne() +  robot.getPosition().getColonne());
            while (eau_versee > 0) {
                //tem.out.println("remplir" + liste_eau.size());
                for (int i = 1; i < liste_eau.size(); i++) {
                    listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), liste_eau.get(i).getLigne(), liste_eau.get(i).getColonne()));
                    date += 1;
                    if (robot.getCapaciteResevoire() == 10000){
                        //tem.out.println("le boss");
                        //tem.out.println("les nouveaux coordonnées sont " + liste_eau.get(i).getLigne() +  liste_eau.get(i).getColonne() );
                    }
                    //tem.out.println("oblig_se deplacer pour remplir" + date);
                }
                //tem.out.println("FINNNNN");
                listeEvenements.add(new Remplissage(donnees, date, donnees.getIndice(robot)));
                date =  date + robot.getTempsVersement();
                for (int i = liste_eau.size() - 1; i >= 0; i--) { // Commence par l'avant-dernier élément et va jusqu'à l'index 1
                    listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), liste_eau.get(i).getLigne(), liste_eau.get(i).getColonne()));
                    //tem.out.println(" date se deplacer pour verser " + date);
                    date += 1;
                }
                date =  date + robot.getTempsVersement();
                listeEvenements.add(new Versement(donnees, date, donnees.getIndice(robot), robot.getVolumeEau(), donnees.getIndice(incendie), incendiesRob));
                //tem.out.println("FINNNNN_Deuxieme");
                eau_versee -= robot.getCapaciteResevoire();
            }
            robot.setDate(date + 1);
            //tem.out.println("On est dans " + robot.getDate());
        }
        return listeEvenements;
    }
}


