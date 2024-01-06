package chemical_response_app.planning;

import chemical_response_app.GeneralInterface;
import chemical_response_app.data.Capability;
import java.util.Vector;

/**
 *
 * Enumeration of spill cases.
 *
 * There are only two but the spill documentation lists least at least half a
 * dozen. We leave the structure open for their addition
 *
 * The current two types are in the ResponderView and will come into play in
 * week on composite as examples.
 *
 */
public enum SpillCase implements GeneralInterface {
    standard, acidChloride;

    @Override
    public SpillCase getStandard() {
        return this.standard;
    }

    @Override
    public SpillCase getAcidChloride() {
        return this.acidChloride;
    }

    @Override
    public SpillCase getRelatedSpillCase() {
        switch (this) {
            case standard:
                return standard;
            case acidChloride:
                return acidChloride;
            default:
                return standard;
        }
    }

    @Override
    public ResponseState getImmediate() {
        return null;
    }

    @Override
    public ResponseState getContain() {
        return null;
    }

    @Override
    public ResponseState getClean() {
        return null;
    }

    @Override
    public ResponseState getNotify() {
        return null;
    }

    @Override
    public ResponseState next() {
        return null;
    }

    @Override
    public Capability getChemicalResponder() {
        return null;
    }

    @Override
    public Capability getRespiratoryProtectionProgram() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Vector<String> getContacts() {
        return null;
    }

    @Override
    public Vector<Capability> capabilities() {
        return null;
    }
}
