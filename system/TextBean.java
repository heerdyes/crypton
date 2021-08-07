package system;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TextBean implements ActionListener, KbSensor {
    int curpos;
    TBox ta;
    TBox tf;
    ArrayList<TextBeanListener> tbsubs;
    
    public TextBean(int r, int c) {
        tbsubs=new ArrayList<>();
        ta=new TBox(10,10,20,20,15f);
        tf=new TBox(10,600,20,1,10f);
    }

    // kbd sensors
    public void kbdown(int kc,char c){
      if(kc==27){
        fireTextBeanEvent(new TextBeanEvent("exit"));
      }else{
        U.d(String.format("[%s] %s",kc,c));
      }
    }

    public void kbup(int kc,char c){}
    
    // loading text area ... 
    public void loadText(String sd) {
        ta.cg.insertString(0, sd);
    }
    
    // get the contents of the document ... 
    public String getDocumentContent() {
        return ta.cg.getText();
    }
    
    // updating the status bar ... 
    private void updateCaretPosition() {
        tf.cg.insertString(0,"caret: ["+ tf.cg.currow +", "+ tf.cg.curcol +"]");
    }
    
    private void updateCaretDot(int cd) {
        int cardot = cd;
        tf.cg.insertString(0,"caret: ["+ cardot +"]");
    }
    
    // changing colors ... 
    public void setBackColor(Color c) {
        System.out.println("todo bg color");
    }
    
    public void setForeColor(Color c) {
        System.out.println("todo fg color");
    }
    
    public void loadFile(File filename) {
        try(FileInputStream fis=new FileInputStream(filename);
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr))
        {
            String ln;
            int ctr=0;
            while((ln=br.readLine())!=null){
                ta.cg.insln(0,ln);
            }
        }catch(IOException ioe){
          System.err.println(ioe.getMessage());
        }
    }
    
    public void saveContents(File filename) {
        // todo
    }
    
    /*...   the plugpoints   ...*/
    
    public void addTextBeanListener(TextBeanListener tbl) {
        tbsubs.add(tbl);
    }
    
    public void fireTextBeanEvent(TextBeanEvent tbe) {
      for(TextBeanListener tbl:tbsubs){
        tbl.textBeanActionPerformed(tbe);
      }
    }
    
    /*...   the listener stuff   ... */
    
    // actionlistener implementation ...
    
    public void actionPerformed(ActionEvent ae) {
        TextBeanEvent tbe = new TextBeanEvent(ae.getSource().toString());
        fireTextBeanEvent(tbe);
    }
}

