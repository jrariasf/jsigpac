import javax.media.jai.*;
//import javax.media.jai.widget.*;
import javax.media.jai.iterator.*;
//import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.*;

/** Sample program that loads an image, does something to every pixel, and
 * displays the result.
 */

//import java.awt.image.*;
import java.io.*;
//import javax.imageio.*;


class RGB
{
  int r, g, b;
  int h, s, v;
  public RGB(int _r, int _g, int _b)
  {
     r = _r;
     g = _g;
     b = _b;
     toHSV();
  }

  void toHSV()
  {
    int min, max;
    int delta;

    max = Math.max(Math.max(r,g), b);
    min = Math.min(Math.min(r,g), b);
    delta=max-min;

    h = -1;
    v = max;
    s = (max!=0) ? (int)(255*delta/(float)max) : 0;

    if (s != 0)
    {
       if      (r==max)  h = (int)(60 * ((g-b)/(float)delta));
       else if (g==max)  h = (int)(60 * (2 + (b-r)/(float)delta));
       else if (b==max)  h = (int)(60 * (4 + (r-g)/(float)delta));

       if (h < 0 )  h += 360;
    }

    return;
  }

  public boolean is_yellow_hsv()
  {
    if (((h >  50) && (h < 75))  &&
        ((s > 100) && (s < 255)) &&
        ((v > 180) && (v < 255)))
       return true;
    else
       return false;
  }

  // "escala_de_amarillo" devuelve un valor entre 0 y 1.
  // Cuanto mas amarillo sea el color, mas cerca de 0 estara el valor devuelto.
  public float escala_de_amarillo()
  {
    float escala=1.0F;

    if (((h >  55) && (h < 65))  &&
        ((s > 100) && (s < 255)) &&
        ((v > 230) && (v < 255)))
    {
       float aux;
       aux = (Math.abs(h-60) / 5.0F) * 0.70F;
       aux = aux + (((255-s)/155.0F) * 0.20F);
       aux = aux + (((255-v)/25.0F) * 0.10F);
    }
    return escala; // Devolvemos 1 para los colores no amarillos
  }
}

public class QuitaManchas extends Thread
{
   String nomFichBase;

   QuitaManchas (String _nomFichBase)
   {
       nomFichBase = _nomFichBase;
       //System.out.println("_xx_ nomFichBase=" + nomFichBase);
   }

