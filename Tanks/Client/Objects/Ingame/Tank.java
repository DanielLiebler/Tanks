package Tanks.Client.Objects.Ingame;

import Tanks.Client.Graphic.TextureManager; 
import Tanks.Client.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public class Tank extends GameObject{
  
  protected BufferedImage textureTank;
  protected BufferedImage textureTurret;
  public double tankRot, turretRot;
  
  public Tank(float x, float y, float szx, float szy){ 
    super(x,y,szx,szy);
    int r,g,b;
    Random r1 = new Random();
    
    r = r1.nextInt(256);
    g = r1.nextInt(256);
    b = r1.nextInt(256);
    
    init(r,g,b);
  }
  public Tank(float x, float y, float szx, float szy, int r, int g, int b){   
    super(x,y,szx,szy);
    init(r,g,b);
  }
  public void init(int r, int g, int b){
    
    textureTank = TextureManager.getTexture("tank.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()));
    textureTank.getGraphics().drawImage(colorImage(TextureManager.getTexture("tank_color.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight())), r, g, b, 255), 0, 0, null);
    
    textureTurret = TextureManager.getTexture("turret.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()));
    textureTurret.getGraphics().drawImage(colorImage(TextureManager.getTexture("turret_color.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight())), r, g, b, 255), 0, 0, null);
    
    tankRot = 0;
    turretRot = 0;
  }
  public BufferedImage getTexture(){
    BufferedImage ret = new BufferedImage(textureTank.getWidth(), textureTank.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics2D retGra = ret.createGraphics();
    
    AffineTransform aTransTank = new AffineTransform();  
    AffineTransform aTransTurret = new AffineTransform();
    
    aTransTank.translate(textureTank.getWidth()/2, textureTank.getHeight()/2); 
    aTransTank.rotate(tankRot);
    aTransTank.translate(-textureTank.getWidth()/4, -textureTank.getHeight()/4);
    aTransTank.scale(0.5,0.5);
    
    aTransTurret.translate(textureTurret.getWidth()/2, textureTurret.getHeight()/2); 
    aTransTurret.rotate(turretRot);
    aTransTurret.translate(-textureTurret.getWidth()/4, -textureTurret.getHeight()/4);     
    aTransTurret.scale(0.5,0.5);
    
    
    retGra.drawImage(textureTank, aTransTank, null);
    retGra.drawImage(textureTurret, aTransTurret, null);
    
    Log.write("Tank returned: " + ret.toString(), Log.LOGDEPTH_High);
    
    return  ret;
  }
  protected BufferedImage colorImage(BufferedImage src, int r, int g, int b, int a){
    int j, k, red, green, blue, alpha;
    Graphics srcGra = src.getGraphics();
    for(j = 0; j < src.getWidth(); j++){
      for(k = 0; k < src.getHeight(); k++){
        Color c = new Color(src.getRGB(j, k));
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();  
        alpha = c.getAlpha();
        if(red == 255 && green == 255 && blue == 255){
          srcGra.setColor(new Color(r, g, b, a));
          srcGra.fillRect(j,k,1,1);
        }
      }
    }
    return src;
  }
}