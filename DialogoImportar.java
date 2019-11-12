import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;

class DialogoImportar extends JDialog
{
  //String[] respuesta;  
  JTextField t_margen;
  JCheckBox onlyTrack; //_hh_
  JButton b_fichero;
  JSigpac misigpac;
  public static File ultimoDirectorio = null;
  public static final long serialVersionUID = 0;
  
  
  public DialogoImportar(JSigpac fr, boolean mod) //, String[] res)
  {
    super(fr, "Importar datos:", mod);
    misigpac = fr;
    //respuesta = res;
    //setSize(300,200);
    //getContentPane().add(new JLabel("Prueba"), BorderLayout.NORTH);
    setLocationRelativeTo(fr.topo_orto);  
    //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    //addWindowListener(new MiAdaptador(this));
    
    
    JPanel p_entrada = new JPanel(new GridLayout(4,1));   
    JTextArea l_titulo = new JTextArea(2,1);
    l_titulo.setText("Permite calcular los datos de un mapa que\ncontenga el track, la ruta, los waypoints, o importar los datos de un fichero de calibración de Ozi o Compe"); 
    //l_titulo.setMargin(new Insets(5,5,5,5));
    Border miborde = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    //sinborde = BorderFactory.createEmptyBorder();
    l_titulo.setBorder(miborde);
    JPanel p_fichero = new JPanel(new BorderLayout());
     JPanel p_fichero_west = new JPanel(new FlowLayout());
    JPanel p_margen = new JPanel(new BorderLayout());
     JPanel p_margen_west = new JPanel(new FlowLayout());
    JPanel p_onlyTrack = new JPanel(new BorderLayout());
     JPanel p_onlyTrack_west = new JPanel(new FlowLayout());
    JPanel p_salida = new JPanel(new FlowLayout());
    
    JLabel l_fichero = new JLabel("Fichero: ");
    b_fichero = new JButton("seleccionar...");
    b_fichero.setActionCommand("seleccionar");
    b_fichero.addActionListener(new ListenerDialogoImportar(this));
    
    JLabel l_margen = new JLabel("Margen: ");
    l_margen.setToolTipText("Para ampliar los márgenes del mapa a descargar");
    t_margen = new JTextField("200", 6);
    JLabel l_metros = new JLabel("(metros)");
    l_metros.setForeground(Color.gray);
    
    onlyTrack = new JCheckBox("Solo descargar el track", misigpac.descargarSoloTrack);
    onlyTrack.setToolTipText("Activar para descargar solamente los cuadrantes por los que va el track o ruta");
    
    
    JButton aceptar = new JButton("Aceptar");
    aceptar.addActionListener(new ListenerDialogoImportar(this));
    JButton cancelar = new JButton("Cancelar");
    cancelar.addActionListener(new ListenerDialogoImportar(this));
    
    p_fichero_west.add(l_fichero);
    p_fichero_west.add(b_fichero);    
    p_fichero.add(p_fichero_west, BorderLayout.WEST);
    
    p_margen_west.add(l_margen);
    p_margen_west.add(t_margen);
    p_margen_west.add(l_metros);
    p_margen.add(p_margen_west, BorderLayout.WEST);
    
    p_onlyTrack_west.add(onlyTrack);
    p_onlyTrack.add(p_onlyTrack_west, BorderLayout.WEST);    
    
    p_entrada.add(l_titulo);
    p_entrada.add(p_fichero);
    p_entrada.add(p_margen);
    p_entrada.add(p_onlyTrack);
    
    //p_salida.setAlignmentX(LEFT_ALIGNMENT);
    p_salida.add(aceptar);
    p_salida.add(cancelar);
    
    getContentPane().add(p_entrada, BorderLayout.NORTH);
    getContentPane().add(p_salida, BorderLayout.SOUTH);
    
    pack();
    setVisible(true);
    
  }
  
  
  
}


class ListenerDialogoImportar implements ActionListener
{
  DialogoImportar miDialogo;
    
  ListenerDialogoImportar(DialogoImportar di)
  {
     miDialogo = di;
  }
  
