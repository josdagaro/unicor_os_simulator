package controllers;

import java.io.FileWriter;

public class Global 
{
	private int i;
	private int time;
	private FileWriter writer;
	private views.MainWindow mainWindow;
	private models.Process process;
	private String letters;
	private long size;
	private float rafaga;
	private String percentage;
	
	public Global ()
	{
		i = 0;
		time = 0;
		mainWindow = null;
		process = null;
		writer = null;
		letters = null;
		size = 0;
		rafaga = 0;
		percentage = null;
	}
	
	public void setI (int i)
	{
		this.i = i;
	}
	
	public int getI ()
	{
		return i;
	}
	
	public void setTime (int time)
	{
		this.time = time;
	}
	
	public int getTime ()
	{
		return time;
	}
	
	public void setWriter (FileWriter writer)
	{
		this.writer = writer;
	}
	
	public FileWriter getWriter ()
	{
		return writer;
	}
	
	public void setLetters (String letters)
	{
		this.letters = letters;
	}
	
	public String getLetters ()
	{
		return letters;
	}
	
	public void setSize (long size)
	{
		this.size = size;
	}
	
	public long getSize ()
	{
		return size;
	}
	
	public void setRafaga (float rafaga)
	{
		this.rafaga = rafaga;
	}
	
	public float getRafaga ()
	{
		return rafaga;
	}
	
	public void setPercentage (String percentage)
	{
		this.percentage = percentage;
	}
	
	public String getPercentage ()
	{
		return percentage;
	}
	
	public boolean isNumeric (String chain) 
	{
		return (chain.matches ("[+-]?\\d*(\\.\\d+)?"));
	}
}
