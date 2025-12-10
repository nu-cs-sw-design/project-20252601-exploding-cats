package domain;

class ShuffleDeckCommand extends Command {
  private final PlayerHand playerHand;
  private final Deck deck;

  ShuffleDeckCommand(PlayerHand playerHand, Deck deck) {
    isIrreversible = false;
    this.deck = deck;
    this.playerHand = playerHand;
  }

  void execute() {
    deck.shuffleDeck();
    playerHand.removeCardTypeFromHand(CardType.SHUFFLE);
  }
}
