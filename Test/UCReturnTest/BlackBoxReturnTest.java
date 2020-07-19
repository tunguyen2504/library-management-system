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

class BlackBoxReturnTest {
    private BorrowingInfo info = new BorrowingInfo();
    private BorrowerInfo borrower = new BorrowerInfo();

    private void setInfo(){
        Date dueDate = null;
        Date collectDate = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dueDate = formatter.parse("2018-02-05");
            collectDate = formatter.parse("2017-12-05");
        } catch (ParseException e) {
            System.out.println("Error parse date");
        }
        info = new BorrowingInfo(4, 2014102048, "NV0167", 6, "Taken", collectDate, dueDate);
    }

    private String extractDate (Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @Test
    void getBorrowingInfo() {
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
    void getNullBorrowingInfo(){
        ReturnBookController controller = new ReturnBookController();
        setInfo();
        ArrayList<BorrowingInfo> listBorrowing = controller.getBorrowingInfo("20131020489", "cardID");

        assertEquals(null, listBorrowing);
    }

    @Test
    void updateFail() {
        ReturnBookController controller = new ReturnBookController();
        assertEquals(false, controller.update(null));
    }

//    @Test
//    void updateSuccess(){
//        setInfo();
//        ReturnBookController controller = new ReturnBookController();
//        assertEquals(true, controller.update(info));
//    }
}