package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JScrollPane;

public class Principal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 678, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 664, 405);
		frame.getContentPane().add(tabbedPane);
		
		JPanel ventanaAdministracion = new JPanel();
		tabbedPane.addTab("Administracion", null, ventanaAdministracion, null);
		ventanaAdministracion.setLayout(null);
		
		CardLayout cardLayoutAdministracion = new CardLayout(0,0);
		JPanel contenidoAdministracion = new JPanel();
		contenidoAdministracion.setBounds(10, 52, 639, 316);
		ventanaAdministracion.add(contenidoAdministracion);
		contenidoAdministracion.setLayout(cardLayoutAdministracion);
		
		
		JPanel items = new JPanel();
		contenidoAdministracion.add(items, "ventanaItems");
		items.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 392, 316);
		items.add(scrollPane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(476, 46, 84, 20);
		items.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(476, 105, 84, 20);
		items.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(476, 174, 84, 20);
		items.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(476, 239, 84, 20);
		items.add(btnNewButton_3);
		JPanel personas = new JPanel();
		contenidoAdministracion.add(personas, "ventanaPersonas");
		JPanel categorias = new JPanel();
		contenidoAdministracion.add(categorias, "ventanaCategorias");
		JPanel tipos = new JPanel();
		contenidoAdministracion.add(tipos, "ventanaTipos");
		
		JButton mostrarVentanaItems = new JButton("Items");
		mostrarVentanaItems.setBounds(32, 20, 84, 20);
		ventanaAdministracion.add(mostrarVentanaItems);
		mostrarVentanaItems.addActionListener(e -> cardLayoutAdministracion.show(contenidoAdministracion, "ventanaItems"));
		
		
		JButton mostrarVentanaPersonas = new JButton("Personas");
		mostrarVentanaPersonas.setBounds(197, 22, 84, 20);
		ventanaAdministracion.add(mostrarVentanaPersonas);
		mostrarVentanaPersonas.addActionListener(e->cardLayoutAdministracion.show(contenidoAdministracion, "ventanaPersonas"));
		
		JButton mostrarVentanaCategorias = new JButton("Categorias");
		mostrarVentanaCategorias.setBounds(390, 20, 84, 20);
		ventanaAdministracion.add(mostrarVentanaCategorias);
		mostrarVentanaCategorias.addActionListener(e->cardLayoutAdministracion.show(contenidoAdministracion, "ventanaCategorias"));
		
		JButton mostrarVentanaTipos = new JButton("Tipos");
		mostrarVentanaTipos.setBounds(534, 20, 84, 20);
		ventanaAdministracion.add(mostrarVentanaTipos);
		mostrarVentanaTipos.addActionListener(e->cardLayoutAdministracion.show(contenidoAdministracion, "ventanaTipos"));
		JPanel ventanaPrestamos = new JPanel();
		tabbedPane.addTab("Prestamos", null, ventanaPrestamos, null);
		
		JPanel ventanaReportes = new JPanel();
		tabbedPane.addTab("Reportes", null, ventanaReportes, null);
		ventanaReportes.setLayout(null);
	}
}
