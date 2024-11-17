package classes;

import classes.OptimalPath.Graphe;
import classes.Evenements.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

// Le principe est similaire à celui du ChefPomipier mais il affecte aléatoirement les incendies au Robots.
public class ChefPompierNaif {

    private final LinkedHashMap<Incendie, Robot> incendiesRob; // Utilise LinkedHashMap pour préserver l'ordre d'insertion
    private final DonneesSimulation donnees;

    public ChefPompierNaif(DonneesSimulation donnees, LinkedHashMap<Incendie, Robot> incendiesRob) {
        this.donnees = donnees;
        this.incendiesRob = incendiesRob;
    }

    public boolean Affecter_Robot() {
        Random random = new Random();
        boolean foundAccessiblePair = false;

        // Filtrer les incendies non affectés et non nuls
        List<Incendie> incendiesNonAffectes = Arrays.stream(donnees.getIncendie())
                .filter(incendie -> incendie != null && !incendiesRob.containsKey(incendie))
                .toList();

        // Vérifier s'il reste des incendies non affectés
        if (incendiesNonAffectes.isEmpty()) {
            return false;
        }

        // Sélectionner un incendie non affecté
        Incendie incendieSelectionne = incendiesNonAffectes.get(random.nextInt(incendiesNonAffectes.size()));
        List<Robot> robotsEssayes = new ArrayList<>();

        // Tentative d'affectation d'un robot accessible
        while (!foundAccessiblePair) {
            List<Robot> robotsNonAffectes = Arrays.stream(donnees.getRobots())
                    .filter(robot -> !incendiesRob.containsValue(robot) && !robotsEssayes.contains(robot))
                    .toList();

            if (robotsNonAffectes.isEmpty()) {
                break;
            }

            Robot robotSelectionne = robotsNonAffectes.get(random.nextInt(robotsNonAffectes.size()));

            Graphe graphe = new Graphe(donnees.getCarte(), robotSelectionne);
            if (robotSelectionne.IncendieAccessible(incendieSelectionne, graphe)) {
                incendiesRob.put(incendieSelectionne, robotSelectionne);
                foundAccessiblePair = true;

            } else {
                robotsEssayes.add(robotSelectionne);
            }
        }
        return foundAccessiblePair;
    }

    public LinkedList<Evenement> RobotChemin(LinkedHashMap<Incendie, Robot> incendiesRob, int index) {
        LinkedList<Evenement> listeEvenements = new LinkedList<>();

        if (index < 0 || index >= incendiesRob.size()) {
            return listeEvenements;
        }

        Incendie incendie = (Incendie) incendiesRob.keySet().toArray()[index];
        Robot robot = incendiesRob.get(incendie);
        Graphe graphe = new Graphe(donnees.getCarte(), robot);

        if (incendie != null && robot != null) {
            LinkedList<Case> list = robot.getChemin(incendie.getPosition(), graphe);
            int date = robot.getDate();
            for (int i = 1; i < list.size(); i++) {
                listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), list.get(i).getLigne(), list.get(i).getColonne()));
                date += 1;
            }
            date = date + robot.getTempsVersement();
            listeEvenements.add(new Versement(donnees, date, donnees.getIndice(robot), robot.getVolumeEau(), donnees.getIndice(incendie), incendiesRob));

            robot.setDate(date + 1);
        }
        return listeEvenements;
    }
    public LinkedList<Evenement> RobotRemplir(LinkedHashMap<Incendie, Robot> incendiesRob,int index){
        Incendie incendie = (Incendie) incendiesRob.keySet().toArray()[index];
        Robot robot = incendiesRob.get(incendie);
        LinkedList<Evenement> listeEvenements = new LinkedList<>();
        Graphe graphe = new Graphe(donnees.getCarte(), robot);
        int date = robot.getDate();
        if ( robot.getVolumeEau() - incendie.getLitre_eau() == 0){
            Case case_eau = donnees.getCaseEau(incendie.getPosition().getLigne(), incendie.getPosition().getColonne());
            LinkedList<Case> liste_eau = incendie.getChemin(case_eau, graphe);
            for (int i = 1; i < liste_eau.size(); i++) {
                listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), liste_eau.get(i).getLigne(), liste_eau.get(i).getColonne()));
                date += 1;
            }
            listeEvenements.add(new Remplissage(donnees, date, donnees.getIndice(robot)));
            date =  date + robot.getTempsVersement();
            robot.setDate(date + 1);
        }
        return listeEvenements;
    }

    public LinkedList<Evenement> RobotRemplissage(LinkedHashMap<Incendie, Robot> incendiesRob,int index){
        Incendie incendie = (Incendie) incendiesRob.keySet().toArray()[index];
        Robot robot = incendiesRob.get(incendie);
        Graphe graphe = new Graphe(donnees.getCarte(), robot);
        LinkedList<Evenement> listeEvenements = new LinkedList<>();
        int date = robot.getDate();
        if (incendie.getLitre_eau() > robot.getVolumeEau()){
            int eau_versee = incendie.getLitre_eau() - robot.getVolumeEau();
            Case case_eau = donnees.getCaseEau(incendie.getPosition().getLigne(), incendie.getPosition().getColonne());
            LinkedList<Case> liste_eau = incendie.getChemin(case_eau, graphe);
            while (eau_versee > 0) {
                for (int i = 1; i < liste_eau.size(); i++) {
                    listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), liste_eau.get(i).getLigne(), liste_eau.get(i).getColonne()));
                    date += 1;
                }
                listeEvenements.add(new Remplissage(donnees, date, donnees.getIndice(robot)));
                date =  date + robot.getTempsVersement();
                for (int i = liste_eau.size() - 1; i >= 0; i--) { // Commence par l'avant-dernier élément et va jusqu'à l'index 1
                    listeEvenements.add(new Deplacement(donnees, date, donnees.getIndice(robot), liste_eau.get(i).getLigne(), liste_eau.get(i).getColonne()));
                    date += 1;
                }
                date =  date + robot.getTempsVersement();
                listeEvenements.add(new Versement(donnees, date, donnees.getIndice(robot), robot.getVolumeEau(), donnees.getIndice(incendie), incendiesRob));
                eau_versee -= robot.getCapaciteResevoire();
            }
            robot.setDate(date + 1);
        }
        return listeEvenements;
    }
}


