package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import models.Activity;
import models.FileSelector;
import models.Manager;
import models.Process;

import views.Form;

public class FormController implements ActionListener, KeyListener
{
	private Form form;
	private Manager manager;
		
	public FormController (Manager manager)
	{
		this.form = new Form ();	
		this.manager = manager;
		initForm ();
	}
	
	public enum formComponents
	{
		rootFileButton, destinationFileButton, saveButton, cancelButton
	}
	
	public void initForm () 
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
		form.getRootFileField ().addKeyListener (this);
		form.getDestinationFileField ().addKeyListener (this);
	}
	
	@Override
	public void actionPerformed (ActionEvent event) //evento multi-caso en el cual se ejecuta una acción distinta dependiendo del botón presionado  
	{
		// TODO Auto-generated method stub
		
		Activity activity = null;
		Process process = null;
		
		switch (formComponents.valueOf (event.getActionCommand ()))
		{
			case rootFileButton:
				
				chooseFile (form.getRootFileField ());
				break;
				
			case destinationFileButton:
				
				chooseFile (form.getDestinationFileField ());
				break;
				
			case saveButton:
				activity = new Activity (form.getRootFileField ().getText (), form.getDestinationFileField ().getText ());
				
				process = new Process 
				(
					Integer.parseInt (form.getPidField ().getText ()), form.getNameField ().getText (), Float.parseFloat (form.getQuantumField ().getText ()),
					activity
				);
				
				manager.getReadyQueue ().add (process);
				clearFields ();
				form.dispose ();
				break;
				
			case cancelButton:
				
				form.dispose ();
				break;
		}
	}	
	
	public void chooseFile (JTextField textField) //se ejecuta una ventana para seleccionar un archivo
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
		
		if 
		(   
			form.getPidField ().getText ().equals ("") || form.getNameField ().getText ().equals ("") || 
			form.getQuantumField ().getText ().equals ("") || form.getRootFileField ().getText ().equals ("") || 
			form.getDestinationFileField ().getText ().equals ("") || 
			form.getRootFileField ().getText ().equals (form.getDestinationFileField ().getText ())
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
