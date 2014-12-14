package gui;

import javax.swing.*;



public class ParLabelTxtBox
{
	public JLabel lblNombreCampo;
	public boolean esPK;
	public JTextField txtInputCampo = new JTextField();
	public ParLabelTxtBox(String lblNombre, boolean esPrimaryKey){
		
		lblNombreCampo = new JLabel(lblNombre + ":");
		txtInputCampo = new JTextField();
		esPK = esPrimaryKey;
	}
}
