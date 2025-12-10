package domain;

class InsertExplodingKittenCommand extends Command {
  private final Deck deck;
  private final int indexToInsertAt;

  InsertExplodingKittenCommand(Deck deck, int indexToInsertAt) {
    isIrreversible = true;
    this.deck = deck;
    this.indexToInsertAt = indexToInsertAt;
  }

  void execute() {
    deck.insertExplodingKittenAtIndex(indexToInsertAt);
  }
}
