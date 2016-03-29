package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import models.Manager;

public class MainWindow implements ActionListener
{
	private views.MainWindow mainWindow;
	private Manager manager;
	
	public MainWindow ()
	{
		mainWindow = new views.MainWindow ();
		manager = null;
		initForm ();
	}
	
	private enum mainWindowComponents
	{
		toggleButton, rebootButton, createButton, addVelocityButton
	}
	
	private void initForm ()
	{
		DefaultTableModel model = null;
		String [] header = {"PID", "Nombre", "Ráfaga", "Quantum", "Estado", "Tiempo de llegada"};
		mainWindow.setLocationRelativeTo (null);
		mainWindow.setVisible (true);
		mainWindow.getToggleButton ().setEnabled (false);
		mainWindow.getToggleButton ().setActionCommand ("toggleButton");
		mainWindow.getToggleButton ().addActionListener (this);
		mainWindow.getRebootButton ().setEnabled (false);
		mainWindow.getRebootButton ().setActionCommand ("rebootButton");
		mainWindow.getRebootButton ().addActionListener (this);
		mainWindow.getCreateButton ().setEnabled (false);
		mainWindow.getCreateButton ().setActionCommand ("createButton");
		mainWindow.getCreateButton ().addActionListener (this);
		mainWindow.getAddVelocityButton ().setActionCommand ("addVelocityButton");
		mainWindow.getAddVelocityButton ().addActionListener (this);
		
		model = new DefaultTableModel (new Object [][] {}, header)
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
		
		mainWindow.getTable ().setModel (model);
	}

	@Override
	public void actionPerformed (ActionEvent event) 
	{
		Global global = null;
		Form form = null;
		// TODO Auto-generated method stub
		switch (mainWindowComponents.valueOf (event.getActionCommand ()))
		{
			case toggleButton:
				
				
				break;
			
			case rebootButton:
				
				
				break;
				
			case createButton:
				
				form = new Form (manager);
				break;
				
			case addVelocityButton:
				
				global = new Global ();
				
				if (global.isNumeric (mainWindow.getVelocityField ().getText ()))
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
}
