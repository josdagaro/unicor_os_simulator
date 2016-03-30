package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class Form extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField pidField;
	private JTextField nameField;
	private JTextField quantumField;
	private JLabel lblArchivoFuente;
	private JTextField rootFileField;
	private JLabel lblArchivoDestino;
	private JTextField destinationFileField;
	private JPanel panel;
	private JButton rootFileButton;
	private JPanel panel_1;
	private JButton destinationFileButton;
	private JPanel panel_2;
	private JButton saveButton;	
	private JButton cancelButton;
	
	/**
	 * Create the frame.
	 */
	public Form () 
	{
		setAlwaysOnTop (true);
		setTitle ("Crear proceso");
		setResizable (false);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setBounds (100, 100, 385, 371);
		contentPane = new JPanel ();
		contentPane.setBorder (new TitledBorder (null, "Nuevo proceso", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		setContentPane (contentPane);
		contentPane.setLayout (new GridLayout (0, 1, 5, 5));		
		JLabel lblPid = new JLabel ("PID:");
		contentPane.add (lblPid);		
		pidField = new JTextField ();
		contentPane.add (pidField);
		pidField.setColumns (10);		
		JLabel lblNombre = new JLabel ("Nombre:");
		contentPane.add (lblNombre);		
		nameField = new JTextField ();
		contentPane.add (nameField);
		nameField.setColumns (10);		
		JLabel lblQuantum = new JLabel ("Quantum:");
		contentPane.add (lblQuantum);		
		quantumField = new JTextField ();
		quantumField.setText("10");
		contentPane.add (quantumField);
		quantumField.setColumns (10);		
		panel = new JPanel ();
		contentPane.add (panel);
		panel.setLayout (new GridLayout (0, 3, 5, 5));		
		lblArchivoFuente = new JLabel ("Archivo fuente:");
		panel.add (lblArchivoFuente);		
		rootFileField = new JTextField ();
		panel.add (rootFileField);
		rootFileField.setColumns (10);		
		rootFileButton = new JButton ("Seleccionar");		
		panel.add (rootFileButton);
		panel_1 = new JPanel ();
		contentPane.add (panel_1);
		panel_1.setLayout (new GridLayout (0, 3, 5, 5));		
		lblArchivoDestino = new JLabel ("Archivo destino:");
		panel_1.add (lblArchivoDestino);		
		destinationFileField = new JTextField ();
		panel_1.add (destinationFileField);
		destinationFileField.setColumns (10);	
		destinationFileButton = new JButton ("Seleccionar");		
		panel_1.add (destinationFileButton);		
		panel_2 = new JPanel ();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout ();
		flowLayout.setHgap (60);
		contentPane.add (panel_2);		
		saveButton = new JButton ("Guardar");
		panel_2.add (saveButton);		
		cancelButton = new JButton ("Cancelar");		
		panel_2.add (cancelButton);
	}
	
	public JTextField getPidField () 
	{
		return pidField;
	}

	public JTextField getNameField () 
	{
		return nameField;
	}

	public JTextField getQuantumField () 
	{
		return quantumField;
	}

	public JTextField getRootFileField () 
	{
		return rootFileField;
	}

	public JTextField getDestinationFileField () 
	{
		return destinationFileField;
	}

	public JButton getRootFileButton () 
	{
		return rootFileButton;
	}
	
	public JButton getDestinationFileButton () 
	{
		return destinationFileButton;
	}

	public JButton getSaveButton () 
	{
		return saveButton;
	}

	public JButton getCancelButton () 
	{
		return cancelButton;
	}	
}
