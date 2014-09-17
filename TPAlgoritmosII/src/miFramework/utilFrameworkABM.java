package miFramework;

import java.util.Collection;

public interface utilFrameworkABM <T>
{
	public void ABM(Class<T> claseUsuario);
	public Collection<T> obtenerColeccion(Class<T> claseUsuario);
}
