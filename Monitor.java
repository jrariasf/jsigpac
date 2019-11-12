import java.io.*;

class Monitor {

  private int permiso = 1;
  private int filas, columnas, compresionJPEG, mapasV, mapasH; 
  private boolean[][] descargado; // Para llevar cuenta de los cuadrantes que faltan por descargar
  private boolean[][] descarga_correcta; // Para controlar si los cuadrantes se han descargado correctamente
  boolean ensamblar_al_acabar = false;
  boolean imprimir = false; // Para sacar trazas relacionadas con los threads
  public Thread ultimoThread; 
  JSigpac misigpac=null;
  OpcionesMapa opciones;
  
  /*
  public Monitor(int FIL, int COL, int inicial, boolean estado, int ultFila, int ultCol)
  {
     imprimir = true;
     filas = FIL;
     columnas = COL;
     // Estas dos variables son necesarias para saber si se trata del ultimo
     // cuadrante que se descarga para que el proceso de ensamblado automatico 
     // no se inicie sin tener realmente el cuadrante:
     //ultimaFila = ultFila;
     //ultimaColumna = ultCol;
     descargado = new boolean[filas][columnas];
     descarga_correcta = new boolean[filas][columnas];
     InicializarArray(estado);
     setPermiso(inicial);
  }
  */
  public Monitor()
  {
     compresionJPEG = 70;
     mapasV = 1;
     mapasH = 1;
  }
  
  public Monitor(int FIL, int COL, int inicial, boolean estado)
  {
     this();
     imprimir = false;
     filas = FIL;
     columnas = COL;     
     descargado = new boolean[filas][columnas];
     descarga_correcta = new boolean[filas][columnas];
     ensamblar_al_acabar = false;
     InicializarArray(estado);
     InicializarTrazas();
     setPermiso(inicial);   
     ultimoThread = null;  
     opciones = null;
  }
  
  public Monitor(int FIL, int COL, int inicial)
  {
     this(FIL, COL, inicial, false);	
  }    
  
  public Monitor(int FIL, int COL, int inicial, JSigpac elsigpac)
  {
     this(FIL, COL, inicial, false);
     misigpac = elsigpac;
     if (misigpac != null)
     {     	     
        misigpac.DarValorMaximoBarraDeProgreso(FIL*COL);
        misigpac.DarValorBarraDeProgreso(0);
     }
  }
  
  public Monitor(int FIL, int COL, int inicial, JSigpac elsigpac, int compresion, int _mapasV, int _mapasH)
  {
     this(FIL, COL, inicial, elsigpac);
     compresionJPEG = compresion;
     mapasV = _mapasV;
     mapasH = _mapasH;
  }
  
  public Monitor(int FIL, int COL, int inicial, JSigpac elsigpac, int compresion)
  {
     this(FIL, COL, inicial, elsigpac, compresion, 1, 1); 	
  }

  public Monitor(OpcionesMapa _opciones, JSigpac elsigpac)
  {
     this(_opciones.n_filas, _opciones.n_col, 1, elsigpac, _opciones.compresion,
          _opciones.mapasVertical, _opciones.mapasHorizontal);
     opciones = _opciones;
  }

  public boolean InicializarTrazas()
  {
     File fi;
     fi = new File("tracear_Monitor");
     //JSigpac.ImprimirLinea("directorio de tracear: " + fi.getAbsolutePath());
     if (fi.exists())
        imprimir = true;
     else
        imprimir = false;
     //JSigpac.ImprimirLinea("tracear_Descargar="+imprimir);
     return imprimir; 	 	
  }  
 
  public void Imprimir(String linea)
  {
    if (imprimir)
       System.out.println("-TRAZA_Monitor- " + linea);
  }
  
  public int getFilas()
  {
     return filas;
  }
  
  public int getColumnas()
  {
     return columnas;
  }
  
  public int getCompresion()
  {
     return compresionJPEG;
  }
  
  public int getMapasV()
  {
     return mapasV;
  }
  
  public int getMapasH()
  {
     return mapasH;
  }
  
  public void InicializarArray(boolean estado)
  {
     for (int i=0; i<filas; i++)
       for (int j=0; j<columnas; j++)
       {
          descargado[i][j] = estado; // Si es false, el cuadrante correspondiente sera descargado.	
          descarga_correcta[i][j] = estado;
       }          
  }
  
  // Esta funcion determina si un cuadrante necesita ser descargado dependiendo del
  // fichero de track o de ruta que se le pase:  _hh_
  public void ReinicializarArrays()
  {
    int con;
    Coordenada p;
    double x, y;
    int m, n;
    
    if (JSigpac.ficheroTrkRte != null)
    {
       InicializarArray(true);
       for (con=0; con < JSigpac.ficheroTrkRte.coordenadas.NumPuntos(); con++)
       {
       	   p = new Coordenada(JSigpac.ficheroTrkRte.coordenadas.datum,
       	                      JSigpac.ficheroTrkRte.coordenadas.longitud[con],
       	                      JSigpac.ficheroTrkRte.coordenadas.latitud[con], 0 );
       	   if (!opciones.datumJSigpac.miString().equals(JSigpac.ficheroTrkRte.coordenadas.datum.miString()))       	   
       	      p = p.CambioDeDatum(opciones.datumJSigpac);	
       	   
       	   x = p.getX();
       	   y = p.getY();
       	   
       	   n = (int)((x - opciones.qx) / opciones.anchura);
       	   m = (int)(((opciones.qy - y) * Mapa.FACTOR) / opciones.anchura);
       	   
       	   if (n<0 || m<0 || n>=columnas || m>=filas)
       	   {
       	      JSigpac.Traza("_hh_ ERRRORRRRR:  m="+m+"   n="+n);
       	      JSigpac.Traza("_hh_ m="+	(((opciones.qy - y) * Mapa.FACTOR) / opciones.anchura) + "   n="+  ((x - opciones.qx) / opciones.anchura));
       	   } else
       	   {
       	      descargado[m][n] = false;
       	      descarga_correcta[m][n] = false;       	   
       	   }
       }	    	
    }
  	
  }
  
