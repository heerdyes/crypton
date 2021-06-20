package system;

import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogBean extends JDialog implements ActionListener {
    private JPanel panel;
    private DialogBeanListener dblis;
    private JToggleButton tb;

    public DialogBean(String title) {
        super();
        
        setTitle(title);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4);
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        
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
        DialogBeanEvent dbe = new DialogBeanEvent(e, e.getSource().toString());
        fireDialogBeanEvent(dbe);
    }
}
