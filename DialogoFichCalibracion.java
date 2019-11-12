import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;
import java.util.*;

class DialogoFichCalibracion extends JDialog
{     
  JSigpac misigpac;
  JCheckBox ozi, compe, gpstuner, pathaway, jgw, globalmapper, ers, tracky, mymotion, tomtom;
  
  public static final long serialVersionUID = 0;
  
  public DialogoFichCalibracion(JSigpac fr)
  {
    super(fr, true);
    //System.out.println("CONSTRUCTOR de DialogoFichCalibracion");  
    misigpac = fr;
    setTitle("Ficheros de calibración:");           
    setLocationRelativeTo(fr.fcalibra);  

    
    // Primero leemos los datos del ficehro "jsigpac.calibrar":    
    boolean calibrarOzi=false, calibrarCompe=false, calibrarGPSTuner=false,
            calibrarPathAway=false, calibrarJGW=false, calibrarGlobalMapper=false,
            calibrarERS=false, calibrarTracky=false, calibrarMyMotion=false,
            calibrarTomTom=false;
    
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
	 System.err.println("DialogoFichCalibracion: No se ha encontrado el fichero: " + dat.getPath());
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
       JSigpac.ImprimirLinea("Debe crearlo manualmente. No importa que se cree vacio");       
    }
    
    
    // Ahora cargamos los valores leidos y los presentamos por pantalla:
    JPanel panel = new JPanel(new GridLayout(10,1));
    
    JButton aceptar, cancelar;
    ozi = new JCheckBox("Ozi Explorer", calibrarOzi);
    compe = new JCheckBox("Compe GPS", calibrarCompe);
    gpstuner = new JCheckBox("GPS Tuner", calibrarGPSTuner);
    pathaway = new JCheckBox("PathAway", calibrarPathAway);
    jgw = new JCheckBox("JGW", calibrarJGW);
    globalmapper = new JCheckBox("Global Mapper", calibrarGlobalMapper);
    ers = new JCheckBox("ERS", calibrarERS);
    tracky = new JCheckBox("Tracky", calibrarTracky);
    mymotion = new JCheckBox("MyMotion", calibrarMyMotion);
    tomtom = new JCheckBox("TomTom", calibrarTomTom);
    
    JPanel botonera = new JPanel(new FlowLayout());
    aceptar = new JButton("Guardar");
    aceptar.setToolTipText("Guardar cambios");
    aceptar.addActionListener(new ListenerDialogoFichCalibracion(this));
    cancelar = new JButton("Cancelar");
    cancelar.setToolTipText("Rechazar cambios");
    cancelar.addActionListener(new ListenerDialogoFichCalibracion(this));
    botonera.add(aceptar);
    botonera.add(cancelar);
    
    panel.add(ozi);
    panel.add(compe);
    panel.add(gpstuner);
    panel.add(pathaway);
    panel.add(jgw);
    panel.add(globalmapper);
    panel.add(ers);
    panel.add(tracky); 
    panel.add(mymotion);
    panel.add(tomtom);
    panel.add(botonera);   
    
    getContentPane().add(panel);                        
    
    pack();
    Dimension dim = getSize();
    setSize(dim.width+75, dim.height);
    setVisible(true);
    
  }    
  
}



class ListenerDialogoFichCalibracion implements ActionListener
{
  DialogoFichCalibracion miDialogo;
    
  ListenerDialogoFichCalibracion(DialogoFichCalibracion di)
  {
     miDialogo = di;
  }
  
  void GuardarEnfichero()
  {    
     File dat = new File("dat" + File.separator + "jsigpac.calibrar");
     FileOutputStream fout=null;
     Properties misProperties = new Properties();
   
     misProperties.setProperty("Ozi", (miDialogo.ozi.isSelected() ? "si" : "no"));
     misProperties.setProperty("Compe", (miDialogo.compe.isSelected() ? "si" : "no"));
     misProperties.setProperty("GPSTuner", (miDialogo.gpstuner.isSelected() ? "si" : "no"));
     misProperties.setProperty("PathAway", (miDialogo.pathaway.isSelected() ? "si" : "no"));
     misProperties.setProperty("JGW", (miDialogo.jgw.isSelected() ? "si" : "no"));
     misProperties.setProperty("GlobalMapper", (miDialogo.globalmapper.isSelected() ? "si" : "no"));
     misProperties.setProperty("ERS", (miDialogo.ers.isSelected() ? "si" : "no"));
     misProperties.setProperty("Tracky", (miDialogo.tracky.isSelected() ? "si" : "no"));
     misProperties.setProperty("MyMotion", (miDialogo.mymotion.isSelected() ? "si" : "no"));
     misProperties.setProperty("TomTom", (miDialogo.tomtom.isSelected() ? "si" : "no"));
     
     try
     {
        fout = new FileOutputStream(dat);
        misProperties.store(fout, "Formatos de calibración:");
        
     } catch (FileNotFoundException fnfe) {
        miDialogo.misigpac.SacarVentanita("Error al salvar en fichero", 
               "No se ha podido guardar la informacion en el fichero: " + dat.getAbsolutePath());
     } catch (SecurityException se) {
        miDialogo.misigpac.SacarVentanita("Error al salvar en fichero",
               "No hay permisos para escribir en el fichero: " + dat.getAbsolutePath());
     } catch (NullPointerException npe) {
     	miDialogo.misigpac.SacarVentanita("Error al salvar en fichero",
               "Valor nulo: " + npe);
     } catch (ClassCastException cce) {
     	miDialogo.misigpac.SacarVentanita("Error al salvar en fichero",
               "Error en los datos a guardar: " + cce);
     } catch (IOException ioe) {
     	miDialogo.misigpac.SacarVentanita("Error al salvar en fichero",
               "IOException: " + ioe);
     }	finally {
      	 try {
           if (fout != null)
           {
      	      fout.close();      
      	      fout = null;
      	   }
         } catch (IOException ioe) {}      	
      } 
  }
   
  
  public void actionPerformed( ActionEvent evt )
  {     
     //System.out.println("actionPerformed="+evt.getActionCommand()+"  paramString="+evt.paramString());
     if (evt.getActionCommand().equals("Cancelar"))
     {     	
     	//System.out.println("Pulsada la tecla Cancelar");    	
     	miDialogo.setVisible(false);
     } else if (evt.getActionCommand().equals("Guardar"))	
     {
     	//System.out.println("Pulsada la tecla GUARDAR: ");    
     	GuardarEnfichero();
     	miDialogo.setVisible(false);
     }	 
  }
}
