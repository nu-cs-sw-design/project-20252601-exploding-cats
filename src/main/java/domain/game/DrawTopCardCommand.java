package domain.game;

public class DrawTopCardCommand extends Command {
  private final Deck deck;
  private final PlayerHand playerHand;

  public DrawTopCardCommand(Deck deck, PlayerHand playerHand) {
    isIrreversible = true;
    this.deck = deck;
    this.playerHand = playerHand;
  }

  void execute() {
    Card newCard = deck.drawCard();
    playerHand.addCardToHand(newCard);
  }
}
