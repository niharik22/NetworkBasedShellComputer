package chemical_response_app;

import chemical_response_app.data.Capability;
import chemical_response_app.planning.ResponseState;
import chemical_response_app.planning.SpillCase;
import java.util.Vector;

/**
 *
 * @author nihar
 */
public interface GeneralInterface {

    public String getName();

    public Vector<Capability> capabilities();

    public Vector<String> getContacts();

    ResponseState getImmediate();

    ResponseState getContain();

    ResponseState getClean();

    ResponseState getNotify();

    ResponseState next();

    Capability getChemicalResponder();

    Capability getRespiratoryProtectionProgram();

    SpillCase getRelatedSpillCase();

    SpillCase getStandard();

    SpillCase getAcidChloride();
}
