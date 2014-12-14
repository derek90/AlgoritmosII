package gui;
import imples.ImpleGrafica;

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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class Ventana_principal<T> extends JFrame  {

	private static BufferedReader br;
	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable table;
	private boolean hayUnaVentanaAbierta;
	private ImpleGrafica<T> objetoImple;
	private Alta_frame ventanaAltaModif;
	private DefaultTableModel model;
	private int indexModificando;
	private boolean filtrado;
	/**
	 * Launch the application.
	 */
	
	
//	public static void main(String[] args) {
//		
//		listaElementos = new ArrayList<>();
//		br = new BufferedReader(new InputStreamReader(System.in));
//		listaElementos.removeAll(Collections.singleton(null));
//		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Ventana_principal frame = new Ventana_principal(null);
////					frame.addParLabelTxtBox("campo1");
////					frame.addParLabelTxtBox("campo2");
////					frame.addParLabelTxtBox("campo3");
//					frame.Show();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public void addParLabelTxtBox(String lblNombreCampo, boolean espK){
		ventanaAltaModif.addParLabelTxtBox(lblNombreCampo, espK);
    }
	
	private String[] getListaLabels(){
		int i = 0;
		int cantidadCampos = ventanaAltaModif.listaLabelsTxtsBoxes.size();
		String[] lista = new String[cantidadCampos];
		Iterator<ParLabelTxtBox> it = ventanaAltaModif.listaLabelsTxtsBoxes.iterator();
		for (i =0; i<cantidadCampos;i++){
			ParLabelTxtBox par = it.next();
			lista[i] = par.lblNombreCampo.getText();
	    }
		
		return lista;
		
}
	public void getData(){
		model.setRowCount(0);
		ArrayList listaa = (filtrado ? objetoImple.listaElementosFiltrada:objetoImple.listaElementos);
		int nroFilas = listaa.size();
		int nroColumnas = objetoImple.claseUsuario.getDeclaredFields().length;
		Iterator it = listaa.iterator();
		int j = 0;
		while (it.hasNext()){
			String[] registro = new String[nroColumnas];
			Object o = it.next();
			Field[] fields = objetoImple.claseUsuario.getDeclaredFields();
			for (Field f: fields){
				String objj = null;
				try
				{
					objj = f.get(o).toString();
				}
				catch(Exception ex)
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				registro[j] = objj;
				j++;
			}
			model.addRow(registro);
			j=0;
		 }
	}
	
	@SuppressWarnings("serial")
	public Ventana_principal(ImpleGrafica<T> objetoImplementacion) {
		objetoImple = objetoImplementacion;
		ventanaAltaModif = new Alta_frame(objetoImple);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		indexModificando = -1;
		filtrado = false;
		
	}
	
	private void refreshear(){
		//this.Show();
		this.getData();
	}
	
	public void Show() {	
		JButton btnAlta = new JButton("Alta");
		hayUnaVentanaAbierta = false;
		
		//action listener Alta
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!hayUnaVentanaAbierta){
					try {
						hayUnaVentanaAbierta = true;
						ventanaAltaModif.inicializar();
						ventanaAltaModif.reAbrir(-1,filtrado);
//						frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						ventanaAltaModif.addWindowListener( new WindowAdapter() {
		                    @Override
		                    public void windowClosing(WindowEvent we) {
		                    	hayUnaVentanaAbierta = false;
		                    }
		                } );
						ventanaAltaModif.addComponentListener ( new ComponentAdapter ()
					    {
					        public void componentShown ( ComponentEvent e )
					        {
					            System.out.println ( "Component shown" );
					        }

					        public void componentHidden ( ComponentEvent e )
					        {
					        	hayUnaVentanaAbierta = false;
					        	LinkedList listaLtB = (LinkedList)ventanaAltaModif.listaLabelsTxtsBoxes;
					        	for (Object par : listaLtB){
					        		((ParLabelTxtBox)par).txtInputCampo.enable();
					        	}
					        	refreshear();
					        }
					    } );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
			}
		});
		//action listener baja
		final Ventana_principal self = this;
		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(table.getSelectedRow() != -1){
					objetoImple.eliminarElemento(table.getSelectedRow(),filtrado);
					self.getData();
				}
			}
		});
		
		
		
		//action listener modificar
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((!hayUnaVentanaAbierta) && (table.getSelectedRow() != -1)){
					try {
						indexModificando = table.getSelectedRow();
						hayUnaVentanaAbierta = true;
						ventanaAltaModif.inicializar();
						ventanaAltaModif.reAbrir(indexModificando,filtrado);
						ventanaAltaModif.setTxtBoxesModif(objetoImple.obtenerValoresObjeto(table.getSelectedRow(),filtrado));
//						frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						ventanaAltaModif.addWindowListener( new WindowAdapter() {
		                    @Override
		                    public void windowClosing(WindowEvent we) {
		                    	hayUnaVentanaAbierta = false;
		                    	
		                    }
		                } );
						ventanaAltaModif.addComponentListener ( new ComponentAdapter ()
					    {
					        public void componentShown ( ComponentEvent e )
					        {
					            System.out.println ( "Component shown" );
					        }

					        public void componentHidden ( ComponentEvent e )
					        {
					        	hayUnaVentanaAbierta = false;
					        	refreshear();
					        	indexModificando = -1;
					        }
					    } );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
			}
		});
		
		
		
		JLabel lblFiltrarPor = new JLabel("Filtrar por:");
		
		JLabel lblBuscar = new JLabel("Buscar:");
		
		txtBuscar = new JTextField();
		txtBuscar.setColumns(10);
		final JComboBox comboBoxFiltro = new JComboBox();
		comboBoxFiltro.setModel(new DefaultComboBoxModel(this.getListaLabels()));
		//evento press key en el campo buscar
		txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String ingresado = txtBuscar.getText();
                int indiceSeleccionado = comboBoxFiltro.getSelectedIndex();
                if (ingresado.length() != 0){
                	objetoImple.generarListaFiltrada(indiceSeleccionado, ingresado);
                	filtrado = true;
                } else {
                	filtrado = false;
                }
                refreshear();
            }
 
            public void keyTyped(KeyEvent e) {
                // TODO: Do something for the keyTyped event
            }
 
            public void keyPressed(KeyEvent e) {
                // TODO: Do something for the keyPressed event
            }
        });
		
		
		
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
							.addComponent(comboBoxFiltro, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblBuscar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtBuscar, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)))
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
						.addComponent(txtBuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFiltrarPor)
						.addComponent(comboBoxFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		model = new DefaultTableModel(getListaLabels(),0){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		this.setVisible(true);		
	}
}
