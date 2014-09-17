package inventory.product

class Product {
    
    Integer productId;
    String primaryPartNumber;
    String manufacturer;
    
    static hasMany = [prodDetails:ProductDetail]

    static constraints = {
        productId (blank:false, nullable: false, unique:true)
        primaryPartNumber (blank:false, nullable: false, unique:true)
    }
}
