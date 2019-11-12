import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.util.regex.*;
import java.text.*;

class Fichero {
 String fichero_entrada = null;
 String fichero_salida = null;
 Datum datumSalida = Datum.datumED50; // Especificará el datum en que se devuelven las coordenadas en CalcularCoordenadas()
 String[] extensiones = null;
 BufferedReader in = null;
 //BufferedWriter out = null;
 PrintWriter out = null;
 Vector<String> saquito = null;
 public static File ultimoDirectorio = null;
 MiArea miarea;
 int lineas_leidas = 0;
 Component miComponent = null;
 public static String mensaje_error = "Error no definido"; // Utilizada por la clase llamante para especificar el error cuando al intentar importar datos de un fichero se devuelva null.
 Puntos coordenadas = null;
 public static final int MAXIMO_NUM_PUNTOS = 5000; // Tamaño máximo inicial porque si se alcanza, en la clase Puntos se amplia al doble mientras sea necesario.
 public static DecimalFormat df;
 public static String[] misExtensiones = {"trk", "TRK", "plt", "PLT", "rte", "RTE", "wpt", "WPT", "gpx", "GPX", "kml", "KML", "imp", "IMP", "map", "MAP"};

 Fichero ()
 {
   out = null;
   lineas_leidas = 0;
   mensaje_error = "Error no definido";
   datumSalida = Datum.datumED50;
   coordenadas = new Puntos(Fichero.MAXIMO_NUM_PUNTOS); 
   df = new DecimalFormat("000"); // Utilizado al generar ficheros de ruta para Ozi PPC.
   //System.out.println("Constructor Fichero");
   //fichero_entrada = EscogerFichero();
   //System.out.println("fichero_entrada: "+ fichero_entrada);
 }


 Fichero (String nombre)
 {
   this();
   fichero_entrada = nombre;
 }

 Fichero (MiArea area, Component com)
 {
   this();
   fichero_entrada = null;
   miarea = area; 
   miComponent = com;
   EscribirTexto("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador Fichero");
   CerrarFichEscritura();
   CerrarFichLectura();
 }

 void EscribirTexto(String texto)
 {
   if (miarea != null)
       miarea.EscribirTexto(texto); 	
   else
      System.out.println(texto);
 }
 
 public static String getExtension(File f) 
 {  
   String s = f.getName();
   return getExtension(s);
 }
  
 public static String getExtension(String s) 
 {
   String ext = null; 	
   int i = s.lastIndexOf('.');

   if (i > 0 &&  i < s.length() - 1) {
      ext = s.substring(i+1).toLowerCase();
   }
   return ext;
 }

/*
   Genera el nombre del fichero de salida a partir del fichero de entrada
   y de lo que se reciba como argumento.
   Si el argumento que recibe contiene un punto '.', indica que le estamos
   pasando una extensión y se la añadiremos al nombre del fichero de entrada.
   Esto sucede cuando estamos intentando generar un fichero de ruta para Ozi PPC
 */
 void FicheroDeSalida(String cola)
 {
   int aux = fichero_entrada.lastIndexOf('.');
   
   if (cola.indexOf('.') != -1)   
      fichero_salida = fichero_entrada.substring(0, aux) + cola;	
   else // A partir del nombre del fichero de entrada y del datum de salida:
      fichero_salida = fichero_entrada.substring(0, aux) +
	    	       cola + fichero_entrada.substring(aux);
   //System.out.println("FicheroDeSalida="+fichero_salida);
 }

 boolean EscogerFichero (String[] extensiones)
 {
   //System.out.println("EscogerFichero");
 
   JFileChooser fc = new JFileChooser(ultimoDirectorio);
   if (extensiones != null)
   {
      fc.addChoosableFileFilter(new MiFiltro(extensiones));
      fc.setAcceptAllFileFilterUsed(false);
   }

   int res = fc.showOpenDialog(miComponent);
   if (res == JFileChooser.APPROVE_OPTION)
   {         
      File entrada = fc.getSelectedFile(); 
      fichero_entrada = (entrada == null ? null : entrada.getAbsolutePath());
      ultimoDirectorio = (entrada == null ? null : entrada.getParentFile());
      //System.out.println("Fichero.toString:"+ultimoDirectorio.toString());
      //System.out.println("Fichero.getAbsolutePath:"+ultimoDirectorio.getAbsolutePath());     
      if (entrada.exists())
         return true;
      else
      {  
      	 EscribirTexto("No existe el fichero: \"" + fichero_entrada + "\"");
         return false;
      }
   }
   else
      return false;
 }

 boolean AbrirFicheroLectura()
 {
   try {
    in = new BufferedReader(new FileReader(fichero_entrada));
   } catch (FileNotFoundException fnfe) {
    EscribirTexto("Fichero no encontrado: <"+fichero_entrada+">");
    return false;
   } catch (NullPointerException npe) {
    //System.out.println("No se ha debido especificar el fichero de entrada");
    EscribirTexto("No se ha debido especificar el fichero de entrada");
    return false;
   }
   return true;
 }

 boolean AbrirFicheroEscritura()
 {
   //System.out.println("Estamos en AbrirFicheroEscritura");
   try {
    if (out == null)
    {
       //System.out.println("Se ha abierto el fichero: "+fichero_salida);
       out = new PrintWriter(new BufferedWriter(new FileWriter(fichero_salida)));
    } else
    {
       //System.out.println("YA ESTABA ABIERTO: "+fichero_salida);
    }
   } catch (FileNotFoundException fnfe) {
    EscribirTexto("Fichero no encontrado: <"+fichero_salida+">");
    return false;
   } catch (IOException e) {
    if (fichero_salida != null)
       EscribirTexto("Error al intentar abrir el fichero: <"+fichero_salida+">");
    //else
    //   EscribirTexto("Error al intentar abrir el fichero");
    return false;
   } catch (NullPointerException npe) {
    //System.out.println("No se ha debido especificar el fichero de salida");
    if (fichero_salida != null)
       EscribirTexto("Error al intentar abrir el fichero: <"+fichero_salida+">");
    //else
    //   EscribirTexto("Error al intentar abrir el fichero");
    return false;
   }
   return true;
 }

 String LeerLinea()
 {
    String linea; 
    if (in == null)
       if (!AbrirFicheroLectura())
	  return null;
    try {
      linea = in.readLine();
      if (linea != null)
         lineas_leidas++;
      return linea;
    } catch (IOException e) {
      EscribirTexto("Error al leer de fichero. Lineas leidas: " + lineas_leidas);
      return null;
    }
 }

 boolean EscribirLinea(String linea)
 {
    // Puede que no podamos escribir todavia en el fichero de salida
    // por no estar definido todavia el nombre de dicho fichero.
    // Asi que inicialmente puede que se guarden algunas lineas en
    // un vector de String y en cuanto se concrete el nombre del
    // fichero de salida, se escribiran las lineas de dicho Vector.

    //System.out.println("EscribirLinea:<"+linea+">");
    if (linea == null)
    {
       //System.out.println("EscribirLinea:  linea==null");
       return false;
    }

    if (out == null)
    {
       //System.out.println("out == null");
       if (!AbrirFicheroEscritura())
       {
          //System.out.println("EscribirLinea: Se almacena la linea en Vector");
	  if (saquito == null)
          {
             //System.out.println("Creo el VECTOR");
	     saquito = new Vector<String>();
          }
          saquito.add(linea);
          //System.out.println("elements="+saquito.size());
	  return true;
       } else
       {  // Se ha creado por fin el fichero de salida y escribimos
	  // en el fichero todo lo que teniamos pendiente:
	  if (saquito != null)
          {
	     String saca; 
             //System.out.println("Por fin !!   saquito != null  elements="+saquito.size());
	     for (int i=0; i<saquito.size(); i++)
             {
                 //System.out.println("i="+i);
		 saca = (String)saquito.get(i);
                 //System.out.println("saca:"+saca);
                 out.println(saca);
             }
             saquito = null;
          }
       }
    }


    //System.out.println("EscribirLinea REAL:<"+linea+">");
    out.println(linea);
    return true;
 }

 boolean CerrarFichLectura()
 {
    try {
      if (in != null)
      {
         in.close();  
	 in = null;
      }
      else
	 return false;
    } catch (IOException e) {
      return false;
    }
    return true;
 }

 boolean CerrarFichEscritura()
 {
   //System.out.println("Estamos en CerrarFichEscritura");
      if (out != null)
      {
         //System.out.println("Se hace el FLUSH y el CLOSE");
         out.flush();  
         out.close();  
	 out = null;
      }
      else
         return false;
    return true;
 }
 
 static String CabeceraFichRutaOzi()
 {
    String cabecera;
    cabecera = "H1,OziExplorer CE Route2 File Version 1.0\n";
    cabecera += "H2,WGS 84\n";
    Calendar fecha = Calendar.getInstance();
    cabecera = cabecera + "H3," + fecha.get(Calendar.DATE) + "-" + 
               (fecha.get(Calendar.MONTH)+1) + "-" + fecha.get(Calendar.YEAR) + 
               " " + fecha.get(Calendar.HOUR_OF_DAY) + ":" + 
               fecha.get(Calendar.MINUTE) + ":" + fecha.get(Calendar.SECOND) + ",,0";
    return cabecera;
 }
 
 double[] CalcularCoordenadas()
 { 
    return CalcularCoordenadas(Datum.datumED50);
 }
 // Lee un fichero de tracks o de rutas o de waypoints y devuelve
 // un vector de 6 elementos con la respuesta:
 double[] CalcularCoordenadas(Datum _datumSalida)
 {            
 	                //    Xsi Ysi huso_si Xid Yid huso_id
    double[] respuesta; // = {34, 44,  30,    94, 24,   30};
    datumSalida = _datumSalida;
    
    AbrirFicheroLectura();
    if (getExtension(fichero_entrada).equals("trk") ||
        getExtension(fichero_entrada).equals("TRK")   )
    {
    	respuesta = CoordenadasTrackCompe();
        return respuesta; 
    } else if (getExtension(fichero_entrada).equals("plt") ||
 	       getExtension(fichero_entrada).equals("PLT")   )
    {
    	respuesta = CoordenadasTrackOzi();
    	return respuesta;
    } else if (getExtension(fichero_entrada).equals("rte") ||
 	       getExtension(fichero_entrada).equals("RTE")   )
    {
    	respuesta = FicheroDeRutas();
    	return respuesta;
    } else if (getExtension(fichero_entrada).equals("wpt") ||
 	       getExtension(fichero_entrada).equals("WPT")   )
    {
    	respuesta = FicheroDeWayPoints();
    	return respuesta;
    } else if (getExtension(fichero_entrada).equals("gpx") ||
               getExtension(fichero_entrada).equals("GPX")  )
    {
        respuesta = FicheroGPX();
        return respuesta;
    } else if (getExtension(fichero_entrada).equals("kml") ||
               getExtension(fichero_entrada).equals("KML")  )
    {
        respuesta = FicheroKML();
        return respuesta;	          	    
    } else if (getExtension(fichero_entrada).equals("map") ||
               getExtension(fichero_entrada).equals("MAP")  )
    {
        respuesta = FicheroCalibracionOzi();
        return respuesta;	    
    } else if (getExtension(fichero_entrada).equals("imp") ||
               getExtension(fichero_entrada).equals("IMP")  )
    {
        respuesta = FicheroCalibracionCompe();
        return respuesta;	
    }
    return null;   
 }

 // A partir de los valores pasados (minLat, maxLat, minLon, maxLon)
 // devuelve un array de "double" con las coordenadas de la esquina
 // superior izquierda y su huso y de la esquina inferior derecha y el huso:
 double[] DevolverEsquinas(double minLat, double maxLat, 
                   double minLon, double maxLon, boolean wgs84)
 {   	
    double Xsi, Ysi, Xid, Yid;
    int huso_si, huso_id;
    double[] respuesta;
    Datum datumEntrada = wgs84 ? Datum.datumWGS84 : Datum.datumED50;
    
    JSigpac.Traza("Latitud: "+minLat+"~"+maxLat+"   Longitud: "+minLon+"~"+maxLon+"  datumEntrada="+datumEntrada.miString()+"   datumSalida="+datumSalida.miString());        
    
    if (datumEntrada == datumSalida)
    {
       // No hace falta cambiar de Datum:
       Coordenada dst;
       // Esquina superior izquierda:
       dst = new Coordenada (datumSalida, minLon, maxLat, 0);
       Xsi = dst.getX();
       Ysi = dst.getY();
       huso_si = dst.getHuso();
       // Esquina inferior derecha:
       dst = new Coordenada (datumSalida, maxLon, minLat, 0);
       Xid = dst.getX();
       Yid = dst.getY();      	       		
       huso_id = dst.getHuso();
    } else
    {
       // Convertimos al datum de salida:
       Coordenada ori, dst;
       // Esquina superior izquierda:
       ori = new Coordenada (datumEntrada, minLon, maxLat, 0);
       dst = ori.CambioDeDatum(datumSalida);
       Xsi = dst.getX();
       Ysi = dst.getY();
       huso_si = dst.getHuso();       	   
       // Esquina inferior derecha:
       ori = new Coordenada (datumEntrada, maxLon, minLat, 0);
       dst = ori.CambioDeDatum(datumSalida);
       Xid = dst.getX();
       Yid = dst.getY();   
       huso_id = dst.getHuso();    	          	          	   
    }
    
    JSigpac.Traza("Xsi="+Xsi+"  Ysi="+Ysi+"  huso_si="+huso_si+"   Xid="+Xid+"  Yid="+Yid+"  huso_id="+huso_id);
    respuesta = new double[6];
    respuesta[0] = Xsi;
    respuesta[1] = Ysi;
    respuesta[2] = huso_si;
    respuesta[3] = Xid;
    respuesta[4] = Yid;
    respuesta[5] = huso_id;
    return respuesta;       
 }      

