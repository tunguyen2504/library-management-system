package UCBorrowBookTest;

import BLL.BorrowBookController;
import DAL.BookInfo;
import DAL.BorrowerInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlackBoxBorrowTest {
    private ArrayList<BookInfo> book_lists = new ArrayList<>();
    private BorrowerInfo borrower = new BorrowerInfo();

    private void setBorrower(){
        borrower = new BorrowerInfo(1, "thanhnn", "thanh295", "Borrower", "thanh@gmail.com",
                "thanh", "Male", "0911111111", true, true, 2014102048);
    }
    private void setBook_list (){
        book_lists.clear();
        BookInfo book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 1, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 2, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 4, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 5, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 6, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
    }

    // UCBorrowBookTest get book info
    @Test
    void getNotNullBookInfo() throws Exception {
        BorrowBookController controller = new BorrowBookController();
        setBook_list();
        ArrayList<BookInfo> result = controller.getBookInfo("mocking", "title");
        for (int i = 0; i < 5; i++){
            assertEquals(book_lists.get(i).getBookID(), result.get(i).getBookID());
            assertEquals(book_lists.get(i).getAuthor(), result.get(i).getAuthor());
            assertEquals(book_lists.get(i).getSequenceNumber(), result.get(i).getSequenceNumber());
            assertEquals(book_lists.get(i).getISBN(), result.get(i).getISBN());
            assertEquals(book_lists.get(i).getPrice(), result.get(i).getPrice());
            assertEquals(book_lists.get(i).getStatus(), result.get(i).getStatus());
            assertEquals(book_lists.get(i).getType(), result.get(i).getType());
            assertEquals(book_lists.get(i).getPublisher(), result.get(i).getPublisher());
            assertEquals(book_lists.get(i).getTitle(), result.get(i).getTitle() );
        }
    }

    @Test
    void getNullBookInfo() throws Exception {
        BorrowBookController controller = new BorrowBookController();

        assertEquals(null, controller.getBookInfo("asdsadasdasdas", "title"));
    }


    // UCBorrowBookTest check request
    @Test
    void checkSuccessRequest() {
        setBook_list();
        setBorrower();
        book_lists.remove(0);
        BorrowBookController controller = new BorrowBookController();
        assertEquals(3, controller.checkRequest(borrower, book_lists));
    }

    @Test
    void checkExcessNumberRequest(){
        setBorrower();
        setBook_list();
        BorrowBookController controller = new BorrowBookController();
        assertEquals(3, controller.checkRequest(borrower, book_lists));
    }

    @Test
    void checkQualifiedRequest(){
        setBorrower();
        setBook_list();
        borrower.setBorrowerStatus(false);
        BorrowBookController controller = new BorrowBookController();
        assertEquals(2, controller.checkRequest(borrower, book_lists));
    }


    // UCBorrowBookTest check out (only UCBorrowBookTest connect DB)
    @Test
    void checkOut() {}
}