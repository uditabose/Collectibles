
package learning.promodoro.store;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author ubose
 */
public class PromodoroListMonitor {
    private PromodoroBeanList currentActionList = null;
    private PromodoroBean currentAction = null;
    
    private int size = 0;
    
    public PromodoroListMonitor(String actionListFilePath ) throws JAXBException {
        populateCurrentActionList(actionListFilePath);
        this.currentAction = currentActionList.getActionList().get(0);
    }
    
    public boolean startAction(int actionId) {
        PromodoroBean actionBean = getByActionId(actionId);
        
        boolean isStarted = false;
        
        if (actionBean != null) {
            actionBean.setHasStarted(true);
        }
        
        return isStarted;
    }
    
    public boolean finishAction(int actionId) {
        return true;
    }
    
    public boolean addAction() {
        
        return true;
    }
    
    public String updateCurrentAction() {
        if (currentAction == null) {
            return "No work! All Play!";
        } else {
            currentAction.setTimeLeft(1000);
            return "Current : 1000";
        }
    }
    
    public boolean deleteAction(int actionId) {
        return true;
    }
    
    private PromodoroBean getByActionId(int actionId) {
        PromodoroBean actionBean = null;
        if (size == 0) {
            System.out.println("No action available!");  
        } else {
            for (PromodoroBean aBean : currentActionList.getActionList()) {
                if (aBean.getId() == actionId) {
                    actionBean = aBean;
                    break;
                }
            }
        }
        return actionBean;
    }

    private void populateCurrentActionList(String actionListFilePath) throws JAXBException {
        File aXmlFile = new File(actionListFilePath);
        
        JAXBContext context = JAXBContext.newInstance(PromodoroBeanList.class, PromodoroBean.class);
        currentActionList = (PromodoroBeanList) context.createUnmarshaller().unmarshal(aXmlFile);
        
        size = currentActionList.getActionList().size();
        
        context = null;
    }
    
    
}
