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
	
	public Persona consultarPersona(String nombre) {
		Persona p=null;
		for (int i=0; i<personas.size(); i++) {
			if (personas.get(i).getNombre()==nombre) {
				p=personas.get(i);
			}
		}
		return p;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void crearItem(String nombre, int Codigo, String descripcion, Tipo tipo) {
		Item item = new Item(nombre, Codigo, descripcion, tipo);
		items.add(item);
	}
	
	public void modificarItem(String nombre, int codigo,  String descripcion, Tipo tipo) {
		Item item= null;
		for (int i=0; i<items.size(); i++) {
			item=items.get(i);
			if (item.getCodigo()==codigo) {
				item.setNombre(nombre);
				item.setDescripcion(descripcion);
				item.setTipo(tipo);
			}
		}
	}
	//agregar un metodo privado para cambiar categorias.
	public void borrarItem(int codigo) {
		items.removeIf(Item -> Item.getCodigo()==codigo);
	}
	
	public Item consultarItem (int codigo) {
		Item item= null;
		for (int i=0; i<items.size(); i++) {
			item=items.get(i);
			if (item.getCodigo()==codigo) {
				return item;
			}
		}
		return null;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	
}
