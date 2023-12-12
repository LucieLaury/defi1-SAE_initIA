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

    public double calculdDitance(VilleState ville){
        double rayonTerre = 6371;
        return (rayonTerre * cCalcul(this.getLat(), this.getLng(),
                ville.getLat(), ville.getLng()));
    }
    private double cCalcul(double latitude1, double longitude1, double latitude2, double longitude2){
        return 2 * Math.atan2(Math.sqrt(aCalcul(latitude1, longitude1, latitude2, longitude2)),
                Math.sqrt(1 - aCalcul(latitude1, longitude1, latitude2, longitude2)) );
    }

    private double aCalcul(double lat1, double lon1, double lat2, double lon2){
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        return Math.pow(Math.sin((lat1-lat2)/2), 2) + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin((lon1-lon2)/2), 2);
    }
}
