package Tanks.Client.Objects.Gui;

import Tanks.Client.*;
import java.awt.image.*;
import java.util.*;
import java.awt.font.*;
import java.awt.*;
import java.awt.geom.*;

public class GUIElement{
  private boolean hasText;
  private ArrayList<String> text;
  private BufferedImage myTexture;
  private float x,y,szx,szy;
  
  public GUIElement(float x, float y, float szx, float szy, boolean hasText, ArrayList<String> text){
    this.x = x;   
    this.y = y;
    this.szx = szx;
    this.szy = szy;
    this.hasText = hasText;
    this.text = text;
    this.myTexture = new BufferedImage((int)(szx* Main.getMyWindow().getWidth()), (int)(szy* Main.getMyWindow().getHeight()), BufferedImage.TYPE_INT_ARGB_PRE);
  }
  public GUIElement(float x, float y, float szx, float szy, boolean hasText, ArrayList<String> text, BufferedImage myTexture){
    this(x,y,szx,szy,hasText, text);
    this.myTexture = myTexture;
  }
  
  public float[] getPos(){
    return new float[]{x,y};
  }
  public float[] getSize(){
    return new float[]{szx,szy};
  }
  
  public BufferedImage getTexture(){
    BufferedImage retTexture = myTexture;
    
    
    Font f1 = new Font("", Font.PLAIN, 100);
    int yOffsetPerLine = 0;
    for (int i = 0; i<text.size(); i++) {
      FontRenderContext frc = retTexture.createGraphics().getFontRenderContext();
      Rectangle2D rect = f1.getStringBounds(text.get(i), frc);
      yOffsetPerLine = (int)(rect.getHeight());
      if (rect.getWidth() > this.szx*Main.getMyWindow().getWidth() || rect.getHeight()*text.size() > this.szy*Main.getMyWindow().getHeight()) {
        f1 = new Font("", Font.PLAIN, f1.getSize()-1);
        i--;
      }
    }     
    for (int i = 0; i<text.size(); i++ ) {  
      FontRenderContext frc = retTexture.createGraphics().getFontRenderContext();
      Rectangle2D rect = f1.getStringBounds(text.get(i), frc);
      int textX = (int)((szx*Main.getMyWindow().getWidth()-rect.getWidth())/2), textY = (int)((szy*Main.getMyWindow().getHeight() - rect.getHeight()*text.size())/2); 
      Graphics gra = retTexture.getGraphics();
      gra.setFont(f1);
      gra.drawString(text.get(i),textX,(int)((i+1)*yOffsetPerLine + textY)); 
    }
    return retTexture;
  }
}