package system;

import java.util.EventListener;

public interface DialogBeanListener extends EventListener {
    public void dialogBeanActionPerformed(DialogBeanEvent dbe);
}
