package inventory.user

class UserRoleController {
    
    static scaffold = true

    def index() {
        [userRoleInstanceList:UserRole.list()]
    }
}
