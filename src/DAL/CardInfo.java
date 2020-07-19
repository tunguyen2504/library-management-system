package DAL;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CardInfo implements Query {

    private int cardID;
    private Date expiredDate;
    private String status;
    private int borrowerID;

    public CardInfo() {
    }

    public CardInfo(int cardID, Date expiredDate, String status, int borrowerID) {
        this.cardID = cardID;
        this.expiredDate = expiredDate;
        this.status = status;
        this.borrowerID = borrowerID;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    /**
     * get date part of object date
     *
     * @param date object date
     * @return string contains date in format: yyyy-mm-dd
     */
    private String extractDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * get card by cardID
     *
     * @param cardId ID number of card
     * @return
     */
    public static CardInfo getCard(int cardId) {
        CardInfo cardInfo = null;
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT * "
                + "FROM cardinfo "
                + "WHERE cardID = " + cardId ;
        System.out.println(query);
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            resultSet.next();
            int cardID = resultSet.getInt("cardID");
            Date expiredDate = resultSet.getDate("expiredDate");
            String status = resultSet.getString("status");
            int borrowerID = resultSet.getInt("borrowerID");

            cardInfo = new CardInfo(cardID, expiredDate, status, borrowerID);
        } catch (Exception e) {
            System.out.println("Error " + e);
        } finally {
            access.close();
        }
        return cardInfo;
    }

    /**
     * Get the information of the list of cards
     * @param numb a number input to search for the card
     * @return the list of cards that matched
     */
    public static ArrayList<CardInfo> getCardList(int numb) {
        ArrayList<CardInfo> card_list = new ArrayList<>();
        MySQLAccess access = new MySQLAccess();
        String query;

        query = "SELECT * "
                + "FROM cardinfo "
                + "WHERE cardID LIKE \"%" + numb + "%\"";
        
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                int cardId = resultSet.getInt("cardID");
                Date expiredDate = resultSet.getDate("expiredDate");
                String status = resultSet.getString("status");
//                if (resultSet.getInt("borrowerID") != 0) {
                int borrowerId = resultSet.getInt("borrowerID");
//                }
                CardInfo cardInfo = new CardInfo(cardId, expiredDate, status, borrowerId);
                card_list.add(cardInfo);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return null;
        } finally {
            access.close();
        }
        return card_list;
    }

    /**
     * Update db
     *
     * @return true if success, false otherwise
     */
    public boolean updateQuery() {
        MySQLAccess access = new MySQLAccess();
        String query = "UPDATE cardinfo "
                + "SET status = \'" + this.status + "\', "
                + "expiredDate = \'" + extractDate(this.expiredDate) + "\' "
                + "WHERE borrowerID = \'" + this.borrowerID + "\' ";
        boolean result = false;
        try {
            access.connectDB();
            int status = access.updateData(query);
            if (status != 0) {
                result = true;
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            access.close();
        }
        return result;
    }
}
