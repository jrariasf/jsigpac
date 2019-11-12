import java.net.*;
import java.io.*;
//import java.util.*;
/*
import java.text.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.image.renderable.*;
import javax.swing.*;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import com.sun.media.jai.codec.*;
*/

 
public class Download
{

 Download(String nomFich)
 {
   String fichero=null;
   FileOutputStream fos=null;
   byte[] datos = new byte[8192];
   URLConnection conexion;
   InputStream is;
 
   try {
     //URL url = new URL(servidor + "aspimagedispatcher.aspx?guid=c670f580deda49f691de71e85b6bd921&npetic=42&function=getutmbitmap&"+ utm +"&Width=618&Height=421&Zone="+opciones.huso);
     //URL url = new URL("http://sigpac.mapa.es/sigpac/imagenes/aspimagedispatcher.aspx?function=getutmbitmap&"+ utm +"&Width=618&Height=421&Zone="+opciones.huso);

     //JSigpac.ImprimirLinea("URL: "+url.toString());

     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=c10d7ba9eb534a42be36bcf0faa1ef39&npetic=22&function=swf&" + utm + "&Zone="+opciones.huso + "&Width=618&Height=421&layers=" + opciones.capa);
     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=c10d7ba9eb534a42be36bcf0faa1ef39&npetic=22&function=swf&" + utm + "&Zone="+opciones.huso + "&Width=618&Height=421&layers=PARCELA");
     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=1ce037d76cb3498386522c15fbf252e2&npetic=5&function=swf&x1=346798.477872&y1=4618208.693208&x2=348319.575083&y2=4619242.448595&Zone=30&Width=618&Height=421&layers=PARCELA");
     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=1ce037d76cb3498386522c15fbf252e2&npetic=8&function=thematic&x1=346798.477872&y1=4618208.693208&x2=348319.575083&y2=4619242.448595&Zone=30&Width=618&Height=421&layers=ARBOLES");
     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=1ce037d76cb3498386522c15fbf252e2&npetic=6&function=swf&x1=346798.477872&y1=4618208.693208&x2=348319.575083&y2=4619242.448595&Zone=30&Width=618&Height=421&layers=RECINTO");     
     //URL url = new URL("http://sigpac.mapa.es/fega/serializador/aspswfprovider.aspx?guid=1ce037d76cb3498386522c15fbf252e2&npetic=7&function=thematic&x1=346798.477872&y1=4618208.693208&x2=348319.575083&y2=4619242.448595&Zone=30&Width=618&Height=421&layers=OLIVOS");
     //URL url = new URL("http://tilesserver.mapa.es/tilesserver/n=topografico-mtn_25;z=30;r=8000;i=178;j=2250.jpg");
     //URL url = new URL("http://kh1.google.es/kh?n=404&v=21&t=tqsrsssttrtrtttsq"); // Mercator
     URL url = new URL("http://kh1.google.es/kh?n=404&v=3&t=tqssrrrtqrtsqqqsq");
     int leidos;
         //System.out.println("Vamos a hacer el openConnection  utm="+utm);
         conexion = url.openConnection();
	 conexion.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; es-ES; rv:1.8.0.2) Gecko/20060308 Firefox/1.5.0.2");
	 conexion.setRequestProperty("Host", "kh1.google.es");
	 conexion.setRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
	 conexion.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");	 
	 conexion.setRequestProperty("Accept-Encoding", "gzip,deflate");
	 conexion.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
	 conexion.setRequestProperty("Connection", "keep-alive");
	 conexion.setRequestProperty("Keep-Alive", "300");
	 conexion.setRequestProperty("Content-type", ""); // application/x-www-form-urlencoded


         //System.out.println("Vamos a hacer el getInputStream");
         is = conexion.getInputStream();
	      
	 fichero = nomFich + ".jpg";

         fos = new FileOutputStream(fichero);
         while ((leidos=is.read(datos)) != -1)
	     fos.write(datos, 0, leidos);
         
         if (fos != null)
	 {
	    fos.close();
	    fos = null;
         }

   } catch (UnknownHostException e) {
     System.err.println("UnknownHostException: " + e);
   } catch (MalformedURLException e) {
     System.err.println("MalformedException: " + e);
   } catch (FileNotFoundException fnfe) {
     if (fnfe.getMessage().indexOf("http://") != -1)
     {
        System.err.println("\n\tError (temporal) al intentar conectarse al SIGPAC");
        System.err.println("\tComprueba si desde tu navegador puedes conectarte a:");
        System.err.println("\t\thttp://sigpac.mapa.es/fega/visor");
     }
     else
        System.err.println("\n\tNo existe el directorio especificado:"+fnfe);
     return;
   } catch( IOException e) {
     System.err.println("Download::IOException: " + e);
   }

   return;
 }

 public static void main (String [] args)
 {
    @SuppressWarnings("unused")
	Download down = new Download(args[0]);
 }

}

