package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class dialogCrearCategoria extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTextField txtNombre;
    private boolean confirmado = false;

    public static void main(String[] args) {
        try {
            dialogCrearCategoria dialog = new dialogCrearCategoria();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public dialogCrearCategoria() {
        this(null);
    }

    public dialogCrearCategoria(JFrame parent) {
        super(parent, "Crear Categoría", true);
        setBounds(100, 100, 350, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 30, 80, 20);
        contentPanel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(110, 30, 200, 20);
        contentPanel.add(txtNombre);

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

    public String getNombre() { return txtNombre.getText(); }
    public boolean isConfirmado() { return confirmado; }
    public void setNombre(String nombre) { txtNombre.setText(nombre); }
}