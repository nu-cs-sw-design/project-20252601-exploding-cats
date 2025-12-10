package domain;

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

  public Command createCommandWithPlayerInput(CardType cardType, PlayerID playerID) {
    PlayerHand playerHand = playerHands.get(playerID);

    switch (cardType) {
      case SHUFFLE:
        return new ShuffleDeckCommand(playerHand, deck);
      case NOPE:
        return new NopeCommand(playerHand);
      case DEFUSE:
        return new DefuseCommand(playerHand);
      case EXPLODING_KITTEN:
        return new ExplodeCommand(turnOrder);
      default:
        throw new UnsupportedOperationException("Card type requires player input: " + cardType);
    }
  }

  public Command createCommandWithIndexInput(CardType cardType, int index) {
    if (cardType == CardType.EXPLODING_KITTEN) {
      return new InsertExplodingKittenCommand(deck, index);
    } else {
      throw new UnsupportedOperationException("Card type does not require index input: " + cardType);
    }
  }
}
