/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractfactory;

/**
 *
 * @author Alireza
 */
public class Client {
    public static void main(String[] args){
        AbstractFactory factory = AbstractFactory.getFactory(Architecture.EMBER);
        CPU cpu = factory.createCPU();
    }
}

/**
 * OUTPUT
 * BUILD SUCCESSFUL (total time: 1 second)
 */