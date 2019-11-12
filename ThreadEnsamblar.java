import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.swing.*;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import com.sun.media.jai.codec.*;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.ComponentColorModel;

class ThreadEnsamblar extends Thread
{
  JSigpac elsigpac;
  String raiz;  
  String coletillaMapa;
  int m_ini, n_ini, m_fin, n_fin;
  int compresionJPEG = 100;
  OpcionesMapa opsMapa = null;
 
 public ThreadEnsamblar(JSigpac _elsigpac, String _raiz, int _m, int _n, int _compresionJPEG)
 {
   this(_elsigpac, _raiz, 0, 0, _m, _n , _compresionJPEG, "");
 }
 
 
 public ThreadEnsamblar(JSigpac _elsigpac, String _raiz, int _m_ini, int _n_ini, 
                                                         int _m_fin, int _n_fin, 
                                                         int _compresionJPEG, String _coletillaMapa)
 {
   elsigpac = _elsigpac;
   raiz = _raiz;
   m_ini = _m_ini;
   n_ini = _n_ini;
   m_fin = _m_fin;
   n_fin = _n_fin;
   compresionJPEG = _compresionJPEG;
   coletillaMapa = _coletillaMapa;
 }
 
 void EstablecerOpcionesMapa(OpcionesMapa _opsMapa)
 {
    opsMapa = _opsMapa;
 }
 
