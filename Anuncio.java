import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

class Anuncio extends JLabel
{  
  String texto;
  String link;
  String[] textos;
  String[] links;
  String[] clave;
  String[] duracion;
  String[] repeticiones;
  Color[] colorFore;
  Color[] colorBack;
  ControlAnuncio controlador;
  public static String linkAnuncio = JSigpac.siteJSIGPAC + "anuncios"; //"http://www.myjavaserver.com/~jrariasf/sigpac/anuncios/jsigpac.anuncio";
  public static String anunciantes = JSigpac.siteJSIGPAC + "anunciantes"; //"http://www.myjavaserver.com/~jrariasf/sigpac/anuncios/jsigpac.anunciantes.html";
  public static String dirContadores = JSigpac.siteJSIGPAC + "contadores"; //"http://www.myjavaserver.com/~jrariasf/sigpac/anuncios/contadores/";
  public static final int maxNumAnuncios = 100;
  public static final int repeticionesPorAnuncio = 1;
  int id_anuncio;
  int numAnuncios;
  JPanel fondoAnuncio;
  public static final long serialVersionUID = 0;
  
 public Anuncio(JPanel _fondoAnuncio)
 {    
     numAnuncios = 0;    
     fondoAnuncio = _fondoAnuncio;  
     LeerAnuncioDeInternet(); 
     Inicializar();    
 } 
 
 /* 
 public Anuncio(String _texto)
 {
     this(_texto, "");
 }
 

 public Anuncio(String _texto, String _link)
 {
     super(_texto);
     Inicializar(_texto, _link);  
 }
 */
  
 public void Inicializar()
 {
     id_anuncio = 0;    
     AsignarValores(id_anuncio);         
     setPreferredSize(new Dimension (180, 30));
     controlador = new ControlAnuncio(this);
     controlador.start();   	
 }
 
 public void ReInicializar()
 {
     controlador.interrupt();         
     AsignarValores(id_anuncio);         
     //setPreferredSize(new Dimension (180, 30));
     controlador = new ControlAnuncio(this);
     controlador.start();   	
 }
 
 
 public void AsignarValores(int id)
 {
     //texto = _texto;
     //link = _link;
     //System.out.println("AsignarValores:  id="+id);
     //System.out.println("colorFore=" + colorFore[id].toString() + "   colorBack=" + colorBack[id].toString());
     setForeground(colorFore[id]);
     setBackground(colorBack[id]); 
     fondoAnuncio.setBackground(colorBack[id]);    
     setToolTipText(links[id]);
 }
 
 public static Color getColor(String micolor)
 {
     if (micolor.equals("black"))
        return Color.black;
     else if (micolor.equals("blue"))
        return Color.blue;
     else if (micolor.equals("cyan"))
        return Color.cyan;
     else if (micolor.equals("darkGray"))
        return Color.darkGray;
     else if (micolor.equals("gray"))
        return Color.gray;
     else if (micolor.equals("green"))
        return Color.green;
     else if (micolor.equals("lightGray"))
        return Color.lightGray;
     else if (micolor.equals("magenta"))
        return Color.magenta;
     else if (micolor.equals("orange"))
        return Color.orange;
     else if (micolor.equals("pink"))
        return Color.pink;
     else if (micolor.equals("red"))
        return Color.red;
     else if (micolor.equals("white"))
        return Color.white;
     else if (micolor.equals("yellow"))
        return Color.yellow;             
        
     return null;
 }
 
 public String getLink()
 {
     return links[id_anuncio];
 }
 
 public int PasarAlSiguienteAnuncio()
 {     
     id_anuncio++;
     if (id_anuncio >= numAnuncios)  //maxNumAnuncios || textos[id_anuncio] == null || links[id_anuncio] == null)
        id_anuncio = 0;
     AsignarValores(id_anuncio);
     return id_anuncio;
 }
 
 public int PasarAlAnteriorAnuncio()
 {     
     id_anuncio--;
     if (id_anuncio < 0)
        id_anuncio = numAnuncios-1;
     AsignarValores(id_anuncio);
     return id_anuncio;
 }
 
