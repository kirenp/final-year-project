/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swingserver;
import java.net.*;
import java.io.*;
public class URLConnDemo
{
   public static void main(String [] args)
   {
      try
      {
         URL url = new URL("http://en.wikipedia.org/wiki/The Matrix");
         URLConnection urlConnection = url.openConnection();
         HttpURLConnection connection = null;
         if(urlConnection instanceof HttpURLConnection)
         {
            connection = (HttpURLConnection) urlConnection;
         }
         else
         {
            System.out.println("Please enter an HTTP URL.");
            return;
         }
         BufferedReader in = new BufferedReader(
         new InputStreamReader(connection.getInputStream()));
         String urlString = "";
         String current;
         String match="From Wikipedia, the free encyclopedia";
         FileWriter fw=new FileWriter("D:\\No1.txt");
         
         while((current = in.readLine()) != null)
         {
            urlString += current;
         }
         fw.write(urlString);
         fw.close();
         urlString="";
         System.out.println(urlString);
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}