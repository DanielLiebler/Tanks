package Tanks.Client.Graphic;

import java.awt.image.*;
import java.util.*;


public class FogHole{
  public static double alphaThickness = 255/0.01;
  
  // Anfang Attribute
  protected float[] center;
  protected float radius;
  protected static BufferedImage bufferedFog;
  // Ende Attribute
  
  public FogHole(float[] center, float radius){
    this.center = center;
    this.radius = radius;
  }
  
  public static BufferedImage getFog(BufferedImage fog, ArrayList<FogHole> holes, boolean changed, boolean sharp){
    if( changed ){
      @SuppressWarnings("unchecked")
      ArrayList<FogHole> myholes = (ArrayList<FogHole>) holes.clone();
      ColorModel cm = fog.getColorModel();
      boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
      WritableRaster raster = fog.copyData(null);
      BufferedImage finalImage =  new BufferedImage(cm, raster, isAlphaPremultiplied, null);
      
      Picture:
      for (int y = 0; y < fog.getHeight(); y++) {
        Line:
        for (int x = 0; x < fog.getWidth(); x++) {
          
          int alpha = 0;
          Holes:
          for (int i = 0; i < myholes.size(); i++) {
            Hole:
            if (y < (myholes.get(i).getCenter()[1]*fog.getHeight()+myholes.get(i).getRadius()*fog.getWidth())) { 
              if ((x > (myholes.get(i).getCenter()[0]-myholes.get(i).getRadius())*fog.getWidth()) && (x < (myholes.get(i).getCenter()[0]+myholes.get(i).getRadius())*fog.getWidth()) && (y > (myholes.get(i).getCenter()[1]*fog.getHeight()-myholes.get(i).getRadius()*fog.getWidth()))) {
                float disx = (myholes.get(i).getCenter()[0])-(float)( ( (double)x ) / ( (double)fog.getWidth()  )  );
                float disy = (float)(((myholes.get(i).getCenter()[1]*(double)fog.getHeight())-(double)y) / ( (double)fog.getWidth()  ));
                float dis = (float)Math.sqrt(disx*disx+disy*disy);  
                if (dis < myholes.get(i).getRadius()) {
                  if (sharp) {
                    alpha = 255;
                  }else{
                    alpha += (myholes.get(i).getRadius()-dis)*alphaThickness;
                    if (alpha > 255){
                      alpha = 255;
                      break Holes;
                    }   
                  }
                } // end of if  
              }
            }else{
              myholes.remove(i);
              if (myholes.size() == 0) break Picture;
              i--;
            } // end of Hole
          } // end of Holes
          
          if (alpha == 255){
            finalImage.setRGB(x,y,0);
          }else if(alpha != 0 && !sharp){           
            int FogARGB = fog.getRGB(x,y);
            int b = ((FogARGB)    &0xFF);
            int g = ((FogARGB>>8) &0xFF);
            int r = ((FogARGB>>16)&0xFF);
            
            finalImage.setRGB(x,y,(int)(((255-alpha) << 24) | (r << 16) | (g << 8) | b));
          }
        }
      }
      bufferedFog = finalImage;
      return finalImage;   
    }else{
      return bufferedFog;
    }
  }
  
  //Anfang Getter/Setter
  public float[] getCenter() {
    return center;
  }
  
  public void setCenter(float[] center) {
    this.center = center;
  }
  
  public float getRadius() {
    return radius;
  }
  
  public void setRadius(float radius) {
    this.radius = radius;
  }
  
  // Ende Getter/Setter
}