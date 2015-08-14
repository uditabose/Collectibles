package drivy

class LessonController {
    static scaffold = Lesson;
    
    def index() {
        [lessonList:Lesson.list()]  
    }

}
