package models;

import java.util.List;
import java.util.Queue;

public class Manager 
{
	private short velocity; //tiempo de espera para cada thread en milisegundos para copiar cada caracter
	private Queue readyQueue; //cola de procesos listos
	private Queue stopQueue; //cola de procesos detenidos
	private List listOfCompleted; //lista de procesos terminados
}
