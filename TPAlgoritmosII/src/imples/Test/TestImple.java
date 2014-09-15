package imples.Test;

import imples.Imple1;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;

import miFramework.utilFrameworkABM;

@SuppressWarnings("unused")
public class TestImple
{

	/**
	 * @param args
	 */

	public static void main(String[] args)
	{
		System.out.println("Prueba del framework");
		utilFrameworkABM utilFW = new Imple1();
		utilFW.generarABM(Alumno.class);
		Collection<Alumno> miLista = utilFW.obtenerColeccion(Alumno.class);
		for (Alumno a:miLista){
			System.out.println(a);
		}

	}

}
