package Tanks.Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


//Allgemeine Logger-Klasse (zum Loggen von allem, was so anf‰llt)


public class Log {                          
  public static final int LOGDEPTH_NONE = 0;
  public static final int LOGDEPTH_Middle = 1;
  public static final int LOGDEPTH_High = 2; 
  public static final int LOGDEPTH_Irreal_High = 3;
  
  public static int logDepth = 0;
  
  public static boolean logenabled = true;
  private static FileWriter fw;  
  private static boolean initialized = false;
  
  public static void init(boolean overwrite) {
    System.out.println("---Log: init");
    
    try {
      File file = new File("log.txt");
      
      if (!file.exists()) {
        file.createNewFile();
      }
      
      if (overwrite) { 
        fw = new FileWriter(file);
      } else {
        fw = new FileWriter(file, true);
      }
      initialized = true;
    } catch (IOException e) {
      System.out.println("Fehler beim ˆffnen der Logfile: " + e.toString());
    }
  }
  public static void enable(boolean state) {
    if(!initialized){
      init(true);
    }
    
    System.out.println("---Log: Logfile enabled = " + state);
    logenabled = state;
  }
  
  public static void write(String text, int logdepth) {
    if(logDepth >= logdepth) write(text, false);
  }
  public static void write(String text, Boolean fehler) {
    if(!initialized){
      init(true);
    }
    
    if (!fehler) {
      System.out.println("---Log: " + text);
    } else {
      System.out.println("!!!Log: " + text);
    }
    
    if (logenabled) {
      try {                    
        java.util.Date now = new java.util.Date();    
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH.mm.ss dd.MM.yyyy");
        if (!fehler) {
          fw.write("---" + sdf.format(now) + "--Log: " + text + "\r\n");
        } else {
          fw.write("!!!" + sdf.format(now) + "--Log: " + text + "\r\n");
        }
        fw.flush();
      } catch (IOException e) {
        System.out.println("Fehler beim schreiben der Logfile: " + e.toString());
      }
    }
  }
  public static void clos() {
    if (logenabled) {
      try {
        System.out.println("---Log: close");
        fw.close(); 
      } catch (IOException e) {
        System.out.println("Fehler beim schlieﬂen der Logfile: " + e.toString());
      }  
    }
  }
}
