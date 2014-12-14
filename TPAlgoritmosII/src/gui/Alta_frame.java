package gui;

import imples.ImpleGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Alta_frame<T> extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public List<ParLabelTxtBox> listaLabelsTxtsBoxes;
	private ImpleGrafica<T> objetoImple;
	private JLabel lblError;
	private int indexModificando;
	private boolean filtrado;
	
	public void addParLabelTxtBox(String lblNombreCampo, boolean esPK, boolean editable){
		ParLabelTxtBox par = new ParLabelTxtBox(lblNombreCampo,esPK,editable);
		listaLabelsTxtsBoxes.add(par);
    }
	public Alta_frame(ImpleGrafica<T> objetoImplementacion) {
		filtrado = false;
		objetoImple = objetoImplementacion;
		listaLabelsTxtsBoxes = new LinkedList<ParLabelTxtBox>();
		lblError = new JLabel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
	}
	
	public void reAbrir(int indexModif, boolean filt){
		indexModificando = indexModif;
		filtrado = filt;
		this.setVisible(true);
	}
	public void setTxtBoxesModif(String[] valoresTxtBox){
		int i = 0;
		for (ParLabelTxtBox par : listaLabelsTxtsBoxes){
			par.txtInputCampo.setText(valoresTxtBox[i]);
			if(par.esPK || (!par.editable)){
				par.txtInputCampo.disable();
			}
			i++;
	    }
	}
	
	public void inicializar() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		int i = 0;
		for (ParLabelTxtBox par : listaLabelsTxtsBoxes){
			par.lblNombreCampo.setBounds(10, 8 + i, 150, 14);
			contentPane.add(par.lblNombreCampo);
			par.txtInputCampo.setBounds(167, 8 + i, 86, 20);
			par.txtInputCampo.setColumns(10);
			par.txtInputCampo.setText("");
			contentPane.add(par.txtInputCampo);
			i+=25;
	    }
		lblError.setText("");
		lblError.setBounds(10, 227, 700, 400);
		contentPane.add(lblError);
//		JLabel lblNombre = new JLabel("Nombre:");
//		lblNombre.setBounds(10, 11, 72, 14);
//		contentPane.add(lblNombre);
//		
//		JLabel lblLegajo = new JLabel("Legajo:");
//		lblLegajo.setBounds(10, 36, 57, 14);
//		contentPane.add(lblLegajo);
//		
//		JLabel lblPromedio = new JLabel("Promedio:");
//		lblPromedio.setBounds(10, 63, 72, 14);
//		contentPane.add(lblPromedio);
//		
//		textField = new JTextField();
//		textField.setBounds(77, 8, 86, 20);
//		contentPane.add(textField);
//		textField.setColumns(10);
//		
//		textField_1 = new JTextField();
//		textField_1.setColumns(10);
//		textField_1.setBounds(77, 33, 86, 20);
//		contentPane.add(textField_1);
//		
//		textField_2 = new JTextField();
//		textField_2.setColumns(10);
//		textField_2.setBounds(77, 60, 86, 20);
//		contentPane.add(textField_2);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(321, 500, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(222, 500, 89, 23);
		contentPane.add(btnCancelar);
		final Alta_frame self = this;
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> listaTxtBoxes = new LinkedList<String>();
				for (ParLabelTxtBox par : listaLabelsTxtsBoxes){
					String valorTxtBox = new String(par.txtInputCampo.getText());
					listaTxtBoxes.add(valorTxtBox);
			    }
				String msjError;
				if (indexModificando == -1){
					msjError = objetoImple.pedidoAltaGUI(listaTxtBoxes);
				} else {
					msjError = objetoImple.pedidoModifGUI(listaTxtBoxes,indexModificando,filtrado);
				}
				if (msjError.length() != 0){
					lblError.setText(msjError);
				} else {
					self.setVisible(false);
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				self.setVisible(false);
			}
		});
	}
}
