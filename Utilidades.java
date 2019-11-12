
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;


public class Utilidades extends JPanel
{
 boolean _wgs2ed;
 //JRadioButton wgs2ed, ed2wgs;
 //JButton convTrackCompe, convWayPointsCompe, convRutaCompe, convMapaCompe;
 //JButton convTrackOzi, convWayPointsOzi, convRutaOzi, convMapaOzi;
 //MiArea miarea;
 //JButton limpiar;
 public static final long serialVersionUID = 0;

 public Utilidades()
 {
   JPanel navOzi; // Panel para generar los ficheros de ruta que utiliza el Ozi

   // Creo el entorno grafico de las utilidades:
   JTabbedPane tabUtils = new JTabbedPane();
   tabUtils.addTab("Navegacion Ozi CE", null, new NavRutaOzi(), "Generar fichero de rutas para Ozi PPC/PDA");
   tabUtils.addTab("Conversor", null, (Component)(new PanelConversor()), "Convertir el datum de un fichero de track, ruta o waypoints de Compe o Ozi");
   tabUtils.addTab("IBP", null, (Component)(new IndiceDureza()), "Obtener el índice de dureza de una ruta");
   
 
   setLayout(new BorderLayout());
   add(tabUtils);
 }


}
