/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.promodoro.logic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ubose
 */
@XmlRootElement(name="action")
@XmlAccessorType(XmlAccessType.FIELD)
public class PromodoroBean {
    
    @XmlElement(name="actionId", required=true)
    private int id = 0;
    
    @XmlElement(name="priority", required=false, defaultValue="0")
    private int priority = 0;
    
    @XmlElement(name="description", required=true)
    private String description = null;
    
    @XmlElement(name="timeLeft", required=false)
    private long timeLeft = 0;
    
    @XmlElement(name="hasStarted", required=false)
    private boolean hasStarted = false;
    
    @XmlTransient
    private long startTime = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    @Override
    public String toString() {
        return "PromodoroBean{" + "id=" + id + ", priority=" + priority 
                + ", description=" + description + ", timeLeft=" 
                + timeLeft + ", hasStarted=" + hasStarted + '}';
    }
    
    
    
}
