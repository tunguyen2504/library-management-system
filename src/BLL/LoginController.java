package BLL;

import DAL.BorrowerInfo;
import DAL.User;

public class LoginController {
    private User user = null;

    public User validateUser(String username, String password) {
        if (username == null || password == null)
            return null;

        this.user = User.getUser(username);
        if (this.user != null) {
            if (password.equals(user.getPassword()))
                return this.user;
        }
        return null;
    }

    public BorrowerInfo getBorrower(String username) {
        if (username == null) {
            return null;
        }
        BorrowerInfo borrower = BorrowerInfo.getBorrower(username, "username");
        if (borrower == null){
            System.out.println("Error db");
        } else {
            return borrower;
        }

        return null;
    }

    public User getUser(String username) {
        if (username == null) {
            return null;
        }
        User user = User.getUser(username);
        if (user == null){
            System.out.println("Error db");
        } else {
            return user;
        }

        return null;
    }
}
