package BLL;

import DAL.BookInfo;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tunguyen
 */
public class UpdateBookInformationController {

    private ArrayList<BookInfo> bookList;

    /**
     * Get information of all book that match the key on a specific category or
     * all categories
     *
     * @param key a string that input to look up data
     * @param catalogue a string that specifies category on where to look up
     * @return the ArrayList bookList of all books that match
     * @throws SQLException if there was a problem when connect or getting data
     * from database
     */
    public ArrayList<BookInfo> getBookInfo(String key, String catalogue) throws SQLException {
        BookInfo bookInfo = new BookInfo();
        this.bookList = bookInfo.getBookInfo(key, catalogue);

        return this.bookList;
    }

    /**
     * Get information of specified book
     * @param bookID the unique ID of a book
     * @return book information
     */
    public BookInfo getSelectedBookInfo(String bookID) {
        BookInfo book;
        book = BookInfo.getSelectedBookInfo(bookID);
        return book;
    }

    /**
     * update information of specified book
     * @param book the bookinfo param
     * @param title the title of a book
     * @param author the author of a book
     * @param publisher the publisher of a book
     * @param ISBN the ISBN code of a book
     * @param sequenceNumber the copyNumber of a book
     * @param type the copyType of a book
     * @param price the price of a book copy
     * @param status the status of a book copy
     * @return boolean if update book successfully or not
     */
    public boolean updateSelectedBook(BookInfo book, String title, String author, String publisher, String ISBN, 
            int sequenceNumber, String type, long price, String status) {
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setISBN(ISBN);
        book.setSequenceNumber(sequenceNumber);
        book.setType(type);
        book.setPrice(price);
        book.setStatus(status);
        
        return book.updateQuery();
    }

}
