package imples.Test;

import gui.Principal;
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
		Principal ventanaPrincipal = new Principal();
		System.out.println("Prueba del framework");
		Collection<Alumno> miLista = null;
		utilFrameworkABM utilFW = new Imple1();
		utilFW.ABM(Alumno.class);
		miLista = utilFW.obtenerColeccion(Alumno.class);
		for (Alumno a:miLista){
			System.out.println(a);
		}
		System.out.println("Vuelvo a llamar al ABM a ver si anda bien..");
		utilFW.ABM(Alumno.class);
		miLista = utilFW.obtenerColeccion(Alumno.class);
		
		for (Alumno a:miLista){
			System.out.println(a);
		}

	}

}
