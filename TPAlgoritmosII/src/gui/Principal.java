package gui;

import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Principal extends JFrame {
	public Principal(){
		
		JFrame frame = new JFrame("ABM");
		frame.setSize(600, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
		
		//Botones
		JButton altaBoton = new JButton("Alta");
		altaBoton.setBounds(10, 10, 100, 25);
		panel.add(altaBoton);
		
		JButton bajaBoton = new JButton("Baja");
		bajaBoton.setBounds(10, 40, 100, 25);
		panel.add(bajaBoton);
		
		JButton modificarBoton = new JButton("Modificar");
		modificarBoton.setBounds(10, 70, 100, 25);
		panel.add(modificarBoton);
		
		frame.setVisible(true);
		
	}
}
