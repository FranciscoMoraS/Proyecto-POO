package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Logica.Item;
import Logica.Persona;

public class dialogHacerPrestamo extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JComboBox<Persona> comboPersona;
    private JTable tablaDisponibles;
    private JTable tablaEnPrestamo;
    private List<Item> itemsDisponibles;
    private List<Item> itemsEnPrestamo;
    private boolean confirmado = false;

    public static void main(String[] args) {
        try {
            dialogHacerPrestamo dialog = new dialogHacerPrestamo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public dialogHacerPrestamo() {
        this(null, null, null);
    }

    public dialogHacerPrestamo(JFrame parent, List<Persona> personas, List<Item> items) {
        super(parent, "Hacer Préstamo", true);
        setBounds(100, 100, 650, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        itemsDisponibles = new ArrayList<>();
        itemsEnPrestamo = new ArrayList<>();

        if (items != null) {
            for (Item item : items) {
                if (!item.Prestado()) {
                    itemsDisponibles.add(item);
                }
            }
        }

        JLabel lblPersona = new JLabel("Persona:");
        lblPersona.setBounds(10, 10, 60, 20);
        contentPanel.add(lblPersona);

        comboPersona = new JComboBox<>();
        if (personas != null) {
            for (Persona p : personas) {
                comboPersona.addItem(p);
            }
        }
        comboPersona.setBounds(80, 10, 200, 20);
        contentPanel.add(comboPersona);

        JLabel lblDisponibles = new JLabel("Disponibles:");
        lblDisponibles.setBounds(10, 40, 100, 20);
        contentPanel.add(lblDisponibles);

        tablaDisponibles = new JTable();
        tablaDisponibles.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nombre" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane scrollDisponibles = new JScrollPane();
        scrollDisponibles.setBounds(10, 60, 220, 240);
        scrollDisponibles.setViewportView(tablaDisponibles);
        contentPanel.add(scrollDisponibles);

        JButton btnAgregar = new JButton("→");
        btnAgregar.setBounds(245, 130, 50, 30);
        contentPanel.add(btnAgregar);

        JButton btnQuitar = new JButton("←");
        btnQuitar.setBounds(245, 170, 50, 30);
        contentPanel.add(btnQuitar);

        JLabel lblEnPrestamo = new JLabel("En el préstamo:");
        lblEnPrestamo.setBounds(310, 40, 100, 20);
        contentPanel.add(lblEnPrestamo);

        tablaEnPrestamo = new JTable();
        tablaEnPrestamo.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nombre" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane scrollEnPrestamo = new JScrollPane();
        scrollEnPrestamo.setBounds(310, 60, 220, 240);
        scrollEnPrestamo.setViewportView(tablaEnPrestamo);
        contentPanel.add(scrollEnPrestamo);

        actualizarTablaDisponibles();

        btnAgregar.addActionListener(e -> {
            int fila = tablaDisponibles.getSelectedRow();
            if (fila == -1) return;

            Item item = itemsDisponibles.get(fila);
            itemsDisponibles.remove(fila);
            itemsEnPrestamo.add(item);
            actualizarTablaDisponibles();
            actualizarTablaEnPrestamo();
        });

        btnQuitar.addActionListener(e -> {
            int fila = tablaEnPrestamo.getSelectedRow();
            if (fila == -1) return;

            Item item = itemsEnPrestamo.get(fila);
            itemsEnPrestamo.remove(fila);
            itemsDisponibles.add(item);
            actualizarTablaDisponibles();
            actualizarTablaEnPrestamo();
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        okButton.addActionListener(e -> { confirmado = true; dispose(); });
        cancelButton.addActionListener(e -> dispose());
    }

    private void actualizarTablaDisponibles() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDisponibles.getModel();
        modelo.setRowCount(0);
        for (Item item : itemsDisponibles) {
            modelo.addRow(new Object[] { item.getNombre() });
        }
    }

    private void actualizarTablaEnPrestamo() {
        DefaultTableModel modelo = (DefaultTableModel) tablaEnPrestamo.getModel();
        modelo.setRowCount(0);
        for (Item item : itemsEnPrestamo) {
            modelo.addRow(new Object[] { item.getNombre() });
        }
    }

    public Persona getPersonaSeleccionada() { return (Persona) comboPersona.getSelectedItem(); }
    public List<Item> getItemsEnPrestamo() { return itemsEnPrestamo; }
    public boolean isConfirmado() { return confirmado; }
}