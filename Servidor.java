

/* Cambios en servidores:

   Servidor de Aragón: Se ha actualizado el visor
   Servidor Asturias: La web de visor de asturias redirige al visor nacional
   +++ Servidor País Vasco: El visor se ha actualizado pero se siguen pudiendo hacer peticiones al servidor antiguo
   Servidor CyM: Actualizado
   Servidor Murcia: Actualizado

*/



class Servidor 
{
	
public static final int NACIONAL = 0;
public static final int PNOA = 1;
public static final int CyL = 2;
public static final int GALICIA = 3;
public static final int ASTURIAS = 4;
public static final int PAISVASCO = 5;
public static final int ARAGON = 6;
public static final int MANCHA = 7;
public static final int MURCIA = 8;
public static final int CANARIAS_SIGPAC = 9;
public static final int CANARIAS_SITCAN = 10;
public static final int CANARIAS_IDECAN = 11;
public static final int MADRID = 12;
public static final int RIOJA = 13;
public static final int IGN = 14;
public static final int ANDALUCIA = 15;
public static final int NAVARRA = 16;
public static final int EXTREMADURA = 17;
public static final int CyL_v3 = 18;
public static final int EUS_v3 = 19;
public static final int CVALENCIANA = 20;
public static final int ARAGON_SITAR = 21;
public static final int NAVARRA1950 = 22;
public static final int NAVARRA1000 = 23;
public static final int RECINTOS = 24;
public static final int PARCELAS = 25;
public static final int IBERPIX = 26;
public static final int IDEANDALORTO = 27;

public static final int NUMERO_DE_SERVIDORES = 28;


   int id = -1;
   String nombre;
   String abreviatura;
   String http;
   // String servidor = "http://sigpac.mapa.es/sigpac/imagenes/";
   static String defecto = "http://sigpac.mapa.es/tilesserver/";
   
