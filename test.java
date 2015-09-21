
public class test {
  
  public static void main(String[] args) {
    
    int x1=0,x2=2,y1=0,y2=2; 
    
    double acath = (x2-x1), gcath = (y2-y1);
    boolean xflag = false, yflag = false;
    xflag = (acath < 0);                 
    yflag = (gcath < 0);
    
    System.out.println(Math.toDegrees(Math.atan((gcath/acath))));
  } // end of main
  
} // end of class test
