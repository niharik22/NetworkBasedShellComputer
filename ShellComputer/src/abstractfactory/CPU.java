/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractfactory;

import chemical_response_app.wers.WERS;
import process.ProcessControlBlock;
import process.ProcessManager;

/**
 *
 * @author Alireza
 */
public abstract class CPU {

    public abstract void launchWERSProgram(WERS wers, ProcessControlBlock pcb, ProcessManager pm);
}
