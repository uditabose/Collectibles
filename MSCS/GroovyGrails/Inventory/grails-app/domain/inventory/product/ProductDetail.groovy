package inventory.product

class ProductDetail {
    Integer productDetailId;
    String detType;
    String detailInfo;
    String altPartNumber;
    
    static belongsTo = [product:Product];
    
    static constraints = {
        productDetailId unique:true
        product (nullable:false)
        
    }
}
