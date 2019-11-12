


/*
  Paso de coordenadas UTM a geograficas:
  http://recursos.gabrielortiz.com/index.asp?Info=058b

*/

public class Coordenada {
 Datum d;

 double X, X_ori;
 double Y, Y_ori;
 double Z, Z_ori;
 byte zona, zona_ori;
 boolean hemisferio_N;

 double longitud;
 double latitud;
 double elevacion;

 double AX;
 double AY;
 double AZ;
 double Rx;
 double Ry;
 double Rz;
 double e;

 public static int PENINSULA = 0;
 public static int NOROESTE  = 1;
 public static int BALEARES  = 2;

 // Las siguientes variables estan definidas como estaticas
 // en la clase Datum:
 //Datum DatumED50 = Datum(6378388D, 6356911.94612795);
 //Datum DatumWGS84 = Datum(6378137D, 6356752.31424518);

 // UTM:
 public Coordenada (Datum _d, double x, double y, double z,
		    byte _zona, boolean _hemisferio_N)
 {
   d = _d;
   X = x;
   Y = y;
   Z = z;  // Altura ortometrica, sobre NMMA
   zona = _zona;
   zona_ori = _zona;
   hemisferio_N = _hemisferio_N;
   GenerarCoordenadasGeograficas();
 }

 // Gdc:
 public Coordenada (Datum _d, double lon, double lat, double ele)
 {
   d = _d;
   longitud = lon;
   latitud = lat;
   elevacion = ele;
   //JSigpac.ImprimirLinea("_cc_\t Se crea Coordenada lon="+lon+"  lat="+lat);
   GenerarCoordenadasUTM();
 }

 double getX() { return X; }
 double getY() { return Y; }
 double getZ() { return Z; }
 double getX_ori() { return X_ori; }
 double getY_ori() { return Y_ori; }
 double getZ_ori() { return Z_ori; }
 byte getHuso() { return zona; }
 byte getHuso_ori() { return zona_ori; }
 double getLon() { return longitud; }
 double getLat() { return latitud; }
 int getGrados(double aux) { return (int)aux; }
 int getGradosAbs(double aux) { return (int)Math.abs(aux); }
 int getMinutos(double aux) { return (int)((Math.abs(aux)-Math.abs(getGrados(aux)))*60); }
 double getSegundos(double aux) { return ((Math.abs((aux-getGrados(aux))*60)-getMinutos(aux))*60); }

 static double getDecimal(int grados, int minutos, double segundos, String EW)
 {
   double decimal;
   if (EW.equals("W")) //grados < 0)
      decimal = -(grados) - ((segundos/60.0D + minutos) / 60.0D);
   else
      decimal = grados + ((segundos/60.0D + minutos) / 60.0D);
   return decimal;
 }
 
 static double getDecimal(int grados, int minutos, double segundos)
 {
   double decimal;
   if (grados < 0)
      decimal = grados - ((segundos/60.0D + minutos) / 60.0D);
   else
      decimal = grados + ((segundos/60.0D + minutos) / 60.0D);
   return decimal;
 }

 // Decimos si es hemisferio NORTE de acuerdo a lo dicho al definir la coordenada UTM:
 boolean EsHemisferioNorte() { return hemisferio_N; }
 // Decimos si es hemisferio SUR teniendo en cuenta la latitud de las Canarias:
 // Esto es un error porque las Canarias estan en el hemisferio Norte !!!
 boolean EsHemisferioSur()
 {
   if (getLat() < 29.46)
      return true;
   else
      return false;
 }

