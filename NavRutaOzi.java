import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class NavRutaOzi extends JPanel
{ 
 JRadioButton compe, ozi, gpx;
 JRadioButton track, ruta, waypoints;
 JButton generarRutaOzi; 
 MiArea miarea; 
 static String _compe = "Compe";
 static String _ozi = "Ozi";
 static String _GPX = "GPX";
 static String _ruta = "Ruta";
 static String _track = "Track";
 static String _waypoints = "Waypoints";
 public static final long serialVersionUID = 0;

 public NavRutaOzi()
 {
   setLayout(new BorderLayout());
   JButton generarRutaOzi;
   //_ff_ MiJTextArea info = new MiJTextArea();  
   TextInfoNRO info = new TextInfoNRO();
   
   generarRutaOzi = new JButton("Generar ruta para OziExplorerCE");
   //generarRutaOzi.setBorder(new EmptyBorder(new Insets(10,10,10,10)));
   generarRutaOzi.setActionCommand("genRutaOzi");
   generarRutaOzi.addActionListener(new UtilidadesActionListener(this));  
   
   compe = new JRadioButton(_compe);
   compe.setActionCommand(_compe);
   ozi = new JRadioButton(_ozi);
   ozi.setActionCommand(_ozi);
   gpx = new JRadioButton(_GPX);
   gpx.setActionCommand(_GPX);
   ButtonGroup groupProg = new ButtonGroup();
   groupProg.add(compe);
   groupProg.add(ozi);
   groupProg.add(gpx);
   ozi.setSelected(true);
   
   ruta = new JRadioButton(_ruta);
   ruta.setActionCommand(_ruta);
   track = new JRadioButton(_track);
   track.setActionCommand(_track);
   waypoints = new JRadioButton(_waypoints);
   waypoints.setActionCommand(_waypoints);
   ButtonGroup groupTipo = new ButtonGroup();
   groupTipo.add(ruta);
   groupTipo.add(track);
   groupTipo.add(waypoints);
   track.setSelected(true);
   
   JPanel norte = new JPanel(new BorderLayout());      
   //JPanel norte_west = new JPanel(new BorderLayout());
   
   JPanel selectores = new JPanel(new BorderLayout());
   JPanel selector_west = new JPanel(new FlowLayout());
   JPanel panelProg = new JPanel(new GridLayout(3,1));
     panelProg.add(compe);
     panelProg.add(ozi);
     panelProg.add(gpx);
   JPanel panelTipo = new JPanel(new GridLayout(3,1));
     panelTipo.add(ruta);
     panelTipo.add(track);
     panelTipo.add(waypoints);
   JPanel panelBoton = new JPanel(new BorderLayout());
     panelBoton.add(generarRutaOzi, BorderLayout.WEST);   
   selector_west.add(panelProg); 
   selector_west.add(panelTipo); 
   selector_west.add(panelBoton); 
   selectores.add(selector_west, BorderLayout.WEST);
    
   //norte_west.add(generarRutaOzi, BorderLayout.WEST);
   norte.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
   //norte_west.setBorder(new EmptyBorder(new Insets(10,0,10,10)));
   norte.add(info, BorderLayout.CENTER);
   norte.add(selectores, BorderLayout.SOUTH);
   
   JPanel sur = new JPanel(new BorderLayout());
   JPanel sur_west = new JPanel(new BorderLayout());
   sur.setBorder(new EmptyBorder(new Insets(5,5,5,5)));  
   sur_west.setBorder(new EmptyBorder(new Insets(5,0,5,5)));    
   miarea = new MiArea("");
   JButton limpiar = new JButton("Limpiar");
   limpiar.addActionListener(new UtilidadesActionListener(this));
   sur_west.add(limpiar, BorderLayout.WEST);   
   sur.add(miarea, BorderLayout.CENTER);
   sur.add(sur_west, BorderLayout.SOUTH);
  
   add(norte, BorderLayout.NORTH);
   //add(centro, BorderLayout.CENTER);
   add(sur, BorderLayout.CENTER);
 } 
}