  // Devuelve "true" si el cuadrante especificado en (i,j) hay que descargarlo:
  boolean HayQueDescargar(int i, int j)
  {
     return !descargado[i][j];	
  }
  
  public void CambiarValor(int i, int j, boolean estado)
  {
     CambiarValor(i, j, estado, false);
  }
  
  // Esta funcion podria dar problemas porque esta siendo llamada sin sincronizacion alguna desde la clase JSigpac.
  public void CambiarValor(int i, int j, boolean estado, boolean error)
  {
     boolean abandonar = false;
     int token;
     
     Imprimir("CambiarValor(i="+i+" , j="+j+")  estado="+estado);
     descargado[i][j] = estado;
     descarga_correcta[i][j] = !error;
     if (!JSigpac.hayThreads)
     {
        token = (filas*columnas) + 1;  // Por si a caso, lo inicializamos al valor máximo.
        for (int m=0; m<filas; m++)
        {
           if (abandonar)
               break;
           for (int n=0; n<columnas; n++)
           {
              if (descargado[m][n] == false)
              {
                 Imprimir("Antes del BREAK m="+m+"  n="+n);              
                 token = (m*columnas) + n + 1;             
                 abandonar = true;
                 break;
             }  
           }
        }
        setPermiso(token);
     } else if (JSigpac.entornoGrafico)
        misigpac.IncrementarBarraDeProgreso(1, true);
  }
  
  public synchronized boolean PedirPermiso(int id_thread)
  {
     Imprimir(id_thread + "- Estamos en PedirPermiso   permiso="+permiso);
     
     /* Para Multithread real comentar todo esto y devolver directamente true */
     while (id_thread != permiso)
     {
     	if (JSigpac.finalizar)
     	   return false;
     	try {
     	   //Imprimir(id_thread + "- Entra en el wait  permiso="+permiso);
           wait(); // Se sale cuando el id_thread coincida con el "permiso".
        } catch( InterruptedException e ) {
           Imprimir(id_thread + "- Sale del wait");
           ;
        }
     }
     // Devolvemos el control al thread:
     Imprimir(id_thread + "- Devolvemos el control al thread");
     //_xx_ notify();  // Ya notificaremos cuando modifiquemos el valor de "permiso" al llamar a "PasarTestigo".
     
     /* Comentar hasta aquí para multithread real */
     
     return true;  	
  }
  
  public void setPermiso(int valor)
  {     
     permiso = valor;
     Imprimir("  permiso=" + permiso);
     if (misigpac != null)     
     	misigpac.DarValorBarraDeProgreso(permiso);
     
  }
      
  public synchronized void PasarTestigo(int i, int j, boolean error)      
  {     
     if (error)
        Imprimir("PasarTestigo ERROR al descargar cuadrante (i="+i+" , j="+j+")");
     else 
        Imprimir("PasarTestigo(i="+i+" , j="+j+")");
     
     CambiarValor(i, j, true, error);
     
     Imprimir("Despues del BREAK. Hacemos el notify y salimos de PasarTestigo");	
     notifyAll();
  }
  
  
  public synchronized void AccesoZonaComun(int i, int j, boolean error)
  {
     if (error)
        Imprimir("PasarTestigo ERROR al descargar cuadrante (i="+i+" , j="+j+")");
     else 
        Imprimir("PasarTestigo(i="+i+" , j="+j+")");
     
     CambiarValor(i, j, true, error);
  }

  public boolean DescargaCompletaOK()
  {
     for (int i=0; i<filas; i++)
       for (int j=0; j<columnas; j++)      
           if (descarga_correcta[i][j] == false)
              return false;
    
     return true;      	
  }
  
  public boolean DescargaFinalizada()
  {
     for (int i=0; i<filas; i++)
       for (int j=0; j<columnas; j++)      
           if (descargado[i][j] == false)
              return false;
    
     return true;    
  }
  
  public void PonerEnsamblarAlAcabar(boolean valor)
  {
     ensamblar_al_acabar = valor;
  }
  
  public boolean EnsamblarAlAcabar()
  {
     return ensamblar_al_acabar;
  }
  
  @SuppressWarnings("unused")
  private synchronized void Incrementar()
  {
     permiso++;
     Imprimir("Se incrementa permiso = "+permiso);
     notifyAll();     
  } 	
  
  void EstablecerOpcionesMapa(OpcionesMapa _opsMapa)
  {
     opciones = _opsMapa;
  }
  
}