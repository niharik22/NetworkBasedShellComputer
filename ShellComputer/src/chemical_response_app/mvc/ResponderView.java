package chemical_response_app.mvc;

import chemical_response_app.planning.Absorbents;
import chemical_response_app.planning.Cautions;
import chemical_response_app.planning.DefaultPlan;
import chemical_response_app.planning.Plan;
import chemical_response_app.planning.Planner;
import kernelfilingsystem.kernel.*;
import java.awt.BorderLayout;

/**
 * Responder view is the point of contact between the spill responder and the
 * system. When the interview has been completed (or before) pressing the
 * getPlan button builds a response plan based on the current state of the
 * interview fields
 */
import java.awt.Button;
import java.awt.Panel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent; //for CloseListener()
import java.awt.event.WindowAdapter; //for CloseListener()
import java.util.Observable; //for update();
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chemical_response_app.planning.SpillCase;

/**
 * A simple responder view for use in development. major parts are: above: a
 * series of fields to be filled out center are large area for text display
 * bottom: a single button to get a plan
 *
 * BTW: Notice the remaining slight error in the display of long plans. We will
 * not get to involved with Swing beyond finding the 'hooks' for the Observer
 * pattern required to plumb the MVC.
 */
public class ResponderView implements java.util.Observer {

    // attributes as must be visible within class
    private Button button;
    private JTextField lastNameTextField;
    private JSeparator jSeparator1;
    private JScrollPane jScrollPane1;
    private ButtonGroup materialButtonGroup;
    private JRadioButton acidChlorideRadioButton;
    private JRadioButton generalSpilljRadioButton;
    private JTextArea responseArea;
    private JTextField sizeTextField;
    private JLabel sizeLabel;
    private JLabel materialLabel;
    private JTextField roomTextField;
    private JLabel roomLabel;
    private JTextField buildingTextField;
    private JLabel buildingLabel;
    private JLabel jLabel1;
    private JTextField firstNameTextField;
    private JLabel lastNameLabel;
    private JPanel responderPanel;
    private chemical_response_app.mvc.WERSController controller;
    private ResponderView view;

