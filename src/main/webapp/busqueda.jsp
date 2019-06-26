<%@page import="java.util.ArrayList"%>
<%@page import="comparadorPrecios.*"%>
<%@page import="org.w3c.dom.Document"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<title>Comparador de precios</title>

<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>

<body>
	<header>
		<a href="https://comparador-de-precios-235614.appspot.com"> <img
			src="img/logo.png" class="header-logo" alt="Logo">
		</a>

		<h1 class="titulo-web">Comparador de precios de supermercados</h1>
	</header>

	<form action="busqueda.jsp" method="get" class="search-form">
		<fieldset>
			<legend>Búsqueda</legend>

			<img src="img/lupa.png" class="img-lupa" alt="lupa">
			<input	type="search" id="busqueda" name="producto" class="search-box" autofocus>
			<input type="submit" class="search-box" value="Buscar">

		</fieldset>
	</form>

	<h2>Productos</h2>
	<ul class="lista-productos">
		<%
		//Para recoger el valor de un formulario en JSP, se utiliza la función request.getParameter.
		//En la variable producto se almacena la búsqueda introducida por el usuario.
		String producto = request.getParameter("producto");
		
		//Se crea un objeto de la clase ComponerBusquedas, que es la encargada de confeccionar la salida
		//añadiendo los productos obtenidos de las diferentes fuentes de datos.
		ComponerBusquedas cb = new ComponerBusquedas(producto);
		cb.busqueda();
		
		//listaProductos contiene todos los productos y su información
		ArrayList<Producto> listaProductos = cb.getListaProductos();
		
		for(int i=0 ; i<listaProductos.size() ; i++){
			Producto p = listaProductos.get(i);
			
			//Se consulta la URL del producto para saber de dónde procede y así
			//añadir el logo en el resultado
			String pathLogo;
			if(p.getUrlProducto().contains("hipercor")){
				pathLogo = "hipercor";
			}else if(p.getUrlProducto().contains("carrefour")){
				pathLogo = "carrefour";
			}
			else{
				pathLogo = "amazon";
			}					
			
			out.println("<li class='producto'><h3>"+p.getPrecio()+" "+p.esPrecioKilo()+
					"</h3><img class='logo-super' src='img/"+pathLogo+
					"-logo.jpg' alt='logo supermercado'><a href='"+
					p.getUrlProducto()+"' target='_blank'><img src='"+p.getUrlImagen()+
					"' alt='"+p.getProducto()+"'></a><p class='titulo'>"+p.getProducto()+
					"</p></li>");
		}
		
		%>
	</ul>
	
	<footer>
		<a href="mailto:e.danielterol@go.ugr.es">Contacto</a>
	</footer>

</body>
</html>