/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UcUpdateCardInfoTest;

import BLL.UpdateBorrowingCardInfoController;
import DAL.CardInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author tunguyen
 */
public class WhiteBoxUpdateCardInfoTest {
    private CardInfo card;
    
    public WhiteBoxUpdateCardInfoTest() {
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

    private String extractDate (Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    private void setCard() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        card = new CardInfo(2014504201, sdf.parse("2018-02-01"), "Activated", 20145042);
    }
    
    /**
     * Test of getSelectedCardInfo method, of class UpdateBorrowingCardInfoController.
     */
    @Test
    public void testGetSelectedCardInfo() {
        System.out.println("getSelectedCardInfo");
        int cardId = card.getCardID();
        UpdateBorrowingCardInfoController controller = new UpdateBorrowingCardInfoController();
        CardInfo result = controller.getSelectedCardInfo(cardId);
        assertEquals(card.getCardID(), result.getCardID());
        assertEquals(card.getExpiredDate(), result.getExpiredDate());
        assertEquals(card.getStatus(), result.getStatus());
        assertEquals(card.getBorrowerID(), result.getBorrowerID());
    }

    /**
     * Test of getCardStatus method, of class UpdateBorrowingCardInfoController.
     */
    @Test
    public void testGetCardStatus() {
        System.out.println("getCardStatus");
        int cardId = card.getCardID();
        UpdateBorrowingCardInfoController controller = new UpdateBorrowingCardInfoController();
        String result = controller.getCardStatus(cardId);
        assertEquals(card.getStatus(), result);
    }

    /**
     * Test of updateSelectedCardInfo method, of class UpdateBorrowingCardInfoController.
     */
    @Test
    public void testUpdateSelectedCardInfo() {
        System.out.println("updateSelectedCardInfo");
        Date expiredDate = null;
        String status = "";
        int borrowerID = 0;
        UpdateBorrowingCardInfoController instance = new UpdateBorrowingCardInfoController();
//        instance.updateSelectedCardInfo(expiredDate, status, borrowerID);
    }
    
}
