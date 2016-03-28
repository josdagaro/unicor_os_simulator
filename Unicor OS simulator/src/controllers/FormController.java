package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Activity;
import models.FileSelector;
import models.Manager;
import models.Process;

import views.Form;

public class FormController implements ActionListener
{
	private Form form;
	private Manager manager;
		
	public FormController (Manager manager)
	{
		this.form = new Form ();	
		this.manager = manager;
		initForm ();
	}
	
	public enum formButtons
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
	}
	
	@Override
	public void actionPerformed (ActionEvent event) 
	{
		FileSelector fileSelector = null;
		// TODO Auto-generated method stub
		switch (formButtons.valueOf (event.getActionCommand ()))
		{
			case rootFileButton:
				
				fileSelector = new FileSelector ();
				form.getRootFileField ().setText (fileSelector.getFilePath ());
				break;
				
			case destinationFileButton:
				
				fileSelector = new FileSelector ();
				form.getDestinationFileField ().setText (fileSelector.getFilePath ());
				break;
				
			case saveButton:
				break;
				
			case cancelButton:
				
				form.dispose ();
				break;
		}
	}
	
}
