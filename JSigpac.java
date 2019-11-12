/* Posibles mejoras:
   - Curso de navegación y GPS:
     http://cursonavegacion-aweleitor.blogspot.com/
   - Web de descarga de mapas:
     http://iogrea.blogspot.com/2008/01/descarga-directa-de-mapas-de-espaa.html
   - Del Garmin y TopoHispania:
     http://foro.todopocketpc.com/showthread.php?t=169556
     Garmin MobileXT:  http://www.foromtb.com/showthread.php?t=245874
     TopoHispania para Garmin:  http://www.foromtb.com/showthread.php?t=185066
     Garmin y Topo España v3:  http://gpsando.blogspot.com/
     Perdidos con GPS: http://perdidoscongps.blogspot.com/2007/10/qu-mapas-seran-recomendables-tener-en.html
     Para hacer mapas vectoriales:  http://www.foromtb.com/showthread.php?t=77931
   
   - Añadir visor de Portugal:
     visor http://mapas.igeo.pt/igp/igp.phtml
     web http://www.igeo.pt/
     WMS: http://mapas.igeo.pt/
          http://mapas.igeo.pt/igp/Capabilities/wms_continente_GetCapabilities.xml
      
   + Ver si funciona el servidor de Aragón   
   + Solucionado el error por no inicializar la clase Servidor 
   - Añadir waypoints al jSIGPAC   
   + Añadir anuncio sobre Hacienda:  http://www.ioncomunicacion.es/noticia.php?id=2148  
   - Añadir posibilidad de leer archivos kml: 
        Userguide:  http://earth.google.es/userguide/v4/ug_kml.html
        Reference:  http://code.google.com/apis/kml/documentation/kmlreference.html
        Tutorial:   http://code.google.com/apis/kml/documentation/kml_tut.html
        Google Maps API concepts: http://code.google.com/apis/maps/documentation/index.html#XML_Overlays
   - Modificar la forma de calibrar para Ozi para que lo haga en coordenadas geográficas (latitud y longitud) y no con coordenadas UTM.
     De esta forma, cuando cargamos un mapa en el smartCom que pertenece a dos husos, no se muere el programa.
     Ver información sobre calibración de Ozi:    http://www.elgps.com/ozi/calibrarmapa/calibrarconozi.html
    */


/* Para usar mySQL:
 
    Connection Information
    User Credentials 	 
    Username 	N/A (project contributor status required)
    Password 	N/A
 
    JDBC Specifics 	 
    Driver Class 	org.gjt.mm.mysql.Driver
    URL 	jdbc:mysql://mysql.mcs2.netarray.com/<username>


    The code below requests a new connection to the database via JDBC. By replacing <password> with the actual password (please see above), a connection should be returned successfully.


    Class.forName("org.gjt.mm.mysql.Driver");

    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://mysql.mcs2.netarray.com/<username>",
        "<username>",
        "<password>");

    // ... 

    conn.close();
*/
/* Mejoras para la en 5.6.13
- Parece que no va el servidor Aragon SITAR (contiene ortofotos de 2009)
- Mirar el nuevo servidor de Baleares. Su visor es: http://ideib.caib.es/visualitzador/visor.jsp
- Mirar GoogleZoom
*/

/* Mejoras incluidas en 5.6.12
 - En el servidor de Iberpix se quitan las limitaciones de las resoluciones (Ver rutina ComprobarResolucion)   
 */
 
/* Mejoras incluidas en 5.6.11
 - Se añaden los servidores de Iberpix y IDE-Andalucía (Orto2009).   
 */
 
/* Mejoras incluidas en 5.6.10
 - Solucionado el error del que hablaba el usuario "rizome" en el foro:
   http://foro.todopocketpc.com/showpost.php?p=1528177&postcount=1960
   
   Está intentando descargar un mapa que tiene partes de Francia, con lo que al pedir un cuadrante fuera de los limites de España se queda frito.
   Además, a la hora de ensamblar, al no tener el cuadrante, pues reintenta la descarga pero el resultado va a ser siempre el mismo, que no se
   consigue descargar. 
   El problema estaba en que en el fichero "Descargar.java" que es donde se descarga el cuadrante se estaba haciendo una llamada a "read" que 
   al ser bloqueante provocaba que se mantuviera en espera 120 segundos antes de devolver el control. Primero había substituido la llamada a "read" 
   por la llamada a "available()".... pero esto no nos vale. 
   Al final he detectado que cuando se pide un cuadrante feura de la órbita del SigPac, te devuelve un mensaje de error y lo que he hecho es 
   mirar si recibo ese mensaje y en caso de recibirlo, pues me creo un cuadrante en blanco.     
   
 */
 
/* Mejoras sobre 5.6.4
   - Error comentado en el foro al intentar bajarse la isla de La Palma (huso 28 y 29)
         http://foro.todopocketpc.com/showpost.php?p=1353532&postcount=1543
         http://foro.todopocketpc.com/showpost.php?p=1355448&postcount=1547
         Captura de pantalla: http://foro.todopocketpc.com/attachment.php?attachmentid=31410&d=1226459001
         
   - Intentar poder especificar el cuadranton:
         http://foro.todopocketpc.com/showpost.php?p=1344844&postcount=1540
         http://foro.todopocketpc.com/showpost.php?p=1344844&postcount=1541     
   - Que al descargar mapas contiguos meta un pequeño solapamiento, por ejemplo, especificado en %.
         http://foro.todopocketpc.com/showpost.php?p=1362008&postcount=1569 
         
   - Servidor de Portugal: [TPPC Foro] Respuesta a 'SIGPAC: Creación de grandes mapas'      
       La web es:  http://www.igeoe.pt/
       El visor es:   http://www.igeoe.pt/igeoesig/igeoesig.asp
       En relación con el Google Earth:   http://www.igeoe.pt/google_earth/inf_google_earth.asp

       Lo de Google Earth tiene relación con el webservices que ofrece el servidor de Portugal:
       Me he bajado el fichero http://www.igeoe.pt/google_earth/IGeoE%20-%20Informa%C3%A7%C3%A3o%20de%20cidadania.zip
       y ahí he visto este link:

         http://www.igeoe.pt/igeoearcweb/wmsconnector/com.esri.wms.Esrimap/WebMapSrv?VERSION=1.1.1&amp;REQUEST=GetMap&amp;SRS=EPSG:4326&amp;WIDTH=512&amp;HEIGHT=512&amp;LAYERS=1,2,3,4,5&amp;STYLES=default,default,default,default,default&amp;TRANSPARENT=TRUE&amp;FORMAT=image/png&amp;BGCOLOR=0xfffffe&amp;
    
    + Archivo ".sat" para TomTom
      http://foro.todopocketpc.com/showpost.php?p=1370241&postcount=1589  >>> Ojo que está mal el orden de las latitudes y longitudes
      http://foro.todopocketpc.com/showpost.php?p=1394932&postcount=1635

	En cuanto a los archivos del tomtom, son 9 lineas.
	Por ejemplo
	Valverde_0-0_.jpg
	-1.8858923351450714
	41.99458466213703
	-1.8706040115863827
	41.98521259845109
	0
	60
	1280
	1024
	 
    + Hacer que al pulsar con el botón derecho del ratón sobre "Fichero:" se genere un nombre de fichero a partir del formato especificado en dat\jsigpac.formato
    
    - Web del jSIGPAC:
       http://www32.websamba.com/jrariasf/jSIGPAC/sigpac.html
       http://code.google.com/p/jsigpac/
       http://sites.google.com/site/jrariasf/
    
    + Ver error de http://foro.todopocketpc.com/showpost.php?p=1390353&postcount=1624
         Caused by: java.lang.StringIndexOutOfBoundsException: String index out of range: -1
         at java.lang.String.substring(Unknown Source)
         at java.lang.String.substring(Unknown Source)
         at JSigpac.ComprobarVersion(JSigpac.java:2021)
         at JSigpac.main(JSigpac.java:3076)
    - Me comenta Florencio por email:
      * En el visor WEb del SITCAN (http://mapa.grafcan.com/Mapa/) aparece un mapa topográfico escala 1:5000, 
      mientras que en el Jsigpac lo máximo que puedo marcar es escala 1:25000. En este caso, ¿Lo que bajo es 
      escala 1:5000 (lo que hay en el visor) o 1:25000 (lo que marco en el jsigpac)?
      
      Otra cosa que comenta:
      * Existe otro visor del Gobierno de Canarias, cuya dirección es http://visor.grafcan.es/visorweb/ 
      (Portal IDE Canarias), en el que, a mi parecer, los mapas topográficos escala 1:5000 tienen mucha más calidad 
      que los mapas de http://mapa.grafcan.com/Mapa/. independientemente del sombreado que se puede activar/desactivar.  
      En este caso, ¿es posible configurar el jsigpac para que obtenga los mapas de de http://visor.grafcan.es/visorweb 
      en vez del servidor que viene por defecto??
      
      Y más cosas de floren:
      * Por si te interesa, he podido abrir en el Compe la cartografía WMS de la página IDE Canarias, 
        mediante la URL  http://idecan2.grafcan.es/ServicioWMS/carto5
 
    + Me comenta Itziar por email (Itziar@lurgeroa.com):  >>> He dejado ese servidor como NAVARRA1950 y he creado de nuevo el de NAVARRA con las ortofotos de 2008
      "Escribo porque estoy intentando descargar ortofotos de Navarra (en el servidor del gobierno de Navarra están 
      colgadas desde 1950 hasta ahora, y las más recientes son del año pasado), y cada vez que pongo la consola,
      me descargo las de 1950. ¿Hay alguna forma de solucionarlo? Gracias."
      
    - Parámetros -Xmx900m  y -Xms100m:
      http://hermosodia.wordpress.com/2008/12/16/java-entendiendo-los-parametros-xmx-y-xms-parte-i/
      
    + Mejora propuesta: Añadir una opción para que desde línea de comandos no se borren los ficheros intermedios
      http://foro.todopocketpc.com/showpost.php?p=1344854&postcount=1541
      
    + Mirar este problemilla al importar datos de un GPX:
      http://www.foroware.com/forum/forum_posts.asp?TID=934&PID=21306#21306
      Ya se tiene en cuenta el datum de entrada seleccinado en el jSIGPAC cuando se importa un fichero GPX.
       
    + Debe haber dejado de funcionar el servidor de Andalucía por lo que comentan en el foro y en un privado de "Langosta" --->> AVISAR a "Langosta" del cambio cuando lo tenga.
    >>>> He probado a descargarme una zona de Almería y me ha funcionado.
    
    + Problema con el servidor de Murcia: Me lo comenta por email
     "Estimado amigo: Me he tomado el atrevimiento de escribirte para comentarte que  al intentar descargar una ortofoto del 
     servidor de Murcia, no es posible, del servidor general sí, pero ocurre que las ortofotos no están igual de actualizadas, 
     las de Murcia son más nuevas. No sé si esto ocurre porque al pinchar en la dirección web que le has 
     puesto (http://147.84.210.4/linksigpac.htm) , para entrar al visor debe redireccionarse a una nueva pantalla 
     que (http://visoragri.carm.es/visor/)  nos dirige al visor, cosa que no ocurre con el link que has puesto en el 
     general de España. Se podría solucionar este problemilla?"
     
    + Añadir posibilidad de descargar RECINTOS y PARCELAS (Ver email de Javier del Pozo Baselga)
    
    - Añadir en la web la explicación de cómo usar el jSIGPAC en Mac:
      http://foro.todopocketpc.com/showthread.php?p=1451138#post1451138
      

*/

/* Mejoras sobre 5.6.2
   + Error al calibrar para myMotion: http://foro.todopocketpc.com/showpost.php?p=1258344&postcount=1444
   + Error de kkmail que volvía a preguntar si queríamos descargar porque no se propagaba la opción -Y
   Jsigpac -x -SESP -DED50 -mORTO -q -h30 -x727996 -y4233130 -A14336 -H9472 -r2.000 -c -w -f"G:\Mapas\mapas jsigpac\ORTOm894_1_1_" -J70 -Yn
   + Quitar un comentario kkmail en "Descargar.java":
     System.err.println("kkmail: Hemos borrado el fichero: " + fichero); //kkmail
   + Poder importar de un fichero de calibración al igual que se hace desde un fichero de track o de ruta (Hecho ya para Compe: FicheroCalibracionCompe()
   + Importa ficheros KML
   + He comentado las llamadas a System.gc(), especialmente la que se hacía en ThreadEnsamblar.java despues de borrar los ficheros intermedios.

 */


/* Mejoras sobre 5.6.1
   + El nombre del fichero de calibración de MyMotion es "cordinates.txt" y no "cordinate.txt".
   
 */


/* jSIGPAC 5.6.1
   + Se ha solucionado un error al convertir el datum de un ficheros de waypoints de Ozi en el que los waypoints no van numerados sino que empiezan todos por -1.
*/

/*
   Mirar lo de MyMotion que me comentan en el foro de:
   - foro: http://www.pdaexpertos.com/foros/viewtopic.php?p=359356#359356
   - MyMotion:  http://users.bigpond.net.au/ian_pendlebury/mymad.htm
   - TGA: http://www.cs.hut.fi/~framling/JVG/
 */
   
/* Mejoras sobre 5.5.5  >>>  5.5.6
   + Solucionado error en servidor de Madrid. Hasta ahora, restaba uno al cuadrante descargado pero han debido actualizar
     el visor y he modificado esa particularidad del código del jSIGPAC con el servidor de Madrid.
   + Actualización del servidor de Galicia a la versión 5
*/

/* Mejoras sobre 5.5.4  >>>  5.5.5
   + He modificado la rutina ReintentarDescargaCuadrante()  porque creo que no se descargaba el cuadrante
     ya que estando los threads activados, sólo se hacía el PutDescarga pero luego no se daba al start(). 
     Así que pahora se ponen los threads en false para que se haga la descarga del cuadrante como antes.
 */

/* Mejoras sobre 5.5.2

   ** WMS:
      http://www.opengeospatial.org/standards/wms
      
     IDEE:
     http://www.idee.es/show.do?to=pideep_ambito_regional_SIG.ES
   
   - Añadir explicación de los datum de: http://foro.todopocketpc.com/showpost.php?p=1225016&postcount=1337
   
   - Me comentan por email lo siguiente: "La cartografía topográfica del servidor de Navarra da la de 3D."
       
   + Añadir lo del IBP: 
     http://www.ibpindex.com/esp/manual.asp
     
   + Poder convertir tracks en rutas para el Ozi de la PDA (para poder navegar con ellas):
     Formato:  (Siempre en WGS 84)
     
        H1,OziExplorer CE Route2 File Version 1.0
	H2,WGS 84
	H3,12-04-2008 21:06:20,,0
	W,RW001,  41.6317794,  -4.7283218,0
	W,RW002,  41.6293759,  -4.7276333,0
	W,RW003,  41.6275976,  -4.7284981,0
	W,RW004,  41.6272987,  -4.7292103,0
	W,RW005,  41.6272194,  -4.7296884,0
	W,RW006,  41.6271711,  -4.7305034,0
	W,RW007,  41.6270580,  -4.7308365,0
	W,RW008,  41.6250996,  -4.7316963,0
	W,RW009,  41.6247021,  -4.7317817,0
	W,RW010,  41.6242922,  -4.7326830,0
	W,RW011,  41.6240005,  -4.7329153,0
	W,RW012,  41.6193907,  -4.7351922,0
	W,RW013,  41.6190651,  -4.7352795,0
	...
     
     
        >>> Estoy añadiendo una pestaña Utilidades.... Ver fichero Utilidades.java y el que ya había: Conversor.java.
            Igual podemos meter la pestaña Conversor dentro de Utilidades.
   - Intentar añadir los servidores de PNOA nuevos:
     - Servidor de ortofotografías aéreas:
       http://www.idee.es/clientesIGN/wmsGenericClient/index.html?lang=ES
     + El servidor abierto a las aplicaciones cliente y adaptado a las especificaciones del OGC:
       http://www.idee.es/wms/PNOA/PNOA
       
   + Sobre el servidor de Andalucía y la barra de escala: (habrá que ver si al poner cuadrantes de 512x512 va mejor o no)
     http://foro.todopocketpc.com/showpost.php?p=1202530&postcount=1284
     
   + Mirar comentario de gautxori en el foro: 
   http://foro.todopocketpc.com/showpost.php?p=1189571&postcount=1250
      + gautxori descarga y ensambla desde línea de comandos... ver si al realizar una descarga y cambiar el
        número de filas y de columnas, se ensambla bien... Es decir, si ensambla con el núevo número de filas y columnas
        y lo mismo si calibra bien.
      + Poder especificar en un fichero el formato del nombre del fichero. Por ejemplo: UTM30_WGS84_580896_4796544_32_32 
        http://foro.todopocketpc.com/showpost.php?p=1189781&postcount=1252
        Se ha añadido la opción "-F" y hay que tener en cuenta el fichero "dat\jsigpac.formato"
        Un ejemplo de sintaxis podría ser:
        #"MapaGalicia_"#X#Y#"huso_"#huso#
          
   - Mirar email sobre goolzoom:
      http://www.tecnologiainmobiliaria.net/2007/08/pnoa-ortofotos-de-toda-espaa.html
      http://ovc.catastro.meh.es/
      http://www.earthtools.org/
      http://www.jstott.me.uk/jscoord/
      PNOA: http://www.ign.es/ign_iberpix/index.htm
      
   + He cambiado una cosa relacionada con el puerto proxy porque al parecer siempre intentaba conectarse con el puerto 80
   + Link al tutorial de IVREO:
     http://rapidshare.com/files/104101500/Tutorial_jSIGPAC_-__Crear_mapas_para_Ozi__Compe_GPS_y_GPS_Tuner_desde_SIGPAC___by_IVREO___v1.1.rar
      
   - Cuando empiezas a descargar algo y le das a STOP... y luego vuelves a intentar descargar algo (cambiar el nombre del fichero), no pone a 0 
     el contador de la barra de progreso al principio sino que lo debe hacer despues de haber hecho los PutDescarga().
     >>> No he tocado nada, debe ser un tema interno del AWT de Java.
      
   + Problemas de un usuario que pide un mapa con coordenadas de un huso diferente (x>750000)... con lo que el jSIGPAC no se entera
     de que está pidiendo coordenadas fuera de huso ya que lo que hace el jSIGPAC hasta ahora es ver si el mapa que se pide pertenece
     a dos husos diferentes:
     http://foro.todopocketpc.com/showpost.php?p=1209741&postcount=1298

*/

/* Mejoras sobre 5.5.1  --->>>  5.5.2:
   + Al usar la opción "-x" para descargar y ensamblar, si falla en la descarga 
     de un cuadrante ahora sí seguirá con el ensamblado ya que se dará cuenta 
     que falta ese cuadrante y reintentará la descarga (buscar "_kkmail_" en el código)
   + Solucionado error con el servidor de Castilla y León ya que no había sido configurado 
     su visor como perteneciente a una versión igual o superior a la 4.0
 */

/* Mejoras sobre 5.5  --->>>  5.5.1:
   + Añadido servidor de la Generalitat Valenciana
 */

/* Mejoras sobre 5.4  ---->>>>  5.5:
   + Modificados los servidores regionales actualizados al visor 4.0 y superior
   + Añadidas resoluciones de 0.125 y 0.250 para las ortofotos
   + Ya no se limita el número de puntos del track o ruta importada a 5000.
 */

/* Temas pendientes:
 *  - Mirar cómo añadir servidores extranjeros. Por ejemplo, este de Francia:
      http://www.geoportail.fr/visu2D.do?ter=metropole
 */
 
/*  Mejoras para v5.3
 *  + En Calculadora.java, en Completar() en sexagesimal no se pueden poner valores negativos en los grados cuando es 0º W
 *    Así que habrá que poner un botón que valga W o E tanto en el panel de arriba como abajo. 
 *    Por ahora lo he hecho funcionar tecleando: 0º -31' 40"
 *  + Modificada la ayuda de la Calculadora "dat\calculadora.txt"
 *  + He modificado la forma de saber si un servidor es de la version 4 o superior (En Servidor.java utilizo un array llamado "version4")
 *  + Hay que tener en cuenta que las descargas de Canarias desde el servidor nacional son en WGS84
 *    Mirar post:  http://foro.todopocketpc.com/showpost.php?p=1132283&postcount=244
 *    En ObtenerURL, al principio se mira si el datum del servidor es diferente al de los datos de entrada.... no sé si puede
 *    haber problemas entre los cálculos que se hacen aquí y los que se hacen a la hora de calibrar.
 *    *****  Estoy modificando OpcionesMapa::RecalcularCoordenadas() porque si en el datum de JSigpac ponemos WGS84, al pulsar
 *           con el botón derecho del ratón sobre "Descargar", cada vez calcula un nuevo ancho-----> Solucionado: He hecho un casting a int.
 *    
 *    OJOOO: Problemas al descargar el fichero "homician.plt". Parece que descarga bien el cuadrante 0_0 pero luego calcula
 *           mal el resto ----->>>>>>> Lo último ha sido la rutina:  TratarSingularidadCanaria()
 *
 *  + Otro error del SITCAN (ver email de amalahama@gmail.com): Tiene que ver con el huso 27 que no está disponible
 *           >>> Tener en cuenta que las coordenadas que da el visor del SITCAN siempre se refieren al huso 28 (aunque la zona occidental de la isla de El Hierro pertenezca al huso 27).
 *  + Poder elegir en qué formato generar los ficheros de calibración (poner un menú en la parte gráfica). Al final, se graba esa informacion
 *    en un fichero (que se leera siempre al principio).
 *             >>>>>>>> Utilizo el fichero "dat\jsigpac.calibrar" .
 *  + Mejora en el borrado de ficheros intermedios
 *  + Ver lo que pasa con el servidor de Asturias en:  (Y que no diga siempre que mires en la web del servidor nacional)
 *      http://foro.todopocketpc.com/showpost.php?p=1135176&postcount=1182
 *     >>>>> El problema estaba en la dirección utilizada para este servidor que ahora es:
 *              http://sigpac.princast.es/imageserver/  (en lugar de http://sigpac.princast.es/images/)
 *              (Modificar también el "jsigpac.server")
 *  + Mirar qué pasa con opciones.datumJSigpac cuando se pincha y arrastra un track sobre el icono del "jsigpac.exe" (porque a la hora de ObtenerURL() al principio mira el datum del Servidor y el del JSigpac.
 *    Ademas, no coge bien el servidor de "jsigpac.defecto"  (mirar homicianSIT.plt, homicioanESP.plt y homicianCAN.plt)
 *  + Cuando se leen los datos de un track o de una ruta, tener en cuenta que los valores de las esquinas van en ED50. Con lo cual,
 *    o bien modificamos el combo con el datum del JSIGPAC o bien hacemos que las coordenadas aparezcan en el datum que está elegido.
 *       Solución: He añadido la variable "datumSalida" en la clase "Fichero"
 *  + Mirar si al pinchar y arrastrar un track, se pueden generar varios mapas poniéndolo en "jsigpac.defecto"
 *           >>> Ya se puede hacer aunque haya que haber movido Roma con Santiago.
 *  + Cuando se pincha y arrastra un track, en Jsigpac.java [6903] en DespuesDeDescargar() pone la traza: "elsigpac=null"
 *           >>> Dejo la traza. es algo que puede pasar y esta controlado.
 *  + Mirar que el servidor de CyL es versión 5.0 y lo estoy tratando como antiguamente
 *            >>> Ahora tenemos el CYL y el CL3 (visor antiguo)... y la Vista global siempre sale porque usa el "CL3"
 *  + Mirar un problema que me dijeron cuando se descarga desde consola para decir que responda siempre que sí y así no se pare
 *    el proceso a preguntarte (Una posible solución es poner un -YS  o -YN o simplemente -Y para que se coja la respuesta por defecto)
 *    "Cuando ejecutas descargas (-d o –x) por lotes (a través de un .BAT) y ya tienes algún cuadrante te pide una entrada manual 
 *     preguntando si reescribe o no. Seria interesante poder decir en el comando de descarga un NO, para así poder dejarla por 
 *     la noche en forma desatendida."
 *           >>>> Añadido argumento "-Ys" o "-Yn" 
 *  - Mirar lo del proxy autentificado:  
 *              http://www.rgagnon.com/javadetails/java-0085.html
 *              http://www.jguru.com/faq/view.jsp?EID=9920
 *              http://www.javaworld.com/javaworld/javatips/jw-javatip42.html
 *              http://www.javasoft.com/products/jdk/1.1/docs/api/java.net.URLConnection.html
 *              http://www.developer.com/java/other/article.php/1551421
 *  + Reintentar la descarga de los cuadrantes fallidos
 *          >>>> Se añade el método Mapa::ReintentarDescargaCuadrante(..) que es llamado desde ThreadEnsamblar.
 *  + Descargar únicamente los cuadrantes por los que pasa el track o la ruta y así generar un mapa que ocupe menos.
 *          >>>> Nueva opción "-t"
 */


/*
   Mejoras para v5.2:
   - Mirar lo de publicidad
   - Entorno multithread real:
     * He visto que el proceso "java.exe" ocupaba 182 megas despues de hacer dos descargas de mapas
     * Parece que no funciona el multithread si se descarga un cuadrante concreto
     * Tampoco va el multithread si se le da a "descargar y ensamblar" a la vez       ---> linea 6266
     * Al imprimir el nombre del fichero con el cuadrante, hay veces que aparece el (i,j) y más adelante el fichero correspondiente.
     http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter10/concurrencyTools.html
   - [Hecho] Añadir nuevo servidor de Canarias:
     http://www.gobiernodecanarias.org/cm.../index.jsp#761
      (entrar en SITCAN y selecciona el Mapa Topográfico 1:5000)
   - [Hecho] Poder hacer una descarga masiva de cuadrantes pero luego decir que los ensamble en 4 mapas o los que sean.
   - En JSigpac.ImprimirLinea he comentado la linea en la que se dejaban de imprimir mensajes cuando se habia abortado el proceso de descarga
   - [Hecho] Hacer que en el jSIGPAC salga en donde lo de Nueva versión disponible..... un anuncio para que colabore con la fundación jSIGPAC.... y que al pinchar se abra el navegador en la web de "es-facil"... o añadir un botón "Colaborar fundación jSIGPAC"
     
   - [Hecho] Cuando se generan varios cuadrantones, comprobar si el fichero de ese cuadranton existe y en ese caso no se creará
   - [Hecho] Que se ponga bien el tamaño de la rejilla.
   - [Hecho] Ver lo del link y hacer que se abra el navegador 
   - [Hecho] Cambiar en la Fundación el link a la DLL:  http://www.upshare.eu/?d=2ADC63C711
   
   - Manual:  http://foro.todopocketpc.com/showpost.php?p=990016&postcount=889
   - Tutorial sobre el uso del jSIGPAC en conjunción con el CompeGPS para realizar planos calibrados:
     http://www.acsakura.org/index.php?option=com_content&task=view&id=18&Itemid=47
   - He inicializado opciones.optAncho y opciones.optAlto a ""
   - [Hecho] Hacer un contador de uso del jSIGPAC que guarde en un fichero en la web cada vez que se arranca un jSIGPAC
   - Entender el formato GPX
       http://www.topografix.com/GPX/1/1/
   - [Hecho] Poder poner varios anuncios
   - [Hecho] Hacer un contador de las veces que se pincha en cada anuncio (asociando un fichero a cada anuncio que contenga un contador)
     Para ello, en el fichero de la web "jsigpac.anuncio" además de "textoN" y "linkN" pueden añadirse más campos (incluso color de fondo del anuncio, etc...)
   - [Hecho] Modificado el servidor regional del JSigpac que ahora va en versión 5.1 (pero permito hacer Vista Pájaro)
   - [Hecho] Añadir un JLabel "web" al lado del desplegable con los servidores de forma que al pinchar te abre la web del visor asociado_
       Ojo !! Hay algún servidor regional que ha cambiado, por ejemplo el de La Rioja (el visor tiene versión 5.1 pero se puede 
              seguir accediendo a la antigua cartografía usando el formato de peticiones http anteriores).
   
 */  


/*  Cambios hechos sobre la 5.2 oficial:
   1.- Se permite obtener la Vista Global (o vista de pájaro) para el IGN topográfico
       * Buscar donde aparezca la cadena "_ll_". 
       * Se añade el método: boolean AjustesIniciales(String[] args, boolean vistaPajaro)
       * Se añade la variable "vistaPajaro" a la clase OpcionesMapa
       
   2.- Añadido servidor de Canarias SITCAN (Orto y Topo 1:15000)
   
   3.- Añadido servidor de Madrid (Orto y Topo 1:200000 y 1:2000000)
   
   4.- Añadido servidor de Extremadura
   
   5.- Variable de entorno JSIGPAC_NAVEGADOR
   
   6.- Nueva opción desde consola (-x) para descargar y ensamblar de un tirón. El resto de opciones es similar a la opción (-d) utilizada para descargar
       

   Documentación sobre hsqldb:
   http://hsqldb.org/web/hsqlDocsFrame.html
       
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.*;
//import java.awt.image.*;
import java.awt.event.*;
//import java.awt.image.renderable.*;
import javax.swing.*;
import javax.swing.border.*;
//import javax.swing.border.EtchedBorder;
//import javax.media.jai.*;
//import javax.media.jai.operator.*;
//import com.sun.media.jai.codec.*;
//import java.lang.reflect.*;

// EXIF:
//import com.drew.imaging.jpeg.*;
//import com.drew.metadata.*;
//import com.imagero.reader.jpeg.*;
//import com.imagero.reader.tiff.*;
//import com.imagero.reader.*;
//import com.imagero.reader.iptc.*;

// http://upload.ohshare.com/v/1999638/jSIGPAC_v5.1.4.zip.html
// .;.;C:\ARCHIV~1\JMF21~1.1E\lib\sound.jar;C:\ARCHIV~1\JMF21~1.1E\lib\jmf.jar;C:\ARCHIV~1\JMF21~1.1E\lib;



         
/*************************************************************************
 * 
 * Clase:   JSigpac
 * Autor:   Jose Ramon Arias Frances
 * Fecha:   6 de Febrero 2006
 * 
 * Funcion: Debido a que los mapas que ofrece el SIGPAC son demasiado pequeños,
 *          he desarrollado este código que en principio guardaba en una serie
 *          de ficheros una zona determinada.
 *          De esta forma, tenemos una matriz de imágenes cuyo ensamblado
 *          posterior va a constituir el mapa de toda la zona.
 *          *Nota1: Existe la versión del programa en forma de ejecutable de
 *                  de Windows (jsigpac.exe)
 *          *Nota2: Se han agrupado las clases Mapa y Ensamblador en un mismo
 *          fichero de forma que puedan ser utilizadas desde una misma
 *          aplicacion que llamaremos jSIGPAC. Aún así, pueden seguir siendo
 *          utilizadas por separado las clases Mapa y Ensamblador.
 *
 *   Uso con JAVA:     java [-Xmx200m] JSigpac [-d | -e] <resto_de_opciones>
 *   Uso en Windows:   jsigpac [-d | -e] <resto_de_opciones>
 *
 *          -d: Para descargar los mapas
 *          -e: Para ensamblar los mapas
 *          -Xmx200m: Establece en 200 megas el tamaño máximo de la pila de Java.
 *		      Suele ser necesario poner este parámetro con la opción "-e".
 *		      El valor 200 puede ser ampliado si diese alguna excepción
 *		      del tipo OutOfMemoryError.
 *                    La version para Windows permite modificar este valor a
 *                    traves de la varable de entorno JSIGPAC_MEM.
 *
 *   El <resto_de_opciones> depende de si se "descarga" o se "ensambla":
 *
 *   Para DESCARGAR:
 *   ==============
 *   java Mapa <-mORTO|-mTOPO> <-hhuso> [-q] <-xX> <-yY> <-aancho_en_metros>
 *                         <-filFILAS> <-colCOLUMNAS> [-c] [-s] [-sol] [-fnom_fich]
 *     ó
 *   java JSigpac -d <-mORTO|-mTOPO> <-hhuso> [-q] <-xX> <-yY> 
 *                        <-aancho_en_metros> <-filFILAS> <-colCOLUMNAS>
 *                        [-c] [-s] [-sol] [-fnom_fich]
 *     ó
 *   jsigpac -d <-mORTO|-mTOPO> <-hhuso> [-q] <-xX> <-yY>
 *                        <-aancho_en_metros> <-filFILAS> <-colCOLUMNAS>
 *                        [-c] [-s] [-sol] [-fnom_fich]
 *
 *   Para ENSAMBLAR:
 *   ==============
 *   java -Xmx200m Ensamblador <-fraiz_nom_fich> <-efilFILAS> <-ecolCOLUMNAS> [-Jcompresion]
 *     ó
 *   java -Xmx200m JSigpac -e <-fraiz_nom_fich> <-efilFILAS> <-ecolCOLUMNAS> [-Jcompresion]
 *     ó
 *   jsigpac -e <-fraiz_nom_fich> <-efilFILAS> <-ecolCOLUMNAS> [-Jcompresion]
 *
 ***********************************************************************************/

/** @class JSigpac
  * @brief Clase fundamental del jSIGPAC.
  */
public class JSigpac extends JFrame {

 static Mapa miMapa;
 static Ensamblador ens; 
 
 public static final long serialVersionUID = 0;
 public static final String version = "jSIGPAC v5.6.14";
 public static final String actualizado = "  Ultima actualizacion:  12 NOV 2010                     ";
 public static final String afiliado = "http://www.es-facil.com/ganar/alta?Id=64559373";
 //public static String siteJSIGPAC = "http://www32.websamba.com/jrariasf/jSIGPAC/";
 public static String siteJSIGPAC = "http://sites.google.com/site/jrariasf/";
 public static String webJSIGPAC; // = siteJSIGPAC + "sigpac.html"; //"http://www.myjavaserver.com/~jrariasf/sigpac/sigpac.html";
 public static String servlet; // = siteJSIGPAC + "jSIGPAC.asp?"; //"http://www.myjavaserver.com/servlet/jrariasf.servlet.sigpac.JSigpac_Servlet?";
 public static final String creativecommons = "http://creativecommons.org/licenses/by-nc-nd/3.0/deed.es";
 public static final String FORMATO = "dat" + File.separator + "jsigpac.formato";	
 public static boolean ejecutableWindows = true;
 public static boolean  esWindows = true;
 public static boolean entornoGrafico = false;
 public static String pathFireFox;
 public static String pathExplorer;
 public static Map <String, String> entorno;  
 public static boolean actualizarContadores=true;    
 
 static String coorCentro = "centro";
 static String coorEsquina = "esquina";
 static String cuadrante = "cuadrante";
 static String total = "total";
 public static boolean soloCalibrar = false;
 static boolean finalizar = false;  // Variable utilizada para finalizar los threads de forma abrupta
 static boolean ensamblando = false; // Variable utilizada para saber si ya se están ensamblando los cuadrantes.
 boolean primeraVez_CV = true; // Relacionado con la funcion ComprobarVersion
 
 static boolean procesoFinalizado = false;
 static boolean tracear = false; // Si existe el fichero "tracear", se activarán las trazas.
 
 static boolean hayThreads=true;
 static Factoria factoria = null;
 ThreadGroup grupeto;
 JButton descargar, ensamblar;
 JButton vistaPajaro;
 
 static String[] opcionesDatum = { "ED50", "WGS84" };
 static String[] opcionesComboTipoMapa = { "Topografico", "Ortofoto" };
  		
 static Servidor servidor;		
       
       
       
 JLabel visor;                                           
 JComboBox topo_orto, resolucion;
 JComboBox servidorCombo, comboDatum;
 JSpinner s_huso;
 JTextField t_x, t_y, t_ancho, t_alto;
 JTextField t_num_filas, t_num_col, t_fichero;
 JRadioButton esquinaRadio, centroRadio;
 JRadioButton anchoCRadio, anchoTRadio;
 JButton arrowUP, arrowDOWN, arrowLEFT, arrowRIGHT;
 JLabel cuadranton;
 JPanel fondoAnuncio;  //_xx_aqui 
 JTextField t_fichEnsam, t_filas, t_columnas;
 JTextField t_mapasH, t_mapasV;
 Rejilla rejilla;
 JButton directorio;
 JCheckBox fcalibra;
 JCheckBox quitaManchas;
 JCheckBox soloCuadrante;
 JCheckBox solapamiento;
 Anuncio anuncio;
 JTextField t_i, t_j;
 JCheckBox ensambleDirecto, borrarFicheros;

 JButton b_esqinfder;
 
 JRadioButton mtn2000, mtn2000T, mtn1250, mtn200, mtn25;
 
 JRadioButton ED50, WGS84;
 JLabel nuevaVersion; // Contiene el mensaje de si hay nueva version disponible.
 JSpinner compresionJPEG;
 Border miborde, sinborde;
 
 JProgressBar progreso;
 
 double[] respCoor;
 Datum datum_respCoor;
 boolean descargarSoloTrack = false; //_hh_
 //static String ficheroTrkRte = null;
 static Fichero ficheroTrkRte = null;

 // Para las trazas en fichero:
 static FileOutputStream canal=null;
 static StringBuffer mibuffer=null;

 public JSigpac()
 {
   JPanel p1;
   addWindowListener(
     new WindowAdapter()
     {
	 public void windowClosing(WindowEvent e)
	 {
            System.exit(1);
         }
     }
   );

  /*
   System.out.println("getClass().getName()="+getClass().getName());
   System.out.println("getClass().getSimpleName()="+getClass().getSimpleName());
   Method [] metodos;
   metodos = getClass().getMethods();
   for (int i=0;i<metodos.length; i++)
       System.out.println(i+".- " + metodos[i].getName());
  */   
   
   //System.out.println("getClass.getResource="+getClass().getResource("JSigpac.class"));
   grupeto = new ThreadGroup("jSIGPAC");   
   respCoor = null;   
   datum_respCoor = null;
   descargarSoloTrack = false; //_hh_

   servidor = new Servidor();
   miMapa = new Mapa(this);
   p1 = TableroJSigpac();
   JTabbedPane tab = new JTabbedPane();
   tab.addTab("jSIGPAC", null, p1, "pulsa para jSIGPAC");
   tab.addTab("Calculadora", null, (Component)(new Calculadora()), "Calcular coordenadas en geograficas, UTM, cambio de datum");
   //tab.addTab("Conversor", null, (Component)(new Conversor()), "Convertir el datum de un fichero de track, ruta o waypoints de Compe o Ozi");
   tab.addTab("jtracker", null, (Component)(new TrackConverter()), "");
   tab.addTab("Utilidades", null, (Component)(new Utilidades()), "");
   
   getContentPane().add(tab);
      
   pack();
   pack();
 }

 JPanel TableroJSigpac()
 {
   Font mifuente = new Font("Dialog", Font.PLAIN, 12); //"Helvetica", Font.PLAIN, 12);
//new Font("Dialog", Font.BOLD, 10);
   //Font mifuente12 = new Font("Dialog", Font.PLAIN, 12); //"Helvetica", Font.PLAIN, 12);
   miborde = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
   sinborde = BorderFactory.createEmptyBorder();
   JPanel bottomPanel = new JPanel();
   bottomPanel.setLayout( new GridLayout(2,1) );

    JPanel panelDescargar = new JPanel( new GridLayout(10,2) );
      //JPanel amarillo1 = new JPanel();
      
      panelDescargar.setBackground(Color.yellow);
      
      //l_descargar.setFont(mifuente12);
      //System.out.println("_xx_ fuente="+l_descargar.getFont().toString());
      
      JPanel botonera = new JPanel(new BorderLayout());
      JPanel botonera_east = new JPanel(new BorderLayout());
      botonera.setBackground(Color.yellow);
      botonera_east.setBackground(Color.yellow);
      botonera_east.setBorder(miborde);
      JButton leerde = new JButton("  Leer de...  ");
      leerde.setBorder(miborde);
      leerde.setActionCommand("leerde");
      leerde.setToolTipText("Leer los datos de la descarga de un fichero tipo jsigpac \"*.jsi\"");
      leerde.setBackground(Color.gray.brighter());
      leerde.addActionListener(new miActionListener(this));
      JButton guardaren = new JButton("  Guardar en...  ");
      guardaren.setBorder(miborde);
      guardaren.setActionCommand("guardaren");
      guardaren.setToolTipText("Guardar los datos de la descarga en un fichero tipo jsigpac \"*.jsi\"");
      guardaren.setBackground(Color.gray.brighter());
      guardaren.addActionListener(new miActionListener(this));
      botonera_east.add(leerde, BorderLayout.WEST);
      botonera_east.add(guardaren, BorderLayout.EAST);
      botonera.add(botonera_east, BorderLayout.EAST);
      
      JPanel p_ayuda = new JPanel(new BorderLayout());      
      JPanel p_ayuda_east = new JPanel(new BorderLayout());
      p_ayuda.setBackground(Color.yellow);
      //p_ayuda.setBorder(miborde);
      p_ayuda_east.setBorder(miborde);
      JButton ayuda = new JButton("   ayuda   ");
      ayuda.setBorder(miborde);
      ayuda.setActionCommand("ayuda");
      ayuda.setToolTipText("Ayuda del comando. Ver consola");
      ayuda.setBackground(Color.gray.brighter());
      ayuda.addActionListener(new miActionListener(this));
      JButton importar = new JButton("   importar   ");
      importar.setBorder(miborde);
      importar.setActionCommand("importar");
      importar.setToolTipText("Importar coordenadas de un fichero: track, ruta, waypoints");
      importar.setBackground(Color.gray.brighter());
      importar.addActionListener(new miActionListener(this));
      JButton reset = new JButton("   reset   ");
      reset.setBorder(miborde);
      reset.setActionCommand("reset");
      reset.setToolTipText("Restaurar valores por defecto");
      reset.setBackground(Color.gray.brighter());
      reset.addActionListener(new miActionListener(this));
      
      //p_ayuda_east.add(leerde);
      //p_ayuda_east.add(guardaren);
      p_ayuda_east.add(ayuda, BorderLayout.EAST);
      p_ayuda_east.add(importar, BorderLayout.WEST);
      p_ayuda_east.add(reset, BorderLayout.CENTER);
      p_ayuda.add(p_ayuda_east, BorderLayout.EAST);
      //panelDescargar.add(l_descargar);
      JPanel amarillo2 = new JPanel();      
      amarillo2.setBackground(Color.yellow);
      //panelDescargar.add(amarillo2);
      panelDescargar.add(botonera);
      panelDescargar.add(p_ayuda);
      //panelDescargar.add(p_ayuda_east);
      
      JPanel panelServidor = new JPanel(new BorderLayout());
      panelServidor.setBackground(Color.yellow);
      JPanel panelServidorIzq = new JPanel(new BorderLayout());
      panelServidorIzq.setBackground(Color.yellow);
      JLabel l_descargar = new JLabel("DESCARGAR :   ");
      l_descargar.setToolTipText("Sujeto a licencia Creative Commons");
      panelServidor.add(l_descargar, BorderLayout.WEST);
            
      servidorCombo = new JComboBox(Servidor.opcionesComboServidor); 
      visor = new JLabel("web", JLabel.CENTER);  
      vistaPajaro = new JButton("Ver Mapa");
      //servidorCombo.setSelectedItem(opcionesComboServidor[0]);   
      // DarValorComboServidor(opcionesComboServidor[0]);      // Ahora llamo a esta funcion una vez definida "esquinaRadio"
      servidorCombo.setActionCommand("servidorCombo");      
      servidorCombo.addActionListener(new miActionListener(this));
      
      //visor = new JLabel("<html><center><a href=\"" + Servidor.visor[0] + "\"   web  </a>");
      
      visor.setFont(mifuente);
      visor.setBackground(Color.yellow);
      visor.setForeground(Color.blue);
      visor.addMouseListener(new EventosDelRaton(this, "webVisor"));
      
      panelServidorIzq.add(servidorCombo, BorderLayout.WEST);
      panelServidorIzq.add(visor, BorderLayout.CENTER);
      panelServidor.add(panelServidorIzq, BorderLayout.CENTER);
      //panelDescargar.add(amarillo2);       
          
      panelDescargar.add(panelServidor);  //l_descargar);
      JPanel P_topo_orto = new JPanel(new BorderLayout());
      P_topo_orto.setBackground(Color.yellow);     
      topo_orto = new JComboBox(opcionesComboTipoMapa);
      topo_orto.setFont(mifuente);
      topo_orto.setSelectedItem(opcionesComboTipoMapa[1]);
      topo_orto.setActionCommand("topo_orto");
      topo_orto.addActionListener(new miActionListener(this));
      P_topo_orto.add(topo_orto, BorderLayout.WEST);
      panelDescargar.add(P_topo_orto);      
      JLabel l_huso = new JLabel("Zona o huso: ", JLabel.RIGHT);
      l_huso.setToolTipText("NOROESTE:29  BALEARES y LEVANTE:31  CANARIAS:27-28   RESTO de ESPAÑA:30");      
      JPanel P_huso = new JPanel(new GridLayout(1,3));
      JPanel P_huso_1 = new JPanel(new BorderLayout());
      P_huso_1.setBackground(Color.yellow);
      String[] valoresHuso = { "27", "28", "29", "30", "31" };
      SpinnerModel husoModel = new SpinnerListModel(valoresHuso);
      s_huso = new JSpinner(husoModel);
      s_huso.setValue("30");
      P_huso_1.add(s_huso, BorderLayout.WEST);
      JPanel P_huso_2 = new JPanel();
      P_huso_2.setBackground(Color.yellow);
      JPanel P_huso_3 = new JPanel(new GridLayout(1,3));
      P_huso_3.setBackground(Color.yellow);
	ImageIcon iconUP = new ImageIcon("dat"+File.separator+"iconos"+File.separator+"arrowUP.gif");
	ImageIcon iconDOWN = new ImageIcon("dat"+File.separator+"iconos"+File.separator+"arrowDOWN.gif");
	ImageIcon iconLEFT = new ImageIcon("dat"+File.separator+"iconos"+File.separator+"arrowLEFT.gif");
	ImageIcon iconRIGHT = new ImageIcon("dat"+File.separator+"iconos"+File.separator+"arrowRIGHT.gif");
        arrowUP = new JButton(iconUP);
	arrowUP.setBackground(Color.yellow);
	arrowUP.setBorderPainted(false);
	arrowUP.setActionCommand("arrowUP");
	arrowUP.addActionListener(new miActionListener(this));
        arrowDOWN = new JButton(iconDOWN);
	arrowDOWN.setBackground(Color.yellow);
	arrowDOWN.setBorderPainted(false);
	arrowDOWN.setActionCommand("arrowDOWN");
	arrowDOWN.addActionListener(new miActionListener(this));
        arrowLEFT = new JButton(iconLEFT);
	arrowLEFT.setBackground(Color.yellow);
	arrowLEFT.setBorderPainted(false);
	arrowLEFT.setActionCommand("arrowLEFT");
	arrowLEFT.addActionListener(new miActionListener(this));
        arrowRIGHT = new JButton(iconRIGHT);
	arrowRIGHT.setBackground(Color.yellow);
	arrowRIGHT.setBorderPainted(false);
	arrowRIGHT.setActionCommand("arrowRIGHT");
	arrowRIGHT.addActionListener(new miActionListener(this));

	JPanel amarillo11 = new JPanel();
	amarillo11.setBackground(Color.yellow);
	JPanel amarillo12 = new JPanel();
	amarillo12.setBackground(Color.yellow);
	P_huso_3.add(amarillo11);
	P_huso_3.add(arrowUP);
	P_huso_3.add(amarillo12);
      P_huso.add(P_huso_1);
      P_huso.add(P_huso_2);
      P_huso.add(P_huso_3);
      panelDescargar.add(l_huso);
      panelDescargar.add(P_huso);

      JPanel panel_esq_cen = new JPanel(new BorderLayout());
      //JPanel panelY = new JPanel(new BorderLayout());
      panel_esq_cen.setBackground(Color.yellow);
      //panelY.setBackground(Color.yellow);
      centroRadio = new JRadioButton(coorCentro);
      centroRadio.setFont(mifuente);
      centroRadio.setToolTipText("Seleccioname si X e Y son las coordenadas del centro del mapa");
      centroRadio.setActionCommand(coorCentro);
      centroRadio.setBackground(Color.yellow);
      //esquinaRadio = new JRadioButton(coorEsquina + "     Datum entrada: ED50 ");
      //esquinaRadio = new JRadioButton(coorEsquina + "  Datum entrada:");
      esquinaRadio = new JRadioButton(coorEsquina);
      esquinaRadio.setFont(mifuente);
      esquinaRadio.setToolTipText("Seleccioname si X e Y son las coordenadas de la esquina superior izquierda");
      esquinaRadio.setActionCommand(coorEsquina);
      esquinaRadio.setBackground(Color.yellow);
      esquinaRadio.setSelected(true);      
      ButtonGroup groupCoor = new ButtonGroup();
      groupCoor.add(centroRadio);
      groupCoor.add(esquinaRadio);
      centroRadio.addActionListener(new miActionListener(this));
      esquinaRadio.addActionListener(new miActionListener(this));
            
      comboDatum = new JComboBox(opcionesDatum);
      
      JPanel datum = new JPanel(new BorderLayout());
      datum.setBackground(Color.yellow);
      JLabel l_datum = new JLabel("Datum entrada:");
      datum.add(l_datum, BorderLayout.WEST);
      datum.add(comboDatum, BorderLayout.EAST);
      
      JPanel esq_datum = new JPanel(new BorderLayout());
      esq_datum.setBackground(Color.yellow);      
      esq_datum.add(esquinaRadio, BorderLayout.WEST);
      esq_datum.add(datum, BorderLayout.EAST);
      

      JLabel l_x = new JLabel("  X: ", JLabel.RIGHT);
      l_x.setToolTipText("Coordenada UTM de las X");
      
      panel_esq_cen.add(centroRadio, BorderLayout.WEST);
      panel_esq_cen.add(esq_datum, BorderLayout.CENTER);
      panel_esq_cen.add(l_x, BorderLayout.EAST);
      JPanel P_xy = new JPanel(new GridLayout(1,3));
      JPanel P_xy_1 = new JPanel(new BorderLayout());
      JPanel P_xy_2 = new JPanel(new BorderLayout());
      JPanel P_x = new JPanel(new BorderLayout());
      JPanel P_y = new JPanel(new BorderLayout());
      P_xy_1.setBackground(Color.yellow);
      P_xy_2.setBackground(Color.yellow);
      P_x.setBackground(Color.yellow);
      P_y.setBackground(Color.yellow);
      t_x = new JTextField(8);
      t_x.setFont(mifuente);
      //t_x.addFocusListener(new FocusListener());
      P_x.add(t_x, BorderLayout.WEST);
      JLabel l_y = new JLabel("  Y: ", JLabel.RIGHT);
      l_y.setToolTipText("Coordenada UTM de las Y");      

      t_y = new JTextField(8);
      t_y.setFont(mifuente);
      P_y.add(l_y, BorderLayout.WEST);
      P_y.add(t_y, BorderLayout.EAST);
      P_xy_1.add(P_x, BorderLayout.WEST);
      P_xy_2.add(P_y, BorderLayout.WEST);
      JPanel P_xy_3 = new JPanel(new GridLayout(1,3));
      P_xy_3.setBackground(Color.yellow);
	
	cuadranton = new JLabel("0,0", JLabel.CENTER);
	P_xy_3.add(arrowLEFT);
	P_xy_3.add(cuadranton);
	P_xy_3.add(arrowRIGHT);

      P_xy.add(P_xy_1);
      P_xy.add(P_xy_2);
      P_xy.add(P_xy_3);
      panelDescargar.add(panel_esq_cen);
      panelDescargar.add(P_xy);

      JPanel panelA = new JPanel(new BorderLayout());
      panelA.setBackground(Color.yellow);
      anchoCRadio = new JRadioButton(JSigpac.cuadrante);
      anchoCRadio.setFont(mifuente);
      anchoCRadio.setToolTipText("El \"Ancho\" es el ancho en metros de un solo cuadrante");
      anchoCRadio.setActionCommand("cuadrante");
      anchoCRadio.setBackground(Color.yellow); //_xx_.darker());
      anchoCRadio.setSelected(true);
      anchoTRadio = new JRadioButton(JSigpac.total);
      anchoTRadio.setFont(mifuente);
      anchoTRadio.setToolTipText("El \"Ancho\" es el ancho en metros del mapa final");
      anchoTRadio.setActionCommand("total");
      anchoTRadio.setBackground(Color.yellow); //_xx_.darker());
      ButtonGroup groupAncho = new ButtonGroup();
      groupAncho.add(anchoCRadio);
      groupAncho.add(anchoTRadio);
      anchoCRadio.addActionListener(new miActionListener(this));
      anchoTRadio.addActionListener(new miActionListener(this));

      JLabel l_ancho = new JLabel("    Ancho: ", JLabel.RIGHT);
      l_ancho.setBackground(Color.yellow);
      l_ancho.setToolTipText("Ancho en metros que abarca un cuadrante o el mapa final completo");      
      
      b_esqinfder = new JButton("  Esq Inf Der  ");
      b_esqinfder.setToolTipText("Permite introducir tambien las coordenadas UTM de la esquina inferior derecha");
      b_esqinfder.setBackground(Color.orange);
      b_esqinfder.setBorder(miborde);
      //b_esqinfder.setBorderPainted(false);
      b_esqinfder.setActionCommand("esqinfder");
      b_esqinfder.addActionListener(new miActionListener(this));
      
      /****************************************
      
      
      
      
      OJO: El boton "Esq Inf Der"  solo tiene sentido cuando esta marcada la opcion esquina, no centro
      
      
      
      *******************************************/
      
      JPanel panel_b_esquina = new JPanel(new BorderLayout());
      panel_b_esquina.setBackground(Color.yellow);
      panel_b_esquina.setBorder(miborde);
      panel_b_esquina.add(b_esqinfder, BorderLayout.CENTER);
      JPanel p_esqinfder = new JPanel(new BorderLayout());
      p_esqinfder.setBackground(Color.yellow);
      //p_esqinfder.setBorder(miborde);
      p_esqinfder.add(panel_b_esquina, BorderLayout.WEST);
      p_esqinfder.add(l_ancho, BorderLayout.EAST);
      
      panelA.add(anchoCRadio, BorderLayout.WEST);
      panelA.add(anchoTRadio, BorderLayout.CENTER);
      panelA.add(p_esqinfder, BorderLayout.EAST);

      JPanel P_t_ancho = new JPanel(new GridLayout(1,3));
      JPanel P_t_ancho_1 = new JPanel(new BorderLayout());
      P_t_ancho_1.setBackground(Color.yellow);
      t_ancho = new JTextField(6);
      t_ancho.setFont(mifuente);
      t_ancho.setCaretPosition(0);
      P_t_ancho_1.add(t_ancho, BorderLayout.WEST);
      P_t_ancho.add(P_t_ancho_1);
      JPanel P_t_ancho_2 = new JPanel(new BorderLayout());
      P_t_ancho_2.setBackground(Color.yellow);
      JLabel l_alto = new JLabel(" Alto: ", JLabel.RIGHT);
      l_alto.setToolTipText("Distancia vertical en metros que abarca un cuadrante o el mapa final completo");
      t_alto = new JTextField(6);
      t_alto.setCaretPosition(0);
      t_alto.setFont(mifuente);      
      P_t_ancho_2.add(l_alto, BorderLayout.WEST);
      P_t_ancho_2.add(t_alto, BorderLayout.EAST);
      P_t_ancho.add(P_t_ancho_2);
      JPanel P_t_ancho_3 = new JPanel(new GridLayout(1,3));
      P_t_ancho_3.setBackground(Color.yellow);
         JPanel amarillo15 = new JPanel();
	 amarillo15.setBackground(Color.yellow);
         JPanel amarillo16 = new JPanel();
	 amarillo16.setBackground(Color.yellow);
         P_t_ancho_3.add(amarillo15);
         P_t_ancho_3.add(arrowDOWN);
         P_t_ancho_3.add(amarillo16);

      P_t_ancho.add(P_t_ancho_3);
      panelDescargar.add(panelA);
      panelDescargar.add(P_t_ancho);

      JPanel resol = new JPanel(new BorderLayout());
      resol.setBackground(Color.yellow);
      //JLabel amarillo9 = new JLabel("                 ");
      JLabel _resolucion = new JLabel("        resolucion: ");
      _resolucion.setFont(mifuente);
      //amarillo9.setBackground(Color.yellow);
      _resolucion.setBackground(Color.yellow);
      //JLabel flecha = new JLabel("       ---->>>       ");
      ImageIcon _flecha = new ImageIcon("dat"+File.separator+"iconos"+File.separator+"flecha.gif");
      JLabel flecha = new JLabel("", _flecha, JLabel.CENTER);
      flecha.setBackground(Color.yellow);
      String[] opcionesRes = { "0.125", "0.250", "0.5", "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048", "4096" };
      resolucion = new JComboBox(opcionesRes);
      //resolucion.setToolTipText("Pruebameee");
      //resolucion.setSize(15,10);
      resolucion.setEditable(true);
      resolucion.setEnabled(false);
      resolucion.setActionCommand("resolucion");
      resolucion.addActionListener(new miActionListener(this));
      //resol.add(amarillo9, BorderLayout.WEST);
      JPanel p_resol = new JPanel(new BorderLayout());
      p_resol.setBackground(Color.yellow);
      p_resol.add(_resolucion, BorderLayout.WEST);
      p_resol.add(new JLabel(""), BorderLayout.CENTER);
      p_resol.add(resolucion, BorderLayout.EAST);
      //resol.add(_resolucion, BorderLayout.WEST);
      //resol.add(resolucion, BorderLayout.CENTER);
      resol.add(p_resol, BorderLayout.WEST);
      resol.add(flecha, BorderLayout.CENTER);

      DarValorComboServidor(Servidor.opcionesComboServidor[Servidor.NACIONAL]); 
      
      JPanel _filcol = new JPanel(new GridLayout(1,3));
      JPanel _col = new JPanel(new BorderLayout());
      JPanel _fil = new JPanel(new BorderLayout());
      JPanel amarillo17 = new JPanel();
      _filcol.setBackground(Color.yellow);
      _col.setBackground(Color.yellow);
      _fil.setBackground(Color.yellow);
      amarillo17.setBackground(Color.yellow);
      JLabel l_num_col = new JLabel("columnas:  ", JLabel.RIGHT);
      l_num_col.setToolTipText("Cantidad de columnas (horizontales) del mosaico final");
      
      t_num_col = new JTextField(3);
      t_num_col.setFont(mifuente);
      _col.add(l_num_col, BorderLayout.WEST);
      _col.add(t_num_col, BorderLayout.EAST);
      JLabel l_num_filas = new JLabel(" filas:  ", JLabel.RIGHT);
      l_num_filas.setToolTipText("Cantidad de columnas (verticales) del mosaico final");
      
      t_num_filas = new JTextField(3);
      t_num_filas.setFont(mifuente);
      _fil.add(l_num_filas, BorderLayout.WEST);
      _fil.add(t_num_filas, BorderLayout.EAST);
      _filcol.add(_col); //, BorderLayout.WEST);
      _filcol.add(_fil); //, BorderLayout.EAST);
      _filcol.add(amarillo17);
      
      panelDescargar.add(resol);
      panelDescargar.add(_filcol);

      JPanel panelS = new JPanel(new BorderLayout());
      panelS.setBackground(Color.yellow);
      quitaManchas = new JCheckBox("Quita manchas", false);
      //quitaManchas.setFont(mifuente);
      quitaManchas.setActionCommand("quitaManchas");
      quitaManchas.setToolTipText("Elimina las marcas amarillas de SigPac");
      quitaManchas.setBackground(Color.yellow);      
      //panelS.add(quitaManchas, BorderLayout.WEST);  // No tiene sentido ya la opción "Quita manchas"


      JLabel l_fichero = new JLabel("Fichero: ", JLabel.RIGHT);
      l_fichero.addMouseListener(new EventosDelRaton(this, "Fichero"));
      l_fichero.setToolTipText("Path absoluto o relativo del nombre del fichero");
      l_fichero.setFont(mifuente);
      panelS.add(l_fichero, BorderLayout.EAST);
      JPanel panelDir = new JPanel(new BorderLayout());
      panelDir.setBackground(Color.yellow);
      t_fichero = new JTextField(18);
      t_fichero.setFont(mifuente);
      directorio = new JButton("  carpeta...  ");
      directorio.setBorder(miborde);
      directorio.setActionCommand("carpeta");
      directorio.setToolTipText("Carpeta donde se dejaran los ficheros");
      directorio.setBackground(Color.gray.brighter());
      directorio.addActionListener(new miActionListener(this));
      panelDir.add(t_fichero, BorderLayout.WEST);
      panelDir.add(directorio, BorderLayout.EAST);

      panelDescargar.add(panelS);
      panelDescargar.add(panelDir);

      JPanel panelF = new JPanel(new BorderLayout());
      panelF.setBackground(Color.yellow);
      fcalibra = new JCheckBox("Ficheros calibrados", true);
      fcalibra.addMouseListener(new EventosDelRaton(this, "fcalibra"));
      fcalibra.setFont(mifuente);
      fcalibra.setActionCommand("fcalibrar");
      fcalibra.setToolTipText("Marcar si se quieren generar los ficheros de calibración. Botón derecho del ratón para especificar los formatos de calibración");
      fcalibra.setBackground(Color.yellow);
      //fcalibra.addActionListener(new miActionListener(this));
      panelF.add(fcalibra, BorderLayout.WEST);
      JPanel p_calibrar = new JPanel(new BorderLayout());
      JButton calibrar = new JButton("   calibrar   ");
      calibrar.setBorder(miborde);
      calibrar.setSize(20,20);
      calibrar.setActionCommand("calibrar");
      calibrar.setToolTipText("Generar solo los ficheros de calibracion");
      calibrar.setBackground(Color.gray.brighter());
      calibrar.addActionListener(new miActionListener(this));
      JPanel amarillo3 = new JPanel();
      amarillo3.setBackground(Color.yellow);
      p_calibrar.add(calibrar, BorderLayout.WEST);
      p_calibrar.add(amarillo3, BorderLayout.CENTER);
      panelF.add(p_calibrar, BorderLayout.CENTER);
      JLabel l_escala = new JLabel("Escala: ");
      l_escala.setFont(mifuente);
      panelF.add(l_escala, BorderLayout.EAST);
      JPanel escala1 = new JPanel(new GridLayout(1,3));

      mtn2000 = new JRadioButton("1:2000000");
      mtn2000.setFont(mifuente);
      mtn2000T = new JRadioButton("Relieve");
      mtn2000T.setFont(mifuente);
      mtn1250 = new JRadioButton("1:1250000");
      mtn1250.setFont(mifuente);
      mtn200 = new JRadioButton("1:200000");
      mtn200.setFont(mifuente);
      mtn25 = new JRadioButton("1:25000");
      mtn25.setFont(mifuente);
      mtn2000T.setBackground(Color.yellow);
      mtn2000.setBackground(Color.yellow);
      mtn1250.setBackground(Color.yellow);
      mtn200.setBackground(Color.yellow);
      mtn25.setBackground(Color.yellow);
      ButtonGroup groupEscala = new ButtonGroup();
      groupEscala.add(mtn2000T);
      groupEscala.add(mtn2000);
      groupEscala.add(mtn1250);
      groupEscala.add(mtn200);
      groupEscala.add(mtn25);
      mtn25.setSelected(true);
      PonerEstadoEscala(false);
      //anchoTRadio.addActionListener(new miActionListener(this));
      escala1.add(mtn25);
      escala1.add(mtn200);
      escala1.add(mtn1250);

      panelDescargar.add(panelF);
      panelDescargar.add(escala1);


      JPanel panelED50WGS = new JPanel(new BorderLayout());
      panelED50WGS.setBackground(Color.yellow);
      // - Tengo que crear los RadioButton con ED50 y WGS84:
      ED50 = new JRadioButton("ED50");
      ED50.setBackground(Color.yellow);
      ED50.setFont(mifuente);
      ED50.setToolTipText("Los ficheros de calibracion iran en el datum ED50");
      WGS84 = new JRadioButton("WGS84");
      WGS84.setBackground(Color.yellow);
      WGS84.setFont(mifuente);
      WGS84.setToolTipText("Los ficheros de calibracion iran en el datum WGS84");
      ButtonGroup datums = new ButtonGroup();
      datums.add(ED50);
      datums.add(WGS84);
      WGS84.setSelected(true);
      panelED50WGS.add(ED50, BorderLayout.WEST);
      panelED50WGS.add(WGS84, BorderLayout.CENTER);


      
      //Border miborde = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
      //Border miborde = BorderFactory.createLineBorder(Color.black);
      //Border miborde = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);            
      //Border miborde = BorderFactory.createRaisedBevelBorder();
      //Border miborde = BorderFactory.createLoweredBevelBorder();
      //Border miborde = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);     
      vistaPajaro.setSize(20,20);
      vistaPajaro.setBorder(miborde);
      vistaPajaro.setActionCommand("pajaro");
      vistaPajaro.setToolTipText("Ver el area abarcada por el mapa");
      vistaPajaro.setBackground(Color.orange);
      vistaPajaro.addActionListener(new miActionListener(this));
      
      JPanel escala2 = new JPanel(new GridLayout(1,3));
      //JPanel amarillo7 = new JPanel(new FlowLayout());
      //amarillo7.setBackground(Color.blue);
      //amarillo7.add(vistaPajaro);
      escala2.add(mtn2000);
      escala2.add(mtn2000T);
      escala2.add(vistaPajaro);
      panelDescargar.add(panelED50WGS);
      panelDescargar.add(escala2);
	  

      JPanel panelSC = new JPanel(new BorderLayout());
      panelSC.setBackground(Color.yellow);
      soloCuadrante = new JCheckBox("Descargar cuadrante (", false);
      soloCuadrante.setFont(mifuente);
      soloCuadrante.setBackground(Color.yellow);
      soloCuadrante.setToolTipText("Para descargar unicamente un cuadrante concreto de todo el mosaico final");
      //soloCuadrante.setActionCommand("soloCuadrante");
      //soloCuadrante.addActionListener(new miActionListener(this));
      panelSC.add(soloCuadrante, BorderLayout.WEST);
      JPanel ij = new JPanel(new BorderLayout());
      JPanel ij_west = new JPanel(new BorderLayout());
      JPanel ij_west_izqda = new JPanel(new BorderLayout());
      JPanel ij_west_dcha = new JPanel(new BorderLayout());
      ij_west.setBackground(Color.yellow);
      ij_west_izqda.setBackground(Color.yellow);
      ij_west_dcha.setBackground(Color.yellow);    
      ij.setBackground(Color.yellow);
      ij_west.setBackground(Color.yellow);
      t_i = new JTextField(3);
      t_i.setBackground(Color.white);
      JLabel coma = new JLabel(" , ");
      coma.setFont(mifuente);
      coma.setBackground(Color.yellow);
      t_j = new JTextField(3);
      t_j.setBackground(Color.white);
      JLabel fin_parentesis = new JLabel(")");
      fin_parentesis.setBackground(Color.yellow);
      fin_parentesis.setFont(mifuente);
      fin_parentesis.setVerticalAlignment(JLabel.CENTER);
      
      ij_west_izqda.add(t_i, BorderLayout.WEST);
      ij_west_izqda.add(coma, BorderLayout.EAST);
      ij_west_dcha.add(t_j, BorderLayout.WEST);
      ij_west_dcha.add(fin_parentesis, BorderLayout.EAST);
      ij_west.add(ij_west_izqda, BorderLayout.WEST);
      ij_west.add(ij_west_dcha, BorderLayout.EAST);
      ij.add(ij_west, BorderLayout.WEST);
      panelSC.add(ij, BorderLayout.CENTER);

      JPanel p_descargar = new JPanel(new BorderLayout());
      descargar = new JButton("   descargar...   ");
      descargar.setBorder(miborde);
      descargar.setActionCommand("descargar");
      descargar.setToolTipText("Solamente se descargan los mapas. Luego habra que ensamblarlos.  Pulsando con el boton derecho del raton se recalculan los valores de forma adecuada");
      
      descargar.setBackground(Color.red);
      descargar.setForeground(Color.yellow);
      descargar.addActionListener(new miActionListener(this));
      descargar.addMouseListener(new EventosDelRaton(this, "descargar"));      
      //JPanel amarillo5 = new JPanel();
      solapamiento = new JCheckBox("Solapamiento perfecto", false);
      solapamiento.setFont(mifuente);
      solapamiento.setActionCommand("solapamiento");
      solapamiento.setToolTipText("Permite solapes cuasi-perfectos. Pero tiene sus inconvenientes.");
      solapamiento.setBackground(Color.yellow);
      //solapamiento.addActionListener(new miActionListener(this));

      //amarillo5.setBackground(Color.yellow);
      //amarillo5.setSize(80,10);
      JPanel amarillo6 = new JPanel();
      amarillo6.setBackground(Color.yellow);
      amarillo6.setSize(80,10);
      //p_descargar.add(amarillo5, BorderLayout.WEST);
      //p_descargar.add(solapamiento, BorderLayout.WEST);  // Elimino lo de "solapamiento perfecto" porque ya no tiene sentido.
      p_descargar.add(amarillo6, BorderLayout.CENTER);
      p_descargar.add(descargar, BorderLayout.EAST);
      panelDescargar.add(panelSC);
      panelDescargar.add(p_descargar);

   JPanel panelEnsamblar = new JPanel( new GridLayout(9,2) );
      panelEnsamblar.setBackground(Color.red);
      
      
      //JPanel panelProgreso = new JPanel();
      CrearBarraDeProgreso();      
      JPanel rojo3 = new JPanel();
      rojo3.setBackground(Color.yellow);
      //JPanel rojo4 = new JPanel();
      //rojo4.setBackground(Color.cyan);
      
      panelEnsamblar.add(rojo3);
      //panelEnsamblar.add(rojo4);
      panelEnsamblar.add(progreso);
      
      //_xx_aqui_
      fondoAnuncio = new JPanel(new BorderLayout());
      fondoAnuncio.setBackground(Color.black);
      //JLabel l_negro1 = new JLabel("http://fon.gs/jsigpac", JLabel.LEFT);
      //l_negro1.setToolTipText("Pincha aqui para acceder a la web del jSIGPAC");      
      //l_negro1.setFont(mifuente);
      //l_negro1.setBackground(Color.black);
      //l_negro1.setForeground(Color.white);
      //l_negro1.addMouseListener(new EventosDelRaton(this, "webJSIGPAC"));
      //System.out.println("Dim negro1 Ancho="+negro1.getWidth()+"   alto="+negro1.getHeight());
      JLabel l_negro1 = new JLabel("   ");
      l_negro1.setBackground(Color.black);
      
      anuncio = new Anuncio(fondoAnuncio); //"Esto es un anuncio que debe aparecer cortado");
      anuncio.addMouseListener(new EventosDelRaton(this, "anuncio"));
      
      fondoAnuncio.add(l_negro1, BorderLayout.WEST);
      fondoAnuncio.add(anuncio, BorderLayout.CENTER);
      
      JPanel negro2 = new JPanel(new BorderLayout());
      negro2.setBackground(Color.black);
      nuevaVersion = new JLabel("", JLabel.LEFT);
      nuevaVersion.setBackground(Color.black);
      nuevaVersion.setForeground(Color.cyan);
      
      JPanel pnuevaVersion = new JPanel(new BorderLayout());
      pnuevaVersion.setBackground(Color.black);
        JPanel pmoverAnuncio = new JPanel(new GridLayout(1,2));        
        ImageIcon iconBACK = new ImageIcon("dat"+File.separator+"iconos"+File.separator+"botonBACK.gif");
        ImageIcon iconGO = new ImageIcon("dat"+File.separator+"iconos"+File.separator+"botonGO.gif");
        JButton bback = new JButton(iconBACK);
        bback.setBackground(Color.black);
        bback.setToolTipText("Volver al anuncio anterior");
        bback.setBorderPainted(false);        
        bback.setFocusPainted(false);
        bback.setPreferredSize(new Dimension(17,17));
        bback.addMouseListener(new EventosDelRaton(this, "bback"));
        JButton bgo = new JButton(iconGO);
        bgo.setBackground(Color.black);
        bgo.setToolTipText("Pasar al siguiente anuncio");
        bgo.setBorderPainted(false);
        bgo.setFocusPainted(false);
        bgo.setPreferredSize(new Dimension(17,17));
        bgo.addMouseListener(new EventosDelRaton(this, "bgo"));
        pmoverAnuncio.add(bback);
        pmoverAnuncio.add(bgo);
      pnuevaVersion.add(pmoverAnuncio, BorderLayout.WEST);                
      pnuevaVersion.add(nuevaVersion, BorderLayout.CENTER);
      
      JLabel l_negro2 = new JLabel(JSigpac.version, JLabel.RIGHT);
      l_negro2.setToolTipText(JSigpac.actualizado);
      l_negro2.setFont(mifuente);
      l_negro2.setBackground(Color.black);
      l_negro2.setForeground(Color.white);
      l_negro2.addMouseListener(new EventosDelRaton(this, "webJSIGPAC"));
      negro2.add(pnuevaVersion, BorderLayout.WEST);
      negro2.add(l_negro2, BorderLayout.EAST);
      panelEnsamblar.add(fondoAnuncio);
      panelEnsamblar.add(negro2);
      JPanel jrariasf = new JPanel(new BorderLayout());
      jrariasf.setBackground(Color.red);
      JButton fundacion = new JButton();
      //fundacion.setActionCommand("Fundacion");      
      fundacion.setBackground(Color.black);
      fundacion.setFocusPainted(false);
      //fundacion.setText("<html><font color=blue><u>Colaborar con la Fundacion</u></font></html>");
      fundacion.setText("Colaborar con la Fundacion");
      fundacion.setForeground(Color.cyan);
      fundacion.addMouseListener(new EventosDelRaton(this, "Fundacion"));
      jrariasf.add(fundacion, BorderLayout.WEST);
      JLabel l_jrariasf = new JLabel("jrariasf ", JLabel.RIGHT);
      l_jrariasf.setFont(mifuente);
      l_jrariasf.setForeground(Color.yellow);
      l_jrariasf.setBackground(Color.red);
      jrariasf.add(l_jrariasf, BorderLayout.EAST);
      JPanel rojo1 = new JPanel();
      rojo1.setBackground(Color.red);      
      panelEnsamblar.add(rojo1);
      panelEnsamblar.add(jrariasf);
      JLabel l_ensamblar = new JLabel("ENSAMBLAR:");
      l_ensamblar.setToolTipText("Sujeto a licencia Creative Commons");
      //l_ensamblar.setFont(mifuente12);
      JPanel p_copiar = new JPanel(new BorderLayout());
      p_copiar.setBackground(Color.red);
      JButton copiar = new JButton("  Copiar valores  ");
      copiar.setBorder(miborde);
      //copiar.setBorderPainted(false);      
      copiar.setActionCommand("copiar");
      copiar.setToolTipText("Copia los valores del panel superior");
      copiar.setBackground(Color.gray.brighter());
      //copiar.setForeground(Color.yellow);      
      copiar.addActionListener(new miActionListener(this));      
      p_copiar.add(copiar, BorderLayout.WEST);
      panelEnsamblar.add(l_ensamblar);
      panelEnsamblar.add(p_copiar);
      JLabel l_fichEnsam = new JLabel("Nombre del fichero: ", JLabel.RIGHT);
      l_fichEnsam.setToolTipText("Sin extension. Coincidira con el puesto en el campo de texto anterior, Fichero");
      l_fichEnsam.setFont(mifuente);
      l_fichEnsam.setForeground(Color.black);
      t_fichEnsam = new JTextField(20);
      t_fichEnsam.setFont(mifuente);
      JPanel p_fichEnsam = new JPanel(new BorderLayout());
      p_fichEnsam.setBackground(Color.red);
      p_fichEnsam.add(t_fichEnsam, BorderLayout.WEST);
      p_fichEnsam.add(new JLabel(""), BorderLayout.EAST);
      panelEnsamblar.add(l_fichEnsam);
      panelEnsamblar.add(p_fichEnsam);

      JLabel l_columnas = new JLabel("numero de columnas: ", JLabel.RIGHT);
      l_columnas.setToolTipText("Normalmente coincidira con el campo \"columnas\" del panel superior");
      l_columnas.setForeground(Color.black);
      l_columnas.setFont(mifuente);
      t_columnas = new JTextField(4);
      t_columnas.setFont(mifuente);
      JPanel p_columnas = new JPanel(new BorderLayout());
      p_columnas.setBackground(Color.red);
            
      JLabel l_filas = new JLabel("     numero de filas: ", JLabel.RIGHT);
      l_filas.setToolTipText("Normalmente coincidira con el campo \"filas\" del panel superior");
      l_filas.setForeground(Color.black);
      l_filas.setFont(mifuente);
      t_filas = new JTextField(4);
      t_filas.setFont(mifuente);
      
      JPanel p_col = new JPanel(new BorderLayout());
      p_col.setBackground(Color.red);
      JPanel p_aux = new JPanel(new BorderLayout());
      p_aux.setBackground(Color.red);
      p_aux.add(l_filas, BorderLayout.WEST);
      p_aux.add(t_filas, BorderLayout.CENTER);
            
      p_col.add(p_aux, BorderLayout.WEST);      
      
      p_columnas.add(t_columnas, BorderLayout.WEST);
      p_columnas.add(p_col, BorderLayout.CENTER);     
            
      panelEnsamblar.add(l_columnas);
      panelEnsamblar.add(p_columnas);
            
            
      JLabel l_mapasH = new JLabel("Mapas en horizontal: ", JLabel.RIGHT);
      l_mapasH.setToolTipText("Numero de mapas en que se dividira horizontalmente");
      l_mapasH.setForeground(Color.black);
      l_mapasH.setFont(mifuente);
      t_mapasH = new JTextField("1", 4);
      t_mapasH.setFont(mifuente);
      t_mapasH.addKeyListener(new miKeyListener(this));
      JPanel p_mapasH = new JPanel(new BorderLayout());
      p_mapasH.setBackground(Color.red);      
      
      JLabel l_mapasV = new JLabel("   Mapas en vertical: ", JLabel.RIGHT);
      l_mapasV.setToolTipText("Numero de mapas en que se dividira verticalmente");
      l_mapasV.setForeground(Color.black);
      l_mapasV.setFont(mifuente);      
      t_mapasV = new JTextField("1", 4);
      t_mapasV.setFont(mifuente);
      t_mapasV.addKeyListener(new miKeyListener(this));
      
      JPanel p_mapas = new JPanel(new BorderLayout());
      p_mapas.setBackground(Color.red);
      JPanel p_mapas_aux = new JPanel(new BorderLayout());
      p_mapas_aux.setBackground(Color.red);
      p_mapas_aux.add(l_mapasV, BorderLayout.WEST);
      p_mapas_aux.add(t_mapasV, BorderLayout.CENTER);
      
      rejilla = new Rejilla(this);
      //p_mapas_aux.add(rejilla, BorderLayout.EAST);
            
      p_mapas.add(p_mapas_aux, BorderLayout.WEST);  
          
      
      p_mapasH.add(t_mapasH, BorderLayout.WEST);
      p_mapasH.add(p_mapas, BorderLayout.CENTER);  
      p_mapasH.add(rejilla, BorderLayout.EAST);    
            
      panelEnsamblar.add(l_mapasH);
      panelEnsamblar.add(p_mapasH);
      
         
      
      // Compresion JPEG:
      JLabel _compresionJPEG = new JLabel("Factor de compresion JPEG: ", JLabel.RIGHT);
      JLabel _porCiento = new JLabel(" %", JLabel.LEFT);
      _compresionJPEG.setToolTipText("Maxima calidad: 100   Mayor compresion: 1");
      _compresionJPEG.setForeground(Color.black);
      _compresionJPEG.setFont(mifuente);
      SpinnerModel compresionModel = new SpinnerNumberModel(100, 1, 100, 5);
      compresionJPEG = new JSpinner(compresionModel);
      JPanel panelCompreJPEG = new JPanel(new BorderLayout());
      panelCompreJPEG.setBackground(Color.red);
      panelCompreJPEG.add(compresionJPEG, BorderLayout.WEST);
      panelCompreJPEG.add(_porCiento, BorderLayout.CENTER);
      panelEnsamblar.add(_compresionJPEG);
      panelEnsamblar.add(panelCompreJPEG);

      //_xx_ Paneles rojos
      
      JPanel p_ensamblar = new JPanel(new BorderLayout());
      p_ensamblar.setBackground(Color.red);
      JPanel opciones = new JPanel(new BorderLayout());
      opciones.setBackground(Color.red);
      ensambleDirecto = new JCheckBox("Descargar y ensamblar", false);
      borrarFicheros = new JCheckBox("Borrar ficheros intermedios", true);
      ensambleDirecto.setBackground(Color.red);
      ensambleDirecto.setToolTipText("Permitira descargar y ensamblar de un tiron al pulsar el boton \"ensamblar\"");
      borrarFicheros.setBackground(Color.red);
      borrarFicheros.setToolTipText("Borrar los ficheros intermedios");

      ensamblar = new JButton("   ensamblar...   ");
      ensamblar.setBorder(miborde);
      ensamblar.setActionCommand("ensamblar");
      ensamblar.setToolTipText("Para unir los mapas descargados en uno solo");
      ensamblar.setBackground(Color.gray);
      ensamblar.setForeground(Color.yellow);
      ensamblar.addActionListener(new miActionListener(this));
      p_ensamblar.add(ensambleDirecto, BorderLayout.WEST);
      p_ensamblar.add(ensamblar, BorderLayout.EAST);
      //JPanel rojo5 = new JPanel();
      //rojo5.setBackground(Color.red);
      JPanel pcreativecommons = new JPanel(new BorderLayout());
      pcreativecommons.setBackground(Color.red);      
      String derechos = "dat" + File.separator + "somerights20.png";
      //ImageIcon icon = createImageIcon(derechos, "CreativeCommons");
      ImageIcon icon = new ImageIcon(derechos, "CreativeCommons");
      JLabel cc = new JLabel("", icon, JLabel.CENTER);
      cc.setToolTipText(JSigpac.creativecommons);
      cc.addMouseListener(new EventosDelRaton(this, "creativecommons"));;
      //panelEnsamblar.add(rojo5);
      pcreativecommons.add(cc, BorderLayout.WEST);
      pcreativecommons.add(borrarFicheros, BorderLayout.EAST);
      panelEnsamblar.add(pcreativecommons);
      panelEnsamblar.add(p_ensamblar);
	
   bottomPanel.add(panelDescargar);
   bottomPanel.add(panelEnsamblar);
   CargarFormulario();
   //getContentPane().add(bottomPanel); 

   return bottomPanel; 
 }

 public static void AccionesPrevias()
 {
   JSigpac.IniciarTrazas();
   entorno = System.getenv();
   EstablecerPaginaWEB();
   new Servidor();  // Es necesario hacerlo para que se inicialicen las variables estáticas de la clase Servidor
   DeterminarSiEsWindows();
   DeterminarPathNavegadores();
 
  /*	
   System.out.println("_ee_ numero de variables: " + entorno.size());
   Set<String> claves = entorno.keySet();
   int tam = claves.size();
   System.out.println("_ee_ Tamaño de claves: " + tam);
   Object[] arrayClaves = claves.toArray(); 
   for (int i=0; i<tam; i++)
   {
       System.out.println("i="+i+".- " + arrayClaves[i] + " = " + entorno.get(arrayClaves[i]));
   }
  */
  
  if ((new File("nocontadores")).exists())  
  {     
     JSigpac.actualizarContadores = false;    
     JSigpac.ImprimirLinea("No se actualizaran los contadores");
  } else
     JSigpac.actualizarContadores = true;
     
  //JSigpac.ImprimirLinea("actualizarContadores=" + JSigpac.actualizarContadores);
 }
 
 public static void EstablecerPaginaWEB()
 {
   FileInputStream fis=null;
   JSigpac.siteJSIGPAC = "http://sites.google.com/site/jrariasf/";   // Valor por defecto
   JSigpac.webJSIGPAC = JSigpac.siteJSIGPAC + "sigpac.html"; //"http://www.myjavaserver.com/~jrariasf/sigpac/sigpac.html";
   JSigpac.servlet = JSigpac.siteJSIGPAC + "jSIGPAC.asp?"; //"http://www.myjavaserver.com/servlet/jrariasf.servlet.sigpac.JSigpac_Servlet?";

   File dat = new File("dat" + File.separator + "jsigpac.paginaweb");
   if (dat.exists())
   {
      try {
        fis = new FileInputStream(dat);
	Properties misProperties = new Properties();
        misProperties.load(fis);
        JSigpac.Traza("Contenido leido del fichero \"" + dat.getCanonicalPath() + "\":");
        JSigpac.Traza(misProperties.toString());
        //misProperties.list(System.out);
	String miweb = misProperties.getProperty("web");
	if (miweb != null)
	   JSigpac.siteJSIGPAC = miweb;                
        
      } catch (FileNotFoundException fnfe) {
	JSigpac.Traza("JSigpac::EstablecerPaginaWEB No se ha encontrado el fichero: " + dat.getPath());
      } catch (SecurityException se) {
	JSigpac.Traza("Acceso de lectura denegado al fichero: " + dat.getPath());
      } catch (IllegalArgumentException se) {
	JSigpac.Traza("Formato incorrecto del fichero: " + dat.getPath());
      } catch (IOException se) {
	JSigpac.Traza("Error al intentar leer el fichero: " + dat.getPath());
      }	finally {
      	 try {
           if (fis != null)
           {
      	      fis.close();      
      	      fis = null;
      	   }
         } catch (IOException ioe) {}      	
      }
   }	
   
   // Si el último carácter no es "/", se añade:
   //System.out.println("Antes: " + JSigpac.siteJSIGPAC);
   if (JSigpac.siteJSIGPAC.trim().charAt( JSigpac.siteJSIGPAC.trim().length()-1 ) != '/')
      JSigpac.siteJSIGPAC += "/";
   //System.out.println("Despues: " + JSigpac.siteJSIGPAC);
   JSigpac.webJSIGPAC = JSigpac.siteJSIGPAC + "sigpac.html";
   JSigpac.servlet = JSigpac.siteJSIGPAC + "jSIGPAC.asp?";
 	
 }
 
 /*   Para saber si es un ordenador con Windows, lo que voy a hacer es mirar 
      si hay definidas una serie de variables de entorno. Si hay definidas unas
      cuantas, pues supongo que es un PC con Windows:
  */
 public static void DeterminarSiEsWindows()
 {    
	@SuppressWarnings("unused")
	String valor;
    int contador = 0;
    if ((valor = entorno.get("ProgramFiles")) != null)
       contador++;
    if ((valor = entorno.get("os")) != null)
       contador++;
    if ((valor = entorno.get("windir")) != null)
       contador++;
    if ((valor = entorno.get("COMPUTERNAME")) != null)
       contador++;             
    
    if (contador < 1)	
       esWindows = false;
    else
       esWindows = true;
 }

 public static boolean EsWindows()
 {
    //System.out.println("EsWindows() devuelve " + esWindows);
    return esWindows;
 }
 
 public static String PathFireFox()
 {
    return pathFireFox;
 }
 
 public static String PathExplorer()
 {
    return pathExplorer;
 }
 
 public static void DeterminarPathNavegadores()
 {
    String program;
    
    program = entorno.get("JSIGPAC_NAVEGADOR");
    if (program != null)
    {
       pathFireFox = program;
       pathExplorer = program;
    } else
    {
       if (JSigpac.EsWindows())
       {
          program = entorno.get("ProgramFiles");
          if (program == null)
             program = "C:\\Archivos de programa";
       
          pathFireFox = program + File.separator + "Mozilla Firefox" + File.separator + "firefox.exe";        
          pathExplorer = program + File.separator + "Internet Explorer" + File.separator + "iexplore.exe";            
       } else // Suponemos que es Unix o Linux (por ahora pasamos de los Mac porque no tengo dónde probar):
       {
          pathFireFox = "netscape"; 
          pathExplorer = "netscape"; 
       }   
    }
 }


 // Cuando se llame a esta rutina hay que asegurarse que están creadas las variables "esquinaRadio" y "resolucion":
 void DarValorComboServidor(String valor)
 {
   JSigpac.Traza("DarValorComboServidor valor="+valor);
   servidor = new Servidor(valor);
   if (servidor.Id() != -1)
   {   
      servidorCombo.setSelectedItem(servidor.Nombre());   
      visor.setToolTipText(Servidor.visor[servidor.Id()]);
   }
 
   vistaPajaro.setEnabled(servidor.HayVistaPajaro());
   
 /*   
   if (servidor.Id() == 1 || servidor.Id() == 4 || servidor.Id() == 16 || servidor.Id() == 17)
      vistaPajaro.setEnabled(true);
   else if (servidor.EsVersion4())
      vistaPajaro.setEnabled(false);
   else
      vistaPajaro.setEnabled(true); 
      
        
   if (servidor.Id() == 0 || servidor.Id() == 8 || servidor.Id() == 19)
      vistaPajaro.setEnabled(false);
   else 
      vistaPajaro.setEnabled(true);
  */       
 }
 
 public static boolean ActualizarContadores()
 {
   return actualizarContadores;	
 }
 
 void CrearBarraDeProgreso()
 {
   
   //panelProgreso.setBorder(sinborde);
   progreso = new JProgressBar();
   progreso.setBorder(sinborde);
   progreso.setStringPainted(true);
   //progreso.setString("Ensamblar");
   progreso.setPreferredSize(new java.awt.Dimension(180, 20));     
   progreso.setBackground(Color.orange);       
   progreso.setMinimum(0); //(new Float(ControlMin)).intValue());
   progreso.setMaximum(100); //(new Float(ControlMax)).intValue());
   progreso.setForeground(Color.red);
   progreso.setValue(0);
 }

 
 void DarValorMaximoBarraDeProgreso(int valor)
 {
 	
   if (progreso != null)  
   { 
      progreso.setMaximum(valor);	
      progreso.setValue(0);
   }
   
   //System.out.println(" ***************  DarValorMaximoBarraDeProgreso=" + valor);	
 }
 
 void PonerTextoEnBarradeProgreso(String texto)
 {
   if (progreso != null)   
   {
      progreso.setString(texto);
      progreso.setValue(0);
   }	
      
 }
 
 void IncrementarBarraDeProgreso(int cantidad, boolean esDescarga)
 {
   if (esDescarga)
   {
      if (progreso != null)
      {
         progreso.setValue(progreso.getValue() + cantidad);
         progreso.setString("Descargando...   " + (int)(progreso.getPercentComplete() * 100) + "%");
      }
   } else // Estamos ensamblando:
   {
      if (progreso != null)
      {
      	 progreso.setValue(progreso.getValue() + cantidad);
      	 progreso.setString("Ensamblando mapa " + progreso.getValue() + " de " + progreso.getMaximum());
      }	
   }
   //System.out.println(" ***************  Incrementar::getPercentComplete()= " + progreso.getPercentComplete());	 	
   //System.out.println(" ***************  Incrementar::getValue()= " + progreso.getValue());
 }
 
 void DarValorBarraDeProgreso(int valor)
 {
   if (progreso != null)
      progreso.setValue(valor);	 
   //System.out.println(" ***************  DarValorBarraDeProgreso= " + valor);
   //System.out.println(" ***************  getPercentComplete()= " + progreso.getPercentComplete());	
 } 

 public void ValoresPorDefecto()
 {
   boolean anchoTOTAL = true;
   //JSigpac.ImprimirLinea("ValoresPorDefecto");
   topo_orto.setSelectedItem(opcionesComboTipoMapa[1]);
   //_xx_servidorCombo
   //servidorCombo.setSelectedItem(opcionesComboServidor[0]);
   DarValorComboServidor(Servidor.opcionesComboServidor[Servidor.NACIONAL]);   
   s_huso.setValue("30");
   esquinaRadio.setSelected(true);
   anchoTRadio.setSelected(anchoTOTAL);
   //anchoCRadio.setSelected(false);    
   t_num_filas.setEnabled(anchoCRadio.isSelected());
   t_num_col.setEnabled(anchoCRadio.isSelected());   
   b_esqinfder.setEnabled(true);
   t_x.setText("");
   t_y.setText("");
   t_ancho.setText("");
   t_alto.setText("");
   t_num_filas.setText("");
   t_num_col.setText("");  
   resolucion.setActionCommand("A la mierda"); // Es una solución chapucera pero que funciona y evita que salte el ActionListener al llamar a setSelectedItem()
   resolucion.setSelectedItem("1");
   resolucion.setActionCommand("resolucion");
   resolucion.setEnabled(anchoTOTAL);
   directorio.setToolTipText(new File(((new File(".")).getAbsolutePath())).getParent());  //_ccc_
   t_fichero.setText("");
   quitaManchas.setSelected(false);
   fcalibra.setSelected(true);
   WGS84.setSelected(true);
   PonerEstadoEscala(false);
   solapamiento.setSelected(false);
   soloCuadrante.setSelected(false);
   t_i.setText("");
   t_j.setText("");
   t_fichEnsam.setText("");
   t_filas.setText("");
   t_columnas.setText("");
   t_mapasH.setText("1");
   t_mapasV.setText("1");
   compresionJPEG.setValue(new Integer(70));
   borrarFicheros.setSelected(true);
   ensambleDirecto.setSelected(false);
   cuadranton.setText("0,0");
 }
  
 
 public void PonerSTOP(boolean activar)
 {
   PonerSTOP(activar, null);
 }
 
 public void PonerSTOP(boolean activar, String msj)
 {
   JSigpac.Traza("JSigpac::PonerSTOP  activar="+activar+"   msj="+msj);
   if (JSigpac.entornoGrafico)
   {
      //System.out.println("_cc_   ===========***********************  PonerSTOP="+activar);
      // Lo primero sera hacer que finalicen todos los threads que pueda haber lanzados:      
      if (activar)
      {
      	 JSigpac.Traza("Ponemos el boton a STOP");
         descargar.setText("STOP");
         descargar.setActionCommand("STOP");
         descargar.setToolTipText("Cancelar el proceso de descarga y/o ensamblado");
         ensamblar.setText("STOP");
         ensamblar.setActionCommand("STOP");
         ensamblar.setToolTipText("Cancelar el proceso de descarga y/o ensamblado");
         DarValorBarraDeProgreso(0);
      } else
      {
         descargar.setText("descargar...");
         descargar.setActionCommand("descargar");
         descargar.setToolTipText("Solamente se descargan los mapas. Luego habra que ensamblarlos");
         ensamblar.setText("ensamblar...");
         ensamblar.setActionCommand("ensamblar");
         ensamblar.setToolTipText("Para unir los mapas descargados en uno solo");  
         //System.out.println("_xx_ PonerSTOP msj=" + (msj != null ? ": " + msj : ""));
         progreso.setString("Proceso finalizado" + (msj != null ? ": " + msj : "")); 
         JSigpac.DesActivarImpresionEnFichero();	   	
      }      	
    }
 }
 
 public static String[] UnirArgumentos(String[] args_inicio, String[] args_fin)
 {
   String[] sumatorio;
   int total = args_inicio.length + args_fin.length;
   int i,j;
   
   JSigpac.Traza("UnirArgumentos:  args_inicio("+args_inicio.length+")  args_fin("+args_fin.length+")  total="+total);
   sumatorio = new String[total];
   for (i=0; i<args_inicio.length; i++)
   {   	
       sumatorio[i] = args_inicio[i];
       JSigpac.Traza("sumatorio["+i+"]="+sumatorio[i]);
   }
   j=0;    
   for (; i<total; i++)
   {
       sumatorio[i] = args_fin[j++];
       JSigpac.Traza("sumatorio["+i+"]="+sumatorio[i]);
   }
 
   return sumatorio;	 	
 }
  
 /*
      Se devuelve una cadena con lo que seria el nombre del fichero
 */ 
 String GetNombreFichero()
 {
   String nombre=null;
   int ancho=0, alto=0;
   
   if (topo_orto.getSelectedItem().equals(opcionesComboTipoMapa[0]))
       nombre = "TOPO" + getEscala();
   else
       nombre = "ORTO";
   
   try {   
      ancho = (int)(Double.parseDouble(t_ancho.getText().trim()));      
   } catch (NumberFormatException nfe) {
      SacarVentanita("Datos incorrectos", "Ancho incorrecto.");
      return null;
   }
   
   try {
      alto = (int)(Double.parseDouble(t_alto.getText().trim()));
   } catch (NumberFormatException nfe) {
      alto = 0;  // (int)(ancho / Mapa.FACTOR);
   }
      
   nombre = nombre + "_r" + ((String)resolucion.getSelectedItem()).trim() +
             "_h" + (String)s_huso.getValue() +
             (esquinaRadio.isSelected() ? "_qx" : "_x") + (int)(Double.parseDouble(t_x.getText().trim())) + 
             (esquinaRadio.isSelected() ? "_qy" : "_y") + (int)(Double.parseDouble(t_y.getText().trim())) +
             (anchoCRadio.isSelected() ? 
                     "_a" + ancho + "_col" + t_num_col.getText() + "_fil" + t_num_filas.getText() :
                     "_A" + ancho + "_H" + alto);                            

   return nombre;                              	
 }
  
 void RepintarValoresCorrectos()
 {
   // Ahora repintamos los valores correctos:
   if (esquinaRadio.isSelected())
   {
      t_x.setText(Double.toString(miMapa.opciones.qx));
      t_y.setText(Double.toString(miMapa.opciones.qy));                     
   } else
   {
      t_x.setText(Double.toString(miMapa.opciones.x));
      t_y.setText(Double.toString(miMapa.opciones.y));	                	
   }
   if (anchoCRadio.isSelected())
   {
      t_ancho.setText(miMapa.opciones.anchura.toString());
      t_alto.setText("");                     
   } else
   {
      t_ancho.setText(Double.toString(miMapa.opciones.anchuraTotal));
      t_alto.setText(Double.toString(miMapa.opciones.alturaTotal));  	                	
   }
   t_num_filas.setText(Integer.toString(miMapa.opciones.n_filas));
   t_num_col.setText(Integer.toString(miMapa.opciones.n_col));    
 }
  
  
 
 
 boolean ObtenerIPServidor()
 {     
    JSigpac.Traza("ObtenerIPServidor...");           
    if (JSigpac.entornoGrafico)
    {                    
       if (servidorCombo == null)
       {
          JSigpac.Traza("Jsigpac::ObtenerIPServidor  servidorCombo == null");
          return false;
       }
       if (vistaPajaro == null)
       {
          JSigpac.Traza("Jsigpac::ObtenerIPServidor  vistaPajaro == null");
          return false;
       }   
       
       int server = servidorCombo.getSelectedIndex();
       servidor = new Servidor(Servidor.opcionesComboIPServidor[server]); 
       JSigpac.Traza("servidor.Id()=" + servidor.Id());
       if (servidor.Id() == -1)
       {
       	  SacarVentanita("Servidor no válido", "Error al configurar el servidor de mapas");
       	  return false;
       }
       
       vistaPajaro.setEnabled(servidor.HayVistaPajaro());
     /*
       if (servidor.Id() == 1 || servidor.Id() == 4 || servidor.Id() == 16 || servidor.Id() == 17)
       {
       	  JSigpac.Traza("vistaPajaro.setEnabled(true)");
          vistaPajaro.setEnabled(true);
       } else if (servidor.EsVersion4()) {
       	  JSigpac.Traza("vistaPajaro.setEnabled(false) porque esVersion4");
          vistaPajaro.setEnabled(false);
       }else {
          JSigpac.Traza("vistaPajaro.setEnabled(true) en el resto de los casos");
          vistaPajaro.setEnabled(false); 
       }
        
       if (servidor.Id() == 0 || servidor.Id() == 8 || servidor.Id() == 19)
          vistaPajaro.setEnabled(false);
       else 
          vistaPajaro.setEnabled(true);                       
      */ 
       //JSigpac.ImprimirLinea("ObtenerIPServidor  servidor = " + JSigpac.servidor.URL());
       Mapa.Dimensionar();       	            
    } else
    {
       EstablecerServidor();
       //JSigpac.ImprimirLinea("ObtenerIPServidor  servidor = " + JSigpac.servidor.URL());
       Mapa.Dimensionar();       
    }
    return true;
 }
 
 
 
  
 
 static boolean EstablecerServidor()
 {
   // Se establece el servidor o visor del SIGPAC desde el que se
   // descargaran los mapas (cuadrantes). Se leera del fichero
   // "dat/jsigpac.server" y nos quedamos con la primera linea
   // que empiece por "http://"
   BufferedReader in=null;
   String linea;
   String nom_fservidor = "dat" + File.separator + "jsigpac.server";
   File fservidor = new File(nom_fservidor);
   // Damos un valor inicial:  
   servidor = new Servidor(Servidor.abreviaturaServidor[Servidor.NACIONAL]);
   
   if (fservidor.exists())
   {
      try {
         in = new BufferedReader(new FileReader(nom_fservidor));
	 while ((linea=in.readLine()) != null)
	 {
	    if (linea.trim().startsWith("http://"))
	    {
	       servidor = new Servidor(linea.trim());
	       in.close();
	       in = null;
	       return true;
            }
         }
      } catch (FileNotFoundException fnfe) {
	 JSigpac.ImprimirLinea("Fichero [" + nom_fservidor + "] no encontrado");
         return false;
      } catch (NullPointerException npe) {
	 JSigpac.ImprimirLinea("Fichero [" + nom_fservidor + "] no encontrado");
	 return false;
      } catch (IOException e) {
	 JSigpac.ImprimirLinea("Error de lectura en fichero [" + nom_fservidor + "]");
	 return false;
      } finally {
         try {
           in.close(); 
           in = null;       
         } catch (IOException ioe) {}
      }
   }
   return false;
 }
  
  
 /*
    RecalcularCoordenadas sirve para calcular los nuevos valores de la X y la Y del cuadranton
    adyacente (tanto en la vertical como en la horizontal). Es decir, calcula las coordenadas
    del mapa que se encuentra al lado (ya sea a la izquierda o a la derecha o arriba o debajo).
 */
 void RecalcularCoordenadas(int i, int j)
 {
   double tx, ty, ancho_cuadrante, tancho, desplazVertical, desplazHor;
   int tfil, tcol;
   String msj = "";

   try {
      msj = "Coordenada X incorrecta";
      tx = (Double.valueOf(t_x.getText())).doubleValue();
      msj = "Coordenada Y incorrecta";
      ty = (Double.valueOf(t_y.getText())).doubleValue();
      msj = "Valor de la anchura incorrecto";
      tancho = (Double.valueOf(t_ancho.getText())).doubleValue();
      msj = "Numero de filas incorrecto";
      tfil = (Integer.valueOf(t_num_filas.getText())).intValue();
      msj = "Numero de columnas incorrecto";
      tcol = (Integer.valueOf(t_num_col.getText())).intValue();
      if (anchoCRadio.isSelected())
      {
	 ancho_cuadrante = tancho;
	 desplazHor = tancho * tcol;
      } else
      {
	 ancho_cuadrante = tancho / tcol;
	 desplazHor = tancho;
      }
      desplazVertical = (ancho_cuadrante / Mapa.FACTOR) * tfil;

      if (j == -1)
      {
         tx = tx - desplazHor;
	 t_x.setText(Double.toString(tx));
      }
      if (j == 1)
      {
         tx = tx + desplazHor;
	 t_x.setText(Double.toString(tx));
      }
      if (i == -1)
      {
         ty = ty + desplazVertical;
	 t_y.setText(Double.toString(ty));
      }
      if (i == 1)
      {
         ty = ty - desplazVertical;
	 t_y.setText(Double.toString(ty));
      }

   } catch (NumberFormatException nfe) {
      JOptionPane.showMessageDialog(this, msj,
	    "Parametro incorrecto", JOptionPane.ERROR_MESSAGE);
      JSigpac.ImprimirLinea(msj);
   }

 }


 void SacarVentanita(String titulo, String mensaje, int tipo)
 {
    JSigpac.ImprimirLinea(titulo + ":\n- " + mensaje);
    if (JSigpac.entornoGrafico)
       JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);                                     
 }
 
 void SacarVentanita(String titulo, String mensaje)
 {
    SacarVentanita(titulo, mensaje, JOptionPane.ERROR_MESSAGE);   
 }

 void PonerEstadoEscala(boolean estado)
 {
   mtn2000T.setEnabled(estado);
   mtn2000.setEnabled(estado);
   mtn1250.setEnabled(estado);
   mtn200.setEnabled(estado);
   mtn25.setEnabled(estado);
 }

 public void IncrementarUtilizacionJSIGPAC()
 {
   URL url;
   URLConnection conexion;   
   
   String direccion = JSigpac.servlet + "anuncio=jsigpac&operacion=inc";
   
   if (!ActualizarContadores())
      return;
    
   //System.out.println("direccion="+direccion); 
   try {
      url = new URL(direccion);
      conexion = url.openConnection();
      conexion.connect();
      conexion.getContent(); //Obj obj = conexion.getContent();
      //System.out.println("obj="+obj.toString());
   } catch (UnknownHostException e) {     
      JSigpac.Traza("UnknownHostException IUJ");
   } catch (MalformedURLException e) {
      JSigpac.Traza("MalformedException IUJ");
   } catch (FileNotFoundException fnfe) {
      JSigpac.Traza("FileNotFoundException IUJ");    
   } catch( IOException e) {
      JSigpac.Traza("IOException IUJ");
   }  	 	
 }

 void ComprobarVersion()
 {
   //  La versión aparece en la web como:  jSIGPACv5.6.8#xx#
   
   byte[] datos = new byte[8192];
   URLConnection conexion;
   InputStream is;
   int leidos;
   String msj;
   boolean salirDelBucle = false;

   if (primeraVez_CV)
   {
     primeraVez_CV = false;
     try {
       //URL url = new URL("http://www.myjavaserver.com/~jrariasf/sigpac/jsigpac.version");
       URL url = new URL(siteJSIGPAC + "version");
       //System.out.println("_elias_ Vamos a hacer el openConnection para ComprobarVersion");
       conexion = url.openConnection();
       //System.out.println("_elias_ Hecho el openConnection");
       is = conexion.getInputStream();
       //System.out.println("_elias_ Hecho el getInputStream");
       while ((leidos=is.read(datos)) != -1 && !salirDelBucle       )
       {              
       	  msj = new String(datos);
          //System.out.println("leidos="+leidos+"  Version leida: "+msj.trim());
	  String verUltima, verActual;
          int posV, posAlmohadilla;
          posV = msj.indexOf("jSIGPACv");
          //System.out.println("posV=" + posV);
          if (posV != -1)
          {
             posV = posV + "jSIGPACv".length();
             posAlmohadilla = msj.indexOf("#xx#");
             //System.out.println("posAlmohadilla=" + posAlmohadilla);
	     verUltima = (msj.substring(posV, posAlmohadilla)).trim();
	     posV = (JSigpac.version.trim()).indexOf("v");
	     posV++;
	     verActual = JSigpac.version.substring(posV);
	     //System.out.println("verUltima="+verUltima+"   verActual="+verActual);
             if (verUltima.compareTo(verActual) > 0)
             {
  	        ImprimirLinea("");
                ImprimirLinea("           Hay una nueva version disponible !!!  (jSIGPAC_"+verUltima+")");
	        ImprimirLinea("");
                if (JSigpac.entornoGrafico == true)
	           nuevaVersion.setText("[Nueva version jSIGPAC_"+verUltima+"]");
             }
             salirDelBucle = true;
          }

       }
       
       is.close();
       
       // Incrementamos contador de utilizacion del jSIGPAC:
       IncrementarUtilizacionJSIGPAC();
       /*
        } catch (UnknownHostException e) {       
          System.err.println("No se ha podido comprobar si hay nueva version (1)");
          System.err.println("UnknownHostException: " + e);
        } catch (MalformedURLException e) {       
          System.err.println("No se ha podido comprobar si hay nueva version (2)");
          System.err.println("MalformedException: " + e);
        } catch (FileNotFoundException fnfe) {       
          System.err.println("No se ha podido comprobar si hay nueva version (3)");
          System.err.println("FileNotFoundException: " + fnfe);
       */
     } catch( UnknownHostException uhe) {    
     	//SacarVentanita("Sin conexion a Internet", "Es posible que no tenga salida a Internet por temas del firewall o del proxy");     	
        System.err.println("No se ha podido comprobar si hay nueva version (4)");
        System.err.println("Es posible que no tenga salida a Internet por temas del firewall o del proxy");
        System.err.println("UnknownHostException: " + uhe);
        JSigpac.Traza("UnknownHostException: " + uhe);
     } catch( StringIndexOutOfBoundsException siobe) {
     	System.err.println("No se ha podido comprobar si hay nueva version (5)");
     	JSigpac.Traza("StringIndexOutOfBounds: " + siobe);
     } catch( IOException e) {
     	System.err.println("No se ha podido comprobar si hay nueva version (6)");
     	JSigpac.Traza("IOException: " + e);
     } 
   }
 }
 
 void setToolTipTextResolucion()
 {
    try {
       double res;
       //if (resolucion == null)
       //   System.out.println("setToolTipTextResolucion   resolucion == null");
       res = (Double.valueOf(((String)resolucion.getSelectedItem()).trim() )).doubleValue();          
       resolucion.setToolTipText("cuadrante de [ "+(res*Mapa.width)+" m. x "+(res*Mapa.height)+" m. ]");
    } catch (NumberFormatException nfe) {
       resolucion.setToolTipText("");
    }
 }

 // Segun el valor del campo "Ancho" y/o "Alto" y la resolucion,
 // se calcula el numero de filas y de columnas:
 
 void AplicarResolucion()
 {       
    double ancho, alto;
    double res;
    int[] respuesta;
    String err="";
    //System.out.println("_xx_ Estamos en AplicarResolucion");
    
    if (!resolucion.isEnabled())
       return;
       
    setToolTipTextResolucion();
    try {
	  err = "Valor incorrecto en campo \"Ancho\"";
	  ancho = (Double.valueOf( t_ancho.getText().trim() )).doubleValue();
	  
	  err = "Valor incorrecto en campo \"resolucion\"";
	  res = (Double.valueOf(((String)resolucion.getSelectedItem()).trim())).doubleValue();
	  
	  err = "Valor incorrecto en campo \"Alto\"";
	  if (t_alto.getText().trim().equals(""))
	     alto = -1.0D;
	  else
	     alto = (Double.valueOf( t_alto.getText().trim() )).doubleValue();
	  	   
	 respuesta = miMapa.AplicarResolucion(ancho, alto, res);
	 err = "Error al calcular el numero de filas y columnas";	 
	 t_ancho.setText(Integer.toString(respuesta[0]));   //_xx_vv_ 
	 t_alto.setText(Integer.toString(respuesta[1]));	 
	 t_num_col.setText(Integer.toString(respuesta[2]));	
	 t_num_filas.setText(Integer.toString(respuesta[3]));  	 
	 
    } catch (NumberFormatException nfe) {
	  JOptionPane.showMessageDialog(this, err,
	       "Parametro incorrecto", JOptionPane.ERROR_MESSAGE);
    }
 }

 
 //  Cuando se importa un fichero de track, ruta o waypoints, se llama a PasarCoordenadas:
 public void PasarCoordenadas()
 {
   String msj = "Error al obtener coordenadas desde el fichero";;
   double xsi, ysi, xid, yid;
   byte hsi, hid;
   double ancho, alto;
   
   JSigpac.Traza("respCoor.length="+respCoor.length);
   
   if (respCoor == null || respCoor.length < 7)
      return;
   //ValoresPorDefecto();
   try {
      msj = "Error al obtener coordenada X de la esquina superior izquierda";
      xsi = respCoor[0] - respCoor[6];
      t_x.setText(Integer.toString((int)xsi));
      msj = "Error al obtener coordenada Y de la esquina superior izquierda";
      ysi = respCoor[1] + respCoor[6];
      t_y.setText(Integer.toString((int)ysi));
      msj = "Error al obtener el huso de la esquina superior izquierda";
      hsi = (byte)respCoor[2];
      esquinaRadio.setSelected(true);
      anchoTRadio.setSelected(true);
      resolucion.setEnabled(true);
      
      msj = "Error al obtener coordenada X de la esquina inferior derecha";
      xid = respCoor[3] + respCoor[6];      
      msj = "Error al obtener coordenada Y de la esquina inferior derecha";
      yid = respCoor[4] - respCoor[6];
      msj = "Error al obtener el huso de la esquina inferior derecha";
      hid = (byte)respCoor[5];
      
      Distancia distancia = new Distancia(xsi, ysi, hsi, xid, yid, hid, datum_respCoor);
      ancho = distancia.CalcularAncho();
      alto = distancia.CalcularAlto();
      msj = "Error al calcular el ancho total del mapa";
      t_ancho.setText(Integer.toString((int)ancho));
      msj = "Error al calcular el alto total del mapa";
      t_alto.setText(Integer.toString((int)alto));
      msj = "Huso incorrecto [" + Byte.toString(hsi) + "]. Son válidos los husos 27, 28, 29, 30 y 31";	
      s_huso.setValue(Byte.toString(hsi));
       
      t_num_filas.setText("");
      t_num_col.setText("");
      t_num_filas.setEnabled(anchoCRadio.isSelected());
      t_num_col.setEnabled(anchoCRadio.isSelected());
      cuadranton.setText("0,0");
      
      /*
      msj = "Error al calcular el ancho total del mapa";
      t_ancho.setText(Integer.toString((int)(respCoor[2]-respCoor[0] + (2*respCoor[5]))));
      msj = "Error al calcular el alto total del mapa";
      t_alto.setText(Integer.toString((int)(respCoor[1]-respCoor[3] + (2*respCoor[5]))));
      msj = "Error al calcular el huso del mapa";            
      msj = "Huso incorrecto [" + Integer.toString((int)respCoor[4]) + "]. Son válidos los husos 28, 29, 30 y 31";	
      s_huso.setValue(Integer.toString((int)respCoor[4]));    	
      */
   } catch (NullPointerException npe) {
      SacarVentanita("Error al importar datos", msj);
   } catch (NumberFormatException nfe) {
      SacarVentanita("Error al importar datos", msj);
   } catch (IllegalArgumentException iae) {
      SacarVentanita("Error al importar datos", msj);
   }
 }
 
 
 /* 
    Devuelve la escala utilizada como String
 */
 
 public String getEscala()
 {
   String esc = "25";
   if (mtn25.isSelected())
      esc = "25";
   else if (mtn200.isSelected())
      esc = "200";
   else if (mtn1250.isSelected())
      esc = "1250";
   else if (mtn2000.isSelected())
      esc = "2000";
   else if (mtn2000T.isSelected())
      esc = "2000_T";	
 	
   return esc; 	
 }
 
 
 /*
    Para guarda en el fichero que se le pasa todos los datos de la descarga
  */
 public boolean GuardarEnFichero(String nomfich)
 {
   PrintWriter jsi = null;
                       
   try {
      jsi = new PrintWriter(new BufferedWriter(new FileWriter(nomfich)));
      
      jsi.println("servidor: " + (String)servidorCombo.getSelectedItem());
      jsi.println("datum_entrada: " + (String)comboDatum.getSelectedItem());
      jsi.println("ORTO: " + topo_orto.getSelectedItem().equals("Ortofoto"));
      jsi.println("esquina: " + esquinaRadio.isSelected());
      jsi.println("cuadrante: " + anchoCRadio.isSelected());
      jsi.println("huso: " + (String)s_huso.getValue());
      jsi.println("X: " + t_x.getText());
      jsi.println("Y: " + t_y.getText());

      jsi.println("ancho: " + t_ancho.getText());
      jsi.println("alto: " + t_alto.getText());
      jsi.println("resolucion: " + resolucion.getSelectedItem());      
      
      jsi.println("filas: " + t_num_filas.getText());
      jsi.println("columnas: " + t_num_col.getText());
      jsi.println("cuadranton: " + cuadranton.getText());
      
      jsi.println("escala: " + getEscala());

      try {
         String aux; // Hay que substituir el posible caracter separador de directorios
	   	     // de Windows "\" por "\\" ya que si no habra problemas a la hora 
		     // de leer del fichero la informacion de la carpeta:
         //System.out.println("directorio:"+miMapa.opciones.directorio.toString());                         
         //System.out.println("Hacemos replace: aux="+tous);
         //aux = miMapa.opciones.directorio.toString().split("\\\\");
         //for (int k=0; k<aux.length; k++)
         //{
         //   System.out.println("aux["+k+"]="+aux[k]);
         //}
         
         // directorio.setToolTipText(new File(((new File(".")).getAbsolutePath())).getParent())
         /*
         JSigpac.ImprimirLinea("miMapa: " + (miMapa == null ? "es NULL  " : "no es NULL  ") +
                               "miMapa.opciones: " + (miMapa.opciones == null ? "es NULL  " : "no es NULL  ") +
                               "miMapa.opciones.directorio: " + (miMapa.opciones.directorio == null ? "es NULL  " : "no es NULL  "));
         */
         if (miMapa != null && miMapa.opciones != null && miMapa.opciones.directorio != null)         
         {
            //JSigpac.ImprimirLinea("directorio="+miMapa.opciones.directorio);
            aux = miMapa.opciones.directorio.toString().replaceAll("\\\\", "\\\\\\\\");
            //JSigpac.ImprimirLinea("aux="+aux);
            //aux = miMapa.opciones.directorio.toString();   //_xx_DD_
         } else
         {
            aux = (new File(((new File(".")).getAbsolutePath()))).getParent();
            //JSigpac.ImprimirLinea("_ccc_ aux="+aux);
            aux = aux.replaceAll("\\\\", "\\\\\\\\");
            //JSigpac.ImprimirLinea("_ccc_ aux="+aux);
         }
         jsi.println("carpeta: " + aux);
         
      } catch (NullPointerException npe) {
      	 JSigpac.ImprimirLinea("Error al guardar la carpeta: " + npe);
      }
      jsi.println("fichero: " + t_fichero.getText());
      jsi.println("quitamanchas: " + quitaManchas.isSelected());
      jsi.println("calibrar: " + fcalibra.isSelected());
      if (WGS84.isSelected())
         jsi.println("datum_calibrar: WGS84");
      else
         jsi.println("datum_calibrar: ED50");
      
      jsi.println("solapamiento: " + solapamiento.isSelected());
      jsi.println("ficheroEnsam: " + t_fichEnsam.getText());
      jsi.println("filasEn: " + t_filas.getText());
      jsi.println("columnasEn: " + t_columnas.getText());
      jsi.println("compresion: " + compresionJPEG.getValue());
      jsi.println("borrar_intermedios: " + borrarFicheros.isSelected());
      jsi.println("desYens: " + ensambleDirecto.isSelected());      
      jsi.println("mapasVertical: " + t_mapasV.getText());
      jsi.println("mapasHorizontal: " + t_mapasH.getText());

   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar guardar los datos de la ultima ejecucion");
      if (jsi != null)
      {
         jsi.close();
         jsi = null;
      }
      return false;
   }

   if (jsi != null)
   {
      jsi.flush();
      jsi.close();
   }
	
   return true; 	
 }
  
  
 /*
    Para leer de un fichero con formato JSI los datos de una descarga.
    Se llama desde CargarFormulario()
  */
 
 public boolean LeerDeFichero(String nomfich)
 {
      String aux;
      File fi = null;
      if (nomfich == null)
         return false;
      
      fi = new File(nomfich);
      
      try {      	
        FileInputStream fis = new FileInputStream(fi);
	Properties misProperties = new Properties();
	
        misProperties.load(fis);
        fis.close();      
        //misProperties.list(System.out);

        //_xx_servidorCombo
        //servidorCombo.setSelectedItem(misProperties.getProperty("servidor", opcionesComboServidor[0]));                      
        DarValorComboServidor(misProperties.getProperty("servidor", Servidor.opcionesComboServidor[Servidor.NACIONAL]));
	   
	if (misProperties.getProperty("datum_entrada", "ED50").equals("ED50"))
           comboDatum.setSelectedItem(JSigpac.opcionesDatum[0]);
        else
           comboDatum.setSelectedItem(JSigpac.opcionesDatum[1]);
           
        if (misProperties.getProperty("ORTO", "true").equals("true"))
	   topo_orto.setSelectedItem("Ortofoto");
        else
	   topo_orto.setSelectedItem("Topografico");
        
        if (misProperties.getProperty("esquina", "true").equals("true"))
	{
	   esquinaRadio.setSelected(true);
	   centroRadio.setSelected(false);
        } else {
	   esquinaRadio.setSelected(false);
	   centroRadio.setSelected(true);
        }

        if (misProperties.getProperty("cuadrante", "true").equals("true"))
	{
	   anchoCRadio.setSelected(true);
	   anchoTRadio.setSelected(false);
	   resolucion.setEnabled(false);
        } else {
	   anchoCRadio.setSelected(false);
	   anchoTRadio.setSelected(true);
	   resolucion.setEnabled(true);
        }

        if (misProperties.getProperty("esquina", "true").equals("true") &&
            misProperties.getProperty("cuadrante", "true").equals("true"))
            b_esqinfder.setSelected(true);
        else
            b_esqinfder.setSelected(false);
        
        
        s_huso.setValue(misProperties.getProperty("huso", "30"));
        
        t_x.setText(misProperties.getProperty("X", ""));
        t_y.setText(misProperties.getProperty("Y", ""));
	t_ancho.setText(misProperties.getProperty("ancho", ""));
        t_alto.setText(misProperties.getProperty("alto", ""));
	t_num_filas.setText(misProperties.getProperty("filas", ""));
	t_num_col.setText(misProperties.getProperty("columnas", ""));
	t_num_filas.setEnabled(anchoCRadio.isSelected());
        t_num_col.setEnabled(anchoCRadio.isSelected());
        cuadranton.setText(misProperties.getProperty("cuadranton", "0,0"));
        
        resolucion.setActionCommand("A la mierda");  // Es una solución chapucera pero que funciona y evita que salte el ActionListener
        resolucion.setSelectedItem(misProperties.getProperty("resolucion", "0.5"));        
        setToolTipTextResolucion();
        resolucion.setActionCommand("resolucion");
       
        aux = misProperties.getProperty("escala", "25");
        if (aux.equals("25"))
	   mtn25.setSelected(true);
        else if (aux.equals("200"))
	   mtn200.setSelected(true);
        else if (aux.equals("1250"))
	   mtn1250.setSelected(true);
        else if (aux.equals("2000"))
	   mtn2000.setSelected(true);
        else if (aux.equals("2000_T"))
	   mtn2000T.setSelected(true);
    
        // CARPETA:
        //JSigpac.ImprimirLinea("carpeta=" + misProperties.getProperty("carpeta")); //_xx_DD_
        /*
        if (miMapa == null)
           System.out.println("LeerDeFichero  miMapa == null");
        else if (miMapa.opciones == null)
           System.out.println("LeerDeFichero  miMapa.opciones == null");
        else if (miMapa.opciones.directorio == null)
           System.out.println("LeerDeFichero  miMapa.opciones.directorio == null");
        */   
        miMapa.opciones.directorio = new File(misProperties.getProperty("carpeta",
                                         new File(((new File(".")).getAbsolutePath())).getParent() ));
        directorio.setToolTipText(miMapa.opciones.directorio.toString());        

        t_fichero.setText(misProperties.getProperty("fichero", ""));
        /*
        if (misProperties.getProperty("quitamanchas", "true").equals("true"))
           quitaManchas.setSelected(true);
        else
           quitaManchas.setSelected(false);
        */
        quitaManchas.setSelected(false);
        
        if (misProperties.getProperty("calibrar", "true").equals("true"))
           fcalibra.setSelected(true);
        else
           fcalibra.setSelected(false);
           
        if (misProperties.getProperty("datum_calibrar", "WGS84").equals("WGS84"))
           WGS84.setSelected(true);
        else
           ED50.setSelected(true);

        /*
        if (misProperties.getProperty("solapamiento", "false").equals("false"))
           solapamiento.setSelected(false);
        else
           solapamiento.setSelected(true);
        */
        solapamiento.setSelected(false);
        
        t_fichEnsam.setText(misProperties.getProperty("ficheroEnsam", ""));

        t_filas.setText(misProperties.getProperty("filasEn", ""));
	
        t_columnas.setText(misProperties.getProperty("columnasEn", ""));

        compresionJPEG.setValue(Integer.valueOf(misProperties.getProperty("compresion", "70")));

        if (misProperties.getProperty("borrar_intermedios", "false").equals("false"))
           borrarFicheros.setSelected(false);
        else
           borrarFicheros.setSelected(true);

        if (misProperties.getProperty("desYens", "false").equals("false"))
           ensambleDirecto.setSelected(false);
        else
           ensambleDirecto.setSelected(true);

        t_mapasH.setText(misProperties.getProperty("mapasHorizontal", "1"));
        t_mapasV.setText(misProperties.getProperty("mapasVertical", "1"));        
        
      } catch (FileNotFoundException fnfe) {
	System.err.println("JSigpac::LeerDeFichero No se ha encontrado el fichero: " + fi.getPath());
	return false;
      } catch (SecurityException se) {
	System.err.println("Acceso de lectura denegado al fichero: " + fi.getPath());
	return false;
      } catch (IllegalArgumentException se) {
	System.err.println("Formato incorrecto del fichero: " + fi.getPath());
	return false;
      } catch (IOException se) {
	System.err.println("Error al intentar leer el fichero: " + fi.getPath());
	return false;
      }	
 
      return true;	
 }
 
 public void DescargarFormulario()
 {
   //JSigpac.ImprimirLinea("_ccc_ DescargarFormulario");
   
   // Grabo en fichero el ultimo comando ejecutado para poder ser 
   // recuperado en una proxima ejecucion del programa a traves de
   // la rutina "CargarFormulario".
   /*
      ORTO: true
      esquina: true
      cuadrante: true
      huso: 30
      X: 365000
      Y: 4760000
      ancho: 618
      resolucion: 2
      filas: 3
      columnas: 4
      escala: 25
      carpeta: c:\jsigpac\mapas
      fichero: mifichero
      quitamanchas: true
      datum_calibrar: WGS84
      solapamiento: false

      ficheroEnsam: mifichero
      filasEn: 3
      columnasEn: 4
      compresion: 80
      borrar_intermedios: true
      desYens: false
   */

   GuardarEnFichero("dat" + File.separator + "jsigpac.ultimo");      
 }


 public void CargarFormulario()
 {
   // Intento leer el fichero "jsigpac.ultimo" para cargar los datos
   // en el formulario. Podemos leer algo parecido a:
   String f = "dat" + File.separator + "jsigpac.ultimo";
   File ultimo = new File(f);
   if (ultimo.exists())
      LeerDeFichero(f); 
   else 
      ValoresPorDefecto();     
 }


 String[] LeerFormulario()
 {
    return LeerFormulario(true);
 }

 /*
   La rutina LeerFormulario lee los valores introducidos a traves de la interfaz grafica y los devuelve 
   en un array de String. El argumento "guardar" sera true si hay que almacenar en fichero los datos de
   la descarga/ensamblado.
 */
 
 String[] LeerFormulario(boolean guardar)
 {
   String[] rest;
   int num_args_fijos;  // Si se especifica la resolucion seran 9 parametros fijos.
                        // Si no se da la resolucion, sino que se dan el numero de filas y de columnas,
                        // entonces seran 10 parametros fijos.
   int num_args_variable = 0;
   int i=0;

   JSigpac.Traza("Leerformulario  guardar="+guardar);
   JSigpac.finalizar = false;
   
   //AplicarResolucion();
   
   if (resolucion.isEnabled())
      num_args_fijos = 9;
   else
      num_args_fijos = 10;
      
   if (esquinaRadio.isSelected())
      num_args_variable++;
   if (fcalibra.isSelected())
      num_args_variable++;
   if (quitaManchas.isSelected())
      num_args_variable++;
   if (soloCuadrante.isSelected())
      num_args_variable = num_args_variable + 2;
   if (WGS84.isSelected())
      num_args_variable++;
   if (solapamiento.isSelected())
      num_args_variable++;
   if (t_alto.getText().trim().length() > 0)
      num_args_variable++;
   if (descargarSoloTrack == true)
      num_args_variable++;
   if (borrarFicheros.isSelected())
      num_args_variable++;
      
   rest = new String[num_args_fijos + num_args_variable];

   //_xx_new_
   servidor = new Servidor((String)servidorCombo.getSelectedItem());
   rest[i] = "-S" + servidor.Abreviatura();   
   i++;
   rest[i] = "-D" + (String)comboDatum.getSelectedItem();   
   i++;
   
   if (topo_orto.getSelectedItem().equals("Topografico"))
   {
      String esc = "";

      if (mtn25.isSelected())
         esc = "25";
      else if (mtn200.isSelected())
         esc = "200";
      else if (mtn1250.isSelected())
         esc = "1250";
      else if (mtn2000.isSelected())
         esc = "2000";
      else if (mtn2000T.isSelected())
         esc = "2000_T";

      rest[i] = "-mTOPO"+esc;
   } else if (topo_orto.getSelectedItem().equals("Ortofoto"))
      rest[i] = "-mORTO";        
   i++;
   rest[i] = "-h" + (String)s_huso.getValue();
   i++;         
   
   if (esquinaRadio.isSelected())
   {
      rest[i] = "-q";
      i++;
   }
   rest[i] = t_x.getText().trim().length() == 0? null : "-x"+t_x.getText().trim();
   i++;
   rest[i] = t_y.getText().trim().length() == 0? null : "-y"+t_y.getText().trim();
   i++;
   if (anchoCRadio.isSelected())
      rest[i] = t_ancho.getText().trim().length() == 0? null : "-a"+t_ancho.getText().trim();
   else
      rest[i] = t_ancho.getText().trim().length() == 0? null : "-A"+t_ancho.getText().trim();
   JSigpac.Traza("ANCHURA: "+rest[i]);
   i++;
   
   if (t_alto.getText().trim().length() > 0)
   {
      rest[i] = "-H" + t_alto.getText().trim();
      JSigpac.Traza("ALTURA: "+rest[i]);
      i++;
   }
   
   if (resolucion.isEnabled())
   {  
      JSigpac.Traza("resolucion.isEnabled()=" + resolucion.isEnabled() + " ---> " + (String)resolucion.getSelectedItem());
      rest[i] = "-r" + ((String)resolucion.getSelectedItem()).trim();
      i++;
   } else
   {
      JSigpac.Traza("resolucion.isEnabled()=" + resolucion.isEnabled());
      rest[i] = t_num_filas.getText().trim().length() == 0? null : "-fil"+t_num_filas.getText().trim();
      i++;
      rest[i] = t_num_col.getText().trim().length() == 0? null : "-col"+t_num_col.getText().trim();
      i++;
   }
   
   if (WGS84.isSelected())
   {
      rest[i] = "-w";
      i++;
   }
   if (soloCuadrante.isSelected())
   {
      //_XX_xx_ rest[i] = t_i.getText().trim().length() == 0? null : "-i"+t_i.getText().trim();
      rest[i] = "-i"+t_i.getText().trim();
      i++;
      //_XX_xx_ rest[i] = t_j.getText().trim().length() == 0? null : "-j"+t_j.getText().trim();
      rest[i] = "-j"+t_j.getText().trim();
      //_xx_ rest[i] = "-j"+t_j.getText().trim();
      i++;
   }
   if (fcalibra.isSelected())
   {
      rest[i] = "-c";
      i++;
   }
   if (quitaManchas.isSelected())
   {
      rest[i] = "-s";
      i++;
   }
   if (solapamiento.isSelected())
   {
      rest[i] = "-sol";
      i++;
   }
   if (descargarSoloTrack == true)
   {
      rest[i] = "-t";
      i++;
   }
   if (borrarFicheros.isSelected())
   {
      rest[i] = "-B";
      i++;
   }
   
   String miaux;
   if (miMapa.opciones.directorio == null)
      miaux = "";
   else
      miaux = miMapa.opciones.directorio.toString() + File.separator;
   rest[i] = t_fichero.getText().trim().length() == 0? "-f"+miaux : "-f"+miaux+t_fichero.getText().trim();

   // Grabamos en fichero los datos del formulario:
   if (guardar)
      DescargarFormulario();
   return rest;
 }
 
 public static void SintaxisIBP(String error)
 {
   System.err.println("");
   System.err.println("Error: " + error);
   System.err.println("La sintaxis es:");
   System.err.println("JSigpac <-ibp | -IBP>  <BTT | Carretera>  <fichero_con_el_trak> [-b]");
   System.err.println("Si el path al fichero contiene espacios, entrecomillelo");
   Salir();
 } 

 public static void ObtenerIBP(String[] params)
 {
   boolean informe = false;
   String mod = "BTT";;
   String fichero;
   boolean abrirNavegador = true;
   
   if (params.length < 3)
      SintaxisIBP("Numero de argumentos incorrecto.");
      
   if (params[0].equals("-ibp"))
      informe = false;
   else if (params[0].equals("-IBP"))
      informe = true;
   else 
      SintaxisIBP("Como primer parametro debe poner \"-ibp\" (para obtener el indice de dureza) o \"-IBP\" (informe completo)");
   
   if (params[1].equals("BTT") || params[1].equals("btt"))
      mod = "BTT";
   else if (params[1].equals("Carretera") || params[1].equals("carretera"))
      mod = "Carretera";
   else 
      SintaxisIBP("Como segundo argumento debe especificar si es una ruta por caminos \"BTT\" o por carretera \"Carretera\"");
    
   fichero = params[2];
     
   if (params.length > 3)
   {
      if (params[3].equals("-b"))
         abrirNavegador = false;
      else  
         SintaxisIBP("El tercer argumento es opcional y en caso de aparecer es \"-b\" para que no se abra el navegador");
   }
         
   ConectarConwebIBP conIBP = new ConectarConwebIBP(fichero, mod, informe, abrirNavegador);   
      
 }
 
 public static void ActivarImpresionEnFichero(String fichero)
 {
   boolean yaexiste = false;
   //System.out.println("ActivarImpresionEnFichero");
   try {
     if (canal != null)
        canal.close();

     File fich = new File(fichero+"_consola.txt");
     if (fich.exists())
	yaexiste = true;
     //System.out.println("yaexiste="+yaexiste);
     canal = new FileOutputStream(fich, true);
     if (!yaexiste)
     {
        ImprimirLinea(version + " " + actualizado.trim());
        //ComprobarVersion();
        ImprimirLinea(JSigpac.webJSIGPAC);
        
      }
   } catch (IOException ioe) {
     canal = null;
     System.err.println("Error al intentar crear el fichero: "+
			 fichero+"_consola.txt");
   }
 }

 public static void DesActivarImpresionEnFichero()
 {   
   try {
     if (canal != null)
     { 
     	canal.close();
     	canal = null;        
     }
     
   } catch (IOException ioe) {
     canal = null;     
   }
 }

 public static void ImprimirLinea(String mensaje)
 {
    ImprimirLinea(mensaje, true); // Se hace el salto de linea
 }

 public static void ImprimirLinea(String mensaje, boolean saltodelinea)
 {
    ImprimirLinea(mensaje, saltodelinea, false);
 }

 public static void ImprimirLinea(String mensaje, boolean saltodelinea,
				  boolean soloEnFichero)
 {
    byte b[];
    // Si se ha cancelado la descarga y/o ensamblado, no se imprimirá nada:
    //if (JSigpac.finalizar)
    //   return;        
       
    if (canal != null)
    {
       try {
       	  //canal.write(' ');
       	  // Lo primero es intentar imprimir lo que pueda haber en el buffer:
       	  if (mibuffer != null)
       	  {
       	     b = mibuffer.toString().getBytes();
       	     canal.write(b); 	
       	     mibuffer = null;	
       	  }
       	  // Y ahora ya imprimimos lo que ha venido ahora:
          b = mensaje.getBytes();
          canal.write(b);
	  if (saltodelinea)
             canal.write('\n');
       } catch (IOException ioe) {
          System.err.println("Excepcion al intentar escribir traza: "+ioe);
          System.err.println("La traza era: " + mensaje);
       }
    } else
    {
      // Todavía no se ha creado el fichero donde se imprimen los mensaje. Por eso, los almacenamos 
      // en un buffer hasta cuando sea posible escribirlos:
      if (mibuffer == null)
         mibuffer = new StringBuffer();
      mibuffer.append(mensaje);	
      if (saltodelinea)
         mibuffer.append('\n');
    }
    
    if (!soloEnFichero)
    {
       if (saltodelinea)
          System.out.println(mensaje);
       else
          System.out.print(mensaje);
    }
 }
 
 public static boolean IniciarTrazas()
 {
    File fi;
    fi = new File("tracear");
    //JSigpac.ImprimirLinea("directorio de tracear: " + fi.getAbsolutePath());
    if (fi.exists())
       tracear = true;
    else
       tracear = false;
    //JSigpac.ImprimirLinea("tracear="+tracear);
    return tracear;
 }
 
 public static void Traza(String traza)
 {
    if (tracear)
       ImprimirLinea("-TRAZA-  " + traza); 	
 }

 public static void SacarAyuda()
 {
 	
   System.err.println("");
   String aux = "************************************************************************";
   int lon_max = aux.length();   
   System.err.println(aux);
   aux = " " + JSigpac.version + " " + JSigpac.actualizado.trim();
   System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
   aux = "           Jose Ramon Arias Frances";
   System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
   aux = "           http://fon.gs/jsigpac";
   System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
   System.err.println("*" + Mapa.pad(" ", lon_max-2) + "*");	 	                                                                

   if (entornoGrafico)
   {
      Ayuda help = new Ayuda("Ayuda "+version, "jsigpac.txt");
      Thread unThread = new Thread(help, "La_Ayuda");
      unThread.start();
   }

   // Leemos la ayuda de un fichero (jsigpac.txt):
   try {
     BufferedReader in = new BufferedReader(
			    new FileReader("dat" + File.separator + "ayudas" + File.separator + "jsigpac.txt"));
     String linea = new String();
     while ((linea = in.readLine()) != null)
       System.out.println(" " + linea); 
     in.close();
   } catch (FileNotFoundException fnfe) {
      aux = " No encontrado el fichero de ayuda";	
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      System.err.println("*" + Mapa.pad(" ", lon_max-2) + "*");
      aux = " Uso: jSigpac [-d | -e | -c | -f] <resto_de_opciones>";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = "      -d: Para descargar los mapas";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = "      -e: Para ensamblar los mapas";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = "      -c: Para generar unicamente los ficheros de calibracion";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = "      -f: Para descargar los mapas segun la informacion contenida en el fichero \".jsi\"";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      System.err.println("*" + Mapa.pad(" ", lon_max-2) + "*");
      aux = " El <resto_de_opciones> depende de si se descarga o se ensambla:";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      System.err.println("*" + Mapa.pad(" ", lon_max-2) + "*");
      aux = " Para DESCARGAR:";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = " ==============";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = " jSigpac -d [-Sservidor] [-Ddatum] <-mORTO|-mTOPO> <-hHUSO> [-q] <-xX> <-yY>";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = "         <-AAnchura_en_metros> <-HAltura_en_metros> <-rRESOLUCION>";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = "         [-c] [-s] [-sol] [-fnom_fich]";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      System.err.println("*" + Mapa.pad(" ", lon_max-2) + "*");
      aux = " Para ENSAMBLAR:";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = " ==============";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      aux = " jSigpac -e <raiz_nom_fich> <filas> <columnas> [compresion]";
      System.err.println("*" + Mapa.pad(aux, lon_max-2) + "*");
      System.err.println("*" + Mapa.pad(" ", lon_max-2) + "*");     
   
   } catch (IOException e) {
       System.err.println("Error al intentar leer la ayuda");
   }

   aux = "************************************************************************";
   System.err.println(aux);
   System.err.println("");
   JSigpac.Salir();
 }

 public static void Salir()
 {	
   Salir(2000);
 }
 
 public static void Salir(int miliseg)
 { 
   //System.gc();
   if (JSigpac.entornoGrafico == true)
      return;

   //b = new byte[1];
   System.out.println("\nPulse la tecla [Enter] para salir...");
   try {     
      System.in.read(); //b, 0, 1);
      //System.out.println("  aux="+aux);     
   } catch(IOException ioe) {}
   
   //System.out.println("----------- Salir en " + miliseg + " milisegundos  ----------------------");
   //Thread mith = Thread.currentThread();
   try {
      Thread.sleep(miliseg);
   } catch(InterruptedException ie) {
      System.err.println("Capturada InterruptedException: "+ie);
   }
   try {
      if (canal != null)
         canal.close();
   } catch (IOException ioe) {}

   System.exit(1);
 }
 

 // main de JSigpac:
 public static void main (String[] args) 
 {
   FileInputStream fis=null;
   String[] rest;
   JSigpac miApli=null;      
   
   JSigpac.ejecutableWindows = true;
   JSigpac.entornoGrafico = false;   
   
   //System.getProperties().setProperty("user.dir", "C:\\Download\\SIGPAC\\Fuentes v5.1.9");
   //System.getProperties().list(System.out);
   //System.out.println("Variable de entorno JSIGPAC_MEM=" + System.getenv("JSIGPAC_MEM"));  
   
   // Configuracion del proxy: Intento leer el fichero "jsigpac.proxy":
   File dat = new File("dat" + File.separator + "jsigpac.proxy");
   if (dat.exists())
   {
      try {
        fis = new FileInputStream(dat);
	Properties misProperties = new Properties();
        misProperties.load(fis);
        //misProperties.list(System.out);
	String proxy = misProperties.getProperty("proxy");
	if (proxy == null)
	{
	   System.err.println("No ha especificado el proxy en el fichero: " +
			      dat.getPath());
           JSigpac.Salir(3000);
	   return;
        }
        //System.out.println("_xx_ proxy="+proxy);
        //System.out.println("_elias_ proxy="+proxy);
        
        System.getProperties().setProperty("http.proxyHost", proxy);
	String puerto = misProperties.getProperty("puerto");
	//System.out.println("_elias_ puerto="+puerto);
	if (puerto == null)
	{
	   System.err.println("No ha especificado el puerto en el fichero: " +
			      dat.getPath());
	   JSigpac.Salir(3000);
	   return;
        }
        //System.out.println("_xx_ puerto="+puerto);

        System.getProperties().setProperty("proxySet", "true");
        //System.getProperties().setProperty("http.proxyPort", "true");
        System.getProperties().setProperty("http.proxyPort", puerto);
        String username = misProperties.getProperty("usuario");
        if (username != null)
        {
           //System.getProperties().setProperty("http.proxyUser",username);
           //System.out.println("_xx_ username="+username);
        } else
        {
           //System.out.println("_xx_ No se ha leido el usuario");
        } 
        //System.out.println("_elias_ username="+username); 
        String password = misProperties.getProperty("clave");   
        if (password != null)
        {
           //System.getProperties().setProperty("http.proxyPassword",password);
	   //System.out.println("_xx_ password="+password);
        } else
        {
           //System.out.println("_xx_ No se ha leido el password");
        }  
        //System.out.println("_elias_ password="+password); 
        
        if (username != null && password != null)
        {
           //System.out.println("_elias_  Preparo la autentificacion");
           Authenticator.setDefault(new SimpleAuthenticator(username, password));
        }
        
      } catch (FileNotFoundException fnfe) {
	System.err.println("JSigpac::main No se ha encontrado el fichero: " + dat.getPath());
      } catch (SecurityException se) {
	System.err.println("Acceso de lectura denegado al fichero: " + dat.getPath());
      } catch (IllegalArgumentException se) {
	System.err.println("Formato incorrecto del fichero: " + dat.getPath());
      } catch (IOException se) {
	System.err.println("Error al intentar leer el fichero: " + dat.getPath());
      }	finally {
      	 try {
           if (fis != null)
           {
      	      fis.close();      
      	      fis = null;
      	   }
         } catch (IOException ioe) {}      	
      }
   }

   JSigpac.AccionesPrevias();

   //System.out.println("argumentos:"+args.length);
   if (args.length == 0)
   {
      System.out.println("Cargando...");
      JSigpac.entornoGrafico = true;
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension d = tk.getScreenSize();
      int resAlto = d.height;
      int resAncho = d.width;
      miApli = new JSigpac();
      JSigpac.IniciarTrazas();
      miApli.setTitle("jSIGPAC: Descarga y ensamblado de mapas");
      miApli.setSize(650,480);
      miApli.setLocation(resAncho/2 - 175, resAlto/2 - 200);
      miApli.setVisible(true);
      System.out.println("Aplicacion cargada");
      System.out.println(JSigpac.version + JSigpac.actualizado);
      miApli.ComprobarVersion();
   } else if (args[0].equals("-c"))
   {
      rest = new String[args.length-1];
      for (int i=1; i<args.length; i++)
          rest[i-1] = args[i];
      miMapa = new Mapa(rest, "CALIBRAR");
   } else if (args[0].equals("-f"))
   {
      rest = new String[args.length-1];
      for (int i=1; i<args.length; i++)
          rest[i-1] = args[i];
      System.out.println(JSigpac.version + JSigpac.actualizado);
      miMapa = new Mapa(rest, "LEER_JSI");      
      JSigpac.Salir();
   } else if (args[0].equals("-d"))
   {  
      rest = new String[args.length-1];
      for (int i=1; i<args.length; i++)
          rest[i-1] = args[i];
      miMapa = new Mapa(rest);
   } else if (args[0].equals("-e"))
   {
      rest = new String[args.length-1];
      for (int i=1; i<args.length; i++)
          rest[i-1] = args[i];
      ens = new Ensamblador(rest, null);
   } else if (args[0].equals("-x"))
   {
      rest = new String[args.length-1];
      for (int i=1; i<args.length; i++)
          rest[i-1] = args[i];
      miMapa = new Mapa(rest, true);
   } else if (args[0].equals("-ibp") || args[0].equals("-IBP"))
   {
      JSigpac.ObtenerIBP(args);
   } else {
       // Primero compruebo si el argumento pasado es un fichero de track, ruta o waypoints:
       //String[] extensiones = {"trk", "TRK", "plt", "PLT", "rte", "RTE", "wpt", "WPT", "gpx", "GPX"};	
       boolean mePasanFichero = false;
       // Comprobamos si la extension del fichero coincide con alguna de estas:       
       for (int h=0; h<Fichero.misExtensiones.length; h++)       
       {
           if (args[0].endsWith(Fichero.misExtensiones[h]))
           {
              mePasanFichero = true;
              break;
           }
        }
        
        if (!mePasanFichero)
           SacarAyuda();    
        else
        {
           System.out.println(JSigpac.version + JSigpac.actualizado);
           miMapa = new Mapa(args, "CARGAR_FICHERO");  
           JSigpac.Salir();            	
        }        
   }   
   //System.out.println("-------------------------- Fin del main de JSigpac -----------------------");
   return;
 }
 
 
}  /* Fin de la clase JSigpac */


/*************************************************************************
 * 
 * Clase:   Mapa
 * Autor:   Jose Ramon Arias Frances
 * Fecha:   15 de Octubre 2005
 * 
 * Funcion: Descarga los mapas que rodean un punto dado por sus coordenadas
 *	    UTM (X,Y) y los guarda en los respectivos ficheros.
 *
 * Uso:     java Mapa <-mORTO|-mTOPO> <-hhuso> [-q] <-xX> <-yY> <-aancho_en_metros>
 *                         <-filFILAS> <-colCOLUMNAS> [-s] [-sol] [-c] [-fnom_fich]
 *
 *          - Todas los argumentos son obligatorios salvo los que aparecen
 *            entre corchetes.
 *          - El primero indica si queremos un mapa topografico o una ortofoto.
 *          - x,y: coordenadas UTM del punto central del plano resultante o las
 *            coordenadas de la esquina sup. izda si ponemos la opcion "-q".
 *          - ancho_en_metros: Si ponemos "-a" estaremos indicando la anchura en
 *            metros de un cuadrante. Si la opcion es "-A", el ancho en metros
 *            se referira al ancho total del mapa final.
 *          - fil: numero de filas del mapa final.
 *          - col: numero de columnas del mapa final.
 *
 * Ejemplo: Supongamos que queremos un mapa topografico con centro en la
 *          ciudad de Valladolid que incluya los pueblos de Simancas, Viana de
 *          Cega, Boecillo, Laguna de Duero, Tudela de Duero, Villabáñez,
 *          Renedo, Mucientes, aeropuerto de Villanubla.
 *          - Consideramos las coordenadas del centro como las coordenadas UTM
 *            del Ayuntamiento situado  en la Plaza Mayor: 356170, 4612830)
 *          - El plano final deberá recoger una extensión de un radio de 36 km.
 *          - Para que el ejemplo no sea muy complicado, supongamos una matriz
 *            de 4*4. Con lo cual, cada una de las imágenes del mosaico tendrán
 *            un ancho de 44 km / 4 = 11000 m.
 *          - Visto lo cual, el comando a ejecutar será:
 *              java Mapa -mTOPO -h30 -x356170 -y4612830 -a11000 -fil4 -col4 -fpucela_
 *          - La salida del programa será:
 *               Numero de cuadrantes totales: 16
 *               (0,0) pucela_0_0.jpg
 *               (0,1) pucela_0_1.jpg
 *               (0,2) pucela_0_2.jpg
 *               (0,3) pucela_0_3.jpg
 *               (1,0) pucela_1_0.jpg
 *               ...
 *               (3,1) pucela_3_1.jpg
 *               (3,2) pucela_3_2.jpg
 *               (3,3) pucela_3_3.jpg
 *
 *             En total, tenemos 16 imágenes en formato JPG que habrá que 
 *             ensamblar con el "Ensamblador". El ensamblador que los ensamble,
 *             buen ensamblador será.
 *
 *
 * Más:     Tener en cuenta que cuantos más cuadrantes especifiquemos, más área
 *          abarcaremos pero también más grande será el fichero final y más
 *          costoso sera el proceso de ensamblaje. 
 *          El ensamblaje de las imágenes se hace con la otra aplicación
 *          llamada "Ensamblador". En mis pruebas he llegado a ensamblar una
 *          imagen formada por una matriz de 11*11. Por ejemplo, una matriz
 *          de 8*8 de un plano topografico da como resultado un fichero JPG
 *          de unos 5 Megas.
 *
 **************************************************************************/

class Mapa {
 OpcionesMapa opciones;
 static DecimalFormat df;
 double XesqSupIzqda=0, YesqSupIzqda=0,
        XesqSupDer=0,   YesqSupDer=0,
        XesqInfIzqda=0, YesqInfIzqda=0,
        XesqInfDer=0,   YesqInfDer=0;
 
 boolean primeraVez_E_W = true; // Relacionado con la funcion E_W
 // Las siguientes variables se utilizan cuando se descargan ficheros desde la consola
 // y ya existen ficheros previamente descargados. Se preguntara si se quiere volver a
 // descargarlos y si se aplica esa respuesta al resto de ficheros:
 //boolean noPreguntarMas = false;
 // boolean sobreescribir;
 //boolean noDescargar = false;
 boolean ensamblar_al_acabar; // Si esta a "true" indica que cuando se acabe de descargar los cuadrantes, se ensamblaran.
                                 
 JSigpac misigpac = null; // Para sacar las ventanitas con mensajes.
 String e_w = "E";
 // Los valores de FACTOR, height y width pueden variar si estamos utilizando el 
 // nuevo servidor nacional (versión 4) ---> Rutina Dimensionar()
 // La relación entre el ancho y el alto de un mapa del SIGPAC tradicional: 618/421
 public static double FACTOR=1.4679334916864608076009501187648;
 public static double height=421D; // Altura de un mapa del SIGPAC
 public static double width=618D;  // Ancho de un mapa del SIGPAC
 
 public static int anchoVistaPajaro=618;  // ancho en pixels del mapa obtenido con VistaPajaro
 public static int altoVistaPajaro=418;   // alto en pixels del mapa obtenido con VistaPajaro
  

 public Mapa()
 {
   ensamblar_al_acabar = false; // Siempre se inicializa a false
   opciones = new OpcionesMapa();      
   //ObtenerIPServidor();   
   // Establecemos el formato de las coordenadas que se mandan al SIGPAC:
   df = new DecimalFormat("########.#######");
   DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
   dfs.setDecimalSeparator('.');
   df.setDecimalFormatSymbols(dfs);
 }

 public Mapa(JSigpac js)
 {
   this();
   misigpac = js;
   misigpac.ObtenerIPServidor();     
 }

 public Mapa(String[] args, boolean ensamblamos_al_final)
 {
   this();
   @SuppressWarnings("unused")
   boolean res;
   ensamblar_al_acabar = ensamblamos_al_final;
   res=IniciarProceso(args);
   //System.out.println("IniciarProceso devuelve: " + res);
 }

 public Mapa(String[] args)
 {
   this(args, false); 	
 }
 
 public Mapa(String[] args, String operacion, JSigpac js)
 {
   this();      
   misigpac = js;
   
   if (operacion.equals("CALIBRAR"))
   {
      if (AjustesIniciales(args) == false)
         return;
      File aux = new File(opciones.raiz);
      JSigpac.ImprimirLinea("\tUTM centro   X:"+opciones.x+"    Y:"+opciones.y);
      JSigpac.ImprimirLinea("\tDirectorio destino: " +
	     (new File(aux.getAbsolutePath())).getParent());
      CalibrarMapa();
      JSigpac.ImprimirLinea("");
      return;
   } else if (operacion.equals("VISTAPAJARO"))
   {
     if (AjustesIniciales(args, true) == false)
        return;
     //System.out.println("_ll_ En VISTAPAJARO opciones.vistaPajaro = " + opciones.vistaPajaro);
     VistaPajaro();	   	
   } else if (operacion.equals("LEER_JSI"))
   {         
     // Debe venir el nombre del fichero (con o sin path):  
          
     try {   
     	 Descargar_Y_ensamblar_desde_FicheroJSI(args[0]);      	
     } catch (ArrayIndexOutOfBoundsException aiobe) {
     	 if (JSigpac.ejecutableWindows == true)         
            System.out.println("\nUso: jSigpac -f <fichero>\n");	    
         else
            System.out.println("\nUso: java JSigpac -f <fichero>\n");	            
     }
     
     //JSigpac.Salir();
   } else if (operacion.equals("CARGAR_FICHERO"))
   {
   	 // Si un fichero de track o de ruta o de waypoints ha sido arrastrado sobre
   	 // el ejecutable del jSIGPAC, en "args" no habra nada que nos interese salvo
   	 // el nombre del fichero de track o ruta o waypoints.
   	 // Pero puede haber sido utilizada desde línea de comando y en "args", a parte 
   	 // del nombre del fichero de track o de rutas podemos tener también otros argumentos
   	 // como la resolucion o el tipo de mapa o si se generan los ficheros de calibracion....
   	 // De todas formas, la resolución y el tipo de mapa (orto o topo y escala) se leeran
   	 // de un fichero donde se encontrarán los valores por defecto a utilizar.
   	 // Ese fichero con los valores por defecto podríamos llamarlo "jsigpac.defecto" y su
   	 // formato seria similar al de ".jsi".
     //System.out.println("getClass.getResource="+getClass().getResource("JSigpac.class"));
     //JSigpac.ImprimirLinea("getClass.getResource="+getClass().getResource("JSigpac.class"));
     Descargar_Y_ensamblar_de_FicheroTrkRteWpt(args);   	   	
   } else if (operacion.equals("CARGAROPCIONES"))
   {
	   AjustesIniciales(args); 
   }
 }
 
 public Mapa(String[] args, String operacion)
 {
   this(args, operacion, null);
 }
 
 void ObtenerIPServidor()
 {
    if (JSigpac.entornoGrafico == true)  //misigpac != null)
       misigpac.ObtenerIPServidor();
    else
    {
       JSigpac.EstablecerServidor();
       //JSigpac.ImprimirLinea("ObtenerIPServidor  servidor = " + JSigpac.servidor.Url());
       Mapa.Dimensionar();
    }
 }
    
 public Mapa(OpcionesMapa opsMapa)
 {   
    JSigpac.hayThreads = false;
    opciones = opsMapa;       
 }
 
 public boolean ReintentarDescargaCuadrante(int i, int j)
 {
    boolean valorAnterior;
    boolean retorno;
    
    valorAnterior = JSigpac.hayThreads;
    JSigpac.hayThreads = false;
    retorno = ObtenerCuadrante(i, j); 
    JSigpac.hayThreads = valorAnterior;
    return retorno;    
 }
  
 void Descargar_Y_ensamblar_de_FicheroTrkRteWpt(String[] args)
 {
    double[] respCoor = null;
    JSigpac.Traza("Descargar_Y_ensamblar_de_FicheroTrkRteWpt: "+args[0]);    
    File dirwork;
    dirwork = new File(".");
    JSigpac.ImprimirLinea("Directorio de trabajo: " + dirwork.getAbsolutePath());
     	 
    //ObtenerIPServidor();   
    //JSigpac.ImprimirLinea("\tServidor elegido: " + servidor); 
    //Dimensionar();
    String error=null;     
    String[] args_defecto;
    String miServidor=null; //(String)JSigpac.opcionesComboServidor[0]
    String miEscala="25";
    String miFichero = null; // Nombre del fichero con el mapa
    boolean miTipoMapaOrto=true, miQuitamanchas=false, miCalibrar=false, miWGS84=false, miSolapamiento=false;
    boolean miSoloTrack=false;
    Datum midatumJSigpac = Datum.datumED50;
    double miRes=1.0D;
    int miMargen = 200; // Margen en metros que queremos añadir al mapa final en cada una de las esquinas
    boolean borrarIntermedios = false;
    int miCompresion = 70; // Factor de compresion JPEG
    int mapasHor=1, mapasVer=1;
    int i;
    //JSigpac.ImprimirLinea("_xx_1 miWGS84="+miWGS84);
    
    String ficheroPorDefecto = "dat" + File.separator + "jsigpac.defecto";
    try 
    {
       // 1º- Llamar a una función que lea el fichero "jsigpac.defecto" y devuelva un array con los valores
       // de la resolucion y el tipo de mapa.
       /*
          Los valores iniciales del fichero "jsigpac.defecto" son
            servidor: Nacional - España
            ORTO: true                                  
            resolucion: 1
            escala: 25
            margen: 200
            quitamanchas: false
            calibrar: true
            datum_calibrar: WGS84
            solapamiento: false
            compresion: 70                 
       */
       args_defecto = LeerFicheroPorDefecto(ficheroPorDefecto);
       if (args_defecto != null)
       {         
          JSigpac.Traza("Asigno valores por defecto");  
          // Ahora asigno esos valores leidos por defecto a las variables correspondientes:
          for (i=0; i<args_defecto.length; i++)
          {
       	      JSigpac.Traza("args_defecto["+i+"]="+args_defecto[i]);
       	      
       	      
       	      if (args_defecto[i].equals("-mORTO"))
       	      {       	      
       	      	 miTipoMapaOrto = true;
       	      	 continue;
       	      } else if (args_defecto[i].startsWith("-mTOPO"))
       	      {
       	      	 error = "Error al tratar el argumento \"TOPO\" por defecto del fichero " + ficheroPorDefecto; 
       	      	 int aux = args_defecto[i].length() - 6; // Le resto la longitud de "-mTOPO".
	         if (aux < 2)
	            miEscala = "25";
                 else 
	            miEscala = args_defecto[i].substring(6);                  
	         miTipoMapaOrto = false;
	         continue;
	      } else if (args_defecto[i].startsWith("-D")) {	          
	         if (args_defecto[i].substring(2,3).equals("W"))
	            midatumJSigpac = Datum.datumWGS84; 	 
	         else
	            midatumJSigpac = Datum.datumED50;	          
	         continue;
	      } else if (args_defecto[i].startsWith("-r")) {
	         error = "Error al tratar el argumento \"resolucion\" por defecto del fichero " + ficheroPorDefecto;
	         miRes = (Double.valueOf( args_defecto[i].substring(2) )).doubleValue();	           
	         JSigpac.Traza("miRes="+miRes);
	         continue;
	      } else if (args_defecto[i].startsWith("-sol")) {
	         miSolapamiento = true;	           
	         continue;
	      } else if (args_defecto[i].startsWith("-S")) {
	         miServidor = args_defecto[i].substring(2);;	           
	         continue;
              } else if (args_defecto[i].startsWith("-s")) {
	         miQuitamanchas = true;	           
	         continue;
              } else if (args_defecto[i].startsWith("-c")) {
	         miCalibrar = true;	           
	         continue;
              } else if (args_defecto[i].startsWith("-w")) {
	         miWGS84 = true;	           
	         //JSigpac.ImprimirLinea("_xx_2 miWGS84="+miWGS84);
	         continue;
              } else if (args_defecto[i].startsWith("-M")) {
                 error = "Error al tratar el argumento \"margen\" por defecto del fichero " + ficheroPorDefecto;
	         miMargen = (Integer.valueOf( args_defecto[i].substring(2) )).intValue();	           
	         continue;
	      } else if (args_defecto[i].startsWith("-J")) {  // Compresion JPEG  
	         error = "Error al tratar el argumento \"compresion\" por defecto del fichero " + ficheroPorDefecto;                
	         miCompresion = (Integer.valueOf( args_defecto[i].substring(2) )).intValue();	           
	         continue;
              } else if (args_defecto[i].startsWith("-mv")) {  // Mapas en vertical  
	         error = "Error al tratar el argumento \"mapasVertical\" por defecto del fichero " + ficheroPorDefecto;                
	         mapasVer = (Integer.valueOf( args_defecto[i].substring(3) )).intValue();	           
	         continue;
              } else if (args_defecto[i].startsWith("-mh")) {  // Mapas en horizontal  
	         error = "Error al tratar el argumento \"mapasHorizontal\" por defecto del fichero " + ficheroPorDefecto;                
	         mapasHor = (Integer.valueOf( args_defecto[i].substring(3) )).intValue();	           
	         continue;
              } else if (args_defecto[i].startsWith("-t")) {  // Para descargar solo los cuadrantes por los que va el track o la ruta  
	         error = "Error al tratar el argumento \"-t\" (soloTrack) por defecto del fichero " + ficheroPorDefecto;                
	         miSoloTrack = true;	           
	         continue;
	      } else if (args_defecto[i].startsWith("-B")) {  // Para que se borren los ficheros intermedios  
	         error = "Error al tratar el argumento \"-B\" pasado como argumento";                
	         borrarIntermedios = true;		         
	         continue;
              }      
          }  // Fin del for de "args_defecto"
       } // Fin if args_defecto != null
       JSigpac.Traza("Por defecto: borrarIntermedios = " + borrarIntermedios);
                                           
       // 2º- Ahora se mira qué viene en args[1], args[2], etc.... y se sobreescribirán los valores por defecto
       // con estos nuevos valores:
                                   
       for (i=1; i<args.length; i++)
       {
           if (i == 1)
              JSigpac.Traza("Asigno los argumentos sobreescribiendo los valores por defecto");
           if (args[i].equals("-mORTO"))
       	   {       	      
       	      miTipoMapaOrto = true;
       	      continue;
       	   } else if (args[i].startsWith("-mTOPO"))
       	   {
       	      error = "Error al tratar el argumento \"TOPO\" por defecto del fichero " + ficheroPorDefecto; 
       	      int aux = args[i].length() - 6; // Le resto la longitud de "-mTOPO".
	      if (aux < 2)
	         miEscala = "25";
              else 
	         miEscala = args[i].substring(6);                  
	      miTipoMapaOrto = false;
	      continue;           
	   } else if (args[i].startsWith("-D")) {	          
	      if (args[i].substring(2,3).equals("W"))
	         midatumJSigpac = Datum.datumWGS84; 	 
	      else
	         midatumJSigpac = Datum.datumED50;	          
	      continue;
	   } else if (args[i].startsWith("-r")) {
              error = "Error al tratar el argumento \"-r\" (resolucion)";
	      miRes = (Double.valueOf( args[i].substring(2) )).doubleValue();
	      JSigpac.Traza("miRes="+miRes);	           
	      continue;
	   } else if (args[i].startsWith("-sol")) {
	      miSolapamiento = true;	           
	      continue;
           } else if (args[i].startsWith("-s")) {
	      miQuitamanchas = true;	           
	      continue;
           } else if (args[i].startsWith("-c")) {
	      miCalibrar = true;	           
	      continue;
           } else if (args[i].startsWith("-w")) {
	      miWGS84 = true;
	      //JSigpac.ImprimirLinea("_xx_3 miWGS84="+miWGS84);	           
	      continue;
           } else if (args[i].startsWith("-M")) {   
              error = "Error al tratar el argumento \"-M\" (margen)";                
	      miMargen = (Integer.valueOf( args[i].substring(2) )).intValue();	           
	      continue;
	   } else if (args[i].startsWith("-f")) {  // Nombre del fichero   
	      error = "Error al tratar el argumento \"-f\" (nombre del mapa)";              
	      miFichero = args[i].substring(2);	           
	      continue;
	   } else if (args[i].startsWith("-J")) {  // Compresion JPEG    
              error = "Error al tratar el argumento \"-J\" (compresion JPEG)";              
	      miCompresion = (Integer.valueOf( args[i].substring(2) )).intValue();	           
	      continue;
           } else if (args[i].startsWith("-mv")) {  // Mapas en vertical  
	      error = "Error al tratar el argumento \"-mv\" pasado como argumento";                
	      mapasVer = (Integer.valueOf( args[i].substring(3) )).intValue();	           
	      continue;
           } else if (args[i].startsWith("-mh")) {  // Mapas en horizontal  
	      error = "Error al tratar el argumento \"-mh\" pasado como argumento";                
	      mapasHor = (Integer.valueOf( args[i].substring(3) )).intValue();	           
	      continue;
           } else if (args[i].startsWith("-t")) {  // Para descargar solo los cuadrantes por los que va el track o la ruta  
	      error = "Error al tratar el argumento \"-t\" (soloTrack) pasado como argumento";                
	      miSoloTrack = true;	           
	      continue;
	   } else if (args[i].startsWith("-B")) {  // Para que se borren los ficheros intermedios  
	      error = "Error al tratar el argumento \"-B\" pasado como argumento";                
	      borrarIntermedios = true;	           
	      continue;
           }                                         
       }   // Fin for de "args"
                	            	                	   
    } catch (ArrayIndexOutOfBoundsException aiobe) {
       String sintaxis = " -f <fichero> [-mORTO|-mTOPOescala] [-rRES] [-s] [-sol] [-c] [-w] [-Mmargen] [-fnomfich] [-JcompresionJPEG]";
       if (JSigpac.ejecutableWindows == true)         
          System.out.println("\nUso: jSigpac" + sintaxis + "\n");	    
       else
          System.out.println("\nUso: java JSigpac" + sintaxis + "\n");
       return;
    } catch (NumberFormatException nfe) {
       String msj = "Valor numerico incorrecto";
       System.out.println("\n" + msj + "\n" + error + "\n");
       if (JSigpac.entornoGrafico)
	  JOptionPane.showMessageDialog(misigpac, error,
                  msj, JOptionPane.ERROR_MESSAGE);
       else
          JSigpac.ImprimirLinea(msj + ": " + error);
       return;
    }	            
     
    JSigpac.Traza("Finalmente: borrarIntermedios = " + borrarIntermedios);   
            
    // Hay que calcular el numero de filas y de columnas a partir de la resolucion y del ancho y alto del mapa.
    // Primero obtenemos las coordenadas que nos permitiran obtener el mapa:
    JSigpac.ficheroTrkRte = new Fichero(args[0]);
    respCoor = JSigpac.ficheroTrkRte.CalcularCoordenadas(midatumJSigpac);
    if (respCoor == null)
       System.out.println("Error al intentar cargar el fichero: " + Fichero.mensaje_error);	             
    else
    {
       // Hay que pasar el nombre del fichero y el directorio:        
       File fi = new File(args[0]);
       String path_sin_extension, directorio;
       JSigpac.Traza("args[0]=" + args[0]);
       path_sin_extension = args[0].substring(0, args[0].lastIndexOf('.'));            
       JSigpac.Traza("path_sin_extension="+path_sin_extension);
       if (miFichero == null)
          miFichero = (new File(path_sin_extension)).getName();
       JSigpac.Traza("miFichero="+miFichero);
       directorio = (new File(fi.getAbsolutePath())).getParent();
       //JSigpac.ImprimirLinea("directorio="+directorio);
                        
        double ancho, alto;
        double miXsi, miYsi, miXid, miYid;
        miXsi = respCoor[0] - miMargen;
        miXid = respCoor[3] + miMargen;
        //ancho = miXid - miXsi;  //respCoor[3]-respCoor[0] + (miMargen * 2);
        miYsi = respCoor[1] + miMargen;
        miYid = respCoor[4] - miMargen;
        JSigpac.Traza("_dd_ miXsi="+miXsi+"  miYsi="+miYsi+"    miXid="+miXid+"  miYid="+miYid); 
        //alto = miYsi - miYid; //respCoor[1]-respCoor[4] + (miMargen * 2);
        //JSigpac.ImprimirLinea("ancho="+ancho+"   alto="+alto);    
        //Distancia distancia = new Distancia(xsi, ysi, huso_si, xid, yid, huso_id);             
        Distancia distancia = new Distancia(miXsi, miYsi, (byte)respCoor[2],
                                            miXid, miYid, (byte)respCoor[5], midatumJSigpac);
        ancho = distancia.CalcularAncho();
        alto =  distancia.CalcularAlto();                                           
        JSigpac.Traza("_dd_ CalcularAncho="+distancia.CalcularAncho()+"  CalcularAlto="+distancia.CalcularAlto());
        //ancho = distancia.CalcularAncho();
        //alto = distancia.CalcularAlto();    
            
        //respuesta = AplicarResolucion(ancho, alto, miRes);
        //JSigpac.ImprimirLinea("Despues de ajustar   ancho="+respuesta[0]+"   alto="+respuesta[1]);                     
        
        GuardarEnFichero(path_sin_extension, /*servidor null*/ miServidor, 
        		 /*tipoMapaOrto true*/ miTipoMapaOrto, 
                         /* Datum de entrada de datos */ midatumJSigpac,
                         /*huso*/ (byte)respCoor[2], /*X*/ miXsi, /*Y*/ miYsi,
                         /*ancho respuesta[0] */ ancho , /*alto respuesta[1] */ alto, 
                         /*resolucion*/ miRes, /*columnas respuesta[2] */ -1, /*filas respuesta[3] */ -1, 
                         /*escala null*/ miEscala, directorio, miFichero,
                         /*quitamanchas false*/ miQuitamanchas, 
                         /*calibrar true*/ miCalibrar, /*WGS84 true*/ miWGS84, miSoloTrack,
                         /*solapamiento false*/ miSolapamiento, borrarIntermedios,
                         miCompresion, mapasVer, mapasHor);
        // Una vez guardado en fichero, se lee de nuevo para poder descargarlo y ensamblarlo:                                      
        Descargar_Y_ensamblar_desde_FicheroJSI(path_sin_extension+".jsi");                                                                               	         	
    } // else respCoor == null
    //JSigpac.Salir(); 
 }
  
 
 void Descargar_Y_ensamblar_desde_FicheroJSI(String nomFichero)
 {
    String[] leido;
    
    JSigpac.Traza("Descargar_Y_ensamblar_desde_FicheroJSI()");
    if ((leido = LeerDeFichero(nomFichero)) != null)
    {
       JSigpac.Traza("Fichero leido: " + nomFichero);                                 
       ensamblar_al_acabar = true;    
       JSigpac.procesoFinalizado = false;
       if (IniciarProceso(leido) == true)
       {
          //JSigpac.ImprimirLinea("Hemos vuelto de IniciarProceso");
          // Ahora habria que ensamblar pero eso se hace cuando el ThreadEsperaDescarga llama a Mapa.DespuesDeDescargar(...)
          // Ahora tenemos que esperar a que el proceso finalice y para ello usamos la variable "JSigpac.procesoFinalizado":
          //Thread mith = Thread.currentThread();
          while (JSigpac.procesoFinalizado == false)
          {
             //JSigpac.ImprimirLinea("**** El proceso no ha finalizado todavia ****");	   
             try {
                Thread.sleep(1500);
             } catch(InterruptedException ie) {
                System.out.println("Dentro del bucle JSigpac.procesoFinalizado de Descargar_Y_ensamblar_desde_FicheroJSI()");
             }
          }
        }           
    } else
       System.err.println("Error al leer los datos del fichero: " + nomFichero); 
 }
 
 
 
 static void Dimensionar()
 {
   /*
    if (EsVersion4())
        Dimensionar(1.0D, 256D, 256D);
    else
        Dimensionar(1.4679334916864608076009501187648, 421D, 618D);
   */ 
    int serv = JSigpac.servidor.Id();
    //JSigpac.ImprimirLinea("IdentificadorServidor devuelve " + serv);
    
    switch (serv)
    {
    	case Servidor.NACIONAL:
    	case Servidor.PNOA:
    	case Servidor.CyL:
    	case Servidor.GALICIA:
    	case Servidor.ASTURIAS:
    	case Servidor.PAISVASCO:
    	case Servidor.ARAGON:
    	case Servidor.MANCHA:
    	case Servidor.MURCIA:
    	case Servidor.CANARIAS_SIGPAC:
    	case Servidor.CANARIAS_SITCAN:  
    	case Servidor.CANARIAS_IDECAN:
    	case Servidor.MADRID:
    	case Servidor.RIOJA: 
    	case Servidor.EXTREMADURA: 
    	case Servidor.CVALENCIANA:
    	case Servidor.ARAGON_SITAR: 
    	case Servidor.RECINTOS:
    	case Servidor.PARCELAS:
    	case Servidor.IBERPIX:   Dimensionar(1.0D, 256D, 256D); break;    
    	case Servidor.CyL_v3: // Antiguo visor de Castilla y León	    	    	
    	case Servidor.EUS_v3: Dimensionar(1.4679334916864608076009501187648D,  421D, 618D); break;     	    	
    	//case 10: Dimensionar(1.3846153846153846153846153846154D,  442D, 612D); break;   
    	case Servidor.IGN: Dimensionar(1.0D, 128D, 128D); break;
    	case Servidor.IDEANDALORTO: 	    	
    	case Servidor.ANDALUCIA: Dimensionar(1.0D, 512D, 512D); break;   
    	case Servidor.NAVARRA: 	Dimensionar(1.629695885509838998211091234347D,  559D, 911D); break;
    	case Servidor.NAVARRA1000:  Dimensionar(1.3787128712871287128712871287129D,  404D, 557D); break;    	
    	case Servidor.NAVARRA1950:  Dimensionar(1.7857142857142857142857142857143D,  280D, 500D); break;
    	
    	default: JSigpac.ImprimirLinea(" ---  Servidor no catalogado --- ");
    }	
 }
 
 static void Dimensionar(double factor, double alto, double ancho)
 {
    // El "factor" es la relacion entre el ancho y el alto:   actor = ancho/alto
    Mapa.FACTOR = factor;
    Mapa.height = alto;
    Mapa.width = ancho;	
 }
     
     
 /* 
     Mapa.AplicarResolucion() recibe como entrada el ancho, el alto y la resolucion del mapa
     y devuelve en un array los nuevos valores correspondientes al ancho, alto, numero de columnas
     y numero de filas. Estos nuevos valores se calculan de acuerdo a los valores de la 
     resolucion y al ancho y alto de un cuadrante del SigPac.
     Si no se especifica el "alto" del mapa, se indicara con un valor -1 (con que sea menor que cero
     es suficiente).
  */
 int[] AplicarResolucion(double ancho, double alto, double res)
 {    	
	                  //    nuevo_ancho    nuevo_alto     nCol          nFil    	
    int[] respuesta=null; //    respuesta[0]   respuesta[1]   respuesta[2]  respuesta[3]
    int nFil, nCol;
       
    //System.out.println("_xx_ Estamos en AplicarResolucion");        
    
    respuesta = new int[4];
    nCol = (int)Math.floor((ancho / (res*Mapa.width)) + 0.999);
    if (nCol <= 0)
       nCol = 1;
    if (alto < 0D)
       nFil = nCol;
    else
    {       
       nFil = (int)Math.floor((alto / (res*Mapa.height)) + 0.999);
       if (nFil <= 0)
          nFil = 1;
    }   
       
    // Calculamos el nuevo ancho:
    respuesta[0] = (int)(nCol * res * Mapa.width);
    // Calculamos el nuevo alto:
    respuesta[1] = (int)(nFil * res * Mapa.height);    
    respuesta[2] = nCol;
    respuesta[3] = nFil;
    
    return respuesta;
 }    
 
 
 /*  
    La singularidad de las Canarias es la siguiente:
    El visor nacional trabaja actualmente (hoy es 4 de Diciembre de 2008) en ED50. 
    Sin embargo, cuando se visualiza la zona de las Canarias, el visor pasa a dar las coordenadas en WGS84.
    Tenemos que tener esto en cuenta para modificar internamente el "datumServidor" a WGS84.
    Además, el usuario deberá tener en cuenta si las coordenadas que teclea van en ED50 o en WGS84 y así
    modificar el desplegable convenientemente.
  */
 void TratarSingularidadCanaria()
 {
   if (opciones.huso < 29)
      if (JSigpac.servidor.Abreviatura().equals("ESP"))      
         opciones.datumServidor = Datum.datumWGS84;	
 }
     
 boolean AjustesIniciales(String[] args)
 {
   return AjustesIniciales(args, false, true);
 }
 
 boolean AjustesIniciales(String[] args, boolean vistaPajaro)
 {
   return AjustesIniciales(args, vistaPajaro, true);
 }
 
 /* A parte de recibir los argumentos, recibo otros dos parametros:
    - vistaPajaro: a true si queremos obtener la Vista Pajaro o Vista Mapa global.
    - comprobarVariosHusos: a true si queremos que al final de AjustesIniciales() se realice un chequeo 
      de si el mapa pertenece a varios husos. Esto lo he hecho así para que cuando se da al boton "ensamblar"
      e intentar cargar los valores del formulario, al llamar a AjustesIniciales() no se realice el chequeo de huso
      para que no aparezca dos veces el mensaje avisando de que el mapa pertenece a dos husos.
  */
 boolean AjustesIniciales(String[] args, boolean vistaPajaro, boolean comprobarVariosHusos)
 {
   String nomFich=null;
   File aux;
   
   JSigpac.Traza("En AjustesIniciales vistaPajaro = " + vistaPajaro);
   
   if (args == null)
      return true;
      
   primeraVez_E_W = true;
   //noPreguntarMas = false; //kkmail
   //noDescargar = false;
   //sobreescribir = true; //kkmail
   
   for (int y=0; y<args.length; y++)
       JSigpac.Traza("leido["+y+"] = " + args[y]);
   
   opciones.Inicializar();
   opciones.vistaPajaro = vistaPajaro; // De esta forma permitirnos sacar la Vista Global del IGN
   
   ObtenerIPServidor();     
   //Dimensionar();  --->>> Ya lo hacemos en ObtenerIPServidor()
   // Establecemos el datum segun el servidor (Para Canarias y Madrid sera WGS84):   
   //opciones.datumServidor = JSigpac.servidor.DameDatumDelServidor();  // Lo hago en TratarArgumentos
   
  
   if (TratarArgumentos(args) == false)
   {
      if (JSigpac.ejecutableWindows == true)
      {
         System.out.println("JSigpac <-d|-x> <-mORTO|-mTOPO> [-Sservidor] [-Ddatum] [-q] <-hhuso> <-xX> <-yY>");
         System.out.println("       <-aancho_cuadrante -filFILAS -colCOLUMNAS> | <-Aancho_mapa -rresolucion>");
	 System.out.println("       [-c] [-w] [-s] [-sol] [-B] [-fnom_fich]\n");
      } else {
         System.out.println("java JSigpac <-d|-x> <-mORTO|-mTOPO> [-Sservidor] [-Ddatum] [-q] <-hhuso> <-xX> <-yY>");
	 System.out.println("       <-aancho_cuadrante -filFILAS -colCOLUMNAS> | <-Aancho_mapa -rresolucion>");
	 System.out.println("       [-c] [-w] [-s] [-sol] [-B] [-fnom_fich]\n");
      }
      JSigpac.Salir();
      return false;
   }
   
   nomFich = opciones.raiz;
   aux = new File(nomFich);
   //System.out.println("FICHERO="+nomFich);
   //System.out.println("getParent="+aux.getParent());
   if (aux.getParent() != null)
      aux = new File(aux.getParent());
   else 
      aux = new File(".");
   
   
   if (!aux.exists())
   {
      JSigpac.ImprimirLinea("\tError: No existe el directorio destino: " + aux.getPath());
      if (JSigpac.entornoGrafico)
	 JOptionPane.showMessageDialog(misigpac,
	      "No existe el directorio destino: " + aux.getPath(),
	      "Directorio no existe", JOptionPane.ERROR_MESSAGE);
      return false;
   }   
         
   CalcularEsquinas();
   JSigpac.ImprimirLinea("");
   if (comprobarVariosHusos)
      return CheckVariosHusos();  
   else
      return true;
 }

 
 boolean IniciarProceso(String[] args)
 {
   boolean algunFallo = false;
   int contadorBuenos=0;
   
   JSigpac.ImprimirLinea("****************************************************************");
   JSigpac.IniciarTrazas();
   JSigpac.Traza("IniciarProceso entrar....");
   
   //for (int y=0; y<args.length; y++)
       //JSigpac.Traza("_xx_ leido["+y+"] = " + args[y]);
       //System.out.println("_xx_ leido["+y+"] = " + args[y]);
       
   if (AjustesIniciales(args) == false)
      return false;

   //JSigpac.ImprimirLinea("\tServidor elegido: " + JSigpac.servidor.Nombre());
  
   JSigpac.ActivarImpresionEnFichero(opciones.raiz);
    
   // Si hemos pulsado el boton de solo obtener los ficheros de calibracion,
   // entonces solo genero los ficheros de calibracion y me salgo:
   if (JSigpac.soloCalibrar == true)
   {
      File aux = new File(opciones.raiz);
      JSigpac.ImprimirLinea("\tUTM centro   X:"+opciones.x+"    Y:"+opciones.y);
      JSigpac.ImprimirLinea("\tDirectorio destino: " +
	     (new File(aux.getAbsolutePath())).getParent());
      CalibrarMapa();
      JSigpac.ImprimirLinea("");
      JSigpac.DesActivarImpresionEnFichero();
      return true;
   }
   
   ImprimirComando(); // De Mapa
   int i=0, j=0;   

   if (opciones._i && opciones._j)
   {
      JSigpac.hayThreads = false;
      if (ObtenerCuadrante(opciones.i, opciones.j) == false)
      {
         algunFallo = true;
         JSigpac.ImprimirLinea("\tError al obtener la imagen *1* ("+opciones.i+","+opciones.j+")");
	   //return;
      }
   } else
   {
   	
   //Coordenada nueva = new Coordenada(Datum.datumED50, -3.801873306, 43.4884075, 0);
   //JSigpac.ImprimirLinea("_cc_ ***EJEMPLO***NUEVA Xn="+nueva.getX()+"  Yn="+nueva.getY()+"  Zn="+nueva.getZ() + "  huso="+nueva.getHuso());
   //JSigpac.ImprimirLinea("_cc_ ***EJEMPLO***");
   //Coordenada nueva = new Coordenada(Datum.datumED50, 0.007996658, 41.25874121, 0);
   //JSigpac.ImprimirLinea("_cc_ Ejemplo NUEVA Xn="+nueva.getX()+"  Yn="+nueva.getY()+"  Zn="+nueva.getZ() + "  huso="+nueva.getHuso());   
   
      //JSigpac.Traza("Llamo PonerSTOP(true) desde IniciarProceso()");	
      PonerSTOP(true);           
      
      contadorBuenos = 0;
      //Monitor monitor = new Monitor(opciones.n_filas, opciones.n_col, 1, misigpac, opciones.compresion, opciones.mapasVertical, opciones.mapasHorizontal);
      Monitor monitor = new Monitor(opciones, misigpac);
      monitor.PonerEnsamblarAlAcabar(ensamblar_al_acabar);
      ensamblar_al_acabar = false;
      
      if (opciones.soloTrack)
         monitor.ReinicializarArrays();  //_hh_ 
      
      //JSigpac.ImprimirLinea("_cc_ EMPIEZA LA FIESTA");
      JSigpac.hayThreads = true;
      /*
      if (opciones==null)
         System.out.println("opciones es null");
      if (monitor==null)
         System.out.println("monitor es null");
      if (this.misigpac==null)
         System.out.println("this.misigpac es null");
      if (this.misigpac.grupeto==null)
      {
         System.out.println("this.misigpac.grupeto es null");
         this.misigpac.grupeto = new ThreadGroup("jSIGPAC");
      }
      */
         
      
      if (JSigpac.hayThreads)
         JSigpac.factoria = new Factoria(opciones, monitor, 10);
         //misigpac.factoria = new Factoria(opciones, monitor, this.misigpac.grupeto, 10);
      
      for (i=0; i<opciones.n_filas; i++)
        for (j=0; j<opciones.n_col; j++)
        {
           //JSigpac.Traza("Se llama a ObtenerCuadrante i="+i+"  j="+j);
           //JSigpac.ImprimirLinea("contadorBuenos="+contadorBuenos);
           if (monitor.HayQueDescargar(i,j))
             if (ObtenerCuadrante(i,j, monitor) == false)
             {
                algunFallo = true;
                JSigpac.ImprimirLinea("\tError al obtener la imagen *2* ("+i+","+j+")");
                monitor.CambiarValor(i, j, true);
                //return;
             } else
                contadorBuenos++;
        }
                                 
      JSigpac.Traza("contadorBuenos="+contadorBuenos + "   entornoGrafico=" + JSigpac.entornoGrafico);            
      
      if (contadorBuenos > 0)
      {                     
         if (JSigpac.hayThreads)
         {
            if (JSigpac.factoria.hayError == null)
            	JSigpac.factoria.start();
            else
            {
               misigpac.SacarVentanita("Mapas no descargados", "No se inicia el proceso de descarga por el siguiente error(1):\n" + JSigpac.factoria.hayError);            
               PonerSTOP(false, "Hay errores");
               return !algunFallo;
            }
         }
         
         ThreadEsperaDescarga th;
         if (JSigpac.entornoGrafico)
            th = new ThreadEsperaDescarga(this.misigpac.grupeto, this, monitor);
         else
            th = new ThreadEsperaDescarga(this, monitor);
         th.start();
      } else
         PonerSTOP(false, "Posibles errores");
   }
   
   /* Esto que viene a continuacion se hace ahora en el thread ThreadEsperaDescarga:
   if (algunFallo)
      JSigpac.ImprimirLinea("\tSe ha producido algun error");
   else
   {
     CalibrarMapa();
     System.out.print("\t- Descarga finalizada. ");
     if ( (opciones.n_filas == 1 && opciones.n_col == 1) ||
  	  (opciones._i && opciones._j) )
        System.out.println("-");
     else
        System.out.println("Ahora deberia ensamblar -");
     JSigpac.ImprimirLinea("");
     //_xx_ JSigpac.Salir();   
   }
   */       
   
   //JSigpac.Traza("Fin de IniciarProceso");
   return !algunFallo;   
 }
  
  
 void PonerSTOP(boolean activar)
 {
   PonerSTOP(activar, null);
 } 
 
 void PonerSTOP(boolean activar, String msj)
 {
   //System.out.println("PonerSTOP activar="+activar+"   msj="+msj);
   JSigpac.Traza("Mapa::PonerSTOP  activar="+activar+"   msj="+msj);
   if (JSigpac.entornoGrafico) 	
      misigpac.PonerSTOP(activar, msj);	
   else
   {
      if (activar == false)
      {
         JSigpac.ImprimirLinea("Proceso finalizado" + (msj != null ? ": " + msj : "")); 
         JSigpac.DesActivarImpresionEnFichero();
      }
   }
 }
 
 
 void CalcularEsquinas()
 {
   double anchuraTotal;

   JSigpac.Traza("CalcularEsquinas()");
   anchuraTotal = opciones.anchura.doubleValue() * opciones.n_col;   //_xx_vv_ opciones.anchura.intValue() * opciones.n_col;
   JSigpac.Traza("anchuraTotal=" + anchuraTotal + "   opciones.anchura=" + opciones.anchura.doubleValue() + "  opciones.n_col=" + opciones.n_col);
   JSigpac.Traza("opciones.alturaTotal=" + opciones.alturaTotal);
   //altura = (opciones.anchura.intValue() * opciones.n_filas) / FACTOR;
   //opciones.alturaTotal = (opciones.anchura.doubleValue() * opciones.n_filas) / FACTOR;  //_xx_vv_  (opciones.anchura.intValue() * opciones.n_filas) / FACTOR;
   XesqSupIzqda = opciones.qx;
   YesqSupIzqda = opciones.qy;
   XesqSupDer = opciones.qx + anchuraTotal;
   YesqSupDer = opciones.qy;
   XesqInfIzqda = opciones.qx;
   YesqInfIzqda = opciones.qy - opciones.alturaTotal;
   XesqInfDer = opciones.qx + anchuraTotal;
   YesqInfDer = opciones.qy - opciones.alturaTotal;
 }
 
 
 // Esta rutina devolvera "false" indicando que NO se continuara con el proceso de descarga.
 // Si se detecta que el mapa pertenece a dos husos diferentes, se preguntara al usuario si 
 // quiere continuar con la descarga. En caso negativo se proporcionaran los valores adecuados
 // para poder descargar los mapas por separado.
 boolean CheckVariosHusos()
 {
   Coordenada izqda, dcha, cambioDeHusoI, cambioDeHusoD;
   /*_datum_	
   izqda = new Coordenada(Datum.datumED50, XesqSupIzqda, YesqSupIzqda, 0, 
		          (byte)opciones.huso.intValue(), true);
   dcha  = new Coordenada(Datum.datumED50, XesqSupDer, YesqSupDer, 0,
		          (byte)opciones.huso.intValue(), true);				       				       				       
   */
   JSigpac.Traza("opciones.huso.intValue()=" + opciones.huso.intValue());
   izqda = new Coordenada(opciones.datumJSigpac, XesqSupIzqda, YesqSupIzqda, 0, 
		          (byte)opciones.huso.intValue(), true);
   dcha  = new Coordenada(opciones.datumJSigpac, XesqSupDer, YesqSupDer, 0,
		          (byte)opciones.huso.intValue(), true);	          

   JSigpac.Traza("izqda.getHuso()=" + izqda.getHuso() + "  dcha.getHuso()=" + dcha.getHuso()  );				       
   if (izqda.getHuso() == dcha.getHuso())
   {
      // Aquí hay que comprobar si la el valor de la X es demasiado alto para en su caso avisar con el mensaje adecuado.      
      //System.out.println("huso_ori=" + opciones.huso.intValue() + "   XesqSupIzqda="+XesqSupIzqda);      
      //if (XesqSupIzqda > 750000)
      JSigpac.Traza("izqda.getLon()=" + izqda.getLon() + "   longitudMax=" + ((((byte)opciones.huso.intValue()) - 30) * 6));
      if (izqda.getLon() > ((((byte)opciones.huso.intValue()) - 30) * 6))
      {
      	 if (misigpac != null)
            misigpac.SacarVentanita("Coordenadas no validas", "Valor demasiado alto en la coordenada X", JOptionPane.INFORMATION_MESSAGE);
         else
            JSigpac.ImprimirLinea("Coordenadas no validas: Valor demasiado alto en la coordenada X");      	
         return false;
      } else      
         return true;
   } else
   {      
      int resp;        
      // Ahora tenemos en cuenta que cerca de los límites del huso podemos estar saltando de uno a otro sin querer:
      //System.out.println("XesqSupIzqda: "+XesqSupIzqda+"   XesqSupDer: "+XesqSupDer);
      //if (XesqSupIzqda < 254000 || XesqSupDer > 748000)
      //   return true;
      //misigpac.SacarVentanita("Informacion", "Esta intentando obtener un mapa que pertenece a dos husos diferentes");	
      
      // Lo primero es determinar en que coordenada UTM de las X se produce el cambio de huso.
      // Tenemos en cuenta que cada huso tiene 6º de ancho.
      /*
         Huso 27       Huso 28         Huso 29          Huso 30         Huso31
                 -18º           -12º             -6º               0º
      */
      // Las coordenadas del cambio de huso son: (la X e Y se referiran al huso derecho)
      /*_datum_
      cambioDeHusoD = new Coordenada(Datum.datumED50, (30-izqda.getHuso())*(-6), izqda.getLat(), 0);
      // Esa misma coordenada del cambio de huso pero referidas al huso de la izquierda son:                                             
      cambioDeHusoI = new Coordenada(Datum.datumED50, (30-izqda.getHuso())*(-6)-0.0000000001, izqda.getLat(), 0);
      */
      cambioDeHusoD = new Coordenada(opciones.datumJSigpac, (30-izqda.getHuso())*(-6), izqda.getLat(), 0);
      // Esa misma coordenada del cambio de huso pero referidas al huso de la izquierda son:                                             
      cambioDeHusoI = new Coordenada(opciones.datumJSigpac, (30-izqda.getHuso())*(-6)-0.0000000001, izqda.getLat(), 0);
      //System.out.println("Cambio de Huso en X="+cambioDeHusoD.getX()+"   Y="+cambioDeHusoD.getY()+ "   huso="+cambioDeHusoD.getHuso());
      //System.out.println("Cambio de Huso en X="+cambioDeHusoI.getX()+"   Y="+cambioDeHusoI.getY()+ "   huso="+cambioDeHusoI.getHuso());
      // Una vez conocemos donde se produce el cambio de huso, miramos a ver si es necesario generar dos mapas o 
      // solo es necesario uno. Este ultimo caso se da cuando la coordenada de una de las esquinas se encuentra muy
      // cerca del cambio de huso:
      double difI, difD;
      difI = cambioDeHusoI.getX() - XesqSupIzqda;
      difD = XesqSupDer - cambioDeHusoI.getX();
      //System.out.println("1.- difI="+difI);
      //System.out.println("2.- difD="+difD);
      /*
         if (difI<8000 && difD<8000)
            return true;
      */         
         
      if (difD < 8000)
      {  // Generamos las coordenadas del mapa de forma que pertenezcan al huso de la izquierda:
         //MostrarCoordenadasUnMapa(XesqSupIzqda, YesqSupIzqda, cambioDeHusoI.getX()+difD, YesqInfDer, cambioDeHusoI.getHuso());
        //return false;
        return true;
      }
      	 
      if (difI < 8000)  // Si llega aqui es porque difD >= 8000
      {  // Generamos las coordenadas del mapa de forma que pertenezcan al huso de la derecha:
      	 double nueva_x = cambioDeHusoD.getX()-difI;
         MostrarCoordenadasUnMapa(nueva_x, YesqSupIzqda, nueva_x + (XesqInfDer-XesqSupIzqda), YesqInfDer, cambioDeHusoD.getHuso()); 
         return false;
      }
      	       	 
      	 
      // En cualquier otro caso, la solucion pasa por generar dos mapas, uno en cada huso:
      String msj = "Esta intentando obtener un mapa que pertenece a dos husos diferentes.";
      if (JSigpac.entornoGrafico)
      {
         resp = JOptionPane.showConfirmDialog(misigpac, msj + " ¿Desea continuar?",
		     "Husos diferentes", JOptionPane.YES_NO_OPTION,
		     JOptionPane.QUESTION_MESSAGE);
      } else
         resp = JOptionPane.NO_OPTION;
         
      if (resp == JOptionPane.NO_OPTION)
      {      	 
      	 MostrarCoordenadasDosMapas(cambioDeHusoI.getX(), cambioDeHusoD.getX(), cambioDeHusoD.getY(), cambioDeHusoD.getHuso());                        
	 return false;
      } else
         return true;	
   }
      	
 }
 
 // Funcion que recibe las nuevas coordenadas de las esquinas en un solo huso:
 void MostrarCoordenadasUnMapa(double xsi, double ysi, double xid, double yid, byte huso)
 { 
   boolean resp;
   
   JSigpac.ImprimirLinea("-----------------------------------------------------------------------------");
   JSigpac.ImprimirLinea(pad("|",2) + "Nuevas coordenadas del mapa en el HUSO: " + pad(huso,2));
   JSigpac.ImprimirLinea(pad("|",2) + "==========================================");
   JSigpac.ImprimirLinea(pad("|",2) + pad(xsi,20) + pad(" ",3) + pad(xid,20));
   JSigpac.ImprimirLinea(pad("|",2) + pad(ysi,20) + pad(" ",3) + pad(ysi,20));
   JSigpac.ImprimirLinea(pad("|",2));
   JSigpac.ImprimirLinea(pad("|",2));
   JSigpac.ImprimirLinea(pad("|",2) + pad(xsi,20) + pad(" ",3) + pad(xid,20));
   JSigpac.ImprimirLinea(pad("|",2) + pad(yid,20) + pad(" ",3) + pad(yid,20));
   JSigpac.ImprimirLinea("-----------------------------------------------------------------------------");
   
   resp = GuardarEnFichero(opciones.raiz+"_huso"+huso,
                           huso, xsi, ysi, xid-xsi, ysi-yid);                       
   if (resp == true)
   {
      if (misigpac != null)
         misigpac.SacarVentanita("Fichero guardado", "El fichero \""+opciones.raiz+"_huso"+huso+".jsi\" contiene los datos correctos.\nPulse despues el boton \"Leer de...\"", JOptionPane.INFORMATION_MESSAGE);
      else
         JSigpac.ImprimirLinea("El fichero \""+opciones.raiz+"_huso"+huso+".jsi\" contiene los datos correctos.");
   } else
   {
      if (misigpac != null)
         misigpac.SacarVentanita("Error:", "No se ha podido crear el fichero \"" + opciones.raiz + "_huso" + huso + ".jsi\"", JOptionPane.INFORMATION_MESSAGE);
      else
         JSigpac.ImprimirLinea("No se ha podido crear el fichero \"" + opciones.raiz + "_huso" + huso + ".jsi\"");
   }
 }
 
 
 void MostrarCoordenadasDosMapas(double XhusoIzqdo, double XhusoDer, double YhusoDer, byte husoD)
 {
   double XesqsupizqHusoIzqdo, YesqsupizqHusoIzqdo, XesqinfderHusoIzqdo, YesqinfderHusoIzqdo;
   double XesqsupizqHusoDcho, YesqsupizqHusoDcho, XesqinfderHusoDcho, YesqinfderHusoDcho;
   //byte husoIzqdo, husoDcho;
   //Coordenada aux1, aux2;
   //husoIzqdo = (byte)opciones.huso.intValue();
   XesqsupizqHusoIzqdo = XesqSupIzqda;
   YesqsupizqHusoIzqdo = YesqSupIzqda;
   XesqinfderHusoIzqdo = XhusoIzqdo + 1000; //750000;
   YesqinfderHusoIzqdo = YesqInfDer;
   
   //aux1 = new Coordenada(new Datum(6378388D, 6356911.94612795),
   //				   XesqinfderHusoIzqdo, YesqsupizqHusoIzqdo, 0,
   //				   (byte)opciones.huso.intValue(), true);
   XesqsupizqHusoDcho = XhusoDer-1000; //250000; //aux1.getX();				   	
   YesqsupizqHusoDcho = YhusoDer; //aux1.getY();
   //XesqinfderHusoDcho = XesqSupDer - XesqsupizqHusoDcho;
   XesqinfderHusoDcho = XesqsupizqHusoDcho +1000 + (XesqSupDer - XhusoIzqdo);
   YesqinfderHusoDcho = YesqsupizqHusoDcho - (YesqsupizqHusoIzqdo-YesqinfderHusoIzqdo);
   
   JSigpac.ImprimirLinea("-----------------------------------------------------------------------------");
   JSigpac.ImprimirLinea(pad("|",2) + pad(pad("HUSO:",6) + pad((byte)opciones.huso.intValue(),2), 32) + pad(" | ",3) + pad(pad("HUSO:",6) + pad(husoD,2), 28));
   JSigpac.ImprimirLinea(pad("|",2) + "===========================================================================");
   JSigpac.ImprimirLinea(pad("|",2) + pad(pad(df.format(XesqsupizqHusoIzqdo),12) + pad(" ",3) + pad(df.format(XesqinfderHusoIzqdo),12),32)+ pad(" | ",3) + pad(pad(df.format(XesqsupizqHusoDcho),15) + pad(" ",3) + pad(df.format(XesqinfderHusoDcho),15),32));
   JSigpac.ImprimirLinea(pad("|",2) + pad(pad(df.format(YesqsupizqHusoIzqdo),12) + pad(" ",3) + pad(df.format(YesqsupizqHusoIzqdo),12),32)+ pad(" | ",3) + pad(pad(df.format(YesqsupizqHusoDcho),15) + pad(" ",3) + pad(df.format(YesqsupizqHusoDcho),15),32));
   JSigpac.ImprimirLinea(pad("|",2) + pad("",32) + pad(" | ",3));
   JSigpac.ImprimirLinea(pad("|",2) + pad("",32) + pad(" | ",3));
   JSigpac.ImprimirLinea(pad("|",2) + pad(pad(df.format(XesqsupizqHusoIzqdo),12) + pad(" ",3) + pad(df.format(XesqinfderHusoIzqdo),12),32)+ pad(" | ",3) + pad(pad(df.format(XesqsupizqHusoDcho),15) + pad(" ",3) + pad(df.format(XesqinfderHusoDcho),15),32));
   JSigpac.ImprimirLinea(pad("|",2) + pad(pad(df.format(YesqinfderHusoIzqdo),12) + pad(" ",3) + pad(df.format(YesqinfderHusoIzqdo),12),32)+ pad(" | ",3) + pad(pad(df.format(YesqinfderHusoDcho),15) + pad(" ",3) + pad(df.format(YesqinfderHusoDcho),15),32));
   JSigpac.ImprimirLinea("-----------------------------------------------------------------------------");
   
   boolean respI, respD;
   String msj;
      	   	
   respI = GuardarEnFichero(opciones.raiz+"_huso"+opciones.huso.intValue(),
                       (byte)opciones.huso.intValue(),
                       XesqsupizqHusoIzqdo, YesqsupizqHusoIzqdo,
                       XesqinfderHusoIzqdo-XesqsupizqHusoIzqdo,
                       YesqsupizqHusoIzqdo-YesqinfderHusoIzqdo);
                      
   boolean error = false;                      
   if (respI)   
      msj = "Datos grabados en \""+opciones.raiz+"_huso"+opciones.huso.intValue()+".jsi\"\n";
     
   else
   {
      msj = "Error al grabar los datos en \""+opciones.raiz+"_huso.jsi\"\n";
      error = true;
   }
  
   respD = GuardarEnFichero(opciones.raiz+"_huso"+husoD,
                       husoD,
                       XesqsupizqHusoDcho, YesqsupizqHusoDcho,
                       XesqinfderHusoDcho-XesqsupizqHusoDcho,
                       YesqsupizqHusoDcho-YesqinfderHusoDcho);
   if (respD)   
      msj += "Datos grabados en \""+opciones.raiz+"_huso"+husoD+".jsi\"\n";      
   else
   {
      msj += "Error al grabar los datos en \""+opciones.raiz+"_huso"+husoD+".jsi\"\n";   
      error = true;
   }                  
      
   if (error == false)
      msj += "Puede cargar los datos correctos leyendo esos ficheros con el boton \"Leer de...\"";
   
   if (misigpac != null)
      misigpac.SacarVentanita("Información:", msj, JOptionPane.INFORMATION_MESSAGE);                 
   else
      JSigpac.ImprimirLinea(msj);
 }

 /*
    Para leer de un fichero con formato JSI los datos de una descarga y 
    devolver un array de String con los argumentos.  
  */
 
 public String[] LeerDeFichero(String nomfich)
 {
      String miAbreviaturaServidor, miDatum, topo_orto, esquina, resolucion=null, ancho, alto=null, huso, x, y,
             fil="", col="", fichero, manchas, calibrar, soloTrack=null, wgs84, solapamiento, borrarIntermedios, compresion=null;
      File fi = new File(nomfich);
      FileInputStream fis = null;
      String[] res=null;
      int num_arg = 0;
      int mapasVer, mapasHor;
      
      try {
        fis = new FileInputStream(fi);
	Properties misProperties = new Properties();
	
        misProperties.load(fis);        
        //misProperties.list(System.out);
	
	//_xx_new_
        JSigpac.servidor = new Servidor(misProperties.getProperty("servidor", 
                                        Servidor.abreviaturaServidor[Servidor.NACIONAL]));
        miAbreviaturaServidor = "-S" + JSigpac.servidor.Abreviatura();
        num_arg++;
        
        if (misProperties.getProperty("datum_entrada", "ED50").equals("ED50"))
           miDatum = "-D" + JSigpac.opcionesDatum[0];
        else
           miDatum = "-D" + JSigpac.opcionesDatum[1];
        num_arg++;
           
        if (misProperties.getProperty("ORTO", "true").equals("true"))
	   topo_orto = "-mORTO";
        else        
	   topo_orto = "-mTOPO" + misProperties.getProperty("escala", "25");                
        num_arg++;
        
        if (misProperties.getProperty("esquina", "true").equals("true")) 
            esquina = "-q";
        else
            esquina = "";        
        num_arg++;
        
        huso = "-h" + misProperties.getProperty("huso", "30");
        num_arg++;
        x = "-x" + misProperties.getProperty("X", "");
        num_arg++;
        y = "-y" + misProperties.getProperty("Y", "");
        num_arg++;
        
        if (misProperties.getProperty("cuadrante", "true").equals("true"))
        {	
	   ancho = "-a"	+ misProperties.getProperty("ancho", ""); 
	   num_arg++;
	   fil = "-fil" + misProperties.getProperty("filas", "");
	   num_arg++;
	   col = "-col" + misProperties.getProperty("columnas", "");       
	   num_arg++;  
        } else
        {           
	   ancho = "-A" + misProperties.getProperty("ancho", "");        
	   num_arg++;	
	   alto = misProperties.getProperty("alto");
	   if (alto != null)
	   {
	      alto = "-H" + alto;
	      num_arg++;
	   }	      
	   resolucion = misProperties.getProperty("resolucion");
	   if (resolucion != null)
	   {
	      resolucion = "-r"+resolucion;
	      num_arg++;
	   } else
	   {
	      fil = "-fil" + misProperties.getProperty("filas", "");
	      num_arg++;
	      col = "-col" + misProperties.getProperty("columnas", "");       
	      num_arg++;
	   }	   	   
	}                                        
                                                        	
                                   
        // CARPETA:
        fichero = "-f" + misProperties.getProperty("carpeta",".") + File.separator + misProperties.getProperty("fichero", "");
        num_arg++;
	
	/* Ya no tiene sentido lo de "Quita manchas":		
        if (misProperties.getProperty("quitamanchas", "true").equals("true"))
        {
           manchas = "-s";
           num_arg++;
           //System.out.println("quitamanchas   num_arg="+num_arg);
        } else
           manchas = null;
        */
        manchas = null;
        
        if (misProperties.getProperty("calibrar", "true").equals("true"))
        {
           calibrar = "-c";
           num_arg++;
           //System.out.println("calibrar   num_arg="+num_arg);
        } else
           calibrar = null;
        
        if (misProperties.getProperty("soloTrack", "false").equals("true"))
        {
           soloTrack = "-t";
           num_arg++;           
        } else
           soloTrack = null;
              
        if (misProperties.getProperty("datum_calibrar", "WGS84").equals("WGS84"))
        {
           wgs84 = "-w";
           num_arg++;
           //System.out.println("wgs84   num_arg="+num_arg);
        } else
           wgs84 = null;
        
        /* Tampoco tiene sentido ya lo de "Solapamiento perfecto":
        if (misProperties.getProperty("solapamiento", "false").equals("false"))        
           solapamiento = null;
        else
        {
           solapamiento = "-sol";                        
           num_arg++;
           //System.out.println("solapamiento   num_arg="+num_arg);
        }
        */
        solapamiento = null;
        
        if (misProperties.getProperty("borrar_intermedios", "false").equals("false"))        
           borrarIntermedios = null;
        else
        {
           borrarIntermedios = "-B";                        
           num_arg++;           
        }
           
        if ((compresion = misProperties.getProperty("compresion")) != null)
        {
           compresion = "-J" + compresion;
           num_arg++;
        }

        try {            
          mapasVer = Integer.valueOf(misProperties.getProperty("mapasVertical", "1")).intValue(); 
          mapasHor = Integer.valueOf(misProperties.getProperty("mapasHorizontal", "1")).intValue(); 
        } catch (NumberFormatException nfe) {
          mapasHor = 1;  mapasVer = 1;
        }
        if (mapasVer > 1 || mapasHor > 1)
           num_arg += 2;
                    
        //System.out.println("Numero de argumentos: " + num_arg);
        int i=0;
        res = new String[num_arg];
        res[i++] = miAbreviaturaServidor;
        res[i++] = miDatum;
        res[i++] = topo_orto;
        res[i++] = esquina;
        res[i++] = huso;
        res[i++] = x;
        res[i++] = y;
        res[i++] = ancho;
        if (alto != null)
           res[i++] = alto;
        if (resolucion != null)
           res[i++] = resolucion;
        else 
        {
           res[i++] = fil;
           res[i++] = col;        
        }
        if (manchas != null)
           res[i++] = manchas;
        if (calibrar != null)
           res[i++] = calibrar;
        if (soloTrack != null)
           res[i++] = soloTrack;
           
        if (wgs84 != null)
           res[i++] = wgs84;
        if (solapamiento != null)
           res[i++] = solapamiento;
        if (borrarIntermedios != null)
           res[i++] = borrarIntermedios;            
        if (compresion != null)
           res[i++] = compresion;
           
        if (mapasVer > 1 || mapasHor > 1)
        {       
           res[i++] = "-mv" + String.valueOf(mapasVer);
           res[i++] = "-mh" + String.valueOf(mapasHor);
        }
           
        res[i++] = fichero;                
        
      } catch (FileNotFoundException fnfe) {
	System.err.println("Mapa::LeerDeFichero(JSI) No se ha encontrado el fichero: " + fi.getPath());
	return null;
      } catch (SecurityException se) {
	System.err.println("Acceso de lectura denegado al fichero: " + fi.getPath());
	return null;
      } catch (IllegalArgumentException se) {
	System.err.println("Formato incorrecto del fichero: " + fi.getPath());
	return null;
      } catch (IOException se) {
	System.err.println("Error al intentar leer el fichero: " + fi.getPath());
	return null;
      }	finally {
      	 try {
           if (fis != null)
           {
      	      fis.close();      
      	      fis = null;
      	   }
         } catch (IOException ioe) {}      	
      }
 
      return res;	
 }
 
 
 public String[] LeerFicheroPorDefecto(String ficheroPorDefecto)
 {     
      String topo_orto, manchas, calibrar, wgs84, resolucion, solapamiento, borrarIntermedios, margen, soloTrack;
      File fi = new File(ficheroPorDefecto);
      FileInputStream fis=null;
      String[] res=null;
      int num_arg = 0;
      String miServidor, miCompresion;
      int mapasVer=1, mapasHor=1;
      JSigpac.Traza("LeerFicheroPorDefecto: " + fi.getAbsolutePath());     
      /*
      JSigpac.ImprimirLinea("fi.getAbsolutePath() de '.'="+(new File(".")).getAbsolutePath());      
      System.getProperties().setProperty("user.dir", "C:\\");
      JSigpac.ImprimirLinea("ficheroPorDefecto="+ficheroPorDefecto);
      JSigpac.ImprimirLinea("user.dir="+System.getProperties().getProperty("user.dir"));
      JSigpac.ImprimirLinea("fi.getPath()="+fi.getPath());
      JSigpac.ImprimirLinea("fi.getParent()="+fi.getParent());
      JSigpac.ImprimirLinea("fi.getParentFile()="+fi.getParentFile());
      JSigpac.ImprimirLinea("fi.getAbsolutePath()="+fi.getAbsolutePath());
      //JSigpac.ImprimirLinea("fi.getCanonicalPath()="+fi.getCanonicalPath());
      */
      try {
        fis = new FileInputStream(fi);
        //JSigpac.ImprimirLinea("Se ha abierto el fichero por defecto");
	    Properties misProperties = new Properties();
	
        misProperties.load(fis);        
        //misProperties.list(System.out);
	
        miServidor = misProperties.getProperty("servidor");   //, JSigpac.opcionesComboServidor[0]); 
                     
        if (Servidor.ServidorValido(miServidor))
        {           
           miServidor = Servidor.AbreviaturaServidor(miServidor);           
           num_arg++;
        } else
           miServidor = null;
        JSigpac.Traza("LeerFicheroPorDefecto()  miServidor="+miServidor);
              
        if (misProperties.getProperty("ORTO", "true").equals("true"))
	       topo_orto = "-mORTO";
        else        
	       topo_orto = "-mTOPO" + misProperties.getProperty("escala", "25");                
        num_arg++;                                                                         	
			
        if (misProperties.getProperty("quitamanchas", "true").equals("true"))
        {
           manchas = "-s";
           num_arg++;
           //System.out.println("quitamanchas   num_arg="+num_arg);
        } else
           manchas = null;
        
        if (misProperties.getProperty("calibrar", "true").equals("true"))
        {
           calibrar = "-c";
           num_arg++;
           //System.out.println("calibrar   num_arg="+num_arg);
        } else
           calibrar = null;
           
        if (misProperties.getProperty("datum_calibrar", "WGS84").equals("WGS84"))
        {
           wgs84 = "-w";
           num_arg++;
           //System.out.println("wgs84   num_arg="+num_arg);
        } else
           wgs84 = null;     
           
        if (misProperties.getProperty("soloTrack", "false").equals("true"))
        {
           soloTrack = "-t";
           num_arg++;           
        } else
           soloTrack = null;           
         
        resolucion = "-r" + misProperties.getProperty("resolucion", "1");                                    
        num_arg++;        
          
        if (misProperties.getProperty("solapamiento", "false").equals("false"))        
           solapamiento = null;
        else
        {
           solapamiento = "-sol";                        
           num_arg++;
           //System.out.println("solapamiento   num_arg="+num_arg);
        }        
        if (misProperties.getProperty("borrar_intermedios", "false").equals("false"))        
           borrarIntermedios = null;
        else
        {
           borrarIntermedios = "-B";                        
           num_arg++;           
        }
        miCompresion = misProperties.getProperty("compresion");                
        if (miCompresion != null)
           num_arg++;                
           
        margen = misProperties.getProperty("margen");
        if (margen != null)
           num_arg++;
        
        try {  
          mapasHor = Integer.valueOf(misProperties.getProperty("mapasHorizontal", "1")).intValue(); 
          mapasVer = Integer.valueOf(misProperties.getProperty("mapasVertical", "1")).intValue(); 
        } catch (NumberFormatException nfe) {
          mapasHor = 1;  mapasVer = 1;
        }
        if (mapasHor > 1 || mapasVer > 1)
           num_arg += 2;
        
        //System.out.println("Numero de argumentos: " + num_arg);
        int i=0;
        res = new String[num_arg];
        if (miServidor != null)    
           res[i++] = "-S" + miServidor;
        res[i++] = topo_orto;             
        if (manchas != null)
           res[i++] = manchas;
        if (calibrar != null)
           res[i++] = calibrar;
        if (wgs84 != null)
           res[i++] = wgs84;
        if (soloTrack != null)
           res[i++] = soloTrack;
        res[i++] = resolucion;
        if (solapamiento != null)
           res[i++] = solapamiento;                
        if (borrarIntermedios != null)
           res[i++] = borrarIntermedios;              
        if (margen != null)
           res[i++] = "-M" + margen;
        if (miCompresion != null)
           res[i++] = "-J" + miCompresion;  
        if (mapasHor > 1 || mapasVer > 1)
        {       
           res[i++] = "-mv" + String.valueOf(mapasVer);
           res[i++] = "-mh" + String.valueOf(mapasHor);
        }
      } catch (FileNotFoundException fnfe) {
      	JSigpac.ImprimirLinea("========================================================");
      	JSigpac.ImprimirLinea("====================   ATENCION   ======================");
	    JSigpac.ImprimirLinea("No se ha encontrado el fichero con los valores por defecto: ");
	    JSigpac.ImprimirLinea("\t" + fi.getAbsolutePath());
	    JSigpac.ImprimirLinea("");
	    JSigpac.ImprimirLinea("Deberia copiar la carpeta \"dat\" en el diretorio:");
	    JSigpac.ImprimirLinea(System.getProperties().getProperty("user.dir"));
	    JSigpac.ImprimirLinea("Y despues dar los valores por defecto deseados en el fichero \"jsigpac.defecto\"");
	    JSigpac.ImprimirLinea("========================================================");
	    JSigpac.ImprimirLinea("");
		    
        byte[] b;      	          	             
        b = new byte[80];
        System.out.print("\n\tDesea continuar a pesar de ello? (S/N)? ");
        try {
           System.in.read(b);               
           if (b[0] == 's' || b[0] == 'S')
              return null;                   
           else //if (b[0] == 's' || b[0] == 'S')
              JSigpac.Salir(500);
	     } catch (IOException ioe) {
              System.out.println("Error al leer de la consola");
         } 
	     return null;
      } catch (SecurityException se) {
	System.err.println("Acceso de lectura denegado al fichero: " + fi.getAbsolutePath());
	return null;
      } catch (IllegalArgumentException se) {
	System.err.println("Formato incorrecto del fichero: " + fi.getAbsolutePath());
	return null;
      } catch (IOException se) {
	System.err.println("Error al intentar leer el fichero: " + fi.getAbsolutePath());
	return null;
      }	finally {
      	 try {
           if (fis != null)
      	      fis.close();      
         } catch (IOException ioe) {}
      }
      
      return res;	 
      	
 }
 
 /* 
    Para guardar en el fichero que se le pasa los datos X,Y, huso, ancho y alto.
    Suele llamarse desde las funciones que chequean si el mapa pertenece a dos husos.
  */
 
 public boolean GuardarEnFichero(String nomfich, byte huso, double X, double Y, double ancho, double alto)
 {
   PrintWriter jsi = null;
   File fi;
   fi = new File(nomfich+".jsi");
   //JSigpac.ImprimirLinea("_ccc_ GuardarEnFichero nomfich-huso-x-y-ancho-alto");
   try {
      jsi = new PrintWriter(new BufferedWriter(new FileWriter(nomfich+".jsi")));
      
      //_xx_new_
      jsi.println("servidor: " + JSigpac.servidor.Nombre()); // (String)misigpac.servidorCombo.getSelectedItem());
      
      if (misigpac != null)      
         jsi.println("ORTO: " + misigpac.topo_orto.getSelectedItem().equals("Ortofoto"));
      else
         jsi.println("ORTO: " + (opciones._ortofoto && opciones.ortofoto));
      jsi.println("esquina: true");
      jsi.println("cuadrante: false");
      jsi.println("huso: " + huso);
      jsi.println("X: " + X);
      jsi.println("Y: " + Y);

      jsi.println("ancho: " + ancho);
      jsi.println("alto: " + alto);
      if (misigpac != null) 
         jsi.println("resolucion: " + misigpac.resolucion.getSelectedItem());                        
      
      if (misigpac != null) 
         jsi.println("filas: " + misigpac.t_num_filas.getText());
      else
         jsi.println("filas: " + opciones.n_filas);
      
      //System.out.println("_xx_ ancho="+ancho+"   op.anchura="+opciones.anchura.intValue()+"   op.n_col="+opciones.n_col+"  anchuraTotal="+opciones.anchuraTotal);
      //double ncol = (ancho / (opciones.anchura.intValue() / opciones.n_col));  
      double ncol = (ancho / opciones.anchura.doubleValue());   //_xx_vv_  (ancho / opciones.anchura.intValue());
      //System.out.println("_xx_ ncol="+ncol);
      jsi.println("columnas: " + (int)Math.floor(ncol+0.999));  
      
      //jsi.println("cuadranton: " + cuadranton.getText());
      
      String esc = "25";
      if (misigpac != null)
      {         
         if (misigpac.mtn25.isSelected())
	    esc = "25";
         else if (misigpac.mtn200.isSelected())
	    esc = "200";
         else if (misigpac.mtn1250.isSelected())
	    esc = "1250";
         else if (misigpac.mtn2000.isSelected())
	    esc = "2000";
         else if (misigpac.mtn2000T.isSelected())
   	    esc = "2000_T";
   	 jsi.println("escala: " + esc);      	  
      } else
      {
      	if (!(opciones._ortofoto && opciones.ortofoto))
      	  if (opciones.escala.length() > "MTN_".length())
      	  {
      	     try {
      	        esc = opciones.escala.substring("MTN_".length());
      	        jsi.println("escala: " + esc);
      	     } catch (IndexOutOfBoundsException iobe) {
      	     	System.err.println("Error al intentar obtener la escala del mapa topografico");
      	     }      	    
      	  }            	      	
      }
   

      try {
         String aux; // Hay que substituir el posible caracter separador de directorios
	   	     // de Windows "\" por "\\" ya que si no habra problemas a la hora 
		     // de leer del fichero la informacion de la carpeta:
         //System.out.println("directorio:"+miMapa.opciones.directorio.toString());      
         aux = opciones.directorio.toString().replaceAll("\\\\", "\\\\\\\\");                                
         //System.out.println("Hacemos replace: aux="+tous);
         //aux = miMapa.opciones.directorio.toString().split("\\\\");
         //for (int k=0; k<aux.length; k++)
         //{
         //   System.out.println("aux["+k+"]="+aux[k]);
         //}
         jsi.println("carpeta: " + aux);
      } catch (NullPointerException npe) {
      }
      
      String nom = fi.getName();
      int j = 0;
      j = nom.lastIndexOf('.');
      if (j>0)
         nom = nom.substring(0, j);
      jsi.println("fichero: " + nom); //fi.getName());
      if (misigpac != null)
         jsi.println("quitamanchas: " + misigpac.quitaManchas.isSelected());
      else
         jsi.println("quitamanchas: " + opciones.quitaManchas);
      
      if (misigpac != null)
      {         	 
         jsi.println("calibrar: " + misigpac.fcalibra.isSelected());
         if (misigpac.WGS84.isSelected())
            jsi.println("datum_calibrar: WGS84");
         else
            jsi.println("datum_calibrar: ED50");
      } else
      {
      	 jsi.println("calibrar: " + opciones.generarFichsCalibracion);
      	 if (opciones.WGS84)
      	    jsi.println("datum_calibrar: WGS84");
      	 else
            jsi.println("datum_calibrar: ED50");
      }
      
      if (misigpac != null)   
         jsi.println("solapamiento: " + misigpac.solapamiento.isSelected());
      else
         jsi.println("solapamiento: " + opciones.solapamiento);
         
      jsi.println("ficheroEnsam: " + fi.getName());
      //jsi.println("filasEn: " + t_filas.getText());
      //jsi.println("columnasEn: " + t_columnas.getText());
      if (misigpac != null)
      {
         jsi.println("compresion: " + misigpac.compresionJPEG.getValue());
         jsi.println("borrar_intermedios: " + misigpac.borrarFicheros.isSelected());
         jsi.println("desYens: " + misigpac.ensambleDirecto.isSelected());
      }
      
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar guardar los datos del mapa del huso "+huso);
      return false;
   }

   if (jsi != null)
   {
      jsi.flush();
      jsi.close();
   }
	
   return true; 	
 }
 
 /*
    Para guardar en el fichero que se le pasa todos esos datos.
    Se llama cuando hay que crear un JSI para posteriormente descargarlo y ensamblarlo
  */
  
 public boolean GuardarEnFichero(String nomfichJSI, String miServidor, 
 				 boolean tipoMapaOrto, Datum midatumJSigpac,
                                 byte huso, double X, double Y, double ancho, double alto, 
                                 double resolucion, int columnas, int filas, 
                                 String escala, String directorio,  String ficheroJPG,
                                 boolean quitamanchas, boolean calibrar, boolean WGS84, boolean soloTrack,
                                 boolean solapamiento, boolean borrarIntermedios,
                                 int compresion, int mapasVer, int mapasHor)
 {
   PrintWriter jsi = null;
   JSigpac.Traza("resolucion="+resolucion+"   Se guarda en el fichero: "+nomfichJSI+".jsi");
   try {
      jsi = new PrintWriter(new BufferedWriter(new FileWriter(nomfichJSI+".jsi")));
      
      jsi.println("servidor: " + Servidor.AbreviaturaServidor(miServidor));      
      jsi.println("datum_entrada: " + midatumJSigpac.miString()); 
      jsi.println("ORTO: " + tipoMapaOrto);                       
      jsi.println("esquina: true");
      jsi.println("cuadrante: false");
      jsi.println("huso: " + huso);
      jsi.println("X: " + X);
      jsi.println("Y: " + Y);
      jsi.println("ancho: " + ancho);
      jsi.println("alto: " + alto);
      
      if (resolucion != -1)
         jsi.println("resolucion: " + resolucion);
     /*
      else if (misigpac != null) 
         jsi.println("resolucion: " + misigpac.resolucion.getSelectedItem());                        
      else
         jsi.println("resolucion: 1");
     */
         
      if (filas > 0)          
         jsi.println("filas: " + filas);                        
      if (columnas > 0)   
         jsi.println("columnas: " + columnas);                
      
      if (tipoMapaOrto == false)
      {
         if (escala != null)
            jsi.println("escala: " + escala);
         else
         {
      	    String esc = "25";
            if (misigpac != null)
            {         
               if (misigpac.mtn25.isSelected())
	          esc = "25";
               else if (misigpac.mtn200.isSelected())
	          esc = "200";
               else if (misigpac.mtn1250.isSelected())
     	          esc = "1250";
               else if (misigpac.mtn2000.isSelected())
	          esc = "2000";
               else if (misigpac.mtn2000T.isSelected())
   	          esc = "2000_T";
   	       jsi.println("escala: " + esc);      	  
            } else
            {
               if (!(opciones._ortofoto && opciones.ortofoto))
      	         if (opciones.escala.length() > "MTN_".length())
      	         {
      	            try {
      	              esc = opciones.escala.substring("MTN_".length());
      	              jsi.println("escala: " + esc);
      	            } catch (IndexOutOfBoundsException iobe) {
      	       	      System.err.println("Error al intentar obtener la escala del mapa topografico");
      	            }      	    
      	         }            	      	
            }
         }
      }           

      try {
         String aux; // Hay que substituir el posible caracter separador de directorios
	   	     // de Windows "\" por "\\" ya que si no habra problemas a la hora 
		     // de leer del fichero la informacion de la carpeta:
         //System.out.println("directorio:"+miMapa.opciones.directorio.toString());      
         aux = directorio.replaceAll("\\\\", "\\\\\\\\");                                
         //System.out.println("Hacemos replace: aux="+tous);
         //aux = miMapa.opciones.directorio.toString().split("\\\\");
         //for (int k=0; k<aux.length; k++)
         //{
         //   System.out.println("aux["+k+"]="+aux[k]);
         //}
         jsi.println("carpeta: " + aux);
      } catch (NullPointerException npe) {
      }            
      
      jsi.println("fichero: " + ficheroJPG); //nom); //fi.getName());
      
      jsi.println("quitamanchas: " + quitamanchas);      
         
      jsi.println("calibrar: " + calibrar);
      jsi.println("soloTrack: " + soloTrack);
          
      if (WGS84)
         jsi.println("datum_calibrar: WGS84");
      else
         jsi.println("datum_calibrar: ED50");
            
      jsi.println("solapamiento: " + solapamiento);      
         
      jsi.println("ficheroEnsam: " + ficheroJPG);  //fi.getName());  // Lo he cambiado porque con "gi.getName() pone el nombre seguido de ".jsi"
      //jsi.println("filasEn: " + t_filas.getText());
      //jsi.println("columnasEn: " + t_columnas.getText());
      /*
         if (misigpac != null)
         {
            jsi.println("compresion: " + misigpac.compresionJPEG.getValue());
            jsi.println("borrar_intermedios: " + misigpac.borrarFicheros.isSelected());
            jsi.println("desYens: " + misigpac.ensambleDirecto.isSelected());
         }
       */
       jsi.println("borrar_intermedios: " + borrarIntermedios);
       jsi.println("compresion: " + compresion);
       
       if (mapasHor > 1 || mapasVer > 1)
       {
       	  jsi.println("mapasVertical: " + mapasVer);
       	  jsi.println("mapasHorizontal: " + mapasHor);          
       }
       
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar guardar los datos del mapa.");
      return false;
   }

   if (jsi != null)
   {
      jsi.flush();
      jsi.close();
   }
	
   return true; 	
 }
 
 

 void ImprimirComando()
 {
  String opt="";
  JSigpac.ImprimirLinea("===============================================================================");
  double xi, yi;
  String elservidor;
  
  if (opciones._coorEsquina)
  {
     xi = opciones.qx;
     yi = opciones.qy;
  } else
  {
     xi = opciones.x;
     yi = opciones.y;
  }
   
  if (opciones.generarFichsCalibracion)
     opt = opt + " -c";
  if (opciones.WGS84)
     opt = opt + " -w";
  if (opciones.quitaManchas)
     opt = opt + " -s";
  if (opciones.solapamiento)
     opt = opt + " -sol";
  if (opciones._i && opciones._j)
     opt = opt+ " -i" + opciones.i + " -j" + opciones.j;
      
  if (JSigpac.servidor.Id() == -1) 
     elservidor = "";
  else
     elservidor = " -S" + JSigpac.servidor.Abreviatura();
  
  /*
  JSigpac.ImprimirLinea("COMANDO: JSigpac -d "+tipo+opciones.coorEsquina +
	   " -h" + opciones.huso + " -x" + xi + " -y" + yi + 
	   opciones.optAncho + opciones.optAlto +
	   " -fil"+opciones.n_filas + " -col" + opciones.n_col +
	   opt + " -f"+opciones.raiz);
  */
  JSigpac.ImprimirLinea("COMANDO: JSigpac -d" + elservidor +
  	   (opciones.datumJSigpac == Datum.datumED50 ? " -DED50" : " -DWGS84") + " " + opciones.tipoMapa +
  	   opciones.coorEsquina + " -h" + opciones.huso + " -x" + xi + " -y" + yi + 
	   opciones.optAncho + opciones.optAlto +
	   (opciones._reso == true ? " -r"+opciones.reso : " -fil"+opciones.n_filas + " -col" + opciones.n_col) +
	   opt + " -f"+opciones.raiz);	   
	   
  JSigpac.ImprimirLinea("");
 }


 boolean TratarArgumentos(String [] args)
 {
  int i=0; 
  String arg_actual="", explicacion=""; 
  boolean Anchura_repe = false;
  JSigpac.Traza("TratarArgumentos");
  
  // Por defecto, seguimos trabajando con ED50:
  opciones.datumJSigpac = Datum.datumED50;  // Modificable con la opcion "-D"
  
  try {
   for (i=0; i<args.length; i++)
   {
      JSigpac.Traza("args["+i+"]="+args[i]);
      if (args[i].startsWith("-S")) {
      	 explicacion=" (servidor)";
         arg_actual = "-S";
         JSigpac.servidor = new Servidor(args[i].substring(2,5));	     
         arg_actual = "-D";
         explicacion=" (datum: ED50 o WGS84)";
         continue;
      } else if (args[i].startsWith("-D")) {
         arg_actual = "-D";
         if (args[i].substring(2,3).equals("W"))
            opciones.datumJSigpac = Datum.datumWGS84; 	 
         else
            opciones.datumJSigpac = Datum.datumED50;
	 arg_actual = "-m";
	 explicacion=" (topografico u ortofoto)";
	 continue; 
      } else if (args[i].equals("-mORTO")) {       	      
         opciones._ortofoto = true;
         opciones.ortofoto = true;
         opciones.tipoMapa = args[i];
         arg_actual = "-h";
         explicacion=" (zona o huso)";	  
         continue;
      } else if (args[i].startsWith("-mTOPO")) {       	   
       	 String escala="25";
       	 int aux = args[i].length() - 6; // Le resto la longitud de "-mTOPO".
         if (aux < 2)
            escala = "25";
         else 
            escala = args[i].substring(6);

         opciones.escala = "MTN_" + escala;
         opciones._ortofoto = true;
         opciones.ortofoto = false;	     
         opciones.tipoMapa = args[i];
         arg_actual = "-h";
         explicacion=" (zona o huso)";
         continue;
      } else if (args[i].startsWith("-h")) {
	 arg_actual = "-h";
	 opciones.huso = Integer.valueOf( args[i].substring(2) );
	 opciones._huso = true;
	 arg_actual = "-x";
	 explicacion=" (coordenada X)";
	 continue;
      } else if (args[i].startsWith("-q")) {
	 opciones._coorEsquina = true;
	 continue;
      } else if (args[i].startsWith("-x")) {
	 arg_actual = "-x";
	 explicacion=" (coordenada X)";
	 opciones.x = (Double.valueOf( args[i].substring(2) )).doubleValue();
	 opciones._x = true;
	 arg_actual = "-y";
	 explicacion=" (coordenada Y)";
	 continue;
      } else if (args[i].startsWith("-y")) {
	 arg_actual = "-y";
	 explicacion=" (coordenada Y)";
	 opciones.y = (Double.valueOf( args[i].substring(2) )).doubleValue();
	 opciones._y = true;
	 arg_actual = "-a";
	 explicacion=" (anchura en metros)";
	 continue;
      } else if (args[i].startsWith("-a")) {
	 arg_actual = "-a";
	 explicacion=" (anchura en metros)";
	 if (opciones._anchuraTotal == true)
	 {
	    Anchura_repe = true;
	    break;
         }
	 opciones._anchuraTotal = false;
	 opciones._anchura = true;
	 opciones.anchura = Double.valueOf( args[i].substring(2) ); //_xx_vv_ Integer.valueOf( args[i].substring(2) );
	 arg_actual = "-fil";
	 explicacion=" (numero de cuadrantes)";
	 continue;
      } else if (args[i].startsWith("-A")) {
	 arg_actual = "-A";
	 explicacion=" (anchura en metros)";
	 if (opciones._anchura == true)
	 {
	    Anchura_repe = true;
	    break;
         }
	 opciones._anchuraTotal = true;
	 opciones._anchura = false;
	 opciones.anchura = Double.valueOf( args[i].substring(2) ); //_xx_vv_ Integer.valueOf( args[i].substring(2) );
	 arg_actual = "-fil";
	 explicacion=" (calculo de cuadrantes)";
	 continue;
      } else if (args[i].startsWith("-H")) {
	 arg_actual = "-H";
	 explicacion=" (altura en metros)";	 
	 opciones._altura = true;	 
	 opciones.alturaTotal = Double.valueOf( args[i].substring(2) ).doubleValue();
	 arg_actual = "-r";
	 explicacion=" (resolucion)";
	 continue;
      } else if (args[i].startsWith("-r")) {
	 arg_actual = "-r";
	 explicacion=" (resolucion)";	 
	 opciones.reso = (Double.valueOf( args[i].substring(2) )).doubleValue(); 
	 opciones._reso = true;	 
	 arg_actual = "-f";
	 explicacion=" (raiz del nombre del fichero)";
	 JSigpac.Traza("TratarArgumentos  reso = " + opciones.reso);
	 continue;
      } else if (args[i].startsWith("-fil")) {
	 arg_actual = "-fil";
	 explicacion=" (numero de filas)";
	 opciones.n_filas = (Integer.valueOf( args[i].substring(4) )).intValue();
	 opciones._n_filas = true;
	 arg_actual = "-col";
	 explicacion=" (numero de columnas)";
	 continue;
      } else if (args[i].startsWith("-col")) {
	 arg_actual = "-col";
	 explicacion=" (numero de columnas)";
	 opciones.n_col = (Integer.valueOf( args[i].substring(4) )).intValue();
	 opciones._n_col = true;
	 arg_actual = "-f";
	 explicacion=" (raiz del nombre del fichero)";
	 continue;
      } else if (args[i].startsWith("-f")) { // Ojo a la opcion "-fil"
	 arg_actual = "-f";
	 explicacion=" (raiz del nombre del fichero)";
	 opciones.raiz = args[i].substring(2);
	 // Si no viene un nombre de fichero, saltara la excepcion antes
	 // de poner a true el valor de opciones._raiz:
	 if (opciones.raiz.trim().length() > 0)
	    opciones._raiz = true;
	 continue;
      } else if (args[i].startsWith("-F")) { 
	 arg_actual = "-F";
	 explicacion=" (fichero con el formato del nombre del mapa)";
	 opciones.fichformato = args[i].substring(2);
	 // Si no viene algo, saltara la excepcion antes
	 // de poner a true el valor de opciones._fichformato:
	 if (opciones.fichformato.trim().length() > 0)
	    opciones._fichformato = true;
	 continue;
      } else if (args[i].startsWith("-c")) {
	 arg_actual = "-c";
	 explicacion=" (para generar los ficheros de calibracion)";
	 opciones.generarFichsCalibracion = true;
	 continue;
      } else if (args[i].startsWith("-w")) {
	 arg_actual = "-w";
	 explicacion=" (usar Datum WGS84 en los ficherios de calibracion)";
	 opciones.WGS84 = true;
	 continue;
      } else if (args[i].startsWith("-sol")) {
	 arg_actual = "-sol";
	 explicacion=" (para realizar solapamiento cuasi-perfecto de cuadrantes)";
	 opciones.solapamiento = true;
	 continue;
      } else if (args[i].startsWith("-B")) {
	 arg_actual = "-B";
	 explicacion=" (para borrar los ficheros intermedios)";
	 opciones.borrarIntermedios = true;
	 continue;
      } else if (args[i].startsWith("-s")) {
	 arg_actual = "-s";
	 explicacion=" (para quitar las marcas de agua del SigPac)";
	 opciones.quitaManchas = true;
	 continue;
      } else if (args[i].startsWith("-i")) {
	 arg_actual = "-i";
	 explicacion=" (cuadrante de la fila i)";
	 opciones.i = (Integer.valueOf( args[i].substring(2) )).intValue();
	 opciones._i = true;
	 continue;
      } else if (args[i].startsWith("-j")) {
	 arg_actual = "-j";
	 explicacion=" (cuadrante de la columna j)";
	 opciones.j = (Integer.valueOf( args[i].substring(2) )).intValue();
	 opciones._j = true;
	 continue;
      } else if (args[i].startsWith("-J")) {
	 arg_actual = "-J";
	 explicacion=" (Factor de compresion JPEG)";
	 opciones.compresion = (Integer.valueOf( args[i].substring(2) )).intValue();
	 //opciones._j = true;
	 continue;
      } else if (args[i].startsWith("-mv")) {
      	 arg_actual = "-mv";
         explicacion=" (numero de mapas en vertical)";
         opciones.mapasVertical = (Integer.valueOf( args[i].substring(3) )).intValue();
         //opciones._mapasVertical = true;
         continue;
      } else if (args[i].startsWith("-mh")) {
         arg_actual = "-mh";
         explicacion=" (numero de mapas en horizontal)";
         opciones.mapasHorizontal = (Integer.valueOf( args[i].substring(3) )).intValue();
         //opciones._mapasHorizontal = true;
         continue;
      } else if (args[i].startsWith("-Y")) {
         arg_actual = "-Y";
         explicacion=" (para respuesta automatico)";
         opciones.noPreguntarMas = true; //kkmail
         JSigpac.Traza("Recibido: " + args[i]);
         try {
            if (args[i].charAt(2) == 'n' || args[i].charAt(2) == 'N')
               opciones.sobreescribir = false;
            else
               opciones.sobreescribir = true;
         } catch(IndexOutOfBoundsException iobe) {
            JSigpac.Traza("sobreescribir = true");
            opciones.sobreescribir = true;
         }
         JSigpac.Traza("sobreescribir=" + opciones.sobreescribir);
         
         //opciones._mapasHorizontal = true;
         continue;
      } else if (args[i].startsWith("-t")) {  //_hh_ 
      	 opciones.soloTrack = true;
      } else {
	 JSigpac.ImprimirLinea("Opcion desconocida: " + args[i]);
      }
   }
  } catch (NullPointerException npe) {
     if (!arg_actual.equals("-f") && !arg_actual.equals("-s") &&
	 !arg_actual.equals("-c"))
     {
     	
        String msj = "Es posible que no haya dado valor a la opcion \""+arg_actual+"\""+explicacion;
        System.out.println("\n" + msj);
	if (JSigpac.entornoGrafico)	
  	   JOptionPane.showMessageDialog(misigpac, msj,
	       "Faltan parametros", JOptionPane.ERROR_MESSAGE);	        	
        return false;
      }
  } catch (NumberFormatException nfe) {
        String msj = "Valor numerico incorrecto en \""+arg_actual+"\""+explicacion;
        System.out.println("\n" + msj);
	if (JSigpac.entornoGrafico)
	   JOptionPane.showMessageDialog(misigpac, msj,
	       "Parametro incorrecto", JOptionPane.ERROR_MESSAGE);
	return false;
  }
  
  //opciones.soloTrack = false; // _hh_    BORRAR ESTOOOOOOOOOOOOOOOOOOOOOOOOOO !!!!
  
  /*  Esto lo hacemos despues de 
  if (opciones._raiz == false)
  {
      opciones.raiz = "mapa_";
      opciones._raiz = true;
  }
  */
  
  String error;
  if ((error = opciones.EstanTodosLosParametros(Anchura_repe)) != null)
  {
     if (JSigpac.entornoGrafico)
	JOptionPane.showMessageDialog(misigpac, error,
	            "Error en los parametros", JOptionPane.ERROR_MESSAGE);
	JSigpac.ImprimirLinea("Error: " + error);   
     return false;
  }   
  opciones.datumServidor = JSigpac.servidor.DameDatumDelServidor();
  TratarSingularidadCanaria(); // Puede verse modificado el valor de "opciones.datumServidor".
  JSigpac.Traza("En AjustesIniciales  opciones.datumServidor="+opciones.datumServidor.miString());
  // En estos momentos ya conocemos el servidor que se va a utilizar. Lo normal es que 
  // dimensionemos ahora el tamaño de los cuadrantes descargados:
  Dimensionar();
  
  if ((error = opciones.RecalcularCoordenadas()) != null)
  {
     if (JSigpac.entornoGrafico)
	JOptionPane.showMessageDialog(misigpac, error,
	            "Resolucion incorrecta", JOptionPane.ERROR_MESSAGE);
	JSigpac.ImprimirLinea("Error: " + error);   
     return false;
  }
  
  if (opciones._raiz == false)
  {
      if (opciones._fichformato)
         opciones.raiz = opciones.GenerarNombreAutomatico(opciones.fichformato);
      else
         opciones.raiz = opciones.GenerarNombreAutomatico(JSigpac.FORMATO);
      opciones._raiz = true;
  } else if (opciones.raiz != null)
  {
      File dirfich = new File(opciones.raiz);
      //JSigpac.ImprimirLinea("getName="+dirfich.getName());
      //JSigpac.ImprimirLinea("getParent="+dirfich.getParent());
      //JSigpac.ImprimirLinea("getParentFile="+dirfich.getParentFile());
      //JSigpac.ImprimirLinea("getPath="+dirfich.getPath());
      //JSigpac.ImprimirLinea("getAbsolutePath="+dirfich.getAbsolutePath());
      //JSigpac.ImprimirLinea("getCanonicalPath="+dirfich.getCanonicalPath());
      //JSigpac.ImprimirLinea("getCanonicalFile="+dirfich.getCanonicalFile());
      if (dirfich.isDirectory())  
      {
      	 if (opciones._fichformato)    
      	    opciones.raiz = opciones.raiz + File.separator + opciones.GenerarNombreAutomatico(opciones.fichformato);	
      	 else
      	    opciones.raiz = opciones.raiz + File.separator + opciones.GenerarNombreAutomatico(JSigpac.FORMATO);      	    
      } else
      {
      	// Si no es un directorio, pues entonces es que ya nos están dando el nombre del mapa y no hacemos nada más.
      	/*
      	 opciones._raiz = false;
      	 if (JSigpac.entornoGrafico)
	    JOptionPane.showMessageDialog(misigpac, "No existe o no es un directorio: " + opciones.raiz,
	            "Directorio no valido", JOptionPane.ERROR_MESSAGE);
	 JSigpac.ImprimirLinea("Error: No existe o no es un directorio: " + opciones.raiz);   
         return false;
        */
      }
  }
  
  if ((error = opciones.ValidarFormulario()) != null)
  {
     if (JSigpac.entornoGrafico)
	JOptionPane.showMessageDialog(misigpac, error,
	            "Datos no válidos", JOptionPane.ERROR_MESSAGE);
	JSigpac.ImprimirLinea("Error: " + error);   
     return false;
  }
  
  opciones.DeterminarFicherosDeCalibracion();
  return true;     
 }


 public static String pad(double numerito, int len)
 {
   return pad(Double.toString(numerito), len);
 }
 
 public static String pad(byte numerito, int len)
 {
   return pad(Byte.toString(numerito), len);
 }

 public static String pad(int numerito, int len)
 {
   return pad(Integer.toString(numerito), len);
 }
 
 // Acomodar un String a la longitud pasada:
 public static String pad(String str, int len)
 {
   StringBuffer sb = new StringBuffer(str);
   //System.out.println("str="+str+" LON="+str.length()+" len="+len);
   for (int i=str.length(); i<len; i++)
       sb.append(" ");
   return sb.toString();
 }


 void CalibrarMapa()
 { 	
   if (opciones == null || opciones.huso == null)
   {
      JSigpac.ImprimirLinea("No se realiza la calibracion por no disponer de los datos necesarios");
      return;
   }
      
   JSigpac.ImprimirLinea("");
   JSigpac.ImprimirLinea("\tCALIBRACION del MAPA (huso " + opciones.huso + "):");
   JSigpac.ImprimirLinea("\t===============================");
   JSigpac.ImprimirLinea("");
   //_datum_
   if (opciones.datumJSigpac == Datum.datumED50)   
      JSigpac.ImprimirLinea("\t[Datum ED50]");
   else
      JSigpac.ImprimirLinea("\t[Datum WGS84]");
   JSigpac.ImprimirLinea("\t"+pad("Esquina sup izqda",24)+"Esquina sup derecha");
   JSigpac.ImprimirLinea("\tX="+pad(XesqSupIzqda,22)+"X="+XesqSupDer);
   JSigpac.ImprimirLinea("\tY="+pad(YesqSupIzqda,22)+"Y="+YesqSupDer);
   JSigpac.ImprimirLinea("");
   JSigpac.ImprimirLinea("\t"+pad("Esquina inf izqda",24)+"Esquina inf derecha");
   JSigpac.ImprimirLinea("\tX="+pad(XesqInfIzqda,22)+"X="+XesqInfDer);
   JSigpac.ImprimirLinea("\tY="+pad(YesqInfIzqda,22)+"Y="+YesqInfDer);
   JSigpac.ImprimirLinea("\t___________________________________________\n");


   if (opciones.datumJSigpac == Datum.datumED50) // Saco tambien la calibracion en WGS84:
   {
      Coordenada utmED50I, utmED50D, utmWGS84I, utmWGS84D;
      //_xx_ee_   
   
      utmED50I = new Coordenada(Datum.datumED50, XesqSupIzqda, YesqSupIzqda, 0,
				       (byte)opciones.huso.intValue(), true); 
      utmWGS84I = utmED50I.CambioDeDatum(Datum.datumWGS84);
      utmED50D = new Coordenada(Datum.datumED50, XesqSupDer, YesqSupDer, 0,
				       (byte)opciones.huso.intValue(), true); 
      utmWGS84D = utmED50D.CambioDeDatum(Datum.datumWGS84);

      JSigpac.ImprimirLinea("\t[Datum WGS84]", true, false);
      JSigpac.ImprimirLinea("\t"+pad("Esquina sup izqda",24)+"Esquina sup derecha", true, false);
      JSigpac.ImprimirLinea("\tX="+pad(utmWGS84I.getX(),22)+"X="+utmWGS84D.getX(), true, false);
      JSigpac.ImprimirLinea("\tY="+pad(utmWGS84I.getY(),22)+"Y="+utmWGS84D.getY(), true, false);
      JSigpac.ImprimirLinea("", true, false);
      utmED50I = new Coordenada(Datum.datumED50, XesqInfIzqda, YesqInfIzqda, 0,
				      (byte)opciones.huso.intValue(), true); 
      utmWGS84I = utmED50I.CambioDeDatum(Datum.datumWGS84);
      utmED50D = new Coordenada(Datum.datumED50, XesqInfDer, YesqInfDer, 0,
				      (byte)opciones.huso.intValue(), true);
      utmWGS84D = utmED50D.CambioDeDatum(Datum.datumWGS84);
      JSigpac.ImprimirLinea("\t"+pad("Esquina inf izqda",24)+"Esquina inf derecha", true, false);
      JSigpac.ImprimirLinea("\tX="+pad(utmWGS84I.getX(),22)+"X="+utmWGS84D.getX(), true, false);
      JSigpac.ImprimirLinea("\tY="+pad(utmWGS84I.getY(),22)+"Y="+utmWGS84D.getY(), true, false);
      JSigpac.ImprimirLinea("", true, false);
   }

   // Crear Ficheros de calibracion:
   if (opciones.generarFichsCalibracion == true)
   {
      if (opciones.calibrarOzi)	
         CalibracionOZI();
      if (opciones.calibrarCompe)
         CalibracionCompeGPS();
      if (opciones.calibrarGPSTuner)
         CalibracionGPSTuner();
      if (opciones.calibrarPathAway)
         CalibracionPathAway();
      if (opciones.calibrarJGW)
         CalibracionJGW();
      if (opciones.calibrarGlobalMapper)
         CalibracionGlobalMapper();
      if (opciones.calibrarERS)
         CalibracionERS();
      if (opciones.calibrarTracky)
         CalibracionTracky();
      if (opciones.calibrarMyMotion)
         CalibracionMyMotion();
      if (opciones.calibrarTomTom)
         CalibracionTomTom();         
   }
 }


 String N_S()
 {
   return "N";
 }

 String E_W()
 {
   // A partir de las coordenadas del punto central,
   // determino si estamos al este o al oeste del meridiano de Greenwich:
   //_datum_
   if (primeraVez_E_W)
   {
      Coordenada utm;
      utm = new Coordenada(opciones.datumJSigpac, opciones.x, opciones.y, 0, 
		   (byte)opciones.huso.intValue(), true);
      primeraVez_E_W = false;
      if (utm.getLon() < 0) // Si la longitud es negativa, OESTE
	 e_w = "W";
      else
	 e_w = "E";
   }
   return e_w;
 }
 
 String E_W(double laX, double laY, byte elHuso)
 {   
   Coordenada utm=null;
   //System.out.println("_cc_ laX="+laX+"   laY="+laY+"  h="+elHuso);
   //_datum_   Por ahora no he cambiado nada
   if (opciones.WGS84)    	
      utm = new Coordenada(Datum.datumWGS84, laX, laY, 0, elHuso, true);
   else
      utm = new Coordenada(Datum.datumED50, laX, laY, 0, elHuso, true);
   
   //System.out.println("_cc_ opciones.WGS84="+opciones.WGS84+"   lon="+utm.getLon()+"  lat="+utm.getLat());   
   
   if (utm.getLon() < 0) // Si la longitud es negativa, OESTE
      e_w = "W";
   else
      e_w = "E";
   
   return e_w;
 }


void CalibracionOZI()
 {
   PrintWriter ozi = null;   
   String extension = ".map";
   boolean distintos = true; // Controla si el datumJSigpac (es decir, el datum en el que el usuario 
                             // ha tecleado las coordenadas) es igual o distinto al datum con el que se 
                             // quieren generar los ficheros de calibracion.
   Datum datumCambio=null; // Valdra ED50 o WGS84 dependiendo del datumJSigpac y del datum en que se calibre.                             
   int anchoTotal, altoTotal;   
   primeraVez_E_W = true; // Relacionado con la funcion E_W
   anchoTotal = (int) Mapa.width * opciones.n_col;
   altoTotal = (int) Mapa.height * opciones.n_filas;

   if (opciones.datumJSigpac == Datum.datumED50)   
      distintos = opciones.WGS84 ? true : false;
   else if (opciones.datumJSigpac == Datum.datumWGS84)
      distintos = opciones.WGS84 ? false : true;
   else
   {
      JSigpac.ImprimirLinea(" --- No se genera fichero de calibracion para Ozi ---");
      return;
   }

   if (distintos)  
      datumCambio = opciones.WGS84 ? Datum.datumWGS84 : Datum.datumED50;
      
   try {
      ozi = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));

      ozi.println("OziExplorer Map Data File Version 2.2");
      File auxfile;
      auxfile = new File(opciones.raiz + ".jpg");
      ozi.println(auxfile.getName());
      ozi.println(auxfile.getName());
      //ozi.println(opciones.raiz + ".jpg");
      //ozi.println(opciones.raiz + ".jpg");
      ozi.println("1 ,Map Code,");
      if (opciones.WGS84)
         ozi.println("WGS 84,, 0.0000, 0.0000,WGS 84");
      else
         ozi.println("European 1950,, 0.0000, 0.0000,WGS 84");
      ozi.println("Reserved 1");
      ozi.println("Reserved 2");
      ozi.println("Magnetic Variation,,,E");
      ozi.println("Map Projection,(UTM) Universal Transverse Mercator,PolyCal,No,AutoCalOnly,No,BSBUseWPX,No");
      ozi.flush();

      Coordenada utmJSigpacsi, utmJSigpacsd, utmJSigpacid, utmJSigpacii;
      Coordenada utmCalibracionsi=null, utmCalibracionsd=null, utmCalibracionid=null, utmCalibracionii=null;

      utmJSigpacsi = new Coordenada(opciones.datumJSigpac, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmJSigpacsd = new Coordenada(opciones.datumJSigpac, XesqSupDer, YesqSupDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmJSigpacid = new Coordenada(opciones.datumJSigpac, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmJSigpacii = new Coordenada(opciones.datumJSigpac, XesqInfIzqda, YesqInfIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);

      // Asigno los valores correctos a las esquinas segun el datum:
      double xsi, ysi, xsd, ysd, xid, yid, xii, yii;
      byte hsi, hsd, hid, hii;
      String E_Wsi, E_Wsd, E_Wid, E_Wii;
      if (distintos)
      { 
      	 
	 // Calculo las coordenadas en el datum de la calibracion:
         utmCalibracionsi = utmJSigpacsi.CambioDeDatum(datumCambio);
         utmCalibracionsd = utmJSigpacsd.CambioDeDatum(datumCambio);
         utmCalibracionid = utmJSigpacid.CambioDeDatum(datumCambio);
         utmCalibracionii = utmJSigpacii.CambioDeDatum(datumCambio);
         xsi = utmCalibracionsi.getX();
	 ysi = utmCalibracionsi.getY();
	 hsi = utmCalibracionsi.getHuso();
	 E_Wsi = utmCalibracionsi.E_W();
	 //System.out.println("_cc_si huso="+utmWGS84si.getHuso());
	 xsd = utmCalibracionsd.getX();
	 ysd = utmCalibracionsd.getY();
	 hsd = utmCalibracionsd.getHuso();
	 E_Wsd = utmCalibracionsd.E_W();
	 //System.out.println("_cc_sd huso="+utmWGS84sd.getHuso());
	 xid = utmCalibracionid.getX();
	 yid = utmCalibracionid.getY();
	 hid = utmCalibracionid.getHuso();
	 E_Wid = utmCalibracionid.E_W();
	 //System.out.println("_cc_id huso="+utmWGS84id.getHuso());
	 xii = utmCalibracionii.getX();
	 yii = utmCalibracionii.getY();
	 hii = utmCalibracionii.getHuso();
	 E_Wii = utmCalibracionii.E_W();
	 
	 //System.out.println("_cc_ii huso="+utmWGS84ii.getHuso());
      } else
      {
         xsi = utmJSigpacsi.getX(); //_cc_ XesqSupIzqda;
	 ysi = utmJSigpacsi.getY(); //YesqSupIzqda;
	 hsi = utmJSigpacsi.getHuso();	 
	 E_Wsi = utmJSigpacsi.E_W();
	 xsd = utmJSigpacsd.getX(); //XesqSupDer;
	 ysd = utmJSigpacsd.getY(); //YesqSupDer;
	 hsd = utmJSigpacsd.getHuso();
	 E_Wsd = utmJSigpacsd.E_W();
	 xid = utmJSigpacid.getX(); //XesqInfDer;
	 yid = utmJSigpacid.getY(); //YesqInfDer;
	 hid = utmJSigpacid.getHuso();
	 E_Wid = utmJSigpacid.E_W();
	 xii = utmJSigpacii.getX(); //XesqInfIzqda;
	 yii = utmJSigpacii.getY(); //YesqInfIzqda;
	 hii = utmJSigpacii.getHuso();
	 E_Wii = utmJSigpacii.E_W();
      }

      // Calibrar esquina superior izquierda:
      //ozi.println("Point01,xy,0,0,in, deg, , ,"+N_S()+", , ,"+E_W(xsi,ysi,hsi)+", grid, "+hsi+", "+xsi+", "+ysi+",N");
      ozi.println("Point01,xy,0,0,in, deg, , ,"+N_S()+", , ,"+E_Wsi+", grid, "+hsi+", "+xsi+", "+ysi+",N");
      // Calibrar esquina superior derecha:
      //ozi.println("Point02,xy,"+anchoTotal+",0,in, deg, , ,"+N_S()+", , ,"+E_W(xsd,ysd,hsd)+", grid, "+hsd+", "+xsd+", "+ysd+",N");
      ozi.println("Point02,xy,"+anchoTotal+",0,in, deg, , ,"+N_S()+", , ,"+E_Wsd+", grid, "+hsd+", "+xsd+", "+ysd+",N");
      // Calibrar esquina inferior derecha:
      //ozi.println("Point03,xy,"+anchoTotal+","+altoTotal+",in, deg, , ,"+N_S()+", , ,"+E_W(xid,yid,hid)+", grid, "+hid+", "+xid+", "+yid+",N");
      ozi.println("Point03,xy,"+anchoTotal+","+altoTotal+",in, deg, , ,"+N_S()+", , ,"+E_Wid+", grid, "+hid+", "+xid+", "+yid+",N");
      // Calibrar esquina inferior izquierda:
      //ozi.println("Point04,xy,0,"+altoTotal+",in, deg, , ,"+N_S()+", , ,"+E_W(xii,yii,hii)+", grid, "+hii+", "+xii+", "+yii+",N");
      ozi.println("Point04,xy,0,"+altoTotal+",in, deg, , ,"+N_S()+", , ,"+E_Wii+", grid, "+hii+", "+xii+", "+yii+",N");

      int i;
      for (i=5; i<10; i++)
          ozi.println("Point0"+i+",xy, , ,in, deg, , ,N, , ,W, grid, , , ,N");
      for (i=10; i<31; i++)
          ozi.println("Point"+i+",xy, , ,in, deg, , ,, , ,, grid, , , ,");

      ozi.println("Projection Setup,,,,,,,,,,");
      ozi.println("Map Feature = MF ; Map Comment = MC     These follow if they exist");
      ozi.println("Track File = TF      These follow if they exist");
      ozi.println("Moving Map Parameters = MM?    These follow if they exist");
      ozi.println("MM0,Yes");
      ozi.println("MMPNUM,4");
      ozi.println("MMPXY,1,0,0");
      ozi.println("MMPXY,2,"+anchoTotal+",0");
      ozi.println("MMPXY,3,"+anchoTotal+","+altoTotal);
      ozi.println("MMPXY,4,0,"+altoTotal);

      if (distintos)
      {
        // Calibrar esquina superior izquierda:
        ozi.println("MMPLL,1,"+utmCalibracionsi.getLon()+","+utmCalibracionsi.getLat());
        // Calibrar esquina superior derecha:
        ozi.println("MMPLL,2,"+utmCalibracionsd.getLon()+","+utmCalibracionsd.getLat());
        // Calibrar esquina inferior derecha:
        ozi.println("MMPLL,3,"+utmCalibracionid.getLon()+","+utmCalibracionid.getLat());
        // Calibrar esquina inferior izquierda:
        ozi.println("MMPLL,4,"+utmCalibracionii.getLon()+","+utmCalibracionii.getLat());
      } else
      {
        // Calibrar esquina superior izquierda:
        ozi.println("MMPLL,1,"+utmJSigpacsi.getLon()+","+utmJSigpacsi.getLat());
        // Calibrar esquina superior derecha:
        ozi.println("MMPLL,2,"+utmJSigpacsd.getLon()+","+utmJSigpacsd.getLat());
        // Calibrar esquina inferior derecha:
        ozi.println("MMPLL,3,"+utmJSigpacid.getLon()+","+utmJSigpacid.getLat());
        // Calibrar esquina inferior izquierda:
        ozi.println("MMPLL,4,"+utmJSigpacii.getLon()+","+utmJSigpacii.getLat());
      }
      double daux;
      daux = opciones.anchura.doubleValue() / Mapa.width;
      ozi.println("MM1B,"+daux);
      ozi.println("MOP,Map Open Position,0,0");
      ozi.println("IWH,Map Image Width/Height,"+anchoTotal+","+altoTotal);

   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibracion de Ozi");
      return;
   }
   if (ozi != null)
      ozi.close();
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion de Ozi: "+carpeta.getName()+extension);

 }


 void CalibracionCompeGPS()
 {
   PrintWriter compe = null;
   String extension = ".imp";
   boolean distintos = true; // Controla si el datumJSigpac (es decir, el datum en el que el usuario 
                             // ha tecleado las coordenadas) es igual o distinto al datum con el que se 
                             // quieren generar los ficheros de calibracion.
   Datum datumCambio=null; // Valdra ED50 o WGS84 dependiendo del datumJSigpac y del datum en que se calibre. 
   int anchoTotal, altoTotal;
   anchoTotal = (int) Mapa.width * opciones.n_col;
   altoTotal = (int) Mapa.height * opciones.n_filas;

   if (opciones.datumJSigpac == Datum.datumED50)   
      distintos = opciones.WGS84 ? true : false;
   else if (opciones.datumJSigpac == Datum.datumWGS84)
      distintos = opciones.WGS84 ? false : true;
   else
   {
      JSigpac.ImprimirLinea(" --- No se genera fichero de calibracion para Compe ---");
      return;
   }

   if (distintos)  
      datumCambio = opciones.WGS84 ? Datum.datumWGS84 : Datum.datumED50;
      
   /*   
     if (datumCambio == Datum.datumWGS84)
        JSigpac.ImprimirLinea("datumCambio es WGS84");
     else
        JSigpac.ImprimirLinea("datumCambio es ED50");
     JSigpac.ImprimirLinea("distintos="+distintos);
   */   
   
   try {
      compe = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));

      compe.println("CompeGPS MAP File");
      compe.println("<Header>");
      compe.println("Version=2");
      compe.println("VerCompeGPS=6.0");
      String norte_sur = ",N,";
      /* Seria bueno saber si es norte o sur pero por ahora no lo sabemos.
	 Igual hay que anadir alguna "Checkbox" al jSIGPAC:
        Coordenada utm = new Coordenada(new Datum(6378388D, 6356911.94612795),
				      XesqSupIzqda, YesqSupIzqda, 0,
				      (byte)opciones.huso,
        if (utm.EsHemisferioSur() == true)
	   norte_sur = ",S,";
      */
      compe.println("Projection=0,UTM," + opciones.huso + norte_sur);
      compe.println("Coordinates=0");
      if (opciones.WGS84)
         compe.println("Datum=WGS84"); 
      else
         compe.println("Datum=European 1950"); 
      compe.println("</Header>");
      compe.println("<Map>");
      File auxfile;
      auxfile = new File(opciones.raiz + ".jpg");
      compe.println("Bitmap=" + auxfile.getName());
      //compe.println("Bitmap=" + opciones.raiz + ".jpg");
      compe.println("BitsPerPixel=0");
      compe.println("BitmapWidth="+anchoTotal);
      compe.println("BitmapHeight="+altoTotal);
      compe.println("</Map>");
      compe.println("<Calibration>");
      
      // Asigno los valores correctos a las esquinas segun el datum:
      Coordenada utmJSigpacsi, utmJSigpacsd, utmJSigpacid, utmJSigpacii;
      utmJSigpacsi = new Coordenada(opciones.datumJSigpac, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmJSigpacsd = new Coordenada(opciones.datumJSigpac, XesqSupDer, YesqSupDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmJSigpacid = new Coordenada(opciones.datumJSigpac, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmJSigpacii = new Coordenada(opciones.datumJSigpac, XesqInfIzqda, YesqInfIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
      double xsi, ysi, xsd, ysd, xid, yid, xii, yii;
      byte hsi, hsd, hid, hii;
      if (distintos)
      {          
         Coordenada utmCalibracionsi, utmCalibracionsd, utmCalibracionid, utmCalibracionii;         
	 // Calculo las coordenadas en el datum WGS84:
         utmCalibracionsi = utmJSigpacsi.CambioDeDatum(datumCambio);
         utmCalibracionsd = utmJSigpacsd.CambioDeDatum(datumCambio);
         utmCalibracionid = utmJSigpacid.CambioDeDatum(datumCambio);
         utmCalibracionii = utmJSigpacii.CambioDeDatum(datumCambio);
         xsi = utmCalibracionsi.getX();
	 ysi = utmCalibracionsi.getY();
	 hsi = utmCalibracionsi.getHuso();
	 xsd = utmCalibracionsd.getX();
	 ysd = utmCalibracionsd.getY();
	 hsd = utmCalibracionsd.getHuso();
	 xid = utmCalibracionid.getX();
	 yid = utmCalibracionid.getY();
	 hid = utmCalibracionid.getHuso();
	 xii = utmCalibracionii.getX();
	 yii = utmCalibracionii.getY();
	 hii = utmCalibracionii.getHuso();
      } else
      {
         xsi = utmJSigpacsi.getX(); //XesqSupIzqda;
	 ysi = utmJSigpacsi.getY(); //YesqSupIzqda;
	 hsi = utmJSigpacsi.getHuso();
	 xsd = utmJSigpacsd.getX(); //XesqSupDer;
	 ysd = utmJSigpacsd.getY(); //YesqSupDer;
	 hsd = utmJSigpacsd.getHuso();
	 xid = utmJSigpacid.getX(); //XesqInfDer;
	 yid = utmJSigpacid.getY(); //YesqInfDer;
	 hid = utmJSigpacid.getHuso();
	 xii = utmJSigpacii.getX(); //XesqInfIzqda;
	 yii = utmJSigpacii.getY(); //YesqInfIzqda;
	 hii = utmJSigpacii.getHuso();
      }

      // Esquina superior izquierda:
      compe.println("P0=0.00000000,0.00000000,"+hsi+"T,"+xsi+","+ysi);
      // Esquina superior derecha:
      compe.println("P1="+(anchoTotal*1.0D)+",0.00000000,"+hsd+"T,"+xsd+","+ysd);
      // Esquina inferior derecha:
      compe.println("P2="+(anchoTotal*1.0D)+","+(altoTotal*1.0D)+","+hid+"T,"+xid+","+yid);
      // Esquina inferior izquierda:
      compe.println("P3=0.00000000,"+(altoTotal*1.0D)+","+hii+"T,"+xii+","+yii);
      compe.println("</Calibration>");
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibraicon de Compe GPS");
      return;
   }
   if (compe != null)
      compe.close();
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion de Compe GPS: "+carpeta.getName()+extension);
 }


 void CalibracionGPSTuner()
 {
   PrintWriter gpstunerJPG=null, gpstunerGTM=null;
   String extension = ".gmi";
   int anchoTotal, altoTotal;
   anchoTotal = (int) Mapa.width * opciones.n_col;
   altoTotal = (int) Mapa.height * opciones.n_filas;

   try {
      gpstunerJPG = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));
      gpstunerGTM = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+"_GTM"+extension)));

      gpstunerJPG.println("Map Calibration data file v2.0");
      gpstunerGTM.println("GTM Calibration data file v2.0");
      gpstunerJPG.println("C:\\tmp\\"+opciones.raiz+".jpg");
      gpstunerGTM.println("C:\\tmp\\"+opciones.raiz+".gtm");
      gpstunerJPG.println(anchoTotal);
      gpstunerJPG.println(altoTotal);

      Coordenada utmED50, utmWGS84;
      String aux;
      // Calibrar esquina superior izquierda:
      //_datum_
      if (opciones.datumJSigpac == Datum.datumED50)
      {
         utmED50 = new Coordenada(Datum.datumED50, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmWGS84 = utmED50.CambioDeDatum(Datum.datumWGS84);
      } else
      {
      	 utmWGS84 = new Coordenada(Datum.datumWGS84, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmED50 = utmWGS84.CambioDeDatum(Datum.datumED50);         			            
      }
      	
      if (opciones.WGS84)
         aux = "0;0;"+utmWGS84.getLon()+";"+utmWGS84.getLat();
      else
         aux = "0;0;"+utmED50.getLon()+";"+utmED50.getLat();
      gpstunerJPG.println(aux);
      gpstunerGTM.println(aux);
   /*
      // Calibrar esquina superior derecha:
      utmED50 = new Coordenada(new Datum(6378388D, 6356911.94612795),
			   XesqSupDer, YesqSupDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmWGS84 = utmED50.CambioDeDatum(new Datum(6378137D, 6356752.31424518));
      aux = anchoTotal+";0;"+utmWGS84.getLon()+";"+utmWGS84.getLat();
      gpstunerJPG.println(aux);
      gpstunerGTM.println(aux);
   */
      // Calibrar esquina inferior derecha:
      //_datum_
      if (opciones.datumJSigpac == Datum.datumED50)
      {
         utmED50 = new Coordenada(Datum.datumED50, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmWGS84 = utmED50.CambioDeDatum(Datum.datumWGS84);
      } else
      {
      	 utmWGS84 = new Coordenada(Datum.datumWGS84, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmED50 = utmWGS84.CambioDeDatum(Datum.datumED50);
      }
      
      if (opciones.WGS84)
         aux = anchoTotal+";"+altoTotal+";"+utmWGS84.getLon()+";"+utmWGS84.getLat();
      else
         aux = anchoTotal+";"+altoTotal+";"+utmED50.getLon()+";"+utmED50.getLat();
      gpstunerJPG.println(aux);
      gpstunerGTM.println(aux);
   /*  
      // En el GPSTuner, al menos en la versión 4.1, con dar dos puntos de calibracion es suficiente.
      // De hecho, si damos mas de dos puntos de calibracion, va a fallar el programa en la PDA.  
      // Calibrar esquina inferior izquierda:
      utmED50 = new Coordenada(Datum.datumED50, XesqInfIzqda, YesqInfIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
      utmWGS84 = utmED50.CambioDeDatum(Datum.datumWGS84);
      aux = "0;"+altoTotal+";"+utmWGS84.getLon()+";"+utmWGS84.getLat();
      gpstunerJPG.println(aux);
      gpstunerGTM.println(aux);
   */

   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibraicon de GPS Tuner");
      return;
   }
   if (gpstunerJPG != null)
      gpstunerJPG.close();
   if (gpstunerGTM != null)
      gpstunerGTM.close();
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion de GPS Tuner: "+carpeta.getName()+extension);

 }


 void CalibracionPathAway()
 {
   PrintWriter pathaway = null;
   String extension = ".pwm";
   int anchoTotal, altoTotal;
   anchoTotal = (int) Mapa.width * opciones.n_col;
   altoTotal = (int) Mapa.height * opciones.n_filas;

   try {
      pathaway = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));


      Coordenada utmED50si, utmED50sd, utmED50id, utmED50ii;
      Coordenada utmWGS84si=null, utmWGS84sd=null, utmWGS84id=null, utmWGS84ii=null;
      
      //_datum_
      if (opciones.datumJSigpac == Datum.datumED50)
      {
         utmED50si = new Coordenada(Datum.datumED50, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmED50sd = new Coordenada(Datum.datumED50, XesqSupDer, YesqSupDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmED50id = new Coordenada(Datum.datumED50, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmED50ii = new Coordenada(Datum.datumED50, XesqInfIzqda, YesqInfIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
			   
         // Calculo las coordenadas en el datum WGS84:
         utmWGS84si = utmED50si.CambioDeDatum(Datum.datumWGS84);
         utmWGS84sd = utmED50sd.CambioDeDatum(Datum.datumWGS84);
         utmWGS84id = utmED50id.CambioDeDatum(Datum.datumWGS84);
         utmWGS84ii = utmED50ii.CambioDeDatum(Datum.datumWGS84);			   
      } else
      {
         utmWGS84si = new Coordenada(Datum.datumWGS84, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmWGS84sd = new Coordenada(Datum.datumWGS84, XesqSupDer, YesqSupDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmWGS84id = new Coordenada(Datum.datumWGS84, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmWGS84ii = new Coordenada(Datum.datumWGS84, XesqInfIzqda, YesqInfIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
			   
         // Calculo las coordenadas en el datum WGS84:
         utmED50si = utmWGS84si.CambioDeDatum(Datum.datumED50);
         utmED50sd = utmWGS84sd.CambioDeDatum(Datum.datumED50);
         utmED50id = utmWGS84id.CambioDeDatum(Datum.datumED50);
         utmED50ii = utmWGS84ii.CambioDeDatum(Datum.datumED50);      	      	
      }

      // Asigno los valores correctos a las esquinas segun el datum:
      double lonsi, latsi, lonsd, latsd, lonid, latid, lonii, latii; 
      
      if (opciones.WGS84)
      {
         lonsi = utmWGS84si.getLon();
         latsi = utmWGS84si.getLat();
         lonsd = utmWGS84sd.getLon();
         latsd = utmWGS84sd.getLat();
         lonid = utmWGS84id.getLon();
         latid = utmWGS84id.getLat();
         lonii = utmWGS84ii.getLon();
         latii = utmWGS84ii.getLat();
      } else
      {
         lonsi = utmED50si.getLon();
         latsi = utmED50si.getLat();
         lonsd = utmED50sd.getLon();
         latsd = utmED50sd.getLat();
         lonid = utmED50id.getLon();
         latid = utmED50id.getLat();
         lonii = utmED50ii.getLon();
         latii = utmED50ii.getLat();
      }

      pathaway.println("pw_north=7=" + latsd);
      pathaway.println("pw_west=7=" + lonii);
      pathaway.println("pw_south=7=" + latii);
      pathaway.println("pw_east=7=" + lonid);
      pathaway.println("c1_lon=7=" + lonsi);
      pathaway.println("c1_lat=7=" + latsi);
      pathaway.println("c1_x=7=0");
      pathaway.println("c1_y=7=0");
      pathaway.println("vpa(1)=7=0.00000000");
      pathaway.println("vpb(1)=7=0.00000000");
      pathaway.println("vpm(1)=7=0.00000000");
      pathaway.println("vpn(1)=7=0.00000000");
      pathaway.println("c2_lon=7=" + lonsd);
      pathaway.println("c2_lat=7=" + latsd);
      pathaway.println("c2_x=7=" + anchoTotal);
      pathaway.println("c2_y=7=0");
      pathaway.println("vpa(2)=7=0.00000000");
      pathaway.println("vpb(2)=7=0.00000000");
      pathaway.println("vpm(2)=7=0.00000000");
      pathaway.println("vpn(2)=7=0.00000000");
      pathaway.println("c3_lon=7=" + lonid);
      pathaway.println("c3_lat=7=" + latid);
      pathaway.println("c3_x=7=" + anchoTotal);
      pathaway.println("c3_y=7=" + altoTotal);
      pathaway.println("vpa(3)=7=0.00000000");
      pathaway.println("vpb(3)=7=0.00000000");
      pathaway.println("vpm(3)=7=0.00000000");
      pathaway.println("vpn(3)=7=0.00000000");
      pathaway.println("c4_lon=7=" + lonii);
      pathaway.println("c4_lat=7=" + latii);
      pathaway.println("c4_x=7=0");
      pathaway.println("c4_y=7=" + altoTotal);
      pathaway.println("vpa(4)=7=0.00000000");
      pathaway.println("vpb(4)=7=0.00000000");
      pathaway.println("vpm(4)=7=0.00000000");
      pathaway.println("vpn(4)=7=0.00000000");


      int i;
      for (i=5; i<10; i++)
      {
        pathaway.println("c" + i + "_lon=7=0.00000000");
        pathaway.println("c" + i + "_lat=7=0.00000000");
        pathaway.println("c" + i + "_x=7=0");
        pathaway.println("c" + i + "_y=7=0");
        pathaway.println("vpa(" + i + ")=7=0.00000000");
        pathaway.println("vpb(" + i + ")=7=0.00000000");
        pathaway.println("vpm(" + i + ")=7=0.00000000");
        pathaway.println("vpn(" + i + ")=7=0.00000000");
      }

   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibracion de PathAway");
      return;
   }
   if (pathaway != null)
      pathaway.close();
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion de PathAway (.pwm): "+carpeta.getName()+extension);

 }

 void CalibracionJGW()
 {
   // El formato de este fichero de calibracion puede verse en:
   //    http://en.wikipedia.org/wiki/World_file

   PrintWriter jgw = null;
   String extension = ".jgw";
   int anchoTotal, altoTotal;
   double incX, incY;
   anchoTotal = (int) Mapa.width * opciones.n_col;
   altoTotal = (int) Mapa.height * opciones.n_filas;

   incX = (XesqSupDer - XesqSupIzqda) / (anchoTotal * 1.0D);
   incY = (YesqInfIzqda -  YesqSupIzqda) / (altoTotal * 1.0D);

   try {
      jgw = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));

      Coordenada utmED50si;
      Coordenada utmWGS84si=null;

      //JSigpac.ImprimirLinea("qx="+XesqSupIzqda+"  qy="+YesqSupIzqda+"  huso="+opciones.huso.intValue());
      //_datum_
      if (opciones.datumJSigpac == Datum.datumED50)
      {
         utmED50si = new Coordenada(Datum.datumED50, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         //JSigpac.ImprimirLinea("x="+utmED50si.getX()+"  y="+utmED50si.getY()+"  huso="+utmED50si.getHuso());
         // Calculo la coordenada en el datum WGS84:
         utmWGS84si = utmED50si.CambioDeDatum(Datum.datumWGS84);
      } else
      {
         utmWGS84si = new Coordenada(Datum.datumWGS84, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);         
         // Calculo la coordenada en el datum ED50:
         utmED50si = utmWGS84si.CambioDeDatum(Datum.datumED50);
      }
      
      jgw.println(incX);
      jgw.println("0.0000000000000");
      jgw.println("0.0000000000000");
      jgw.println(incY);
      if (opciones.WGS84)
      {
         jgw.println(utmWGS84si.getX());
         jgw.println(utmWGS84si.getY());
         //JSigpac.ImprimirLinea("x="+utmWGS84si.getX()+"  y="+utmWGS84si.getY()+"  huso="+utmWGS84si.getHuso());
      } else
      {
      	 // Un "error" comentado por Ximo en gmail. Me dijo que para JGW convenia mantener el huso que 
      	 // habia especificado el usuario en las coordenadas del mapa (en el caso de mapas que juegan en el 
      	 // limite del huso):
      	 if (utmED50si.getHuso() != utmED50si.getHuso_ori())
      	 {
            jgw.println(utmED50si.getX_ori());
            jgw.println(utmED50si.getY_ori());
         } else
         {
            jgw.println(utmED50si.getX());
            jgw.println(utmED50si.getY());
         }
         //JSigpac.ImprimirLinea("x="+utmED50si.getX()+"  y="+utmED50si.getY()+"  huso="+utmED50si.getHuso());
      }
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibracion jgw");
      return;
   }
   if (jgw != null)
      jgw.close();
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion .JGW: "+carpeta.getName()+extension);

}


void CrearPRJ(String path)
{
   PrintWriter prj = null;
   String extension = ".prj";
   
   try {
     prj = new PrintWriter(new BufferedWriter(
		 new FileWriter(path + File.separator + opciones.huso + extension)));
     prj.println("Projection     UTM");
     if (opciones.WGS84)
        prj.println("Datum          WGS_1984");
     else
        prj.println("Datum          EUROPEAN_1950");
     prj.println("Zunits         NO");
     prj.println("Units          METERS");
     prj.println("Zone           " + opciones.huso);
     prj.println("Xshift         0.000000");
     prj.println("Yshift         0.000000");
     prj.println("Parameters");
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero PRJ para Global Mapper");
      JSigpac.ImprimirLinea("ioe="+ioe);
      return;
   }
   if (prj != null)
      prj.close();
   //File carpeta = new File(opciones.raiz);
   //JSigpac.ImprimirLinea("\tFichero de .PRJ: "+carpeta.getName()+extension);
   
}

 void CalibracionGlobalMapper()
 {
   PrintWriter gm = null;
   String extension = ".gms";
   //int anchoTotal, altoTotal;
   //anchoTotal = (int) Mapa.width * opciones.n_col;
   //altoTotal = (int) Mapa.height * opciones.n_filas;	
   File aux = new File(opciones.raiz);
   String dir = aux.getParent();
   if (dir == null)
      dir = ".";
   String nomfich = aux.getName();
   
   CrearPRJ(dir);
   
   try {
      gm = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));
      
      Coordenada utmED50si;
      Coordenada utmWGS84si=null;

      //_datum_
      if (opciones.datumJSigpac == Datum.datumED50)
      {
         utmED50si = new Coordenada(Datum.datumED50, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);

         // Calculo la coordenada en el datum WGS84:
         utmWGS84si = utmED50si.CambioDeDatum(Datum.datumWGS84);
      } else
      {
         utmWGS84si = new Coordenada(Datum.datumWGS84, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);

         // Calculo la coordenada en el datum ED50:
         utmED50si = utmWGS84si.CambioDeDatum(Datum.datumED50);
      }
      
      gm.println("GLOBAL_MAPPER_SCRIPT VERSION=1.00");
      gm.println("UNLOAD_ALL");
      gm.println("LOAD_PROJECTION FILENAME=\"" + opciones.raiz + ".prj\"");
      gm.println("IMPORT FILENAME=\"" + opciones.raiz + ".jpg\" " + "TYPE=JPEG ANTI_ALIAS=NO AUTO_CONTRAST=NO CLIP_COLLAR=NO TEXTURE_MAP=NO PROJ_FILENAME=\"" + dir + File.separator + opciones.huso + ".prj\"");
      gm.println("EXPORT_RASTER FILENAME=\"" + opciones.raiz + ".ecw\" TYPE=ECW TARGET_COMPRESSION=20 GEN_WORLD_FILE=NO GEN_PRJ_FILE=NO");
                                         
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibracion para Global Mapper gms");
      return;
   }
   if (gm != null)
      gm.close();
  
   JSigpac.ImprimirLinea("\tFichero de calibracion Global Mapper .gms: "+nomfich+extension);   		
 }

 String FechaHoraERS()
 {
   String fechor;
   
   DecimalFormat nf = new DecimalFormat("00");
   String[] dia = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
   String[] mes = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
   Calendar fecha = Calendar.getInstance();
   //System.out.println("Mes: " + fecha.get(Calendar.MONTH));
   //System.out.println("Día: " + fecha.get(Calendar.DATE));
   //System.out.println("Hora: " + fecha.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE));
   //System.out.println("Agosto es el mes " + Calendar.AUGUST);
   //System.out.println("DAY_OF_WEEK " + fecha.get(Calendar.DAY_OF_WEEK));
   //System.out.println("MONTH " + fecha.get(Calendar.MONTH));
   fechor = dia[fecha.get(Calendar.DAY_OF_WEEK)-1] + " " + mes[fecha.get(Calendar.MONTH)] + " " + 
            nf.format(fecha.get(Calendar.DATE)) + " " + fecha.get(Calendar.HOUR_OF_DAY) + ":" + 
            fecha.get(Calendar.MINUTE) + ":" + fecha.get(Calendar.SECOND) + " GMT " + fecha.get(Calendar.YEAR);
   
   //System.out.println("FECHA: " + fechor);
   
   return fechor;
 }
 
 void CalibracionERS()
 {
   PrintWriter ers = null;
   String extension = ".ers";
   boolean distintos = true; // Controla si el datumJSigpac (es decir, el datum en el que el usuario 
                             // ha tecleado las coordenadas) es igual o distinto al datum con el que se 
                             // quieren generar los ficheros de calibracion.
   Datum datumCambio=null; // Valdra ED50 o WGS84 dependiendo del datumJSigpac y del datum en que se calibre. 
   String datum;
   int anchoTotal, altoTotal;
   anchoTotal = (int) Mapa.width * opciones.n_col;
   altoTotal = (int) Mapa.height * opciones.n_filas;

   if (opciones.datumJSigpac == Datum.datumED50)   
      distintos = opciones.WGS84 ? true : false;
   else if (opciones.datumJSigpac == Datum.datumWGS84)
      distintos = opciones.WGS84 ? false : true;
   else
   {
      JSigpac.ImprimirLinea(" --- No se genera fichero de calibracion ERS ---");
      return;
   }

   if (distintos)  
      datumCambio = opciones.WGS84 ? Datum.datumWGS84 : Datum.datumED50;
    
   /*  
   if (datumCambio == Datum.datumWGS84)
      JSigpac.ImprimirLinea("datumCambio es WGS84");
   else
      JSigpac.ImprimirLinea("datumCambio es ED50");
   */
   //JSigpac.ImprimirLinea("distintos="+distintos);
   
   try {
      ers = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));

      // Asigno los valores correctos a las esquinas segun el datum:
      Coordenada utmJSigpacsi;
      utmJSigpacsi = new Coordenada(opciones.datumJSigpac, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
      double xsi, ysi;
      byte hsi;
      if (distintos)
      {          
         Coordenada utmCalibracionsi;         
	 // Calculo las coordenadas en el datum WGS84:
         utmCalibracionsi = utmJSigpacsi.CambioDeDatum(datumCambio);         
         xsi = utmCalibracionsi.getX();
	 ysi = utmCalibracionsi.getY();
	 hsi = utmCalibracionsi.getHuso();	 
      } else
      {
         xsi = utmJSigpacsi.getX(); //XesqSupIzqda;
	 ysi = utmJSigpacsi.getY(); //YesqSupIzqda;
	 hsi = utmJSigpacsi.getHuso();	 
      }      

      ers.println("DatasetHeader Begin");
      ers.println("\tVersion		= \"7.1\"");
      File auxfile;
      auxfile = new File(opciones.raiz);
     
      ers.println("\tName		= \"" + auxfile.getName() + ".ers" + "\"");
      // Obtenemos la fecha y hora en el formato esperado por ERS:        
   
             
      ers.println("\tLastUpdated	= " + FechaHoraERS());
      ers.println("\tDataFile	= \"" + auxfile.getName() + ".ECW" + "\"");
      ers.println("\tDataSetType	= Translated");
      ers.println("\tDataType	= Raster");
      ers.println("\tByteOrder	= LSBFirst");
      ers.println("\tCoordinateSpace Begin");
      if (opciones.WGS84)
         datum = "WGS84";
      else 
         datum = "ED50";
      ers.println("\t\tDatum		= \"" + datum + "\"");
      ers.println("\t\tProjection	= \"NUTM" + hsi + "\"");
      ers.println("\t\tCoordinateType	= EN");
      ers.println("\t\tRotation	= 0:0:0.0");
      ers.println("\tCoordinateSpace End");
      ers.println("\tRasterInfo Begin");
      ers.println("\t\tCellType	= Unsigned8BitInteger");
      ers.println("\t\tCellInfo Begin");
      ers.println("\t\t\tXdimension	= " + opciones.reso);
      ers.println("\t\t\tYdimension	= " + opciones.reso);
      ers.println("\t\tCellInfo End");
      ers.println("\t\tNrOfLines	= " + altoTotal);
      ers.println("\t\tNrOfCellsPerLine	= " + anchoTotal);
      ers.println("\t\tRegistrationCoord Begin");            
      ers.println("\t\t\tEastings	= " + xsi);
      ers.println("\t\t\tNorthings	= " + ysi);
      ers.println("\t\tRegistrationCoord End");
      ers.println("\t\tNrOfBands	= 3");
      ers.println("\t\tBandId Begin");
      ers.println("\t\t\tValue		= \"Red\"");
      ers.println("\t\tBandId End");
      ers.println("\t\tBandId Begin");
      ers.println("\t\t\tValue		= \"Green\"");
      ers.println("\t\tBandId End");
      ers.println("\t\tBandId Begin");
      ers.println("\t\t\tValue		= \"Blue\"");
      ers.println("\t\tBandId End");
      ers.println("\t\tRegionInfo Begin");
      ers.println("\t\t\tType		= Polygon");
      ers.println("\t\t\tRegionName	= \"All\"");
      ers.println("\t\t\tSubRegion	= {");
      ers.println("\t\t\t\t0\t0");	                 
      ers.println("\t\t\t\t0\t" + altoTotal);
      ers.println("\t\t\t\t" + anchoTotal + "\t" + altoTotal);
      ers.println("\t\t\t\t" + anchoTotal + "\t0");
      ers.println("\t\t\t}");
      ers.println("\t\tRegionInfo End");
      ers.println("\tRasterInfo End");
      ers.println("DatasetHeader End");	 
      	
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero ERS");
      return;
   }
   if (ers != null)
      ers.close();
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion de ERS: "+carpeta.getName()+extension);
 }


 /*
    Mas informacion en:  http://www.trackthisout.com/Manual.htm
    Tracky map calibration format.

	A map calibration file <map>.txt stores calibration info for map picture <map>.gif or <map>.bmp. The file has the following contents (given numbers are examples):

	width  = 1024
	height = 768
	grid   = 1
	ref1x  = 358
	ref1y  = 118
	lat1   = 48.19955271
	lon1   = 6.85414643
	widthM = 17705.64

	Width and height specify the size of the map picture (in pixels). Grid specifies the grid to which this map is aligned, see supported grids. Ref1x and ref1y specify the position of the first reference point (in pixels, relative from the top-left corner of the map). Lat1 and lon1 store the absolute position of that reference point (in degrees using the WGS_84 datum). The attribute widthM specifies the area width of the map picture.

	Instead of the attribute widthM, also a second reference point can be given. The file then has the following contents:

	width  = 1024
	height = 768
	grid   = 1
	ref1x  = 358
	ref1y  = 118
	lat1   = 48.19955271
	lon1   = 6.85414643
	ref2x  = 1024
	ref2y  = 768
	lat2   = 48.09837385
	lon2   = 7.00968051
  */
  
 void CalibracionTracky()  // Solo se debería calibrar en WGS84
 {
   PrintWriter trk = null;
   String extension = ".txt";
      
   boolean distintos = true; // Controla si el datumJSigpac (es decir, el datum en el que el usuario 
                             // ha tecleado las coordenadas) es igual o distinto al datum con el que se 
                             // quieren generar los ficheros de calibracion (que en este caso es siempre WGS84)
   Datum datumCambio=null; // Valdra ED50 o WGS84 dependiendo del datumJSigpac y del datum en que se calibre (WGS84).    
   int anchoTotal, altoTotal;
   anchoTotal = (int) Mapa.width * opciones.n_col;
   altoTotal = (int) Mapa.height * opciones.n_filas;

   if (opciones.datumJSigpac == Datum.datumED50)   
      distintos = true;
   else if (opciones.datumJSigpac == Datum.datumWGS84)
      distintos = false;
   else
   {
      JSigpac.ImprimirLinea(" --- No se genera fichero de calibracion para Tracky ---");
      return;
   }

   if (distintos)  
      datumCambio = Datum.datumWGS84;       
   
   try {
      trk = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));

      // Asigno los valores correctos a las esquinas segun el datum:
      Coordenada utmJSigpacsi, utmJSigpacid;
      utmJSigpacsi = new Coordenada(opciones.datumJSigpac, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);      
      utmJSigpacid = new Coordenada(opciones.datumJSigpac, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      double latsi, lonsi, latid, lonid;
      
      if (distintos)
      {          
         Coordenada utmCalibracionsi, utmCalibracionid;         
	 // Calculo las coordenadas en el datum WGS84:
         utmCalibracionsi = utmJSigpacsi.CambioDeDatum(datumCambio);         
         latsi = utmCalibracionsi.getLat();
	 lonsi = utmCalibracionsi.getLon();
	 utmCalibracionid = utmJSigpacid.CambioDeDatum(datumCambio);         
         latid = utmCalibracionid.getLat();
	 lonid = utmCalibracionid.getLon();	 
      } else
      {      	        
         latsi = utmJSigpacsi.getLat();
	 lonsi = utmJSigpacsi.getLon();	          
         latid = utmJSigpacid.getLat();
	 lonid = utmJSigpacid.getLon();         
      }      

      trk.println("width  = " + anchoTotal);
      trk.println("height = " + altoTotal);
      trk.println("grid   = 1");
      trk.println("ref1x  = 0");
      trk.println("ref1y  = 0");
      trk.println("lat1   = " + latsi);
      trk.println("lon1   = " + lonsi);
      trk.println("ref2x  = " + anchoTotal);
      trk.println("ref2y  = " + altoTotal);
      trk.println("lat2   = " + latid);
      trk.println("lon2   = " + lonid); 
      	
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero ERS");
      return;
   }
   if (trk != null)
      trk.close();
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion de Tracky: "+carpeta.getName()+extension);
 	
 }

 /* 
    Información sobre MyMotion en: http://users.bigpond.net.au/ian_pendlebury/mymad.htm
    Algo sobre el formato de gráficos TGA: http://schmidt.devlib.org/java/pixel-image-io-libraries.html
    * Ejemplo de fichero de calibración:
      38,3076547284265 -0,62436328856549 38,4423052018715 -0,427879465023153 
  */
 void CalibracionMyMotion()
 {
   PrintWriter mym = null;
   String extension = ".txt";
   //int anchoTotal, altoTotal;
   //anchoTotal = (int) Mapa.width * opciones.n_col;
   //altoTotal = (int) Mapa.height * opciones.n_filas;	
   File aux = new File(opciones.raiz);
   String dir = aux.getParent();
   if (dir == null)
      dir = ".";
   String nomfich = dir + File.separator + "cordinates" + extension;
   //JSigpac.Traza("CalibracionMyMotion nomfich="+nomfich);
      
   try {
      mym = new PrintWriter(new BufferedWriter(
		   new FileWriter(nomfich)));
      
      Coordenada utmED50ii=null;
      Coordenada utmWGS84ii=null;
      Coordenada utmED50sd=null;
      Coordenada utmWGS84sd=null;

      //_datum_
      if (opciones.datumJSigpac == Datum.datumED50)
      {
      	 // Esquina inferior izquierda:
         utmED50ii = new Coordenada(Datum.datumED50, XesqInfIzqda, YesqInfIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         // Calculo la coordenada en el datum WGS84:
         utmWGS84ii = utmED50ii.CambioDeDatum(Datum.datumWGS84);
         // Esquina superior derecha:
         utmED50sd = new Coordenada(Datum.datumED50, XesqSupDer, YesqSupDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         // Calculo la coordenada en el datum WGS84:
         utmWGS84sd = utmED50sd.CambioDeDatum(Datum.datumWGS84);
      } else
      {
         utmWGS84ii = new Coordenada(Datum.datumWGS84, XesqInfIzqda, YesqInfIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmWGS84sd = new Coordenada(Datum.datumWGS84, XesqSupDer, YesqSupDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      }
      
      mym.println(utmWGS84ii.getLat() + " " + utmWGS84ii.getLon() + " " + utmWGS84sd.getLat() + " " + utmWGS84sd.getLon());
                                         
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibracion para MyMotion cordinates.txt");
      if (mym != null)
         mym.close();
      return;
   }
   if (mym != null)
      mym.close();
  
   JSigpac.ImprimirLinea("\tFichero de calibracion MyMotion: cordinates.txt");   		
 }


 /* 
    Información sobre TomTom en:      
      http://foro.todopocketpc.com/showpost.php?p=1394932&postcount=1635

	En cuanto a los archivos del tomtom, son 9 lineas:
	1 Nombre del archivo de imagen
	2 Longitud punto 1
	3 Latitud punto 1
	4 Longitud Punto 2
	5 Latitud punto 2
	6 zoom minimo (yo pongo 0 y 60 poniendo un poco mas de 60 si la imagen es grande tambien esta bien.)
	7 zoom maximo
	8 tamaño en pixeles ancho
	9 tamaño en pixeles alto

	* Ejemplo de fichero de calibración:
	Valverde_0-0_.jpg
	-1.8858923351450714
	41.99458466213703
	-1.8706040115863827
	41.98521259845109
	0
	60
	1280
	1024
          
 */
 void CalibracionTomTom()
 {
   PrintWriter tom = null;
   String extension = ".sat";
      
   try {
      tom = new PrintWriter(new BufferedWriter(
		   new FileWriter(opciones.raiz+extension)));
      
      Coordenada utmED50si=null;
      Coordenada utmWGS84si=null;
      Coordenada utmED50id=null;
      Coordenada utmWGS84id=null;

      //_datum_
      if (opciones.datumJSigpac == Datum.datumED50)
      {
      	 JSigpac.Traza("opciones.datumJSigpac == Datum.datumED50");
      	 // Esquina superior izquierda:
      	 JSigpac.Traza("Esquina superior izquierda: " + XesqSupIzqda + ", " + YesqSupIzqda);
         utmED50si = new Coordenada(Datum.datumED50, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         // Calculo la coordenada en el datum WGS84:
         utmWGS84si = utmED50si.CambioDeDatum(Datum.datumWGS84);
         JSigpac.Traza("WGS84: " + utmWGS84si.getX() + ", " + utmWGS84si.getY());
         // Esquina inferior derecha:
         JSigpac.Traza("Esquina inferior derecha: " + XesqInfDer + ", " + YesqInfDer);
         utmED50id = new Coordenada(Datum.datumED50, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
         // Calculo la coordenada en el datum WGS84:
         utmWGS84id = utmED50id.CambioDeDatum(Datum.datumWGS84);
         JSigpac.Traza("WGS84: " + utmWGS84id.getX() + ", " + utmWGS84id.getY());
      } else
      {
         utmWGS84si = new Coordenada(Datum.datumWGS84, XesqSupIzqda, YesqSupIzqda, 0, 
			   (byte)opciones.huso.intValue(), true);
         utmWGS84id = new Coordenada(Datum.datumWGS84, XesqInfDer, YesqInfDer, 0, 
			   (byte)opciones.huso.intValue(), true);
      }
            
      int anchoTotal, altoTotal;
      anchoTotal = (int) Mapa.width * opciones.n_col;
      altoTotal = (int) Mapa.height * opciones.n_filas;
      // Comenzamos a escribir en el fichero:
      File auxfile;
      auxfile = new File(opciones.raiz + ".jpg");
      tom.println(auxfile.getName());
      tom.println(utmWGS84si.getLon());
      tom.println(utmWGS84si.getLat());
      tom.println(utmWGS84id.getLon());
      tom.println(utmWGS84id.getLat());
      tom.println("0");
      tom.println("60");
      tom.println(anchoTotal);
      tom.println(altoTotal);      
                                         
   } catch (IOException ioe) {
      JSigpac.ImprimirLinea("Error al intentar crear el fichero de calibracion para TomTom sat");
      if (tom != null)
         tom.close();
      return;
   }
   if (tom != null)
      tom.close();
  
   File carpeta = new File(opciones.raiz);
   JSigpac.ImprimirLinea("\tFichero de calibracion de TomTom: "+carpeta.getName()+extension);  		
 }
 
 public String ObtenerURL(double xMin, double yMax, double xMax, double yMin)
 {
   return ObtenerURL(xMin, yMax, xMax, yMin, false);	
 }

 String EscalaTopograficoExtremadura(String escala)
 {
    if (escala.equals("MTN_SIGPAC"))                                    
        return "topografico:tematico";                  
    else if (escala.equals("MTN_2000"))          	             
        return "topografico:tematico";                     
    else if (escala.equals("MTN_1250"))          	             
        return "topografico:tematico";                    
    else if (escala.equals("MTN_200"))          	             
        return "topografico:topografico200";                
    else if (escala.equals("MTN_25"))          	             
        return "topografico:topografico50"; 	
    else
        return "topografico:topografico50";	
 }

 String EscalaTopograficoMadrid(String escala)
 {
    if (escala.equals("MTN_SIGPAC"))                                    
        return "mtn2000";                  
    else if (escala.equals("MTN_2000"))          	             
        return "mtn2000";                                           
    else if (escala.equals("MTN_200"))          	             
        return "mtn200";                    
    else
        return "mtn200";	
 }
 
 String EscalaTopograficoIberpix(String escala)
 {
    if (escala.equals("MTN_25"))    
       return "mtn25";
    else if (escala.equals("MTN_200"))          	             
       return "mtn50";                                        	             
    else if (escala.equals("MTN_1250"))          	             
       return "mtn200";
    else if (escala.equals("MTN_2000") || escala.equals("MTN_SIGPAC"))          	             
       return "millon";
    else   
       return "mtn50";                            	
 }
           
 String EscalaTopograficoMurcia(String escala)
 {
    if (escala.equals("MTN_25"))    
       return "mtn25";
    else                                         	             
       return "ign200m";                            	
 }
 
 public String ObtenerURL(double xMin, double yMax, double xMax, double yMin, boolean vistaPajaro)
 {   
   String direccion=null, utm;
   String dimensiones=null;
   int serv;
   double xMinS, yMaxS, xMaxS, yMinS; // Valores correctos segun el datum del JSigPac y el datum del Servidor
   Coordenada izqda_ori, izqda_fin, dcha_ori, dcha_fin;
         
   byte husoActual;
   husoActual = (byte)opciones.huso.intValue();      
         
   serv = JSigpac.servidor.Id();
   
   //JSigpac.Traza("xMin="+xMin+"  yMin="+yMin+"    xMax="+xMax+"  yMax="+yMax);
   // Ahora hay que ver si las coordenadas que ha dado el usuario están en el mismo datum
   // en el que trabaja el servidor de mapas correspondiente:
   //JSigpac.Traza("datumJSigpac="+opciones.datumJSigpac.miString() +"   datumServidor="+opciones.datumServidor.miString());
   
   if (opciones.datumJSigpac == opciones.datumServidor)
   {
      xMinS = xMin;
      yMaxS = yMax;
      xMaxS = xMax;
      yMinS = yMin;
   } else
   {
      izqda_ori = new Coordenada(opciones.datumJSigpac, xMin, yMax, 0, husoActual, true);
      dcha_ori = new Coordenada(opciones.datumJSigpac, xMax, yMin, 0, husoActual, true);
      izqda_fin = izqda_ori.CambioDeDatum(opciones.datumServidor);
      dcha_fin = dcha_ori.CambioDeDatum(opciones.datumServidor);
      xMinS = izqda_fin.getX();
      yMaxS = izqda_fin.getY();
      xMaxS = dcha_fin.getX();
      yMinS = dcha_fin.getY();
   }   
   //JSigpac.Traza("xMinS="+xMinS+"  yMinS="+yMinS+"    xMaxS="+xMaxS+"  yMaxS="+yMaxS);
   
   // Variables necesarias para hacer las peticiones en los visores del Sigpac con versión superior a la 4.0:
   int r;
   long vi, vj;
   //long vi2, vj2;
   
   switch (serv)
   {
    	case Servidor.NACIONAL:  // Nacional - España
    	case Servidor.CyL:  // Castilla y León
    	case Servidor.GALICIA: // Galicia
    	case Servidor.ASTURIAS:  // Asturias
    	case Servidor.PAISVASCO:  // País Vasco
    	case Servidor.ARAGON:  // Aragon
    	case Servidor.MANCHA:  // Castilla La Mancha
    	case Servidor.MURCIA:  // Murcia
    	case Servidor.CANARIAS_SIGPAC:  // Canarias
    	case Servidor.MADRID: // Madrid
    	case Servidor.EXTREMADURA: // Extremadura   
    	case Servidor.IBERPIX: // IGN-Iberpix  	
    	    r = (int) (opciones.reso * 1000);
            vi = (long)(((xMinS * 1000) / (Mapa.width * r)) + 0.0001); //Math.round( (xMin * 1000) / (Mapa.width * r) );
            vj = (long)(((yMaxS * 1000) / (Mapa.height * r)) + 0.0001); //Math.round( (yMax * 1000) / (Mapa.height * r) );
            vj--; // Si no hacemos esto, el mapa obtenido no sale bien calibrado.... habrá que ver porqué.... pa qué, si funciona, mejor no tocar.            
            
            if (opciones.ortofoto)
            {
               if (serv == Servidor.MANCHA)  
                  utm = "n=ortofotos2006";
               else if (serv == Servidor.IBERPIX)
                  utm = "n=pnoa";
               else
                  utm = "n=ortofotos";
            } else 
            {
               if (serv == Servidor.MADRID)  // Madrid      
               	  utm = "n=topografico:" + EscalaTopograficoMadrid(opciones.escala);
               else if (serv == Servidor.EXTREMADURA) // Extremadura
                  utm = "n=" + EscalaTopograficoExtremadura(opciones.escala);               
               else if (serv == Servidor.CyL || serv == Servidor.ARAGON || serv == Servidor.MANCHA)
                  utm = "n=topografico:" + opciones.escala;
               else if (serv == Servidor.MURCIA) // Murcia                              	  
                  utm = "n=" + EscalaTopograficoMurcia(opciones.escala);
               else if (serv == Servidor.IBERPIX) // IGN-Iberpix                              	  
                  utm = "n=mapa_" + EscalaTopograficoIberpix(opciones.escala);
               else	            	
                  utm = "n=topografico-" + opciones.escala;
            }
            
            // En el caso del servidor de Castilla y León, para la vista pájaro podemos seguir utilizando
            // el antiguo visor:
            if (vistaPajaro)
            { 
               if ((serv == Servidor.CyL || serv == Servidor.PAISVASCO))
               {
                  Mapa.anchoVistaPajaro = 621;
    	          Mapa.altoVistaPajaro = 418;
    	          if (opciones.ortofoto)
                     utm = "Name=ORTOFOTOS&Tpc=0&x1=";
                  else             
                     utm = "Name=TOPOGRAFICO:" + opciones.escala + "&Tpc=0&x1=";   
            
                  String servidorRegional;
                  if (serv == Servidor.CyL)
                     servidorRegional = Servidor.URLServidor("CL3");
                  else if (serv == Servidor.PAISVASCO)
                     servidorRegional = Servidor.URLServidor("EU3");
                  else
                     servidorRegional = "";
                  
                  utm = utm + df.format(xMinS) + "&y1=" + df.format(yMaxS) + "&x2=" + df.format(xMaxS) + "&y2=" + df.format(yMinS);
                  direccion = servidorRegional + "aspimagedispatcher.aspx?function=getutmbitmap&" +
                              utm + "&Width=618&Height=421&Zone=" + husoActual;
               } else // Usamos PNOA para el resto:              
               { 
               	  Mapa.anchoVistaPajaro = 600;
    	          Mapa.altoVistaPajaro = 600;  
    	          dimensiones = "&WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro;    	      
    	          utm = "REQUEST=GetMap&VERSION=1.1.0&SERVICE=WMS&SRS=EPSG:230" + opciones.huso + 
    	                "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS + dimensiones +
    	                "&LAYERS=pnoa&STYLES=default&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=TRUE"; //&EXCEPTIONS=application/vnd.ogc.se_inimage
    	        	     	        	    
    	          direccion = JSigpac.servidor.URLServidor(Servidor.PNOA) + utm;  
               }                                                
            } else
            {
               if (serv == Servidor.PAISVASCO || serv == Servidor.NACIONAL || serv == Servidor.GALICIA || serv == Servidor.IBERPIX)
                  utm = utm + ";z=" + husoActual + ";r=" + r + ";i=" + vi + ";j=" + vj + ".jpg";
               else
                  utm = utm + "&z=" + husoActual + "&r=" + r + "&i=" + vi + "&j=" + vj;
               direccion = JSigpac.servidor.Url() + utm; //JSigpac.servidor + utm;
            }
            //JSigpac.ImprimirLinea("direccion="+direccion);
            break;             
        case Servidor.RECINTOS:
        case Servidor.PARCELAS:
            if (vistaPajaro)
    	    {    	       	       
    	       Mapa.anchoVistaPajaro = 600;
    	       Mapa.altoVistaPajaro = 600;
    	    } else
    	    {
    	       Mapa.anchoVistaPajaro = 256;
    	       Mapa.altoVistaPajaro = 256;
    	    }
    	    String layer = serv == Servidor.RECINTOS ? "RECINTO" : "PARCELA";
    	    dimensiones = "&WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro;    	      
    	    utm = "REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&SRS=EPSG:230" + opciones.huso + 
    	          "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS + dimensiones +
    	          "&LAYERS=" + layer + "&STYLES=Default&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=TRUE"; //&EXCEPTIONS=application/vnd.ogc.se_inimage
    	        	     	        	    
    	    direccion = JSigpac.servidor.Url() + utm;              
            break;                       
        case Servidor.PNOA:
            if (vistaPajaro)
    	    {    	       	       
    	       Mapa.anchoVistaPajaro = 600;
    	       Mapa.altoVistaPajaro = 600;
    	    } else
    	    {
    	       Mapa.anchoVistaPajaro = 256;
    	       Mapa.altoVistaPajaro = 256;
    	    }
    	    dimensiones = "&WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro;    	      
    	    utm = "REQUEST=GetMap&VERSION=1.1.0&SERVICE=WMS&SRS=EPSG:230" + opciones.huso + 
    	          "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS + dimensiones +
    	          "&LAYERS=pnoa&STYLES=default&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=TRUE"; //&EXCEPTIONS=application/vnd.ogc.se_inimage
    	        	     	        	    
    	    direccion = JSigpac.servidor.Url() + utm;  
            //REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&SRS=EPSG:25830&BBOX=350000,4599000,360000,4609000&WIDTH=5000&HEIGHT=5000&LAYERS=pnoa&STYLES=default&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=TRUE&EXCEPTIONS=application/vnd.ogc.se_inimage
            break;            
        case Servidor.CyL_v3: // Antiguo visor de Castilla y León    	    	   	
    	case Servidor.EUS_v3: // Antiguo visor del País Vasco   	    	    	    	    	
    	    Mapa.anchoVistaPajaro = 621;
    	    Mapa.altoVistaPajaro = 418;
    	    if (opciones.ortofoto)
               utm = "Name=ORTOFOTOS&Tpc=0&x1=";
            else             
               utm = "Name=TOPOGRAFICO:" + opciones.escala + "&Tpc=0&x1=";   
            
            utm = utm + df.format(xMinS) + "&y1=" + df.format(yMaxS) + "&x2=" + df.format(xMaxS) + "&y2=" + df.format(yMinS);
            direccion = JSigpac.servidor.Url() + utm + "&Width=618&Height=421&Zone=" + husoActual;
            //JSigpac.ImprimirLinea("direccion="+direccion);
    	    break; 
    	case Servidor.CANARIAS_SITCAN:   // Canarias SITCAN
    	    if (vistaPajaro)
    	    {    	       	       
    	       Mapa.anchoVistaPajaro = 600;
    	       Mapa.altoVistaPajaro = 600;
    	    } else
    	    {
    	       Mapa.anchoVistaPajaro = 256;
    	       Mapa.altoVistaPajaro = 256;
    	    }
    	    if (opciones.ortofoto)
    	       utm = "ecw=OrtoExpress&";
    	    else
    	       utm = "ecw=Carto5000&";
    	       
    	    utm = utm + "sizex=" + Mapa.anchoVistaPajaro + "&sizey=" + Mapa.altoVistaPajaro +
    	          "&worldtlx=" + df.format(xMinS) + "&worldtly=" + df.format(yMaxS) + 
    	          "&worldbrx=" + df.format(xMaxS) + "&worldbry=" + df.format(yMinS) +
    	          "&fill=000000&type=jpeg&quality=94";    	        	    
    	    direccion = JSigpac.servidor.Url() + utm;   
    	    break;
    	case Servidor.CANARIAS_IDECAN:   // Canarias IDECAN   
    	    {
    	    String base; 	    
    	    if (opciones.ortofoto)      	      	
    	       base = "OrtoExpress?REQUEST=GetMap&SERVICE=WMS&VERSION=1.1.1&SRS=EPSG:4326&LAYERS=ortoexpress&STYLES=&FORMAT=image/jpeg&BBOX=";
    	    else    	    
    	       base = "MT5?REQUEST=GetMap&SERVICE=WMS&VERSION=1.1.1&SRS=EPSG:4326&LAYERS=WMS_MT5&STYLES=&FORMAT=image/png&BBOX=";
    	        	   
    	    Coordenada c_inf_izq, c_sup_der;    	   
    	    //JSigpac.ImprimirLinea("xMin="+xMinS+"  yMin="+yMinS+"   xMax="+xMaxS+"  yMax="+yMaxS);
    	    //_datum_
    	    	
    	    //c_inf_izq = new Coordenada(Datum.datumED50, xMin, yMin, 0, husoActual, true);
	    //c_sup_der = new Coordenada(Datum.datumED50, xMax, yMax, 0, husoActual, true);	        
	        
	    c_inf_izq = new Coordenada(opciones.datumJSigpac, xMinS, yMinS, 0, husoActual, true);
	    c_inf_izq = c_inf_izq.CambioDeDatum(Datum.datumWGS84);
	    c_sup_der = new Coordenada(opciones.datumJSigpac, xMaxS, yMaxS, 0, husoActual, true);
	    c_sup_der = c_sup_der.CambioDeDatum(Datum.datumWGS84);
	    //JSigpac.ImprimirLinea(xMinS+","+yMinS+"   "+xMaxS+","+yMaxS);
	        
	    if (vistaPajaro)
    	    {    	           	       
    	       Mapa.anchoVistaPajaro = 512;
    	       Mapa.altoVistaPajaro = 512;    	           
    	    } else
    	    {    	           
    	       Mapa.anchoVistaPajaro = 256;
    	       Mapa.altoVistaPajaro = 256;
    	    }
    	    dimensiones = "&WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro;
    	    /* Coordenadas UTM en ED50:   	        
    	        utm = "REQUEST=GetMap&VERSION=1.1.0&SERVICE=WMS&SRS=EPSG:230" + 
    	              opciones.huso + "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS +
    	              dimensiones + "&LAYERS=Todas&STYLES=default&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=FALSE";
    	     */
    	    /*  Coordenadas geográficas en WGS84: */
    	    utm = base + c_inf_izq.getLon() + "," + c_inf_izq.getLat() + "," + c_sup_der.getLon() + "," + c_sup_der.getLat() +
    	          dimensiones + "&reaspect=false";
    	      
    	    /*
    	        utm = "REQUEST=GetMap&VERSION=1.1.0&SERVICE=WMS&STYLES=sombreado&REASPECT=FALSE&FORMAT=JPEG&LAYERS=Todas&SRS=EPSG:230" + 
    	              opciones.huso + "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS +
    	              dimensiones;
    	     */
    	    direccion = JSigpac.servidor.Url() + utm;
    	        //JSigpac.ImprimirLinea("direccion="+direccion);
    	    }
    	    break;    
    	/* Ahora hay un visor del SigPac para Madrid en lugar del de TresCantos:
    	case 10:   // Madrid
    	    Mapa.anchoVistaPajaro = 612;
    	    Mapa.altoVistaPajaro = 442;
    	    if (opciones.ortofoto)    	    
    	       utm = "tipo=0&Xmin=";    	       
    	    else    	    
    	       utm = "tipo=5&Xmin=";
    	    utm = utm + df.format(xMinS) + "&Ymin=" + df.format(yMinS) + "&Xmax=" + df.format(xMaxS) + 
    	          "&Ymax=" + df.format(yMaxS) + "&PixAncho=612&PixAlto=442";
    	    direccion = JSigpac.servidor.Url() + utm;
    	    //JSigpac.ImprimirLinea("direccion="+direccion);
    	    break;
    	 */
    	case Servidor.RIOJA:   // La Rioja:  Es un caso especial porque a pesar de utilizar ahora el visor con versión 5.1
    	           //            todavía está accesible la cartografía utilizando el formato de petición original
    	           //            del SigPac (y así poder obtener la "Vista pájaro" al dar al botón "Ver Mapa".    	     
    	     
    	     if (vistaPajaro)
    	     {
    	     	// Utilizamos el formato original de petición del SigPac:
    	     	Mapa.anchoVistaPajaro = 621;
    	        Mapa.altoVistaPajaro = 418;
    	        if (opciones.ortofoto)
                   utm = "Name=ORTOFOTOS&Tpc=0&x1=";
                else             
                   utm = "Name=TOPOGRAFICO:" + opciones.escala + "&Tpc=0&x1=";   
            
                utm = utm + df.format(xMinS) + "&y1=" + df.format(yMaxS) + "&x2=" + df.format(xMaxS) + "&y2=" + df.format(yMinS);
                direccion = JSigpac.servidor.Url() + "imagenes/aspimagedispatcher.aspx?function=getutmbitmap&" +
                                utm + "&Width=618&Height=421&Zone=" + husoActual;    	     	
    	     } else  // Utilizamos el formato de petición de la versión del visor 4.0 y superiores 
    	     {
    	     	r = (int) (opciones.reso * 1000);
                vi = (long)((xMinS * 1000) / (Mapa.width * r)); //Math.round( (xMin * 1000) / (Mapa.width * r) );
                vj = (long)((yMaxS * 1000) / (Mapa.height * r)); //Math.round( (yMax * 1000) / (Mapa.height * r) );
                vj--; // Si no hacemos esto, el mapa obtenido no sale bien calibrado.... habrá que ver porqué.... pa qué, si funciona, mejor no tocar.
                if (opciones.ortofoto)
                   utm = "baldosas/tilesserver.aspx?n=ortofotos"; 
                else
                   utm = "baldosas/tilesserver.aspx?n=topografico-" + opciones.escala;
                utm = utm + "&z=" + husoActual + "&r=" + r + "&i=" + vi + "&j=" + vj;
                direccion = JSigpac.servidor.Url() + utm; //JSigpac.servidor + utm;    	     	
    	     } 
    	     //JSigpac.ImprimirLinea("direccion="+direccion);        	           
    	    break;       
    	case Servidor.IGN:   // IGN    	    
    	    if (opciones.ortofoto)  
    	    {  	
    	       direccion = null;    	       
    	       JSigpac.ImprimirLinea(" ---  No hay ortofotos en el servidor del IGN  --- ");
    	    } else    	    
    	    {
    	    	Coordenada c_inf_izq, c_sup_der;    	   
    	    	//JSigpac.ImprimirLinea("xMin="+xMinS+"  yMin="+yMinS+"   xMax="+xMaxS+"  yMax="+yMaxS);
    	    	//_datum_
    	    	
    	    	//c_inf_izq = new Coordenada(Datum.datumED50, xMin, yMin, 0, husoActual, true);
	        //c_sup_der = new Coordenada(Datum.datumED50, xMax, yMax, 0, husoActual, true);	        
	        
	        c_inf_izq = new Coordenada(opciones.datumJSigpac, xMinS, yMinS, 0, husoActual, true);
	        c_inf_izq = c_inf_izq.CambioDeDatum(Datum.datumWGS84);
	        c_sup_der = new Coordenada(opciones.datumJSigpac, xMaxS, yMaxS, 0, husoActual, true);
	        c_sup_der = c_sup_der.CambioDeDatum(Datum.datumWGS84);
	        //JSigpac.ImprimirLinea(xMinS+","+yMinS+"   "+xMaxS+","+yMaxS);
	        
	        if (vistaPajaro)
    	        {    	           	       
    	           Mapa.anchoVistaPajaro = 512;
    	           Mapa.altoVistaPajaro = 512;    	           
    	        } else
    	        {    	           
    	           Mapa.anchoVistaPajaro = (int)Mapa.width;
    	           Mapa.altoVistaPajaro = (int)Mapa.height;
    	        }
    	        dimensiones = "&WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro;
    	        /* Coordenadas UTM en ED50:   	        
    	        utm = "REQUEST=GetMap&VERSION=1.1.0&SERVICE=WMS&SRS=EPSG:230" + 
    	              opciones.huso + "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS +
    	              dimensiones + "&LAYERS=Todas&STYLES=default&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=FALSE";
    	        */
    	        /*  Coordenadas geográficas en ED50: */
    	        utm = "REQUEST=GetMap&VERSION=1.1.0&SERVICE=WMS&SRS=EPSG:4326&BBOX=" + c_inf_izq.getLon() + "," + c_inf_izq.getLat() + "," + c_sup_der.getLon() + "," + c_sup_der.getLat() +
    	              dimensiones + "&LAYERS=Todas&STYLES=default&REASPECT=TRUE&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=FALSE";
    	        
    	        /*
    	        utm = "REQUEST=GetMap&VERSION=1.1.0&SERVICE=WMS&STYLES=sombreado&REASPECT=FALSE&FORMAT=JPEG&LAYERS=Todas&SRS=EPSG:230" + 
    	              opciones.huso + "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS +
    	              dimensiones;
    	        */
    	        direccion = JSigpac.servidor.Url() + utm;
    	        //JSigpac.ImprimirLinea("direccion="+direccion);
    	    }
    	    break;
    	case Servidor.ANDALUCIA:   // Andalucía    	    
    	    utm = "servlet/wms?bbox=" + df.format(xMinS) + "," + df.format(yMinS) + "," + 
    	          df.format(xMaxS) + "," + df.format(yMaxS) + "&Format=image/jpeg&request=GetMap&layers=";
    	    
    	    if (vistaPajaro)
    	    {
    	       dimensiones = "&width=500&height=500&srs=EPSG:0";	       
    	       Mapa.anchoVistaPajaro = 512;
    	       Mapa.altoVistaPajaro = 512;
    	    } else
    	    {
    	       dimensiones = "&width=512&height=512&srs=EPSG:0";
    	       Mapa.anchoVistaPajaro = 512;
    	       Mapa.altoVistaPajaro = 512;
    	    }
    	    if (opciones.ortofoto)  
    	    {        	       
    	       if (opciones.reso < 1.0D)
    	          utm = utm + "ORTOFOTO" + dimensiones;
    	       else  	    
    	          utm = utm + "ORTOFOTO_60000" + dimensiones;     	          	       
    	    } else  
    	       utm = utm + "400_ICA" + dimensiones;   	    
    	     	       
    	    direccion = JSigpac.servidor.Url() + utm;
    	    break;
    	case Servidor.NAVARRA:   // Navarra
    	    if (vistaPajaro)
    	    {
    	       Mapa.anchoVistaPajaro = 911; //500
    	       Mapa.altoVistaPajaro = 559;  //280
    	    } else {
    	       Mapa.anchoVistaPajaro = 911;
    	       Mapa.altoVistaPajaro = 559;
    	    }	
    	    if (opciones.ortofoto)    	       
    	       utm = "tipo=0,100&fondo=&Xmin=";
    	    else    	       
    	       utm = "tipo=0,100&fondo=&Xmin=";
    	    utm = utm + df.format(xMinS) + "&Ymin=" + df.format(yMinS) + "&Xmax=" + df.format(xMaxS) + 
    	          "&Ymax=" + df.format(yMaxS) + "&PixAncho=" + Mapa.anchoVistaPajaro + "&PixAlto=" + Mapa.altoVistaPajaro;
    	    direccion = JSigpac.servidor.Url() + utm;   
    	    break;
    	case Servidor.NAVARRA1950:   // Ortofotos de Navarra de 1950
    	    if (vistaPajaro)
    	    {
    	       Mapa.anchoVistaPajaro = 500; //500
    	       Mapa.altoVistaPajaro = 400;  //280
    	    } else {
    	       Mapa.anchoVistaPajaro = 500;
    	       Mapa.altoVistaPajaro = 280;
    	    }	
    	    if (opciones.ortofoto)
    	       utm = "tipo=0&Xmin=";
    	    else
    	       utm = "tipo=8&Xmin=";
    	    utm = utm + df.format(xMinS) + "&Ymin=" + df.format(yMinS) + "&Xmax=" + df.format(xMaxS) + 
    	          "&Ymax=" + df.format(yMaxS) + "&PixAncho=" + Mapa.anchoVistaPajaro + "&PixAlto=" + Mapa.altoVistaPajaro;
    	    direccion = JSigpac.servidor.Url() + utm;   
    	    break;	
    	case Servidor.NAVARRA1000:   // Navarra ortofoto 1:1000
    	    if (vistaPajaro)
    	    {
    	       Mapa.anchoVistaPajaro = 557; //500
    	       Mapa.altoVistaPajaro = 404;  //280
    	    } else {
    	       Mapa.anchoVistaPajaro = 557;
    	       Mapa.altoVistaPajaro = 404;
    	    }	
    	    if (opciones.ortofoto)    	       
    	       utm = "tipo=0,104&fondo=&Xmin=";
    	    else    	       
    	       utm = "tipo=0,104&fondo=&Xmin=";
    	    utm = utm + df.format(xMinS) + "&Ymin=" + df.format(yMinS) + "&Xmax=" + df.format(xMaxS) + 
    	          "&Ymax=" + df.format(yMaxS) + "&PixAncho=" + Mapa.anchoVistaPajaro + "&PixAlto=" + Mapa.altoVistaPajaro;
    	    direccion = JSigpac.servidor.Url() + utm;   
    	    break;    	      	        
    	case Servidor.CVALENCIANA:    // Generalitat Valenciana
    	    if (vistaPajaro)
    	    {    	       	       
    	       Mapa.anchoVistaPajaro = 768;
    	       Mapa.altoVistaPajaro = 768;
    	    } else
    	    {    	       
    	       Mapa.anchoVistaPajaro = 256;
    	       Mapa.altoVistaPajaro = 256;
    	    }   	   	        	    
    	    utm = "cgi-bin/mapserv.exe?MAP=mapfiles/orto.map&VERSION=1.1.1&REQUEST=GetMap&SRS=EPSG:23030&LAYERS=Ortofoto,Satelite&STYLES=&FORMAT=image/jpeg&TRANSPARENT=TRUE&";
    	    utm = utm + "WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro + 
    	          "&BBOX=" + df.format(xMinS) + "," + df.format(yMinS) + "," + df.format(xMaxS) + "," + df.format(yMaxS);
    	    
    	    direccion = JSigpac.servidor.Url() + utm;
    	    break;
    	case Servidor.ARAGON_SITAR:    // Aragón SITAR    	        	    
    	    if (vistaPajaro)
    	    {    	       	       
    	       Mapa.anchoVistaPajaro = 768;
    	       Mapa.altoVistaPajaro = 768;
    	    } else
    	    {    	       
    	       Mapa.anchoVistaPajaro = 256;
    	       Mapa.altoVistaPajaro = 256;
    	    }   	        	    
    	    utm = "SRS=EPSG:230" + opciones.huso + "&Layers=Ortofoto&FORMAT=JPEG&TRANSPARENT=TRUE&BGCOLOR=0x000000&service=WMS&request=getmap&ServiceName=AragonBase_WMS&version=1.1.1&STYLES=&" + 
    	          "WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro +
    	          "&BBOX=" + df.format(xMinS) + "," + df.format(yMinS) + "," + df.format(xMaxS) + "," + df.format(yMaxS);                                                   

    	    direccion = JSigpac.servidor.Url() + utm;
    	    break;
    	case Servidor.IDEANDALORTO:
            if (vistaPajaro)
    	    {    	       	       
    	       Mapa.anchoVistaPajaro = 600;
    	       Mapa.altoVistaPajaro = 600;
    	    } else
    	    {
    	       Mapa.anchoVistaPajaro = 256;
    	       Mapa.altoVistaPajaro = 256;
    	    }
    	    dimensiones = "&WIDTH=" + Mapa.anchoVistaPajaro + "&HEIGHT=" + Mapa.altoVistaPajaro;    	      
    	    utm = "LAYERS=OCA10_2009&TRANSPARENT=false&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&STYLES=&EXCEPTIONS=application/vnd.ogc.se_inimage&FORMAT=image/jpeg&SRS=EPSG:230" + opciones.huso + 
    	          "&BBOX=" + xMinS + "," + yMinS + "," + xMaxS + "," + yMaxS + dimensiones;
    	        	     	        	    
    	    direccion = JSigpac.servidor.Url() + utm;  
            //REQUEST=GetMap&VERSION=1.1.1&SERVICE=WMS&SRS=EPSG:25830&BBOX=350000,4599000,360000,4609000&WIDTH=5000&HEIGHT=5000&LAYERS=pnoa&STYLES=default&FORMAT=image/jpeg&BGCOLOR=0xFFFFFF&TRANSPARENT=TRUE&EXCEPTIONS=application/vnd.ogc.se_inimage
            break;            
    	default: JSigpac.ImprimirLinea(" -*-  Servidor no catalogado -*- ");
   }
   JSigpac.Traza("direccion="+direccion);
   return direccion;
 }


 public boolean ObtenerUnicoCuadrante()
 {
    return ObtenerCuadrante(-1, -1);
 }

 public boolean ObtenerCuadrante(int i, int j)
 {
    return ObtenerCuadrante(i, j, null);
 }

 public boolean ObtenerCuadrante(int i, int j, Monitor monitor)
 {
   double x1=0,y1=0,x2=0,y2=0;
   double altura;
   String nomFich=null;
   File aux;   

   if (opciones.n_filas == 1 && opciones.n_col == 1)
      nomFich = opciones.raiz;
   else
      nomFich = opciones.raiz+i+"_"+j;
   aux = new File(nomFich);
   //System.out.println("_cc_ Estamos en ObtenerCuadrante ("+i+","+j+")    entornoGrafico="+JSigpac.entornoGrafico);
   if (JSigpac.entornoGrafico == false)
   {            	      	  
      File aux2 = new File(aux.getPath() + ".jpg");
      //System.out.println("aux="+aux.toString());
      if (aux2.exists())
      {
         byte[] b;      	   
      	 //if (noPreguntarMas && noDescargar)                   
         //   return false;               
         if (!opciones.noPreguntarMas)
         {
            opciones.noPreguntarMas = true;
            b = new byte[80];
            //System.out.print("\n\tEl fichero \"" + aux2.getName() + "\" ya existe.\n\tQuiere descargarlo de nuevo (S/N)?");
            System.out.print("\n\tExisten ya ficheros descargados.\n\tQuiere sobreescribirlos (S/N)? ");
            try {
              System.in.read(b);
              //System.out.println("Primer read leidos="+leidos+"   b[0]="+b[0]);
              //System.out.println("N=" + ((byte)'N'));
              //System.out.println("leidos="+leidos+"   b[0]="+b[0]);   
              if (b[0] == 'n' || b[0] == 'N')
                 opciones.sobreescribir = false;
                 //noDescargar = true;          
              else if (b[0] == 's' || b[0] == 'S')
                 opciones.sobreescribir = true;
                 //noDescargar = false;
              else
                 opciones.sobreescribir = true;
                 //noDescargar = false;
             /*
              System.out.print("\n\tAplicar esa respuesta al resto de ficheros (S/N)?");                 
              leidos = System.in.read(b);
              System.out.println("Segundo read leidos="+leidos+"   b[0]="+b[0]);
              if (b[0] == 'n' || b[0] == 'N')
                 noPreguntarMas = false;          
              else if (b[0] == 's'|| b[0] == 'S')
                 noPreguntarMas = true; 
              else
                 noPreguntarMas = false;
              */
              //if (noDescargar)
              //System.out.println("sobreescribir="+sobreescribir);
              if (!opciones.sobreescribir)
              {
              	 if (monitor != null)
              	    monitor.CambiarValor(i, j, true);
                 return true;
              }                
            } catch (IOException ioe) {
              System.out.println("Error al leer de la consola");
            }  
            System.out.print("");                    	      	
         } else
         {
            if (!opciones.sobreescribir)
            {
            	if (monitor != null)
                   monitor.CambiarValor(i, j, true);
                return true;
            }
         }
      }
   } 
   
   // En el siguiente "if" he puesto la comprobacion de "monitor != null" porque cuando fallaba la descarga de algun cuadrante
   // y se reintentaba la descarga, en caso de ser el cuadrante (0,0) pues volvia a imprimir todo el mensaje con el servidor, las esquinas, etc...   
   if (i==0 && j==0 && monitor != null)
   {
      //_xx_new_ "reso" lo metemos en opciones.reso:
      //double reso =  opciones.anchura.doubleValue() / Mapa.width;  // _xx_vv_  opciones.anchura.intValue() / Mapa.width;  
      //int serv = JSigpac.servidor.Id(); 
      JSigpac.ImprimirLinea("\tServidor: " + JSigpac.servidor.Abreviatura() + "  ("+JSigpac.servidor.Nombre()+")");            
      JSigpac.ImprimirLinea("\tNumero de cuadrantes totales: " + (opciones.n_filas * opciones.n_col) + 
                  "  [ " + opciones.n_filas + " filas x  " + opciones.n_col + " columnas ]");   
      JSigpac.ImprimirLinea("\tDatum " + (opciones.datumJSigpac == Datum.datumED50 ? "ED50" : "WGS89") + ":");  
      JSigpac.ImprimirLinea("\t------------");                            
      JSigpac.ImprimirLinea("\t"+ pad("UTM centro", 23) + "X: " + pad(opciones.x, 16) + " Y:"+opciones.y);
      JSigpac.ImprimirLinea("\t"+ pad("UTM esquina sup.izqda", 23) + "X: " + pad(opciones.qx, 16) + " Y:"+opciones.qy);
      JSigpac.ImprimirLinea("\tDimensiones del mapa:  Alto=" + opciones.alturaTotal +
                            "m.  Ancho=" + opciones.anchuraTotal + "m.");
      //_xx_new_ He añadido la variable "reso" a Opciones:
      JSigpac.ImprimirLinea("\tresolucion=" + opciones.reso + " metros/pixel\n");               
      JSigpac.ImprimirLinea("\tDirectorio destino: " +
	     (new File(aux.getAbsolutePath())).getParent());
   }
      
   x1 = opciones.qx + (j * opciones.anchura.doubleValue());
   if (opciones.solapamiento)
      x2 = x1 + ((opciones.anchura.doubleValue() * (Mapa.width+1)) / Mapa.width);
   else
      x2 = x1 + opciones.anchura.doubleValue();

   altura = opciones.anchura.doubleValue() / FACTOR;
   y1 = opciones.qy - (i * altura);
   if (opciones.solapamiento)
      y2 = y1 - (altura * ((Mapa.height+1) / Mapa.height));
   else
      y2 = y1 - altura;

   
   String direccion;
   
   direccion = ObtenerURL(x1, y1, x2, y2);
   if (direccion == null)
      return false;
      
   //JSigpac.ImprimirLinea("Peticion: " + direccion);

   if (!JSigpac.hayThreads)
   {                      
      Thread mithread=null;   
      try {  
        if (JSigpac.entornoGrafico)
           mithread = new Thread(new Descargar(nomFich, direccion, opciones, JSigpac.servidor.Nombre(), i, j, monitor));
        else
           mithread = new Thread(new Descargar(nomFich, direccion, opciones, JSigpac.servidor.Nombre(), i, j, monitor));
   
        mithread.start();
      } catch(OutOfMemoryError oome) {
          System.err.println("EXCEPCION OutOfMemoryError  _________" + oome);
      } catch(Throwable th) {   	
          System.err.println("EXCEPCION Throwable en ObtenerCuadrante  ______________");  
      } 
  
      if (monitor == null)
      {
         // Esperar a que finalice el Thread para devolver el control:
         //Thread mith = Thread.currentThread();
         //System.out.println("THREAD lanzado");
         while (mithread.isAlive())
         {
            //System.out.println("Antes del try isalive");	   
            try {
            	Thread.sleep(500);
            } catch(InterruptedException ie) {
         	//System.out.println("Dentro del bucle isAlive");
            }
         }
      }
   } else
   {     
         if (JSigpac.factoria.PutDescarga(nomFich, direccion, i, j) == false)
            return false;
   }
   
   return true;
 }

/* La funcion VistaPajaro obtiene un mapa de un solo cuadrante que abarca toda la extension.
   Esta funcionalidad no es posible con los servidores del SigPac con versión superior a la 4.0
 */
 public void VistaPajaro()
 {
   Monitor monitor = null;
   String nomFich=null;       
   String direccion=null;
      
   nomFich = opciones.raiz + "_TOTAL";  
   
   File aux = new File(nomFich + ".jpg");
   if (aux.exists())
       aux.delete();   
   
   //byte husoActual;
   //husoActual = (byte)opciones.huso.intValue();             
   
   direccion = ObtenerURL(XesqSupIzqda, YesqSupIzqda, XesqInfDer, YesqInfDer, true);      
      
   if (direccion == null)
      return;
      
       //_xx_ "aspimagedispatcher.aspx?guid=c670f580deda49f691de71e85b6bd921&npetic=42&function=getutmbitmap&"               
   Thread mithread;
   
   boolean tmp = opciones.quitaManchas;
   opciones.quitaManchas = false;
   if (JSigpac.entornoGrafico)
      mithread = new Thread(new Descargar(nomFich, direccion, opciones, JSigpac.servidor.Nombre(), 0, 0, monitor));
   else
      mithread = new Thread(new Descargar(nomFich, direccion, opciones, JSigpac.servidor.Nombre(), 0, 0, monitor));
   
   mithread.start();
   
   if (monitor == null)
   {
      // Esperar a que finalice el Thread para devolver el control:
      //Thread mith = Thread.currentThread();
      //System.out.println("THREAD lanzado");
      while (mithread.isAlive())
      {
         //System.out.println("Antes del try isalive");	   
         try {
         	Thread.sleep(500);
         } catch(InterruptedException ie) {
         	//System.out.println("Dentro del bucle isAlive");
         }
      }
   }
   
   opciones.quitaManchas = tmp;
   // Ahora se supone que ya tenemos la imagen con el mapa total en un solo cuadrante.
   // Lo intentamos sacar en una ventanita:
   @SuppressWarnings("unused")
   DialogoPajaro di = new DialogoPajaro(misigpac, true, nomFich, 
                                  Mapa.anchoVistaPajaro, Mapa.altoVistaPajaro);	 	
 }
 
 
 // La siguiente función es llamada desde el ThreadEsperaDescarga una vez 
 // que se han descargado correctamente todos los ficheros y se tiene que hacer 
 // el ensamblaje a continuación (Por ejemplo, cuando se llama al jSIGPAC con un 
 // fichero de track, ruta o waypoints):
 //public void DespuesDeDescargar(int nFil, int nCol, int compresion, int mapasV, int mapasH)
 public void DespuesDeDescargar(OpcionesMapa ops)
 {  
   String[] args_todos = null;
   String[] args_des = null;   
   String[] args_ens = new String[6];
   
   JSigpac.Traza("Mapa::DespuesDeDescargar   entrar...");
   
   args_ens[0] = "-f" + opciones.raiz; 
   args_ens[1] = "-efil" + String.valueOf(ops.n_filas);
   args_ens[2] = "-ecol" + String.valueOf(ops.n_col);
   args_ens[3] = "-J" + String.valueOf(ops.compresion);
   args_ens[4] = "-mv" + String.valueOf(ops.mapasVertical);
   args_ens[5] = "-mh" + String.valueOf(ops.mapasHorizontal);
   
   // _PP_ Aqui podriamos añadir las opciones del Mapa que se acaba de descargar por si hicieran falta esos datos
   //      en caso de generar generar varios cuadrantones en lugar de un único mapa  ??
   // Pero vamos a comentarlo y vamos a pasar siempre todos los argumentos disponibles:
   /*
   if (ops.mapasVertical > 1 || ops.mapasHorizontal > 1)
   {
      JSigpac.Traza("Mapa::DespuesDeDescargar  Se van a generar cuadrantones");
      args_des = ops.GenerarArgumentos(ops, 0, 0, ops.n_filas, ops.n_col, "");
      args_todos = JSigpac.UnirArgumentos(args_ens, args_des);    	
   } else
      args_todos = args_ens;
   */	
   // Una vez comentado, ponemos estas dos lineas:
   args_des = ops.GenerarArgumentos(ops, 0, 0, ops.n_filas, ops.n_col, "");
   args_todos = JSigpac.UnirArgumentos(args_ens, args_des);
      
      
   @SuppressWarnings("unused")
   Ensamblador ensam = new Ensamblador(args_todos, null); 
     
   PonerSTOP(false);
   //System.gc();
 }

 public static void main (String [] args)
 {
    JSigpac.ejecutableWindows = false;
    @SuppressWarnings("unused")
	Mapa miMapa = new Mapa(args);
 }

}




/****************************************************************************
 *
 * Clase:   Ensamblador
 * Autor:   Jose Ramon Arias Frances
 * Fecha:   15 de Octubre 2005
 * 
 * Funcion: Ensamblar los diferentes mapas que hemos obtenido con la otra
 *          aplicación "Mapa" y obtener en un sólo fichero en formato JPG
 *          el mapa total.
 *
 * Uso:     java -Xmx200M Ensamblador <raiz_nom_fich> <filas> <columnas>
 *
 *          - Todas los argumentos son obligatorios.
 *          - La opción -Xmx200M es necesaria debido a que el proceso consume
 *            bastante memoria y es preciso aumentarla para evitar un error
 *            del tipo "OutOfMemoryError".
 *          - raiz_nom_fich: Es el nombre base o raiz de los ficheros. En el
 *            ejemplo que he puesto para la aplicación "Mapa", la raíz sería
 *            "pucela_". Se supone que los ficheros a ensamblar están nombrados
 *            siguiendo el siguiente formato: <raiz_nom_fich><i>_<j>.jpg
 *          - filas: número de imágenes en el eje vertical.
 *          - columnas: número de imágenes en el eje horizontal.
 *
 * Requisitos: Es necesario disponer del "Java Advanced Imaging" aunque la
 *          version para Windows ya lo incluye y no haría falta descargarlo.
 *          Pero si va a ejecutarse como una aplicación Java, el JAI puede
 *          ser descargado de:
 *          http://java.sun.com/products/java-media/jai/current.html
 *          Y luego añadir al CLASSPATH el camino a: (si no se usa el ejecutable
 *          - ./jai-1_1_3-alpha/lib/mlibwrapper_jai.jar
 *          - ./jai-1_1_3-alpha/lib/jai_core.jar
 *          - ./jai-1_1_3-alpha/lib/jai_codec.jar
 *          
 *
 * Ejemplo: Supongamos que queremos ensamblar las imágenes obtenidas
 *          anteriormente que teniendo como centro la ciudad de Valladolid
 *          incluía los pueblos de Simancas, Viana de Cega, Boecillo, Laguna de
 *          Duero, Tudela de Duero, Villabáñez, Renedo, Mucientes, aeropuerto
 *          de Villanubla.
 *          - Habíamos obtenido 16 imágenes que formaban una matriz de 4*4.
 *          - El comando que tendremos que ejecutar será:
 *               java -Xmx200M Ensamblador pucela_ 4 4
 *          - Como resultado obtendremos un fichero llamado "pucela_.jpg" que
 *            es la unión de todas las pequeñas imágenes. Más o menos, el
 *            fichero obtenido tiene un tamaño de 1.5 megas.
 *
 **************************************************************************/



class Ensamblador {
 OpcionesEnsamblado opciones; 
 JSigpac elsigpac;
 Mapa miMapa;  // Usada en casos especiales: al ensamblar desde linea de comandos con las opciones -mv y mh
 //String raiz;
 //int m,n;  // m = filas,    n = columnas
 //int mapasV=1, mapasH=1;
 //int compresionJPEG = 100;
 boolean primeraVez;
 static String errorEnsam = null; // Utilizado en la rutina PonerSTOP para visualizar el mensaje de error en la barra de progreso

 public Ensamblador(String[] args, JSigpac js)
 {
   miMapa = null;
   opciones = new OpcionesEnsamblado();  
   
   elsigpac = js;  // Siempre que se utilice esta variable sera necesario
		   // comprobar previamente que estamos usando el
		   // entorno grafico mediante JSigpac.entornoGrafico.
   errorEnsam = null;		   
   JSigpac.IniciarTrazas();
   JSigpac.Traza("Ensamblador  constructor");
   if (TratarArgumentos(args) == false)
   {     
      DespuesDeFinalizarEnsamblado();      
      JSigpac.Salir(2000);      
      return;
   }  
 } 
  
 void PonerSTOP(boolean activar)
 {
     JSigpac.procesoFinalizado = !activar;
     if (errorEnsam == null)
        JSigpac.ImprimirLinea("\n\tPROCESO FINALIZADO");
     if (JSigpac.entornoGrafico) 
        if (elsigpac != null)	
           elsigpac.PonerSTOP(activar, errorEnsam);	
 }


// La función Ensamblar() la llevo dentro de la clase ThreadEnsamblar: 
/*
  public void Ensamblar()
  {
   int n_cuadrantes = m*n;
   RenderedOp[] cuadrantes = new RenderedOp[n_cuadrantes];
   boolean error = false; // Variable utilizada para que en caso de error no se eliminen todos los ficheros intermedios.	
   ...
   ...
   System.gc();
 }
*/

 static void ImprimirComando(String _raiz, int _mini, int _mfin, int _nini, int _nfin, int _compresionJPEG)
 {
     JSigpac.ImprimirLinea("===============================================================================");
     JSigpac.ImprimirLinea("Ensamblando filas " + _mini + " a " + (_mfin-1) + " y columnas " + _nini + " a " + (_nfin-1));
     JSigpac.ImprimirLinea("COMANDO: JSigpac -e -f"+_raiz+" -efil"+_mfin+" -ecol"+_nfin+" -J"+_compresionJPEG);
     JSigpac.ImprimirLinea("");
 }

 boolean TratarArgumentos(String [] args)
 {   
   int i=0; 
   String arg_actual="", explicacion="";
   String[] argsMapa = null;
   int num_args = 0;
   
   // JSigpac -e -fFICHERO -efilFILAS -ecolCOLUMNAS -JFACTORCOMPRESION -mvMAPASVERTICAL -mhMAPASHORIZONTAL
   /* Nota: En ciertos casos nos van a hacer falta mas datos sobre el mapa, como por 
    *       ejemplo si usamos las opciones "-mh" y "-mv" desde linea de comandos.
    *       En ese caso, a continuacion de esos parametros podemos añadir el resto de parametros
    *       que realmente se utilizaron a la hora de descargar y con ellos construimos
    *       un objeto de la clase Mapa para poder tener un miMapa y unas "opciones"    
    */
   
   try {
    for (i=0; i<args.length; i++)
    {
      JSigpac.Traza("Ensamblado: args["+i+"]="+args[i]);
      if (args[i].startsWith("-efil")) {  // Esto debe aparecer antes de la opcion "-f". Bueno, aquí da igual porque es "-efil"
	     arg_actual = "-efil";
	     explicacion=" (numero de filas)";
	     opciones.n_filas = (Integer.valueOf( args[i].substring(5) )).intValue(); // filas	 
	     opciones._n_filas = true;
	     arg_actual = "-ecol";
	     //explicacion=" (numero de columnas)";
	     continue;
      } else if (args[i].startsWith("-ecol")) {
	     arg_actual = "-ecol";
	     explicacion=" (numero de columnas)";
	     opciones.n_col = (Integer.valueOf( args[i].substring(5) )).intValue();
	     opciones._n_col = true;
	     arg_actual = "-f";
	     //explicacion=" (raiz del nombre del fichero)";
	     continue;
      } else if (args[i].startsWith("-f") && !args[i].startsWith("-fil")) { // Ojo a la opcion "-fil"
	     arg_actual = "-f";
	     explicacion=" (raiz del nombre del fichero)";
	     opciones.raiz = args[i].substring(2);
	     // Si no viene un nombre de fichero, saltara la excepcion antes
	     // de poner a true el valor de opciones._raiz:
	     if (opciones.raiz.trim().length() > 0)
	        opciones._raiz = true;
	     continue;
      } else if (args[i].startsWith("-J")) {
	     arg_actual = "-J";
	     explicacion=" (Factor de compresion JPEG)";
	     opciones.compresionJPEG = (Integer.valueOf( args[i].substring(2) )).intValue();
	     opciones._compresionJPEG = true;	 
	     continue;
      } else if (args[i].startsWith("-mv")) {
	     arg_actual = "-mv";
	     explicacion=" (numero de mapas en vertical)";
	     opciones.mapasVertical = (Integer.valueOf( args[i].substring(3) )).intValue();
	     opciones._mapasVertical = true;
	     continue;
      } else if (args[i].startsWith("-mh")) {
	     arg_actual = "-mh";
	     explicacion=" (numero de mapas en horizontal)";
	     opciones.mapasHorizontal = (Integer.valueOf( args[i].substring(3) )).intValue();
	     opciones._mapasHorizontal = true;
	     continue;
      } else { 
      	 //_PP_   Si es desde consola, pasamos la opcion a la clase Mapa
    	 //_PP_  if (!JSigpac.entornoGrafico)
    	 //_PP_  {
    	    if (argsMapa == null)
    	    {
    		   argsMapa = new String[20];
    		   num_args = 0;
    	    }
    	    argsMapa[num_args] = args[i];
    	    JSigpac.Traza("args_mapa["+num_args+"]="+argsMapa[num_args]);
    	    num_args++;    	    
    	 //_PP_  } 
      }
    }
  } catch (NullPointerException npe) {
     //if (!arg_actual.equals("-f") && !arg_actual.equals("-s"))
     String msj = "No ha dado valor a un argumento. El ultimo argumento tratado es \""+arg_actual+"\"";        
	 if (JSigpac.entornoGrafico)
	    elsigpac.SacarVentanita("Faltan parametros", msj);
	 else
	    System.out.println("\n" + msj);	  	 	        	
     return false;    
  } catch (NumberFormatException nfe) {
     String msj = "Valor numerico incorrecto en \""+arg_actual+"\""+explicacion;        
	 if (JSigpac.entornoGrafico)
	    elsigpac.SacarVentanita("Parametro incorrecto", msj);
	 else
	    System.out.println("\n" + msj);
	 return false;
  }	
   	
  String error = null;
  if ((error = opciones.EstanTodosLosParametros()) != null)
  {
     //System.err.println("EEErrror en los parametros: " + error);
     if (JSigpac.entornoGrafico && elsigpac != null)       
          elsigpac.SacarVentanita("Error en los parametros", error);
       else	
	  JSigpac.ImprimirLinea("Error en los parametros: " + error);   
	  
     return false;
  }     
  
  // Ponemos los valores por defecto en los argumentos opcionales que no nos hayan pasado:
  if (!opciones._compresionJPEG)	
  {
     opciones.compresionJPEG = 70;
     opciones._compresionJPEG = true;
  }
  if (!opciones._mapasHorizontal)
  {
     opciones.mapasHorizontal = 1;
     opciones._mapasHorizontal = true;
  }
  if (!opciones._mapasVertical)
  {
     opciones.mapasVertical = 1;
     opciones._mapasVertical = true;
  }
  
  // Comprobamos si hay opciones para la clase Mapa y si son correctas:
  if (argsMapa != null)
  {	 
	 String[] argumentos = new String[num_args+1];
	 JSigpac.Traza("num_args="+num_args);
	 for (int p=0; p<num_args; p++)
	 {
             argumentos[p] = argsMapa[p];
             JSigpac.Traza("argumentos["+p+"]="+argumentos[p]);
         }
	 argumentos[num_args] = "-f" + opciones.raiz;
	 miMapa = new Mapa(argumentos, "CARGAROPCIONES");
  }
  
  //opciones.Imprimir();
    	
   JSigpac.ActivarImpresionEnFichero(opciones.raiz);
   JSigpac.ImprimirLinea("\t[Recibida peticion de ensamblado]");

   // Ahora miramos si estan todos los ficheros que deben estar:
   File fich;
   String nom_fich;
   int resp;
   int j=0;
   i=0;   
   primeraVez = true;
   
   PonerSTOP(true);
   Monitor monitor = new Monitor(opciones.n_filas, opciones.n_col, 1, elsigpac, opciones.compresionJPEG, 
                                 opciones.mapasVertical, opciones.mapasHorizontal);  
       
   JSigpac.hayThreads = true;
   JSigpac.factoria = null; // Lo inicializamos
   
   boolean onlyTrack = false; //_hh_
   if (miMapa != null)
   {
      onlyTrack = miMapa.opciones.soloTrack;
      monitor.EstablecerOpcionesMapa(miMapa.opciones);
   } else if (JSigpac.miMapa != null)
   {
      onlyTrack = JSigpac.miMapa.opciones.soloTrack;
      monitor.EstablecerOpcionesMapa(JSigpac.miMapa.opciones);
   }
   JSigpac.Traza("onlyTrack="+onlyTrack);
   
   if (onlyTrack)
   {
      monitor.ReinicializarArrays();  //_hh_  Hacer algo parecido desde IniciarProceso()
   }
   
   for (i=0; i<opciones.n_filas; i++)
     for (j=0; j<opciones.n_col; j++)
     {
        nom_fich = opciones.raiz + i + "_" + j + ".jpg";
        //JSigpac.Traza("Ensamblador::TratarArgumentos  nom_fich="+nom_fich);        
	fich = new File(nom_fich);
	if (fich.exists() == false && monitor.HayQueDescargar(i,j))    //_hh_ onlyTrack == false )     // || (i==m-1 && j==n-1)) 
        {  // Si no existe el cuadrante y nos dicen que hay que descargarlo:   // o es el ultimo cuadrante:
	   if (JSigpac.entornoGrafico)
	   {
             if (primeraVez)
	     {
	        primeraVez = false;
                JSigpac.ImprimirLinea("\t- Faltan ficheros.");
                monitor.setPermiso((i*opciones.n_col) + j + 1);
		if (!elsigpac.ensambleDirecto.isSelected())
		{
	           resp = JOptionPane.showConfirmDialog(elsigpac,
		     "Faltan ficheros. ¿Desea descargar los cuadrantes que faltan?",
		     "Faltan cuadrantes", JOptionPane.YES_NO_OPTION,
		     JOptionPane.QUESTION_MESSAGE);
                   if (resp == JOptionPane.NO_OPTION)                   
	              return false;
                }
	        
	        String[] argus;
	        JSigpac.soloCalibrar = false;
	        argus = elsigpac.LeerFormulario();
	        if (argus != null)
	        {
                   JSigpac.ImprimirLinea("\t- Se procede con la descarga.");
	           if (JSigpac.miMapa != null)
	           {
	              if (JSigpac.miMapa.AjustesIniciales(argus) == false)
	              {
	              	 errorEnsam = "Posibles errores";
	                 return false;
	              }
	           } else
		   {
	              JSigpac.miMapa = new Mapa();
	              if (JSigpac.miMapa.AjustesIniciales(argus) == false)
	              {
	              	 errorEnsam = "Posibles errores";
	                 return false;
	              }
                   }
		   JSigpac.miMapa.ImprimirComando(); // De Mapa
		   if (JSigpac.hayThreads)
                      JSigpac.factoria = new Factoria(JSigpac.miMapa.opciones, monitor, 10);
                } else
	       	{
                  JSigpac.ImprimirLinea("Ha habido algun error. " +
		     "Compruebe que los valores son correctos");
		  errorEnsam = "Errores en descarga";
                  return false;
		}
            }
	    // Ahora intentamos obtener el cuadrante en cuestion:
            if (JSigpac.miMapa.ObtenerCuadrante(i, j, monitor) == false)
	       ; //JSigpac.ImprimirLinea("\t_cc_ Error al obtener la imagen *3* ("+i+","+j+")");
          } else
          {
            JSigpac.ImprimirLinea("\tNo existe el fichero \"" + nom_fich + "\"");
            // Si ejecutamos el programa desde consola, si faltan ficheros, no hacemos nada mas:
            /*
            if (JSigpac.entornoGrafico == false)
               return false;
            */
            if (miMapa != null)
            {
               if (primeraVez)
	       {
	          primeraVez = false;
	          if (JSigpac.hayThreads)
                     JSigpac.factoria = new Factoria(miMapa.opciones, monitor, 10);
               }
               // Ahora intentamos obtener el cuadrante en cuestion:
               JSigpac.ImprimirLinea("\tReintentamos la descarga del fichero \"" + nom_fich + "\"");
               if (miMapa.ObtenerCuadrante(i, j, monitor) == false)
                  ;
            } else {
            	// Si ejecutamos el programa desde consola, si faltan ficheros y no disponemos
            	// de la suficiente informacion, no hacemos nada mas:                                
                return false;                
            }
          }
       } else  // El fichero existe por lo que no intentamos descargarlo:
       {
       	  //System.out.println("_cc_ Llamamos a Monitor i=" + i + "  j="+j);
       	  monitor.CambiarValor(i, j, true);       	
       }
      } // Fin del for
            
      if (JSigpac.hayThreads)
      {
        try {
           if (JSigpac.factoria != null)
           {
              if (JSigpac.factoria.hayError == null)             
                 JSigpac.factoria.start();
              else
              {
              	 String msj = "No se inicia el proceso de descarga por el siguiente error(2):\n" + JSigpac.factoria.hayError;
              	 if (JSigpac.entornoGrafico && elsigpac != null)        
                    elsigpac.SacarVentanita("Mapa no generado", msj);
                 else
                    JSigpac.ImprimirLinea(msj);
                 //JSigpac.ImprimirLinea("No se inicia el proceso de descarga por el siguiente error(2): " + elsigpac.factoria.hayError);
                 errorEnsam = "Hay error";
                 return false;
              }
           }
           else
              JSigpac.ImprimirLinea("\tParece ser que ya tenemos todos los cuadrantes descargados.\n\tContinuamos...");
        } catch(IllegalThreadStateException itse) {
           //JSigpac.ImprimirLinea("IllegalThreadStateException: " + itse);
           String msj = "Se ha producido una excepcion al intentar descargar\nlos cuadrantes que faltaban";
           if (JSigpac.entornoGrafico && elsigpac != null)        
              elsigpac.SacarVentanita("Error al obtener cuadrantes", msj);
           else
              JSigpac.ImprimirLinea(msj);          
           errorEnsam = "Error en descarga";
           return false;
        }
      }
        
      //System.out.println("_cc_ Lanzamos el ThreadEsperaDescarga");
      ThreadEsperaDescarga th;
      if (JSigpac.entornoGrafico)
         th = new ThreadEsperaDescarga(this.elsigpac.grupeto, this, monitor);
      else
         th = new ThreadEsperaDescarga(this, monitor);
      th.start();
     // Lo que viene a continuacion ahora se hace en una función llamada desde el ThreadEsperaDescarga:
     /* 
      // Y ahora, si primeraVez es false, preguntamos si queremos ensamblar o
      // preferimos comprobar antes todas las imagenes por si queda alguna mancha:
      if (JSigpac.entornoGrafico)
      {
         if (primeraVez == false && !elsigpac.ensambleDirecto.isSelected())
         {
            resp = JOptionPane.showConfirmDialog(elsigpac,
                 "¿Desea ensamblar ahora (SI) o prefiere comprobar antes " +
                 "los cuadrantes descargados (NO) ?",
	         "Descarga completa", JOptionPane.YES_NO_OPTION,
	         JOptionPane.QUESTION_MESSAGE);
            if (resp == JOptionPane.NO_OPTION)
               return false;
         }
      }
     */
      
   return true; 
 }

 // La siguiente función es llamada desde el ThreadEsperaDescarga una vez 
 // que se dispone de todos los ficheros para ensamblar:
 @SuppressWarnings("static-access")
public void DespuesDeDescargar()
 {
   //System.out.println("_cc_ Estamos en DespuesDeDescargar");
   JSigpac.Traza("DespuesDeDescargar...");
   if (JSigpac.entornoGrafico)
   {
      if (primeraVez == false && !elsigpac.ensambleDirecto.isSelected())
      {
         int resp = JOptionPane.showConfirmDialog(elsigpac,
                "¿Desea ensamblar ahora (SI) o prefiere comprobar antes " +
                "los cuadrantes descargados (NO) ?",
	         "Descarga completa", JOptionPane.YES_NO_OPTION,
	         JOptionPane.QUESTION_MESSAGE);
         if (resp == JOptionPane.NO_OPTION)
         {
            errorEnsam = "Compruebe los cuadrantes descargados";
            PonerSTOP(false);
            return;
         }
      }
   }
   
   // Una vez que la descarga se ha completado, hacemos la calibracion:
   if (elsigpac != null && elsigpac.miMapa != null)
      elsigpac.miMapa.CalibrarMapa();
   else
      JSigpac.Traza("elsigpac=" + elsigpac + (elsigpac == null ? "" : "  elsigpac.miMapa=" + elsigpac.miMapa));
      
   // _mm_ Ensamblar();
   
   
   int m_ini, m_fin, n_ini, n_fin;
   int numColPerMap = opciones.n_col / opciones.mapasHorizontal;
   int numFilPerMap = opciones.n_filas / opciones.mapasVertical;
   ThreadEnsamblar ensamblar;
   Thread mith = Thread.currentThread();
   String coletilla;
   String[] args = null;   
   boolean salirDelBucle = false;   
      
   if (JSigpac.entornoGrafico)
      elsigpac.DarValorMaximoBarraDeProgreso(opciones.mapasVertical*opciones.mapasHorizontal); 
   //elsigpac.DarValorBarraDeProgreso(0);
   
   // Antes de entrar en el bucle que hace el ensamblado, para que en caso de no encontrarse 
   // algún cuadrante se pueda hacer la descarga, vemos si tenemos los datos necesarios para pasárselos:
   OpcionesMapa opsMapa = null;
   if (elsigpac != null && elsigpac.miMapa != null)
      opsMapa = elsigpac.miMapa.opciones;
   else if (miMapa != null)
      opsMapa = miMapa.opciones;
   
   
   for (int i=0; i<opciones.mapasVertical; i++)
   {
      if (salirDelBucle)
         break;
      m_ini = i*numFilPerMap;
      if (i == (opciones.mapasVertical-1))          
     	 m_fin = (i*numFilPerMap) + (opciones.n_filas - (i*numFilPerMap));
      else
         m_fin = (i+1)*numFilPerMap;
     	     
      for (int j=0; j<opciones.mapasHorizontal; j++)
      {
      	  if (JSigpac.finalizar == true)
      	  {
      	     errorEnsam = "Proceso cancelado";
      	     salirDelBucle = true;
      	     break;
      	  }
     	  n_ini = j*numColPerMap;     	  
          if (j == (opciones.mapasHorizontal-1))          
     	     n_fin = (j*numColPerMap) + (opciones.n_col - (j*numColPerMap));
     	  else
     	     n_fin = (j+1)*numColPerMap;
     	  
     	  if (opciones.mapasVertical == 1 && opciones.mapasHorizontal == 1)
     	     coletilla = "";
     	  else 
     	     coletilla = "_"+i+"-"+j+"_";
     	  
     	  if (JSigpac.entornoGrafico)  
     	     elsigpac.IncrementarBarraDeProgreso(1, false);   
     	  
     	  if ( (opciones.mapasVertical == 1 && opciones.mapasHorizontal ==1) &&
     	       (m_ini==0 && m_fin==1 && n_ini==0 && n_fin==1))
     	  {
     	     // No tiene que hacer nada si el mapa consta sólo de un cuadrante
     	     salirDelBucle = true;
      	     break;
     	  } else
     	  { 
     	     File aux = new File(opciones.raiz+coletilla+".jpg");
             if (aux.exists())
             {
                JSigpac.Traza("m_ini=" + m_ini + "   n_ini="+n_ini+"    m_fin="+m_fin+"  n_fin="+n_fin);
                if (JSigpac.entornoGrafico)
                   elsigpac.SacarVentanita("Fichero ya existente", "Ya existe el fichero: " + aux.getAbsolutePath() + " Si quiere volver a ensamblarlo, debe borrarlo.", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                   JSigpac.ImprimirLinea("Ya existe el fichero: \"" + aux.getAbsolutePath() + "\"");
                   JSigpac.ImprimirLinea("Si quiere volver a ensamblarlo, antes debe borrarlo.\n");
                }
                continue; // Si el fichero existe, no lo sobreescribimos y pasamos al siguiente   
             }
          }
          
     	  ensamblar = new ThreadEnsamblar(elsigpac, opciones.raiz, m_ini, n_ini, m_fin, n_fin, opciones.compresionJPEG, coletilla);  
     	  ensamblar.EstablecerOpcionesMapa(opsMapa);
     	  ensamblar.start();
     	  
     	  while (ensamblar.isAlive())
          {
             //System.out.println("Antes del try isalive 1");	         
             try {
                mith.sleep(1000);
                //System.out.println("_mm_ Pasan 2 segundos más   JSigpac.finalizar="+JSigpac.finalizar);
                //if (JSigpac.finalizar)
                //   ensamblar.interrupt();
             } catch(InterruptedException ie) {
               //System.out.println("Dentro del bucle isAlive 1");
             }
          }  
          JSigpac.Traza("Salimos del while (ensamblar.isAlive())"); 
          // System.out.println("********* HECHO EL " + coletilla +"   **********");
          //elsigpac.miMapa.opciones.Imprimir();
          
          // Ahora generamos los ficheros de calibración de ese cuadrantón, si es que hay cuadrantones, claro:            	      
  	  if (opciones.mapasVertical > 1 || opciones.mapasHorizontal > 1)
  	  {
            @SuppressWarnings("unused")
            Mapa cuadranton;
            if (elsigpac != null && elsigpac.miMapa != null)
    	       args = elsigpac.miMapa.opciones.GenerarArgumentos(elsigpac.miMapa.opciones, m_ini, n_ini, m_fin, n_fin, coletilla);
            else if (miMapa != null)
               args = miMapa.opciones.GenerarArgumentos(miMapa.opciones, m_ini, n_ini, m_fin, n_fin, coletilla);
            
            if (args != null)
   	       cuadranton = new Mapa(args, "CALIBRAR", elsigpac);
            else
               JSigpac.ImprimirLinea("\t** No se dispone de datos para calibrar el cuadranton " + coletilla);
   	  }
      }
   }    

   DespuesDeFinalizarEnsamblado();   
 }
 
 public void DespuesDeFinalizarEnsamblado()
 {
   //System.out.println("_cc_ DespuesDeFinalizarEnsamblado");
   JSigpac.ensamblando = false;   
   PonerSTOP(false);
   //JSigpac.DesActivarImpresionEnFichero();
   //System.gc();
 }
 
 public static void main (String[] args)
 {
    JSigpac.ejecutableWindows = false;
    @SuppressWarnings("unused")
    Ensamblador ens = new Ensamblador(args, null);
 }
}


/******************************************************************
 **********                                              **********
 **********            RECOGIDA DE EVENTOS               **********
 **********                                              **********
 ******************************************************************/

// Clase para recoger los eventos de las pulsaciones:
class miActionListener implements ActionListener
{
  JSigpac misigpac;
  String[] rest;
  
  miActionListener(JSigpac sg)
  {
     misigpac = sg;
  }
  	
  void Proceder(String[] argus)
  {
     if (JSigpac.miMapa != null)
        JSigpac.miMapa.IniciarProceso(argus);
     else
        JSigpac.miMapa = new Mapa(argus);   
        
     misigpac.RepintarValoresCorrectos();
  }

  public void actionPerformed( ActionEvent evt )
  {
     String err="";
     JSigpac.finalizar = false;  // Este podria ser un buen sitio para inicializar esta variable.
     //System.out.println("actionPerformed="+evt.getActionCommand()+"  paramString="+evt.paramString());
     if (evt.getActionCommand().equals("topo_orto"))
     {
	if (misigpac.topo_orto.getSelectedItem().equals("Ortofoto"))
           misigpac.PonerEstadoEscala(false);
	else
           misigpac.PonerEstadoEscala(true);
     } else if (evt.getActionCommand().equals("servidorCombo"))
     {
	//misigpac.miMapa.ObtenerIPServidor();  
	misigpac.ObtenerIPServidor();
	misigpac.setToolTipTextResolucion();
	int server = JSigpac.servidor.Id();
	//JSigpac.ImprimirLinea("Servidor elegido server=" + server);
	if (Servidor.DatumServidorWGS84[server])
	   misigpac.comboDatum.setSelectedItem(JSigpac.opcionesDatum[1]);
	else
	   misigpac.comboDatum.setSelectedItem(JSigpac.opcionesDatum[0]);
	/*
        if (server == 8 || server == 9)
            misigpac.comboDatum.setSelectedIndex(1);
        else
            misigpac.comboDatum.setSelectedIndex(0);   
        */
        
        if (server > -1)
           misigpac.visor.setToolTipText(Servidor.visor[server]); 
     } else if (evt.getActionCommand().equals("descargar"))
     {     	
	String[] argus;
        JSigpac.soloCalibrar = false;	
	argus = misigpac.LeerFormulario();
	if (argus != null)
	   Proceder(argus);
     } else if (evt.getActionCommand().equals("calibrar"))
     {
	String[] argus;
        JSigpac.soloCalibrar = true;	
	argus = misigpac.LeerFormulario();
	if (argus != null)
	   Proceder(argus);
     } else if (evt.getActionCommand().equals("ensamblar"))
     {
     	// Intentamos leer la informacion del panel de descarga:
     	//JSigpac.ImprimirLinea("_pp_ Intentamos leer la informacion del panel de descarga");
     	String[] argus;
        JSigpac.soloCalibrar = false;	
	argus = misigpac.LeerFormulario(false);
	if (argus != null)
	{
	   if (JSigpac.miMapa != null)
	   {
              if (JSigpac.miMapa.AjustesIniciales(argus, false, false) == false)
                 return;
              //misigpac.miMapa.ImprimirComando(); // De Mapa
              // Ahora repintamos los valores correctos:
              misigpac.RepintarValoresCorrectos();                   
           } else
              System.out.println("misigpac.miMapa  == null  en getActionCommand==ensamblar");
     	}
     	//JSigpac.ImprimirLinea("_pp_ PANEL LEIDO");
     	
     	// Y ahora hacemos lo que hay que hacer por haber pulsado el botón "ensamblar":     	
     	rest = new String[6];
        String miaux;
        if (JSigpac.miMapa.opciones.directorio == null)
 	   miaux = "";
        else
  	   miaux = JSigpac.miMapa.opciones.directorio.toString() + File.separator;
        rest[0] = "-f" + (misigpac.t_fichEnsam.getText().trim().length() == 0? "" : miaux + misigpac.t_fichEnsam.getText().trim());
        rest[1] = "-efil" + (misigpac.t_filas.getText().trim().length() == 0? "" : misigpac.t_filas.getText().trim());
        rest[2] = "-ecol" + (misigpac.t_columnas.getText().trim().length() == 0? "" : misigpac.t_columnas.getText().trim());
        rest[3] = "-J" + ((Integer)misigpac.compresionJPEG.getValue()).toString();
        rest[4] = "-mv" + (misigpac.t_mapasV.getText().trim().length() == 0? "1" : misigpac.t_mapasV.getText().trim());
        rest[5] = "-mh" + (misigpac.t_mapasH.getText().trim().length() == 0? "1" : misigpac.t_mapasH.getText().trim());
        
        @SuppressWarnings("unused")
        Ensamblador ensam = new Ensamblador(rest, misigpac);    
     } else if (evt.getActionCommand().equals("pajaro"))  
     {
     	String[] argus;
        JSigpac.soloCalibrar = false;	
	argus = misigpac.LeerFormulario();
	if (argus != null)
	{
	   if (JSigpac.miMapa != null)
	   {
	      if (JSigpac.miMapa.AjustesIniciales(argus, true) == false)
                 return;
              JSigpac.miMapa.VistaPajaro();
           } else
              JSigpac.miMapa = new Mapa(argus, "VISTAPAJARO");
	}
     	  
     } else if (evt.getActionCommand().equals("copiar"))
     { 
     	misigpac.t_fichEnsam.setText(misigpac.t_fichero.getText());
     	misigpac.t_columnas.setText(misigpac.t_num_col.getText());
     	misigpac.t_filas.setText(misigpac.t_num_filas.getText());   
     } else if (evt.getActionCommand().equals("STOP"))
     {	
     	// Si se encuentra ensamblando los cuadrantes, no se puede cancelar la operación y hay que esperar a que acabe:
     	if (JSigpac.ensamblando)
     	{
     	   JSigpac.finalizar = true;
     	   //misigpac.SacarVentanita("Operación no permitida", "No es posible cancelar la operación en estos momentos. Debe esperar a que finalice el ensamblado de este mapa", JOptionPane.INFORMATION_MESSAGE);
     	   misigpac.SacarVentanita("Cancelacion aceptada", "La cancelacion se hará efectiva al finalizar el ensamblado de este mapa", JOptionPane.INFORMATION_MESSAGE);
     	   return;
     	}
     	
     	JSigpac.ImprimirLinea("\n\t-- Recibida orden de parar el proceso --\n");
     	JSigpac.finalizar = true;
     	misigpac.PonerSTOP(false, "Proceso cancelado");  	     	     
     } else if (evt.getActionCommand().equals("carpeta"))
     {
	File dir;
	JSigpac.Traza("misigpac.miMapa.opciones.directorio="+JSigpac.miMapa.opciones.directorio);
	dir = JSigpac.miMapa.opciones.directorio;
	JFileChooser fc = new JFileChooser(dir);
	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	int res = fc.showOpenDialog(misigpac);
	if (res == JFileChooser.APPROVE_OPTION)
	{
	   File file = fc.getSelectedFile();
	   //System.out.println("getName="+file.toString());
	   JSigpac.miMapa.opciones.directorio = file;
           // Poner tooltip al boton:
           misigpac.directorio.setToolTipText(file.toString());
        }
     } else if (evt.getActionCommand().equals("ayuda"))
     {
       JSigpac.SacarAyuda();
     } else if (evt.getActionCommand().equals("reset"))
     {
        //System.out.println("getActionCommand: " + evt.getActionCommand());
        //System.out.println("getModifiers: " + evt.getModifiers());        
        //System.out.println("paramString: " + evt.paramString());

        // Damos valores por defecto:
        misigpac.ValoresPorDefecto();
     } else if (evt.getActionCommand().equals("fcalibrar"))
     {
	//System.err.println("calibrados="+ misigpac.fcalibra.isSelected());
	// No hacemos nada porque antes de generar los
	// fichero comprobamos el estado del JCheckBox.
	//_xd_ Mapa.GenerarFichCalibracion = misigpac.fcalibra.isSelected();
	//misigpac.miMapa.generarFichsCalibracion = misigpac.fcalibra.isSelected();
     } else if (evt.getActionCommand().equals("quitaManchas"))
     {
	//_xd_ Mapa.QuitarManchas = misigpac.quitaManchas.isSelected();
	//misigpac.miMapa.opciones.quitaManchas = misigpac.quitaManchas.isSelected();
     } else if (evt.getActionCommand().equals(JSigpac.coorEsquina))
     {
	if (misigpac.anchoTRadio.isSelected())
     	   misigpac.b_esqinfder.setEnabled(true);
	
     } else if (evt.getActionCommand().equals(JSigpac.coorCentro))
     {
     	misigpac.b_esqinfder.setEnabled(false);
     } else if (evt.getActionCommand().equals("total"))
     {
     	misigpac.resolucion.setEnabled(true);
     	misigpac.t_num_filas.setEnabled(false);
        misigpac.t_num_col.setEnabled(false);
     	if (misigpac.esquinaRadio.isSelected())
     	   misigpac.b_esqinfder.setEnabled(true);
     	       
        if ( misigpac.t_ancho.getText().trim().equals("")  ||
     	     misigpac.t_num_col.getText().trim().equals("")  )
     	   return;
     	int  nCol, nFil;     	
     	double ancho, alto;
     	
     	try {     
          // Ancho:	  
          err = "Valor incorrecto en campo \"Ancho\"";
          ancho = (Double.valueOf( misigpac.t_ancho.getText().trim() )).doubleValue();                            
          err = "Valor incorrecto en campo \"columnas\"";
          nCol = (Integer.valueOf(((String)misigpac.t_num_col.getText()).trim())).intValue();          
          double aux1;
          err = "Error al calcular el ancho total";
          aux1 = ancho * nCol;
          misigpac.t_ancho.setText(Mapa.df.format(aux1));
          
          // Alto:
          double aux2;
          err = "Error al calcular la altura total";
          if (!misigpac.t_num_filas.getText().trim().equals(""))
          {
             err = "Valor incorrecto en campo \"filas\"";
             nFil = (Integer.valueOf(((String)misigpac.t_num_filas.getText()).trim())).intValue();
          } else
             nFil = nCol;
            
          if (!misigpac.t_alto.getText().trim().equals(""))
          {
             err = "Valor incorrecto en campo \"Alto\"";
             alto = (Double.valueOf( misigpac.t_alto.getText().trim() )).doubleValue();
          } else
             alto = ancho / Mapa.FACTOR;
                                  
          aux2 = alto * nFil;
          	         
          misigpac.t_alto.setText(Double.toString(aux2));
          misigpac.t_ancho.setCaretPosition(0);
          misigpac.t_alto.setCaretPosition(0);
        } catch (NumberFormatException nfe) {
	  JOptionPane.showMessageDialog(misigpac, err,
	      "Parametro incorrecto", JOptionPane.ERROR_MESSAGE);
        } 	
     } else if (evt.getActionCommand().equals("cuadrante"))
     {
        misigpac.resolucion.setEnabled(false);
        misigpac.b_esqinfder.setEnabled(false);
        misigpac.t_num_filas.setEnabled(true);
        misigpac.t_num_col.setEnabled(true);
        if ( misigpac.t_ancho.getText().trim().equals("")  ||
     	     misigpac.t_num_col.getText().trim().equals("")  )
     	   return;
     	int nCol, nFil;     	
     	double alto, ancho;
     	try {   
     	  // Ancho:  	  
          err = "Valor incorrecto en campo \"Ancho\"";
          ancho = (Double.valueOf( misigpac.t_ancho.getText().trim() )).doubleValue();
          err = "Valor incorrecto en campo \"columnas\"";
          nCol = (Integer.valueOf(((String)misigpac.t_num_col.getText()).trim())).intValue();
          double aux1;
          err = "Error al calcular el ancho de un cuadrante";
          aux1 = ancho / (nCol * 1.0D);
          misigpac.t_ancho.setText(Mapa.df.format(aux1));
          
          // Alto:
          double aux2;
          err = "Error al calcular la altura de un cuadrante";
          if (!misigpac.t_num_filas.getText().trim().equals(""))
          {
             err = "Valor incorrecto en campo \"filas\"";
             nFil = (Integer.valueOf(((String)misigpac.t_num_filas.getText()).trim())).intValue();
          } else
             nFil = nCol;
            
          if (!misigpac.t_alto.getText().trim().equals(""))
          {
             err = "Valor incorrecto en campo \"Alto\"";
             alto = (Double.valueOf( misigpac.t_alto.getText().trim() )).doubleValue();
          } else
             alto = ancho / Mapa.FACTOR;
                                  
          aux2 = alto / (nFil * 1.0D);
          	         
          misigpac.t_alto.setText(Double.toString(aux2));
          misigpac.t_ancho.setCaretPosition(0);
          misigpac.t_alto.setCaretPosition(0);          
        } catch (NumberFormatException nfe) {
	  JOptionPane.showMessageDialog(misigpac, err,
	      "Parametro incorrecto", JOptionPane.ERROR_MESSAGE);
        }
          
     } else if (evt.getActionCommand().equals("resolucion"))
     {
     	misigpac.AplicarResolucion();
      /*
     	//System.out.println("getActionCommand: " + evt.getActionCommand());
        //System.out.println("getModifiers: " + evt.getModifiers());        
        //System.out.println("paramString: " + evt.paramString());
        // Segun el valor del campo "Ancho" y/o "Alto" y la resolucion,
	// se calcula el numero de filas y de columnas:
	double ancho, alto;
	double res;
	misigpac.setToolTipTextResolucion();
	try {
	  err = "Valor incorrecto en campo \"Ancho\"";
	  ancho = (Double.valueOf( misigpac.t_ancho.getText().trim() )).doubleValue();
	  
	  err = "Valor incorrecto en campo \"resolucion\"";
	  res = (Double.valueOf(((String)misigpac.resolucion.getSelectedItem()).trim())).doubleValue();
	  int nFil, nCol; //, n_cuad;
	  nCol = (int)Math.floor((ancho / (res*Mapa.width)) + 0.999);
	  if (nCol <= 0)
	     nCol = 1;
	  err = "Valor incorrecto en campo \"Alto\"";
	  if (misigpac.t_alto.getText().trim().equals(""))
	     nFil = nCol;
	  else
	  {
	     alto = (Double.valueOf( misigpac.t_alto.getText().trim() )).doubleValue();
	     nFil = (int)Math.floor((alto / (res*Mapa.height)) + 0.999);
	     if (nFil <= 0)
	         nFil = 1;
	  }
	  	
	  err = "Error al calcular el numero de filas y columnas";
	  misigpac.t_num_filas.setText(Integer.toString(nFil));
	  misigpac.t_num_col.setText(Integer.toString(nCol));
	  // Se recalcula el valor de la anchura y la altura de acuerdo a la resolucion:
	  misigpac.t_ancho.setText(Integer.toString((int)(nCol * res * Mapa.width)));
	  misigpac.t_alto.setText(Integer.toString((int)(nFil * res * Mapa.height)));
        } catch (NumberFormatException nfe) {
	  JOptionPane.showMessageDialog(misigpac, err,
	      "Parametro incorrecto", JOptionPane.ERROR_MESSAGE);
        }

        //opciones.anchura = Integer.valueOf( args[i].substring(2) );
      */   
     } else if (evt.getActionCommand().equals("arrowUP"))
     {
       String [] val;
       int i,j;
       val = misigpac.cuadranton.getText().split(",");
       i = Integer.parseInt(val[0]);
       j = Integer.parseInt(val[1]);
       i--;
       misigpac.cuadranton.setText(i+","+j);
       misigpac.RecalcularCoordenadas(-1, 0);
     } else if (evt.getActionCommand().equals("arrowDOWN"))
     {
       String [] val;
       int i,j;
       val = misigpac.cuadranton.getText().split(",");
       i = Integer.parseInt(val[0]);
       j = Integer.parseInt(val[1]);
       i++;
       misigpac.cuadranton.setText(i+","+j);
       misigpac.RecalcularCoordenadas(1, 0);
     } else if (evt.getActionCommand().equals("arrowLEFT"))
     {
       String [] val;
       int i,j;
       val = misigpac.cuadranton.getText().split(",");
       i = Integer.parseInt(val[0]);
       j = Integer.parseInt(val[1]);
       j--;
       misigpac.cuadranton.setText(i+","+j);
       misigpac.RecalcularCoordenadas(0, -1);
     } else if (evt.getActionCommand().equals("arrowRIGHT"))
     {
       String [] val;
       int i,j;
       val = misigpac.cuadranton.getText().split(",");
       i = Integer.parseInt(val[0]);
       j = Integer.parseInt(val[1]);
       j++;
       misigpac.cuadranton.setText(i+","+j);
       misigpac.RecalcularCoordenadas(0, 1);
     } else if (evt.getActionCommand().equals("importar"))
     {       	 	
     	//String[] respuesta = new String[5];
     	misigpac.respCoor = null;
     	misigpac.datum_respCoor = null;
     	@SuppressWarnings("unused")
        DialogoImportar di = new DialogoImportar(misigpac, true); //, respuesta);
     	// Puede que respCoor siga siendo null. Poner un try{}catch
     	try {
     	  //System.out.println("respCoor length=" + misigpac.respCoor.length);
     	  //for (int h=0; h<misigpac.respCoor.length; h++)
     	  //    System.out.println("respCoor["+h+"] = "+ misigpac.respCoor[h]);
     	  misigpac.PasarCoordenadas();
     	} catch (NullPointerException npe) {
     	  JSigpac.Traza("respCoor es NULL");
     	}
                     
        // El siguiente codigo es una prueba que he hecho para intentar añadir
        // informacion a los mapas en forma de metadatos
        /* CODIGO 1:
        // Este codigo es para leer metadatos de un JPG: 
     	File jpegFile = new File("prueba.jpg");
     	try {
          Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
          // iterate through metadata directories
          Iterator directories = metadata.getDirectoryIterator();
          while (directories.hasNext()) {
            Directory directory = (Directory)directories.next();
            // iterate through tags and print to System.out
            Iterator tags = directory.getTagIterator();
            while (tags.hasNext()) {
              Tag tag = (Tag)tags.next();
              // use Tag.toString()
              System.out.println(tag);
            }
          }
        } catch (JpegProcessingException jpe) {
           System.out.println("JpegProcessingException: " + jpe);
        }
        */	
        /*  CODIGO 2:
        try {
          JpegReader reader = new JpegReader(new File("prueba.jpg"));
          IPTCEntryCollection collection = new IPTCEntryCollection();
          collection.addEntry(IPTCConstants.RECORD_APPLICATION, IPTCConstants.CAPTION_ABSTRACT, "Very very long description".getBytes());
          MetadataUtils.insertIPTC(reader, collection, "prueba_sal.jpg");
          
          //IFDEntry[][] entries = MetadataUtils.getExif(reader);
          //for (int i = 0; i < entries.length; i++) {
          //  IFDEntry[] entry = entries[i];
          //  for (int j = 0; j < entry.length; j++) {
          //      IFDEntry ifdEntry = entry[j];
          //      IFDEntryMeta meta = ifdEntry.getEntryMeta();
          //      String name = meta.getName();
          //      System.out.println(name + ": " + ifdEntry);
          //  }
          //}
          
        } catch (IOException ioe) {
           System.out.println("IOException: " + ioe);
        }
        */

     } else if (evt.getActionCommand().equals("leerde"))
     {	
     	@SuppressWarnings("unused")
        DialogoLeerGuardar di = new DialogoLeerGuardar(misigpac, true);
     } else if (evt.getActionCommand().equals("guardaren"))
     {	
     	@SuppressWarnings("unused")
        DialogoLeerGuardar di = new DialogoLeerGuardar(misigpac, false);	
     } else if (evt.getActionCommand().equals("esqinfder"))
     {	
     	@SuppressWarnings("unused")
        DialogoEsquinaInfDcha di = new DialogoEsquinaInfDcha(misigpac);
     }
     

     //System.out.println("actionPerformed="+evt.getActionCommand());
     //System.out.println("paramString="+evt.paramString());
  }
}



class miKeyListener implements KeyListener
{
   JSigpac misigpac;  
  
    miKeyListener(JSigpac sg)
    {
       misigpac = sg;
    }
  
    public void keyTyped(KeyEvent e) {
    	//System.out.println("");
	//System.out.println("keyTyped " + e);
	misigpac.rejilla.repaint();
    }

    public void keyPressed(KeyEvent e) {
    	//System.out.println("");
	//System.out.println("keyPressed " + e);
	misigpac.rejilla.repaint();
    }
    
    public void keyReleased(KeyEvent e) {
    	//System.out.println("");
	//System.out.println("keyReleased " + e);
	misigpac.rejilla.repaint();
    }
	
}



class miFocusListener implements FocusListener
{
   JSigpac misigpac;  
  
    miFocusListener(JSigpac sg)
    {
       misigpac = sg;
    }
  
    public void focusGained(FocusEvent e) {
    	System.out.println("");
	System.out.println("Focus gained " + e);
	misigpac.rejilla.repaint();
    }

    public void focusLost(FocusEvent e) {
    	System.out.println("");
	System.out.println("Focus lost " + e);
    }
	
}


class EventosDelRaton implements MouseListener 
{	

    JSigpac misigpac;
    String actioncommand;
    
    EventosDelRaton(JSigpac sg, String comando)
    {
       misigpac = sg;
       actioncommand = comando;
    }
    
    public void mousePressed(MouseEvent e) {
       //saySomething("Mouse pressed; # of clicks: " + e.getClickCount(), e);
       
       if (actioncommand.equals("descargar"))
       {
       	  JSigpac.Traza("ancho=" + misigpac.t_ancho.getText()+"  alto="+misigpac.t_alto.getText());
       	  // Si NO se ha pulsado el botón izquierdo, actualizo los valores correctos en X, Y, ancho y alto:
          if (e.getButton() != 1)
          {
       	     String[] argus;
             JSigpac.soloCalibrar = false;	
             JSigpac.Traza("Pulsado botón descargar con derecho");
	     argus = misigpac.LeerFormulario(false);
	     if (argus != null)
	     {
	        if (JSigpac.miMapa != null)
	        {
	           JSigpac.Traza("Se llama a JSigpac.miMapa.AjustesIniciales");
                   if (JSigpac.miMapa.AjustesIniciales(argus) == false)
                      return;
                   JSigpac.miMapa.ImprimirComando(); // De Mapa
                   // Ahora repintamos los valores correctos:
                   misigpac.RepintarValoresCorrectos();                   
                } else
                   System.out.println("misigpac.miMapa  == null"); //misigpac.miMapa = new Mapa(argus);
	    }   
          }
       } else if (actioncommand.equals("Fichero"))
       {
          if (e.getButton() != 1)
          {
             String comando=null;
             // Antes llamábamos a:
             // comando = misigpac.GetNombreFichero();
             // Pero ahora usamos la siguiente llamada:
             comando = misigpac.miMapa.opciones.GenerarNombreAutomatico(JSigpac.FORMATO);
             JSigpac.Traza("comando="+comando);              
             misigpac.t_fichero.setText(comando);                         
             if (comando.equals(""))
                misigpac.SacarVentanita("Error al crear el nombre del fichero", "Deberia PULSAR antes con el BOTON DERECHO del raton sobre el boton descargar...");
          }
       } else if (actioncommand.equals("fcalibra"))
       {
          if (e.getButton() != 1)
          {
             DialogoFichCalibracion dfc = new DialogoFichCalibracion(misigpac);
          }
       }
    }

    public void mouseReleased(MouseEvent e) {
       saySomething("Mouse released; # of clicks: "
                    + e.getClickCount(), e);
    }

    public void mouseEntered(MouseEvent e) {
       saySomething("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) {
       saySomething("Mouse exited", e);
    }

    public void mouseClicked(MouseEvent e) {
       String link="";	
       //System.out.println("ACTIONCOMMAND: " + actioncommand);
       saySomething("Mouse clicked (# of clicks: "
                    + e.getClickCount() + ")", e);
       saySomething("actioncommand: " + actioncommand, e);
       
       if (actioncommand.equals("Fundacion"))
          link = JSigpac.afiliado;
       else if (actioncommand.equals("webJSIGPAC"))
       {
       	  System.out.println("Se ha pulsado en webJSIGPAC  link=" + JSigpac.webJSIGPAC);
          link = JSigpac.webJSIGPAC;
       }   
       else if (actioncommand.equals("anuncio"))
       {
          link = misigpac.anuncio.getLink();
          misigpac.anuncio.RegistrarAccesoAnuncio();
       } else if (actioncommand.equals("webVisor"))       
          link = misigpac.visor.getToolTipText();              
       else if (actioncommand.equals("creativecommons"))       
          link = JSigpac.creativecommons;
                               
       
       if (actioncommand.equals("anuncio"))
          if (misigpac.anuncio.getLink().length() == 0)
             return;
             
       //if (actioncommand.equals("Fundacion")  || 
       //    actioncommand.equals("webJSIGPAC") ||
       //    actioncommand.equals("anuncio")    ||
       //    actioncommand.equals("webVisor"))
       
       if (actioncommand.equals("bback"))
       {
       	  misigpac.anuncio.PasarAlAnteriorAnuncio();
       	  misigpac.anuncio.ReInicializar();
       } else if (actioncommand.equals("bgo"))
       {
       	  misigpac.anuncio.PasarAlSiguienteAnuncio();
       	  misigpac.anuncio.ReInicializar();
       } else
       {
       	 if (link.equals(""))
       	    return;
       	 //System.out.println("Has pulsado en Fundacion");       	 

       	 try {
       	  //Runtime.getRuntime().exec("C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe http://www.hotmail.com/");       	
       	    Runtime.getRuntime().exec(misigpac.PathFireFox() + " " + link);       	
       	 } catch (IOException ioe1) {
       	    try {
       	       Runtime.getRuntime().exec(misigpac.PathExplorer() + " " + link);
       	    } catch (IOException ioe2) { 
       	       misigpac.SacarVentanita("Navegador no encontrado", "Es necesario configurar la variable de entorno JSIGPAC_NAVEGADOR. Se intentó acceder a: "+ JSigpac.afiliado);       	    	
       	       //JSigpac.ImprimirLinea("No se han localizado ni el Firefox ni el iExplorer\n");
       	       //JSigpac.ImprimirLinea("Intento de acceso a: " + link + "\n");
       	    }
       	 }
       }
    }

    void saySomething(String eventDescription, MouseEvent e) {
        //System.out.println(eventDescription + " detected on " + e.getButton());
    }
}






class OpcionesMapa 
{   
   boolean _ortofoto;
   boolean ortofoto;
   boolean vistaPajaro;
   boolean soloTrack;    // Si esta a "true" significa que solo se descargan los cuadrantes por los que va el track o ruta //_hh_
   String tipoMapa;      // Contendrá el argumento "-m": "-mORTO" o "-mTOPO25", etc...
   Datum datumServidor;  // Datum utilizado por el servidor de los mapas. Ej: Madrid y Canarias manejan el WGS84
   Datum datumJSigpac;   // Datum utilizado por el jSIGPAC. Es decir, es el datum en el que se encuentran 
   			 // las coordenadas introducidas por el usuario en el interfaz del jSIGPAC (datum de entrada)
   boolean _coorEsquina; // Vale "true" si la coordenada UTM se refiere a
			 // la esquina superior izquierda del mapa.
   String coorEsquina;   // Contendra "-q" o nada
   boolean _huso=false;
   Integer huso;         // Zona o huso utilizados
   boolean _x, _y;
   double x,y;           // Coordenadas UTM del punto central
   double qx=0.0D, qy=0.0D; // Coordenadas UTM de la esquina superior izquierda
   boolean _anchuraTotal; // Vale "true" si la opcion de anchura es "-A"
   double anchuraTotal;  // anchura en metros del mapa final //_xx_vv_ int anchuraTotal; 
   String optAncho;      // Contendra "-a" o "-A"
   boolean _anchura;
   Double anchura;       // Al final debera contener el ancho en metros de un cuadrante // _xx_vv_  Integer anchura;
   String optAlto;       //Contendrá "-H" o puede que nada.
   boolean _altura;
   double alturaTotal;   // Alto en metros abarcado por el mapa final.
   //boolean _n_cuadrantes;
   //Integer n_cuadrantes; // Numero de cuadrantes a lo ancho del mapa. Es par o vale 1.
   boolean _reso;        // Para identificar si el usuario especifica la resolucion o facilita el numero de filas y de columnas
   double reso;          // Resolucion en metros/pixel del mapa
   boolean _n_filas;
   int n_filas;          // Numero de filas del mosaico final
   boolean _n_col;
   int n_col;            // Numero de columnas del mosaico final
   boolean _raiz;
   String raiz;          // Nombre base de los ficheros. Por defecto sera "img_"
   boolean _fichformato;
   String fichformato;   // Nombre del fichero con el formato del nombre del mapa. 
   File directorio=null;
   boolean generarFichsCalibracion; // Si aparece la opcion "-c" significa que
	                            // hay que generar los ficheros de calibracion.
      boolean calibrarOzi;
      boolean calibrarCompe;
      boolean calibrarGPSTuner;
      boolean calibrarPathAway;
      boolean calibrarJGW;
      boolean calibrarGlobalMapper;
      boolean calibrarERS;
      boolean calibrarTracky;
      boolean calibrarMyMotion;
      boolean calibrarTomTom;	
      	                            
   boolean WGS84; // Si aparece la opcion "-w" significa que los ficheros de
		  // calibracion de Ozi y Compe van con el datum WGS84 en lugar de ED50.
   boolean quitaManchas; // Con la opcion "-s" eliminamos las marcas del SigPac
   boolean _i, _j;
   int i,j;  // Cuadrante de la fila "i" y la columna "j".
   String escala;   
   boolean solapamiento; // Tiene que ver con el ajuste perfecto de los cuadrantes
			 // propuesto por "scaner".
   boolean borrarIntermedios;  // Especificamos si se borran o no los ficheros (cuadrantes) descargados una vez que se ensambla el mapa final.			 
   int compresion;  // Factor de compresion JPEG
   
   boolean noPreguntarMas;
   boolean sobreescribir;
   // Las siguientes dos variables se usan cuando se pincha y arrastra un fichero sobre el icono del "jsigpac.exe"
   // y en los parámetros por defecto ("dat\jsigpac.defecto") se especifica el numero de mapas en vertical y en horizontal:
   int mapasVertical=1;   
   int mapasHorizontal=1;

   public void Imprimir()
   {
     JSigpac.ImprimirLinea("ortofoto="+ortofoto+"   tipoMapa="+tipoMapa+"   _coorEsquina="+_coorEsquina);
     JSigpac.ImprimirLinea("huso="+huso+"   x="+x+"   y="+y+"   qx="+qx+"  qy"+qy);
     JSigpac.ImprimirLinea("_anchuraTotal="+_anchuraTotal+"  anchuraTotal="+anchuraTotal+"  optAncho="+optAncho+"  anchura="+anchura);
     JSigpac.ImprimirLinea("alturaTotal="+alturaTotal+"  _reso="+_reso+"  reso="+reso);
     JSigpac.ImprimirLinea("n_filas="+n_filas+"   n_col="+n_col);
     JSigpac.ImprimirLinea("raiz="+raiz);     
   }

   public OpcionesMapa()
   {
     Inicializar();
   }
   
   void Inicializar()
   {
      JSigpac.Traza("OpcionesMapa::Inicializar");	
      // Hay que restaurar las condiciones iniciales:
      datumServidor = null;
      datumJSigpac = null;
      _ortofoto = false;
      vistaPajaro = false; // Por defecto no se trata de obtener el mapa global
      soloTrack = false;
      _coorEsquina = false;
      coorEsquina = null;
      _huso = false;
      huso = null;
      _x = false;
      _y = false;
      optAncho = "";
      _anchuraTotal = false;
      _anchura = false;
      anchura = null;
      optAlto = "";
      _altura = false;
      _reso = false;
      _n_filas = false;
      _n_col = false;
      _raiz = false;
      raiz = null;
      _fichformato = false;
      fichformato = null;
      //directorio = null;  // No lo pongo a null porque si no perdemos el valor entre una llamada y otra. 
                            // Es decir, si primero descargábamos, despues al ensamblar no sabía en qué directorio hacerlo.                          
      _i = false;
      _j = false;
      WGS84 = false; // Por defecto, generamos en ED50
      generarFichsCalibracion = false;
      CalibracionesPorDefecto();
        
      quitaManchas = false;
      solapamiento = false;
      borrarIntermedios = false;
      reso = 1.0D;
      compresion = 70;
      escala = null;
      noPreguntarMas = false;
      sobreescribir = true;
      mapasVertical=1;   
      mapasHorizontal=1;
      //JSigpac.Traza("Inicializar  reso=" + reso);
   }

   void CalibracionesPorDefecto()
   {
      calibrarOzi = true;
      calibrarCompe = true;
      calibrarGPSTuner = false;
      calibrarPathAway = false;
      calibrarJGW = false;
      calibrarGlobalMapper = false;
      calibrarERS = false;
      calibrarTracky = false;   
      calibrarMyMotion = false;
      calibrarTomTom = false;
   }
   
   
   /*
       Una vez que se han leido todos los argumentos habra
       que ver si estan todos los parametros obligatorios.
       Se devuelve un mensaje con el error o null si todo va bien:
    */

   String EstanTodosLosParametros(boolean Anchura_repe)
   {            
     if (!_ortofoto)     
        return "Debe especificar si el mapa es topografico \"-mTOPO\" u ortofoto \"-mORTO\".";        
     else if (!_huso)
        return "Debe especificar el huso o zona: -h";       
     else if (Anchura_repe)
        return "Ha introducido dos veces la anchura -a|-A";       
     else if (!_x)
        return "Debe introducir la coordenada X: -x";        
     else if (!_y)
        return "Debe introducir la coordenada Y: -y";      
     else if (!_anchura && !_anchuraTotal)
        return "Debe introducir la anchura en metros: -a|-A";       
     else if (_reso) {
     	   if (_n_filas || _n_col)
              return "Especificar la resolucion es incompatible con especificar las filas o las columnas";
           else if (!_anchuraTotal)
              return "Para especificar la resolucion es necesario dar tambien la anchura total (-A)";
     } else if (!_n_filas && !_reso)
        return "Debe introducir el numero de filas (-fil) o la resolucion (-r)";       
     else if (!_n_col && !_reso)
        return "Debe introducir el numero de columnas (-col) o la resolucion (-r)";        
     else if (_i && !_j)
        return "No ha especificado la columna j deseada (-j)";       
     else if (_j && !_i)
        return "No ha especificado la fila i deseada (-i)";            

     return null;
   }

   String GenerarNombreAutomatico(String fichero)
   {

     // Leemos del fichero "dat\jsigpac.formato" el formato del nombre del fichero:  
     String nombre=""; 
     String formato=null;  
     String pordefecto = "#tipo_mapa#\"_\"#servidor#\"_h\"#huso#\"_qx\"#qx#\"_qy\"#qy#\"_A\"#anchura_total#\"_H\"#altura_total#\"_r\"#resolucion#";
     File dat = new File(fichero);
     FileInputStream fis=null;
    
     JSigpac.Traza("Intentamos leer el fichero: " + dat.getAbsolutePath());
      
     if (dat.exists())
     {
       try {
       	 JSigpac.Traza("El fichero existe");
         fis = new FileInputStream(dat);
	 Properties misProperties = new Properties();
         misProperties.load(fis);
         //misProperties.list(System.out);
         formato = misProperties.getProperty("formato", pordefecto);	 
       } catch (FileNotFoundException fnfe) {
	  JSigpac.Traza("GenerarNombreAutomatico: No se ha encontrado el fichero: " + dat.getPath());
       } catch (SecurityException se) {
	  JSigpac.Traza("GenerarNombreAutomatico: Acceso de lectura denegado al fichero: " + dat.getPath());
       } catch (IllegalArgumentException se) {
 	  JSigpac.Traza("GenerarNombreAutomatico: Formato incorrecto del fichero: " + dat.getPath());
       } catch (IOException se) {
	  JSigpac.Traza("GenerarNombreAutomatico: Error al intentar leer el fichero: " + dat.getPath());
       } finally {
      	  try {
            if (fis != null)
            {
      	       fis.close();      
      	       fis = null;
      	    }
          } catch (IOException ioe) {}      	
       } 	
     } else // No existe el fichero donde se especifica cómo se construye el nombre del fichero:
     {
       JSigpac.Traza("No existe el fichero: " + dat.getAbsolutePath());
       formato = pordefecto;
     }
  
     if (formato == null)          	
        formato = pordefecto;
     
     JSigpac.ImprimirLinea("FORMATO: " + formato);   
     StringTokenizer st = new StringTokenizer(formato, "#");
     String token;
     while (st.hasMoreTokens())
     {
       token = st.nextToken();
       //JSigpac.Traza("token: " + token);
       try {
         if (token.startsWith("\""))
         {
            // Lo que viene a continuación de las comillas debe ser tomado literalmente. 
            // Aunque debería acabar con otras comillas dobles, en caso de no existir, no lo tendré en cuenta y sere condescendiente:
            nombre += token.substring(1, token.lastIndexOf("\""));                           
         } else if (token.equals("servidor"))
         {
       	    nombre += JSigpac.servidor.Abreviatura();
         } else if (token.equals("datum_entrada"))
         {
       	    nombre += datumJSigpac.miString();
         } else if (token.equals("tipo_mapa"))
         {
       	    nombre += tipoMapa.substring(2);
         } else if (token.equals("huso"))
         {
       	    nombre += huso.intValue();
         } else if (token.equals("qx"))
         {       	  
       	    nombre += qx == (int)qx ? Integer.toString((int)qx) : qx; // Coordenada X de la esquina superior izquierda
         } else if (token.equals("qy"))
         {
       	    nombre += qy == (int)qy ? Integer.toString((int)qy) : qy; // Coordenada Y de la esquina superior izquierda
         } else if (token.equals("x"))
         {
         	  nombre += x == (int)x ? Integer.toString((int)x) : x; // Coordenada X del centro
         } else if (token.equals("y"))
         {
       	    nombre += y == (int)y ? Integer.toString((int)y) : y; // Coordenada Y del centro
         } else if (token.equals("anchura_total"))
         {
       	    nombre += anchuraTotal == (int)anchuraTotal ? Integer.toString((int)anchuraTotal) : anchuraTotal;
         } else if (token.equals("altura_total"))
         {
       	    nombre += alturaTotal == (int)alturaTotal ? Integer.toString((int)alturaTotal) : alturaTotal;
         } else if (token.equals("ancho_cuadrante"))
         {
       	    nombre += anchura.intValue();
         } else if (token.equals("resolucion"))
         {
       	    nombre += reso == (int)reso ? Integer.toString((int)reso) : reso;
         } else if (token.equals("filas"))
         {
       	    nombre += n_filas;
         } else if (token.equals("columnas"))
         {
       	    nombre += n_col;              
         } else if (token.equals("datum_calibrar"))
         {		
       	    nombre += WGS84 ? "WGS84" : "ED50";	
         } else if (token.equals("compresion"))
         {
       	    nombre += compresion;       
         } else
         {  
            JSigpac.ImprimirLinea("Error en el formato del nombre del fichero. No se reconoce el termino \"" + token + "\"");
            JSigpac.ImprimirLinea("Puede que tenga que ponerlo entrecomillado");      
         }
         //JSigpac.Traza("nombre: " + nombre);
       } catch (Exception exc) {
       	    JSigpac.ImprimirLinea("Error al generar nombre de fichero de forma automatica.");       	    
       	    return "";
       }         
     }
     return nombre;
   }
   
   
   String RecalcularCoordenadas()
   {
      double anchuraTotal_old, alturaTotal_old;	
      double fx, fy; // Son las coordenadas de lo que sería la esquina inferior derecha
      
      JSigpac.Traza("RecalcularCoordenadas()___________________________coorEsquina="+_coorEsquina);
      JSigpac.Traza(">>>>>>> Inicialmente: qx="+qx+"  x="+x+"   qy="+qy+"  y="+y);
      JSigpac.Traza(">>>>>>> Inicialmente: n_col="+n_col+"   n_filas="+n_filas);  
      // Lo primero es hacer que (qx, qy) sean las coordenadas de la esquina superior izquierda:
      if (_coorEsquina == true)
      {
	     double aux;
             coorEsquina = " -q";
             qx = x; // Guardamos la coordenada X de la esquina sup. izqda
             qy = y; // Guardamos la coordenada Y de la esquina sup. izqda             
      } else
      {  // Nos han dado las coordenada del centro y calcularemos las de la esquina:
            double aux, anchoCuadrante;
            coorEsquina = "";
                          
	    // Calculamos las coordenadas de la esquina sup izqda:
	    if (_anchuraTotal)
	       aux = anchura.doubleValue() / 2.0D;
	    else
               aux = (anchura.doubleValue() * n_col) / 2.0D;
            JSigpac.Traza("ancho: aux="+aux+"  anchura="+anchura.doubleValue()+"  n_col="+n_col);
            qx = x - aux;

            if (_anchuraTotal)
            {
               if (_altura)          
                  aux = alturaTotal / 2.0D;
               else
                  aux = anchura.doubleValue() / (2.0D*Mapa.FACTOR);
            } else
               aux = (anchura.doubleValue() * n_filas) / (2.0D*Mapa.FACTOR);
            JSigpac.Traza("alto: aux="+aux+"  anchura="+anchura.doubleValue()+"  n_filas="+n_filas);
            qy = y + aux;
      }
      JSigpac.Traza(">>>>>>> Paso 1 :      qx="+qx+"  x="+x+"   qy="+qy+"  y="+y);
      
      // Ahora, asignamos el valor de la anchura de un cuadrante
      // a la variable "opciones.anchura" y la anchura total a "opciones.anchuraTotal":
      if (_anchuraTotal == true)
      {
         anchuraTotal = anchura.doubleValue(); // Guardamos la anchura total //_xx_vv anchura.intValue();
         anchuraTotal_old = anchuraTotal;
         fx = qx + anchuraTotal;
         JSigpac.Traza("_anchuraTotal == true  anchuraTotal_old="+anchuraTotal_old);
         if (_reso)
         {  // Hay que calcular el valor de n_col y n_filas:    
            //Sigpac.Traza("_reso == true");   
            JSigpac.Traza("anchuraTotal / (Mapa.width * reso) = " + (anchuraTotal / (Mapa.width * reso)));    
            n_col = (int)Math.round( (anchuraTotal / (Mapa.width * reso)) + 0.49D );            
            if (_altura == false)
            {               
      	       alturaTotal = anchuraTotal;  
      	       JSigpac.Traza("Como _altura == false, alturaTotal="+alturaTotal);    	       
      	       _altura = true;      	       
      	    }      	    
      	    JSigpac.Traza("ANTES: n_filas="+n_filas);
            JSigpac.Traza("alturaTotal="+alturaTotal+"  Mapa.width="+Mapa.height+"  reso="+reso);
            JSigpac.Traza("( alturaTotal / (Mapa.height * reso) ) = " + ( alturaTotal / (Mapa.height * reso) ));
      	    n_filas = (int)Math.round( (alturaTotal / (Mapa.height * reso)) + 0.49D );    
      	    JSigpac.Traza("DESPUES: n_filas="+n_filas);  	
         } // Por ahora dejamos que habiendo puesto la opcion "-A", tambien se especifiquen las filas y columnas, por
           // lo menos para que desde línea de comando pueda seguir utilizándose como hasta ahora:
           // else
           //  return "No ha especificado la resolucion habiendo dado el ancho total del mapa";
         else
         {            
            reso = anchuraTotal / (n_col * Mapa.width);
            JSigpac.Traza("_reso == false    RecalcularCoordenadas I  reso = " + reso);
         }                   
         fy = qy - alturaTotal; 
         alturaTotal_old = alturaTotal;
         JSigpac.Traza("alturaTotal_old="+alturaTotal_old);   
         // Recalculamos la anchura y altura totales:
         anchuraTotal = n_col * Mapa.width * reso;
         alturaTotal = n_filas * Mapa.height * reso;
         JSigpac.Traza("Recalculamos:  anchuraTotal="+anchuraTotal+"  alturaTotal="+alturaTotal); 
         anchura = new Double(anchuraTotal / n_col);
         optAncho = " -A" + anchuraTotal;
         optAlto = " -H" + alturaTotal; 
         JSigpac.Traza("optAncho="+optAncho+"   optAlto="+optAlto);
      } else
      {      	 
      	 anchuraTotal = anchura.doubleValue() * n_col;
      	 anchuraTotal_old = anchuraTotal;
      	 fx = qx + anchuraTotal;
      	 alturaTotal = (anchura.doubleValue() * n_filas) / Mapa.FACTOR; 
      	 fy = qy - alturaTotal;
      	 alturaTotal_old = alturaTotal;
         optAncho=" -a" + anchura.toString();
         optAlto = "";
         JSigpac.Traza("_anchuraTotal == false  anchuraTotal="+anchuraTotal+"   alturaTotal="+alturaTotal);
         reso =  anchura.doubleValue() / Mapa.width;
         JSigpac.Traza("RecalcularCoordenadas II  reso = " + reso);
      }
      JSigpac.Traza("qx="+qx+"   qy="+qy+"    fx="+fx+"   fy="+fy);            
      // "x" e "y" deberan ser las coordenadas del punto central.
      // "qx" y "qy" ya son en estos momentos las coordenadas de la esquina sup. izqda.      
      if (_coorEsquina == true)
      {
	     double aux;             
             // Calculamos las coordenadas del punto central:
             aux = (anchura.doubleValue() * n_col) / 2.0D;
             x = qx + aux;
             aux = (anchura.doubleValue() * n_filas) / (2.0D*Mapa.FACTOR);  //_xx_vv_  (anchura.intValue() * n_filas) / (2.0D*Mapa.FACTOR);
	     y = qy - aux;
      }
      JSigpac.Traza(">>>>>>> Paso 2 :      qx="+qx+"  x="+x+"   qy="+qy+"  y="+y);      
      // Ahora hay que reajustar los valores si estamos trabajando contra la version 4 del visor del SigPac:
      if (JSigpac.servidor.EsVersion4())
      {    
      	 String error;
         if ((error = ComprobarResolucion()) != null)
            return error;	
        	 
      	 // La anchura de un cuadrante debe ser múltiplo de Mapa.width (256)
      	 double razon;
      	 long redondeo;
      	 double qx_old, qy_old;
      	 double qxS, qyS; // Contienen los valores de qx y qy en el datum del servidor
      	 double fxS, fyS; // Contienen los valores de fx y fy en el datum del servidor
      	 Coordenada q, qS, f, fS;
      	 
      	 // Guardamos los valores iniciales para poder calcular al final el ancho necesario para
      	 // que el mapa final abarque la zona deseada: (previamente hemos almacenado los valores 
      	 // de la anchura y altura totales)      	       	 
      	 JSigpac.Traza("datumJSigpac="+datumJSigpac.miString()+"    datumServidor="+datumServidor.miString()); 
      	 //JSigpac.Traza("anchuraTotal="+anchuraTotal+"   anchuraTotal_old="+anchuraTotal_old);
         if (datumJSigpac == datumServidor)
         {
            qxS = qx;
            qyS = qy;
            fxS = fx;
            fyS = fy;
            qx_old = qx;
      	    qy_old = qy;      
         } else
         {
            q = new Coordenada(datumJSigpac, qx, qy, 0, huso.byteValue(), true); 
            qS = q.CambioDeDatum(datumServidor);
            qxS = qS.getX();
            qyS = qS.getY(); 
            f = new Coordenada(datumJSigpac, fx, fy, 0, huso.byteValue(), true); 
            fS = f.CambioDeDatum(datumServidor);
            fxS = fS.getX();
            fyS = fS.getY();
            qx_old = qxS;
      	    qy_old = qyS;                  
         }
      	       	       	 
      	 JSigpac.Traza("Valores iniciales:_____________");
      	 JSigpac.Traza("anchura="+anchura.doubleValue());
      	 JSigpac.Traza("anchuraTotal="+anchuraTotal);
      	 JSigpac.Traza("qx="+qx+"  qxS="+qxS+"   fxs="+fxS);
      	 JSigpac.Traza("qy="+qy+"  qyS="+qyS+"   fys="+fyS);
      	 //JSigpac.Traza("x="+x);
      	 //JSigpac.Traza("y="+y);
      	 JSigpac.Traza("reso="+reso);
      	 
      	 if (anchura.doubleValue() < Mapa.width)
      	 {
      	    razon = anchura.doubleValue() / Mapa.width;
      	    if (razon <= 0.1875D)
      	       anchura = new Double(Mapa.width / 8.0D);
      	    else if (razon <= 0.375)	    
      	       anchura = new Double(Mapa.width / 4.0D);
      	    else
      	       anchura = new Double(Mapa.width / 2.0D);
      	 } else 
      	 {
      	    razon = anchura.doubleValue() / Mapa.width;
      	    redondeo = Math.round(razon);  
       	    if (razon != redondeo)      	    
      	       anchura = new Double(redondeo * Mapa.width);
      	 }
      	 anchuraTotal = anchura.doubleValue() * n_col;
      	 JSigpac.Traza("Ajustamos a anchuraTotal="+anchuraTotal);
      	 reso =  anchura.doubleValue() / Mapa.width;
      	 //JSigpac.Traza("RecalcularCoordenadas III  reso = " + reso);
                     	 
      	 // Continuamos con la X y la Y de la coordenada UTM de la esquina superior izquierda
      	 // (que también tiene que ser múltiplo de algo ya que el SigPac devuelve cuadraditos de 256 x 256):
      	 int r = (int) (reso * 1000);      	 
      	 //JSigpac.Traza("qxS*100 / width*r = " + (qxS * 1000) / (Mapa.width * r));  
         long vi = Math.round( (qxS * 1000) / (Mapa.width * r) - 0.499D );                       
         JSigpac.Traza("vi="+vi);             
         //JSigpac.Traza("    qyS*100 / height*r = " + (qyS * 1000) / (Mapa.height * r));
         long vj = Math.round( ((qyS * 1000) / (Mapa.height * r)) + 0.499D );         
         JSigpac.Traza("vj="+vj);
         qxS = (vi * Mapa.width * r) / 1000;
         qyS = ((vj) * Mapa.height * r) / 1000;   
         JSigpac.Traza("qxS="+qxS+"  qyS="+qyS);      
         
         // En este momento hay que ver si con la esquina (qxS, qyS) y el ancho y alto del mapa abarcamos
         // toda la zona que queriamos inicialmente (especialmente en lo que concierne a la esquina inferior derecha):
         // *Nota: Vamos a trabajar sólo con la parte entera para evitar imprecisiones debidas a cambios de datum (cuando el datum del jSigpac es distinto que el datum del servidor). 
         //JSigpac.Traza("(int)qxS + anchuraTotal = " + ((int)qxS + anchuraTotal) + "  (int)qx_old + anchuraTotal_old = " + ((int)qx_old + anchuraTotal_old));
         //if (((int)qxS + anchuraTotal) < ((int)qx_old + anchuraTotal_old))
         JSigpac.Traza("((int)qxS + anchuraTotal) < fxS)  >>>   "+((int)qxS + anchuraTotal) +  " < " + fxS);
         if (((int)qxS + anchuraTotal) < fxS)
         {
            n_col++;	
            anchuraTotal = anchura.doubleValue() * n_col;            
         } 
         JSigpac.Traza("n_col="+n_col+"  anchuraTotal="+anchuraTotal);
         //JSigpac.Traza("qyS - alturaTotal = " + (qyS - alturaTotal) + "  qy_old - alturaTotal_old = " + (qy_old - alturaTotal_old));         
         //if ((qyS - alturaTotal) > (qy_old - alturaTotal_old))
         JSigpac.Traza("((int)qyS - alturaTotal) > fyS)  >>>   "+((int)qyS + alturaTotal)+  " < " + fyS);
         if (((int)qyS - alturaTotal) > fyS)
         {
            n_filas++;	
            alturaTotal = (anchura.doubleValue() * n_filas) / (Mapa.FACTOR);            
         } 
         JSigpac.Traza("n_filas="+n_filas+"  alturaTotal="+alturaTotal);
         
         // Ponemos en qx y qy los valores correspondientes a partir de qxS y qyS:
         if (datumJSigpac == datumServidor)
         {
            qx = qxS;
            qy = qyS;                
         } else
         {
            qS = new Coordenada(datumServidor, qxS, qyS, 0, huso.byteValue(), true); 
            q = qS.CambioDeDatum(datumJSigpac);
            qx = q.getX();
            qy = q.getY();                            
         }
         
         // A partir de la coordenada de la esquina y de la anchura, calculamos la coordenada del centro:
         double aux;
         aux = (anchura.doubleValue() * n_col) / 2.0D;                  
         x = qx + aux;
         aux = (anchura.doubleValue() * n_filas) / (2.0D*Mapa.FACTOR);
         y = qy - aux;
         
         JSigpac.Traza("Valores finales:____________");
      	 JSigpac.Traza("anchura="+anchura.doubleValue());
      	 JSigpac.Traza("anchuraTotal="+anchuraTotal);
      	 JSigpac.Traza("qx="+qx+"  qxS="+qxS);
      	 JSigpac.Traza("qy="+qy+"  qyS="+qyS);
      	 //JSigpac.Traza("x="+x);
      	 //JSigpac.Traza("y="+y);
      	 JSigpac.Traza("reso="+reso);
      	 JSigpac.Traza("n_col="+n_col+"   n_filas="+n_filas);
      	 
      } // Fin EsVersion4()
      JSigpac.Traza(">>>>>>> Paso 3 :      qx="+qx+"  x="+x+"   qy="+qy+"  y="+y);  
      
      if (_anchuraTotal == true)
      {
         optAncho = " -A" + anchuraTotal;
	 optAlto = " -H" + alturaTotal;
      } else
      {
      	 optAncho=" -a" + anchura.toString();
         optAlto = "";
      }
            
      JSigpac.Traza("optAncho="+optAncho+"   optAlto="+optAlto);
      return null;
   }  /* Fin de RecalcularCoordenadas  */
   

  /*
    Valores válidos para la resolución según el tipo de mapa para la version 4.0 del SigPac:
    ORTOFOTO:          0.5, 1, 2, 4
    TOPOGRAFICO: 
           MTN_SIGPAC: 4096, 2048
           MTN_2000:   1024, 512
           MTN_1250:   256, 128, 64
           MTN_200:    32, 16
           MTN_25:     8, 4, 2
           
    Si todo va bien, se devuelve "null". En caso de ir mal, se devuelve un String con el mensaje de error.
  */
  String ComprobarResolucion()
  {           
    System.out.println("Servidor nombre=" + JSigpac.servidor.nombre);
    
    if (JSigpac.servidor.EsVersion4() && JSigpac.servidor.Id() != Servidor.IBERPIX)
    {       
       // Comprobamos que la resolucion pedida es acorde al tipo de mapa (Ortofoto o topografico) y a la escala:
       if (ortofoto)
       {
       	  if (reso != 0.125 && reso != 0.250 && reso != 0.5 && reso != 1 && reso != 2 && reso != 4 )     	  
             return "Las resoluciones válidas para ORTOFOTO son: 0.125, 0.250, 0.5, 1, 2, y 4 metros/pixel  (reso="+reso+")";               	  
       } else  // Se trata de un mapa topografico:
       {      
          // Dependiendo de la escala, hara unas resoluciones válidas:
          if (escala.equals("MTN_SIGPAC") && reso != 2048 && reso != 4096)                                    
                return "Las resoluciones válidas para TOPOGRAFICO MTN_SIGPAC son: 2048 y 4096 metros/pixel";                
  	  else if (escala.equals("MTN_2000") && reso != 512 && reso != 1024)          	             
                return "Las resoluciones válidas para TOPOGRAFICO MTN_2000 son: 512 y 1024 metros/pixel";                
  	  else if (escala.equals("MTN_1250") && reso != 64 && reso != 128 && reso != 256)          	             
                return "Las resoluciones válidas para TOPOGRAFICO MTN_1250 son: 64, 128 y 256 metros/pixel";                
  	  else if (escala.equals("MTN_200") && reso != 16 && reso != 32)          	             
                return "Las resoluciones válidas para TOPOGRAFICO MTN_200 son: 16 y 32 metros/pixel";                
  	  else if (escala.equals("MTN_25") && reso != 2 && reso != 4 && reso != 8)          	             
                return "Las resoluciones válidas para TOPOGRAFICO MTN_25 son: 2, 4 y 8 metros/pixel";                  	  
       } // Fin else topografico.
    } // Fin EsVersion4()
    
    return null;
 }   
 
 String ValidarFormulario()
 {    
    int serv = JSigpac.servidor.Id();
    
    switch (serv)
    {    	
    	case Servidor.NACIONAL:
    	   // He comprobado que los cuadrantes del servidor nacional siempre tienen la
    	   // mancha amarilla situada en el mismo sitio por lo que es imposible eliminarla:
    	   if (quitaManchas)
    	      return "En el servidor Nacional no tiene sentido la opción de \"Quita manchas\". Desactívela";
    	   break;
    	case Servidor.PNOA:
    	   if (!ortofoto)
    	      return "Para el servidor de PNOA, sólo están disponibles las ortofotos";
    	   break;
    	case Servidor.CyL:
    	   if (!ortofoto)
    	      if (escala.equals("MTN_25"))
    	         return "Para el servidor de Castilla y León, no es posible el topográfico 1:25000";
    	   break;  
    	case Servidor.MANCHA:
    	   if (quitaManchas)
    	      return "En el servidor de Castilla la Mancha no tiene sentido la opción de \"Quita manchas\". Desactívela";
    	   break;
    	case Servidor.CANARIAS_SIGPAC:
    	   // El servidor de Canarias no tiene manchas de ningún tipo:
    	   if (quitaManchas)
    	      return "En el servidor de Canarias no tiene sentido la opción de \"Quita manchas\". Desactívela";
    	   break;  
    	//case 10:  // Esto era para el servisor de TresCantos
    	//   return "De momento, no funcionan las descargas desde el servidor de la Comunidad de Madrid";
    	   //break; 
    	case Servidor.CANARIAS_SITCAN:
    	   // En el servidor del Gobierno de Canarias (SITCAN) hay que tener en cuenta que solo utiliza el huso 28 y que
    	   // la zona occidental de la isla de El Hierro (perteneciente al huso 27) la referencia utilizando coordenadas
    	   // del huso 28 (con lo cual, es necesario mirar las coordenadas en el propio visor del SITCAN, no en las del sigPac.
    	   if (huso.byteValue() != 28)
    	      return "El SITCAN trabaja siempre con coordenadas del huso 28";
    	   break;
    	case Servidor.MADRID:
    	   if (!ortofoto)
    	      if (escala.equals("MTN_1250") || escala.equals("MTN_25"))
    	         return "Para el servidor de Madrid, no estan disponibles las escalas 1:25000 ni 1:1250000";
    	   break;
    	case Servidor.RIOJA:
    	   if (!ortofoto)
    	      if (!escala.equals("MTN_200") && !escala.equals("MTN_2000"))
    	         return "Para el servidor de la Rioja, los topográficos válidos son 1:200000 y 1:2000000";
    	   break;
    	case Servidor.IGN:
    	   //System.out.println("_ll_ vistaPajaro=" + vistaPajaro);
    	   if (ortofoto)
    	      return "Para el servidor del IGN sólo se admiten mapas topográficos";    	      
    	   //else if (!vistaPajaro)
    	   //   return "Debido a ciertos problemas de ensamblaje, por ahora no funciona esta opción";    	   
    	   break;    	 	   	
    	case Servidor.ANDALUCIA:
    	case Servidor.IDEANDALORTO:
    	   if (!ortofoto)
    	      return "Para el servidor de Andalucía sólo están disponibles las ortofotos";
    	   break;    
    	case Servidor.CVALENCIANA:
    	   if (!ortofoto)
    	      return "Para el servidor de la Generalitat Valenciana sólo están disponibles las ortofotos";
    	   break; 
    	case Servidor.ARAGON_SITAR:
    	   if (!ortofoto)
    	      return "Para el servidor del Gobierno de Aragon del SITAR sólo están disponibles las ortofotos";
    	   break;  	   	   
        case Servidor.NAVARRA: 	
    	case Servidor.NAVARRA1000:   	
    	case Servidor.NAVARRA1950:
    	   if (!ortofoto)
    	      return "Para el servidor del Gobierno de Navarra de IDENA/SITNA sólo están disponibles las ortofotos";
    	   break;   	   	   	 	   	
    }
    
    return null;
 }
 
 public String[] GenerarArgumentos(OpcionesMapa op, int m_ini, int n_ini, int m_fin, int n_fin, String coletilla)
 {
    String elservidor;
    String[] args;
    double coorX, coorY;
    int num_arg = 10; // Aqui ponemos el numero de argumentos minimo
    int i=0;
   	
    //JSigpac.ImprimirLinea("GenerarArgumentos  entrar...");
    
    if (JSigpac.servidor.Id() == -1)
       elservidor = null;
    else
    {
       elservidor = "-S" + JSigpac.servidor.Abreviatura();
       num_arg++;
    }
    
    if (op.generarFichsCalibracion)
       num_arg++;   
    if (op.WGS84)
       num_arg++;
    if (op.soloTrack)
      num_arg++;
    if (op.noPreguntarMas)
      num_arg++;     
    if (op.borrarIntermedios)
      num_arg++;            
        	
    args = new String[num_arg];
   	
    if (elservidor != null)
       args[i++] = elservidor;
    
    args[i++] = op.datumJSigpac == Datum.datumED50 ? "-DED50" : "-DWGS84";
    args[i++] = op.tipoMapa;
    args[i++] = "-q";
    args[i++] = "-h" + op.huso;   	
    args[i++] = "-a" + op.anchura;
    coorX = qx + (n_ini*op.anchura);
    args[i++] = "-x" + coorX;
    coorY = qy - (m_ini*(op.anchura / Mapa.FACTOR));
    args[i++] = "-y" + coorY;
    args[i++] = "-fil" + (m_fin-m_ini);   // Igual aquí hay problemas y hay que restar 1  !!!!!!
    args[i++] = "-col" + (n_fin-n_ini);
    if (op.generarFichsCalibracion)
       args[i++] = "-c";
    if (op.WGS84)
       args[i++] = "-w";
    if (op.soloTrack)
       args[i++] = "-t";   
    if (op.borrarIntermedios)
       args[i++] = "-B";
         
    args[i++] = "-f" + op.raiz + coletilla;

    if (op.noPreguntarMas) //kkmail
    {
       if (op.sobreescribir)
          args[i++] = "-Ys";
       else
          args[i++] = "-Yn";
    }           
   	
    return args;
  }
  
  
  void DeterminarFicherosDeCalibracion()
  {
    if (!generarFichsCalibracion)
       return;
    
    File dat = new File("dat" + File.separator + "jsigpac.calibrar");
    FileInputStream fis=null;
    
    if (dat.exists())
    {
      try {
         fis = new FileInputStream(dat);
	 Properties misProperties = new Properties();
         misProperties.load(fis);
         //misProperties.list(System.out);
	 calibrarOzi = !(misProperties.getProperty("Ozi", "no").toLowerCase().equals("no"));	     	 
         calibrarCompe = !(misProperties.getProperty("Compe", "no").toLowerCase().equals("no"));
         calibrarGPSTuner = !(misProperties.getProperty("GPSTuner", "no").toLowerCase().equals("no"));
         calibrarPathAway = !(misProperties.getProperty("PathAway", "no").toLowerCase().equals("no"));
         calibrarJGW = !(misProperties.getProperty("JGW", "no").toLowerCase().equals("no"));
         calibrarGlobalMapper = !(misProperties.getProperty("GlobalMapper", "no").toLowerCase().equals("no"));
         calibrarERS = !(misProperties.getProperty("ERS", "no").toLowerCase().equals("no"));
         calibrarTracky = !(misProperties.getProperty("Tracky", "no").toLowerCase().equals("no"));
         calibrarMyMotion = !(misProperties.getProperty("MyMotion", "no").toLowerCase().equals("no"));
         calibrarTomTom = !(misProperties.getProperty("TomTom", "no").toLowerCase().equals("no"));
      } catch (FileNotFoundException fnfe) {
	 System.err.println("DeterminarFicherosDeCalibracion: No se ha encontrado el fichero: " + dat.getPath());
      } catch (SecurityException se) {
	 System.err.println("Acceso de lectura denegado al fichero: " + dat.getPath());
      } catch (IllegalArgumentException se) {
	 System.err.println("Formato incorrecto del fichero: " + dat.getPath());
      } catch (IOException se) {
	 System.err.println("Error al intentar leer el fichero: " + dat.getPath());
      }	finally {
      	 try {
           if (fis != null)
           {
      	      fis.close();      
      	      fis = null;
      	   }
         } catch (IOException ioe) {}      	
      } 	
    } else // No existe el fichero donde se especifica en qué formatos generar los ficheros de calibración:
    {
       JSigpac.ImprimirLinea("No existe el fichero: " + dat.getAbsolutePath());
       JSigpac.ImprimirLinea("Se calibraran los mapas para Ozi y Compe solamente");
       CalibracionesPorDefecto();
    }
  }
}



class OpcionesEnsamblado 
{   
   boolean _n_filas;
   int n_filas; // Numero de filas del mosaico final
   boolean _n_col;
   int n_col; // Numero de columnas del mosaico final
   boolean _raiz;
   String raiz; // Nombre base de los ficheros. Por defecto sera "img_"
   File directorio=null;

   boolean _compresionJPEG;
   int compresionJPEG;  // Factor de compresion JPEG
   boolean _mapasVertical;
   int mapasVertical;
   boolean _mapasHorizontal;
   int mapasHorizontal;

   public void Imprimir()
   {    
     JSigpac.ImprimirLinea("raiz="+raiz); 
     JSigpac.ImprimirLinea("n_filas="+n_filas+"   n_col="+n_col);
     JSigpac.ImprimirLinea("compresionJPEG="+_compresionJPEG);
     JSigpac.ImprimirLinea("mapasVertical="+mapasVertical+"   mapasHorizontal="+mapasHorizontal);          
   }

   public OpcionesEnsamblado()
   {
     Inicializar();
   }
   
   void Inicializar()
   {
      JSigpac.Traza("OpcionesEnsamblado::Inicializar");	
      // Hay que restaurar las condiciones iniciales: 
      _n_filas = false;
      _n_col = false;
      _raiz = false;
      _compresionJPEG = false;
      _mapasHorizontal = false;
      _mapasVertical = false;
      raiz = null;
      //directorio = null;  // No lo pongo a null porque si no perdemos el valor entre una llamada y otra. 
                            // Es decir, si primero descargábamos, despues al ensamblar no sabía en qué directorio hacerlo.                          
      compresionJPEG = 70;      
   }

   /*
       Una vez que se han leido todos los argumentos habra
       que ver si estan todos los parametros obligatorios.
       Se devuelve un mensaje con el error o null si todo va bien:
    */

   String EstanTodosLosParametros()
   {   
     //JSigpac.Traza("Estamos en Ensamblador::opciones::EstanTodosLosParametros");              
     if (!_n_filas)
        return "Debe introducir el numero de filas (-efil) a ensamblar";       
     else if (!_n_col)
        return "Debe introducir el numero de columnas (-ecol) a ensamblar";        
     else if (!_raiz)
        return "No ha especificado el nombre raiz de los ficheros a ensamblar";                      

     return null;
   }
}

