package defi1.probleme;

import defi1.framework.common.State;
import defi1.framework.recherche.Problem;

import java.util.Objects;

public class Ville extends Problem {

    @Override
    public boolean isGoalState(State s) {
        return false;
    }
}