 public void GenerarCoordenadasGeograficas()
 {
   double aux;
   double cuadcosfi;
   //double ea = Math.sqrt((d.a*d.a)-(d.b*d.b)) / d.a;   // excentricidad
   double eb = Math.sqrt((d.a*d.a)-(d.b*d.b)) / d.b;   // segunda excentricidad
   double eb2 = eb*eb;     // segunda excentricidad al cuadrado
   //System.out.println("\teb2="+eb2);
   double c = (d.a*d.a)/d.b;     // radio polar de curvatura
   //double apla = (d.a-d.b)/d.a;  // aplanamiento

   double x = X - 500000;                    // coordenada X sin retranqueo
   double y = hemisferio_N ? Y : Y-10000000; // coordenada Y sin retranqueo
   
   double lambda = (zona * 6) - 183;
   double fi = y / (6366197.724*0.9996);
   //System.out.println("\tfi="+fi);
   cuadcosfi = Math.cos(fi);
   cuadcosfi = cuadcosfi*cuadcosfi;
   aux = 1 + (eb2*cuadcosfi);
   double v = (c / Math.sqrt(aux)) * 0.9996;
   //System.out.println("\tv="+v);
   double a = x/v;
   double A1 = Math.sin(2*fi);
   double A2 = A1 * cuadcosfi;
   double J2 = fi + (A1/2D);
   //System.out.println("\tJ2="+J2);
   double J4 = ((3*J2)+A2) / 4D;
   //System.out.println("\tJ4="+J4);
   double J6 = ((5*J4) + (A2*cuadcosfi)) / 3D;
   //System.out.println("\tJ6="+J6);
   double alfa = (3D/4D)*eb2;
   //System.out.println("\talfa="+alfa);
   double beta = (5D/3D)*alfa*alfa;
   //System.out.println("\tbeta="+beta);
   double i = (35D/27D)*alfa*alfa*alfa;
   //System.out.println("\ti="+i);
   double B = 0.9996*c*( fi-(alfa*J2)+(beta*J4)-(i*J6));
   //System.out.println("\tB="+B);
   double b = (y-B)/v;
   //System.out.println("\tb="+b);
   double raton = (eb2*a*a*cuadcosfi) / 2D;
   //System.out.println("\traton="+raton);
   double epsilon = a * (1-(raton/3));
   //System.out.println("\tepsilon="+epsilon);
   double n = (b*(1-raton)) + fi;
   //System.out.println("\tn="+n);
   //System.out.println("\te="+Math.exp(1));
   double seno = (Math.exp(epsilon) - Math.exp(-epsilon)) / 2D;
   //System.out.println("\tseno="+seno);
   double I_lambda = Math.atan(seno / Math.cos(n)); 
   //System.out.println("\tI_lambda="+I_lambda);
   double t = Math.atan(Math.cos(I_lambda)*Math.tan(n));
   //System.out.println("\tt="+t);

   // Calculo final de las coordenadas geograficas:
   longitud = ((I_lambda/Math.PI)*180) + lambda;
   latitud = fi + ((1+(eb2*cuadcosfi)-
		      ((3/2)*eb2*Math.sin(fi)*Math.cos(fi)*(t-fi)))*(t-fi));
   // Lo paso a grados decimales:
   latitud = (latitud*180) / Math.PI;
   int grados = (int)latitud;
   int minutos = (int)((latitud-grados)*60);
   @SuppressWarnings("unused")
   double segundos = (((latitud-grados)*60) - minutos)*60;
   //System.out.println("Geograficas:"+longitud + "   "+latitud);
   //System.out.println("    latitud "+grados+"¦ "+Math.abs(minutos)+"\" "+Math.abs(segundos)+"'");
   //longitud = (longitud*180) / Math.PI;
   grados = (int)longitud;
   minutos = (int)((longitud-grados)*60);
   segundos = (((longitud-grados)*60) - minutos)*60;
   //System.out.println("   longitud "+grados+"¦ "+Math.abs(minutos)+"\" "+Math.abs(segundos)+"'");
   
   int hs = (int)((longitud/6D)+31);
   if (hs != zona)
   {
      //JSigpac.ImprimirLinea("_cc_ huso=" + zona + "   pero deberia ser " + hs);
      //JSigpac.ImprimirLinea("_cc_ Antes de generar de nuevo: X="+X+"  Y="+Y+"  Z="+Z+"  zona="+zona+"  lon="+longitud+"  lat="+latitud);

      // Recalculo los valores de X e Y pero salvando los que tenemos:
      X_ori = X;
      Y_ori = Y;
      Z_ori = Y;
      zona_ori = zona;      
      GenerarCoordenadasUTM();
      
      //JSigpac.ImprimirLinea("_cc_ Después de generar de nuevo: X="+X+"  Y="+Y+"  Z="+Z+"  zona="+zona+"  lon="+longitud+"  lat="+latitud);
      
      //Coordenada nueva = new Coordenada(d, longitud, latitud, Z);
      //JSigpac.ImprimirLinea("_cc_ Nueva X="+X+"  Y="+Y+"  Z="+Z+"   Xn="+nueva.getX()+"  Yn="+nueva.getY()+"  Zn="+nueva.getZ() + "  huso="+nueva.getHuso());
      /*
      zona = hs;
      X = nueva.getX();
      Y = nueva.getY();
      Z = nueva.getZ();
      */
   }
 }

