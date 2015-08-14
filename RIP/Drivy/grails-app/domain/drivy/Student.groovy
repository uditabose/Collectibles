package drivy

class Student {
    
    Integer studentId
    String studentName
    
    static hasMany = [weeklyLesson:Lesson]
    
    static constraints = {
    }
}
