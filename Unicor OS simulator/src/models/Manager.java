package models;

import java.util.LinkedList;
import java.util.Queue;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "Manager")

public class Manager extends Thread
{
	private int velocity; //tiempo de espera para cada thread en milisegundos para copiar cada caracter
	private Queue <Process> readyQueue; //cola de procesos listos
	private Queue <Process> stopQueue; //cola de procesos detenidos
	private LinkedList <Process> listOfCompleted; //lista de procesos terminados
	private Process execution;
	private boolean running;
	private boolean suspended;
	private boolean completed;
	private boolean stopped;
	
	public Manager (int velocity)
	{
		setVelocity (velocity);
		setExecution (null);
		init ();
		running = false;
		suspended = false;
		completed = false;
		stopped = false;
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
	
	public boolean searchAProcessByPid (int pid, Queue <Process> queue)
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
	
	public void run ()
	{
		boolean liberation = false;
		running = true;
		int arrivalTime = 0;
						
		while (!getReadyQueue ().isEmpty () || !getStopQueue ().isEmpty ())
		{
			if (!liberation && !getReadyQueue ().isEmpty ())
			{
				setExecution (getReadyQueue ().poll ());												
			}
			else if (!getStopQueue ().isEmpty ())
			{
				setExecution (getStopQueue ().poll ());
				liberation = false;				
			}		
			
			if (!getExecution ().alreadyItCame ())
			{
				getExecution ().setArrivalTime (arrivalTime);
				getExecution ().itCame ();
			}
			
			getExecution ().setExecutions (getExecution ().getExecutions () + 1);
			
			while (true)
			{
				if (isSuspended ())
				{
					System.out.println ("2) Se reanuda el controlador de la ventana principal");
					resumeIt ();					
					break;
				}
			}
			
			try 
			{
				System.out.println ("3) Se detiene el despachador");
				suspendIt ();				
				System.out.println ("Esperando suspensión o finalización");
				
				while (true)
				{	
					Thread.sleep (1);
					
					if (getExecution ().getActivity ().getTask ().isStopped ())
					{
						System.out.println ("Por ende el proceso manager se detiene con stopIt");
						stopIt ();
						System.out.println ("Se reactiva el proceso manager");
						System.out.println ("Por ende el proceso de copiado");
						getExecution ().getActivity ().getTask ().resumeIt ();
					}					
					else if 
					(
						getExecution ().getActivity ().getTask ().isSuspended () || 
						getExecution ().getActivity ().getTask ().isCompleted ()
					)
					{						
						break;
					}					
				}
				
				if (getExecution ().getActivity ().getTask ().isSuspended ())
				{
					System.out.println ("Proceso almacenado en cola de detenidos");
					getStopQueue ().add (getExecution ());							
				}
				else if (getExecution ().getActivity ().getTask ().isCompleted ())
				{
					System.out.println ("Tarea terminada");
					getListOfCompleted ().add (getExecution ());						
					liberation = true;	
				}							
					
				if (getReadyQueue ().isEmpty () && getStopQueue ().isEmpty ())
				{
					completed = true;
				}
				
				System.out.println ("5) Se detiene el despachador");				
				suspendIt ();
				arrivalTime ++;
				setExecution (null);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}											
		}	
		
		System.out.println ("Fin del manager.");
		running = false;
	}
	
	public synchronized void resumeIt ()
	{
		suspended = false;
		stopped = false;
		
		synchronized (this)
		{
			notify ();
		}
	}
	
	public boolean isSuspended ()
	{
		return suspended;
	}
	
	public boolean isStopped ()
	{
		return stopped;
	}
	
	public void stopTrue ()
	{
		stopped = true;
	}
	
	public void suspendIt () throws InterruptedException
	{
		suspended = true;
		
		synchronized (this)
		{
			wait ();
		}
	}
	
	public void stopIt () throws InterruptedException
	{
		stopped = true;
		
		synchronized (this)
		{
			wait ();
		}
	}
	
	public boolean isCompleted ()
	{
		return completed;
	}
	
	public boolean isRunning ()
	{
		return running;
	}
}
