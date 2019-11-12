//import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Calculadora extends JPanel
{
 // ENTRADA:
 JRadioButton utm, geo;
 JTextField x, y, z;
 JRadioButton N, S;
 JSpinner huso;
 JRadioButton decimal, sexagesimal;
 JTextField lonDec, latDec;
 JTextField gradosLon, minLon, segLon;
 JButton EW;
 JTextField gradosLat, minLat, segLat;

 JButton completar, limpiar, convertir;
 JComboBox ed_wgs; // n_s;
 JButton n_s;

 // SALIDA:
 JTextField xs, ys, zs;
 JTextField husos;
 JTextField lonDec_ss, latDec_ss;
 JTextField gradosLon_ss, minLon_ss, segLon_ss;
 JTextField gradosLat_ss, minLat_ss, segLat_ss;

 JButton limpiar_ss;
 JLabel ed_wgs_ss, EW_ss, n_ss;
 public static final long serialVersionUID = 0;

public Calculadora()
{
 // Creo el entorno grafico de la calculadora:
 JPanel entrada, salida;
 JPanel entrada_norte, entrada_centro, entrada_sur;

 setLayout(new BorderLayout());

 entrada = new JPanel(new BorderLayout());

 entrada_norte = new JPanel(new BorderLayout());
 String[] opcionesCombo = { "ED50", "WGS84" };
 ed_wgs = new JComboBox(opcionesCombo);
 ed_wgs.setToolTipText("Datum de las coordenadas de entrada");
 ed_wgs.setForeground(Color.red);
 ed_wgs.setActionCommand("ed_wgs");
 ed_wgs.addActionListener(new CalculadoraActionListener(this));
 JButton ayuda = new JButton("ayuda");
 ayuda.setActionCommand("ayuda");
 ayuda.setToolTipText("Ayuda de la calculadora");
 ayuda.setBackground(Color.gray.brighter());
 ayuda.addActionListener(new CalculadoraActionListener(this));

JLabel informacion = new JLabel("     - El cambio de datum sólo es válido para coordenadas españolas -");

 entrada_norte.add(ed_wgs, BorderLayout.WEST);
 entrada_norte.add(informacion, BorderLayout.CENTER, JLabel.CENTER);
 entrada_norte.add(ayuda, BorderLayout.EAST);
 entrada.add(entrada_norte, BorderLayout.NORTH);

 entrada_sur = new JPanel(new BorderLayout());
 JPanel botonera_entrada = new JPanel();
 completar = new JButton("completar");
 completar.setToolTipText("Convierte al resto de formatos los valores introducidos");
 completar.setBackground(Color.cyan);
 completar.setActionCommand("completar");
 completar.addActionListener(new CalculadoraActionListener(this));
 limpiar = new JButton("limpiar");
 limpiar.setToolTipText("Borra todas las cajas");
 limpiar.setBackground(Color.green);
 limpiar.setActionCommand("limpiar_entrada");
 limpiar.addActionListener(new CalculadoraActionListener(this));
 convertir = new JButton("CONVERTIR a WGS84");
 convertir.setBackground(Color.pink);
 convertir.setToolTipText("Generar coordenadas en el nuevo Datum");
 convertir.setActionCommand("convertir");
 convertir.addActionListener(new CalculadoraActionListener(this));
 botonera_entrada.add(completar);
 botonera_entrada.add(limpiar);
 botonera_entrada.add(convertir);
 entrada_sur.add(botonera_entrada, BorderLayout.EAST);
 entrada.add(entrada_sur, BorderLayout.SOUTH);

 entrada_centro = new JPanel(new BorderLayout());
 JPanel izqda = new JPanel (new GridLayout(3,2));
 JPanel drcha = new JPanel (new GridLayout(3,1));
 utm = new JRadioButton("UTM");
 utm.setActionCommand("en utm");
 utm.addActionListener(new CalculadoraActionListener(this));
 geo = new JRadioButton("GEO");
 geo.setActionCommand("en geo");
 geo.addActionListener(new CalculadoraActionListener(this));
 decimal = new JRadioButton("dec");
 decimal.setActionCommand("en geoDec");
 decimal.addActionListener(new CalculadoraActionListener(this));
 sexagesimal = new JRadioButton("sex");
 sexagesimal.setActionCommand("en geoSex");
 sexagesimal.addActionListener(new CalculadoraActionListener(this));
 //sexagesimal.setBackground(Color.orange);
 ButtonGroup utm_geo = new ButtonGroup();
 utm_geo.add(utm);
 utm_geo.add(geo);
 ButtonGroup dec_sex = new ButtonGroup();
 dec_sex.add(decimal);
 dec_sex.add(sexagesimal);
 izqda.add(utm);
 JPanel aux1 = new JPanel();
 izqda.add(aux1);
 izqda.add(geo);
 izqda.add(decimal);
 JPanel aux2 = new JPanel();
 izqda.setBackground(Color.red);
 izqda.add(aux2);
 izqda.add(sexagesimal);


 // Coordenadas UTM:
 JPanel panelUTM = new JPanel();
 JLabel _x, _y, _z, _huso;
 x = new JTextField(10);
 y = new JTextField(10);
 z = new JTextField(5);
 z.setText("0");
 _x = new JLabel("X:");
 _y = new JLabel(" Y:");
 _z = new JLabel(" Z:");
 _huso = new JLabel("  huso:");
 //String[] opcionesNS = { "N", "S" };
 //n_s = new JComboBox(opcionesNS);
 n_s = new JButton("N");
 n_s.setToolTipText("Hemisferio Norte o Sur. Pulse para cambiar.");
 n_s.setActionCommand("n_s");
 n_s.addActionListener(new CalculadoraActionListener(this));
 //n_s.setSize(10,10);
 SpinnerModel husoModel = new SpinnerNumberModel(30, 1, 60, 1);
 huso = new JSpinner(husoModel);
 panelUTM.add(_x);
 panelUTM.add(x);
 panelUTM.add(_y);
 panelUTM.add(y);
 panelUTM.add(_z);
 panelUTM.add(z);
 panelUTM.add(n_s);
 panelUTM.add(_huso);
 panelUTM.add(huso);
 drcha.add(panelUTM, BorderLayout.CENTER);

 // Coordenadas geodesicas (decimal):
 JPanel panelDec = new JPanel();
 JLabel _lonDec, _latDec;
 lonDec = new JTextField(12);
 latDec = new JTextField(12);
 _lonDec = new JLabel("   Longitud:");
 _latDec = new JLabel("   Latitud:");
 panelDec.add(_lonDec);
 panelDec.add(lonDec);
 panelDec.add(_latDec);
 panelDec.add(latDec);
 drcha.add(panelDec, BorderLayout.CENTER);

 // Coordenadas geodesicas (sexagesimal):
 JPanel panelSex = new JPanel();
 JLabel _lonSex, _latSex;
 JLabel _gradosLon, _minLon, _segLon, _gradosLat, _minLat, _segLat;
 _lonSex = new JLabel("   Lon:");
 _latSex = new JLabel("   Lat:");
 gradosLon = new JTextField(3);
 minLon = new JTextField(3);
 segLon = new JTextField(8);
 EW = new JButton("E");
 EW.setToolTipText("E: longitudes negativas;  W: longitudes positivas");
 EW.setActionCommand("e_w");
 EW.addActionListener(new CalculadoraActionListener(this));
 gradosLat = new JTextField(3);
 minLat = new JTextField(3);
 segLat = new JTextField(8);
 _gradosLon = new JLabel("º");
 _minLon = new JLabel("'");
 _segLon = new JLabel("\"");
 _gradosLat = new JLabel("º");
 _minLat = new JLabel("'");
 _segLat = new JLabel("\"");
 panelSex.add(_lonSex);
 panelSex.add(gradosLon);
 panelSex.add(_gradosLon);
 panelSex.add(minLon);
 panelSex.add(_minLon);
 panelSex.add(segLon);
 panelSex.add(_segLon);
 panelSex.add(EW);
 panelSex.add(_latSex);
 panelSex.add(gradosLat);
 panelSex.add(_gradosLat);
 panelSex.add(minLat);
 panelSex.add(_minLat);
 panelSex.add(segLat);
 panelSex.add(_segLat);
 drcha.add(panelSex, BorderLayout.CENTER);

 entrada_centro.add(izqda, BorderLayout.WEST);
 entrada_centro.add(drcha, BorderLayout.CENTER);
 entrada.add(entrada_centro, BorderLayout.CENTER);


 // Panel salida:
 JPanel salida_norte, salida_centro, salida_sur;

 salida = new JPanel(new BorderLayout());

 salida_norte = new JPanel(new BorderLayout());
 ed_wgs_ss = new JLabel("WGS84");
 ed_wgs_ss.setToolTipText("Datum de las coordenadas de salida");
 ed_wgs_ss.setBackground(Color.orange);
 //_cc_ convertir = new JButton("CONVERTIR a WGS84");
 //_cc_ convertir.setActionCommand("convertir");
 salida_norte.add(ed_wgs_ss, BorderLayout.WEST);
 //_cc_ salida_norte.add(convertir, BorderLayout.EAST);
 salida.add(salida_norte, BorderLayout.NORTH);

 salida_sur = new JPanel(new BorderLayout());
 JPanel botonera_salida = new JPanel();
 limpiar_ss = new JButton("Limpiar");
 limpiar_ss.setBackground(Color.green);
 limpiar_ss.setToolTipText("Borra todas las cajas");
 limpiar_ss.setActionCommand("limpiar_salida");
 limpiar_ss.addActionListener(new CalculadoraActionListener(this));
 botonera_salida.add(limpiar_ss);
 salida_sur.add(botonera_salida, BorderLayout.EAST);
 salida.add(salida_sur, BorderLayout.SOUTH);

 salida_centro = new JPanel(new BorderLayout());
 JPanel izqda_ss = new JPanel (new GridLayout(3,2));
 JPanel drcha_ss = new JPanel (new GridLayout(3,1));
 JLabel aux3, aux4, aux5, aux6, aux7, aux8;
 aux3 = new JLabel("           ");
 aux4 = new JLabel("           ");
 aux5 = new JLabel("           ");
 aux6 = new JLabel("           ");
 aux7 = new JLabel("           ");
 aux8 = new JLabel("           ");

 izqda_ss.add(aux3);
 izqda_ss.add(aux4);
 izqda_ss.add(aux5);
 izqda_ss.add(aux6);
 izqda_ss.add(aux7);
 izqda_ss.add(aux8);


 // Coordenadas UTM:
 JPanel panelUTM_ss = new JPanel();
 JLabel _xs, _ys, _zs, _husos;
 xs = new JTextField(10);
 ys = new JTextField(10);
 zs = new JTextField(8);
 xs.setEditable(false);
 ys.setEditable(false);
 zs.setEditable(false);
 _xs = new JLabel("X:");
 _ys = new JLabel(" Y:");
 _zs = new JLabel(" Z:");
 _husos = new JLabel("  huso:");
 n_ss = new JLabel("  N");
 husos = new JTextField(3);
 husos.setEditable(false);
 panelUTM_ss.add(_xs);
 panelUTM_ss.add(xs);
 panelUTM_ss.add(_ys);
 panelUTM_ss.add(ys);
 panelUTM_ss.add(_zs);
 panelUTM_ss.add(zs);
 panelUTM_ss.add(n_ss);
 panelUTM_ss.add(_husos);
 panelUTM_ss.add(husos);
 drcha_ss.add(panelUTM_ss, BorderLayout.CENTER);

 // Coordenadas geodesicas (decimal):
 JPanel panelDec_ss = new JPanel();
 JLabel _lonDec_ss, _latDec_ss;
 lonDec_ss = new JTextField(14);
 lonDec_ss.setEditable(false);
 latDec_ss = new JTextField(14);
 latDec_ss.setEditable(false);
 _lonDec_ss = new JLabel("   Longitud:");
 _latDec_ss = new JLabel("   Latitud:");
 panelDec_ss.add(_lonDec_ss);
 panelDec_ss.add(lonDec_ss);
 panelDec_ss.add(_latDec_ss);
 panelDec_ss.add(latDec_ss);
 drcha_ss.add(panelDec_ss, BorderLayout.CENTER);

 // Coordenadas geodesicas (sexagesimal):
 JPanel panelSex_ss = new JPanel();
 JLabel _lonSex_ss, _latSex_ss;
 JLabel _gradosLon_ss, _minLon_ss, _segLon_ss, _gradosLat_ss, _minLat_ss, _segLat_ss;
 _lonSex_ss = new JLabel("   Lon:");
 _latSex_ss = new JLabel("   Lat:");
 gradosLon_ss = new JTextField(3);
 gradosLon_ss.setEditable(false);
 minLon_ss = new JTextField(3);
 minLon_ss.setEditable(false);
 segLon_ss = new JTextField(12);
 segLon_ss.setEditable(false);
 EW_ss = new JLabel("  E"); 
 gradosLat_ss = new JTextField(3);
 gradosLat_ss.setEditable(false);
 minLat_ss = new JTextField(3);
 minLat_ss.setEditable(false);
 segLat_ss = new JTextField(12);
 segLat_ss.setEditable(false);
 _gradosLon_ss = new JLabel("º");
 _minLon_ss = new JLabel("'");
 _segLon_ss = new JLabel("\"");
 _gradosLat_ss = new JLabel("º");
 _minLat_ss = new JLabel("'");
 _segLat_ss = new JLabel("\"");
 panelSex_ss.add(_lonSex_ss);
 panelSex_ss.add(gradosLon_ss);
 panelSex_ss.add(_gradosLon_ss);
 panelSex_ss.add(minLon_ss);
 panelSex_ss.add(_minLon_ss);
 panelSex_ss.add(segLon_ss);
 panelSex_ss.add(_segLon_ss);
 panelSex_ss.add(EW_ss);
 panelSex_ss.add(_latSex_ss);
 panelSex_ss.add(gradosLat_ss);
 panelSex_ss.add(_gradosLat_ss);
 panelSex_ss.add(minLat_ss);
 panelSex_ss.add(_minLat_ss);
 panelSex_ss.add(segLat_ss);
 panelSex_ss.add(_segLat_ss);
 drcha_ss.add(panelSex_ss, BorderLayout.CENTER);

 //salida_centro.add(izqda_ss, BorderLayout.WEST);
 salida_centro.add(drcha_ss, BorderLayout.CENTER);

 add(entrada, BorderLayout.NORTH);
 //add(centro, BorderLayout.CENTER);
 salida.add(salida_norte, BorderLayout.NORTH);
 salida.add(salida_centro, BorderLayout.CENTER);
 salida.add(salida_sur, BorderLayout.SOUTH);

 add(salida, BorderLayout.SOUTH);

 //utm.setSelected(true);
 CambiarEstado_UTM(true);
 CambiarEstado_Decimal(false);
 CambiarEstado_Sexasegimal(false);
 //decimal.setEnabled(false);
}


public Calculadora(String[] args)
{
  //IniciarProceso(args);
}


void Ayuda()
{
  Ayuda help = new Ayuda("Ayuda: Calculadora", "calculadora.txt");
  Thread unThread = new Thread(help, "La_Ayuda");
  unThread.start();
}

void SacarVentanita(String titulo, String mensaje)
{
  JOptionPane.showMessageDialog(this, mensaje, titulo,
	  JOptionPane.ERROR_MESSAGE);
}

void CambiarEstado_UTM(boolean estado)
{
  utm.setSelected(estado);
  //decimal.setEnabled(!estado)
  //sexagesimal.setEnabled(!estado);
  //x.setEnabled(estado);
  x.setEditable(estado);
  //y.setEnabled(estado);
  y.setEditable(estado);
  //z.setEnabled(estado);
  z.setEditable(estado);
  n_s.setEnabled(estado);
  //n_s.setEditable(estado);
  huso.setEnabled(estado);
  //huso.setEditable(estado);

}

void CambiarEstado_Decimal(boolean estado)
{
  geo.setSelected(estado);
  decimal.setEnabled(estado);
  decimal.setSelected(estado);
  //lonDec.setEnabled(estado);
  lonDec.setEditable(estado);
  //latDec.setEnabled(estado);
  latDec.setEditable(estado);
}

void CambiarEstado_Sexasegimal(boolean estado)
{
  geo.setSelected(estado);
  //sexagesimal.setSelected(estado);
  sexagesimal.setEnabled(estado);
  //gradosLon.setEnabled(estado);
  gradosLon.setEditable(estado);
  //minLon.setEnabled(estado);
  minLon.setEditable(estado);
  //segLon.setEnabled(estado);
  segLon.setEditable(estado);
  EW.setEnabled(estado);
  
  //gradosLat.setEnabled(estado);
  gradosLat.setEditable(estado);
  //minLat.setEnabled(estado);
  minLat.setEditable(estado);
  //segLat.setEnabled(estado);
  segLat.setEditable(estado);
}

void LimpiarEntrada()
{
  x.setText("");
  y.setText("");
  z.setText("0");
  //n_s.setSelectedItem("N");
  n_s.setText("N");
  huso.setValue(new Integer(30));
  lonDec.setText("");
  latDec.setText("");
  gradosLon.setText("");
  minLon.setText("");
  segLon.setText("");
  EW.setText("E");
  gradosLat.setText("");
  minLat.setText("");
  segLat.setText("");

}

void LimpiarSalida()
{
  xs.setText("");
  ys.setText("");
  zs.setText("");
  n_ss.setText("N");
  husos.setText("30");
  lonDec_ss.setText("");
  latDec_ss.setText("");
  gradosLon_ss.setText("");
  minLon_ss.setText("");
  segLon_ss.setText("");
  EW_ss.setText("E");
  gradosLat_ss.setText("");
  minLat_ss.setText("");
  segLat_ss.setText("");

}

void PonerSalida(Coordenada utmDST)
{
  // Rellenamos la cuadricula de salida:
  xs.setText(Double.toString(utmDST.getX()));
  ys.setText(Double.toString(utmDST.getY()));
  zs.setText(Double.toString(utmDST.getZ()));
  husos.setText(Byte.toString(utmDST.getHuso()));
  
  if (utmDST.EsHemisferioNorte())
     n_ss.setText("N");
  else
     n_ss.setText("S");

  lonDec_ss.setText(Double.toString(utmDST.getLon()));
  latDec_ss.setText(Double.toString(utmDST.getLat()));
  gradosLon_ss.setText(Integer.toString(utmDST.getGradosAbs(utmDST.getLon())));
  minLon_ss.setText(Integer.toString(utmDST.getMinutos(utmDST.getLon())));
  segLon_ss.setText(Double.toString(utmDST.getSegundos(utmDST.getLon())));
  if (utmDST.getLon() < 0)
     EW_ss.setText("W");
  else
     EW_ss.setText("E");
  gradosLat_ss.setText(Integer.toString(utmDST.getGrados(utmDST.getLat())));
  minLat_ss.setText(Integer.toString(utmDST.getMinutos(utmDST.getLat())));
  segLat_ss.setText(Double.toString(utmDST.getSegundos(utmDST.getLat())));
}

void CompletarUTM(Coordenada utmORI)
{
  x.setText(Double.toString(utmORI.getX()));
  y.setText(Double.toString(utmORI.getY()));
  z.setText(Double.toString(utmORI.getZ()));
  huso.setValue(new Integer(utmORI.getHuso()));
  
  if (utmORI.EsHemisferioNorte())
     n_s.setText("N"); //n_s.setSelectedItem("N");
  else
     n_s.setText("S"); // n_s.setSelectedItem("S");

}

void CompletarGeoDec(Coordenada utmORI)
{
  lonDec.setText(Double.toString(utmORI.getLon()));
  latDec.setText(Double.toString(utmORI.getLat()));
}

void CompletarGeoSex(Coordenada utmORI)
{
  gradosLon.setText(Integer.toString(utmORI.getGradosAbs(utmORI.getLon())));
  minLon.setText(Integer.toString(utmORI.getMinutos(utmORI.getLon())));
  segLon.setText(Double.toString(utmORI.getSegundos(utmORI.getLon())));
  if (utmORI.getLon() < 0)
     EW.setText("W");
  else
     EW.setText("E");
  gradosLat.setText(Integer.toString(utmORI.getGrados(utmORI.getLat())));
  minLat.setText(Integer.toString(utmORI.getMinutos(utmORI.getLat())));
  segLat.setText(Double.toString(utmORI.getSegundos(utmORI.getLat())));
}

void CompletarGeo(Coordenada utmORI)
{
  CompletarGeoDec(utmORI);
  CompletarGeoSex(utmORI);
}

void Completar()
{
  Datum datumORI;
  Coordenada utmORI;
  String msj = "";

  // Comprobamos a ver en que datum esta la coordenada de entrada:
  if (ed_wgs.getSelectedItem().equals("ED50"))
     datumORI = new Datum(6378388D, 6356911.94612795);
  else
     datumORI = new Datum(6378137D, 6356752.31424518);

  if (utm.isSelected())
  {
     double X=0, Y=0, Z=0;
     byte h;
     boolean norte;
     try {
       msj = "coordenada X";
       X = (Double.valueOf(x.getText().trim())).doubleValue();
       msj = "coordenada Y";
       Y = (Double.valueOf(y.getText().trim())).doubleValue();
       msj = "coordenada Z";
       Z = (Double.valueOf(z.getText().trim())).doubleValue();
     } catch (NullPointerException npe) {
       SacarVentanita("Valor incorrecto", "Valor nulo en "+msj);
     } catch (NumberFormatException nfe) {
       SacarVentanita("Valor incorrecto", "Valor incorrecto en "+msj);
     }

     h = ((Integer)(huso.getValue())).byteValue();
     //if (n_s.getSelectedItem().equals("N"))
     if (n_s.getText().equals("N"))
	norte = true;
     else
	norte = false;

     //System.out.println("UTM: X="+X+" Y="+Y+" z"+Z+" h="+h+" norte="+norte);

     utmORI = new Coordenada(datumORI, X, Y, Z, h, norte);
     CompletarGeo(utmORI);
  } else if (decimal.isSelected())
  {
     double lon=0, lat=0;

     try {
       msj = "longitud (decimal)";
       lon = (Double.valueOf(lonDec.getText().trim())).doubleValue();
       msj = "latitud (decimal)";
       lat = (Double.valueOf(latDec.getText().trim())).doubleValue();
     } catch (NullPointerException npe) {
       SacarVentanita("Valor incorrecto", "Valor nulo en "+msj);
     } catch (NumberFormatException nfe) {
       SacarVentanita("Valor incorrecto", "Valor incorrecto en "+msj);
     }

     utmORI = new Coordenada(datumORI, lon, lat, 0); // Tomamos la elevacion NULA !!!
     CompletarGeoSex(utmORI);
     CompletarUTM(utmORI);
  } else // Sexagesimal:
  {
     double lon=0, lat=0;
     int grados, minutos;
     double segundos;

     try {
        msj = "grados longitud (sexagesimal)";
        grados = Math.abs((Integer.valueOf(gradosLon.getText().trim())).intValue());
        msj = "minutos longitud (sexagesimal)";
        minutos = Math.abs((Integer.valueOf(minLon.getText().trim())).intValue());
        msj = "segundos longitud (sexagesimal)";
        segundos = Math.abs((Double.valueOf(segLon.getText().trim())).doubleValue());
        lon = Coordenada.getDecimal(grados, minutos, segundos, EW.getText());

        msj = "grados latitud (sexagesimal)";
        grados = (Integer.valueOf(gradosLat.getText().trim())).intValue();
        msj = "minutos latitud (sexagesimal)";
        minutos = (Integer.valueOf(minLat.getText().trim())).intValue();
        msj = "segundos latitud (sexagesimal)";
        segundos = (Double.valueOf(segLat.getText().trim())).doubleValue();
        lat = Coordenada.getDecimal(grados, minutos, segundos);
     } catch (NullPointerException npe) {
        SacarVentanita("Valor incorrecto", "Valor nulo en "+msj);
     } catch (NumberFormatException nfe) {
        SacarVentanita("Valor incorrecto", "Valor incorrecto en "+msj);
     }

     utmORI = new Coordenada(datumORI, lon, lat, 0); // Tomamos la elevacion NULA !!!
     CompletarGeoDec(utmORI);
     CompletarUTM(utmORI);
  }

  HacerVisiblesLosTextos();

}

void HacerVisiblesLosTextos()
{

 x.setCaretPosition(0);
 y.setCaretPosition(0);
 z.setCaretPosition(0);
 lonDec.setCaretPosition(0);
 latDec.setCaretPosition(0);
 gradosLon.setCaretPosition(0);
 minLon.setCaretPosition(0);
 segLon.setCaretPosition(0);
 gradosLat.setCaretPosition(0);
 minLat.setCaretPosition(0);
 segLat.setCaretPosition(0);
 xs.setCaretPosition(0);
 ys.setCaretPosition(0);
 zs.setCaretPosition(0);
 husos.setCaretPosition(0);
 lonDec_ss.setCaretPosition(0);
 latDec_ss.setCaretPosition(0);
 gradosLon_ss.setCaretPosition(0);
 minLon_ss.setCaretPosition(0);
 segLon_ss.setCaretPosition(0);
 gradosLat_ss.setCaretPosition(0);
 minLat_ss.setCaretPosition(0);
 segLat_ss.setCaretPosition(0);

}

void Convertir()
{
  Datum datumORI, datumDST;
  Coordenada utmORI, utmDST;
  String msj = "";

  //BoundedRangeModel model = x.getHorizontalVisibility();
  //System.out.println("model: "+model);
  //int extent = model.getExtent();
  //System.out.println("extent: "+extent);
  //System.out.println("getCaretPosition: "+x.getCaretPosition());
  // x.setScrollOffset(extent);


  // Comprobamos a ver en que datum esta  la coordenada de entrada:
  if (ed_wgs.getSelectedItem().equals("ED50"))
  {
     datumORI = new Datum(6378388D, 6356911.94612795);
     datumDST = new Datum(6378137D, 6356752.31424518);
  } else
  {
     datumORI = new Datum(6378137D, 6356752.31424518);
     datumDST = new Datum(6378388D, 6356911.94612795);
  }

  if (utm.isSelected())
  {
     double X=0, Y=0, Z=0;
     byte h;
     boolean norte;
     try {
       msj = "coordenada X";
       X = (Double.valueOf(x.getText().trim())).doubleValue();
       msj = "coordenada Y";
       Y = (Double.valueOf(y.getText().trim())).doubleValue();
       msj = "coordenada Z";
       Z = (Double.valueOf(z.getText().trim())).doubleValue();
     } catch (NullPointerException npe) {
       SacarVentanita("Valor incorrecto", "Valor nulo en "+msj); return;
     } catch (NumberFormatException nfe) {
       SacarVentanita("Valor incorrecto", "Valor incorrecto en "+msj); return;
     }

     h = ((Integer)(huso.getValue())).byteValue();
     //if (n_s.getSelectedItem().equals("N"))
     if (n_s.getText().equals("N"))
	norte = true;
     else
	norte = false;

     //System.out.println("UTM: X="+X+" Y="+Y+" z"+Z+" h="+h+" norte="+norte);

     utmORI = new Coordenada(datumORI, X, Y, Z, h, norte);
     utmDST = utmORI.CambioDeDatum(datumDST);
     PonerSalida(utmDST);

  } else if (decimal.isSelected())
  {
     double lon=0, lat=0;

     try {
       msj = "longitud (decimal)";
       lon = (Double.valueOf(lonDec.getText().trim())).doubleValue();
       msj = "latitud (decimal)";
       lat = (Double.valueOf(latDec.getText().trim())).doubleValue();
     } catch (NullPointerException npe) {
       SacarVentanita("Valor incorrecto", "Valor nulo en "+msj); return;
     } catch (NumberFormatException nfe) {
       SacarVentanita("Valor incorrecto", "Valor incorrecto en "+msj); return;
     }

     utmORI = new Coordenada(datumORI, lon, lat, 0); // Tomamos la elevacion NULA !!!
     utmDST = utmORI.CambioDeDatum(datumDST);
     PonerSalida(utmDST);
  } else {  // Sexagesimal
     double lon=0, lat=0;
     int grados, minutos;
     double segundos;

     try {
        msj = "grados longitud (sexagesimal)";
        grados = Math.abs((Integer.valueOf(gradosLon.getText().trim())).intValue());
        msj = "minutos longitud (sexagesimal)";
        minutos = Math.abs((Integer.valueOf(minLon.getText().trim())).intValue());
        msj = "segundos longitud (sexagesimal)";
        segundos = Math.abs((Double.valueOf(segLon.getText().trim())).doubleValue());
        lon = Coordenada.getDecimal(grados, minutos, segundos, EW.getText());

        msj = "grados latitud (sexagesimal)";
        grados = (Integer.valueOf(gradosLat.getText().trim())).intValue();
        msj = "minutos latitud (sexagesimal)";
        minutos = (Integer.valueOf(minLat.getText().trim())).intValue();
        msj = "segundos latitud (sexagesimal)";
        segundos = (Double.valueOf(segLat.getText().trim())).doubleValue();
        lat = Coordenada.getDecimal(grados, minutos, segundos);
     } catch (NullPointerException npe) {
        SacarVentanita("Valor incorrecto", "Valor nulo en "+msj); return;
     } catch (NumberFormatException nfe) {
        SacarVentanita("Valor incorrecto", "Valor incorrecto en "+msj); return;
     }

     utmORI = new Coordenada(datumORI, lon, lat, 0); // Tomamos la elevacion NULA !!!
     utmDST = utmORI.CambioDeDatum(datumDST);
     PonerSalida(utmDST);
  }

  HacerVisiblesLosTextos();
}

public static void main (String[] args)
{
  JFrame fr = new JFrame();
  Calculadora calc = new Calculadora();
  fr.getContentPane().add(calc);
  fr.setSize(600,400); 
  fr.setVisible(true);
}
}

