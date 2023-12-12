package defi1.probleme;

import defi1.framework.common.State;
import defi1.framework.recherche.Problem;

import java.util.List;


public class Ville extends Problem {

    private VilleState goalState = null;

    public Ville (List<VilleState> states){
        STATES = states.toArray(new State[0]);
    }

    public void initGoalState(VilleState goalState){
        this.goalState = goalState;
        calculDistanceWithGoatState();
    }


    private void calculDistanceWithGoatState(){
        double rayonTerre = 6371;
        VilleState stateCourant;
        for(int i = 0; i<STATES.length; i++){
            stateCourant = (VilleState) STATES[i];
            stateCourant.setDist_to_goal(rayonTerre * cCalcul(stateCourant.getLat(), stateCourant.getLng(),
                    goalState.getLat(), goalState.getLng()));
            //System.out.println("distance : " + stateCourant.getNomVille() + ". " + stateCourant.getHeuristic());
        }
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

    @Override
    public boolean isGoalState(State s) {
        return goalState.equalsState(s);
    }
}
