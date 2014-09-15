package imples;


import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import miFramework.utilFrameworkABM;

public class Imple1 implements utilFrameworkABM
{
	private ArrayList listaElementos;
	List<String> opciones = Arrays.asList("1", "2", "3");
	private BufferedReader br;
	public Imple1(){
			
	}


	@SuppressWarnings({"unchecked"})
	@Override
	public <T> void generarABM(Class<T> claseUsuario){
		String opt = "";
		listaElementos = new ArrayList<>();
		do{
			System.out.println("Opciones: 1 ,2 ,3. Ingrese 0 para salir");
			br = new BufferedReader(new InputStreamReader(System.in));
			try
			{
				opt = br.readLine();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}

			switch (opt) {
	            case "1":
            		    T nuevoObjeto= null;
            		    try
            			{
            				nuevoObjeto = claseUsuario.newInstance();
            			}
            			catch(Exception ex1)
            			{
            				ex1.printStackTrace();
            			}
            		    listaElementos.add(nuevoObjeto);
	            		 
	            		 instanciarNuevoObjeto(claseUsuario, nuevoObjeto);
	                     break;
	            case "2":  
	            		 darDeBaja(claseUsuario);
	                     break;
	            case "3":  //MODIFICACION
                    break;
	            
	            default: 
	            		 System.out.println("Opcion invalida. Saliendo del proceso ABM");
	                     break;
	        }
		
		}while(opciones.contains(opt));
		
	}

	@SuppressWarnings({})
	private <T> void instanciarNuevoObjeto(Class<T> claseUsuario, T nuevoObjeto)
	{
		
		
		
		miFramework.annotations.claseABM annotationClase = claseUsuario.getAnnotation(miFramework.annotations.claseABM.class);
		System.out.println("Ingrese los datos del nuevo" + annotationClase.tituloVentanaABM());
		Field[] fields = claseUsuario.getDeclaredFields();
		for(Field f: fields){
			
			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
			if (annotationCampo.required()){
			Object valor = "";
			do {
			System.out.println("Ingrese " + annotationCampo.nombreParaABM() +" . Maximo "+annotationCampo.maxLength()+ " caracteres");
			try
			{
				valor = br.readLine();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
			}while(
			( ((String)valor).length()==0 )
			|| ( ((String)valor).length()> annotationCampo.maxLength() )
			|| ( (annotationPK != null ? ClaveRepetida(valor,claseUsuario,f,nuevoObjeto): false ) )
			);
			Class<?> fieldtype = f.getType();
			if (fieldtype.equals(Integer.TYPE)){
				try {
				valor =  Integer.parseInt(((String)valor));
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
		        }
				
			} 
			try {
				f.set(nuevoObjeto,valor);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
		        }
			}
		}
			
	}

	private <T> boolean ClaveRepetida(Object valor,Class<T> claseUsuario, Field f, T nuevoObjeto)
	{
		
		
		for (Object elem : listaElementos ){
			try
			{
				@SuppressWarnings("unused")
				int o = 5;
				if(	(elem!= null) && (nuevoObjeto!=elem) &&	( f.get(elem)) .equals(valor)	){
					return true;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return false;
	}
	
	private void darDeBaja(Class<?> claseUsuario){
		
		Field[] fields = claseUsuario.getDeclaredFields();
		Field campoPK = null;
		for(Field f: fields){
			
			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
			if (annotationPK != null){
				campoPK = f;
				break;
			}
		}
		if (campoPK==null){
			System.out.println("No existe campo PK en esta clase!!");
			return;
		}
		
		System.out.print("Ingrese el valor de la clave '" + campoPK.getName() +"' a eliminar");
		Object valor = null;
		try
		{
			valor=br.readLine();
		}
		catch(IOException ex1)
		{
			// TODO Auto-generated catch block
			ex1.printStackTrace();
		}
		System.out.println("");
		boolean elementoEliminado = false;
		for (Object elem : listaElementos ){
			try
			{
				if(		( campoPK.get(elem)) .equals(valor)	){
					listaElementos.remove(elem);
					elementoEliminado = true;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(!elementoEliminado)
			System.out.println("No existe ningun elemento con ese valor de clave primaria");
		
		return;
	}
	

	@SuppressWarnings("unchecked")
	public <T> Collection<T> obtenerColeccion(Class<T> claseUsuario)
	{
		return (Collection<T>)listaElementos;
	}



}
