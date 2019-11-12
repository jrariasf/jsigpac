import javax.swing.*;
import java.awt.*;

public class Rejilla extends JPanel
{
   private JSigpac misigpac;
   public static final long serialVersionUID = 0;
   
   public Rejilla(JSigpac js)
   {      
      misigpac = js;
      setPreferredSize(new Dimension(30,30));
   }
   
   public void paintComponent(Graphics g)
   {
   	super.paintComponent(g);
   	setBackground(Color.red);
   	//System.out.println("Estamos en paintcomponent");
   	setPreferredSize(new Dimension(30,30));
   	int mapasH, mapasV;
   	int anchoMapa, altoMapa;
   	
   	try 
   	{
   	   mapasH = Integer.parseInt(misigpac.t_mapasH.getText());
   	   mapasV = Integer.parseInt(misigpac.t_mapasV.getText());
   	   int alto = (int)getSize().getHeight();   	
           //System.out.println("alto="+alto);
           //setPreferredSize(new Dimension(ancho, ancho));
           setPreferredSize(new Dimension(alto,alto));   
           //setPreferredSize(new Dimension(30,30));      
   	   g.setColor(Color.BLACK);
   	   g.drawRect(0,0,alto-1,alto-1);
   	
   	   anchoMapa = (int)(alto / mapasH);
   	   altoMapa = (int)(alto / mapasV);
   	   int i;
   	   for (i=1; i<mapasH; i++)   	   		   		
   	      g.drawLine(i*anchoMapa, 0, i*anchoMapa, alto);    
   	   
   	   for (i=1; i<mapasV; i++)   	   		   		
   	      g.drawLine(0, i*altoMapa, alto, i*altoMapa);     
   	} catch (NumberFormatException nfe) {   }
   	  catch (ArithmeticException ae) {  }
   	      	
   	//System.out.println("getSize Ancho="+getSize().getWidth()+"   Alto="+getSize().getHeight());
   }
}