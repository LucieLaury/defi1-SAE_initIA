package defi1.probleme;

import defi1.framework.common.State;
import defi1.framework.recherche.HasHeuristic;

import java.util.Arrays;

public class VilleState extends State implements HasHeuristic {

    private String nomVille;
    private int x, y;
    private int population;
    private Heuristique heuristique;

    public VilleState(String nomVille, int x, int y, int population,  Heuristique heuristique){
        this.nomVille = nomVille;
        this.x = x;
        this. y = y;
        this.population = population;
        this.heuristique = heuristique;
    }

    @Override
    protected State cloneState() {
        return new VilleState(nomVille ,x, y, population, heuristique);
    }

    @Override
    protected boolean equalsState(State o) {
        VilleState otherState = (VilleState) o;
        return (otherState.nomVille.equals(nomVille) &&
                (otherState.x == this.x)&&
                (otherState.y == this.y));
    }

    @Override
    protected int hashState() {
        return Integer.hashCode(x) + Integer.hashCode(y) + nomVille.hashCode();
    }

    @Override
    public double getHeuristic() {
        return this.heuristique.getHeuristique();
    }
}
