package domain;

import java.util.ArrayList;
import java.util.List;

// TODO: go back into class diagram once I finalize implementation
public class PlayerHand {
  final PlayerID playerID;
  final List<Card> hand;
  boolean isDead;

  private static final String INVALID_INDEX_EXCEPTION = "Invalid Index";
  private static final String NO_CARD_FOUND_EXCEPTION = "No Card Found";

  public PlayerHand(PlayerID playerID) {
    this.playerID = playerID;
    this.hand = new ArrayList<>();
  }

  void addCardToHand(Card card) {
    hand.add(card);
  }

//  void removeCardFromHand(int index) {
//    checkCardIndexIsWithinBounds(index);
//    hand.remove(index);
//    // TODO: only return cardType if actually necessary for one of the 4 required cards
//    // CardType cardType = hand.get(index).getCardType();
//    // return cardType;
//  }

  void removeCardTypeFromHand(CardType cardType) {
    int index = getIndexOfCardType(cardType);
    hand.remove(index);
  }

  boolean hasCardType(CardType cardType) {
    for (Card card : hand) {
      if (card.isCardType(cardType)) {
        return true;
      }
    }
    return false;
  }

  List<Card> getHand() {
    return hand;
  }

  private int getIndexOfCardType(CardType cardType) {
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);

      if (card.isCardType(cardType)) {
        return i;
      }
    }

    throw new IllegalArgumentException(NO_CARD_FOUND_EXCEPTION);
  }

  // TODO: may need command that uses this? I assume this is for selection of card
//  Card getCardAtIndex(int index) {
//    return hand.get(index);
//  }

//  private void checkCardIndexIsWithinBounds(int index) {
//    if (index < 0 || index >= hand.size()) {
//      throw new IllegalArgumentException(INVALID_INDEX_EXCEPTION);
//    }
//  }
}
