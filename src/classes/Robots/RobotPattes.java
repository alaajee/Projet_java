    package classes.Robots;
    import classes.*;

    public class RobotPattes extends Robot {
        private final double vitesse; // Vitesse constante pour RobotPattes
        private int capaciteReservoir;
        private int volumeEau;
        public int tempsRemplissage;
        public int tempsVersement;
        public RobotPattes(Case position, double vitesse, int volumeEau, int capaciteReservoir) {
            super(position, "assets/pattes.png",volumeEau); // Chemin d'image spécifique pour RobotPattes
            this.vitesse = vitesse;
            this.volumeEau = 2000000;
            this.capaciteReservoir = capaciteReservoir;
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
            // RobotPattes peut avoir une vitesse affectée par certains terrains, comme la montagne

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
            //.out.println("RobotPattes n'a pas de réservoir d'eau.");
            // Optionnellement, lancer une exception si RobotPattes ne peut pas remplir de réservoir
        }
        @Override
        public boolean estTraversable(Case c) {
            NatureTerrain nature = c.getNature();
            // Retourner false pour les terrains non traversables par ce robot
            return nature != NatureTerrain.EAU ;
        }
        public int getVolumeEau(){
            return this.volumeEau;
        }

    }
