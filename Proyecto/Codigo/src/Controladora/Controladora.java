package Controladora;

import java.util.List;

import Logica.Categoria;
import Logica.Item;
import Logica.Persona;
import Logica.Prestamo;
import Logica.Tipo;

public class Controladora {
	private List<Persona> personas;
	private List<Prestamo> prestamos;
	private List<Item> items;
	private List<Tipo> tipos;
	private List<Categoria> categorias;
	
	
	public List<Persona> getPersonas() {
		return personas;
	}

	public void crearPersona(String nombre, String telefono, String email) {
		//agregar comprobacion para el email aquí o en la clase persona
		Persona p= new Persona(nombre, telefono,email);
		personas.add(p);
	}
	
	public void modificarPersona(String nombre, String telefono, String email) {
		Persona p;
		for (int i=0;i<personas.size();i++) {
			if (personas.get(i).getNombre()==nombre) {
				p= personas.get(i);
				p.setNombre(nombre);
				p.setEmail(email);
				p.setTelefono(telefono);
			}
		}	
	}
	
	public void borrarPersona(String nombre) {
		personas.removeIf(Persona -> Persona.getNombre()==nombre);
	}
	
}
