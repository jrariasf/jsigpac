import java.io.*;
import java.net.*;

class ConectarConwebIBP extends Thread
{
  IndiceDureza ibp=null;
  String mod;
  boolean informe;
  String exsistingFileName;
  boolean abrirNavegador;
  
  // Este constructor es utilizado cuando se llama desde linea de comandos:
  public ConectarConwebIBP(String track, String _mod, boolean _informe, boolean _abrirNavegador)
  {
    this((IndiceDureza)null, _mod, _informe);
    abrirNavegador = _abrirNavegador;
    exsistingFileName = track;	
    Conectar();
  }
  
  // Este constructor se utiliza cuando se utiliza el JSigpac desde el entorno grafico:
  public ConectarConwebIBP(IndiceDureza _ibp, String _mod, boolean _informe)
  {
    ibp = _ibp;
    mod = _mod;
    abrirNavegador = true;
    informe = _informe;
    if (ibp != null)
       exsistingFileName = ibp.fichero_entrada;
  }
  
  public void EscribirTexto(String mensaje)
  {
    if (ibp == null)
       JSigpac.ImprimirLinea(mensaje);       
    else
       ibp.EscribirTexto(mensaje);
  }
  
  public void run()
  {  
    Conectar();
  }
	
  public void Conectar()
  {
    HttpURLConnection conn = null;
    BufferedReader br = null;
    DataOutputStream dos = null;
    //DataInputStream inStream = null;
    BufferedReader inStream = null;
    
    InputStream is = null;
    OutputStream os = null;
    boolean ret = false;
    String StrMessage = "";
    //String exsistingFileName = args[0];
    //String exsistingFileName = ibp.fichero_entrada;
    File fichero;
    String lineEnd = "\r\n";
    String dosGuiones = "--";
    String boundary = "----------a7ffab3496f6d6";

    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1*1024*1024;
    String responseFromServer = "";
    String urlString;
    
    EscribirTexto("___________________________________________");
    if (informe)    
    {
       urlString = "http://www.ibpindex.com/esp/ibpweb2_e.asp?CFO=FFFFFF&CTE=000000&CTA=BDBDBD";
       EscribirTexto("Generando informe IBP. Espere un momento...");
    } else
    {
       urlString = "http://www.ibpindex.com/esp/ibpresponse.asp";
       EscribirTexto("Obteniendo el indice IBP. Puede tardar un poco...");
    }
     
    //JSigpac.Traza("ConectarConwebIBP  isEventDispatchThread=" + javax.swing.SwingUtilities.isEventDispatchThread() + "   currentThread="+Thread.currentThread());
    JSigpac.Traza("mod=" + mod + "  informe=" + informe + "   fichero=" + exsistingFileName);
     
    try
    {
      //------------------ CLIENT REQUEST
      fichero = new File(exsistingFileName);
      JSigpac.Traza("Longitud fichero: " + fichero.length());
      FileInputStream fileInputStream = new FileInputStream( fichero );
      // open a URL connection to the Servlet 
      URL url = new URL(urlString);
      // Open a HTTP connection to the URL
      conn = (HttpURLConnection) url.openConnection();
      // Allow Inputs
      conn.setDoInput(true);
      // Allow Outputs
      conn.setDoOutput(true);  
      // Don't use a cached copy.
      conn.setUseCaches(false);
      // Use a post method.
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Connection", "Keep-Alive");   
      conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
      dos = new DataOutputStream( conn.getOutputStream() );
      
      dos.writeBytes(dosGuiones + boundary + lineEnd);
      dos.writeBytes("Content-Disposition: form-data; name=\"MOD\";" + lineEnd + lineEnd + mod);      
      dos.writeBytes(lineEnd);
      
      if (informe)
      {     
      	 dos.writeBytes(dosGuiones + boundary + lineEnd);
         dos.writeBytes("Content-Disposition: form-data; name=\"UDO\";" + lineEnd + lineEnd);      
         dos.writeBytes(lineEnd);
   
         dos.writeBytes(dosGuiones + boundary + lineEnd);
         dos.writeBytes("Content-Disposition: form-data; name=\"VOL\";" + lineEnd + lineEnd + "formar_e.asp");      
         dos.writeBytes(lineEnd);      
      } else
      {
      	 dos.writeBytes(dosGuiones + boundary + lineEnd);
         dos.writeBytes("Content-Disposition: form-data; name=\"UDO\";" + lineEnd + lineEnd + "jSIGPAC");      
         dos.writeBytes(lineEnd); 
      }
      
      dos.writeBytes(dosGuiones + boundary + lineEnd);
      dos.writeBytes("Content-Disposition: form-data; name=\"fichero\";" + " filename=\"" + 
                     fichero.getName() +"\"" + lineEnd + 
                     "Content-Type: text/plain;" + lineEnd);
                     //URLEncoder.encode(fichero.getName(), "UTF-8") +"\"" + lineEnd + 
                     //"Content-Type: text/plain;" + lineEnd);
      dos.writeBytes(lineEnd);         

      // create a buffer of maximum size
      bytesAvailable = fileInputStream.available();
      bufferSize = Math.min(bytesAvailable, maxBufferSize);
      buffer = new byte[bufferSize];
      
      EscribirTexto("Mandando datos...");
      // read file and write it into form...
      bytesRead = fileInputStream.read(buffer, 0, bufferSize);      
      while (bytesRead > 0)
      {
      	JSigpac.Traza("bytesRead=" + bytesRead + "   bytesAvailable=" + bytesAvailable);  
        dos.write(buffer, 0, bufferSize);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
      }    

      dos.writeBytes(lineEnd);
      dos.writeBytes(dosGuiones + boundary + dosGuiones + lineEnd);
      JSigpac.Traza("Fichero ENVIADO");
      // close streams
      fileInputStream.close();
      dos.flush();
      dos.close();
    } catch (MalformedURLException ex) {
       EscribirTexto("Error mandando datos: "+ex);
       return; // false;
    } catch (IOException ioe) {
       EscribirTexto("Error mandando datos: "+ioe);
       return; // false;
    }


    //------------------ read the SERVER RESPONSE
    
    try
    {
      //inStream = new DataInputStream ( conn.getInputStream() );
      
      inStream = new BufferedReader ( new InputStreamReader(conn.getInputStream()) );
      String str;
      if (informe)
      {      	 
         PrintWriter jsi = new PrintWriter(new BufferedWriter(new FileWriter(exsistingFileName + ".html")));         
         boolean primerPaquete = true;
         while (( str = inStream.readLine()) != null)
         {
           if (primerPaquete)
           {
              EscribirTexto("Recibiendo datos...");
              EscribirTexto("Espere un poco...");
              primerPaquete = false;
           }
           //System.out.println("Server response is: "+str);           
           jsi.println(str);
           jsi.println("");
         }
         jsi.close();
         EscribirTexto("Se ha guardado el informe con el IBP en:");
         EscribirTexto("\t\"" + exsistingFileName + ".html\"");
         
         if (abrirNavegador)
         {
            // Abrimos el navegador con el informe:
            //String comando = JSigpac.PathFireFox() + " \"" + exsistingFileName + ".html\"";
            //JSigpac.Traza("comando runtime: " + comando);
            try {       	         	
       	       Runtime.getRuntime().exec(JSigpac.PathFireFox() + " \"" + exsistingFileName + ".html\"");    
       	       //Runtime.getRuntime().exec(comando);   	
       	    } catch (IOException ioe1) {
       	       try {
       	          Runtime.getRuntime().exec(JSigpac.PathExplorer() + " \"" + exsistingFileName + ".html\"");
       	       } catch (IOException ioe2) { 
       	          EscribirTexto("Navegador no encontrado: Es necesario configurar la variable de entorno JSIGPAC_NAVEGADOR.");
       	          EscribirTexto("Se intentó acceder a: "+ exsistingFileName + ".html");       	    	       	       
       	       }
       	    }
         }
                  
      } else // Sólo tenemos que leer el valor numerico con el IBP:
      {      	
      	 str = inStream.readLine();
      	 if (str != null && str.trim().length() > 0)
      	 {
      	    if (ibp != null)
      	       ibp.valorIBP.setText(str);      	    
      	    EscribirTexto("El IBP de ese track es: " + str);      	    
      	 } else
      	 {
      	    EscribirTexto("Se ha producido un error en el servidor. Puede que el formato del fichero no sea correcto.");
      	    /*
      	    PrintWriter jsi = new PrintWriter(new BufferedWriter(new FileWriter(exsistingFileName + ".html")));
      	    while (( str = inStream.readLine()) != null)
            {
               //System.out.println("Server response is: "+str);           
               jsi.println(str);
               jsi.println("");
            }
            jsi.close();
      	    EscribirTexto("Para saber el error, mire el fichero \"" + exsistingFileName + ".html\"");
      	    */
      	 }      	
      }
      
      inStream.close();   
    } catch (IOException ioex) {
       EscribirTexto("From (ServerResponse): "+ioex);
       return; // false;
    }
    
    return; // true;
  }
}