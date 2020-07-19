/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UcUpdateBookInfoTest;

import BLL.UpdateBookInformationController;
import DAL.BookInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author tunguyen
 */
public class WhiteBoxUpdateBookInfoTest {

    private ArrayList<BookInfo> book_lists = new ArrayList<>();

    public WhiteBoxUpdateBookInfoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private void setBook_list() {
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
    void testGetBookInfo() throws Exception {
        System.out.println("getBookInfo");
        setBook_list();
        UpdateBookInformationController controller = new UpdateBookInformationController();
        ArrayList<BookInfo> result = controller.getBookInfo("mocking", "title");
        for (int bookIndex = 0; bookIndex < 5; bookIndex++) {
            assertEquals(book_lists.get(bookIndex).getBookID(), result.get(bookIndex).getBookID());
            assertEquals(book_lists.get(bookIndex).getAuthor(), result.get(bookIndex).getAuthor());
            assertEquals(book_lists.get(bookIndex).getSequenceNumber(), result.get(bookIndex).getSequenceNumber());
            assertEquals(book_lists.get(bookIndex).getISBN(), result.get(bookIndex).getISBN());
            assertEquals(book_lists.get(bookIndex).getPrice(), result.get(bookIndex).getPrice());
            assertEquals(book_lists.get(bookIndex).getType(), result.get(bookIndex).getType());
            assertEquals(book_lists.get(bookIndex).getPublisher(), result.get(bookIndex).getPublisher());
            assertEquals(book_lists.get(bookIndex).getTitle(), result.get(bookIndex).getTitle());
        }
    }

    /**
     * Test of getSelectedBookInfo method, of class
     * UpdateBookInformationController.
     */
    @Test
    public void testGetSelectedBookInfo() {
        System.out.println("getSelectedBookInfo");
        int bookIndex = 1;
        setBook_list();
        UpdateBookInformationController controller = new UpdateBookInformationController();
        BookInfo result = controller.getSelectedBookInfo(bookIndex);
        assertEquals(book_lists.get(bookIndex).getBookID(), result.getBookID());
        assertEquals(book_lists.get(bookIndex).getAuthor(), result.getAuthor());
        assertEquals(book_lists.get(bookIndex).getSequenceNumber(), result.getSequenceNumber());
        assertEquals(book_lists.get(bookIndex).getISBN(), result.getISBN());
        assertEquals(book_lists.get(bookIndex).getPrice(), result.getPrice());
        assertEquals(book_lists.get(bookIndex).getType(), result.getType());
        assertEquals(book_lists.get(bookIndex).getPublisher(), result.getPublisher());
        assertEquals(book_lists.get(bookIndex).getTitle(), result.getTitle());
    }

    /**
     * Test of updateSelectedBook method, of class
     * UpdateBookInformationController.
     */
    @Test
    public void testUpdateSelectedBook() {
        System.out.println("updateSelectedBook");
        String title = "";
        String author = "";
        String publisher = "";
        String type = "";
        double price = 0.0;
        UpdateBookInformationController instance = new UpdateBookInformationController();
        instance.updateSelectedBook(title, author, publisher, type, price);
    }

}
