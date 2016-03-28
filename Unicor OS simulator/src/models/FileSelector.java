package models;

import java.io.File;
import javax.swing.JFileChooser;

public class FileSelector 
{
	private JFileChooser fileChooser;
	
	public FileSelector ()
	{
		fileChooser = new JFileChooser ();
	}
	
	public String getFilePath () 
	{
		String path = null;
		
		if (fileChooser.showOpenDialog (null) == JFileChooser.APPROVE_OPTION)
		{
			path = new String ();
			File file = fileChooser.getSelectedFile ();
			path = file.getPath ();
		}
		
		return path;
	}
}