class TextInfoNRO extends TextInfo
{  
  public static final long serialVersionUID = 0;
  
  TextInfoNRO ()
  {    
    super();   
  }
  
  void rellenar()
  {
    append("Informacion:");
    //append("\n============");
    append("\nUtilidad que genera un fichero de ruta \".rt2\" valido para OziExplorer CE (PDA)\n");
    append("\nDespues de especificar el tipo de fichero (Ozi-compe-GPX y Ruta-Track-Waypoints), pulse el botón para");
    append("\nseleccionar el fichero a partir del cual generar la ruta. El fichero generado con la ruta podra ser");
    append("\nutilizado por la aplicacion OziExplorerCE y de esa forma poder utilizar la opcion navegar sobre una ruta.");
    append("\n*Nota: Intentar reducir el numero de puntos del fichero original a los estrictamente necesarios");
    
  }
}

// Clase para recoger los eventos de las pulsaciones:
class UtilidadesActionListener implements ActionListener
{
  NavRutaOzi miNavRutaOzi;

  UtilidadesActionListener(NavRutaOzi miNRO)
  {
    miNavRutaOzi = miNRO;
    //System.out.println("");
  }

  public void actionPerformed( ActionEvent evt )
  {
    if (evt.getActionCommand().equals("genRutaOzi"))
    {
       //@SuppressWarnings("unused")       
       //ConversorTrackCompe CTC = new ConversorTrackCompe(miNavRutaOzi.miarea, miNavRutaOzi, true);
       
       if (miNavRutaOzi.compe.isSelected())
       {        	  
       	  if (miNavRutaOzi.track.isSelected()) {       	  
       	     @SuppressWarnings("unused")      	         	    
       	     ConversorTrackCompe CTC = new ConversorTrackCompe(miNavRutaOzi.miarea, miNavRutaOzi, true);
       	  } else if (miNavRutaOzi.ruta.isSelected()) {
       	     ConversorRutaCompe CRC = new ConversorRutaCompe(miNavRutaOzi.miarea, miNavRutaOzi, true);   	  	
       	  } else if (miNavRutaOzi.waypoints.isSelected()) {
       	     ConversorWayPointsCompe CWC = new ConversorWayPointsCompe(miNavRutaOzi.miarea, miNavRutaOzi, true);    	  	       	         	
       	  }
       }   else if (miNavRutaOzi.ozi.isSelected())
       {
       	  if (miNavRutaOzi.track.isSelected()) {
       	     ConversorTrackOzi CTO = new ConversorTrackOzi(miNavRutaOzi.miarea, miNavRutaOzi, true);
       	  } else if (miNavRutaOzi.ruta.isSelected()) {
       	     ConversorRutaOzi CRO = new ConversorRutaOzi(miNavRutaOzi.miarea, miNavRutaOzi, true);
       	  } else if (miNavRutaOzi.waypoints.isSelected()) {
       	     ConversorWayPointsOzi CWO = new ConversorWayPointsOzi(miNavRutaOzi.miarea, miNavRutaOzi, true);       	 
       	  }
       } else if (miNavRutaOzi.gpx.isSelected())
       {
       	  char tipo = 't';
       	  if (miNavRutaOzi.track.isSelected()) {
       	     tipo = 't';
       	  } else if (miNavRutaOzi.ruta.isSelected()) {
       	     tipo = 'r';
       	  } else if (miNavRutaOzi.waypoints.isSelected()) {
       	     tipo = 'w';       	 
       	  }
       	  
       	  ConversorGPX CGPX = new ConversorGPX(miNavRutaOzi.miarea, miNavRutaOzi, tipo);
       }
       
       //System.out.println("UtilidadesActionListener::actionPerformed  genRutaOzi");     
    } else if (evt.getActionCommand().equals("Limpiar"))
    {
       miNavRutaOzi.miarea.Limpiar();
    }
  }
}
