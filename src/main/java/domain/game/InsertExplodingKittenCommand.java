package domain.game;

public class InsertExplodingKittenCommand extends Command {
  Deck deck;
  int indexToInsertAt;

  public InsertExplodingKittenCommand(Deck deck, int indexToInsertAt) {
    isIrreversible = true;
    this.deck = deck;
    this.indexToInsertAt = indexToInsertAt;
  }

  void execute() {
    deck.insertExplodingKittenAtIndex(indexToInsertAt);
  }
}
