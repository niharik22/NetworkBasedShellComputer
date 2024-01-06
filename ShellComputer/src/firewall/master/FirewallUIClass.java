package firewall.master;

import kernelfilingsystem.kernel.KernelSystem;


public class FirewallUIClass {

    public static void main(String[] args) {
        System.out.println("###Starting the Application###");
        FirewallInterface firewall = new Firewall("src/firewall/master/rules.csv");
        System.out.println("Processing First Packet");
        boolean firstUserRequest = firewall.accept_packet("inbound", "tcp", 80, "192.168.1.2");
        if (firstUserRequest == true) {
            System.out.println("Packet Was Accepted");
            contactKernelSystem(args);
        } else {
            System.out.println("Packet Was Rejected");
        }

        System.out.println("Processing Second Packet");
        boolean secondUserRequest = firewall.accept_packet("inbound", "tcp", 81, "192.168.1.2");
        if (secondUserRequest == false) {
            System.out.println("Packet Was Rejected");
        } else {
            System.out.println("Packet Was Accepted");
            contactKernelSystem(args);
        }

        System.out.println("###Exiting the Application###");
    }

    /**
     * Contacts the Kernel Filing System.
     *
     * This method creates an instance of the KernelSystem class and invokes its
     * functionality, passing the provided arguments.
     *
     * @param args An array of arguments to be passed to the Kernel Operating
     * System.
     */
    private static void contactKernelSystem(String[] args) {
        KernelSystem kernelFS = new KernelSystem();
        System.out.println("***Invoking Kernel Filing System***");
        kernelFS.invokeKernelSystem(args);
    }

}
