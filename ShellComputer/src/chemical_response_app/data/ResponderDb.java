package chemical_response_app.data;

import chemical_response_app.GeneralInterface;
import chemical_response_app.planning.ResponseState;
import chemical_response_app.planning.SpillCase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


/*
 * Dummy responder database to stand in for application database
 * will be replaced by adapter, proxy or similar...
 */
public class ResponderDb {

    private static ResponderDb theDb;
    HashMap<String, Responder> responders = null;

    /**
     * Internal record of responder matching system required interface
     *
     */
    class Responder implements GeneralInterface {

        String name;
        Vector<String> contactNumbers;
        Vector<Capability> capabilities;

        public Responder(String name, String[] phones) {
            contactNumbers = new Vector<String>();
            capabilities = new Vector<Capability>();
            this.name = name;
            for (int i = 0; i < phones.length; i++) {
                contactNumbers.add(new String(phones[i]));
            }
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Vector<String> getContacts() {
            Vector<String> c = new Vector<String>();
            Iterator<String> it = this.contactNumbers.iterator();
            while (it.hasNext()) {
                c.add(it.next());
            }
            return c;
        }

        @Override
        public Vector<Capability> capabilities() {
            return capabilities;
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

    }

    /**
     * Just set up some dummy data for now.
     */
    ResponderDb() {
        responders = new HashMap<String, Responder>();

        // add EHS and Public Safety
        String[] numbers1 = {"000-111-2222"};
        Responder r = new Responder("EHS", numbers1);
        r.capabilities.add(Capability.chemicalResponder);
        r.capabilities.add(Capability.respiratoryProtectionProgram);
        responders.put("EHS", r);

        String[] numbers2 = {"111-222-3333", "222-333-4444"};
        r = new Responder("Public Safety", numbers2);
        r.capabilities.add(Capability.chemicalResponder);
        r.capabilities.add(Capability.respiratoryProtectionProgram);
        responders.put("Public Safety", r);

        // add other known responders
        String[] numbers3 = {"111-222-3333", "222-333-4444"};
        r = new Responder("Bill Smith", numbers3);
        r.capabilities.add(Capability.chemicalResponder);
        r.capabilities.add(Capability.respiratoryProtectionProgram);
        responders.put("Bill Smith", r);

        String[] numbers4 = {"111-222-4444", "222-333-4444"};
        r = new Responder("Akiko Kurasawa", numbers4);
        r.capabilities.add(Capability.chemicalResponder);
        responders.put("Akiko Kurasawa", r);

        String[] numbers5 = {"111-222-5555", "222-333-5555"};
        r = new Responder("Mary Shelly", numbers5);
        r.capabilities.add(Capability.chemicalResponder);
        r.capabilities.add(Capability.respiratoryProtectionProgram);
        responders.put("Mary Shelly", r);

        String[] numbers6 = {"222-333-66666"};
        r = new Responder("Django Reihardt", numbers6);
        r.capabilities.add(Capability.chemicalResponder);
        responders.put("Django Reinhardt", r);

    }

    public Responder get(String name) {
        return responders.get(name);
    }

    /**
     * Want a singleton so get DB this way.
     *
     * @return the singleton responder DB
     */
    public static ResponderDb getDb() {
        if (theDb == null) {
            theDb = new ResponderDb();
        }
        return theDb;
    }

}
