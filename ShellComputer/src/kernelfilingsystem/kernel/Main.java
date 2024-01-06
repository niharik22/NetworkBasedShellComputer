package kernelfilingsystem.kernel;

import java.util.Scanner;
import kernelfilingsystem.kernel.FastDisk;

/**
 *
 * @author Roshan Lamichhane
 *
 */
 
public class Main{
  
  public static FastDisk disk;
  
  public static void main(String[] args){
  
  try{
    while (true) { 
    
    System.out.print("> ");
    
    Scanner s = new Scanner(System.in);
    String input = s.nextLine();
   
   String commands[] = input.split("&");
   
   CommandExecutorThread commandExecutorThread[] = new CommandExecutorThread[commands.length];
   
      for (int i = 0; i < commands.length; i++){
      commandExecutorThread[i] = new CommandExecutorThread(i, commands[i].trim());
      commandExecutorThread[i].start();
     }
     
     for (int i = 0; i < commandExecutorThread.length; i++){
          commandExecutorThread[i].join();
      }
    }  
  }
  
  catch (Exception e){
    System.out.println("\n\nInterruption was detected. JShell is closing.");
      System.exit(0);
    }
  }  
}
  class CommandExecutorThread extends Thread{
  private int myID = 0;
  private String command;
  
  public CommandExecutorThread(int myID, String command){
      this.myID = myID;
      this.command = command;
 }
 
 public void run(){
      System.out.println("myID = " + myID + ". Running command " + command);
      
      switch (command){
        case "format":
          break;
         }
       }
      } 
