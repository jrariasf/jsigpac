import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

class DialogoPajaro extends JDialog
{    
  JTextField t_margen;
  JSigpac misigpac;
  public static File ultimoDirectorio = null;
  public static final long serialVersionUID = 0;
  
  public DialogoPajaro(JSigpac fr, boolean mod, String nomFich, int anchoMapa, int altoMapa)
  {
    super(fr, "Vista global", mod);    
    Lienzo miLienzo;
        
    misigpac = fr;  
     
    Insets min = fr.getInsets();
    //System.out.println("bottom="+min.bottom+"  right="+min.right+"   left="+min.left+"   top="+min.top);
    
    setSize(anchoMapa+min.right+min.left, altoMapa+min.bottom+min.top);    
    setLocationRelativeTo(fr);      
    /*
    String fichero = nomFich + ".jpg";
    File fich = new File(fichero);
    if (fich.exists())
    {
       mapaGlobal = new ImageIcon(nomFich + ".jpg", "Vista Global");
       aloja = new JLabel(mapaGlobal);                      
    } else
       aloja = new JLabel("- Ha debido producirse algún error al obtener la vista global -", JLabel.CENTER); 
       
    */
    
    String fichero = nomFich + ".jpg";
    File aux = new File(fichero);
    if (aux.exists())
    {      
       Toolkit tk = Toolkit.getDefaultToolkit();
       Image img = tk.createImage(fichero);

       // Create an instance of the JPanel subclass
       miLienzo =  new Lienzo(img, anchoMapa+min.right+min.left, altoMapa+min.bottom+min.top);
       getContentPane().add(miLienzo, BorderLayout.CENTER); 
    } else
    {
       getContentPane().setLayout(new FlowLayout());
       JLabel mensajito = new JLabel("- Error al obtener la vista global -");
       //getContentPane().add(new Label(""), BorderLayout.WEST);
       getContentPane().add(mensajito); //, BorderLayout.NORTH);
       //getContentPane().add(new Label(""), BorderLayout.EAST); 	
       
    }
    	
    /*
    min = getInsets();
    System.out.println("_xx_ Antes del pack: bottom="+min.bottom+"  right="+min.right+"   left="+min.left+"   top="+min.top);
    pack();
    min = getInsets();
    System.out.println("_xx_ Despues del pack: bottom="+min.bottom+"  right="+min.right+"   left="+min.left+"   top="+min.top);
    */
    
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //DO_NOTHING_ON_CLOSE    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        //System.out.println("WindowClosing");
      }
    });
    
    //getContentPane().setSize(618, 421);
    //pack();    
    setVisible(true);  
    /* 
    Dimension dim = aloja.getSize(null);
    System.out.println("ancho="+dim.width+"  alto="+dim.height);
    Rectangle rec = getContentPane().getBounds();
    System.out.println("x="+rec.x+"  y="+rec.y+"   height="+rec.height+"   width="+rec.width);
    min = getInsets();
    System.out.println("bottom="+min.bottom+"  right="+min.right+"   left="+min.left+"   top="+min.top);    
    setVisible(false);
    setSize(618+618-dim.width, 421+421-dim.height);
    setVisible(true);
    */
  }
    
  
}

class Lienzo extends JPanel
{
  Image img;
  int _ancho, _alto;
  public static final long serialVersionUID = 0;
  
  Lienzo(Image image, int ancho, int alto)
  {
     img = image;
     _ancho = ancho;
     _alto = alto;
  }
  
  public void paintComponent (Graphics g) {
   // First paint background unless you will
   // paint whole area yourself.
   super.paintComponent (g);

   // Use the image width & height to find the starting point
   //int msgX = getSize ().width/2 - img.getWidth (this);
   //int msgY = getSize ().height/2 - img.getHeight (this);
   
   //System.out.println("msgX="+msgX+"   msgY="+msgY+"   ancho="+_ancho+"   alto="+_alto);

   //Draw image at centered in the middle of the panel    
   g.drawImage (img, 0, 0, this);
  } // paintComponent

} 
