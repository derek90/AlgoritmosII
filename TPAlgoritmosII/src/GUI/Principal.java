package GUI;

import javax.swing.JFrame;

public class Principal extends JFrame {
	public Principal(){
		//t�tulo de la ventana
		setTitle("ABM");
		//tama�o de la ventana
		setSize(800, 600);
		//Ubico la ventana en el centro de la pantalla
		setLocationRelativeTo(null);
		//Al hacer click en el bot�n de cerrar, se cierra la ventana 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
