package domain.game;

import java.util.Map;

public class SimpleCardCommandFactory {
  private final Deck deck;
  private final TurnOrder turnOrder;
  private final Map<PlayerID, PlayerHand> playerHands;

  public SimpleCardCommandFactory(Deck deck, TurnOrder turnOrder, Map<PlayerID, PlayerHand> playerHands) {
    this.deck = deck;
    this.turnOrder = turnOrder;
    this.playerHands = playerHands;
  }

  // TODO: add remaining methods here
}
