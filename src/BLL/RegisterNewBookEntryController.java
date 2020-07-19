package BLL;

import DAL.BookInfo;

public class RegisterNewBookEntryController {
    
    /**
     * Add the new book information submitted in the form
     *
     * @param bookID is the ID of the new book entry
     * @param title is the title of the new book entry
     * @param author is the author of the new book entry
     * @param publisher is the publisher of the new book entry
     * @param isbn is the isbn of the new book entry
     */
    public boolean addNewBook(String bookID, String title, String author, String publisher, String isbn){
    	if(bookID.matches("^[A-Z]{2}\\d{4}")){
    	BookInfo newBook = new BookInfo(bookID, title, author, publisher, isbn);
        return newBook.addBookQuery();
    	}
    	return false;
    }
    
    /**
     * Add new book copy information to the database
     *
     * @param bookID is the ID of the book in database
     * @param copy is the copy number of the book in database
     * @param type is the type of the book in database
     * @param price is the price of the book in database
     * @param status is a the status of the book in database
     */
    public boolean addNewCopy(int copy, String bookID, String type, double price, String status){
    	if (checkNewCopyInformation(bookID, copy)){
    	BookInfo newCopy = new BookInfo(copy, type, status, price);
        newCopy.setBookID(bookID);
        return newCopy.addCopyQuery();
    	}else
    		return false;
    }
    
    /**
     * Check new book copy information.
     *
     * @param bookID is the ID of the book in database
     * @param copy is the copy number of the book in database
     * @return  Return false if the information is invalid or not in format, true if the information is valid
     */
    public boolean checkNewCopyInformation(String bookID, int copy){
        if(bookID.matches("^[A-Z]{2}\\d{4}") && copy >= 0)
        	return true;
        else
        	return false;
    }
}
    
