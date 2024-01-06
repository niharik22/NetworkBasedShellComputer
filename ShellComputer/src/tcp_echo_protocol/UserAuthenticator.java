/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcp_echo_protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author nihar
 */
public class UserAuthenticator {

    private static BlockingQueue<String> userIdQueue = new ArrayBlockingQueue<>(10);
    private static BlockingQueue<String> authRequestQueue = new ArrayBlockingQueue<>(10);
    private static final BlockingQueue<String> validatePasswordQueue = new ArrayBlockingQueue<String>(10);
    public static Scanner input = new Scanner(System.in);
    private static final String DATA_FILE = "src/utility/userdata.txt";
    public static HashMap<String, String> credentialsMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        loadUserData(); // Load existing user credentials from the file

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private static void producer() throws InterruptedException {
        String userId;
        String password;

        while (true) {
            System.out.println("Please enter your User ID to login to WERS:");
            userId = input.nextLine();

            // Check if the input is null or empty, and prompt again if necessary
            while (userId == null || userId.isEmpty()) {
                System.out.println("Invalid input. Please enter a non-null User ID:");
                userId = input.nextLine();
            }

            userId = userId.toLowerCase();

            if (!credentialsMap.containsKey(userId)) {
                System.out.println("User not registered. Starting registration for user: " + userId);
                System.out.println("Please enter a password for the user id : " + userId + ":");
                password = input.nextLine();

                // Check if the password is null or empty, and prompt again if necessary
                while (password == null || password.isEmpty()) {
                    System.out.println("Invalid input. Please enter a non-null password:");
                    password = input.nextLine();
                }

                addUser(userId, password);
                System.out.println("User successfully registered");
            } else {
                break;
            }
        }

        userIdQueue.put(userId);

        System.out.println("Please input your password to validate for the user id: " + userId + ":");
        password = input.nextLine();

        // Check if the password is null or empty, and prompt again if necessary
        while (password == null || password.isEmpty()) {
            System.out.println("Invalid input. Please enter a non-null password:");
            password = input.nextLine();
        }

        validatePasswordQueue.put(password);
    }

    private static void consumer() throws InterruptedException {
        Thread.sleep(100);
        String user = userIdQueue.take();
        String validatePassword = validatePasswordQueue.take();
        if (authenticateUser(user, validatePassword)) {
            System.out.println("Validated");
        } else {
            System.out.println("Validation Failed");
        }
    }

    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    credentialsMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            // Handle file IO errors
            System.err.println("Error loading user data");
            e.printStackTrace();
        }
    }

    static public boolean authenticateUser(String userId, String pass) {
        String storedPassword = credentialsMap.get(userId);
        return storedPassword != null && storedPassword.equals(pass);
    }

    public static void addUser(String userId, String pass) {
        credentialsMap.put(userId, pass);
        saveUserData();
    }

    private static void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (HashMap.Entry<String, String> entry : credentialsMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle file IO errors
            System.err.println("Error: Save User Data");
            e.printStackTrace();
        }
    }
}
