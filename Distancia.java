  
class Distancia {  
  Coordenada UTMsi, UTMid;
  double Lon_si, Lat_si, Lon_id, Lat_id;
 
  public Distancia(double xsi, double ysi, byte huso_si, double xid, double yid, byte huso_id, Datum datum)
  { 
     // Ahora se calculan las coordenas para tener la latitud y longitud:                    
     //UTMsi = new Coordenada(new Datum(6378388D, 6356911.94612795),
     UTMsi = new Coordenada(datum, xsi, ysi, 0, huso_si, true);
     //UTMid = new Coordenada(new Datum(6378388D, 6356911.94612795),
     UTMid = new Coordenada(datum, xid, yid, 0, huso_id, true);
				           				            
     Lon_si = (UTMsi.getLon() * Math.PI) / 180.0D;
     Lat_si = (UTMsi.getLat() * Math.PI) / 180.0D;
     Lon_id = (UTMid.getLon() * Math.PI) / 180.0D;
     Lat_id = (UTMid.getLat() * Math.PI) / 180.0D;         
   }
   
   public Distancia(double lat_si, double lon_si, double lat_id, double lon_id, Datum datum)
   {   
     UTMsi = new Coordenada(datum, lon_si, lat_si, 0);
     UTMid = new Coordenada(datum, lon_id, lat_id, 0);           
     Lon_si = (lon_si* Math.PI) / 180.0D;
     Lat_si = (lat_si* Math.PI) / 180.0D;
     Lon_id = (lon_id* Math.PI) / 180.0D;
     Lat_id = (lat_id* Math.PI) / 180.0D;         	
   }
     
     
   public double CalcularDistancia() 
   {
     double Radio = 6372795.0D; // (en metros) 
     double IncLon, IncAngular;
     //System.out.println("lat_si = " + Lat_si + "  lon_si = " + Lon_si + "    lat_id = " + Lat_id + "  lon_id = " + Lon_id);  
     
     if (UTMsi.getHuso() == UTMid.getHuso())
     {    
     	// Aplico Pitagoras: 	
     	double hipo, cateto1, cateto2;
     	cateto1 = UTMsi.getY() - UTMid.getY();
     	cateto1 *=cateto1;
     	cateto2 = UTMsi.getX() - UTMid.getX();
     	cateto2 *=cateto2;
     	hipo = Math.sqrt(cateto1 + cateto2);
     	//System.out.println("hipotenusa="+hipo);
     	return hipo;
     } else
     {
        IncLon = Lon_id - Lon_si;
        double sinLatsi, sinLatid, cosLatsi, cosLatid, sinIncLon, cosIncLon;
        sinLatsi = Math.sin(Lat_si);
        sinLatid = Math.sin(Lat_id);
        cosLatsi = Math.cos(Lat_si);
        cosLatid = Math.cos(Lat_id);
        sinIncLon = Math.sin(IncLon);
        cosIncLon = Math.cos(IncLon);		      
	
        double A, B, C, D, E, F, G, H, I;
        A = sinLatsi * sinLatid;
        //System.out.println("A="+A);
        B = cosLatsi * cosLatid * cosIncLon;
        //System.out.println("B="+B);
        C = cosLatid * sinIncLon;
        //System.out.println("C="+C);
        D = cosLatsi * sinLatid;
        //System.out.println("D="+D);
        E = sinLatsi * cosLatid * cosIncLon;
        //System.out.println("E="+E);
        F = D - E;
        //System.out.println("F="+F);
        G = (C*C) + (F*F);
        //System.out.println("G="+G);
        H = Math.sqrt(G);
        //System.out.println("H="+H);
        I = H / (A + B);
        //System.out.println("I="+I);
        IncAngular = Math.atan(I);
     
        double distancia = Radio * IncAngular;
     
        //System.out.println("distancia=" + distancia); 
        return distancia;    
     }
  }
  
