

package learning.promodoro.task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import learning.promodoro.util.PromodoroEnumContainer;

/**
 *
 * @author Udita
 */
public class PromodoroTaskManager {
    
    private static final int MAX_ALLOWED_TASKS = 10;
    private static final int OPTIMAL_NUMBER_OF_TASKS = 5;
    private static final long TIME_INTERVAL = 60 * 1000;

    private final List<PromodoroTask> taskList = new LinkedList<PromodoroTask>();
    private final List<PromodoroTask> historicalWorkingList = new LinkedList<PromodoroTask>();
    
    // threads
    private Executor backUpExecutor;
    private Executor taskCompletionExecutor;
    
    private PromodoroTaskManager() {
        initializeManager();
    }
    
    private static class Holder {
        private static final PromodoroTaskManager TASK_MANAGER = new PromodoroTaskManager();
    } 
    
    public static PromodoroTaskManager getInstance() {
        return Holder.TASK_MANAGER;
    }
  
    // TODO : 2 threads, back-up, marking task complete
    
    public void addTask(PromodoroTask task) {
        synchronized(taskList) {
            if (taskList.size() == MAX_ALLOWED_TASKS) {
                processMaxTaskReached();
            } else if(taskList.size() >= OPTIMAL_NUMBER_OF_TASKS) {
                processOptimalTaskReached();
                taskList.add(task);
            } else {
                taskList.add(task);
            }  
        }
    }
    
    public void removeTask(int taskIndex) {
        synchronized(taskList) {
            taskList.remove(taskIndex);
        }
    }
    
    public List getWorkingQueue() {
        LinkedList<PromodoroTask> workingList = new LinkedList<PromodoroTask>();
        synchronized(taskList) {
            for (int i = 0; i < taskList.size(); i++) {
                workingList.add(taskList.get(i));
                if ((i + 1) % 2 == 0) {
                    workingList.add(new PromodoroTask(PromodoroEnumContainer.TaskType.BREAK));
                } else if ((i + 1) % 4 == 0) {
                    workingList.add(new PromodoroTask(PromodoroEnumContainer.TaskType.BREAK));
                }
            }
        }
        return workingList;
    }

    private void processMaxTaskReached() {
        // alert the user 
        System.out.println("Maximum allowed live tasks is reached");
    }

    private void processOptimalTaskReached() {
        // alert user about optimal task
        System.out.println("Optimal number of tasks is reached");
    }

    private void initializeManager() {
        Runnable backUpTaskRunnable = new Runnable() {

            @Override
            public void run() {
                if (historicalWorkingList.size() >= MAX_ALLOWED_TASKS) {
                    
                }
            }
        };
    }

}