   static String[] opcionesComboServidor;                                                                      
   static String[] opcionesComboIPServidor;
   static String[] abreviaturaServidor;
   static boolean[] DatumServidorWGS84;
   static String[] visor;                                     
   static boolean[] version4;                                         
   static boolean[] vistaPajaro;
       				       			                                                                                          				   
 		             
 public Servidor()
 {   
   opcionesComboServidor = new String[NUMERO_DE_SERVIDORES];;                                                                      
   opcionesComboIPServidor = new String[NUMERO_DE_SERVIDORES];
   abreviaturaServidor = new String[NUMERO_DE_SERVIDORES];
   DatumServidorWGS84 = new boolean[NUMERO_DE_SERVIDORES];
   visor = new String[NUMERO_DE_SERVIDORES];                                     
   version4 = new boolean[NUMERO_DE_SERVIDORES];                                         
   vistaPajaro = new boolean[NUMERO_DE_SERVIDORES];
       
   opcionesComboServidor[NACIONAL] = "Nacional - España"; 
   opcionesComboServidor[PNOA] = "PNOA";  
   opcionesComboServidor[CyL] = "Castilla y León";		
   opcionesComboServidor[GALICIA] = "Galicia";
   opcionesComboServidor[ASTURIAS] = "Asturias";
   opcionesComboServidor[PAISVASCO] = "País Vasco";
   opcionesComboServidor[ARAGON] = "Aragón";			
   opcionesComboServidor[MANCHA] = "Castilla La Mancha";
   opcionesComboServidor[MURCIA] = "Murcia";
   opcionesComboServidor[CANARIAS_SIGPAC] = "Canarias";
   opcionesComboServidor[CANARIAS_SITCAN] = "Canarias SITCAN";
   opcionesComboServidor[CANARIAS_IDECAN] = "Canarias IDECAN";
   opcionesComboServidor[MADRID] = "Madrid";
   opcionesComboServidor[RIOJA] = "La Rioja";
   opcionesComboServidor[IGN] = "Mapa IGN";
   opcionesComboServidor[ANDALUCIA] = "Andalucía";
   opcionesComboServidor[NAVARRA] = "Navarra";
   opcionesComboServidor[EXTREMADURA] = "Extremadura";
   opcionesComboServidor[CyL_v3] = "Castilla y León v3";
   opcionesComboServidor[EUS_v3] = "País Vasco v3";
   opcionesComboServidor[CVALENCIANA] = "Generalitat Valenciana";
   opcionesComboServidor[ARAGON_SITAR] = "Aragón SITAR";
   opcionesComboServidor[NAVARRA1950] = "Navarra1950";
   opcionesComboServidor[NAVARRA1000] = "Navarra1000";
   opcionesComboServidor[RECINTOS] = "Recintos";
   opcionesComboServidor[PARCELAS] = "Parcelas";
   opcionesComboServidor[IBERPIX] = "IGN-Iberpix";
   opcionesComboServidor[IDEANDALORTO] = "IDE Andalucia ORTO";
        
     
   // La original del IGN es "http://www.idee.es/wms/IDEE-Base/" pero no se hacen bien los solapamientos.
   // "http://idee.unizar.es/wms/MTN-Raster/MTN-Raster?"      
   opcionesComboIPServidor[NACIONAL] = "http://tilesserver.mapa.es/tilesserver/";
   opcionesComboIPServidor[PNOA] = "http://www.idee.es/wms/PNOA/PNOA?";
   opcionesComboIPServidor[CyL] = "http://217.71.16.146/tilesserver/asptilesserver.aspx?";                                                                     
   opcionesComboIPServidor[GALICIA] = "http://emediorural3.xunta.es/vf5ts/"; //"http://www.xunta.es/conselle/ag/fogga/sixpac/servidorimagenes/";
   opcionesComboIPServidor[ASTURIAS] = "http://sigpac.princast.es/imageserver/tilesserver.aspx?";
   opcionesComboIPServidor[PAISVASCO] = "http://62.99.86.165/tilesserver2008/";
   opcionesComboIPServidor[ARAGON] = "http://sigpac1.aragob.es/asptilesserver50/tilesserver.aspx?";    //"http://sigpac1.aragob.es/imageserver/";
   opcionesComboIPServidor[MANCHA] = "http://sigpac.jccm.es/servidorbaldosas/tilesserver.aspx?";
   //opcionesComboIPServidor[MURCIA] = "http://sigpac.carm.es/servidorimagenes/tilesserver.aspx?";
   opcionesComboIPServidor[MURCIA] = "http://sigpac.carm.es/servidorbaldosas/tilesserver.aspx?";
   opcionesComboIPServidor[CANARIAS_SIGPAC] = "http://sigpac3.gobiernodecanarias.org/sigpac/tilesserver/tilesserver.aspx?"; //195.57.95.89
   opcionesComboIPServidor[CANARIAS_SITCAN] = "http://195.57.95.86/imgsvr/ecw.aspx?";
   opcionesComboIPServidor[CANARIAS_IDECAN] = "http://idecan3.grafcan.es/ServicioWMS/";
   opcionesComboIPServidor[MADRID] = "http://www.madrid.org/servidorbaldosas/tilesserver.aspx?";
   opcionesComboIPServidor[RIOJA] = "http://sigpac.larioja.org/";
   opcionesComboIPServidor[IGN] = "http://www.idee.es/wms/IDEE-Base/IDEE-Base?";                                            
   opcionesComboIPServidor[ANDALUCIA] = "http://desdeelcielo.andaluciajunta.es/desdeelcielo/";   
   opcionesComboIPServidor[NAVARRA] = "http://idena.navarra.es/navegar/asp/mime.aspx?";  
   opcionesComboIPServidor[EXTREMADURA] = "http://62.175.245.28/tilesserver11/tilesserver.aspx?";
   opcionesComboIPServidor[CyL_v3] = "http://217.71.16.147/imageserver/aspimagedispatcher.aspx?function=getutmbitmap&";
   opcionesComboIPServidor[EUS_v3] = "http://62.99.86.165/imagenes/aspimagedispatcher.aspx?function=getutmbitmap&";
   opcionesComboIPServidor[CVALENCIANA] = "http://llagosta.cma.gva.es/";
   opcionesComboIPServidor[ARAGON_SITAR] = "http://sitar.aragon.es/AragonWMS?";
   opcionesComboIPServidor[NAVARRA1950] = "http://sitna.tracasa.es/navegar/asp/mime.aspx?";
   opcionesComboIPServidor[NAVARRA1000] = "http://idena.navarra.es/navegar/asp/mime.aspx?";          
   opcionesComboIPServidor[RECINTOS] = "http://wms.mapa.es/wms/wms.aspx?";
   opcionesComboIPServidor[PARCELAS] = "http://wms.mapa.es/wms/wms.aspx?";  
   opcionesComboIPServidor[IBERPIX] = "http://www2.ign.es/iberpix/tileserver/";
   opcionesComboIPServidor[IDEANDALORTO] = "http://www.ideandalucia.es/wms/ortofoto2009?";
   
   abreviaturaServidor[NACIONAL] = "ESP";    
   abreviaturaServidor[PNOA] = "PNO"; 
   abreviaturaServidor[CyL] = "CYL";
   abreviaturaServidor[GALICIA] = "GAL";
   abreviaturaServidor[ASTURIAS] = "AST";			
   abreviaturaServidor[PAISVASCO] = "EUS";
   abreviaturaServidor[ARAGON] = "ARA";		
   abreviaturaServidor[MANCHA] = "CLM";	
   abreviaturaServidor[MURCIA] = "MUR";
   abreviaturaServidor[CANARIAS_SIGPAC] = "CAN";
   abreviaturaServidor[CANARIAS_SITCAN] = "CSI";
   abreviaturaServidor[CANARIAS_IDECAN] = "CDE";			
   abreviaturaServidor[MADRID] = "MAD";
   abreviaturaServidor[RIOJA] = "RIO";
   abreviaturaServidor[IGN] = "IGN";		
   abreviaturaServidor[ANDALUCIA] = "AND";
   abreviaturaServidor[NAVARRA] = "NAV";
   abreviaturaServidor[EXTREMADURA] = "EXT";
   abreviaturaServidor[CyL_v3] = "CL3";
   abreviaturaServidor[EUS_v3] = "EU3";
   abreviaturaServidor[CVALENCIANA] = "GVA";
   abreviaturaServidor[ARAGON_SITAR] = "ARS";
   abreviaturaServidor[NAVARRA1950] = "N50";
   abreviaturaServidor[NAVARRA1000] = "N10";
   abreviaturaServidor[RECINTOS] = "REC";
   abreviaturaServidor[PARCELAS] = "PAR";
   abreviaturaServidor[IBERPIX] = "IBX";
   abreviaturaServidor[IDEANDALORTO] = "IAO";
   
   
   // La variable "DatumServidorWGS84" contiene un false para indicar que el servidor correspondiente
   // funciona con ED50 o un valor true si funciona con WGS84:
   DatumServidorWGS84[NACIONAL] = false;
   DatumServidorWGS84[PNOA] = false;
   DatumServidorWGS84[CyL] = false;
   DatumServidorWGS84[GALICIA] = false;
   DatumServidorWGS84[ASTURIAS] = false;
   DatumServidorWGS84[PAISVASCO] = false;
   DatumServidorWGS84[ARAGON] = false;
   DatumServidorWGS84[MANCHA] = false;
   DatumServidorWGS84[MURCIA] = false;
   DatumServidorWGS84[CANARIAS_SIGPAC] = true;
   DatumServidorWGS84[CANARIAS_SITCAN] = true;
   DatumServidorWGS84[CANARIAS_IDECAN] = true;
   DatumServidorWGS84[MADRID] = false;
   DatumServidorWGS84[RIOJA] = false;    
   DatumServidorWGS84[IGN] = false;
   DatumServidorWGS84[ANDALUCIA] = false;
   DatumServidorWGS84[NAVARRA] = false;
   DatumServidorWGS84[EXTREMADURA] = false;
   DatumServidorWGS84[CyL_v3] = false;
   DatumServidorWGS84[EUS_v3] = false;
   DatumServidorWGS84[CVALENCIANA] = false;
   DatumServidorWGS84[ARAGON_SITAR] = false;
   DatumServidorWGS84[NAVARRA1950] = false;
   DatumServidorWGS84[NAVARRA1000] = false;
   DatumServidorWGS84[RECINTOS] = false;
   DatumServidorWGS84[PARCELAS] = false;
   DatumServidorWGS84[IBERPIX] = true;
   DatumServidorWGS84[IDEANDALORTO] = false;
 	
 	
   visor[NACIONAL] = "http://sigpac.mapa.es/fega/visor/";
   visor[PNOA] = "http://sigpac.mapa.es/fega/visor/";
   visor[CyL] = "http://www.sigpac.jcyl.es/visor/";
   visor[GALICIA] = "http://www.xunta.es/visor/";  // http://emediorural.xunta.es/visor5/
   visor[ASTURIAS] = "http://sigpac.princast.es/visor/";
   visor[PAISVASCO] = "http://arc.ikt.es/sigpac/";
   visor[ARAGON] = "http://sigpac1.aragob.es/visor/";		
   visor[MANCHA] = "http://sigpac.jccm.es/visorsigpac/";
   visor[MURCIA] = "http://visoragri.carm.es/visor/";
   visor[CANARIAS_SIGPAC] = "http://sigpac.gobiernodecanarias.org/sigpac/visor/";
   visor[CANARIAS_SITCAN] = "http://mapa.grafcan.com/Mapa/";
   visor[CANARIAS_IDECAN] = "http://visor.grafcan.es/visorweb/";
   visor[MADRID] = "http://www.madrid.org/sigpac/"; /* "http://www.trescantossa.com/geomadrid/Navegar.aspx",*/
   visor[RIOJA] = "http://sigpac.larioja.org/visor/";
   visor[IGN] = "http://www.idee.es/clientesIGN/wmsGenericClient/index.html?lang=ES/";
   visor[ANDALUCIA] = "http://www.juntadeandalucia.es/agriculturaypesca/sigpac/";  /* Poner un link directo a http://www.juntadeandalucia.es/agriculturaypesca/sigpac/servlet/client  */   
   visor[NAVARRA] = "http://idena.navarra.es/navegar/?lang=";
   visor[EXTREMADURA] = "http://62.175.245.28/visor/";
   visor[CyL_v3] = "http://www.sigpac.jcyl.es/visor/";
   visor[EUS_v3] = "http://arc.ikt.es/sigpac/";
   visor[CVALENCIANA] = "http://orto.cth.gva.es/website/urbanismo/viewer.htm";
   visor[ARAGON_SITAR] = "http://sitar.aragon.es/visor/";
   visor[NAVARRA1950] = "http://sitna.tracasa.es";  /* Aunque hay otro que es: http://sigpac.navarra.es/navegar/ */
   visor[NAVARRA1000] = "http://idena.navarra.es/navegar/?lang=";
   visor[RECINTOS] = "http://www.idee.es/clientesIGN/wmsGenericClient/index.html?lang=ES";
   visor[PARCELAS] = "http://www.idee.es/clientesIGN/wmsGenericClient/index.html?lang=ES";
   visor[IBERPIX] = "http://www.ign.es/iberpix/visoriberpix/visorign.html";
   visor[IDEANDALORTO] = "http://www.ideandalucia.es/index.php/es/servicios/visualizacion-wms/44-servicios-de-ortofotos-y-ortoimagnees-generales/178-ortofotografia-expedita-de-andalucia-ano-2008-2009";
   

   version4[NACIONAL] = true;
   version4[PNOA] = false;
   version4[CyL] = true;
   version4[GALICIA] = false;
   version4[ASTURIAS] = true;
   version4[PAISVASCO] = true;
   version4[ARAGON] = true;
   version4[MANCHA] = true;
   version4[MURCIA] = true;
   version4[CANARIAS_SIGPAC] = true;
   version4[CANARIAS_SITCAN] = false;
   version4[CANARIAS_IDECAN] = false;
   version4[MADRID] = true;
   version4[RIOJA] = true;    
   version4[IGN] = false;
   version4[ANDALUCIA] = false;
   version4[NAVARRA] = false;
   version4[EXTREMADURA] = true;
   version4[CyL_v3] = false;
   version4[EUS_v3] = false;
   version4[CVALENCIANA] = false;
   version4[ARAGON_SITAR] = false;
   version4[NAVARRA1950] = false;
   version4[NAVARRA1000] = false;
   version4[RECINTOS] = false;
   version4[PARCELAS] = false;
   version4[IBERPIX] = true;
   version4[IDEANDALORTO] = false;
 
 
   vistaPajaro[NACIONAL] = true;
   vistaPajaro[PNOA] = true;
   vistaPajaro[CyL] = true;
   vistaPajaro[GALICIA] = true;
   vistaPajaro[ASTURIAS] = false;
   vistaPajaro[PAISVASCO] = true;
   vistaPajaro[ARAGON] = false;
   vistaPajaro[MANCHA] = false;
   vistaPajaro[MURCIA] = false;
   vistaPajaro[CANARIAS_SIGPAC] = false;
   vistaPajaro[CANARIAS_SITCAN] = true;
   vistaPajaro[CANARIAS_IDECAN] = true;
   vistaPajaro[MADRID] = false;
   vistaPajaro[RIOJA] = true;
   vistaPajaro[IGN] = true;
   vistaPajaro[ANDALUCIA] = true;
   vistaPajaro[NAVARRA] = true;
   vistaPajaro[EXTREMADURA] = false;
   vistaPajaro[CyL_v3] = true;
   vistaPajaro[EUS_v3] = true;
   vistaPajaro[CVALENCIANA] = true;
   vistaPajaro[ARAGON_SITAR] = false; 
   vistaPajaro[NAVARRA1950] = true;
   vistaPajaro[NAVARRA1000] = true;
   vistaPajaro[RECINTOS] = true;
   vistaPajaro[PARCELAS] = true;
   vistaPajaro[IBERPIX] = true;
   vistaPajaro[IDEANDALORTO] = true;
    		                       				                                                                                                            	
 } 		             
 		                           				                                          
