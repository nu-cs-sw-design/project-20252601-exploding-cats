package domain;

class NopeCommand extends Command {
  private final PlayerHand playerHand;

  NopeCommand(PlayerHand playerHand) {
    isIrreversible = false;
    this.playerHand = playerHand;
  }

  void execute() {
    playerHand.removeCardTypeFromHand(CardType.NOPE);
  }
}