 public void GenerarCoordenadasUTM()
 {
   double aux;
   double cuadcoslat;
   //double ea = Math.sqrt((d.a*d.a)-(d.b*d.b)) / d.a;   // excentricidad
   double eb = Math.sqrt((d.a*d.a)-(d.b*d.b)) / d.b;   // segunda excentricidad
   double eb2 = eb*eb;     // segunda excentricidad al cuadrado
   //JSigpac.ImprimirLinea("_cc_\teb2="+eb2);
   double c = (d.a*d.a)/d.b;     // radio polar de curvatura

   double lonrad = (longitud * Math.PI) / 180D;
   //JSigpac.ImprimirLinea("_cc_\tlonrad="+lonrad);
   double latrad = (latitud * Math.PI) / 180D;
   //JSigpac.ImprimirLinea("_cc_\tlatrad="+latrad);
   int huso = (int)((longitud/6D)+31);
   //JSigpac.ImprimirLinea("_cc_\thuso="+huso);
   int lambda = (huso*6)-183;  // meridiano central del huso
   //JSigpac.ImprimirLinea("_cc_\tlambda="+lambda);
   double I_lambda = lonrad - (lambda*Math.PI/180D);
   //JSigpac.ImprimirLinea("_cc_\tIncremento lambda="+I_lambda);

   // Aplicamos las ecuaciones de Coticchia-Surace:
   double A = Math.cos(latrad) * Math.sin(I_lambda);
   //JSigpac.ImprimirLinea("_cc_\tA="+A);
   double epsilon = 0.5*Math.log((1+A)/(1-A));
   //JSigpac.ImprimirLinea("_cc_\tepsilon="+epsilon);
   double n = Math.atan(Math.tan(latrad)/Math.cos(I_lambda)) - latrad;
   //JSigpac.ImprimirLinea("_cc_\tn="+n);
   cuadcoslat = Math.cos(latrad);
   cuadcoslat = cuadcoslat*cuadcoslat;
   aux = 1 + (eb2*cuadcoslat);
   double v = (c / Math.sqrt(aux)) * 0.9996;
   //JSigpac.ImprimirLinea("_cc_\tv="+v);
   double raton = (eb2/2D) * epsilon * epsilon * cuadcoslat;
   double A1 = Math.sin(2*latrad);
   double A2 = A1 * cuadcoslat;
   double J2 = latrad + (A1/2D);
   //JSigpac.ImprimirLinea("_cc_\tJ2="+J2);
   double J4 = ((3*J2) + A2) / 4D;
   //JSigpac.ImprimirLinea("_cc_\tJ4="+J4);
   double J6 = ((5*J4) + (A2*cuadcoslat)) / 3D;
   //JSigpac.ImprimirLinea("_cc_\tJ6="+J6);

   double alfa = (3D/4D)*eb2;
   //JSigpac.ImprimirLinea("_cc_\talfa="+alfa);
   double beta = (5D/3D)*(alfa*alfa);
   //JSigpac.ImprimirLinea("_cc_\tbeta="+beta);
   double y = (35D/27D)*(alfa*alfa*alfa);
   //JSigpac.ImprimirLinea("_cc_\ty="+y);

   double B = 0.9996*c*(latrad - (alfa*J2) + (beta*J4) - (y*J6));
   //JSigpac.ImprimirLinea("_cc_\tB="+B);

   //Calculo final de las coordenadas UTM:
   X = epsilon * v * (1+(raton/3D)) + 500000;   
   Y = n * v * (1+raton) + B;
   Z = elevacion;   
   //JSigpac.ImprimirLinea("_cc_\tX="+X+ "  Y="+Y);
   if (Y<0)
   {
      Y = Y + 10000000; // Suponemos que estabamos trabajando con coordenadas
			// del hemisferio sur.
      hemisferio_N = false;
   } else
      hemisferio_N = true;
   zona = (byte)huso;
 }