 public Servidor(String nombre_url_abreviatura)
 {
    this();
    id = -1;
    if (nombre_url_abreviatura == null)
    {
       id = -1;
       nombre = null;
       abreviatura = null;
       http = null;
       return;
    }
    
    if (nombre_url_abreviatura.length() == 3)
    {
       if (ServidorValido(nombre_url_abreviatura) == false)
          return;
       abreviatura = nombre_url_abreviatura;
       nombre = NombreServidor(nombre_url_abreviatura);
       http = URLServidor(nombre_url_abreviatura);
    } else if (nombre_url_abreviatura.startsWith("http"))
    {
       http = nombre_url_abreviatura;
       abreviatura = AbreviaturaServidor(nombre_url_abreviatura);
       nombre = NombreServidor(nombre_url_abreviatura);       
    } else {
       nombre = nombre_url_abreviatura;
       abreviatura = AbreviaturaServidor(nombre_url_abreviatura);
       http = URLServidor(nombre_url_abreviatura);	
    }
    
   id = GenerarId(); 
 }
   
 String Nombre()
 {
    if (id == -1)
       return null;
    return opcionesComboServidor[id];
 }
 
 String Abreviatura()
 {
    if (id == -1)
       return null;
    return abreviaturaServidor[id];
 }
 
 String Url()
 {
    if (id == -1)
       return null;
    return opcionesComboIPServidor[id];
 }
 
