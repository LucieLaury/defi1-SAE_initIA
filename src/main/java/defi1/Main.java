package defi1;

import java.io.IOException;
import defi1.framework.RequeteAPI;
import defi1.framework.common.ArgParse;
import defi1.framework.common.State;
import defi1.framework.recherche.TreeSearch;
import defi1.probleme.Ville;

public class Main {

    public static void main(String[] args) throws IOException {
        // créer un problem, un état intial et un  algo
        Ville p = (Ville) ArgParse.makeProblem("Hyères","Gravelines", "vitesse");
        State s = ArgParse.makeInitialState(p);
        TreeSearch algo = ArgParse.makeAlgo("ucs", p, s);

        // resoudre
        long startTime = System.nanoTime();
       boolean algoSolve = algo.solve();
       long endTime = System.nanoTime();
        System.out.println("algo.solves : " + algoSolve);
        if( algoSolve)
            algo.printSolution();
        System.out.println("temps d'exécution : " + (endTime-startTime));
    }






}
