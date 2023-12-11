package defi1.probleme;

public class Heuristique {
    //heuristique : distance a vol d'oiseau de la ville courante à la ville d'arrivée
    private double distance;
    //heuristique : voie entre les villes (evaluation de la rapidité)
    //130 = autoroute
    //110 = voie rapide
    //80 = route départementale
    private double rapidite;

    private double heuristique;
    public Heuristique (double distance, double rapidite){
        this.distance = distance;
        this.rapidite = rapidite;
        this.heuristique = distance;
    }

    public double getDistance() {
        return distance;
    }

    public double getRapidite() {
        return rapidite;
    }

    public double getHeuristique() {
        return heuristique;
    }

    public void setHeuristique(double heuristique) {
        this.heuristique = heuristique;
    }
}