  public double CalcularAncho()
   {
     double Radio = 6372795.0D; // (en metros) 
     double IncLon, IncAngular;
     //System.out.println("lat_si = " + Lat_si + "  lon_si = " + Lon_si + "    lat_id = " + Lat_id + "  lon_id = " + Lon_id); 
     
     if (UTMsi.getHuso() == UTMid.getHuso())
     {     	
     	double ancho;
     	ancho = Math.abs(UTMsi.getX() - UTMid.getX());
     	//System.out.println("ancho="+ancho);
     	return ancho;
     } else
     { 
        IncLon = Lon_id - Lon_si;
        double sinLatsi, sinLatid, cosLatsi, cosLatid, sinIncLon, cosIncLon;
        sinLatsi = Math.sin(Lat_si);
        sinLatid = Math.sin(Lat_si);
        cosLatsi = Math.cos(Lat_si);
        cosLatid = Math.cos(Lat_id);
        sinIncLon = Math.sin(IncLon);
        cosIncLon = Math.cos(IncLon);		      
	
        double A, B, C, D, E, F, G, H, I;
        A = sinLatsi * sinLatid;
        //System.out.println("A="+A);
        B = cosLatsi * cosLatid * cosIncLon;
        //System.out.println("B="+B);
        C = cosLatid * sinIncLon;
        //System.out.println("C="+C);
        D = cosLatsi * sinLatid;
        //System.out.println("D="+D);
        E = sinLatsi * cosLatid * cosIncLon;
        //System.out.println("E="+E);
        F = D - E;
        //System.out.println("F="+F);
        G = (C*C) + (F*F);
        //System.out.println("G="+G);
        H = Math.sqrt(G);
        //System.out.println("H="+H);
        I = H / (A + B);
        //System.out.println("I="+I);
        IncAngular = Math.atan(I);
     
        double distancia = Radio * IncAngular;
     
        //System.out.println("ancho" + distancia); 
        return distancia;    
     }
  }
  
   public double CalcularAlto()
   {
     //double Radio = 6372795.0D; // (en metros)      
     //System.out.println("lat_si = " + Lat_si + "  lon_si = " + Lon_si + "    lat_id = " + Lat_id + "  lon_id = " + Lon_id);
       	
     double alto;
     alto = Math.abs(UTMsi.getY() - UTMid.getY());
     //System.out.println("alto="+alto);
     return alto;
           
     /*  
     IncLon = 0;
     double sinLatsi, sinLatid, cosLatsi, cosLatid, sinIncLon, cosIncLon;
     sinLatsi = Math.sin(Lat_si);
     sinLatid = Math.sin(Lat_id);
     cosLatsi = Math.cos(Lat_si);
     cosLatid = Math.cos(Lat_id);
     sinIncLon = Math.sin(IncLon);
     cosIncLon = Math.cos(IncLon);		      
	
     double A, B, C, D, E, F, G, H, I;
     A = sinLatsi * sinLatid;
     //System.out.println("A="+A);
     B = cosLatsi * cosLatid * cosIncLon;
     //System.out.println("B="+B);
     C = cosLatid * sinIncLon;
     //System.out.println("C="+C);
     D = cosLatsi * sinLatid;
     //System.out.println("D="+D);
     E = sinLatsi * cosLatid * cosIncLon;
     //System.out.println("E="+E);
     F = D - E;
     //System.out.println("F="+F);
     G = (C*C) + (F*F);
     //System.out.println("G="+G);
     H = Math.sqrt(G);
     //System.out.println("H="+H);
     I = H / (A + B);
     //System.out.println("I="+I);
     IncAngular = Math.atan(I);
     
     double distancia = Radio * IncAngular;
     
     //System.out.println("ancho" + distancia); 
     return distancia;  
     */  
  }
   
  public static void main (String[] args)
  {
    Distancia dist;
    if (args.length == 4)
       dist = new Distancia(Double.valueOf(args[0]).doubleValue(),
                            Double.valueOf(args[1]).doubleValue(),
                            Double.valueOf(args[2]).doubleValue(),
                            Double.valueOf(args[3]).doubleValue(),
                            Datum.datumED50);
    else 
       dist = new Distancia(Double.valueOf(args[0]).doubleValue(),
                            Double.valueOf(args[1]).doubleValue(),
                            Byte.valueOf(args[2]).byteValue(),
                            Double.valueOf(args[3]).doubleValue(),
                            Double.valueOf(args[4]).doubleValue(),
                            Byte.valueOf(args[5]).byteValue(),
                            Datum.datumED50);	
    
    System.out.println("distancia = " + dist.CalcularDistancia());
    System.out.println("ALTO = " + dist.CalcularAlto());
    System.out.println("ANCHO = " + dist.CalcularAncho());
  }
 }
  