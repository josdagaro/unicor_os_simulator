package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

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
	 * Launch the application.
	 */
	public static void main (String[] args) 
	{
		EventQueue.invokeLater 
		(
			new Runnable () 
			{
				public void run() 
				{
					try 
					{
						Form frame = new Form ();
						frame.setVisible (true);
					} 
					catch (Exception e) 
					{
						e.printStackTrace ();
					}
				}
			}
		);
	}

	/**
	 * Create the frame.
	 */
	public Form () 
	{
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setBounds (100, 100, 385, 344);
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
		contentPane.add (panel_2);
		
		saveButton = new JButton ("Guardar");
	
		panel_2.add (saveButton);
		
		cancelButton = new JButton ("Cancelar");
		panel_2.add (cancelButton);
	}
}
