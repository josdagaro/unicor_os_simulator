package controllers;

import java.awt.EventQueue; import models.Manager;

import views.Form;

public class Main 
{
	public static void main (String [] args)
	{
		EventQueue.invokeLater 
		(
			new Runnable () 
			{
				public void run() 
				{
					try 
					{
						new FormController (new Manager (100));						
					} 
					catch (Exception e) 
					{
						e.printStackTrace ();
					}
				}
			}
		);
	}
}
