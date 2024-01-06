package kernelfilingsystem.kernel;

import java.io.*;
import java.util.*;

import chemical_response_app.wers.WERS;

import static java.lang.System.*;
import utility.UtilityClass;
import tcp_echo_protocol.TCPEchoServer;

public class KernelSystem {

    private static String[] helpInfo = {
        "help",
        "quit",
        "format",
        "cd pathname",
        "pwd",
        "create pathname",
        "read pathname",
        "write pathname data",
        "writeln pathname",
        "rm pathname",
        "mkdir pathname",
        "rmdir pathname",
        "ln oldpath newpath",
        "readlink pathname",
        "ls [ dirname ]",};

    /**
     * Disk block size, as retrieved from the Disk (cheating!).
     */
    public static int blockSize;
    private static BufferedReader input = null;
    private static PrintWriter output = null;

    public void invokeKernelSystem(String[] args) {

        boolean fromFile = (args.length == 1);
        UtilityClass.fromTCPServer = (args.length == 2);

        // Power on the disk
        Kernel.interrupt(Kernel.INTERRUPT_POWER_ON, 10, 0, new FastDisk(100), null, null, null, null, null);
        // XXX: Script to test commands
        //        args = new String[1];
        //        args[0] = "test1.script";
//        args[0] = "mytest.script";

        if (args.length > 2) {
            System.err.println("usage: FileTester [ script-file ]");
            System.exit(0);
        }

        // blockSize = Disk.BLOCK_SIZE;
// This is a bit of a cheat.  We really should have a Kernel call to get this information.
        blockSize = Library.getBlockSizeOfDisk();

        if (fromFile) {
            try {
                input = new BufferedReader(new FileReader(args[0]));
            } catch (FileNotFoundException e) {
                System.err.println("Error: Script file " + args[0] + " not found.");
                System.exit(1);
            }
        } else if (UtilityClass.fromTCPServer) {
            input = TCPEchoServer.clientOutput;
            output = TCPEchoServer.serverOutput;
        } else {
            input = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintWriter(System.out);
        }

        for (;;) {
            String cmd = null;
            try {
                // Print out the prompt for the user
                if (!fromFile) {
                    //Modified - Niharik
                    output.println("\nPlease enter the task you'd like the Kernel System to execute:?");
                    output.flush();
                    //TCPEchoServer.serverOutput.println("\nPlease enter the task you'd like the Kernel System to execute:?");
                    //TCPEchoServer.serverOutput.flush();
                }

                String line = input.readLine();

                if (line == null) {
                    return;
                }
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }

                if (line.startsWith("//")) {
                    if (fromFile) {
                        System.out.printf("%s\n", line);
                    }
                    continue;
                }
                if (line.startsWith("/*")) {
                    continue;
                }

                if (fromFile) {
                    System.out.printf("--> %s\n", line);
                }

                StringTokenizer st = new StringTokenizer(line);
                cmd = st.nextToken();

                int result = 0;
                System.out.println("** Processing User request : " + cmd + " **");
                if (cmd.equalsIgnoreCase("quit")) {
                    Library.shutdown();
                    output.println("Closing Kernel System");
                    output.flush();
                    return;
                } else if (cmd.equalsIgnoreCase("help") || cmd.equals("?")) {
                    help();
                    continue;
                } else if (cmd.equalsIgnoreCase("format")) {
                    result = Library.format();
                } else if (cmd.equalsIgnoreCase("cd")) {
                    result = Library.chdir(st.nextToken());
                } else if (cmd.equalsIgnoreCase("pwd")) {
                    result = pwd();
                } else if (cmd.equalsIgnoreCase("create")) {
                    result = Library.create(st.nextToken());
                } else if (cmd.equalsIgnoreCase("read")) {
                    result = readTest(st.nextToken(), false);
                } else if (cmd.equalsIgnoreCase("write")) {
                    result = writeTest(st.nextToken(), line);
                } else if (cmd.equalsIgnoreCase("writeln")) {
                    result = writeLines(st.nextToken(), input);
                } else if (cmd.equalsIgnoreCase("rm")) {
                    result = Library.delete(st.nextToken());
                } else if (cmd.equalsIgnoreCase("mkdir")) {
                    result = Library.mkdir(st.nextToken());
                } else if (cmd.equalsIgnoreCase("rmdir")) {
                    result = Library.rmdir(st.nextToken());
                } else if (cmd.equalsIgnoreCase("ln")) {
                    String oldName = st.nextToken();
                    String newName = st.nextToken();
                    result = Library.symlink(oldName, newName);
                } else if (cmd.equalsIgnoreCase("readlink")) {
                    result = readTest(st.nextToken(), true);
                } else if (cmd.equalsIgnoreCase("ls")) {
                    if (st.hasMoreTokens()) {
                        result = dumpDir(st.nextToken());
                    } else {
                        result = dumpDir(".");
                    }
                } else if (cmd.equalsIgnoreCase(UtilityClass.WERS)) {
                    Library.exec(UtilityClass.WERS, args);
                } else {
                    output.println("unknown command\n");
                    output.flush();
//                    TCPEchoServer.serverOutput.println("unknown command\n");
//                    TCPEchoServer.serverOutput.flush();
                    continue;
                }

                if (result != 0) {
                    if (result == -1) {
//                        TCPEchoServer.serverOutput.printf("*** System call failed***\n");
//                        TCPEchoServer.serverOutput.flush();
                        System.out.printf("*** System call failed***\n\n");
                    } else {
//                        TCPEchoServer.serverOutput.printf("*** Bad result %d from system call***\n", result);
//                        TCPEchoServer.serverOutput.flush();
                        System.out.printf("*** Bad result %d from system call***\n\n", result);
                    }
                }
            } catch (NoSuchElementException e) {
                // Handler for nextToken()
                System.out.print("Incorrect number of arguments\n");
                //TCPEchoServer.serverOutput.flush();
                help(cmd);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private static void help() {
        StringBuilder helpText = new StringBuilder("Commands are: -> ");

        for (int i = 0; i < helpInfo.length; i++) {
            helpText.append("    ").append(helpInfo[i]);
        }

        output.printf("%s%n", helpText.toString());
        output.flush();
    }

    private static void help(String cmd) {
        for (int i = 0; i < helpInfo.length; i++) {
            if (helpInfo[i].startsWith(cmd)) {
                output.printf("usage: %s\n", helpInfo[i]);
                output.flush();
                return;
            }
        }
        output.printf("unknown command '%s'\n", cmd);
        output.flush();
    }

    private static int readTest(String fname, boolean isLink) {
        byte[] buf = new byte[blockSize];
        int n = isLink
                ? Library.readlink(fname, buf)
                : Library.read(fname, buf);
        boolean needNewline = false;
        if (n < 0) {
            return n;
        }
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] != 0) {
                showChar(buf[i] & 0xff);
                needNewline = (buf[i] != '\n');
            }
        }
        if (needNewline) {
            output.printf("\n");
            output.flush();
        }
        return n;
    }

    private static int writeTest(String fname, String info) {
        int p;
        p = info.indexOf(' ');
        if (p >= 0) {
            p = info.indexOf(' ', p + 1);
            if (p < 0) {
                p = info.length();
            } else {
                p++;
            }
        } else {
            p = 0;
        }
        byte[] buf = new byte[Math.max(blockSize, info.length() - p)];
        int i = 0;
        while (p < info.length()) {
            buf[i++] = (byte) info.charAt(p++);
        }
        return Library.write(fname, buf);
    }

    private static int writeLines(String fname, BufferedReader in) {
        try {
            byte[] buf = new byte[blockSize];
            int i = 0;
            for (;;) {
                String line = in.readLine();
                if (line == null || line.equals(".")) {
                    break;
                }
                for (int j = 0; j < line.length(); j++) {
                    if (i >= buf.length) {
                        byte[] newBuf = new byte[buf.length * 2];
                        System.arraycopy(buf, 0, newBuf, 0, buf.length);
                        buf = newBuf;
                    }
                    buf[i++] = (byte) line.charAt(j);
                }
                if (i >= buf.length) {
                    break;
                }
                buf[i++] = '\n';
            }
            return Library.write(fname, buf);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static void showChar(int b) {
        if (b >= ' ' && b <= '~') {
            output.printf("%c", (char) b);
            return;
        }
        if (b == '\n') {
            output.printf("\\n\n");
            return;
        }
        if (b == '\\') {
            output.printf("\\\\");
            return;
        }
        output.printf("\\%03o", b);
    }

    private static int dumpDir(String dirname) {
        byte[] buf = new byte[blockSize];
        int n = Library.readdir(dirname, buf);
        if (n < 0) {
            return n;
        }
        for (int i = 0; i < buf.length; i += 16) {
            int block = ((buf[i] & 0xff) << 8) + (buf[i + 1] & 0xff);
            if (block == 0) {
                continue;
            }
            StringBuffer sb = new StringBuffer();
            for (int j = 3; j < 16; j++) {
                if (buf[i + j] == 0) {
                    break;
                }
                sb.append((char) buf[i + j]);
            }
            String fname = sb.toString();
            output.printf("%s %s", block, fname);
            switch (buf[i + 2]) {
                case 'O':
                    break;
                case 'D':
                    output.printf("/");
                    break;
                case 'L':
                    output.printf(" -> ");
                    byte[] buf1 = new byte[blockSize];
                    n = Library.readlink(dirname + "/" + fname, buf1);
                    if (n < 0) {
                        return n;
                    }
                    for (int j = 0; j < buf1.length; j++) {
                        if (buf1[j] == 0) {
                            break;
                        }
                        output.printf("%c", (char) buf1[j]);
                    }
                    break;
                default:
                    output.printf("?type \\%03o?", buf[i + 2]);
                // output.printf("<type %d>", buf[i + 2]);
            }
            output.printf("\n");
        }
        return n;
    }

    private static int pwd() {
        int rc, dot, dotdot;
        int child = 0;
        String relPath = ".";
        List<String> path = new LinkedList<String>();
        byte[] buf = new byte[blockSize];
        for (;;) {
            rc = Library.readdir(relPath, buf);
            if (rc != 0) {
                output.printf("pwd:  cannot read directory \"%s\"\n", relPath);
                return -1;
            }
            dot = dirSearch(buf, ".");
            if (dot == 0) {
                output.printf("pwd: bad directory \"%s\": no . entry\n", relPath);
                return -1;
            }
            if (child != 0) {
                String cname = dirSearch(buf, child);
                if (cname == null) {
                    output.printf("pwd: bad directory \"%s\": " + " no entry for %d\n", relPath, child);
                    return -1;
                }
                path.add(0, cname);
            }
            dotdot = dirSearch(buf, "..");
            if (dotdot == 0) {
                output.printf("pwd: bad directory \"%s\": no .. entry\n", relPath);
                return -1;
            }
            if (dot == dotdot) {
                break;
            }
            child = dot;
            relPath += "/..";
        }
        if (path.size() == 0) {
            output.printf("/");
        } else {
            for (String s : path) {
                output.printf("/%s", s);
            }
        }
        output.printf("\n");
        return 0;
    }

    private static int dirSearch(byte[] buf, String s) {
        for (int offset = 0; offset < buf.length; offset += 16) {
            int j;
            for (j = 0; j < 13; j++) {
                if (j >= s.length()
                        || buf[offset + j + 3] != (byte) s.charAt(j)) {
                    break;
                }
            }
            if (j == s.length() && buf[offset + j + 3] == 0) {
                return (((buf[offset] & 0xff) << 8) + (buf[offset + 1] & 0xff));
            }
        }
        return 0;
    }

    private static String dirSearch(byte[] buf, int n) {
        for (int offset = 0; offset < buf.length; offset += 16) {
            int blk = (((buf[offset] & 0xff) << 8) + (buf[offset + 1] & 0xff));
            if (blk == n) {
                int j;
                for (j = 0; j < 13; j++) {
                    if (buf[offset + j + 3] == 0) {
                        break;
                    }
                }
                return new String(buf, offset + 3, j);
            }
        }
        return null;
    }
}
