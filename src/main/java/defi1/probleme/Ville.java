package defi1.probleme;

import defi1.framework.RequeteAPI;
import defi1.framework.common.Action;
import defi1.framework.common.State;
import defi1.framework.recherche.Problem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Ville extends Problem {

    private VilleState goalState = null;

    private VilleState initState = null;

    private String coutEvalue;

    public Ville (List<VilleState> states,String initialState, String goalState, String coutEvalue) throws IOException {
        ACTIONS = new ArrayList<>();
        STATES = states;
        this.coutEvalue = coutEvalue;

        initInitialState(RequeteAPI.construireState_for_Ville(initialState));
        initGoalState(RequeteAPI.construireState_for_Ville(goalState));
        initAllActionTransition();
    }

    public void initGoalState(VilleState goalState){
        this.goalState = goalState;
        calculerHeuristiqueGoalState();
        //goalState ne fait pas parti de la liste des stats car on ne veut pas
        if(STATES.contains(goalState)){
            STATES.remove(this.goalState);
        }

    }

    public void initInitialState(VilleState initState){
        this.initState = initState;
        if(!STATES.contains(this.initState)){
            STATES.add(this.initState);
        }
    }


    private void calculerHeuristiqueGoalState(){
        VilleState stateCourant;
        double heuristique;
        for(int i = 0; i<STATES.size(); i++){
            stateCourant = STATES.get(i);
            heuristique = calculerCout(stateCourant, goalState);
            stateCourant.setHeuristique(heuristique);
            //System.out.println("distance : " + stateCourant.getNomVille() + ". " + stateCourant.getHeuristic());
        }
    }

    public Action createGoalAction(VilleState villeState){
        Action action = new Action("goto "+goalState.getNomVille());
        TRANSITIONS.addTransition(villeState, action, goalState, calculerCout(villeState, goalState));
        return action;
    }

    @Override
    public boolean isGoalState(State s) {
        double heuristiqueCourante;
        for(VilleState villeCourante : STATES){
            heuristiqueCourante = villeCourante.getHeuristic();
            if(heuristiqueCourante<((VilleState) s).getHeuristic()){
                return false;
            }
        }
        return true;
    }

    public void initAllActionTransition(){
        VilleState stateCourant;
        String nomCourant;
        for(int i = 0; i<STATES.size(); i++){
           stateCourant = STATES.get(i);
            initActionTransition(stateCourant);
        }
    }

    public void initActionTransition(VilleState villeCourante){
        Action action = new Action("goto "+ villeCourante.getNomVille());
        if(!ACTIONS.contains(action)){
            ACTIONS.add(action);
        }
        for(int j= 0; j<STATES.size(); j++){
            VilleState stateJ = STATES.get(j);
            if(!stateJ.equals(villeCourante)){
                double cout = calculerCout(villeCourante, stateJ);
                TRANSITIONS.addTransition(stateJ, action, villeCourante,  cout);
                /*if(!STATES.contains(villeCourante)){
                    Action action_versStateJ = ACTIONS.stream()
                            .filter(elem -> elem.getName().contains(stateJ.getNomVille()))
                            .findAny()
                            .get();
                    TRANSITIONS.addTransition(villeCourante, action_versStateJ, stateJ, cout);
                }*/
            }
        }
    }

    public double calculerCout(VilleState ville1, VilleState ville2){
        if(coutEvalue.equals("distance")){
            return ville1.calculdDitance(ville2);
        } else {
            double distance = ville1.calculdDitance(ville2);
            double vitesse;
            if(STATES.subList(0,50).contains(ville1) && STATES.subList(0,50).contains(ville2)){
                vitesse = 130;
            } else if(STATES.subList(51,100).contains(ville1)&&STATES.subList(51,100).contains(ville2)){
                vitesse = 110;
            } else {
                vitesse =80;
            }
            double temps = distance/vitesse;
            System.out.println("temps : " + ville1.getNomVille() + "/"+ville2.getNomVille()+ " : " + temps);

            return temps;
        }
    }

    public VilleState getInitState() {
        return initState;
    }
}
