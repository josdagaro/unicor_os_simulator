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
	
	public void copyAndPaste (int velocity, Process process, views.MainWindow mainWindow, Manager manager) throws IOException, 
	InterruptedException
	{		
		String text = readRootFile ();
		File destinationFile = getDestinationFileIfExists ();
		FileWriter writer = null;
		long size = 0;
		char [] letters = null;
		int time = 0;
		float limit = process.getQuantum () * 1000;
		float rafaga = 0;
		String percentage = null;
		
		if (text != null && destinationFile != null)
		{
			size = text.length ();
			rafaga = size * velocity;
			letters = text.toCharArray ();
			writer = new FileWriter (destinationFile);
					
			for (int i = 0; i < size; i ++)
			{	
				//System.out.println ("Test");							
				if (time < limit)
				{													
					try 
					{						
						writer.append (letters [i]);
						//chronometer.initChronometer ();
						for (int j = 0; j < velocity; j ++)
						{
							Thread.sleep (1);							
						}
						
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}															
					
					time += velocity;
					mainWindow.getProgressBar ().setValue ((int) ((i * velocity * 100) / rafaga));
					percentage = new String (String.valueOf ((int) ((i * velocity * 100) / rafaga)));	
					mainWindow.getProgressLabel ().setText ("Progreso: " + percentage + "%");
					mainWindow.getProgressBar ().repaint ();
				}
				else
				{						
					//manager.getStopQueue ().add (process);
					//manager.setExecution (null);
					process.suspendIt ();					
					//global.setTime (0);
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
							
			process.completed ();																					

			
			/*while (true)
			{
				if (global.getTime () >= limit)
				{
					global.setTime (0);
					timer.stop ();		
					System.out.println ("entro");
					break;
				}
			}*/
			/*while (global.getI () < global.getSize ())
			{
				if (global.getTime () < limit)
				{
					timer = new Timer (velocity, getActionListener (limit, mainWindow, velocity));			
					timer.setRepeats (false);
					timer.start ();
				}
				else
				{	
					System.out.println ("Test 4");
					//timer.stop ();
					/*
					try 
					{
						global.getWriter ().close ();
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace ();
					}*/
					/*
					process.wait ();
				}
				
			}
			
			/*for (int i = 0; i < size; i ++)
			{
				if (time < limit) //limit es el quantum expresado en milisegundos
				{
					writer.append (letters [i]);
					timer.start ();
					//se espera el tiempo indicado entre copiado de caracter
					time += velocity;
					mainWindow.getProgressBar ().setValue ((int) ((i * velocity * 100) / rafaga)); //se actualiza la barra de progreso
					percentage = String.valueOf ((int) ((i * velocity * 100) / rafaga));				
					mainWindow.getProgressLabel ().setText ("Progreso: " + percentage + "%"); //se actualiza el label de progreso
					mainWindow.getProgressBar ().repaint ();				
				}
				else //si time llega a ser igual al quantum, entonces se reinicia time a cero y se detiene el proceso de copiado con la funcion wait
				{	//JOptionPane.showMessageDialog (null, "aqui");
					time = 0;
					writer.close ();
					
					synchronized (this)
					{
						this.wait ();
					}
					//el objeto de tipo Activity va a esperar que la funcion notify se llame para reanudar su proceso					
				}				
			}
			
			writer.close ();*/
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