 // Ejemplo de fichero de track de Compe:
/* 
G  European 1950
U  1
C  0 0 255 2 -1.000000
V  0.0 0.0 0 0 0 0 0.0
E 0|1|00-NUL-00 00:00:00|00:00:00|0
T  A 41.6406049932ºN 4.7429363303ºW 10-MAY-06 09:27:21 s -1.0 0.0 0.0 0.0 0 -1000.0 -1.0 ÿ -1.0 -1.0
T  A 41.6405407767ºN 4.7419057143ºW 10-MAY-06 09:27:21 s -1.0 0.0 0.0 0.0 0 -1000.0 -1.0 ÿ -1.0 -1.0
T  A 41.6404609641ºN 4.7407327654ºW 10-MAY-06 09:27:21 s -1.0 0.0 0.0 0.0 0 -1000.0 -1.0 ÿ -1.0 -1.0
T  A 41.6402943483ºN 4.7400187070ºW 10-MAY-06 09:27:21 s -1.0 0.0 0.0 0.0 0 -1000.0 -1.0 ÿ -1.0 -1.0
T  A 41.6401555048ºN 4.7398139257ºW 10-MAY-06 09:27:21 s -1.0 0.0 0.0 0.0 0 -1000.0 -1.0 ÿ -1.0 -1.0
T  A 41.6398573965ºN 4.7395812086ºW 10-MAY-06 09:27:21 s -1.0 0.0 0.0 0.0 0 -1000.0 -1.0 ÿ -1.0 -1.0
*/

 // La siguiente funcion lee de un fichero (que debera seguir el formato
 // de un fichero de track de Compe y devuelve las coordenadas de la
 // esquina superior izquierda y de la esquina inferior derecha y el huso
 // del mapa que los contiene:
 double[] CoordenadasTrackCompe()
 {
    String entrada;
    String[] val;    
    //boolean resultado = true;
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    int puntos = 0;
    boolean datum_determinado = false;
    boolean wgs84 = true;    
    
    while (((entrada=LeerLinea()) != null)) // && (resultado == true))
    {      
      if (entrada.trim().startsWith("G"))
      {
      	 val = entrada.trim().split("(\\s*G\\s+)");
      	 try {
          if (val[1].trim().equals("European 1950"))  
          {                 	    
	     wgs84 = false;
	     coordenadas.EstablecerDatum(Datum.datumED50);
	  } else if (val[1].trim().equals("WGS 84"))          	    
	  {
	     wgs84 = true;      	    
	     coordenadas.EstablecerDatum(Datum.datumWGS84);
          }else
          {
	    EscribirTexto("Datum de entrada no reconocido: " + val[1].trim());
	    mensaje_error = "Datum de entrada no reconocido: " + val[1].trim();
            return null;
          }
          datum_determinado = true;
         } catch (ArrayIndexOutOfBoundsException aio) {
	    EscribirTexto("Error en la linea: <"+entrada+">");
	    mensaje_error = "Error en la linea: <"+entrada+">";
            return null;
         }
         
      } else if (entrada.trim().startsWith("T"))
      {
        int pos; 
        String aux;
        String finN, finW;  // Contienen algo como "ºN" y "ºW"           

        // Me deshago de la "A":
        pos = entrada.indexOf("A") + 1;
        //System.out.println(pos+"-"+entrada.substring(pos));
        aux = entrada.substring(pos).trim();
        val = aux.split(" ");
 
        try {
          // Posicion 0: Latitud
          pos = val[0].length();
          finN = val[0].substring(pos-2, pos);
          val[0] = val[0].substring(0, pos-2);
          // Posicion 1: Longitud
          pos = val[1].length();
          finW = val[1].substring(pos-2, pos);
          val[1] = val[1].substring(0, pos-2);
          //System.out.println(finN+"   "+finW+":"+val[0]);
   
          double lat = (Double.valueOf(val[0])).doubleValue();
          if (finN.equals("ºS"))
             lat = (-lat);
          double lon = (Double.valueOf(val[1])).doubleValue();
          if (finW.equals("ºW"))
             lon = (-lon);    
             
          coordenadas.PutPunto(lat, lon);
          
          if (primeraVez)
          {
            primeraVez = false;
            minLat = maxLat = lat;
            minLon = maxLon = lon;
          } else
          {
            if (lat < minLat)
                minLat = lat;
            if (lat > maxLat)
                maxLat = lat;
            if (lon < minLon)
                minLon = lon;
            if (lon > maxLon)
                maxLon = lon;
          }
          
        } catch (ArrayIndexOutOfBoundsException aio) {
          EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
          mensaje_error = "[AIOB] Formato no esperado en linea " + lineas_leidas + " <"+entrada+">";
          JSigpac.Traza("ArrayIndexOutOfBoundsException=" + aio);
          return null;
        } catch (StringIndexOutOfBoundsException sie) {
          EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
          mensaje_error = "[SIOB] Formato no esperado en linea " + lineas_leidas + " <"+entrada+">";
          return null;
        } catch(NumberFormatException nfe) {
          EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
          mensaje_error = "[NFE] Formato no esperado en linea " + lineas_leidas + " <"+entrada+">";
          return null;
        } /* catch(PuntosOutOfBoundsException pobe) {
          EscribirTexto(pobe.getMessage());
          mensaje_error = pobe.getMessage();
          return null;	
        } */
      }
      puntos++;
    }	// Fin while
    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else if (datum_determinado == false)
    {
       mensaje_error = "No se especifica el datum en el fichero o datum desconocido";       
       return null;
    } else
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);
               
 } // Fin de CoordenadasTrackCompe
       

// Ejemplo de fichero de track de Ozi:
/*
OziExplorer Track Point File Version 2.1
WGS 84
Altitude is in Feet
Reserved 3
0,2,255,17/05/2006 14:58:37                ,0,0,2,8421376
5
  41.520592,  -4.721444,1, -777.0,38854.5407169, 17-may-06, 12:58:37
  41.520353,  -4.721602,0, -777.0,38854.5407377, 17-may-06, 12:58:39
  41.520023,  -4.721358,0, -777.0,38854.5407579, 17-may-06, 12:58:41
  41.518441,  -4.722165,0, -777.0,38854.5407856, 17-may-06, 12:58:43
  41.518355,  -4.722210,0, -777.0,38854.5408261, 17-may-06, 12:58:47
*/

 // Usada por "CoordenadasTrackOzi" para obtener la latitud y longitud:
 double[] TratarLineaTrackOzi(String lineaTrack, boolean wgs84)
 {
    String[] val;   
    double[] respuesta=null; 

    if (lineaTrack == null)
       return null;

    val = lineaTrack.trim().split(",");
    try {
      //for (int i=0; i<val.length; i++)
      // 	System.out.println(i+": <"+val[i]+">");
      double lat = (Double.valueOf(val[0])).doubleValue();
      double lon = (Double.valueOf(val[1])).doubleValue();
      respuesta = new double[2];
      respuesta[0] = lat;
      respuesta[1] = lon;  
      coordenadas.PutPunto(lat, lon);    
    } catch (ArrayIndexOutOfBoundsException aio) {
       EscribirTexto("Formato no esperado en TratarLineaTrackOzi");
    } catch (StringIndexOutOfBoundsException sie) {
       EscribirTexto("Formato no esperado en TratarLineaTrackOzi");
    } catch(NumberFormatException nfe) {
       EscribirTexto("Formato no esperado en TratarLineaTrackOzi");
    } /* catch(PuntosOutOfBoundsException pobe) {       
       EscribirTexto(pobe.getMessage());
       mensaje_error = pobe.getMessage();
       return null;	
    }*/
    
    return respuesta;
 }
 

 // La siguiente funcion lee de un fichero (que debera seguir el formato
 // de un fichero de track de Ozi y devuelve las coordenadas de la
 // esquina superior izquierda y de la esquina inferior derecha y el huso
 // del mapa que los contiene: 
 double[] CoordenadasTrackOzi()
 {
    String entrada;
    //boolean resultado = true;
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    int puntos = 0;
    boolean datum_determinado = false; // Cuando se sepa el datum, se pondra a true.
    boolean wgs84 = true;
    
    //System.out.println("_xx_ Leemos PLT de Ozi");
    while (((entrada=LeerLinea()) != null)) // && (resultado == true))
    {    
      if (datum_determinado == false)
      {
      	 if (entrada.trim().startsWith("European 1950"))
      	 {
      	    wgs84 = false;
      	    datum_determinado = true;
      	    coordenadas.EstablecerDatum(Datum.datumED50);
      	    //System.out.println("_xx_ Datum European 1950");
      	 } else if (entrada.trim().startsWith("WGS 84"))
      	 {
      	    wgs84 = true;
      	    datum_determinado = true;
      	    coordenadas.EstablecerDatum(Datum.datumWGS84);
      	    //System.out.println("_xx_ Datum WGS 84");
      	 }
      } else  // Ya conocemos el datum del fichero:
      {                
         // Tengo que buscar la linea que empieza y contiene un solo
         // numero (que se corresponde con el numero de puntos del track:
         Pattern p = Pattern.compile("^\\d+");
         Matcher m;         
         String linea;
         int num_puntos;

         //System.out.println("Resto: " + entrada);
         m = p.matcher(entrada.trim());
         if (m.matches() == false)
            continue; // Para que lea otra linea (hasta que encontremos el numerito)
     
         num_puntos = (Integer.valueOf(entrada.trim())).intValue();
         // Ahora hay que leer tantas lineas del fichero como "num_puntos":
         // Pero hay que tener en cuenta que a veces los ficheros con el track que
         // genera el OziExplorer de la PDA `pone un "0":
         //System.out.println("_xx_ ---Hay que leer estas lineas: " + num_puntos);
         double[] valor;
         for (int i=0; (num_puntos>0 && i<num_puntos) || num_puntos==0; i++)
         {
            linea = LeerLinea();
            //System.out.println(i+".- _xx_ Leida linea:" + linea);
	          valor = TratarLineaTrackOzi(linea, wgs84);	       
            if (valor != null)
            {                                    
               double lat = valor[0];
               double lon = valor[1];
               //try {
                 coordenadas.PutPunto(lat, lon);
               /*
               } catch(PuntosOutOfBoundsException pobe) {
                 EscribirTexto(pobe.getMessage());
                 mensaje_error = pobe.getMessage();
                 return null;	
               }
               */
                         
               if (primeraVez)
               {
                  primeraVez = false;
                  minLat = maxLat = lat;
                  minLon = maxLon = lon;
               } else
               {
                 if (lat < minLat)
                    minLat = lat;
                 if (lat > maxLat)
                    maxLat = lat;
                 if (lon < minLon)
                    minLon = lon;
                 if (lon > maxLon)
                    maxLon = lon;
               }
               puntos++;
            } else
            {
            	if (num_puntos == 0)
            	   break;
            }
         }
      } // Fin del else      
    }	// Fin while
    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else if (datum_determinado == false)
    {
       mensaje_error = "No se especifica el datum en el fichero o datum desconocido";       
       return null;
    } else
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);            
 } // Fin de CoordenadasTrackOzi 

 
 double[] FicheroDeRutas()
 {
    String entrada;
   
    while (((entrada=LeerLinea()) != null) && 
             entrada.trim().equals("") )
             ;
    if (entrada != null)
    {
       if (entrada.trim().startsWith("OziExplorer"))
          return CoordenadasRutaOzi(entrada);
       else
          return CoordenadasRutaCompe(entrada);
    } else
    {
       mensaje_error = "Puede que el fichero este vacio";   
       return null;
    }
 }
 
 