// Clase para recoger los eventos de las pulsaciones:
class CalculadoraActionListener implements ActionListener
{
  Calculadora calculadora;

  CalculadoraActionListener(Calculadora calc)
  {
    calculadora = calc;
  }

  public void actionPerformed( ActionEvent evt )
  {
    if (evt.getActionCommand().equals("ayuda"))
    {
       calculadora.Ayuda();
    } else if (evt.getActionCommand().equals("n_s"))
    {
       if (calculadora.n_s.getText().equals("N"))
	  calculadora.n_s.setText("S");
       else
	  calculadora.n_s.setText("N");
    } else if (evt.getActionCommand().equals("e_w"))
    {
       if (calculadora.EW.getText().equals("E"))
	  calculadora.EW.setText("W");
       else
	  calculadora.EW.setText("E");  	  	  	  
    } else if (evt.getActionCommand().equals("completar"))
    {
       calculadora.Completar();
    } else if (evt.getActionCommand().equals("limpiar_entrada"))
    {
      calculadora.LimpiarEntrada();
    } else if (evt.getActionCommand().equals("limpiar_salida"))
    {
      calculadora.LimpiarSalida();
    } else if (evt.getActionCommand().equals("convertir"))
    {
       calculadora.Convertir();
    } else if (evt.getActionCommand().equals("en utm"))
    {
       calculadora.CambiarEstado_UTM(true);
       calculadora.CambiarEstado_Decimal(false);
       calculadora.CambiarEstado_Sexasegimal(false);
    } else if (evt.getActionCommand().equals("en geo"))
    {
       calculadora.CambiarEstado_UTM(false);       
       calculadora.CambiarEstado_Decimal(true);
       calculadora.CambiarEstado_Sexasegimal(false);
       //calculadora.decimal.setSelected(true);
       calculadora.sexagesimal.setEnabled(true);

    } else if (evt.getActionCommand().equals("en geoDec"))
    {
       calculadora.CambiarEstado_UTM(false);
       calculadora.CambiarEstado_Decimal(true);
       calculadora.CambiarEstado_Sexasegimal(false);
       calculadora.sexagesimal.setEnabled(true);
    } else if (evt.getActionCommand().equals("en geoSex"))
    {
       calculadora.CambiarEstado_UTM(false);
       calculadora.CambiarEstado_Decimal(false);
       calculadora.CambiarEstado_Sexasegimal(true);
       calculadora.decimal.setEnabled(true);
    } else if (evt.getActionCommand().equals("ed_wgs"))
    {
       //System.out.println("ed_wgs: "+evt.getModifiers()+ "  "+evt.paramString());
       if (calculadora.ed_wgs.getSelectedItem().equals("ED50"))
       {
	  calculadora.ed_wgs_ss.setText("WGS84");
          calculadora.convertir.setText("CONVERTIR a WGS84");
       } else
       {
	  calculadora.ed_wgs_ss.setText("ED50");
          calculadora.convertir.setText("CONVERTIR a ED50");
       }
    }

  }
}
