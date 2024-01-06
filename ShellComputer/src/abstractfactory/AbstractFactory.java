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
public abstract class AbstractFactory {

    private static final EmberToolKit EMBER_TOOLKIT = new EmberToolKit();
    private static final EnginolaToolKit ENGINOLA_TOOLKIT = new EnginolaToolKit();

    /**
     * Returns a concrete factory object that is an instance of the concrete
     * factory class appropriate for the given architecture.
     */
    public static AbstractFactory getFactory(Architecture architecture) {
        AbstractFactory factory = null;
        switch (architecture) {
            case ENGINOLA:
                factory = ENGINOLA_TOOLKIT;
                break;
            case EMBER:
                factory = EMBER_TOOLKIT;
                break;
        }
        return factory;
    }

    public abstract CPU createCPU();

    public abstract MMU createMMU();

}
