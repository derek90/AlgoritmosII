package imples.Test;

import miFramework.annotations.*;

@claseABM(tituloVentanaABM="Alumno")
public class Alumno
{
	@PK
	@campoABM(nombreParaABM="Legajo")
	public String legajo;
	@campoABM(nombreParaABM="Nombre del Alumno", maxLength = 20)
	public String nombre;
	@campoABM(nombreParaABM="Promedio")
	public int promedio;
	public Alumno(){
		
	}
	@Override
	public String toString()
	{
		return "Alumno [legajo="+legajo+", nombre="+nombre+", promedio="+promedio+"]";
	}
	

}