 void LeerAnuncioDeInternet()
 {     
   URLConnection conexion;
   InputStream is;
   
   textos = new String[maxNumAnuncios];
   links = new String[maxNumAnuncios];
   clave = new String[maxNumAnuncios];
   duracion = new String[maxNumAnuncios];
   repeticiones = new String[maxNumAnuncios];
   colorFore = new Color[maxNumAnuncios];
   colorBack = new Color[maxNumAnuncios];
   
   numAnuncios = 0;
   
   try 
   {
       
       int leidos=0;
       URL url = new URL(Anuncio.linkAnuncio);
       conexion = url.openConnection();
       is = conexion.getInputStream();
       
       byte[] datos = new byte[8192];
       String msj;
       
       leidos=is.read(datos);
       if (leidos != -1)
       {
       	  msj = new String(datos);
          //System.out.print("leidos="+leidos+"  Anuncio leido: "+msj);	  

       }     
       
       
       Properties misProperties = new Properties();	
       misProperties.load(is);
       int i=0;
       String key_texto, key_link, key_clave, key_duracion, key_repeticiones, key_fore, key_back;
       for (i=0; i<maxNumAnuncios ;i++)
       {
       	  key_texto = "texto" + i;
       	  key_link = "link" + i;
       	  key_clave = "clave" + i;
       	  key_duracion = "duracion" + i;
       	  key_repeticiones = "repeticiones"+i;
       	  key_fore = "foreground" + i;
       	  key_back = "background" + i;
          textos[i] = misProperties.getProperty(key_texto);          
          //System.err.println("key_texto: "+key_texto+"="+textos[i]);       
          links[i] = misProperties.getProperty(key_link);
          //System.err.println("key_link: "+key_link+"="+links[i]);
          clave[i] = misProperties.getProperty(key_clave, "sinclave");
          //System.err.println("key_clave: "+key_clave+"="+clave[i]);
          duracion[i] = misProperties.getProperty(key_duracion, "3000");
          //System.err.println("key_duracion: "+key_duracion+"="+duracion[i]);
          repeticiones[i] = misProperties.getProperty(key_repeticiones, "1");
          //System.err.println("key_repeticiones: "+key_repeticiones+"="+repeticiones[i]);
          colorFore[i] = Anuncio.getColor(misProperties.getProperty(key_fore, "white"));
          //System.err.println("key_fore: "+key_fore+"="+misProperties.getProperty(key_fore, "white"));
          colorBack[i] = Anuncio.getColor(misProperties.getProperty(key_back, "black"));
          //System.err.println("key_back: "+key_back+"="+misProperties.getProperty(key_back, "black"));
          if (textos[i] == null || links[i] == null)
             break;
       }
       //System.out.println("i="+i);
       
       numAnuncios = i;
       
       for (int j=i; j<maxNumAnuncios; j++)
       {
       	   textos[j] = null;
       	   links[j] = null;
       	   clave[j] = null;
       	   duracion[j] = null;
       	   repeticiones[j] = null;
       	   colorFore[j] = null;
       	   colorBack[j] = null;
       }
       
       is.close();

   } catch (UnknownHostException e) {
       JSigpac.Traza("LAI-1 UnknownHostException: " + e);       
   } catch (MalformedURLException e) {
       JSigpac.Traza("LAI-1 MalformedException: " + e);       
   } catch (FileNotFoundException fnfe) {
       JSigpac.Traza("LAI-1 FileNotFoundException: " + fnfe);       
   } catch( IOException e) {
       JSigpac.Traza("LAI-1 IOException: " + e);       
   }
  
   if (numAnuncios == 0)
   {      
      textos[0] = "Colabore con la  Fundacion jSIGPAC dandose de alta aqui...";
      links[0] = JSigpac.afiliado;
      clave[0] = "fundacion";
      duracion[0] = "3000";
      repeticiones[0] = "1";
      colorFore[0] = Color.orange;
      colorBack[0] = Color.black;
      textos[1] = "- Inserta aqui tu publicidad -";
      links[1] = Anuncio.anunciantes;
      clave[1] = "publicidad";
      duracion[1] = "3000";
      repeticiones[1] = "1";
      colorFore[1] = Color.black;
      colorBack[1] = Color.red;
      textos[2] = "ayCachorro! Diseño gráfico...";
      links[2] = "http://www.aycachorro.com/";
      clave[2] = "fundacion";
      duracion[2] = "3000";
      repeticiones[2] = "2";
      colorFore[2] = Color.white;
      colorBack[2] = Color.black;
      numAnuncios = 3;
   }              
 }
  
  
 // Se lee el contador almacenado en el fichero asociado y se incrementa en uno:
 public void RegistrarAccesoAnuncio()
 {
   URL url;
   URLConnection conexion;
   String peticion = "directo=no&";   
      
   String direccion = JSigpac.servlet + peticion + "anuncio=" + clave[id_anuncio] + "&operacion=inc";
   
   if (!JSigpac.ActualizarContadores())
      return;
   //System.out.println("direccion="+direccion); 
   try {
      url = new URL(direccion);
      conexion = url.openConnection();
      conexion.connect();
      conexion.getContent(); //Obj obj = conexion.getContent();
      //System.out.println("obj="+obj.toString());
   } catch (UnknownHostException e) {     
      System.err.println("UnknownHostException A: " + e);
   } catch (MalformedURLException e) {
      System.err.println("MalformedException A: " + e);
   } catch (FileNotFoundException fnfe) {
      System.err.println("FileNotFoundException A: "+fnfe);    
   } catch( IOException e) {
      System.err.println("IOException A: " + e);
   }  	
 	
 }
  
}


