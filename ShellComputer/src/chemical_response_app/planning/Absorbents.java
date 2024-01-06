package chemical_response_app.planning;

import java.util.Observable;

/**
 *
 * List of absorbents OK for a spilled material.
 *
 */
public class Absorbents extends Observable {

    protected String[] absorbents;

    protected void setAbsorbents(String[] items) {
        absorbents = items;
        setChanged(); // Notify observers that the absorbents array has changed
        notifyObservers(absorbents); // Pass the updated absorbents array to observers

    }

    public String[] getAbsorbents() {
        return absorbents;
    }

    public String toString() {
        String rslt = new String("\n");
        for (int i = 0; i < this.absorbents.length; i++) {
            rslt += "\n" + this.absorbents[i];
        }
        return rslt;
    }
}
