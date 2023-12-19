package defi1.algo;



import defi1.framework.common.Action;
import defi1.framework.common.State;
import defi1.framework.recherche.SearchNode;
import defi1.framework.recherche.SearchProblem;
import defi1.framework.recherche.TreeSearch;
import defi1.probleme.Ville;
import defi1.probleme.VilleState;

import java.util.ArrayList;
import java.util.HashSet;

public class AStar extends TreeSearch {

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public AStar(SearchProblem p, State s) {
        super(p, s);
    }

    @Override
    public boolean solve() {

        SearchNode node = SearchNode.makeRootSearchNode(intial_state);

        // Initialisation de la frontière et des vues
        frontier = new ArrayList<>();
        frontier.add(node);
        explored = new HashSet<>();

        while( !frontier.isEmpty()) {

            // Prendre le noeud le moins coûteux (ici le premier car le tableau est trié)
            node = frontier.get(0);
            frontier.remove(0);

            State state = node.getState();

            // Si le noeud contient un état brut
            if (problem.isGoalState(state)) {
                Action action = ((Ville) this.problem).createGoalAction((VilleState) state);
                SearchNode goal = SearchNode.makeChildSearchNode(this.problem, node, action );
                this.end_node = goal;
                frontier = new ArrayList<>(); // ????
                return true;
            } else {
                explored.add(state);
                // Les actions possibles depuis cette état
                ArrayList<Action> actions = problem.getActions(state);
                for (Action a : actions) {
                    SearchNode childNode = SearchNode.makeChildSearchNode(problem, node, a);
                    // Si pas dans frontier et explored
                    if (frontier.contains(childNode)) {
                        SearchNode oldNoeud = frontier.get(frontier.indexOf(childNode));
                        if (oldNoeud.getHeuristic()+ oldNoeud.getCost() > childNode.getHeuristic()+childNode.getCost() ) {
                            frontier.set(frontier.indexOf(childNode), childNode);
                        }
                    }
                    if (!frontier.contains(childNode) && !explored.contains(childNode.getState())&&(childNode.getCost()< node.getHeuristic())) {
                        frontier.add(childNode);
                    }
                    // Si dans frontier, vérifier le coût des deux noeuds et garder le moins coûteux
                }

            }

        }

        return false;
    }
}