// Ejemplo de fichero de ruta de Compe:
/*
G  European 1950
 U   1 
 R   16711680 , Ruta_Compe , 1 , -1 
 W  r001__ A 41.6336440065ºN 4.7030831080ºW 27-MAR-62 00:00:00 711.000916 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
 W  r002__ A 41.6221727512ºN 4.6931668092ºW 27-MAR-62 00:00:00 723.654846 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
 W  r003__ A 41.6171847818ºN 4.6819244912ºW 27-MAR-62 00:00:00 753.846741 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
 W  r004__ A 41.6115708984ºN 4.6685454585ºW 27-MAR-62 00:00:00 809.060791 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
 W  r005__ A 41.6159829403ºN 4.6744022852ºW 27-MAR-62 00:00:00 756.630615 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
*/

 // La siguiente funcion lee de un fichero (que debera seguir el formato
 // de un fichero de ruta de Compe y devuelve las coordenadas de la
 // esquina superior izquierda y de la esquina inferior derecha y el huso
 // del mapa que los contiene: 
 double[] CoordenadasRutaCompe(String pimeraLinea)
 {
    String entrada;
    String[] val;
    //boolean resultado = true;
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    int puntos = 0;
    boolean datum_determinado = false; // Cuando se sepa el datum, se pondra a true.
    boolean wgs84 = true;
    
    //System.out.println("_cc_ CoordenadasRutaCompe");
    
    entrada = pimeraLinea;
    while (entrada != null) // && (resultado == true))
    {    
      if (entrada.trim().startsWith("G"))
      {
         val = entrada.trim().split("(\\s*G\\s+)");
         try {
            if (val[1].trim().equals("European 1950"))  
            {                    	    
	       wgs84 = false;	    
	       coordenadas.EstablecerDatum(Datum.datumED50);
            } else if (val[1].trim().equals("WGS 84"))          	    
            {
               wgs84 = true;      	    
               coordenadas.EstablecerDatum(Datum.datumWGS84);
            } else
            {
	       EscribirTexto("Datum de entrada no reconocido: " + val[1].trim());
	       mensaje_error = "Datum de entrada no reconocido: " + val[1].trim();
               return null;
            }
          } catch (ArrayIndexOutOfBoundsException aio) {
	    EscribirTexto("Error en la linea: <"+entrada+">");
	    mensaje_error = "Error en la linea: <"+entrada+">";
            return null;
          }
      	  datum_determinado = true; 
      } else if (entrada.trim().startsWith("W"))
      {               
        int pos; 
        //String aux, salida=" W ";
        String finN, finW;  // Contienen algo como "ºN" y "ºW"

        val = entrada.trim().split("\\s+");
        //for (int i=0; i<val.length; i++)
        //    System.out.println("i="+i+"  :"+val[i]);
        try {
          // Posicion 3: Latitud
          pos = val[3].length();
          finN = val[3].substring(pos-2, pos);
          val[3] = val[3].substring(0, pos-2);
          // Posicion 4: Longitud
          pos = val[4].length();
          finW = val[4].substring(pos-2, pos);
          val[4] = val[4].substring(0, pos-2);
   
          double lat = (Double.valueOf(val[3])).doubleValue();
          if (finN.equals("ºS"))
             lat = (-lat);
          double lon = (Double.valueOf(val[4])).doubleValue();
          if (finW.equals("ºW"))
             lon = (-lon);             
          
          coordenadas.PutPunto(lat, lon);
          
          if (primeraVez)
          {
             primeraVez = false;
             minLat = maxLat = lat;
             minLon = maxLon = lon;
          } else
          {
             if (lat < minLat)
                minLat = lat;
             if (lat > maxLat)
                maxLat = lat;
             if (lon < minLon)
                minLon = lon;
             if (lon > maxLon)
                maxLon = lon;
          }
          puntos++;
        } catch (ArrayIndexOutOfBoundsException aio) {
           EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
           entrada = LeerLinea();
           continue;
        } catch (StringIndexOutOfBoundsException sie) {
           EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
           entrada = LeerLinea();
           continue;
        } catch(NumberFormatException nfe) {
           EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
           entrada = LeerLinea();
           continue;
        } /* catch(PuntosOutOfBoundsException pobe) {
          EscribirTexto(pobe.getMessage());
          mensaje_error = pobe.getMessage();
          return null;	
        }*/
      } // Fin del else 
      entrada = LeerLinea();
    }	// Fin while
    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else if (datum_determinado == false)
    {
       mensaje_error = "No se especifica el datum en el fichero o datum desconocido";       
       return null;
    } else
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);            
 } // Fin de CoordenadasRutaCompe 


// Ejemplo de fichero de ruta de Ozi:
/*
OziExplorer Route File Version 1.0
WGS 84
Reserved 1
Reserved 2
R,  0,R0            ,
W,  0,  1, 47,47              , -26.601308, 152.382914,35640.92711, 0, 1, 0,   8388608,     65535,                                        , 0, 0
W,  0,  2, 29,29              , -26.568702, 152.369428,35640.92024, 0, 1, 0,   8388608,     65535,                                        , 0, 0
W,  0,  3, 59,59              , -26.493497, 152.354207,35641.56277, 0, 1, 0,   8388608,     65535,                                        , 0, 0
W,  0,  4, 60,60              , -26.469303, 152.345428,35641.56277, 0, 1, 0,   8388608,     65535,                                        , 0, 0
W,  0,  5, 62,62              , -26.464571, 152.361817,35641.56277, 0, 1, 0,   8388608,     65535,                                        , 0, 0
W,  0,  6, 61,61              , -26.460892, 152.371782,35641.56277, 0, 1, 0,   8388608,     65535,                                        , 0, 0
R,  1,ROUTE 1       ,


***Nota: A veces, una ruta en Ozi no es mas que una lista de waypoints y aunque el fichero tenga extension ".RTE" el formato es como el ".WPT"

*/

 double[] CoordenadasRutaOzi(String primeraLinea)
 {
    String entrada;
    String[] val;
    //boolean resultado = true;
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    int puntos = 0;
    boolean datum_determinado = false; // Cuando se sepa el datum, se pondra a true.
    boolean wgs84 = true;    
    
    entrada = primeraLinea;
    while (entrada != null) // && (resultado == true))
    {           	
      if (entrada.trim().startsWith("European 1950"))
      {      
         wgs84 = false; 
         datum_determinado = true; 
         coordenadas.EstablecerDatum(Datum.datumED50);
      } else if (entrada.trim().startsWith("WGS 84"))
      {
         wgs84 = true;
         datum_determinado = true;
         coordenadas.EstablecerDatum(Datum.datumWGS84);
      } else if (entrada.trim().startsWith("W"))
      { 	    	 
      	 double lat=0, lon=0;         
         val = entrada.trim().split(",");                        
         
         //for (int i=0; i<val.length; i++)
         //	System.out.println(i+": <"+val[i]+">");
         try {
            lat = (Double.valueOf(val[5])).doubleValue();
            lon = (Double.valueOf(val[6])).doubleValue();
            coordenadas.PutPunto(lat, lon);
            if (primeraVez)
            {
               primeraVez = false;
               minLat = maxLat = lat;
               minLon = maxLon = lon;
            } else
            {
               if (lat < minLat)
                  minLat = lat;
               if (lat > maxLat)
                  maxLat = lat;
               if (lon < minLon)
                  minLon = lon;
               if (lon > maxLon)
                 maxLon = lon;
            }
            puntos++;        
         } catch (ArrayIndexOutOfBoundsException aio) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;
         } catch (StringIndexOutOfBoundsException sie) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;
         } catch(NumberFormatException nfe) {
            EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;            
         } /*catch(PuntosOutOfBoundsException pobe) {
            EscribirTexto(pobe.getMessage());
            mensaje_error = pobe.getMessage();
            return null;	
         } */                    
      } // Fin del else
      entrada = LeerLinea();
    }  // Fin while     
  	    
    if (puntos < 2)
    {
       // Primero suponemos que se trata en realidad de un fichero de waypoints tratado como ruta.
       // Entonces, tendremos que volver a abrir el fichero y tratarlo como un fichero de waypoints:
       CerrarFichLectura();
       return FicheroDeWayPoints();
       //mensaje_error = "Debe haber al menos 2 puntos";       
       //return null;
    } else if (datum_determinado == false)
    {
       mensaje_error = "No se especifica el datum en el fichero o datum desconocido";       
       return null;
    } else
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);
 } // Fin de CoordenadasRutaOzi
   
   
 double[] FicheroDeWayPoints()
 {
    String entrada;
    //System.out.println("_cc_ FicheroDeWayPoints");
    while (((entrada=LeerLinea()) != null) && 
             entrada.trim().equals("") )
             ;
    if (entrada != null)
    {
       //System.out.println("_cc_ entrada="+entrada);
       if (entrada.trim().startsWith("OziExplorer"))
          return CoordenadasWayPointsOzi(entrada);
       else
          return CoordenadasWayPointsCompe(entrada);
    } else
    {
       mensaje_error = "Puede que el fichero este vacio";   
       return null;
    }	 	
 }
   
// Ejemplo de fichero de waypoints de Compe:       
/*
G  European 1950 (Spain and Portugal)
U  1
W  piscina A 41.6387955042ºN 4.7411609728ºW 27-MAR-62 00:00:00 0.000000 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
W  paque_laberinto A 41.6394859376ºN 4.7400915635ºW 27-MAR-62 00:00:00 0.000000 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
W  puente A 41.6405407767ºN 4.7419057143ºW 27-MAR-62 00:00:00 -1.000000 
w Waypoint,0,-1.0,16777215,255,1,7,,0.0,
*/

 // La siguiente funcion lee de un fichero (que debera seguir el formato
 // de un fichero de waypoints de Compe y devuelve las coordenadas de la
 // esquina superior izquierda y de la esquina inferior derecha y el huso
 // del mapa que los contiene: 
 double[] CoordenadasWayPointsCompe(String primeraLinea)
 {
    // Como solo tenemos que tratar las lineas que empiezan por "G"
    // o por "W", y el formato coincide con el utilizado en el fichero
    // de rutas de Compe, llamamos a la misma funcion:	    
    //System.out.println("_cc_  CoordenadasWayPointsCompe"); 
    return CoordenadasRutaCompe(primeraLinea);
 }
 
 
 
