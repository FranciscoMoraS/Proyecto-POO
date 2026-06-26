package Controladora;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Logica.Categoria;
import Logica.Item;
import Logica.Persona;
import Logica.Prestamo;
import Logica.Tipo;

public class Controladora implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Persona> personas;
	private List<Prestamo> prestamos;
	private List<Item> items;
	private List<Tipo> tipos;
	private List<Categoria> categorias;
	private int conteoPrestamos;
	private int consecutivoItems=0;
	
	public Controladora() {
		personas = new ArrayList<Persona>();
		prestamos = new ArrayList<Prestamo>();
		items = new ArrayList<Item>();
		tipos = new ArrayList<Tipo>();
		categorias= new ArrayList<Categoria>();
		conteoPrestamos=0;
		consecutivoItems=0;
		tipos.add(new Tipo("inicial"));
	}
	public List<Persona> getPersonas() {
		return personas;
	}

	public void crearPersona(String nombre, String telefono, String email) {
		//agregar comprobacion para el email aquí o en la clase persona
		Persona p= new Persona(nombre, telefono,email);
		personas.add(p);
	}
	
	public void modificarPersona(String nombreOriginal, String nombreNuevo, String telefono, String email) {
		Persona p;
		for (int i=0;i<personas.size();i++) {
			if (personas.get(i).getNombre().equals(nombreOriginal)) {
				p= personas.get(i);
				p.setNombre(nombreNuevo);
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
			if (personas.get(i).getNombre().equals(nombre)) {
				p=personas.get(i);
			}
		}
		return p;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void crearItem(String nombre, String descripcion, Tipo tipo) {
		Item item = new Item(nombre, consecutivoItems, descripcion, tipo);
		items.add(item);
		consecutivoItems++;
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
	public void modificarCategoriasItem(int codigo, List<Categoria> categoriasNuevas) {
	    Item item = consultarItem(codigo);
	    if (item != null) {
	        for (Categoria c : item.getCategorias()) {
	            c.quitarItem(item);
	        }
	        item.getCategorias().clear();
	        for (Categoria c : categoriasNuevas) {
	            item.agregarCategoria(c);
	            c.agregarItem(item);      
	        }
	    }
	}
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
	
	public void crearCategoria(String nombre) {
		Categoria c= new Categoria(nombre);
		categorias.add(c);
	}
	public void modificarCategoria(String nombreOriginal, String nombreNuevo) {
		Categoria c= null;
		for (int i=0; i<categorias.size();i++) {
			c= categorias.get(i);
			if (c.getNombre().equals(nombreOriginal))
				c.setNombre(nombreNuevo);
		}
	}
	public void borrarCategoria(String nombre) {
	    Categoria categoria = consultarCategoria(nombre);
	    if (categoria != null) {
	        for (Item item : items) {
	            item.quitarCategoria(categoria);
	        }
	        categorias.remove(categoria);
	    }
	}
	public Categoria consultarCategoria(String nombre) {
		Categoria categoria= null;
		for (int i=0; i<categorias.size(); i++) {
			categoria=categorias.get(i);
			if (categoria.getNombre().equals(nombre)) {
				return categoria;
			}
		}
		return null;
	}
	

	public List<Tipo> getTipos() {
		return tipos;
	}
	public void crearTipo(String nombre) {
		Tipo t= new Tipo(nombre);
		tipos.add(t);
	}
	public void modificarTipo(String nombreOriginal, String nombreNuevo) {
		Tipo t= null;
		for (int i=0; i<tipos.size();i++) {
			t= tipos.get(i);
			if (t.getNombre().equals(nombreOriginal))
				t.setNombre(nombreNuevo);
		}
	}
	public void borrarTipo(String nombre) {
	    Tipo tipoGenerico = tipos.get(0);
	    Tipo tipo = consultarTipo(nombre);
	    
	    if (tipo != null && tipo != tipoGenerico) {
	        for (Item item : items) {
	            if (item.getTipo() == tipo) {
	                item.setTipo(tipoGenerico);
	            }
	        }
	        tipos.remove(tipo);
	    }
	}
	public Tipo consultarTipo(String nombre) {
		Tipo t= null;
		for (int i=0; i<tipos.size(); i++) {
			t=tipos.get(i);
			if (t.getNombre().equals(nombre)) {
				return t;
			}
		}
		return t;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}
	
	public void crearPrestamo(Persona persona) {
		Prestamo p= new Prestamo(persona, conteoPrestamos);
		prestamos.add(p);
		conteoPrestamos++;
	}
	public void agregarItemPrestamo(int ID, Item item) throws Exception {
		if (ID>conteoPrestamos || ID<0)
			throw new Exception("Index fuera de rango");
		Prestamo p= prestamos.get(ID);
		p.agregarItem(item);
	}
	public void eliminarItemPrestamo(int ID, Item item) throws Exception{
		if (ID>conteoPrestamos || ID<0)
			throw new Exception("Index fuera de rango");
		Prestamo p= prestamos.get(ID);
		p.quitarItem(item);
	}
	public void retornarItemPrestamo(int ID, Item item) throws Exception{
		if (ID>conteoPrestamos || ID<0)
			throw new Exception("Index fuera de rango");
		Prestamo p= prestamos.get(ID);
		p.retornarItem(item);
	}
	
	public void terminarPrestamo(int ID) throws Exception{
		if (ID>conteoPrestamos || ID<0)
			throw new Exception("Index fuera de rango");
		Prestamo p= prestamos.get(ID);
		p.retornarItems();
		prestamos.remove(p);
	}
	// cambiar logica de prestamo para usar un diccionario
	
	public void guardarDatos() {
	    try (ObjectOutputStream oos = new ObjectOutputStream(
	            new FileOutputStream("datos.ser"))) {
	        oos.writeObject(this);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static Controladora cargarDatos() {
	    try (ObjectInputStream ois = new ObjectInputStream(
	            new FileInputStream("datos.ser"))) {
	        return (Controladora) ois.readObject();
	    } catch (Exception e) {
	        return new Controladora();
	    }
	}
	
}
