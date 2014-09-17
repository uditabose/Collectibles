package inventory.pricing

import inventory.product.Product

class Pricing {
    Integer pricingId;
    String partNumber;
    String pricingType;
    Double pricingPerUnit;
    String vendor;
    Date pricingDate;
    Integer noOfPieces;
    
    Product product;
    
    static constraints = {
        pricingId (blank:false, nullable: false, unique:true)
        partNumber (blank:true, nullable: true)
        pricingType (inList: ["Quote", "Source"])
        pricingPerUnit(blank:false, nullable: false)
        noOfPieces (nullable: true)
        vendor(blank:false, nullable: false)
        product(nullable: true)
    }
}
