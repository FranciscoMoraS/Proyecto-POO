package Interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class dialogCrearItem extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField nombreItem;
	private JTextField descripcionItem;
	private JComboBox comboBox;
	private boolean confirmado = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialogCrearItem dialog = new dialogCrearItem();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public dialogCrearItem() {
		this(null, null);
	}
	public dialogCrearItem(JFrame parent,List tipos) {
		super (parent, "Crear Item", true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(23, 24, 44, 12);
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
		
		comboBox = new JComboBox();
		comboBox.setBounds(103, 103, 170, 39);
		contentPanel.add(comboBox);
		
		if (tipos != null) {
			for (Object t : tipos) {
				comboBox.addItem(t);
			}
		}
		
		JLabel lblNewLabel_2 = new JLabel("Tipo: ");
		lblNewLabel_2.setBounds(23, 116, 44, 12);
		contentPanel.add(lblNewLabel_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(e-> { confirmado = true; dispose();});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(e->dispose());
			}
			
		}
	}
	public String getNombre() {return nombreItem.getText();}
	public String getDescripcion() {return descripcionItem.getText();}
	public Object getTipoSeleccionado() {return comboBox.getSelectedItem();}
	public boolean isConfirmado() {return confirmado;}
}
