package miFramework;

@ABM(onAdd="procesaAdd",onDelete="procesarDelete")
public class Alumno2
{
	@Campo(obligatorio=true, regex="", validMtd="validaLegajo")
	public String legajo;

	public String nombre;
	public String email;

	@Campo(obligatorio=true, regex="", validMtd="", tipoCampo="ComboBox", populate="obtenerNacionalidades", campoId="idNac", campoDesc="desc")
	public String nacionalidad;
	
	
	public Collection<?> obtenerNacionalidades()
	{
		
	}
	
	public void procesarAdd()
	
	
	
	
	public String validaLegajo()
	{
		return null;
	}
}
