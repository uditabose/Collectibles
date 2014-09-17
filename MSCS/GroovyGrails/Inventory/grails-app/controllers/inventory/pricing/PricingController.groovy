package inventory.pricing

class PricingController {
    static scaffold = true;
    def index() { 
        [pricingInstanceList: Pricing.list()]
    }
    
    def detail(){
        if(params.partNo) {
            List<Pricing> pricingList = Pricing.findAllByPartNumber(params.partNo)
            print pricingList
            render(view:'detail', model:["pricingList": pricingList])
        }
    }
}