 Coordenada CambioDeDatum(Datum dst)
 {
   double h = 0;  /* Altitud elipsoidal: h=H+No */
   // H es la altura ortometrica (altura sobre el nivel del mar: NMMA)
   // No es la ondulacion del geoide o altura geoidal
   double H=Z; // Coordenada Z de las UTM
   double No=0;
   double aux, bux, cux, dux;
   // Pasamos a geocentricas las coordenadas geodesicas o geograficas:
   double Xoc,Xc, Yoc,Yc, Zoc,Zc;
   //System.out.println("a="+d.a+ "   b="+d.b);
   // Radio de curvatura en la vertical del punto (N):
   
   // Si el datum "dst" coincide con el actual, pues entonces no hace falta calcular nada:
   if (d.EsIgual(dst))
      return this;
   
   aux = d.a*d.a;
   bux = d.b*d.b;
   double latrad = (latitud * Math.PI) / 180D;
   double lonrad = (longitud * Math.PI) / 180D;
   double cosfi = Math.cos(latrad);
   double cos2fi = cosfi*cosfi;
   double senfi = Math.sin(latrad);
   double sen2fi = senfi*senfi;
   dux = Math.sqrt( (aux*cos2fi)+(bux*sen2fi) );
   double N = aux / dux;
   //System.out.println("N="+N);

   // h por lo menos es igual a N (a falta de saber el valor de H);
   h = H+No;
   Xoc = (N+h) * cosfi * Math.cos(lonrad);
   Yoc = (N+h) * cosfi * Math.sin(lonrad);
   Zoc = ( ((bux/aux)*N) + h) * Math.sin(latrad);
   //System.out.println("Xoc="+Xoc+"  Yoc="+Yoc+"  Zoc="+Zoc);

   ParametrosTransf();
   //System.out.println("AX="+AX+"  AY="+AY+"  AZ="+AZ+"  Rx="+Rx+"  Ry="+Ry+"  Rz="+Rz+"  e="+e);
   Xc = AX+((1+e)*(Xoc+(Rz*Yoc)-(Ry*Zoc)));
   Yc = AY+((1+e)*(((-Rz)*Xoc)+Yoc+(Rx*Zoc)));
   Zc = AZ+((1+e)*((Ry*Xoc)-(Rx*Yoc)+Zoc));
   //System.out.println("Xc="+Xc+"  Yc="+Yc+"  Zc="+Zc);

   // Ahora ya podemos empezar el paso de geocentricas a geodesicas
   // en el datum destino:
   double ea = Math.sqrt((dst.a*dst.a)-(dst.b*dst.b)) / dst.a; // excentricidad
   double eb = Math.sqrt((dst.a*dst.a)-(dst.b*dst.b)) / dst.b; // segunda excentricidad
   double eb2 = eb*eb; // segunda excentricidad al cuadrado (en datum destino)

   double p; // Segundo valor de conversion
   p = Math.sqrt((Xc*Xc) + (Yc*Yc));
   //System.out.println("p="+p);
   double tita; // Factor de conversion
   tita = Math.atan((Zc*dst.a)/(p*dst.b));
   //System.out.println("tita="+tita);

   double latitud, longitud;
   aux = Math.sin(tita);
   aux = aux*aux*aux;
   bux = Math.cos(tita);
   bux = bux*bux*bux;
   latitud = Math.atan((Zc+(eb2*dst.b*aux))/(p-(ea*ea*dst.a*bux)));

   longitud = Math.atan(Yc/Xc);
   //System.out.println("latitud="+latitud+"   longitud="+longitud);

   aux = dst.a*dst.a;
   bux = dst.b*dst.b;
   cux = Math.cos(latitud);
   cux = cux*cux;
   dux = Math.sin(latitud);
   dux = dux*dux;
   N = aux / Math.sqrt((aux*cux) + (bux*dux));
   //System.out.println("N="+N);

   h = (p/Math.cos(latitud)) - N;
   //System.out.println("h="+h);

   // Pasamos la latitud y la longitud a grados sexagesimales:
   latitud = (latitud/Math.PI)*180D;
   longitud = (longitud/Math.PI)*180D;
   //System.out.println("latitud="+latitud+"   longitud="+longitud);

   Coordenada nc = new Coordenada(dst, longitud, latitud, h); //_xx_XX_
   nc.GenerarCoordenadasUTM();
   //System.out.println("X="+nc.X+"  Y="+nc.Y+"  Z="+nc.Z+"  zona="+nc.zona+
   //		      "  hemisferio Norte="+nc.hemisferio_N);

   return nc;

 }

 int DameSector()
 {
   if (getLon() < -4.5 && getLat() > 41.5)
      return NOROESTE;
   else if (getLon() > 1 && getLat() < 40.15)
      return BALEARES;
   else
      return PENINSULA; 
 }


