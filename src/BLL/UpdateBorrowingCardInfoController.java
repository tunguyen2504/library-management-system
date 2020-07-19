package BLL;

import DAL.BorrowerInfo;
import java.util.Date;
import DAL.CardInfo;
import java.util.ArrayList;

/**
 *
 * @author tunguyen
 */
public class UpdateBorrowingCardInfoController {
    private CardInfo cardInfo;
    private CardInfo selectedCardInfo;
    
    /**
     * Get information of the specified card
     * @param cardId a string to identify the selected card
     * @return the detail of the selected card
     */
    public CardInfo getSelectedCardInfo(int cardId) {
        
        return CardInfo.getCard(cardId);
    }
    
    /**
     * Get information of the list of cards
     * @param numb an arbitrary number which the cardID contains
     * @return list of cards that matched
     */
    public ArrayList<CardInfo> getCardInfo(int numb) {
        ArrayList<CardInfo> card_list = CardInfo.getCardList(numb);
        if (card_list != null) {
            if (card_list.isEmpty())
                return null;
        } else {
            return null;
        }
        
        return card_list;
    }
    
    /**
     * Get information of borrower by borrowerID
     * @param borrowerId the unique Id to identify borrower
     * @return a borrower information that matched
     */
    public BorrowerInfo getBorrowerById (int borrowerId) {
        BorrowerInfo borrower = BorrowerInfo.getBorrowerById(borrowerId);
        if (borrower == null) {
            return null;
        }
        return borrower;
    }
    
    /**
     * Update the information of the card identified by cardId
     * @param cardInfo the card that need to be updated
     * @param expiredDate the date that you want to update
     * @return boolean if update successfully or not
     */
    public boolean updateSelectedCardInfo(CardInfo cardInfo, Date expiredDate) {
        cardInfo.setExpiredDate(expiredDate);
        return cardInfo.updateQuery();
    }
    
}
