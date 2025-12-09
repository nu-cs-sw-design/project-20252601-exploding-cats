package domain;

import java.util.List;
import java.util.Map;

public class GameModel {
  private final Deck deck;
  private final TurnOrder turnOrder;
  private final Map<PlayerID, PlayerHand> playerHands;

  public GameModel(Deck deck, TurnOrder turnOrder, Map<PlayerID, PlayerHand> playerHands) {
    this.deck = deck;
    this.turnOrder = turnOrder;
    this.playerHands = playerHands;
  }

  public PlayerID getCurrentPlayer() {
    return turnOrder.getCurrentPlayer();
  }

  public int getDeckSize() {
    return deck.getDeckSize();
  }

  public List<Card> getHandForPlayerID(PlayerID player) {
    return playerHands.get(player).getHand();
  }

  public CardType drawCardForPlayerID(PlayerID playerID) {
    PlayerHand playerHand = playerHands.get(playerID);
    Card newCard = deck.drawCard();
    playerHand.addCardToHand(newCard);
    return newCard.getCardType();
  }
}
