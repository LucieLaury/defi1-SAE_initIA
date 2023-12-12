package defi1;

import java.io.IOException;
import defi1.framework.RequeteAPI;
import defi1.framework.common.ArgParse;
import defi1.framework.common.State;
import defi1.probleme.Ville;

public class Main {

    public static void main(String[] args) throws IOException {
        // créer un problem, un état intial et un  algo
        Ville p = (Ville) ArgParse.makeProblem();
        p.initGoalState(RequeteAPI.construireState_for_Ville("Besançon"));
        State s = ArgParse.makeInitialState("Lille");
        //TreeSearch algo = ArgParse.makeAlgo("bfs", p, s);

        // resoudre
       /* boolean algoSolve = algo.solve();
        //System.out.println("algo.solves : " + algoSolve);
        if( algoSolve)
            algo.printSolution();*/
    }






}
