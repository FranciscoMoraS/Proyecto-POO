package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Logica.Item;
import Logica.Persona;
import Logica.Tipo;
import Controladora.Controladora;

public class Principal {

	private JFrame frame;
	private JTable tablaItems;
	private Controladora controladora;
	private JTable tablaPersonas;
	private JTable tablaCategorias;
	private JTable tablaTipos;

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
		controladora= new Controladora();
		//controladora.crearTipo("Inicial");
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
		
		tablaItems = new JTable();
		tablaItems.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"codigo", "Nombre"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tablaItems.getColumnModel().getColumn(0).setPreferredWidth(74);
		scrollPane.setViewportView(tablaItems);
		
		JButton crearItem = new JButton("Crear Item");
		crearItem.setBounds(461, 46, 122, 20);
		items.add(crearItem);
		crearItem.addActionListener(e -> {
		    List tipos = controladora.getTipos(); // obtienes la lista del controlador
		    dialogCrearItem dialog = new dialogCrearItem(frame, tipos);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        String nombre = dialog.getNombre();
		        String descripcion = dialog.getDescripcion();
		        Tipo tipo = (Tipo) dialog.getTipoSeleccionado();
		        
		        controladora.crearItem(nombre, descripcion, tipo);
		        actualizarTablaItems();
		    }
		});
		
		
		JButton modificarItem = new JButton("Modificar Item");
		modificarItem.setBounds(461, 105, 122, 20);
		items.add(modificarItem);
		modificarItem.addActionListener(e -> {
		    int filaSeleccionada = tablaItems.getSelectedRow();
		    
		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un item primero");
		        return;
		    }
		    
		    int codigo = (int) tablaItems.getValueAt(filaSeleccionada, 0);
		    Item item = controladora.consultarItem(codigo);
		    
		    dialogModificarItem dialog = new dialogModificarItem(frame, item, controladora.getTipos(), controladora.getCategorias());
		    dialog.setVisible(true);
		    
		    if (dialog.isConfirmado()) {
		        controladora.modificarItem(dialog.getNombre(), item.getCodigo(), dialog.getDescripcion(), dialog.getTipoSeleccionado());
		        controladora.modificarCategoriasItem(item.getCodigo(), dialog.getCategoriasSeleccionadas(controladora.getCategorias()));
		        actualizarTablaItems();
		    }
		});
		
		JButton borrarItem = new JButton("Borrar Item");
		borrarItem.setBounds(461, 171, 122, 20);
		items.add(borrarItem);
		borrarItem.addActionListener(e -> {
		    int filaSeleccionada = tablaItems.getSelectedRow();
		    
		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un item primero");
		        return;
		    }
		    
		    int codigo = (int) tablaItems.getValueAt(filaSeleccionada, 0);
		    Item item = controladora.consultarItem(codigo);
		    
		    if (item.Prestado()) {
		        JOptionPane.showMessageDialog(frame, "No se puede borrar un item que está prestado");
		        return;
		    }
		    
		    int respuesta = JOptionPane.showConfirmDialog(
		        frame,
		        "¿Está seguro que desea borrar el item: " + item.getNombre() + "?",
		        "Confirmar borrado",
		        JOptionPane.YES_NO_OPTION
		    );
		    
		    if (respuesta == JOptionPane.YES_OPTION) {
		        controladora.borrarItem(codigo);
		        actualizarTablaItems();
		    }
		});
		
		JButton consultarItem = new JButton("Consultar Item");
		consultarItem.setBounds(461, 239, 122, 20);
		items.add(consultarItem);
		consultarItem.addActionListener(e -> {
		    int filaSeleccionada = tablaItems.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un item primero");
		        return;
		    }

		    int codigo = (int) tablaItems.getValueAt(filaSeleccionada, 0);
		    Item item = controladora.consultarItem(codigo);

		    dialogConsultarItem dialog = new dialogConsultarItem(frame, item);
		    dialog.setVisible(true);
		});
		
		JPanel personas = new JPanel();
		contenidoAdministracion.add(personas, "ventanaPersonas");
		personas.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 394, 296);
		personas.add(scrollPane_1);
		
		tablaPersonas = new JTable();
		tablaPersonas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Telefono", "Email"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(tablaPersonas);
		
		JButton agregarPersona = new JButton("Agregar Persona");
		agregarPersona.setBounds(461, 38, 123, 20);
		personas.add(agregarPersona);
		agregarPersona.addActionListener(e -> {
		    dialogCrearPersona dialog = new dialogCrearPersona(frame);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        controladora.crearPersona(dialog.getNombre(), dialog.getTelefono(), dialog.getEmail());
		        actualizarTablaPersonas();
		    }
		});
		
		JButton modificarPersona = new JButton("Modificar persona");
		modificarPersona.setBounds(461, 95, 123, 20);
		personas.add(modificarPersona);
		modificarPersona.addActionListener(e -> {
		    int filaSeleccionada = tablaPersonas.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione una persona primero");
		        return;
		    }

		    String nombre = (String) tablaPersonas.getValueAt(filaSeleccionada, 0);
		    Persona persona = controladora.consultarPersona(nombre);

		    dialogModificarPersona dialog = new dialogModificarPersona(frame, persona);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        controladora.modificarPersona(nombre, dialog.getNombre(), dialog.getTelefono(), dialog.getEmail());
		        actualizarTablaPersonas();
		    }
		    
		});
		
		JButton borrarPersona = new JButton("Borrar Persona");
		borrarPersona.setBounds(461, 163, 123, 20);
		personas.add(borrarPersona);
		borrarPersona.addActionListener(e -> {
		    int filaSeleccionada = tablaPersonas.getSelectedRow();
		    
		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione una Persona primero");
		        return;
		    }
		    
		    String nombre = (String) tablaPersonas.getValueAt(filaSeleccionada, 0);
		    Persona persona = controladora.consultarPersona(nombre);
		    
		    if (persona.tienePrestamos()) {
		        JOptionPane.showMessageDialog(frame, "No se puede borrar una Persona con prestamos");
		        return;
		    }
		    
		    int respuesta = JOptionPane.showConfirmDialog(
		        frame,
		        "¿Está seguro que desea borrar la Persona: " + persona.getNombre() + "?",
		        "Confirmar borrado",
		        JOptionPane.YES_NO_OPTION
		    );
		    
		    if (respuesta == JOptionPane.YES_OPTION) {
		        controladora.borrarPersona(nombre);
		        actualizarTablaPersonas();
		    }
		});
		
		JButton consultarPersona = new JButton("Consultar Persona");
		consultarPersona.setBounds(461, 241, 123, 20);
		personas.add(consultarPersona);
		consultarPersona.addActionListener(e -> {
		    int filaSeleccionada = tablaPersonas.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione una persona primero");
		        return;
		    }

		    String nombre = (String) tablaPersonas.getValueAt(filaSeleccionada, 0);
		    Persona persona = controladora.consultarPersona(nombre);

		    dialogConsultarPersona dialog = new dialogConsultarPersona(frame, persona);
		    dialog.setVisible(true);
		});
		
		JPanel categorias = new JPanel();
		contenidoAdministracion.add(categorias, "ventanaCategorias");
		categorias.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 10, 405, 296);
		categorias.add(scrollPane_2);
		
		tablaCategorias = new JTable();
		tablaCategorias.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_2.setViewportView(tablaCategorias);
		
		JButton crearCategoria = new JButton("Crear Categoria");
		crearCategoria.setBounds(465, 41, 125, 20);
		categorias.add(crearCategoria);
		crearCategoria.addActionListener(e -> {
		    dialogCrearCategoria dialog = new dialogCrearCategoria(frame);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        controladora.crearCategoria(dialog.getNombre());
		        actualizarTablaCategorias();
		    }
		});
		
		JButton modificarCategoria = new JButton("modificarCategoria");
		modificarCategoria.setBounds(465, 102, 125, 20);
		categorias.add(modificarCategoria);
		modificarCategoria.addActionListener(e -> {
		    int filaSeleccionada = tablaCategorias.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione una categoría primero");
		        return;
		    }

		    String nombre = (String) tablaCategorias.getValueAt(filaSeleccionada, 0);
		    dialogCrearCategoria dialog = new dialogCrearCategoria(frame);
		    dialog.setNombre(nombre);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        controladora.modificarCategoria(nombre, dialog.getNombre());
		        actualizarTablaCategorias();
		    }
		});
		
		JButton borrarCategoria = new JButton("Borrar Categoria");
		borrarCategoria.setBounds(465, 160, 125, 20);
		categorias.add(borrarCategoria);
		borrarCategoria.addActionListener(e -> {
		    int filaSeleccionada = tablaCategorias.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione una categoría primero");
		        return;
		    }

		    String nombre = (String) tablaCategorias.getValueAt(filaSeleccionada, 0);

		    int respuesta = JOptionPane.showConfirmDialog(
		        frame,
		        "¿Está seguro que desea borrar la categoría: " + nombre + "?",
		        "Confirmar borrado",
		        JOptionPane.YES_NO_OPTION
		    );

		    if (respuesta == JOptionPane.YES_OPTION) {
		        controladora.borrarCategoria(nombre);
		        actualizarTablaCategorias();
		    }
		});
		
		JButton consultarCategoria = new JButton("Consultar Categoria");
		consultarCategoria.setBounds(465, 221, 125, 20);
		categorias.add(consultarCategoria);
		consultarCategoria.addActionListener(e -> {
		    int filaSeleccionada = tablaCategorias.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione una categoría primero");
		        return;
		    }

		    String nombre = (String) tablaCategorias.getValueAt(filaSeleccionada, 0);
		    Logica.Categoria categoria = controladora.consultarCategoria(nombre);

		    dialogConsultarCategoria dialog = new dialogConsultarCategoria(frame, categoria);
		    dialog.setVisible(true);
		});
		
		
		JPanel tipos = new JPanel();
		contenidoAdministracion.add(tipos, "ventanaTipos");
		tipos.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 380, 296);
		tipos.add(scrollPane_3);
		
		tablaTipos = new JTable();
		tablaTipos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_3.setViewportView(tablaTipos);
		
		JButton crearTipo = new JButton("Crear Tipo");
		crearTipo.setBounds(450, 38, 119, 20);
		tipos.add(crearTipo);
		
		JButton modificarTipo = new JButton("Modificar Tipo");
		modificarTipo.setBounds(450, 101, 119, 20);
		tipos.add(modificarTipo);
		
		JButton borrarTipo = new JButton("Borrar Tipo");
		borrarTipo.setBounds(450, 170, 119, 20);
		tipos.add(borrarTipo);
		
		JButton consultarTipo = new JButton("Consultar Tipo");
		consultarTipo.setBounds(450, 243, 119, 20);
		tipos.add(consultarTipo);
		
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
	private void actualizarTablaItems() {
	    DefaultTableModel modelo = (DefaultTableModel) tablaItems.getModel();
	    modelo.setRowCount(0);
	    
	    for (Item item : controladora.getItems()) {
	        modelo.addRow(new Object[] {
	            item.getCodigo(),
	            item.getNombre()
	        });
	    }
	}
	private void actualizarTablaPersonas() {
	    DefaultTableModel modelo = (DefaultTableModel) tablaPersonas.getModel();
	    modelo.setRowCount(0);

	    for (Persona persona : controladora.getPersonas()) {
	        modelo.addRow(new Object[] {
	            persona.getNombre(),
	            persona.getTelefono(),
	            persona.getEmail()
	        });
	    }
	}
	private void actualizarTablaCategorias() {
	    DefaultTableModel modelo = (DefaultTableModel) tablaCategorias.getModel();
	    modelo.setRowCount(0);

	    for (Logica.Categoria categoria : controladora.getCategorias()) {
	        modelo.addRow(new Object[] { categoria.getNombre() });
	    }
	}
}
