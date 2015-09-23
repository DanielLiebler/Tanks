package Tanks.Client.Objects.Gui;

import Tanks.Client.*;  
import Tanks.Client.Graphic.TextureManager; 
import java.awt.image.*;                      
import java.awt.event.*;
import java.util.*;
import java.awt.font.*;
import java.awt.*;
import java.awt.geom.*;

public class GUIElement{
  private boolean hasText;
  private String[] text;
  private ArrayList<Callback> onClick = new ArrayList<Callback>();
  private BufferedImage myTexture;
  private float x,y,szx,szy;
  
  public GUIElement(float px, float py, float sizx, float sizy, boolean hasText, String[] text){
    this.x = px;   
    this.y = py;
    this.szx = sizx;
    this.szy = sizy;
    this.hasText = hasText;
    this.text = text;
    this.myTexture = new BufferedImage((int)(sizx* Main.getMyWindow().getWidth()), (int)(sizy* Main.getMyWindow().getHeight()), BufferedImage.TYPE_INT_ARGB_PRE);
  }
  public GUIElement(float x, float y, float szx, float szy, boolean hasText, String[] text, String myTexture){
    this(x,y,szx,szy,hasText, text);
    this.myTexture = TextureManager.getTexture(myTexture, (int)(szx* Main.getMyWindow().getWidth()), (int)(szy* Main.getMyWindow().getHeight()), false);
  }
  
  public float[] getPos(){
    return new float[]{x,y};
  }
  public float[] getSize(){
    return new float[]{szx,szy};
  }
  public void addCallback(Callback e){
    onClick.add(e);
  }
  public boolean onClick(MouseEvent evt){
    float posx = (float)(((double)evt.getX())/((double)Main.getMyWindow().getWidth()));
    float posy = (float)(((double)evt.getY())/((double)Main.getMyWindow().getHeight()));   
    if(posx >= this.getPos()[0] && posx <= (this.getPos()[0]+this.getSize()[0]) && posy >= this.getPos()[1] && posy <= (this.getPos()[1]+this.getSize()[1])){
      for(int i = 0; i < onClick.size(); i++){     
        if((boolean)onClick.get(i).run(evt)) return true;
      }
    }
    return false;
  }
  
  public BufferedImage getTexture(){
    BufferedImage retTexture = myTexture;
    
    
    Font f1 = new Font("", Font.PLAIN, 100);
    int yOffsetPerLine = 0;
    for (int i = 0; i<text.length; i++) {
      FontRenderContext frc = retTexture.createGraphics().getFontRenderContext();
      Rectangle2D rect = f1.getStringBounds(text[i], frc);
      yOffsetPerLine = (int)(rect.getHeight());
      if (rect.getWidth() > this.szx*Main.getMyWindow().getWidth() || rect.getHeight()*text.length > this.szy*Main.getMyWindow().getHeight()) {
        f1 = new Font("", Font.PLAIN, f1.getSize()-1);
        i--;
      }
    }     
    for (int i = 0; i<text.length; i++ ) {  
      FontRenderContext frc = retTexture.createGraphics().getFontRenderContext();
      Rectangle2D rect = f1.getStringBounds(text[i], frc);
      int textX = (int)((szx*Main.getMyWindow().getWidth()-rect.getWidth())/2), textY = (int)((szy*Main.getMyWindow().getHeight() - rect.getHeight()*text.length)/2); 
      Graphics gra = retTexture.getGraphics();
      gra.setFont(f1);
      gra.drawString(text[i],textX,(int)((i+1)*yOffsetPerLine + textY)); 
    }
    return retTexture;
  }
  
  public String toString(){
    return ("GUIElement title=" + text[0] + ", tex=" + myTexture);
  }
}