package DAL;

import java.sql.ResultSet;

public class User implements Query{
    private int userID;
    private String username;
    private String password;
    private String role;
    private String email;
    private String name;
    private String gender;
    private String phoneNumber;

    public User() {

    }

    public User(int userID, String username, String password, String role, String email,
                String name, String gender, String phoneNumber) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
    
    public User(int userID, String username, String name) {
        this.userID = userID;
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public int getUserID() {
        return userID;
    }

    /**
     * Get information of the specified user
     * @param username to identify the user
     * @return information of user that matched
     */
    public static User getUser(String username) {
        User user = null;
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT * FROM userinfo WHERE username='" + username + "'";
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            resultSet.next();
            int userID = resultSet.getInt("userID");
            String uname = resultSet.getString("username");
            String name = resultSet.getString("name");
            String gender = resultSet.getString("gender");
            String phoneNum = resultSet.getString("phoneNumber");
            String pass = resultSet.getString("password");
            String role = resultSet.getString("role");
            String email = resultSet.getString("email");
            user = new User(userID, uname, pass, role, email, name, gender, phoneNum);
        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            access.close();
        }
        return user;
    }

    @Override
    public boolean updateQuery() {
        return false;
    }
}
