package defi1.probleme;

import defi1.framework.RequeteAPI;
import defi1.framework.common.Action;
import defi1.framework.common.State;
import defi1.framework.recherche.Problem;

import java.io.IOException;
import java.util.List;


public class Ville extends Problem {

    private VilleState goalState = null;

    private String coutEvalue;

    private List<Action> actions;

    public Ville (List<VilleState> states,String goalState, String coutEvalue) throws IOException {
        STATES = states;
        this.coutEvalue = coutEvalue;
        initGoalState(RequeteAPI.construireState_for_Ville(goalState));

    }

    public void initGoalState(VilleState goalState){
        this.goalState = goalState;
        calculDistanceWithGoatState();
    }


    private void calculDistanceWithGoatState(){
        VilleState stateCourant;
        double distanceCourante;
        for(int i = 0; i<STATES.size(); i++){
            stateCourant = STATES.get(i);
            distanceCourante = stateCourant.calculdDitance(goalState);
            stateCourant.setDist_to_goal(distanceCourante);
            //System.out.println("distance : " + stateCourant.getNomVille() + ". " + stateCourant.getHeuristic());
        }
    }



    @Override
    public boolean isGoalState(State s) {
        return goalState.equalsState(s);
    }

    public void initActionTransition(){
        VilleState stateCourant;
        String nomCourant;
        for(int i = 0; i<STATES.size(); i++){
           stateCourant = (VilleState) STATES.get(i);
           nomCourant = stateCourant.getNomVille();
           Action action = new Action("goto "+ nomCourant);
            if(!actions.contains(action)){
                actions.add(action);
            }
            for(int j= 0; j<STATES.size(); j++){
                if(!STATES.get(j).equals(stateCourant)){
                    TRANSITIONS.addTransition(STATES.get(j), action,
                            stateCourant,  calculerCout(stateCourant, STATES.get(j)));
                }
            }

        }
    }

    public double calculerCout(VilleState ville1, VilleState ville2){
        if(coutEvalue.equals("distance")){
            return ville1.calculdDitance(ville2);
        } else {
            double distance = ville1.calculdDitance(ville2);
            double vitesse = 80;
            if(STATES.subList(0,50).contains(ville1) && STATES.subList(0,50).contains(ville2)){
                vitesse = 130;
            } else if(STATES.contains(ville1)||STATES.contains(ville2)){
                vitesse = 110;
            }
            System.out.println("vitesse : " + ville1.getNomVille() + "/"+ville2.getNomVille()+ " : " + vitesse);
            return (distance/vitesse);
        }
    }
}
