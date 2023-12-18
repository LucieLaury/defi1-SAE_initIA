package defi1.algo;

import defi1.framework.common.Action;
import defi1.framework.common.ArgParse;
import defi1.framework.common.State;
import defi1.framework.recherche.Problem;
import defi1.framework.recherche.SearchNode;
import defi1.framework.recherche.SearchProblem;
import defi1.framework.recherche.TreeSearch;
import defi1.probleme.Ville;
import defi1.probleme.VilleState;

import java.util.ArrayList;

public class AStar extends TreeSearch {

    private ArrayList<SearchNode> frontiere = new ArrayList<>();

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public AStar(SearchProblem p, State s) {
        super(p, s);
    }

    /**
     * Lance la recherche pour résoudre le problème
     * <p>A concrétiser pour chaque algorithme.</p>
     * <p>La solution devra être stockée dans end_node.</p>
     *
     * @return Vrai si solution trouvé
     */
    @Override
    public boolean solve() {
        SearchNode currentNode = SearchNode.makeRootSearchNode(this.intial_state);
        //Ajout du noeud initial dans la liste des noeuds à parcourir
        frontiere.add(currentNode);
        if(ArgParse.DEBUG) {
            System.out.println("["+ currentNode.getState());
        }

        //Tant qu'il y a des noeuds à parcourir, on continue
        while(!frontiere.isEmpty()) {

            //On récupère le premier élément à inspecter
            //On le supprime de cette liste et on l'ajoute dans la liste d'élément à parcourir
            currentNode = frontiere.get(0);
            frontiere.remove(currentNode);
            explored.add(currentNode.getState());

            //Pour le noeud courant, on créer ses noeuds fils à partir de chaque actions possibles
            //Si le noeud fils crée n'est pas le but, on l'ajoute à la liste des noeuds à parcourir sinon, le problème est résolu
            for (Action a : this.problem.getActions(currentNode.getState())) {
                SearchNode childNode = SearchNode.makeChildSearchNode(this.problem, currentNode, a);
                if(ArgParse.DEBUG) {
                    System.out.println(" + " + a + "] -> [" + childNode.getState());
                }
                if(this.problem.isGoalState(childNode.getState())) {
                    Action action = ((Ville) this.problem).createGoalAction((VilleState) childNode.getState());

                    SearchNode goal = SearchNode.makeChildSearchNode(this.problem, childNode, action );
                    this.end_node = goal;
                    if (ArgParse.DEBUG) {
                        System.out.println("]");
                    }
                    return true;
                }

                if (!explored.contains(childNode.getState()) && !frontiere.contains(childNode)){
                    int i = 0;
                    while(i < frontiere.size()) {
                        SearchNode n = frontiere.get(i);
                        if(n.getCost() + n.getHeuristic() > childNode.getCost() + childNode.getHeuristic()) {
                            frontiere.add(i, childNode);
                            break;
                        }
                        i++;
                    }
                    if(i == frontiere.size()) {
                        frontiere.add(childNode);
                    }
                }
            }
        }
        System.out.println("Aucune solution trouvée");
        return false;
    }
}
