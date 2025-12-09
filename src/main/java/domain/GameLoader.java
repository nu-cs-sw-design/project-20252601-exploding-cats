package domain;

import java.util.HashMap;
import java.util.Map;

public class GameLoader {
  private final int numPlayers;
  private final Deck deck;
  private Map<PlayerID, PlayerHand> playerHands;

  public GameLoader(int numPlayers, Deck deck) {
    this.numPlayers = numPlayers;
    this.deck = deck;
  }

  public Map<PlayerID, PlayerHand> instantiatePlayerHands() {
   this.playerHands = new HashMap<PlayerID, PlayerHand>();

    int numPlayersLeft = numPlayers;
    for (PlayerID playerID : PlayerID.values()) {
      if (numPlayersLeft == 0) {
        break;
      }

      PlayerHand playerHand = new PlayerHand(playerID);
      playerHands.put(playerID, playerHand);
      numPlayersLeft--;
    }

    populatePlayerHands();
    return this.playerHands;
  }

  private void populatePlayerHands() {
    final int startHandSize = 5;
    deck.removeExplodingKittens();
    deck.shuffleDeck();

    for (int cardsDrawn = 0; cardsDrawn < startHandSize; cardsDrawn++) {
      for (PlayerHand hand: playerHands.values()) {
        Card card = deck.drawCard();
        hand.addCardToHand(card);
      }
    }

    deck.reinsertExplodingKittens();
    deck.shuffleDeck();
  }
}
