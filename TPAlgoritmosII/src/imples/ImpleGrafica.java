package imples;
import gui.*;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import miFramework.utilFrameworkABM;

public class ImpleGrafica<T> implements utilFrameworkABM <T>
{
	private Ventana_principal ventana_ppal;
	public ArrayList listaElementos;
	public ArrayList listaElementosFiltrada;
	private int nroCamposClaseUsuario;
	private BufferedReader br;
	public Class<T> claseUsuario;
	@SuppressWarnings("unchecked")
	public ImpleGrafica(Class<T> claseUsuario){
		this.claseUsuario = claseUsuario;
		ventana_ppal = new Ventana_principal(this);
		listaElementos = new ArrayList<>();
		listaElementosFiltrada = new ArrayList<>();
		generarCamposVentanaAltaModif();
		ventana_ppal.Show();
	}
	
	public void eliminarElemento(int index, boolean filtrado){
		if (!filtrado){
			listaElementos.remove(index);
		} else {
			Object objetoAeliminarListaFiltrada = listaElementosFiltrada.get(index);
			Field campoPk = ObtenerCampoPK();
			Object valorPkEliminar = null;
			try
			{
				valorPkEliminar = campoPk.get(objetoAeliminarListaFiltrada);
			}
			catch(Exception ex)
			{
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			int indiceAeliminarListaComun = -1;
			int i = 0;
			for (Object o : listaElementos){
				try
				{
					if (campoPk.get(o).toString().equals(valorPkEliminar.toString())){
						indiceAeliminarListaComun = i;
						break;
					}
				}
				catch(IllegalArgumentException|IllegalAccessException ex)
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				i++;
			}
			listaElementos.remove(indiceAeliminarListaComun);
			
			listaElementosFiltrada.remove(index);
		}
	}
	
	public String[] obtenerValoresObjeto(int index, boolean filtrado){
		String[] lista = new String[nroCamposClaseUsuario];
		ArrayList listaLocal = (filtrado ? listaElementosFiltrada : listaElementos);
		Object o = listaLocal.get(index);
		Field[] fields = claseUsuario.getDeclaredFields();
		int i = 0;
		for(Field f: fields){
			try
			{
				lista[i] = f.get(o).toString();
			}
			catch(IllegalArgumentException|IllegalAccessException ex)
			{
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			i++;
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public void generarListaFiltrada(int indiceCampoFiltro, String textoIngresado){
		listaElementosFiltrada.clear();
		Field campoFiltro = claseUsuario.getDeclaredFields()[indiceCampoFiltro];
		for (Object o : listaElementos){
			try
			{
				String valorCampoElementoActual = campoFiltro.get(o).toString();
				if (valorCampoElementoActual.equals(textoIngresado)){
					listaElementosFiltrada.add(o);
				}
			}
			catch(Exception ex)
			{
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	
	private void generarCamposVentanaAltaModif(){
		Field[] fields = claseUsuario.getDeclaredFields();
		nroCamposClaseUsuario = fields.length;
		for(Field f: fields){
			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
			boolean esPK = false;
			if (annotationPK != null){
				esPK = true;
			}
			ventana_ppal.addParLabelTxtBox(annotationCampo.nombreParaABM(),esPK);
		}
	}


	
//	private void obtenerTextoTxtBox(){
//		Field[] fields = claseUsuario.getDeclaredFields();
//		for(Field f: fields){
//			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
//			ventana_ppal.addParLabelTxtBox(annotationCampo.nombreParaABM());
//		}
//
//	}
	
	public String pedidoAltaGUI(List<String> listaTxtBoxes){
		T nuevoObjeto= null;
		String mensajeError = "";
	    try
		{
			nuevoObjeto = claseUsuario.newInstance();
		}
		catch(Exception ex1)
		{
			ex1.printStackTrace();
		}
	    Field[] fields = claseUsuario.getDeclaredFields();
	    Iterator<String> it = listaTxtBoxes.iterator();
		for(Field f: fields){
			String valorTxtBox = it.next().toString();
			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
			if (annotationCampo.required() && (valorTxtBox.length() == 0)){
				mensajeError += "El campo '" + f.getName() + "' es obligatorio. ";
			} else if (valorTxtBox.length() > annotationCampo.maxLength()){
				mensajeError += "El campo '" + f.getName() + "' debe tener como máximo " + annotationCampo.maxLength() + ". ";
			}
			else if (annotationPK != null && ClaveRepetida(valorTxtBox,f,nuevoObjeto)){
				mensajeError += "Ya existe un registro con '"  + f.getName() + "' " + valorTxtBox +".";
			}
			else {
				int valor;
				Class<?> fieldtype = f.getType();
				if (fieldtype.equals(Integer.TYPE)){
					try {
					valor =  Integer.parseInt((valorTxtBox));
					f.set(nuevoObjeto,valor);
					}
					catch(Exception ex)
					{
						mensajeError += "El campo '" + f.getName() + "' debe ser un numero valido. ";
						ex.printStackTrace();
			        }
				} else {
					try {
						f.set(nuevoObjeto,valorTxtBox);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
			        }
				}
			}	
		}//fin for
		if (mensajeError.length() == 0){
			listaElementos.add(nuevoObjeto);
		}
		return mensajeError;
//	    AltaGUI(claseUsuario, nuevoObjeto);
	}
	
	public String pedidoModifGUI(List<String> listaTxtBoxes,int index, boolean filtrado){
		ArrayList listaLocal = (filtrado ? listaElementosFiltrada : listaElementos);
		Object original = listaLocal.get(index);
		//---------------------------------
		Field campoPk = ObtenerCampoPK();
		Object valorPkModificar = null;
		try
		{
			valorPkModificar = campoPk.get(original);
		}
		catch(Exception ex)
		{
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		
		//----------------------
		T nuevoObjeto= null;
		String mensajeError = "";
	    try
		{
			nuevoObjeto = claseUsuario.newInstance();
		}
		catch(Exception ex1)
		{
			ex1.printStackTrace();
		}
	    Field[] fields = claseUsuario.getDeclaredFields();
	    Iterator<String> it = listaTxtBoxes.iterator();
		for(Field f: fields){
			String valorTxtBox = it.next().toString();
			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
			if (annotationCampo.required() && (valorTxtBox.length() == 0)){
				mensajeError += "El campo '" + f.getName() + "' es obligatorio. ";
			} else if (valorTxtBox.length() > annotationCampo.maxLength()){
				mensajeError += "El campo '" + f.getName() + "' debe tener como máximo " + annotationCampo.maxLength() + ". ";
			}
//			else if (annotationPK != null && ClaveRepetida(valorTxtBox,f,nuevoObjeto)){
//				mensajeError += "Ya existe un registro con '"  + f.getName() + "' " + valorTxtBox +".";
//			}
			else {
				int valor;
				Class<?> fieldtype = f.getType();
				if (fieldtype.equals(Integer.TYPE)){
					try {
					valor =  Integer.parseInt((valorTxtBox));
					f.set(nuevoObjeto,valor);
					}
					catch(Exception ex)
					{
						mensajeError += "El campo '" + f.getName() + "' debe ser un numero valido. ";
						ex.printStackTrace();
			        }
				} else {
					try {
						f.set(nuevoObjeto,valorTxtBox);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
			        }
				}
			}	
		}
		
		
		if (mensajeError.length() == 0){
			for(Field f: fields){
				try
				{
					f.set(original,f.get(nuevoObjeto));
				}
				catch(Exception ex)
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
			
			if (filtrado){
				int indiceAModificarListaComun = -1;
				int i = 0;
				for (Object o : listaElementos){
					try
					{
						if (campoPk.get(o).toString().equals(valorPkModificar.toString())){
							indiceAModificarListaComun = i;
							break;
						}
					}
					catch(IllegalArgumentException|IllegalAccessException ex)
					{
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					i++;
				}
				Object objetoModificarListaComun = listaElementos.get(indiceAModificarListaComun);
				for(Field f: fields){
					try
					{
						f.set(objetoModificarListaComun,f.get(nuevoObjeto));
					}
					catch(Exception ex)
					{
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			}
				
			
		}
		return mensajeError;
//	    AltaGUI(claseUsuario, nuevoObjeto);
	}
	
	private <T> void AltaGUI(Class<T> claseUsuario, T nuevoObjeto)
	{
		
//		miFramework.annotations.claseABM annotationClase = claseUsuario.getAnnotation(miFramework.annotations.claseABM.class);
//		System.out.println("Ingrese los datos del nuevo" + annotationClase.tituloVentanaABM());
		
			
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public void ABM(Class<T> claseUsuario){
//		String opt = "";
//		//listaElementos = new ArrayList<>();
//		listaElementos.removeAll(Collections.singleton(null));
//		//br = new BufferedReader(new InputStreamReader(System.in));
//		
//		do{
//			opt = PedirInput("Ingrese 1 para ALTA ,2 para BAJA,3 para MODIFICAR. Ingrese 0 para salir" );
//			switch (opt) {
//	            case "1":
//            		    T nuevoObjeto= null;
//            		    try
//            			{
//            				nuevoObjeto = claseUsuario.newInstance();
//            			}
//            			catch(Exception ex1)
//            			{
//            				ex1.printStackTrace();
//            			}
//            		    listaElementos.add(nuevoObjeto);
//	            		 
//            		     Alta(claseUsuario, nuevoObjeto);
//	                     break;
//	            case "2":  
//	            		 darDeBaja(claseUsuario);
//	                     break;
//	            case "3":  
//	            		 Modificar(claseUsuario);
//                    	 break;
//	            case "0":  
//	            		 System.out.println("Saliendo del proceso ABM");
//		               	 break;
//	            
//	            default: 
//	            		 System.out.println("Opcion invalida.");
//	                     break;
//	        }
//		
//		}while(!opt.equals("0"));
		
	}

	@SuppressWarnings({})
	private <T> void Alta(Class<T> claseUsuario, T nuevoObjeto)
	{
//		miFramework.annotations.claseABM annotationClase = claseUsuario.getAnnotation(miFramework.annotations.claseABM.class);
//		System.out.println("Ingrese los datos del nuevo" + annotationClase.tituloVentanaABM());
//		Field[] fields = claseUsuario.getDeclaredFields();
//		for(Field f: fields){
//			
//			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
//			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
//			if (annotationCampo.required()){
//			Object valor = "";
//			
//			do {
//			valor = PedirInput("Ingrese " + annotationCampo.nombreParaABM() +" . Maximo "+annotationCampo.maxLength()+ " caracteres");
//			}while(
//			( ((String)valor).length()==0 )
//			|| ( ((String)valor).length()> annotationCampo.maxLength() )
//			|| ( (annotationPK != null ? ClaveRepetida(valor,claseUsuario,f,nuevoObjeto): false ) )
//			);
//			
//			Class<?> fieldtype = f.getType();
//			if (fieldtype.equals(Integer.TYPE)){
//				try {
//				valor =  Integer.parseInt(((String)valor));
//				}
//				catch(Exception ex)
//				{
//					ex.printStackTrace();
//		        }
//				
//			} 
//			try {
//				f.set(nuevoObjeto,valor);
//				}
//				catch(Exception ex)
//				{
//					ex.printStackTrace();
//		        }
//			}
//		}
			
	}

	private <T> boolean ClaveRepetida(Object valor,Field f, T nuevoObjeto)
	{
		
		
		for (Object elem : listaElementos ){
			try
			{
				@SuppressWarnings("unused")
				int o = 5;
				if(	(elem!= null) && (nuevoObjeto!=elem) &&	( f.get(elem).toString()) .equals(valor.toString())	){
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
		
		
//		Field campoPK = ObtenerCampoPK(claseUsuario);
//		Object valor = null;
//		valor = PedirInput("Ingrese el valor de la clave '" + campoPK.getName() +"' a eliminar");
//
//		System.out.println("");
//		boolean elementoEliminado = false;
//
//		@SuppressWarnings("rawtypes")
//		Iterator iter = listaElementos.iterator();
//		Object elem = null;
//		while (iter.hasNext()) {
//			elem = iter.next();
//
//		    try
//			{
//				if(	 (elem!= null)	&& ( campoPK.get(elem)) .equals(valor)	){
//					iter.remove();
//					elementoEliminado = true;
//				}
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//		        
//		}
//		if(!elementoEliminado){
//			System.out.println("No existe ningun elemento con ese valor de clave primaria");
//			return;
//		}
//		
//		System.out.println("Eliminado con exito!");
//		return;
	}
	
	

	@SuppressWarnings("unchecked")
	public Collection<T> obtenerColeccion(Class<T> claseUsuario)
	{
		return (Collection<T>)listaElementos;
	}
	
	public <T> void Modificar(Class<T> claseUsuario){
//		Field campoPK = ObtenerCampoPK(claseUsuario);
//		Object valor = null;
//		valor = PedirInput("Ingrese el valor de la clave '" + campoPK.getName() +"' a modificar");
//
//		System.out.println("");
//		
//		boolean elementoEncontrado = false;
//
//		@SuppressWarnings("rawtypes")
//		Iterator iter = listaElementos.iterator();
//		Object elem = null;
//		while ((!elementoEncontrado) && iter.hasNext()  ) {
//			elem = iter.next();
//
//		    try
//			{
//				if(	 (elem!= null)	&& ( campoPK.get(elem)) .equals(valor)	){
//					elementoEncontrado = true;
//				}
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//		        
//		}
//		if(!elementoEncontrado){
//			System.out.println("No existe ningun elemento con ese valor de clave primaria");
//			return;
//		}
//		
//		Field[] fields = claseUsuario.getDeclaredFields();
//		for(Field f: fields){
//			
//			miFramework.annotations.campoABM annotationCampo= f.getAnnotation(miFramework.annotations.campoABM.class);
//			miFramework.annotations.PK annotationPK= f.getAnnotation(miFramework.annotations.PK.class);
//			if (annotationPK == null){
//			valor = "";
//			do {
//			valor = PedirInput("Ingrese el nuevo " + annotationCampo.nombreParaABM() +" . Maximo "+annotationCampo.maxLength()+ " caracteres");
//			}while(
//			( ((String)valor).length()==0 )
//			|| ( ((String)valor).length()> annotationCampo.maxLength() )
//			 );
//			
//			Class<?> fieldtype = f.getType();
//			if (fieldtype.equals(Integer.TYPE)){
//				try {
//				valor =  Integer.parseInt(((String)valor));
//				}
//				catch(Exception ex)
//				{
//					ex.printStackTrace();
//		        }
//				
//			} 
//			try {
//				f.set(elem,valor);
//				}
//				catch(Exception ex)
//				{
//					ex.printStackTrace();
//		        }
//			}
//		}
//		System.out.println("Modificado con exito!");
		
	}
	
	private Field ObtenerCampoPK(){
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
