package models;

import java.util.LinkedList;
import java.util.Queue;

public class Manager 
{
	private int velocity; //tiempo de espera para cada thread en milisegundos para copiar cada caracter
	private Queue <Process> readyQueue; //cola de procesos listos
	private Queue <Process> stopQueue; //cola de procesos detenidos
	private LinkedList <Process> listOfCompleted; //lista de procesos terminados
	private Process execution;
	
	public Manager (int velocity)
	{
		setVelocity (velocity);
		init ();
	}
	
	public void setVelocity (int velocity)
	{
		this.velocity = velocity;
	}
	
	public void setExecution (Process execution)
	{
		this.execution = execution;
	}
	
	public int getVelocity ()
	{
		return velocity;
	}
	
	public Queue <Process> getReadyQueue ()
	{
		return readyQueue;
	}
	
	public Queue <Process> getStopQueue ()
	{
		return stopQueue;
	}
	
	public LinkedList <Process> getListOfCompleted ()
	{
		return listOfCompleted;
	}
	
	public Process getExecution ()
	{
		return execution;
	}
	
	public void init () //metodo para inicializar como nueva la lista y colas 
	{
		if (getReadyQueue () != null)
		{
			getReadyQueue ().clear ();
		}
		
		if (getStopQueue () != null) 
		{
			getStopQueue ().clear ();
		}
		
		if (getListOfCompleted () != null)
		{
			getListOfCompleted ().clear ();
		}
		
		readyQueue = new LinkedList <Process> ();
		stopQueue = new LinkedList <Process> ();
		listOfCompleted = new LinkedList <Process> ();
	}
	
	public boolean validateExistenceByPid (int pid) 
	{
		boolean check = false;
		
		if (searchAProcessByPid (pid, getReadyQueue ()))
		{
			check = true;
		}
		else if (searchAProcessByPid (pid, getStopQueue ()))
		{
			check = true;
		}
		else if (searchAProcessByPid (pid, getListOfCompleted ()))
		{
			check = true;
		}
		
		return check;
	}
	
	private boolean searchAProcessByPid (int pid, Queue <Process> queue)
	{
		boolean check = false;
		Queue <Process> temporalQueue = new LinkedList <Process> ();
		Process process = null;
		
		if (!queue.isEmpty ())
		{
			while (!queue.isEmpty ())
			{
				temporalQueue.add (queue.poll ());
				process = temporalQueue.peek ();
				
				if (process.getPid () == pid)
				{
					check = true;
					break;
				}
			}
			
			while (!temporalQueue.isEmpty ())
			{
				queue.add (temporalQueue.poll ());
			}
		}		
			
		return check;
	}
}