 int Id()
 {
   return id;
 }
   

 boolean ServidorValido()
 {
    return ServidorValido(nombre);
 }
 
 // Devuelve "true" si el servidor pasado esta dentro de los posibles:
 static boolean ServidorValido(String unServidor)
 {
    JSigpac.Traza("ServidorValido  unservidor="+unServidor);
    if (unServidor == null)
       return false;
       
    for (int i=0; i<opcionesComboServidor.length; i++)
    {
    	if (unServidor.equals(opcionesComboServidor[i]) ||
    	    unServidor.equals(abreviaturaServidor[i]) )
    	{
           JSigpac.Traza("ServidorValido devuelve true");
    	   return true;
    	}
    }
    JSigpac.ImprimirLinea("Servidor no reconocido: " + unServidor);
    return false; 	
 }
 
 
 /*
     Podemos recibir o bien la URL de un servidor o bien la abreviatura de un servidor.
     De cualquiera de las maneras, lo que se devuelve es el nombre del servidor correspondiente.
  */
 static String NombreServidor(String cadena)
 {
    if (cadena == null)
       return opcionesComboServidor[0];
    
    if (cadena.startsWith("http"))
    {
       for (int i=0; i<opcionesComboServidor.length; i++)
       {
       	   if (cadena.equals(opcionesComboIPServidor[i]))    	
    	      return opcionesComboServidor[i];
       }
    } else if (cadena.length() == 3)
    {
       for (int i=0; i<abreviaturaServidor.length; i++)
       {
       	   if (cadena.equals(abreviaturaServidor[i]))    	
    	      return opcionesComboServidor[i];
       }
    }	
    
    return opcionesComboServidor[0]; 	
 }	
 
