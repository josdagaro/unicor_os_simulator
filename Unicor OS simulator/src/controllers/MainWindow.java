package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import models.Global;
import models.Manager;

public class MainWindow implements ActionListener
{
	private views.MainWindow mainWindow;
	private Manager manager;
	
	public MainWindow ()
	{
		this.mainWindow = new views.MainWindow (getTableModel ());
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
		mainWindow.getCreateButton ().setEnabled (false);
		mainWindow.getToggleButton ().setActionCommand ("toggleButton");
		mainWindow.getRebootButton ().setActionCommand ("rebootButton");
		mainWindow.getCreateButton ().setActionCommand ("createButton");
		mainWindow.getAddVelocityButton ().setActionCommand ("addVelocityButton");
		mainWindow.getToggleButton ().addActionListener (this);
		mainWindow.getRebootButton ().addActionListener (this);
		mainWindow.getCreateButton ().addActionListener (this);
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
					if (!manager.isSuspended () && manager.getExecution () == null)
					{					
						mainWindow.getToggleButton ().setText ("Detener");
						
						final SwingWorker <?, ?> worker = new SwingWorker <Object, Object> () 
						{								
							@Override
							protected Object doInBackground () throws Exception 
							{
								processesRun ();
								return null;
							}
						};
						
						worker.execute ();
					}	
					else
					{		
						System.out.println ("Se reanuda el manager");
						mainWindow.getToggleButton ().setText ("Detener");
						manager.resumeIt ();
						modifyStateInTable (manager.getExecution ().getPid (), "Ejecución");
					}
					
					if (manager.isCompleted ())
					{
						JOptionPane.showMessageDialog (null, "Todo el procesamiento ha finalizado, no hay nada que iniciar");
					}
				}
				else if (manager.isRunning ())
				{	
					mainWindow.getToggleButton ().setText ("Iniciar");	
					
					final SwingWorker <?, ?> worker = new SwingWorker <Object, Object> () 
					{								
						@Override
						protected Object doInBackground () throws Exception 
						{
							System.out.println ("Se activa stopTrue para la tarea");
							manager.getExecution ().getActivity ().getTask ().stopTrue ();	
							modifyStateInTable (manager.getExecution ().getPid (), "Detenido");
							return null;
						}
					};
					
					worker.execute ();						
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
	
	private void processesRun () throws IOException, InterruptedException
	{	
		System.out.println ("Despachador inicia");	
		manager.start ();
		
		while (!manager.isCompleted ())
		{										
			synchronized (manager)
			{
				System.out.println ("1) Se detiene el controlador de la ventana principal");
				manager.suspendIt ();
				System.out.println ("Se actualiza estado del proceso en la tabla");
				mainWindow.getPidLabel ().setText ("PID: " + String.valueOf (manager.getExecution ().getPid ()));
				mainWindow.getNameLabel ().setText ("Nombre: " + manager.getExecution ().getName ());	
				modifyStateInTable (manager.getExecution ().getPid (), "Ejecución");				
			}			
			
			while (true)
			{				
				if (manager.isSuspended ())
				{
					if (!manager.getExecution ().getActivity ().getTask ().isSuspended ())
					{
						System.out.println ("Se inicia nueva tarea");	
						
						manager.getExecution ().executeActivity 
						(
							manager.getVelocity (), mainWindow.getProgressBar (), mainWindow.getProgressLabel ()
						);
					}
					else
					{
						System.out.println ("Se resume tarea");
						System.out.println ("Se actualiza estado del proceso en la tabla");
						manager.getExecution ().getActivity ().getTask ().resumeIt ();						
						modifyStateInTable (manager.getExecution ().getPid (), "Ejecución");
					}
					
					System.out.println ("4) Se reanuda el despachador");
					manager.resumeIt ();
					
					while (true)
					{
						if (manager.isSuspended ())
						{
							System.out.println ("Se actualiza estado del proceso en la tabla");
							
							if (manager.getExecution ().getActivity ().getTask ().isSuspended ())
							{			
								System.out.println ("Detenido");
								modifyStateInTable (manager.getExecution ().getPid (), "Detenido");
							}
							else if (manager.getExecution ().getActivity ().getTask ().isCompleted ())
							{						
								System.out.println ("Terminado");
								modifyStateInTable (manager.getExecution ().getPid (), "Terminado");
							}							
							
							System.out.println ("6) Se reanuda el despachador");
							manager.resumeIt ();
							break;
						}
						
						Thread.sleep (1);
					}
					
					break;
				}
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
