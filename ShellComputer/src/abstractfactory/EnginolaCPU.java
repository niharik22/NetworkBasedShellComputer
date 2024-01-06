/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractfactory;

import chemical_response_app.wers.WERS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import process.ProcessControlBlock;
import process.ProcessManager;
import tcp_echo_protocol.TCPEchoServer;
import utility.ProcessState;
import utility.UtilityClass;

/**
 *
 * @author Alireza
 */
public class EnginolaCPU extends CPU {

    private static BufferedReader input = null;
    private static PrintWriter output = null;

    @Override
    public void launchWERSProgram(WERS wers, ProcessControlBlock pcb, ProcessManager pm) {
        if (UtilityClass.fromTCPServer) {
            input = TCPEchoServer.clientOutput;
            output = TCPEchoServer.serverOutput;
        } else {
            input = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintWriter(System.out);
        }

        //Launching WERS
        WERS.invokeWERS(wers);

        while (true) {
            output.println("Do you want to terminate the wers program (yes/no):?");
            output.flush();
            //TCPEchoServer.serverOutput.println("Do you want to terminate the wers program (yes/no):?");
            //TCPEchoServer.serverOutput.flush();
            //BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line = input.readLine().trim();
                //String line = TCPEchoServer.clientOutput.readLine().trim();
                if (line.equalsIgnoreCase("yes")) {
                    wers.setVisible(false);
                    wers.dispose();
                    /*
                    for (Frame f : Frame.getFrames()) {
                        f.setVisible(false);
                        f.dispose();
                    }*/
                    System.out.println("***Class: EnginolaCPU, Architecture: " + UtilityClass.architecture.name() + ", ProcessName: " + UtilityClass.WERS + ", ProcessState: Terminate, ProcessNumber: " + UtilityClass.processCounter + "***");
                    pm.alterPCB(UtilityClass.WERS, ProcessState.Terminate, UtilityClass.processCounter);
                    break;
                } else if (line.equalsIgnoreCase("no")) {
                    System.out.println("***Class: EnginolaCPU, Architecture: " + UtilityClass.architecture.name() + ", ProcessName: " + UtilityClass.WERS + ", ProcessState: Running, ProcessNumber: " + UtilityClass.processCounter + "***");
                    pm.alterPCB(UtilityClass.WERS, ProcessState.Running, UtilityClass.processCounter);
                    break;
                } else {
                    output.println("Invalid option selected.");
                    output.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(EnginolaCPU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
