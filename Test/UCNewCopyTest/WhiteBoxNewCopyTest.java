package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import BLL.RegisterNewBookCopyController;
import BLL.SearchBookController;
import DAL.BookInfo;

public class WhiteBoxNewCopyTest {
	private ArrayList<BookInfo> book_lists = new ArrayList<>();
    private void setBook_list (){
        book_lists.clear();
        BookInfo book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 1, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 2, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 3, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 5, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
        book = new BookInfo("NV0167", "To kill a mocking bird", "Harper Lee",
                50000, 6, "9788373015470", "Borrowable", "Grand Central", "Available");
        book_lists.add(book);
    }

	@Test
	void getBookNull() {
	    SearchBookController controller = new SearchBookController();

	    assertEquals(null, controller.getBookInfo("qwerty", "title"));
	}
	
	@Test
	void getBookList(){
		SearchBookController controller = new SearchBookController();
	    setBook_list();
	    ArrayList<BookInfo> result = controller.getBookInfo("mock", "title");
	    for (int i = 0; i < 5; i++){
	        assertEquals(book_lists.get(i).getBookID(), result.get(i).getBookID());
	        assertEquals(book_lists.get(i).getAuthor(), result.get(i).getAuthor());
	        assertEquals(book_lists.get(i).getSequenceNumber(), result.get(i).getSequenceNumber());
	        assertEquals(book_lists.get(i).getISBN(), result.get(i).getISBN());
	        assertEquals(book_lists.get(i).getPrice(), result.get(i).getPrice(),0);
	        assertEquals(book_lists.get(i).getStatus(), result.get(i).getStatus());
	        assertEquals(book_lists.get(i).getType(), result.get(i).getType());
	        assertEquals(book_lists.get(i).getPublisher(), result.get(i).getPublisher());
	        assertEquals(book_lists.get(i).getTitle(), result.get(i).getTitle() );
	    }
	}
	
	@Test
	void checkSuccessInformation() {
		RegisterNewBookCopyController controller = new RegisterNewBookCopyController();
	    BookInfo book = new BookInfo();
	    book.setBookID("PH1000");
	    book.setSequenceNumber(10);
	    assertEquals(true, controller.checkNewCopyInformation(book.getBookID(),book.getSequenceNumber()));
	}
	
	@Test
	void checkInformation1() {
		RegisterNewBookCopyController controller = new RegisterNewBookCopyController();
	    BookInfo book = new BookInfo();
	    book.setBookID("PHYSICS01");
	    book.setSequenceNumber(10);
	    assertEquals(false, controller.checkNewCopyInformation(book.getBookID(),book.getSequenceNumber()));
	}
	
	@Test
	void checkInformation2() {
		RegisterNewBookCopyController controller = new RegisterNewBookCopyController();
	    BookInfo book = new BookInfo();
	    book.setBookID("PH1000");
	    book.setSequenceNumber(-5);
	    assertEquals(false, controller.checkNewCopyInformation(book.getBookID(),book.getSequenceNumber()));
	}
}
