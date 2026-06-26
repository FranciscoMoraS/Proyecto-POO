package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Logica.Categoria;
import Logica.Item;
import Logica.Tipo;

public class dialogModificarItem extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    // Variables de clase
    private JTextField Nombre;
    private JTextField Descripcion;
    private JComboBox comboBoxTipos;
    private JTable tablaCategorias;
    private boolean confirmado = false;

    public static void main(String[] args) {
        try {
            dialogModificarItem dialog = new dialogModificarItem();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Constructor para WindowBuilder
    public dialogModificarItem() {
        this(null, null, null, null);
    }

    // Constructor principal
    public dialogModificarItem(JFrame parent, Item item, List tipos, List categorias) {
        super(parent, "Modificar Item", true);
        setBounds(100, 100, 510, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setBounds(25, 23, 44, 12);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Descripción");
        lblNewLabel_1.setBounds(25, 58, 65, 12);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Tipo");
        lblNewLabel_2.setBounds(25, 98, 44, 12);
        contentPanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Categorias");
        lblNewLabel_3.setBounds(226, 23, 57, 12);
        contentPanel.add(lblNewLabel_3);

        Nombre = new JTextField();
        Nombre.setBounds(100, 20, 96, 18);
        contentPanel.add(Nombre);
        Nombre.setColumns(10);

        Descripcion = new JTextField();
        Descripcion.setBounds(100, 55, 96, 18);
        contentPanel.add(Descripcion);
        Descripcion.setColumns(10);

        comboBoxTipos = new JComboBox();
        comboBoxTipos.setBounds(106, 94, 90, 20);
        contentPanel.add(comboBoxTipos);

        
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
        scrollPane.setBounds(226, 51, 270, 200);
        scrollPane.setViewportView(tablaCategorias);
        contentPanel.add(scrollPane);

        
        if (item != null) {
            Nombre.setText(item.getNombre());
            Descripcion.setText(item.getDescripcion());
        }

        
        if (tipos != null) {
            for (Object t : tipos) {
                comboBoxTipos.addItem(t);
            }
            if (item != null) {
                comboBoxTipos.setSelectedItem(item.getTipo());
            }
        }

        
        if (categorias != null) {
            DefaultTableModel modelo = (DefaultTableModel) tablaCategorias.getModel();
            for (Object c : categorias) {
                Categoria cat = (Categoria) c;
                boolean tieneLaCategoria = item != null && item.getCategorias().contains(cat);
                modelo.addRow(new Object[] { tieneLaCategoria, cat.getNombre() });
            }
        }

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

    
    public String getNombre() { return Nombre.getText(); }
    public String getDescripcion() { return Descripcion.getText(); }
    public Tipo getTipoSeleccionado() { return (Tipo) comboBoxTipos.getSelectedItem(); }
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