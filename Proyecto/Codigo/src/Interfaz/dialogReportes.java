package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class dialogReportes extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    public static void main(String[] args) {
        try {
            dialogReportes dialog = new dialogReportes();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public dialogReportes() {
        this(null, "Reporte", new String[] {}, new Object[][] {});
    }

    public dialogReportes(JFrame parent, String titulo, String[] columnas, Object[][] datos) {
        super(parent, titulo, true);
        setBounds(100, 100, 792, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JTable tabla = new JTable();
        tabla.setModel(new DefaultTableModel(datos, columnas) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 758, 300);
        scrollPane.setViewportView(tabla);
        contentPanel.add(scrollPane);

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