// Ejemplo de fichero de waypoints de Ozi: 
/*
OziExplorer Waypoint File Version 1.1
WGS 84
Reserved 2
magellan
1,IT DEUSTO,  41.517732,  -4.723098,38854.6223830,  0, 0, 3,         0,     65535,edificio galileo, 0, 0,    0,   -777, 6, 0,17,0,10.0,2,,,
2,ROTONDA,  41.521770,  -4.721638,38854.6228628,  0, 0, 3,         0,     65535,, 0, 0,    0,   -777, 6, 0,17,0,10.0,2,,,
3,CIDAUT,  41.521082,  -4.721399,38854.6230162,  0, 0, 3,         0,     65535,, 0, 0,    0,   -777, 6, 0,17,0,10.0,2,,,
*/

 
 // La siguiente funcion lee de un fichero (que debera seguir el formato
 // de un fichero de waypoints de Ozi y devuelve las coordenadas de la
 // esquina superior izquierda y de la esquina inferior derecha y el huso
 // del mapa que los contiene:  
 double[] CoordenadasWayPointsOzi(String primeraLinea)
 {
    String entrada;
    String[] val;
    //boolean resultado = true;
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    int puntos = 0;
    boolean datum_determinado = false; // Cuando se sepa el datum, se pondra a true.
    boolean wgs84 = true;
    //Pattern p = Pattern.compile("^\\d+,.*");
    Pattern p = Pattern.compile("^-??\\d+,.*"); // El guion inicial es porque a veces en lugar de numerar los waypoints, la linea empieza con -1
    Matcher m;    
    
    entrada = primeraLinea;
    while (entrada != null) // && (resultado == true))
    {       
      JSigpac.Traza("entrada:" + entrada);
      if (entrada.trim().startsWith("European 1950"))
      {      
         wgs84 = false; 
         datum_determinado = true; 
         coordenadas.EstablecerDatum(Datum.datumED50);
      } else if (entrada.trim().startsWith("WGS 84"))
      {
         wgs84 = true;
         datum_determinado = true;
         coordenadas.EstablecerDatum(Datum.datumWGS84);
      } else 
      { 	    	 
      	 double lat=0, lon=0;                          
         m = p.matcher(entrada.trim());

         if (m.matches() == false)
         {
            JSigpac.Traza("NO cuadra");
            entrada = LeerLinea();
            continue; // A por otra linea porque esta no nos interesa.
         }
         JSigpac.Traza("SI cuadra");
         // Segun sea el datum de entrada, asi sera el de salida:
         val = entrada.trim().split(",");
         //for (int i=0; i<val.length; i++)
         //	System.out.println(i+": <"+val[i]+">");
         try {
            lat = (Double.valueOf(val[2])).doubleValue();
            lon = (Double.valueOf(val[3])).doubleValue();
            coordenadas.PutPunto(lat, lon);
            if (primeraVez)
            {
               primeraVez = false;
               minLat = maxLat = lat;
               minLon = maxLon = lon;
            } else
            {
               if (lat < minLat)
                  minLat = lat;
               if (lat > maxLat)
                  maxLat = lat;
               if (lon < minLon)
                  minLon = lon;
               if (lon > maxLon)
                 maxLon = lon;
            }
            puntos++;        
         } catch (ArrayIndexOutOfBoundsException aio) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;
         } catch (StringIndexOutOfBoundsException sie) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;
         } catch(NumberFormatException nfe) {
            EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;            
         } /* catch(PuntosOutOfBoundsException pobe) {
            EscribirTexto(pobe.getMessage());
            mensaje_error = pobe.getMessage();
            return null;	
         }*/                     
      } // Fin del else
      entrada = LeerLinea();
    }  // Fin while     
  	    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else if (datum_determinado == false)
    {
       mensaje_error = "No se especifica el datum en el fichero o datum desconocido";       
       return null;
    } else
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);           
 } // Fin de CoordenadasWayPointsOzi 
   
 
 /* Información sobre el formato GPX sacada de:
    http://www.topografix.com/GPX/1/1/
  */
 double[] FicheroGPX()
 {
    String entrada;
    String[] val;
    //boolean resultado = true;
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    int puntos = 0;
    boolean wgs84 = true; // Con ficheros GPX siempre se considera que las coordenadas van en WGS84, por definicion.
    Pattern p = Pattern.compile(".*<.*pt.*lat=.*lon=.*");
    Matcher m; 
    double lat=0, lon=0;   
    
    coordenadas.EstablecerDatum(Datum.datumWGS84);
    
    //System.out.println("_cc_ FicheroDeWayPoints");
    while (((entrada=LeerLinea()) != null) && 
             entrada.trim().equals("") )
             ;
    
    while (entrada != null) // && (resultado == true))
    {        	 
      	                  
         //System.out.println("entrada="+entrada.trim());
         m = p.matcher(entrada.trim());

         if (m.matches() == false)
         {
            //System.out.println("No coincide");
            entrada = LeerLinea();
            continue; // A por otra linea porque esta no nos interesa.
         } //else
           // System.out.println("COINCIDE: " + entrada.trim());
         
         // Segun sea el datum de entrada, asi sera el de salida:
         val = entrada.trim().split("\"");
         //for (int i=0; i<val.length; i++)
         //	System.out.println(i+": <"+val[i]+">");
         try {
            lat = (Double.valueOf(val[1])).doubleValue();
            lon = (Double.valueOf(val[3])).doubleValue();
            coordenadas.PutPunto(lat, lon);
            //System.out.println("lat="+lat+"   lon="+lon);
            if (primeraVez)
            {
               primeraVez = false;
               minLat = maxLat = lat;
               minLon = maxLon = lon;
            } else
            {
               if (lat < minLat)
                  minLat = lat;
               if (lat > maxLat)
                  maxLat = lat;
               if (lon < minLon)
                  minLon = lon;
               if (lon > maxLon)
                 maxLon = lon;
            }
            puntos++;        
         } catch (ArrayIndexOutOfBoundsException aio) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;
         } catch (StringIndexOutOfBoundsException sie) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;
         } catch(NumberFormatException nfe) {
            EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
            entrada = LeerLinea();
            continue;            
         } /*catch(PuntosOutOfBoundsException pobe) {
            EscribirTexto(pobe.getMessage());
            mensaje_error = pobe.getMessage();
            return null;	
         }*/                    
     
      entrada = LeerLinea();
    }  // Fin while     
  	    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else
    {
       //System.out.println("minLon="+minLon+"   maxLat="+maxLat+"     maxLon="+maxLon+"  minLat="+minLat);
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);  
    }
  }        
  
 /* Información sobre el formato KML sacada de:
    http://code.google.com/apis/kml/documentation/
  */
 double[] FicheroKML()
 {
    String entrada;
    String[] val;
    //boolean resultado = true;
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    int puntos = 0;
    boolean wgs84 = true; // Con ficheros KML creo que siempre se considera que las coordenadas van en WGS84.
    Pattern p = Pattern.compile(".*<.*pt.*lat=.*lon=.*");
    Matcher m; 
    double lat=0, lon=0; 
    boolean inicioCoordenadasEncontrado = false;  
    int ini=0, fin=0;
    StringBuffer sb = null;
    
    val = null; 
    coordenadas.EstablecerDatum(Datum.datumWGS84);
    
    //System.out.println("_cc_ FicheroDeWayPoints");
    while (((entrada=LeerLinea()) != null) && 
             entrada.trim().equals("") )
             ;
   
    while (entrada != null) // && (resultado == true))
    {        	 
      	 // Primero buscco la cadena "<coordinates>" y cuando la encuentro, voy guardando
      	 // todo lo que venga en un buffer hasta que se lee "</coordinates>":
      	 // OJO !! que <coordinates> y </coordinates> pueden estar en la misma línea.
         //System.out.println("entrada="+entrada.trim());
         if (inicioCoordenadasEncontrado)
         {
            if ((fin = entrada.indexOf("</coordinates>")) != -1)  // Se ha localizado el final de las coordenadas
            {
               ini = 0;               
               do {
               	  sb.append(" ");
                  sb.append(entrada.substring(ini, fin).trim());
                  ini = fin + "</coordinates>".length();
                  if ((ini = entrada.indexOf("<coordinates>", ini)) != -1)                  
                     inicioCoordenadasEncontrado = true;
                  else
                  {
                     inicioCoordenadasEncontrado = false;
                     break;
                  } 
               } while ((fin = entrada.indexOf("</coordinates>", ini)) != -1);
               
               if (inicioCoordenadasEncontrado == true)
               {     
               	  sb.append(" ");          
               	  sb.append(entrada.substring(ini + ("<coordinates>".length())).trim());
               } else
               {
               	  String coordenadas = sb.toString().trim();
                  JSigpac.Traza("coordenadas:" + coordenadas);
                  val = coordenadas.split("\\s+");
                  sb = null;
               }   
                              
            } else
            {
               sb.append(" ");
               sb.append(entrada.trim());                        	
               //continue;
            }
         	
         } else if ((ini = entrada.indexOf("<coordinates>")) != -1)
         {
            inicioCoordenadasEncontrado = true;
            sb = new StringBuffer();
            if ((fin = entrada.indexOf("</coordinates>")) != -1)  // En la misma línea tenemos el "<coordinates>" y el "</coordinates>":
            {    
               inicioCoordenadasEncontrado = false;        	
               do {      
               	  sb.append(" ");         	  
                  sb.append(entrada.substring(ini + ("<coordinates>".length()), fin).trim());
                  ini = fin + "</coordinates>".length();
                  if ((ini = entrada.indexOf("<coordinates>", ini)) != -1)
                     inicioCoordenadasEncontrado = true;
                  else
                  {
                     inicioCoordenadasEncontrado = false;
                     break;
                  }
                  JSigpac.Traza("do-while: ini=" + ini + "   fin="+fin);
               } while ((fin = entrada.indexOf("</coordinates>", ini)) != -1);	
            	
               if (inicioCoordenadasEncontrado == true)   
               {
               	  sb.append(" ");            
               	  sb.append(entrada.substring(ini + ("<coordinates>".length())).trim());
               } else
               {
               	  String coordenadas = sb.toString().trim();
                  JSigpac.Traza("coordenadas:" + coordenadas);
                  val = coordenadas.split("\\s+");
                  sb = null;
               }   
                              
            } else
            {
               sb = new StringBuffer();
               sb.append(entrada.substring(ini + ("<coordinates>".length())).trim());
               //continue;
            }         	
         }
         
         if (inicioCoordenadasEncontrado == false && val != null)
         {
            // Trato las cordenadas que estarán en "val":
            String[] aux;
            for (int i=0; i<val.length; i++)
            {
            	JSigpac.Traza("val["+i+"]="+val[i]);
                aux = val[i].split(",");                
                try {
                   lat = (Double.valueOf(aux[1])).doubleValue();
                   lon = (Double.valueOf(aux[0])).doubleValue();
                   coordenadas.PutPunto(lat, lon);
                   //System.out.println("lat="+lat+"   lon="+lon);
                   if (primeraVez)
                   {
                      primeraVez = false;
                      minLat = maxLat = lat;
                      minLon = maxLon = lon;
                   } else
                   {
                      if (lat < minLat)
                         minLat = lat;
                      if (lat > maxLat)
                         maxLat = lat;
                      if (lon < minLon)
                         minLon = lon;
                      if (lon > maxLon)
                         maxLon = lon;
                   }
                   puntos++;        
                } catch (ArrayIndexOutOfBoundsException aio) {
                   EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
                   entrada = LeerLinea();
                   continue;
                } catch (StringIndexOutOfBoundsException sie) {
                   EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
                   entrada = LeerLinea();
                   continue;
                } catch(NumberFormatException nfe) {
                   EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
                   entrada = LeerLinea();
                   continue;            
                } /*catch(PuntosOutOfBoundsException pobe) {
                   EscribirTexto(pobe.getMessage());
                   mensaje_error = pobe.getMessage();
                   return null;	
                }*/                    
            	
            } // Fin del FOR
            val = null;
         } // Fin del IF
         
         entrada = LeerLinea();
         
    }  // Fin while     
  	    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else
    {
       //System.out.println("minLon="+minLon+"   maxLat="+maxLat+"     maxLon="+maxLon+"  minLat="+minLat);
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);  
    }
  }        
 
 
 /* Obtener las esquinas del mapa a partir de un fichero de calibración de Compe ".imp"
 
    Ejemplo de fichero de calibración de Compe:
    
      CompeGPS MAP File
      <Header>
      Version=2
      VerCompeGPS=6.0
      Projection=0,UTM,29,N,
      Coordinates=0
      Datum=WGS84
      </Header>
      <Map>
      Bitmap=Olleros_ORTO_r1_h29_x740352_y4652032_A6144_H6144.jpg
      BitsPerPixel=0
      BitmapWidth=6144
      BitmapHeight=6144
      </Map>
      <Calibration>
      P0=0.00000000,0.00000000,29T,737156.5910772365,4654889.142540659
      P1=6144.0,0.00000000,29T,743300.6479508171,4654889.147152404
      P2=6144.0,6144.0,29T,743300.653682198,4648745.090553966
      P3=0.00000000,6144.0,29T,737156.596945127,4648745.085934549
      </Calibration>
 */
 
 double[] FicheroCalibracionCompe()
 {       
    String entrada;   
    String[] val; 
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    double minLonAsociado=0, minLatAsociado=0, maxLonAsociado=0, maxLatAsociado=0;
    double minX=0, maxY=0, maxX=0, minY=0;
    byte huso_izqda=0, huso_dcha=0;
    int puntos = 0;
    boolean datum_determinado = false; // Cuando se sepa el datum, se pondra a true.
    boolean wgs84 = true;    
    Datum datumOri = null;
    //Pattern p = Pattern.compile("^P\\d+=\\d+");
    //Matcher m;  
    double lat=0, lon=0;
        
    while (((entrada=LeerLinea()) != null)) // && (resultado == true))
    {    
      if (datum_determinado == false)
      {
      	 if (entrada.trim().startsWith("Datum="))
      	 {
      	    if (entrada.trim().startsWith("Datum=European 1950"))
      	    {
      	       wgs84 = false;
      	       datum_determinado = true;
      	       coordenadas.EstablecerDatum(Datum.datumED50);
      	       //System.out.println("_xx_ Datum European 1950");
      	    } else if (entrada.trim().startsWith("Datum=WGS84"))
      	    {
      	       wgs84 = true;
      	       datum_determinado = true;
      	       coordenadas.EstablecerDatum(Datum.datumWGS84);
      	       //System.out.println("_xx_ Datum WGS 84");
      	    } else  // Datum no contemplado:
      	    {
      	       mensaje_error = "Datum no contemplado: " + entrada.trim();       
               return null;	      	    	
      	    }
      	 }
      } else  // Ya conocemos el datum del fichero:
      {                
         // Tengo que buscar las lineas que empiezan por "P" seguido de un número (son los puntos de calibración):   
         // Ejemplo:   P0=0.00000000,0.00000000,29T,737156.5910772365,4654889.142540659                                  
         
         String[] resto;
         String[] aux;
         Coordenada utmORI, utmDST;
         double x, y;
         byte huso;
         boolean norte;
   
         val = entrada.trim().split("(P\\d+=)");
   
         try {
           for (int i=0; i<val.length; i++)
               JSigpac.Traza("i="+i+"  :"+val[i]);
 
           // Tengo que tener en cuenta que hay otra linea que
           // empieza por la letra "P" que es: "Projection=0,UTM,29,N,"
           if (val.length <= 1)
              continue;
 
           // En val[1] hay: 0.00000000,0.00000000,29T,737156.5910772365,4654889.142540659
           resto = val[1].split(",");
           for (int i=0; i<resto.length; i++)
               JSigpac.Traza("i="+i+"  :"+resto[i]);
           x = Double.valueOf(resto[3]).doubleValue();   
           y = Double.valueOf(resto[4]).doubleValue(); 
           //JSigpac.Traza("_________ x=" + String.valueOf(x) + "   y=" + String.valueOf(y));  
           aux = resto[2].split("T");  // Contiene algo como "29T".
           for (int i=0; i<aux.length; i++)
              JSigpac.Traza("i="+i+"  :"+aux[i]);
           huso = Byte.valueOf(aux[0]).byteValue();
           norte = true; // Por ahora consideramos siempre que trabajamos en el hemisferio norte !!
           
           datumOri = wgs84 ? Datum.datumWGS84 : Datum.datumED50;
           utmORI = new Coordenada(datumOri, x, y, 0, huso, norte);
                      
           lat = utmORI.getLat();
           lon = utmORI.getLon();
           coordenadas.PutPunto(lat, lon);
           JSigpac.Traza("lat="+lat+"   lon="+lon);
           if (primeraVez)
           {
              primeraVez = false;
              minLat = maxLat = lat;              
              minLonAsociado = maxLonAsociado = lon;
              minX = maxX = x; 
              huso_izqda = huso;
              minLon = maxLon = lon;
              minLatAsociado = maxLatAsociado = lat;
              minY = maxY = y;
              huso_dcha = huso;
              //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
           } else
           {
              if (lat < minLat)
              {
                 minLat = lat;
                 minLonAsociado = lon;                                  
                 if (huso >= huso_dcha)                 
                   if (x >= maxX && y <= minY)
                   {
                      maxX = x;
                      minY = y;
                      huso_dcha = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }                                 
              }
              if (lat > maxLat)
              {
                 maxLat = lat;
                 maxLonAsociado = lon;
                 if (huso <= huso_izqda)
                   if (x <= minX && y >= maxY)
                   {
                      minX = x;
                      maxY = y;                   
                      huso_izqda = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }
              }
              if (lon < minLon)
              {
                 minLon = lon;
                 minLatAsociado = lat;
                 if (huso <= huso_izqda)                 
                   if (x <= minX && y >= maxY)
                   {
                      minX = x;
                      maxY = y;
                      huso_izqda = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }
              }
              if (lon > maxLon)
              {
                maxLon = lon;
                maxLatAsociado = lat;
                if (huso >= huso_dcha)                 
                   if (x >= maxX && y <= minY)
                   {
                      maxX = x;
                      minY = y;
                      huso_dcha = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }
              }
           }
           puntos++;
           
         } catch (ArrayIndexOutOfBoundsException aio) {
            mensaje_error = "Formato no esperado en la linea: " + entrada.trim();
            return null;
         } catch (StringIndexOutOfBoundsException sie) {
            mensaje_error = "Formato no esperado en la linea: " + entrada.trim();
            return null;
         } catch(NumberFormatException nfe) {
            mensaje_error = "Formato no esperado en la linea: " + entrada.trim();
            return null;
         }
      }
    }
                           	    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else
    {
       //System.out.println("minLon="+minLon+"   maxLat="+maxLat+"     maxLon="+maxLon+"  minLat="+minLat);
       //return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84); 
       Coordenada si, id;
       si = new Coordenada(datumOri, minX, maxY, 0, huso_izqda, true);  
       id = new Coordenada(datumOri, maxX, minY, 0, huso_dcha, true);
       JSigpac.Traza("six=" + String.valueOf(si.getX()) + "siy=" + String.valueOf(si.getY()) + "idx=" + String.valueOf(id.getX()) + "idy=" + String.valueOf(id.getY()));
       minLat = id.getLat();
       maxLat = si.getLat();
       minLon = si.getLon();
       maxLon = id.getLon();       
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);  
    }
  }          
    
  /* Obtener las esquinas del mapa a partir de un fichero de calibración de Ozi ".map"
 
    Ejemplo de fichero de calibración de Ozi:
    
	OziExplorer Map Data File Version 2.2
	pruebaED50.jpg
	pruebaED50.jpg
	1 ,Map Code,
	European 1950,, 0.0000, 0.0000,WGS 84
	Reserved 1
	Reserved 2
	Magnetic Variation,,,E
	Map Projection,(UTM) Universal Transverse Mercator,PolyCal,No,AutoCalOnly,No,BSBUseWPX,No
	Point01,xy,0,0,in, deg, , ,N, , ,W, grid, 30, 364544.0, 4760064.0,N
	Point02,xy,1280,0,in, deg, , ,N, , ,W, grid, 30, 367104.0, 4760064.0,N
	Point03,xy,1280,1280,in, deg, , ,N, , ,W, grid, 30, 367104.0, 4757504.0,N
	Point04,xy,0,1280,in, deg, , ,N, , ,W, grid, 30, 364544.0, 4757504.0,N
	...
 */
 
 double[] FicheroCalibracionOzi()
 {       
    String entrada;   
    String[] val; 
    boolean primeraVez = true;
    double minLat=0, minLon=0, maxLat=0, maxLon=0;
    double minLonAsociado=0, minLatAsociado=0, maxLonAsociado=0, maxLatAsociado=0;
    double minX=0, maxY=0, maxX=0, minY=0;
    byte huso_izqda=0, huso_dcha=0;
    int puntos = 0;
    boolean datum_determinado = false; // Cuando se sepa el datum, se pondra a true.
    boolean wgs84 = true;        
    double lat=0, lon=0;
    Datum datumOri = null;
        
    while (((entrada=LeerLinea()) != null)) // && (resultado == true))
    {    
      if (datum_determinado == false)
      {
      	 JSigpac.Traza("linea: " + entrada);
      	 if (entrada.trim().startsWith("European 1950"))
         {      
            wgs84 = false; 
            datum_determinado = true; 
            coordenadas.EstablecerDatum(Datum.datumED50);
         } else if (entrada.trim().startsWith("WGS 84"))
         {
            wgs84 = true;
            datum_determinado = true;
            coordenadas.EstablecerDatum(Datum.datumWGS84);
         }
      } else  // Ya conocemos el datum del fichero:
      {                
         // Tengo que buscar las lineas que empiezan por "Point" seguido de un número (son los puntos de calibración):   
         // Ejemplo:   Point01,xy,0,0,in, deg, , ,N, , ,W, grid, 30, 364544.0, 4760064.0,N                                  
         
         String[] resto;
         String[] aux;
         Coordenada utmORI, utmDST;
         double x, y;
         byte huso;
         boolean norte;
   
         val = entrada.trim().split("(Point\\d+,)");
         if (val.length <= 1)
            continue;
            
         try {
           for (int i=0; i<val.length; i++)
               JSigpac.Traza("i="+i+"  :"+val[i]);
            
 
           // En val[1] hay: xy,0,0,in, deg, , ,N, , ,W, grid, 30, 364544.0, 4760064.0,N
           resto = val[1].split(",");
           for (int i=0; i<resto.length; i++)
               JSigpac.Traza("i="+i+"  :"+resto[i]);           
           x = Double.valueOf(resto[13].trim()).doubleValue();   
           y = Double.valueOf(resto[14].trim()).doubleValue();              
           
           huso = Byte.valueOf(resto[12].trim()).byteValue();
           norte = true; // Por ahora consideramos siempre que trabajamos en el hemisferio norte !!
           
           datumOri = wgs84 ? Datum.datumWGS84 : Datum.datumED50;
           utmORI = new Coordenada(datumOri, x, y, 0, huso, norte);
                      
           lat = utmORI.getLat();
           lon = utmORI.getLon();
           coordenadas.PutPunto(lat, lon);
           JSigpac.Traza("lat="+lat+"   lon="+lon);
           
           if (primeraVez)
           {
              primeraVez = false;
              minLat = maxLat = lat;              
              minLonAsociado = maxLonAsociado = lon;
              minX = maxX = x; 
              huso_izqda = huso;
              minLon = maxLon = lon;
              minLatAsociado = maxLatAsociado = lat;
              minY = maxY = y;
              huso_dcha = huso;
              //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
           } else
           {
              if (lat < minLat)
              {
                 minLat = lat;
                 minLonAsociado = lon;                                  
                 if (huso >= huso_dcha)                 
                   if (x >= maxX && y <= minY)
                   {
                      maxX = x;
                      minY = y;
                      huso_dcha = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }                                 
              }
              if (lat > maxLat)
              {
                 maxLat = lat;
                 maxLonAsociado = lon;
                 if (huso <= huso_izqda)
                   if (x <= minX && y >= maxY)
                   {
                      minX = x;
                      maxY = y;                   
                      huso_izqda = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }
              }
              if (lon < minLon)
              {
                 minLon = lon;
                 minLatAsociado = lat;
                 if (huso <= huso_izqda)                 
                   if (x <= minX && y >= maxY)
                   {
                      minX = x;
                      maxY = y;
                      huso_izqda = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }
              }
              if (lon > maxLon)
              {
                maxLon = lon;
                maxLatAsociado = lat;
                if (huso >= huso_dcha)                 
                   if (x >= maxX && y <= minY)
                   {
                      maxX = x;
                      minY = y;
                      huso_dcha = huso;
                      //JSigpac.Traza("minX="+String.valueOf(minX)+"  maxY="+String.valueOf(maxY)+"   maxX="+String.valueOf(maxX)+"  minY="+String.valueOf(minY));
                   }
              }
           }
           puntos++;
           
         } catch (ArrayIndexOutOfBoundsException aio) {
            mensaje_error = "AIOB: Formato no esperado en la linea: " + entrada.trim();
            return null;
         } catch (StringIndexOutOfBoundsException sie) {
            mensaje_error = "SIOB: Formato no esperado en la linea: " + entrada.trim();
            return null;
         } catch(NumberFormatException nfe) {
            //mensaje_error = "NF: Formato no esperado en la linea: " + entrada.trim();
            //return null;
         }
      }
    }
                           	    
    if (puntos < 2)
    {
       mensaje_error = "Debe haber al menos 2 puntos";       
       return null;
    } else
    {
       //System.out.println("minLon="+minLon+"   maxLat="+maxLat+"     maxLon="+maxLon+"  minLat="+minLat);
       //return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84); 
       Coordenada si, id;
       si = new Coordenada(datumOri, minX, maxY, 0, huso_izqda, true);  
       id = new Coordenada(datumOri, maxX, minY, 0, huso_dcha, true);
       JSigpac.Traza("six=" + String.valueOf(si.getX()) + "siy=" + String.valueOf(si.getY()) + "idx=" + String.valueOf(id.getX()) + "idy=" + String.valueOf(id.getY()));
       minLat = id.getLat();
       maxLat = si.getLat();
       minLon = si.getLon();
       maxLon = id.getLon();       
       return DevolverEsquinas(minLat, maxLat, minLon, maxLon, wgs84);  
    }
  }          
  
}  // Fin de class Fichero


