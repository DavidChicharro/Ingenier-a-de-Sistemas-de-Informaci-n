package comparadorPrecios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Clase encargada de integrar la información de las tres fuentes de datos.
 * La información de dos de las fuentes de datos se consigue mediante web scraping.
 * La restante se consigue accediendo a la API de Amazon.
 */
public class ComponerBusquedas {
	String producto;
	ArrayList<Producto> listaProductos = new ArrayList<Producto>();
	
	public ComponerBusquedas(String str) {
		this.producto = str;
	}
		
	/**
	 * Método encargado de ordenar los elementos de la clase Producto 
	 * de menor a mayor en base al atributo precio.
	 */
	Comparator<Producto> compareByPrice = new Comparator<Producto>() {
		@Override
		public int compare(Producto p1, Producto p2) {
			float precio1 = p1.getPrecio();
			float precio2 = p2.getPrecio();
			
			return Float.compare(precio1, precio2);
		}
	};
	
	/**
	 * Método que crea un objeto de la clase Scraping y llama a la función buscar().
	 * Recopila la información de las dos fuentes de datos del WebScraping en una 
	 * lista de productos que se recoge en 'listaProductos'.
	 * @throws IOException
	 */
	private void busquedaScraping() throws IOException {
		Scraping s = new Scraping(producto);
		s.buscar();
		
		listaProductos = s.getListaProductos();
	}
	
	/**
	 * Método que crea un objeto de la clase JavaCodeSnippet y llama a la función buscarAmazon().
	 * Recopila la información de los productos en un ArrayList de productos, el cual se añade
	 * en 'listaProductos'.
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private void busquedaAmazon() throws IOException, ParserConfigurationException, SAXException {
		JavaCodeSnippet jcs = new JavaCodeSnippet(producto);
		jcs.buscarAmazon();
		
		listaProductos.addAll(jcs.getListaProductos());	
	}

	public ArrayList<Producto> getListaProductos(){
    	return this.listaProductos;
    }
	
	/**
	 * Método que llama a los dos métodos principales de esta clase,
	 * encargados de recopilar toda la información de los productos.
	 * Por último ordena la lista de productos de menor a mayor precio.
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void busqueda() throws IOException, ParserConfigurationException, SAXException {
		busquedaScraping();
		busquedaAmazon();
		
		Collections.sort(listaProductos, compareByPrice);
	}
	
	
}
