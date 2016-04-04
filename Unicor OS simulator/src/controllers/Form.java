package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.Activity;
import models.FileSelector;
import models.Manager;
import models.Process;

import javax.swing.table.DefaultTableModel;

import libraries.Global;

public class Form implements ActionListener, KeyListener
{
	private views.Form form;
	private Manager manager;
	private views.MainWindow mainWindow;
		
	public Form (Manager manager, views.MainWindow mainWindow)
	{
		this.form = new views.Form ();	
		this.manager = manager;
		this.mainWindow = mainWindow;
		initForm ();
	}
	
	private enum formComponents
	{
		rootFileButton, destinationFileButton, saveButton, cancelButton
	}
	
	private void initForm () 
	{
		form.setTitle ("Agregar proceso");
		form.setLocationRelativeTo (null);
		form.setVisible (true);
		form.getRootFileButton ().setActionCommand ("rootFileButton");
		form.getRootFileButton ().addActionListener (this);
		form.getDestinationFileButton ().setActionCommand ("destinationFileButton");
		form.getDestinationFileButton ().addActionListener (this);
		form.getSaveButton ().setActionCommand ("saveButton");
		form.getSaveButton ().addActionListener (this);
		form.getSaveButton ().setEnabled (false);
		form.getCancelButton ().setActionCommand ("cancelButton");
		form.getCancelButton ().addActionListener (this);			
		form.getPidField ().addKeyListener (this);
		form.getNameField ().addKeyListener (this);
		form.getQuantumField ().addKeyListener (this);		
		form.getRootFileField ().setEnabled (false);
		form.getDestinationFileField ().setEnabled (false);
	}
	
	@Override
	public void actionPerformed (ActionEvent event) //evento multi-caso en el cual se ejecuta una acción distinta dependiendo del botón presionado  
	{
		// TODO Auto-generated method stub
		
		Activity activity = null;
		Process process = null;
		Object [] object = null;
		
		switch (formComponents.valueOf (event.getActionCommand ()))
		{
			case rootFileButton:
				
				chooseFile (form.getRootFileField ());
				break;
				
			case destinationFileButton:
				
				chooseFile (form.getDestinationFileField ());
				break;
				
			case saveButton:
				
				if (!manager.validateExistenceByPid (Integer.parseInt (form.getPidField ().getText ())))
				{
					activity = new Activity (form.getRootFileField ().getText (), form.getDestinationFileField ().getText ());
					
					process = new Process 
					(
						Integer.parseInt (form.getPidField ().getText ()), form.getNameField ().getText (), Float.parseFloat (form.getQuantumField ().getText ()),
						activity
					);
					
					manager.getReadyQueue ().add (process);
					clearFields ();								
					
					try 
					{
						object = new Object [] 
						{
							process.getPid (), process.getName (), process.getRafagaTime (manager.getVelocity ()) / 1000,
							process.getQuantum (), "Listo", 0, process.getTotalExecutions (manager.getVelocity ())
						};
						
						((DefaultTableModel) mainWindow.getTable ().getModel ()).addRow (object);						
					} 
					catch (IOException exception)
					{
						// TODO Auto-generated catch block
						exception.printStackTrace ();
					}
					
					mainWindow.getToggleButton ().setEnabled (true);
					mainWindow.setEnabled (true);
					form.dispose ();
				}
				else
				{
					JOptionPane.showMessageDialog (null, "El PID que intenta registrar ya se encuentra en uso");
				}
				
				break;
				
			case cancelButton:
				
				mainWindow.setEnabled (true);
				form.dispose ();
				break;
		}
	}	
	
	private void chooseFile (JTextField textField) //se ejecuta una ventana para seleccionar un archivo
	{
		FileSelector fileSelector = new FileSelector ();
		String path = fileSelector.getFilePath ();
		
		if (path != null && !path.equals (""))
		{
			textField.setText (path);
			
			if (validateFields ())
			{
				form.getSaveButton ().setEnabled (true);
			}
			else
			{
				form.getSaveButton ().setEnabled (false);
			}
		}
	}
	
	private boolean validateFields () //se valida que ningún campo de texto esté vacio, incluyendo que la ruta de archivo fuente sea distinta del archivo destino
	{
		boolean check = true;
		Global global = new Global ();
		
		if 
		(   
			form.getPidField ().getText ().equals ("") || form.getNameField ().getText ().equals ("") || 
			form.getQuantumField ().getText ().equals ("") || form.getRootFileField ().getText ().equals ("") || 
			form.getDestinationFileField ().getText ().equals ("") || 
			form.getRootFileField ().getText ().equals (form.getDestinationFileField ().getText ()) || !global.isNumeric (form.getPidField ().getText ()) ||
			!global.isNumeric (form.getQuantumField ().getText ())
		)
		{	
			check = false;
		}
		
		return check;
	}

	@Override
	public void keyPressed (KeyEvent e) //cada vez que se presione una tecla, en cualquiera de los campos, se validan todos los campos
	{
		if (!validateFields ())
		{			
			form.getSaveButton ().setEnabled (false);
		}
		else
		{
			form.getSaveButton ().setEnabled (true);
		}
	}	
	
	private void clearFields ()
	{
		form.getPidField ().setText (null);
		form.getNameField ().setText (null);
		form.getQuantumField ().setText (null);
		form.getRootFileField ().setText (null);
		form.getDestinationFileField ().setText (null);
		form.getSaveButton ().setEnabled (false);
	}

	@Override
	public void keyTyped (KeyEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased (KeyEvent e) 
	{
		// TODO Auto-generated method stub		
	}
}