class ControlAnuncio extends Thread
{
  public static final int refrescoLetra = 300;
  public static final int tiempoEsperaAnuncio = 4000;
  Anuncio anuncio;
  
  public ControlAnuncio(Anuncio _anuncio)
  {
     anuncio = _anuncio;
  }
 
  public void run()
  {
     String texto;   
       
     int tamTotal;
     byte[] letras;
     int inicio=0;
     String cabecera, cola;
     int contador_repeticiones=0;
     
     texto = anuncio.textos[anuncio.id_anuncio];
     if (texto == null)
     {
     	System.err.println(" NO HAY PUBLICIDAD ");
     	return;
     }	
     tamTotal = texto.length() + 2; // Le sumo los dos espacios en blanco que concateno a continuación:
     letras = texto.concat("  ").getBytes();          
     
     do {	  
       try {
          sleep(ControlAnuncio.refrescoLetra);
       } catch(InterruptedException ie) {
          //System.err.println("Capturada InterruptedException (1) en run de Anuncio: "+ie);
          return;
       }
       cabecera = new String(letras, inicio, tamTotal-inicio);
       cola = new String(letras, 0, inicio);
       anuncio.setText(cabecera + cola);
       
       inicio++;
       if (inicio > tamTotal)
       {
          inicio = 0;
          try {
             //sleep(ControlAnuncio.tiempoEsperaAnuncio);
             int duracion = Integer.parseInt(anuncio.duracion[anuncio.id_anuncio]);
             sleep(duracion);
          } catch(InterruptedException ie) {
             //System.err.println("Capturada InterruptedException (2) en run de Anuncio: "+ie);
             return;
          } 
          contador_repeticiones++;
          //if (contador_repeticiones >= Anuncio.repeticionesPorAnuncio)
          int repeticiones;
          try {
             repeticiones = Integer.parseInt(anuncio.repeticiones[anuncio.id_anuncio]);
          } catch (NumberFormatException nfe) {
             repeticiones = 1;
          }
          if (contador_repeticiones >= repeticiones)
          {
             int id;
             contador_repeticiones = 0;
             // Paso al siguiente anuncio:
             id = anuncio.PasarAlSiguienteAnuncio();
             texto = anuncio.textos[id];
             tamTotal = texto.length() + 2; // Le sumo los dos espacios en blanco que concateno a continuación:
             letras = texto.concat("  ").getBytes();
          }          
        }
     }while (true);
  	
  }
	
}


   	