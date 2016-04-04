package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

public class MainWindow extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField velocityField;
	private JLabel pidLabel;
	private JLabel nameLabel;
	private JLabel progressLabel;
	private JButton toggleButton;
	private JButton rebootButton;
	private JButton createButton;
	private JButton addVelocityButton;
	private JProgressBar progressBar;
	private JLabel executionsLabel;
	private JPanel panel_8;
	private JPanel panel_9;
	private JPanel panel_10;
	private JButton importButton;
	private JPanel panel_11;
	private JTextField xmlField;
	private JPanel panel_12;
	private JLabel lblNewLabel;
	private JPanel panel_13;
	private JButton viewButton;
	
	/**
	 * Create the frame.
	 */
	
	public MainWindow (DefaultTableModel model) 
	{		
		setResizable (false);		
		setTitle ("Unicor OS simulator");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setBounds (100, 100, 766, 482);
		contentPane = new JPanel ();
		contentPane.setBorder (new TitledBorder (null, "Tabla de procesos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane (contentPane);
		contentPane.setLayout (new GridLayout (3, 0, 0, 10));		
		table = new JTable ();				
		table.setFont(new Font ("Dialog", Font.BOLD, 12));
		table.setBorder (new LineBorder(new Color (0, 0, 0)));
		table.setModel (model);
		contentPane.add (table);
		contentPane.add (new JScrollPane (table));
		JPanel panel = new JPanel ();
		contentPane.add (panel);
		panel.setLayout (new GridLayout (2, 1, 0, 0));		
		JPanel panel_1 = new JPanel ();
		panel_1.setBorder (new TitledBorder (null, "Proceso en ejecuci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add (panel_1);
		panel_1.setLayout (new GridLayout (2, 3, 0, 0));		
		JPanel panel_5 = new JPanel ();
		panel_1.add (panel_5);
		panel_5.setLayout (new GridLayout (0, 4, 0, 0));		
		pidLabel = new JLabel ("PID:");
		panel_5.add (pidLabel);		
		nameLabel = new JLabel ("Nombre:");
		panel_5.add (nameLabel);		
		progressLabel = new JLabel ("Progreso:");
		panel_5.add (progressLabel);		
		progressBar = new JProgressBar ();
		panel_5.add (progressBar);		
		JPanel panel_2 = new JPanel ();
		panel_2.setBorder (new TitledBorder (new LineBorder (new Color (184, 207, 229)), "Controles", TitledBorder.LEADING, TitledBorder.TOP, null, new Color (51, 51, 51)));
		panel.add (panel_2);		
		panel_2.setLayout (new GridLayout (1, 0, 100, 0));		
		toggleButton = new JButton ("Iniciar");
		panel_2.add (toggleButton);		
		rebootButton = new JButton ("Reiniciar");
		panel_2.add (rebootButton);		
		createButton = new JButton ("Crear proceso");
		panel_2.add (createButton);		
		JPanel panel_3 = new JPanel ();
		contentPane.add (panel_3);
		panel_3.setLayout (new GridLayout (2, 0, 0, 0));
		JPanel panel_4 = new JPanel ();
		panel_4.setBorder (new TitledBorder (null, "Actividad de copiado y pegado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add (panel_4);
		panel_4.setLayout (new GridLayout (0, 2, 0, 0));
		JPanel panel_6 = new JPanel ();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout ();
		flowLayout.setHgap (10);
		flowLayout.setAlignment (FlowLayout.LEFT);
		panel_4.add (panel_6);
		JLabel lblVelocidad = new JLabel ("Velocidad:");
		panel_6.add (lblVelocidad);
		velocityField = new JTextField ();
		panel_6.add (velocityField);
		velocityField.setColumns (10);
		addVelocityButton = new JButton ("Asignar");
		panel_6.add (addVelocityButton);
		JPanel panel_7 = new JPanel ();
		panel_4.add (panel_7);
		panel_7.setLayout (new GridLayout (0, 2, 0, 0));
		executionsLabel = new JLabel ("Conteo de ejecuciones:");
		panel_7.add (executionsLabel);
		panel_8 = new JPanel ();
		panel_3.add (panel_8);
		panel_8.setLayout (new GridLayout (0, 2, 0, 0));		
		panel_9 = new JPanel ();
		panel_9.setBorder (new TitledBorder (null, "Importar procesos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.add (panel_9);		
		panel_9.setLayout (new GridLayout (0, 3, 0, 50));		
		panel_10 = new JPanel ();
		panel_9.add (panel_10);				
		lblNewLabel = new JLabel ("Documento XML:");
		panel_10.add (lblNewLabel);		
		panel_11 = new JPanel ();
		panel_9.add (panel_11);		
		xmlField = new JTextField ();
		panel_11.add (xmlField);
		xmlField.setColumns (10);		
		panel_12 = new JPanel ();
		panel_9.add (panel_12);
		importButton = new JButton ("Importar");
		panel_12.add (importButton);		
		panel_13 = new JPanel ();
		panel_8.add (panel_13);		
		viewButton = new JButton ("Graficar resultados");
		panel_13.add (viewButton);
		setDefaultLookAndFeelDecorated (true);
	}

	public JTable getTable () 
	{
		return table;
	}
	
	public JTextField getVelocityField () 
	{
		return velocityField;
	}

	public JLabel getPidLabel () 
	{
		return pidLabel;
	}

	public JLabel getNameLabel () 
	{
		return nameLabel;
	}	

	public JLabel getProgressLabel () 
	{
		return progressLabel;
	}

	public JButton getToggleButton () 
	{
		return toggleButton;
	}

	public JButton getRebootButton () 
	{
		return rebootButton;
	}

	public JButton getCreateButton () 
	{
		return createButton;
	}

	public JButton getAddVelocityButton () 
	{
		return addVelocityButton;
	}
	
	public JProgressBar getProgressBar ()
	{
		return progressBar;
	}
	
	public JLabel getExecutionsLabel ()
	{
		return executionsLabel;
	}
	
	public JButton getImportButton ()
	{
		return importButton;
	}
	
	public JTextField getXmlField ()
	{
		return xmlField;
	}
	
	public JButton getViewButton ()
	{
		return viewButton;
	}
}
