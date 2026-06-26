
package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Persona implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Nombre;
	private String Telefono;
	private String Email;
	private List<Prestamo> prestamos;
	
	public Persona(String nombre, String telefono, String email) {
		this.Email=email;
		this.Telefono=telefono;
		this.Nombre=nombre;
		this.prestamos=new ArrayList<>();
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void borrarPrestamo(Prestamo prestamo) throws Exception {
		if (!prestamos.contains(prestamo)) {
			throw new Exception("El prestamo no existe");
		}
		prestamos.remove(prestamo);
	}
	public void agregarPrestamo(Prestamo prestamo) throws Exception {
		if (prestamos.contains(prestamo)) {
			throw new Exception("El prestamo ya está en la lista");
		}
		prestamos.add(prestamo);
	}
	public boolean tienePrestamos() {
		return prestamos.size()>0;
	}

}
