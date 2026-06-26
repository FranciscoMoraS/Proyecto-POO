package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Nombre;
	private int Codigo;
	private String Descripcion;
	private Prestamo Prestamo;
	private Tipo tipo;
	private List<Categoria> categorias;
	
	public Item(String nombre, int codigo, String descripcion, Tipo tipo) {
		this.Nombre=nombre;
		this.Codigo=codigo;
		this.Descripcion=descripcion;
		this.tipo=tipo;
		this.categorias=new ArrayList<>();
		this.Prestamo=null;
	}
	

	public String getNombre() {
		return Nombre;
	}


	public void setNombre(String nombre) {
		Nombre = nombre;
	}


	public String getDescripcion() {
		return Descripcion;
	}


	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	public int getCodigo() {
		return Codigo;
	}


	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	
	public Prestamo getPrestamo() {
		return Prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		Prestamo=prestamo;
	}
	public void agregarCategoria(Categoria nuevaCategoria) {
		categorias.add(nuevaCategoria);
	}
	public void quitarCategoria(Categoria quitar) {
		categorias.remove(quitar);
	}
	public boolean Prestado() {
		if (Prestamo!=null) {
			return true;
		}
		return false;
	}
}

