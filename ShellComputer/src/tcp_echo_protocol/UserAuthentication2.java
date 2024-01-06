package tcp_echo_protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserAuthentication2 {

    private static BlockingQueue<String> userIdQueue = new ArrayBlockingQueue<>(10);
    private static BlockingQueue<String> authRequestQueue = new ArrayBlockingQueue<>(10);
    private static final BlockingQueue<String> validatePasswordQueue = new ArrayBlockingQueue<String>(10);
    private static final String DATA_FILE = "src/utility/userdata.txt";
    private static HashMap<String, String> credentialsMap = new HashMap<>();
    private static BufferedReader input = TCPEchoServer.clientOutput;
    private static PrintWriter output = TCPEchoServer.serverOutput;
    private static boolean authenticateFlag = false;

    private static final Lock userAuthLock = new ReentrantLock();

    public static boolean userAuthentication() {
        try {
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

        } catch (Exception e) {
        }
        return authenticateFlag;
    }

    private static void producer() throws InterruptedException {
        String userId;
        String password;

        try {
            acquireLocks(userAuthLock);

            while (true) {
                output.println("Please enter your User ID to login to WERS :?");
                userId = input.readLine();
                // Check if the input is null or empty, prompt again if necessary
                while (userId == null || userId.isEmpty()) {
                    output.println("Invalid input. Please enter a non-null User ID:?");
                    userId = input.readLine();
                }

                userId = userId.toLowerCase();

                if (!credentialsMap.containsKey(userId)) {
                    output.println("User not registered. Starting registration for user: " + userId);
                    output.println("Please enter a password for the user id : " + userId + ":?");
                    password = input.readLine();

                    // Check if the password is null or empty, and prompt again if necessary
                    while (password == null || password.isEmpty()) {
                        output.println("Invalid input. Please enter a non-null password :?");
                        password = input.readLine();
                    }

                    addUser(userId, password);
                    output.println("User successfully registered.");
                } else {
                    break;
                }
            }

            userIdQueue.put(userId);

            output.println("Please input your password to validate for the user id: " + userId + " :?");
            password = input.readLine();

            // Check if the password is null or empty, and prompt again if necessary
            while (password == null || password.isEmpty()) {
                output.println("Invalid input. Please enter a non-null password :?");
                password = input.readLine();
            }

            validatePasswordQueue.put(password);
        } catch (IOException | InterruptedException e) {
        } finally {
            userAuthLock.unlock();
        }
    }

    private static void consumer() throws InterruptedException {
        userAuthLock.lock(); // Acquire the lock

        try {
            Thread.sleep(100);
            String user = userIdQueue.take();
            String validatePassword = validatePasswordQueue.take();
            if (authenticateUser(user, validatePassword)) {
                authenticateFlag = true;
                System.out.println("User validated successfully");
            } else {
                authenticateFlag = false;
                System.out.println("User validation Failed");
            }
        } finally {
            userAuthLock.unlock(); // Release the lock in the finally block
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

    private static void acquireLocks(Lock lock) throws InterruptedException {
        while (true) {
            boolean gotLock = false;

            try {
                gotLock = lock.tryLock();
            } finally {
                if (gotLock) {
                    return;
                }

                lock.unlock();
            }

            // Lock not acquired
            Thread.sleep(1);
        }
    }
}