 /*
     Podemos recibir o bien el nombre de un servidor o bien la dirección http de un servidor.
     De cualquiera de las maneras, lo que se devuelve es la abreviatura del servidor correspondiente.
  */
  
 static String AbreviaturaServidor(String elServidor)
 {
    JSigpac.Traza("AbreviaturaServidor  elServidor="+elServidor);
    if (elServidor == null)
       return abreviaturaServidor[0];
     
    if (elServidor.length() == 3) // Puede tratarse ya de una abreviatura. Por ahora no hacemos NINGUNA comprobacion mas.
       return elServidor;
    else if (elServidor.startsWith("http"))
    {
       for (int i=0; i<opcionesComboIPServidor.length; i++)
       {
    	   if (elServidor.equals(opcionesComboIPServidor[i]))    	
    	      return abreviaturaServidor[i];
       }
    } else
    {  
       for (int i=0; i<opcionesComboServidor.length; i++)
       {
    	   if (elServidor.equals(opcionesComboServidor[i]))    	
    	      return abreviaturaServidor[i];
       }
    }
    
    return abreviaturaServidor[0]; 	
 }
 
 /*
     Podemos recibir o bien el nombre de un servidor o bien la abreviatura de un servidor.
     De cualquiera de las maneras, lo que se devuelve es la URL.
  */
 static String URLServidor(String cadena)
 {
    if (cadena == null)
       return opcionesComboIPServidor[0];
    
    if (cadena.length() == 3)
    {
       for (int i=0; i<abreviaturaServidor.length; i++)
       {
       	   if (cadena.equals(abreviaturaServidor[i]))    	
    	      return opcionesComboIPServidor[i];
       }
    } else
    {
       for (int i=0; i<opcionesComboServidor.length; i++)
       {
       	   if (cadena.equals(opcionesComboServidor[i]))    	
    	      return opcionesComboIPServidor[i];
       }
    }	
    
    return opcionesComboIPServidor[0]; 	
 }	
 
