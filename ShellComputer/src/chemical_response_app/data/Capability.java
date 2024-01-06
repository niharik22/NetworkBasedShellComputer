package chemical_response_app.data;

import chemical_response_app.GeneralInterface;
import chemical_response_app.planning.ResponseState;
import chemical_response_app.planning.SpillCase;
import java.util.Vector;

/**
 * Enumerates the possible and required training of responders.
 */
public enum Capability implements GeneralInterface {
    chemicalResponder, respiratoryProtectionProgram;

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
        return this.chemicalResponder;
    }

    @Override
    public Capability getRespiratoryProtectionProgram() {
        return this.respiratoryProtectionProgram;
    }

    @Override
    public SpillCase getStandard() {
        return null;
    }

    @Override
    public SpillCase getAcidChloride() {
        return null;
    }

    @Override
    public SpillCase getRelatedSpillCase() {
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
