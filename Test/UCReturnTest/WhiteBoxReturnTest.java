package UCReturnTest;

import BLL.ReturnBookController;
import DAL.BorrowerInfo;
import DAL.BorrowingInfo;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WhiteBoxReturnTest {
    private BorrowingInfo info = new BorrowingInfo();
    private BorrowerInfo borrower = new BorrowerInfo();

    private void setInfo(){
        Date dueDate = null;
        Date collectDate = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dueDate = formatter.parse("2018-01-01");
            collectDate = formatter.parse("2017-11-17");
        } catch (ParseException e) {
            System.out.println("Error parse date");
        }
        info = new BorrowingInfo(1, 2014102048, "NV0167", 3, "Taken", collectDate, dueDate);
    }

    private String extractDate (Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @Test
    void getBorrowingInfo_incorrectKeyword() {
        ReturnBookController controller = new ReturnBookController();
        setInfo();
        ArrayList<BorrowingInfo> listBorrowing = controller.getBorrowingInfo("21321312", "cardID");
        BorrowingInfo one = null;
        if (listBorrowing != null) {
            one = listBorrowing.get(0);
        }
        assertEquals(null, one);
    }

    @Test
    void getBorrowingInfo_noMoreElement(){
        ReturnBookController controller = new ReturnBookController();
        setInfo();
        ArrayList<BorrowingInfo> listBorrowing = controller.getBorrowingInfo("Returned", "status");
        BorrowingInfo one = null;
        if (listBorrowing != null) {
            one = listBorrowing.get(0);
        }
        assertEquals(null, one);
    }

    @Test
    void getBorrowingInfo_list(){
        ReturnBookController controller = new ReturnBookController();
        setInfo();
        ArrayList<BorrowingInfo> listBorrowing = controller.getBorrowingInfo("2014102048", "cardID");
        BorrowingInfo one = new BorrowingInfo();
        if (listBorrowing != null) {
            one = listBorrowing.get(0);
        }

        assertEquals(info.getBorrowingID(), one.getBorrowingID());
        assertEquals(info.getCardNumber(), one.getCardNumber());
        assertEquals(info.getBookNumber(), one.getBookNumber());
        assertEquals(info.getCopyNumber(), one.getCopyNumber());
        assertEquals(info.getStatus(), one.getStatus());
        assertEquals(extractDate(info.getCollectDate()), one.getCollectDate().toString());
        assertEquals(info.getDueDate(), one.getDueDate());
    }

    @Test
    void update() {}
}