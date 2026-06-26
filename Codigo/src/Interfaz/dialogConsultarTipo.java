package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
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
import Logica.Tipo;

public class dialogConsultarTipo extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    public static void main(String[] args) {
        try {
            dialogConsultarTipo dialog = new dialogConsultarTipo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public dialogConsultarTipo() {
        this(null, null, null);
    }

    public dialogConsultarTipo(JFrame parent, Tipo tipo, List<Item> todosLosItems) {
        super(parent, "Consultar Tipo", true);
        setBounds(100, 100, 400, 350);
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

        JLabel lblItems = new JLabel("Items:");
        lblItems.setBounds(20, 55, 80, 20);
        contentPanel.add(lblItems);

        JTable tablaItems = new JTable();
        tablaItems.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Código", "Nombre" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 80, 340, 180);
        scrollPane.setViewportView(tablaItems);
        contentPanel.add(scrollPane);

        if (tipo != null) {
            valorNombre.setText(tipo.getNombre());

            DefaultTableModel modelo = (DefaultTableModel) tablaItems.getModel();
            for (Item item : todosLosItems) {
                if (item.getTipo() == tipo) {
                    modelo.addRow(new Object[] {
                        item.getCodigo(),
                        item.getNombre()
                    });
                }
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