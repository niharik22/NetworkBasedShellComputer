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
import utility.UtilityClass;
import utility.ProcessState;

/**
 *
 * @author Alireza
 */
public class EnginolaMMU extends MMU {

    public WERS wers;
    public ProcessControlBlock pcb;
    public ProcessManager pm;

    private static BufferedReader input = null;
    private static PrintWriter output = null;

    public EnginolaMMU() {
    }

    @Override
    public void setWERS(WERS wers) {
        this.wers = wers;
    }

    @Override
    public void setPCB(ProcessControlBlock pcb) {
        this.pcb = pcb;
    }

    @Override
    public void setPM(ProcessManager pm) {
        this.pm = pm;
    }

    @Override
    public void executeWERSProgramIfRequested(CPU cpu) {

        if (UtilityClass.fromTCPServer) {
            input = TCPEchoServer.clientOutput;
            output = TCPEchoServer.serverOutput;
        } else {
            input = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintWriter(System.out);
        }

        while (true) {
            output.println("Do you want to send the wers program for execution and launching (yes/no):?");
            output.flush();
//            TCPEchoServer.serverOutput.println("Do you want to send the wers program for execution and launching (yes/no):?");
//            TCPEchoServer.serverOutput.flush();
            try {
                String line = input.readLine().trim();
                if (line.equalsIgnoreCase("yes")) {

                    System.out.println("***Class: EnginolaMMU, Architecture: " + UtilityClass.architecture.name() + ", ProcessName: " + UtilityClass.WERS + ", ProcessState: Execution, ProcessNumber: " + UtilityClass.processCounter + "***");
                    pm.alterPCB(UtilityClass.WERS, ProcessState.Execution, 1);
                    ((EnginolaCPU) cpu).launchWERSProgram(wers, pcb, pm);
                    break;
                } else if (line.equalsIgnoreCase("no")) {
                    System.out.println("***Class: EnginolaMMU, Architecture: " + UtilityClass.architecture.name() + ", ProcessName: " + UtilityClass.WERS + ", ProcessState: Waiting, ProcessNumber: " + UtilityClass.processCounter + "***");
                    pm.alterPCB(UtilityClass.WERS, ProcessState.Waiting, 1);
                    break;
                } else {
                    output.println("Invalid option selected.");
                    output.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(EnginolaMMU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
