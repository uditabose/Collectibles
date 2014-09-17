package inventory.user

class UserController {
    static scaffold = true
    def index() { 
        [userInstanceList:User.list()]
    }
    
    /*def create() {
        //[userRoleInstanceList:UserRole.list()]
    }*/
}
