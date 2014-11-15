package imples.Test;

import imples.Imple1;

import java.awt.EventQueue;
import java.io.*;
import java.util.Collection;
import java.util.LinkedList;

import GUI.Principal;
import miFramework.utilFrameworkABM;

@SuppressWarnings("unused")
public class TestImple
{

	/**
	 * @param args
	 */

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				Principal ventanaPrincipal = new Principal();
				ventanaPrincipal.setVisible(true);
			}
		});
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
