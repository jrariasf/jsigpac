import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;



public class IndiceDureza extends JPanel
{ 
 //JButton numIBP, informeIBP;
 JRadioButton BTT, carretera;
 JTextField valorIBP;
 String fichero_entrada = null;
 public static File ultimoDirectorio = null;
 
 MiArea miarea;  
 public static final long serialVersionUID = 0;

 public IndiceDureza()
 {
   setLayout(new BorderLayout());
   JButton numIBP = new JButton("IBP");
   numIBP.setActionCommand("numIBP");
   numIBP.addActionListener(new IBPActionListener(this)); 
   JButton informeIBP = new JButton("Informe IBP");
   informeIBP.setActionCommand("informeIBP");
   informeIBP.addActionListener(new IBPActionListener(this));
   
   TextInfoIBP info = new TextInfoIBP();  
   valorIBP = new JTextField("", 5);
   Font mifuente = new Font("Dialog", Font.PLAIN, 12);
   valorIBP.setFont(mifuente);
   
   BTT = new JRadioButton("BTT");
   carretera = new JRadioButton("Carretera");
   ButtonGroup groupProg = new ButtonGroup();
   groupProg.add(BTT);
   groupProg.add(carretera);
   BTT.setSelected(true);
   
   JPanel norte = new JPanel(new BorderLayout());
   norte.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
   JPanel panelIBP = new JPanel(new BorderLayout());
   JPanel panelIBP_norte = new JPanel();
   panelIBP_norte.add(BTT);
   panelIBP_norte.add(carretera);
   JPanel panelIBP_sur = new JPanel();
   panelIBP_sur.add(numIBP);
   panelIBP_sur.add(valorIBP);
   panelIBP_sur.add(informeIBP);
   
   panelIBP.add(panelIBP_norte, BorderLayout.NORTH); 
   panelIBP.add(panelIBP_sur, BorderLayout.SOUTH);
   
   norte.add(info, BorderLayout.NORTH);   
   norte.add(panelIBP, BorderLayout.SOUTH);  
   
   JPanel sur = new JPanel(new BorderLayout());
   JPanel sur_west = new JPanel(new BorderLayout());
   sur.setBorder(new EmptyBorder(new Insets(5,5,5,5)));  
   sur_west.setBorder(new EmptyBorder(new Insets(5,0,5,5)));    
   miarea = new MiArea("");
   JButton limpiar = new JButton("Limpiar");
   limpiar.addActionListener(new IBPActionListener(this));
   sur_west.add(limpiar, BorderLayout.WEST);   
   sur.add(miarea, BorderLayout.CENTER);
   sur.add(sur_west, BorderLayout.SOUTH);
  
   add(norte, BorderLayout.NORTH);
   //add(centro, BorderLayout.CENTER);
   add(sur, BorderLayout.CENTER);
         
 } 
 
 void Limpiar()
 {
    miarea.Limpiar();
    valorIBP.setText("");
 }
    
 void EscribirTexto(String texto)
 {
   if (miarea != null)
       miarea.EscribirTexto(texto); 	
   else
      System.out.println(texto);
 }
 
 boolean ObtenerIBP(String mod, boolean informe)
 {
   //Limpiar();
   valorIBP.setText("");
   if (ElegirTrack() == true)
   { 
      // Creamos un thread que haga la conexión porque si no se nos queda frito
      // y se actualiza correctamente el "miarea".
      JSigpac.Traza("Creamos el thread ConectarConwebIBP");
      ConectarConwebIBP conIBP = new ConectarConwebIBP(this, mod, informe);
      conIBP.start();  
      JSigpac.Traza("Hecho el start()");
      return true; // ConectarConwebIBP(mod, informe);	   	
   } else
      return false; 
 }

 

	
 boolean ElegirTrack()
 {
   String[] extensiones = {"plt", "PLT", "trk", "TRK", "gpx", "GPX"};   
   //System.out.println("ultimoDirectorio="+ (ultimoDirectorio == null ? "-NULL-" : ultimoDirectorio.toString()));
   JFileChooser fc = new JFileChooser(ultimoDirectorio);
   
   fc.addChoosableFileFilter(new MiFiltro(extensiones));
   fc.setAcceptAllFileFilterUsed(false);   

   int res = fc.showOpenDialog(this);
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
}


class TextInfoIBP extends TextInfo
{  
  public static final long serialVersionUID = 0;
  
  TextInfoIBP ()
  {    
    super();    
  }
  
  void rellenar()
  {
    append("Informacion:");
    //append("\n============");
    append("\nPermite obtener el indice IBP que valora el grado de dificultad de una ruta realizada en");
    append("\nbicicleta de montaña o de carretera. Seleccionar primero si el track es de BTT o de carretera.");
    append("\nMás información en: http://www.ibpindex.com");        
  }
}

// Clase para recoger los eventos de las pulsaciones:
class IBPActionListener implements ActionListener
{
  IndiceDureza miIBP;

  IBPActionListener(IndiceDureza miIndice)
  {
    miIBP = miIndice;
    //System.out.println("");
  }

  public void actionPerformed( ActionEvent evt )
  {
    String MOD = "BTT";
    
    if (miIBP.BTT.isSelected())
       MOD = "BTT";
    else
       MOD = "Carretera";
          
    if (evt.getActionCommand().equals("numIBP"))
    {
       //@SuppressWarnings("unused")              
       miIBP.ObtenerIBP(MOD, false);   
    } else if (evt.getActionCommand().equals("informeIBP"))   
    {    
       //@SuppressWarnings("unused")    	  
       miIBP.ObtenerIBP(MOD, true);            
    } else if (evt.getActionCommand().equals("Limpiar"))
    {
       miIBP.Limpiar();
    }
  }
}