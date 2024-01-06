package tcp_echo_protocol;

/**
 * Reference: Graba, J. (2013). An Introduction to Network Programming with Java
 * (3rd e.d.). Springer: ISBN 978-1-4471-5253-8
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPEchoClient {

    private static InetAddress host;
    private static final int PORT = 1234;
    private static BufferedReader serverOutput;
    private static PrintWriter clientOutput;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        Socket connection = null;

        try {
            /* Step -1 : Connection Establishment - Start*/
            connection = new Socket(host, PORT);

            System.out.println("Client is Connected to server...");
            /* Step -1 : Connection Establishment - End*/

            /**
             * Step -2 : Read stream from server*
             */
            serverOutput
                    = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

            clientOutput
                    = new PrintWriter(
                            connection.getOutputStream(), true);//Step 2.

            //Set up stream for keyboard entry...
            Scanner userEntry = new Scanner(System.in);

            String message, response;
            do {
                System.out.print("\nEnter message: ");
                message = userEntry.nextLine();

                if (message.equalsIgnoreCase("start wers")) {
                    // Combine the packet information and message into a single string
                    String packetInfo = "inbound|tcp|80|192.168.1.2";
                    message = packetInfo + "|" + message;
                    clientOutput.println(message);
                    clientOutput.flush();

                    // Read and process responses from the server
                    while (true) {
                        String line = serverOutput.readLine();
                        if (line == null) {
                            break; // End of server responses
                        }
                        response = line;
//                        System.out.println("\nSERVER> " + response);
                        System.out.println(response);
                        if (response.contains(":?")) {
                            message = userEntry.nextLine();
                            clientOutput.println(message);
                            clientOutput.flush();
                        }
                        if (response.contains("Closing Kernel System") || response.contains("User authentication failed")) {
                            break; // End of server responses
                        }
                        response = "";
                    }

                } else {
                    clientOutput.println(message);
                    response = serverOutput.readLine();
                    System.out.println("\nSERVER> " + response);
                }

            } while (!message.equals("***CLOSE***"));
        } catch (IOException ioEx) {
            System.err.println(ioEx.getMessage());
        } finally {
            try {
                System.out.println(
                        "\n** Closing connection with Server **");
                connection.close();					//Step 4.
            } catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }
    }
}
