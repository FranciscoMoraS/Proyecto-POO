package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Nombre;
	private List<Item> Items;
	
	public Categoria(String nombre) {
		this.Nombre=nombre;
		this.Items=new ArrayList<>();
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public List<Item> getItems() {
		return Items;
	}
	
	public void agregarItem(Item nuevo) {
		Items.add(nuevo);
	}
	public void quitarItem(Item quitar) {
		Items.remove(quitar);
	}
	public String toString() {
		return Nombre;
	}
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    Categoria other = (Categoria) obj;
	    return Nombre.equals(other.Nombre);
	}
}
