package inventory.user

class User {
    Integer userId;
    String userName;
    String password;
    UserRole userRole;

    static constraints = {
        //userId unique:true
        //userName blank:false
        //userRole nullable:true
    }
}
