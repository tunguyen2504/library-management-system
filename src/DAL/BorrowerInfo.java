package DAL;

import java.sql.ResultSet;

public class BorrowerInfo extends User implements Query {
    private boolean isHUST;
    private boolean isBorrowAble;
    private int cardID;

    public BorrowerInfo() {}

    public BorrowerInfo(int userID, String username, String password, String role,
                        String email, String name, String gender, String phoneNumber,
                        boolean isBorrowAble, boolean isHUST, int cardID) {
        super(userID, username, password, role, email, name, gender, phoneNumber);
        this.isBorrowAble = isBorrowAble;
        this.isHUST = isHUST;
        this.cardID = cardID;
    }
    
    public BorrowerInfo(int userID, String username, String name){
        super(userID, username, name);
    }

    public boolean getIsHUST() {return isHUST;}

    public int getCardID() {
        return cardID;
    }

    public boolean getBorrowerStatus() {
        return isBorrowAble;
    }

    public void setBorrowerStatus(boolean isAble) {
        this.isBorrowAble = isAble;
    }

    /**
     * Update information to database
     * @return true if success, false otherwise
     */
    public boolean updateQuery() {
        MySQLAccess access = new MySQLAccess();
        String query = "UPDATE bookcopyinfo " +
                "SET isBorrowAble = \'" + this.isBorrowAble + "\', " +
                "cardID = \'" + this.cardID + "\' " +
                "WHERE borrowerID = \'" + this.getUserID() + "\' ";
        boolean result = false;
        try {
            access.connectDB();
            int status = access.updateData(query);
            if (status != 0)
                result = true;
        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            access.close();
        }
        return result;
    }

    /**
     * get Borrower Info corresponding
     * @param keyword
     * @param catalogue
     * @return a borrowerInfo if found, null otherwise
     */
    public static BorrowerInfo getBorrower(String keyword, String catalogue) {
        BorrowerInfo borrower = null;
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT * " +
                "FROM borrowerinfo bi, userinfo u " +
                "WHERE bi.borrowerID = u.userID " +
                "AND " + catalogue + " = \'" + keyword + "\'";

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
            boolean borrowAble = resultSet.getBoolean("isBorrowAble");
            boolean isHUST = resultSet.getBoolean("isHustStudent");
            int cardID = resultSet.getInt("cardID");

            borrower = new BorrowerInfo(userID, uname, pass, role, email,
                    name, gender, phoneNum, borrowAble, isHUST, cardID);
        } catch (Exception e) {
            System.out.println("Error " + e);
        } finally {
            access.close();
        }
        return borrower;
    }
    
    /**
     * Get information of the specified borrower
     * @param borrowerId an unique Id to specify borrower
     * @return the information of the borrower that matched
     */
    public static BorrowerInfo getBorrowerById (int borrowerId) {
        BorrowerInfo borrower = null;
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT * " +
                "FROM borrowerinfo bi, userinfo u " +
                "WHERE bi.borrowerID = u.userID " +
                "AND bi.borrowerID = " + borrowerId;
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            resultSet.next();
            int userID = resultSet.getInt("userID");
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            borrower = new BorrowerInfo(userID, username, name);
        } catch (Exception e) {
            System.out.println("Error " + e);
        } finally {
            access.close();
        }
        return borrower;
    }
}
