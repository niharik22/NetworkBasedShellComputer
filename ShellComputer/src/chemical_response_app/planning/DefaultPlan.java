package chemical_response_app.planning;

import java.util.Iterator;
import java.util.Vector;

import chemical_response_app.GeneralInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The default plan for any un-resolvable situation: unrecognized materials,
 * locations, responders or unsure responder or spill too large to handle
 */
public class DefaultPlan extends Plan {

    private String campusListInfo;

    public DefaultPlan() {
        state = ResponseState.notify;
        interview = null;
        responders = new Vector<GeneralInterface>();
        immediateActions = null;

        containActions = null;
        absorbents = null;
        cautions = null;
        notifications = new Vector<String>();
        notifications.add(new String(
                "Call one or more of the listed responders:"));
    }

    /**
     * It is redundant to look up most of the default info.(and error prone!) So
     * just clone = a trivial example of Prototype
     *
     * @param interview
     * @return a clone of the default plan
     */
    protected DefaultPlan clone(Interview interview) {
        DefaultPlan p = new DefaultPlan();
        /*
		 * try { p = (DefaultPlan)this.clone(); } catch
		 * (CloneNotSupportedException e) { // TODO Auto-generated catch block
		 * System.err.println("Clone failed!!"); e.printStackTrace(); } // TODO:
		 * causes clone not supported exception
         */
        p.immediateActions = this.immediateActions;
        p.location = this.location;
        p.responders = this.responders;
        p.state = this.state;
        p.interview = interview;
        return p;
    }

    public String toString() {
        String rslt = interview.toString();

        /*
		 * rslt += "\n   IMMEDIATE ACTIONS"; Iterator<Action> it =
		 * immediateActions.iterator(); while (it.hasNext()) { Action a =
		 * it.next(); rslt += "\n\n" + a.toString(); }
         */
        rslt += "\n\n   NOTIFICATIONS";
        Iterator<String> nit = notifications.iterator();
        while (nit.hasNext()) {
            String a = nit.next();
            rslt += "\n\n" + a.toString();
        }

        rslt += "\n\n    FOR HELP";
        rslt += "\n\n Responder list:";
        Iterator<GeneralInterface> rit = responders.iterator();
        while (rit.hasNext()) {
            GeneralInterface r = rit.next();
            rslt += "\n" + r.getName() + " at " + r.getContacts();
        }
        return rslt;
    }

    public Plan makePlan(String[] inputs, int sz) {
        //System.out.println("\n\n\t plan forming\n" + this);
        setChanged();
        notifyObservers(this.toString());
        return this;
    }

    public String printDefaultRespondersInfo() {
        String output;
        output = interview.toString();

        output += this.printStructuredCampusInformation();

        output += "\n\n   NOTIFICATIONS";
        Iterator<String> nit = notifications.iterator();
        while (nit.hasNext()) {
            String a = nit.next();
            output += "\n\n" + a.toString();
        }
        //For the responder view, only retrieve the information of 'EHS' and 'Public Safety'
        output += "\n\n    FOR HELP";
        output += "\n\n Responder list:";
        Iterator<GeneralInterface> rit = responders.iterator();
        while (rit.hasNext()) {
            GeneralInterface r = rit.next();
            if (r.getName() == "EHS" || r.getName() == "Public Safety") {
                output += "\n" + r.getName() + " at " + r.getContacts();

            }

        }

        return output;

    }

    /**
     * Method for returning the tree structured information of the Materials,
     * Room, Building, and Campus to be printed in GUI
     *
     */
    private String printStructuredCampusInformation() {

        ArrayList<String> listContainingDuplictesOfMaterials = new ArrayList();

        String output = "";
        output += "\n";
        output += "\n";
        output += "\n";
        output += "Tree structure of csu information for spill: ";
        output += "\n";
        output += "---------------------------------------------------------------";
        output += "\n";

        output += getCampusListInfo();

        output += "\n";
        output += "\n";
        output += "\n";
        output += "List of materials needed for the related spill: ";
        output += "\n";
        output += "(ALPHABETICAL ORDERING)";
        output += "\n";
        output += "---------------------------------------------------------------";
        output += "\n";
        materials1.addMaterials();
        iterator.Iterator m_iterator = materials1.generateIterator();
        try {
            for (m_iterator.firstElement(); m_iterator.getIteratorPosition() <= m_iterator.getSize();
                    m_iterator.nextElement()) {

                listContainingDuplictesOfMaterials.add(m_iterator.currentElement());

            }

        } catch (IndexOutOfBoundsException e) {
            output += "";
        }
        HashSet<String> listOfNoDuplicatesForMaterials = new HashSet<String>(listContainingDuplictesOfMaterials);
        Set<String> sortedMaterials = new TreeSet<String>(listOfNoDuplicatesForMaterials);

        Iterator materialIterater = sortedMaterials.iterator();
        while (materialIterater.hasNext()) {
            output += "   ";
            output += materialIterater.next();
            output += "\n";
        }
        return output;
    }

    /**
     * Method for returning the tree structured information of the Materials,
     * Room, Building, and Campus to be printed in GUI
     *
     */
    public String returnStructuredCampusInformation() {

        ArrayList<String> listContainingDuplictesOfMaterials = new ArrayList();

        String output = "";
        output += "\n***Tree structure of csu information for spill***\n";
        output += "---------------------------------------------------------------";
        output += getCampusListInfo();

        output += "\n\nList of materials needed for the related spill: \n";
        output += "(ALPHABETICAL ORDERING)\n\n";
        materials1.addMaterials();
        iterator.Iterator m_iterator = materials1.generateIterator();
        try {
            for (m_iterator.firstElement(); m_iterator.getIteratorPosition() <= m_iterator.getSize();
                    m_iterator.nextElement()) {

                listContainingDuplictesOfMaterials.add(m_iterator.currentElement());
            }

        } catch (IndexOutOfBoundsException e) {
            output += "";
        }
        HashSet<String> listOfNoDuplicatesForMaterials = new HashSet<String>(listContainingDuplictesOfMaterials);
        Set<String> sortedMaterials = new TreeSet<String>(listOfNoDuplicatesForMaterials);

        Iterator materialIterater = sortedMaterials.iterator();
        while (materialIterater.hasNext()) {
            output += "   ";
            output += materialIterater.next();
            output += "\n";
        }
        return output;
    }

    private String getCampusListInfo() {
        if (campusListInfo == null) {
            campusListInfo = csu.listInformation().toString();
        }
        return campusListInfo;
    }
}
