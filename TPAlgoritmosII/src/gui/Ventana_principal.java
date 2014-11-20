package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;


public class Ventana_principal extends JFrame  {

	private static ArrayList listaElementos;
	private static BufferedReader br;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private boolean hayUnaVentanaAbierta;

	/**
	 * Launch the application.
	 */

	
	public static void main(String[] args) {
		
		listaElementos = new ArrayList<>();
		br = new BufferedReader(new InputStreamReader(System.in));
		listaElementos.removeAll(Collections.singleton(null));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana_principal frame = new Ventana_principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	

	
	@SuppressWarnings("serial")
	public Ventana_principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAlta = new JButton("Alta");
		hayUnaVentanaAbierta = false;
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!hayUnaVentanaAbierta){
					try {
						hayUnaVentanaAbierta = true;
						Alta_frame frame = new Alta_frame();
						frame.setVisible(true);
//						frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						frame.addWindowListener( new WindowAdapter() {
		                    @Override
		                    public void windowClosing(WindowEvent we) {
		                    	hayUnaVentanaAbierta = false;
		                    }
		                } );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
			}
		});
		
		JButton btnBaja = new JButton("Baja");
		
		JButton btnModificar = new JButton("Modificar");
		
		JLabel lblFiltrarPor = new JLabel("Filtrar por:");
		
		JLabel lblBuscar = new JLabel("Buscar:");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		//Ésta lista está hardcodeada, habría que hacer que cargue los campos que el usuario cree (puede ser cualquier cantidad y llamarse como al usuario se le antoje)
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Legajo", "Nombre", "Promedio"}));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBar(null);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnModificar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnBaja, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAlta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFiltrarPor)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblBuscar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnAlta)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBaja)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnModificar))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 527, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBuscar)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFiltrarPor)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1234567", "Derek", "10"},
			},
			new String[] {
				"Legajo", "Nombre", "Promedio"
			}
		)
		);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
}
