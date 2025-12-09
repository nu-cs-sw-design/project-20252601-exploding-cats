package domain;

class DefuseCommand extends Command {
  private final PlayerHand playerHand;

  DefuseCommand(PlayerHand playerHand) {
    isIrreversible = true;
    this.playerHand = playerHand;
  }

  void execute() {
    playerHand.removeCardTypeFromHand(CardType.DEFUSE);
    playerHand.removeCardTypeFromHand(CardType.EXPLODING_KITTEN);
  }
}