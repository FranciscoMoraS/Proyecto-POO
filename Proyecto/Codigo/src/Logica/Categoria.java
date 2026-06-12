package Logica;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
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
}
