package defi1.algo;

import defi1.framework.common.Action;
import defi1.framework.common.State;
import defi1.framework.recherche.SearchNode;
import defi1.framework.recherche.SearchProblem;
import defi1.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Parcours en largeur
 */
public class BFS extends TreeSearch {

    private List<SearchNode> vus;
    private List<SearchNode> frontiere;

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public BFS(SearchProblem p, State s) {
        super(p, s);
        frontiere = new ArrayList<>();
        vus = new ArrayList<>();
    }

    @Override
    public boolean solve() {
        // On commence à létat initial
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        State state = node.getState();
        frontiere.add(node);

        while(!frontiere.isEmpty()){
            SearchNode searchNodecourant = frontiere.get(0);
            vus.add(searchNodecourant);
            frontiere.remove(searchNodecourant);
            ArrayList<Action> actions = problem.getActions(searchNodecourant.getState());
            for(Action action : actions){
                SearchNode sPrime = SearchNode.makeChildSearchNode(problem, searchNodecourant, action);
                if(problem.isGoalState(sPrime.getState())){
                    end_node = sPrime;
                    return true;
                } else {
                    if(!vus.contains(sPrime)&&!frontiere.contains(sPrime)){
                        frontiere.add(sPrime);
                    }
                }
            }

        }
        return false;
    }
}
