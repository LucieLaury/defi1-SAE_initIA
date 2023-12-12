package defi1.probleme;

import defi1.framework.common.State;
import defi1.framework.recherche.HasHeuristic;

public class VilleState extends State implements HasHeuristic {

    private String nomVille;
    private int lat, lng;
    private int population;
    private Heuristique heuristique;

    public VilleState(String nomVille, int lat, int lng, int population,  Heuristique heuristique){
        this.nomVille = nomVille;
        this.lat = lat;
        this.lng = lng;
        this.population = population;
        this.heuristique = heuristique;
    }

    @Override
    protected State cloneState() {
        return new VilleState(nomVille , lat, lng, population, heuristique);
    }

    @Override
    protected boolean equalsState(State o) {
        VilleState otherState = (VilleState) o;
        return (otherState.nomVille.equals(nomVille) &&
                (otherState.lat == this.lat)&&
                (otherState.lng == this.lng));
    }

    @Override
    protected int hashState() {
        return Integer.hashCode(lat) + Integer.hashCode(lng) + nomVille.hashCode();
    }

    @Override
    public double getHeuristic() {
        return this.heuristique.getHeuristique();
    }
}
