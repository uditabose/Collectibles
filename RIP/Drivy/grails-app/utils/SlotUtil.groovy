
import drivy.Slot

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Udita <udita.bose@nyu.edu>
 */
class SlotUtil {
    
    static final String[] SLOT_START = ["10:00", "11:00", "12:00", "13:00", "14:00"] 
    static final String[] SLOT_END = ["11:00", "12:00", "13:00", "14:00", "15:00"] 
    static final Integer[] SLOT_NUMBER = [100, 200, 300, 400, 500]
    
    def List<Slot> getAllSlotsOfTheDay() {
        return getAllSlotsOfTheDay(new Date());
    }
    
    def List<Slot> getAllSlotsOfTheDay(Date aDate) {
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
    
    def List<Slot> getAllSlotsOfTheWeek(Date aDate) {
        List<Slot> slotsOfWeek = []
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        
        Date thisDate = null
        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DATE, i);
            thisDate = cal.getTime()
            slotsOfWeek.addAll(getAllSlotsOfTheDay(thisDate))
        }
        return slotsOfWeek
    }
    
    def List<Slot> getFreeSlots(List<Slot> bookedSlots, Date aDate) {
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
            slot.timeFrom = SLOT_START[(it/100 - 1)]
            slot.timeTo = SLOT_END[(it/100 - 1)]
            freeSlots << alot
            
        }
        
        
    }
	
}

