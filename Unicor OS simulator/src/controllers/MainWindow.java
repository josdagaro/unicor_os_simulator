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
		int size = 0;

		// TODO Auto-generated method stub
		switch (mainWindowComponents.valueOf (event.getActionCommand ()))
		{
			case toggleButton:
				
				if (mainWindow.getToggleButton ().getText ().equals ("Iniciar"))
				{
					mainWindow.getToggleButton ().setText ("Detener");
					
					try 
					{
						startProcessing ();
					} 
					catch (IOException | InterruptedException exception) 
					{
						// TODO Auto-generated catch block
						exception.printStackTrace();
					}
				}
				else
				{
					mainWindow.getToggleButton ().setText ("Iniciar");
				}							
					
				break;
			
			case rebootButton:
				
				size = mainWindow.getTable ().getRowCount ();
				
				for (int i = 0; i < size; i ++)
				{
					((DefaultTableModel) mainWindow.getTable ().getModel ()).removeRow (0);
				}	
				
				mainWindow.getToggleButton ().setEnabled (false);
				mainWindow.getCreateButton ().setEnabled (false);
				mainWindow.getVelocityField ().setEnabled (true);
				mainWindow.getVelocityField ().setText (null);
				mainWindow.getPidLabel ().setText ("PID:");
				mainWindow.getNameLabel ().setText ("Nombre:");
				mainWindow.getProgressLabel ().setText ("Progreso:");
				mainWindow.getProgressBar ().setValue (0);
				
				break;
				
			case createButton:
				
				new Form (manager, mainWindow);		
				mainWindow.setEnabled (false);
				break;
				
			case addVelocityButton:
				
				global = new Global ();
				
				if (!mainWindow.getVelocityField ().getText ().equals ("") && global.isNumeric (mainWindow.getVelocityField ().getText ()))
				{
					manager = new Manager (Integer.parseInt (mainWindow.getVelocityField ().getText ()));
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
	
	private void startProcessing () throws IOException, InterruptedException
	{
		models.Process process = null;
		int velocity = manager.getVelocity ();
		
		while (true)
		{
			if (!manager.getReadyQueue ().isEmpty ())
			{
				process = manager.getReadyQueue ().poll ();
				
				if (manager.getExecution () == null)
				{
					mainWindow.getPidLabel ().setText ("PID: " + String.valueOf (process.getPid ()));
					mainWindow.getNameLabel ().setText ("Nombre: " + process.getName ());
					manager.setExecution (process); //De listo a ejecución
					manager.getExecution().start();
					manager.getExecution ().run (velocity, mainWindow);
					
					if (manager.getExecution ().isInterrupted ())
					{
						JOptionPane.showMessageDialog (null, "interrumpido");
					}
					else
					{
						JOptionPane.showMessageDialog (null, "NO interrumpido");
					}
				}								
			}
			else
			{
				break;
			}
		}
	}
}
