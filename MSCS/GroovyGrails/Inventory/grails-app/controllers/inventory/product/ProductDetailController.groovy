package inventory.product

class ProductDetailController {
    static scaffold = true
    def index() { 
        [productDetailInstanceList:ProductDetail.list()]
    }
}
