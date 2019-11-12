import java.io.*;
import java.net.*;
import java.util.*;


class Descargar implements Runnable
{

 String nomFichBase;
 String direccion;
 OpcionesMapa opciones;
 String servidor;
 int i, j;
 boolean error = false;
 Monitor miMonitor;
 int fil, col;
 int id_thread;
 boolean imprimir = false; // Para sacar trazas relacionadas con los threads

 public Descargar(String nombre_fichero, String dir,
                  OpcionesMapa op, String server, int I, int J, Monitor monitor)
 {
   //super(thg, "Descargar");   
   nomFichBase = nombre_fichero;
   direccion = dir;
   opciones = op;
   servidor = server;
   i = I;
   j = J;   
   error = false;     
   miMonitor = monitor;
   if (monitor != null)
   {
      fil = monitor.getFilas();
      col = monitor.getColumnas();
   } else 
   {
      fil = op.n_filas;
      col = op.n_col;	
   }
   id_thread = (i*col) + (j+1);
   //setName(String.valueOf(id_thread));
   InicializarTrazas();
   Imprimir("Constructor de i="+i+"  j="+j+"   fil="+fil+"  col="+col+"   id_thread="+id_thread); 
 }

 
 public Descargar(String nombre_fichero, String dir, 
                  OpcionesMapa op, String server, int I, int J)
 {
   this(nombre_fichero, dir, op, server, I, J, null);
 }
 
 public boolean InicializarTrazas()
 {
   File fi;
   fi = new File("tracear_Descargar");
   //JSigpac.ImprimirLinea("directorio de tracear: " + fi.getAbsolutePath());
   if (fi.exists())
      imprimir = true;
   else
      imprimir = false;
   //JSigpac.ImprimirLinea("tracear_Descargar="+imprimir);
   return imprimir; 	 	
 }
 
 public void Imprimir(String linea)
 {
   if (imprimir)
      System.out.println("-TRAZA_Descargar- " + linea);
 }
 
 public void ImprimirT(String linea)
 {
   if (imprimir)    // && id_thread == 11)
      System.out.println(linea);
 }
 
