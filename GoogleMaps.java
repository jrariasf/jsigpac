
/*

Información en:
http://carzel.wordpress.com/2007/01/29/hackeando-a-google-maps/
http://www.tupila.com/blog/?p=75
http://web.media.mit.edu/~nvawter/projects/googlemaps/index.html
http://intepid.com/2005-07-17/21.50/

Más:
YahooMaps:  http://foro.elhacker.net/index.php?topic=181129.msg863357;topicseen 
http://carzel.wordpress.com/2007/01/29/hackeando-a-google-maps/
http://www.aaaasoft.com/gmid/
http://www.geoskating.com/gmap/gmap-wms.js



http://kh3.google.es/kh?n=404&v=21&t=tqsrsssttrtrtttt    http://kh0.google.es/kh?n=404&v=21&t=tqsrsssttrtrttts


http://kh0.google.es/kh?n=404&v=21&t=tqsrsssttrtsqqqq    http://kh1.google.es/kh?n=404&v=21&t=tqsrsssttrtsqqqr



Para dar una referencia de googlemaps con longitud y latitud: (vista mapa)
http://maps.google.com/maps?ll=39.877222,116.406944&spn=0.3,0.3&q=39.877222,116.406944
Lo mismo pero vista satélite:
http://maps.google.com/maps?ll=39.877222,116.406944&spn=0.3,0.3&t=k&q=39.877222,116.406944
Lo mismo pero vista híbrido:
http://maps.google.com/maps?ll=39.877222,116.406944&spn=0.3,0.3&t=h&q=39.877222,116.406944

*/

public class GoogleMaps {
	
 static String GetQuadtreeAddress(double lon, double lat)
 {    
    int digits = 18; // how many digits precision
    // now convert to normalized square coordinates
    // use standard equations to map into mercator projection
    double x = (180.0D + lon) / 360.0D;
    double y = -lat * Math.PI / 180.0D; // convert to radians
    y = 0.5 * Math.log((1+Math.sin(y)) / (1 - Math.sin(y)));
    y *= 1.0D/(2.0D * Math.PI); // scale factor from radians to normalized
    y += 0.5D; // and make y range from 0 - 1
    String quad = "t"; // google addresses start with t
    String lookup = "qrts"; // tl tr bl br
    int inicio=0;
    System.out.println("floor(-2.67)="+Math.floor(-2.67));
    System.out.println("floor(-2.2)="+Math.floor(-2.2));
    
    System.out.println("x="+x+"   y="+y);
    while (digits-- > 0) // (post-decrement)
    {
      // make sure we only look at fractional part
      System.out.println("x="+x+"   y="+y+"  Floor(x)="+Math.floor(x)+"   Floor(y)="+Math.floor(y));
      x -= Math.floor(x);
      y -= Math.floor(y);
      //System.out.println("quad="+quad+"  lookup="+lookup+"  ("+(x >= 0.5 ? 1 : 0)+","+(y >= 0.5 ? 2 : 0)+")");
      inicio = (x >= 0.5 ? 1 : 0) + (y >= 0.5 ? 2 : 0);
      quad = quad + lookup.substring(inicio, inicio+1);
      // now descend into that square
      x *= 2;
      y *= 2;
    }
    return quad;
 }
 
 static String getSatURL(int zoom, double x, double y)
 {

    double wx, wy, cx, cy;
    String tid;
    int i;

    cx = 0;
    cy = 0;
    wx = 180;
    wy = 180;
    tid = "t";

    for (i=1; i<(zoom-1); i++)
    {
      System.out.println("x="+x+"  cx="+cx+"  y="+y+"  cy="+cy);
      if (x >= cx && y >= cy)
      {
         tid = tid + "r";
         cx = cx + (wx / 2);
         cy = cy + (wy / 2);
      } else if (x >= cx && y < cy)
      {
         tid = tid + "s";
         cx = cx + (wx / 2);
         cy = cy - (wy / 2);
      } else if (x < cx && y < cy)
      {
         tid = tid + "t";
         cx = cx - (wx / 2);
         cy = cy - (wy / 2);
      } else
      {
         tid = tid + "q";
         cx = cx - (wx / 2);
         cy = cy + (wy / 2);
      }
      wx = wx / 2;
      wy = wy / 2;
    }
    
    return "http://kh.google.com/kh?v=21&t=" + tid;
 }

 public static void main (String[] args) 
 {
    System.out.println("arg[0] = " + args[0]);
    System.out.println("arg[1] = " + args[1]);
    System.out.println("quad = " + GetQuadtreeAddress(Double.valueOf(args[0]).doubleValue(), Double.valueOf(args[1]).doubleValue()));
    System.out.println("getSatURL = " + getSatURL(18, Double.valueOf(args[0]).doubleValue(), Double.valueOf(args[1]).doubleValue()));
 }
}