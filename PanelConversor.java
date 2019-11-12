//import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class PanelConversor extends JPanel
{
 boolean _wgs2ed;
 JRadioButton wgs2ed, ed2wgs;
 JButton convTrackCompe, convWayPointsCompe, convRutaCompe, convMapaCompe;
 JButton convTrackOzi, convWayPointsOzi, convRutaOzi, convMapaOzi;
 MiArea miarea;
 JButton limpiar;
 public static final long serialVersionUID = 0;

public PanelConversor()
{
 JPanel ParaCompe, ParaOzi;
 setBorder(new EmptyBorder(new Insets(5,5,5,5))); 
 // Creo el entorno grafico del conversor:
 JTabbedPane tabConversor = new JTabbedPane();
 ParaCompe = PanelCompe();
 ParaOzi = PanelOzi();
 tabConversor.addTab("Compe GPS", null, ParaCompe, "Conversor de datum para ficheros de CompeGPS");
 tabConversor.addTab("Ozi Explorer", null, ParaOzi, "Conversor de datum para ficheros de Ozi Explorer");

 setLayout(new BorderLayout());
 miarea = new MiArea("-- Para transformar el datum (ED50 <--> WGS84) de diferentes tipos de ficheros --");
 JPanel centro = new JPanel(new BorderLayout());
 centro.setBorder(new EmptyBorder(new Insets(5,0,5,0)));
 centro.add(miarea, BorderLayout.CENTER);
 JPanel panelSUR = new JPanel(new BorderLayout());
 limpiar = new JButton("Limpiar");
 limpiar.addActionListener(new ConversorActionListener(this));
 panelSUR.add(limpiar, BorderLayout.EAST);

 add(tabConversor, BorderLayout.NORTH);
 add(centro, BorderLayout.CENTER);
 add(panelSUR, BorderLayout.SOUTH);
}


JPanel PanelCompe()
{
  JPanel compe = new JPanel(new BorderLayout());
  convTrackCompe = new JButton("Convertir Track");
  convTrackCompe.setForeground(Color.GREEN.darker());
   convTrackCompe.setActionCommand("convTrackCompe");
   convTrackCompe.addActionListener(new ConversorActionListener(this));
  convWayPointsCompe = new JButton("Convertir WayPoints");
   convWayPointsCompe.setForeground(Color.GREEN.darker());
   convWayPointsCompe.setActionCommand("convWayPointsCompe");
   convWayPointsCompe.addActionListener(new ConversorActionListener(this));
  convRutaCompe = new JButton("Convertir Ruta");
   convRutaCompe.setForeground(Color.GREEN.darker());
   convRutaCompe.setActionCommand("convRutaCompe");
   convRutaCompe.addActionListener(new ConversorActionListener(this));
  convMapaCompe = new JButton("Conv Fich Calibracion");
   convMapaCompe.setForeground(Color.GREEN.darker());
   convMapaCompe.setActionCommand("convMapaCompe");
   convMapaCompe.addActionListener(new ConversorActionListener(this));

  JPanel norte = new JPanel();
  norte.add(convTrackCompe);
  norte.add(convWayPointsCompe);
  norte.add(convRutaCompe);
  norte.add(convMapaCompe);

  compe.add(norte, BorderLayout.NORTH);

  return  compe;
}


JPanel PanelOzi()
{
  JPanel ozi = new JPanel(new BorderLayout());
  convTrackOzi = new JButton("Convertir Track");
   convTrackOzi.setForeground(Color.BLUE.brighter());
   convTrackOzi.setActionCommand("convTrackOzi");
   convTrackOzi.addActionListener(new ConversorActionListener(this));
  convWayPointsOzi = new JButton("Convertir WayPoints");
   convWayPointsOzi.setForeground(Color.BLUE.brighter());
   convWayPointsOzi.setActionCommand("convWayPointsOzi");
   convWayPointsOzi.addActionListener(new ConversorActionListener(this));
  convRutaOzi = new JButton("Convertir Ruta");
   convRutaOzi.setForeground(Color.BLUE.brighter());
   convRutaOzi.setActionCommand("convRutaOzi");
   convRutaOzi.addActionListener(new ConversorActionListener(this));
  convMapaOzi = new JButton("Conv Fich Calibracion");
   convMapaOzi.setForeground(Color.BLUE.brighter());
   convMapaOzi.setActionCommand("convMapaOzi");
   convMapaOzi.addActionListener(new ConversorActionListener(this));

  JPanel norte = new JPanel();
  norte.add(convTrackOzi);
  norte.add(convWayPointsOzi);
  norte.add(convRutaOzi);
  norte.add(convMapaOzi);

  ozi.add(norte, BorderLayout.NORTH);

  return  ozi;
}


}



// Clase para recoger los eventos de las pulsaciones:
class ConversorActionListener implements ActionListener
{
  PanelConversor miconversor;

  ConversorActionListener(PanelConversor con)
  {
    miconversor = con;
  }

  public void actionPerformed( ActionEvent evt )
  {
    if (evt.getActionCommand().equals("convTrackCompe"))
    {
       @SuppressWarnings("unused")
	ConversorTrackCompe CTC = new ConversorTrackCompe(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("convWayPointsCompe"))
    {
       @SuppressWarnings("unused")
	ConversorWayPointsCompe CWC = new ConversorWayPointsCompe(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("convRutaCompe"))
    {
       @SuppressWarnings("unused")
	ConversorRutaCompe CRC = new ConversorRutaCompe(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("convMapaCompe"))
    {
       @SuppressWarnings("unused")
	ConversorMapaCompe CMC = new ConversorMapaCompe(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("convTrackOzi"))
    {
       @SuppressWarnings("unused")
	ConversorTrackOzi CTO = new ConversorTrackOzi(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("convWayPointsOzi"))
    {
       @SuppressWarnings("unused")
	ConversorWayPointsOzi CWO = new ConversorWayPointsOzi(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("convRutaOzi"))
    {
       @SuppressWarnings("unused")
	ConversorRutaOzi CRO = new ConversorRutaOzi(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("convMapaOzi"))
    {
       @SuppressWarnings("unused")
	ConversorMapaOzi CMO = new ConversorMapaOzi(miconversor.miarea, miconversor);
    } else if (evt.getActionCommand().equals("Limpiar"))
    {
       miconversor.miarea.Limpiar();
    }
  }
}
