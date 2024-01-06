package chemical_response_app.planning;

import java.util.Vector;
import java.util.HashMap;

import chemical_response_app.mvc.ResponderView;

import chemical_response_app.data.AreaDb;
import chemical_response_app.data.AreaInformation;
import chemical_response_app.data.ResponderDb;
import chemical_response_app.GeneralInterface;
import chemical_response_app.mvc.AdminView;
import java.util.Observable;
import java.util.Observer;


/*
 * A singleton planner instantiating data base and standard plan elements
 * primary purpose is to map views to models and establish correct observation
 * */
public class Planner extends Observable {

    public static Planner thePlanner = null;
    public Vector<Plan> plans = null;

    private final AreaDb areaDb = AreaDb.getDb();
    final private ResponderDb responderDb = ResponderDb.getDb();

    public Vector<GeneralInterface> defaultResponders = null;
    public DefaultPlan theDefaultPlan = null;
    Vector<Observer> views = new Vector<Observer>();

    HashMap<ResponderView, Plan> planMap = new HashMap<ResponderView, Plan>();

    public Planner() {
        plans = new Vector<Plan>();
        defaultResponders = new Vector<GeneralInterface>();
        GeneralInterface r = responderDb.get("EHS"); // EHS should always be there!
        defaultResponders.add(r);
        r = responderDb.get("Public Safety"); // Public Safety should always be there!
        defaultResponders.add(r);
        theDefaultPlan = new DefaultPlan();
    }

    // return the singleton planner - creating a planner if none exists
    public static Planner getThePlanner() {
        if (thePlanner == null) {
            thePlanner = new Planner();
        }
        return thePlanner;
    }

    // return a plan from information given for interview
    // used by unit tests
    public Plan getPlan(String[] inputs, SpillCase spill, int sz) {
        Interview interview = new Interview(inputs, spill, sz);
        return getPlan(interview);
    }

    // returns a plan determined by contents of the interview
    private Plan getPlan(Interview interview) {
        Plan p = Plan.buildActionPlan(this, interview);
        plans.add(p);
        // Notify observers about the new plan
        setChanged();
        notifyObservers(p.toString());

        return p;
    }

    // record a responder view in the planner
    // TODO: complete view <--> plan map
    public void addView(ResponderView v) {
        System.out.println("Planner: adding view" + v);
        views.add(v);
    }

    // TODO: change to AdminView
    public void registerView(AdminView view) {
        this.views.add(view);
    }

    // find a responder in the database 
    // exceptions for name not found or name not qualified
    // TODO: eventually improve name match
    GeneralInterface getResponder(String contact, SpillCase material) {
        GeneralInterface r = responderDb.get(contact);
        // TODO: add real check for qualifications
        return r;
    }

    // return area information information
    public AreaInformation findArea(String rm, String bldg) {
        //AreaInfo info = database.getArea(rm, bldg);
        int bldgNo = 0;
        int roomNo = 0;
        try {
            bldgNo = Integer.parseInt(bldg);
        } catch (NumberFormatException e) {
            bldgNo = 0;
        }
        try {
            roomNo = Integer.parseInt(rm);
        } catch (NumberFormatException e) {
            roomNo = 0;
        }
        // just return EHS if building, room unrecognized!
        AreaInformation info = areaDb.find(bldgNo, roomNo);
        return info;
    }

    // EHS information used as a default
    public AreaInformation getEhsAreaInfo() {
        return areaDb.ehsAreaInfo;
    }

    public void consumeSpillKit(String building, String room) {
        /// database.consumeSpillKit(building,room);
        int rm = Integer.parseInt(room);
        int bldg = Integer.parseInt(building);
        areaDb.consumeSpillKit(bldg, rm);
    }

}
