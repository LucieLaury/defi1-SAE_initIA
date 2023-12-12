package defi1.probleme;

import defi1.framework.common.State;
import defi1.framework.recherche.HasHeuristic;

public class VilleState extends State implements HasHeuristic {

    private String nomVille;
    private double lat, lng;
    private int population;
    private double dist_to_goal = 0;

    public VilleState(String nomVille, double lat, double lng, int population,  double distance_to_goal){
        this.nomVille = nomVille;
        this.lat = lat;
        this.lng = lng;
        this.population = population;
        this.dist_to_goal = distance_to_goal;
    }

    @Override
    protected State cloneState() {
        return new VilleState(nomVille , lat, lng, population, dist_to_goal);
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
        return Double.hashCode(lat) + Double.hashCode(lng) + nomVille.hashCode();
    }

    @Override
    public double getHeuristic() {
        return this.dist_to_goal;
    }

    public void setDist_to_goal(double dist_to_goal) {
        this.dist_to_goal = dist_to_goal;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getNomVille() {
        return nomVille;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
