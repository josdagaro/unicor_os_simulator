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
		switch (formComponents.valueOf (event.getActionCommand ()))
		{
			case rootFileButton:
				
				chooseFile (form.getRootFileField ());
				break;
				
			case destinationFileButton:
				
				chooseFile (form.getDestinationFileField ());
				break;
				
			case saveButton:
				break;
				
			case cancelButton:
				
				form.dispose ();
				break;
		}
	}	
	
	public void chooseFile (JTextField textField) //se ejecuta una ventana para seleccionar un archivo
	{
		FileSelector fileSelector = new FileSelector ();
		textField.setText (fileSelector.getFilePath ());
	}

	@Override
	public void keyPressed (KeyEvent e) //se valida que ningún campo de texto esté vacio
	{
		if 
		(   
			form.getPidField ().getText ().equals ("") || form.getNameField ().getText ().equals ("") || 
			form.getQuantumField ().getText ().equals ("") || form.getRootFileField ().getText ().equals ("") || 
			form.getDestinationFileField ().getText ().equals ("")
		)
		{			
			form.getSaveButton ().setEnabled (false);
		}
		else
		{
			form.getSaveButton ().setEnabled (true);
		}
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
