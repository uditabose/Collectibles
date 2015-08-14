package drivy

class Teacher {
    
    Integer teacherId
    String teacherName
    
    static hasMany = [weeklySlots:Slot];
    
    static constraints = {
        
    }
}
