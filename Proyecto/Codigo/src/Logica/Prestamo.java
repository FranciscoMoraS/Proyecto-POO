package Logica;

import java.util.ArrayList;
import java.util.List;

public class Prestamo {
	private int ID;
	private Persona persona;
	private List<Item> items;
	private Alerta alerta;
	
	
	public Prestamo(Persona persona, int ID) {
		this.ID=ID;
		this.persona=persona;
		items= new ArrayList<>();
	}


	public Persona getPersona() {
		return persona;
	}


	public List<Item> getItems() {
		return items;
	}


	public Alerta getAlerta() {
		return alerta;
	}
	
	public void agregarItem(Item item) {
		items.add(item);
		item.setPrestamo(this);
	}
	public void agregarAlerta(String mensaje, int lapso) {
		
	}
	public void quitarItem(Item item) throws Exception {
		if(!items.contains(item)) {
			throw new Exception("El item no pertenece al prestamo");
		}
		items.remove(item);
	}
	public void retornarItem(Item item) throws Exception {
		if(!items.contains(item)) {
			throw new Exception("El item no pertenece al prestamo");
		}
		int indexItem=items.indexOf(item);
		Item itemRetornado =items.get(indexItem);
		itemRetornado.setPrestamo(null);
		items.remove(indexItem);
	}
	public void retornarItems() throws Exception {
		for (int i=0; i<items.size();i++) {
			retornarItem(items.get(i));
		}
		items.clear();
	}
	
	
}
