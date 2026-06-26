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
import Logica.Persona;

public class dialogModificarPersona extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private boolean confirmado = false;

    public static void main(String[] args) {
        try {
            dialogModificarPersona dialog = new dialogModificarPersona();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Constructor para WindowBuilder
    public dialogModificarPersona() {
        this(null, null);
    }

    // Constructor principal
    public dialogModificarPersona(JFrame parent, Persona persona) {
        super(parent, "Modificar Persona", true);
        setBounds(100, 100, 450, 300);
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

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 70, 80, 20);
        contentPanel.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(110, 70, 200, 20);
        contentPanel.add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 110, 80, 20);
        contentPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(110, 110, 200, 20);
        contentPanel.add(txtEmail);

        // Prellenar con los datos actuales
        if (persona != null) {
            txtNombre.setText(persona.getNombre());
            txtTelefono.setText(persona.getTelefono());
            txtEmail.setText(persona.getEmail());
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

    public String getNombre() { return txtNombre.getText(); }
    public String getTelefono() { return txtTelefono.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public boolean isConfirmado() { return confirmado; }
}