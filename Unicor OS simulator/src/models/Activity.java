package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Activity /*recibe el nombre ya que es una clase diseñada solo para copiar y pegar caracteres 
					    de un archivo de texto a otro*/
{
	private String rootPath; //ruta del archivo que contiene los caracteres
	private String destinationPath; //ruta del archivo destino en el cual se van a pegar los caracteres
	private Task task; //Hilo que realizará el copiado y pegado
	
	public Activity (String rootPath, String destinationPath) 
	{
		setRootPath (rootPath);
		setDestinationPath (destinationPath);
		task = new Task ();
	}
	
	public void setRootPath (String rootPath)
	{
		this.rootPath = rootPath;		
	}

	public void setDestinationPath (String destinationPath)
	{
		this.destinationPath = destinationPath;
	}

	public String getRootPath ()
	{
		return rootPath;
	}

	public String getDestinationPath ()
	{
		return destinationPath;
	}
	
	public Task getTask ()
	{
		return task;
	}
	
	private File getRootFileIfExists ()
	{
		File rootFile = new File (getRootPath ());
		
		if (!rootFile.exists ())
		{
			rootFile = null;
		}
		
		return rootFile;
	}
	
	private File getDestinationFileIfExists () throws IOException //metodo para validar la existencia del archivo destino, si no existe, lo crea, pero si ya hay uno con ese nombre, retorna null
	{
		File destinationFile = new File (getDestinationPath ());
		
		if (!destinationFile.exists ())
		{
			try 
			{
				if (!destinationFile.createNewFile ())
				{
					destinationFile = null;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return destinationFile;
	}
	
	private String readRootFile () throws IOException
	{
		File rootFile = getRootFileIfExists ();
		FileReader reader = null;
		BufferedReader buffer = null;
		String text = null, line = null;
		
		if (rootFile != null)
		{
			reader = new FileReader (rootFile);
			buffer = new BufferedReader (reader);
			text = new String ();
			line = new String ();
			
			while (true)
			{				
				line = buffer.readLine ();
				
				if (line != null)
				{
					text += line + "\n";
				}
				else
				{
					break;
				}
			}
			
			buffer.close ();
		}
		
		return text;
	}
	
	public void copyAndPaste (String name, int velocity, float quantum, views.MainWindow mainWindow) throws IOException, 	
	InterruptedException
	{				
		String text = readRootFile ();
		File destinationFile = getDestinationFileIfExists ();
		FileWriter writer = null;
		long size = 0;
		char [] letters = null;		
		float limit = quantum * 1000;
		float rafaga = 0;
				
		if (text != null && destinationFile != null)
		{
			size = text.length ();
			rafaga = size * velocity;
			letters = text.toCharArray ();
			writer = new FileWriter (destinationFile);
			task.init (name, size, writer, limit, letters, mainWindow, rafaga, velocity);
			task.start ();
		}
	}
	
	public long getNumberOfCharacters () throws IOException
	{
		String text = readRootFile ();
		long size = 0;
		
		if (text != null)
		{
			size = text.length ();			
		}
		
		return size;
	}
}
