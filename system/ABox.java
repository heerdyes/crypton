package system;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

class ABox{
  Rectangle2D r;
  Color bg,fg;
  
  ABox(double x,double y,double w,double h){
    r=new Rectangle2D.Double(x,y,w,h);
    fg=new Color(0,0,0,255);
    bg=new Color(230,230,230,255);
  }
  
  void render(Graphics2D g2){
    g2.setPaint(bg);
    g2.fill(r);
  }
}

