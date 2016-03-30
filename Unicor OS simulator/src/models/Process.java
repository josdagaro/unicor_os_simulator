package models;

import java.io.IOException;

public class Process extends Thread 
{
	private int pid; //identificación del proceso	
	private float quantum; //tiempo en segundos en uso CPU
	private Activity activity; /*actividad del proceso, contiene ruta fuente (copiado) y destino (pegado)
	 							 como también las funciones necesarias para realizar dicha tarea*/
	private boolean suspended;
	private boolean completed;

	public Process (int pid, String name, float quantum, Activity activity)
	{
		super ();
		setPid (pid);
		setName (name); //metodos setName () y getName () heredados de la clase Thread
		setQuantum (quantum);
		setActivity (activity);
		suspended = false;
		completed = false;
	}

	public void setPid (int pid)
	{
		this.pid = pid;
	}	

	public void setQuantum (float quantum)
	{
		this.quantum = quantum;
	}

	public void setActivity (Activity activity)
	{
		this.activity = activity;
	}

	public int getPid ()
	{
		return pid;
	}	

	public float getQuantum ()
	{
		return quantum;
	}

	public Activity getActivity ()
	{
		return activity;
	}

	public void executeActivity (int velocity, views.MainWindow mainWindow) throws IOException, InterruptedException
	{
		getActivity ().copyAndPaste (velocity, this, mainWindow);		
	}

	public float getRafagaTime (int velocity) throws IOException
	{
		float rafagaTime = 0;
		rafagaTime = velocity * getActivity ().getNumberOfCharacters ();
		return rafagaTime;
	}
	
	public void suspendIt ()
	{
		suspended = true;
	}

	public void run ()
	{
		try 
		{
			synchronized (this) 
			{
				while (suspended) 
				{
					wait ();
				}
			}
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace ();
		}
	}
	
	public synchronized void resumeIt ()
	{
		suspended = false;
		notify ();
	}
	
	public boolean isSuspended ()
	{
		return suspended;
	}
	
	public boolean isCompleted ()
	{
		return completed;
	}
	
	public void completed ()
	{
		completed = true;
	}
}
