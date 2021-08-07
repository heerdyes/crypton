package system;

import java.awt.event.*;
import javax.swing.*;
import static java.lang.System.out;

public class MenuBean implements ActionListener {
    MenuBeanListener mblis;
    
    public MenuBean() {
    }
    
    // subroutines ... 
    public void addMenuBeanListener(MenuBeanListener mbl) {
        mblis = mbl;
    }
    
    public void fireMenuBeanEvent(MenuBeanEvent mbe) {
        try {
            mblis.menuBeanActionPerformed(mbe);
        }
        catch(Exception ignore) {
		    out.println(ignore.toString());
		}
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("e = "+ e);
        //System.out.println("source = "+ e.getSource().toString());

        MenuBeanEvent mbeve = new MenuBeanEvent(e.getActionCommand());
        try {
            fireMenuBeanEvent(mbeve);
        }catch(Exception ignore) {
    	    out.println(ignore.toString());
		}
    }

}
