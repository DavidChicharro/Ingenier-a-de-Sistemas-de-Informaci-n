package comparadorPrecios;

/**
 * Clase encargada de transformar y almacenar la información obtenida de los productos.
 * De cada producto se almacena el precio, el nombre del mismo, la URL al producto en 
 * la fuente de datos correspondiente, la URL de la imagen y una variable booleana para 
 * saber el formato en el que aparece el precio.
 */
public class Producto {
	float precio;
	String producto;
	String urlProducto;
	String urlImagen;
	boolean precioKilo;
	
	/**
	 * Método encargado de transformar el precio a un formato 
	 * adecuado para poder procesarlo posteriormente
	 * @param strPrecio
	 * @return precio en coma flotante
	 */
	private float convertirAPrecio(String strPrecio) {
		String tmpPrecio;       
        
		//Si el precio tiene presente '/kg', el formato del precio es 
		//diferente al resto y lo almacenamos en la variable booleana.
        if(strPrecio.contains("/kg")){
            this.precioKilo = true;
            tmpPrecio = strPrecio.replace( "€/kg", "" );
        }
        else{
            this.precioKilo = false;
            tmpPrecio = strPrecio.replace( "€", "" );
        }
        
        //Se sustituye la coma (,) por un punto (.) en el precio
        tmpPrecio = tmpPrecio.replace(",",".");
        
        return Float.parseFloat(tmpPrecio); 
	}

    Producto(String pre, String pro, String urlPro, String urlImg){
    	this.precio = convertirAPrecio(pre);
		this.producto = pro;
		this.urlProducto = urlPro;
		this.urlImagen = urlImg;
    }
    
    public float getPrecio() {
    	return this.precio;
    }
    
    public String getProducto() {
    	return this.producto;
    }
    
    public String getUrlProducto() {
    	return this.urlProducto;
    }
    
    public String getUrlImagen() {
    	return this.urlImagen;
    }
    
    public String esPrecioKilo() {
    	if (precioKilo)
    		return "€/kg";
    	return "€";
    }
}
