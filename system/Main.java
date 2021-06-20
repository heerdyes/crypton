package system;

//import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.BorderLayout;
//import java.awt.Component;

import javax.swing.SwingUtilities;
//import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import javax.swing.JButton;
//import javax.swing.JToggleButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Main implements MenuBeanListener, FrameBeanListener, TextBeanListener, DialogBeanListener, ActionListener {
    private MenuBean mubn;
    private TextBean ttbn;
    private FrameBean febn;
    private DialogBean dgbn;
    
    private String[][] menudata;
    private char[][] mnemonicdata;
    
    private boolean[][] metamenudata;
    private boolean ttbnPresent;
    private boolean subtle;
    private boolean boldState;
    private boolean italicState;
    private boolean underlineState;

    public static void main(String[] args) {
        final String[] _args = args;
        Runnable r = new Runnable() {
            public void run() {
                Main app = new Main();
                app._main(_args);
            }
        };
        
        SwingUtilities.invokeLater(r);
        System.out.println("dispatched ...");
    }
    
    // the core dna begins ... 
    
    private void _main(String[] arguments) {
        ttbnPresent = false;
        
        String[][] _menudata = {
            {"File", "New", "Open", "Close", "Save", "Print", "Dump", "Exit"},
            {"Format", "Font", "Bold", "Italic", "Underline"},
            {"Edit", "Cut", "Copy", "Paste"},
            {"Color", "Background", "Foreground"},
            {"Settings", "Preferences"},
            {"Tools", "Options", "Shell"},
            {"Help", "About"}
        };
        menudata = _menudata;
        
        boolean[][] _metamenudata = {
            {true, true, true, false, false, false, false, true},
            {true, false, false, false, false},
            {false, false, false, false},
            {false, false, false},
            {true, true},
            {true, true, true},
            {true, true}
        };
        metamenudata = _metamenudata;
        
        char[][] _mnemonicdata = {
            {'f', 'n', 'o', 'c', 's', 'p', 'd', 'x'},
            {'o', 'f', 'b', 'i', 'u'},
            {'e', 'c', 'u', 'p'},
            {'l', 'b', 'd'},
            {'s', 'r'},
            {'t', 'o', 'l'},
            {'h', 'b'}
        };
        mnemonicdata = _mnemonicdata;
        
        _menudata = null;
        _metamenudata = null;
        _mnemonicdata = null;
        
        subtle = false;
        subtlety("The most brilliant things are the simplest", "Comply ?");
        
        // creating the menubean ...
        mubn = new MenuBean(menudata, metamenudata);
        mubn.addMenuBeanListener(this);
        mubn.updateMnemonicData(mnemonicdata);
        
        // creating the framebean ...
        febn = new FrameBean(mubn);
        febn.addFrameBeanListener(this);
        
        //System.out.println("Hello there");
    }
    
    // subroutines ... 
    
    private void setPreferences() {
        dgbn = new DialogBean("Preferences");
        dgbn.addDialogBeanListener(this);
    }
    
    private void subtlety(String sa, String sb) {
        if(subtle) {
            JOptionPane.showMessageDialog(null, sa, sb, JOptionPane.OK_CANCEL_OPTION);
        }
    }
    
    private void createTextBean() {
        if(ttbnPresent) {
            destroyTextBean();
        }
        
        System.out.println("Creating TextBean ...");
        ttbn = new TextBean(10, 40);
        ttbn.addTextBeanListener(this);
        febn.addTextBean(ttbn);
        ttbnPresent = true;                // this may cause some trouble ... 
        updateMetaMenuData(ttbnPresent);
        
        boldState = false;
        italicState = false;
        underlineState = false;
    }
    
    private void destroyTextBean() {
        febn.remove(ttbn);
        febn.validate();
        febn.pack();
        ttbn = null;
        ttbnPresent = false;
        updateMetaMenuData(ttbnPresent);
    }
    
    private void updateMetaMenuData(boolean state) {
        int i, j;
        if(state) {
            for(i = 0; i < metamenudata.length; i++) {
                for(j = 0; j < metamenudata[i].length; j++) {
                    metamenudata[i][j] = true;
                    System.out.print(metamenudata[i][j] +" ");
                }
                System.out.println();
            }
        }
        else {
            metamenudata[0][3] = false;
            metamenudata[0][4] = false;
            metamenudata[1][0] = false;
            metamenudata[2][0] = false;
            metamenudata[1][1] = false;
            metamenudata[1][2] = false;
            metamenudata[1][3] = false;
            metamenudata[1][4] = false;
            metamenudata[1][5] = false;
            metamenudata[0][6] = false;
        }
        
        mubn.updateMetaData(metamenudata);
        
        febn.validate();
        System.out.println("eof updatemmd");
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
            ttbn.doPrint();
        }
        else if(mbeInfo.compareTo("Preferences") == 0) {
            // preferences ...
            setPreferences();
        }
        else if(mbeInfo.compareTo("Exit") == 0) {
            System.out.println("Exiting...");
            febn.dispose();
            subtlety("visit again!", "exiting");
            cleanup();
        }
        else if(mbeInfo.compareTo("Open") == 0) {
            // call createTextBean later ... 
            createTextBean();
            
            System.out.println("in open handler...");
            JFileChooser fc = new JFileChooser();
            File fl;
            String sdata;
            
            System.out.println("declarations...");
            int retval = fc.showOpenDialog(febn);
            
            if(retval == JFileChooser.APPROVE_OPTION) {
                fl = fc.getSelectedFile();
                
                if(fl.toString().endsWith(".txt")) {
                    System.out.println("Opening ==>> "+ fl);
                    
                    try {
                        ttbn.initTextPane();
                        System.out.println("initiated... \nttbn: "+ ttbn);
                        
                        ttbn.loadFile(fl);
                        
                        System.out.println("source stream closed");
                        febn.validate();
                    }
                    catch(Exception exc) {
                        System.err.println("exception: "+ exc);
                    }
                }
                else {
                    subtlety("Unknown type of file", "Error");
                    destroyTextBean();
                }
            }
            else if(retval == JFileChooser.CANCEL_OPTION) {
                System.out.println("operation cancelled");
            }
            else {
                System.out.println("error encountered");
            }
        }
        else if(mbeInfo.compareTo("Save") == 0) {
            JFileChooser fc = new JFileChooser();
            File fl;
            
            if(!ttbnPresent) {
                System.out.println("ttbnPresent: "+ ttbnPresent);
                JButton test = new JButton("warning: textarea status: "+ ttbnPresent);
                
                final JDialog d = new JDialog();
                d.setTitle("Buffer empty");
                d.setSize(400, 100);
                
                d.getContentPane().add(test);
                
                test.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        d.dispose();
                    }
                });
                
                d.setVisible(true);
                d.validate();
            }
            else {
                int retval = fc.showSaveDialog(febn);
                if(retval == JFileChooser.APPROVE_OPTION) {
                    fl = fc.getSelectedFile();
                    System.out.println("Saving ==>> "+ fl);
                    try {
                        ttbn.saveContents(fl);
                    }
                    catch(Exception exc) {
                        System.err.println("Exception: "+ exc);
                    }
                }
                else if(retval == JFileChooser.CANCEL_OPTION) {
                    System.out.println("operation cancelled");
                }
                else {
                    System.out.println("error encountered");
                }
            }
        }
        else if(mbeInfo.compareTo("Background") == 0) {
            JColorChooser cc = new JColorChooser();
            Color cr = JColorChooser.showDialog(febn, "Background Color", Color.white);
            if(ttbnPresent) {
                //ttbn.setBackColor(cr);
                ttbn.updateStyle(cr, false);
            }
        }
        else if(mbeInfo.compareTo("Foreground") == 0) {
            JColorChooser cc = new JColorChooser();
            Color cr = JColorChooser.showDialog(febn, "Foreground Color", Color.black);
            if(ttbnPresent) {
                //ttbn.setForeColor(cr);
                ttbn.updateStyle(cr, true);
            }
        }
        else if(mbeInfo.compareTo("Close") == 0) {
            if(ttbnPresent) {
                JOptionPane.showMessageDialog(febn, "Current text will be irreparably deleted!");
                destroyTextBean();
            }
            
            febn.validate();
        }
        else if(mbeInfo.compareTo("About") == 0) {
            System.out.println("project crypton");
            subtle = true;
            subtlety("HM, GKT, KRB, MSRB, MA", "Team Members' Initials : ");
        }
        else if(mbeInfo.compareTo("Cut") == 0) {
            ttbn.doCut();
        }
        else if(mbeInfo.compareTo("Copy") == 0) {
            ttbn.doCopy();
        }
        else if(mbeInfo.compareTo("Paste") == 0) {
            ttbn.doPaste();
        }
        else if(mbeInfo.compareTo("Font") == 0) {
            // change font type
            System.out.println("Pending ...");
            
            // recent changes ... 
            // keep under comment until fully fixed ...
            
            //*
            final JDialog td = new JDialog();
            td.setTitle("Font");
            td.setSize(200, 100);
            String family;
            
            System.out.println("in font handler");
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontFamilies = ge.getAvailableFontFamilyNames();
            
            final JComboBox cb = new JComboBox();
            for(int i = 0; i < fontFamilies.length; i++) {
                cb.addItem(fontFamilies[i]);
            }
            
            final JComboBox cbsz = new JComboBox();
            for(int i = 4; i <= 48; i += 2) {
                cbsz.addItem(i);
            }
            cbsz.setSelectedItem(12);
            
            td.getContentPane().add(cb, BorderLayout.NORTH);
            td.getContentPane().add(cbsz, BorderLayout.SOUTH);
            
            //*
            cb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    //System.out.println("ActionEvent :: "+ ae);
                    ttbn.updateStyle( (String)cb.getSelectedItem(), ((Integer)cbsz.getSelectedItem()).intValue() );
                    //System.out.println("family: "+ family);
                    td.validate();
                    ttbn.validate();
                }
            });
            
            cbsz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    //System.out.println("ActionEvent :: "+ ae);
                    ttbn.updateStyle( (String)cb.getSelectedItem(), ((Integer)cbsz.getSelectedItem()).intValue() );
                    //System.out.println("family: "+ family);
                    td.validate();
                    ttbn.validate();
                }
            });
            //*/
            
            //cb.addActionListener(this);
            //cbsz.addActionListener(this);

            /*
            final Main _parent = this;
            td.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    cb.removeActionListener(_parent);
                    cbsz.removeActionListener(_parent);
                }
            });
            */
            
            td.validate();
            td.setVisible(true);

            //*/
        }
        else if(mbeInfo.compareTo("Bold") == 0) {
            if(boldState == false) {
                boldState = true;
            }
            else {
                boldState = false;
            }
            ttbn.updateStyle(boldState, italicState, underlineState);
        }
        else if(mbeInfo.compareTo("Italic") == 0) {
            if(italicState == false) {
                italicState = true;
            }
            else {
                italicState = false;
            }
            ttbn.updateStyle(boldState, italicState, underlineState);
        }
        else if(mbeInfo.compareTo("Underline") == 0) {
            if(underlineState == false) {
                underlineState = true;
            }
            else {
                underlineState = false;
            }
            ttbn.updateStyle(boldState, italicState, underlineState);
        }
        else if(mbeInfo.compareTo("Dump") == 0) {
            ttbn.doDump();
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
    
    // cleaning the jvm floor (is it so?) ...
    public void cleanup() {
        // under construxion ... 
        
        //mubn = null;
        //ttbn = null;
        //febn = null;
        
        System.exit(0);
    }
}
