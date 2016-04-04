package models;

import java.io.IOException;

public class Process
{
	private int pid; //identificación del proceso	
	private String name;
	private float quantum; //tiempo en segundos en uso CPU
	private Activity activity; /*actividad del proceso, contiene ruta fuente (copiado) y destino (pegado)
	 							 como también las funciones necesarias para realizar dicha tarea*/
	private int arrivalTime; //tiempo de llegada del proceso
	private boolean came; //verifica si el proceso ya tuvo su llegada
	private int executions;
	private float turnAround;

	public Process (int pid, String name, float quantum, Activity activity)
	{
		super ();
		setPid (pid);
		setName (name); //metodos setName () y getName () heredados de la clase Thread
		setQuantum (quantum);
		setActivity (activity);
		setArrivalTime (0);
		setExecutions (0);
		came = false;		
		turnAround = 0;
	}

	public void setPid (int pid)
	{
		this.pid = pid;
	}	

	public void setName (String name)
	{
		this.name = name;
	}
	
	public void setQuantum (float quantum)
	{
		this.quantum = quantum;
	}

	public void setActivity (Activity activity)
	{
		this.activity = activity;
	}
	
	public void setArrivalTime (int arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}
	
	public void setExecutions (int executions)
	{
		this.executions = executions;
	}
	
	public void setTurnAround (float turnAround)
	{
		this.turnAround = turnAround;
	}
	
	public void itCame ()
	{
		came = true;
	}	

	public int getPid ()
	{
		return pid;
	}	
	
	public String getName ()
	{
		return name;
	}
	
	public float getQuantum ()
	{
		return quantum;
	}

	public Activity getActivity ()
	{
		return activity;
	}
	
	public int getArrivalTime ()
	{
		return arrivalTime;
	}
	public float getTurnAround ()
	{
		return turnAround;
	}
	
	public boolean alreadyItCame ()
	{
		return came;
	}
	
	public int getExecutions ()
	{
		return executions;
	}
	
	public int getTotalExecutions (int velocity) throws IOException
	{
		return (int) Math.ceil ((getRafagaTime (velocity) / 1000) / getQuantum ());
	}

	public void executeActivity (int velocity, views.MainWindow mainWindow, Manager manager) throws IOException, 
	InterruptedException
	{
		getActivity ().copyAndPaste (getName (), velocity, getQuantum (), mainWindow, manager);		
	}

	public float getRafagaTime (int velocity) throws IOException
	{
		float rafagaTime = 0;
		rafagaTime = velocity * getActivity ().getNumberOfCharacters ();
		return rafagaTime;
	}
}
