package models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Task extends Thread
{
	private long size;
	private FileWriter writer;	
	private boolean suspended;
	private boolean completed;
	private float limit;
	private char [] letters;	
	private float rafaga;
	private int velocity;
	private boolean stopped;
	private views.MainWindow mainWindow;
	private Manager manager;
	
	public Task ()
	{
		super ();
		suspended = false;
		completed = false;
		stopped = false;
	}
	
	public void init (String name, long size, FileWriter writer, float limit, char [] letters, views.MainWindow mainWindow, float rafaga, int velocity, Manager manager)
	{
		setName (name);
		this.writer = writer;
		this.size = size;
		this.limit = limit;
		this.letters = letters;
		this.mainWindow = mainWindow;
		this.rafaga = rafaga;
		this.velocity = velocity;
		this.manager = manager;
	}
	
	public boolean isSuspended ()
	{
		return suspended;
	}
	
	public boolean isCompleted ()
	{
		return completed;
	}
	
	public void stopTrue ()
	{
		stopped = true;
	}
	
	public void stopIt () throws InterruptedException
	{
		stopped = true;
		
		synchronized (this)
		{
			wait ();
		}
	}
	
	public boolean isStopped ()
	{
		return stopped;
	}
	
	public void suspendIt () throws InterruptedException
	{
		suspended = true;
		
		synchronized (this)
		{
			wait ();
		}
	}
	
	public void resumeIt ()
	{
		suspended = false;
		stopped = false;
		
		synchronized (this)
		{
			notify ();
		}
	}
	
	public void run ()
	{		
		String percentage = null;
		int time = 0;
		Queue <Process> temporalQueue = new LinkedList <Process> ();
		Process process = null;
		System.out.println ("Inicia la actividad");		
		
		while (!manager.getReadyQueue ().isEmpty ())
		{
			process = manager.getReadyQueue ().peek ();
			temporalQueue.add (manager.getReadyQueue ().poll ());			
			process.setTurnAround (process.getTurnAround () + (size * velocity));
		}
		
		while (!temporalQueue.isEmpty ())
		{
			manager.getReadyQueue ().add (temporalQueue.poll ());
		}					

		while (!manager.getStopQueue ().isEmpty ())
		{
			process = manager.getStopQueue ().peek ();
			temporalQueue.add (manager.getStopQueue ().poll ());
			process.setTurnAround (process.getTurnAround () + (size * velocity));
		}
		
		while (!temporalQueue.isEmpty ())
		{
			manager.getStopQueue ().add (temporalQueue.poll ());
		}
		
		manager.getExecution ().setTurnAround (manager.getExecution ().getTurnAround () + (size * velocity));
		
		for (int i = 0; i < size; i ++)
		{
			if (time < limit)
			{									
				if (!stopped)
				{
					try
					{						
						writer.append (letters [i]);
						Thread.sleep (velocity);					
					} 
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						
					
					time += velocity;
					mainWindow.getProgressBar ().setValue ((int) (((i + 1) * velocity * 100) / rafaga));					
					percentage = new String (String.valueOf ((int) (((i + 1) * velocity * 100) / rafaga)));	
					mainWindow.getProgressLabel ().setText ("Progreso: " + percentage + "%");					
					mainWindow.getProgressBar ().repaint ();					
				}
				else
				{
					i --;
					
					try 
					{
						System.out.println ("Proceso detenido con metodo stopIt");
						stopIt ();
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{		
				i --;
				
				try 
				{
					System.out.println ("Proceso [" + getName () + "]: detenido");					
					suspendIt ();					
				} 
				catch (InterruptedException e) 
				{
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				System.out.println ("Proceso [" + getName () + "]: reanudado");
				time = 0;
			}	
		}
		
		try 
		{			
			writer.close ();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		completed = true;
	}
}
