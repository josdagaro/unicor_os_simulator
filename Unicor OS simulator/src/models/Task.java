package models;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Task extends Thread
{
	private long size;
	private FileWriter writer;	
	private boolean suspended;
	private boolean completed;
	private float limit;
	private char [] letters;
	private JProgressBar progressBar;
	private JLabel progressLabel;
	private float rafaga;
	private int velocity;
	
	public Task ()
	{
		super ();
		suspended = false;
		completed = false;
	}
	
	public void init (String name, long size, FileWriter writer, float limit, char [] letters, JProgressBar progressBar, JLabel progressLabel, float rafaga, int velocity)
	{
		setName (name);
		this.writer = writer;
		this.size = size;
		this.limit = limit;
		this.letters = letters;
		this.progressBar = progressBar;
		this.progressLabel = progressLabel;
		this.rafaga = rafaga;
		this.velocity = velocity;
	}
	
	public boolean isSuspended ()
	{
		return suspended;
	}
	
	public boolean isCompleted ()
	{
		return completed;
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
		
		synchronized (this)
		{
			notify ();
		}
	}
	
	public void run ()
	{
		String percentage = null;
		int time = 0;
		System.out.println ("Inicia la actividad");
		
		for (int i = 0; i < size; i ++)
		{
			if (time < limit)
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
				progressBar.setValue ((int) ((i * velocity * 100) / rafaga));					
				percentage = new String (String.valueOf ((int) ((i * velocity * 100) / rafaga)));	
				progressLabel.setText ("Progreso: " + percentage + "%");					
				progressBar.repaint ();
			}
			else
			{						
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
