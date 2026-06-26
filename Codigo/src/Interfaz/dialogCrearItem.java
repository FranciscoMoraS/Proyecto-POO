package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import Logica.Categoria;

public class dialogCrearItem extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTextField nombreItem;
    private JTextField descripcionItem;
    private JComboBox comboBox;
    private JTable tablaCategorias;
    private boolean confirmado = false;

    public static void main(String[] args) {
        try {
            dialogCrearItem dialog = new dialogCrearItem();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public dialogCrearItem() {
        this(null, null, null);
    }

    public dialogCrearItem(JFrame parent, List tipos, List categorias) {
        super(parent, "Crear Item", true);
        setBounds(100, 100, 688, 380);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nombre:");
        lblNewLabel.setBounds(23, 24, 60, 12);
        contentPanel.add(lblNewLabel);

        nombreItem = new JTextField();
        nombreItem.setBounds(103, 21, 96, 18);
        contentPanel.add(nombreItem);
        nombreItem.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Descripcion:");
        lblNewLabel_1.setBounds(20, 62, 73, 12);
        contentPanel.add(lblNewLabel_1);

        descripcionItem = new JTextField();
        descripcionItem.setBounds(103, 59, 96, 18);
        contentPanel.add(descripcionItem);
        descripcionItem.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Tipo:");
        lblNewLabel_2.setBounds(23, 116, 44, 12);
        contentPanel.add(lblNewLabel_2);

        comboBox = new JComboBox();
        comboBox.setBounds(103, 103, 170, 39);
        contentPanel.add(comboBox);

        if (tipos != null) {
            for (Object t : tipos) {
                comboBox.addItem(t);
            }
        }

        JLabel lblCategorias = new JLabel("Categorias:");
        lblCategorias.setBounds(358, 24, 80, 12);
        contentPanel.add(lblCategorias);

        tablaCategorias = new JTable();
        tablaCategorias.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Seleccionar", "Categoria" }
        ) {
            Class[] columnTypes = new Class[] { Boolean.class, String.class };
            public Class getColumnClass(int columnIndex) { return columnTypes[columnIndex]; }
            boolean[] columnEditables = new boolean[] { true, false };
            public boolean isCellEditable(int row, int column) { return columnEditables[column]; }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(357, 39, 280, 200);
        scrollPane.setViewportView(tablaCategorias);
        contentPanel.add(scrollPane);

        if (categorias != null) {
            DefaultTableModel modelo = (DefaultTableModel) tablaCategorias.getModel();
            for (Object c : categorias) {
                Categoria cat = (Categoria) c;
                modelo.addRow(new Object[] { false, cat.getNombre() });
            }
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(e -> { confirmado = true; dispose(); });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        cancelButton.addActionListener(e -> dispose());
    }

    public String getNombre() { return nombreItem.getText(); }
    public String getDescripcion() { return descripcionItem.getText(); }
    public Object getTipoSeleccionado() { return comboBox.getSelectedItem(); }
    public boolean isConfirmado() { return confirmado; }

    public List<Categoria> getCategoriasSeleccionadas(List<Categoria> todasLasCategorias) {
        List<Categoria> seleccionadas = new ArrayList<>();
        DefaultTableModel modelo = (DefaultTableModel) tablaCategorias.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            boolean marcada = (boolean) modelo.getValueAt(i, 0);
            if (marcada) {
                seleccionadas.add(todasLasCategorias.get(i));
            }
        }
        return seleccionadas;
    }
}