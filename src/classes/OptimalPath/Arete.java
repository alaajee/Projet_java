
package classes.OptimalPath;

import  classes.Case;
public class Arete {
    private Case from;
    private Case to;
    private double poids;

    public Arete(Case from, Case to, double poids) {
        this.from = from;
        this.to = to;
        this.poids = poids;
    }

    public Case getFrom() { return from; }
    public Case getTo() { return to; }
    public double getPoids() { return poids; }
}
