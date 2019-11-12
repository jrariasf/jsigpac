import java.util.regex.*;

public class MiPattern
{
  
 MiPattern(String[] args)
 {
   Pattern p = Pattern.compile(args[0]);
   Matcher m;

   System.out.print("Patron <"+args[0]+">   Cadena: "+args[1]);
   m = p.matcher(args[1]);
   System.out.println("  ---->>>>  "+m.matches());
   System.out.println("pattern():"+p.pattern());
   //if (m.matches())
   {
      String[] trozos;
      trozos = p.split(args[1]);
      for (int i=0; i<trozos.length; i++)
	  System.out.println(trozos[i]);
    }
   
 }

 public static void main (String[] args)
 {
   // Recibe dos parametros: patron y cadena:
   @SuppressWarnings("unused")
   MiPattern mp = new MiPattern(args);

 }
}
