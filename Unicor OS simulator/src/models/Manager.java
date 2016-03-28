package models;

import java.util.LinkedList;
import java.util.Queue;

public class Manager 
{
	private short velocity; //tiempo de espera para cada thread en milisegundos para copiar cada caracter
	private Queue <Process> readyQueue; //cola de procesos listos
	private Queue <Process> stopQueue; //cola de procesos detenidos
	private LinkedList <Process> listOfCompleted; //lista de procesos terminados
	
	public Manager (short velocity)
	{
		setVelocity (velocity);
		init ();
	}
	
	public void setVelocity (short velocity)
	{
		this.velocity = velocity;
	}
	
	public Queue <Process> getReadyQueue ()
	{
		return readyQueue;
	}
	
	public Queue <Process> getStopQueue ()
	{
		return stopQueue;
	}
	
	public void init () //metodo para inicializar como nueva la lista y colas 
	{
		if (readyQueue != null)
		{
			readyQueue.clear ();
		}
		
		if (stopQueue != null) 
		{
			stopQueue.clear ();
		}
		
		if (listOfCompleted != null)
		{
			listOfCompleted.clear ();
		}
		
		readyQueue = new LinkedList <Process> ();
		stopQueue = new LinkedList <Process> ();
		listOfCompleted = new LinkedList <Process> ();
	}
}
