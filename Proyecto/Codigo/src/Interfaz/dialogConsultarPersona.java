package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
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
import Logica.Prestamo;

public class dialogConsultarPersona extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    public static void main(String[] args) {
        try {
            dialogConsultarPersona dialog = new dialogConsultarPersona();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public dialogConsultarPersona() {
        this(null, null);
    }

    public dialogConsultarPersona(JFrame parent, Persona persona) {
        super(parent, "Consultar Persona", true);
        setBounds(100, 100, 500, 450);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 20);
        contentPanel.add(lblNombre);

        JLabel valorNombre = new JLabel();
        valorNombre.setBounds(110, 20, 200, 20);
        contentPanel.add(valorNombre);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 50, 80, 20);
        contentPanel.add(lblTelefono);

        JLabel valorTelefono = new JLabel();
        valorTelefono.setBounds(110, 50, 200, 20);
        contentPanel.add(valorTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 80, 80, 20);
        contentPanel.add(lblEmail);

        JLabel valorEmail = new JLabel();
        valorEmail.setBounds(110, 80, 200, 20);
        contentPanel.add(valorEmail);

        JLabel lblPrestamos = new JLabel("Préstamos:");
        lblPrestamos.setBounds(20, 120, 80, 20);
        contentPanel.add(lblPrestamos);

        JTable tablaPrestamos = new JTable();
        tablaPrestamos.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID", "Items" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(350);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 145, 440, 200);
        scrollPane.setViewportView(tablaPrestamos);
        contentPanel.add(scrollPane);

        if (persona != null) {
            valorNombre.setText(persona.getNombre());
            valorTelefono.setText(persona.getTelefono());
            valorEmail.setText(persona.getEmail());

            DefaultTableModel modelo = (DefaultTableModel) tablaPrestamos.getModel();
            for (Prestamo prestamo : persona.getPrestamos()) {
                StringBuilder itemsNombres = new StringBuilder();
                for (Item item : prestamo.getItems()) {
                    if (itemsNombres.length() > 0) itemsNombres.append(", ");
                    itemsNombres.append(item.getNombre());
                }
                modelo.addRow(new Object[] {
                    prestamo.getID(),
                    itemsNombres.toString()
                });
            }
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(e -> dispose());
    }
}