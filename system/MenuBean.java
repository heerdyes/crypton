package system;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import static java.lang.System.out;
//import javax.swing.JFrame;
//import javax.swing.JDialog;

public class MenuBean extends JMenuBar implements ActionListener {
    private JMenu[] menus;
    private JMenuItem[][] items;
    //private MenuBeanEvent mbeve;
    private MenuBeanListener mblis;

    public MenuBean(String[][] data, boolean[][] metadata) {
    /*  // debugging info ... 
        System.out.println("data.length = "+ data.length);
        System.out.println("data[0].length = "+ data[0].length);
        System.out.println("data[1].length = "+ data[1].length);
        System.out.println("data[2].length = "+ data[2].length);
    */

        int i ,j;
        menus = new JMenu[data.length];
        items = new JMenuItem[data.length][];

        for(i = 0; i < data.length; i++) {
            items[i] = new JMenuItem[data[i].length];
            menus[i] = new JMenu(data[i][0]);
            menus[i].setEnabled(metadata[i][0]);
            for(j = 1; j < data[i].length; j++) {
                items[i][j] = new JMenuItem(data[i][j]);
                items[i][j].setEnabled(metadata[i][j]);
                items[i][j].addActionListener(this);
                menus[i].add(items[i][j]);
            }
            System.out.println("i = "+ i);
            System.out.println("j = "+ j);

            add(menus[i]);
            
            validate();
        }
    }
    
    // subroutines ... 
    public void updateMetaData(boolean[][] md) {
        System.out.println("in updateMetaData of MenuBean");
        int i, j;
        for(i = 0; i < md.length; i++) {
            menus[i].setEnabled(md[i][0]);
            //menus[i].validate();
            for(j = 1; j < md[i].length; j++) {
                items[i][j].setEnabled(md[i][j]);
                //items[i][j].validate();
            }
        }
        
        System.out.println("eof updatemd of menubean");
    }
    
    public void updateMnemonicData(char[][] mnd) {
        int i, j;
        for(i = 0; i < mnd.length; i++) {
            menus[i].setMnemonic(mnd[i][0]);
            for(j = 1; j < mnd[i].length; j++) {
                items[i][j].setMnemonic(mnd[i][j]);
            }
        }
    }

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

        MenuBeanEvent mbeve = new MenuBeanEvent(e, e.getActionCommand());
        try {
            fireMenuBeanEvent(mbeve);
        }
        catch(Exception ignore) {
			out.println(ignore.toString());
		}
    }

}
