package drivy

class SlotController {
    static scaffold = Slot;
    def index() {
        [slotList:Slot.list()]
    }
}