   //public static void main(String[] argv)
   public void run()
   {
       ParameterBlockJAI loadPB1, loadPB2, loadPB3, loadPB4;
       RenderedOp loadImage1, loadImage2, loadImage3, loadImage4;
       TiledImage outImage;
       String uno = nomFichBase+"_0.jpg";
       String dos = nomFichBase+"_1.jpg";
       String tres = nomFichBase+"_2.jpg";
       String cuatro = nomFichBase+"_3.jpg";
             
       loadPB1 = new ParameterBlockJAI("fileload");       
       loadPB2 = new ParameterBlockJAI("fileload");       
       loadPB3 = new ParameterBlockJAI("fileload");       
       loadPB4 = new ParameterBlockJAI("fileload");       
       loadPB1.setParameter("filename", uno);
       loadPB2.setParameter("filename", dos);
       loadPB3.setParameter("filename", tres);
       loadPB4.setParameter("filename", cuatro);
       loadImage1 = JAI.create("fileload", loadPB1);
       loadImage2 = JAI.create("fileload", loadPB2);
       loadImage3 = JAI.create("fileload", loadPB3);
       loadImage4 = JAI.create("fileload", loadPB4);

       //System.out.println("#########   loadImage1.getMinX()=" + loadImage1.getMinX()+"   loadImage1.getMinY()="+loadImage1.getMinY());
       //SampleModel sm = loadImage1.getSampleModel();
       //System.out.println("#########   SampleModel  getWidth="+sm.getWidth()+"   getHeight="+sm.getHeight()+"  bands="+sm.getNumBands()+"  type="+sm.getDataType());
       ColorModel cm = loadImage1.getColorModel();
       System.out.println("#########   transferType="+cm.getTransferType()+"   getPixelSize="+cm.getPixelSize()+"  hasAlpha="+cm.hasAlpha()+"  ColorSpace_type="+cm.getColorSpace().getType()+"  ColorSpace_mincom="+cm.getColorSpace().getNumComponents()+"   CS_sRGB="+ColorSpace.CS_sRGB+"   CS_GRAY="+ColorSpace.CS_GRAY);
       //System.out.println("#########   isAlphaPremultiplied="+cm.isAlphaPremultiplied() + "   transparency="+cm.getTransparency()+" O="+Transparency.OPAQUE+"  T="+Transparency.TRANSLUCENT);
       //System.out.println("#########   getTransferType="+cm.getTransferType());   
          
       outImage = new TiledImage(loadImage1.getMinX(),
                                 loadImage1.getMinY(),
                                 loadImage1.getWidth(),
                                 loadImage1.getHeight(),
                                 loadImage1.getMinX(),
                                 loadImage1.getMinY(),
                                 loadImage1.getSampleModel(),
                                 loadImage1.getColorModel());

       // Loop over the input, copy each pixel to the output, modifying
       // them as we go

       //int bands = loadImage1.getSampleModel().getNumBands();
       int height = loadImage1.getHeight();
       int width = loadImage1.getWidth();
       //System.out.println("bands("+bands+")  alto("+height+")   ancho("+width+")");
       
       // used to access the source image
       RandomIter iter1 = RandomIterFactory.create(loadImage1, null);
       RandomIter iter2 = RandomIterFactory.create(loadImage2, null);
       RandomIter iter3 = RandomIterFactory.create(loadImage3, null);
       RandomIter iter4 = RandomIterFactory.create(loadImage4, null);
       
       int R=0, G=0, B=0;
       int r1, r2, r3, r4;
       int g1, g2, g3, g4;
       int b1, b2, b3, b4;
       int puntosDN1=0, puntosDN2=0, puntosDN3=0, puntosDN4=0,
	   puntosDN5=0, puntosDN6=0, puntosDN7=0;
       
       for (int line=0; line < height; line++) {
         for (int samp=0; samp < width; samp++) {
           r1 = iter1.getSample(samp, line, 0);
           r2 = iter2.getSample(samp, line, 0);
           r3 = iter3.getSample(samp, line, 0);
           r4 = iter4.getSample(samp, line, 0);
           g1 = iter1.getSample(samp, line, 1);
           g2 = iter2.getSample(samp, line, 1);
           g3 = iter3.getSample(samp, line, 1);
           g4 = iter4.getSample(samp, line, 1);
           b1 = iter1.getSample(samp, line, 2);
           b2 = iter2.getSample(samp, line, 2);
           b3 = iter3.getSample(samp, line, 2);
           b4 = iter4.getSample(samp, line, 2);

           if ((r1==r2 && g1==g2 && b1==b2) || (r1==r3 && g1==g3 && b1==b3) ||
	       (r1==r4 && g1==g4 && b1==b4))
           {
              R = r1;
              G = g1;
              B = b1;
              puntosDN1++;                   
           } else if ((r2==r3 && g2==g3 && b2==b3) || (r2==r4 && g2==g4 && b2==b4))
	   {
              R = r2;
              G = g2;
              B = b2;
              puntosDN2++;                   
           } else if (r3==r4 && g3==g4 && b3==b4)
	   {
              R = r3;
              G = g3;
              B = b3;
              puntosDN3++;                   
           } else // Nos quedamos con el menos amarillo de los cuatro:
           {
              RGB p2, p3, p4;	      
              //p1 = new RGB(r1,g1,b1);
              p2 = new RGB(r2,g2,b2);
              p3 = new RGB(r3,g3,b3);
              p4 = new RGB(r4,g4,b4);
              
	      /*****
	      float e1, e2, e3, e4; 
	      e1 = p1.escala_de_amarillo();
	      e2 = p2.escala_de_amarillo();
	      e3 = p3.escala_de_amarillo();

	      if (e1>=e2 && e2>=e3) {
              	 R = r1;
                 G = g1;
                 B = b1;
                 puntosDN3++;
              } else if (e2>=e1 && e1>=e3) {
                 R = r2;
                 G = g2;
                 B = b2;
                 puntosDN4++;                   
              } else {
                 R = r3;
                 G = g3;
                 B = b3;
                 puntosDN5++;                   
              }
	      *****/
		 
		 

              if (!p4.is_yellow_hsv())
              {
              	 R = r4;
                 G = g4;
                 B = b4;
                 puntosDN4++;
              } else if (!p3.is_yellow_hsv())
              {
              	 R = r3;
                 G = g3;
                 B = b3;
                 puntosDN5++;
              } else if (!p2.is_yellow_hsv())
              {
                 R = r2;
                 G = g2;
                 B = b2;     
                 puntosDN6++;      	              	
              } else
              {
                 R = r1;
                 G = g1;
                 B = b1;     
                 puntosDN7++;      	              	
              }
              
           }
                 
                   //System.out.println(dn1+"  "+dn2+"  "+dn3+"  ---->  "+dn);

                   //dn = (dn + 12) % 256;              // whatever
                   
            outImage.setSample(samp, line, 0, R);
            outImage.setSample(samp, line, 1, G);
            outImage.setSample(samp, line, 2, B);
            
         }
       }
    
       //System.out.println("puntosDN1="+puntosDN1+"  puntosDN2="+puntosDN2+"  puntosDN3="+puntosDN3+"  puntosDN4="+puntosDN4+"  puntosDN5="+puntosDN5);

       ParameterBlockJAI savePB = new ParameterBlockJAI("filestore");
       savePB.setParameter("filename", nomFichBase+".jpg");
       savePB.setParameter("format", "jpeg");
       savePB.setSource(outImage, 0);
       JAI.create("filestore", savePB);
       
       loadImage1.dispose();
       loadImage2.dispose();
       loadImage3.dispose();
       loadImage4.dispose();

       // Ahora borro los ficheros utilizados:
       File borrame;
       try {
         borrame = new File(uno);
	 borrame.delete();
         borrame = new File(dos);
	 borrame.delete();
         borrame = new File(tres);
	 borrame.delete();
         borrame = new File(cuatro);
	 borrame.delete();
	 /*
         if (!borrame.delete())
	    System.err.println("Error al intentar borarr el fichero: "+uno);
         borrame = new File(dos);
         if (!borrame.delete())
	    System.err.println("Error al intentar borarr el fichero: "+dos);
         borrame = new File(tres);
         if (!borrame.delete())
	    System.err.println("Error al intentar borarr el fichero: "+tres);
         */
       } catch (SecurityException se) {
	 System.err.println("Imposible borrar el fichero "+nomFichBase+"*");
       }
   }
}
