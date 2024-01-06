package tcp_echo_protocol;

/**
 * Reference: Graba, J. (2013). An Introduction to Network Programming with Java
 * (3rd e.d.). Springer: ISBN 978-1-4471-5253-8
 */
import firewall.master.Firewall;
import firewall.master.FirewallInterface;
import java.io.*;
import java.net.*;
import java.util.*;
import kernelfilingsystem.kernel.KernelSystem;
import utility.UtilityClass;

public class TCPEchoServer {

    private static ServerSocket servSock;
    private static final int PORT = 1234;
    public static BufferedReader clientOutput;
    public static PrintWriter serverOutput;
    //
    private static FirewallInterface firewall;

    public static void main(String[] args) {
        System.out.println("Opening port...\n");
        try {
            servSock = new ServerSocket(PORT); // Step-1 : Creating Socket
            System.out.println("Waiting for Client...");
        } catch (IOException ioEx) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        do {
            handleClient(new String[2]);
        } while (true);
    }

    private static void handleClient(String[] args) {
        Socket connection = null;
        try {
            connection = servSock.accept();               //Step-2 : Listens for a connection
            System.out.println("Server Connected to Client...");
            clientOutput = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())); //Step 3.
            serverOutput
                    = new PrintWriter(
                            connection.getOutputStream(), true); //Step 3.

            int numMessages = 0;
            String clientMessage = clientOutput.readLine();      //Step 4.
            //clientOutput.;
            while (!clientMessage.equalsIgnoreCase("***CLOSE***")) {
                System.out.println("Message received.");
                numMessages++;
                if (clientMessage.toLowerCase().contains("start wers")) {
                    handleWers(clientMessage, args);
                } else {
                    serverOutput.println("Message " + numMessages
                            + ": " + clientMessage);   //Step 4.
                }
                clientMessage = clientOutput.readLine();
            }
            serverOutput.println(numMessages
                    + " messages received.");//Step 4.
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                System.out.println(
                        "\n* Closing connection... *");
                connection.close();				    //Step 5.
            } catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }

    private static void handleWers(String clientMessage, String[] args) {

        if (UserAuthentication2.userAuthentication()) {
            if (authenticateWERSRequest(clientMessage)) {
                System.out.println("Packet Was Accepted");
                serverOutput.println("\nAuthentication Successful, Invoked WERS Application...");
                contactKernelSystem(args);
            }
        } else {
            serverOutput.println("\nUser authentication failed.");
        }
    }

    private static boolean authenticateWERSRequest(String message) {
        boolean reqAuthenticated = false;
        firewall = new Firewall("src/firewall/master/rules.csv");
        System.out.println("Processing Acceptable Packet");
        String[] parts = message.split("\\|");
        // Extract packet information
        String direction = parts[0]; // Extract packet information - direction
        String protocol = parts[1]; // Extract packet information - protocol
        int port = Integer.parseInt(parts[2]);// Extract the message - port
        String ip_address = parts[3];// Extract the message - ip_address
        reqAuthenticated = firewall.accept_packet(direction, protocol, port, ip_address);
        return reqAuthenticated;
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
        //output.println("***Invoking Kernel Filing System***");
        kernelFS.invokeKernelSystem(args);
    }

}
