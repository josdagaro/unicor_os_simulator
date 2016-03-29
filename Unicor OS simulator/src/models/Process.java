package models;

import java.io.IOException;

public class Process extends Thread 
{
	private int pid; //identificación del proceso	
	private float quantum; //tiempo en segundos en uso CPU
	private Activity activity; /*actividad del proceso, contiene ruta fuente (copiado) y destino (pegado)
	 							 como también las funciones necesarias para realizar dicha tarea*/
	
	public Process (int pid, String name, float quantum, Activity activity)
	{
		setPid (pid);
		setName (name); //metodos setName () y getName () heredados de la clase Thread
		setQuantum (quantum);
		setActivity (activity);
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
	
	public void run (int velocity) throws IOException, InterruptedException
	{
		getActivity ().copyAndPaste (velocity, getQuantum ());		
	}
}
