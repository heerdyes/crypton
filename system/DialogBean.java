package system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DialogBean extends JDialog implements ActionListener {
    private JPanel panel;
    private DialogBeanListener dblis;
    private JToggleButton tb;

    public DialogBean(String title) {
        super();
        
        setTitle(title);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension dim=tk.getScreenSize();
        int w=dim.width;
        int h=dim.height;
        setLocation(w / 4, h / 4);
        setSize(w / 2, h / 2);
        
        panel = new JPanel();
        tb = new JToggleButton("subtle");
        tb.setSize(200, 100);
        tb.addActionListener(this);
        panel.add(tb);
        
        getContentPane().add(panel);
        super.setVisible(true);
    }

    /* the plugpoints ... */
    
    public void addDialogBeanListener(DialogBeanListener dbl) {
        dblis = dbl;
    }
    
    public void fireDialogBeanEvent(DialogBeanEvent evt) {
        dblis.dialogBeanActionPerformed(evt);
    }
    
    public void actionPerformed(ActionEvent e) {
        DialogBeanEvent dbe = new DialogBeanEvent(e.getSource().toString());
        fireDialogBeanEvent(dbe);
    }
}
