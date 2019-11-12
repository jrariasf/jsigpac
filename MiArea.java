
import javax.swing.*;
import java.awt.*;

class MiArea extends JScrollPane
{
  JTextArea miarea;
  public static final long serialVersionUID = 0;
  
  MiArea ()
  {
    miarea = new JTextArea();
    miarea.setMargin(new Insets(5,5,5,5));
    miarea.setEditable(false);
    //miarea.setCaretPosition(0);
    //miarea.setAlignmentY(JTextArea.BOTTOM_ALIGNMENT);
    setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);     
    JViewport view = getViewport();
    //JSigpac.Traza("view.isOpaque()=" + view.isOpaque());
    //view.setOpaque(false);
    view.add(miarea);
  }

  MiArea (String texto)
  {
     this();
     EscribirTexto(texto);
  }

  void EscribirTexto(String texto)
  {     
     //JSigpac.Traza("MiArea::EscribirTexto  isEventDispatchThread=" + javax.swing.SwingUtilities.isEventDispatchThread() + "   currentThread="+Thread.currentThread());
     JSigpac.Traza("EscribirTexto: " + texto);     
     miarea.append(texto+"\n");   
     miarea.setCaretPosition(miarea.getText().length());
  }

  void Limpiar()
  {
     miarea.setText("");
  }
}