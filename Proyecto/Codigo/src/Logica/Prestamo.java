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
		//agregar comprobacion de item para saber si esta prestado o no
		items.add(item);
		//se le dice al item que pertenece al prestamo.
	}
	
}
