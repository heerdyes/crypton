package system;

import java.awt.Toolkit;
//import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

public class FrameBean extends JFrame implements ActionListener, WindowListener {
    private Toolkit tk;
    private int width;
    private int height;
    private MenuBean mb;
    private TextBean tb;
    private FrameBeanListener fblis;
    private FrameBeanEvent fbe;

    public FrameBean(MenuBean mbean) {
        super("Frame_Bean");
        addWindowListener(this);

        tk = Toolkit.getDefaultToolkit();
        width = tk.getScreenSize().width;
        height = tk.getScreenSize().height;

        mb = mbean;
        setJMenuBar(mb);
        update(null);

        setSize(width / 2, height / 2);
        setLocation(width / 4, height / 4);
        setVisible(true);
    }
    
    // dynamic functionality ... 
    
        // adding a textbean ... 
    public void addTextBean(TextBean tbean) {
        tb = tbean;
        getContentPane().add(tb);
        validate();
        pack();
    }

    // bean phenomena ... 
    public void addFrameBeanListener(FrameBeanListener fbl) {
        fblis = fbl;
    }
    
    public void fireFrameBeanEvent(FrameBeanEvent fbevt) {
        fblis.frameBeanActionPerformed(fbevt);
    }
    
    // listening apparatus ... 
    
        // actionlistener ...
    public void actionPerformed(ActionEvent ae) {
        fbe = new FrameBeanEvent(ae, ae.getActionCommand());
        fireFrameBeanEvent(fbe);
    }
    
        // windowlistener ... 
    public void windowActivated(WindowEvent we) {}
    
    public void windowClosed(WindowEvent we) {}
    
    public void windowClosing(WindowEvent we) {
        fbe = new FrameBeanEvent(we, "exit");
        fireFrameBeanEvent(fbe);
    }
    
    public void windowDeactivated(WindowEvent we) {}
    
    public void windowDeiconified(WindowEvent we) {}
    
    public void windowIconified(WindowEvent we) {}
    
    public void windowOpened(WindowEvent we) {}
    
        // phew !! 
}
