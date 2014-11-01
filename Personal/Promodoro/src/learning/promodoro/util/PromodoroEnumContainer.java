

package learning.promodoro.util;

import learning.promodoro.util.PromodoroEnumContainer.TaskColour;

/**
 *
 * @author Udita
 */
public class PromodoroEnumContainer {
    public enum TaskColour {
        GRAY("#EEEEEE"), 
        YELLOW("#FFEA14"),
        GREEN("#00B815"),
        RED("#C42015"),
        BLUE("#3508C4"),
        PURPLE("#9908C4");
        
        private String colourCode;
        TaskColour(String colourCode) {
            this.colourCode = colourCode;
        }
        
        public String getColourCode() {
            return this.colourCode;
        }
    }
    
    public enum TaskType {
        BREAK(TaskColour.GRAY, 100),
        LONGBREAK(TaskColour.GRAY, 150),
        SIMPLE(TaskColour.BLUE, 200),
        IMPORTANT(TaskColour.PURPLE, 300),
        WIP(TaskColour.YELLOW, 700),
        FAILED(TaskColour.RED, 600),
        DONE(TaskColour.GREEN, 1000);
            
        private int taskWeight;
        private TaskColour taskColour;
        TaskType(TaskColour taskColour, int taskWeight) {
            this.taskColour = taskColour;
            this.taskWeight = taskWeight;
        }
        
        public TaskColour getTaskColour() {
            return this.taskColour;
        }
        
        public int getTaskWeight() {
            return this.taskWeight;
        }
    }
}
