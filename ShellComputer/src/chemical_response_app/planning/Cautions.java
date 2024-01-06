package chemical_response_app.planning;

import java.util.Observable;

/**
 * List of special cautions for a spilled material.
 *
 */
public class Cautions extends Observable {

    protected String[] cautions;

    protected void setCautions(String[] items) {
        cautions = items;
        setChanged(); // Notify observers that the cautions array has changed
        notifyObservers(cautions); // Pass the updated cautions array to observers
    }

    public String[] getCautions() {
        return cautions;
    }

    public String toString() {
        String rslt = new String("\n");
        if (cautions.length > 0) {
            for (int i = 0; i < cautions.length; i++) {
                rslt += "\n" + cautions[i];
            }
        } else {
            rslt += " none";
        }

        return rslt;
    }
}
