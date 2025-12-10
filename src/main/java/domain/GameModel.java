package domain;

import java.util.ArrayList;
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

  public List<PlayerID> getOtherPlayersWithNopeCards(PlayerID currentPlayer) {
    List<PlayerID> playersWithNopeCards = new ArrayList<PlayerID>();

    for (PlayerID playerID : PlayerID.values()) {
      if (playerID == currentPlayer) {
        continue;
      }

      if (playerHasCardType(playerID, CardType.NOPE)) {
        playersWithNopeCards.add(playerID);
      }
    }

    return playersWithNopeCards;
  }

  public boolean playerHasCardType(PlayerID playerID, CardType cardType) {
    return playerHands.get(playerID).hasCardType(cardType);
  }

  public boolean gameIsOver() {
    return turnOrder.checkNumberOfAlivePlayers() == 1;
  }

  public void nextPlayerTurn() {
    turnOrder.nextPlayerTurn();
  }
}
