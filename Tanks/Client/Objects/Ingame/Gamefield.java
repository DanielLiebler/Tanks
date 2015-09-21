package Tanks.Client.Objects.Ingame;

import Tanks.Client.Log;
import java.util.*;
import java.io.*;

public class Gamefield{
  private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
  private ArrayList<float[]> starts = new ArrayList<float[]>();
  
  // Ende Attribute
  
  
  // Anfang Methoden
  
  public Gamefield(String filename){
    loadMap(filename);
  }
  public Obstacle getObstacle(int index) {
    return obstacles.get(index);
  }
  public void addObstacle(Obstacle o){
    obstacles.add(o);
  }   
  public void removeObstacle(Obstacle o){
    obstacles.remove(o);
  }
  public void removeObstacle(int index){
    obstacles.remove(index);
  }
  public int getObstacleLength(){
    return obstacles.size();
  }
  
  public boolean loadMap(String filename){       
    Log.write("loadMap('Saves\\" + filename + "') of Gamefield " + this, Log.LOGDEPTH_High); 
    try{
      File file = new File("Saves\\" + filename);
      Scanner scan = new Scanner(file);
      Scanner value;                   
      
      scan.nextLine();
      scan.nextLine();
      scan.nextLine();
      scan.nextLine();
      String line;
      float startx, starty;
      boolean start = true;
      while (start) {
        line = scan.nextLine();
        if(line.contains(">>")){
          start = false;      
        }else{
          value = new Scanner(line);  
          value.useDelimiter(",");
          startx = Float.parseFloat(value.next()); 
          starty = Float.parseFloat(value.next()); 
          starts.add(new float[]{startx, starty});   
        }
      }
      
      Obstacle newObs = new Obstacle();
      while(scan.hasNext()){
        String aktLine = scan.nextLine();
        if(aktLine.contains(">>")){
          obstacles.add(newObs);
          newObs = new Obstacle();
        }else{
          value = new Scanner(aktLine);   
          value.useDelimiter(",");
          if(value.hasNext()){
            float x = Float.parseFloat(value.next()); 
            if(value.hasNext()){
              float y = Float.parseFloat(value.next());
              newObs.addPoint(new float[]{x, y});
            }
          }
        }
      }
      obstacles.add(newObs);
      
      return true;
    }catch(Exception e){
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      Log.write(errors.toString(), true);
      return false;
    }
  }
  // Ende Methoden
}