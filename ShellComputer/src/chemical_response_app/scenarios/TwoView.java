package chemical_response_app.scenarios;

import chemical_response_app.mvc.ResponderView;
import chemical_response_app.mvc.WERSController;

/**
 * Simple simulation of two responder view.
 * Connects responder views and controller.
 * Controller connects to planner thence to plans and registers 
 *   the view with the plan for the Observer pattern.
 */
public class TwoView {
	
	public static void main(String [] args) {
		//create Model and View
		WERSController myController = new WERSController();

		//tell Model about View. 
		ResponderView myView1 	= new ResponderView();
		myController.addView(myView1);
		myView1.addController(myController);

		
		ResponderView myView2 	= new ResponderView();
		myController.addView(myView2);
		myView2.addController(myController);
	} 

}
