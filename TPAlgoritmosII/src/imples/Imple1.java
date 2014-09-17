package imples;


import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import miFramework.utilFrameworkABM;

public class Imple1<T> implements utilFrameworkABM <T>
{
	private ArrayList listaElementos;
	List<String> opciones = Arrays.asList("1", "2", "3");
	private BufferedReader br;
	public Imple1(){
		listaElementos = new ArrayList<>();
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	private String PedirInput(String mensaje  ){
		System.out.println(mensaje);
		String input = "";
		try
		{
			input = br.readLine();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return input;
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public void ABM(Class<T> claseUsuario){
		String opt = "";
		//listaElementos = new ArrayList<>();
		listaElementos.removeAll(Collections.singleton(null));
		//br = new BufferedReader(new InputStreamReader(System.in));
		
		do{
			opt = PedirInput("Ingrese 1 para ALTA ,2 para BAJA,3 para MODIFICAR. Ingrese 0 para salir" );
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
	            		 
            		     Alta(claseUsuario, nuevoObjeto);
	                     break;
	            case "2":  
	            		 darDeBaja(claseUsuario);
	                     break;
	            case "3":  
	            		 Modificar(claseUsuario);
                    	 break;
	            case "0":  
	            		 System.out.println("Saliendo del proceso ABM");
		               	 break;
	            
	            default: 
	            		 System.out.println("Opcion invalida.");
	                     break;
	        }
		
		}while(!opt.equals("0"));
		
	}

	@SuppressWarnings({})
	private <T> void Alta(Class<T> claseUsuario, T nuevoObjeto)
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
			valor = PedirInput("Ingrese " + annotationCampo.nombreParaABM() +" . Maximo "+annotationCampo.maxLength()+ " caracteres");
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
		
		
		Field campoPK = ObtenerCampoPK(claseUsuario);
		Object valor = null;
		valor = PedirInput("Ingrese el valor de la clave '" + campoPK.getName() +"' a eliminar");

		System.out.println("");
		boolean elementoEliminado = false;

		@SuppressWarnings("rawtypes")
		Iterator iter = listaElementos.iterator();
		Object elem = null;
		while (iter.hasNext()) {
			elem = iter.next();

		    try
			{
				if(	 (elem!= null)	&& ( campoPK.get(elem)) .equals(valor)	){
					iter.remove();
					elementoEliminado = true;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		        
		}
		if(!elementoEliminado){
			System.out.println("No existe ningun elemento con ese valor de clave primaria");
			return;
		}
		
		System.out.println("Eliminado con exito!");
		return;
	}
	
	

	@SuppressWarnings("unchecked")
	public Collection<T> obtenerColeccion(Class<T> claseUsuario)
	{
		return (Collection<T>)listaElementos;
	}
	
	public <T> void Modificar(Class<T> claseUsuario){
		Field campoPK = ObtenerCampoPK(claseUsuario);
		Object valor = null;
		valor = PedirInput("Ingrese el valor de la clave '" + campoPK.getName() +"' a modificar");

		System.out.println("");
		
		boolean elementoEncontrado = false;

		@SuppressWarnings("rawtypes")
		Iterator iter = listaElementos.iterator();
		Object elem = null;
		while ((!elementoEncontrado) && iter.hasNext()  ) {
			elem = iter.next();

		    try
			{
				if(	 (elem!= null)	&& ( campoPK.get(elem)) .equals(valor)	){
					elementoEncontrado = true;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		        
		}
		if(!elementoEncontrado){
			System.out.println("No existe ningun elemento con ese valor de clave primaria");
			return;
		}
		
		Field[] fields = claseUsuario.getDeclaredFields();
		for(Field f: fields){
			
			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
			if (annotationPK == null){
			valor = "";
			do {
			valor = PedirInput("Ingrese el nuevo " + annotationCampo.nombreParaABM() +" . Maximo "+annotationCampo.maxLength()+ " caracteres");
			}while(
			( ((String)valor).length()==0 )
			|| ( ((String)valor).length()> annotationCampo.maxLength() )
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
				f.set(elem,valor);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
		        }
			}
		}
		System.out.println("Modificado con exito!");
		
	}
	
	private Field ObtenerCampoPK(Class<?> claseUsuario){
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
		}
		return campoPK;
	}

	



}
