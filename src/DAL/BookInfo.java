package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookInfo implements Query {

    private String title;
    private String author;
    private String publisher;
    private String type;
    private String ISBN;
    private String bookID;
    private int sequenceNumber;
    private String status;
    private double price;
    private int numberLeft;
    private int totalCopy;

    public int getTotalCopy() {
        return totalCopy;
    }

    public void setTotalCopy(int totalCopy) {
        this.totalCopy = totalCopy;
    }

    public void setNumberLeft(int numberLeft) {
        this.numberLeft = numberLeft;
    }

    public int getNumberLeft() {
        return numberLeft;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getType() {
        return type;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public BookInfo() {

    }

    public BookInfo(String bookID, String title, String author, double price, int sequenceNumber,
            String ISBN, String type, String publisher, String status) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.price = price;
        this.sequenceNumber = sequenceNumber;
        this.ISBN = ISBN;
        this.type = type;
        this.publisher = publisher;
        this.status = status;
    }

    public BookInfo(int sequenceNumber, String type, String status, double price) {
        this.sequenceNumber = sequenceNumber;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    public BookInfo(String bookID, String title, String author, String publisher, String isbn) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = isbn;
    }

    /**
     * Get information of all book that match keyword on a specific catalogue or
     * all catalogues
     *
     * @param keyword a string that input to look up data
     * @param catalogue a string that specifies catalogue on where to look up,
     * can be "all" for all catalogues
     * @return return the ArrayList Bookinfo of all books that match
     */
    public static ArrayList<BookInfo> getBookInfo(String keyword, String catalogue) {
        ArrayList<BookInfo> book_list = new ArrayList<>();
        MySQLAccess access = new MySQLAccess();
        String query;
        if (catalogue.equals("all")) {
            query = "SELECT * "
                    + "FROM bookinfo b, bookcopyinfo c "
                    + "WHERE b.bookNumber = c.bookNumber "
                    + "AND (b.title LIKE \"%" + keyword + "%\" "
                    + "OR b.author LIKE \"%" + keyword + "%\" "
                    + "OR b.publisher LIKE \"%" + keyword + "%\" "
                    + "OR b.ISBN LIKE \"%" + keyword + "%\") "
                    + "ORDER BY b.title";
        } else {
            query = "SELECT * "
                    + "FROM bookinfo b, bookcopyinfo c "
                    + "WHERE b.bookNumber = c.bookNumber "
                    + "AND b." + catalogue + " LIKE \"%" + keyword + "%\" "
                    + "ORDER BY b.title";
        }
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                String bookID = resultSet.getString("bookNumber");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                int sequenceNumber = resultSet.getInt("copyNumber");
                String ISBN = resultSet.getString("ISBN");
                String type = resultSet.getString("type");
                String publisher = resultSet.getString("publisher");
                String status = resultSet.getString("status");
                BookInfo book = new BookInfo(bookID, title, author, price,
                        sequenceNumber, ISBN, type, publisher, status);
                book_list.add(book);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return null;
        } finally {
            access.close();
        }
        return book_list;
    }

    /**
     * Get information of the specified book
     * @param bookID the unique ID to specify book
     * @return information of the book that matched
     */
    public static BookInfo getSelectedBookInfo(String bookID) {
        BookInfo book = new BookInfo();
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT * FROM bookinfo b, bookcopyinfo c " +
                "WHERE b.bookNumber = c.bookNumber " +
                "AND b.bookNumber = \'" + bookID + "\'";
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                String bookNumber = resultSet.getString("bookNumber");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                String ISBN = resultSet.getString("ISBN");
                int sequenceNumber = resultSet.getInt("copyNumber");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");
                book = new BookInfo(bookNumber, title, author, price,
                        sequenceNumber, ISBN, type, publisher, status);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return null;
        } finally {
            access.close();
        }
        return book;
    }

    /**
     * Get the copy number of the specified book
     * @param bookID the unique ID to specify book
     * @return the copy number of the book that matched
     */
    public static ArrayList<Integer> getSequenceNumber(String bookID) {
        ArrayList<Integer> sequenceNumber_list = new ArrayList();
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT copyNumber FROM bookcopyinfo "
                + "WHERE bookNumber = \'" + bookID + "\'";
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                sequenceNumber_list.add(resultSet.getInt("copyNumber"));
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return null;
        } finally {
            access.close();
        }
        return sequenceNumber_list;
    }

    /**
     * Get list of copy type of specified book
     * @param bookID the unique ID to specify book
     * @return the copy type of the book that matched
     */
    public static ArrayList<String> getCopyType(String bookID) {
        ArrayList<String> copyType_list = new ArrayList();
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT type FROM bookcopyinfo "
                + "WHERE bookNumber = \'" + bookID + "\'";
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                copyType_list.add(resultSet.getString("type"));
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return null;
        } finally {
            access.close();
        }
        return copyType_list;
    }

    /**
     * Get copy type of specified book with specified copy
     * @param copyNumber unique ID to specify book copy
     * @return copy type of book copy of the book that matched
     */
    public static String getCopyTypeByID(int copyNumber, String bookID) {
        String copyType = null;
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT type FROM bookcopyinfo "
                + "WHERE copyNumber = \'" + copyNumber + "\' " +
                "AND bookNumber = \'" + bookID + "\'";
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                copyType = resultSet.getString("type");
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return null;
        } finally {
            access.close();
        }
        return copyType;
    }

    /**
     * Get price of specified book with specified copy
     * @param copyNumber unique ID to specify book copy
     * @return price of book copy of the book that matched
     */
    public static long getPriceByID(int copyNumber) {
        long price = 0L;
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT price FROM bookcopyinfo "
                + "WHERE copyNumber = \'" + copyNumber + "\'";
        System.out.println(query);
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                price = resultSet.getLong("price");
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return 0L;
        } finally {
            access.close();
        }
        return price;
    }

    /**
     * Get status of specified book with specified copy
     * @param copyNumber unique ID to specify book copy
     * @return status of book copy of the book that matched
     */
    public static String getStatusByID(int copyNumber) {
        String status = null;
        MySQLAccess access = new MySQLAccess();
        String query = "SELECT status FROM bookcopyinfo "
                + "WHERE copyNumber = \'" + copyNumber + "\'";
        System.out.println(query);
        try {
            access.connectDB();
            ResultSet resultSet = access.getData(query);
            while (resultSet.next()) {
                status = resultSet.getString("status");
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            return null;
        } finally {
            access.close();
        }
        return status;
    }

    /**
     * add book to db
     *
     * @return true if success, false otherwise
     */
    public boolean addBookQuery() {
        MySQLAccess access = new MySQLAccess();
        String query = "INSERT INTO bookinfo(bookNumber,title,author,publisher,isbn) " + " values (?, ?, ?, ?, ?)";
        try {
            access.connectDB();
            PreparedStatement preparedStmt = access.getPreparedStatement(query);
            preparedStmt.setString(1, this.bookID);
            preparedStmt.setString(2, this.title);
            preparedStmt.setString(3, this.author);
            preparedStmt.setString(4, this.publisher);
            preparedStmt.setString(5, this.ISBN);
            preparedStmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            access.close();
        }
        return false;
    }

    public boolean addCopyQuery() {
        MySQLAccess access = new MySQLAccess();
        String query = "INSERT INTO bookcopyinfo(copyNumber,bookNumber,type,price,status)" + " values (?, ?, ?, ?, ?)";
        try {
            access.connectDB();
            PreparedStatement preparedStmt = access.getPreparedStatement(query);
            preparedStmt.setInt(1, this.sequenceNumber);
            preparedStmt.setString(2, this.bookID);
            preparedStmt.setString(3, this.type);
            preparedStmt.setDouble(4, this.price);
            preparedStmt.setString(5, this.status);
            preparedStmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            access.close();
        }
        return false;
    }

    /**
     * Update information to database
     *
     * @return true if success, false otherwise
     */
    public boolean updateQuery() {
        MySQLAccess access = new MySQLAccess();
        String query = "UPDATE bookcopyinfo "
                + "SET status = \'" + this.status + "\', "
                + "type = \'" + this.type + "\', "
                + "price = \'" + this.price + "\' "
                + "WHERE bookNumber = \'" + this.bookID + "\' AND copyNumber = \'" + this.sequenceNumber + "\' ";
        String queryBook = "UPDATE bookinfo "
                + "SET title = \'" + this.title + "\', "
                + "author = \'" + this.author + "\', "
                + "publisher = \'" + this.publisher + "\', "
                + "ISBN = \'" + this.ISBN + "\' "
                + "WHERE bookNumber = \'" + this.bookID + "\'";
        boolean result = false;
        try {
            access.connectDB();
            int status = access.updateData(query);
            if (status != 0) {
                int bookStatus = access.updateData(queryBook);
                if (bookStatus != 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            access.close();
        }
        return result;
    }
}
