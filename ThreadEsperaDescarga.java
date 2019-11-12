import java.io.*;


class ThreadEsperaDescarga extends Thread
{	
 Mapa miMapa;
 Ensamblador miEnsamblador;
 Monitor miMonitor;
 int token;
 boolean imprimir = false; // Para sacar trazas relacionadas con los threads 
 
 public void IniThEspera(Mapa mapa, Ensamblador ensamblador, Monitor m, int permiso)
 {   	   
   imprimir = false;
   miMapa = mapa;
   miEnsamblador = ensamblador;
   token = permiso;
   miMonitor = m;
   Imprimir("===========================================================");
 }
 
 // Para DESCARGAR:
 public ThreadEsperaDescarga(ThreadGroup thg, Mapa mapa, Monitor m, int permiso)
 {    
   super(thg, "ThreadEsperaDescarga-"+permiso);
   IniThEspera(mapa, null, m, permiso);
 }
 
 public ThreadEsperaDescarga(ThreadGroup thg, Mapa mapa, Monitor m)
 {    
   super(thg, "ThreadEsperaDescarga-");
   IniThEspera(mapa, null, m, (m.getColumnas()*m.getFilas()) + 1);
   setName("ThreadEsperaDescarga-"+token);
 }
 
 public ThreadEsperaDescarga(Mapa mapa, Monitor m, int permiso)
 {
   IniThEspera(mapa, null, m, permiso);
 }
 
 public ThreadEsperaDescarga(Mapa mapa, Monitor m)
 {
   this(mapa, m,  (m.getColumnas()*m.getFilas()) + 1);  
 }


 // Para ENSAMBLAR:
 public ThreadEsperaDescarga(ThreadGroup thg, Ensamblador ensamblador, Monitor m, int permiso)
 {
   super(thg, "ThreadEsperaEnsamble-"+permiso);
   IniThEspera(null, ensamblador, m, permiso);
 }
 
 public ThreadEsperaDescarga(ThreadGroup thg, Ensamblador ensamblador, Monitor m)
 {
   super(thg, "ThreadEsperaEnsamble-");
   IniThEspera(null, ensamblador, m, (m.getColumnas()*m.getFilas()) + 1);
   setName("ThreadEsperaEnsamble-"+token);
 }
 
 public ThreadEsperaDescarga(Ensamblador ensamblador, Monitor m, int permiso)
 { 
   IniThEspera(null, ensamblador, m, permiso); 	  
 }
 
 public ThreadEsperaDescarga(Ensamblador ensamblador, Monitor m)
 {
   this(ensamblador, m,  (m.getColumnas()*m.getFilas()) + 1);     
 }
 
 public boolean InicializarTrazas()
 {
    File fi;
    fi = new File("tracear_ThreadEsperaDescarga");
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
      System.out.println(linea);
 }
 
 public void run()
 {

   //int id_thread = (i*col) + (j+1);
   //System.out.println("_cc_ run() de ThreadEsperaDescarga");
   
   Imprimir(token + "- ThreadEsperaDescarga ******************" + toString());  
   if (!JSigpac.hayThreads)
   {
      if (miMonitor.PedirPermiso(token) == false)
      {
         Imprimir("Finaliza ThreadEsperaDescarga-"+token);
         return; // Se acaba el thread
      }
      Imprimir(token + "- He vuelto de PedirPermiso  " + toString());
   } else 
   {  
      while (!miMonitor.DescargaFinalizada())
      {
        try {
           if (JSigpac.finalizar == true)
           {
              System.out.println("\tParando el proceso...");
              JSigpac.factoria.Finalizar();
              return;
           }	
           sleep(1000);
        } catch(InterruptedException ie) {
      	   //System.out.println("Dentro del bucle isAlive 1");
        }
      }
      //System.out.println("_mm_ miMonitor.DescargaFinalizada() ha devuelto true");
      if (JSigpac.finalizar == true)
      {
      	 // Puede que hayamos recibido una orden de abortar el proceso (con el botón STOP):
      	 //System.out.println("_mm_  JSigpac.finalizar == true");
         return;
      } //else
        // System.out.println("_mm_  JSigpac.finalizar == false");             
   }
    
   boolean procesoCorrecto = true;  
   if (miMapa != null)
   {
      // Si todo ha ido bien o, aunque no haya ido bien, hay que ensamblar al acabar, pues seguimos:
      if (miMonitor.DescargaCompletaOK() || miMonitor.EnsamblarAlAcabar()) // _kkmail_
      {
         miMapa.CalibrarMapa();
         JSigpac.ImprimirLinea("\n\t- Descarga finalizada. ", false);
         if ((miMonitor.getFilas() == 1 && miMonitor.getColumnas() == 1))
        	 JSigpac.ImprimirLinea("-");
         else
         {
            if (miMonitor.EnsamblarAlAcabar())
            {
               JSigpac.ImprimirLinea("-");
               //JSigpac.ImprimirLinea("_xx_ getCompresion=" + miMonitor.getCompresion());
               //miMapa.DespuesDeDescargar(miMonitor.getFilas(), miMonitor.getColumnas(), 
               //                          miMonitor.getCompresion(), miMonitor.getMapasV(), miMonitor.getMapasH());
               miMapa.DespuesDeDescargar(miMonitor.opciones);
            }
            else 
            {
               JSigpac.ImprimirLinea("\tAhora deberia ensamblar -");
               JSigpac.ImprimirLinea("\t(Recuerda que son " + miMonitor.getFilas() + " filas y " + miMonitor.getColumnas() + " columnas)");
            }
            JSigpac.ImprimirLinea(""); 
        }
      } else
      {
      	 procesoCorrecto = false;
         JSigpac.ImprimirLinea("\tSe ha producido algun error");
      }
      
      JSigpac.ImprimirLinea("");
      if (procesoCorrecto)
         miMapa.PonerSTOP(false);
      else
         miMapa.PonerSTOP(false, "Descarga incompleta");
   } else if (miEnsamblador != null)
   {     
      if (miMonitor.ultimoThread == null)    
         Imprimir("ultimoThread == null");
      
      // Esperamos a que la ultima descarga finalice antes de intentar ensamblar:  
      if (!JSigpac.hayThreads) 
      {
         while (miMonitor.ultimoThread != null && miMonitor.ultimoThread.isAlive())
         {
            Imprimir("Antes del try isalive 2");	   
            try {
               sleep(1500);
            } catch(InterruptedException ie) {
               System.out.println("Dentro del bucle isAlive 2");
            }
         }
         Imprimir("_cc_ Estamos fuera del bucle isAlive 2");            
      }
      miEnsamblador.DespuesDeDescargar();	   	   
   }         
   
   Imprimir(token + "- Fin de " + toString());
   
   //System.out.println("_cc_ - Fin de " + toString());
 }
} 
 
 