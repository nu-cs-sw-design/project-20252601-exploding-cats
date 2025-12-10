package domain;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
  final PlayerID playerID;
  final List<Card> hand;

  private static final String NO_CARD_FOUND_EXCEPTION = "No Card Found";

  public PlayerHand(PlayerID playerID) {
    this.playerID = playerID;
    this.hand = new ArrayList<>();
  }

  void addCardToHand(Card card) {
    hand.add(card);
  }

  void removeCardTypeFromHand(CardType cardType) {
    int index = getIndexOfCardType(cardType);
    hand.remove(index);
  }

  boolean hasCardType(CardType cardType) {
    for (Card card : hand) {
      if (card.getCardType() == cardType) {
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

      if (card.getCardType() == cardType) {
        return i;
      }
    }

    throw new IllegalArgumentException(NO_CARD_FOUND_EXCEPTION);
  }
}
