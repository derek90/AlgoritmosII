package miFramework;

import java.util.Collection;

public interface utilFrameworkABM
{
	public <T> void generarABM(Class<T> claseUsuario);
	public <T> Collection<T> obtenerColeccion(Class<T> claseUsuario);
}