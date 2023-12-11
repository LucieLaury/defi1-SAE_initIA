package defi1.probleme;

import java.util.Objects;

public class Ville {
    private Coordonnees coordonnees;
    private int population;





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return population == ville.population && coordonnees.equals(ville.coordonnees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordonnees, population);
    }
}
