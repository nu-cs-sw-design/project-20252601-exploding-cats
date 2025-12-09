package domain.game;

class ShuffleDeckCommand extends Command {
  private final Deck deck;

  ShuffleDeckCommand(Deck deck) {
    isIrreversible = false;
    this.deck = deck;
  }

  void execute() {
    deck.shuffleDeck();
  }
}
