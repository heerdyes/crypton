package system;

import java.awt.*;
import java.util.*;

public class FrameBean extends Frame implements FBEEmitter, TextBeanListener{
  int x,y,w,h;
  Slate c;
  TextBean tb;
  InDevCtl idc;
  ArrayList<FrameBeanListener> fbsubs;
  
  FrameBean(int x,int y,int w,int h,GraphicsConfiguration gc){
    super("central",gc);
    this.x=x;
    this.y=y;
    this.w=w;
    this.h=h;
    c=new Slate();
    idc=new InDevCtl();
    fbsubs=new ArrayList<>();
    tb=new TextBean(20,30);
    launch();
  }
  
  // bean phenomena ... 
  public void addFrameBeanListener(FrameBeanListener fbl) {
    fbsubs.add(fbl);
  }

  public void fireFrameBeanEvent(FrameBeanEvent fbevt) {
    for(FrameBeanListener fbl:fbsubs){
      fbl.frameBeanActionPerformed(fbevt);
    }
  }
  
  // other subroutines ...
  void info(){
    U.d(String.format("location: (%d,%d)",x,y));
    U.d(String.format("dimensions: (%d,%d)",w,h));
  }
  
  void initsensors(){
    U.d("[initsensors] adding keylistener");
    c.addKeyListener(idc);
    idc.registerKbSensor(tb);
    tb.addTextBeanListener(this);
  }
  
  void initattrs(){
    setLocation(x,y);
    setSize(w,h);
    setLayout(new GridLayout(1,1));
    setUndecorated(true);
    c.setFocusable(true);
  }
  
  void launch(){
    initsensors();
    initattrs();
    add(c);
    setVisible(true);
  }

  public void textBeanActionPerformed(TextBeanEvent tbe){
    U.d("rcvd msg from textbean: "+tbe.toString());
    if("exit".equals(tbe.getEvtInfo())){
      U.d("exiting...");
      System.exit(0);
    }
  }
}

