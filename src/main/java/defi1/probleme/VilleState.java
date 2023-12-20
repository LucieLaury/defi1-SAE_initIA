package defi1.probleme;

import defi1.framework.common.State;
import defi1.framework.recherche.HasHeuristic;

public class VilleState extends State implements HasHeuristic {

    private String nomVille;
    private double lat, lng;
    private int population;
    /*
    heuristique : soit la distance entre cette VilleState et le goalState
    soit le temps entre les deux villes
     */
    private double heuristique = 0;

    public VilleState(String nomVille, double lat, double lng, int population,  double heuristique){
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
        return Double.hashCode(lat) + Double.hashCode(lng) + nomVille.hashCode();
    }

    @Override
    public double getHeuristic() {
        return this.heuristique;
    }

    public void setHeuristique(double heuristique) {
        this.heuristique = heuristique;
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
        double lat1 = Math.toRadians(this.lat);
        double lon1 = Math.toRadians(this.lng);
        double lat2 = Math.toRadians(ville.lat);
        double lon2 = Math.toRadians(ville.lng);
        double deltaLat = lat2-lat1;
        double deltaLng = lon2-lon1;

        double a = Math.pow(Math.sin(deltaLat/2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(deltaLng/2), 2);

        double c = 2 *Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return rayonTerre*c;
    }

    @Override
    public String toString() {
        return nomVille + "("+this.heuristique+")";
    }
}
