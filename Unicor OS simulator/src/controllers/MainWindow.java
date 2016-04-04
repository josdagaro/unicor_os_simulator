package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import libraries.Global;
import models.Activity;
import models.FileSelector;
import models.Manager;

public class MainWindow implements ActionListener
{
	private views.MainWindow mainWindow;
	private Manager manager;
	
	public MainWindow ()
	{
		this.mainWindow = new views.MainWindow (getTableModel ());
		manager = null;
		initForm ();
	}
	
	private enum mainWindowComponents
	{
		toggleButton, rebootButton, createButton, addVelocityButton, importButton
	}
	
	private void initForm ()
	{		
		mainWindow.setLocationRelativeTo (null);
		mainWindow.setVisible (true);
		mainWindow.getToggleButton ().setEnabled (false);
		mainWindow.getCreateButton ().setEnabled (false);
		mainWindow.getXmlField ().setEnabled (false);
		mainWindow.getToggleButton ().setActionCommand ("toggleButton");
		mainWindow.getRebootButton ().setActionCommand ("rebootButton");
		mainWindow.getCreateButton ().setActionCommand ("createButton");
		mainWindow.getAddVelocityButton ().setActionCommand ("addVelocityButton");
		mainWindow.getImportButton ().setActionCommand ("importButton");
		mainWindow.getToggleButton ().addActionListener (this);
		mainWindow.getRebootButton ().addActionListener (this);
		mainWindow.getCreateButton ().addActionListener (this);
		mainWindow.getAddVelocityButton ().addActionListener (this);
		mainWindow.getImportButton ().addActionListener (this);
	}

