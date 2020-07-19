package BLL;

import DAL.BookInfo;

import java.util.ArrayList;

public class SearchBookController {
    private ArrayList<BookInfo> book_list = new ArrayList<>();

    /**
     * Get information of all book that match keyword on a specific catalogue or all catalogues
     *
     * @param keyword  a string that input to look up data
     * @param catalogue a string that specifies catalogue on where to look up, can be "all" for all catalogues
     * @return return the ArrayList Bookinfo of all books that match
     */
    public ArrayList<BookInfo> getBookInfo(String keyword, String catalogue) {
        ArrayList<BookInfo> lists = BookInfo.getBookInfo(keyword, catalogue);
        if (lists != null) {
            if (lists.isEmpty())
                return null;
        } else {
            return null;
        }
        book_list.clear();
        for (BookInfo book : lists) {
                book_list.add(book);
        }
        lists.clear();
        int i = 0;
        while (i < this.book_list.size()) {
            int count = 1;
            int j = i + 1;
            while (j < this.book_list.size()) {
                if (this.book_list.get(j).getBookID().equals(this.book_list.get(i).getBookID())) {
                    count++;
                    j++;
                } else
                    break;
            }
            for (int k = i; k < j; k++)
                this.book_list.get(k).setTotalCopy(count);
            i += count;
        }
        
        
        return this.book_list;
    }
    
    /**
     * This function is used to get the book sequenceNumber by bookID
     * @param bookID a String to specify a book
     * @return the ArrayList of sequenceNumber of book copy
     */
    public ArrayList<Integer> getSequenceNumber(String bookID) {
        ArrayList<Integer> sequenceNumber_list = new ArrayList();
        sequenceNumber_list = BookInfo.getSequenceNumber(bookID);
        
        return sequenceNumber_list;
    }
    
    /**
     * This function is used to get the book copyType_list by bookID
     * @param bookID a String to specify a book
     * @return the ArrayList of copyType of book copy
     */
    public ArrayList<String> getCopyType(String bookID) {
        ArrayList<String> copyType_list = new ArrayList();
        copyType_list = BookInfo.getCopyType(bookID);
        
        return copyType_list;
    }
    
    /**
     * This function is used to get the book copyType by bookID
     * @param copyNumber the unique ID of a book copy
     * @return the only copyType that matched
     */
    public String getCopyTypeByID(int copyNumber, String bookNumber) {
        String copyType = null;
        copyType = BookInfo.getCopyTypeByID(copyNumber, bookNumber);

        return copyType;
    }
    
    /**
     * This function is used to get the bookCopy price by bookID
     * @param copyNumber the unique ID of a book copy
     * @return the long type price
     */
    public long getPriceByID(int copyNumber) {
        long price = 0;
        price = BookInfo.getPriceByID(copyNumber);
        
        return price;
    }

    /**
     * This function is used to get the bookCopy status by bookID
     * @param copyNumber the unique ID of a book copy
     * @return the String type status
     */
    public String getStatusByID(int copyNumber) {
        String status = null;
        status = BookInfo.getStatusByID(copyNumber);
        
        return status;
    }
    /**
     * Check if the book exists in the database
     * 
     * @param keyword an input string to search data
     * @return return true if the book exists, false if the book does not exist
     */
	public boolean checkBookExists(String keyword) {
		if (getBookInfo(keyword,"all") == null)
			return false;
		else
			return true;
	}

}
