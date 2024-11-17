package classes;

public class Carte {
    private final Case[][] caseMatrice;
    private final int tailleCases;
    private final int nbLignes;
    private final int nbColonnes;

    public Carte(int nbLignes, int nbColonnes, int tailleCases) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCases = tailleCases;
        this.caseMatrice = new Case[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                // Ensure TERRAIN_LIBRE is a valid NatureTerrain type, or replace with an appropriate default
                this.caseMatrice[i][j] = new Case(i, j, NatureTerrain.EAU);
            }
        }
    }

    public int getNbLignes() {
        return this.nbLignes;
    }

    public int getNbColonnes() {
        return this.nbColonnes;
    }

    public int getTailleCases() {
        return this.tailleCases;
    }

    public Case getCase(int lig, int col) {
        return this.caseMatrice[lig][col];
    }

    public void setCase(int lig, int col, Case cell) {
        this.caseMatrice[lig][col] = cell;
    }

    public boolean voisinExiste(Case src, Direction dir) {
        int l = src.getLigne();
        int c = src.getColonne();
        return switch (dir) {
            case NORD -> l > 0;
            case SUD -> l < this.nbLignes - 1;
            case OUEST -> c > 0;
            case EST -> c < this.nbColonnes - 1;
        };
    }

    public Case getVoisin(Case src, Direction dir) {
        if (voisinExiste(src, dir)) {
            int l = src.getLigne();
            int c = src.getColonne();
            return switch (dir) {
                case NORD -> caseMatrice[l - 1][c];
                case SUD -> caseMatrice[l + 1][c];
                case OUEST -> caseMatrice[l][c - 1];
                case EST -> caseMatrice[l][c + 1];
            };
        }
        return src;
    }
}
