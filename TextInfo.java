import javax.swing.JTextArea;
import java.awt.Insets;

abstract class TextInfo extends JTextArea
{  
  public static final long serialVersionUID = 0;
  
  TextInfo ()
  {    
    setMargin(new Insets(5,5,5,5));
    setEditable(false);
    rellenar();    
  }
  
  abstract void rellenar();
  
}