 public void run()
 {
   String fichero=null;
   FileOutputStream fos=null;
   byte[] datos=null;
   URLConnection conexion;
   InputStream is=null;
   int num_imagenes=1, n=0;
   error = false;   
   //int id_thread = (i*col) + (j+1);

   Imprimir("\t" + id_thread + "- run de " + toString());
      
   if (miMonitor != null && !JSigpac.hayThreads)
   {
      if (miMonitor.PedirPermiso(id_thread) == false)
      {
      	 Imprimir("Finaliza Descarga-"+id_thread);
         return;  // Se acaba el thread
      }
      Imprimir(id_thread + "- He vuelto de PedirPermiso  " + toString());
      miMonitor.ultimoThread = Thread.currentThread(); //this;
   }
      
   ImprimirT("El thread " + id_thread + "  inicia el proceso de descarga del cuadrante ("+i+","+j+")");
   
   try {
     ImprimirT("Vamos a hacer el new URL");
     //_cc_ URL url = new URL(servidor + "aspimagedispatcher.aspx?guid=c670f580deda49f691de71e85b6bd921&npetic=42&function=getutmbitmap&"+ utm +"&Width=618&Height=421&Zone="+opciones.huso);
     URL url = new URL(direccion);
     ImprimirT("direccion: " + direccion);
     //URL url = new URL("http://sigpac.mapa.es/sigpac/imagenes/aspimagedispatcher.aspx?function=getutmbitmap&"+ utm +"&Width=618&Height=421&Zone="+opciones.huso);

     //JSigpac.ImprimirLinea("URL: "+url.toString());

     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=c10d7ba9eb534a42be36bcf0faa1ef39&npetic=22&function=swf&" + utm + "&Zone="+opciones.huso + "&Width=618&Height=421&layers=" + opciones.capa);
     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=c10d7ba9eb534a42be36bcf0faa1ef39&npetic=22&function=swf&" + utm + "&Zone="+opciones.huso + "&Width=618&Height=421&layers=PARCELA");

     if (opciones.quitaManchas == true)
	num_imagenes = 4;
     else
	num_imagenes = 1;

     ImprimirT("num_imagenes = " + num_imagenes);
     
     int leidos, acumulados, num_reintentos;
     int max_reintentos = 2;
     for (n=0; n<num_imagenes; n++)
     {
         //System.out.println("Vamos a hacer el openConnection  utm="+utm);
         ImprimirT("Voy a hacer el openConnection");
         /*
         conexion = url.openConnection();
         conexion.setConnectTimeout(5000);
         conexion.setReadTimeout(5000);
	 conexion.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; es-ES; rv:1.8.0.2) Gecko/20060308 Firefox/1.5.0.2");
	 //conexion.setRequestProperty("Host", "sigpac.mapa.es");
	 //conexion.setRequestProperty("Host", "www.ide.es");
	 conexion.setRequestProperty("Host", url.getHost());
	 conexion.setRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
	 */
	 //conexion.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");	 
	 /*
	 conexion.setRequestProperty("Accept-Encoding", "gzip,deflate");
	 conexion.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
	 conexion.setRequestProperty("Connection", "keep-alive");
	 conexion.setRequestProperty("Keep-Alive", "300");
	 conexion.setRequestProperty("Content-type", ""); // application/x-www-form-urlencoded
         */
         // Para poder usar el servidor de Madrid puede que haga falta esta cookie:
         // Otra cosa diferente de la peticion al servidor de Madrid es que no pone TOPOGRAFICO_MTN2000 si no solo MTN2000
         //conexion.setRequestProperty("Cookie", "JSESSIONID=awTTW29kgvT9; SS_X_JSESSIONID=c0a80b0cce6e663e1588d92471ba4ced7c457edc3c5.okbHml1GokbKa3qIpk5yqAzy-AXMnNCNaN0McybtahyM-x4Qc2SLagSLawSMmA8PaheIah0PbkaRaN0Qb6aI-hrAnAmxox9znhyKb2TDmQPvq6LDmR0KbMTHmkfMokeIpR9B8N4LaNmOahaOcgb48ObJpBfzpBfhnl9Qnl8xn6jAmljGr5XDqQLvpAe_");


	 //conexion.setRequestProperty("Cache-Control", "no-cache");

         //System.out.println("Vamos a hacer el getInputStream");
         
	      
         if (num_imagenes == 1)
	    fichero = nomFichBase + ".jpg";
         else
	    fichero = nomFichBase + "_" + n + ".jpg";

         ImprimirT("fichero=" + fichero);
         is = null;
         num_reintentos = 0;
         int disponibles;
         
         do {
           conexion = url.openConnection();
           conexion.setConnectTimeout(20000); // * (num_reintentos+1));
           conexion.setReadTimeout(20000); // * (num_reintentos+1));
	   conexion.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; es-ES; rv:1.8.0.2) Gecko/20060308 Firefox/1.5.0.2");
	   //conexion.setRequestProperty("Host", "sigpac.mapa.es");
	   //conexion.setRequestProperty("Host", "www.ide.es");
	   conexion.setRequestProperty("Host", url.getHost());
	   conexion.setRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
	   conexion.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");	 
	   conexion.setRequestProperty("Accept-Encoding", "gzip,deflate");
	   conexion.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
	   conexion.setRequestProperty("Connection", "keep-alive");
	   conexion.setRequestProperty("Keep-Alive", "300");
	   conexion.setRequestProperty("Content-type", ""); // application/x-www-form-urlencoded
	   
           ImprimirT("Voy a hacer el getInputStream");
           //Calendar calendario = new GregorianCalendar();
           //JSigpac.Traza("HORA: " + calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND));
           //JSigpac.Traza("ConnectTimeout=" + conexion.getConnectTimeout());
           //JSigpac.Traza("ReadTimeout=" + conexion.getReadTimeout());
           //System.err.println("Se abre fos y is: " + fichero);
           if (is == null)
           {
              is = conexion.getInputStream();
              //JSigpac.Traza("Hecho el getInputStream");
           }
           fos = new FileOutputStream(fichero);
           //JSigpac.Traza("Hecho el FileOutputStream");
           acumulados = 0;
           leidos = 0;
           datos = new byte[120000]; //[65536];  //[8192];
           
           //JSigpac.Traza("Bytes que puede leerse (available): " + is.available());
           
           /*
           disponibles = 0;
           
           while ((disponibles=is.available()) > 0)
           {               
               //JSigpac.Traza("disponibles=" + disponibles + "   Vamos a hacer el read");	
               if ((leidos=is.read(datos)) != -1)
               {
                  acumulados += leidos;
	          fos.write(datos, 0, leidos);
	          //JSigpac.Traza("Hemos hecho el write  de " + leidos + " bytes");
	       } //else
	          //JSigpac.Traza("Ha fallado el read aunque el available era bueno");
	       try {          	   
            	   Thread.sleep(500);
               } catch(InterruptedException ie) {
         	   //System.out.println("Dentro del bucle isAlive 1");
               }
	   }
	   */
           try {
           while ((leidos=is.read(datos)) != -1)
           {
               JSigpac.Traza("Hemos hecho el read (leidos="+leidos+"   datos="+datos);	
               acumulados += leidos;
               
               if (leidos < 40)  // Si se pide un cuadrante fuera de la "órbita" del SigPac, devuelve el mensaje: "<h2>Error generando baldosa</h2>"
               {
               	  String str = new String(datos,0,32);
               	  if (str.indexOf("Error generando baldosa") != -1)
               	  {
               	     // Damos por supuesto que ese cuadrante está fuera de la "órbita" del SigPac por lo que no reintentamos su descarga
               	     num_reintentos = max_reintentos + 2;  // Para forzar la salida del bucle exterior y no reintentar la descarga.
               	     acumulados = 6666666;
               	     // Creamos un cuadrante en blanco pero primero cerramos el fichero que tenemos abierto:
               	     if (fos != null)
	             {
	                //JSigpac.Traza("Se cierra fos: " + fichero);
	                fos.close();
	                fos = null;
                     }
                     // Necesitamos saber los valores de ancho y alto en pixeles del cuadrante:
                     JSigpac.Traza("El cuadrante " + fichero + "  queda fuera de la orbita del SigPac.");
                     ThreadEnsamblar.CrearCuadrante((int)Mapa.width, (int)Mapa.height, fichero);
                     /*
                     int ancho, alto;
                     if (miMonitor != null && miMonitor.misigpac != null && miMonitor.misigpac.miMapa!= null)
                     {
                     	ancho = (int)miMonitor.misigpac.miMapa.width;
                     	alto = (int)miMonitor.misigpac.miMapa.height;
                     	ThreadEnsamblar.CrearCuadrante(ancho, alto, fichero);
                     	JSigpac.Traza("*******====== ***** HEMOS HECHO EL CUADRANTE BLANCO  ________________");
                     } else
                        JSigpac.Traza("*******====== *****  NO PODEMOS CONOCER LAS DIMENSIONES DEL CUADRANTE  **** ");                                       	     
                     */
               	     break; // Para abandonar este bucle y no seguir leyendo.
               	  }
               }
               
	       fos.write(datos, 0, leidos);
	       //JSigpac.Traza("Hemos hecho el write  disponibles=" + is.available());
	   }
	   } catch (SocketTimeoutException ste) {	   	
	   	acumulados = 0;
	   	JSigpac.Traza("Salta el timeout para " + fichero + "   num_reintentos=" + num_reintentos + "   SocketTimeoutException:" + ste);	   	
	   }
           
           
           ImprimirT("leidos="+leidos+"   acumulados = " + acumulados);
           //JSigpac.Traza("acumulados = " + acumulados);
           
           if (is != null)
           {
              //JSigpac.Traza("Se cierra is");
              is.close();
              is = null;              
           }
                
           if (fos != null)
	   {
	      //JSigpac.Traza("Se cierra fos: " + fichero);
	      fos.close();
	      fos = null;
           }
           
         } while (acumulados < 600 && (num_reintentos++ < max_reintentos));
         
         if (acumulados < 600)
            error = true;
            
         if (is != null)
         {
            is.close();
            is = null;
            //JSigpac.Traza("Se cierra is");
         }
         if (fos != null)
	 {
	    //JSigpac.Traza("Se cierra fos: " + fichero);
	    fos.close();
	    fos = null;
         }
           
         if (num_reintentos >= 1 && num_reintentos < (max_reintentos + 2))
            JSigpac.ImprimirLinea("\tEl cuadrante fila="+ i + "  col="+j+"  img="+n+"  se ha intentado descargar " + num_reintentos + " veces");
            //JSigpac.ImprimirLinea("\tEste cuadrante se ha reintentado descargar " + num_reintentos + "veces");
         ImprimirT("Fin del for");
      } // Fin del for

/*
      if (i!=-1 && j!=-1)
      {
         JSigpac.ImprimirLinea("\t(" + Mapa.pad(i + "," + j + ")", 7), false);
         //System.out.println("\t(" + i + "," + j + ") ");
      }
*/         
      File aux = new File(nomFichBase+".jpg");
      ImprimirT("Creado el fichero: "+ aux.getName());
      String auxnom, linea;
      int lonnom;
      lonnom = aux.getName().length();
      if (lonnom > 60)
         auxnom = aux.getName().substring(0,40) + "..." + aux.getName().substring(lonnom-9, lonnom);
      else
         auxnom = aux.getName();
         
      if (i!=-1 && j!=-1)
      {
      	 linea = "\t(" + Mapa.pad(i + "," + j + ")", 7) + "Fichero: " + auxnom;
         //JSigpac.ImprimirLinea("\t(" + Mapa.pad(i + "," + j + ")", 7), false);         
      }
      else
         linea = "Fichero: " + auxnom;
      
      JSigpac.ImprimirLinea(linea);  
      
      //JSigpac.ImprimirLinea("Fichero: "+auxnom); //aux.getName()); //nomFichBase+".jpg");
      //System.out.println("\tFichero: "+aux.getName());
      
      if (opciones.quitaManchas == true)
      {
         // Aqui es donde debo lanzar el Thread que quite las marcas de agua:
         QuitaManchas ariel = new QuitaManchas(nomFichBase);
	 ariel.start();
	 
	 // Debemos esperar a que finalice el thread "QuitaManchas" para salir del thread "Descargar"
	 // ya que si se ha elegido "descargar y ensamblar", la última imagen puede no estar disponible
	 // para ensamblarse hasta pasados unos segundos:  
	 //if (i == fil-1 && j == col-1)
	 //{        
            while (ariel.isAlive())
            {
               //System.out.println("Antes del try isalive 1");	  
               //Thread mith = Thread.currentThread(); 
               try {
          	   //mith.sleep(500);
            	   Thread.sleep(500);
               } catch(InterruptedException ie) {
         	   //System.out.println("Dentro del bucle isAlive 1");
               }
            }
            //System.out.println("Estamos fuera del bucle isAlive 1");
         //}
         
      }      

   } catch (UnknownHostException e) {
     error = true;
     System.err.println("UnknownHostException (fichero=" + fichero + "): " + e); //return;
   } catch (MalformedURLException e) {
     error = true;
     System.err.println("MalformedException (fichero=" + fichero + "): " + e); //return;
   } catch (FileNotFoundException fnfe) {
     error = true;
     if (fnfe.getMessage().indexOf("http://") != -1)
     {
        System.err.println("\n\tError (temporal) al intentar conectarse al SIGPAC");
        System.err.println("\tComprueba si desde tu navegador puedes conectarte a:");
        System.err.println("\t\thttp://sigpac.mapa.es/fega/visor");
     }
     else
        System.err.println("\n\tNo existe el directorio especificado:"+fnfe);    
   } catch( IOException e) {
     error = true;
     System.err.println("Descargar::IOException fichero=" + fichero + "): " + e); //return;
   } catch(OutOfMemoryError oome) {
     error = true;
     System.err.println("EXCEPCION OutOfMemoryError en Descargar  _________  fichero=" + fichero);
   } catch(Throwable th) {
     error = true;	
     System.err.println("EXCEPCION Throwable en Descargar_________________ fichero=" + fichero);
   } finally {
     ImprimirT("Estamos en el finally");
     if (fos != null)
     {
     	try {  
          //JSigpac.Traza("Se intenta cerrar fos");    	     	  
	  fos.close();
	  fos = null;
	  //JSigpac.Traza("Se cierra fos");
	} catch (IOException ioe) {}
     }
     if (is != null)
     {
     	try {   
     	  //JSigpac.Traza("Se intenta cerrar is");       	  	
	  is.close();
	  is = null;
	  //JSigpac.Traza("Se cierra is");
	} catch (IOException ioe) {}
     }
     if (error) // _kkmail_
     {
        System.err.println("Se ha producido un error al intentar descargar el cuadrante " + fichero);
        // Si el fichero existe y ocupa muy pocos bytes, lo borramos:
        File mifile = new File(fichero);
        if (mifile.exists())
           //if (mifile.length() < 400)
           {
              mifile.delete();        
              //JSigpac.Traza("kkmail: Hemos borrado el fichero: " + fichero); //kkmail
           }
     }
   }
   
   ImprimirT("Hemos salido del try");
   if (JSigpac.hayThreads)
   {
      if (miMonitor != null)
         miMonitor.AccesoZonaComun(i, j, error);	
      else
         JSigpac.Traza("Habria que controlar este error porque miMonitor es null en Descargar.java, metodo run()");
   	
   } else if (miMonitor != null)
   {
      Imprimir(id_thread + "- Llamamos a PasarTestigo " + toString());
      miMonitor.PasarTestigo(i, j, error);
   }
   Imprimir("\t" + id_thread + "- Fin de " + toString());
 }

}
