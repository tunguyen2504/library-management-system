package BLL;

import DAL.BookInfo;
import DAL.BorrowerInfo;
import DAL.BorrowingInfo;

import java.util.ArrayList;
import java.util.Date;

public class ReturnBookController {
    private ArrayList<BorrowingInfo> borrowing_list = new ArrayList<>();
    private BorrowerInfo borrower = new BorrowerInfo();

    public BorrowerInfo getBorrower() {
        return borrower;
    }

    /**
     * Get information of all borrowing that match keyword on a specific catalogue or all catalogues to borrowing_list
     *
     * @param keyword  a string that input to look up data
     * @param category a string that specifies catalogue on where to look up, can be "Borrowing Card" or "Copy number"
     */
    public ArrayList<BorrowingInfo> getBorrowingInfo(String keyword, String category) {
        borrowing_list.clear();
        ArrayList<BorrowingInfo> lists;

        lists = BorrowingInfo.getBorrowingInfo(keyword, category);
        if (lists != null) {
            if (!lists.isEmpty()) {
                for (BorrowingInfo one : lists) {
                    if (one.getDueDate().after(new Date()) && !one.getDueDate().before(new Date())) {
                        if (one.getStatus().equals("Taken")){
                            borrowing_list.add(one);
                        }
                    }
                }
                lists.clear();
                if (borrowing_list.isEmpty())
                    return null;
                return borrowing_list;
            }
        }
        return null;
    }

    /**
     * Update returned book
     *
     * @param info
     * @return true of success, false otherwise
     */
    private boolean updateBookInfo(BorrowingInfo info) {
        int copyNumber = info.getCopyNumber();
        String bookNumber = info.getBookNumber();
        BookInfo book = new BookInfo();

        ArrayList<BookInfo> lists = BookInfo.getBookInfo(bookNumber, "bookNumber");
        if (lists != null) {
            for (BookInfo element : lists) {
                if (element.getSequenceNumber() == copyNumber) {
                    book = element;
                    break;
                }
            }
            book.setStatus("Available");
            return book.updateQuery();
        } else {
            return false;
        }
    }

    private boolean updateBorrowingInfo(BorrowingInfo info) {
        info.setStatus("Returned");
        return info.updateQuery();
    }

    public void setBorrower(BorrowingInfo info) {
        int cardNumber = info.getCardNumber();
        borrower = BorrowerInfo.getBorrower(Integer.toString(cardNumber), "cardID");
    }


    private boolean updateBorrowerStatus() {
        if (!borrower.getBorrowerStatus()) {
            borrower.setBorrowerStatus(true);
        }
        return borrower.updateQuery();
    }

    /**
     * update single borrowing info
     * @param info info of one borrowing
     * @return true if update success, false otherwise
     */
    public boolean update(BorrowingInfo info) {
        if (info == null)
            return false;
        setBorrower(info);
        if (!updateBorrowingInfo(info) || !updateBookInfo(info)) {
            return false;
        } else {
            if (!borrower.getBorrowerStatus()) {
                return updateBorrowerStatus();
            }
        }
        return true;
    }

    /**
     * get book from one borrowing info
     * @param info info of one borrowing
     * @return a bookInfo if found, null otherwise
     */
    public BookInfo getBook(BorrowingInfo info){
        ArrayList<BookInfo> bookInfos = BookInfo.getBookInfo(info.getBookNumber(), "bookNumber");
        if (bookInfos != null){
            if (!bookInfos.isEmpty()){
                for (BookInfo one : bookInfos){
                    if (one.getSequenceNumber() == info.getCopyNumber())
                        return one;
                }
            }
        }
        return null;
    }
}