class MiFiltro extends FileFilter {

 String[] mi_extension;
 MiFiltro(String[] extensiones)
 {
   mi_extension = extensiones;
 }
 
 public boolean accept(File f)
 {
   if (f.isDirectory())
      return true;

   String extension = Fichero.getExtension(f);
   
   for (int i=0; i<mi_extension.length; i++)
   {
      if (extension != null)
      {
        if (extension.equals(mi_extension[i]))
          return true;
      }
   }

   return false;
 }

 //The description of this filter
 public String getDescription() {
    String desc="*.*";
    if (mi_extension.length > 0)
       desc = "*." + mi_extension[0];
    for (int i=1; i<mi_extension.length; i++)
        desc = desc + ", *." + mi_extension[i];
    
    return desc;
 }
}


class Conversor extends Fichero
{
  Datum datumORI, datumDST;
  boolean convertirARutaOzi; // Si está a "true" significa que se quiere obtener el fichero de ruta para Ozi (para poder navegar 
                             // sobre esa ruta en Ozi de PPC). Si está a "false" funcionará como hasta ahora, es decir, convertirá
                             // el fichero que se le pasa de un datum a otro.
  String datum_destino;  // Contiene "_ED50" o "_WGS84"
  
  Conversor(MiArea area, Component com)
  {
     super(area, com);	
  }
}


/***************************************************
 *
 *     ConversorTrackCompe
 *
 ***************************************************/


class ConversorTrackCompe extends Conversor
{ 
 String[] extension = {"trk", "TRK"}; 

 ConversorTrackCompe(MiArea area, Component com)
 {
    this(area, com, false);
 }

 ConversorTrackCompe(MiArea area, Component com, boolean convertir_a_rutaOzi)
 {
   super(area, com);  
   convertirARutaOzi = convertir_a_rutaOzi;
   JSigpac.Traza("Constructor ConversorTrackCompe convertir_a_rutaOzi="+convertir_a_rutaOzi);   
   if (convertirARutaOzi)
      EscribirTexto("Generar una ruta OziExplorerCE a partir de un track de Compe");
   else
      EscribirTexto("Conversion de Tracks de Compe");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorTrackCompe");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;

   if (convertirARutaOzi)
      EscribirLinea(CabeceraFichRutaOzi()); 
      
   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      //System.out.println("Linea de entrada <"+entrada+">");
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.
      if (entrada.trim().startsWith("G"))
      {
         salida = ModificarLinea_G(entrada); 
         if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("T"))
      {
         salida = ModificarLinea_T(entrada);         
         resultado = EscribirLinea(salida); 
      } else if (!convertirARutaOzi)
         resultado = EscribirLinea(entrada); 
   }
   return resultado;
 }

    
 String ModificarLinea_G(String entrada)
 {
    String salida="";
    //Pattern p = Pattern.compile("(\\s*G\\s*)");
    //Matcher m;
    //boolean b;
    String[] val;
    //m = p.matcher(entrada);
    //System.out.println("Cadena <"+s+">  "+m.matches());
    // Segun sea el datum de entrada, asi sera el de salida:
    val = entrada.trim().split("(\\s*G\\s+)");
    //val = p.split(entrada);
    //for (int i=0; i<val.length; i++)
    //    System.out.println(i+": "+val[i]);
    try {
      if (val[1].trim().equals("European 1950"))
      {
	 salida = "WGS 84";
	 datum_destino = "_WGS84";
	 
	 if (convertirARutaOzi)
            FicheroDeSalida(".rt2");
         else
            FicheroDeSalida(datum_destino);
	          
	 datumORI = Datum.datumED50;
	 datumDST = Datum.datumWGS84;
      } else if (val[1].trim().equals("WGS 84"))
      {
	 salida = "European 1950";
	 datum_destino = "_ED50";
         FicheroDeSalida(datum_destino);
         
         if (convertirARutaOzi)
         {
            FicheroDeSalida(".rt2");
            // En las rutas de Ozi parece que siempre deben ir en WGS 84:
            datumDST = Datum.datumWGS84;
         } else
         {
            FicheroDeSalida(datum_destino);
            datumDST = Datum.datumED50;
         }
         
	 datumORI = Datum.datumWGS84;	 
      } else
      {
	 EscribirTexto("Datum de entrada no reconocido: "+val[1].trim());
         return null;
      }
    } catch (ArrayIndexOutOfBoundsException aio) {
	 EscribirTexto("Error en la linea: <"+entrada+">");
         return null;
    }

    return ("G  " + salida);
 }


 String ModificarLinea_T(String entrada)
 {
   int pos; 
   String aux, salida;
   String finN, finW;  // Contienen algo como "ºN" y "ºW"
   String[] val;
   Coordenada utmORI, utmDST;

   // Me deshago de la "A":
   pos = entrada.indexOf("A") + 1;
   //System.out.println(pos+"-"+entrada.substring(pos));
   aux = entrada.substring(pos).trim();
   val = aux.split(" ");
 
   try {
     // Posicion 0: Latitud
     pos = val[0].length();
     finN = val[0].substring(pos-2, pos);
     val[0] = val[0].substring(0, pos-2);
     // Posicion 1: Longitud
     pos = val[1].length();
     finW = val[1].substring(pos-2, pos);
     val[1] = val[1].substring(0, pos-2);
     //System.out.println(finN+"   "+finW+":"+val[0]);
   
     double lat = (Double.valueOf(val[0])).doubleValue();
     if (finN.equals("ºS"))
        lat = (-lat);

     double lon = (Double.valueOf(val[1])).doubleValue();
     if (finW.equals("ºW"))
        lon = (-lon);
     //System.out.println("lon="+lon);      
     
     double altura;
     if (val[5].equals("-1"))
     {
        altura = -1.0D;
        val[5] = "-1";
     } else
        altura = (Double.valueOf(val[5])).doubleValue();

     utmORI = new Coordenada(datumORI, lon, lat, altura);
     utmDST = utmORI.CambioDeDatum(datumDST);
 
     if (utmDST.getLon() < 0)
     {
        lon = (-utmDST.getLon());
        finW = "ºW";
     } else
     {
        lon = utmDST.getLon();
        finW = "ºE";
     }

     coordenadas.PutPunto(utmDST.getLat(), utmDST.getLon());
     
     if (convertirARutaOzi)
         salida = "W,RW" + Fichero.df.format(coordenadas.NumPuntos()) + ",  " + Mapa.df.format(utmDST.getLat()) + ",  " + Mapa.df.format(utmDST.getLon());
     else
     {
        // Habria que hacer lo mismo para el ºN y ºS pero como vamos a 
        // trabajar con coordenadas de Espa±a, siempre sera ºN.
      
        salida = "T  A " + utmDST.getLat() + finN + " " + lon + finW;
 
        for (int i=2; i<val.length; i++)
            salida = salida + " " + val[i];
     }
   } catch (ArrayIndexOutOfBoundsException aio) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch (StringIndexOutOfBoundsException sie) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch(NumberFormatException nfe) {
      EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
      return null;
   }
   
   return salida;
 }
}


