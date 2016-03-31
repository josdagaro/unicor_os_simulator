package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import models.Manager;

public class MainWindow implements ActionListener
{
	private views.MainWindow mainWindow;
	private Manager manager;
	
	public MainWindow ()
	{
		mainWindow = new views.MainWindow (getTableModel ());		
		manager = null;
		initForm ();
	}
	
	private enum mainWindowComponents
	{
		toggleButton, rebootButton, createButton, addVelocityButton
	}
	
	private void initForm ()
	{		
		mainWindow.setLocationRelativeTo (null);
		mainWindow.setVisible (true);
		mainWindow.getToggleButton ().setEnabled (false);
		mainWindow.getToggleButton ().setActionCommand ("toggleButton");
		mainWindow.getToggleButton ().addActionListener (this);
		mainWindow.getRebootButton ().setActionCommand ("rebootButton");
		mainWindow.getRebootButton ().addActionListener (this);
		mainWindow.getCreateButton ().setEnabled (false);
		mainWindow.getCreateButton ().setActionCommand ("createButton");
		mainWindow.getCreateButton ().addActionListener (this);
		mainWindow.getAddVelocityButton ().setActionCommand ("addVelocityButton");
		mainWindow.getAddVelocityButton ().addActionListener (this);		
	}

	@Override
	public void actionPerformed (ActionEvent event) 
	{
		Global global = null;

		// TODO Auto-generated method stub
		switch (mainWindowComponents.valueOf (event.getActionCommand ()))
		{
			case toggleButton:
				
				if (mainWindow.getToggleButton ().getText ().equals ("Iniciar"))
				{
					mainWindow.getToggleButton ().setText ("Detener");
					
					if (!manager.isSuspended ())
					{
						try 
						{
							processing ();
						} 
						catch (IOException | InterruptedException exception) 
						{
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
					}	
					else
					{
						manager.resumeIt ();
					}
				}
				else
				{
					mainWindow.getToggleButton ().setText ("Iniciar");										
					manager.suspendIt ();
				}							
					
				break;
			
			case rebootButton:
				
				mainWindow.dispose ();
				mainWindow = new views.MainWindow (getTableModel ());
				manager = null;
				initForm ();				
				break;
				
			case createButton:
				
				new Form (manager, mainWindow);		
				mainWindow.setEnabled (false);
				break;
				
			case addVelocityButton:
				
				global = new Global ();
				
				if (!mainWindow.getVelocityField ().getText ().equals ("") && global.isNumeric (mainWindow.getVelocityField ().getText ()))
				{
					if (manager == null)
					{
						manager = new Manager (Integer.parseInt (mainWindow.getVelocityField ().getText ()));					
					}
					
					manager.start ();
					mainWindow.getVelocityField ().setEnabled (false);
					mainWindow.getCreateButton ().setEnabled (true);
				}
				else
				{
					JOptionPane.showMessageDialog (null, "La velocidad especificada no es valida");
				}
				
				break;
		}
	}
	
	private DefaultTableModel getTableModel ()
	{
		String [] header = {"PID", "Nombre", "Ráfaga", "Quantum", "Estado", "Tiempo de llegada"};
		
		DefaultTableModel model = new DefaultTableModel (new Object [][] {}, header)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			Class <?> [] columnTypes = new Class []
			{
				Integer.class, String.class, Float.class, Float.class, String.class, Float.class
			};
			
			public Class <?> getColumnClass (int columnIndex)
			{
				return columnTypes [columnIndex];
			}
			
			boolean [] columnEditables = new boolean []
			{
				false, false, false, false, false, false
			};
			
			public boolean isCellEditable (int row, int column)
			{
				return columnEditables [column];
			}
		};
		
		return model;
	}
	
	private void processing () throws IOException, InterruptedException
	{	
		boolean liberation = true;
		models.Process process = null;
		int velocity = manager.getVelocity ();
		
		while (true)
		{
			if (!manager.getReadyQueue ().isEmpty () && liberation)
			{				
				if (manager.getExecution () == null)
				{
					process = manager.getReadyQueue ().poll ();
					mainWindow.getPidLabel ().setText ("PID: " + String.valueOf (process.getPid ()));
					mainWindow.getNameLabel ().setText ("Nombre: " + process.getName ());
					manager.setExecution (process); //De listo a ejecución
					modifyStateInTable (manager.getExecution ().getPid (), "Ejecución");
					manager.getExecution ().start ();		
					manager.getExecution ().executeActivity (velocity, mainWindow, manager);														
					
					if (!manager.getExecution ().isSuspended () || !manager.getExecution ().isCompleted ())
					{
						System.out.println ("¡No suspendido!");
					}
					else
					{
						System.out.println ("¡Suspendido!");
					}
					/*INTENTAR PASANDO MANAGER POR PARAMETRO PARA PASA A COLA DETENIDO ALLA EN ACTIVITY*/
					/*while (true)
					{
						if (manager.getExecution ().isSuspended ())
						{
							modifyStateInTable (manager.getExecution ().getPid (), "Detenido");
							manager.getStopQueue ().add (manager.getExecution ());
							manager.setExecution (null);
							liberation = true;
							JOptionPane.showMessageDialog (null, "¡Suspendido!");
							break;							
						}
					}*/
					/*while (true)
					{
						if (manager.getExecution ().isSuspended ())
						{
							modifyStateInTable (manager.getExecution ().getPid (), "Detenido");
							manager.getStopQueue ().add (manager.getExecution ());
							manager.setExecution (null);
							liberation = true;
							break;
						}
						else if (manager.getExecution ().isCompleted ())
						{
							modifyStateInTable (manager.getExecution ().getPid (), "Terminado");
							manager.getListOfCompleted ().add (manager.getExecution ());
							manager.setExecution (null);
							liberation = false;
							break;
						}*/
				}	
			}
			else if (!manager.getStopQueue ().isEmpty ())
			{
				/*if (manager.getExecution () == null)
				{
					process = manager.getStopQueue ().poll ();
					mainWindow.getPidLabel ().setText ("PID: " + String.valueOf (process.getPid ()));
					mainWindow.getNameLabel ().setText ("Nombre: " + process.getName ());
					manager.setExecution (process); //De listo a ejecución
					modifyStateInTable (manager.getExecution ().getPid (), "Ejecución");
					manager.getExecution ().resumeIt ();
				}*/ break;
			}
			else
			{
				break;
			}
		}
	}
	
	private void modifyStateInTable (int pid, String state)
	{
		int size = mainWindow.getTable ().getRowCount ();
		
		for (int i = 0; i < size; i ++)
		{
			if (pid == (int) mainWindow.getTable ().getValueAt (i, 0))
			{
				mainWindow.getTable ().setValueAt (state, i, 4);
			}
		}
	}
}
