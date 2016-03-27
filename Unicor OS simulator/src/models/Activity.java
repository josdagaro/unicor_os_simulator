package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	
	private File getDestinationFileIfExists ()
	{
		File destinationFile = new File (getDestinationPath ());
		
		if (!destinationFile.exists ())
		{
			destinationFile = null;
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
	
	public short copyAndPaste (short velocity) throws IOException
	{		
		String text = readRootFile ();
		File destinationFile = getDestinationFileIfExists ();
					
		short eventIdentifier = 0;
		
		if (text != null && destinationFile != null)
		{
			//copiado
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