/***************************************************
 *
 *     ConversorRutaCompe 
 *
 ***************************************************/

class ConversorRutaCompe extends Conversor
{
 String[] extension = {"rte", "RTE"};

 ConversorRutaCompe(MiArea area, Component com)
 {
    this(area, com, false);
 }

 ConversorRutaCompe(MiArea area, Component com, boolean convertir_a_rutaOzi)
 {
   super(area, com);
   convertirARutaOzi = convertir_a_rutaOzi;
   JSigpac.Traza("Constructor ConversorRutaCompe convertir_a_rutaOzi="+convertir_a_rutaOzi);   
   if (convertirARutaOzi)
      EscribirTexto("Generar una ruta OziExplorerCE a partir de una ruta de Compe");
   else
      EscribirTexto("Conversion de Rutas de Compe");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorRutaCompe");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;

   if (convertirARutaOzi)
      EscribirLinea(CabeceraFichRutaOzi());  
      
   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.
      if (entrada.trim().startsWith("G"))
      {
	 //System.out.println("Linea que empieza por G");
         salida = ModificarLinea_G(entrada); 
	 //System.out.println("Salida:"+salida);
	 if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
    /*
      } else if (entrada.trim().startsWith("R"))
      {
	 //System.out.println("Linea que empieza por R");
         salida = ModificarLinea_R(entrada); 
	 //System.out.println("Salida:"+salida);
	 if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
    */
      } else if (entrada.trim().startsWith("W"))
      {
	 //System.out.println("Linea que empieza por W");
         salida = ModificarLinea_W(entrada); 
	 //System.out.println("Salida:"+salida);	
         resultado = EscribirLinea(salida); 
      } else if (!convertirARutaOzi)
      {
	 //System.out.println("Salida:"+entrada);	 	 
         resultado = EscribirLinea(entrada); 
      }
   }
   return resultado;
 }

 String ModificarLinea_G(String entrada)
 {
    String salida=null;
    //Pattern p = Pattern.compile("(\\s*G\\s*)");
    //Matcher m;
    //boolean b;
    String[] val;
    //m = p.matcher(entrada);
    //System.out.println("Cadena <"+s+">  "+m.matches());
    // Segun sea el datum de entrada, asi sera el de salida:
    //val = p.split(entrada);
    val = entrada.trim().split("(\\s*G\\s+)");
    try {
      if (val[1].trim().equals("European 1950"))
      {
	 salida = "WGS 84";
	 datum_destino = "_WGS84";
	 if (convertirARutaOzi)
            FicheroDeSalida(".rt2");
         else
            FicheroDeSalida(datum_destino);
	 datumORI = Datum.datumED50;
	 datumDST = Datum.datumWGS84;
      } else if (val[1].trim().equals("WGS 84"))
      {
	 salida = "European 1950";
	 datum_destino = "_ED50";
	 
	 if (convertirARutaOzi)
         {
            FicheroDeSalida(".rt2");
            // En las rutas de Ozi parece que siempre deben ir en WGS 84:
            datumDST = Datum.datumWGS84;
         } else
         {
            FicheroDeSalida(datum_destino);
            datumDST = Datum.datumED50;
         }
  	         
	 datumORI = Datum.datumWGS84;	
      } else
      {
	 EscribirTexto("Datum de entrada no reconocido: "+val[1].trim());
         return null;
      }
    } catch (ArrayIndexOutOfBoundsException aio) {
	 EscribirTexto("Error en la linea: <"+entrada+">");
         return null;
    }

    return ("G  " + salida);
 }

 String ModificarLinea_R(String entrada)
 {
    String salida=" R  ";
    String[] val;

    val = entrada.trim().split("(\\s+,*\\s*)");
    //for (int i=0; i<valos.length; i++)
    //    System.out.println("i="+i+"  :"+val[i]);
    try {
      val[2] = val[2] + datum_destino;

      //System.out.println("DESPUES DE ANADIR LA COLETILLA");
      //for (int i=0; i<val.length; i++)
      //    System.out.println("i="+i+"  :"+val[i]);

      salida = " R   " + val[1];
      for (int i=2; i<val.length; i++)
          salida = salida + " , " + val[i];
    } catch (ArrayIndexOutOfBoundsException aio) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch (StringIndexOutOfBoundsException sie) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch(NumberFormatException nfe) {
       EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
       return null;
    }

    return salida;
 }


 String ModificarLinea_W(String entrada)
 {
   int pos; 
   String salida=" W ";
   String finN, finW;  // Contienen algo como "ºN" y "ºW"
   String[] val;
   Coordenada utmORI, utmDST;

   val = entrada.trim().split("\\s+");

   //for (int i=0; i<val.length; i++)
   //    System.out.println("i="+i+"  :"+val[i]);

   try {
     // Posicion 3: Latitud
     pos = val[3].length();
     finN = val[3].substring(pos-2, pos);
     val[3] = val[3].substring(0, pos-2);
     // Posicion 4: Longitud
     pos = val[4].length();
     finW = val[4].substring(pos-2, pos);
     val[4] = val[4].substring(0, pos-2);
   
     double lat = (Double.valueOf(val[3])).doubleValue();
     if (finN.equals("ºS"))
        lat = (-lat);
     double lon = (Double.valueOf(val[4])).doubleValue();
     if (finW.equals("ºW"))
        lon = (-lon);
     double ele = (Double.valueOf(val[7])).doubleValue();
   
     utmORI = new Coordenada(datumORI, lon, lat, ele);
     utmDST = utmORI.CambioDeDatum(datumDST);
 
     if (utmDST.getLon() < 0)
     {
        lon = (-utmDST.getLon());
        finW = "ºW";
     } else
     {
        lon = utmDST.getLon();
        finW = "ºE";
     }
     
     coordenadas.PutPunto(utmDST.getLat(), utmDST.getLon());
     
     if (convertirARutaOzi)
        salida = "W,RW" + Fichero.df.format(coordenadas.NumPuntos()) + ",  " + Mapa.df.format(utmDST.getLat()) + ",  " + Mapa.df.format(utmDST.getLon());
     else
     { 
        for (int i=1; i<3; i++)
            salida = salida + " " + val[i];
        // Anadimos la latitud y la longitud en el nuevo datum:
        salida = salida + " " + utmDST.getLat() + finN + " " + lon + finW +
                 " " + val[5] + " " + val[6] + " " + utmDST.getZ();
     }
   } catch (ArrayIndexOutOfBoundsException aio) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch (StringIndexOutOfBoundsException sie) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch(NumberFormatException nfe) {
      EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
      return null;
   }
   return salida;
 }
}


/***************************************************
 *
 *     ConversorWayPointsCompe
 *
 ***************************************************/

class ConversorWayPointsCompe extends Conversor
{
 String[] extension = {"wpt", "WPT"};
 
 ConversorWayPointsCompe(MiArea area, Component com)
 {
    this(area, com, false);
 }

 ConversorWayPointsCompe(MiArea area, Component com, boolean convertir_a_rutaOzi)
 {
   super(area, com);
  
   convertirARutaOzi = convertir_a_rutaOzi;
   JSigpac.Traza("Constructor ConversorWayPointsCompe convertir_a_rutaOzi="+convertir_a_rutaOzi);
   if (convertirARutaOzi)
      EscribirTexto("Generar una ruta OziExplorerCE a partir de los WayPoints de Compe");
   else
      EscribirTexto("Conversion de WayPoints de Compe");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorWayPointsCompe");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;

   if (convertirARutaOzi)
      EscribirLinea(CabeceraFichRutaOzi());
   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.
      if (entrada.trim().startsWith("G"))
      {
	 //System.out.println("Linea que empieza por G");	 
         salida = ModificarLinea_G(entrada); 
	 //System.out.println("Salida:"+salida);
	 if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("W"))
      {
	 //System.out.println("Linea que empieza por W");
         salida = ModificarLinea_W(entrada); 
	 //System.out.println("Salida:"+salida);	 
         resultado = EscribirLinea(salida); 
      } else if (!convertirARutaOzi)
      {
	 //System.out.println("Salida:"+entrada);
         resultado = EscribirLinea(entrada); 
      }
   }
   return resultado;
 }

 String ModificarLinea_G(String entrada)
 {
    String salida=null;
    //Pattern p = Pattern.compile("(\\s*G\\s*)");
    //Matcher m;
    //boolean b;
    String[] val;
    //m = p.matcher(entrada);
    //System.out.println("Cadena <"+s+">  "+m.matches());
    // Segun sea el datum de entrada, asi sera el de salida:
    //val = p.split(entrada);
    val = entrada.trim().split("(\\s*G\\s+)");

    try {
      //for (int i=0; i<val.length; i++)
      //	System.out.println(i+": <"+val[i]+">");
      if (val[1].trim().equals("European 1950"))
      {
	 salida = "WGS 84";
	 datum_destino = "_WGS84";
	 if (convertirARutaOzi)
            FicheroDeSalida(".rt2");
         else
            FicheroDeSalida(datum_destino);        
	 datumORI = Datum.datumED50;
	 datumDST = Datum.datumWGS84;
      } else if (val[1].trim().equals("WGS 84"))
      {
	 salida = "European 1950";
	 datum_destino = "_ED50";
	 
	 if (convertirARutaOzi)
         {
            FicheroDeSalida(".rt2");
            // En las rutas de Ozi parece que siempre deben ir en WGS 84:
            datumDST = Datum.datumWGS84;
         } else
         {
            FicheroDeSalida(datum_destino);
            datumDST = Datum.datumED50;
         }
	          
	 datumORI = Datum.datumWGS84;
      } else
      {
         EscribirTexto("Datum de entrada no reconocido: "+val[1].trim());
         return null;
      }
    } catch (ArrayIndexOutOfBoundsException aio) {
	 EscribirTexto("Error en la linea: <"+entrada+">");
         return null;
    }

    return ("G  " + salida);
 }


 String ModificarLinea_W(String entrada)
 {
   int pos; 
   String salida="W ";
   String finN, finW;  // Contienen algo como "ºN" y "ºW"
   String[] val;
   Coordenada utmORI, utmDST;

   val = entrada.trim().split("\\s+");

   try {
     //for (int i=0; i<val.length; i++)
     //    System.out.println("i="+i+"  :"+val[i]);
     // Posicion 3: Latitud
     pos = val[3].length();
     finN = val[3].substring(pos-2, pos);
     val[3] = val[3].substring(0, pos-2);
     // Posicion 4: Longitud
     pos = val[4].length();
     finW = val[4].substring(pos-2, pos);
     val[4] = val[4].substring(0, pos-2);
   
     double lat = (Double.valueOf(val[3])).doubleValue();
     if (finN.equals("ºS"))
        lat = (-lat);
     double lon = (Double.valueOf(val[4])).doubleValue();
     if (finW.equals("ºW"))
        lon = (-lon);
     double ele = (Double.valueOf(val[7])).doubleValue();
   
     utmORI = new Coordenada(datumORI, lon, lat, ele);
     utmDST = utmORI.CambioDeDatum(datumDST);
 
     if (utmDST.getLon() < 0)
     {
        lon = (-utmDST.getLon());
        finW = "ºW";
     } else
     {
        lon = utmDST.getLon();
        finW = "ºE";
     }
      
     coordenadas.PutPunto(utmDST.getLat(), utmDST.getLon());
      
     // W,RW001,  41.6317794,  -4.7283218,0
     if (convertirARutaOzi)
        salida = "W,RW" + Fichero.df.format(coordenadas.NumPuntos()) + ",  " + Mapa.df.format(utmDST.getLat()) + ",  " + Mapa.df.format(utmDST.getLon());
     else
     {
        for (int i=1; i<3; i++)
            salida = salida + " " + val[i];
        // Anadimos la latitud y la longitud en el nuevo datum:
        salida = salida + " " + utmDST.getLat() + finN + " " + lon + finW +
                 " " + val[5] + " " + val[6] + " " + utmDST.getZ();
     }
   } catch (ArrayIndexOutOfBoundsException aio) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch (StringIndexOutOfBoundsException sie) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch(NumberFormatException nfe) {
      EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
      return null;
   }

   return salida;
 }
}


/***************************************************
 *
 *     ConversorMapaCompe
 *
 ***************************************************/

class ConversorMapaCompe extends Conversor
{
 String[] extension = {"imp", "IMP"};
 
