package process;

import utility.ProcessState;

public class ProcessControlBlock {
	private String processName;
	private ProcessState processState;
	private int processNumber;
	
	public ProcessControlBlock(){
		
	}
	public ProcessControlBlock(String name, ProcessState state, int number){
		this.processName = name;
		this.processState = state;
		this.processNumber = number;
	}
	
	public void setName(String name){
		this.processName = name;
	}
	public void setState(ProcessState state){
		this.processState = state;
	}
	public void setNumber(int number){
		this.processNumber = number;
	}
	
	public String getName(){
		return this.processName;
	}
	public ProcessState getState(){
		return this.processState;
	}
	public int getNumber(){
		return this.processNumber;
	}
}