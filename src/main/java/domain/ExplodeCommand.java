package domain;

public class ExplodeCommand extends Command {
  private final TurnOrder turnOrder;

  ExplodeCommand(TurnOrder turnOrder) {
    isIrreversible = true;
    this.turnOrder = turnOrder;
  }

  void execute() {
    turnOrder.playExplode();
  }
}