    public ResponderView() {

        Plan planInstance = new Plan();
        planInstance.addObserver(this);

        Planner plannerInstance = new Planner();
        plannerInstance.addObserver(this);

        DefaultPlan defaultPlanInstance = new DefaultPlan();
        defaultPlanInstance.addObserver(this);

        Absorbents absorbentsInstance = new Absorbents();
        absorbentsInstance.addObserver(this);

        Cautions cautionsInstance = new Cautions();
        cautionsInstance.addObserver(this);

        System.out.println("View()" + this);
        view = this;
        Frame frame = new Frame("CSU Emergency Response System");
        Panel panel = new Panel();
        {
            button = new Button("NEXT");
            panel.add(button);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    controller.requestPlan(view);
                }
            });
            button.setLabel("GET PLAN");
        }
        frame.add("South", panel);
        {
            responderPanel = new JPanel();
            frame.add(responderPanel, BorderLayout.NORTH);
            frame.add(getJScrollPane1(), BorderLayout.CENTER);
            responderPanel.setPreferredSize(new java.awt.Dimension(362, 174));
            {
                lastNameLabel = new JLabel();
                responderPanel.add(lastNameLabel);
                lastNameLabel.setText("first name:");
            }
            {
                firstNameTextField = new JTextField();
                responderPanel.add(firstNameTextField);
                firstNameTextField.setText("unknown");
                firstNameTextField.setPreferredSize(new java.awt.Dimension(263,
                        20));
            }
            {
                jLabel1 = new JLabel();
                responderPanel.add(jLabel1);
                jLabel1.setText("last name:");
            }
            {
                lastNameTextField = new JTextField();
                responderPanel.add(lastNameTextField);
                lastNameTextField.setText("unknown");
                lastNameTextField.setPreferredSize(new java.awt.Dimension(263,
                        23));
            }
            {
                buildingLabel = new JLabel();
                responderPanel.add(buildingLabel);
                buildingLabel.setText("accident bldg:");
                buildingLabel.setPreferredSize(new java.awt.Dimension(104, 16));
            }
            {
                buildingTextField = new JTextField();
                responderPanel.add(buildingTextField);
                buildingTextField.setText("unknown");
                buildingTextField.setPreferredSize(new java.awt.Dimension(215,
                        23));
            }
            {
                roomLabel = new JLabel();
                responderPanel.add(roomLabel);
                roomLabel.setText("accident room:");
            }
            {
                roomTextField = new JTextField();
                responderPanel.add(roomTextField);
                roomTextField.setText("unknown");
                roomTextField.setPreferredSize(new java.awt.Dimension(239, 23));
            }
            {
                materialLabel = new JLabel();
                responderPanel.add(materialLabel);
                responderPanel.add(getGeneralSpilljRadioButton());
                responderPanel.add(getAcidChlorideRadioButton());
                materialLabel.setText("material spilled:");
                materialButtonGroup = getMaterialButtonGroup();

            }
            {
                sizeLabel = new JLabel();
                responderPanel.add(sizeLabel);
                sizeLabel.setText("size: in inches");
            }
            {
                sizeTextField = new JTextField();
                responderPanel.add(sizeTextField);
                responderPanel.add(getJSeparator1());
                sizeTextField.setText("100");
            }
        }

        frame.addWindowListener(new CloseListener());
        frame.setSize(400, 600);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        frame.setPreferredSize(new java.awt.Dimension(362, 600));

    } // View()

    /**
     * Call-backs from the observed plan come here. We don't use too much of our
     * capabilities here. Just push the message onto the text area.
     */
    @Override
    public void update(Observable obs, Object obj) {

        // who called us and what did they send?
        // System.out.println ("View      : Observable is " + obs.getClass() +
        // ", object passed is " + obj.getClass());
        // model Pull - ignore obj and ask model for value,
        // un-comment next line to do Model Pull
        // myTextField.setText("" + model.getValue());
        // model Push
        // parse obj not needed yet
        if (obs instanceof Plan || obs instanceof Planner || obs instanceof DefaultPlan || obs instanceof Absorbents || obs instanceof Cautions) {
            System.err.println("Update from " + obs.hashCode() + " to " + this);
            System.err.println(obj + "\n END OF MSG");
            responseArea.setLineWrap(true);
            //writeTextToFile((String) obj);
            //responseArea.setText((String) obj);
            responseArea.setText(((DefaultPlan) obs).printDefaultRespondersInfo());
            String structuredMessage = ((DefaultPlan) obs).returnStructuredCampusInformation();
            //int writeSuccessfull = writeStructuredMessageToFile(structuredMessage);
            writeStructuredMessageToFile(structuredMessage);
        }
    }

    private static int writeStructuredMessageToFile(String info) {
        int fileExists = Library.create("Wers");
        int p = 0;
        byte[] buf = new byte[Math.max(KernelSystem.blockSize, info.length() - p)];
        int i = 0;
        while (p < info.length()) {
            buf[i++] = (byte) info.charAt(p++);
        }
        if (fileExists == -1) {
            System.out.println("ResponderView: Writing Plan to existing File : Wers on the disk");
        } else if (fileExists == 0) {
            System.out.println("ResponderView: Writing Plan to New File : Wers created on the disk");
        }
        return Library.write("Wers", buf);
    }

    /**
     * Hooks the view to the controller.
     *
     * @param myController
     */
    public void addController(WERSController myController) {
        view.controller = myController;
        System.out.println("View      : adding controller " + controller);
        // button.addActionListener(myController); // need instance of
        // controller before we can reference it
    }

    public JPanel getResponderPanel() {
        return responderPanel;
    }

    public static class CloseListener extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }

    /**
     * Extract the responder information for use by the controller and plan.
     *
     * @return String with responders information as entered.
     */
    public String[] getResponderInfo() {
        // TODO: map names to element indices
        String[] responder = new String[45];
        responder[0] = this.lastNameTextField.getText();
        responder[1] = this.firstNameTextField.getText();
        responder[2] = this.buildingTextField.getText();
        responder[3] = this.roomTextField.getText();
        return responder;
    }

    /**
     * Only two spill cases so far but the returns the proper enumeration
     * constant so it is easy to add many more. Could do something more elegant
     * her!
     *
     * @return a SpillCase
     */
    public SpillCase getMaterial() {
        if (acidChlorideRadioButton.isSelected()) {
            return SpillCase.acidChloride;
        } else {
            return SpillCase.standard;
        }
    }

    /**
     *
     * @return estimated width of spill in inches
     */
    public String getSize() {
        return this.sizeTextField.getText();
    }

    private JRadioButton getGeneralSpilljRadioButton() {
        if (generalSpilljRadioButton == null) {
            generalSpilljRadioButton = new JRadioButton();
            generalSpilljRadioButton.setText("general");
            generalSpilljRadioButton.setPreferredSize(new java.awt.Dimension(
                    99, 20));
        }
        return generalSpilljRadioButton;
    }

    private JRadioButton getAcidChlorideRadioButton() {
        if (acidChlorideRadioButton == null) {
            acidChlorideRadioButton = new JRadioButton();
            acidChlorideRadioButton.setText("acid chloride");
            acidChlorideRadioButton.setPreferredSize(new java.awt.Dimension(
                    118, 20));
        }
        return acidChlorideRadioButton;
    }

    private ButtonGroup getMaterialButtonGroup() {
        if (materialButtonGroup == null) {
            materialButtonGroup = new ButtonGroup();
        }
        materialButtonGroup.add(generalSpilljRadioButton);
        generalSpilljRadioButton.setSelected(true);
        materialButtonGroup.add(acidChlorideRadioButton);
        return materialButtonGroup;
    }

    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            // TODO: fix Ugly kludge for truncated plan display
            jScrollPane1.setPreferredSize(new java.awt.Dimension(662, 1893));
            {
                responseArea = new JTextArea();
                jScrollPane1.setViewportView(responseArea);
                responseArea.setPreferredSize(new java.awt.Dimension(662, 1893));
                responseArea.setWrapStyleWord(true);
            }
        }
        return jScrollPane1;
    }

    private JSeparator getJSeparator1() {
        if (jSeparator1 == null) {
            jSeparator1 = new JSeparator();
            jSeparator1.setPreferredSize(new java.awt.Dimension(206, 11));
        }
        return jSeparator1;
    }
}
