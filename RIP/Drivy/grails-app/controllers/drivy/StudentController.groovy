package drivy

class StudentController {
    static scaffold = Student;
    def index() { 
        [studentList:Student.list()]
    }
    
    def dashboard() {
        if(params.id) {
            
            Student student = Student.findByStudentId(params.id)
            def teachers = Teacher.findAll()
            
            [student: student, teachers: teachers]
            
        } else {
            redirect(action:"index")
        }
    }
    
    def update() {
        if (!params.studentId || !params.teacherId) {
            render ("<span>No valid student or teacher id present </span>")
            return
        }
        
        if (!params.weeklySlots) {
            redirect(action:"dashboard")
        }
        
        Student student = Student.findByStudentId(params.studentId)
        Teacher teacher = Teacher.findByTeacherId(params.teacherId)
        Lesson aLesson = new Lesson()
        
        aLesson.lessonId = 100
        aLesson.lessonStudent = student
        aLesson.lessonTeacher = teacher
        aLesson.lessonStatus = 1
        aLesson.lessonRating = 1
        aLesson.lessonTitle = (student.studentName + "*" + teacher.teacherName)
        aLesson.lessonSubject = "New Subject"
        
        

        if(teacher?.weeklySlots) {
            params.weeklySlots.each{
                Slot aSlot = teacher.weeklySlots.getAt(Integer.parseInt(it))
                aSlot.isBooked = true
                aLesson.addToLessonSlots(aSlot)
            }
        }
        
        teacher.save(flush: true)
        student.addToWeeklyLesson(aLesson)
        student.save(flush: true)
        aLesson.save(flush:true)
        
        
    }
}