 void ParametrosTransf()
 {
  int i;
  /* Los siguientes valores valdrian para WGS84--->ED50               */
  /*                        PENINSULA  NOROESTE   BALEARES            */ 
  /*                        ==============================            */
   double ParamTrans[][] = {{ 131.032,  178.383,  181.4609} ,   /* AX */
      			    { 100.251,   83.172,   90.2931} ,   /* AY */
			    { 163.354,  221.293,  187.1902} ,   /* AZ */
			    { -1.2438,   0.5401,    0.1435} ,   /* Rx */
			    { -0.0195,  -0.5319,    0.4922} ,   /* Ry */
			    { -1.1436,  -0.1263,   -0.3935} ,   /* Rz */
			    {   -9.39,    -21.2,    -17.57} };  /* e  */

   /* Dependiendo de las coordenadas del punto, se aplicaran
      los parametros de transformacion de la Peninsula, Noroeste
      o Baleares */

   /* Algunos signos cambian dependiendo de si la transformacion es
      de ED50--->WGS84  o de WGS84--->ED50   */
   double signo = 1.0D;  // Por defecto, suponemos que es de WGS84 ---> ED50
   if (d.a == 6378388D)  // Si el datum origen es ED50
      signo = -1.0D;

   i = DameSector();

   AX = ParamTrans[0][i] * signo;
   AY = ParamTrans[1][i] * signo;
   AZ = ParamTrans[2][i] * signo;
   Rx = ((((ParamTrans[3][i] / 60D) / 60D)*Math.PI)/180D) * signo;
   Ry = ((((ParamTrans[4][i] / 60D) / 60D)*Math.PI)/180D) * signo;
   Rz = ((((ParamTrans[5][i] / 60D) / 60D)*Math.PI)/180D) * signo;
   e  = (ParamTrans[6][i] * signo) / 1.0E6;

 }

 // La siguiente función devuelve "W" o "E" dependiendo de si es 
 // una coordenada con longitud negativa o no:
 String E_W()
 {   
   if (getLon() < 0) // Si la longitud es negativa, OESTE
      return "W";
   else
      return "E";   
 } 

 public static void main (String[] args)
 {
   //System.out.println("Inicio...");
   //Coordenada utm = new Coordenada(new Datum(6378388, 6356911.946130),
   //			    435157.59, 4815453.64, 0, (byte)30, true);
   //Coordenada utm = new Coordenada(new Datum(6378388, 6356911.946130),
   //			    448500759, 4377580.93, 0, (byte)30, true);
   //System.out.println("UTM a geograficas: x="+utm.X+"  y="+utm.Y);
   //utm.GenerarCoordenadasGeograficas();
   //System.out.println("latitud="+utm.latitud+"  longitud="+utm.longitud);

   //Coordenada geo = new Coordenada(new Datum(6378388.00, 6356911.946130),
   //					     utm.longitud, utm.latitud, 0);
   //geo.GenerarCoordenadasUTM();
   //System.out.println("X="+geo.X+"  Y="+geo.Y+"  Z="+geo.Z+"  zona="+geo.zona+
   //		      "  hemisferio Norte="+geo.hemisferio_N);

/* // De WGS84 a ED50: 
   Coordenada utm = new Coordenada(new Datum(6378137D, 6356752.31424518),
			   448500.79, 4377580.93, 771.46, (byte)30, true);
   Coordenada res;
   res = utm.CambioDeDatum(new Datum(6378388D, 6356911.94612795));
*/
   // De ED50 a WGS84:
   Coordenada utm = new Coordenada(new Datum(6378388D, 6356911.94612795),
			   481742, 4770800, 700, (byte)29, true);
   //System.out.println("lon="+utm.getLon()+"      lat="+utm.getLat());
   //System.out.println(utm.getGrados(utm.getLon())+"¦ "+utm.getMinutos(utm.getLon())+"' "+utm.getSegundos(utm.getLon())+"\"   "+utm.getGrados(utm.getLat())+"¦ "+utm.getMinutos(utm.getLat())+"' "+utm.getSegundos(utm.getLat())+"\"");
   @SuppressWarnings("unused")
   Coordenada res;
   res = utm.CambioDeDatum(new Datum(6378137D, 6356752.31424518));

   //System.out.println("Coordenadas en Datum destino: X="+res.X+"  Y="+res.Y);
   //System.out.println("lon="+res.getLon()+"      lat="+res.getLat());
   //System.out.println(res.getGrados(res.getLon())+"¦ "+res.getMinutos(res.getLon())+"' "+res.getSegundos(res.getLon())+"\"   "+res.getGrados(res.getLat())+"¦ "+res.getMinutos(res.getLat())+"' "+res.getSegundos(res.getLat())+"\"");
				   
 }
}
