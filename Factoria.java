import java.util.concurrent.*;


class Factoria extends Thread
{
   int filas, columnas, max_threads, th_activos, faltan;
   OpcionesMapa opciones;
   Monitor monitor;
   int activos;
   int[][] estado;
   DatosDescarga[][] datosDescarga;
   ExecutorService executor;
   String hayError=null; // Vale null si no hay error y si hay error, pues contiene el mensaje de error
   	
 public Factoria(OpcionesMapa _opciones, Monitor _monitor, int _max_threads)
 {
    opciones = _opciones;
    filas = opciones.n_filas;
    columnas = opciones.n_col;
    faltan = filas*columnas;
    monitor = _monitor;    
    max_threads = _max_threads;
    th_activos = 0;
    datosDescarga = new DatosDescarga[filas][columnas];  
    hayError = null;
 }	
 	
 public boolean PutDescarga(String nombre_fichero, String dir, int i, int j)
                    //OpcionesMapa op, String server, int I, int J, Monitor monitor)
                    // server = JSigpac.servidor.Nombre();
 {
    // Si ya hay un error, retornamos false:
    if (hayError != null)
       return false;
       
    try {
       datosDescarga[i][j] = new DatosDescarga(nombre_fichero, dir); 	 	
    } catch (ArrayIndexOutOfBoundsException aiob) {
       /*
       if (monitor != null && monitor.misigpac != null)
          monitor.misigpac.ImprimirLinea("Asegurese que el numero de filas y columnas a descargar coincide en el panel de DESCARGA y el panel de ENSAMBLAR");
       else
          System.err.println("Asegurese que el numero de filas y columnas a descargar coincide en el panel de DESCARGA y el panel de ENSAMBLAR");            
       */
       hayError = "Asegurese que el numero de filas y columnas a descargar coincide en el panel de DESCARGA y el panel de ENSAMBLAR";
       return false;
    }
    
    return true;
 }

 public void Finalizar()
 {
    if (executor != null)
       executor.shutdownNow();	
    //System.out.println("_mm_ Realizado el shutdown");
 }
 
 /*
    Lo que debe hacer el método "run" es lanzar los threads que se encarguen de hacer las descargas.
    - A la vez que se lanza un thread para hacer la descarga, se lanza otro thread que controla el momento
     en que finaliza la descarga para así actualizar las variables oportunas en la clase Factoria (quitarlas de Monitor).
    - La clase Monitor habría que quitarla y meterla aquí.
    - Para controlar si un thread sigue vivo se hace así: (pasarle el thread al Thread que espera la muerte)
    
             while (ariel.isAlive())
             {                 
               try {
          	   sleep(500);
               } catch(InterruptedException ie) {
         	   //System.out.println("Dentro del bucle isAlive 1");
               }
             }
     
     
     Mirar estos links:
     http://www.sunncity.com/Tutorial_Java/partTwo/multithread.html#synchron
     http://www.google.es/search?hl=es&rlz=1B3DVFC_esES243ES246&q=multithread+java+synchronized&btnG=Buscar&meta=
     http://www.ibm.com/developerworks/java/library/j-threads1.html
     http://www.deitel.com/articles/java_tutorials/20051126/JavaMultithreading_Tutorial_Part1.html
 */ 
 public void run()
 {
 	
    executor = Executors.newFixedThreadPool (max_threads);
    for (int i=0; i<filas; i++)
      for (int j=0; j<columnas; j++)
      {      	  
      	  if (datosDescarga[i][j] != null)
             executor.execute (new Descargar(datosDescarga[i][j].nombre_fichero, 
                                             datosDescarga[i][j].dir, opciones, 
                                             JSigpac.servidor.Nombre(), i, j, monitor));
          
      } 
    executor.shutdown();	
 }

}
 
 
	
	
	