package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import BLL.RegisterNewBookCopyController;
import BLL.SearchBookController;
import DAL.BookInfo;

public class WhiteBoxNewEntryTest {
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
	void checkBookExists1() {
		SearchBookController controller = new SearchBookController();
		setBook_list();
	    assertEquals(true, controller.checkBookExists("NV0167"));
	}
	
	@Test
	void checkBookExists2() {
		SearchBookController controller = new SearchBookController();
		
	    assertEquals(false, controller.checkBookExists("PH0001"));
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