  boolean EscogerFichero (String[] extensiones)
  {
    //File fichero;
    String nombre_fichero = null;
    //System.out.println("EscogerFichero");
 
    if (DialogoImportar.ultimoDirectorio == null)
    	DialogoImportar.ultimoDirectorio = JSigpac.miMapa.opciones.directorio;
    	//DialogoImportar.ultimoDirectorio = miDialogo.misigpac.miMapa.opciones.directorio;    
 
    JFileChooser fc = new JFileChooser(DialogoImportar.ultimoDirectorio);
    
    if (extensiones != null)
    {
       fc.addChoosableFileFilter(new MiFiltro(extensiones));
       fc.setAcceptAllFileFilterUsed(false);
    }    

    int res = fc.showOpenDialog(miDialogo);
    if (res == JFileChooser.APPROVE_OPTION)
    {
    	DialogoImportar.ultimoDirectorio = fc.getSelectedFile();
       //System.out.println("Fichero.toString:"+ultimoDirectorio.toString());
       //System.out.println("Fichero.getAbsolutePath:"+fichero.getAbsolutePath());
       nombre_fichero = DialogoImportar.ultimoDirectorio.getAbsolutePath();
       //miDialogo.misigpac.ficheroTrkRte = nombre_fichero;
       //JSigpac.Traza("ficheroTrkRte="+miDialogo.misigpac.ficheroTrkRte);
       CalcularCoordenadas(nombre_fichero);
       return true;
    } 
    else
       return false;
  }
  
  public void CalcularCoordenadas(String nomFich)
  {
     Datum datumSalida;
     JSigpac.ficheroTrkRte = new Fichero(nomFich);
     
     if (((String)miDialogo.misigpac.comboDatum.getSelectedItem()).equals(miDialogo.misigpac.opcionesDatum[0]))
        datumSalida = Datum.datumED50;
     else
        datumSalida = Datum.datumWGS84;
     
     miDialogo.misigpac.datum_respCoor = datumSalida;     
     miDialogo.misigpac.respCoor = JSigpac.ficheroTrkRte.CalcularCoordenadas(datumSalida);
     
     if (miDialogo.misigpac.respCoor == null)
        miDialogo.misigpac.SacarVentanita("Error al intentar importar datos", Fichero.mensaje_error);
  }
  
  public void actionPerformed( ActionEvent evt )
  {    
     //System.out.println("actionPerformed="+evt.getActionCommand()+"  paramString="+evt.paramString());
     if (evt.getActionCommand().equals("Aceptar"))
     {
     	double[] res = new double[7];
     	int margen;
     	try {
          margen = (Integer.valueOf(miDialogo.t_margen.getText().trim())).intValue();
        } catch (NullPointerException npe) {
          margen = 400; // Por defecto ponemos un margen de 400 metros
        } catch (NumberFormatException nfe) {
          margen = 400; // Por defecto ponemos un margen de 400 metros
        }
     	if (miDialogo.misigpac.respCoor != null &&
     	   miDialogo.misigpac.respCoor.length == 6)
     	{
           for (int i=0; i<6; i++)
               res[i] = miDialogo.misigpac.respCoor[i];
           res[6] = margen;
           miDialogo.misigpac.respCoor = res;
           miDialogo.misigpac.descargarSoloTrack = miDialogo.onlyTrack.isSelected();
        } else
        {
           miDialogo.misigpac.SacarVentanita("Datos no leidos", "No ha seleccionado ningun fichero");
           miDialogo.misigpac.respCoor = null;
           miDialogo.misigpac.descargarSoloTrack = false;
        }
     	miDialogo.setVisible(false);	 
     } else if (evt.getActionCommand().equals("Cancelar"))
     {
     	//System.out.println("Se ha pulsado CANCELAR");
     	miDialogo.misigpac.respCoor = null;     	
     	miDialogo.misigpac.descargarSoloTrack = miDialogo.onlyTrack.isSelected();
     	miDialogo.setVisible(false);
     } else if (evt.getActionCommand().equals("seleccionar"))
     {
     	boolean ficheroElegido = false;
     	String[] extensiones = Fichero.misExtensiones; //{"trk", "TRK", "plt", "PLT", "rte", "RTE", "wpt", "WPT", "gpx", "GPX"};
     	miDialogo.misigpac.respCoor = null;
     	ficheroElegido = EscogerFichero(extensiones);
     	miDialogo.b_fichero.setEnabled(!ficheroElegido);
     }
   }
}
