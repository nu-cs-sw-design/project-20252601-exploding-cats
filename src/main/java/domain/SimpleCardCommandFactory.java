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

  public Command createCommandWithNoInput(CardType cardType) {
    switch (cardType) {
      case NOPE:
        return new NopeCommand();
      case SHUFFLE:
        return new ShuffleDeckCommand(deck);
      case EXPLODING_KITTEN:
        return new ExplodeCommand(turnOrder);
      default:
        throw new UnsupportedOperationException("Card type requires input: " + cardType);
    }
  }

  public Command createCommandWithPlayerInput(CardType cardType, PlayerID playerID) {
    PlayerHand playerHand = playerHands.get(playerID);

    if (cardType == CardType.DEFUSE) {
      return new DefuseCommand(playerHand);
    } else {
      throw new UnsupportedOperationException("Card type does not require player input: " + cardType);
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