 ConversorMapaCompe(MiArea area, Component com)
 {
   super(area, com);
   JSigpac.Traza("Constructor ConversorMapaCompe");
   EscribirTexto("Conversion de ficheros de calibracion de Compe");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorMapaCompe");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;

   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.
      if (entrada.trim().startsWith("Datum="))
      {
	 //System.out.println("Linea que empieza por Datum=");
         salida = ModificarLinea_Datum(entrada); 
	 //System.out.println("Salida:"+salida);
         resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("P"))
      {
	 //System.out.println("Linea que empieza por P");
         salida = ModificarLinea_P(entrada); 
	 //System.out.println("Salida:"+salida);
         resultado = EscribirLinea(salida); 
      } else
      {
	 //System.out.println("Salida:"+entrada);
         resultado = EscribirLinea(entrada); 
      }
   }
   return resultado;
 }

 String ModificarLinea_Datum(String entrada)
 {
    String salida=null;
    String[] val;

    val = entrada.trim().split("(Datum=)");

    try {
      //for (int i=0; i<val.length; i++)
  	//System.out.println(i+": <"+val[i]+">");
      if (val[1].trim().equals("European 1950"))
      {
	 salida = "WGS84";
	 datum_destino = "_WGS84";
         FicheroDeSalida(datum_destino);
	 datumORI = Datum.datumED50;
	 datumDST = Datum.datumWGS84;
      } else if (val[1].trim().equals("WGS84"))
      {
	 salida = "European 1950";
	 datum_destino = "_ED50";
         FicheroDeSalida(datum_destino);
	 datumORI = Datum.datumWGS84;
	 datumDST = Datum.datumED50;
      } else
      {
         EscribirTexto("Datum de entrada no reconocido: "+val[1].trim());
         return null;
      }
    } catch (ArrayIndexOutOfBoundsException aio) {
	 EscribirTexto("Error en la linea: <"+entrada+">");
         return null;
    }

    return ("Datum=" + salida);
 }


 String ModificarLinea_P(String entrada)
 {
   String salida="";   
   String[] val;
   String[] resto;
   String[] aux;
   Coordenada utmORI, utmDST;
   double x, y;
   byte huso;
   boolean norte;
   
   val = entrada.trim().split("(P\\d+=)");
   
   try {
     //for (int i=0; i<val.length; i++)
     //    System.out.println("i="+i+"  :"+val[i]);
 
     // Tengo que tener en cuenta que hay otra linea que
     // empieza por la letra "P" que es: "Projection=0,UTM,29,N,"
     if (val.length <= 1)
        return entrada;
 
     // En val[1] hay: 6180.0,4210.0,29T,511891.0,4700597.229773463
     resto = val[1].split(",");
     //for (int i=0; i<resto.length; i++)
     //    System.out.println("i="+i+"  :"+resto[i]);
     x = Double.valueOf(resto[3]).doubleValue();   
     y = Double.valueOf(resto[4]).doubleValue();   
     aux = resto[2].split("T");  // Contiene algo como "29T".
     //for (int i=0; i<aux.length; i++)
     //   System.out.println("i="+i+"  :"+aux[i]);
     huso = Byte.valueOf(aux[0]).byteValue();
     norte = true; // Por ahora consideramos siempre que trabajamos en el hemisferio norte !!
     
     utmORI = new Coordenada(datumORI, x, y, 0, huso, norte);
     utmDST = utmORI.CambioDeDatum(datumDST);
 
     salida = val[0] + resto[0]+ "," + resto[1] + "," +
              resto[2] + "," + utmDST.getX() + "," + utmDST.getY();
   } catch (ArrayIndexOutOfBoundsException aio) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch (StringIndexOutOfBoundsException sie) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch(NumberFormatException nfe) {
      EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
      return null;
   }
 
   return salida;
 }
}



/***************************************************
 *
 *     ConversorMapaOzi
 *
 ***************************************************/

class ConversorMapaOzi extends Conversor
{
 String[] extension = {"map", "MAP"};

 ConversorMapaOzi(MiArea area, Component com)
 {
   super(area, com);   
   JSigpac.Traza("Constructor ConversorMapaOzi");
   EscribirTexto("Conversion de ficheros de calibracion de Ozi");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorMapaOzi");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;

   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.
      if (entrada.trim().startsWith("European 1950"))
      {
         salida = ModificarLinea_ED50(entrada); 
         resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("WGS 84"))
      {
         salida = ModificarLinea_WGS84(entrada); 
         resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("Point"))
      {
         salida = ModificarLinea_Point(entrada); 
         resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("MMPLL"))
      {
         salida = ModificarLinea_MMPLL(entrada); 
         resultado = EscribirLinea(salida); 
      } else
         resultado = EscribirLinea(entrada); 
   }
   return resultado;
 }

 String ModificarLinea_ED50(String entrada)
 {
    String salida=null;
    String[] trozos;
    trozos = entrada.trim().split(",");

    salida = "WGS 84";
    datum_destino = "_WGS84";
    FicheroDeSalida(datum_destino);
    datumORI = Datum.datumED50;
    datumDST = Datum.datumWGS84;

    for (int i=1; i<trozos.length; i++)
       salida = salida + "," + trozos[i]; 

    return salida;
 }

 String ModificarLinea_WGS84(String entrada)
 {
    String salida=null;
    String[] trozos;
    // Segun sea el datum de entrada, asi sera el de salida:
    trozos = entrada.trim().split(",");

    salida = "European 1950";
    datum_destino = "_ED50";
    FicheroDeSalida(datum_destino);
    datumORI = Datum.datumWGS84;
    datumDST = Datum.datumED50;

    for (int i=1; i<trozos.length; i++)
       salida = salida + "," + trozos[i]; 

    return salida;
 }

 String ModificarLinea_Point(String entrada)
 {
    // Point01,xy,0,0,in, deg, , ,N, , ,W, grid, 29, 508801.0, 4702702.229773463,N
    double x, y, z;
    byte huso;
    String salida = "";
    String[] val;
    Coordenada utmORI, utmDST;
    // Segun sea el datum de entrada, asi sera el de salida:
    val = entrada.trim().split(",");

    try {
      //for (int i=0; i<val.length; i++)
        //System.out.println("i="+i+"  :"+val[i]);
      huso = (Byte.valueOf(val[13].trim())).byteValue();
      x = (Double.valueOf(val[14].trim())).doubleValue();
      y = (Double.valueOf(val[15].trim())).doubleValue();
      z = 0.0D;
      utmORI = new Coordenada(datumORI, x, y, z, huso, true);
      utmDST = utmORI.CambioDeDatum(datumDST);

      salida = val[0];
      for (int i=1; i<14; i++)
         salida = salida + "," + val[i];

      salida = salida + "," + utmDST.getX() + "," + utmDST.getY() + ",N";
    } catch (ArrayIndexOutOfBoundsException aio) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch (StringIndexOutOfBoundsException sie) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch(NumberFormatException nfe) {
       //salida = entrada;
       EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
       return null;
    }

    return salida;
 }

 String ModificarLinea_MMPLL(String entrada)
 {
    String salida;
    String[] val;
    Coordenada utmORI, utmDST;
    // Segun sea el datum de entrada, asi sera el de salida:
    val = entrada.trim().split(",");

    try {
      double lon = (Double.valueOf(val[2])).doubleValue();
      double lat = (Double.valueOf(val[3])).doubleValue();
      utmORI = new Coordenada(datumORI, lon, lat, 0);
      utmDST = utmORI.CambioDeDatum(datumDST);

      salida = val[0] + "," + val[1] + "," +
  	     utmDST.getLon() + "," + utmDST.getLat();
    } catch (ArrayIndexOutOfBoundsException aio) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch (StringIndexOutOfBoundsException sie) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch(NumberFormatException nfe) {
       EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
       return null;
    }

    return salida;
 }

}


/***************************************************
 *
 *     ConversorWayPointsOzi
 *
 ***************************************************/

class ConversorWayPointsOzi extends Conversor
{
 String[] extension = {"wpt", "WPT"};
 
 ConversorWayPointsOzi(MiArea area, Component com)
 {
    this(area, com, false);
 }

 ConversorWayPointsOzi(MiArea area, Component com, boolean convertir_a_rutaOzi)
 {
   super(area, com);
   convertirARutaOzi = convertir_a_rutaOzi;
   JSigpac.Traza("Constructor ConversorWayPointsOzi convertirARutaOzi="+convertir_a_rutaOzi);
  
   if (convertirARutaOzi)
      EscribirTexto("Generar una ruta OziExplorerCE a partir de los WayPoints de OziExplorer");
   else
      EscribirTexto("Conversion de WayPoints de Ozi");
   
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorWayPointsOzi");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;

   if (convertirARutaOzi)
      EscribirLinea(CabeceraFichRutaOzi());
      
   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      //System.out.println("LeerLinea="+entrada);
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.
      if (entrada.trim().startsWith("European 1950"))
      {
         salida = ModificarLinea_ED50(entrada); 
         if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("WGS 84"))
      {
         salida = ModificarLinea_WGS84(entrada); 
         if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
      } else 
      {
         salida = ModificarLinea_Resto(entrada); 
         resultado = EscribirLinea(salida); 
      }
   }
   return resultado;
 }

 String ModificarLinea_ED50(String entrada)
 {
    String salida;

    //System.out.println("El datum de entrada es ED50");
    salida = "WGS 84";
    datum_destino = "_WGS84";
    if (convertirARutaOzi)
       FicheroDeSalida(".rt2");
    else
       FicheroDeSalida(datum_destino);
    datumORI = Datum.datumED50;
    datumDST = Datum.datumWGS84;

    return salida;
 }

 String ModificarLinea_WGS84(String entrada)
 {
    String salida;
    //System.out.println("El datum de entrada es WGS84");
    // Segun sea el datum de entrada, asi sera el de salida:

    salida = "European 1950";
    datum_destino = "_ED50";
    
    if (convertirARutaOzi)
    {
       FicheroDeSalida(".rt2");
       // En las rutas de Ozi parece que siempre deben ir en WGS 84:
       datumDST = Datum.datumWGS84;
    } else
    {
       FicheroDeSalida(datum_destino);
       datumDST = Datum.datumED50;
    }
    
    datumORI = Datum.datumWGS84;
    return salida;
 }

 String ModificarLinea_Resto(String entrada)
 {
    // Tengo que mirar si la linea empieza por un numero seguido de una coma
    // en cuyo caso hare el tratamiento de cambio de datum:
    Coordenada utmORI, utmDST;
    //Pattern p = Pattern.compile("^\\d+,.*");
    Pattern p = Pattern.compile("^-??\\d+,.*"); // El guion inicial es porque a veces en lugar de numerar los waypoints, la linea empieza con -1
    Matcher m;
    String[] val;
    String salida=null;
    m = p.matcher(entrada);

    if (m.matches() == false)
       return entrada;

    // Segun sea el datum de entrada, asi sera el de salida:
    val = entrada.trim().split(",");
    //for (int i=0; i<val.length; i++)
    //	System.out.println(i+": <"+val[i]+">");

    try {
      double lat = (Double.valueOf(val[2])).doubleValue();
      double lon = (Double.valueOf(val[3])).doubleValue();
      utmORI = new Coordenada(datumORI, lon, lat, 0);
      utmDST = utmORI.CambioDeDatum(datumDST);

      coordenadas.PutPunto(utmDST.getLat(), utmDST.getLon());
      
      // W,RW001,  41.6317794,  -4.7283218,0
      if (convertirARutaOzi)
         salida = "W,RW" + Fichero.df.format(coordenadas.NumPuntos()) + ",  " + Mapa.df.format(utmDST.getLat()) + ",  " + Mapa.df.format(utmDST.getLon());
      else
      {
         salida = val[0] + "," + val[1] + "," +
	          utmDST.getLat() + "," + utmDST.getLon();

         for (int i=4; i<val.length; i++)
      	     salida = salida + "," + val[i];
      }
      
    } catch (ArrayIndexOutOfBoundsException aio) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch (StringIndexOutOfBoundsException sie) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch(NumberFormatException nfe) {
       EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
       return null;
    }

    return salida;
 }

}


/***************************************************
 *
 *     ConversorRutaOzi 
 *
 ***************************************************/

class ConversorRutaOzi extends Conversor
{
 String[] extension = {"rte", "RTE"};
 
 ConversorRutaOzi(MiArea area, Component com)
 {
    this(area, com, false);
 }

