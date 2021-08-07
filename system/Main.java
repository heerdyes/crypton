package system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Main implements MenuBeanListener, FrameBeanListener, TextBeanListener, DialogBeanListener, ActionListener {
    private MenuBean mubn;
    private TextBean ttbn;
    private FrameBean febn;
    private DialogBean dgbn;
    private boolean ttbnPresent;
    
    GraphicsConfiguration obtainGC(){
      GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] devs=ge.getScreenDevices();
      if(devs.length!=1){
        U.d("[obtainGC] detected multiple graphics devices! panicking and quitting!");
        System.exit(0);
      }
      GraphicsDevice gd=devs[0];
      U.d("[obtainGC] graphics device: "+gd.toString());
      GraphicsConfiguration gc=gd.getDefaultConfiguration();
      return gc;
    }
    
    void deploycmd(GraphicsConfiguration gc){
      if(gc==null){
        U.d("[deploycmd] gc null!");
        return;
      }
      Rectangle br=gc.getBounds();
      int cx=br.width/2;
      int cy=br.height/2;
      int cw=1280;
      int ch=720;
      febn=new FrameBean(cx-cw/2,cy-ch/2,cw,ch,gc);
    }

    public static void main(String[] args) {
        final String[] _args = args;
        Runnable r = new Runnable() {
            public void run() {
                Main app = new Main();
                app._main(_args);
            }
        };
        
        SwingUtilities.invokeLater(r);
    }
    
    // the core dna begins ... 
    
    private void _main(String[] arguments) {
        deploycmd(obtainGC());
    }
    
    // subroutines ... 
    
    private void setPreferences() {
        dgbn = new DialogBean("Preferences");
        dgbn.addDialogBeanListener(this);
    }
    
    private void createTextBean() {
        // todo add text bean
    }

    private void destroyTextBean(){
      // doyu koto?
    }
    
    // listener methods ... 

    public void menuBeanActionPerformed(MenuBeanEvent mbe) {
        String mbeInfo = mbe.getEvtInfo();
        System.out.println("mbe: "+ mbeInfo);
        
        // cases for menubean ... 
        if(mbeInfo.compareTo("New") == 0) {
            // calling the subroutine createTextBean ... 
            createTextBean();
        }
        else if(mbeInfo.compareTo("Print") == 0) {
            // print handling ...
            System.out.println("todo print");
        }
        else if(mbeInfo.compareTo("Preferences") == 0) {
            // preferences ...
            setPreferences();
        }
        else if(mbeInfo.compareTo("Exit") == 0) {
            System.out.println("Exiting...");
            febn.dispose();
            cleanup();
        }
        else if(mbeInfo.compareTo("Open") == 0) {
            // todo
        }
        else if(mbeInfo.compareTo("Save") == 0) {
            // todo
        }
        else if(mbeInfo.compareTo("Background") == 0) {
            JColorChooser cc = new JColorChooser();
            Color cr = JColorChooser.showDialog(febn, "Background Color", Color.white);
            System.out.println("todo background color change");
        }
        else if(mbeInfo.compareTo("Foreground") == 0) {
            JColorChooser cc = new JColorChooser();
            Color cr = JColorChooser.showDialog(febn, "Foreground Color", Color.black);
            System.out.println("todo foreground color change");
        }
        else if(mbeInfo.compareTo("Close") == 0) {
            // todo
        }
        else if(mbeInfo.compareTo("About") == 0) {
            System.out.println("project crypton");
        }
        else if(mbeInfo.compareTo("Cut") == 0) {
            System.out.println("todo cut");
        }
        else if(mbeInfo.compareTo("Copy") == 0) {
            System.out.println("todo copy");
        }
        else if(mbeInfo.compareTo("Paste") == 0) {
            System.out.println("todo paste");
        }
        else if(mbeInfo.compareTo("Font") == 0) {
            // change font type
            System.out.println("todo font");
        }
        else if(mbeInfo.compareTo("Bold") == 0) {
            System.out.println("todo bold");
        }
        else if(mbeInfo.compareTo("Italic") == 0) {
            System.out.println("todo italic");
        }
        else if(mbeInfo.compareTo("Underline") == 0) {
            System.out.println("todo underline");
        }
        else if(mbeInfo.compareTo("Dump") == 0) {
            System.out.println("dumping...");
        }
		else if(mbeInfo.equals("Shell")) {
		    // launch shell
		}
    }
    
    public void frameBeanActionPerformed(FrameBeanEvent fbe) {
        System.out.println("fbe: "+ fbe.getEvtInfo());
        if(fbe.getEvtInfo().equals("exit")) {
            cleanup();
        }
    }
    
    public void textBeanActionPerformed(TextBeanEvent tbe) {
        System.out.println("tbe ==>> "+ tbe);
    }
    
    public void dialogBeanActionPerformed(DialogBeanEvent dbe) {
        System.out.println("dbe ==>> "+ dbe);
    }
    
    public void actionPerformed(ActionEvent ae) {
        System.out.println("ae: "+ ae);
    }
    
    public void cleanup() {
        System.exit(0);
    }
}