 // Devuelve la URL del servidor a partir del indentificador:
 static String URLServidor(int identificador)
 {
    return opcionesComboIPServidor[identificador];	
 }
 
 int GenerarId()
 {
   int i;
   
   for (i=0; i<opcionesComboIPServidor.length; i++)
   {
       if (abreviatura.equals(abreviaturaServidor[i]) )
          return i;
   }   
   return -1; 	
 }  
 
 static int IdentificadorServidor(String servidor)
 {
   int i;
   
   for (i=0; i<opcionesComboIPServidor.length; i++)
   {
       if (servidor.equals(opcionesComboIPServidor[i]) ||
           servidor.equals(abreviaturaServidor[i]) )
          return i;
   }
   
   return -1; 	
 } 
 
 
 
 /* Devuelve true si estamos utilizando el servidor del SigPac de la versión 4 o superior.
    OJO !! que hasta ahora, la forma de saber si el visor era de la version 4.0 o superior consistía 
    mirar si en la IP de las peticiones aparecía la palabra "tilesserver". Esto sucede por 
    ahora para el servidor Nacional y para el servidor de Canarias.
  */
 boolean EsVersion4()
 {
    //return nombre.indexOf("tilesserver") == -1 ? false : true; 	
    // Ahora lo hacemos de otra forma: Si el id del servidor es el 0 o el 8, pues es version 4:
    //if (id == 0 || id == 8 || id == 11)
    //   return true;
    //return false;
    return version4[id];
 }
 
  boolean HayVistaPajaro()
 {    
    return vistaPajaro[id];
 }
 
 Datum DameDatumDelServidor()
 {
    if (DatumServidorWGS84[id] == true)
       return Datum.datumWGS84;
    else
       return Datum.datumED50;
 }
 
}		