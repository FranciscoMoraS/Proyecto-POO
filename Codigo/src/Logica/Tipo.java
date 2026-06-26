package Logica;

import java.util.ArrayList;
import java.util.List;

public class Tipo {
	private String Nombre;
	private List<Item> lista;
	
	public Tipo(String nombre) {
		this.Nombre=nombre;
		this.lista= new ArrayList<>();
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public List<Item> getLista() {
		return lista;
	}
	public void agregarItem(Item agregar) {
		lista.add(agregar);
	}
	public void quitarItem(Item quitar) {
		lista.remove(quitar);
	}
	
	public String toString() {
		return Nombre;
	}
}
