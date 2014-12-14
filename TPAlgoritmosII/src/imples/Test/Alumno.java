package imples.Test;

import miFramework.annotations.*;


public class Alumno
{
	@PK
	@campoABM(nombreParaABM="Legajo")
	public String legajo;
	@campoABM(nombreParaABM="Nombre del Alumno", maxLength = 20)
	public String nombre;
	@campoABM(nombreParaABM="Promedio")
	public int promedio;
	@campoABM(nombreParaABM="fecha Nac", maxLength = 20, editable = false, validador="validarFechaNacimiento")
	public String fechaNacimiento;
	
	public Alumno(){
		
	}
	@Override
	public String toString()
	{
		return "Alumno [legajo="+legajo+", nombre="+nombre+", promedio="+promedio+"]";
	}
	
	public static String validarFechaNacimiento(String fecha){
		if (fecha.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")){
			return "";
		} else {
			return " Formato de fecha invalida. ";
		}
	}
	

}
