package chemical_response_app.mvc;

import java.awt.BorderLayout;

/**
 * Administrator view This view observes all plans through interaction with the
 * model and through callbacks from plans as they change.
 */
import java.awt.Frame;
import java.awt.event.WindowEvent; 		//for CloseListener()
import java.awt.event.WindowAdapter; 	//for CloseListener()
import java.util.Observable; 			//for update();

import javax.swing.JTextArea;

public class AdminView implements java.util.Observer {

    // attributes as must be visible within class
    private JTextArea responsejTextArea1;

    private WERSController controller;

    //TODO need to be re-checked
    public AdminView() {
        System.out.println("View()" + this);

        // frame in constructor and not an attribute as doesn't need to be
        // visible to whole class
        Frame frame = new Frame("CSU Emergency Response System");
        // panel in constructor and not an attribute as doesn't need to be
        // visible to whole class
        frame.addWindowListener(new CloseListener());
        frame.setSize(400, 600);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        frame.setPreferredSize(new java.awt.Dimension(557, 277));
        frame.setTitle("WERS Admin View");
        {
            responsejTextArea1 = new JTextArea();
            frame.add(responsejTextArea1, BorderLayout.CENTER);
            responsejTextArea1.setPreferredSize(new java.awt.Dimension(557, 95));
        }

    }

    /**
     * Called from the Model(s) (i.e. plans with changes
     */
    @Override
    public void update(Observable obs, Object obj) {
//        System.err.println("Update from " + obs.hashCode() + " to " + this);
//        System.err.println(obj + "\n END OF MSG");
        responsejTextArea1.setLineWrap(true);
        responsejTextArea1.setText((String) obj);
    }

    /**
     * Hooks the view to the controller. 
     *
     * @param myController
     */
    public void addController(WERSController myController) {
        this.controller = myController;
        System.out.println("View      : adding controller " + controller);
    }

    public static class CloseListener extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }

    public void init(String viewsData) {
        responsejTextArea1.setText(viewsData);
    }

}
