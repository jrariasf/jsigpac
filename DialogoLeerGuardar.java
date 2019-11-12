import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;

class DialogoLeerGuardar extends JDialog
{     
  JSigpac misigpac;
  public static File ultimoDirectorio = null;
  String nombre_fichero = null;
  JTextArea jta;
  public static final long serialVersionUID = 0;
  
  public DialogoLeerGuardar(JSigpac fr, boolean leer)
  {
    super(fr, true);
    //System.out.println("CONSTRUCTOR de DialogoLeerGuardar");
    nombre_fichero = null;
    misigpac = fr;
    if (leer == true)
       setTitle("Leer datos de fichero:");
    else
       setTitle("Guardar datos de fichero:");
       
    //setSize(300,150);
    //getContentPane().add(new JLabel("Prueba"), BorderLayout.NORTH);
    setLocationRelativeTo(fr.servidorCombo);  
    //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    //addWindowListener(new MiAdaptador(this));
            
    JPanel p_entrada = new JPanel(new BorderLayout()); 
    JTextArea l_titulo = new JTextArea(2,1);
    if (leer == true)
       l_titulo.setText("Permite leer de un fichero \".jsi\" los datos\nnecesarios para completar el formulario");
    else
       l_titulo.setText("Permite guardar en un fichero con\nextension \".jsi\" los datos de la descarga");
    Border miborde = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    l_titulo.setBorder(miborde);
    
    JPanel centro = new JPanel(); 
    JPanel p_fichero = new JPanel(new FlowLayout());     
    JPanel p_salida = new JPanel(new FlowLayout());
    
    JLabel l_fichero = new JLabel("Fichero: ");
    JButton b_fichero = new JButton("seleccionar...");
    if (leer)
       b_fichero.setToolTipText("Pulse para seleccionar el fichero a cargar en el formulario");
    else
       b_fichero.setToolTipText("Pulse para seleccionar el fichero donde grabar el formulario");
    b_fichero.setActionCommand("seleccionar");
    b_fichero.addActionListener(new ListenerDialogoLeerGuardar(this));    
    
    JButton aceptar;
    if (leer)
    {
       aceptar = new JButton("Leer");
       //aceptar.setActionCommand("leer");
    } else
    {
       aceptar = new JButton("Guardar");
       //aceptar.setActionCommand("guardar");
    }
    aceptar.addActionListener(new ListenerDialogoLeerGuardar(this));
    JButton cancelar = new JButton("Cancelar");
    cancelar.addActionListener(new ListenerDialogoLeerGuardar(this));
    
    p_fichero.add(l_fichero);
    p_fichero.add(b_fichero);
    p_entrada.add(l_titulo, BorderLayout.NORTH); 
    p_entrada.add(p_fichero, BorderLayout.SOUTH);       
    
    jta = new JTextArea();
    jta.setForeground(Color.white);
    jta.setBackground(Color.black);
    jta.setEditable(false);
    centro.add(jta);
    //p_salida.setAlignmentX(LEFT_ALIGNMENT);
    p_salida.add(aceptar);
    p_salida.add(cancelar);
    
    getContentPane().add(p_entrada, BorderLayout.NORTH);
    getContentPane().add(centro, BorderLayout.CENTER);
    getContentPane().add(p_salida, BorderLayout.SOUTH);
    
    pack();
    Dimension dim = getSize();
    setSize(dim.width+75, dim.height);
    setVisible(true);
    
  }    
  
}



class ListenerDialogoLeerGuardar implements ActionListener
{
  DialogoLeerGuardar miDialogo;
    
  ListenerDialogoLeerGuardar(DialogoLeerGuardar di)
  {
     miDialogo = di;
  }
  
  boolean EscogerFichero (String[] extensiones)
  {    
    //System.out.println("EscogerFichero");
    
    if (DialogoImportar.ultimoDirectorio == null)
    	DialogoImportar.ultimoDirectorio = JSigpac.miMapa.opciones.directorio;
        //DialogoImportar.ultimoDirectorio = miDialogo.misigpac.miMapa.opciones.directorio;
    //else
    //    System.err.println("LeerGuardar::ultimoDirectorio="+miDialogo.ultimoDirectorio);
           
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
       miDialogo.nombre_fichero = DialogoImportar.ultimoDirectorio.getAbsolutePath(); 
       String aux;
       //System.out.println("Longitud = " + miDialogo.nombre_fichero.length());
       //System.out.println("0-15 = " + miDialogo.nombre_fichero.substring(0,15));
       //System.out.println("Separador "+File.separator+" en: " + miDialogo.nombre_fichero.lastIndexOf(File.separatorChar));
       if (miDialogo.nombre_fichero.length() > 30)       
       	  aux = miDialogo.nombre_fichero.substring(0,22) + "..." + 
       	        miDialogo.nombre_fichero.substring(miDialogo.nombre_fichero.lastIndexOf(File.separatorChar));
       else
          aux = miDialogo.nombre_fichero;	
       miDialogo.jta.setText(aux);           
       return true;
    } 
    else
       return false;
  }
   
  
  public void actionPerformed( ActionEvent evt )
  {     
     //System.out.println("actionPerformed="+evt.getActionCommand()+"  paramString="+evt.paramString());
     if (evt.getActionCommand().equals("Cancelar"))
     {
     	//System.out.println("Se ha pulsado CANCELAR"); 
     	//System.out.println("Pulsada la tecla Cancelar");    	
     	miDialogo.setVisible(false);
     } else if (evt.getActionCommand().equals("Leer"))	
     {
     	//System.out.println("Pulsada la tecla LEER: "+miDialogo.nombre_fichero);
     	if (miDialogo.misigpac.LeerDeFichero(miDialogo.nombre_fichero) == true)
     	   miDialogo.setVisible(false);
     	else
     	   miDialogo.misigpac.SacarVentanita("Error en lectura", "Se ha producido un error al intentar leer el fichero: " + miDialogo.nombre_fichero);
     } else if (evt.getActionCommand().equals("Guardar"))	
     {
     	//System.out.println("Pulsada la tecla GUARDAR: "+miDialogo.nombre_fichero);
     	
     	if (miDialogo.nombre_fichero == null)
     	   miDialogo.jta.setText("- No ha elegido ningún fichero -");
     	else
     	{
           // Compruebo si tiene extension ".jsi":
           int pos;     	   
     	   pos = miDialogo.nombre_fichero.lastIndexOf('.');
     	   if (pos != -1)
     	   {
     	      //Comprobamos que realmente tenga extension ".jsi":
     	      if (!miDialogo.nombre_fichero.substring(pos).equals(".jsi"))
     	         miDialogo.nombre_fichero += ".jsi";
     	   } else
     	      miDialogo.nombre_fichero += ".jsi";
     	      
     	   //System.out.println("FICHERO: " + miDialogo.nombre_fichero);
     	   miDialogo.misigpac.GuardarEnFichero(miDialogo.nombre_fichero);
     	   miDialogo.setVisible(false);     	
     	}    
     } else if (evt.getActionCommand().equals("seleccionar"))
     {
     	String[] extensiones = {"jsi"};     	
     	EscogerFichero(extensiones);
     }
   }
}
