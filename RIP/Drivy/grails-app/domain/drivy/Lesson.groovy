package drivy

class Lesson {
    
    Integer lessonId
    String lessonTitle
    String lessonSubject
    Integer lessonRating
    Integer lessonStatus
    
    Teacher lessonTeacher
    Student lessonStudent
    
    static hasMany = [lessonSlots:Slot]

    static constraints = {
    }
}
