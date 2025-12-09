package domain.game;

class DefuseOrExplodeCommand extends Command {
  private final PlayerHand playerHand;

  DefuseOrExplodeCommand(PlayerHand playerHand) {
    isIrreversible = true;
    this.playerHand = playerHand;
  }

  void execute() {
    if (playerHand.hasCardType(CardType.DEFUSE)) {
      playerHand.removeCardTypeFromHand(CardType.DEFUSE);
    } else {
      playerHand.playExplode();
    }
  }
}