package domain;

public abstract class Command {
  protected boolean isIrreversible;

  void execute() {}

  boolean isIrreversible() {
    return isIrreversible;
  }
}
