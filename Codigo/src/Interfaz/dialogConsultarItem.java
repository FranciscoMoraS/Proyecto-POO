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
import javax.swing.JLabel;
import Logica.Categoria;
import Logica.Item;

public class dialogConsultarItem extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    public static void main(String[] args) {
        try {
            dialogConsultarItem dialog = new dialogConsultarItem();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public dialogConsultarItem() {
        this(null, null);
    }

    
    public dialogConsultarItem(JFrame parent, Item item) {
        super(parent, "Consultar Item", true);
        setBounds(100, 100, 450, 380);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 20);
        contentPanel.add(lblCodigo);

        JLabel valorCodigo = new JLabel();
        valorCodigo.setBounds(110, 20, 200, 20);
        contentPanel.add(valorCodigo);

        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 50, 80, 20);
        contentPanel.add(lblNombre);

        JLabel valorNombre = new JLabel();
        valorNombre.setBounds(110, 50, 200, 20);
        contentPanel.add(valorNombre);

        
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 80, 80, 20);
        contentPanel.add(lblDescripcion);

        JLabel valorDescripcion = new JLabel();
        valorDescripcion.setBounds(110, 80, 200, 20);
        contentPanel.add(valorDescripcion);

        
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 110, 80, 20);
        contentPanel.add(lblTipo);

        JLabel valorTipo = new JLabel();
        valorTipo.setBounds(110, 110, 200, 20);
        contentPanel.add(valorTipo);

        
        JLabel lblPrestado = new JLabel("Prestado:");
        lblPrestado.setBounds(20, 140, 80, 20);
        contentPanel.add(lblPrestado);

        JLabel valorPrestado = new JLabel();
        valorPrestado.setBounds(110, 140, 200, 20);
        contentPanel.add(valorPrestado);

        
        JLabel lblCategorias = new JLabel("Categorías:");
        lblCategorias.setBounds(20, 175, 80, 20);
        contentPanel.add(lblCategorias);

        JTable tablaCategorias = new JTable();
        tablaCategorias.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nombre" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 200, 390, 100);
        scrollPane.setViewportView(tablaCategorias);
        contentPanel.add(scrollPane);

        
        if (item != null) {
            valorCodigo.setText(String.valueOf(item.getCodigo()));
            valorNombre.setText(item.getNombre());
            valorDescripcion.setText(item.getDescripcion());
            valorTipo.setText(item.getTipo().toString());
            valorPrestado.setText(item.Prestado() ? "Sí" : "No");

            DefaultTableModel modelo = (DefaultTableModel) tablaCategorias.getModel();
            for (Categoria c : item.getCategorias()) {
                modelo.addRow(new Object[] { c.getNombre() });
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