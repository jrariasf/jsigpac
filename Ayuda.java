import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Ayuda extends JFrame implements Runnable {
 JPanel panel;
 String titulo;
 String nomFich;
 JTextArea area;
 JButton salir;
 public static final long serialVersionUID = 0;

 public Ayuda(String _titulo, String nom_fich)
 {
   titulo = _titulo;
   nomFich = "dat" + File.separator + "ayudas" +
	     File.separator + nom_fich;
 }

 public void run()
 {
   setTitle(titulo);
   panel = new JPanel(new BorderLayout());
   JLabel tit = new JLabel("Ayuda jSIGPAC");
   area = new JTextArea();
   area.setMargin(new Insets(5,5,5,5));
   area.setEditable(false);
   JScrollPane areaScroll = new JScrollPane();
   areaScroll.getViewport().add(area);
   salir = new JButton(" - Salir - ");
   salir.addMouseListener (new java.awt.event.MouseAdapter() {
      public void mouseReleased (MouseEvent evt) {
	 dispose();
      }
   });

  panel.add(tit, BorderLayout.NORTH);
  panel.add(areaScroll, BorderLayout.CENTER);
  panel.add(salir, BorderLayout.SOUTH);
  getContentPane().add(panel);
  CargarFichero(nomFich);
  int anchoPantalla = Toolkit.getDefaultToolkit().getScreenSize().width;
  int altoPantalla =  Toolkit.getDefaultToolkit().getScreenSize().height;
  int ancho = 600;
  int alto = 500;
  setBounds(anchoPantalla/2-ancho/2, altoPantalla/2-alto/2,ancho,alto);
  setVisible( true );
}


public void CargarFichero(String fich) {
  try {
    FileReader file = new FileReader(fich);
    area.read(file, fich);
    file.close();
  } catch (IOException e) {
    JOptionPane.showMessageDialog(null, 
	  "No se ha podido leer el fichero de ayuda: "+fich,
	  "Fichero inexistente", JOptionPane.ERROR_MESSAGE);
  }
}

}

