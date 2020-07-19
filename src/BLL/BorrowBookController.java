package BLL;

import DAL.BookInfo;
import DAL.BorrowerInfo;
import DAL.BorrowingInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BorrowBookController {
    private ArrayList<BookInfo> book_list = new ArrayList<>();

    /**
     * Get information of all book that is Available and Borrowable
     *
     * @param keyword   a string that input to look up data
     * @param catalogue a string that specifies catalogue on where to look up, can be "all" for all catalogues
     * @return non-empty ArrayList of BookInfo, null otherwise
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
            if (book.getStatus().equals("Available") && book.getType().equals("Borrowable")) {
                book_list.add(book);
            }
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
                this.book_list.get(k).setNumberLeft(count);
            i += count;
        }
        return this.book_list;
    }

    /**
     * Get number of current borrowing book of that User
     *
     * @param borrower a BorrowerInfo indicates Borrower using system
     * @return int
     */
    private int getNumberOfCurrentBook(BorrowerInfo borrower) {
        int cardID = borrower.getCardID();
        ArrayList<BorrowingInfo> list = BorrowingInfo.getBorrowingInfo(Integer.toString(cardID), "cardID");
        if (list == null) {
            return 0;
        }
        if (list.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (BorrowingInfo one : list) {
            if (one.getStatus().equals("Taken") || one.getStatus().equals("Pending")) {
                count++;
            }
        }
        return count;
    }

    private boolean updateBookInfo(BookInfo book) {
        book.setStatus("Borrowed");
        return book.updateQuery();
    }

    /**
     * create borrowing info
     * @param borrower borrower who borrow
     * @param book a bookInfo to be borrowed
     * @return true if success, false otherwise
     */
    private boolean createBorrowingInfo(BorrowerInfo borrower, BookInfo book) {
        BorrowingInfo borrowingInfo = new BorrowingInfo();
        borrowingInfo.setStatus("Pending");
        borrowingInfo.setBookNumber(book.getBookID());
        borrowingInfo.setCardNumber(borrower.getCardID());
        borrowingInfo.setCopyNumber(book.getSequenceNumber());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 2);
        Date collectDate = c.getTime();
        c.add(Calendar.MONTH, 2);
        Date dueDate = c.getTime();
        borrowingInfo.setCollectDate(collectDate);
        borrowingInfo.setDueDate(dueDate);
        return borrowingInfo.insertQuery();
    }

    /**
     * check request to checkout
     *
     * @param borrower a BorrowerInfo indicates borrower using system
     * @param select_list an ArrayList of BookInfo contains list of selected books
     * @return 1 if no problem, 2 if not able to borrow, 3 if select excess number of books
     */
    public int checkRequest(BorrowerInfo borrower, ArrayList<BookInfo> select_list) {
        boolean status = borrower.getBorrowerStatus();
        if (!status)
            return 2;
        int numBook = getNumberOfCurrentBook(borrower) + select_list.size();
        if (numBook > 5) {
            return 3;
        }
        return 1;
    }

    /**
     * process checkout
     * @param borrower borrower that do the checkout
     * @param select_list selected book list for the checkout
     * @return 1 if success, 0 otherwise
     */
    public int checkOut(BorrowerInfo borrower, ArrayList<BookInfo> select_list) {
        int numBook = getNumberOfCurrentBook(borrower) + select_list.size();
        if (numBook == 5){
            borrower.setBorrowerStatus(false);
            borrower.updateQuery();
        }
        for (BookInfo book : select_list) {
            if (!updateBookInfo(book) || !createBorrowingInfo(borrower, book))
                return 0;
        }
        return 1;
    }

    /**
     * Warn Borrower time to collect book expired
     *
     * @param borrower a BorrowerInfo indicates borrower using system
     * @return true if there is a overdue date book, false otherwise
     */
    public boolean warnExpireTime(BorrowerInfo borrower) {
        ReturnBookController rController = new ReturnBookController();
        ArrayList<BorrowingInfo> lists = getBorrowingInfo(Integer.toString(borrower.getCardID()), "cardID");
        if (lists != null){
            if (!lists.isEmpty()){
                for (BorrowingInfo one : lists){
                    rController.update(one);
                }
                return true;
            }
        }

        return false;
    }

    /** get borrowing info to check expire time
     *
     * @param keyword keyword to search
     * @param category category applied
     * @return non-empty lists of borrowingInfo, null otherwise
     */
    private ArrayList<BorrowingInfo> getBorrowingInfo(String keyword, String category) {
        ArrayList<BorrowingInfo> borrowingInfos = new ArrayList<>();
        ArrayList<BorrowingInfo> lists;

        lists = BorrowingInfo.getBorrowingInfo(keyword, category);
        if (lists != null) {
            if (!lists.isEmpty()) {
                for (BorrowingInfo one : lists) {
                    if (one.getCollectDate().before(new Date()) && one.getStatus().equals("Pending"))
                        borrowingInfos.add(one);
                }
                lists.clear();
                if (borrowingInfos.isEmpty())
                    return null;
                return borrowingInfos;
            }
        }
        return null;
    }
}
