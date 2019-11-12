import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;

class DialogoEsquinaInfDcha extends JDialog
{  
  JSigpac misigpac;	
  JTextField t_x, t_y;
  JSpinner s_huso;
  public static File ultimoDirectorio = null;
  public static final long serialVersionUID = 0;
  
  public DialogoEsquinaInfDcha(JSigpac fr)
  {
    super(fr, "Esquina inferior derecha:", true);
    misigpac = fr;
    //setSize(300,200);
    //getContentPane().add(new JLabel("Prueba"), BorderLayout.NORTH);
    setLocationRelativeTo(fr.t_x);  
    
    //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    //addWindowListener(new MiAdaptador(this));
    
    
    JPanel p_entrada = new JPanel(new GridLayout(3,1));   
    JTextArea l_titulo = new JTextArea();
    l_titulo.setText("Coordenadas UTM de la esquina inferior derecha"); 
    //l_titulo.setMargin(new Insets(5,5,5,5));
    Border miborde = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    //sinborde = BorderFactory.createEmptyBorder();
    l_titulo.setBorder(miborde);
    
    /*
    JPanel p_huso = new JPanel(new GridLayout(1,2));
    JPanel p_huso_uno = new JPanel(new BorderLayout());
    */
    JPanel p_huso = new JPanel(new BorderLayout());
    JPanel p_huso_izqda = new JPanel(new BorderLayout());
    
    JPanel p_coordenadas = new JPanel(new GridLayout(1,2)); //BorderLayout());
    JPanel p_x = new JPanel(new BorderLayout());
    //JPanel p_x = new JPanel(new BorderLayout());
    JPanel p_y = new JPanel(new BorderLayout());
    JPanel p_salida = new JPanel(new FlowLayout());
    
    JLabel l_huso = new JLabel(" Huso: ");
    
    String[] valoresHuso = { "28", "29", "30", "31" };
    SpinnerModel husoModel = new SpinnerListModel(valoresHuso);
    s_huso = new JSpinner(husoModel);
    s_huso.setValue(misigpac.s_huso.getValue());
    p_huso_izqda.add(l_huso, BorderLayout.WEST);
    p_huso_izqda.add(s_huso, BorderLayout.EAST); 
      
    p_huso.add(p_huso_izqda, BorderLayout.WEST);
    JPanel p_huso_dos = new JPanel();
    p_huso.add(p_huso_dos);
    
    
    
    
    JLabel l_x = new JLabel("  X: ");
    l_x.setToolTipText("Valor de las X de la coordenada UTM de la esquina inferior derecha");
    t_x = new JTextField(10);
    JLabel l_y = new JLabel("  Y: ");
    l_y.setToolTipText("Valor de las Y de la coordenada UTM de la esquina inferior derecha");
    t_y = new JTextField(10);
    
    p_x.add(l_x, BorderLayout.WEST);
    p_x.add(t_x, BorderLayout.EAST);
    p_y.add(l_y, BorderLayout.WEST);
    p_y.add(t_y, BorderLayout.EAST);
    p_coordenadas.add(p_x); //, BorderLayout.WEST);
    p_coordenadas.add(p_y); //, BorderLayout.EAST);
    //p_coordenadas.add(new JPanel(), BorderLayout.EAST);
            
    JButton aceptar = new JButton("Aceptar");
    aceptar.addActionListener(new ListenerDialogoEsquinaInfDcha(this));
    JButton cancelar = new JButton("Cancelar");
    cancelar.addActionListener(new ListenerDialogoEsquinaInfDcha(this));
        
    p_entrada.add(l_titulo);        
    p_entrada.add(p_huso);
    p_entrada.add(p_coordenadas);
    
    //p_salida.setAlignmentX(LEFT_ALIGNMENT);
    p_salida.add(aceptar);
    p_salida.add(cancelar);
    
    getContentPane().add(p_entrada, BorderLayout.NORTH);
    getContentPane().add(p_salida, BorderLayout.SOUTH);
    
    pack();
    setVisible(true);
    
  }
  
  
  
}


class ListenerDialogoEsquinaInfDcha implements ActionListener
{
  DialogoEsquinaInfDcha miDialogo;
    
  ListenerDialogoEsquinaInfDcha(DialogoEsquinaInfDcha di)
  {
     miDialogo = di;
  }  
  