 ConversorRutaOzi(MiArea area, Component com, boolean convertir_a_rutaOzi) 
 {
   super(area, com);   
   convertirARutaOzi = convertir_a_rutaOzi;
   JSigpac.Traza("Constructor ConversorRutaOzi convertirARutaOzi="+convertir_a_rutaOzi);
   
   if (convertirARutaOzi)
      EscribirTexto("Generar una ruta OziExplorerCE a partir de una ruta de OziExplorer");
   else
      EscribirTexto("Conversion de Rutas de Ozi");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorRutaOzi");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;

   if (convertirARutaOzi)
      EscribirLinea(CabeceraFichRutaOzi()); 
      
   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.
      if (entrada.trim().startsWith("European 1950"))
      {      	 
         salida = ModificarLinea_ED50(entrada); 
         if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("WGS 84"))
      {      	 
         salida = ModificarLinea_WGS84(entrada); 
         if (!convertirARutaOzi)
            resultado = EscribirLinea(salida); 
      } else if (entrada.trim().startsWith("W"))
      {
	 //System.out.println("Linea que empieza por W");
         salida = ModificarLinea_W(entrada); 
	 //System.out.println("Salida:"+salida);
         resultado = EscribirLinea(salida); 
      } else
      {
	 //System.out.println("Salida:"+entrada);
	 if (!convertirARutaOzi)
            resultado = EscribirLinea(entrada); 
      }
   }
   return resultado;
 }

 String ModificarLinea_ED50(String entrada)
 {
    String salida;

    salida = "WGS 84";
    datum_destino = "_WGS84";
    if (convertirARutaOzi)
       FicheroDeSalida(".rt2");
    else
       FicheroDeSalida(datum_destino);
    datumORI = Datum.datumED50;
    datumDST = Datum.datumWGS84;

    return salida;
 }
       

 String ModificarLinea_WGS84(String entrada)
 {
    String salida;
    // Segun sea el datum de entrada, asi sera el de salida:

    salida = "European 1950";
    datum_destino = "_ED50";
    if (convertirARutaOzi)
    {
       FicheroDeSalida(".rt2");
       // En las rutas de Ozi parece que siempre deben ir en WGS 84:
       datumDST = Datum.datumWGS84;
    } else
    {
       FicheroDeSalida(datum_destino);
       datumDST = Datum.datumED50;
    }
   
    datumORI = Datum.datumWGS84;

    return salida;
 }
    

 String ModificarLinea_W(String entrada)
 {
   String salida="W";   
   String[] val;
   Coordenada utmORI, utmDST;
   int i=0;

   val = entrada.trim().split(",");

   try {
     //for (i=0; i<val.length; i++)
     //    System.out.println("i="+i+"  :"+val[i]);
 
     // Posicion 5: Latitud
     double lat = (Double.valueOf(val[5])).doubleValue();
     // Posicion 6: Longitud
     double lon = (Double.valueOf(val[6])).doubleValue();
   
     //double ele = (Double.valueOf(val[7])).doubleValue();
   
     utmORI = new Coordenada(datumORI, lon, lat, 0);
     utmDST = utmORI.CambioDeDatum(datumDST);
 
     coordenadas.PutPunto(utmDST.getLat(), utmDST.getLon());
     
     if (convertirARutaOzi)    
         salida = "W,RW" + Fichero.df.format(coordenadas.NumPuntos()) + ",  " + Mapa.df.format(utmDST.getLat()) + ",  " + Mapa.df.format(utmDST.getLon());
     else
     {    
        for (i=1; i<5; i++)
            salida = salida + "," + val[i];

        // Anadimos la latitud y la longitud en el nuevo datum:
        salida = salida + "," + utmDST.getLat() + "," + utmDST.getLon();

        for (i=7; i<val.length; i++)
            salida = salida + "," + val[i];
      }

   } catch (ArrayIndexOutOfBoundsException aio) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch (StringIndexOutOfBoundsException sie) {
      EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
      return null;
   } catch(NumberFormatException nfe) {
      EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
     return null;
   }

   return salida;
 }
}

/***************************************************
 *
 *     ConversorTrackOzi 
 *
 ***************************************************/

class ConversorTrackOzi extends Conversor
{ 
 String[] extension = {"plt", "PLT"};
 
 ConversorTrackOzi(MiArea area, Component com)
 {
    this(area, com, false);
 }
 
 ConversorTrackOzi(MiArea area, Component com, boolean convertir_a_rutaOzi)
 {
   super(area, com);
   convertirARutaOzi = convertir_a_rutaOzi;
   JSigpac.Traza("Constructor ConversorTrackOzi  convertirARutaOzi="+convertirARutaOzi);
   if (convertirARutaOzi)
      EscribirTexto("Generar una ruta OziExplorerCE a partir de un track de OziExplorer");
   else
      EscribirTexto("Conversion de Tracks de Ozi");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorTrackOzi");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;
   
   if (convertirARutaOzi)
      EscribirLinea(CabeceraFichRutaOzi());         
       
   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());
   while (((entrada=LeerLinea()) != null) && (resultado == true))
   {
      //System.out.println("ENTRADA="+entrada);
      // Habria que considerar lineas vacias al principio porque
      // pueden joder la marrana a la hora de crear el fichero de salida.      
      if (entrada.trim().startsWith("European 1950"))
      {
         salida = ModificarLinea_ED50(entrada); 
         if (!convertirARutaOzi)
            resultado = EscribirLinea(salida);
      } else if (entrada.trim().startsWith("WGS 84"))
      {
         salida = ModificarLinea_WGS84(entrada); 
         if (!convertirARutaOzi)
            resultado = EscribirLinea(salida);
      } else
      {
	 // La funcion que voy a llamar, tambien
	 // escribe en el fichero de salida:
         resultado = ModificarLinea_Resto(entrada);
      }
   }
   return resultado;
 }

 String ModificarLinea_ED50(String entrada)
 {
    String salida;

    salida = "WGS 84";
    datum_destino = "_WGS84";
    if (convertirARutaOzi)
       FicheroDeSalida(".rt2");
    else
       FicheroDeSalida(datum_destino);
    datumORI = Datum.datumED50;
    datumDST = Datum.datumWGS84;

    return salida;
 }

 String ModificarLinea_WGS84(String entrada)
 {
    String salida;
    // Segun sea el datum de entrada, asi sera el de salida:

    salida = "European 1950";
    datum_destino = "_ED50";
    if (convertirARutaOzi)
    {
       FicheroDeSalida(".rt2");
       // En las rutas de Ozi parece que siempre deben ir en WGS 84:
       datumDST = Datum.datumWGS84;
    } else
    {
       FicheroDeSalida(datum_destino);
       datumDST = Datum.datumED50;
    }
    datumORI = Datum.datumWGS84;        

    return salida;
 }

 boolean ModificarLinea_Resto(String entrada)
 {
    // Tengo que mirar si la linea empieza y contiene un solo
    // numero (que se corresponde con el numero de puntos del track:
    // en cuyo caso hare el tratamiento de cambio de datum:
    Pattern p = Pattern.compile("^\\d+");
    Matcher m;
    String linea;
    int num_puntos;

    //System.out.println("Resto: " + entrada);
    m = p.matcher(entrada.trim());

    if (m.matches() == false)
    {
       if (convertirARutaOzi)
          return true;
       else
          return EscribirLinea(entrada); 
    }
    
    // Escribimos la linea con el numero:
    if (!convertirARutaOzi)
       if (EscribirLinea(entrada) == false)       
          return false;

    num_puntos = (Integer.valueOf(entrada.trim())).intValue();
    String puntos;
    // Ahora hay que leer tantas lineas del fichero como "num_puntos":
    // (De todas formas, hay ficheros ".plt" en los que el numero de puntos
    //  especificado es mayor que el existente por lo que puede alcanzarse
    //  el final de fichero)
    //System.out.println("---Hay que leer estas lineas: " + num_puntos);
    JSigpac.Traza("---Hay que leer estas lineas: " + num_puntos);
    
    for (int i=0; i<num_puntos; i++)
    {
         linea = LeerLinea();
         if (linea == null)
            return true; //_rte_
         //System.out.println(i+".- Leida linea:" + linea);
	 puntos = TratarLineaTrack(linea, i);	 
	 //puntos = linea; //_CC_
         //System.out.println("convertida a:" + puntos);
         if (EscribirLinea(puntos) == false)
         {
            //System.out.println("EscribirLinea ha devuelto FALSE");
	    return false;
         }
    }
   
    return true;
 }


 String TratarLineaTrack(String lineaTrack, int elem)
 {
    Coordenada utmORI, utmDST;
    String[] val;
    String salida="";

    if (lineaTrack == null)
       return null;

    val = lineaTrack.trim().split(",");

    try {
      //for (int i=0; i<val.length; i++)
      // 	System.out.println(i+": <"+val[i]+">");
      double lat = (Double.valueOf(val[0])).doubleValue();
      double lon = (Double.valueOf(val[1])).doubleValue();
      utmORI = new Coordenada(datumORI, lon, lat, 0);
      utmDST = utmORI.CambioDeDatum(datumDST);

      coordenadas.PutPunto(utmDST.getLat(), utmDST.getLon());
      
      // W,RW001,  41.6317794,  -4.7283218,0
      if (convertirARutaOzi)
         salida = "W,RW" + Fichero.df.format(elem) + ",  " + Mapa.df.format(utmDST.getLat()) + ",  " + Mapa.df.format(utmDST.getLon());
      else
      {
         salida = "  " + utmDST.getLat() + ",  " + utmDST.getLon();
         for (int i=2; i<val.length; i++)
       	     salida = salida + ", " + val[i];
      }

    } catch (ArrayIndexOutOfBoundsException aio) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch (StringIndexOutOfBoundsException sie) {
       EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
       return null;
    } catch(NumberFormatException nfe) {
       EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
      return null;
    }

    //System.out.println("Devolvemos SALIDA=" + salida);
    return salida;
 }

}



/***************************************************
 *
 *     ConversorGPX 
 *
 ***************************************************/

class ConversorGPX extends Conversor
{ 
 String[] extension = {"gpx", "GPX"};
 char tipo;
 
 ConversorGPX(MiArea area, Component com, char _tipo)
 {
   super(area, com);
   tipo = _tipo;
   JSigpac.Traza("Constructor ConversorGPX");   
   EscribirTexto("Generar una ruta OziExplorerCE a partir de un track GPX");
   if (EscogerFichero(extension) == true)
   {
      EscribirTexto("Fichero de entrada: <"+fichero_entrada+">");
      if (Convertir() == true)
      {
         EscribirTexto("Proceso realizado correctamente");
         EscribirTexto("Fichero de salida: <"+fichero_salida+">");
      } else
         EscribirTexto("Se ha producido algun error en la conversion");
      EscribirTexto("Lineas leidas: " + (lineas_leidas-1));
      EscribirTexto("Posiciones tratadas: " + coordenadas.NumPuntos());
   }
   CerrarFichEscritura();
 }

 protected void finalize ()
 {
   //System.out.println("Finalizador ConversorTrackOzi");
   super.finalize();
 }

 boolean Convertir()
 {
   String entrada, salida;
   boolean resultado = true;
   
   //EspecificarDatum();
   FicheroDeSalida(".rt2");
   EscribirLinea(CabeceraFichRutaOzi());         
      
   //System.out.println("Convertir");
   //System.out.println("linea="+LeerLinea());                             
   return TratarFicheroGPX();    

 }

 void EspecificarDatum()
 {       
    datum_destino = "_WGS84";   
    FicheroDeSalida(".rt2");
    
    //datumORI = Datum.datumED50;
    //datumDST = Datum.datumWGS84;

 }

  /* Información sobre el formato GPX sacada de:
    http://www.topografix.com/GPX/1/1/
  */
 boolean TratarFicheroGPX()
 {    
    String[] val; 
    String entrada, salida;   
    boolean primeraVez = true;    
    boolean wgs84 = true; // Con ficheros GPX siempre se considera que las coordenadas van en WGS84, por definicion.
    Pattern p; //= Pattern.compile(".*<.*pt.*lat=.*lon=.*");
    Matcher m;        
    
    switch (tipo) {
    	case 't':
    	    p = Pattern.compile(".*<.*trkpt.*lat=.*lon=.*");
    	    break;
    	case 'r':
    	    p = Pattern.compile(".*<.*rtept.*lat=.*lon=.*");
    	    break;    	
    	case 'w':
    	    p = Pattern.compile(".*<.*wpt.*lat=.*lon=.*");
    	    break;
    	default:
    	    JSigpac.Traza("tipo=" + tipo + "  no definido. Por defecto, suponemos que es un track");
    	    p = Pattern.compile(".*<.*trkpt.*lat=.*lon=.*");
    	    break;
    }
        	
    while ((entrada=LeerLinea()) != null) // && (resultado == true))
    {      
    	 double lat=0, lon=0;  	       	                
         //System.out.println("entrada="+entrada.trim());
         m = p.matcher(entrada.trim());

         if (m.matches() == false)
         {
            //System.out.println("No coincide");
            //entrada = LeerLinea();
            continue; // A por otra linea porque esta no nos interesa.
         } //else
           // System.out.println("COINCIDE: " + entrada.trim());
         
         // Segun sea el datum de entrada, asi sera el de salida:
         val = entrada.trim().split("\"");
         //for (int i=0; i<val.length; i++)
         //	System.out.println(i+": <"+val[i]+">");
         try {
            lat = (Double.valueOf(val[1])).doubleValue();
            lon = (Double.valueOf(val[3])).doubleValue();            
            //System.out.println("lat="+lat+"   lon="+lon);
            // W,RW001,  41.6317794,  -4.7283218,0          
            coordenadas.PutPunto(lat, lon);  
            salida = "W,RW" + Fichero.df.format(coordenadas.NumPuntos()) + ",  " + Mapa.df.format(lat) + ",  " + Mapa.df.format(lon);
            EscribirLinea(salida);        
         } catch (ArrayIndexOutOfBoundsException aio) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            //entrada = LeerLinea();
            continue;
         } catch (StringIndexOutOfBoundsException sie) {
            EscribirTexto("Formato no esperado en linea: " + lineas_leidas);
            //entrada = LeerLinea();
            continue;
         } catch(NumberFormatException nfe) {
            EscribirTexto("Formato de numero no esperado en linea: " + lineas_leidas);
            //entrada = LeerLinea();
            continue;            
         } /*catch(PuntosOutOfBoundsException pobe) {
            EscribirTexto(pobe.getMessage());
            mensaje_error = pobe.getMessage();
            return null;	
         }*/                    
     
       //entrada = LeerLinea();
    }  // Fin while     
  	    
    return true;
  }        

}

