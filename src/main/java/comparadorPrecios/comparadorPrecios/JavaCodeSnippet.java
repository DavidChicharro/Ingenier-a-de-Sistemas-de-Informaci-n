package comparadorPrecios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/*
 * This class shows how to make a simple authenticated call to the
 * Amazon Product Advertising API.
 */
public class JavaCodeSnippet {
		
    /*
     * Your Access Key ID, as taken from the Your Account page.
     */
    private static final String ACCESS_KEY_ID = "AKIAJ2M7Z62GFWKITXBA";

    /*
     * Your Secret Key corresponding to the above ID, as taken from the
     * Your Account page.
     */
    private static final String SECRET_KEY = "ASJo63DLv+0IaNwTp1j4ytzQvkUaM+AXNlbAOBVa";

    /*
     * Use the end-point according to the region you are interested in.
     */
    private static final String ENDPOINT = "webservices.amazon.es";
    
    private String producto;
    
    private ArrayList<Producto> listaProductos = new ArrayList<Producto>();
    
    public JavaCodeSnippet(String producto) {
    	this.producto = producto;
    }
    
    /**
     * Método que realiza la conexión con la API de Amazon
     * @return Archivo XML con la información de la consulta
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private String consultaAmazon() throws ParserConfigurationException, SAXException, IOException {

        /*
         * Set up the signed requests helper.
         */
        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, ACCESS_KEY_ID, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        String requestUrl = null;

        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", "AKIAJ2M7Z62GFWKITXBA");
        params.put("AssociateTag", "ikor0b-21");
        params.put("SearchIndex", "All");
        params.put("Keywords", producto);
        params.put("ResponseGroup", "Images,ItemAttributes,Offers");

        requestUrl = helper.sign(params);
       
        return requestUrl;
    }    

    public ArrayList<Producto> getListaProductos(){
    	return this.listaProductos;
    }
    
    /**
     * Método encargado de conseguir la información que necesitamos para mostrar los productos.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void buscarAmazon() throws IOException, ParserConfigurationException, SAXException {    	 
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        String requestUrl = consultaAmazon();
        //La función parse devuelve un documento DOM correspondiente al documento XML
        //enviado como parámetro.
        Document doc = db.parse(requestUrl);
        
        //Del nuevo documento DOM se extraen los elementos cuya etiqueta es 'Item',
        //puesto que el XML que devuelve Amazon tiene a cada producto entre dicha etiqueta
        NodeList nList = doc.getElementsByTagName("Item");
        
        System.out.println("Comprobando entradas de: Amazon");
        for (int i = 0; i < nList.getLength(); i++) {
        	   
    	   NodeList nodeLink = doc.getElementsByTagName("DetailPageURL");
    	   NodeList nodeTitulo = doc.getElementsByTagName("Title");
    	   NodeList nodePrecio = doc.getElementsByTagName("Price");
    	   NodeList nodeUrlImagen = doc.getElementsByTagName("ImageSets");
			
    	   //La función getTextContent() convierte a texto el contenido de la etiqueta.
    	   String link = nodeLink.item(i).getTextContent();
    	   String titulo = nodeTitulo.item(i).getTextContent();
    	   
    	   //La función getChildNodes() devuelve las etiquetas Amount, CurrencyCode y FormattedPrice.
    	   //En 'Amount' interesa el ítem 0 dentro de Price, y se convierte a texto.
    	   String numero = nodePrecio.item(i).getChildNodes().item(0).getTextContent();
    	   Float nFloat = Float.parseFloat(numero) / (float)100.0;
    	   
    	   String precio = Float.toString(nFloat);
    	   
    	   //La URL de la imagen requiere acceder a varios niveles de la etiqueta <ImageSets>
    	   //hasta llegar a la <URL> de <LargeImage>
    	   String urlImagen = nodeUrlImagen.item(i).getChildNodes().item(0).getChildNodes().item(5).getChildNodes().item(0).getTextContent();
    
    	   Producto p = new Producto(precio, titulo, link, urlImagen);
    	   listaProductos.add(p);
        }
    }
}