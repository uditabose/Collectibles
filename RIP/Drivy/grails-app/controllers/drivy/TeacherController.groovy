package drivy

import java.text.SimpleDateFormat

//import SlotUtil

class TeacherController {
    static scaffold = Teacher;
    
    static final String[] SLOT_START = ["10:00", "11:00", "12:00", "13:00", "14:00"] 
    static final String[] SLOT_END = ["11:00", "12:00", "13:00", "14:00", "15:00"] 
    static final Integer[] SLOT_NUMBER = [1, 2, 3, 4, 5]
    
    static allowedMethods = [dashboard: "GET", index: "GET", 
                                update: "POST", list: "GET", show : "GET"]
    
    //private static final SlotUtil slotUtil = new SlotUtil()
    
    def index() { 
        [teacherList:Teacher.list()]
    }
    
    def mock() {
        if  (params.id) {
            Teacher teacher = Teacher.findByTeacherId(params.id)
            [teacher: teacher]
        } else {
            redirect(action:"index")
        }
    }

    def dashboard() {
        if  (params.id) {
            Teacher teacher = Teacher.findByTeacherId(params.id)
            [teacher: teacher, freeSlots:getAllSlotsOfTheWeek(new Date())]
        } else {
            redirect(action:"index")
        }
    }
    
    def update() {
        
        StringBuilder renderString = new StringBuilder("<span>Updated</span><br/>");
        Teacher teacher = null
        if (params.teacherId) {
            teacher = Teacher.findByTeacherId(params.teacherId)
        }
        if (!teacher) {
            render "<span> No valid teacher data found </span>"
            return
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        if(params.freeSlots) {
            def freeSlots = params.freeSlots
            
            freeSlots.each {
                Slot aSlot = new Slot()
                aSlot.slotDate = sdf.parse(it.split("#")[0])
                aSlot.isRegistered = true
                aSlot.isBooked = false
                int slotNumber = Integer.parseInt(it.split("#")[1])
                aSlot.slotNumber = slotNumber
                aSlot.timeFrom = SLOT_START[slotNumber  - 1]
                aSlot.timeTo = SLOT_END[slotNumber  - 1]
                teacher.addToWeeklySlots(aSlot) 
            }
            
        }
        
        teacher.save(flush: true)
        render (renderString.toString())
    }
    
    private def List<Slot> getAllSlotsOfTheDay() {
        return getAllSlotsOfTheDayByDay(new Date());
    }
    
    
    
    private def List<Slot> getAllSlotsOfTheDayByDay(Date aDate) {
        List<Slot> slotsOfTheDay = [];
        
        Slot aSlot = null
        for (int i = 0; i < SLOT_START.length; i++) {
            aSlot = new Slot();
            aSlot.slotNumber = SLOT_NUMBER[i]
            aSlot.slotDate = aDate
            aSlot.timeFrom = SLOT_START[i]
            aSlot.timeTo = SLOT_END[i]
            slotsOfTheDay.add(aSlot)
        }
        
        return slotsOfTheDay;
    }
    
    private def List<Slot> getAllSlotsOfTheWeek(Date aDate) {
        List<Slot> slotsOfWeek = []
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        Date thisDate = null
        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DATE, i);
            thisDate = cal.getTime()
            slotsOfWeek.addAll(getAllSlotsOfTheDayByDay(thisDate))
        }
        return slotsOfWeek
    }
    
    private def List<Slot> getFreeSlots(List<Slot> bookedSlots, Date aDate) {
        def bookedNumbers = bookedSlots.each {
            it.slotNumber
        }
        
        def freeNumbers = SLOT_NUMBER - bookedNumbers
        List<Slot> freeSlots = new ArrayList<Slot>()
        
        freeNumbers.each {
            Slot slot = new Slot()
            slot = new Slot();
            slot.slotNumber = it
            slot.slotDate = aDate
            slot.timeFrom = SLOT_START[(it - 1)]
            slot.timeTo = SLOT_END[(it - 1)]
            freeSlots << alot
            
        }
    }
}
