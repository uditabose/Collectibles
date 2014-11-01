
package learning.promodoro.store;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ubose
 */
@XmlRootElement(name="actionList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PromodoroBeanList {
    @XmlElement(name="action")
    private List<PromodoroBean> actionList = null;

    public List<PromodoroBean> getActionList() {
        return actionList;
    }

    public void setActionList(List<PromodoroBean> actionList) {
        this.actionList = actionList;
    }
    
    
}
