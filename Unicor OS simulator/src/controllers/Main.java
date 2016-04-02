package controllers;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Main 
{
	public static void main (String [] args)
	{
		SwingUtilities.invokeLater
		(
			new Runnable ()
			{
				public void run ()
				{					
					new MainWindow ();
				}
			}
		);		
	}
}
