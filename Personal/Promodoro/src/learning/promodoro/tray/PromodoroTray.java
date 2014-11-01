
package learning.promodoro.tray;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import learning.promodoro.store.PromodoroListMonitor;

/**
 *
 * @author ubose
 */
public class PromodoroTray {

    private SystemTray systemTray = null;
    private TrayIcon promoTrayIcon = null;
    private PromodoroListMonitor listMonitor = null;

    public PromodoroTray(PromodoroListMonitor plm) {
        try {
            this.listMonitor = plm;
            this.systemTray = SystemTray.getSystemTray();
            createTrayIcon();
        } catch (Exception e) {
            systemTray = null;
            promoTrayIcon = null;
        }
    }

    private void createTrayIcon() throws AWTException {
        Image promoIcon = new ImageIcon(getClass().getResource("promo.png")).getImage();
        promoTrayIcon = new TrayIcon(promoIcon, "Stay nimble!");
        addMenu();

        promoTrayIcon.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                updateCurrentAction();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // do nothing
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // do nothing
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // do nothing
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // do nothing
            }
        });
        systemTray.add(promoTrayIcon);
    }

    private void destoryTrayIcon() {
        systemTray.remove(promoTrayIcon);
    }

    private void addMenu() {
        PopupMenu promoPopup = new PopupMenu();

        MenuItem currentAction = new MenuItem("Current action");

        

        MenuItem viewAllActions = new MenuItem("All actions");

        MenuItem exit = new MenuItem("Exit!");

        promoPopup.add(currentAction);
        promoPopup.addSeparator();
        promoPopup.add(viewAllActions);
        promoPopup.addSeparator();
        promoPopup.add(exit);
        promoTrayIcon.setPopupMenu(promoPopup);
    }

    private void updateCurrentAction() {
        String updateCurrentAction = listMonitor.updateCurrentAction();
    }
}
