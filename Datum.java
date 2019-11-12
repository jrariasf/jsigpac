

class Datum {
 double a;
 double b;

 public static final Datum datumED50 = new Datum(6378388D, 6356911.94612795);
 public static final Datum datumWGS84 = new Datum(6378137D, 6356752.31424518);

 public Datum(double _a, double _b)
 {
   a = _a;
   b = _b;
 }
 
 String miString()
 {
   if (a == 6378388D && b == 6356911.94612795)
      return "ED50";
   else if (a == 6378137D && b == 6356752.31424518)
      return "WGS84";
   else 
      return "DATUM DESCONOCIDO";
  }
  
  public boolean EsIgual(Datum otro)
  {
     return (a == otro.a) && (b == otro.b) ? true : false;
  }
}

