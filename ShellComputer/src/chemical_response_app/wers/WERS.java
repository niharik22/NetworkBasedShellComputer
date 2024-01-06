package chemical_response_app.wers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import chemical_response_app.scenarios.OneView;
import chemical_response_app.scenarios.TwoView;
import chemical_response_app.scenarios.TwoViewAdmin;

/**
 * Top level entry into the planning simulations.
 *
 * Starts one, two or three (2 responder, 1 admin) views.
 *
 * Although the final system will probably be a service, we can simulate most of
 * our logical problems in this simple way.
 */
public class WERS extends javax.swing.JFrame {

    private static final long serialVersionUID = -1968991847244548656L;

    public static void invokeWERS(WERS wersInstance) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                wersInstance.setLocationRelativeTo(null);
                wersInstance.setVisible(true);
            }
        });
    }

    private JMenuItem helpMenuItem;
    private JMenu jMenu5;
    private JButton runTwoPlusAdminjButton;
    private JButton runTwoViewsJButton;
    private JButton runOneViewJButton;
    private JPanel runPanel;
    private JMenuItem deleteMenuItem;
    private JSeparator jSeparator1;
    private JMenuItem pasteMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem cutMenuItem;
    private JMenu jMenu4;
    private JMenuItem exitMenuItem;
    private JSeparator jSeparator2;
    private JMenuItem closeFileMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem openFileMenuItem;
    private JMenuItem newFileMenuItem;
    private JMenu jMenu3;
    private JMenuBar jMenuBar1;

    public WERS() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
            {
                this.setTitle("WERS runner");
            }
            {
                runPanel = new JPanel();
                getContentPane().add(runPanel, BorderLayout.NORTH);
                runPanel.setPreferredSize(new java.awt.Dimension(384, 64));
                {
                    runOneViewJButton = new JButton();
                    runPanel.add(runOneViewJButton);
                    runOneViewJButton.setText("Run one view");
                    runOneViewJButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            runOneViewJButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    runTwoViewsJButton = new JButton();
                    runPanel.add(runTwoViewsJButton);
                    runTwoViewsJButton.setText("run two views");
                    runTwoViewsJButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            runTwoViewsJButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    runTwoPlusAdminjButton = new JButton();
                    runPanel.add(runTwoPlusAdminjButton);
                    runTwoPlusAdminjButton.setText("run two views and admin view");
                    runTwoPlusAdminjButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            runTwoPlusAdminjButtonActionPerformed(evt);
                        }
                    });
                }
            }
            this.setSize(400, 309);
            {
                jMenuBar1 = new JMenuBar();
                setJMenuBar(jMenuBar1);
                {
                    jMenu3 = new JMenu();
                    jMenuBar1.add(jMenu3);
                    jMenu3.setText("File");
                    {
                        newFileMenuItem = new JMenuItem();
                        jMenu3.add(newFileMenuItem);
                        newFileMenuItem.setText("New");
                    }
                    {
                        openFileMenuItem = new JMenuItem();
                        jMenu3.add(openFileMenuItem);
                        openFileMenuItem.setText("Open");
                    }
                    {
                        saveMenuItem = new JMenuItem();
                        jMenu3.add(saveMenuItem);
                        saveMenuItem.setText("Save");
                    }
                    {
                        saveAsMenuItem = new JMenuItem();
                        jMenu3.add(saveAsMenuItem);
                        saveAsMenuItem.setText("Save As ...");
                    }
                    {
                        closeFileMenuItem = new JMenuItem();
                        jMenu3.add(closeFileMenuItem);
                        closeFileMenuItem.setText("Close");
                    }
                    {
                        jSeparator2 = new JSeparator();
                        jMenu3.add(jSeparator2);
                    }
                    {
                        exitMenuItem = new JMenuItem();
                        jMenu3.add(exitMenuItem);
                        exitMenuItem.setText("Exit");
                    }
                }
                {
                    jMenu4 = new JMenu();
                    jMenuBar1.add(jMenu4);
                    jMenu4.setText("Edit");
                    {
                        cutMenuItem = new JMenuItem();
                        jMenu4.add(cutMenuItem);
                        cutMenuItem.setText("Cut");
                    }
                    {
                        copyMenuItem = new JMenuItem();
                        jMenu4.add(copyMenuItem);
                        copyMenuItem.setText("Copy");
                    }
                    {
                        pasteMenuItem = new JMenuItem();
                        jMenu4.add(pasteMenuItem);
                        pasteMenuItem.setText("Paste");
                    }
                    {
                        jSeparator1 = new JSeparator();
                        jMenu4.add(jSeparator1);
                    }
                    {
                        deleteMenuItem = new JMenuItem();
                        jMenu4.add(deleteMenuItem);
                        deleteMenuItem.setText("Delete");
                    }
                }
                {
                    jMenu5 = new JMenu();
                    jMenuBar1.add(jMenu5);
                    jMenu5.setText("Help");
                    {
                        helpMenuItem = new JMenuItem();
                        jMenu5.add(helpMenuItem);
                        helpMenuItem.setText("Help");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runOneViewJButtonActionPerformed(ActionEvent evt) {
        System.out.println("runOneViewJButton.actionPerformed, event=" + evt);
        String[] args = new String[0];
        OneView.main(args);
    }

    private void runTwoViewsJButtonActionPerformed(ActionEvent evt) {
        System.out.println("runTwoViewsJButton.actionPerformed, event=" + evt);
        String[] args = new String[0];
        TwoView.main(args);
    }

    private void runTwoPlusAdminjButtonActionPerformed(ActionEvent evt) {
        System.out.println("runTwoPlusAdminjButton.actionPerformed, event=" + evt);
        String[] args = new String[0];
        TwoViewAdmin.main(args);
    }

}
