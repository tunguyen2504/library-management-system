package DAL;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BorrowingInfo implements Query {
    private int borrowingID;
    private int cardNumber;
    private String bookNumber;
    private int copyNumber;
    private String status;
    private Date collectDate;
    private Date dueDate;

    public BorrowingInfo() {
    }

    public BorrowingInfo(int borrowingID, int cardNumber, String bookNumber,
                         int copyNumber, String status, Date collectDate, Date dueDate) {
        this.borrowingID = borrowingID;
        this.cardNumber = cardNumber;
        this.bookNumber = bookNumber;
        this.copyNumber = copyNumber;
        this.status = status;
        this.collectDate = collectDate;
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getBorrowingID() {
        return borrowingID;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public Date getCollectDate() {
        return this.collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBorrowingID(int borrowingID) {
        this.borrowingID = borrowingID;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    private String extractDate (Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * Get information of all book that match keyword on a specific catalogue or all catalogues
     *
     * @param keyword  a string that input to look up data
     * @param category a string that specifies catalogue on where to look up, can be "all" for all catalogues
     * @return return the ArrayList Bookinfo of all books that match
     */
    public static ArrayList<BorrowingInfo> getBorrowingInfo(String keyword, String category) {
        ArrayList<BorrowingInfo> list = new ArrayList<>();
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT * FROM borrowinginfo " +
                "WHERE " + category + " = \'" + keyword + "\'";

        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                int borrowingID = resultSet.getInt("borrowingID");
                int cardID = resultSet.getInt("cardID");
                String bookNumber = resultSet.getString("bookNumber");
                int copyNumber = resultSet.getInt("copyNumber");
                String status = resultSet.getString("status");
                Date dueDate = resultSet.getDate("dueDate");
                Date collectDate = resultSet.getDate("collectDate");

                BorrowingInfo oneInfo = new BorrowingInfo(borrowingID, cardID, bookNumber,
                        copyNumber, status, collectDate, dueDate);
                list.add(oneInfo);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        } finally {
            access.close();
        }
        return list;
    }

    /**
     * Update information to database
     * @return true if success, false otherwise
     */
    public boolean updateQuery() {
        MySQLAccess access = new MySQLAccess();
        String query = "UPDATE borrowinginfo " +
                "SET status = \'" + this.status + "\'" +
                "WHERE borrowingID = \'" + this.borrowingID + "\' ";
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
     * execute insert query
     * @return true if success, false otherwise
     */
    public boolean insertQuery() {
        MySQLAccess access = new MySQLAccess();
        String query = "INSERT INTO borrowinginfo (cardID, bookNumber, copyNumber, status, dueDate, collectDate) " +
                "VALUE (\'" + cardNumber + "\', " +
                "\'" + bookNumber + "\', " +
                "\'" + copyNumber + "\', " +
                "\'" + status + "\', " +
                "\'" + extractDate(dueDate) + "\', " +
                "\'" + extractDate(collectDate) + "\')";
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
}
