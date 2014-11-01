

package learning.promodoro.task;

import learning.promodoro.util.PromodoroEnumContainer;

/**
 *
 * @author Udita
 */
public class PromodoroTask {
    
    private final static long TASK_DURATION = 30; 
    private PromodoroEnumContainer.TaskType taskType;
    private String taskDescription;
    private final long startTime;
    private final long endTime;

    public PromodoroTask(PromodoroEnumContainer.TaskType taskType) {
        this.taskType = taskType;
        this.startTime = System.currentTimeMillis();
        this.endTime = this.startTime + (TASK_DURATION * 60 * 1000);
    }

    public PromodoroTask(String taskDescription, PromodoroEnumContainer.TaskType taskType) {
        this(taskType);
        this.taskDescription = taskDescription;
    }
    
    public boolean hasStarted() {
        return (System.currentTimeMillis() - startTime >= 0);
    }
    
    public boolean hasFinished() {
        return (System.currentTimeMillis() - endTime >= 0);
    }
    
    public PromodoroEnumContainer.TaskType getTaskType() {
        return this.taskType;
    }
    
    public void setTaskType(PromodoroEnumContainer.TaskType updatedTaskType) {
        this.taskType = updatedTaskType;
    }
    
    public void setTaskDescription(String updatedDescription) {
        this.taskDescription = updatedDescription;
    }
    
    public String getTaskDescription() {
        return this.taskDescription;
    }
    
}
