package defi1.algo;



import defi1.framework.common.Action;
import defi1.framework.common.State;
import defi1.framework.recherche.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class DFS extends TreeSearch {

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public DFS(SearchProblem p, State s) {
        super(p, s);
    }

    @Override
    public boolean solve() {

        SearchNode node = SearchNode.makeRootSearchNode(intial_state);

        // Initialisation de la frontière et des vues
        frontier = new ArrayList<>();
        frontier.add(node);
        explored = new HashSet<>();

        //System.out.println(frontier);
        //System.out.println(explored);

        while( !frontier.isEmpty()) {

            // Prendre le premier noeud et le supprimer (FIFO)
            node = frontier.get(0);
            frontier.remove(0);

            State state = node.getState();

            // Si le noeud contient un état brut
            if (problem.isGoalState(state)) {
                end_node = node;
                frontier = new ArrayList<>(); // ????
            } else {
                explored.add(state);
                ArrayList<SearchNode> temp = new ArrayList<>();
                // Les actions possibles depuis cette état
                ArrayList<Action> actions = problem.getActions(state);
                for (Action a : actions) {
                    SearchNode childNode = SearchNode.makeChildSearchNode(problem, node, a);
                    if (!frontier.contains(childNode) && !explored.contains(childNode.getState())) {
                        temp.add(childNode);
                    }
                }
                temp.addAll(frontier);
                frontier = temp;
            }

        }

        return true;
    }

}
