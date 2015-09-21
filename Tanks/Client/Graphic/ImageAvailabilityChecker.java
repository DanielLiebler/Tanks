package Tanks.Client.Graphic;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;


//Kram, den Java für Bilder braucht. Prüft, ob genug Daten eines Bildes
//berechnet wurden.


public class ImageAvailabilityChecker implements ImageObserver {  
  
  
  public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
    if ((infoflags & ImageObserver.ALLBITS) != 0) {
      return false;
    } else {
      return true;
    }
  }  
} 
