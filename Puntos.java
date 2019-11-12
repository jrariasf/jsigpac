
class Puntos {
  Datum datum;
  double[] latitud;
  double[] longitud;
  int indice=0;
  int maximo=0;
  
  Puntos(int max)
  {
     maximo = max;
     latitud = new double[max];
     longitud = new double[max];
     indice = 0;
  }
  
  void EstablecerDatum(Datum _datum)
  {
     datum = _datum;	
  }
  
  void PutPunto(double lat, double lon) // throws PuntosOutOfBoundsException
  {
     if (indice >= maximo)
     {
        //throw new PuntosOutOfBoundsException("Superado el numero maximo de puntos del fichero (" + Fichero.MAXIMO_NUM_PUNTOS + ")");
        double[] clat = new double[2*maximo];
        double[] clon = new double[2*maximo];
        for(int f=0; f<maximo; f++)
        {
           clat[f] = latitud[f];
           clon[f] = longitud[f];
        }
        maximo = 2*maximo;
        latitud = clat;
        longitud = clon;
     }
     latitud[indice] = lat;
     longitud[indice] = lon;
     indice++;
  }
  
  int NumPuntos()
  {
      return indice;
  }
}

class PuntosOutOfBoundsException extends Exception
{ 
   public PuntosOutOfBoundsException()
   {
      super();
   }
   
   public PuntosOutOfBoundsException(String mensaje)
   {
      super(mensaje);
   }
}

