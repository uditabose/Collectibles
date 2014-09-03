/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.promodoro;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import learning.promodoro.logic.PromodoroListMonitor;
import learning.promodoro.tray.PromodoroTrayMonitor;

/**
 *
 * @author ubose
 */
public class Promodoro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PromodoroListMonitor listMonitor = 
                    new PromodoroListMonitor("C:\\Documents and Settings\\ubose\\Desktop\\s.xml");
            PromodoroTrayMonitor promodoroTray = new PromodoroTrayMonitor(listMonitor);
            /*Map<String, String> arguments = new HashMap<String, String>();
            if (!validateArgument(args, arguments)) {
                System.out.println(" Invalid arguments ");
                System.exit(0);
            }
            
            PromodoroListMonitor monitor = new PromodoroListMonitor(arguments.get("ACTION_LIST"));*/
            
            
            
            /*JAXBContext context = JAXBContext.newInstance(PromodoroBeanList.class, PromodoroBean.class);
            PromodoroBeanList beanList = new PromodoroBeanList();
            beanList.setActionList(new ArrayList<PromodoroBean>());
            
            PromodoroBean aBean = new PromodoroBean();
            aBean.setId(2);
            aBean.setDescription("Test desc");
            aBean.setHasStarted(true);
            aBean.setPriority(1);
            aBean.setTimeLeft(10);
            beanList.getActionList().add(aBean);
            beanList.getActionList().add(aBean);
            beanList.getActionList().add(aBean);
            
            //context.createMarshaller().marshal(beanList, System.out);
            
            File aXmlFile = new File("C:\\Documents and Settings\\ubose\\Desktop\\s.xml");
            PromodoroBeanList obj = (PromodoroBeanList) context.createUnmarshaller().unmarshal(aXmlFile);
            System.out.println();
            System.out.println(obj.getActionList().get(0));*/
            
        } catch (Exception ex) {
            Logger.getLogger(Promodoro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean validateArgument(String[] arguments, Map<String, String> argumentsMap) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
