package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Logica.Item;
import Logica.Prestamo;

public class dialogModificarPrestamo extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTable tablaItems;
    private Prestamo prestamo;
    private List<Item> itemsOriginales; // copia del estado original
    private boolean confirmado = false;

    public static void main(String[] args) {
        try {
            dialogModificarPrestamo dialog = new dialogModificarPrestamo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public dialogModificarPrestamo() {
        this(null, null);
    }

    public dialogModificarPrestamo(JFrame parent, Prestamo prestamo) {
        super(parent, "Modificar Préstamo", true);
        this.prestamo = prestamo;
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Guardar copia del estado original
        if (prestamo != null) {
            itemsOriginales = new ArrayList<>(prestamo.getItems());
        }

        // ID
        JLabel lblID = new JLabel("ID:");
        lblID.setBounds(10, 10, 40, 20);
        contentPanel.add(lblID);

        JLabel valorID = new JLabel();
        valorID.setBounds(60, 10, 200, 20);
        contentPanel.add(valorID);

        // Persona
        JLabel lblPersona = new JLabel("Persona:");
        lblPersona.setBounds(10, 40, 60, 20);
        contentPanel.add(lblPersona);

        JLabel valorPersona = new JLabel();
        valorPersona.setBounds(80, 40, 200, 20);
        contentPanel.add(valorPersona);

        // Tabla items
        JLabel lblItems = new JLabel("Items en préstamo:");
        lblItems.setBounds(10, 75, 120, 20);
        contentPanel.add(lblItems);

        tablaItems = new JTable();
        tablaItems.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nombre" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 100, 300, 200);
        scrollPane.setViewportView(tablaItems);
        contentPanel.add(scrollPane);

        JButton btnRetornar = new JButton("Retornar Item");
        btnRetornar.setBounds(320, 100, 100, 30);
        contentPanel.add(btnRetornar);

        if (prestamo != null) {
            valorID.setText(String.valueOf(prestamo.getID()));
            valorPersona.setText(prestamo.getPersona().getNombre());
            actualizarTabla();
        }

        btnRetornar.addActionListener(e -> {
            int fila = tablaItems.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un item primero");
                return;
            }
            Item item = prestamo.getItems().get(fila);
            try {
                prestamo.retornarItem(item);
                actualizarTabla();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

        okButton.addActionListener(e -> {
            confirmado = true;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            // Restaurar estado original
            try {
                // Retornar todos los items actuales
                List<Item> itemsActuales = new ArrayList<>(prestamo.getItems());
                for (Item item : itemsActuales) {
                    prestamo.retornarItem(item);
                }
                // Volver a agregar los originales
                for (Item item : itemsOriginales) {
                    prestamo.agregarItem(item);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dispose();
        });
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaItems.getModel();
        modelo.setRowCount(0);
        for (Item item : prestamo.getItems()) {
            modelo.addRow(new Object[] { item.getNombre() });
        }
    }

    public boolean isConfirmado() { return confirmado; }
}