	@Override
	public void actionPerformed (ActionEvent event) 
	{
		Global global = null;		

		// TODO Auto-generated method stub
		switch (mainWindowComponents.valueOf (event.getActionCommand ()))
		{
			case toggleButton:
								
				if (mainWindow.getToggleButton ().getText ().equals ("Iniciar"))
				{																
					if (!manager.isSuspended () && manager.getExecution () == null)
					{					
						mainWindow.getToggleButton ().setText ("Detener");
						
						final SwingWorker <?, ?> worker = new SwingWorker <Object, Object> () 
						{								
							@Override
							protected Object doInBackground () throws Exception 
							{
								processesRun ();
								return null;
							}
						};
						
						worker.execute ();
					}	
					else
					{		
						System.out.println ("Se reanuda el manager");
						mainWindow.getToggleButton ().setText ("Detener");
						manager.resumeIt ();
						try {
							modifyStateInTable (manager.getExecution ().getPid (), "Ejecución", 0);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (manager.isCompleted ())
					{
						JOptionPane.showMessageDialog (null, "Todo el procesamiento ha finalizado, no hay nada que iniciar");
					}
				}
				else if (manager.isRunning ())
				{	
					mainWindow.getToggleButton ().setText ("Iniciar");	
					
					final SwingWorker <?, ?> worker = new SwingWorker <Object, Object> () 
					{								
						@Override
						protected Object doInBackground () throws Exception 
						{
							System.out.println ("Se activa stopTrue para la tarea");
							manager.getExecution ().getActivity ().getTask ().stopTrue ();	
							modifyStateInTable (manager.getExecution ().getPid (), "Detenido", 0);
							return null;
						}
					};
					
					worker.execute ();						
				}							
					
				break;
			
			case rebootButton:
				
				mainWindow.dispose ();
				mainWindow = new views.MainWindow (getTableModel ());
				manager = null;
				initForm ();				
				break;
				
			case createButton:
				
				new Form (manager, mainWindow);		
				mainWindow.setEnabled (false);
				break;
				
			case addVelocityButton:
				
				global = new Global ();
				
				if (mainWindow.getXmlField ().getText () == null || mainWindow.getXmlField ().getText ().equals ("")) 
				{
					if (!mainWindow.getVelocityField ().getText ().equals ("") && global.isNumeric (mainWindow.getVelocityField ().getText ()))
					{
						if (manager == null)
						{
							manager = new Manager (Integer.parseInt (mainWindow.getVelocityField ().getText ()));					
						}
						
						mainWindow.getVelocityField ().setEnabled (false);
						mainWindow.getCreateButton ().setEnabled (true);
					}
					else
					{
						JOptionPane.showMessageDialog 
						(
							null, "La velocidad especificada no es valida\n Ingrese una velocidad válida o importe un archivo XML de estructura válida"
						);
					}
				}
				else
				{
					try 
					{
						importData ();
						mainWindow.getVelocityField ().setEnabled (false);
						mainWindow.getCreateButton ().setEnabled (true);
						mainWindow.getToggleButton ().setEnabled (true);
					} 
					catch (SAXException | IOException | ParserConfigurationException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				break;
				
			case importButton:
				
				chooseFile (mainWindow.getXmlField ());				
				break;
		}
	}
	
	private DefaultTableModel getTableModel ()
	{
		String [] header = {"PID", "Nombre", "T. ráfaga", "Quantum", "Estado", "T. llegada", "Ejecuciones"};
		
		DefaultTableModel model = new DefaultTableModel (new Object [][] {}, header)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			Class <?> [] columnTypes = new Class []
			{
				Integer.class, String.class, Float.class, Float.class, String.class, Integer.class, Integer.class
			};
			
			public Class <?> getColumnClass (int columnIndex)
			{
				return columnTypes [columnIndex];
			}
			
			boolean [] columnEditables = new boolean []
			{
				false, false, false, false, false, false, false
			};
			
			public boolean isCellEditable (int row, int column)
			{
				return columnEditables [column];
			}
		};
		
		return model;
	}
	
	private void processesRun () throws IOException, InterruptedException
	{	
		System.out.println ("Despachador inicia");	
		manager.start ();
		
		while (!manager.isCompleted ())
		{										
			synchronized (manager)
			{
				System.out.println ("1) Se detiene el controlador de la ventana principal");
				manager.suspendIt ();
				System.out.println ("Se actualiza estado del proceso en la tabla");
				mainWindow.getPidLabel ().setText ("PID: " + String.valueOf (manager.getExecution ().getPid ()));
				mainWindow.getNameLabel ().setText ("Nombre: " + manager.getExecution ().getName ());
				mainWindow.getExecutionsLabel ().setText ("Conteo de ejecuciones: " + manager.getExecution ().getExecutions ());
				
				modifyStateInTable 
				(
					manager.getExecution ().getPid (), "Ejecución", manager.getExecution ().getArrivalTime ()				
				);
			}
			
			while (true)
			{				
				if (manager.isSuspended ())
				{										
					if (!manager.getExecution ().getActivity ().getTask ().isSuspended ())
					{
						System.out.println ("Se inicia nueva tarea");							
						manager.getExecution ().executeActivity (manager.getVelocity (), mainWindow);	
					}
					else
					{
						System.out.println ("Se resume tarea");
						System.out.println ("Se actualiza estado del proceso en la tabla");
						manager.getExecution ().getActivity ().getTask ().resumeIt ();						
						modifyStateInTable (manager.getExecution ().getPid (), "Ejecución", 0);
					}
														
					System.out.println ("4) Se reanuda el despachador");
					manager.resumeIt ();
					
					while (true)
					{
						if (manager.isSuspended ())
						{
							System.out.println ("Se actualiza estado del proceso en la tabla");
							
							if (manager.getExecution ().getActivity ().getTask ().isSuspended ())
							{			
								System.out.println ("Detenido");
								modifyStateInTable (manager.getExecution ().getPid (), "Detenido", 0);
							}
							else if (manager.getExecution ().getActivity ().getTask ().isCompleted ())
							{						
								System.out.println ("Terminado");
								modifyStateInTable (manager.getExecution ().getPid (), "Terminado", 0);
							}							
							
							System.out.println ("6) Se reanuda el despachador");
							manager.resumeIt ();
							break;
						}
						
						Thread.sleep (1);
					}
					
					break;
				}
			}							
		}
		
		System.out.println ("Fin del controlador main window");
		mainWindow.getToggleButton ().setText ("Iniciar");
		mainWindow.getToggleButton ().setEnabled (false);
	}
	
	private void modifyStateInTable (int pid, String state, int arrivalTime) throws InterruptedException
	{
		int size = mainWindow.getTable ().getRowCount ();
		
		for (int i = 0; i < size; i ++)
		{
			if (pid == (int) mainWindow.getTable ().getValueAt (i, 0))
			{				
				mainWindow.getTable ().setValueAt (state, i, 4);
				
				if (state.equals ("Ejecución"))
				{
					mainWindow.getTable ().getCellRenderer(i, 4).getTableCellRendererComponent 
					(
						mainWindow.getTable (), state, false, false, i, 4
					).setBackground (Color.cyan);
				}
				else if (state.equals ("Detenido"))
				{
					mainWindow.getTable ().getCellRenderer(i, 4).getTableCellRendererComponent 
					(
						mainWindow.getTable (), state, false, false, i, 4
					).setBackground (Color.red);
				}
				else if (state.equals ("Terminado"))
				{
					mainWindow.getTable ().getCellRenderer(i, 4).getTableCellRendererComponent 
					(
						mainWindow.getTable (), state, false, false, i, 4
					).setBackground (Color.green);
				}
				
				if (arrivalTime != 0)
				{
					mainWindow.getTable ().setValueAt (arrivalTime, i, 5);
				}
				
				Thread.sleep (100);
				break;
			}
		}
	}
	
	private void importData () throws SAXException, IOException, ParserConfigurationException //función para importar documento XML
	{	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
		DocumentBuilder builder = factory.newDocumentBuilder ();
		Document document = null;
		NodeList managerList = null;
		models.Process process = null;
		Object[] object = null;
		int size = 0, length = 0, count = 0, longitude = 0,pid = 0;
		String name = null, rootPath = null, destinationPath = null;
		float quantum = 0;
		Activity activity = null;
		document = builder.parse (mainWindow.getXmlField ().getText ());
		managerList = document.getElementsByTagName ("Manager");
		size = managerList.getLength ();
		
		for (int i = 0; i < size; i ++)
		{
			Node managerNode = managerList.item (i);
			
			if (managerNode.getNodeType () == Node.ELEMENT_NODE)
			{
				Element managerElement = (Element) managerNode;												
				NodeList managerChildrenList = managerElement.getChildNodes ();
				length = managerChildrenList.getLength ();
				
				for (int j = 0; j < length; j ++)
				{
					Node managerObject = managerChildrenList.item (j);
					
					if (managerObject.getNodeType () == Node.ELEMENT_NODE)
					{
						Element managerObjectElement = (Element) managerObject;
						
						switch (managerObjectElement.getTagName ())
						{
							case "velocity":
								
								System.out.println ("Velocidad: " + managerObjectElement.getTextContent ());
								manager = new Manager (Integer.parseInt (managerObjectElement.getTextContent ()));
								mainWindow.getVelocityField ().setText (managerObjectElement.getTextContent ());
								mainWindow.getVelocityField ().setEnabled (false);
								break;
								
							case "Process":

								Element processElement = (Element) managerObjectElement;
								NodeList processChildrenList = processElement.getChildNodes ();
								count = processChildrenList.getLength ();
								
								for (int k = 0; k < count; k ++)
								{
									Node processObject = processChildrenList.item (k);

									if (processObject.getNodeType () == Node.ELEMENT_NODE)
									{
										Element processObjectElement = (Element) processObject;
										
										switch (processObjectElement.getTagName ())
										{
											case "pid":
															
												pid = Integer.parseInt (processObjectElement.getTextContent ());
												break;
												
											case "name":
												
												name = processObjectElement.getTextContent ();
												break;
												
											case "quantum":
												
												quantum = Float.parseFloat (processObjectElement.getTextContent ());
												break;
												
											case "Activity":
												
												Element activityElement = (Element) processObjectElement;
												NodeList activityChildrenList = activityElement.getChildNodes ();
												longitude = activityChildrenList.getLength ();
												
												for (int l = 0; l < longitude; l ++)
												{
													Node activityObject = activityChildrenList.item (l);
													
													if (activityObject.getNodeType () == Node.ELEMENT_NODE)
													{
														Element activityObjectElement = (Element) activityObject;
														
														switch (activityObjectElement.getTagName ())
														{
															case "rootPath":
																
																rootPath = activityObjectElement.getTextContent ();
																break;
																
															case "destinationPath":
																
																destinationPath = activityObjectElement.getTextContent ();
																break;
														}
													}
												}
												
												activity = new Activity (rootPath, destinationPath);												
												break;
										}																												
									}
								}
								
								System.out.println ("Proceso:\nPID: " + pid + "\nNombre: " + name + "\nQuantum: " + quantum);
								System.out.println ("Actividad:\nArchivo fuente: " + rootPath + "\nArchivo destino: " + destinationPath);																
								process = new models.Process (pid, name, quantum, activity);
								
								object = new Object [] 
								{
									process.getPid (), process.getName (), process.getRafagaTime (manager.getVelocity ()) / 1000,
									process.getQuantum (), "Listo", 0, process.getTotalExecutions (manager.getVelocity ())
								};

								((DefaultTableModel) mainWindow.getTable ().getModel ()).addRow (object);
								manager.getReadyQueue ().add (process);
								System.out.println ("Proceso cola\npid:" + manager.getReadyQueue ().peek ().getPid () + "\nName: " + manager.getReadyQueue ().peek ().getName()
										+ "\nquantum: " + manager.getReadyQueue ().peek ().getQuantum () + "\n");
								break;
						}
					}
				}							
			}
		}
	}
	
	public void chooseFile (JTextField textField) //se ejecuta una ventana para seleccionar un archivo
	{
		FileSelector fileSelector = new FileSelector ();
		String path = fileSelector.getFilePath ();
		
		if (path != null && !path.equals (""))
		{
			textField.setText (path);
		}
	}
}
