package process;

import utility.ProcessState;

public class ProcessManager {

    public ProcessControlBlock pcb;

    public ProcessManager() {
        this.pcb = new ProcessControlBlock();
    }

    public void setPcb(ProcessControlBlock pcb) {
        this.pcb = pcb;
    }

    public ProcessControlBlock getPcb() {
        return pcb;
    }

    public void alterPCB(String name, ProcessState state, int number) {
        pcb.setName(name);
        pcb.setState(state);
        pcb.setNumber(number);
    }

}