  // A partir de las coordenadas de la esquina superior izquierda y de la esquina inferior derecha
  // se calcula el ancho y alto del mapa.
  // Sigo lo dicho en: http://en.wikipedia.org/wiki/Great-circle_distance
  public boolean CalcularAnchoAlto(double xid, double yid, byte huso_id)
  {
     double xsi, ysi;
     byte huso_si;
     String msj = "";
     //double Radio = 6372795.0D; // (en metros)
     double ancho=0D, alto=0D;;
     
     try {     
        msj = "Huso incorrecto de la esquina superior izquierda";	
        huso_si = (Byte.valueOf((String)miDialogo.misigpac.s_huso.getValue())).byteValue() ;
        msj = "Valor incorrecto de la X de la esquina superior izquierda";
        xsi = (Double.valueOf(miDialogo.misigpac.t_x.getText().trim())).doubleValue();
        msj = "Valor incorrecto de la Y de la esquina superior izquierda";
        ysi = (Double.valueOf(miDialogo.misigpac.t_y.getText().trim())).doubleValue();
                                 
     } catch (NullPointerException npe) {           
        miDialogo.misigpac.SacarVentanita("Datos incorrectos:", msj);          
        return false;
     } catch (NumberFormatException nfe) {        	           
        miDialogo.misigpac.SacarVentanita("Datos incorrectos:", msj);
        return false;
     }
         
     if (huso_si == huso_id)
     {
        ancho = xid - xsi;   
        alto = ysi - yid;
     } else if (huso_si > huso_id)
     {
     	miDialogo.misigpac.SacarVentanita("Datos incorrectos:", "Husos incorrectos.\nHuso izquierdo: " + huso_si + "    Huso derecho: " + huso_id);
     	return false; 
     } else
     {
     	Datum datumSalida;          
        if (((String)miDialogo.misigpac.comboDatum.getSelectedItem()).equals(miDialogo.misigpac.opcionesDatum[0]))
           datumSalida = Datum.datumED50;
        else
           datumSalida = Datum.datumWGS84;
        Distancia distancia = new Distancia(xsi, ysi, huso_si, xid, yid, huso_id, datumSalida);     
        ancho = distancia.CalcularAncho();
        alto = distancia.CalcularAlto();
        //System.out.println("ancho="+ancho+"   alto="+alto);
        //System.out.println("DISTANCIA=" + distancia.CalcularDistancia());        
        //System.out.println("ANCHO=" + distancia.CalcularAncho()); 
        //System.out.println("ALTO=" + distancia.CalcularAlto());  
     }
     
     if (ancho < 0)
     {
        miDialogo.misigpac.SacarVentanita("Datos incorrectos:", "El \"ancho\" calculado es negativo.\nComprueba que las coordenadas y el huso son correctas");   
        return false;
     }
       
     if (alto < 0)
     {
        miDialogo.misigpac.SacarVentanita("Datos incorrectos:", "El \"alto\" calculado es negativo.\nComprueba que las coordenadas son correctas");   
        return false;
     }
     
     miDialogo.misigpac.t_ancho.setText(Double.toString(ancho));
     miDialogo.misigpac.t_alto.setText(Double.toString(alto));
     miDialogo.misigpac.t_num_filas.setText("");
     miDialogo.misigpac.t_num_col.setText("");
     return true;
  }
  
  public void actionPerformed( ActionEvent evt )
  {
     String msj="";
     //boolean err = false;
     //System.out.println("actionPerformed="+evt.getActionCommand()+"  paramString="+evt.paramString());
     if (evt.getActionCommand().equals("Aceptar"))
     {     	
     	double x, y;
     	byte huso;
     	try {     
           msj = "Huso incorrecto";	
           huso = (Byte.valueOf((String)miDialogo.s_huso.getValue())).byteValue() ;
     	   msj = "Valor incorrecto de la X";
           x = (Double.valueOf(miDialogo.t_x.getText().trim())).doubleValue();
           msj = "Valor incorrecto de la Y";
           y = (Double.valueOf(miDialogo.t_y.getText().trim())).doubleValue();
           
           if (CalcularAnchoAlto(x, y, huso))
              miDialogo.setVisible(false);
        } catch (NullPointerException npe) {
           //err = true;
           miDialogo.misigpac.SacarVentanita("Datos incorrectos:", msj);          
        } catch (NumberFormatException nfe) {        	
           //err = true;
           miDialogo.misigpac.SacarVentanita("Datos incorrectos:", msj);
        }
             	                      	         
     } else if (evt.getActionCommand().equals("Cancelar"))
     {
     	//System.out.println("Se ha pulsado CANCELAR");     	
     	miDialogo.setVisible(false);
     } 
   }
}
