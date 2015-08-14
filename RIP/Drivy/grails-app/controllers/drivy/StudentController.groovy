package drivy

class StudentController {
    static scaffold = Student;
    def index() { 
        [studentList:Student.list()]
    }
}
