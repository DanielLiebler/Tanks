package Tanks.Client.Graphic;

import Tanks.Client.Log;
import java.util.*;
import java.io.*;          
import java.awt.*;
import java.awt.image.*;        
import javax.imageio.*;

public class TextureManager{
  private static ArrayList<BufferedImage> imgList = new ArrayList<BufferedImage>();
  private static ArrayList<int[]> imgScales = new ArrayList<int[]>();  
  private static ArrayList<String> imgNames = new ArrayList<String>();
  
  public static BufferedImage getTexture(String texName, int x, int y, boolean load){
    int indx = imgNames.indexOf(texName);
    if (indx >= 0) {
      if(load){    
        return loadTexture(texName, x, y);
      }else{
        if(x == imgScales.get(indx)[0] && y == imgScales.get(indx)[1]){
          return imgList.get(indx);
        }else{
          return loadTexture(texName, x, y);
        }           
      }
    }else{
      return loadTexture(texName, x, y);
    }
  }
  private static BufferedImage loadTexture(String texName, int x, int y){
    try {       
      if(texName.charAt(0) == '\\'){
        
      }else{
        
        BufferedImage texture = ImageIO.read(new File("tex/" + texName));      
        
        
        Log.write("Textur(tex/" + texName + ") geladen", Log.LOGDEPTH_Middle);
        imgList.add(scaleTexture(texture, x, y));
        imgScales.add(new int[]{x, y});
        imgNames.add(texName);
        return imgList.get(imgList.size()-1);   
      }
    } catch (IOException e) {
      Log.write("Fehler beim Laden von tex/" + texName + ": " + e.toString(), true);         
    }  
    return null;
  }
  private static BufferedImage scaleTexture(BufferedImage tex, int x, int y){
    Log.write("rescaling Texture(" + tex.toString() + ") to " + x + "/" + y, Log.LOGDEPTH_High);
    BufferedImage retTexture;
    Image img = tex.getScaledInstance(x, y, Image.SCALE_FAST);
    retTexture = new BufferedImage(x, y ,BufferedImage.TYPE_INT_ARGB_PRE);
    retTexture.getGraphics().drawImage(img , 0, 0, null);  
    return retTexture;
  }
}