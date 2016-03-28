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

	public Activity (String rootPath, String destinationPath) 
	{
		setRootPath (rootPath);
		setDestinationPath (destinationPath);
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
	
	public String readRootFile () throws IOException
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
	
	public synchronized short copyAndPaste (short velocity, float quantum) throws IOException, InterruptedException
	{		
		String text = readRootFile ();
		File destinationFile = getDestinationFileIfExists ();
		FileWriter writer = null;
		long size = 0;
		char [] letters = null;
		int time = 0;
		float limit = quantum * 1000;
					
		short eventIdentifier = 0;
		
		if (text != null && destinationFile != null)
		{			
			size = text.length ();
			letters = text.toCharArray ();
			writer = new FileWriter (destinationFile);
			
			for (int i = 0; i < size; i ++)
			{
				if (time < limit) //limit es el quantum expresado en milisegundos
				{
					writer.append (letters [i]);
					Thread.sleep (velocity); //se espera el tiempo indicado entre copiado de caracter
					time += velocity;
				}
				else //si time llega a ser igual al quantum, entonces se reinicia time a cero y se detiene el proceso de copiado con la funcion wait
				{
					time = 0;
					this.wait (); //el objeto de tipo Activity va a esperar que la funcion notify se llame para reanudar su proceso
				}				
			}
			
			writer.close ();
		}
		else 
		{
			if (text == null)
			{
				eventIdentifier = 1;
			}
			
			if (destinationFile == null)
			{
				eventIdentifier = 2;
			}
			
			if (text == null && destinationFile == null)
			{
				eventIdentifier = 3;
			}
		}
		
		return eventIdentifier;
	}
}