 public void run()
 {
   int n_cuadrantes = (m_fin-m_ini)*(n_fin-n_ini);
   Mapa auxMapa = null;
   //JSigpac.ImprimirLinea("m_ini="+m_ini+"  m_fin="+m_fin+"   n_ini="+n_ini+"   n_fin="+n_fin+"   n_cuadrantes="+n_cuadrantes);
   
   RenderedOp[] cuadrantes = new RenderedOp[n_cuadrantes];
   boolean error = false; // Variable utilizada para que en caso de error no se eliminen todos los ficheros intermedios.   
   String nom_fich;
   int i=0, j=0;
   RenderedOp image=null;   
   RenderedOp salida;
   @SuppressWarnings("unused")
   BufferedImage trans;
   ParameterBlockJAI transParams;
   boolean creadoCuadranteBlanco = false; //_hh_
   RenderedOp cuadranteBlanco = null;  //_hh_

   JSigpac.ensamblando = true;
   
   JSigpac.Traza("Ensamblar...");   
   
   int width=0, height=0;
   nom_fich = raiz + m_ini + "_" + n_ini + ".jpg"; //"0_0.jpg";          
   try
   {
     image = JAI.create("fileload", nom_fich);
     width = image.getWidth();
     height = image.getHeight();
     //System.out.println("width="+width+"   height="+height);
     //System.out.println("Mapa.width="+Mapa.width+"   Mapa.heigth="+Mapa.height);
   } catch(Exception exc) {      
      if (exc instanceof IllegalArgumentException ||
          exc instanceof RuntimeException)
      {	
         boolean resp = false;         
         if (opsMapa != null) // Reintentamos hasta tres veces la descarga de ese cuadrante:
         {
             if (opsMapa.soloTrack) //_hh_
             {
               width = (int)Mapa.width;
               height = (int)Mapa.height;
               resp = true;
             } else
             {   
               int maxdes = 2, p=0;
               if (auxMapa == null)
                  auxMapa = new Mapa(opsMapa);         
               do {
                    resp = auxMapa.ReintentarDescargaCuadrante(m_ini, n_ini);
                    p++;
                    if (resp == false)
                       continue;

                    try 
                    {   
                       JSigpac.ImprimirLinea("\n1.- Se ha reintentado (p="+p+") la descarga de: " + nom_fich);              
                       image = JAI.create("fileload", nom_fich);
                       width = image.getWidth();
                       height = image.getHeight();
                       resp = true;
                    } catch (IllegalArgumentException iae2) { 
                       JSigpac.Traza("Despues de reintento: IllegalArgumentException:" + iae2);
              	       resp = false;        	                 
                    } catch (RuntimeException rte) {
                       JSigpac.Traza("Despues de reintento: RuntimeException:" + rte);
              	       resp = false;        	                 
                    }                	
               } while (resp == false && p < maxdes);
             }
          } else       
      	     resp = false;         
         
         if (resp == false)
         {            
            JSigpac.ImprimirLinea("\tERROR2 !!!  No existe el fichero: "+nom_fich);
            JSigpac.ImprimirLinea("\t[Puede que no exista o que no hayas dado el nombre correcto\n\t al fichero a ensamblar]");
            if (JSigpac.entornoGrafico)
            {
               String msj;
               msj = "No encontrado el fichero \""+nom_fich+"\". Puede que no exista o que no hayas dado el nombre correcto al fichero a ensamblar";
               JOptionPane.showMessageDialog(elsigpac, msj,
	                "Faltan ficheros", JOptionPane.ERROR_MESSAGE);
            }
            JSigpac.ensamblando = false;
            return;
         }  
      } // Fin del if del "instanceof"      
   }   
  
   image = null;

   //JSigpac.ActivarImpresionEnFichero(raiz);
   Ensamblador.ImprimirComando(raiz, m_ini, m_fin, n_ini, n_fin, compresionJPEG);  

   int k=0;
   int oriX=0, oriY=0;
   for (i=m_ini; i<m_fin; i++)
   {
     JSigpac.ImprimirLinea("");
     JSigpac.ImprimirLinea("\tFila: " + (i-m_ini));
     for (j=n_ini; j<n_fin; j++)
     {
        nom_fich = raiz + i + "_" + j + ".jpg";
        JSigpac.ImprimirLinea("\t  " + nom_fich);	    

        try {
           image = JAI.create("fileload", nom_fich);
        } catch(IllegalArgumentException iae) {
           boolean resp = false;
           
           if (opsMapa != null) // Reintentamos hasta tres veces la descarga de ese cuadrante:
           {
            if (opsMapa.soloTrack == false) //_hh_
            {
               int maxdes = 2, p=0;
               (new File(nom_fich)).delete();
               if (auxMapa == null)
                  auxMapa = new Mapa(opsMapa);                    
               do {
                    resp = auxMapa.ReintentarDescargaCuadrante(i, j);
                    p++;
                    if (resp == false)
                       continue;

                    try 
                    {   
                       JSigpac.ImprimirLinea("\n2.- Se ha reintentado (p="+p+") la descarga de: " + nom_fich);              
                       image = JAI.create("fileload", nom_fich);
                       resp = true;
                    } catch(IllegalArgumentException iae2) { 
                       resp = false;        	                 
                    }
               } while (resp == false && p < maxdes);
         
            } else   //_hh_
            {
               JSigpac.Traza("ThreadEnsamblar   creadoCuadranteBlanco="+creadoCuadranteBlanco);
               if (creadoCuadranteBlanco == false)
                  cuadranteBlanco = CrearCuadranteBlanco(width, height);
               image = cuadranteBlanco;
               creadoCuadranteBlanco = true;    
      	       resp = true;
      	    }
           } else
              resp = false;
              
           if (resp == false) 
           {                 	             
              JSigpac.ImprimirLinea("\tERROR1 !!!  No existe el fichero: "+nom_fich);
              JSigpac.ImprimirLinea("\t[Puede que no exista o que no hayas dado el nombre correcto\n\t al fichero a ensamblar]");
              if (JSigpac.entornoGrafico)
              {
                 String msj;
                 msj = "No encontrado el fichero \""+nom_fich+"\". Puede que no exista o que no hayas dado el nombre correcto al fichero a ensamblar";
                 JOptionPane.showMessageDialog(elsigpac, msj,
	              "Faltan ficheros", JOptionPane.ERROR_MESSAGE);
              }
              JSigpac.ensamblando = false;
              return;              
           }
        }
        
        
	 try {
           trans = image.getAsBufferedImage();   

         } catch(Throwable thr1) {                
           if (thr1 instanceof OutOfMemoryError ||
               thr1 instanceof RuntimeException)
           {
              boolean resp = false;
              int p=0;
              if (opsMapa != null) // Reintentamos hasta tres veces la descarga de ese cuadrante:
              {
                 int maxdes = 2;
                 (new File(nom_fich)).delete();
                 if (auxMapa == null)
                    auxMapa = new Mapa(opsMapa);                    
                 do {
                      resp = auxMapa.ReintentarDescargaCuadrante(i, j);
                      p++;
                      if (resp == false)
                         continue;

                      try 
                      {   
                         JSigpac.ImprimirLinea("\n3.- Se ha reintentado (p="+p+") la descarga de: " + nom_fich);              
                         trans = image.getAsBufferedImage();
                         resp = true;
                      } catch(Throwable thr2) {                
                         if (thr2 instanceof OutOfMemoryError ||
                             thr2 instanceof RuntimeException)
                            resp = false;   
                      }                   
                 } while (resp == false && p < maxdes);
         
              } else       
      	         resp = false;
       
              if (resp == false) 
              {             	
                 String msj;
	         msj = "Puede ser que el fichero \""+nom_fich+"\" no sea un JPG. Compruebalo.     Pero lo mas seguro es que tengas que dar un valor mayor a la variable de entorno JSIGPAC_MEM (mirar en la web)";
                 JSigpac.ImprimirLinea("\n\tERROR (p=" + p + ") " + msj);  
                 if (JSigpac.entornoGrafico)
                 {
                    JOptionPane.showMessageDialog(elsigpac, msj,
	                "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
                 }
                 JSigpac.ensamblando = false;
	         return;
              }           	  
           } // Fin del if del "instanceof" 
        }
        
        trans = null;
	  
        transParams = new ParameterBlockJAI("Translate", "rendered");
        transParams.addSource(image);
        transParams.setParameter("xTrans", (float)oriX);
        transParams.setParameter("yTrans", (float)oriY);
        cuadrantes[k++] = JAI.create("Translate", transParams);
	oriX += width;
	image = null;
      }
      oriX = 0;
      oriY += height;
   } // Fin del "for"

   JSigpac.ImprimirLinea("\n\tAhora hacemos el mosaico...");
   //MosaicDescriptor md;
   //md = new MosaicDescriptor();
   salida = MosaicDescriptor.create(cuadrantes, MosaicDescriptor.MOSAIC_TYPE_BLEND, /*MosaicDescriptor.MOSAIC_TYPE_OVERLAY*/
		      null, null, null, null, null);
   		     
   //RenderedOp res;
   //_cc_ res = JAI.create("filestore", salida, raiz+".jpg", "jpeg", null);
   JPEGEncodeParam encodeParam = new JPEGEncodeParam();
   encodeParam.setQuality(compresionJPEG/100.0F);
   
   FileOutputStream out = null;
   ImageEncoder encoder = null;
   try {
      out = new FileOutputStream(raiz+coletillaMapa+".jpg");
      encoder = ImageCodec.createImageEncoder("JPEG", out, encodeParam);
      encoder.encode(salida);
      JSigpac.ImprimirLinea("\tFichero obtenido: "+ raiz+coletillaMapa+".jpg");
      JSigpac.ImprimirLinea("\tProceso de ensamblado finalizado.");      
   } catch(Throwable th) {
      error = true;   
      JSigpac.ImprimirLinea("\n\t-------------    Excepcion de Memoria    -------------");
      if (JSigpac.entornoGrafico)
      {
         if (elsigpac != null)
            elsigpac.SacarVentanita("Memoria insuficiente:", "Aumenta el valor de la variable de entorno JSIGPAC_MEM");             
      } else
      {
         JSigpac.ImprimirLinea("\tDebes aumentar el valor de la opción -XmxNNNm");
         JSigpac.ImprimirLinea("Excepcion Throwable: " + th);
      }
      JSigpac.ImprimirLinea("\t------------------------------------------------------");
   } finally {
      try {
      	if (out != null)
           out.close();        
      } catch (IOException ioe) {}
   }
   
   JSigpac.ImprimirLinea("");

   // Eliminamos los ficheros intermedios:
   File borrame;
   boolean borrar = true && !error;
   //_xx_ if (JSigpac.entornoGrafico)
   //_xx_    borrar = elsigpac.borrarFicheros.isSelected() && !error;
   if (opsMapa != null)
   {      
      borrar = opsMapa.borrarIntermedios && !error;
      JSigpac.Traza("opsMapa != null   borrar=" + borrar);
   } else if (JSigpac.entornoGrafico)
   {
      borrar = elsigpac.borrarFicheros.isSelected() && !error;	
      JSigpac.Traza("opsMapa es null Estamos en entorno grafico  borrar=" + borrar);
   } else {
      borrar = false;
      JSigpac.Traza("opsMapa == null NO es entorno grafico  borrar=" + borrar);
   }
    
   //Calendar fecha = Calendar.getInstance();     
   //System.out.println(fecha.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE) + ":" + fecha.get(Calendar.SECOND));              
   //System.out.println(">>>>>>>>>>>>>>>>>< borrar="+borrar);
   
   for (i=m_ini; i<m_fin; i++)
     for (j=n_ini; j<n_fin; j++)
     {
         //nom_fich = raiz + coletillaMapa + i + "_" + j; // + ".jpg";
         nom_fich = raiz + i + "_" + j; // + ".jpg";
         //System.out.println("nom_fich="+nom_fich);
         if (borrar)
	 {
             borrame = new File(nom_fich+".jpg");
             borrame.delete();
             //   System.out.println("Error al intentar borrar nom_fich="+nom_fich); 
         }
         borrame = new File(nom_fich+"_0.jpg");
	 borrame.delete();
         borrame = new File(nom_fich+"_1.jpg");
	 borrame.delete();
         borrame = new File(nom_fich+"_2.jpg");
	 borrame.delete();
         borrame = new File(nom_fich+"_3.jpg");
	 borrame.delete();
     } 
   //fecha = Calendar.getInstance();     
   //System.out.println(fecha.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE) + ":" + fecha.get(Calendar.SECOND));
   //System.out.println("Llamada al GARBAGE COLLECTION");  
   //System.gc();
   //fecha = Calendar.getInstance();     
   //System.out.println(fecha.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE) + ":" + fecha.get(Calendar.SECOND));
   //System.out.println("BASURA RECOGIDA");
   
   // Damos otra vuelta para borrar los ficheros que hayan podido quedar:
   for (i=m_ini; i<m_fin; i++)
     for (j=n_ini; j<n_fin; j++)
     {         
         nom_fich = raiz + i + "_" + j; // + ".jpg";         
         if (borrar)
	 {
             borrame = new File(nom_fich+".jpg");
             borrame.delete();              
         }
     }
     
   //fecha = Calendar.getInstance();     
   //System.out.println(fecha.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE) + ":" + fecha.get(Calendar.SECOND));  
   //System.out.println("BORRADOS todos los ficheros intermedios");
   JSigpac.ensamblando = false;
 }
 
 //_hh_ 
 
 RenderedOp CrearCuadranteBlanco(int ancho, int alto)
 {
    return CrearCuadrante(ancho, alto, raiz+"_BLANCO.jpg");
 }
 
 static RenderedOp CrearCuadrante(int ancho, int alto, String nomFich)
 {       
   RenderedOp res;
   TiledImage outImage;   
   JSigpac.Traza("CrearCuadrante ancho="+ancho+"  alto="+alto+"ColorSpace.TYPE_RGB="+ColorSpace.TYPE_RGB);  
   ComponentSampleModel csm = new ComponentSampleModel(0, ancho, alto, 1, ancho, new int[]{0,0,0});
   ComponentColorModel ccm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
  
   outImage = new TiledImage(0, 0, ancho, alto, 0, 0, csm, ccm);
                                 //loadImage1.getColorModel());	
   for (int line=0; line < alto; line++) 
     for (int samp=0; samp < ancho; samp++)
     {
         outImage.setSample(samp, line, 0, 255);
         outImage.setSample(samp, line, 1, 255);
         outImage.setSample(samp, line, 2, 255);                                 
     }
        
    ParameterBlockJAI savePB = new ParameterBlockJAI("filestore");
    savePB.setParameter("filename", nomFich);
    savePB.setParameter("format", "jpeg");
    savePB.setSource(outImage, 0);
    JAI.create("filestore", savePB); 
    
    res = JAI.create("fileload", nomFich);
    return res;    	    
 }
 
}