package domain;

class DefuseOrExplodeCommand extends Command {
  private final PlayerHand playerHand;
  private final TurnOrder turnOrder;

  DefuseOrExplodeCommand(PlayerHand playerHand, TurnOrder turnOrder) {
    isIrreversible = true;
    this.playerHand = playerHand;
    this.turnOrder = turnOrder;
  }

  // TODO: do i also incorporate InsertExplodingKitten command here somehow?
  void execute() {
    if (playerHand.hasCardType(CardType.DEFUSE)) {
      playerHand.removeCardTypeFromHand(CardType.DEFUSE);
    } else {
      turnOrder.playExplode();
    }
  }
}