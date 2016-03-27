package models;

public class Process extends Thread 
{
	private short pid; //identificación del proceso	
	private float quantum; //tiempo en segundos en uso CPU
	private Activity activity; /*actividad del proceso, contiene ruta fuente (copiado) y destino (pegado)
	 							 como también las funciones necesarias para realizar dicha tarea*/
	
	public Process (short pid, String name, float quantum, Activity activity)
	{
		setPid (pid);
		setName (name); //metodos setName () y getName () heredados de la clase Thread
		setQuantum (quantum);
		setActivity (activity);
	}
	
	public void setPid (short pid)
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
	
	public short getPid ()
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
}
