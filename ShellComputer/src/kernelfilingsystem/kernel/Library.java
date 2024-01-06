package kernelfilingsystem.kernel;

import static java.lang.System.*;
import abstractfactory.AbstractFactory;
import abstractfactory.Architecture;
import abstractfactory.EnginolaMMU;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import process.ProcessControlBlock;
import process.ProcessManager;
import tcp_echo_protocol.TCPEchoServer;
import utility.UtilityClass;

public class Library {

    private Library() {
    }

    public static final String[] errorMessage = {
        "OK", // 0
        "Invalid argument", // ERROR_BAD_ARGUMENT = -1
        "No such class", // ERROR_NO_CLASS = -2
        "Class has no main method", // ERROR_NO_MAIN = -3
        "Command aborted", // ERROR_BAD_COMMAND = -4
        "Argument out of range", // ERROR_OUT_OF_RANGE = -5
        "End of file on console input", // ERROR_END_OF_FILE = -6
        "I/O error on console input", // ERROR_IO = -7
        "Exception in user program", // ERROR_IN_CHILD = -8
        "No such process" // ERROR_NO_SUCH_PROCESS = -9
    };

    private static BufferedReader input = null;
    private static PrintWriter output = null;

    public static int output(String s) {
        return Kernel.interrupt(Kernel.INTERRUPT_USER,
                Kernel.SYSCALL_OUTPUT, 0, s, null, null, null, null, null);
    }

    public static int input(StringBuffer result) {
        result.setLength(0);
        return Kernel.interrupt(Kernel.INTERRUPT_USER,
                Kernel.SYSCALL_INPUT, 0, result, null, null, null, null, null);
    }

    public static int exec(String command, String args[]) {

        if (UtilityClass.fromTCPServer) {
            input = TCPEchoServer.clientOutput;
            output = TCPEchoServer.serverOutput;
        } else {
            input = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintWriter(System.out);
        }
        if (!UtilityClass.fromTCPServer) {
            if (UtilityClass.architecture == null) {
                while (true) {
                    output.println("Please select the architecture you want the system to follow:");
                    output.println("1. EMBER");
                    output.println("2. ENGINOLA");
                    output.println("Enter your choice (1 or 2):?");
                    output.flush();
                    try {
                        String line = input.readLine().trim();
                        if (line.equalsIgnoreCase("1")) {
                            UtilityClass.architecture = Architecture.EMBER;
                            break;
                        } else if (line.equalsIgnoreCase("2")) {
                            UtilityClass.architecture = Architecture.ENGINOLA;
                            break;
                        } else {
                            output.println("Invalid option selected.");
                            output.flush();
                        }
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            UtilityClass.architecture = Architecture.EMBER;
        }

        FileSys fs = new FileSys();
        ProcessControlBlock pcb = fs.getPCB();
        ProcessManager pm = fs.getPM();
        AbstractFactory factory = fs.getAbstractFactory(UtilityClass.architecture);
        return Kernel.interrupt(Kernel.INTERRUPT_USER,
                Kernel.SYSCALL_EXEC, 0, command, args, null, factory, pcb, pm);

    }

    public static int join(int pid) {
        return Kernel.interrupt(Kernel.INTERRUPT_USER,
                Kernel.SYSCALL_JOIN, pid, null, null, null, null, null, null);
    }

    public static int getBlockSizeOfDisk() {
        return Kernel.interrupt(Kernel.INTERRUPT_USER, Kernel.SYSCALL_GET_BLOCK_SIZE, 0, null, null, null, null, null, null);
    }

    public static int format() {
//        err.println("format system call not implemented yet");
//        return -1;

        return Kernel.interrupt(Kernel.INTERRUPT_USER, Kernel.SYSCALL_FORMAT, 0, null, null, null, null, null, null);

    }

    public static int chdir(String pathname) {
        err.println("chdir system call not implemented yet");
        return -1;
    }

    public static int create(String pathname) {
//        err.println("create system call not implemented yet");
//        return -1;        
        return Kernel.interrupt(
                Kernel.INTERRUPT_USER, Kernel.SYSCALL_CREATE, 0, null, null, pathname.getBytes(), null, null, null);

    }

    public static int read(String pathname, byte[] buffer) {

//        err.println("read system call not implemented yet");
//        return -1;
        return Kernel.interrupt(
                Kernel.INTERRUPT_USER, Kernel.SYSCALL_READ, 0, null, null, pathname.getBytes(), null, null, null);

    }

    public static int write(String pathname, byte[] buffer) {
//        err.println("write system call not implemented yet");
        return Kernel.interrupt(
                Kernel.INTERRUPT_USER, Kernel.SYSCALL_WRITE, 0, pathname, null, buffer, null, null, null);
//        return -1;
    }

    public static int delete(String pathname) {
        return Kernel.interrupt(
                Kernel.INTERRUPT_USER, Kernel.SYSCALL_DELETE, 0, pathname, null, null, null, null, null);
//        return -1;
    }

    public static int mkdir(String pathname) {
        err.println("mkdir system call not implemented yet");
        return -1;
    }

    public static int rmdir(String pathname) {
        err.println("rmdir system call not implemented yet");
        return -1;
    }

    public static int symlink(String oldName, String newName) {
        err.println("symlink system call not implemented yet");
        return -1;
    }

    public static int readlink(String pathname, byte[] buffer) {
        err.println("readlink system call not implemented yet");
        return -1;
    }

    public static int readdir(String pathname, byte[] buffer) {
        return Kernel.interrupt(Kernel.INTERRUPT_USER, Kernel.SYSCALL_READDIR, 0, null, null, null, null, null, null);
//        return -1;
    }

    public static void shutdown() {
        Kernel.interrupt(
                Kernel.INTERRUPT_USER, Kernel.SYSCALL_SHUTDOWN, 0, null, null, null, null, null, null);
    }
}
