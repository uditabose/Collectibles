package inventory.product

class ProductController {
    static scaffold = true
    def index() { 
        [productInstanceList: Product.list()]
    }
    
    def detail() {
        if(params.partNo) {
            Product product = Product.findByPrimaryPartNumber(params.partNo)
            print product
            render(view:'detail', model:["product": product])
        }
    }
}
