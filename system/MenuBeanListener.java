package system;

import java.util.EventListener;

public interface MenuBeanListener extends EventListener {
    public void menuBeanActionPerformed(MenuBeanEvent mbevt);
}
