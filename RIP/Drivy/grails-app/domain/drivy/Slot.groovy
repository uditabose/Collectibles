package drivy

class Slot {

    Integer slotNumber
    Date slotDate
    String timeFrom
    String timeTo
    Boolean isRegistered
    Boolean isBooked
    
    static belongsTo = [teacher:Teacher]
    
    static constraints = {
    }
}
