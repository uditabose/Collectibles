package drivy

class TeacherController {
    static scaffold = Teacher;
    def index() { 
        [teacherList:Teacher.list()]
    }
    
    def dashboard() {
        if  (params.id) {
            Teacher teacher = Teacher.findByTeacherId(params.id)
            [teacher: teacher]
        } else {
            redirect(action:"index")
        }
    }
}
