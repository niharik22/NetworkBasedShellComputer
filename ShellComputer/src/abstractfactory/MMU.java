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
public abstract class MMU {

    public abstract void setWERS(WERS wers);

    public abstract void setPCB(ProcessControlBlock pcb);

    public abstract void setPM(ProcessManager pm);

    public abstract void executeWERSProgramIfRequested(CPU cpu);
}
