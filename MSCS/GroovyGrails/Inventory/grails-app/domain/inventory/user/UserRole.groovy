package inventory.user

class UserRole {
    Integer roleId;
    String roleType;
    String roleDesc;
    
    static constraints = {
        roleId unique:true
        roleType blank:false
    }
}
