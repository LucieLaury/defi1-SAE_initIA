package defi1.probleme;

import defi1.framework.common.State;
import defi1.framework.recherche.HasHeuristic;

public class VilleState extends State implements HasHeuristic {

    private Ville ville;
    private Heuristique heuristique;

    public VilleState(Ville ville, Heuristique heuristique){
        this.ville = ville;
        this.heuristique = heuristique;
    }

    @Override
    protected State cloneState() {
        return new VilleState(ville, heuristique);
    }

    @Override
    protected boolean equalsState(State o) {
        VilleState otherState = (VilleState) o;
        return (otherState.ville.equals(ville));
    }

    @Override
    protected int hashState() {
        return 0;
    }

    @Override
    public double getHeuristic() {
        return this.heuristique.getHeuristique();
    }
}
