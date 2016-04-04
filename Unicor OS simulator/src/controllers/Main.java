package controllers;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
					try 
					{
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} 
					catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					new MainWindow ();
				}
			}
		);		
	}
}
