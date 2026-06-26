package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Logica.Item;
import Logica.Persona;
import Logica.Prestamo;
import Logica.Tipo;
import Controladora.Controladora;

public class Principal {

	private JFrame frame;
	private JTable tablaItems;
	private Controladora controladora;
	private JTable tablaPersonas;
	private JTable tablaCategorias;
	private JTable tablaTipos;
	private JTable table;

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
		controladora = Controladora.cargarDatos();;
		frame = new JFrame();
		frame.setBounds(100, 100, 678, 442);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        controladora.guardarDatos();
		        System.exit(0);
		    }
		});
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
		    dialogCrearItem dialog = new dialogCrearItem(frame, controladora.getTipos(), controladora.getCategorias());
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        String nombre = dialog.getNombre();
		        String descripcion = dialog.getDescripcion();
		        Tipo tipo = (Tipo) dialog.getTipoSeleccionado();

		        controladora.crearItem(nombre, descripcion, tipo);
		        Item itemCreado = controladora.getItems().get(controladora.getItems().size() - 1);
		        controladora.modificarCategoriasItem(itemCreado.getCodigo(), dialog.getCategoriasSeleccionadas(controladora.getCategorias()));
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
		crearTipo.addActionListener(e -> {
		    dialogCrearCategoria dialog = new dialogCrearCategoria(frame);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        controladora.crearTipo(dialog.getNombre());
		        actualizarTablaTipos();
		    }
		});
		
		JButton modificarTipo = new JButton("Modificar Tipo");
		modificarTipo.setBounds(450, 101, 119, 20);
		tipos.add(modificarTipo);
		modificarTipo.addActionListener(e -> {
		    int filaSeleccionada = tablaTipos.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un tipo primero");
		        return;
		    }

		    String nombre = (String) tablaTipos.getValueAt(filaSeleccionada, 0);
		    dialogCrearCategoria dialog = new dialogCrearCategoria(frame);
		    dialog.setNombre(nombre);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        controladora.modificarTipo(nombre, dialog.getNombre());
		        actualizarTablaTipos();
		    }
		});

		
		JButton borrarTipo = new JButton("Borrar Tipo");
		borrarTipo.setBounds(450, 170, 119, 20);
		tipos.add(borrarTipo);
		borrarTipo.addActionListener(e -> {
		    int filaSeleccionada = tablaTipos.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un tipo primero");
		        return;
		    }

		    if (filaSeleccionada == 0) {
		        JOptionPane.showMessageDialog(frame, "El tipo genérico no se puede borrar");
		        return;
		    }

		    String nombre = (String) tablaTipos.getValueAt(filaSeleccionada, 0);
		    Tipo tipo = controladora.consultarTipo(nombre);

		    StringBuilder itemsDelTipo = new StringBuilder();
		    for (Item item : controladora.getItems()) {
		        if (item.getTipo().getNombre().equals(nombre)) {
		            if (itemsDelTipo.length() > 0) itemsDelTipo.append(", ");
		            itemsDelTipo.append(item.getNombre());
		        }
		    }

		    String mensaje = "¿Está seguro que desea borrar el tipo: " + nombre + "?\n";
		    if (itemsDelTipo.length() > 0) {
		        mensaje += "Los siguientes items pasarán al tipo genérico:\n" + itemsDelTipo.toString();
		    } else {
		        mensaje += "No hay items con este tipo.";
		    }

		    int respuesta = JOptionPane.showConfirmDialog(
		        frame,
		        mensaje,
		        "Confirmar borrado",
		        JOptionPane.YES_NO_OPTION
		    );

		    if (respuesta == JOptionPane.YES_OPTION) {
		        controladora.borrarTipo(nombre);
		        actualizarTablaTipos();
		        actualizarTablaItems(); 
		    }
		});
		
		JButton consultarTipo = new JButton("Consultar Tipo");
		consultarTipo.setBounds(450, 243, 119, 20);
		tipos.add(consultarTipo);
		consultarTipo.addActionListener(e -> {
		    int filaSeleccionada = tablaTipos.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un tipo primero");
		        return;
		    }

		    String nombre = (String) tablaTipos.getValueAt(filaSeleccionada, 0);
		    Tipo tipo = controladora.consultarTipo(nombre);

		    dialogConsultarTipo dialog = new dialogConsultarTipo(frame, tipo, controladora.getItems());
		    dialog.setVisible(true);
		});
		
		
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
		ventanaPrestamos.setLayout(null);
		
		JButton hacerPrestamo = new JButton("Hacer un prestamo");
		hacerPrestamo.setBounds(491, 65, 135, 20);
		ventanaPrestamos.add(hacerPrestamo);
		hacerPrestamo.addActionListener(e -> {
		    dialogHacerPrestamo dialog = new dialogHacerPrestamo(frame, controladora.getPersonas(), controladora.getItems());
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        Persona persona = dialog.getPersonaSeleccionada();
		        controladora.crearPrestamo(persona);
		        int idPrestamo = controladora.getConteoPrestamos()-1;
		        for (Item item : dialog.getItemsEnPrestamo()) {
		            try {
		                controladora.agregarItemPrestamo(idPrestamo, item);
		            } catch (Exception ex) {
		                ex.printStackTrace();
		            }
		        }
		        actualizarTablaPrestamos();
		    }
		});
		
		JButton modificarPrestamo = new JButton("Modificar un Prestamo");
		modificarPrestamo.setBounds(491, 144, 135, 20);
		ventanaPrestamos.add(modificarPrestamo);
		modificarPrestamo.addActionListener(e -> {
		    int filaSeleccionada = table.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un préstamo primero");
		        return;
		    }

		    int id = (int) table.getValueAt(filaSeleccionada, 0);
		    Logica.Prestamo prestamo = controladora.getPrestamos().get(id);

		    dialogModificarPrestamo dialog = new dialogModificarPrestamo(frame, prestamo);
		    dialog.setVisible(true);

		    if (dialog.isConfirmado()) {
		        actualizarTablaPrestamos();
		        actualizarTablaItems();
		    }
		});
		
		JButton terminarPrestamo = new JButton("Terminar un Prestamo");
		terminarPrestamo.setBounds(491, 234, 135, 20);
		ventanaPrestamos.add(terminarPrestamo);
		terminarPrestamo.addActionListener(e -> {
		    int filaSeleccionada = table.getSelectedRow();

		    if (filaSeleccionada == -1) {
		        JOptionPane.showMessageDialog(frame, "Seleccione un préstamo primero");
		        return;
		    }

		    int id = (int) table.getValueAt(filaSeleccionada, 0);
		    Logica.Prestamo prestamo = controladora.getPrestamos().get(id);

		    int respuesta = JOptionPane.showConfirmDialog(
		        frame,
		        "¿Está seguro que desea terminar el préstamo ID: " + id + " de " + prestamo.getPersona().getNombre() + "?",
		        "Confirmar",
		        JOptionPane.YES_NO_OPTION
		    );

		    if (respuesta == JOptionPane.YES_OPTION) {

		        try {
		            controladora.terminarPrestamo(id);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		        actualizarTablaPrestamos();
		        actualizarTablaItems();
		    }
		});
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 10, 450, 358);
		ventanaPrestamos.add(scrollPane_4);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Persona", "Items", "Alerta"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_4.setViewportView(table);
		
		JPanel ventanaReportes = new JPanel();
		tabbedPane.addTab("Reportes", null, ventanaReportes, null);
		ventanaReportes.setLayout(null);
		
		JButton reporteUsuarios = new JButton("Generar Reporte Por Usuario");
		reporteUsuarios.setBounds(42, 34, 249, 120);
		ventanaReportes.add(reporteUsuarios);
		reporteUsuarios.addActionListener(e -> {
		    List<Persona> personasOrdenadas = new ArrayList<>(controladora.getPersonas());
		    personasOrdenadas.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));

		    String[] columnas = { "Nombre", "Teléfono", "Email", "ID Préstamos" };
		    Object[][] datos = new Object[personasOrdenadas.size()][4];

		    for (int i = 0; i < personasOrdenadas.size(); i++) {
		        Persona persona = personasOrdenadas.get(i);

		        StringBuilder ids = new StringBuilder();
		        for (Prestamo p : persona.getPrestamos()) {
		            if (ids.length() > 0) ids.append(", ");
		            ids.append(p.getID());
		        }

		        datos[i][0] = persona.getNombre();
		        datos[i][1] = persona.getTelefono();
		        datos[i][2] = persona.getEmail();
		        datos[i][3] = ids.length() > 0 ? ids.toString() : "Sin préstamos";
		    }

		    dialogReportes dialog = new dialogReportes(frame, "Reporte por Usuario", columnas, datos);
		    dialog.setVisible(true);
		});
		
		JButton reporteItem = new JButton("Generar Reporte Por Item");
		reporteItem.setBounds(379, 34, 249, 120);
		ventanaReportes.add(reporteItem);
		reporteItem.addActionListener(e -> {
		    List<Item> itemsOrdenados = new ArrayList<>(controladora.getItems());
		    itemsOrdenados.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));

		    String[] columnas = { "Nombre", "Descripción", "Tipo", "Categorías", "Préstamo" };
		    Object[][] datos = new Object[itemsOrdenados.size()][5];

		    for (int i = 0; i < itemsOrdenados.size(); i++) {
		        Item item = itemsOrdenados.get(i);

		        StringBuilder categoriasReporte = new StringBuilder();
		        for (Logica.Categoria c : item.getCategorias()) {
		            if (categoriasReporte.length() > 0) categoriasReporte.append(", ");
		            categoriasReporte.append(c.getNombre());
		        }

		        datos[i][0] = item.getNombre();
		        datos[i][1] = item.getDescripcion();
		        datos[i][2] = item.getTipo().getNombre();
		        datos[i][3] = categoriasReporte.length() > 0 ? categoriasReporte.toString() : "Sin categorías";
		        datos[i][4] = item.Prestado() ? "Préstamo ID: " + item.getPrestamo().getID() : "Disponible";
		    }

		    dialogReportes dialog = new dialogReportes(frame, "Reporte por Item", columnas, datos);
		    dialog.setVisible(true);
		});
		
		JButton reporteCategoria = new JButton("Generar Reporte Por Categoria");
		reporteCategoria.setBounds(42, 229, 249, 114);
		ventanaReportes.add(reporteCategoria);
		reporteCategoria.addActionListener(e -> {
		    List<Logica.Categoria> categoriasOrdenadas = new ArrayList<>(controladora.getCategorias());
		    categoriasOrdenadas.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));

		    String[] columnas = { "Categoría", "Items" };
		    Object[][] datos = new Object[categoriasOrdenadas.size()][2];

		    for (int i = 0; i < categoriasOrdenadas.size(); i++) {
		        Logica.Categoria categoria = categoriasOrdenadas.get(i);

		        StringBuilder itemsNombres = new StringBuilder();
		        for (Item item : categoria.getItems()) {
		            if (itemsNombres.length() > 0) itemsNombres.append(", ");
		            itemsNombres.append(item.getNombre());
		        }

		        datos[i][0] = categoria.getNombre();
		        datos[i][1] = itemsNombres.length() > 0 ? itemsNombres.toString() : "Sin items";
		    }

		    dialogReportes dialog = new dialogReportes(frame, "Reporte por Categoría", columnas, datos);
		    dialog.setVisible(true);
		});
		
		JButton reporteTipo = new JButton("Generar Reporte Por Tipo");
		reporteTipo.setBounds(379, 229, 249, 114);
		ventanaReportes.add(reporteTipo);
		reporteTipo.addActionListener(e -> {
		    List<Tipo> tiposOrdenados = new ArrayList<>(controladora.getTipos());
		    tiposOrdenados.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));

		    String[] columnas = { "Tipo", "Items" };
		    Object[][] datos = new Object[tiposOrdenados.size()][2];

		    for (int i = 0; i < tiposOrdenados.size(); i++) {
		        Tipo tipo = tiposOrdenados.get(i);

		        StringBuilder itemsNombres = new StringBuilder();
		        for (Item item : controladora.getItems()) {
		            if (item.getTipo() == tipo) {
		                if (itemsNombres.length() > 0) itemsNombres.append(", ");
		                itemsNombres.append(item.getNombre());
		            }
		        }

		        datos[i][0] = tipo.getNombre();
		        datos[i][1] = itemsNombres.length() > 0 ? itemsNombres.toString() : "Sin items";
		    }

		    dialogReportes dialog = new dialogReportes(frame, "Reporte por Tipo", columnas, datos);
		    dialog.setVisible(true);
		});
		
		
		actualizarTablaItems();
		actualizarTablaPersonas();
		actualizarTablaCategorias();
		actualizarTablaTipos();
		actualizarTablaPrestamos();
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
	private void actualizarTablaTipos() {
	    DefaultTableModel modelo = (DefaultTableModel) tablaTipos.getModel();
	    modelo.setRowCount(0);

	    for (Tipo tipo : controladora.getTipos()) {
	        modelo.addRow(new Object[] { tipo.getNombre() });
	    }
	}
	private void actualizarTablaPrestamos() {
	    DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	    modelo.setRowCount(0);

	    for (Prestamo prestamo : controladora.getPrestamos().values()) {
	        StringBuilder itemsNombres = new StringBuilder();
	        for (Item item : prestamo.getItems()) {
	            if (itemsNombres.length() > 0) itemsNombres.append(", ");
	            itemsNombres.append(item.getNombre());
	        }
	        modelo.addRow(new Object[] {
	            prestamo.getID(),
	            prestamo.getPersona().getNombre(),
	            itemsNombres.toString(),
	            "" // alerta por ahora vacía
	        });
	    }
	}
}
