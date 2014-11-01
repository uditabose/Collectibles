/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.promodoro.tray;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class PromodoroTrayMonitor {

    private SystemTray systemTray = null;
    private TrayIcon promoTrayIcon = null;
    private PopupMenu promodoroMenu = null;
    private MenuItem currentActionItem = null;
    private MenuItem allActionsItem = null;
    private MenuItem exitActionItem = null;
    
    private PromodoroListMonitor listMonitor = null;

    
    public PromodoroTrayMonitor(PromodoroListMonitor plm) {
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

        promoTrayIcon.addMouseListener(new TrayMouseListener());
        systemTray.add(promoTrayIcon);
    }

    private void destoryTrayIcon() {
        systemTray.remove(promoTrayIcon);
    }
    
    

    private void addMenu() {
        promodoroMenu = new PopupMenu();
        MenuItemActionListener listener = new MenuItemActionListener();
        
        currentActionItem = new MenuItem("Current action");
        allActionsItem = new MenuItem("All actions");
        exitActionItem = new MenuItem("Exit!");
        
        currentActionItem.addActionListener(listener);
        allActionsItem.addActionListener(listener);
        exitActionItem.addActionListener(listener);

        promodoroMenu.add(currentActionItem);
        promodoroMenu.addSeparator();
        promodoroMenu.add(allActionsItem);
        promodoroMenu.addSeparator();
        promodoroMenu.add(exitActionItem);
        
        promoTrayIcon.setPopupMenu(promodoroMenu);
    }

    private void updateCurrentAction() {
        String updateCurrentAction = listMonitor.updateCurrentAction();
        currentActionItem.setLabel(updateCurrentAction);
    }
    
    private class MenuItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object sourceObj = e.getSource();
            
            if (sourceObj == currentActionItem) {
                updateCurrentAction();
            } else if (sourceObj == allActionsItem) {
                //TODO : Write the panel code!
            } else {
                System.out.println(" It has to be exit action!");
                destoryTrayIcon();
                //TODO : Write the synching code!
                System.exit(0);
            }
        }
        
    }
    
    
    private class TrayMouseListener implements MouseListener {

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
    
    }
    
    
}
