import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class TrackConverter extends JPanel
{
 boolean _wgs2ed;
 JRadioButton wgs2ed, ed2wgs;
 public static final long serialVersionUID = 0;

public TrackConverter()
{
 // Creo el entorno grafico del jtracker:
 JPanel jtracker = new JPanel(new BorderLayout());
 JPanel norte = new JPanel();
 JTextArea center = new JTextArea();
 center.setMargin(new Insets(5,5,5,5));
 center.setEditable(false);
 rellenar(center);

 wgs2ed = new JRadioButton("WGS84 a ED50");
 ed2wgs = new JRadioButton("ED50 a WGS84");
 wgs2ed.setSelected(true);
 wgs2ed.setBackground(Color.gray.brighter());
 ed2wgs.setBackground(Color.gray.brighter());
 ButtonGroup bg = new ButtonGroup();
 bg.add(wgs2ed);
 bg.add(ed2wgs);

 JButton fichero = new JButton("fichero");
 fichero.setActionCommand("fichero");
 fichero.addActionListener(new TrackActionListener(this));

 norte.add(wgs2ed);
 norte.add(ed2wgs);
 norte.add(new JPanel());
 norte.add(fichero);

 jtracker.add(norte, BorderLayout.NORTH);
 jtracker.add(center, BorderLayout.CENTER);

 add(jtracker);
}

void rellenar(JTextArea center)
{
 center.append("\nPara convertir tracks de Compe GPS de WGS84 a ED50 y viceversa. Tiene que ver con la conversion");
 center.append("\ninexacta que hacen los programas convencionales como Compe y Ozi al tratar diferentes datum.");
 center.append("\nPor ejemplo, al llevar un track del GPS (datum WGS84) a un mapa obtenido del SIGPAC (datum");
 center.append("\nED50), se observan irregularidades. Entonces, si convertimos antes el track de WGS84 a ED50");
 center.append("\ny luego lo cargamos en Compe o Ozi, los resultados mejoraran.");
 center.append("\nPara mas informacion consultar el foro:");
 center.append("\nhttp://www.pcdemano.com/phpBB2/viewtopic.php?t=13713");
 center.append("\nEl formato de los ficheros leidos es algo similar a:\n");
 center.append("\n\tG  WGS 84\n\tU  1");
 center.append("\n\tC  255 0 0 2 -1.000000");
 center.append("\n\tL  -01:00:00");
 center.append("\n\tV  0.0 0.0 0 0 0 0 0.0");
 center.append("\n\tE 0|1|00-NUL-00 00:00:00|00:00:00|0|156");
 center.append("\n\n\tz  -4.411417,41.535667,-4.404083,41.541500^");
 center.append("\n\tT  A 41.5415ºN 4.4116ºW 12-MAR-06 12:12:35 N 769.0 0.0 0.0 0.0 0 -1000.0 -1.0 -1 -1.0 -1.0");
 center.append("\n\tT  A 41.5426ºN 4.4119ºW 12-MAR-06 12:13:32 s 775.0 0.0 0.0 0.0 0 -1000.0 -1.0 -1 -1.0 -1.0");
 center.append("\n\t ...");

}

public TrackConverter(String[] args)
{
  IniciarProceso(args);
}

boolean  IniciarProceso(String[] args)
{
  String fileIN, fileOUT;
  _wgs2ed = true;
  String cola = "";

  if (args.length < 2)
  {
     System.out.println("Faltan argumentos");
     return false;
  }

  if (args[0].equals("-e"))
  {
     _wgs2ed = false;
     cola = "_WGS84";
  } else if (args[0].equals("-w"))
  {
     _wgs2ed = true;
     cola = "_ED50";
  } else
  {
     System.out.println("No se ha especificado el tipo de conversion a realizar");
     return false;
  }

  fileIN = args[1];
  int aux;
  //File aux;
  //aux = new File(fileIN);
  //System.out.println("getPath="+aux.getPath());
  //System.out.println("getParent="+aux.getParent());
  //System.out.println("getName="+aux.getName());
  aux = fileIN.lastIndexOf('.');
  fileOUT = fileIN.substring(0, aux) + cola + fileIN.substring(aux);
  System.out.println("fileOUT="+fileOUT);
  //fileOUT = "_"+fileIN;

  try {
    BufferedReader in = new BufferedReader(
		      new FileReader(fileIN));
    BufferedWriter out = new BufferedWriter(
		      new FileWriter(fileOUT));
    String linea = new String();
    String nuevalinea = new String();
    while ((linea = in.readLine()) != null)
    {
      if (linea.trim().startsWith("G"))
      {
	 if (_wgs2ed)
            nuevalinea = "G  European 1950";
	 else
            nuevalinea = "G  WGS 84";
      }
      else if (linea.trim().startsWith("z"))
         nuevalinea = ModificarLinea_z(linea);
      else if (linea.trim().startsWith("T"))
         nuevalinea = ModificarLinea_T(linea);
      else
         nuevalinea = linea;

      out.write(nuevalinea, 0, nuevalinea.length());
      out.newLine();
      out.flush();
    }
    in.close();
    out.close();
  } catch (FileNotFoundException fnfe) {
     System.err.println("No encontrado el fichero");
     System.err.println("fileIN="+fileIN);
     return false;
  } catch (IOException e) {
     System.err.println("TrackConverter::IOException: "+e);
     return false;
  }
  return true;
}


String ModificarLinea_z(String original)
{
  String dst, aux;
  String[] val;
  Datum datumORI, datumDST;
  Coordenada utmORIa, utmDSTa, utmORIb, utmDSTb;

  aux = original.trim().substring(1).trim(); // No contiene la 'z'
  val = aux.split(",");
  //System.out.println(val[0]+"*"+val[1]+"*"+val[2]+"*"+val[3]);
  if (_wgs2ed)
  {
     datumORI = new Datum(6378137D, 6356752.31424518);
     datumDST = new Datum(6378388D, 6356911.94612795);
  } else
  {
     datumORI = new Datum(6378388D, 6356911.94612795);
     datumDST = new Datum(6378137D, 6356752.31424518);
  }

  utmORIa = new Coordenada(datumORI,
	        (Double.valueOf(val[0])).doubleValue(),
	        (Double.valueOf(val[1])).doubleValue(), 0);
  utmDSTa = utmORIa.CambioDeDatum(datumDST);
  utmORIb = new Coordenada(datumORI,
	        (Double.valueOf(val[2])).doubleValue(),
	        (Double.valueOf(val[3])).doubleValue(), 0);
  utmDSTb = utmORIb.CambioDeDatum(datumDST);

  dst = "z  "+utmDSTa.getLon()+","+utmDSTa.getLat()+","+
	      utmDSTb.getLon()+","+utmDSTb.getLat();
  return dst;

}

String ModificarLinea_T(String original)
{
  int pos; 
  String aux, dst;
  String finN, finW;  // Contienen algo como "¦N" y "¦W"
  String[] val;
  Datum datumORI, datumDST;
  Coordenada utmORI, utmDST;

  if (_wgs2ed)
  {
     datumORI = new Datum(6378137D, 6356752.31424518);
     datumDST = new Datum(6378388D, 6356911.94612795);
  } else
  {
     datumORI = new Datum(6378388D, 6356911.94612795);
     datumDST = new Datum(6378137D, 6356752.31424518);
  }

  // Me deshago de la "A":
  pos = original.indexOf("A") + 1;
  //System.out.println(pos+"-"+original.substring(pos));
  aux = original.substring(pos).trim();
  val = aux.split(" ");
  //for (int i=0; i<val.length; i++)
  //  System.out.println(i+": "+val[i]);

  pos = val[0].length();
  finN = val[0].substring(pos-2, pos);
  val[0] = val[0].substring(0, pos-2);
  pos = val[1].length();
  finW = val[1].substring(pos-2, pos);
  val[1] = val[1].substring(0, pos-2);
  //System.out.println(finN+"   "+finW+":"+val[0]);
  
  double w = (Double.valueOf(val[1])).doubleValue();
  if (finW.equals("¦W"))
     w = (-w);
  //System.out.println("w="+w);

  utmORI = new Coordenada(datumORI,
	        w, (Double.valueOf(val[0])).doubleValue(), 
	        (Double.valueOf(val[5])).doubleValue()); 
  utmDST = utmORI.CambioDeDatum(datumDST);

  if (utmDST.getLon() < 0)
  {
     w = (-utmDST.getLon());
     finW = "¦W";
  } else
  {
     w = utmDST.getLon();
     finW = "¦E";
  }
     
  dst = "T  A " + utmDST.getLat() + finN + " " + w + finW;

  for (int i=2; i<val.length; i++)
      dst = dst + " " + val[i];
  
  return dst;
}


public static void main (String[] args)
{
  @SuppressWarnings("unused")
  TrackConverter tc = new TrackConverter(args);
}

}


// Clase para recoger los eventos de las pulsaciones:
class TrackActionListener implements ActionListener
{
  TrackConverter mitrack;

  TrackActionListener(TrackConverter tc)
  {
    mitrack = tc;
  }

  public void actionPerformed( ActionEvent evt )
  {
    if (evt.getActionCommand().equals("fichero"))
    {
       JFileChooser fc = new JFileChooser();
       int res = fc.showOpenDialog(mitrack);
       if (res == JFileChooser.APPROVE_OPTION)
       {
	  File file = fc.getSelectedFile();
	  System.out.println("Fichero:"+file.toString());

	  String[] args;
          args = new String[2];

	  if (mitrack.ed2wgs.isSelected())
	  {
	     args[0] = "-e";
	     args[1] = file.toString();
          } else
	  {
	     args[0] = "-w";
	     args[1] = file.toString();
	  }
	  if (mitrack.IniciarProceso(args))
	     JOptionPane.showMessageDialog(null,
		  "Proceso realizado. Mire la consola.");
          else
	     JOptionPane.showMessageDialog(null,
		  "Se ha producido algun error. Mire la consola.");

       }
     }

  }
}
